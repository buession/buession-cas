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
package org.apereo.cas.services.client;

import com.buession.core.serializer.JacksonJsonSerializer;
import com.buession.core.serializer.JsonSerializer;
import com.buession.httpclient.HttpClient;

/**
 * 默认 Service 注册客户端
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class DefaultServiceRegistryClient extends AbstractServiceRegistryClient {

	/**
	 * 构造函数
	 *
	 * @param baseUrl
	 * 		基路径
	 */
	public DefaultServiceRegistryClient(final String baseUrl){
		super(baseUrl, new JacksonJsonSerializer());
	}

	/**
	 * 构造函数
	 *
	 * @param baseUrl
	 * 		基路径
	 * @param httpClient
	 *        {@link HttpClient} 实例
	 */
	public DefaultServiceRegistryClient(final String baseUrl, final HttpClient httpClient){
		super(baseUrl, new JacksonJsonSerializer(), httpClient);
	}

	/**
	 * 构造函数
	 *
	 * @param baseUrl
	 * 		基路径
	 * @param serializer
	 * 		JSON 反序列化实例
	 */
	public DefaultServiceRegistryClient(final String baseUrl, final JsonSerializer serializer){
		super(baseUrl, serializer);
	}

	/**
	 * 构造函数
	 *
	 * @param baseUrl
	 * 		基路径
	 * @param serializer
	 * 		JSON 反序列化实例
	 * @param httpClient
	 *        {@link HttpClient} 实例
	 */
	public DefaultServiceRegistryClient(final String baseUrl, final JsonSerializer serializer,
										final HttpClient httpClient){
		super(baseUrl, serializer, httpClient);
	}

}
