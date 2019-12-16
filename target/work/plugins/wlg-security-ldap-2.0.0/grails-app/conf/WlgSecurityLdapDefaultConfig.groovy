/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * All rights reserved.
 */
ldap {
	security {
		// url = 'ldap://ad.wlg.local:389'
		tls = false // Ensure sever's certificate is installed in jssecacerts if using tls
		// domain = 'wlg'
		search {
			// base = 'dc=wlg,dc=local'
			filter = "samAccountName={0}"
			// timeLimit = 5000 // ms
			// attributes = ['sn', 'givenName', 'mail', 'telephoneNumber']
		}
		// updateProperties = [firstName:'givenName', lastName:'sn', email:'mail', phone:'telephoneNumber']
	}
}