package com.ogame.terminal.services.impl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This utility class attempts to extract a single piece of 
 * information from an input, based on a specified regex.
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class SingleValueMatcher {

	final String input;
	final String[] regex;
	
	public SingleValueMatcher (String input, String ... regex) {
		this.input = input;
		this.regex = regex;
	}
	
	public String match () {
		String prevMatched = input;
		for (int i = 0; i < regex.length; i++) {
			Matcher matcher = Pattern.compile(regex [i]).matcher(prevMatched);
			if (matcher.find()) {
				prevMatched = matcher.group();
			}
			
			if (prevMatched == null)
				return null;
		}
		
		return prevMatched;
	}
}
