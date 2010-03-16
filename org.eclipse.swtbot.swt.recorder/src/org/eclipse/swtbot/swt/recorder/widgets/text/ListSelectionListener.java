/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.recorder.widgets.text;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotList;
import org.eclipse.swtbot.swt.recorder.generators.ISWTBotAccessor;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotEvent;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;
import org.eclipse.swtbot.swt.recorder.widgets.ListAccessorCreatorStrategy;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: PushButtonSelectionListener.java 64 2009-01-07 18:43:01Z kpadegaonka $
 */
public class ListSelectionListener extends AbstractTextBasedRecorderListener {

	public ListSelectionListener(ActionList eventList, SWTBot bot) {
		super(SWTBotList.class, eventList, bot);
	}


	protected ISWTBotAccessor createAccessor(Event event) {
		return new ListAccessorCreatorStrategy(event, this, getAnnotation(), bot).create();
	}

	protected SWTBotEvent createEvent(Event event) {
		// Get current selection, and use it as argument for setSelection method.
		// TODO VV: We currently support only one selection.
		String[] selections = ((List) getWidget(event)).getSelection();
		return new SWTBotEvent("setSelection", selections[0]); //$NON-NLS-1$
	}

	protected boolean doCanHandleEvent(Event event) {
		return event.type == SWT.Selection || event.type == SWT.DefaultSelection;
	}
	
	@Override
	protected boolean canHandleWidget(Event event) {
		Widget widget = getWidget(event);
		return matchesWidgetType(widget) && matchesWidgetStyle(widget) && doCanHandleEvent(event);
	}
}
