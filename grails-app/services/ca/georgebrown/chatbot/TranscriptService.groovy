package ca.georgebrown.chatbot

import grails.transaction.Transactional
import groovy.json.JsonSlurper


@Transactional
class TranscriptService {

    def errorsEscalationService
    def intentsService

    // This function is called by AWS Lambda "uploadTranscript" function. It updates chatbot_transcript table in the database.
    def updateTranscript(def params) {
        def data = params.data
        def addedRecords = 0
        def jsonSlurper = new JsonSlurper()
        def success = true

        try {
            def object = jsonSlurper.parseText(data)
            def token = object.UserIdPrimaryKey
            def orderMessage = (object.orderString).toInteger()
            def message = object.ChatMessage
            def timeSentMessage = new Date().parse('yyyy-MM-dd-HH-mm-ss', object.TimeSent)
            def speaker = object.Dialog
            def userId

            if (!object.GBCuserId || !object.GBCuserId?.isInteger()) userId = -1
            else userId = (object.GBCuserId).toInteger()

            def result = ChatbotTranscript.findByChatTokenAndChatMessageAndTimeSentMessage(token, message, timeSentMessage) ?: null

            if (!result) {
                def record = new ChatbotTranscript(userId: userId, chatToken: token, orderBubble: orderMessage, speakerBubble: speaker, chatMessage: message, resolved: false, timeSentMessage: timeSentMessage)
                record.save(flush: true, failOnError: true)
                addedRecords++

                // This "Sorry, I don't understand." clause needs to be modified when the wording changes on AWS Lex Console-unrecognized intent response.
                if(record.chatMessage.contains("Sorry, I don't understand.")) {
                    updateUnrecognizedUtterances(ChatbotTranscript.findByChatTokenAndOrderBubble(record.chatToken, record.orderBubble-1))
                }
            }
        } catch (Exception e) {
            print(e)
            success = false
        }

        return [success: success, addedRecords: addedRecords]
    }

    // This function sets the intent for the unrecognized utterances.
    def updateUnrecognizedUtterances(ChatbotTranscript chatbotTranscript) {
        ChatbotIntentUtterance chatbotIntentUtterance = new ChatbotIntentUtterance(intent: 'unrecognized',
                utterance: chatbotTranscript.chatMessage, chatToken: chatbotTranscript.chatToken, timeSentMessage: chatbotTranscript.timeSentMessage)
        chatbotIntentUtterance.save(flush: true, failOnError: true)
        updateIntentTable(chatbotIntentUtterance)
    }

    // This function is called by AWS Lambda "uploadTranscript" function. It updates chatbot_intent_utterance and chatbot_intents table in the database.
    def updateIntentUtterances(def params) {
        def data = params.data
        def other = data.replaceAll("\\\\", "")
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(other)
        def addedRecords = 0
        def success = true

        try {
            def token = object.ChatToken
            def intent = object.Intent
            def timeSentMessage = new Date().parse('yyyy-MM-dd-HH-mm-ss', object.TimeSent)
            def result = ChatbotIntentUtterance.findByChatTokenAndTimeSentMessageAndIntent(token, timeSentMessage, intent)
            def utterance = object.Utterance

            if (!result) {
                ChatbotIntentUtterance chatbotIntentUtterance = new ChatbotIntentUtterance(intent: intent, utterance: utterance, chatToken: token, correctIntent: intent, timeSentMessage: timeSentMessage)
                chatbotIntentUtterance.save(flush: true, failOnError: true)
                addedRecords++
                updateIntentTable(chatbotIntentUtterance)
            }
        }
        catch (Exception e) {
            print(e)
            success = false
        }

        return [success: success, addedRecords: addedRecords]
    }

