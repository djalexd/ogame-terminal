package com.ogame.terminal;

import java.io.Serializable;

/**
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class AuthCredentials implements Serializable {

	/**
	 * Eclipse genearted serialization id.
	 */
	private static final long serialVersionUID = 1L;

	private final String username;
	private final String password;
	private final String universe;
	
	public AuthCredentials(String username, String password, String universe) {
		this.username = username;
		this.password = password;
		this.universe = universe;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUniverse() {
		return universe;
	}

	@Override
	public String toString() {
		return "AuthCredentials ["
				+ (password != null ? "password=" + password + ", " : "")
				+ (universe != null ? "universe=" + universe + ", " : "")
				+ (username != null ? "username=" + username : "") + "]";
	}
}
