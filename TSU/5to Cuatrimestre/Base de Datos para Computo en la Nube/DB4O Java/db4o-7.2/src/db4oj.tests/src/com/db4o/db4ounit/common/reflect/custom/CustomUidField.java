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
package com.db4o.db4ounit.common.reflect.custom;

import com.db4o.foundation.*;
import com.db4o.reflect.*;

public class CustomUidField implements ReflectField {

	public CustomClassRepository _repository;

	public CustomUidField() {
	}

	public CustomUidField(CustomClassRepository repository) {
		_repository = repository;
	}

	public Object get(Object onObject) {
		return entry(onObject).uid;
	}

	private PersistentEntry entry(Object onObject) {
		return ((PersistentEntry)onObject);
	}

	public ReflectClass getFieldType() {
		return _repository.forFieldType(java.lang.Object.class);
	}

	public String getName() {
		return "uid";
	}

	public Object indexEntry(Object orig) {
		throw new NotImplementedException();
	}

	public ReflectClass indexType() {
		throw new NotImplementedException();
	}

	public boolean isPublic() {
		return true;
	}

	public boolean isStatic() {
		return false;
	}

	public boolean isTransient() {
		return false;
	}

	public void set(Object onObject, Object value) {
		entry(onObject).uid = value;
	}

	public void setAccessible() {
	}

	public String toString() {
		return "CustomUidField()";
	}

}
