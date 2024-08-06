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
package org.apereo.cas.services;

import com.buession.core.utils.StringUtils;

import java.util.Objects;
import java.util.Set;

/**
 * Service 属性
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public final class Property {

	/**
	 * Service 属性值
	 */
	private Set<String> values;

	/**
	 * 构造函数
	 */
	public Property() {
	}

	/**
	 * 构造函数
	 *
	 * @param values
	 * 		Service 属性值
	 */
	public Property(Set<String> values) {
		this.values = values;
	}

	/**
	 * 返回 Service 属性值
	 *
	 * @return Service 属性值
	 */
	public Set<String> getValues() {
		return values;
	}

	/**
	 * 设置 Service 属性值
	 *
	 * @param values
	 * 		Service 属性值
	 */
	public void setValues(Set<String> values) {
		this.values = values;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		}

		if(o instanceof Property){
			Property property = (Property) o;

			return Objects.equals(values, property.values);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(values);
	}

	@Override
	public String toString() {
		return "[" + StringUtils.join(values, ", ") + "]";
	}

}
