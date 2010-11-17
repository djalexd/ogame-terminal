package com.ogame.terminal.domain;

import java.util.ArrayList;
import java.util.HashMap;


public class Planet {
	public static final String UNKNOWN = "UNKNOWN_ID";

	private String id;
	
	private HashMap<String, String> details = 
		new HashMap<String, String>();
	private ArrayList<Resource> resources =
		new ArrayList<Resource>();
	private ArrayList<Building> buildings =
		new ArrayList<Building>();
	
	public Planet () {
		this (UNKNOWN);
	}
	public Planet (String id) {
		this.id = id;
	}

	public HashMap<String, String> getDetails() {
		return details;
	}

	public ArrayList<Resource> getResources() {
		return resources;
	}

	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Planet [id=" + id + ", details=" + details + ", resources="
				+ resources + ", buildings=" + buildings + "]";
	}
}
