/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * All rights reserved.
 */
package com.whylightgroup.security.authentication

import org.apache.commons.logging.LogFactory
import org.springframework.security.core.userdetails.UserDetails
import grails.plugin.springsecurity.SpringSecurityUtils

import javax.naming.Context;
import javax.naming.NamingEnumeration
import javax.naming.directory.Attribute
import javax.naming.directory.Attributes
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult
import javax.naming.ldap.InitialLdapContext
import javax.naming.ldap.LdapContext
import javax.naming.ldap.StartTlsRequest
import javax.naming.ldap.StartTlsResponse;

class LdapPasswordValidator implements PasswordValidator {

	private static log = LogFactory.getLog(this)

	def grailsApplication

	public boolean isPasswordValid(UserDetails userDetails, String password, Object salt) {
		String url = grailsApplication.mergedConfig.ldap.security.url
		boolean tls = grailsApplication.mergedConfig.ldap.security.tls
		String domain = grailsApplication.mergedConfig.ldap.security.domain
		String username = userDetails.getUsername()
		String domainUsername = domain ? "${domain}\\${username}" : username
		String searchBase = grailsApplication.mergedConfig.ldap.security.search.base
		String searchFilter = grailsApplication.mergedConfig.ldap.security.search.filter.replace('{0}',username)
		int searchTimeLimit = grailsApplication.mergedConfig.ldap.security.search.timeLimit ?: 0
		String[] searchAttributes = grailsApplication.mergedConfig.ldap.security.search.attributes

		// Set up the environment for creating the initial context
		Hashtable<String, String> env = new Hashtable<String, String>()
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
		env.put(Context.PROVIDER_URL, url)

		LdapContext ctx = null;
		try {
			// Create the initial context
			ctx = new InitialLdapContext(env)

			// Start TLS if required
			if (tls) {
				((StartTlsResponse)ctx.extendedOperation(new StartTlsRequest())).negotiate()
			}

			// Add security
			ctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, "simple")
			ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, domainUsername)
			ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password)

			// Test authentication by executing command that requires security
			ctx.getAttributes("", ["dnsHostName"] as String[])

			// Find user object and update database user object
			try {
				SearchControls constraints = new SearchControls(SearchControls.SUBTREE_SCOPE,
						1L, searchTimeLimit, searchAttributes, false, false);
				NamingEnumeration<?> searchResults = ctx.search(searchBase, searchFilter, constraints);

				if (searchResults != null && searchResults.hasMore()) {
					SearchResult searchResult = (SearchResult) searchResults.next();
					Attributes attributes = searchResult.getAttributes();
					if (attributes != null) {
						Map<String, String> ldapAttributes = new HashMap<String, String>()
						for (NamingEnumeration enums = attributes.getAll(); enums.hasMore();) {
							Attribute attribute = (Attribute) enums.next();
							ldapAttributes.put(attribute.getID(), attribute.get());
						}
						updateUser(userDetails, ldapAttributes)
					}
				}
			} catch (Exception e) {
				// Ignore error and simply do not update database
			}

			return true
		} catch (Exception e) {
			log.error e
			return false
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (Exception e) {
					// Ignore Error
				}
			}
		}

	}

	private void updateUser(UserDetails userDetails, def ldapAttributes) {
		def updateProperties = grailsApplication.mergedConfig.ldap.security.asMap().updateProperties
		if (updateProperties) {
			def conf = SpringSecurityUtils.securityConfig

			String userClassName = conf.userLookup.userDomainClassName
			def dc = grailsApplication.getDomainClass(userClassName)
			if (!dc) {
				throw new IllegalArgumentException("The specified user domain class '$userClassName' is not a domain class")
			}
			Class<?> User = dc.clazz

			User.withTransaction {
				def user = User.get(userDetails.getId())
				updateProperties.each { userProp, ldapProp ->
					def value = ldapAttributes."${ldapProp}"
					if (value) {
						user."${userProp}" = value
					}
				}
				if (user.metaClass.respondsTo(user, "generateKeywords")) {
					user.generateKeywords()
				}
				user.save()
			}
		}
	}

}
