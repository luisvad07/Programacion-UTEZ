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
package com.db4o.db4ounit.common.constraints;

import com.db4o.config.*;
import com.db4o.constraints.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;


public class UniqueFieldIndexTestCase extends AbstractDb4oTestCase{
	
	public static void main(String[] arguments) {
		new UniqueFieldIndexTestCase().runAll();
	}
	
	public static class Item {
		
		public String	_str;

		public Item(){
		}
		
		public Item(String str){
			_str = str;
		}
	}
	
	protected void configure(Configuration config) throws Exception {
		super.configure(config);
		indexField(config, Item.class, "_str");
		config.add(new UniqueFieldValueConstraint(Item.class, "_str"));
	}
	
	protected void store() throws Exception {
		addItem("1");
		addItem("2");
		addItem("3");
	}
	
	public void testNewViolates(){
		addItem("2");
		commitExpectingViolation();
	}	
	
	public void testUpdateViolates(){
		updateItem("2", "3");
		commitExpectingViolation();
	}
	
	public void testUpdateDoesNotViolate(){
		updateItem("2", "4");
		db().commit();
	}

	public void testUpdatingSameObjectDoesNotViolate() {
		updateItem("2", "2");
		db().commit();
	}
	
	public void testNewAfterDeleteDoesNotViolate() {
		deleteItem("2");
		addItem("2");
		db().commit();
	}
	
	public void testDeleteAfterNewDoesNotViolate() {
		Item existing = queryItem("2");
		addItem("2");
		db().delete(existing);
		db().commit();
	}

	private void deleteItem(String value) {
		db().delete(queryItem(value));
	}
	
	private void commitExpectingViolation() {
		Assert.expect(UniqueFieldValueConstraintViolationException.class, new CodeBlock() {
			public void run() throws Throwable {
				db().commit();
			}
		});
		db().rollback();
	}

	private Item queryItem(String str) {
		Query q = newQuery(Item.class);
		q.descend("_str").constrain(str);
		return (Item) q.execute().next();
	}
	
	private void addItem(String value) {
		store(new Item(value));
	}
	
	private void updateItem(String existing, String newValue) {
		Item item = queryItem(existing);
		item._str = newValue;
		store(item);
	}	
}
