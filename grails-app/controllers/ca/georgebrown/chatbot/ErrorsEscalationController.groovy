package ca.georgebrown.chatbot

import ca.georgebrown.security.Appuser
import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_DEVELOPER'])
class ErrorsEscalationController {

    def springSecurityService
    def errorsEscalationService

    def index() { }

    def resolve() {
        errorsEscalationService.updateProcessingErrors(params)
        redirect(action: "errorsEscalation", params: params)
    }

    def resolveUnrecognized() {
        errorsEscalationService.updateUnrecognizedAsResolved(params)
        redirect(action: "errorsEscalation", params: params)
    }

    def resolveMismatch() {
        errorsEscalationService.updateMismatchErrors(params)
        redirect(action: "errorsEscalation", params: params)
    }

    def errorsEscalation() {
        Appuser appuser = springSecurityService.currentUser
        def chatbotEscalations = ChatbotEscalation.list().sort{ -it.freq }
        def errorsList = ChatbotTranscript.findAllBySpeakerBubbleAndResolved("Error", false)
        def mismatch = ChatbotIntentUtterance.findAllByIncorrect(true)
        def unrecognized = []

        ChatbotIntentUtterance.findAllByIntent("unrecognized").each() { ChatbotIntentUtterance chatbotIntentUtterance ->
            ChatbotTranscript chatbotTranscript = ChatbotTranscript.findByChatMessage(chatbotIntentUtterance.utterance)
            if (chatbotTranscript && !chatbotTranscript.resolved) {
                unrecognized.add(chatbotIntentUtterance)
            }
        }

        def escalationStat = errorsEscalationService.percentageOfEscalation()
        [appuser:appuser, escalationStats: chatbotEscalations, errorsList: errorsList, unrecognized: unrecognized, mismatch: mismatch, escalationStat: escalationStat]
    }
}
