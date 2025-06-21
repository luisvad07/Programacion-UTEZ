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
package com.db4o.db4ounit.common.stored;

import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;
import db4ounit.extensions.util.CrossPlatformServices;

public class ArrayStoredTypeTestCase extends AbstractDb4oTestCase {

	public static class Data {
		public boolean[] _primitiveBoolean;
		public Boolean[] _wrapperBoolean;
		public int[] _primitiveInt;
		public Integer[] _wrapperInteger;

		public Data(boolean[] primitiveBoolean, Boolean[] wrapperBoolean, int[] primitiveInteger, Integer[] wrapperInteger) {
			this._primitiveBoolean = primitiveBoolean;
			this._wrapperBoolean = wrapperBoolean;
			this._primitiveInt = primitiveInteger;
			this._wrapperInteger = wrapperInteger;
		}
	}
	
	protected void store() throws Exception {
		Data data=new Data(
				new boolean[] { true, false },
				new Boolean[] { Boolean.TRUE, Boolean.FALSE },
				new int[] { 0, 1, 2 },
				new Integer[] { new Integer(4),new Integer(5), new Integer(6)}
		);
		store(data);
	}
	
	public void testArrayStoredTypes() {
		StoredClass clazz = db().storedClass(Data.class);
		assertStoredType(clazz, "_primitiveBoolean", boolean.class);
		assertStoredType(clazz, "_wrapperBoolean", Boolean.class);
		assertStoredType(clazz, "_primitiveInt", int.class);
		assertStoredType(clazz, "_wrapperInteger", Integer.class);
	}

	private void assertStoredType(StoredClass clazz, String fieldName,
			Class type) {
		StoredField field = clazz.storedField(fieldName,null);
		
		Assert.areEqual(
				type.getName(),
				// getName() also contains the assembly name in .net
				// so we better remove it for comparison
				CrossPlatformServices.simpleName(field.getStoredType().getName()));
	}
}
