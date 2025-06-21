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
package com.db4o.instrumentation.classfilter;

import com.db4o.instrumentation.core.*;

/**
 * Filter by class name/prefix.
 */

public class ByNameClassFilter implements ClassFilter {

	private final String[] _names;
	private final boolean _prefixMatch;
	
	public ByNameClassFilter(String fullyQualifiedName) {
		this(fullyQualifiedName, false);
	}

	public ByNameClassFilter(String name, boolean prefixMatch) {
		this(new String[]{ name }, prefixMatch);
	}

	public ByNameClassFilter(String[] fullyQualifiedNames) {
		this(fullyQualifiedNames, false);
	}

	public ByNameClassFilter(String[] names, boolean prefixMatch) {
		_names = names;		
		_prefixMatch = prefixMatch;
	}

	public boolean accept(Class clazz) {
		for (int idx = 0; idx < _names.length; idx++) {
			boolean match = (_prefixMatch ? clazz.getName().startsWith(_names[idx]) : _names[idx].equals(clazz.getName()));
			if(match) {
				return true;
			}
		}
		return false;
	}

}