    // This function update chatbot_intents table.
    def updateIntentTable(ChatbotIntentUtterance chatbotIntentUtterance) {
        ChatbotIntents chatbotIntents = ChatbotIntents.findByIntent(chatbotIntentUtterance.intent)
        if (!chatbotIntents) {
            ChatbotIntents newIntent = new ChatbotIntents(intent: chatbotIntentUtterance.intent, correctIntent: chatbotIntentUtterance.intent, freq: 1)
            newIntent.save(flush: true, failOnError: true)
        }
        else {
            chatbotIntents.freq += 1
            chatbotIntents.save(flush: true, failOnError: true)
            if (chatbotIntentUtterance.intent in errorsEscalationService.getEscalationReasons()) {
                updateEscalationTable(chatbotIntentUtterance)
            }
        }
    }

    // This function updates chatbot_escalation table.
    def updateEscalationTable(def chatbotIntentUtterance) {
        ChatbotEscalation chatbotEscalation = ChatbotEscalation.findByReason(chatbotIntentUtterance.intent)
        if(chatbotEscalation.lastTimeSent < chatbotIntentUtterance.timeSentMessage || !chatbotEscalation.lastTimeSent) {
            chatbotEscalation.lastTimeSent = chatbotIntentUtterance.timeSentMessage
        }
        chatbotEscalation.freq += 1
        chatbotEscalation.save(flush: true, failOnError: true)
    }

    // This function gets the transcript from the chat token
    def getTranscripts(def params) {
        def token = params.token
        def c = ChatbotTranscript.createCriteria()
        def transcriptList = c.list {
            eq("chatToken", token)
            order("timeSentMessage", "asc")
            order("orderBubble", "asc")
        }
        return transcriptList
    }

    // This function gets the conversation. If the user searches user ID, it returns conversation from the specific user.
    def getConversations(def params) {
        def offset = params.offset
        if ((offset.toString()).isInteger()) {
            def escalationReasons = errorsEscalationService.getEscalationReasons()
            Map chatbotTranscripts
            def chatbotTranscriptsUnsorted

            if(params."search-userId" == "Go" && params.userId != "") {
                chatbotTranscriptsUnsorted = ChatbotTranscript.findAllByUserId(params.userId).groupBy { it.chatToken }
            }
            else {
                chatbotTranscriptsUnsorted = ChatbotTranscript.list().groupBy { it.chatToken }
            }

            Comparator comparator = [compare: {a , b ->
                chatbotTranscriptsUnsorted.get(b)[0].timeSentMessage.compareTo(chatbotTranscriptsUnsorted.get(a)[0].timeSentMessage)
            }] as Comparator

            chatbotTranscripts = new TreeMap(comparator)
            chatbotTranscripts.putAll(chatbotTranscriptsUnsorted)

            chatbotTranscripts.each { def chatbotTranscript ->
                String chatToken = chatbotTranscript.key
                def escalateTotal = ChatbotIntentUtterance.findAllByChatTokenAndIntentInList(chatToken, escalationReasons)
                def escalate = []
                escalateTotal.each() { ChatbotIntentUtterance chatbotIntentUtterance1 ->
                    if(ChatbotTranscript.findByChatMessageAndChatTokenAndResolved(chatbotIntentUtterance1.utterance, chatToken, false)) {
                        escalate.add(chatbotIntentUtterance1)
                    }
                }
                def error = ChatbotTranscript.findAllByChatTokenAndSpeakerBubbleAndResolved(chatToken as String, 'Error', false)
                def mismatch = intentsService.getIntentMismatchList(chatToken)

                if(escalate) chatbotTranscript.value.add("escalate")
                if(error) chatbotTranscript.value.add("error")
                if(mismatch) chatbotTranscript.value.add("mismatch")
            }
            return chatbotTranscripts
        }
        return null
    }

    def deleteDuplicateTranscript() {
        def transcriptList = ChatbotTranscript.list()
        transcriptList.each() { ChatbotTranscript chatbotTranscript ->
            def duplicates = ChatbotTranscript.findAllByChatTokenAndChatMessageAndTimeSentMessage(chatbotTranscript.chatToken, chatbotTranscript.chatMessage, chatbotTranscript.timeSentMessage)
            if(duplicates.size() > 1) {
                def count = 0
                for (item in duplicates) {
                    if(count != 0) item.delete(flush: true)
                    count++
                }
            }
        }
    }
}
