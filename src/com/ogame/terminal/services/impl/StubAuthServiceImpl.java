package com.ogame.terminal.services.impl;

import java.io.IOException;

import com.ogame.terminal.AuthCredentials;
import com.ogame.terminal.AuthData;
import com.ogame.terminal.services.AuthException;
import com.ogame.terminal.services.AuthService;


public class StubAuthServiceImpl implements AuthService {
	@Override
	public AuthData auth(AuthCredentials credentials) throws AuthException,
			IOException {
		return new AuthData(credentials, null, null);
	}
}
