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
package com.db4o.db4ounit.common.sampledata;

public class AtomData {
	
	public AtomData child;
	public String name;
	
	public AtomData(){
	}
	
	public AtomData(AtomData child){
		this.child = child;
	}
	
	public AtomData(String name){
		this.name = name;
	}
	
	public AtomData(AtomData child, String name){
		this(child);
		this.name = name;
	}
	
	public int hashCode() {
		return this.name != null ? this.name.hashCode() : 0;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof AtomData){
			AtomData other = (AtomData)obj;
			if(name == null){
				if(other.name != null){
					return false;
				}
			}else{
				if(! name.equals(other.name)){
					return false;
				}
			}
			if(child != null){
				return child.equals(other.child);
			}
			return other.child == null;
		}
		return false;
	}
	
	public String toString(){
		String str = "Atom(" + name + ")";
		if(child != null){
			return str + "." + child.toString();
		}
		return str;
	}
	
}
