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
import com.db4o.test.types.*;

public class GarbageCollection
{
	public static void main(String[] args){
		new java.io.File("tgc.yap").delete();

		int strSize = 1;
		int objectCount = 10000;
		ObjectContainer con = Db4o.openFile("tgc.yap");
		String longString = "String";
		ObjectSimplePublic osp = null;
		ArrayTypedPublic atp = null;
		for(int i = 0; i < strSize; i++){
			longString = longString + longString;
		}
		int toGetTen = objectCount / 10;
		for(int i = 0; i < objectCount; i++){

			/*
			osp = new ObjectSimplePublic(longString);
			con.set(osp);
			con.deactivate(osp, 5);
			*/

			atp = new ArrayTypedPublic();
			atp.set(1);
			con.store(atp);

			if( (((double)i / toGetTen) - (i / toGetTen)) < 0.000001){
				con.commit();
				con.ext().purge();
			    mem();
			}
			
		}
		con.commit();
		con.ext().purge();
		longString = null;
		osp = null;
		mem();
		mem();
		con.close();
	}

     static void mem() {
     	System.runFinalization();
         Runtime r = Runtime.getRuntime();
         r.gc();
		 r.runFinalization();
		 r.gc();
         System.out.println(r.totalMemory() - r.freeMemory());
     }

}
