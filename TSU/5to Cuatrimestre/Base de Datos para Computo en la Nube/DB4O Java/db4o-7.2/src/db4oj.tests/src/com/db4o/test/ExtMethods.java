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
package com.db4o.test;

import com.db4o.Db4o;
import com.db4o.ext.*;

public class ExtMethods {
	
    public void configure(){
        Db4o.configure().generateUUIDs(Integer.MAX_VALUE);
        Db4o.configure().generateVersionNumbers(Integer.MAX_VALUE);
    }

	public void test(){
		
		ExtMethods em = new ExtMethods();
		Test.store(em);
		
		ExtObjectContainer eoc = Test.objectContainer();
		
		Test.ensure(! eoc.isClosed());
		
		Test.ensure(eoc.isActive(em));
		Test.ensure(eoc.isStored(em));
		
		eoc.deactivate(em, 1);
		Test.ensure(! eoc.isActive(em));
		
		eoc.activate(em, 1);
		Test.ensure(eoc.isActive(em));
		
		long id = eoc.getID(em);
		
		Test.ensure(eoc.isCached(id));
		
		eoc.purge(em);
		
		Test.ensure(! eoc.isCached(id));
		Test.ensure(! eoc.isStored(em));
		Test.ensure(! eoc.isActive(em));
		
		eoc.bind(em, id);
		
		Test.ensure(eoc.isCached(id));
		Test.ensure(eoc.isStored(em));
		Test.ensure(eoc.isActive(em));
		
		ExtMethods em2 = (ExtMethods)eoc.getByID(id);
		
		Test.ensure(em == em2);
		
		// Purge all and try again
		eoc.purge();  
		
		Test.ensure(eoc.isCached(id));
		Test.ensure(eoc.isStored(em));
		Test.ensure(eoc.isActive(em));
		
		em2 = (ExtMethods)eoc.getByID(id);
		Test.ensure(em == em2);
		
		Test.delete(em2);
		Test.commit();
		Test.ensure(! eoc.isCached(id));
		Test.ensure(! eoc.isStored(em2));
		Test.ensure(! eoc.isActive(em2));
		
		// Null checks
		Test.ensure(! eoc.isStored(null));
		Test.ensure(! eoc.isActive(null));
		Test.ensure(! eoc.isCached(0));
		
	}

}
