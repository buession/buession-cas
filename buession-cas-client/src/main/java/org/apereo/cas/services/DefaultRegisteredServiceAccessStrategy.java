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

import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * This is {@link DefaultRegisteredServiceAccessStrategy}
 * that allows the following rules:
 * <ul>
 * <li>A service may be disallowed to use CAS for authentication</li>
 * <li>A service may be disallowed to take part in CAS single sign-on such that
 * presentation of credentials would always be required.</li>
 * <li>A service may be prohibited from receiving a service ticket
 * if the existing principal attributes don't contain the required attributes
 * that otherwise grant access to the service.</li>
 * </ul>
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class DefaultRegisteredServiceAccessStrategy implements AccessStrategy {

	private final static long serialVersionUID = 584647211488093121L;

	/**
	 * Is the service allowed at all?
	 */
	private boolean enabled = true;

	/**
	 * Is the service allowed to use SSO?
	 */
	private boolean ssoEnabled = true;

	/**
	 * The Unauthorized redirect url.
	 */
	private URI unauthorizedRedirectUrl;

	/**
	 * The delegated authn policy.
	 */
	private DelegatedAuthenticationPolicy delegatedAuthenticationPolicy;

	/**
	 * Defines the attribute aggregation behavior when checking for required attributes.
	 * Default requires that all attributes be present and match the principal's.
	 */
	private boolean requireAllAttributes = true;

	/**
	 * Collection of required attributes for this service to proceed.
	 */
	private Map<String, Set<String>> requiredAttributes;

	/**
	 * Collection of attributes that will be rejected which will cause this policy to refuse access.
	 */
	private Map<String, Set<String>> rejectedAttributes;

	/**
	 * Indicates whether matching on required attribute values
	 * should be done in a case-insensitive manner.
	 */
	private boolean caseInsensitive;

	/**
	 * The sorting/execution order of this strategy.
	 */
	private int order;

	/**
	 * Return is the service allowed at all?
	 *
	 * @return true/false
	 */
	public boolean isEnabled(){
		return enabled;
	}

	/**
	 * Sets is the service allowed at all?
	 *
	 * @param enabled
	 * 		true/false
	 */
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	/**
	 * Return is the service allowed to use SSO?
	 *
	 * @return Is the service allowed to use SSO?
	 */
	public boolean isSsoEnabled(){
		return ssoEnabled;
	}

	/**
	 * Sets is the service allowed to use SSO?
	 *
	 * @param ssoEnabled
	 * 		Is the service allowed to use SSO?
	 */
	public void setSsoEnabled(boolean ssoEnabled){
		this.ssoEnabled = ssoEnabled;
	}

	/**
	 * Return he Unauthorized redirect url.
	 *
	 * @return The Unauthorized redirect url.
	 */
	public URI getUnauthorizedRedirectUrl(){
		return unauthorizedRedirectUrl;
	}

	/**
	 * Sets the Unauthorized redirect url.
	 *
	 * @param unauthorizedRedirectUrl
	 * 		The Unauthorized redirect url.
	 */
	public void setUnauthorizedRedirectUrl(URI unauthorizedRedirectUrl){
		this.unauthorizedRedirectUrl = unauthorizedRedirectUrl;
	}

	/**
	 * Return the delegated authn policy.
	 *
	 * @return The delegated authn policy.
	 */
	public DelegatedAuthenticationPolicy getDelegatedAuthenticationPolicy(){
		return delegatedAuthenticationPolicy;
	}

	/**
	 * Sets he delegated authn policy.
	 *
	 * @param delegatedAuthenticationPolicy
	 * 		The delegated authn policy.
	 */
	public void setDelegatedAuthenticationPolicy(DelegatedAuthenticationPolicy delegatedAuthenticationPolicy){
		this.delegatedAuthenticationPolicy = delegatedAuthenticationPolicy;
	}

	/**
	 * Return defines the attribute aggregation behavior when checking for required attributes.
	 * Default requires that all attributes be present and match the principal's.
	 *
	 * @return Defines the attribute aggregation behavior when checking for required attributes.
	 */
	public boolean isRequireAllAttributes(){
		return requireAllAttributes;
	}

	/**
	 * Defines the attribute aggregation behavior when checking for required attributes.
	 *
	 * @param requireAllAttributes
	 * 		Defines the attribute aggregation behavior when checking for required attributes.
	 */
	public void setRequireAllAttributes(boolean requireAllAttributes){
		this.requireAllAttributes = requireAllAttributes;
	}

	/**
	 * Return collection of required attributes for this service to proceed.
	 *
	 * @return Collection of required attributes for this service to proceed.
	 */
	public Map<String, Set<String>> getRequiredAttributes(){
		return requiredAttributes;
	}

	/**
	 * Sets collection of required attributes for this service to proceed.
	 *
	 * @param requiredAttributes
	 * 		Collection of required attributes for this service to proceed.
	 */
	public void setRequiredAttributes(Map<String, Set<String>> requiredAttributes){
		this.requiredAttributes = requiredAttributes;
	}

	/**
	 * Return collection of attributes that will be rejected which will cause this policy to refuse access.
	 *
	 * @return Collection of attributes that will be rejected which will cause this policy to refuse access.
	 */
	public Map<String, Set<String>> getRejectedAttributes(){
		return rejectedAttributes;
	}

	/**
	 * Sets collection of attributes that will be rejected which will cause this policy to refuse access.
	 *
	 * @param rejectedAttributes
	 * 		Collection of attributes that will be rejected which will cause this policy to refuse access.
	 */
	public void setRejectedAttributes(Map<String, Set<String>> rejectedAttributes){
		this.rejectedAttributes = rejectedAttributes;
	}

	/**
	 * Return indicates whether matching on required attribute values
	 * should be done in a case-insensitive manner.
	 *
	 * @return Indicates whether matching on required attribute values.
	 */
	public boolean isCaseInsensitive(){
		return caseInsensitive;
	}

	/**
	 * Sets ndicates whether matching on required attribute values.
	 *
	 * @param caseInsensitive
	 * 		Indicates whether matching on required attribute values.
	 */
	public void setCaseInsensitive(boolean caseInsensitive){
		this.caseInsensitive = caseInsensitive;
	}

	/**
	 * Return he sorting/execution order of this strategy.
	 *
	 * @return The sorting/execution order of this strategy.
	 */
	public int getOrder(){
		return order;
	}

	/**
	 * Set the sorting/execution order of this strategy.
	 *
	 * @param order
	 * 		The sorting/execution order of this strategy.
	 */
	public void setOrder(int order){
		this.order = order;
	}

	@Override
	public String toString(){
		return StringBuilder.getInstance(this)
				.add("enabled", enabled)
				.add("ssoEnabled", ssoEnabled)
				.add("unauthorizedRedirectUrl", unauthorizedRedirectUrl)
				.add("delegatedAuthenticationPolicy", delegatedAuthenticationPolicy)
				.add("requireAllAttributes", requireAllAttributes)
				.add("requiredAttributes", requiredAttributes)
				.add("rejectedAttributes", rejectedAttributes)
				.add("caseInsensitive", caseInsensitive)
				.add("order", order)
				.asString();
	}

}
