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

import java.io.*;

import com.db4o.*;
import com.db4o.tools.*;

public class ObjectNotStorable implements Runnable{
	
	private static final String FILE = "notStorable.yap";
	private static boolean throwException = false;
	
	private String name;
	
	private ObjectNotStorable(String name){
		if(throwException){
			throw new RuntimeException();
		}
		this.name = name;
	}

	public static void main(String[] args) {
		throwException = false;
		new ObjectNotStorable(null).run();
	}
	
	public void run(){
		new File(FILE).delete();
		Db4o.configure().exceptionsOnNotStorable(true);
		run1();
	}
	
	private void run1(){
		try{
			setExc();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			getExc();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void setOK(){
		throwException = false;
		ObjectContainer con = Db4o.openFile(FILE);
		ObjectNotStorable ons = new ObjectNotStorable("setOK");
		con.store(ons);
		con.close();
	}
	
	private static void setExc(){
		ObjectContainer con = Db4o.openFile(FILE);
		throwException = false;
		ObjectNotStorable ons = new ObjectNotStorable("setExc");
		throwException = true;
		con.store(ons);
		con.close();
	}
	
	private static void getOK(){
		throwException = false;
		ObjectContainer con = Db4o.openFile(FILE);
		ObjectSet set = con.queryByExample(new ObjectNotStorable(null));
		while(set.hasNext()){
			Logger.log(con, set.next());
		}
		con.close();
	}
	
	private static void getExc(){
		throwException = true;
		ObjectContainer con = Db4o.openFile(FILE);
		ObjectSet set = con.queryByExample(null);
		while(set.hasNext()){
			Logger.log(con, set.next());
		}
		con.close();
		
	}
}
