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

import com.buession.lang.Constants;
import com.buession.logging.core.LogData;
import com.buession.logging.core.Principal;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.manager.BasicLoginLoggingManager;
import org.apereo.cas.logging.manager.HistoryLoginLoggingManager;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.Optional;

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
	 * {@link CasLoggingConfigurationProperties}
	 */
	private final CasLoggingConfigurationProperties casLoggingConfigurationProperties;

	/**
	 * 基本登录日志管理器
	 */
	private final BasicLoginLoggingManager basicLoginLoggingManager;

	/**
	 * 历史登录日志管理器接口
	 */
	private final HistoryLoginLoggingManager historyLoginLoggingManager;

	private final String description;

	/**
	 * 构造函数
	 *
	 * @param casLoggingConfigurationProperties
	 *        {@link CasLoggingConfigurationProperties}
	 * @param basicLoginLoggingManager
	 * 		基本登录日志管理器
	 * @param historyLoginLoggingManager
	 * 		历史登录日志管理器接口
	 */
	public LoginLoggingAction(final CasLoggingConfigurationProperties casLoggingConfigurationProperties,
							  final BasicLoginLoggingManager basicLoginLoggingManager,
							  final HistoryLoginLoggingManager historyLoginLoggingManager) {
		super();
		this.casLoggingConfigurationProperties = casLoggingConfigurationProperties;
		this.basicLoginLoggingManager = basicLoginLoggingManager;
		this.historyLoginLoggingManager = historyLoginLoggingManager;
		this.description = Optional.ofNullable(casLoggingConfigurationProperties.getDescription()).orElse(
				Constants.EMPTY_STRING);
	}

	@Override
	protected Event doExecute(final RequestContext requestContext) {
		final LogData loginData = new LogData();
		final String username = requestContext.getRequestParameters().get("username");
		final Principal principal = new Principal();

		principal.setUserName(username);

		loginData.setBusinessType(casLoggingConfigurationProperties.getBusinessType());
		loginData.setEvent(casLoggingConfigurationProperties.getEvent());
		loginData.setPrincipal(principal);
		loginData.setDescription(description);

		historyLoginLoggingManager.execute(loginData);
		basicLoginLoggingManager.execute(loginData);

		return success();
	}

}
