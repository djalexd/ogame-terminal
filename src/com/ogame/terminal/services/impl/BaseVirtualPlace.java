package com.ogame.terminal.services.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ogame.terminal.services.AbstractDataParser;
import com.ogame.terminal.services.AuthDataManager;
import com.ogame.terminal.services.DataManager;
import com.ogame.terminal.services.DataProcessor;
import com.ogame.terminal.services.FormattingService;
import com.ogame.terminal.services.VirtualPlace;


public abstract class BaseVirtualPlace implements VirtualPlace {
	final DataManager dataManager;
	final TokenManager tokenManager;
	final AuthDataManager authDataManager;
	final FormattingService formattingManager;
	
	final DataProcessor processor;
	final Logger logger = Logger.getLogger(getClass());
	
	public BaseVirtualPlace (AuthDataManager authDataManager, 
			DataManager manager, TokenManager tokenManager, 
			FormattingService formattingManager, 
			DataProcessor processor) {
		
		this.dataManager = manager;
		this.tokenManager = tokenManager;
		this.authDataManager = authDataManager;
		this.formattingManager = formattingManager;
		this.processor = processor;
		
		logger.debug("New instance of " + getClass().getName());
	}

	@Override
	public void go() {
		logger.debug("go() called for " + getClass().getName());
		
		// Get to the active colony and parse data using 
		final Object url = token();
		logger.info(getClass().getName() + ".go() with token " + url.toString());
		
		// Set the input
		processor.setInput(url);
		logger.info(parsers().length + " parsers available");
		
		for (AbstractDataParser parser : parsers ()) {
			Date t1 = new Date();
			
			logger.info("Parsing with " + parser.toString());
			parser.setDataManager(dataManager);
			parser.parse(processor);
			
			Date t2 = new Date ();
			logger.info("Parsed in " + (t2.getTime() - t1.getTime()) + " milliseconds");
		}
		
		try {
			logger.info("Pushed a new token to the token manager: " + url.toString());
			
			// Finally, push the url to the token manager
			tokenManager.push((String) url);
		
		} catch (ClassCastException e) {
			// Ignore this
		}
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public TokenManager getTokenManager() {
		return tokenManager;
	}

	public AuthDataManager getAuthDataManager() {
		return authDataManager;
	}

	public FormattingService getFormattingManager() {
		return formattingManager;
	}
}
