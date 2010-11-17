package com.ogame.terminal.services.impl;

import java.util.ArrayList;

import com.ogame.terminal.services.VirtualPlace;


/**
 * Handles {@link VirtualPlace#token() tokens}. This class 
 * is used to stack places, offering history management.
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class TokenManager {
	final ArrayList<String> history =
		new ArrayList<String>();
	
	/**
	 * Push a new token to the end of the array. 
	 * @param newToken
	 */
	public final void push (String newToken) {
		if (newToken == null)
			throw new IllegalArgumentException("Cannot have null token");
		history.add(newToken);
	}
	
	/**
	 * Returns the last token from history, or <code>null</code> if
	 * none was found.
	 * @return
	 */
	public final String last () {
		if (history.size() == 0) {
			// For no history, just return null. The application
			// will decide flow from there. Throwing an exception
			// is too expensive actually, this is why it's not used
			return null;
		}
		
		return history.get(history.size() - 1);
	}
	
	/**
	 * Utility method that returns the length of history.
	 * @return
	 */
	public final int getHistoryLength () {
		return history.size();
	}
}
