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
package EDU.purdue.cs.bloat.tree;

/**
 * UseInformation stores information about a use of a local variable.
 * 
 * @author Thomas VanDrunen
 */
public class UseInformation {

	int type;

	int type0s;

	int type1s;

	int type0_x1s;

	int type0_x2s;

	int type1_x1s;

	int type1_x2s;

	public UseInformation() {

		type = 2; // assume type > 1 unless discovered otherwise
		type0s = 0;
		type1s = 0;
		type0_x1s = 0;
		type0_x2s = 0;
		type1_x1s = 0;
		type1_x2s = 0;
	}
}
