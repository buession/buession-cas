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
package org.apereo.cas.client.exception;

import java.util.StringJoiner;

/**
 * @author Yong.Teng
 * @since 2.2.0
 */
public class ServiceRegistryClientHttpException extends ServiceRegistryClientException {

	private static final long serialVersionUID = 4142153593236050023L;

	private final int statusCode;

	private final String statusText;

	public ServiceRegistryClientHttpException(int statusCode, String statusText){
		super(statusText);
		this.statusCode = statusCode;
		this.statusText = statusText;
	}

	public ServiceRegistryClientHttpException(int statusCode, String statusText, String message){
		super(message);
		this.statusCode = statusCode;
		this.statusText = statusText;
	}

	public ServiceRegistryClientHttpException(int statusCode, String statusText, String message, Throwable cause){
		super(message, cause);
		this.statusCode = statusCode;
		this.statusText = statusText;
	}

	public ServiceRegistryClientHttpException(int statusCode, String statusText, Throwable cause){
		super(statusText, cause);
		this.statusCode = statusCode;
		this.statusText = statusText;
	}

	public ServiceRegistryClientHttpException(int statusCode, String statusText, String message, Throwable cause,
											  boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
		this.statusCode = statusCode;
		this.statusText = statusText;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public String getStatusText(){
		return statusText;
	}

	@Override
	public String toString(){
		return new StringJoiner(", ", "{", "}")
				.add("statusCode=" + statusCode)
				.add("statusText=" + statusText)
				.add(super.toString())
				.toString();
	}
	
}
