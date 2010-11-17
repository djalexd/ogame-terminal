package com.ogame.terminal.services.impl;

import org.w3c.dom.NodeList;

import com.ogame.terminal.domain.Planet;
import com.ogame.terminal.services.DataManager;
import com.ogame.terminal.services.DataProcessor;
import com.ogame.terminal.services.MyWorldsParser;

public class MyWorldsParserImpl implements MyWorldsParser {
	DataManager manager;

	@Override
	public void parse(DataProcessor processor) {
		
		try {
			String value = new XPathValueEvaluator(new XHtmlRootNode(processor).root(), "//div[@id='myPlanets']/div[@id='countColonies']/p/span/text()").value();
			
			int numExistingColonies = Integer.parseInt(value.substring(0, value.indexOf('/')).trim());
			int numAllowedColonies = Integer.parseInt(value.substring(value.indexOf('/') + 1).trim());
			manager.getUser().setColoniesCount(numExistingColonies);
			manager.getUser().setMaxColoniesAllowed(numAllowedColonies);
			
			NodeList colonies = new XPathEvaluator(new XHtmlRootNode(processor).root(), "//div[@class='smallplanet']/a/@href").evaluate();
			if (colonies.getLength() != numExistingColonies) {
				throw new IllegalStateException("Invalid parsing");
			}
			
			for (int i = 0; i < colonies.getLength(); i++) {
				String href = colonies.item(i).getTextContent();
				if (href.indexOf("cp=") != -1) {
					String colonyId = href.substring(href.indexOf("cp=") + "cp=".length());
					
					// First of all, let's check if we already inited the colonies. If that's the case,
					// we check if any has a 'null' id, then we set it. Otherwise, do nothing. If not all
					// are inited, we just add it
					addColonyWithId(numExistingColonies, colonyId);
					
				} else {
					
					// This is the harder case, there is no colony id (this may be the
					// active colony, or we just haven't parsed correctly).
					addColonyNoId(numExistingColonies);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	private void addColonyWithId(int numExistingColonies, String colonyId) {
		if (manager.getUser().getColonies().size() == numExistingColonies) {
			for (Planet p : manager.getUser().getColonies()) {
				if (p.getId().equals(Planet.UNKNOWN)) {
					// Here we're setting the id of the last-null-id colony
					p.setId(colonyId);
					return;
				}
			}
			
			// At this point, we already looped through all the colonies
			// and found none with null-id. Just ignore it then!
			
		} else {
			
			// However, we might just add the colony ...
			manager.getUser().addColony(new Planet (colonyId));
			
			// Set this as the active colony
			if (manager.getUser().getColonies().size() == 1) {
				manager.getUser().setActiveColony(
					manager.getUser().getColonyById(colonyId));
			}
		}
	}
	
	private void addColonyNoId(int numExistingColonies) {
		if (manager.getUser().getColonies().size() == numExistingColonies) {
			// Do nothing, we already have the number of colonies
		} else {
			
			// Add the colony with null-id for the first time.
			manager.getUser().addColony(new Planet());
			
			// Set this as the active colony
			if (manager.getUser().getColonies().size() == 1) {
				manager.getUser().setActiveColony(
					manager.getUser().getColonyById(Planet.UNKNOWN));
			}
		}
	}	

	@Override
	public void setDataManager(DataManager manager) {
		this.manager = manager;
	}

	@Override
	public DataManager getDataManager() {
		return this.manager;
	}
}
