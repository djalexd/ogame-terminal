package com.ogame.terminal.services;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.log4j.Logger;


public class FormattingServiceImpl implements FormattingService {
	
	final Logger logger = Logger.getLogger(FormattingServiceImpl.class);
	
	@Override
	public void logRequest(HttpRequestBase request) {
		printRequestLine(request);
		printHeaders(request);
	}

	@Override
	public void logResponse(HttpResponse response) {
		printStatusLine(response);
		printHeaders(response);
		printEntity(response.getEntity());	
	}

	private void printRequestLine(HttpRequestBase request) {
		logger.debug(request.getMethod() + " " + request.getURI().toString());
	}	
	
	private void printStatusLine(HttpResponse response) {
		StatusLine line = response.getStatusLine();
		String status = line.getStatusCode() + " " + line.getReasonPhrase() + 
			" [" + line.getProtocolVersion().toString() + "]";
		logger.debug(status);
	}

	private void printEntity(HttpEntity entity) {
		if (entity != null) {
			logger.debug("Response entity:");
			if (entity.getContentType() != null)
				logger.debug("  > content-type: " + entity.getContentType().getValue());
			logger.debug("  > content-length: " + entity.getContentLength());
			if (entity.getContentEncoding() != null)
				logger.debug("  > content-encoding: " + entity.getContentEncoding().getValue());
			logger.debug("  > content-class: " + entity.getClass().getName());
		}
	}
	
	private void printHeaders(HttpMessage message) {
		logger.debug("Headers:");
		for (Header h : message.getAllHeaders()) {
			logger.debug("  > " + h.getName() + " : " + h.getValue());
		}
	}	
}
