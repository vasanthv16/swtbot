package org.eclipse.swtbot.swt.recorder.widgets.text;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotAccessor;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotEvent;
import org.eclipse.swtbot.swt.recorder.listeners.ActionList;
import org.eclipse.swtbot.swt.recorder.widgets.AccessorCreatorStrategy;
import org.eclipse.swtbot.swt.recorder.widgets.ComboAccessorCreatorStrategy;

/**
 * This listener gets a Combo's state on FocusIn, does its thing on Combo's
 * Selection, and clears state info on a Combo's FocusOut. When a Combo's
 * selection happens, we cannot know the Combo's previous value. Hence, we use
 * FocusIn to remember the state.
 */
public class ComboSelectionListener extends AbstractTextBasedRecorderListener {

	/**
	 * Field to remember state between FocusIn, Selection and FocusOut on a Combo.
	 */
	private String comboText;

	public ComboSelectionListener(ActionList eventList, SWTBot bot) {
		super(SWTBotCombo.class, eventList, bot);
	}

	@Override
	protected SWTBotAccessor createAccessor(Event event) {
		// Creates an accessor based on the Combo's previous text value (before there is a selection change).
		AccessorCreatorStrategy accessorCreatorStrategy = new ComboAccessorCreatorStrategy(
				event, this, getAnnotation(), bot, comboText);
		return accessorCreatorStrategy.create();
	}

	protected SWTBotEvent createEvent(Event event) {
		Combo combo = (Combo) event.widget;
		return new SWTBotEvent("setSelection", combo.getText()); //$NON-NLS-1$
	}

	protected boolean doCanHandleEvent(Event event) {
		// On FocusIn, remember the Combo's state.
		if (event.type == SWT.FocusIn) {
			comboText = ((Combo) event.widget).getText();
		}
		// On FocusOut, remove any Combo state info, since it will be obsolete.
		else if (event.type == SWT.FocusOut) {
			comboText = null;
		}

		return event.type == SWT.Selection
				|| event.type == SWT.DefaultSelection;
	}

	protected Shell getShell(Widget widget) {
		if (widget instanceof MenuItem) {
			return ((MenuItem) widget).getParent().getShell();
		}
		return super.getShell(widget);
	}

}
