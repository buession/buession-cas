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
package org.apereo.cas.support.loginlog.flow.action;

import com.buession.core.validator.Validate;
import com.buession.web.servlet.http.request.RequestUtils;
import org.apereo.cas.support.loginlog.manager.LoginLogManager;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 登录日志 {@link Action}
 *
 * @author Yong.Teng
 * @since 2.1.0
 */
public class LoginLogAction extends AbstractAction {

	/**
	 * Action 名称
	 */
	public final static String NAME = "loginLogAction";

	/**
	 * 登录日志管理器
	 */
	private final LoginLogManager loginLogManager;

	/**
	 * 真实 IP 头名称
	 */
	private final String clientIpHeaderName;

	/**
	 * 构造函数
	 *
	 * @param loginLogManager
	 * 		登录日志管理器
	 * @param clientIpHeaderName
	 * 		真实 IP 头名称
	 */
	public LoginLogAction(final LoginLogManager loginLogManager, final String clientIpHeaderName){
		super();
		this.loginLogManager = loginLogManager;
		this.clientIpHeaderName = clientIpHeaderName;
	}

	@Override
	protected Event doExecute(final RequestContext requestContext){
		HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext(requestContext);
		String clientIp = Validate.hasText(clientIpHeaderName) ? request.getHeader(clientIpHeaderName) : null;

		if(Validate.isBlank(clientIp)){
			clientIp = RequestUtils.getClientIp(request);
		}

		loginLogManager.execute(requestContext.getRequestParameters().get("username"), new Date(), clientIp);

		return success();
	}

}
