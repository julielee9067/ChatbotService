/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * All rights reserved.
 */

import com.whylightgroup.security.authentication.DaoPasswordValidator
import com.whylightgroup.security.authentication.MultiAuthenticationProvider
import com.whylightgroup.security.userdetails.WlgUserDetailsService

import grails.plugin.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.validation.ConstrainedProperty

import com.whylightgroup.constraint.PasswordConstraint

class WlgSecurityGrailsPlugin {

	def version = "1.0.0"

	def grailsVersion = "2.0 > *"

	//def dependsOn = [
	//		"spring-security-core": "2.0 > *"
	//]

	def loadAfter = ['spring-security-core']

	def title = "WhyLight Group Security Extensions Plugin"
	def description = "WhyLight Group Security Extensions Plugin"
	def author = "Jeffrey Whynot"
	def authorEmail = "jwhynot@whylightgroup.com"
	def documentation = "http://www.whylightgroup.com/framework/plugin/wlg-security"
	def license = [ name: "WhyLight Group Canada Limited", url: "http://www.whylightgroup.com/framework/license" ]
	def organization = [ name: "WhyLight Group Canada Limited", url: "http://www.whylightgroup.com/" ]
	def developers = [
			[ name: "Peter Menhart", email: "peter@menhartconsulting.com" ]
	]

    def doWithSpring = {
		SpringSecurityUtils.providerNames.clear()
		SpringSecurityUtils.registerProvider 'multiAuthenticationProvider'

		userDetailsService(WlgUserDetailsService) {
			grailsApplication = ref('grailsApplication')
		}

		daoPasswordValidator(DaoPasswordValidator) {
			passwordEncoder = ref('passwordEncoder')
		}

		multiAuthenticationProvider(MultiAuthenticationProvider) {
			grailsApplication = ref('grailsApplication')
			userDetailsService = ref('userDetailsService')
			passwordEncoder = ref('passwordEncoder')
			saltSource = ref('saltSource')
			userCache = ref('userCache')
			preAuthenticationChecks = ref('preAuthenticationChecks')
			postAuthenticationChecks = ref('postAuthenticationChecks')
			messageSource = ref('messageSource')
		}

		ConstrainedProperty.registerNewConstraint(PasswordConstraint.CONSTRAINT_NAME, PasswordConstraint.class)
    }

    def doWithApplicationContext = { ctx ->
        SpringSecurityUtils.loadSecondaryConfig("DefaultWlgSecurityConfig")
    }

    def onConfigChange = { event ->
        SpringSecurityUtils.resetSecurityConfig()
		SpringSecurityUtils.reloadSecurityConfig()
		SpringSecurityUtils.loadSecondaryConfig("DefaultWlgSecurityConfig")
    }

}
