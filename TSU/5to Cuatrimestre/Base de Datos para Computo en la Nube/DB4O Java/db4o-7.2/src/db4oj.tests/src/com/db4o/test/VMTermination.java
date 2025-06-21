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

public class VMTermination {
	
	private String str;
	
	public VMTermination(){}
	
	public VMTermination(String str){
		this.str = str;
	}
	
	public static void main(String[] args) throws Exception{
		
		
		// to circumvent the shutdown-hook, run this
		// testcase with a breakpoint in the debugger
		// see comments below
		
		
		// run one at a time, one after the other
		
		int step = 0;
		
		switch(step){
			case 0:
				// Test.delete();
				Test.statistics();
				break;
			case 1:
				killSingleUser();	
				break;
			case 2:
				testSingleUser();
				break;
			case 3:
				killServer1();
				break;
			case 4:
				testServer1();
				break;
			case 5:
				killServer2();
				break;
			case 6:
				testServer2();
				break;
		}
		
	}
	
	public static void killSingleUser() throws Exception{
		Test.runServer = false;
		Test.clientServer = false;
		Test.delete();
		ObjectContainer con = Test.open();
		con.store(new VMTermination("willbethere"));
		con.commit();
		
		// place a breakpoint on the following line 
		// and stop the debugger
	
		System.exit(0);
	}
	
	public static void testSingleUser() {
		Test.runServer = false;
		Test.clientServer = false;
		ObjectContainer con = Test.open();
		Test.ensureOccurrences(new VMTermination(), 1);
		Test.logAll();
		Test.end();
	}
	
	public static void killServer1() throws Exception{
		Test.runServer = true;
		Test.clientServer = true;
		Test.delete();
		ObjectContainer con = Test.open();
		con.store(new VMTermination("willbethere"));
		con.commit();
		
		// place a breakpoint on the following line 
		// and stop the debugger
		System.exit(0);
	}
	
	public static void testServer1(){
		Test.runServer = true;
		Test.clientServer = true;
		ObjectContainer con = Test.open();
		Test.ensureOccurrences(new VMTermination(), 1);
		Test.logAll();
		Test.end();
	}
	
	public static void killServer2() throws Exception{
		Test.runServer = true;
		Test.clientServer = true;
		ObjectContainer con = Test.open();
		con.store(new VMTermination("willbethere"));
		
		
		// a bit tougher now: place a
		// breakpoint in commit sourcecode
		// and stop the debugger there
		con.commit();
	}
	
	public static void testServer2(){
		Test.runServer = true;
		Test.clientServer = true;
		ObjectContainer con = Test.open();
		Test.ensureOccurrences(new VMTermination(), 2);
		Test.logAll();
		Test.end();
	}
	
	
	
	
	
	
}

