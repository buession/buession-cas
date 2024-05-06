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
package org.apereo.cas.logging.config.basic;

import com.buession.logging.support.config.HandlerProperties;

import java.io.Serializable;

/**
 * 控制台日志配置
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class BasicConsoleLogProperties implements HandlerProperties, Serializable {

	private final static long serialVersionUID = -6264010693592997006L;

	/**
	 * 日志模板
	 */
	private String template =
			"${id} login success at: ${time}(IP: ${clientIp}), User-Agent: ${User-Agent}, operating system: " +
					"${os_name} ${os_version}, device type: ${device_type}, browser: ${browser_name} " +
					"${browser_version}.";

	/**
	 * 返回日志模板
	 *
	 * @return 日志模板
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * 设置日志模板
	 *
	 * @param template
	 * 		日志模板
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * 返回日志模板
	 *
	 * @return 日志模板
	 */
	@Deprecated
	public String getMessage() {
		return getTemplate();
	}

	/**
	 * 设置日志模板
	 *
	 * @param message
	 * 		日志模板
	 */
	@Deprecated
	public void setMessage(String message) {
		setTemplate(message);
	}

}
