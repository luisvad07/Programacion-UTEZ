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

import com.db4o.internal.Transaction;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.fixtures.OptOutCS;

/**
 * @exclude
 */
public class StringIndexTestCase extends StringIndexTestCaseBase implements OptOutCS {
	
	public static void main(String[] args) {
		new StringIndexTestCase().runSolo();
	}
    
    public void testNotEquals() {
    	add("foo");
    	add("bar");
    	add("baz");
    	add(null);
    	
    	final Query query = newQuery(Item.class);
    	query.descend("name").constrain("bar").not();
		assertItems(new String[] { "foo", "baz", null }, query.execute());
    }

	public void testCancelRemovalRollback() throws Exception {
    	
    	prepareCancelRemoval(trans(), "original");
    	rename("original", "updated");
    	db().rollback();
    	grafittiFreeSpace();
    	reopen();
    	
    	assertExists("original");
    }
    
    public void testCancelRemovalRollbackForMultipleTransactions() throws Exception {
    	final Transaction trans1 = newTransaction();
    	final Transaction trans2 = newTransaction();
        
        prepareCancelRemoval(trans1, "original");
        assertExists(trans2, "original");
    	
        trans1.rollback();
        assertExists(trans2, "original");
        
        add(trans2, "second");
        assertExists(trans2, "original");
        
        trans2.commit();
        assertExists(trans2, "original");
        
    	grafittiFreeSpace();
        reopen();
        assertExists("original");
    }
    
    public void testCancelRemoval() throws Exception {
    	prepareCancelRemoval(trans(), "original");
    	db().commit();
    	grafittiFreeSpace();
    	reopen();
    	
    	assertExists("original");
    }

	private void prepareCancelRemoval(Transaction transaction, String itemName) {
		add(itemName);    	
    	db().commit();
    	
    	rename(transaction, itemName, "updated");    	
    	assertExists(transaction, "updated");
    	
    	rename(transaction, "updated", itemName);
    	assertExists(transaction, itemName);
	}
    
    public void testCancelRemovalForMultipleTransactions() throws Exception {
    	final Transaction trans1 = newTransaction();
    	final Transaction trans2 = newTransaction();
    	
    	prepareCancelRemoval(trans1, "original");
    	rename(trans2, "original", "updated");    	
    	trans1.commit();
    	grafittiFreeSpace();
    	reopen();
    	
    	assertExists("original");
    }
    
    public void testDeletingAndReaddingMember() throws Exception{
		add("original");
    	assertExists("original");
        rename("original", "updated");        
        assertExists("updated");
        Assert.isNull(query("original"));
        reopen();        
        assertExists("updated");
        Assert.isNull(query("original"));
    }
}
