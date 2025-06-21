/* Copyright (C) 2004 - 2008  db4objects Inc.  http://www.db4o.com

This file is part of the db4o open source object database.

db4o is free software; you can redistribute it and/or modify it under
the terms of version 2 of the GNU General Public License as published
by the Free Software Foundation and as clarified by db4objects' GPL 
interpretation policy, available at
http://www.db4o.com/about/company/legalpolicies/gplinterpretation/
Alternatively you can write to db4objects, Inc., 1900 S Norfolk Street,
Suite 350, San Mateo, CA 94403, USA.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. */
package com.db4o.db4ounit.jre11.assorted;

import com.db4o.foundation.*;
import com.db4o.internal.*;

import db4ounit.*;


public class NanoTimeTestCase implements TestCase {

	public void testCorrectExceptionThrownOnJdkLowerThan5() {
		if (jdkVer() >= 5) {
			return;
		}
		Assert.expect(NotImplementedException.class, new CodeBlock() {
			public void run() {
				Platform4.jdk().nanoTime();
			}
		});
	}
	
	public void testNanoTimeAvailableOnJdk5Plus() {
		if (jdkVer() < 5) {
			return;
		}
		try {
			Platform4.jdk().nanoTime();
		} catch (Exception e) {
			Assert.fail("nanoTime should be available on JDK 5 and higher.", e);
		}
	}
	
	private int jdkVer() {
		return Platform4.jdk().ver();
	}
	
}
