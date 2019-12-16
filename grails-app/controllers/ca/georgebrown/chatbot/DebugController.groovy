package ca.georgebrown.chatbot

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured


@Secured('permitAll')
class DebugController {

    def oasService

    def index() {
        def result
        result = oasService.authUser(params.id, params.token)
        render ([result] as JSON)
    }

    def fixDates() {
        def x = ChatbotEscalation.getAll()

        x.each() { ChatbotEscalation y->
            y.timeSentMessage = new Date().parse('yyyy-MM-dd-HH-mm-ss', y.timeSent)
            y.save(flush:true, failOnError: true)
        }

        render([x] as JSON)
    }
}
