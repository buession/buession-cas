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
package org.apereo.cas.authentication.principal;

import org.apereo.cas.services.entity.Entity;
import org.apereo.cas.services.AttributeMergingStrategy;

import java.util.Set;

/**
 * 用户身份验证主体属性仓库
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public interface PrincipalAttributesRepository extends Entity {

	/**
	 * 用户身份验证主体属性仓库抽象类
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	abstract class AbstractPrincipalAttributesRepository implements PrincipalAttributesRepository {

		private final static long serialVersionUID = -919901110966922094L;

		/**
		 * 属性合并策略
		 */
		private AttributeMergingStrategy mergingStrategy = AttributeMergingStrategy.MULTIVALUED;

		/**
		 * The attribute repository ids.
		 */
		private Set<String> attributeRepositoryIds;

		private boolean ignoreResolvedAttributes;

		/**
		 * 返回属性合并策略
		 *
		 * @return 属性合并策略
		 */
		public AttributeMergingStrategy getMergingStrategy() {
			return this.mergingStrategy;
		}

		/**
		 * 设置属性合并策略
		 *
		 * @param mergingStrategy
		 * 		属性合并策略
		 */
		public void setMergingStrategy(final AttributeMergingStrategy mergingStrategy) {
			this.mergingStrategy = mergingStrategy;
		}

		/**
		 * Return attribute repository ids that should be used to fetch attributes.
		 * An empty collection indicates that all sources available and defined should be used.
		 *
		 * @return The attribute repository ids.
		 */
		public Set<String> getAttributeRepositoryIds() {
			return this.attributeRepositoryIds;
		}

		/**
		 * Sets attribute repository ids that should be used to fetch attributes.
		 *
		 * @param attributeRepositoryIds
		 * 		The attribute repository ids.
		 */
		public void setAttributeRepositoryIds(final Set<String> attributeRepositoryIds) {
			this.attributeRepositoryIds = attributeRepositoryIds;
		}

		public boolean isIgnoreResolvedAttributes() {
			return this.ignoreResolvedAttributes;
		}

		public void setIgnoreResolvedAttributes(final boolean ignoreResolvedAttributes) {
			this.ignoreResolvedAttributes = ignoreResolvedAttributes;
		}

		@Override
		public String toString() {
			return StringBuilder.getInstance(this)
					.add("mergingStrategy", mergingStrategy)
					.add("attributeRepositoryIds", attributeRepositoryIds)
					.add("ignoreResolvedAttributes", ignoreResolvedAttributes)
					.asString();
		}

	}

}
