package ca.georgebrown.security

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured


@Secured('permitAll')
class AuthenticationFailController {
    def index() {
        render([success:false,error:'Authentication failure.'] as JSON)
    }
}

