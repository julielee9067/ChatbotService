package ca.georgebrown.chatbot

import ca.georgebrown.security.Appuser
import java.text.SimpleDateFormat
import grails.plugin.springsecurity.annotation.Secured


@Secured(['ROLE_APP_MANAGER'])
class IntentsController {

    def springSecurityService
    def intentsService
    def errorsEscalationService

    def intents() {
        Appuser appuser = springSecurityService.currentUser
        if (!params.Intent && params.Utterance) {
            if (ChatbotIntentUtterance.findByUtterance(params.Utterance)) {
                params.Intent = ChatbotIntentUtterance.findByUtterance(params.Utterance).intent
            }
        }

        if (!params.selection) params.selection = 'main'

        def intents = ChatbotIntents.list()
        return [appuser:appuser, params:params, intents: intents]
    }

    def updateIntents() {
        intentsService.updateUtteranceCorrect(params)
        redirect(controller: "intents", action: "intents")
    }

    def updateIntentData() {
        intentsService.updateIntentData(params)
        redirect(action: "intents", params: params)
    }

    def escalationData() {
        def chatbotEscalations = ChatbotEscalation.list().sort{-it.freq}
        render template: 'escalation',
                model: [
                        chatbotEscalations: chatbotEscalations
                ]
    }

    def manageCategory() {
        def chatbotIntentTypes = ChatbotIntentTypes.list()
        render template: 'manageCategory',
                model: [
                        chatbotIntentTypes: chatbotIntentTypes
                ]
    }

    def browse() {
        def utterances = null
        if(params.selection == 'popular' || params.selection == 'date') {
            utterances = intentsService.getUtterancesByCriteria(params.selection)
        }

        render template: 'browse', model: [utterances : utterances]
    }

    def intentData() {
        def chatbotIntents = ChatbotIntents.findAllByIntentNotInList(errorsEscalationService.getEscalationReasons()).sort{ -it.freq }
        def chatbotIntentTypes = ChatbotIntentTypes.findAllByCategoryNotEqual("escalation")
        render template: 'intentData', model: [chatbotIntents: chatbotIntents, chatbotIntentTypes: chatbotIntentTypes]
    }

    def viewConversations() {
        def utterances = ChatbotIntentUtterance.findAllByIntent(params?.Intent).groupBy { it.utterance }
        render template: 'conversations', model: [utterances : utterances]
    }

    def viewMainStats() {
        def popular = intentsService.getTopFivePopular()
        def catRankings = intentsService.getCategoryRankings()
        def totalConversations = ChatbotTranscript.list().groupBy{ it.chatToken }.size()
        def chatbotEscalations = ChatbotEscalation.list().sort{ -it.freq }
        def yesterday = new SimpleDateFormat("MMM-dd-yyyy").format(new Date() - 1)
        render template: 'mainStats',
                model: [
                        popular: popular,
                        catRankings: catRankings,
                        totalConversations: totalConversations,
                        escalationRankings: chatbotEscalations,
                        yesterday: yesterday
                ]
    }

    def updateIntentDescription() {
        intentsService.updateDescription(params)
        redirect(action: "intents", params: params)
    }

    def updateIntentCategory() {
        if (params.categoryAction == 'deleteCategory') intentsService.deleteCategory(params.category)
        else if (params.addCategory) {
            if (params.newCategory) intentsService.addNewCategory(params.newCategory)
        }
        else if (params.submit == "UPDATE") {
            def chatbotIntentTypes = ChatbotIntentTypes.list()
            def description

            chatbotIntentTypes.each() { def chatbotIntentType ->
                if (params."${chatbotIntentType.category}.description" != "") {
                    description = params."${chatbotIntentType.category}.description"
                    intentsService.updateCategoryDescription(description, chatbotIntentType.category)
                }
            }
        }

        params.selection = "manageCategory"
        redirect(action: "intents", params: params)
    }

    def updateEscalation() {
        if (params.escalationAction == 'deleteEscalation') errorsEscalationService.deleteEscalation(params.escalation)

        else if (params.addEscalation) {
            if (params.newEscalation) errorsEscalationService.addNewEscalation(params.newEscalation)
        }
        else if (params.submit == "UPDATE") {
            def reasonList = errorsEscalationService.getEscalationReasons()
            def description
            reasonList.each() { def reason ->
                if (params."${reason}.description" != "") {
                    description = params."${reason}.description"
                    errorsEscalationService.updateEscalationDescription(description, reason)
                }
            }
        }

        params.selection = "escalation"
        redirect(action: "intents", params: params)
    }

    def viewIntentInformation() {
        def utterances = ChatbotIntentUtterance.findAllByIntent(params?.Intent).groupBy { it.utterance }
        def description = ChatbotIntents.findByIntent(params?.Intent)?.description
        render template: 'intentSelect',
                model: [utterances : utterances, description: description]
    }
}
