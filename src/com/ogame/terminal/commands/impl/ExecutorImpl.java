package com.ogame.terminal.commands.impl;

import com.ogame.terminal.commands.Executor;
import com.ogame.terminal.commands.InputCommand;

public class ExecutorImpl implements Executor {

	@Override
	public void execute(InputCommand command) {
		InputCommand formattedCmd = tryGuessCmd (command);
	}
	
	private InputCommand tryGuessCmd (InputCommand input) {
		if (input.name() == null)
			throw new IllegalArgumentException();
		if (input.name().length() == 0)
			throw new IllegalArgumentException();
		
		if (input.name().equalsIgnoreCase("changeColony")) {
			// Ensure enough params to the call
			
		}
		
		return null;
	}
}
