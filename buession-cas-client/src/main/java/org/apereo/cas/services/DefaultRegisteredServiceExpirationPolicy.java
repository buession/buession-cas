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

/**
 * This is {@link DefaultRegisteredServiceExpirationPolicy}.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class DefaultRegisteredServiceExpirationPolicy implements ExpirationPolicy {

	private final static long serialVersionUID = 6262218909087283352L;

	/**
	 * Is whether service should be deleted from the registry if and when expired.
	 */
	private boolean deleteWhenExpired;

	/**
	 * Is notify service owners and contacts when this service is marked as expired and is about to be deleted.
	 */
	private boolean notifyWhenDeleted;

	/**
	 * Iss expiration date that indicates when this may be expired.
	 */
	private boolean notifyWhenExpired;

	/**
	 * The expiration date.
	 */
	private String expirationDate;

	/**
	 * Return whether service should be deleted from the registry if and when expired.
	 *
	 * @return true/false
	 */
	public boolean isDeleteWhenExpired(){
		return deleteWhenExpired;
	}

	/**
	 * Sets whether service should be deleted from the registry if and when expired.
	 *
	 * @param deleteWhenExpired
	 * 		true/false
	 */
	public void setDeleteWhenExpired(boolean deleteWhenExpired){
		this.deleteWhenExpired = deleteWhenExpired;
	}

	/**
	 * Return notify service owners and contacts when this service is marked as expired and is about to be deleted.
	 *
	 * @return true/false
	 */
	public boolean isNotifyWhenDeleted(){
		return notifyWhenDeleted;
	}

	/**
	 * Sets notify service owners and contacts when this service is marked as expired and is about to be deleted.
	 *
	 * @param notifyWhenDeleted
	 * 		true/false
	 */
	public void setNotifyWhenDeleted(boolean notifyWhenDeleted){
		this.notifyWhenDeleted = notifyWhenDeleted;
	}

	/**
	 * Return notify service owners and contacts when this service is marked as expired.
	 *
	 * @return true/false
	 */
	public boolean isNotifyWhenExpired(){
		return notifyWhenExpired;
	}

	/**
	 * Sets notify service owners and contacts when this service is marked as expired.
	 *
	 * @param notifyWhenExpired
	 * 		true/false
	 */
	public void setNotifyWhenExpired(boolean notifyWhenExpired){
		this.notifyWhenExpired = notifyWhenExpired;
	}

	/**
	 * Return expiration date that indicates when this may be expired.
	 *
	 * @return The expiration date.
	 */
	public String getExpirationDate(){
		return expirationDate;
	}

	/**
	 * Sets expiration date that indicates when this may be expired.
	 *
	 * @param expirationDate
	 * 		The expiration date.
	 */
	public void setExpirationDate(String expirationDate){
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString(){
		return StringBuilder.getInstance(this)
				.add("deleteWhenExpired", deleteWhenExpired)
				.add("notifyWhenDeleted", notifyWhenDeleted)
				.add("notifyWhenExpired", notifyWhenExpired)
				.add("expirationDate", expirationDate)
				.asString();
	}

}
