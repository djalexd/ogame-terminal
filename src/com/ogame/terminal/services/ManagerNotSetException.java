package com.ogame.terminal.services;

/**
 * This exception should be thrown by any implementation of 
 * {@link AbstractDataParser}, for  
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class ManagerNotSetException extends IllegalStateException {
	public ManagerNotSetException() {
		super();
	}

	public ManagerNotSetException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ManagerNotSetException(String arg0) {
		super(arg0);
	}

	public ManagerNotSetException(Throwable arg0) {
		super(arg0);
	}
}
