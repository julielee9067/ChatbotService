package ca.georgebrown.chatbot

import ca.georgebrown.security.Appuser
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_APP_MANAGER'])
class TranscriptController {

    def springSecurityService
    def transcriptService
    def intentsService
    def errorsEscalationService

    // This function is called by AWS Lambda "uploadTranscript" function.
    def updateTranscript() {
        render (transcriptService.updateTranscript(params) as JSON)
    }

    def updateIntentTable() {
        transcriptService.updateIntentTable()
        render(null as JSON)
    }

    def updateIntentUtterances() {
        render (transcriptService.updateIntentUtterances(params) as JSON)
    }

    def transcript() {
        Appuser appuser = springSecurityService.currentUser
        def latestToken
        params.max = params?.max?.toInteger() ?: 10
        params.offset = params?.offset?.toInteger() ?: 0

        if (!params.token && !params.tokens) {
            params.tokens = latestToken
            params.token = latestToken
        }
        else if (!params.tokens) {
            params.tokens = params.token
        }
        else {
            params.token = params.tokens
        }

        if(!params.userId) {
            params.userId = ChatbotTranscript.findByChatToken(params.token)?.userId ?: null
        }

        def transcriptList = transcriptService.getTranscripts(params)
        def unrecognizedList = ChatbotIntentUtterance.findAllByIntentAndChatToken("unrecognized", params?.token)
        def unrecognizedUtterances = []
        unrecognizedList.each() { ChatbotIntentUtterance chatbotIntentUtterance ->
            if(ChatbotTranscript.findByChatTokenAndChatMessageAndResolved(chatbotIntentUtterance.chatToken, chatbotIntentUtterance.utterance, false)) {
                unrecognizedUtterances.add(chatbotIntentUtterance.utterance)
            }
        }

        def escalation = errorsEscalationService.getEscalationReasons()
        def escalationList = ChatbotIntentUtterance.findAllByIntentInListAndChatToken(escalation, params?.token)
        def escalationUtterances = []
        escalationList.each() { ChatbotIntentUtterance chatbotIntentUtterance ->
            if(ChatbotTranscript.findByChatTokenAndChatMessageAndResolved(chatbotIntentUtterance.chatToken, chatbotIntentUtterance.utterance, false)) {
                escalationUtterances.add(chatbotIntentUtterance.utterance)
            }
        }

        def insultList = ChatbotIntentUtterance.findAllByIntentAndChatToken("insult", params?.token)
        def insultUtterances = []
        insultList.each() { ChatbotIntentUtterance chatbotIntentUtterance ->
            if(ChatbotTranscript.findByChatTokenAndChatMessageAndResolved(chatbotIntentUtterance.chatToken, chatbotIntentUtterance.utterance, false)) {
                insultUtterances.add(chatbotIntentUtterance.utterance)
            }
        }

        def dateTranscript = ChatbotTranscript.findByChatToken(params.token)?.timeSentMessage
        def conversations = transcriptService.getConversations(params)
        def checkUserId = ChatbotTranscript.findByUserId(params.userId) ? true : false
        def checkChatToken = ChatbotTranscript.findByChatToken(params.token) ? true : false
        def mismatch = intentsService.getIntentMismatchList(params.token)

        [transcriptList: transcriptList, appuser:appuser, dateTranscript: dateTranscript,
         conversations:conversations, checkUserId: checkUserId, checkChatToken: checkChatToken, insultList: insultUtterances,
         mismatch: mismatch, unrecognized: unrecognizedUtterances, escalated: escalationUtterances]
    }

    def resolveEscalationFromTranscript(def params) {
        errorsEscalationService.updateEscalationAsResolved(params)
        redirect(controller: 'transcript', action: 'transcript', params: params)
    }

    def browseTranscripts() {
        params.max = params?.max?.toInteger() ?: 10
        params.offset = params?.offset?.toInteger() ?: 0

        def conversations = transcriptService.getConversations(params)
        def count = conversations.size()
        render template: 'browseTranscripts',
                model: [
                        conversations : conversations ?: null,
                        total: count,
                        max: params.max,
                        offset: params.offset,
                        div_id: 'browse'
                ]
    }
}
