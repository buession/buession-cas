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
 * Generates PersistentIds based on the Shibboleth algorithm.
 * The generated ids are based on a principal attribute is specified, or
 * the authenticated principal id.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class ShibbolethCompatiblePersistentIdGenerator implements PersistentIdGenerator {

	private final static long serialVersionUID = -8471511405431575393L;

	private String salt;

	private String attribute;

	public String getSalt(){
		return salt;
	}

	public void setSalt(String salt){
		this.salt = salt;
	}

	public String getAttribute(){
		return attribute;
	}

	public void setAttribute(String attribute){
		this.attribute = attribute;
	}

	@Override
	public String toString(){
		return StringBuilder.getInstance(this)
				.add("salt", salt)
				.add("attribute", attribute)
				.asString();
	}

}
