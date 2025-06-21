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
package com.db4o.ta.instrumentation.ant;

import java.util.*;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import org.apache.tools.ant.util.regexp.*;

import com.db4o.instrumentation.ant.*;
import com.db4o.instrumentation.classfilter.*;
import com.db4o.instrumentation.core.*;
import com.db4o.ta.instrumentation.*;

/**
 * @exclude
 */
public class TAAntClassEditFactory extends ProjectComponent implements AntClassEditFactory {

	private final List _regExp = new ArrayList();
	private final List _filters = new ArrayList();

	public RegularExpression createRegexp() {
        RegularExpression regExp = new RegularExpression();
        _regExp.add(regExp);
        return regExp;
	}
	
	public void add(ClassFilter classFilter) {
		_filters.add(classFilter);
	}
	
	public BloatClassEdit createEdit(ClassFilter clazzFilter) {
		final List filters = new ArrayList(2);
		for (Iterator filterIter = _filters.iterator(); filterIter.hasNext();) {
			ClassFilter filter = (ClassFilter) filterIter.next();
			filters.add(filter);
		}
		if(!_regExp.isEmpty()) {
			Regexp[] regExp = new Regexp[_regExp.size()];
			int idx = 0;
			for (Iterator reIter = _regExp.iterator(); reIter.hasNext();) {
				RegularExpression re = (RegularExpression) reIter.next();
				regExp[idx++] = re.getRegexp(getProject());
			}
			filters.add(new AntRegExpClassFilter(regExp));
		}
		ClassFilter userClassFilter = null;
		switch(filters.size()) {
			case 0:
				userClassFilter = new AcceptAllClassesFilter();
				break;
			case 1:
				userClassFilter = (ClassFilter) filters.get(0);
				break;
			default:
				userClassFilter = new CompositeOrClassFilter((ClassFilter[]) filters.toArray(new ClassFilter[filters.size()]));
		}
		if(clazzFilter != null) {
			userClassFilter = new CompositeAndClassFilter(new ClassFilter[]{ clazzFilter, userClassFilter });
		}
		return new InjectTransparentActivationEdit(userClassFilter);
	}

}
