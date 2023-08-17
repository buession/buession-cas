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
package org.apereo.cas.logging.manager;

import com.buession.core.utils.StringUtils;
import com.buession.logging.core.LogData;

/**
 * 控制台基本登录日志管理器
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class ConsoleBasicLoginLoggingManager extends AbstractLoginLoggingManager implements BasicLoginLoggingManager {

	private final String template;

	public ConsoleBasicLoginLoggingManager(final String template) {
		this.template = template;
	}

	@Override
	public void execute(final LogData logData) {
		String message = template;

		message = StringUtils.replace(message, "${id}", logData.getPrincipal().toString());
		message = StringUtils.replace(message, "${time}", logData.getDateTime().toString());
		message = StringUtils.replace(message, "${clientIp}", logData.getClientIp());
		message = StringUtils.replace(message, "${User-Agent}", logData.getUserAgent());
		message = StringUtils.replace(message, "${os_name}", logData.getOperatingSystem().getName());
		message = StringUtils.replace(message, "${os_version}", logData.getOperatingSystem().getVersion());
		message = StringUtils.replace(message, "${device_type}", logData.getDeviceType().getName());
		message = StringUtils.replace(message, "${browser_name}", logData.getBrowser().getName());
		message = StringUtils.replace(message, "${browser_version}", logData.getBrowser().getVersion());

		System.out.println(message);
	}

}
