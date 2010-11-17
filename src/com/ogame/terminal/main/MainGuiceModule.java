package com.ogame.terminal.main;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.ogame.terminal.services.AuthDataManager;
import com.ogame.terminal.services.AuthService;
import com.ogame.terminal.services.AuthServiceImpl;
import com.ogame.terminal.services.AuthDataManagerImpl;
import com.ogame.terminal.services.DataManager;
import com.ogame.terminal.services.DataManagerImpl;
import com.ogame.terminal.services.FormattingService;
import com.ogame.terminal.services.FormattingServiceImpl;
import com.ogame.terminal.services.impl.TokenManager;


public class MainGuiceModule extends AbstractModule {
	
	private static class Provider1 implements Provider<AuthDataManager> {
		private final static AuthDataManager _manager =
			new AuthDataManagerImpl();
		
		@Override
		public AuthDataManager get() {
			return _manager;
		}
	}
	
	private static class Provider2 implements Provider<DataManager> {
		private final static DataManager _manager =
			new DataManagerImpl();

		@Override
		public DataManager get() {
			return _manager;
		}
	}
	
	@Override
	protected void configure() {
		bind (FormattingService.class).to(FormattingServiceImpl.class);
		
		// Bind services below (granted that all dependencies have been
		// satisfied above).
		bind (AuthService.class).to(AuthServiceImpl.class);
		bind (AuthDataManager.class).toProvider(Provider1.class);
		bind (DataManager.class).toProvider(Provider2.class);
		
		// This should work, I'm binding an implementation here
		bind (TokenManager.class).in(Singleton.class);
	}
}
