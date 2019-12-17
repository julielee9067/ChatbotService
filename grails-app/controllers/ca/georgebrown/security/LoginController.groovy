package ca.georgebrown.security

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.WebAttributes
import javax.servlet.http.HttpServletResponse


class LoginController {
    
    def authenticateService
    def authenticationTrustResolver
    def springSecurityService
    def securityService
    def grailsApplication

    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
            redirect action: 'auth', params: params
        }
    }

    def auth() {
        def sessionEmail = session.getAttribute('sessionEmail')
        if(!sessionEmail) session.setAttribute('sessionEmail', grailsApplication.config.grails.mail.overrideAddress)
        def config = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
            redirect uri: config.successHandler.defaultTargetUrl
            return
        }

        String view = 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl, rememberMeParameter: config.rememberMe.parameter]
    }

    def authChatbot() {
        render view: 'authChatbot', model: [lexChatToken: params?.lexChatToken ?: 'no token', authChatbot: true, message: false]
    }

    def authChatbotProcess() {
        if(securityService.chatbotAuthenticate(params)) {
            securityService.chatbotLogin(params?.lexChatToken ?: 'no-token', params?.username ?: 'unknown')
            render view: 'authChatbotSuccess', model: [authChatbot: true]
        }
        else {
            render view: 'authChatbot', model: [lexChatToken: params?.lexChatToken ?: 'no token', authChatbot: true, message: 'Unsuccessful authentication']
        }
    }

    def authAjax() {
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

    def denied() {
        if (springSecurityService.isLoggedIn() && authenticationTrustResolver.isRememberMe(SecurityContextHolder.context?.authentication)) {
            redirect action: 'full', params: params
        }
    }

    def full() {
        def config = SpringSecurityUtils.securityConfig
        render view: 'auth', params: params,
                model: [hasCookie: authenticationTrustResolver.isRememberMe(SecurityContextHolder.context?.authentication),
                        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    def authfail() {
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.expired")
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.passwordExpired")
            }
            else if (exception instanceof DisabledException) {
                msg = g.message(code: "springSecurity.errors.login.disabled")
            }
            else if (exception instanceof LockedException) {
                msg = g.message(code: "springSecurity.errors.login.locked")
            }
            else {
                msg = g.message(code: "springSecurity.errors.login.fail")
            }
        }

        if (springSecurityService.isAjax(request)) render([error: msg] as JSON)
        else {
            flash.message = msg
            redirect action: 'auth', params: params
        }
    }

    def ajaxSuccess() {
        render([success: true, username: springSecurityService.authentication.name] as JSON)
    }

    def ajaxDenied() {
        render([error: 'access denied'] as JSON)
    }

    def ajaxCheckUsername() {
        authenticateService.createIfNew(params.username)
        render ([done:'done'] as JSON)
    }
}

