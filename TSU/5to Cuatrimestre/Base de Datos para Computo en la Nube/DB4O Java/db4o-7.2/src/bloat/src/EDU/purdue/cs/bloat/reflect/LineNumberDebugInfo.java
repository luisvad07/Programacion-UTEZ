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
package EDU.purdue.cs.bloat.reflect;

/**
 * LineNumberDebugInfo is used to map a range of instructions to a line number
 * in the original Java source file. An instance of
 * <tt>file.LineNumberTable</tt> contains an array of these guys.
 * 
 * @see EDU.purdue.cs.bloat.file.LineNumberTable
 * 
 * @author Nate Nystrom (<a
 *         href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class LineNumberDebugInfo {
	private int startPC;

	private int lineNumber;

	/**
	 * Constructor.
	 * 
	 * @param startPC
	 *            The start PC of the instructions for this line number.
	 * @param lineNumber
	 *            The line number for this group of instructions.
	 */
	public LineNumberDebugInfo(final int startPC, final int lineNumber) {
		this.startPC = startPC;
		this.lineNumber = lineNumber;
	}

	/**
	 * Get the start PC of the instructions for this line number.
	 * 
	 * @return The start PC.
	 */
	public int startPC() {
		return startPC;
	}

	/**
	 * Get the line number for this group of instructions.
	 * 
	 * @return The line number.
	 */
	public int lineNumber() {
		return lineNumber;
	}

	/**
	 * Convert the attribute to a string.
	 * 
	 * @return A string representation of the attribute.
	 */
	public String toString() {
		return "(line #" + lineNumber + " pc=" + startPC + ")";
	}

	public Object clone() {
		return (new LineNumberDebugInfo(this.startPC, this.lineNumber));
	}
}
