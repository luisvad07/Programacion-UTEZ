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
package EDU.purdue.cs.bloat.cfg;

import java.util.*;

import EDU.purdue.cs.bloat.editor.*;

/**
 * <tt>Handler</tt> represents a try-catch block. It containes a set of
 * protected <tt>Block</tt>s (the "try" blocks), a catch <tt>Block</tt>,
 * and the <tt>Type</tt> of exception that is caught by the catch block.
 * 
 * @see Block
 * @see EDU.purdue.cs.bloat.reflect.Catch
 * @see EDU.purdue.cs.bloat.editor.TryCatch
 */
public class Handler {
	Set protectedBlocks;

	Block catchBlock;

	Type type;

	/**
	 * Constructor.
	 * 
	 * @param catchBlock
	 *            The block of code that handles an exception
	 * @param type
	 *            The type of exception that is thrown
	 */
	public Handler(final Block catchBlock, final Type type) {
		this.protectedBlocks = new HashSet();
		this.catchBlock = catchBlock;
		this.type = type;
	}

	/**
	 * Returns a <tt>Collection</tt> of the "try" blocks.
	 */
	public Collection protectedBlocks() {
		return protectedBlocks;
	}

	public void setCatchBlock(final Block block) {
		catchBlock = block;
	}

	public Block catchBlock() {
		return catchBlock;
	}

	public Type catchType() {
		return type;
	}

	public String toString() {
		return "try -> catch (" + type + ") " + catchBlock;
	}
}
