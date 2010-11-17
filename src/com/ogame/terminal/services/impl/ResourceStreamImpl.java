package com.ogame.terminal.services.impl;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.ogame.terminal.services.AuthDataManager;
import com.ogame.terminal.services.DataProcessor;
import com.ogame.terminal.services.DataStream;
import com.ogame.terminal.services.FormattingService;


public class ResourceStreamImpl implements DataProcessor {
	
	private final static String URL = "http://{0}.ogame.ro/game/index.php?page=overview&session={1}&lgn=1";	
	final AuthDataManager manager;
	final FormattingService formatter;
	
	final Logger logger = Logger.getLogger(ResourceStreamImpl.class);

	@Inject
	public ResourceStreamImpl(AuthDataManager manager, FormattingService formatter) {
		this.manager = manager;
		this.formatter = formatter;
		logger.debug("New instance of: " + this.getClass().getName());
	}

	private void addHeaders (HttpGet get) {
		get.addHeader("Host", manager.getData().getCredentials().getUniverse() + ".ogame.ro");
		get.addHeader("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.126 Safari/533.4");
		get.addHeader("Referer", "http://ogame.ro/");
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
		HttpGet get = new HttpGet(URL.
				replace("{0}", manager.getData().getCredentials().getUniverse()).
				replace("{1}", manager.getData().getSession()));
		
		addHeaders(get);
		
		try {
			formatter.logRequest(get);
			HttpResponse response = client.execute(get);
			formatter.logResponse(response);
			
			return new HttpDataStreamImpl(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setInput(Object data) {
		// TODO Auto-generated method stub
		
	}
}
