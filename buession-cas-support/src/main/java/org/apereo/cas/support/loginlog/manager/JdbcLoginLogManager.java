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
package org.apereo.cas.support.loginlog.manager;

import com.buession.web.utils.useragentutils.Browser;
import com.buession.web.utils.useragentutils.OperatingSystem;

import java.util.Date;

/**
 * JDBC 登录日志管理器
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class JdbcLoginLogManager extends AbstractLoginLogManager {

	@Override
	public void execute(final String id, final Date dateTime, final String clientIp){
		System.out.printf("%s login success at: %s(IP: %s).", id, dateTime, clientIp);
	}

	@Override
	public void execute(final String id, final Date dateTime, final String clientIp, final String userAgent,
						final OperatingSystem operatingSystem, final Browser browser){
		System.out.printf("%s login success at: %s(IP: %s), User-Agent: %s, operating system: %s %s, device type: %s," +
						" browser: %s %s.", id, dateTime, clientIp, userAgent, operatingSystem.getName(),
				operatingSystem.getVersion(), operatingSystem.getDeviceType().getName(), browser.getName(),
				browser.getVersion());
	}

}
