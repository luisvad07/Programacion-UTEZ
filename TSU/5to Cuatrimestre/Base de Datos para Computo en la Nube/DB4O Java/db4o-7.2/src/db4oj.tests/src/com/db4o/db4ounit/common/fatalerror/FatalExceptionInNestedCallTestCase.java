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
package com.db4o.db4ounit.common.fatalerror;

import db4ounit.extensions.AbstractDb4oTestCase;


public class FatalExceptionInNestedCallTestCase extends AbstractDb4oTestCase{
	
	public static void main(String[] arguments) {
		new FatalExceptionInNestedCallTestCase().runSolo();
	}
	
	public static class Item {
		
		public Item _child;
		
		public int _depth;
		
		public Item(){
			
		}
		
		public Item(Item child, int depth){
			_child = child;
			_depth = depth;
		}

		
	}
	
	public static class FatalError extends Error{
		
	}
	
	protected void store() throws Exception {
		Item childItem = new Item(null, 1);
		Item parentItem = new Item(childItem, 0);
		store(parentItem);
	}
	
	public void test(){
		/* TODO FIXME
		eventRegistry().updated().addListener(new EventListener4() {
			public void onEvent(Event4 e, EventArgs args) {
				ObjectEventArgs objectArgs = (ObjectEventArgs) args;
				Item item = (Item) objectArgs.object();
				if(item._depth == 0){
					throw new FatalError();
				}
		
			}
		});
		Query q = this.newQuery(Item.class);
		q.descend("_depth").constrain(new Integer(0));
		ObjectSet objectSet = q.execute();
		final Item parentItem = (Item) objectSet.next();
		Assert.expect(FatalError.class, new CodeBlock() {
			public void run() throws Throwable {
				db().set(parentItem, 3);
			}
		});
		*/
	}
	
//	private EventRegistry eventRegistry() {
//		return EventRegistryFactory.forObjectContainer(db());
//	}


}
