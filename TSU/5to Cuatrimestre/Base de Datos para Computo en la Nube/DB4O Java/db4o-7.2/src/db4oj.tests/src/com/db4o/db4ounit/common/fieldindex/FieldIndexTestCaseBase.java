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
package com.db4o.db4ounit.common.fieldindex;

import com.db4o.config.*;
import com.db4o.internal.*;
import com.db4o.query.Query;

import db4ounit.extensions.AbstractDb4oTestCase;
import db4ounit.extensions.fixtures.OptOutCS;

public abstract class FieldIndexTestCaseBase extends AbstractDb4oTestCase
		implements OptOutCS {

	public FieldIndexTestCaseBase() {
		super();
	}

	protected void configure(Configuration config) {
		indexField(config, FieldIndexItem.class, "foo");
	}

	protected abstract void store();

	protected void storeItems(final int[] foos) {
		for (int i = 0; i < foos.length; i++) {
			store(new FieldIndexItem(foos[i]));
		}
	}

	protected Query createQuery(final int id) {
		Query q = createItemQuery();
		q.descend("foo").constrain(new Integer(id));
		return q;
	}

	protected Query createItemQuery() {
		return createQuery(FieldIndexItem.class);
	}

	protected Query createQuery(Class clazz) {
		return createQuery(trans(), clazz);
	}

	protected Query createQuery(Transaction trans, Class clazz) {
		Query q = createQuery(trans);
		q.constrain(clazz);
		return q;
	}

	protected Query createItemQuery(Transaction trans) {
		Query q = createQuery(trans);
		q.constrain(FieldIndexItem.class);
		return q;
	}

	private Query createQuery(Transaction trans) {
		return container().query(trans);
	}
}