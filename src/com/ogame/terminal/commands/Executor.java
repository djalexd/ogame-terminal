package com.ogame.terminal.commands;


public interface Executor {
	/**
	 * Execute an input command. Usually there is a 1-to-1 mapping
	 * between {@link InputCommand input commands} and 
	 * {@link Executor executable commands}. 
	 * 
	 * @param command
	 */
	abstract void execute (InputCommand command); 
}
