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

import com.db4o.db4ounit.common.foundation.IntArrays4;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.btree.*;
import com.db4o.internal.handlers.IntHandler;

import db4ounit.Assert;

public class BTreeAssert {

	public static ExpectingVisitor createExpectingVisitor(int value, int count) {
	    int[] values = new int[count];
	    for (int i = 0; i < values.length; i++) {
	        values[i] = value;
	    }
	    return new ExpectingVisitor(IntArrays4.toObjectArray(values));
	}

	public static ExpectingVisitor createExpectingVisitor(int[] keys) {
		return new ExpectingVisitor(IntArrays4.toObjectArray(keys));
	}
	
	private static ExpectingVisitor createSortedExpectingVisitor(int[] keys) {
		return new ExpectingVisitor(IntArrays4.toObjectArray(keys), true, false);
	}

	public static void traverseKeys(BTreeRange result, Visitor4 visitor) {
		final Iterator4 i = result.keys();
		while (i.moveNext()) {
			visitor.visit(i.current());
		}
	}

	public static void assertKeys(final Transaction transaction, BTree btree, final int[] keys) {
		final ExpectingVisitor visitor = createExpectingVisitor(keys);
		btree.traverseKeys(transaction, visitor);
		visitor.assertExpectations();
	}

	public static void assertEmpty(Transaction transaction, BTree tree) {
	    final ExpectingVisitor visitor = new ExpectingVisitor(new Object[0]);
	    tree.traverseKeys(transaction, visitor);
		visitor.assertExpectations();
	    Assert.areEqual(0, tree.size(transaction));
	}

	public static void dumpKeys(Transaction trans, BTree tree) {
		tree.traverseKeys(trans, new Visitor4() {
			public void visit(Object obj) {
				System.out.println(obj);
			}
		});
	}

	public static ExpectingVisitor createExpectingVisitor(final int expectedID) {
		return createExpectingVisitor(expectedID, 1);
	}

	public static int fillSize(BTree btree) {
		return btree.nodeSize()+1;
	}

	public static int[] newBTreeNodeSizedArray(final BTree btree, int value) {
		return IntArrays4.fill(new int[fillSize(btree)], value);
	}

	public static void assertRange(int[] expectedKeys, BTreeRange range) {
		Assert.isNotNull(range);
		final ExpectingVisitor visitor = createSortedExpectingVisitor(expectedKeys);
		
		traverseKeys(range, visitor);
		visitor.assertExpectations();
	}

	public static BTree createIntKeyBTree(final ObjectContainerBase stream, int id, int nodeSize) {
		return new BTree(stream.systemTransaction(), id, new IntHandler(), nodeSize, stream.configImpl().bTreeCacheHeight());
	}
	
	public static BTree createIntKeyBTree(final ObjectContainerBase stream, int id, int treeCacheHeight, int nodeSize) {
		return new BTree(stream.systemTransaction(), id, new IntHandler(), nodeSize, treeCacheHeight);
	}

	public static void assertSingleElement(Transaction trans, BTree btree, Object element) {
		Assert.areEqual(1, btree.size(trans));
		
		final BTreeRange result = btree.search(trans, element);
		ExpectingVisitor expectingVisitor = new ExpectingVisitor(new Object[] { element });
		BTreeAssert.traverseKeys(result, expectingVisitor);
		expectingVisitor.assertExpectations();
		
		expectingVisitor = new ExpectingVisitor(new Object[] { element });
		btree.traverseKeys(trans, expectingVisitor);
		expectingVisitor.assertExpectations();
	}

}
