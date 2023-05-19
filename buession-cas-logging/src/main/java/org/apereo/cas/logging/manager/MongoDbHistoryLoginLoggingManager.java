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

import com.buession.core.utils.Assert;
import org.apereo.cas.logging.config.history.HistoryMongoDbLogProperties;
import org.apereo.cas.logging.model.HistoryLoginLog;
import org.apereo.cas.logging.model.LoginData;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * MongoDB 历史登录日志管理器
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class MongoDbHistoryLoginLoggingManager extends AbstractLoginLoggingManager
		implements HistoryLoginLoggingManager {

	private final MongoTemplate mongoTemplate;

	private final String collectionName;

	public MongoDbHistoryLoginLoggingManager(final MongoTemplate mongoTemplate,
											 final HistoryMongoDbLogProperties properties){
		super();
		Assert.isNull(mongoTemplate, "MongoTemplate cloud not be null.");

		this.mongoTemplate = mongoTemplate;
		this.collectionName = properties.getCollection();
	}

	@Override
	public void execute(final LoginData loginData){
		final HistoryLoginLog historyLoginLog = new HistoryLoginLog();

		// 用户名
		historyLoginLog.setUserName(loginData.getId());
		// 登录时间
		historyLoginLog.setLoginTime(loginData.getDateTime());
		// 登录 IP 地址
		historyLoginLog.setLoginIp(loginData.getClientIp());
		// 登录 User-Agent
		historyLoginLog.setUserAgent(loginData.getUserAgent());
		// 登录操作系统
		historyLoginLog.setOperatingSystem(loginData.getOperatingSystem());
		// 登录设备
		historyLoginLog.setDeviceType(loginData.getOperatingSystem().getDeviceType().name());
		// 登录浏览器
		historyLoginLog.setBrowser(loginData.getBrowser());
		// 登录地区
		final HistoryLoginLog.District district = new HistoryLoginLog.District();
		district.setCountryCode(loginData.getLocation().getCountry().getCode());
		district.setCountryName(loginData.getLocation().getCountry().getName());
		district.setName(loginData.getLocation().getDistrict().getFullName());
		historyLoginLog.setDistrict(district);

		mongoTemplate.save(historyLoginLog, collectionName);
	}

}
