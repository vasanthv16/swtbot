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
package org.eclipse.swtbot.swt.recorder.methodargs;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id: AbstractSWTBotEventArguments.java 4 2008-12-16 16:56:08Z kpadegaonka $
 */
public abstract class AbstractSWTBotEventArguments implements SWTBotEventArguments {

	public boolean equals(SWTBotEventArguments other) {
		return asString().equals(other.asString());
	}

}
