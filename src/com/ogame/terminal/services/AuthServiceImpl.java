package com.ogame.terminal.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.ogame.terminal.AuthCredentials;
import com.ogame.terminal.AuthData;

public class AuthServiceImpl implements AuthService {
	private static final String AUTH_URL = "http://{0}.ogame.ro/game/reg/login2.php";
	
	final FormattingService formatter;
	private Logger logger = Logger.getLogger(AuthServiceImpl.class);
	
	@Inject
	public AuthServiceImpl(FormattingService formatter) {
		this.formatter = formatter;
		logger.debug("New instance of " + this.getClass().getName());
	}

	/*
	 * uni_id=&v=2&is_utf8=0&uni_url=uni3.ogame.ro&login=fanitza&pass=americanZ
	 */
	private HttpEntity createEntity (AuthCredentials credentials) 
				throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("uni_id=&")
			  .append("v=2&")
			  .append("is_utf8=0&")
			  .append("uni_url=" + credentials.getUniverse() + ".ogame.ro&")
			  .append("login=" + credentials.getUsername() + "&")
			  .append("pass=" + credentials.getPassword());
		
		return new StringEntity(buffer.toString());
	}
	
	private void addRequestHeaders (HttpPost method, Header[] additional) {
		method.addHeader("Accept","application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		method.addHeader("Accept-Encoding","gzip,deflate");
		method.addHeader("Content-Type","application/x-www-form-urlencoded");
		method.addHeader("Origin", "http://ogame.ro");
		method.addHeader("Referer", "http://ogame.ro");
		method.addHeader("User-Agent","Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.126 Safari/533.4");
		method.addHeader("Connection", "Keep-Alive");
		//method.addHeader("Content-Length", "" + method.getEntity().getContentLength());
		method.addHeader("Cache-Control", "0");
		method.addHeader("Accept-Language","en-US,eq;q=0.8");
		method.addHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.3");
		
		if (additional != null && additional.length > 0) {
			String cookies = "";			
			for (Header h : additional) {
				if (h.getName().equals("Set-Cookie")) {
					cookies += h.getValue() + ";";
				}
			}
			method.addHeader("Cookie", cookies);
		}
	}
	
	@Override
	public AuthData auth(AuthCredentials credentials) 
	throws AuthException, IOException {
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(AUTH_URL.replace("{0}", credentials.getUniverse()));
		logger.info("Auth with credentials: " + credentials);
		
		post.setEntity(createEntity(credentials));		
		addRequestHeaders(post, null);
		
		// Put the request in file
		formatter.logRequest(post);
		
		// Execute the request & 
		HttpResponse response = client.execute(post);
		formatter.logResponse(response);
		
		Header[] tryHeaders = response.getHeaders("Location");
		if (tryHeaders != null && tryHeaders.length > 0) {
			// Let's try to get the response from here
			Header h = tryHeaders [0];
			String session = tryGuessSession (h.getValue()); 
			String cookies = getCookiesFromResponse (response);
			if (session != null) {
				// Force deallocation of resources
				client.getConnectionManager().shutdown();
				client = null;
				
				logger.info("Found session parameter: " + session);
				return new AuthData(credentials, session, cookies);
			}
		}
		
		
		Header[] headers = response.getHeaders("Set-Cookie");
		if (headers != null && headers.length > 0) {
			addRequestHeaders(post, headers);
			
			// We will need to instantiate a new connection here
			client.getConnectionManager().shutdown();
			client = null;
			client = new DefaultHttpClient();
			
			response = client.execute(post);
			formatter.logResponse(response);
		}
		
		tryHeaders = response.getHeaders("Location");
		if (tryHeaders != null && tryHeaders.length > 0) {
			// Let's try to get the response from here
			Header h = tryHeaders [0];
			String session = tryGuessSession (h.getValue()); 
			String cookies = getCookiesFromResponse (response);
			if (session != null) {
				// Force deallocation of resources
				client.getConnectionManager().shutdown();
				client = null;
				
				logger.debug("Found session parameter: " + session);
				return new AuthData(credentials, session, cookies);
			}
		}
		
		client.getConnectionManager().shutdown();
		logger.error("", new Exception ("Auth service exception"));
		
		return null;
	}
	
	/*
	 * /game/index.php?page=overview&session=417ef52bb0f9&lgn=1
	 */
	private String tryGuessSession (String data) {
		logger.debug("Guess session from : " + data);
		if (!data.contains("session"))
			return null;
		
		int i1 = data.indexOf("session=") + "session=".length();
		if (data.indexOf('&', i1 + 1) > 0) {
			return data.substring(i1, data.indexOf('&', i1 + 1));
		} else {
			return data.substring(i1);
		}
	}
	
	private String getCookiesFromResponse (HttpResponse response) {
		Header[] headers = response.getHeaders("Set-Cookie");
		String cookies = "";
		for (Header h : headers) {
			String candidate = h.getValue();
			cookies += candidate + "; ";
		}
		
		cookies = cookies.replaceAll("path=/; ", "");
		cookies = cookies.replaceAll("expires=([^;]*); ", "");
		
		String[] cookiesTokens = cookies.split(";");
		String[] prefs = { "prsess", "login", "PHPSESSID" };
		
		cookies = "";
		for (int i = 0; i < prefs.length; i++) {
			for (int j = 0; j < cookiesTokens.length; j++) {
				String tmp = cookiesTokens [j].trim();
				if (tmp.startsWith(prefs [i])) {
					cookies += cookiesTokens [j].trim() + "; ";
					break;
				}
			}
		}
		
		return cookies;
	}
}
