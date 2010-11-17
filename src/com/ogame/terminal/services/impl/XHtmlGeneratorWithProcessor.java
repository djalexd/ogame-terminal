package com.ogame.terminal.services.impl;

import java.io.InputStream;

import com.ogame.terminal.services.DataProcessor;

/**
 * A wrapper around {@link XHtmlGenerator} that gets its {@link InputStream}
 * from a {@link DataProcessor}. 
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class XHtmlGeneratorWithProcessor extends XHtmlGenerator {

	public XHtmlGeneratorWithProcessor(DataProcessor processor) {
		super(processor.getStream().stream());
	}
}
