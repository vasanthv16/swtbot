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

import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Similar to {@link WithItem} except that this version requires a match on *all* items through the matcher, instead of just any one.
 * @author
 * @version $Id$
 */
public class WithItems<T extends Item> extends AbstractMatcher<T> {
	private final Matcher<?>	itemsMatcher;

	/**
	 * Matches widgets which contains <code>item(s)</code>, as returned by <code>getItems</code> method, that match
	 * given matcher...i.e List with items "abc", "xyz"
	 * 
	 * @param itemsMatcher the items matcher
	 */
	WithItems(Matcher<?> itemsMatcher) {
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
			T[] actualItems = getItems(obj);
			String[] actualItemValues = getValuesForItems(actualItems);
			if (this.itemsMatcher.matches(actualItemValues)) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Returns an array of values corresponding to the specified list of tree
	 * items.
	 * 
	 * @param items
	 * @return
	 */
	public String[] getValuesForItems(T[] items) {
		String[] values = new String[items.length];
		for (int i = 0; i < items.length; i++) {
			T item = items[i];
			values[i] = item.getText();
		}

		return values;
	}

//	@SuppressWarnings("unchecked")
//	private String[] getItems(Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//		return (String[]) SWTUtils.invokeMethod(obj, "getItems");
//	}

	@SuppressWarnings("unchecked")
	private T[] getItems(Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return (T[]) SWTUtils.invokeMethod(obj, "getItems");
	}

	public ArrayList<T> getAllMatches() {
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
	public
	static <T extends Item> WithItems<T> withItems(Matcher<?> matcher) {
		return new WithItems<T>(matcher);
	}
}
