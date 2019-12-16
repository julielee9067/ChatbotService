package ca.georgebrown.chatbot

import grails.plugin.springsecurity.annotation.Secured


@Secured('permitAll')
class DenyController {

    def index() {
          render (params.message)
    }
}
