/**
 * 
 */
package org.eclipse.swtbot.swt.recorder.generators;

import org.eclipse.swtbot.swt.finder.utils.internal.Assert;

/**
 * @author Vasanth Velusamy
 * 
 */
public class SWTBotItemsBasedAccessor implements ISWTBotAccessor {

	private final String bot;
	private final String methodCall;
	private final String[] items;
	private final int index;

	public SWTBotItemsBasedAccessor(String bot, String methodCall,
			String[] items, int index) {
		Assert.isNotNull(bot);
		Assert.isNotNull(methodCall);
		Assert.isNotNull(items);
		Assert.isTrue(index >= 0);
		this.bot = bot;
		this.methodCall = methodCall;
		this.items = items;
		this.index = index;
	}

	public SWTBotItemsBasedAccessor(String bot, String methodCall,
			String[] items) {
		this(bot, methodCall, items, 0);
	}

	public String toString() {
		return toJava();
	}

	/**
	 * Returns a string of the format {@code bot.button("Foo")} or {@code
	 * bot.button("Foo", 10)}
	 * 
	 * @return the Java representation of this accessor.
	 */
	public String toJava() {
		String index = ""; //$NON-NLS-1$
		if (this.index != 0)
			index = ", " + this.index; //$NON-NLS-1$

		StringBuilder accessor = new StringBuilder();
		if(this.items != null)
		{
			accessor.append("new String[] { ");
			for(String item : this.items)
			{
				accessor.append("\"").append(item).append("\",");
			}
			// Remove the last comma.
			accessor.setLength(accessor.length()-1);
			accessor.append(" }");
		}

		return bot + "." + methodCall + "(" + accessor + index + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SWTBotItemsBasedAccessor other = (SWTBotItemsBasedAccessor) obj;
		return items.equals(other.items) && bot.equals(other.bot)
				&& (index == other.index)
				&& methodCall.equals(other.methodCall);
	}
}
