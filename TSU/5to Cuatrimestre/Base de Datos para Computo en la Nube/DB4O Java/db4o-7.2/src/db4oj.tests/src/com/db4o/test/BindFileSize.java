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
import com.db4o.config.*;

public class BindFileSize {
	
	static final int LENGTH = 1000;
    
    public static class Item{
        
        public String foo;
        
        public Item(){
        }
        
        public Item(int length){
            StringBuffer sb = new StringBuffer(length);
            for (int i = 0; i < length; i++) {
                sb.append("g");
            }  
            this.foo = sb.toString();
        }
        
    }
    
    public void configure(){
        Db4o.configure().generateUUIDs(Integer.MAX_VALUE);
        Db4o.configure().generateVersionNumbers(Integer.MAX_VALUE);
    }
    
	public void store(){
		Test.deleteAllInstances(Item.class);
        Item item1 = new Item(LENGTH - 1);
        Item item2 = new Item(LENGTH - 1);
        Test.store(item1);
        Test.store(item2);
        Test.commit();
        Test.delete(item1);
        Test.delete(item2);
        Test.commit();
        Test.store(new Item(LENGTH));
	}
	
	public void testGrowth(){
        Test.reOpen();
		Item item =  (Item)Test.getOne(Item.class);
		long id = Test.objectContainer().getID(item);
		for (int call = 0; call < 50; call++) {
			item = new Item(LENGTH);
			Test.objectContainer().bind(item, id);
			Test.objectContainer().store(item);
			Test.commit();
			checkFileSize(call);
			Test.reOpen();
		}
	}
	
	private void checkFileSize(int call){
		if(Test.canCheckFileSize()){
			int newFileLength = Test.fileLength();
			
			// Interesting for manual tests:
			// System.out.println(newFileLength);
			
			if(call == 10){
				// consistency reached, start testing
				jumps = 0;
				fileLength = newFileLength;
			}else if(call > 10){
				if(newFileLength > fileLength){
					if(jumps < 4){
						fileLength = newFileLength;
						jumps ++;
						// allow two further steps in size
						// may be necessary for commit space extension
					}else{
						// now we want constant behaviour
						// Test.error();
					}
				}
			}
		}
	}
	
	private static transient int fileLength;
	private static transient int jumps; 



}
