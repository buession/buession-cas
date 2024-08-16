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

import com.buession.core.builder.ListBuilder;
import com.buession.core.utils.Assert;
import com.buession.httpclient.HttpClient;
import com.buession.httpclient.core.Header;
import com.buession.httpclient.core.Response;
import com.buession.httpclient.exception.RequestException;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.cas.services.client.exception.ServiceRegistryClientException;
import org.apereo.cas.services.client.exception.ServiceRegistryClientHttpException;
import org.apereo.cas.services.RegexRegisteredService;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
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
	 * {@link HttpClient} 实例
	 */
	private HttpClient httpClient;

	private final static Logger logger = LoggerFactory.getLogger(AbstractServiceRegistryClient.class);

	/**
	 * 构造函数
	 *
	 * @param baseUrl
	 * 		基路径
	 */
	public AbstractServiceRegistryClient(final String baseUrl) {
		Assert.isBlank(baseUrl, "CAS Server base url cloud not be empty or null.");
		this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
	}

	/**
	 * 构造函数
	 *
	 * @param baseUrl
	 * 		基路径
	 * @param httpClient
	 *        {@link HttpClient} 实例
	 */
	public AbstractServiceRegistryClient(final String baseUrl, final HttpClient httpClient) {
		this(baseUrl);
		setHttpClient(httpClient);
	}

	/**
	 * 返回 {@link HttpClient} 实例
	 *
	 * @return {@link HttpClient} 实例
	 */
	public HttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * 设置 {@link HttpClient} 实例
	 *
	 * @param httpClient
	 *        {@link HttpClient} 实例
	 */
	public void setHttpClient(HttpClient httpClient) {
		Assert.isNull(httpClient, HttpClient.class.getName() + " instance cloud not be null.");
		this.httpClient = httpClient;
	}

	@Override
	public RegisteredService save(final RegisteredService service) throws ServiceRegistryClientException {
		final ObjectMapper objectMapper = getObjectMapper();

		try{
			objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
					ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
			String str = objectMapper.writeValueAsString(service);

			str = "{\"@class\":\"" + service.getClass().getName() + "\"," + str.substring(1);
			Response response = httpClient.post(baseUrl + "/importRegisteredServices",
					new JsonRawRequestBody(str, str.length()));

			if(response.isSuccessful()){
				return service;
			}else{
				if(logger.isErrorEnabled()){
					logger.error("Request import registered services list error: HTTP Code is: {}",
							response.getStatusCode());
				}
				throw new ServiceRegistryClientHttpException(response.getStatusCode(), response.getStatusText());
			}
		}catch(IOException e){
			if(logger.isErrorEnabled()){
				logger.error("Request import registered services list error: {}", e.getMessage());
			}
			throw new ServiceRegistryClientException(e.getMessage(), e);
		}catch(RequestException e){
			logger.error("Request import registered services list error: {}", e.getMessage());
			throw new ServiceRegistryClientHttpException(0, e.getMessage(), e);
		}
	}

	@Override
	public List<RegisteredService> list() throws ServiceRegistryClientException {
		final ObjectMapper objectMapper = getObjectMapper();

		try{
			Response response = httpClient.get(baseUrl + "/registeredServices");

			if(response.isSuccessful()){
				objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
						ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, JsonTypeInfo.As.PROPERTY);
				final List<RegexRegisteredService> services = objectMapper.readValue(response.getBody(),
						new TypeReference<List<RegexRegisteredService>>() {

						});
				return new ArrayList<>(services);
			}else{
				if(logger.isErrorEnabled()){
					logger.error("Request registered services list error: HTTP Code is: {}", response.getStatusCode());
				}
				throw new ServiceRegistryClientHttpException(response.getStatusCode(), response.getStatusText());
			}
		}catch(IOException e){
			if(logger.isErrorEnabled()){
				logger.error("Request registered services list error: {}", e.getMessage());
			}
			throw new ServiceRegistryClientException(e.getMessage(), e);
		}catch(RequestException e){
			logger.error("Request registered services list error: {}", e.getMessage());
			throw new ServiceRegistryClientHttpException(0, e.getMessage(), e);
		}
	}

	@Override
	public RegisteredService get(final int id) throws ServiceRegistryClientException {
		final ObjectMapper objectMapper = getObjectMapper();

		try{
			Response response = httpClient.get(baseUrl + "/registeredServices/" + id);

			if(response.isSuccessful()){
				objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
						ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, JsonTypeInfo.As.PROPERTY);
				return objectMapper.readValue(response.getBody(), RegexRegisteredService.class);
			}else{
				if(logger.isErrorEnabled()){
					logger.error("Request registered service detail error: HTTP Code is: {}",
							response.getStatusCode());
				}
				throw new ServiceRegistryClientHttpException(response.getStatusCode(), response.getStatusText());
			}
		}catch(IOException e){
			if(logger.isErrorEnabled()){
				logger.error("Request registered service detail error: {}", e.getMessage());
			}
			throw new ServiceRegistryClientException(e.getMessage(), e);
		}catch(RequestException e){
			logger.error("Request registered service detail error: {}", e.getMessage());
			throw new ServiceRegistryClientHttpException(0, e.getMessage(), e);
		}
	}

	@Override
	public void delete(final int id) throws ServiceRegistryClientException {
		try{
			Response response = httpClient.delete(baseUrl + "/registeredServices/" + id,
					ListBuilder.of(new Header("Content-Type", "application/json")));

			if(response.isSuccessful()){
				//
			}else{
				if(logger.isErrorEnabled()){
					logger.error("Delete registered service error: HTTP Code is: {}",
							response.getStatusCode());
				}
				throw new ServiceRegistryClientHttpException(response.getStatusCode(), response.getStatusText());
			}
		}catch(IOException e){
			if(logger.isErrorEnabled()){
				logger.error("Delete registered service error: {}", e.getMessage());
			}
			throw new ServiceRegistryClientException(e.getMessage(), e);
		}catch(RequestException e){
			logger.error("Delete registered service error: {}", e.getMessage());
			throw new ServiceRegistryClientHttpException(0, e.getMessage(), e);
		}
	}

	protected static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return objectMapper;
	}

}
