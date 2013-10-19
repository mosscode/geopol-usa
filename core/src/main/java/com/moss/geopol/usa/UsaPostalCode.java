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

import javax.xml.bind.annotation.XmlElement;

public final class UsaPostalCode implements Serializable {
	
	@XmlElement
	private String code;
	
	@XmlElement
	private String plusFour;
	
	UsaPostalCode() {}
	
	public UsaPostalCode(String code) throws StInvalidPostalCodeException {
		parseCode(code);
	}
	
	public UsaPostalCode(String code, String plusFour) throws StInvalidPostalCodeException {
		if (plusFour == null) {
			parseCode(code);
		}
		else {
			parseCode(code + '-' + plusFour);
		}
	}
	
	public UsaPostalCode(UsaPostalCode other) {
		this.code = other.code;
		this.plusFour = other.plusFour;
	}
	
	public String toString() {
		if (plusFour == null) {
			return code;
		}
		else {
			return code + "-" + plusFour;	
		}
	}
	
	public int hashCode() {
		return toString().hashCode();
	}

	public boolean equals(Object o) {
		return
			o != null
			&&
			o instanceof UsaPostalCode
			&&
			((UsaPostalCode)o).toString().equals(toString());
	}
	
	public String code() {
		return code;
	}
	
	public String plusFour() {
		return plusFour;
	}
	
	public UsaPostalCode copy() {
		return new UsaPostalCode(this);
	}
	
	private void parseCode(String code) throws StInvalidPostalCodeException {
		
		if(code == null) throw new StInvalidPostalCodeException("Postal Code cannot be parsed");
		
		String codeToParse = null;
		String plusFourToParse = null;
		String parseChar = "-";
		
		if(code.length() > 5) {
			if(code.charAt(5) == ' ') {
				parseChar = " ";
			}
		}
		
		String[] hyphonParsedCode = code.split(parseChar);
		if(hyphonParsedCode.length > 1) {
			if(hyphonParsedCode.length > 2) throw new StInvalidPostalCodeException("Postal Code cannot be parsed");
			else {
				codeToParse = hyphonParsedCode[0];
				plusFourToParse = hyphonParsedCode[1];
			}
		} else {
			
			if(code.length() > 5) {
				if(code.length() != 9) throw new StInvalidPostalCodeException("Postal Code cannot be parsed");
				
				codeToParse = code.substring(0,5);
				plusFourToParse = code.substring(5);
				
				try {
					Integer.parseInt(codeToParse.trim());
				} catch(NumberFormatException ex) {
					throw new StInvalidPostalCodeException("Postal Code must only contain numerical data.");
				}
				this.code = code;
				
				try {
					plusFourToParse = plusFourToParse.trim();
					Integer.parseInt(plusFourToParse);
					this.plusFour = plusFourToParse;
				} catch(NumberFormatException ex) {
					throw new StInvalidPostalCodeException("Plus Four Code must only contain numerical data.");
				}
				return;
			}
			
			if(code.length() != 5) throw new StInvalidPostalCodeException("Postal Code must be 5 digits");
			try {
				Integer.parseInt(code.trim());
			} catch(NumberFormatException ex) {
				throw new StInvalidPostalCodeException("Postal Code must only contain numerical data.");
			}
			this.code = code;
			return;
		}
		
		if(codeToParse.length() != 5) throw new StInvalidPostalCodeException("Postal Code must be 5 digits");
		else {
			try {
				Integer.parseInt(codeToParse.trim());
			} catch(NumberFormatException ex) {
				throw new StInvalidPostalCodeException("Postal Code must only contain numerical data.");
			}
			this.code = codeToParse;
		}
		
		if(plusFourToParse.length() != 4) throw new StInvalidPostalCodeException("Plus Four must be 4 digits");
		else {
			try {
				plusFourToParse = plusFourToParse.trim();
				Integer.parseInt(plusFourToParse);
				this.plusFour = plusFourToParse;
			} catch(NumberFormatException ex) {
				throw new StInvalidPostalCodeException("Plus Four must only contain numerical data.");
			}
		}
	}
}
