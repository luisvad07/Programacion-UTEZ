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
package com.db4o.db4ounit.common.ta.ta;

import com.db4o.activation.*;
import com.db4o.db4ounit.common.ta.*;
import com.db4o.query.*;

import db4ounit.*;

public class TAActivateTestCase extends TAItemTestCaseBase {

    public static void main(String[] args) {
        new TAActivateTestCase().runAll();
    }

    private final int ITEM_DEPTH = 10;

    protected void assertItemValue(Object obj) throws Exception {
        TAItem taItem = (TAItem) obj;
        for (int i = 0; i < ITEM_DEPTH - 1; i++) {
            Assert.areEqual("TAItem " + (ITEM_DEPTH - i), taItem.getName());
            Assert.areEqual(ITEM_DEPTH - i, taItem.getValue());
            Assert.isNotNull(taItem.next());
            taItem = taItem.next();
        }
        Assert.areEqual("TAItem 1", taItem.getName());
        Assert.areEqual(1, taItem.getValue());
        Assert.isNull(taItem.next());
    }

    protected void assertRetrievedItem(Object obj) throws Exception {
        TAItem taItem = (TAItem) obj;
        assertNullItem(taItem);

        // depth = 0, no effect
        db().activate(taItem, 0);
        assertNullItem(taItem);

        // depth = 1
        db().activate(taItem, 1);
        assertActivatedItem(taItem, 0, 1);

        // depth = 5
        db().activate(taItem, 5);
        assertActivatedItem(taItem, 0, 5);

        db().activate(taItem, ITEM_DEPTH + 100);
        assertActivatedItem(taItem, 0, ITEM_DEPTH);
    }

    private void assertActivatedItem(TAItem item, int from, int depth) {
        if (depth > ITEM_DEPTH) {
            throw new IllegalArgumentException(
                    "depth should not be greater than ITEM_DEPTH.");
        }

        TAItem next = item;
        for (int i = from; i < depth; i++) {
            Assert.areEqual("TAItem " + (ITEM_DEPTH - i), next._name);
            Assert.areEqual(ITEM_DEPTH - i, next._value);
            if (i < ITEM_DEPTH - 1) {
                Assert.isNotNull(next._next);
            }
            next = next._next;
        }

        if (depth < ITEM_DEPTH) {
            assertNullItem(next);
        }

    }

    private void assertNullItem(TAItem taItem) {
        Assert.isNull(taItem._name);
        Assert.isNull(taItem._next);
        Assert.areEqual(0, taItem._value);
    }

    public Object retrieveOnlyInstance(Class clazz) {
        Query q = db().query();
        q.constrain(clazz);
        q.descend("_isRoot").constrain(new Boolean(true));
        return q.execute().next();
    }

    protected Object createItem() throws Exception {
        TAItem taItem = TAItem.newTAItem(ITEM_DEPTH);
        taItem._isRoot = true;
        return taItem;
    }

    public static class TAItem extends ActivatableImpl {

        public String _name;

        public int _value;

        public TAItem _next;

        public boolean _isRoot;

        public static TAItem newTAItem(int depth) {
            if (depth == 0) {
                return null;
            }
            TAItem root = new TAItem();
            root._name = "TAItem " + depth;
            root._value = depth;
            root._next = newTAItem(depth - 1);
            return root;
        }

        public String getName() {
            activate(ActivationPurpose.READ);
            return _name;
        }

        public int getValue() {
            activate(ActivationPurpose.READ);
            return _value;
        }

        public TAItem next() {
            activate(ActivationPurpose.READ);
            return _next;
        }
    }
}
