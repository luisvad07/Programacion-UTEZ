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
package com.db4o.test;

import com.db4o.*;

public class DualDelete {
	
	public Atom atom;
	
	public void configure(){
		Db4o.configure().objectClass(this).cascadeOnDelete(true);
		Db4o.configure().objectClass(this).cascadeOnUpdate(true);
	}
	
	public void store(){
		Test.deleteAllInstances(this);
		Test.deleteAllInstances(new Atom());
		DualDelete dd1 = new DualDelete();
		dd1.atom = new Atom("justone");
		Test.store(dd1);
		DualDelete dd2 = new DualDelete();
		dd2.atom = dd1.atom;
		Test.store(dd2);
	}
	
	public void test(){
		Test.deleteAllInstances(this);
		Test.ensureOccurrences(new Atom(), 0);
		Test.rollBack();
		Test.ensureOccurrences(new Atom(), 1);
		Test.deleteAllInstances(this);
		Test.ensureOccurrences(new Atom(), 0);
		Test.commit();
		Test.ensureOccurrences(new Atom(), 0);
		Test.rollBack();
		Test.ensureOccurrences(new Atom(), 0);
	}
	

}
