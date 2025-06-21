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
package com.db4o.db4ounit.common.ta.mixed;

import com.db4o.db4ounit.common.ta.*;

import db4ounit.*;

/**
 * @exclude
 */
public class NTNTestCase extends ItemTestCaseBase {
	
	public static void main(String[] args) {
		new NTNTestCase().runAll();
	}
	
	protected Object createItem() throws Exception {
		return new NTNItem(42);
	}

	protected void assertRetrievedItem(Object obj) throws Exception {
		NTNItem item = (NTNItem) obj;
		Assert.isNotNull(item.tnItem);
		Assert.isNull(item.tnItem.list);
	}
		
	protected void assertItemValue(Object obj) throws Exception {
		NTNItem item = (NTNItem) obj;
		Assert.areEqual(LinkedList.newList(42), item.tnItem.value());
	}
	
	public void testDeactivateDepth() throws Exception {
		NTNItem item = (NTNItem) retrieveOnlyInstance();
		TNItem tnItem = item.tnItem;
		tnItem.value();
		Assert.isNotNull(tnItem.list);
		// item.tnItem.list
		db().deactivate(item, 2);
		// FIXME: failure 
		// Assert.isNull(tnItem.list);
		
		db().activate(item, 42);
		db().deactivate(item, 10);
		Assert.isNull(tnItem.list);
	}

}
