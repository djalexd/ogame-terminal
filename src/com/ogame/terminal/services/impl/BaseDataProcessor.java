package com.ogame.terminal.services.impl;

import com.ogame.terminal.services.DataProcessor;
import com.ogame.terminal.services.DataStream;


public class BaseDataProcessor implements DataProcessor {	
	final DataStream stream;
	Object data;
	
	public BaseDataProcessor (DataStream stream, Object data) {
		this.stream = stream;
		this.data = data;
	}
	
	@Override
	public DataStream getStream() {
		return this.stream;
	}

	@Override
	public void setInput(Object data) {
		this.data = data;
	}
}
