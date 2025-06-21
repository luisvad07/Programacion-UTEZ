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

/**
 * Set of live {@link FixtureVariable}/value pairs.
 * 
 */
public class FixtureContext {
	
	private static final DynamicVariable _current = new DynamicVariable() {
		private final FixtureContext EMPTY_CONTEXT = new FixtureContext();
		
		protected Object defaultValue() {
			return EMPTY_CONTEXT;
		}
	};
	
	/**
	 * @sharpen.property
	 */
	public static FixtureContext current() {
		return (FixtureContext)_current.value();
	}
	
	public Object run(Closure4 closure) {
		return _current.with(this, closure);
	}

	public void run(Runnable block) {
		_current.with(this, block);
	}
	
	static class Found {
		public final Object value;
		
		public Found(Object value_) {
			value = value_;
		}
	}
	
	Found get(FixtureVariable fixture) {
		return null;
	}
	
	public FixtureContext combine(final FixtureContext parent) {
		return new FixtureContext() {
			Found get(FixtureVariable fixture) {
				Found found = FixtureContext.this.get(fixture);
				if (null != found) return found;
				return parent.get(fixture);
			}
		};
	}

	FixtureContext add(final FixtureVariable fixture, final Object value) {
		return new FixtureContext() {
			Found get(FixtureVariable key) {
				if (key == fixture) {
					return new Found(value);
				}
				return FixtureContext.this.get(key);
			}
		};
	}
}
