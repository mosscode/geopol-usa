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
package com.moss.geopol.usa;

import java.io.Serializable;

/**
 * Represents a street address in the USA (though it may be usable for other countries as well),
 */
public final class UsaAddress implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String addressLine1;
	private String addressLine2;
	private UsaCity city;
	private UsaState state;
	private UsaPostalCode postalCode;
	
	public UsaAddress() {}
	
	public UsaAddress(UsaAddress other) {
		this.addressLine1 = other.addressLine1;
		this.addressLine2 = other.addressLine2;
		this.city = other.getCity().copy();
		this.state = other.state;
		this.postalCode = other.postalCode.copy();
	}
	public UsaAddress(String addressLine1, String addressLine2, UsaCity city, UsaState state, UsaPostalCode postalCode) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.postalCode = postalCode;
		this.city = city;
		this.state = state;
	}
	
	public UsaAddress copy() {
		return new UsaAddress(this);
	}
	
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public UsaPostalCode getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(UsaPostalCode postalCode) {
		this.postalCode = postalCode;
	}
	
	public String toString(){
		StringBuffer text = new StringBuffer();
		text.append(addressLine1);
		text.append("\n");
		text.append(addressLine2);
		text.append(",");
		
		if(postalCode!=null){
			text.append(postalCode.code());
		}
		return text.toString();
	
	}
	
	public String getOneLine(){
		StringBuffer text = new StringBuffer();
		if(addressLine1 != null) {
			text.append(addressLine1);
			text.append("\n");
		} 
		
		if(addressLine2 != null) {
			text.append(addressLine2);
			text.append(", ");
		}
		
		if(city != null) {
			text.append(city);
			text.append(", ");
		}
		
		if(state != null) {
			text.append(state.getAbbreviation());
			text.append(" ");
		}
		
		if(postalCode!=null){
			text.append(postalCode.code());
		}
		return text.toString();
	
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((addressLine1 == null) ? 0 : addressLine1.hashCode());
		result = PRIME * result + ((addressLine2 == null) ? 0 : addressLine2.hashCode());
		result = PRIME * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UsaAddress other = (UsaAddress) obj;
		if (addressLine1 == null) {
			if (other.addressLine1 != null)
				return false;
		} else if (!addressLine1.equals(other.addressLine1))
			return false;
		if (addressLine2 == null) {
			if (other.addressLine2 != null)
				return false;
		} else if (!addressLine2.equals(other.addressLine2))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		return true;
	}

	public UsaCity getCity() {
		return city;
	}

	public void setCity(UsaCity city) {
		this.city = city;
	}

	public UsaState getState() {
		return state;
	}

	public void setState(UsaState state) {
		this.state = state;
	}
}
