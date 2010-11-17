package com.ogame.terminal.services.impl;

import java.io.IOException;
import java.io.InputStream;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XHtmlGenerator {
	final InputStream input;

	public XHtmlGenerator(InputStream input) {
		this.input = input;
	}
	
	public String generate () 
		throws SAXException, IOException {
		
		final Parser p = new Parser();
		final StringBuilder builder = new StringBuilder();
		
		p.setContentHandler(new XHtmlContentHandler(builder));			
		p.parse(new InputSource(input));
		
		return builder.toString();
	}
}
