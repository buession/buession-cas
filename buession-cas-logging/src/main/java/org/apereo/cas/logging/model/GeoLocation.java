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

import java.io.Serializable;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public class GeoLocation implements Serializable {

	private final static long serialVersionUID = 951578665709567861L;

	private Country country;

	private District district;

	public Country getCountry(){
		return country;
	}

	public void setCountry(Country country){
		this.country = country;
	}

	public District getDistrict(){
		return district;
	}

	public void setDistrict(District district){
		this.district = district;
	}

	public final static class Country implements Serializable {

		private final static long serialVersionUID = 2542996497353771071L;

		private String code;

		private String name;

		private String fullName;

		public String getCode(){
			return code;
		}

		public void setCode(String code){
			this.code = code;
		}

		public String getName(){
			return name;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getFullName(){
			return fullName;
		}

		public void setFullName(String fullName){
			this.fullName = fullName;
		}
	}

	public final static class District implements Serializable {

		private final static long serialVersionUID = 8703201935758837505L;

		private String name;

		private String fullName;

		public String getName(){
			return name;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getFullName(){
			return fullName;
		}

		public void setFullName(String fullName){
			this.fullName = fullName;
		}

	}

}
