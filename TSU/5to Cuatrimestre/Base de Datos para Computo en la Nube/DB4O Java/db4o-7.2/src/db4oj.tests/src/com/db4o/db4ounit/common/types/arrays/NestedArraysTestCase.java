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
package com.db4o.db4ounit.common.types.arrays;

import db4ounit.*;
import db4ounit.extensions.*;


public class NestedArraysTestCase extends AbstractDb4oTestCase {

    private static final int DEPTH = 5;
    private static final int ELEMENTS = 3;

    public static class Data {
	    public Object _obj;
	    public Object[] _arr;

	    public Data(Object obj, Object[] arr) {
			this._obj = obj;
			_arr = arr;
		}
    }
    
    protected void store(){
        Object[] obj = new Object[ELEMENTS];
        fill(obj, DEPTH);
        Object[] arr = new Object[ELEMENTS];
        fill(arr, DEPTH);
        db().store(new Data(obj,arr));
    }
    
    private void fill(Object[] arr, int depth){
        if(depth <= 0){
            arr[0] = "somestring";
            arr[1] = new Integer(10);
            return;
        }
        
        depth --;
        
        for (int i = 0; i < ELEMENTS; i++) {
            arr[i] = new Object[ELEMENTS];
            fill((Object[])arr[i], depth );
        }
    }
    
    public void testOne(){
    	Data data=(Data)retrieveOnlyInstance(Data.class);
        db().activate(data, Integer.MAX_VALUE);
        check((Object[])data._obj, DEPTH);
        check(data._arr, DEPTH);
    }
    
    private void check(Object[] arr, int depth){
        if(depth <= 0){
            Assert.areEqual("somestring",arr[0]);
            Assert.areEqual(new Integer(10),arr[1]);
            return;
        }
        
        depth --;
        
        for (int i = 0; i < ELEMENTS; i++) {
            check((Object[])arr[i], depth );
        }
        
    }
    
}
