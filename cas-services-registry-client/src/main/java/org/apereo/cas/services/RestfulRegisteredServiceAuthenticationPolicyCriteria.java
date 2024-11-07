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
 * This is {@link RestfulRegisteredServiceAuthenticationPolicyCriteria}.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class RestfulRegisteredServiceAuthenticationPolicyCriteria implements AuthenticationPolicyCriteria {

	private final static long serialVersionUID = -5384026225871407864L;

	private String url;

	private String basicAuthUsername;

	private String basicAuthPassword;

	public String getUrl(){
		return this.url;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getBasicAuthUsername(){
		return this.basicAuthUsername;
	}

	public void setBasicAuthUsername(String basicAuthUsername){
		this.basicAuthUsername = basicAuthUsername;
	}

	public String getBasicAuthPassword(){
		return this.basicAuthPassword;
	}

	public void setBasicAuthPassword(String basicAuthPassword){
		this.basicAuthPassword = basicAuthPassword;
	}

	@Override
	public String toString(){
		return StringBuilder.getInstance(this)
				.add("url", url)
				.add("basicAuthUsername", basicAuthUsername)
				.add("basicAuthPassword", basicAuthPassword)
				.asString();
	}

}
