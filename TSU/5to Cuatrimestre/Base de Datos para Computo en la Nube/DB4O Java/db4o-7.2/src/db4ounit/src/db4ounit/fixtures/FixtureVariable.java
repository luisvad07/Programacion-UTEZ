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
package db4ounit.fixtures;

import com.db4o.foundation.*;

import db4ounit.fixtures.FixtureContext.*;

public class FixtureVariable {
	
	private final String _label;
	
	public FixtureVariable() {
		this("");
	}

	public FixtureVariable(String label) {
		_label = label;
	}
	
	/**
	 * @sharpen.property
	 */
	public String label() {
		return _label;
	}
	
	public String toString() {
		return _label;
	}
	
	public Object with(Object value, Closure4 closure) {
		return inject(value).run(closure);
	}

	public void with(Object value, Runnable runnable) {
		inject(value).run(runnable);
	}

	private FixtureContext inject(Object value) {
		return currentContext().add(this, value);
	} 
	
	/**
	 * @sharpen.property
	 */
	public Object value() {
		final Found found = currentContext().get(this);
		if (null == found) throw new IllegalStateException();
		return found.value;
	}

	private FixtureContext currentContext() {
		return FixtureContext.current();
	}
}
