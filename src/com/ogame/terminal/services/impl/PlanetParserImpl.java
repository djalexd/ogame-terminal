package com.ogame.terminal.services.impl;

import java.util.HashMap;

import org.w3c.dom.NodeList;

import com.ogame.terminal.domain.Resource;
import com.ogame.terminal.services.DataProcessor;
import com.ogame.terminal.services.PlanetParser;
import com.ogame.terminal.services.impl.util.SingleValueMatcher;



public class PlanetParserImpl extends BaseDataParser implements PlanetParser {

	@Override
	public void parse(DataProcessor processor) {
		try {
			// Just look for these values ...
			XHtmlGenerator gen = new XHtmlGeneratorWithProcessor(processor);
			String generated = gen.generate();

			int idx1 = generated.indexOf("textContent[1]");
			int idx2 = generated.indexOf("textContent[7]");

			generated = generated.substring(idx1, idx2);
			generated = generated.replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">");

			final HashMap<String, String> details = getDataManager().
			getUser().getActiveColony().getDetails();

			details.put("diameter", new SingleValueMatcher(generated, "\\d{1,3}.\\d{1,3}km").match());
			details.put("built", new SingleValueMatcher(generated, "\\(<span>\\d{1,3}", "\\d{1,3}").match());
			details.put("available", new SingleValueMatcher(generated, "\\/<span>\\d{1,3}", "\\d{1,3}").match());
			details.put("tempMin", new SingleValueMatcher(generated, "intre \\-?\\d{1,3}", "\\-?\\d{1,3}").match());
			details.put("tempMax", new SingleValueMatcher(generated, "si \\-?\\d{1,3}", "\\-?\\d{1,3}").match());
			details.put("position", new SingleValueMatcher(generated, "\\[\\d{1,3}:\\d{1,3}:\\d{1,3}\\]").match());

			//<span id="planetNameHeader">
			//p0                </span>
			details.put("name", new XPathValueEvaluator(new XHtmlRootNode(processor).root(), "//span[@id='planetNameHeader']/text()").value().trim());


			// Let's parse the resources
			NodeList resources = new XPathEvaluator(new XHtmlRootNode(processor).root(), "//ul[@id='resources']/li/@title").evaluate();
			for (int i = 0; i < resources.getLength(); i++) {
				String data = resources.item(i).getTextContent();
				if (data.contains("Materie"))
					continue;
				
				String name = new SingleValueMatcher(data, "<B>\\w+:", ">\\w+","\\w+").match();				
				String q1 = new SingleValueMatcher(data, "\\d+(\\.\\d+)?\\/", "\\d+(\\.\\d+)?").match();
				String q2 = new SingleValueMatcher(data, "\\/\\d+(\\.\\d+)??", "\\d+(\\.\\d+)?").match();

				String q3 = null;
				if (!name.equals("Energie")) {
					q3 = new SingleValueMatcher(data, "\\(\\+?\\-?\\d+(\\.\\d+)?\\)", "\\+?\\-?\\d+(\\.\\d+)?").match().replace("+", "");
				} else {
					q3 = "" + (Integer.parseInt(q2.replace(".", "")) - Integer.parseInt(q1.replace(".", "")));
				}
				
				getDataManager().getUser().getActiveColony().getResources().add(
						new Resource(name, Long.parseLong(q1.replace(".", "")), Long.parseLong(q2.replace(".", "")), Integer.parseInt(q3.replace(".", ""))));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
