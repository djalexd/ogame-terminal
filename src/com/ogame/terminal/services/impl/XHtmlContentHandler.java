package com.ogame.terminal.services.impl;

import java.util.Arrays;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.ogame.terminal.services.impl.util.XHtmlContentFormatter;



/**
 * A simple implementation of {@link ContentHandler}, that
 * just outputs XHTML-valid code, from raw HTML.
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class XHtmlContentHandler implements ContentHandler {

	final StringBuilder builder;
	final XHtmlContentFormatter formatter = new XHtmlContentFormatter();
	
	public XHtmlContentHandler(StringBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
		// Delegate to the formatter
		formatter.startElement(uri, localName, qName, atts);
		
		int len = atts.getLength();
		String attributes = "";
		for (int i = 0; i < len; i++) {
			attributes += atts.getLocalName(i) + "=\"" + atts.getValue(i).replaceAll("\"","\'") + "\" ";
		}
		
		attributes = attributes.replaceAll("&", "&amp;").
			replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
		
		char[] prefix = new char [formatter.getCounter()];
		Arrays.fill(prefix, ' ');
		builder.append(new String(prefix) + "<" + qName + " " + attributes + ">\n");		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		char[] prefix = new char [formatter.getCounter()];
		Arrays.fill(prefix, ' ');
		
		builder.append(new String (prefix) + "</" + qName + ">\n");
		
		// Delegate to the formatter
		formatter.endElement(uri, localName, qName);		
	}	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		String copy = new String (Arrays.copyOfRange(ch, start, start + length));
		copy = copy.replaceAll("&", "&amp;").
			replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();

		builder.append(copy);
	}
	
	
	@Override
	public void startDocument() throws SAXException {}
	
	@Override
	public void skippedEntity(String name) throws SAXException {}
	
	@Override
	public void setDocumentLocator(Locator locator) {}
	
	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {}
	
	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {}
	
	@Override
	public void endPrefixMapping(String prefix) throws SAXException {}
	
	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {}
	
	@Override
	public void endDocument() throws SAXException {}	
}
