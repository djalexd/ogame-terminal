package com.ogame.terminal.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Separate thread that reads user-input.
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class InputWorkerRunnable implements Runnable {

	final Executor executor;
	private boolean running;
	public InputWorkerRunnable(Executor executor) {
		this.executor = executor;
		this.running = true;

		new Thread(this).start();
	}

	@Override
	public void run() {
		BufferedReader reader = 
			new BufferedReader (new InputStreamReader(System.in));
		String command = null;

		while (running) {
			try {
				while ( (command = reader.readLine()) != null) {

					if (!running)
						break;

					int idx = command.indexOf(' ');
					String tokenString = null;
					if (idx != -1) {
						final String cmdName = command.substring(0, idx);
						
						tokenString = command.substring(idx);
						tokenString = tokenString.trim();
						final String[] cmdTokens = tokenString.split(" ");
						
						if (executor != null) {
							executor.execute(new InputCommandImpl(cmdName, cmdTokens));
						}
						
					} else {	
						
						if (executor != null) {
							executor.execute(new InputCommandImpl(command, null));
						}
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println ("Input finished");	
	}
}
