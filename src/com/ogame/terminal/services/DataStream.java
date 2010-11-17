package com.ogame.terminal.services;

import java.io.InputStream;


/**
 * A generic way to request data from Ogame. The impl will handle decompression, 
 * formatting, etc. 
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public interface DataStream {

	abstract InputStream stream ();
	
	abstract long wireBytesCount ();
	
	abstract long uncompressedBytesCount ();
	
	abstract String charset ();
}
