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
package org.apereo.cas.logging.config.history;

import com.buession.logging.mongodb.spring.MongoHandlerFactoryBean;
import com.mongodb.MongoClientSettings;
import org.bson.UuidRepresentation;
import org.springframework.data.mapping.model.FieldNamingStrategy;

import java.io.Serializable;

/**
 * MongoDB 历史登录日志配置
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class HistoryMongoProperties implements Serializable {

	/**
	 * MongoDB 主机地址
	 */
	private String host;

	/**
	 * MongoDB 端口
	 */
	private int port = MongoHandlerFactoryBean.DEFAULT_PORT;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * Mongo database URI.
	 */
	private String url = MongoHandlerFactoryBean.DEFAULT_URL;

	/**
	 * 副本集名称
	 */
	private String replicaSetName;

	/**
	 * 数据库名称
	 */
	private String databaseName;

	/**
	 * 认证数据库名称
	 */
	private String authenticationDatabase;

	/**
	 * Collection 名称
	 */
	private String collectionName;

	/**
	 * Representation to use when converting a UUID to a BSON binary value.
	 */
	private UuidRepresentation uuidRepresentation = MongoHandlerFactoryBean.DEFAULT_UUID_REPRESENTATION;

	/**
	 * Whether to enable auto-index creation.
	 */
	private Boolean autoIndexCreation = true;

	/**
	 * Fully qualified name of the FieldNamingStrategy to use.
	 */
	private Class<? extends FieldNamingStrategy> fieldNamingStrategy = MongoHandlerFactoryBean.DEFAULT_FIELD_NAMING_STRATEGY;

	/**
	 * {@link MongoClientSettings}
	 */
	private MongoClientSettings clientSettings = MongoClientSettings.builder().build();

	/**
	 * 返回 MongoDB 主机地址
	 *
	 * @return MongoDB 主机地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置 MongoDB 主机地址
	 *
	 * @param host
	 * 		MongoDB 主机地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 返回 MongoDB 端口
	 *
	 * @return MongoDB 端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置 MongoDB 端口
	 *
	 * @param port
	 * 		MongoDB 端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 返回用户名
	 *
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 *
	 * @param username
	 * 		用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回密码
	 *
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password
	 * 		密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Return Mongo database URI.
	 *
	 * @return Mongo database URI.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets Mongo database URI.
	 *
	 * @param url
	 * 		Mongo database URI.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回副本集名称
	 *
	 * @return 副本集名称
	 */
	public String getReplicaSetName() {
		return replicaSetName;
	}

	/**
	 * 设置副本集名称
	 *
	 * @param replicaSetName
	 * 		副本集名称
	 */
	public void setReplicaSetName(String replicaSetName) {
		this.replicaSetName = replicaSetName;
	}

	/**
	 * 返回数据库名称
	 *
	 * @return 数据库名称
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * 设置数据库名称
	 *
	 * @param databaseName
	 * 		数据库名称
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * 返回认证数据库名称
	 *
	 * @return 认证数据库名称
	 */
	public String getAuthenticationDatabase() {
		return authenticationDatabase;
	}

	/**
	 * 设置认证数据库名称
	 *
	 * @param authenticationDatabase
	 * 		认证数据库名称
	 */
	public void setAuthenticationDatabase(String authenticationDatabase) {
		this.authenticationDatabase = authenticationDatabase;
	}

	/**
	 * 返回 Collection 名称
	 *
	 * @return Collection 名称
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * 设置 Collection 名称
	 *
	 * @param collectionName
	 * 		Collection 名称
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	/**
	 * Return representation to use when converting a UUID to a BSON binary value.
	 *
	 * @return Representation to use when converting a UUID to a BSON binary value.
	 */
	public UuidRepresentation getUuidRepresentation() {
		return uuidRepresentation;
	}

	/**
	 * Sets representation to use when converting a UUID to a BSON binary value.
	 *
	 * @param uuidRepresentation
	 * 		Representation to use when converting a UUID to a BSON binary value.
	 */
	public void setUuidRepresentation(UuidRepresentation uuidRepresentation) {
		this.uuidRepresentation = uuidRepresentation;
	}

	/**
	 * Return enable auto-index creation.
	 *
	 * @return true / false
	 */
	public Boolean getAutoIndexCreation() {
		return autoIndexCreation;
	}

	/**
	 * Sets enable auto-index creation.
	 *
	 * @param autoIndexCreation
	 * 		enable auto-index creation.
	 */
	public void setAutoIndexCreation(Boolean autoIndexCreation) {
		this.autoIndexCreation = autoIndexCreation;
	}

	/**
	 * Return fully qualified name of the FieldNamingStrategy to use.
	 *
	 * @return Fully qualified name of the FieldNamingStrategy to use.
	 */
	public Class<? extends FieldNamingStrategy> getFieldNamingStrategy() {
		return fieldNamingStrategy;
	}

	/**
	 * Sets fully qualified name of the FieldNamingStrategy to use.
	 *
	 * @param fieldNamingStrategy
	 * 		Fully qualified name of the FieldNamingStrategy to use.
	 */
	public void setFieldNamingStrategy(
			Class<? extends FieldNamingStrategy> fieldNamingStrategy) {
		this.fieldNamingStrategy = fieldNamingStrategy;
	}

	/**
	 * 返回 {@link MongoClientSettings}
	 *
	 * @return {@link MongoClientSettings}
	 */
	public MongoClientSettings getClientSettings() {
		return clientSettings;
	}

	/**
	 * 设置 {@link MongoClientSettings}
	 *
	 * @param clientSettings
	 *        {@link MongoClientSettings}
	 */
	public void setClientSettings(MongoClientSettings clientSettings) {
		this.clientSettings = clientSettings;
	}

}
