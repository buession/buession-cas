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
 * | Copyright @ 2013-2022 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.captcha.web.support;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 密码错误拦截器
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public interface PasswordFailureInterceptorAdapter extends AsyncHandlerInterceptor {

	@Override
	default boolean preHandle(final HttpServletRequest request,
							  final HttpServletResponse response,
							  final Object handler) throws Exception{
		return true;
	}

	@Override
	default void postHandle(final HttpServletRequest request,
							final HttpServletResponse response,
							final Object handler,
							@Nullable final ModelAndView modelAndView) throws Exception{
	}

	@Override
	default void afterCompletion(final HttpServletRequest request,
								 final HttpServletResponse response,
								 final Object handler,
								 @Nullable final Exception e) throws Exception{
	}

	@Override
	default void afterConcurrentHandlingStarted(final HttpServletRequest request,
												final HttpServletResponse response,
												final Object handler) throws Exception{
	}

}
