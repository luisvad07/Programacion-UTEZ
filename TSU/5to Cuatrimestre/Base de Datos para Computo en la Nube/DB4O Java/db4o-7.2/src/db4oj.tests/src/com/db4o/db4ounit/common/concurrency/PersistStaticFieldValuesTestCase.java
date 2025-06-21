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
package com.db4o.db4ounit.common.concurrency;

import com.db4o.config.*;
import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class PersistStaticFieldValuesTestCase extends Db4oClientServerTestCase {
	
	public static void main(String[] args) {
		new PersistStaticFieldValuesTestCase().runConcurrency();
	}

	public static final PsfvHelper ONE = new PsfvHelper();

	public static final PsfvHelper TWO = new PsfvHelper();

	public static final PsfvHelper THREE = new PsfvHelper();

	public PsfvHelper one;

	public PsfvHelper two;

	public PsfvHelper three;

	protected void configure(Configuration config) {
		config.objectClass(PersistStaticFieldValuesTestCase.class)
				.persistStaticFieldValues();
	}

	protected void store() {
		PersistStaticFieldValuesTestCase psfv = new PersistStaticFieldValuesTestCase();
		psfv.one = ONE;
		psfv.two = TWO;
		psfv.three = THREE;
		store(psfv);
	}

	public void conc(ExtObjectContainer oc) {
		PersistStaticFieldValuesTestCase psfv = (PersistStaticFieldValuesTestCase) retrieveOnlyInstance(
				oc, PersistStaticFieldValuesTestCase.class);
		Assert.areSame(ONE, psfv.one);
		Assert.areSame(TWO, psfv.two);
		Assert.areSame(THREE, psfv.three);
	}

	public static class PsfvHelper {

	}

}
