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
package org.apereo.cas.authentication.principal;

import java.util.StringJoiner;

/**
 * Wrapper around an attribute repository where attributes cached for a configurable period
 * based on google guava's caching library.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class CachingPrincipalAttributesRepository extends
		PrincipalAttributesRepository.AbstractPrincipalAttributesRepository {

	private final static long serialVersionUID = 635669154384144165L;

	/**
	 * The expiration time.
	 */
	private long expiration;

	/**
	 * Expiration time unit.
	 */
	private String timeUnit;

	/**
	 * Return the expiration time.
	 *
	 * @return The expiration time.
	 */
	public long getExpiration(){
		return this.expiration;
	}

	/**
	 * Sets the expiration time.
	 *
	 * @param expiration
	 * 		The expiration time.
	 */
	public void setExpiration(final long expiration){
		this.expiration = expiration;
	}

	/**
	 * Return xpiration time unit.
	 *
	 * @return Expiration time unit.
	 */
	public String getTimeUnit(){
		return this.timeUnit;
	}

	/**
	 * Sets xpiration time unit.
	 *
	 * @param timeUnit
	 * 		Expiration time unit.
	 */
	public void setTimeUnit(final String timeUnit){
		this.timeUnit = timeUnit;
	}

	@Override
	public String toString(){
		return new StringJoiner(", ")
				.add(super.toString())
				.add("expiration=" + expiration)
				.add("timeUnit=" + timeUnit)
				.toString();
	}

}
