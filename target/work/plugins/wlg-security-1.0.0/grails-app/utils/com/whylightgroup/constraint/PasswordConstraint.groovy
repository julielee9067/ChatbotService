/* Copyright 2014 WhyLight Group Canada Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.whylightgroup.constraint

import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

class PasswordConstraint extends AbstractConstraint {

	public static final String CONSTRAINT_NAME = "password"
	private static log = LogFactory.getLog(this)

	@Override
	public boolean supports(Class type) {
		return type != null && String.class.isAssignableFrom(type)
	}

	@Override
	public String getName() {
		return CONSTRAINT_NAME;
	}

	@Override
	protected void processValidate(Object target, Object propertyValue, Errors errors) {
		String value = propertyValue.toString();
		def minSize = constraintParameter.minSize;
		def authType = constraintParameter.authType;
		if (minSize && value?.length() < minSize && target.authenticationType == authType) {
			def args = (Object[]) [constraintPropertyName, constraintOwningClass, minSize]
			super.rejectValue(target, errors, "invalid.password.minSize", "default.invalid.password.minSize", args)
		}
	}
}
