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
package org.apereo.cas.services;

import java.util.List;

/**
 * This is {@link ChainingAttributeReleasePolicy}.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class ChainingAttributeReleasePolicy implements AttributeReleasePolicy {

	private List<AttributeReleasePolicy> policies;

	private String mergingPolicy;

	private int order;

	public List<AttributeReleasePolicy> getPolicies(){
		return policies;
	}

	public void setPolicies(List<AttributeReleasePolicy> policies){
		this.policies = policies;
	}

	public String getMergingPolicy(){
		return mergingPolicy;
	}

	public void setMergingPolicy(String mergingPolicy){
		this.mergingPolicy = mergingPolicy;
	}

	public int getOrder(){
		return order;
	}

	public void setOrder(int order){
		this.order = order;
	}

	@Override
	public String toString(){
		return StringBuilder.getInstance(this)
				.add("policies", policies)
				.add("mergingPolicy", mergingPolicy)
				.add("order", order)
				.asString();
	}

}
