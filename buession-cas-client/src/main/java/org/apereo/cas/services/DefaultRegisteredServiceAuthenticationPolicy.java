/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 * =========================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +-------------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										       |
 * | Author: Yong.Teng <webmaster@buession.com> 													       |
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.services;

import java.util.Set;

/**
 * This is {@link DefaultRegisteredServiceAuthenticationPolicy}.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class DefaultRegisteredServiceAuthenticationPolicy implements AuthenticationPolicy {

	private final static long serialVersionUID = -4299141576921130940L;

	/**
	 * The required authentication handlers.
	 */
	private Set<String> requiredAuthenticationHandlers;

	/**
	 * The excluded authentication handlers.
	 */
	private Set<String> excludedAuthenticationHandlers;

	/**
	 * The required authentication policies.
	 */
	private AuthenticationPolicyCriteria criteria;

	/**
	 * Return required authentication handlers by their name/id.
	 *
	 * @return The required authentication handlers.
	 */
	public Set<String> getRequiredAuthenticationHandlers(){
		return requiredAuthenticationHandlers;
	}

	/**
	 * Sets required authentication handlers by their name/id.
	 *
	 * @param requiredAuthenticationHandlers
	 * 		The required authentication handlers.
	 */
	public void setRequiredAuthenticationHandlers(Set<String> requiredAuthenticationHandlers){
		this.requiredAuthenticationHandlers = requiredAuthenticationHandlers;
	}

	/**
	 * Return excluded authentication handlers by their name/id.
	 *
	 * @return The excluded authentication handlers.
	 */
	public Set<String> getExcludedAuthenticationHandlers(){
		return excludedAuthenticationHandlers;
	}

	/**
	 * Sets excluded authentication handlers by their name/id.
	 *
	 * @param excludedAuthenticationHandlers
	 * 		The excluded authentication handlers.
	 */
	public void setExcludedAuthenticationHandlers(Set<String> excludedAuthenticationHandlers){
		this.excludedAuthenticationHandlers = excludedAuthenticationHandlers;
	}

	/**
	 * Return required authentication policy criteria.
	 *
	 * @return The required authentication policies.
	 */
	public AuthenticationPolicyCriteria getCriteria(){
		return criteria;
	}

	/**
	 * Sets required authentication policy criteria.
	 *
	 * @param criteria
	 * 		The required authentication policies.
	 */
	public void setCriteria(AuthenticationPolicyCriteria criteria){
		this.criteria = criteria;
	}

	@Override
	public String toString(){
		return StringBuilder.getInstance(this)
				.add("requiredAuthenticationHandlers", requiredAuthenticationHandlers)
				.add("excludedAuthenticationHandlers", excludedAuthenticationHandlers)
				.add("criteria", criteria)
				.asString();
	}

}
