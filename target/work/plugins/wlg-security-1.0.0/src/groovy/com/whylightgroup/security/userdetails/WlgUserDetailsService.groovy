/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * All rights reserved.
 */
package com.whylightgroup.security.userdetails

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GormUserDetailsService;
import grails.plugin.springsecurity.userdetails.NoStackUsernameNotFoundException

class WlgUserDetailsService extends GormUserDetailsService {
	
	/**
	 * {@inheritDoc}
	 * @see com.whylightgroup.security.userdetails.WlgUserDetailsService#loadUserByUsername(
	 * 	java.lang.String, boolean)
	 */
	UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {

		def conf = SpringSecurityUtils.securityConfig
		String userClassName = conf.userLookup.userDomainClassName
		def dc = grailsApplication.getDomainClass(userClassName)
		if (!dc) {
			throw new IllegalArgumentException("The specified user domain class '$userClassName' is not a domain class")
		}

		Class<?> User = dc.clazz

		User.withTransaction { status ->
			def user = null
			if (conf.userLookup.usernameCaseSensitive) {
				user = User.findWhere((conf.userLookup.usernamePropertyName): username)
			} else {
				user = User.withCriteria(uniqueResult: true) {
					ilike(conf.userLookup.usernamePropertyName, username)
				}
			}
			if (!user) {
				log.warn "User not found: $username"
				throw new NoStackUsernameNotFoundException()
			}

			Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
			createUserDetails user, authorities
		}
	}

	protected UserDetails createUserDetails(user, Collection<GrantedAuthority> authorities) {
		
		def conf = SpringSecurityUtils.securityConfig

		String usernamePropertyName = conf.userLookup.usernamePropertyName
		String passwordPropertyName = conf.userLookup.passwordPropertyName
		String enabledPropertyName = conf.userLookup.enabledPropertyName
		String accountExpiredPropertyName = conf.userLookup.accountExpiredPropertyName
		String accountLockedPropertyName = conf.userLookup.accountLockedPropertyName
		String passwordExpiredPropertyName = conf.userLookup.passwordExpiredPropertyName
		String authenticationTypePropertyName = conf.userLookup.authenticationTypePropertyName

		String username = user."$usernamePropertyName"
		String password = user."$passwordPropertyName"
		boolean enabled = enabledPropertyName ? user."$enabledPropertyName" : true
		boolean accountExpired = accountExpiredPropertyName ? user."$accountExpiredPropertyName" : false
		boolean accountLocked = accountLockedPropertyName ? user."$accountLockedPropertyName" : false
		boolean passwordExpired = passwordExpiredPropertyName ? user."$passwordExpiredPropertyName" : false
		String authenticationType = authenticationTypePropertyName ? user."$authenticationTypePropertyName" : 
			'DAO'

		new WlgUser(username, password, enabled, !accountExpired, !passwordExpired,
				!accountLocked, authorities, user.id, authenticationType)
	}
}
