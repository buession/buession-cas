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
package org.apereo.cas.logging.model;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public class District implements Serializable {

	private final static long serialVersionUID = -2622605869813427465L;

	/**
	 * 国家 Code 字段名称
	 */
	@Field(name = "country_code", targetType = FieldType.STRING)
	private String countryCode;

	/**
	 * 国家名称字段名称
	 */
	@Field(name = "country_name", targetType = FieldType.STRING)
	private String countryName;

	/**
	 * 地区名称字段名称
	 */
	@Field(name = "name", targetType = FieldType.STRING)
	private String name;

	public String getCountryCode(){
		return countryCode;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryName(){
		return countryName;
	}

	public void setCountryName(String countryName){
		this.countryName = countryName;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

}
