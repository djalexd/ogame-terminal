package com.ogame.terminal.services;

/**
 * Marker interface that generates {@link DataStream} objects.
 * Note that {@link #getStream()} doesn't require any parameters, since
 * this is an interface; it's supposed to have all dependencies 
 * supplied within the constructor (better said, before this method
 * is called).
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public interface DataProcessor {
	abstract DataStream getStream ();
	
	abstract void setInput (Object data);
}
