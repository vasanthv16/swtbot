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
import java.util.Arrays;

import org.eclipse.swt.widgets.Item;
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
	private final String[] items;

	/**
	 * Matches widgets which contains <code>item(s)</code>, as returned by <code>getItems</code> method, that match
	 * given matcher...i.e List with items "abc", "xyz"
	 * 
	 * @param itemsMatcher the items matcher
	 */
	WithItems(Matcher<?> itemsMatcher) {
		this.itemsMatcher = itemsMatcher;
		this.items = null;
	}

	/**
	 * Matches widgets which contains <code>item(s)</code>, as returned by <code>getItems</code> method, that match
	 * the specified list of items...i.e List with items "abc", "xyz"
	 * 
	 * @param itemsMatcher the items matcher
	 */
	WithItems(String[] items) {
		this.items = items;
		this.itemsMatcher = null;
	}

	public void describeTo(Description description) {
		if(this.itemsMatcher != null)
		{
			description.appendText("with item matching (");
			this.itemsMatcher.describeTo(description);
			description.appendText(")");
		}
		else if(this.items != null)
		{
			description.appendText("with item (");
			description.appendValueList("(", ",", ")", this.items);
			description.appendText(")");
		}
	}

	protected boolean doMatch(Object obj) {
		boolean result = false;
		try {
			String[] actualItems = getItems(obj);
			if (this.itemsMatcher != null && this.itemsMatcher.matches(actualItems)) {
				result = true;
			}
			else if(this.items != null && Arrays.equals(this.items, actualItems))
			{
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

	/**
	 * Returns a matcher that matches objects containing all the specified items.
	 * <p>
	 * <strong>Note:</strong> This invokes getItems method on the object and expects to see an array as a return value.
	 * </p>
	 * 
	 * @param items An array of expected items.
	 * @return a matcher.
	 */
	@Factory
	public
	static <T extends Item> WithItems<T> withItems(String[] items) {
		return new WithItems<T>(items);
	}
}
