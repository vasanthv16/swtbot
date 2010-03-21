/**
 * 
 */
package org.eclipse.swtbot.swt.recorder.widgets;

import static org.hamcrest.Matchers.allOf;

import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.recorder.generators.ISWTBotAccessor;
import org.eclipse.swtbot.swt.recorder.generators.SWTBotItemsBasedAccessor;
import org.eclipse.swtbot.swt.recorder.widgets.text.AbstractTextBasedRecorderListener;
import org.eclipse.swtbot.swt.recorder.widgets.text.TreeEventListener;
import org.hamcrest.Matcher;

/**
 * Accessor creator strategy for a tree. Matches a tree by looking at its treeitems.
 * 
 * @author Vasanth Velusamy
 * 
 */
public class TreeAccessorCreatorStrategy extends AccessorCreatorStrategy {

	public TreeAccessorCreatorStrategy(Event event,
			AbstractTextBasedRecorderListener eventListener,
			SWTBotWidget annotation, SWTBot bot) {
		super(event, eventListener, annotation, bot);
	}
	
	protected Matcher<Widget> createMatcher(String text) {
		return allOf(typeMatcher(), styleMatcher(), WidgetMatcherFactory.withItems(getItems()));
	}
	
	private String[] getItems()
	{
		TreeItem[] treeItems = ((Tree) getWidget()).getItems();
		return TreeEventListener.getValuesForTreeItems(treeItems);
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
