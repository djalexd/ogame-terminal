package com.ogame.terminal.services.impl.util;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


/**
 * Format the generated XHtml code by simply incrementing a counter
 */
public class XHtmlContentFormatter implements ContentHandler {
	private static final int INCREMENTED = 2;
	
	int counter = 0;

	@Override
	public void setDocumentLocator(Locator locator) {}

	@Override
	public void startDocument() throws SAXException {}

	@Override
	public void endDocument() throws SAXException {}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		counter += INCREMENTED;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		counter -= INCREMENTED;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {}

	@Override
	public void skippedEntity(String name) throws SAXException {}

	
	public int getCounter() {
		return counter;
	}
}
