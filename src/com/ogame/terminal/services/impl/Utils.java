package com.ogame.terminal.services.impl;

import java.io.UnsupportedEncodingException;

import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;

import com.ogame.terminal.services.DataProcessor;

public class Utils {
	public static Parser parser (DataProcessor processor) {
		try {
			return new Parser(new Lexer (
					new Page (processor.getStream().stream(), 
							  processor.getStream().charset())));
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
