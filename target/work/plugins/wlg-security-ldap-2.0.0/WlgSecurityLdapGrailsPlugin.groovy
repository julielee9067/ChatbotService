/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * All rights reserved.
 */
import com.whylightgroup.security.authentication.LdapPasswordValidator

class WlgSecurityLdapGrailsPlugin {

	def version = "2.0.0"

	def grailsVersion = "2.0 > *"

	//def dependsOn = [
	//		"spring-security-core": "2.0 > *"
	//]

	def loadAfter = ['wlg-security']

	def title = "WhyLight Group LDAP Security Extensions Plugin"
	def description = "WhyLight Group LDAP Security Extensions Plugin"
	def author = "Jeffrey Whynot"
	def authorEmail = "jwhynot@whylightgroup.com"
	def documentation = "http://www.whylightgroup.com/framework/plugin/wlg-security-ldap"
	def license = [ name: "WhyLight Group Canada Limited", url: "http://www.whylightgroup.com/framework/license" ]
	def organization = [ name: "WhyLight Group Canada Limited", url: "http://www.whylightgroup.com/" ]
	def developers = [
			[ name: "Peter Menhart", email: "peter@menhartconsulting.com" ]
	]

    def doWithSpring = {
        ldapPasswordValidator(LdapPasswordValidator) {
			grailsApplication = ref('grailsApplication')
		}
    }

}
