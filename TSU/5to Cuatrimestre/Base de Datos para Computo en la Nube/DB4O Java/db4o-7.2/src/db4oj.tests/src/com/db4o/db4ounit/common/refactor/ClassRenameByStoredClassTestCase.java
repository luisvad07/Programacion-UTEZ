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

import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.fixtures.*;
import db4ounit.extensions.util.*;

public class ClassRenameByStoredClassTestCase extends AbstractDb4oTestCase implements OptOutNetworkingCS {

	private static String NAME = "test";
	
	public static void main(String[] args) {
		new ClassRenameByStoredClassTestCase().runAll();
	}
	
    public static class Original {
    	public String _name;
    	public Original(String name) {
    		this._name = name;
		}
    }
    
    public static class Changed {
    	public String _name;
    	public String _otherName;
    	public Changed(String name) {
    		_name = name;
    		_otherName = name;
		}
    }
    
    
    protected void store() throws Exception {
    	store(new Original(NAME));
    }
    
    public void testWithReopen() throws Exception {
    	assertRenamed(true);
	}

    public void testWithoutReopen() throws Exception {
    	assertRenamed(false);
	}

	private void assertRenamed(boolean doReopen) throws Exception {
		StoredClass originalClazz = db().ext().storedClass(Original.class);
    	originalClazz.rename(CrossPlatformServices.fullyQualifiedName(Changed.class));
    	if(doReopen) {
    		reopen();
    	}
    	Changed changedObject = (Changed) retrieveOnlyInstance(Changed.class);
    	Assert.areEqual(NAME, changedObject._name);
    	Assert.isNull(changedObject._otherName);
	}

}
