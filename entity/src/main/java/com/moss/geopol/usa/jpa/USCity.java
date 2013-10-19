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

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.search.annotations.Field;

import com.moss.geopol.usa.UsaCity;
import com.moss.geopol.usa.UsaState;

@Embeddable
public class USCity implements Serializable {

	@Field
	private String cityName;
	
	@Field
	private String stateCode;
	
	@Field
	private String countryCode;
	
	public USCity() {}
	
	public USCity(String cityName, String stateCode, String countryCode) {
		this.cityName = cityName;
		this.stateCode = stateCode;
		this.countryCode = countryCode;
	}
	
	public UsaCity toDto(){
		try {
			return new UsaCity(cityName);
		}
		catch (com.moss.geopol.usa.UsaCity.E2CityNameParserException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public UsaState toStateAbbreviationDto() {
		
		UsaState state = UsaState.getByAbbreviation(stateCode);
		
		if (state == null) {
			throw new RuntimeException("Data format error: There was an error parsing data that should have already been validated: " + stateCode);
		}
		
		return state;
	}
	
	public String getName() {
		return cityName;
	}

	public void setName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
}
