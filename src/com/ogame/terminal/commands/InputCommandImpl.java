package com.ogame.terminal.commands;


public class InputCommandImpl implements InputCommand {
	final String name;
	final String[] params;	
	
	public InputCommandImpl(String name, String[] params) {
		this.name = name;
		this.params = params;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String[] params() {
		return this.params;
	}	
}
