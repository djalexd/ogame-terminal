package com.ogame.terminal;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AuthData implements Serializable {

	private final AuthCredentials credentials;
	private final String session;
	private final String cookies;

	
	public AuthData(AuthCredentials credentials, String session, String cookies) {
		this.credentials = credentials;
		this.session = session;
		this.cookies = cookies;
	}

	public AuthCredentials getCredentials() {
		return credentials;
	}
	
	public String getSession() {
		return session;
	}

	@Override
	public String toString() {
		return "AuthData ["
				+ (credentials != null ? "credentials=" + credentials + ", "
						: "") + (session != null ? "session=" + session : "")
				+ "]";
	}

	public String getCookies() {
		return cookies;
	}	
}
