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

import com.db4o.*;
import com.db4o.ext.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class ObjectSetIDsTestCase extends Db4oClientServerTestCase {
	
	public static void main(String[] args) {
		new ObjectSetIDsTestCase().runConcurrency();
	}
	
	static final int COUNT = 11;
	
	protected void store(){
		for (int i = 0; i < COUNT; i++) {
			store(new ObjectSetIDsTestCase());
        }
	}
	
	public void conc(ExtObjectContainer oc){
		Query q = oc.query();
		q.constrain(this.getClass());
		ObjectSet res = q.execute();
		Assert.areEqual(COUNT,res.size());
		long[] ids1 = new long[res.size()];
		int i =0;
		while(res.hasNext()){
			ids1[i++]=oc.getID(res.next());
		}
		
		res.reset();
		long[] ids2 = res.ext().getIDs();
		
		Assert.areEqual(COUNT,ids1.length);
		Assert.areEqual(COUNT,ids2.length);
		
		for (int j = 0; j < ids1.length; j++) {
			boolean found = false;
			for (int k = 0; k < ids2.length; k++) {
				if(ids1[j] == ids2[k]){
					found = true;
					break;
				}
			}
			Assert.isTrue(found);
        }
	}
}
