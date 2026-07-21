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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.logging.rest.httpclient;

import com.buession.core.utils.ClassUtils;
import com.buession.httpclient.apache.ApacheNioClientConnectionManager;
import com.buession.httpclient.conn.OkHttpNioClientConnectionManager;
import com.buession.logging.rest.spring.config.AbstractHttpClientConfiguration;
import com.buession.logging.rest.spring.config.HttpClientConfigurer;
import org.apereo.cas.util.spring.DirectObjectProvider;

/**
 * @author Yong.Teng
 * @since 1.0.0
 */
public class HttpAsyncClient {

	private final HttpClientConfigurer httpClientConfigurer;

	public HttpAsyncClient(final HttpClientConfigurer httpClientConfigurer) {
		this.httpClientConfigurer = httpClientConfigurer;
	}

	public com.buession.httpclient.HttpAsyncClient build() {
		if(ClassUtils.isPresent("org.apache.hc.client5.http.async.HttpAsyncClient")){
			final AsyncHttpClientConfiguration.AsyncApache asyncApache = new AsyncHttpClientConfiguration.AsyncApache();
			final ApacheNioClientConnectionManager nioClientConnectionManager =
					asyncApache.apache5NioClientConnectionManager(httpClientConfigurer);

			return asyncApache.httpAsyncClient(new DirectObjectProvider<>(nioClientConnectionManager));
		}

		if(ClassUtils.isPresent("org.apache.http.nio.client.HttpAsyncClient")){
			final AsyncHttpClientConfiguration.AsyncApache asyncApache = new AsyncHttpClientConfiguration.AsyncApache();
			final ApacheNioClientConnectionManager nioClientConnectionManager =
					asyncApache.apache5NioClientConnectionManager(httpClientConfigurer);

			return asyncApache.httpAsyncClient(new DirectObjectProvider<>(nioClientConnectionManager));
		}

		if(ClassUtils.isPresent("okhttp3.OkHttpClient")){
			final AsyncHttpClientConfiguration.AsyncOkHttp asyncOkHttp = new AsyncHttpClientConfiguration.AsyncOkHttp();
			final OkHttpNioClientConnectionManager clientConnectionManager =
					asyncOkHttp.okHttpNioClientConnectionManager(httpClientConfigurer);

			return asyncOkHttp.httpAsyncClient(new DirectObjectProvider<>(clientConnectionManager));
		}

		return null;

	}

	private final static class AsyncHttpClientConfiguration extends AbstractHttpClientConfiguration {

		private final static class AsyncApache extends AbstractAsyncApacheHttpClientConfiguration {

		}

		private final static class AsyncOkHttp extends AbstractAsyncOkHttpClientConfiguration {

		}

	}

}
