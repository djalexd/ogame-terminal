package com.ogame.terminal.services.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.ogame.terminal.domain.Planet;
import com.ogame.terminal.domain.User;
import com.ogame.terminal.services.AbstractDataParser;
import com.ogame.terminal.services.DataManager;
import com.ogame.terminal.services.DataManagerImpl;
import com.ogame.terminal.services.DataProcessor;


/**
 * Test case for {@link BuildingParserImpl}
 * 
 * @author Alex Dobjanschi (alex.dobjanschi@gmail.com)
 *
 */
public class TestBuildingParser {
	private static final String DATA_PREFIX = "test/" + 
		TestBuildingParser.class.getPackage().getName().replace('.', '/') + "/";
	
	AbstractDataParser buildingParser;
	DataManager dataManager;
	DataProcessor processor;
	
	@Before
	public void setUp () {
		dataManager = new DataManagerImpl();
		buildingParser = new BuildingParserImpl();
		processor = new FileDataProcessorImpl();
		buildingParser.setDataManager(dataManager);
		
		dataManager.setUser(new User());
		dataManager.getUser().getColonyById(null); // Make a default planet ... TODO This is rather stupid
	}
	
	@After
	public void tearDown () {
		buildingParser = null;
		dataManager = null;
	}
	
	/**
	 * A test that should not fail, none of the buildings are upgrading.
	 */
	@Test
	public void testJustParse () {
		processor.setInput(DATA_PREFIX + "TestBuildingParser.data");
		buildingParser.parse(processor);
		
		// Let's assert
		Planet activeColony = buildingParser.getDataManager().getUser().getActiveColony();
		assertTrue (activeColony != null);
		assertTrue (activeColony.getBuildings() != null);
		assertTrue (activeColony.getBuildings().size() > 0);
		
		// For this test alone, the number of buildings should be 9
		assertTrue (activeColony.getBuildings().size() == 9);
	}
	
	
	/**
	 * A test that should not fail, even if one of the buildings is upgrading.
	 */
	@Test
	public void testParseWhileUpgrading () {
		processor.setInput(DATA_PREFIX + "TestBuildingParser_upgrading.data");
		buildingParser.parse(processor);
		
		// Let's assert		
		Planet activeColony = buildingParser.getDataManager().getUser().getActiveColony();
		assertTrue (activeColony != null);
		assertTrue (activeColony.getBuildings() != null);
		assertTrue (activeColony.getBuildings().size() > 0);			
	}
}
