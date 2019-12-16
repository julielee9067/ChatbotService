/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * All rights reserved.
 */
package com.whylightgroup.security.authentication

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails

import grails.plugin.springsecurity.SpringSecurityUtils

import grails.util.GrailsNameUtils

/**
 * DaoAuthenticationProvider that provides alternate password validation options.
 * 
 * @author jwhynot
 *
 */
class MultiAuthenticationProvider extends DaoAuthenticationProvider {
	
	def grailsApplication

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
		UsernamePasswordAuthenticationToken authentication) {
		
		def conf = SpringSecurityUtils.securityConfig
		
		Object salt = null

		if (this.saltSource != null) {
			salt = this.saltSource.getSalt(userDetails)
		}
		
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided")
			throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails)
		}
		
		String presentedPassword = authentication.getCredentials().toString()
        String authenticationType = userDetails.getAuthenticationType()
		
		def passwordValidator = grailsApplication.getMainContext().getBean(createBeanName(authenticationType)) 
        
        if (!passwordValidator.isPasswordValid(userDetails, presentedPassword, salt)) {
			logger.debug("Authentication failed: password does not match stored value")
			throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails)
		}
			  
	}
		
	private String createBeanName(String type) {
		String typeName = GrailsNameUtils.getPropertyName(type.toLowerCase().split(/\\W/).collect{ it.capitalize() }.join())
		"${typeName}PasswordValidator"
	}

}
