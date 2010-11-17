package com.ogame.terminal.domain;

import java.util.ArrayList;

public class User {

	private String name;
	private int coloniesCount, maxColoniesAllowed;
	
	private Planet activeColony;
	private ArrayList<Planet> colonies = 
		new ArrayList<Planet>();
	private ArrayList<Message> messages =
		new ArrayList<Message>();
	
	public int getColoniesCount() {
		return coloniesCount;
	}
	
	public void setColoniesCount(int coloniesCount) {
		this.coloniesCount = coloniesCount;
	}
	
	public int getMaxColoniesAllowed() {
		return maxColoniesAllowed;
	}
	
	public void setMaxColoniesAllowed(int maxColoniesAllowed) {
		this.maxColoniesAllowed = maxColoniesAllowed;
	}
	
	public ArrayList<Planet> getColonies() {
		return colonies;
	}
	
	public void setColonies(ArrayList<Planet> colonies) {
		this.colonies = colonies;
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Planet getColonyById (String id) {
		if (id == null) {
			// TODO Active planet, just a mock? What's left here to do?
		}
		
		// Found a colony
		for (Planet p : getColonies()) {
			if (p.getId() != null && p.getId().equals(id))
				return p;
		}
		
		Planet newPlanet = new Planet();
		newPlanet.setId(id);
		getColonies().add(newPlanet);
		
		if (activeColony == null && id == null) {
			// Initial case, we are just setting the active colony here
			activeColony = newPlanet;
		}
		
		return newPlanet;
	}
	
	/**
	 * Add a planet to the list. 
	 * 
	 * @param colony
	 */
	public void addColony (Planet colony) {
		// Validate the data here
		if (colony == null)
			throw new NullPointerException();
		if (colonyExists(colony))
			throw new RuntimeException("Colony already exists");
	
		// Data is valid, just add the colony.
		this.getColonies().add(colony);
	}
	
	public Planet getActiveColony() {
		return activeColony;
	}
	
	public void setActiveColony(Planet activeColony) {
		if (activeColony == null)
			throw new NullPointerException();
		
		// Validate input (the active colony must exist among the
		// list of colonies).
		if (!colonyExists(activeColony)) {
			throw new RuntimeException("Colony doesn't exist");
		}
		
		// Finally set the data
		this.activeColony = activeColony;
	}
	
	private boolean colonyExists (Planet colony) {
		if (colony == null)
			throw new NullPointerException();
		
		for (Planet p : getColonies()) {
			if (p.getId() != null && p.getId().equals(colony.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", coloniesCount=" + coloniesCount
				+ ", maxColoniesAllowed=" + maxColoniesAllowed
				+ ", activeColony=" + activeColony + ", colonies=" + colonies
				+ ", messages=" + messages + "]";
	}
}
