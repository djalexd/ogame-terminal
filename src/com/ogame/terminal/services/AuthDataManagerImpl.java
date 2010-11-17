package com.ogame.terminal.services;

import com.ogame.terminal.AuthCredentials;
import com.ogame.terminal.AuthData;

public class AuthDataManagerImpl implements AuthDataManager {
	
	private AuthService authService;
	private AuthData data;

	@Override
	public AuthData getData() {
		return this.data;
	}
	
	@Override
	public void setCredentials(AuthCredentials credentials) {
		assert (this.authService != null) : "This manager only works with a AuthService instance";
		try {
			this.data = this.authService.auth(credentials);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}
}
