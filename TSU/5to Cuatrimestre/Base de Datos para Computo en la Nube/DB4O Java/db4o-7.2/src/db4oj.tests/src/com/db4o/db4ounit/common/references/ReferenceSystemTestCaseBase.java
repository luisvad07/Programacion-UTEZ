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
package com.db4o.db4ounit.common.references;

import com.db4o.internal.*;

import db4ounit.*;

public abstract class ReferenceSystemTestCaseBase implements TestLifeCycle {

	private static class Data {
	}
	
	private ReferenceSystem _refSys;
	
	public void testEmpty() {
		assertNullReference(42, new Data());
	}

	public void testAddDeleteReaddOne() {
		int id = 42;
		Data data = new Data();
		ObjectReference ref = createRef(id, data);
		_refSys.addNewReference(ref);
		assertReference(id, data, ref);
		_refSys.removeReference(ref);
		assertNullReference(id, data);
		_refSys.addNewReference(ref);
		assertReference(id, data, ref);
	}

	public void testDanglingReferencesAreRemoved() {
		int[] id = { 42, 43 };
		Data[] data = { new Data(), new Data() };
		ObjectReference ref0 = createRef(id[0], data[0]);
		ObjectReference ref1 = createRef(id[1], data[1]);
		_refSys.addNewReference(ref0);
		_refSys.addNewReference(ref1);
		_refSys.removeReference(ref0);
		_refSys.removeReference(ref1);
		_refSys.addNewReference(ref0);
		assertReference(id[0], data[0], ref0);
		assertNullReference(id[1], data[1]);
	}
	
	private void assertNullReference(int id, Data data) {
		Assert.isNull(_refSys.referenceForId(id));
		Assert.isNull(_refSys.referenceForObject(data));
	}

	private void assertReference(int id, Data data, ObjectReference ref) {
		Assert.areSame(ref, _refSys.referenceForId(id));
		Assert.areSame(ref, _refSys.referenceForObject(data));
	}

	private ObjectReference createRef(int id, Data data) {
		ObjectReference ref = new ObjectReference(id);
		ref.setObject(data);
		return ref;
	}
	
	public void setUp() {
		_refSys = createReferenceSystem();
	}
	
	public void tearDown() {
	}
	
	protected abstract ReferenceSystem createReferenceSystem();
}
