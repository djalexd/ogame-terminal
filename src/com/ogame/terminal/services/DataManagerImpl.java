package com.ogame.terminal.services;

import com.ogame.terminal.domain.User;

public class DataManagerImpl implements DataManager {
	private User user;
	
	@Override
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
