package ca.georgebrown.chatbot

import grails.transaction.Transactional


@Transactional
class IntentsService {

    def errorsEscalationService

    // This function returns top 5 intents sorted by frequency.
    def getTopFivePopular() {
        def c = ChatbotIntents.createCriteria()
        def escalationList = errorsEscalationService.getEscalationReasons()
        def popularList = c.list {
            order("freq", "desc")
            escalationList.each() { def reason ->
                ne("intent", reason)
            }
            maxResults(5)
        }
        return popularList
    }

    // This function returns top 5 categories sorted by frequency.
    def getCategoryRankings() {
        def c = ChatbotIntentTypes.createCriteria()
        def categoryList = c.list {
            order("freq", "desc")
            ne("category", "escalation")
            maxResults(5)
        }
        return categoryList
    }

    // This function returns the list of utterances by popularity or date.
    def getUtterancesByCriteria(def selection) {
        def chatbotIntentUtteranceList = ChatbotIntentUtterance.findAllByIntentNotInList(errorsEscalationService.getEscalationReasons()).groupBy {it.utterance}
        def finalUtteranceList = []
        chatbotIntentUtteranceList.each() { def utterance ->
            if (utterance.value.size() > 1) utterance.value.sort{a,b -> b.timeSentMessage <=> a.timeSentMessage}
            finalUtteranceList.add(utterance)
        }

        if (selection == 'popular') {
            finalUtteranceList.sort{-it.value.size()}
            return finalUtteranceList
        }
        else if (selection == 'date') {
            finalUtteranceList.sort{it.value[0].timeSentMessage}
            finalUtteranceList = finalUtteranceList.reverse()
            return finalUtteranceList
        }
    }

    // This function is used when the admin goes to Search -> select intent -> change the description for the specific intent.
    def updateDescription(def params) {
        def intent = params.Intent
        ChatbotIntents chatbotIntents = ChatbotIntents.findByIntent(intent)

        if (chatbotIntents) {
            chatbotIntents.description = params."${intent}.description" ?: ""
            chatbotIntents.save(flush: true, failOnError: true)
        }
    }

    // This function is used when what the utterance does not match the intent that is supposed to be invoked.
    def updateUtteranceCorrect(def params) {
        def chatbotIntentUtteranceList = ChatbotIntentUtterance.list()

        chatbotIntentUtteranceList.each() { ChatbotIntentUtterance chatbotIntentUtterance ->
            if (params."${chatbotIntentUtterance.utterance}.incorrect" == "on") {
                chatbotIntentUtterance.incorrect = true
                chatbotIntentUtterance.correctIntent = params."${chatbotIntentUtterance.utterance}.correct_intent"
            }
            else {
                chatbotIntentUtterance.incorrect = false
                chatbotIntentUtterance.correctIntent = chatbotIntentUtterance.intent
            }
            chatbotIntentUtterance.save(flush: true, failOnError: true)
        }
    }

    // This function updates the intent description.
    def updateIntentDescription(def newDescription, def intent) {
        ChatbotIntents chatbotIntents = ChatbotIntents.findByIntent(intent)
        chatbotIntents.description = newDescription
        chatbotIntents.save(flush: true, failOnError: true)
    }

    // This function gets the mismatch intent list.
    def getIntentMismatchList(def chatToken) {
        def chatbotIntentUtteranceList = ChatbotIntentUtterance.findAllByChatToken(chatToken)
        def mismatchList = []

        chatbotIntentUtteranceList.each() { ChatbotIntentUtterance chatbotIntentUtterance ->
            if (chatbotIntentUtterance.incorrect) mismatchList.add(chatbotIntentUtterance)
        }

        return mismatchList
    }

    // This function updates the description and category for the intent.
    def updateIntentData(def params) {
        if (!params?.intents) return null
        def intentList = ChatbotIntents.list()

        intentList.each() { ChatbotIntents chatbotIntent ->
            if (params."${chatbotIntent.intent}.description" != "") {
                def newDescription = params."${chatbotIntent.intent}.description"
                updateIntentDescription(newDescription, chatbotIntent.intent)
            }
            if (params."${chatbotIntent.intent}.category" && params."${chatbotIntent.intent}.category" != "" && chatbotIntent.category != params."${chatbotIntent.intent}.category") {
                chatbotIntent.category = params."${chatbotIntent.intent}.category"
            }
            chatbotIntent.save(flush: true, failOnError: true)
        }

        return updateCategoryFreq()
    }

    // This function updates the frequency of the intent category. (when the category of the intent changes)
    def updateCategoryFreq() {
        def categoryList = ChatbotIntentTypes.list()

        categoryList.each() { ChatbotIntentTypes chatbotIntentTypes ->
            chatbotIntentTypes.freq = 0
            def chatbotIntentList = ChatbotIntents.findAllByCategory(chatbotIntentTypes.category)

            chatbotIntentList.each() { ChatbotIntents chatbotIntents ->
               chatbotIntentTypes.freq += chatbotIntents.freq
            }

            chatbotIntentTypes.save(flush: true, failOnError: true)
        }
    }

    // This function adds a new category for the intent.
    def addNewCategory(def newCategory) {
        ChatbotIntentTypes newType = new ChatbotIntentTypes(category: newCategory)
        newType.save(flush: true, failOnError: true)
    }

    // This function deletes a category for the intent.
    def deleteCategory(def category) {
        ChatbotIntentTypes type = ChatbotIntentTypes.findByCategory(category)
        type.delete(flush: true)
    }

    // This function updates the category description.
    def updateCategoryDescription(def newDescription, def category) {
        ChatbotIntentTypes chatbotIntentTypes = ChatbotIntentTypes.findByCategory(category)
        chatbotIntentTypes.description = newDescription
        chatbotIntentTypes.save(flush: true, failOnError: true)
    }
}
