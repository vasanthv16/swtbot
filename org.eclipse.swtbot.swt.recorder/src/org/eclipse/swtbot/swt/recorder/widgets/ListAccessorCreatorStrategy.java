/**
 * 
 */
package org.eclipse.swtbot.swt.recorder.widgets;

import static org.hamcrest.Matchers.allOf;

import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.recorder.generators.ISWTBotAccessor;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotItemsBasedAccessor;
import org.eclipse.swtbot.swt.recorder.widgets.text.AbstractTextBasedRecorderListener;
import org.hamcrest.Matcher;

/**
 * @author Vasanth Velusamy
 * 
 */
public class ListAccessorCreatorStrategy extends AccessorCreatorStrategy {

	public ListAccessorCreatorStrategy(Event event,
			AbstractTextBasedRecorderListener eventListener,
			SWTBotWidget annotation, SWTBot bot) {
		super(event, eventListener, annotation, bot);
	}
	
	protected Matcher<Widget> createMatcher(String text) {
		return allOf(typeMatcher(), styleMatcher(), WidgetMatcherFactory.withItems(getItems()));
	}
	
	private String[] getItems()
	{
		return ((org.eclipse.swt.widgets.List) getWidget()).getItems();
	}

	/**
	 * Creates an accessor based on the list's items.
	 */
	@Override
	public ISWTBotAccessor create() {
		Widget widget = getWidget();
		Matcher<Widget> matcher = createMatcher(null);
		List<? extends Widget> similarWidgets = similarWidgets(matcher, widget);
		int index = similarWidgets.indexOf(widget);
		return new SWTBotItemsBasedAccessor("bot", getAnnotation().preferredName(), getItems(), index); //$NON-NLS-1$
	}

}
