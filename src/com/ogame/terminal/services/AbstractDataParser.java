package com.ogame.terminal.services;


/**
 *<p> It should need access to a local data store. This way we don't 
 * manually set data to it, but let the implementation select its
 * own necessary data. Moreover, doing so not only simplifies selection
 * of input/output data, but removes the parameterized interface 
 * declaration!</p> 
 * 
 * <p></p>
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public interface AbstractDataParser {
	abstract void parse (DataProcessor processor);
	
	abstract void setDataManager (DataManager manager);
	abstract DataManager getDataManager ();
}
