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
package org.apereo.cas.logging.config.history;

import com.buession.logging.rest.core.RequestMethod;
import com.buession.logging.support.config.HandlerProperties;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.support.RequiredProperty;

import java.io.Serializable;

/**
 * Rest 历史登录日志配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@JsonFilter("HistoryRestLogProperties")
public class HistoryRestLogProperties implements HandlerProperties, Serializable {

	private final static long serialVersionUID = -7178083723307742589L;

	/**
	 * Rest Url
	 */
	@RequiredProperty
	private String url;

	/**
	 * 请求方式 {@link RequestMethod}
	 */
	private RequestMethod requestMethod = RequestMethod.POST;

	/**
	 * 请求体构建器
	 */
	private String requestBodyBuilder = "com.buession.logging.rest.core.JsonRequestBodyBuilder";

	/**
	 * 返回 Rest Url
	 *
	 * @return Rest Url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置 Rest Url
	 *
	 * @param url
	 * 		Rest Url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回请求方式 {@link RequestMethod}
	 *
	 * @return 请求方式 {@link RequestMethod}
	 */
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * 设置请求方式 {@link RequestMethod}
	 *
	 * @param requestMethod
	 * 		请求方式 {@link RequestMethod}
	 */
	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * 返回请求体构建器
	 *
	 * @return 请求体构建器
	 */
	public String getRequestBodyBuilder() {
		return requestBodyBuilder;
	}

	/**
	 * 设置请求体构建器
	 *
	 * @param requestBodyBuilder
	 * 		请求体构建器
	 */
	public void setRequestBodyBuilder(String requestBodyBuilder) {
		this.requestBodyBuilder = requestBodyBuilder;
	}

}
