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
 * This is {@link DefaultRegisteredServiceConsentPolicy}.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class DefaultRegisteredServiceConsentPolicy implements ConsentPolicy {

	private final static long serialVersionUID = -3309241770227298790L;

	/**
	 * Indicate whether consent is enabled.
	 */
	private TriStateBoolean status;

	/**
	 * The excluded attributes.
	 */
	private Set<String> excludedAttributes;

	/**
	 * The include-only attributes.
	 */
	private Set<String> includeOnlyAttributes;

	private int order;

	/**
	 * Indicate whether consent is enabled.
	 *
	 * @return true/false/undefined
	 */
	public TriStateBoolean getStatus(){
		return this.status;
	}

	/**
	 * Sets indicate whether consent is enabled.
	 *
	 * @param status
	 * 		true/false/undefined
	 */
	public void setStatus(TriStateBoolean status){
		this.status = status;
	}

	/**
	 * Return excluded attributes. Excludes the set of specified attributes from consent.
	 *
	 * @return The excluded attributes.
	 */
	public Set<String> getExcludedAttributes(){
		return excludedAttributes;
	}

	/**
	 * Sets excluded attributes.
	 *
	 * @param excludedAttributes
	 * 		The excluded attributes.
	 */
	public void setExcludedAttributes(Set<String> excludedAttributes){
		this.excludedAttributes = excludedAttributes;
	}

	/**
	 * Return include-only attributes.
	 * If specified, consent should only be applied to the listed attributes
	 * and not everything the attribute release policy may indicate.
	 *
	 * @return The include-only attributes.
	 */
	public Set<String> getIncludeOnlyAttributes(){
		return includeOnlyAttributes;
	}

	/**
	 * Sets include-only attributes.
	 *
	 * @param includeOnlyAttributes
	 * 		The include-only attributes.
	 */
	public void setIncludeOnlyAttributes(Set<String> includeOnlyAttributes){
		this.includeOnlyAttributes = includeOnlyAttributes;
	}

	public int getOrder(){
		return this.order;
	}

	public void setOrder(int order){
		this.order = order;
	}

	@Override
	public String toString(){
		return StringBuilder.getInstance(this)
				.add("status", status)
				.add("excludedAttributes", excludedAttributes)
				.add("includeOnlyAttributes", includeOnlyAttributes)
				.add("order", order)
				.asString();
	}

}
