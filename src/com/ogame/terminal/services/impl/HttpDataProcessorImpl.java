package com.ogame.terminal.services.impl;

import java.util.ArrayList;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.ogame.terminal.services.AuthDataManager;
import com.ogame.terminal.services.DataProcessor;
import com.ogame.terminal.services.DataStream;
import com.ogame.terminal.services.FormattingService;


/**
 * TODO No logging, why not?!
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class HttpDataProcessorImpl implements DataProcessor {
	
	final AuthDataManager manager;
	final FormattingService formatter;
	final TokenManager tokenManager;
	String url;
	
	final Logger logger = Logger.getLogger(ResourceStreamImpl.class);
	ArrayList<Header> headers =
		  new ArrayList<Header>();
	
	boolean addHeaders = true;

	@Inject
	public HttpDataProcessorImpl(AuthDataManager manager, 
			FormattingService formatter, TokenManager tokenManager) {
		
		this.manager = manager;
		this.formatter = formatter;
		this.tokenManager = tokenManager;
		
		logger.debug("New instance of: " + this.getClass().getName());
	}

	/**
	 * Overridable method (customize the headers you want to add for each request).
	 */
	protected void addHeaders (HttpGet get) {
		get.addHeader("Host", manager.getData().getCredentials().getUniverse() + ".ogame.ro");
		get.addHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.126 Safari/533.4");
		
		get.addHeader("Referer",tokenManager.last());		
		get.addHeader("Accept", "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		get.addHeader("Keep-Alive", "115");
		get.addHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		get.addHeader("Accept-Encoding", "gzip");
		get.addHeader("Accept-Language", "en-us,en;q=0.5");
		get.addHeader("Connection", "keep-alive");
		get.addHeader("Cookie", manager.getData().getCookies());
	}

	
	@Override
	public DataStream getStream() {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		
		if (this.isAddHeaders()) {
			addHeaders(get);
		}
		
		try {
			Date t1 = new Date ();
			HttpResponse response = client.execute(get);
			Date t2 = new Date ();
			
			logger.info("Fetched " + url + " in " + (t2.getTime() - t1.getTime()) + " milliseconds");			
			return new HttpDataStreamImpl(response);
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setInput(Object data) {
		if (!(data instanceof String))
			throw new IllegalArgumentException();
		
		this.url = (String) data;
	}

	public boolean isAddHeaders() {
		return addHeaders;
	}

	public void setAddHeaders(boolean addHeaders) {
		this.addHeaders = addHeaders;
	}
	
}
