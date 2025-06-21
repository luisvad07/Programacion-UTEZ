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
package com.db4o.db4ounit.common.assorted;

import com.db4o.ObjectSet;
import com.db4o.ext.*;
import com.db4o.foundation.Hashtable4;
import com.db4o.query.Query;

import db4ounit.Assert;

/**
 * @exclude
 */
public class UUIDTestItem {
	public String name;

	public UUIDTestItem() {
	}

	public UUIDTestItem(String name) {
		this.name = name;
	}

	public static void assertItemsCanBeRetrievedByUUID(final ExtObjectContainer container, Hashtable4 uuidCache) {
		Query q = container.query();
		q.constrain(UUIDTestItem.class);
		ObjectSet objectSet = q.execute();
		while (objectSet.hasNext()) {
			UUIDTestItem item = (UUIDTestItem) objectSet.next();
			Db4oUUID uuid = container.getObjectInfo(item).getUUID();			
			Assert.isNotNull(uuid);
			Assert.areSame(item, container.getByUUID(uuid));
			final Db4oUUID cached = (Db4oUUID) uuidCache.get(item.name);
			if (cached != null) {
				Assert.areEqual(cached, uuid);
			} else {
				uuidCache.put(item.name, uuid);
			}
		}
	}
}