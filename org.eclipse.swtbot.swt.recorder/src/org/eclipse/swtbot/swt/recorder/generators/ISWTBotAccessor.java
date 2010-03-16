package org.eclipse.swtbot.swt.recorder.generators;

public interface ISWTBotAccessor {

	/**
	 * Returns a string of the format {@code bot.button("Foo")} or {@code bot.button("Foo", 10)}
	 * 
	 * @return the Java representation of this accessor.
	 */
	public String toJava();

}