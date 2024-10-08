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
package org.apereo.cas.web.flow.action;

import com.buession.core.validator.Validate;
import com.buession.lang.Constants;
import com.buession.logging.core.LogData;
import com.buession.logging.core.Principal;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.logging.LoggingManager;
import org.apereo.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 日志 {@link Action}
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public class LoggingAction extends AbstractAction {

	/**
	 * Action 名称
	 */
	public final static String NAME = "loginLoggingAction";

	/**
	 * {@link com.buession.logging.core.BusinessType} 值
	 */
	private final String businessType;

	/**
	 * {@link com.buession.logging.core.Event} 值
	 */
	private final String event;

	/**
	 * 描述
	 */
	private final String description;

	/**
	 * ID 字段名称
	 */
	private final String idFieldName;

	/**
	 * 用户名字段名称
	 */
	private final String usernameFieldName;

	/**
	 * 真实姓名字段名称
	 */
	private final String realNameFieldName;

	/**
	 * 日志管理器
	 */
	private final List<LoggingManager> loggingManagers;

	/**
	 * 构造函数
	 *
	 * @param businessType
	 *        {@link com.buession.logging.core.BusinessType}
	 * @param event
	 *        {@link com.buession.logging.core.Event}
	 * @param description
	 * 		描述
	 * @param idFieldName
	 * 		ID 字段名称
	 * @param usernameFieldName
	 * 		用户名字段名称
	 * @param realNameFieldName
	 * 		真实姓名字段名称
	 * @param loggingManagers
	 * 		基本日志管理器
	 */
	public LoggingAction(final String businessType, final String event, final String description,
						 final String idFieldName, final String usernameFieldName, final String realNameFieldName,
						 final List<LoggingManager> loggingManagers) {
		this.businessType = businessType;
		this.event = event;
		this.description = Optional.ofNullable(description).orElse(Constants.EMPTY_STRING);
		this.idFieldName = idFieldName;
		this.usernameFieldName = usernameFieldName;
		this.realNameFieldName = realNameFieldName;
		this.loggingManagers = loggingManagers;
	}

	@Override
	protected Event doExecute(final RequestContext requestContext) {
		final AuthenticationResult authenticationResult = WebUtils.getAuthenticationResult(requestContext);
		final Authentication authentication = authenticationResult.getAuthentication();
		final org.apereo.cas.authentication.principal.Principal principal = authentication.getPrincipal();
		final Map<String, List<Object>> uAttributes = principal.getAttributes();
		String uid;
		String username;
		String realName;

		final LogData loginData = new LogData();

		final Principal logPrincipal = new Principal();

		if(Validate.hasText(idFieldName) && uAttributes.containsKey(idFieldName)){
			List<Object> uids = uAttributes.get(idFieldName);
			if(uids.isEmpty()){
				uid = principal.getId();
			}else{
				uid = uids.get(0).toString();
				uAttributes.remove(idFieldName);
			}
		}else{
			uid = principal.getId();
		}

		if(Validate.hasText(usernameFieldName) && uAttributes.containsKey(usernameFieldName)){
			List<Object> usernames = uAttributes.get(usernameFieldName);
			if(usernames.isEmpty()){
				username = principal.toString();
			}else{
				username = usernames.get(0).toString();
				uAttributes.remove(usernameFieldName);
			}
		}else{
			username = principal.toString();
		}

		if(Validate.hasText(realNameFieldName) && uAttributes.containsKey(realNameFieldName)){
			List<Object> realNames = uAttributes.get(realNameFieldName);
			if(realNames.isEmpty()){
				realName = null;
			}else{
				realName = realNames.get(0).toString();
				uAttributes.remove(realNameFieldName);
			}
		}else{
			realName = null;
		}

		logPrincipal.setId(uid);
		logPrincipal.setUserName(username);
		logPrincipal.setRealName(realName);

		final Map<String, Object> extra = new HashMap<>(uAttributes);

		loginData.setBusinessType(businessType);
		loginData.setEvent(event);
		loginData.setPrincipal(logPrincipal);
		loginData.setDescription(description);
		loginData.setExtra(extra);

		loggingManagers.forEach((loggingManager)->{
			loggingManager.execute(loginData);
		});

		return success();
	}

}
