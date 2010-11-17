package com.ogame.terminal.services;

import com.ogame.terminal.AuthCredentials;
import com.ogame.terminal.AuthData;


public interface AuthDataManager {

	abstract AuthData getData ();
	
	abstract void setCredentials (AuthCredentials credentials);
	
	abstract void setAuthService (AuthService authService);
}
