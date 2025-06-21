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
package com.db4o.db4ounit.common.defragment;

import java.io.*;

import com.db4o.*;
import com.db4o.defragment.*;
import com.db4o.query.*;

import db4ounit.*;

public class SlotDefragmentTestCase implements TestLifeCycle {
	
	public void testPrimitiveIndex() throws Exception {
		SlotDefragmentFixture.assertIndex(SlotDefragmentFixture.PRIMITIVE_FIELDNAME);
	}

	public void testWrapperIndex() throws Exception {
		SlotDefragmentFixture.assertIndex(SlotDefragmentFixture.WRAPPER_FIELDNAME);
	}

	public void testTypedObjectIndex() throws Exception {
		SlotDefragmentFixture.forceIndex();
		Defragment.defrag(SlotDefragmentTestConstants.FILENAME,SlotDefragmentTestConstants.BACKUPFILENAME);
		ObjectContainer db=Db4o.openFile(Db4o.newConfiguration(),SlotDefragmentTestConstants.FILENAME);
		Query query=db.query();
		query.constrain(SlotDefragmentFixture.Data.class);
		query.descend(SlotDefragmentFixture.TYPEDOBJECT_FIELDNAME).descend(SlotDefragmentFixture.PRIMITIVE_FIELDNAME).constrain(new Integer(SlotDefragmentFixture.VALUE));
		ObjectSet result=query.execute();
		Assert.areEqual(1,result.size());
		db.close();
	}

	public void testNoForceDelete() throws Exception {
		Defragment.defrag(SlotDefragmentTestConstants.FILENAME,SlotDefragmentTestConstants.BACKUPFILENAME);
		Assert.expect(IOException.class, new CodeBlock() {
			public void run() throws Throwable {
				Defragment.defrag(SlotDefragmentTestConstants.FILENAME,SlotDefragmentTestConstants.BACKUPFILENAME);
			}
		});
	}	

	public void setUp() throws Exception {
		new File(SlotDefragmentTestConstants.FILENAME).delete();
		new File(SlotDefragmentTestConstants.BACKUPFILENAME).delete();
		SlotDefragmentFixture.createFile(SlotDefragmentTestConstants.FILENAME);
	}

	public void tearDown() throws Exception {
	}
}
