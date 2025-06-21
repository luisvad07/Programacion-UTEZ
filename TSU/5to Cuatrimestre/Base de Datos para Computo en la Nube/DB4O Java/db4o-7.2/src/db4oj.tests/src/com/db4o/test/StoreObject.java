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

import com.db4o.*;
import com.db4o.query.*;
import com.db4o.tools.*;

public class StoreObject {

	Object _field;
	
	public void storeOne()	{
		_field = new Object();
	}
	
	public void testOne() {
        
        Db4o.configure().objectClass(Object.class).cascadeOnActivate(true);
        
        
        Query q = Test.query();
        q.constrain(new Object());
        q.execute();
        
        Test.close();
        
        Statistics.main(new String[]{Test.FILE_SOLO});
        
        Test.reOpen();
        
        q = Test.query();
        StoreObject template = new StoreObject();
        template._field = new Object(); 
        q.constrain(template);
        q.execute();
        
        Test.close();
        
        Statistics.main(new String[]{Test.FILE_SOLO});
        
        Test.reOpen();


        
        
        
		// Test.ensure(_field != null);
	}
}
