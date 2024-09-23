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
package org.apereo.cas.configuration.model.support.logging;

import com.buession.httpclient.conn.nio.IOReactorConfig;
import com.buession.httpclient.core.Configuration;
import com.buession.logging.core.RequestMethod;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;
import java.util.concurrent.ThreadFactory;

/**
 * Rest 日志适配器配置
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@JsonFilter("HistoryRestLogProperties")
public class RestLoggingProperties implements AdapterLoggingProperties, Serializable {

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
	private String requestBodyBuilderClass = "com.buession.logging.rest.core.JsonRequestBodyBuilder";

	/**
	 * {@link com.buession.httpclient.HttpClient} 配置
	 *
	 * @since 3.0.0
	 */
	@NestedConfigurationProperty
	private HttpClientProperties httpClient = new HttpClientProperties();

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
	public String getRequestBodyBuilderClass() {
		return requestBodyBuilderClass;
	}

	/**
	 * 设置请求体构建器
	 *
	 * @param requestBodyBuilderClass
	 * 		请求体构建器
	 */
	public void setRequestBodyBuilderClass(String requestBodyBuilderClass) {
		this.requestBodyBuilderClass = requestBodyBuilderClass;
	}

	/**
	 * 返回 {@link com.buession.httpclient.HttpClient} 配置
	 *
	 * @return {@link com.buession.httpclient.HttpClient} 配置
	 *
	 * @since 3.0.0
	 */
	public HttpClientProperties getHttpClient() {
		return httpClient;
	}

	/**
	 * 设置 {@link com.buession.httpclient.HttpClient} 配置
	 *
	 * @param httpClient
	 *        {@link com.buession.httpclient.HttpClient} 配置
	 *
	 * @since 3.0.0
	 */
	public void setHttpClient(HttpClientProperties httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * {@link com.buession.httpclient.HttpClient} 配置
	 */
	public final static class HttpClientProperties extends Configuration {

		private ApacheClient apacheClient;

		private OkHttp okHttp;

		public ApacheClient getApacheClient() {
			return apacheClient;
		}

		public void setApacheClient(ApacheClient apacheClient) {
			this.apacheClient = apacheClient;
		}

		public OkHttp getOkHttp() {
			return okHttp;
		}

		public void setOkHttp(OkHttp okHttp) {
			this.okHttp = okHttp;
		}

		public final static class ApacheClient {

			private IOReactorConfig ioReactor;

			private Class<? extends ThreadFactory> threadFactory;

			public IOReactorConfig getIoReactor() {
				return ioReactor;
			}

			public void setIoReactor(IOReactorConfig ioReactor) {
				this.ioReactor = ioReactor;
			}

			public Class<? extends ThreadFactory> getThreadFactory() {
				return threadFactory;
			}

			public void setThreadFactory(Class<? extends ThreadFactory> threadFactory) {
				this.threadFactory = threadFactory;
			}

		}

		public final static class OkHttp {

		}

	}

}
