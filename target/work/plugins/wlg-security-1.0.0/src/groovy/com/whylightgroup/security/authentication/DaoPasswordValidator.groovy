/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * All rights reserved.
 */
package com.whylightgroup.security.authentication

import org.springframework.security.core.userdetails.UserDetails;

class DaoPasswordValidator implements PasswordValidator {

	def passwordEncoder
	
	@Override
	public boolean isPasswordValid(UserDetails userDetails, String password, Object salt) {
		passwordEncoder.isPasswordValid(userDetails.getPassword(), password, salt)
	}

}
