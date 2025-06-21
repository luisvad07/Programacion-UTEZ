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
package com.db4o.db4ounit.util.test;

import com.db4o.db4ounit.util.*;

import db4ounit.*;

public class PermutingTestConfigTestCase implements TestCase {

	public void testPermutation() {
		Object[][] data= new Object[][] {
				new Object[] {"A","B"},
				new Object[] {"X","Y","Z"},
		};
		final PermutingTestConfig config=new PermutingTestConfig(data);
		Object[][] expected= new Object[][] {
				new Object[] {"A","X"},	
				new Object[] {"A","Y"},	
				new Object[] {"A","Z"},	
				new Object[] {"B","X"},	
				new Object[] {"B","Y"},	
				new Object[] {"B","Z"},	
		};
		for (int groupIdx = 0; groupIdx < expected.length; groupIdx++) {
			Assert.isTrue(config.moveNext());
			Object[] current={config.current(0),config.current(1)};
			ArrayAssert.areEqual(expected[groupIdx],current);
		}
		Assert.isFalse(config.moveNext());
	}
	
}
