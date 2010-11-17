package com.ogame.terminal.services;

import java.io.IOException;

import com.ogame.terminal.AuthCredentials;
import com.ogame.terminal.AuthData;

public interface AuthService {

	/**
	 * Performs a  login operation. Intends to return a
	 * {@link AuthData} with supplied session. 
	 * @param credentials
	 * @throws AuthException Thrown if something goes wrong (bad credentials).
	 * @throws IOException Thrown if something goes wrong on the wire.
	 */
	abstract AuthData auth (AuthCredentials credentials)
				throws AuthException, IOException;
}
