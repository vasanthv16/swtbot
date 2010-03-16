package org.eclipse.swtbot.swt.recorder.widgets;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.recorder.widgets.text.AbstractTextBasedRecorderListener;

/**
 * Accessor creator for a Combo. It uses the Combo's old value to create an
 * accessor.
 */
public class ComboAccessorCreatorStrategy extends AccessorCreatorStrategy {

	private String oldValue;

	public ComboAccessorCreatorStrategy(Event event,
			AbstractTextBasedRecorderListener eventListener,
			SWTBotWidget annotation, SWTBot bot, String oldValue) {
		super(event, eventListener, annotation, bot);
		this.oldValue = oldValue;
	}
	
	@Override
	protected String getText(Widget widget) {
		// Use the combo's old value when creating the accessor.
		return oldValue;
	}

}
