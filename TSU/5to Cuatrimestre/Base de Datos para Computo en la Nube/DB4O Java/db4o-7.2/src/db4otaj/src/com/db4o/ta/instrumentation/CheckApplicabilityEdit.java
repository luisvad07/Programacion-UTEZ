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
package com.db4o.ta.instrumentation;

import EDU.purdue.cs.bloat.editor.*;

import com.db4o.instrumentation.core.*;
import com.db4o.instrumentation.util.*;

/**
 * @exclude
 */
class CheckApplicabilityEdit implements BloatClassEdit {

	public InstrumentationStatus enhance(ClassEditor ce, ClassLoader origLoader, BloatLoaderContext loaderContext) {
		try {
			Class clazz = BloatUtil.classForEditor(ce, origLoader);
			if (clazz.isInterface()) {
				return InstrumentationStatus.FAILED;
			}
			if (!isApplicableClass(clazz)) {
				return InstrumentationStatus.FAILED;
			}
		} catch (ClassNotFoundException e) {
			return InstrumentationStatus.FAILED;
		}
		return InstrumentationStatus.NOT_INSTRUMENTED;
	}

	private boolean isApplicableClass(Class clazz) {
		Class curClazz = clazz;
		// FIXME string reference to Enum is rather fragile
		while (curClazz != Object.class && curClazz != null && !curClazz.getName().equals("java.lang.Enum")) {
			if (BloatUtil.isPlatformClassName(curClazz.getName())) {
				return false;
			}
			curClazz = curClazz.getSuperclass();
		}
		return true;
	}

	
}
