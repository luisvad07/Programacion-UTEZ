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
package com.db4o.db4ounit.common.fieldindex;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.db4ounit.common.btree.*;
import com.db4o.db4ounit.common.foundation.*;
import com.db4o.ext.*;
import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.btree.*;
import com.db4o.query.*;

import db4ounit.*;


public class FieldIndexTestCase extends FieldIndexTestCaseBase {
	
	private static final int[] FOOS = new int[]{3,7,9,4};
    
    public static void main(String[] arguments) {
        new FieldIndexTestCase().runSolo();
    }
    
    protected void configure(Configuration config) {
    	super.configure(config);
    }
    
	protected void store() {
		storeItems(FOOS);
	}
    
    public void testTraverseValues(){
        StoredField field = yapField();
        ExpectingVisitor expectingVisitor = new ExpectingVisitor(IntArrays4.toObjectArray(FOOS));
        field.traverseValues(expectingVisitor);
        expectingVisitor.assertExpectations();
    }
    
    public void testAllThere() throws Exception{
        for (int i = 0; i < FOOS.length; i++) {
            Query q = createQuery(FOOS[i]);
            ObjectSet objectSet = q.execute();
            Assert.areEqual(1, objectSet.size());
            FieldIndexItem fii = (FieldIndexItem) objectSet.next();
            Assert.areEqual(FOOS[i], fii.foo);
        }
    }

	public void testAccessingBTree() throws Exception{
        BTree bTree = yapField().getIndex(trans());
        Assert.isNotNull(bTree);
        expectKeysSearch(bTree, FOOS);
    }

    private void expectKeysSearch(BTree btree, int[] values) {
        int lastValue = Integer.MIN_VALUE;
        for (int i = 0; i < values.length; i++) {
            if(values[i] != lastValue){
                final ExpectingVisitor expectingVisitor = BTreeAssert.createExpectingVisitor(values[i], IntArrays4.occurences(values, values[i]));
                BTreeRange range = fieldIndexKeySearch(trans(), btree, new Integer(values[i]));
                BTreeAssert.traverseKeys(range, new Visitor4() {
                    public void visit(Object obj) {
                        FieldIndexKey fik = (FieldIndexKey)obj;
                        expectingVisitor.visit(fik.value());
                    }
                });
                expectingVisitor.assertExpectations();
                lastValue = values[i];
            }
        }
    }
    
    private FieldIndexKey fieldIndexKey(int integerPart, Object composite){
        return new FieldIndexKey(integerPart, composite);
    }
    
    private BTreeRange fieldIndexKeySearch(Transaction trans, BTree btree, Object key) {
        // SearchTarget should not make a difference, HIGHEST is faster
        BTreeNodeSearchResult start = btree.searchLeaf(trans, fieldIndexKey(0, key), SearchTarget.LOWEST);
        BTreeNodeSearchResult end = btree.searchLeaf(trans, fieldIndexKey(Integer.MAX_VALUE, key), SearchTarget.LOWEST);
        return start.createIncludingRange(end);
    }
    
    private FieldMetadata yapField() {
        return classMetadataFor(FieldIndexItem.class).fieldMetadataForName("foo");
    }
    

}
