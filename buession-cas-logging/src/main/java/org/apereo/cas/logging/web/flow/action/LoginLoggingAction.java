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
package org.apereo.cas.logging.web.flow.action;

import com.buession.core.concurrent.ThreadPoolConfiguration;
import com.buession.core.validator.Validate;
import com.buession.geoip.Resolver;
import com.buession.web.servlet.http.request.RequestUtils;
import com.buession.web.utils.useragentutils.UserAgent;
import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.LoginLoggingThreadPoolExecutor;
import org.apereo.cas.logging.manager.BasicLoginLoggingManager;
import org.apereo.cas.logging.manager.HistoryLoginLoggingManager;
import org.apereo.cas.logging.model.LoginData;
import org.apereo.cas.logging.model.GeoLocation;
import org.apereo.cas.logging.utils.GeoIpUtils;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 登录日志 {@link Action}
 *
 * @author Yong.Teng
 * @since 2.1.0
 */
public class LoginLoggingAction extends AbstractAction {

	/**
	 * Action 名称
	 */
	public final static String NAME = "loginLoggingAction";

	/**
	 * 基本登录日志管理器
	 */
	private final BasicLoginLoggingManager basicLoginLoggingManager;

	/**
	 * 历史登录日志管理器接口
	 */
	private final HistoryLoginLoggingManager historyLoginLoggingManager;

	/**
	 * GeoIp 解析器
	 */
	protected final Resolver resolver;

	/**
	 * 真实 IP 头名称
	 */
	private final String clientIpHeaderName;

	/**
	 * 线程池配置
	 */
	private final ThreadPoolConfiguration threadPoolConfiguration;

	private final ThreadPoolExecutor threadPoolExecutor;

	/**
	 * 构造函数
	 *
	 * @param basicLoginLoggingManager
	 * 		基本登录日志管理器
	 * @param historyLoginLoggingManager
	 * 		历史登录日志管理器接口
	 * @param resolver
	 * 		GeoIp 解析器
	 * @param clientIpHeaderName
	 * 		真实 IP 头名称
	 * @param threadPoolConfiguration
	 * 		线程池配置
	 */
	public LoginLoggingAction(final BasicLoginLoggingManager basicLoginLoggingManager,
							  final HistoryLoginLoggingManager historyLoginLoggingManager,
							  final Resolver resolver, final String clientIpHeaderName,
							  final ThreadPoolConfiguration threadPoolConfiguration){
		super();
		this.basicLoginLoggingManager = basicLoginLoggingManager;
		this.historyLoginLoggingManager = historyLoginLoggingManager;
		this.resolver = resolver;
		this.clientIpHeaderName = clientIpHeaderName;
		this.threadPoolConfiguration = threadPoolConfiguration;
		this.threadPoolExecutor = createThreadPoolExecutor();
	}

	@Override
	protected Event doExecute(final RequestContext requestContext){
		HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);
		final LoginData loginData = new LoginData();
		final String username = requestContext.getRequestParameters().get("username");
		final String clientIp = getClientIp(request);
		final String userAgent = request.getHeader("User-Agent");

		loginData.setId(username);
		loginData.setDateTime(new Date());
		loginData.setClientIp(clientIp);
		loginData.setUserAgent(userAgent);

		threadPoolExecutor.execute(()->{
			final UserAgent userAgentInstance = new UserAgent(loginData.getUserAgent());
			final GeoLocation location = GeoIpUtils.resolver(resolver, clientIp);

			if(location.getCountry() == null){
				final GeoLocation.Country country = new GeoLocation.Country();
				country.setCode(Constants.NULL_VALUE);
				country.setName(Constants.NULL_VALUE);
				country.setFullName(Constants.NULL_VALUE);
				location.setCountry(country);
			}else{
				if(Validate.isEmpty(location.getCountry().getCode())){
					location.getCountry().setCode(Constants.NULL_VALUE);
				}
				if(Validate.isEmpty(location.getCountry().getName())){
					location.getCountry().setName(Constants.NULL_VALUE);
				}
				if(Validate.isEmpty(location.getCountry().getFullName())){
					location.getCountry().setFullName(Constants.NULL_VALUE);
				}
			}

			if(location.getDistrict() == null){
				final GeoLocation.District district = new GeoLocation.District();
				district.setName(Constants.NULL_VALUE);
				district.setFullName(Constants.NULL_VALUE);
				location.setDistrict(district);
			}else{
				if(Validate.isEmpty(location.getDistrict().getName())){
					location.getDistrict().setName(Constants.NULL_VALUE);
				}
				if(Validate.isEmpty(location.getDistrict().getFullName())){
					location.getDistrict().setFullName(Constants.NULL_VALUE);
				}
			}

			loginData.setOperatingSystem(userAgentInstance.getOperatingSystem());
			loginData.setBrowser(userAgentInstance.getBrowser());
			loginData.setLocation(location);

			basicLoginLoggingManager.execute(loginData);
			historyLoginLoggingManager.execute(loginData);
		});

		return success();
	}

	@Override
	protected void doPostExecute(RequestContext context) throws Exception{
		threadPoolExecutor.shutdown();
	}

	protected String getClientIp(final HttpServletRequest request){
		String clientIp = Validate.hasText(clientIpHeaderName) ? request.getHeader(clientIpHeaderName) : null;

		if(Validate.isBlank(clientIp)){
			clientIp = RequestUtils.getClientIp(request);
		}

		return clientIp;
	}

	protected ThreadPoolExecutor createThreadPoolExecutor(){
		return new LoginLoggingThreadPoolExecutor(threadPoolConfiguration);
	}

}
