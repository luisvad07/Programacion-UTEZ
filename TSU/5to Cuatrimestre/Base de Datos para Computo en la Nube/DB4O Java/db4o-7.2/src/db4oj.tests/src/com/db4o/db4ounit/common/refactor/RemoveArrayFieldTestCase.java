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
package com.db4o.db4ounit.common.refactor;

import com.db4o.ObjectSet;
import com.db4o.config.ObjectClass;
import com.db4o.query.Query;

import db4ounit.Assert;
import db4ounit.extensions.AbstractDb4oTestCase;
import db4ounit.extensions.fixtures.OptOutDefragSolo;
import db4ounit.extensions.util.CrossPlatformServices;

public class RemoveArrayFieldTestCase extends AbstractDb4oTestCase implements OptOutDefragSolo {
	public static class DataBefore {
		
		public Object[] array;	
		public String name;
		public boolean status;

		public DataBefore(String name, boolean status, Object[] array) {
			this.name = name;
			this.status = status;
			this.array = array;	
		}
	}

	public static class DataAfter {
		
		public String name;
		public boolean status;

		public DataAfter(String name, boolean status) {
			this.name = name;
			this.status = status;
		}
	}
		
	public void testRemoveArrayField() throws Exception {
		DataBefore dataA = new DataBefore("a", true, new Object[] { "X" });
		DataBefore dataB = new DataBefore("b", false, new Object[0]);
		store(dataA);
		store(dataB);

        ObjectClass oc = fixture().config().objectClass(DataBefore.class);
        // we must use ReflectPlatform here as the string must include
        // the assembly name in .net
        oc.rename(CrossPlatformServices.fullyQualifiedName(DataAfter.class));        
        reopen();
        
        Query query = newQuery(DataAfter.class);
		query.descend("name").constrain("a");
		ObjectSet result = query.execute();
		Assert.areEqual(1, result.size());
		DataAfter data = (DataAfter) result.next();
		Assert.areEqual(dataA.name, data.name);
		Assert.areEqual(dataA.status, data.status);
	}
}
