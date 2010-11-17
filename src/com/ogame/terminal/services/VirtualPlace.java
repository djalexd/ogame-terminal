package com.ogame.terminal.services;


/**
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public interface VirtualPlace {
	
	/**
	 * Returns an array of parsers for the specified place. 
	 * @return
	 */
	abstract AbstractDataParser[] parsers ();
	
	/**
	 * Visit the virtual place.
	 */
	abstract void go ();
	
	/**
	 * @return Returns the final url that was last visited.
	 */
	abstract Object token ();
}
