package com.ogame.terminal.services.impl;

import java.io.File;
import java.io.FileOutputStream;

import com.ogame.terminal.services.AbstractDataParser;
import com.ogame.terminal.services.DataManager;
import com.ogame.terminal.services.DataProcessor;


/**
 * This implementation of {@link AbstractDataParser} writes the output
 * of a {@link DataProcessor} to the specified filename.
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class FileWriterDataProcessor implements AbstractDataParser {

	final String filename;
	public FileWriterDataProcessor(String filename) {
		assert (filename != null) : "Filename cannot be null";
		this.filename = filename;
	}

	@Override
	public void parse(DataProcessor processor) {
		XHtmlGenerator gen = new XHtmlGeneratorWithProcessor(processor);
		
		try {
			String buffer = gen.generate();
			
			FileOutputStream out = new FileOutputStream(new File(filename));
			out.write(buffer.getBytes());
			out.close ();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		}
	}

	@Override
	public void setDataManager(DataManager manager) {
		// Does nothing
	}

	@Override
	public DataManager getDataManager() {
		// Does nothing
		return null;
	}
}
