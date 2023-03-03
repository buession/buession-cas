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
 * This is {@link GroovyRegisteredServiceAccessStrategy}.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class GroovyRegisteredServiceAccessStrategy implements AccessStrategy {

	private final static long serialVersionUID = -558826730705386557L;

	private String groovyScript;

	/**
	 * The sorting/execution order of this strategy.
	 */
	private int order;

	public String getGroovyScript(){
		return groovyScript;
	}

	public void setGroovyScript(String groovyScript){
		this.groovyScript = groovyScript;
	}

	/**
	 * Return the sorting/execution order of this strategy.
	 *
	 * @return The sorting/execution order of this strategy.
	 */
	public int getOrder(){
		return order;
	}

	/**
	 * Sets the sorting/execution order of this strategy.
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
				.add("groovyScript", groovyScript)
				.add("order", order)
				.asString();
	}

}
