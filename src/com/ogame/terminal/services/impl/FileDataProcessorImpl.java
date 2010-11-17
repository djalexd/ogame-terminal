package com.ogame.terminal.services.impl;

import com.ogame.terminal.services.DataProcessor;
import com.ogame.terminal.services.DataStream;


public class FileDataProcessorImpl implements DataProcessor {
	
	String filename;	
	public FileDataProcessorImpl () {}
	
	@Override
	public DataStream getStream() {
		if (filename == null)
			throw new IllegalStateException(); // TODO Should throw an IOException instead
		return new FileDataStreamImpl(filename);
	}

	@Override
	public void setInput(Object data) {
		if (!(data instanceof String))
			throw new IllegalArgumentException();
		
		this.filename = (String) data;		
	}
}
