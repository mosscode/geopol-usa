/**
 * Copyright (C) 2013, Moss Computing Inc.
 *
 * This file is part of geopol-usa.
 *
 * geopol-usa is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * geopol-usa is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with geopol-usa; see the file COPYING.  If not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library.  Thus, the terms and
 * conditions of the GNU General Public License cover the whole
 * combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent
 * modules, and to copy and distribute the resulting executable under
 * terms of your choice, provided that you also meet, for each linked
 * independent module, the terms and conditions of the license of that
 * module.  An independent module is a module which is not derived from
 * or based on this library.  If you modify this library, you may extend
 * this exception to your version of the library, but you are not
 * obligated to do so.  If you do not wish to do so, delete this
 * exception statement from your version.
 */
package com.moss.geopol.usa.jpa;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;

import com.moss.geopol.usa.StInvalidPostalCodeException;
import com.moss.geopol.usa.UsaAddress;
import com.moss.geopol.usa.UsaCity;
import com.moss.geopol.usa.UsaPostalCode;
import com.moss.geopol.usa.UsaState;

@Embeddable
public class USAddress {
	
	@Field
	@XmlElement
	private String addressLine1;
	
	@Field
	@XmlElement
	private String addressLine2;
	
	@Embedded
	@IndexedEmbedded
	@XmlElement
	private USCity city;
	
	@Embedded
	@IndexedEmbedded
	@XmlElement
	private USPostalCode postalCode;
	
	@Embedded
	@IndexedEmbedded
	@XmlElement
	private USPostalCodePlus4 postalCodePlus4;
	
	public USAddress(){}
	
	public USAddress(USAddress address){
		this.addressLine1 = address.addressLine1;
		this.addressLine2 = address.addressLine2;
		this.city = address.city;		
		this.postalCode = address.postalCode;
		this.postalCodePlus4 = address.postalCodePlus4;
	}
	
	public USAddress(String line1, USCity city, USPostalCode postalCode){
		this.addressLine1 = line1;
		this.city = city;
		this.postalCode = postalCode;
	}
	
	public USAddress(String addressLine1, String addressLine2, USCity city, USPostalCode postalCode) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.postalCode = postalCode;
	}
	
	public USAddress(String addressLine1, String addressLine2, USCity city, USPostalCode postalCode, USPostalCodePlus4 plus4) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.postalCode = postalCode;
		this.postalCodePlus4 = plus4;
	}
	public USAddress(UsaAddress dto) {
		
		setAddressLine1(dto.getAddressLine1());
		setAddressLine2(dto.getAddressLine2());
		
		UsaPostalCode dtoCode = dto.getPostalCode();
		
		if (dtoCode != null) {
			
			if (dtoCode.code() != null) {
				USPostalCode usPostalCode = new USPostalCode(dtoCode);
				setPostalCode(usPostalCode);
			}
			
			if (dtoCode.plusFour() != null) {
				USPostalCodePlus4 plus4 = new USPostalCodePlus4(dtoCode.plusFour());
				setPostalCodePlus4(plus4);
			}
		}
		
		USCity city = new USCity();
		if(dto.getCity() != null) city.setCityName(dto.getCity().getName());
		if(dto.getState() != null) city.setStateCode(dto.getState().getAbbreviation());
		setCity(city);
	}
	public String getCityStateZip() {
		String s = city.getName() + " " + city.getStateCode() + " " + postalCode;
		if (postalCodePlus4 != null)  s += "-" + postalCodePlus4;
		return s;
	}

	public UsaAddress toDto() throws RuntimeException {
		UsaPostalCode postalCode = null;
		try {
			if (getPostalCode() != null && getPostalCode().getCode() != null) {
				postalCode = new UsaPostalCode(getPostalCode().getCode());
			}
		} catch(StInvalidPostalCodeException ex) {
			throw new RuntimeException("Error parsing Postal Code");
		}
		
		UsaCity dtoCity = null;
		if (city != null) {
			dtoCity = city.toDto();
		}
		
		UsaState state = null;
		if (city != null) {
			state = UsaState.getByAbbreviation(city.toStateAbbreviationDto().getAbbreviation());
		}
				
		return new UsaAddress(
				getAddressLine1(), 
				getAddressLine2(),
				dtoCity,
				state,
				postalCode);
		
	}
	
	@XmlTransient
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	@XmlTransient
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	@XmlTransient
	public USPostalCode getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(USPostalCode postalCode) {
		this.postalCode = postalCode;
	}
	
	@XmlTransient
	public String getAddressBlock(){
		String addressBlock = addressLine1;
		if(addressLine2 != null) addressBlock += "\n" + addressLine2;
		addressBlock += "\n" + 
		city.getName() + ", " + city.getStateCode() + " " + getPostalCode().getCode();
		return addressBlock;
	}
	
	@XmlTransient
	public String getAddressBlockTwoLines(){
		String addressBlock = getBothAddressLinesOnOneLine() + "\n" + 
			city.getName() + ", " + city.getStateCode() + " " + getPostalCode().getCode();
		return addressBlock;
	}
	
	@XmlTransient
	public String getBothAddressLinesOnOneLine(){
		String addressBlock = "";
		if(addressLine1 != null) addressBlock += addressLine1;
		if(addressLine2 != null) addressBlock += " " + addressLine2;
		return addressBlock;
	}
	
	@XmlTransient
	public String getAddressBlockOnOneLine(){
		String addressBlock = getBothAddressLinesOnOneLine();
		addressBlock += " " + 
		city.getName() + ", " + city.getStateCode() + " " + getPostalCode().getCode();
		return addressBlock;
	}
	
	@Override
	public String toString(){
		return getBothAddressLinesOnOneLine();
	}

	@XmlTransient
	public USPostalCodePlus4 getPostalCodePlus4() {
		return postalCodePlus4;
	}

	public void setPostalCodePlus4(USPostalCodePlus4 postalCodePlus4) {
		this.postalCodePlus4 = postalCodePlus4;
	}

	@XmlTransient
	public USCity getCity() {
		return city;
	}

	public void setCity(USCity city) {
		this.city = city;
	}
}
