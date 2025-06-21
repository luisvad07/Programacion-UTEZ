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

import db4ounit.*;

public final class FixtureDecoration implements TestDecoration {
	private final Test _test;
	private final FixtureVariable _variable;
	private final Object _value;
	private final String _fixtureLabel;
	
	public FixtureDecoration(Test test, FixtureVariable fixtureVariable, Object fixtureValue) {
		this(test, null, fixtureVariable, fixtureValue);
	}

	public FixtureDecoration(Test test, String fixtureLabel, FixtureVariable fixtureVariable, Object fixtureValue) {
		_test = test;
		_fixtureLabel = fixtureLabel;
		_variable = fixtureVariable;
		_value = fixtureValue;
	}
	
	public String label() {
		final ObjectByRef label = new ObjectByRef(); 
		runDecorated(new Runnable() {
			public void run() {
				label.value = "(" + fixtureLabel() + ") " + _test.label();
			}
		});
		return (String)label.value;
	}

	public void run() {
		runDecorated(_test);
	}
	
	public Test test() {
		return _test;
	}

	private void runDecorated(final Runnable block) {
		_variable.with(value(), block);
	}

	private Object value() {
		return _value instanceof Deferred4
			? ((Deferred4)_value).value()
			: _value;
	}

	private Object fixtureLabel() {
		return (_fixtureLabel == null ? _value : _fixtureLabel);
	}
}