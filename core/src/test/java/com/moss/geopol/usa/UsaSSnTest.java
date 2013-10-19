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

import com.moss.geopol.usa.UsaSSn;

import junit.framework.TestCase;


public class UsaSSnTest extends TestCase {
	
	public void testWithinOneChar() throws Exception {
		matchCase("123456789", "123456789");
		matchCase("123456789", "123456788");
		matchCase("123456789", "123456779");
		matchCase("123456789", "123456689");
		matchCase("123456789", "123455789");
		matchCase("123456789", "123446789");
		matchCase("123456789", "123356789");
		matchCase("123456789", "122456789");
		matchCase("123456789", "113456789");
		matchCase("123456789", "023456789");
		matchCase("000000001", "000000002");
		matchCase("100000000", "200000000");
		
		noMatchCase("123456789", "234567890");
		noMatchCase("123456789", "012345678");
		noMatchCase("111111111", "222222222");
		
	}
	
	private void matchCase(String a, String b) throws Exception {
		assertTrue("Didn't Catch closeness: " + a + ", " + b,UsaSSn.withinOneChar(new UsaSSn(a), new UsaSSn(b)));
		assertTrue("Didn't Catch closeness: " + a + ", " + b,UsaSSn.withinOneChar(new UsaSSn(b), new UsaSSn(a)));
	}
	private void noMatchCase(String a, String b) throws Exception {
		assertFalse("Invalid closeness: " + a + ", " + b, UsaSSn.withinOneChar(new UsaSSn(a), new UsaSSn(b)));
		assertFalse("Invalid closeness: " + a + ", " + b, UsaSSn.withinOneChar(new UsaSSn(b), new UsaSSn(a)));
	}
}
