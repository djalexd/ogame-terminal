package com.ogame.terminal.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.ogame.terminal.AuthCredentials;

/**
 * Simple reader for user credentials.
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class CredentialsReader {

	private final static String DEFAULT_FILENAME = "credentials.in";
	
	private final String filename;
	public CredentialsReader (String filename) {
		this.filename = filename;
	}
	
	public CredentialsReader () {
		this (DEFAULT_FILENAME);
	}
	
	public final AuthCredentials readCredentials () {
		String user = null;
		String pwd = null;
		String uni = null;

		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
			
			String line = null;
			while ( (line = in.readLine()) != null) {
				line = line.trim();
				
				// Skip empty lines, or lines that start with a '#' (comment)
				if (line.length() == 0 || line.startsWith("#"))
					continue;
				
				if (line.startsWith("user")) {
					if (line.indexOf('=') == -1)
						continue;
					
					user = line.substring(line.indexOf('=') + 1).trim();
				} else if (line.startsWith("pass")) {
					if (line.indexOf('=') == -1)
						continue;
					
					pwd = line.substring(line.indexOf('=') +1).trim();
				} else if (line.startsWith("universe")) {
					if (line.indexOf('=') == -1)
						continue;
					
					uni = line.substring(line.indexOf('=') + 1).trim();
				}
			}
			
			in.close();
		} catch (IOException e) {
			throw new RuntimeException("IOException while reading " + filename, e);
		}
		
		return new AuthCredentials(user, pwd, uni);
	}
}
