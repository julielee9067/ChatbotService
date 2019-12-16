package ca.georgebrown.security

import grails.plugin.springsecurity.annotation.Secured
import javax.servlet.http.HttpSession


@Secured(['permitAll'])
class LoginSuccessController {

    def springSecurityService

    def index() {
        HttpSession session = request.getSession()
        Appuser user = springSecurityService.currentUser
        def targetUrl = session.getAttribute("targetUrl")

        if (targetUrl) {
            redirect uri: targetUrl
            return
        }

        if(!user) {
            redirect(controller:"login")
            return
        }
        if(Approle.findByAuthority('ROLE_DEVELOPER') in user.authorities){
            redirect(controller: "settings")
            return
        }
        if(Approle.findByAuthority('ROLE_APP_MANAGER') in user.authorities){
            redirect(controller: "settings")
            return
        }
        if(Approle.findByAuthority('ROLE_USER_ADMIN') in user.authorities){
            redirect(controller: "userManagement", action: "index")
            return
        }

        if(Approle.findByAuthority('ROLE_WS_ACCT') in user.authorities){
            redirect(controller: "userManagement", action: "wsAcct")
            return
        }

        redirect(controller: 'logout')
    }


}
