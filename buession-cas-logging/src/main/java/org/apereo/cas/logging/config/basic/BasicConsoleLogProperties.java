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
package org.apereo.cas.logging.config.basic;

import java.io.Serializable;

/**
 * 控制台日志配置
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class BasicConsoleLogProperties implements Serializable {

	private final static long serialVersionUID = -6264010693592997006L;

	/**
	 * 日志消息
	 */
	private String message =
			"${id} login success at: ${time}(IP: ${time}), User-Agent: ${User-Agent}, operating system: ${os_name} " +
					"${os_version}, device type: ${device_type}, browser: ${browser_name} ${browser_version}.";

	/**
	 * 返回日志消息
	 *
	 * @return 日志消息
	 */
	public String getMessage(){
		return message;
	}

	/**
	 * 设置日志消息
	 *
	 * @param message
	 * 		日志消息
	 */
	public void setMessage(String message){
		this.message = message;
	}

}
