package ca.georgebrown.chatbot

import ca.georgebrown.security.Approle
import ca.georgebrown.security.Appuser
import ca.georgebrown.security.AppuserApprole
import grails.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Transactional
class SecurityService {

    def springSecurityService
    def settingService
    def oasService

    def login(String username, String password) {
        Appuser user = Appuser.findByUsername(username)
        if (!user) return false

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder()
        if (passwordEncoder.matches(password, user.getPassword())) return true
        return false
    }

    def authenticated(def params) {
        if (!settingService.get(Setting.CODE.AUTHENTICATION_REQUIRED)) return true
        if (params?.action && params.action in oasService.getPublicFunctions()) return true

        if (springSecurityService.currentUser) {
            if (AppuserApprole.findAllByAppuserAndApprole(springSecurityService.currentUser as Appuser, Approle.findByAuthority('ROLE_WS_ACCT'))) return true
        }

        if (login(params.username, params.password)) {
            if (AppuserApprole.findAllByAppuserAndApprole(Appuser.findByUsername(params.username), Approle.findByAuthority('ROLE_WS_ACCT'))) return true
        }
        return false
    }
}
