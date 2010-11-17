package com.ogame.terminal.main;

import org.apache.log4j.PropertyConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ogame.terminal.AuthCredentials;
import com.ogame.terminal.domain.Building;
import com.ogame.terminal.domain.Planet;
import com.ogame.terminal.domain.Resource;
import com.ogame.terminal.domain.User;
import com.ogame.terminal.places.PlacesModule;
import com.ogame.terminal.places.PlanetPlaces;
import com.ogame.terminal.places.file.FileBasedPlacesModule;
import com.ogame.terminal.services.AuthDataManager;
import com.ogame.terminal.services.AuthService;
import com.ogame.terminal.services.DataManager;
import com.ogame.terminal.services.impl.TokenManager;

/*
 * 1. Login with credentials
 * 2. Generate the initial virtual place
 * 3. Generate all other virtual places & bind them to commands
 * 4. A virtual place will have a specific list of parsers
 */
public class Main {
	final static Injector injector = Guice.createInjector(new MainGuiceModule(), new PlacesModule());	
	//final static Injector injector = Guice.createInjector(new FileGuiceModule(), new FileBasedPlacesModule());

	public static void main (String[] args) 
			throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		
		// Write the first place on the token manager
		TokenManager tokenManager = injector.getInstance(TokenManager.class);
		tokenManager.push("http://ogame.ro");
		
		// Instantiate auth manager`
		auth();
		
		DataManager dataManager = injector.getInstance(DataManager.class);
		dataManager.setUser(new User());
		
		PlanetPlaces places = injector.getInstance(PlanetPlaces.class);
		places.goToOverview();
		places.goToResources();
		
		for (int i = 1; i < dataManager.getUser().getColoniesCount(); i++) {
			try {
				long sleepTime = (long) (1500 + 1500 * Math.random());
				System.out.println ("** Sleeping for " + sleepTime + " miliseconds **");
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {}
			
			places.changeActiveColony("" + i);
			places.goToResources();
		}
		
		// See what came out of parsing the user & active colony
		printUser (dataManager.getUser());
		//System.out.println (dataManager.getUser());
	}

	private static void auth() {
		AuthDataManager authManager = injector.getInstance(AuthDataManager.class);
		authManager.setAuthService(injector.getInstance(AuthService.class));
	}
	
	private static void printUser (User user) {
		System.out.println ("User stats");
		System.out.println (" > name: " + user.getName());
		System.out.println (" > colonies / max_colonies: " + user.getColoniesCount() + " / " + user.getMaxColoniesAllowed());
		for (Planet p : user.getColonies()) {
			System.out.println (" > colony " + p.getDetails().get("name") + " (" + p.getDetails().get("diameter") + " - " + p.getDetails().get("built") + "/" + p.getDetails().get("available") + " | pos=" + p.getDetails().get("position") +" | cp=" + p.getId() + ")");
			for (Resource r : p.getResources()) { 
				System.out.println ("    * " + r.getName() + " => " + r.getQuantity() + " / " + r.getMaxQuantity() + " (" + r.getProduction() + ")");
			}
			
			System.out.println ("     --------");
			for (Building b : p.getBuildings()) {
				System.out.println ("    * " + b.getName() + " (" + b.getLevel() + ")");
			}
			
		}
	}
}
