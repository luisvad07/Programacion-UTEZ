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

import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.btree.*;

import db4ounit.*;


public class BTreeNodeTestCase extends BTreeTestCaseBase {
	
	public static void main(String[] args) {
		new BTreeNodeTestCase().runSolo();
	}

	private final int[] keys = new int[] {
			-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 7, 9
	};
	
	protected void db4oSetupAfterStore() throws Exception {
		super.db4oSetupAfterStore();
		add(keys);		
		commit();
	}
	
	public void testLastKeyIndex(){
		BTreeNode node = node(3);
		Assert.areEqual(1, node.lastKeyIndex(trans()));
		Transaction trans = newTransaction();
		_btree.add(trans, new Integer(5));
		Assert.areEqual(1, node.lastKeyIndex(trans()));
		_btree.commit(trans);
		Assert.areEqual(2, node.lastKeyIndex(trans()));
	}

	private BTreeNode node(final int value) {
		BTreeRange range = search(value);
		Iterator4 i = range.pointers();
		i.moveNext();
		BTreePointer firstPointer = (BTreePointer) i.current();
		BTreeNode node = firstPointer.node();
		node.debugLoadFully(systemTrans());
		return node;
	}
	
	public void testLastPointer(){
		BTreeNode node = node(3);
		BTreePointer lastPointer = node.lastPointer(trans());
		assertPointerKey(4, lastPointer);
	}
	


}
