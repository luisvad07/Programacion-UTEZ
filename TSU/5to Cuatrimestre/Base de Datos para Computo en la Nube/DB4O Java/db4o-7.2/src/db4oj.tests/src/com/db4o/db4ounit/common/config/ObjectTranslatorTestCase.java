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
package com.db4o.db4ounit.common.config;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.foundation.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class ObjectTranslatorTestCase extends AbstractDb4oTestCase {
	
	public static class Thing {
		public String name;
		
		public Thing(String name) {
			this.name = name;
		}
	}

	public static class ThingCounterTranslator implements ObjectConstructor {
		
		private Hashtable4 _countCache = new Hashtable4();

		public void onActivate(ObjectContainer container, Object applicationObject, Object storedObject) {
		}

		public Object onStore(ObjectContainer container, Object applicationObject) {
			Thing t = (Thing) applicationObject;
			addToCache(t);
			return t.name;
		}
		
		private void addToCache(Thing t) {
			Object o = (Object) _countCache.get(t.name);
			if (o == null) o = new Integer(0);
			_countCache.put(t.name, new Integer(((Integer)o).intValue() + 1));
		}
		
		public int getCount(Thing t) {
			Object o = (Integer) _countCache.get(t.name);
			if (o == null) return 0;
			return ((Integer)o).intValue();
		}

		public Object onInstantiate(ObjectContainer container, Object storedObject) {
			String name = (String) storedObject;
			return new Thing(name);
		}

		public Class storedClass() {
			return String.class;
		}
	}

	private ThingCounterTranslator _trans;

	protected void configure(Configuration config) {
		config.objectClass(Thing.class).translate(_trans = new ThingCounterTranslator());
	}

	protected void store() throws Exception {
		db().store(new Thing("jbe"));
	}

	public void _testTranslationCount() {
		Thing t = (Thing) retrieveOnlyInstance(Thing.class);
		Assert.isNotNull(t);
		Assert.areEqual("jbe", t.name);
		Assert.areEqual(1, _trans.getCount(t));
	}

	public static void main(String[] args) {
		new ObjectTranslatorTestCase().runSolo();
	}
}
