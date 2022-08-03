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
package org.apereo.cas.support;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yong.Teng
 * @since 2.0.3
 */
@ConfigurationProperties(prefix = CasSupportConfigurationProperties.PREFIX)
public class CasSupportConfigurationProperties {

	public final static String PREFIX = CasConfigurationProperties.PREFIX + ".support";

	/**
	 * 真实客户端 IP 头名称
	 */
	private String clientRealIpHeaderName;

	/**
	 * 返回真实客户端 IP 头名称
	 *
	 * @return 真实客户端 IP 头名称
	 */
	public String getClientRealIpHeaderName(){
		return clientRealIpHeaderName;
	}

	/**
	 * 设置真实客户端 IP 头名称
	 *
	 * @param clientRealIpHeaderName
	 * 		真实客户端 IP 头名称
	 */
	public void setClientRealIpHeaderName(String clientRealIpHeaderName){
		this.clientRealIpHeaderName = clientRealIpHeaderName;
	}

}
