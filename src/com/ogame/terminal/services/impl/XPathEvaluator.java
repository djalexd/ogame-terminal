package com.ogame.terminal.services.impl;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathEvaluator {
	// Log everything
	final Logger logger = Logger.getLogger(getClass());

	final Node node;
	final String expression;
	public XPathEvaluator(Node node, String expression) {
		this.node = node;
		this.expression = expression;
		
		logger.debug("New instance of : " + getClass().getName());
		logger.debug("Matching '" + expression + "' on " + node.toString());
	}
	
	public NodeList evaluate () 
	throws XPathExpressionException {
		
		// Parse using xpath
		XPathFactory factory = XPathFactory.newInstance();
		XPathExpression expr = factory.newXPath().compile(expression);
		
		// Write some info here
		final NodeList nodes = (NodeList) expr.evaluate(node, XPathConstants.NODESET);
		logger.debug("Matched " + nodes.getLength() + " nodes with expression '" + expression + "'");
		
		return nodes;
	}
}
