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
package com.db4o.db4ounit.common.querying;

import com.db4o.*;
import com.db4o.config.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class CascadeOnDelete extends AbstractDb4oTestCase {
	
	public static class Item {
		public String item;
	}
	
	public Item[] items;
	
	public void test() throws Exception {
		noAccidentalDeletes();
	}
	
	private void noAccidentalDeletes() throws Exception {
	 	noAccidentalDeletes1(true, true);
	 	noAccidentalDeletes1(true, false);
	 	noAccidentalDeletes1(false, true);
	 	noAccidentalDeletes1(false, false);
	}
	
	private void noAccidentalDeletes1(boolean cascadeOnUpdate, boolean cascadeOnDelete) throws Exception {
		deleteAll(getClass());
		deleteAll(Item.class);
		
		ObjectClass oc = Db4o.configure().objectClass(CascadeOnDelete.class);
		oc.cascadeOnDelete(cascadeOnDelete);
		oc.cascadeOnUpdate(cascadeOnUpdate);
		
		reopen();
		
		Item i = new Item();
		CascadeOnDelete cod = new CascadeOnDelete();
		cod.items = new Item[]{ i };
		db().store(cod);
		db().commit();
		
		cod.items[0].item = "abrakadabra";
		db().store(cod);
		if(! cascadeOnDelete && ! cascadeOnUpdate){
			// the only case, where we don't cascade
			db().store(cod.items[0]);
		}
		
		Assert.areEqual(1, countOccurences(Item.class));
		db().commit();
		Assert.areEqual(1, countOccurences(Item.class));
	}
}
