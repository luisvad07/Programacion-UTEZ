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
package com.db4o.db4ounit.common.btree;

public class BTreeAsSetTestCase extends BTreeTestCaseBase {
	
	/**
	 * For now this won't work completely easy.
	 * If multiple transactions add the same value, there may
	 * be multiple add patches in the BTree with the same value.
	 * 
	 * There could be many of these patches and they could even
	 * be on different nodes, so we may be on the wrong node
	 * when we want to check. 
	 * 
	 * We will have to take a look at this again for unique field
	 * values, so the test can stay here.
	 * 
	 */
	public void _testAddSameValueFromSameTransaction() {
		add(42);
		add(42);
		assertSingleElement(42);
	}

}
