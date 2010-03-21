/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Patel - initial API and implementation (Bug 259860)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.matchers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.List;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matcher that matches on a {@link List}'s item values. All the values in a
 * List should match, for the matcher to be successful.
 * 
 * @author Vasanth Velusamy &lt;vasanthv16 [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class WithListItems<T extends List> extends AbstractMatcher<T> {
	private final Matcher<?>	itemsMatcher;

	/**
	 * Matches widgets which contains <code>item(s)</code>, as returned by <code>getItems</code> method, that match
	 * given matcher...i.e List with items "abc", "xyz"
	 * 
	 * @param itemsMatcher the items matcher
	 */
	WithListItems(Matcher<?> itemsMatcher) {
		this.itemsMatcher = itemsMatcher;
	}

	public void describeTo(Description description) {
		description.appendText("with item matching (");
		this.itemsMatcher.describeTo(description);
		description.appendText(")");
	}

	protected boolean doMatch(Object obj) {
		boolean result = false;
		try {
			String[] actualItems = getItems(obj);
			if (this.itemsMatcher.matches(actualItems)) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private String[] getItems(Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return (String[]) SWTUtils.invokeMethod(obj, "getItems");
	}

	public ArrayList<String[]> getAllMatches() {
		// This method is not meaningful for this subclass.
		throw new UnsupportedOperationException();
	}

	public Object get(int index) {
		// This method is not meaningful for this subclass.
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a matcher that matches objects containing all items that matches the matcher.
	 * <p>
	 * <strong>Note:</strong> This invokes getItems method on the object and expects to see an array as a return value.
	 * </p>
	 * 
	 * @param matcher the matcher.
	 * @return a matcher.
	 */
	@Factory
	public static <T extends List> WithListItems<T> withListItems(Matcher<?> matcher) {
		return new WithListItems<T>(matcher);
	}
}
