package com.ogame.terminal.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;


public interface FormattingService {

	abstract void logRequest (HttpRequestBase request);
	
	abstract void logResponse (HttpResponse response);
}
