package com.ogame.terminal.services.impl;

import com.ogame.terminal.services.AbstractDataParser;
import com.ogame.terminal.services.DataManager;

public abstract class BaseDataParser implements AbstractDataParser {
	protected DataManager manager;

	@Override
	public void setDataManager(DataManager manager) {
		this.manager = manager;
	}

	@Override
	public DataManager getDataManager() {
		return this.manager;
	}
}
