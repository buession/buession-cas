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
package org.apereo.cas.configuration.model.support.logging;

import com.buession.logging.console.formatter.ConsoleLogDataFormatter;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.support.RequiredProperty;

import java.io.Serializable;

/**
 * 控制台日志适配器配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@JsonFilter("BasicConsoleLogProperties")
public class BasicConsoleLogProperties implements Serializable {

	private final static long serialVersionUID = -6264010693592997006L;

	/**
	 * 日志模板
	 */
	@RequiredProperty
	private String template =
			"${id} login success at: ${time}(IP: ${clientIp}), User-Agent: ${User-Agent}, operating system: " +
					"${os_name} ${os_version}, device type: ${device_type}, browser: ${browser_name} " +
					"${browser_version}.";

	/**
	 * 格式化 {@link ConsoleLogDataFormatter} 类名
	 *
	 * @since 3.0.0
	 */
	@RequiredProperty
	private String formatterName = "com.buession.logging.console.formatter.DefaultConsoleLogDataFormatter";

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
	 * 返回格式化 {@link ConsoleLogDataFormatter} 类名
	 *
	 * @return 格式化 {@link ConsoleLogDataFormatter} 类名
	 *
	 * @since 3.0.0
	 */
	public String getFormatterName() {
		return formatterName;
	}

	/**
	 * 设置格式化 {@link ConsoleLogDataFormatter} 类名
	 *
	 * @param formatterName
	 * 		格式化 {@link ConsoleLogDataFormatter} 类名
	 *
	 * @since 3.0.0
	 */
	public void setFormatterName(String formatterName) {
		this.formatterName = formatterName;
	}

}
