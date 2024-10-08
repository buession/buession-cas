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
package org.apereo.cas.logging;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.apereo.cas.configuration.model.support.logging.AdapterLoggingProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * 日志适配器 {@link org.springframework.boot.autoconfigure.AutoConfiguration} 基类
 *
 * @param <PROPS>
 * 		日志配置适配器
 * @param <HFB>
 *        {@link LogHandler} Factory Bean
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public abstract class AbstractLoggingConfiguration<PROPS extends AdapterLoggingProperties,
		HFB extends BaseLogHandlerFactoryBean<? extends LogHandler>> {

	/**
	 * {@link ConfigurableApplicationContext}
	 */
	protected final ConfigurableApplicationContext applicationContext;

	/**
	 * {@link AdapterLoggingProperties}
	 */
	protected final List<PROPS> properties;

	/**
	 * 构造函数
	 *
	 * @param properties
	 * 		日志配置适配器
	 * @param applicationContext
	 *        {@link ConfigurableApplicationContext}
	 */
	public AbstractLoggingConfiguration(final ConfigurableApplicationContext applicationContext,
										final List<PROPS> properties) {
		this.applicationContext = applicationContext;
		this.properties = properties;
	}

	public abstract List<HFB> logHandlerFactoryBean();

}
