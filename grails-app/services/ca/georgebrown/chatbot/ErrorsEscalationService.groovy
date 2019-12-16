package ca.georgebrown.chatbot

import grails.transaction.Transactional


@Transactional
class ErrorsEscalationService {

    def intentsService

    // This function returns the escalation reasons.
    def getEscalationReasons() {
        def escalations = ChatbotEscalation.list()
        def escalationReasons = []

        escalations.each() { ChatbotEscalation chatbotEscalation ->
            escalationReasons.add(chatbotEscalation.reason)
        }

        return escalationReasons
    }

    // This function updates the processing error's state to "Resolved."
    def updateProcessingErrors(def params) {
        ChatbotTranscript chatbotTranscript = ChatbotTranscript.findById(params.transcriptId)
        chatbotTranscript.resolved = true
        chatbotTranscript.save(flush: true, failOnError: true)
    }

    // This function updates the unrecognized error's state to "Resolved."
    def updateEscalationAsResolved(def params) {
        def chatbotTranscripts = ChatbotTranscript.findAllByChatMessageAndChatToken(params.utterance, params.token)

        chatbotTranscripts.each() { ChatbotTranscript chatbotTranscript ->
            chatbotTranscript.resolved = true
            chatbotTranscript.save(flush: true, failOnError: true)
        }
    }

    def updateUnrecognizedAsResolved(def params) {
        def chatbotTranscripts = ChatbotTranscript.findAllByChatMessageAndResolved(params.utterance, false)

        chatbotTranscripts.each() { ChatbotTranscript chatbotTranscript ->
            chatbotTranscript.resolved = true
            chatbotTranscript.save(flush: true, failOnError: true)
        }
    }

    // This function changes the mismatch error's intent to the corrent intent
    def updateMismatchErrors(def params) {
        ChatbotIntentUtterance chatbotIntentUtterance = ChatbotIntentUtterance.findByUtterance(params.utterance)
        chatbotIntentUtterance.intent = chatbotIntentUtterance.correctIntent
        chatbotIntentUtterance.incorrect = false
        chatbotIntentUtterance.save(flush: true, failOnError: true)
        def chatbotTranscripts = ChatbotTranscript.findAllByChatMessage(params.utterance)

        chatbotTranscripts.each() { ChatbotTranscript chatbotTranscript ->
            chatbotTranscript.resolved = !chatbotIntentUtterance.intent in getEscalationReasons()
            chatbotTranscript.save(flush: true, failOnError: true)
        }
    }

    // This function adds a new escalation. If there is an existing intent with the same name, it creates a row in escalation table with same frequency and description.
    def addNewEscalation(def escalation) {
        if(ChatbotEscalation.findByReason(escalation)) return null

        ChatbotEscalation chatbotEscalation = new ChatbotEscalation(reason: escalation)
        ChatbotIntents chatbotIntents = ChatbotIntents.findByIntent(escalation)

        if(!chatbotIntents) chatbotIntents = new ChatbotIntents(intent: escalation, category: "escalation", correctIntent: escalation)
        else {
            chatbotIntents.category = "escalation"
            def freq = chatbotIntents.freq
            chatbotEscalation.freq = freq
            chatbotEscalation.description = chatbotIntents.description
            ChatbotIntentTypes chatbotIntentTypes = ChatbotIntentTypes.findByCategory("escalation")
            chatbotIntentTypes.freq += freq
            chatbotIntentTypes.save(flush: true, failOnError: true)
        }

        chatbotIntents.save(flush: true, failOnError: true)
        chatbotEscalation.save(flush: true, failOnError: true)
    }

    // This function deletes an escalation. If there is an existing intent with the same name, the intent will re-appear in "See Intent Data" tab.
    def deleteEscalation(def escalation) {
        ChatbotEscalation chatbotEscalation = ChatbotEscalation.findByReason(escalation)
        ChatbotIntentTypes chatbotIntentTypes = ChatbotIntentTypes.findByCategory("escalation")

        if(chatbotEscalation && chatbotIntentTypes) {
            chatbotIntentTypes.freq -= chatbotEscalation.freq
            chatbotIntentTypes.save(flush: true, failOnError: true)
            chatbotEscalation.delete(flush: true)
        }
    }

    // This function updates a description for a specific intent. If there is an existing intent with the same name, it also updates a description for that intent, too.
    def updateEscalationDescription(def newDescription, def reason) {
        ChatbotEscalation chatbotEscalation = ChatbotEscalation.findByReason(reason)
        chatbotEscalation.description = newDescription
        chatbotEscalation.save(flush: true, failOnError: true)
        intentsService.updateIntentDescription(newDescription, reason)
    }

    // This function returns the percentage of escalation over the total intent invoked.
    def percentageOfEscalation() {
        def intentList = ChatbotIntents.list()
        def escalationList = ChatbotEscalation.list()
        double escalationCount = 0
        double intentCount = 0

        escalationList.each() { ChatbotEscalation chatbotEscalation ->
            escalationCount += chatbotEscalation.freq
        }

        intentList.each() { ChatbotIntents chatbotIntents ->
            intentCount += chatbotIntents.freq
        }

        return [percentage: (escalationCount/intentCount * 100).round(2), numIntent: intentCount, numEscalation: escalationCount]
    }
}
