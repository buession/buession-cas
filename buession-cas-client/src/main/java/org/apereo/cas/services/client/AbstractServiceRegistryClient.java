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

import com.buession.core.serializer.JsonSerializer;
import com.buession.core.serializer.SerializerException;
import com.buession.core.serializer.type.TypeReference;
import com.buession.core.utils.Assert;
import com.buession.httpclient.HttpClient;
import com.buession.httpclient.core.Response;
import com.buession.httpclient.exception.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Service 注册客户端抽象类
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public abstract class AbstractServiceRegistryClient implements ServiceRegistryClient {

	/**
	 * 基路径
	 */
	private final String baseUrl;

	/**
	 * JSON 反序列化实例
	 */
	private JsonSerializer serializer;

	/**
	 * {@link HttpClient} 实例
	 */
	private HttpClient httpClient;

	private final static Logger logger = LoggerFactory.getLogger(AbstractServiceRegistryClient.class);

	/**
	 * 构造函数
	 *
	 * @param baseUrl
	 * 		基路径
	 * @param serializer
	 * 		JSON 反序列化实例
	 */
	public AbstractServiceRegistryClient(final String baseUrl, final JsonSerializer serializer){
		Assert.isBlank(baseUrl, "CAS Server base url cloud not be empty or null.");
		Assert.isNull(serializer, "JsonSerializer cloud not be null.");
		this.baseUrl = baseUrl;
		this.serializer = serializer;
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
	public AbstractServiceRegistryClient(final String baseUrl, final JsonSerializer serializer,
										 final HttpClient httpClient){
		this(baseUrl, serializer);
		setHttpClient(httpClient);
	}

	/**
	 * 返回 {@link HttpClient} 实例
	 *
	 * @return {@link HttpClient} 实例
	 */
	public HttpClient getHttpClient(){
		return httpClient;
	}

	/**
	 * 设置 {@link HttpClient} 实例
	 *
	 * @param httpClient
	 *        {@link HttpClient} 实例
	 */
	public void setHttpClient(HttpClient httpClient){
		Assert.isNull(httpClient, HttpClient.class.getName() + " instance cloud not be null.");
		this.httpClient = httpClient;
	}

	@Override
	public RegisteredService save(final RegisteredService service){
		return null;
	}

	@Override
	public List<RegisteredService> list(){
		try{
			Response response = httpClient.get(baseUrl + "/registeredServices");

			if(response.isSuccessful()){
				return serializer.deserialize(response.getBody(), new TypeReference<List<RegisteredService>>() {

				});
			}else{
				if(logger.isErrorEnabled()){
					logger.error("Request registered services list error: HTTP Code is: {}", response.getStatusCode());
				}
			}
		}catch(IOException e){
			if(logger.isErrorEnabled()){
				logger.error("Request registered services list error: {}", e.getMessage());
			}
		}catch(RequestException e){
			logger.error("Request registered services list error: {}", e.getMessage());
		}catch(SerializerException e){
			logger.error("Request registered services list error: {}", e.getMessage());
		}

		return null;
	}

	@Override
	public RegisteredService list(final String id){
		return null;
	}

	@Override
	public void delete(final String id){

	}

}
