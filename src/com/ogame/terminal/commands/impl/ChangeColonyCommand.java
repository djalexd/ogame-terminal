package com.ogame.terminal.commands.impl;

import com.ogame.terminal.commands.InputCommandImpl;

public class ChangeColonyCommand extends InputCommandImpl {
	public ChangeColonyCommand(String idOrIndex) {
		super("changeColony", new String[] { idOrIndex });
	}
}
