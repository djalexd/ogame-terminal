package com.ogame.terminal.services;


/**
 * Exception thrown in {@link AuthService#auth(com.ogame.terminal.AuthCredentials)}
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class AuthException extends Exception {
	public AuthException() {
		super();
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthException(String message) {
		super(message);
	}

	public AuthException(Throwable cause) {
		super(cause);
	}
}
