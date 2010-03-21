/*******************************************************************************
 * Copyright (c) 2008 Vasanth Velusamy and others.

 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Vasanth Velusamy
 *******************************************************************************/
package org.eclipse.swtbot.swt.recorder.widgets.text;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.recorder.generators.ISWTBotAccessor;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotEvent;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;
import org.eclipse.swtbot.swt.recorder.widgets.TreeAccessorCreatorStrategy;

/**
 * Provides support for recording events on a tree.
 * 
 * @author Vasanth Velusamy &lt;vasanthv16 [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class TreeEventListener extends AbstractTextBasedRecorderListener {

	public TreeEventListener(ActionList eventList, SWTBot bot) {
		super(SWTBotTree.class, eventList, bot);
	}


	protected ISWTBotAccessor createAccessor(Event event) {
		return new TreeAccessorCreatorStrategy(event, this, getAnnotation(), bot).create();
	}

	protected SWTBotEvent createEvent(Event event) {
		if(event.type == SWT.Selection || event.type == SWT.DefaultSelection)
		{
			// Get current selection, and use it as argument for setSelection method.
			TreeItem[] selectedTreeItems = ((Tree) getWidget(event)).getSelection();
			
			String[] selections = getValuesForTreeItems(selectedTreeItems);
			
			return new SWTBotEvent("setSelection", selections); //$NON-NLS-1$
		}
		else if(event.type == SWT.Expand)
		{
			// TODO VV: Add support for expanding a tree node.
		} else if(event.type == SWT.Collapse)
		{
			// TODO VV: Add support for collapsing a tree node.
		}
		
		return null;
	}

	/**
	 * Returns an array of values corresponding to the specified list of tree
	 * items.
	 * 
	 * @param treeItems
	 * @return
	 */
	public static String[] getValuesForTreeItems(TreeItem[] treeItems) {
		String[] values = new String[treeItems.length];
		for (int i = 0; i < treeItems.length; i++) {
			TreeItem treeItem = treeItems[i];
			values[i] = treeItem.getText();
		}

		return values;
	}


	protected boolean doCanHandleEvent(Event event) {
		return event.type == SWT.Selection
				|| event.type == SWT.DefaultSelection
				|| event.type == SWT.Expand || event.type == SWT.Collapse;
	}
	
	@Override
	protected boolean canHandleWidget(Event event) {
		Widget widget = getWidget(event);
		return matchesWidgetType(widget) && matchesWidgetStyle(widget) && doCanHandleEvent(event);
	}
}
