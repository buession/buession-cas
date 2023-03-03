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
 * This is {@link DefaultRegisteredServiceMultifactorPolicy}.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class DefaultRegisteredServiceMultifactorPolicy implements MultifactorPolicy {

	private final static long serialVersionUID = 5717967927777524740L;

	/**
	 * The authentication provider id.
	 */
	private Set<String> multifactorAuthenticationProviders;

	/**
	 * The failure mode.
	 */
	private MultifactorPolicyFailureModes failureMode;

	/**
	 * The principal attribute name trigger.
	 */
	private String principalAttributeNameTrigger;

	/**
	 * The principal attribute value to match.
	 */
	private String principalAttributeValueToMatch;

	/**
	 * Indicates whether authentication should be skipped.
	 */
	private boolean bypassEnabled;

	/**
	 * Whether multifactor authentication should forcefully trigger
	 */
	private boolean forceExecution;

	/**
	 * Whether multifactor authentication should bypass trusted device registration,
	 * and check for device records and/or skip prompt for registration.
	 */
	private boolean bypassTrustedDeviceEnabled;

	/**
	 * The principal attribute name trigger.
	 */
	private String bypassPrincipalAttributeName;

	/**
	 * The principal attribute value to match.
	 */
	private String bypassPrincipalAttributeValue;

	/**
	 * MFA trigger as a script path or embedded script.
	 */
	private String script;

	/**
	 * Return MFA authentication provider id.
	 *
	 * @return The authentication provider id.
	 */
	public Set<String> getMultifactorAuthenticationProviders(){
		return multifactorAuthenticationProviders;
	}

	/**
	 * Sets MFA authentication provider id.
	 *
	 * @param multifactorAuthenticationProviders
	 * 		The authentication provider id.
	 */
	public void setMultifactorAuthenticationProviders(Set<String> multifactorAuthenticationProviders){
		this.multifactorAuthenticationProviders = multifactorAuthenticationProviders;
	}

	/**
	 * Return failure mode.
	 *
	 * @return The failure mode.
	 */
	public MultifactorPolicyFailureModes getFailureMode(){
		return failureMode;
	}

	/**
	 * Sets failure mode.
	 *
	 * @param failureMode
	 * 		The failure mode.
	 */
	public void setFailureMode(MultifactorPolicyFailureModes failureMode){
		this.failureMode = failureMode;
	}

	/**
	 * Return principal attribute name trigger.
	 *
	 * @return The principal attribute name trigger.
	 */
	public String getPrincipalAttributeNameTrigger(){
		return principalAttributeNameTrigger;
	}

	/**
	 * Sets principal attribute name trigger.
	 *
	 * @param principalAttributeNameTrigger
	 * 		The principal attribute name trigger.
	 */
	public void setPrincipalAttributeNameTrigger(String principalAttributeNameTrigger){
		this.principalAttributeNameTrigger = principalAttributeNameTrigger;
	}

	/**
	 * Return principal attribute value to match. Values may be regex patterns.
	 *
	 * @return The principal attribute value to match.
	 */
	public String getPrincipalAttributeValueToMatch(){
		return principalAttributeValueToMatch;
	}

	/**
	 * Sets principal attribute value to match.
	 *
	 * @param principalAttributeValueToMatch
	 * 		The principal attribute value to match.
	 */
	public void setPrincipalAttributeValueToMatch(String principalAttributeValueToMatch){
		this.principalAttributeValueToMatch = principalAttributeValueToMatch;
	}

	/**
	 * Indicates whether authentication should be skipped.
	 *
	 * @return true/false
	 */
	public boolean isBypassEnabled(){
		return bypassEnabled;
	}

	/**
	 * Sets indicates whether authentication should be skipped.
	 *
	 * @param bypassEnabled
	 * 		Indicates whether authentication should be skipped.
	 */
	public void setBypassEnabled(boolean bypassEnabled){
		this.bypassEnabled = bypassEnabled;
	}

	/**
	 * Whether multifactor authentication should forcefully trigger,
	 * even if the existing authentication context can be satisfied without MFA.
	 *
	 * @return true/false
	 */
	public boolean isForceExecution(){
		return forceExecution;
	}

	/**
	 * Sets multifactor authentication should forcefully trigger,
	 * even if the existing authentication context can be satisfied without MFA.
	 *
	 * @param forceExecution
	 * 		Whether multifactor authentication should forcefully trigger
	 */
	public void setForceExecution(boolean forceExecution){
		this.forceExecution = forceExecution;
	}

	/**
	 * Whether multifactor authentication should bypass trusted device registration,
	 * and check for device records and/or skip prompt for registration.
	 *
	 * @return true/false
	 */
	public boolean isBypassTrustedDeviceEnabled(){
		return bypassTrustedDeviceEnabled;
	}

	/**
	 * Sets multifactor authentication should bypass trusted device registration,
	 * and check for device records and/or skip prompt for registration.
	 *
	 * @param bypassTrustedDeviceEnabled
	 * 		Whether multifactor authentication should bypass trusted device registration,
	 * 		and check for device records and/or skip prompt for registration.
	 */
	public void setBypassTrustedDeviceEnabled(boolean bypassTrustedDeviceEnabled){
		this.bypassTrustedDeviceEnabled = bypassTrustedDeviceEnabled;
	}

	/**
	 * Return principal attribute name trigger to enable bypass.
	 *
	 * @return The principal attribute name trigger.
	 */
	public String getBypassPrincipalAttributeName(){
		return bypassPrincipalAttributeName;
	}

	/**
	 * Sets principal attribute name trigger to enable bypass.
	 *
	 * @param bypassPrincipalAttributeName
	 * 		The principal attribute name trigger.
	 */
	public void setBypassPrincipalAttributeName(String bypassPrincipalAttributeName){
		this.bypassPrincipalAttributeName = bypassPrincipalAttributeName;
	}

	/**
	 * Return principal attribute value to match to enable bypass. Values may be regex patterns.
	 *
	 * @return The principal attribute value to match.
	 */
	public String getBypassPrincipalAttributeValue(){
		return bypassPrincipalAttributeValue;
	}

	/**
	 * Sets principal attribute value to match to enable bypass.
	 *
	 * @param bypassPrincipalAttributeValue
	 * 		The principal attribute value to match.
	 */
	public void setBypassPrincipalAttributeValue(String bypassPrincipalAttributeValue){
		this.bypassPrincipalAttributeValue = bypassPrincipalAttributeValue;
	}

	/**
	 * Return path to an external/embedded script that allows for triggering of MFA.
	 *
	 * @return MFA trigger as a script path or embedded script.
	 */
	public String getScript(){
		return script;
	}

	/**
	 * Sets path to an external/embedded script that allows for triggering of MFA.
	 *
	 * @param script
	 * 		MFA trigger as a script path or embedded script.
	 */
	public void setScript(String script){
		this.script = script;
	}

	@Override
	public String toString(){
		return StringBuilder.getInstance(this)
				.add("multifactorAuthenticationProviders", multifactorAuthenticationProviders)
				.add("failureMode", failureMode)
				.add("principalAttributeNameTrigger", principalAttributeNameTrigger)
				.add("principalAttributeValueToMatch", principalAttributeValueToMatch)
				.add("bypassEnabled", bypassEnabled)
				.add("forceExecution", forceExecution)
				.add("bypassTrustedDeviceEnabled", bypassTrustedDeviceEnabled)
				.add("bypassPrincipalAttributeName", bypassPrincipalAttributeName)
				.add("bypassPrincipalAttributeValue", bypassPrincipalAttributeValue)
				.add("script", script)
				.asString();
	}

}
