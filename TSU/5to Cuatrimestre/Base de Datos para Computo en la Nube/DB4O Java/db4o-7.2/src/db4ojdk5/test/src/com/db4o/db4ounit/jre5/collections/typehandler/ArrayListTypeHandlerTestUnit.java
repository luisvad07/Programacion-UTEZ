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
package com.db4o.db4ounit.jre5.collections.typehandler;

import java.util.*;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.query.*;
import com.db4o.typehandlers.*;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;


@SuppressWarnings("unchecked")
public class ArrayListTypeHandlerTestUnit extends AbstractDb4oTestCase implements OptOutDefragSolo {
	
    public static class Item {
        public ArrayList list;
    }
    
    protected void configure(Configuration config) throws Exception {
        config.registerTypeHandler(
            new SingleClassTypeHandlerPredicate(ArrayList.class), 
            new ArrayListTypeHandler());
    }
    
	protected void store() throws Exception {
        Item item = new Item();
        item.list = new ArrayList();
        for (int eltIdx = 0; eltIdx < elements().length; eltIdx++) {
			item.list.add(elements()[eltIdx]);
		}
        item.list.add(null);
        store(item);
    }

    public void testRetrieveInstance(){
        Item item = (Item) retrieveOnlyInstance(Item.class);
        db().activate(item, Integer.MAX_VALUE);
        assertListContent(item);
    }
    
    public void testSuccessfulQuery() throws Exception {
    	assertQuery(true, elements()[0]);
	}

    public void testFailingQuery() throws Exception {
    	assertQuery(false, notContained());
	}

	private void assertQuery(boolean successful, Object element) {
		Query q = newQuery(Item.class);
		q.descend("list").constrain(element);
		assertQueryResult(q, successful);
	}

	public void testCompareItems() throws Exception {
    	assertCompareItems(elements()[0], true);
    }

	public void testFailingCompareItems() throws Exception {
    	assertCompareItems(notContained(), false);
    }

	private void assertCompareItems(Object element, boolean successful) {
		Query q = newQuery();
    	Item item = new Item();
    	item.list = new ArrayList();
		item.list.add(element);
    	q.constrain(item);
		assertQueryResult(q, successful);
	}

	public void _testContainsQuery() throws Exception {
    	Query q = newQuery(Item.class);
    	q.descend("list").constrain("e").endsWith(false);
    	assertSuccessfulQueryResult(q);
	}
	
	public void _testFailingContainsQuery() throws Exception {
    	Query q = newQuery(Item.class);
    	q.descend("list").constrain("g").endsWith(false);
    	assertEmptyQueryResult(q);
	}
	
	private void assertQueryResult(Query q, boolean successful) {
		if(successful) {
			assertSuccessfulQueryResult(q);
		}
		else {
			assertEmptyQueryResult(q);
		}
	}
	
	private void assertEmptyQueryResult(Query q) {
		ObjectSet set = q.execute();
		Assert.isTrue(set.isEmpty());
	}

	private void assertSuccessfulQueryResult(Query q) {
		ObjectSet set = q.execute();
    	Assert.areEqual(1, set.size());
    	Item item = (Item)set.next();
        assertListContent(item);
	}
	
	private void assertListContent(Item item) {
		Assert.areEqual(elements().length + 1, item.list.size());
		for (int eltIdx = 0; eltIdx < elements().length; eltIdx++) {
	        Assert.areEqual(elements()[eltIdx], item.list.get(eltIdx));
		}
		Assert.isNull(item.list.get(elements().length));
	}

	private Object[] elements() {
		return elementsSpec()._elements;
	}

	private ArrayListHandlerTestElementsSpec elementsSpec() {
		return (ArrayListHandlerTestElementsSpec) ArrayListHandlerTestVariables.ELEMENTS_SPEC.value();
	}    

	private Object notContained() {
		return elementsSpec()._notContained;
	}

}
