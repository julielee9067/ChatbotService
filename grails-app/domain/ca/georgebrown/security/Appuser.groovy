package ca.georgebrown.security

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class Appuser implements Serializable {
	static final AUTHENTICATION_TYPE = [DAO:'DAO', LDAP: 'LDAP']
	static final NO_CHG_PASSWORD = '*************'
	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	String password = "nopass"

	String firstName
	String lastName
	String email
	boolean enabled = true
	boolean accountExpired = false
	boolean accountLocked = false
	boolean passwordExpired = false
	String authenticationType = AUTHENTICATION_TYPE.LDAP
	Appuser(String username, String password) {
		this()
		this.username = username
		this.password = password
	}

	Set<Approle> getAuthorities() {
		AppuserApprole.findAllByAppuser(this)*.approle
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
		firstName nullable: true
		lastName nullable: true
		email nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	def getDisplayName() {
		return "${firstName} ${lastName}"
	}

}
