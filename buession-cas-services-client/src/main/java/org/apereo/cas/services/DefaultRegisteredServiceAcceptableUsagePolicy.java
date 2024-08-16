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

/**
 * This is {@link DefaultRegisteredServiceAcceptableUsagePolicy}.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class DefaultRegisteredServiceAcceptableUsagePolicy implements AcceptableUsagePolicy {

	private final static long serialVersionUID = -5432687188782541137L;

	/**
	 * Indicate whether policy is enabled.
	 */
	private boolean enabled = true;

	/**
	 * The message code.
	 */
	private String messageCode;

	/**
	 * The policy text verbatim.
	 */
	private String text;

	/**
	 * Indicate whether policy is enabled.
	 *
	 * @return true/false
	 */
	public boolean isEnabled(){
		return enabled;
	}

	/**
	 * Sets indicate whether policy is enabled.
	 *
	 * @param enabled
	 * 		Indicate whether policy is enabled.
	 */
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	/**
	 * Return message code that links the policy terms and body to a language bundle code.
	 *
	 * @return The message code.
	 */
	public String getMessageCode(){
		return messageCode;
	}

	/**
	 * Sets message code that links the policy terms and body to a language bundle code.
	 *
	 * @param messageCode
	 * 		The message code.
	 */
	public void setMessageCode(String messageCode){
		this.messageCode = messageCode;
	}

	/**
	 * Return the policy text verbatim.
	 *
	 * @return The policy text verbatim.
	 */
	public String getText(){
		return text;
	}

	/**
	 * Sets the policy text verbatim.
	 *
	 * @param text
	 * 		The policy text verbatim.
	 */
	public void setText(String text){
		this.text = text;
	}

	@Override
	public String toString(){
		return StringBuilder.getInstance(this)
				.add("enabled", enabled)
				.add("messageCode", messageCode)
				.add("text", text)
				.asString();
	}

}
