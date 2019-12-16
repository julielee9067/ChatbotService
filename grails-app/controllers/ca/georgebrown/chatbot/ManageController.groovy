package ca.georgebrown.chatbot

import org.springframework.security.access.annotation.Secured


@Secured(['ROLE_APP_MANAGER'])
class ManageController {

    def index() { }
}
