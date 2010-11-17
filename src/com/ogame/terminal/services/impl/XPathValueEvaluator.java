package com.ogame.terminal.services.impl;

import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XPathValueEvaluator extends XPathEvaluator {

	// Log everything here :)
	final Logger logger = Logger.getLogger(getClass());
	
	public XPathValueEvaluator(Node node, String expression) {
		super(node, expression);
	}
	
	public String value ()
	throws XPathExpressionException {		
		return value (0);
	}
	
	public String value(int index)
	throws XPathExpressionException, IndexOutOfBoundsException {		
		NodeList nodes = super.evaluate();
		if (nodes == null) {
			logger.warn("super.evaluate() returned null");
			return null;
		}
		
		if (nodes.getLength() == 0) {
			logger.warn("** warning, no nodes were selected **");
			return null;
		} else {
			if (index < 0 || index > (nodes.getLength() - 1))
				throw new IndexOutOfBoundsException();
			
			logger.debug("** warning, multiple nodes were selected (" + nodes.getLength() + ")");
			return nodes.item(index).getNodeValue();
		}
	}
	
}
