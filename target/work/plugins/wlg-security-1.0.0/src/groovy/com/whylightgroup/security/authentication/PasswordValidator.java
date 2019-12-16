/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * All rights reserved.
 */
package com.whylightgroup.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public interface PasswordValidator {

    public boolean isPasswordValid(UserDetails userDetails, String password, Object salt);
    
}
