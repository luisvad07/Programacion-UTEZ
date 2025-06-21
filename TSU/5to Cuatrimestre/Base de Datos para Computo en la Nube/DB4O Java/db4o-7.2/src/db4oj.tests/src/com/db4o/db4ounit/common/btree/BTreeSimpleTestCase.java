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

import com.db4o.internal.Transaction;
import com.db4o.internal.btree.BTree;

import db4ounit.extensions.AbstractDb4oTestCase;
import db4ounit.extensions.fixtures.*;

public class BTreeSimpleTestCase extends AbstractDb4oTestCase implements
		OptOutDefragSolo, OptOutCS {

	protected static final int BTREE_NODE_SIZE = 4;

	int[] _keys = { 3, 234, 55, 87, 2, 1, 101, 59, 70, 300, 288 };

	int[] _values;

	int[] _sortedKeys = { 1, 2, 3, 55, 59, 70, 87, 101, 234, 288, 300 };

	int[] _sortedValues;

	int[] _keysOnRemoval = { 1, 2, 55, 59, 70, 87, 234, 288, 300 };

	int[] _valuesOnRemoval;

	int[] _one = { 1 };

	int[] _none = {};

	public BTreeSimpleTestCase() {
		super();

		_values = new int[_keys.length];
		for (int i = 0; i < _keys.length; i++) {
			_values[i] = _keys[i];
		}

		_sortedValues = new int[_sortedKeys.length];
		for (int i = 0; i < _sortedKeys.length; i++) {
			_sortedValues[i] = _sortedKeys[i];
		}

		_valuesOnRemoval = new int[_keysOnRemoval.length];
		for (int i = 0; i < _keysOnRemoval.length; i++) {
			_valuesOnRemoval[i] = _keysOnRemoval[i];
		}
	}

	public void testIntKeys() throws Exception {
		BTree btree = BTreeAssert.createIntKeyBTree(container(), 0,
				BTREE_NODE_SIZE);
		for (int i = 0; i < 5; i++) {
			btree = cycleIntKeys(btree);
		}
	}

	private BTree cycleIntKeys(BTree btree) throws Exception {
		addKeys(btree);
		expectKeys(btree, _sortedKeys);

		btree.commit(trans());

		expectKeys(btree, _sortedKeys);

		removeKeys(btree);

		expectKeys(btree, _keysOnRemoval);

		btree.rollback(trans());

		expectKeys(btree, _sortedKeys);

		int id = btree.getID();

		reopen();

		btree = BTreeAssert.createIntKeyBTree(container(), id, BTREE_NODE_SIZE);

		expectKeys(btree, _sortedKeys);

		removeKeys(btree);

		expectKeys(btree, _keysOnRemoval);

		btree.commit(trans());

		expectKeys(btree, _keysOnRemoval);

		// remove all but 1
		for (int i = 1; i < _keysOnRemoval.length; i++) {
			btree.remove(trans(), new Integer(_keysOnRemoval[i]));
		}

		expectKeys(btree, _one);

		btree.commit(trans());

		expectKeys(btree, _one);

		btree.remove(trans(), new Integer(1));

		btree.rollback(trans());

		expectKeys(btree, _one);

		btree.remove(trans(), new Integer(1));

		btree.commit(trans());

		expectKeys(btree, _none);

		return btree;

	}
	
	private void removeKeys(BTree btree) {
		btree.remove(trans(), new Integer(3));
		btree.remove(trans(), new Integer(101));
	}

	private void addKeys(BTree btree) {
		Transaction trans = trans();
		for (int i = 0; i < _keys.length; i++) {
			btree.add(trans, new Integer(_keys[i]));
		}
	}

	private void expectKeys(BTree btree, final int[] keys) {
		BTreeAssert.assertKeys(trans(), btree, keys);
	}
}
