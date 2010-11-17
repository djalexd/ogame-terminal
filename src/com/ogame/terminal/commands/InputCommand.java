package com.ogame.terminal.commands;

public interface InputCommand {

	/**
	 * Name of the command
	 * @return
	 */
	abstract String name ();
	
	/**
	 * All other params of the command
	 * @return
	 */
	abstract String[] params ();
}
