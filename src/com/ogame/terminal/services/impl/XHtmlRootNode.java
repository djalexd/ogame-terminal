package com.ogame.terminal.services.impl;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.ccil.cowan.tagsoup.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.ogame.terminal.services.DataProcessor;


/**
 * Utility class that uses {@link XHtmlContentHandler} and
 * {@link Parser} to format raw HTML to valid XHTML, then
 * obtains the first node, doing everything for the XPath
 * to proceed (avoiding this trouble!)
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class XHtmlRootNode {
	
	// Used for logging
	final Logger logger = Logger.getLogger(getClass());

	final DataProcessor processor;
	public XHtmlRootNode (DataProcessor processor) {
		this.processor = processor;
		
		logger.debug("New instance of: " + getClass().getName());
		//logger.debug("Processor: " + processor.getStream().toString());
	}

	public Node root () 
	throws IOException {
		try {
			XHtmlGenerator gen = new XHtmlGeneratorWithProcessor(processor);
			//logger.debug("Parsing: " + processor);

			// Also write the bytes
			//logger.debug(gen.generate());
			
			final String xhtml = gen.generate();
			//logger.debug(xhtml);
			Document document = DocumentBuilderFactory.newInstance().
					newDocumentBuilder().parse(new InputSource(
						new StringReader(xhtml)));
			
			logger.debug("Generated a xhtml document of " + (document.getTextContent() != null ? document.getTextContent().length() : "unknown") + " bytes");
			return document.getFirstChild();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new IOException(e);
		} finally {
			// Do nothing for now
		}
	}
}
