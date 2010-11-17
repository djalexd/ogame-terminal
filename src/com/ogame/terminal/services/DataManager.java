package com.ogame.terminal.services;

import com.ogame.terminal.domain.User;


public interface DataManager {
	abstract User getUser ();
	abstract void setUser (User user);
}
