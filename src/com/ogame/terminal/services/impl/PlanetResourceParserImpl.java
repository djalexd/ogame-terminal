package com.ogame.terminal.services.impl;


import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.NodeVisitor;

import com.ogame.terminal.domain.Planet;
import com.ogame.terminal.domain.Resource;
import com.ogame.terminal.services.DataManager;
import com.ogame.terminal.services.DataProcessor;
import com.ogame.terminal.services.PlanetResourceParser;



public class PlanetResourceParserImpl implements PlanetResourceParser {
	
	final Logger logger = Logger.getLogger(PlanetResourceParserImpl.class);
	Planet planet;

	private void extractResources(NodeList nodes) {
		
		planet.getResources().clear();
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.elementAt(i);
			n.accept(new NodeVisitor() {

				@Override
				public void visitTag(Tag tag) {
					String title = tag.getAttribute("title");
					title = title.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&#39;", "'");
					
					title = title.replaceAll("<([^\\<\\>])*>", "");
					title = title.replaceAll("\\|", "");
					
					String[] tokens = title.split("[:/\\(\\)]");
					if (tokens.length == 4) {
						final Resource resource = new Resource(
								tokens [0].trim(), 
								Long.parseLong(tokens [1].trim().replaceAll("\\.", "")), 
								Long.parseLong(tokens [2].trim().replaceAll("\\.", "")), 
								Integer.parseInt(tokens [3].trim().replaceAll("\\.", "").replaceAll("\\+", "")));
						
						planet.getResources().add(resource);
					} else {
						logger.info("Warning. Unable to parse this line: " + title);
					}
				}

				@Override
				public boolean shouldRecurseChildren() {
					return false;
				}					
			});
		}
	}


	@Override
	public void parse(DataProcessor processor) {
		try {
			// Instantiate the parser
			Parser p = Utils.parser(processor);
			
			// Extract the node list.
			NodeList nodes = p.extractAllNodesThatMatch(
					new AndFilter(new NodeFilter[] {
							
				new HasParentFilter(new TagNameFilter("ul")),
				new TagNameFilter("li"),
				new HasAttributeFilter("class")
			}));
			
			// Extract data in this step.
			extractResources(nodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void setDataManager(DataManager manager) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public DataManager getDataManager() {
		// TODO Auto-generated method stub
		return null;
	}
}
