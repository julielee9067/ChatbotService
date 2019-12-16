/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * All rights reserved.
 */
package com.whylightgroup.security.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import grails.plugin.springsecurity.userdetails.GrailsUser;

public class WlgUser extends GrailsUser {
	
	private static final long serialVersionUID = 1;
	
	private final String authenticationType;
	
	/**
	 * Constructor.
	 *
	 * @param username the username presented to the
	 *        <code>DaoAuthenticationProvider</code>
	 * @param password the password that should be presented to the
	 *        <code>DaoAuthenticationProvider</code>
	 * @param enabled set to <code>true</code> if the user is enabled
	 * @param accountNonExpired set to <code>true</code> if the account has not expired
	 * @param credentialsNonExpired set to <code>true</code> if the credentials have not expired
	 * @param accountNonLocked set to <code>true</code> if the account is not locked
	 * @param authorities the authorities that should be granted to the caller if they
	 *        presented the correct username and password and the user is enabled. Not null.
	 * @param id the id of the domain class instance used to populate this
	 * @param authenticationType type of authentication to use
	 */
	public WlgUser(String username, String password, boolean enabled, boolean accountNonExpired,
		 boolean credentialsNonExpired, boolean accountNonLocked,
		 Collection<GrantedAuthority> authorities, Object id, String authenticationType) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities, id);
		this.authenticationType = authenticationType;
	}

	/**
	 * Get the authenticationType.
	 * @return the authenticationType
	 */
	public String getAuthenticationType() {
		return authenticationType;
	}
}
