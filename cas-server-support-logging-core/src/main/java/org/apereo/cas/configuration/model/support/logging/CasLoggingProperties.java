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
package org.apereo.cas.logging.config;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 日志配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@RequiresModule(name = "cas-server-core-authentication", automated = true)
@JsonFilter("CasLoggingProperties")
@ConfigurationProperties(prefix = CasLoggingProperties.PREFIX)
public class CasLoggingProperties {

	public final static String PREFIX = CasConfigurationProperties.PREFIX + ".logging";

	/**
	 * {@link com.buession.logging.core.BusinessType} 值
	 */
	@RequiredProperty
	private String businessType = "Login";

	/**
	 * {@link com.buession.logging.core.Event} 值
	 */
	@RequiredProperty
	private String event = "Login";

	/**
	 * 描述
	 */
	private String description = "Login";

	/**
	 * 基本日志配置
	 */
	@NestedConfigurationProperty
	private BasicLoggingProperties basic = new BasicLoggingProperties();

	/**
	 * 历史日志配置
	 */
	@NestedConfigurationProperty
	private HistoryLoggingProperties history = new HistoryLoggingProperties();

	/**
	 * 返回 {@link com.buession.logging.core.BusinessType} 值
	 *
	 * @return {@link com.buession.logging.core.BusinessType} 值
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 设置 {@link com.buession.logging.core.BusinessType} 值
	 *
	 * @param businessType
	 *        {@link com.buession.logging.core.BusinessType} 值
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 返回 {@link com.buession.logging.core.Event} 值
	 *
	 * @return {@link com.buession.logging.core.Event} 值
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * 设置 {@link com.buession.logging.core.Event} 值
	 *
	 * @param event
	 *        {@link com.buession.logging.core.Event} 值
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * 返回描述
	 *
	 * @return 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 *
	 * @param description
	 * 		描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回基本日志配置
	 *
	 * @return 基本日志配置
	 */
	public BasicLoggingProperties getBasic() {
		return basic;
	}

	/**
	 * 设置基本日志配置
	 *
	 * @param basic
	 * 		基本日志配置
	 */
	public void setBasic(BasicLoggingProperties basic) {
		this.basic = basic;
	}

	/**
	 * 返回历史日志配置
	 *
	 * @return 历史日志配置
	 */
	public HistoryLoggingProperties getHistory() {
		return history;
	}

	/**
	 * 设置历史日志配置
	 *
	 * @param history
	 * 		历史日志配置
	 */
	public void setHistory(HistoryLoggingProperties history) {
		this.history = history;
	}

}
