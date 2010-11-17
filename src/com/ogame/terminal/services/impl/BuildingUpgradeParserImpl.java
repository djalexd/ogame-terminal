package com.ogame.terminal.services.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ogame.terminal.domain.Building;
import com.ogame.terminal.services.BuildingUpgradeParser;
import com.ogame.terminal.services.DataProcessor;

public class BuildingUpgradeParserImpl extends BaseDataParser implements BuildingUpgradeParser {
	Building building;
	
	@Override
	public void parse(DataProcessor processor) {
		try {
			Node node = new XHtmlRootNode(processor).root();
			
			System.out.println ("modus=" + new XPathValueEvaluator(node, "//input[@name='modus']/@value").value());
			System.out.println ("type=" + new XPathValueEvaluator(node, "//input[@name='type']/@value").value());
			
			System.out.println ("current_lvl=" + new XPathValueEvaluator(node, "//span[@class='level']/text()").value());
			
			NodeList list = new XPathEvaluator(node, "//span[@class='cost']").evaluate();
			if (list.getLength() == 0) {
				// TODO LOG THIS
			} else {
				for (int i = 0; i < list.getLength(); i++) {
					System.out.println (list.item(i).getTextContent());
				}
			}
			
			System.out.println ("duration=" + new XPathValueEvaluator(node, "//span[@class='time']/text()").value());
			System.out.println ("req_energy=" + new XPathValueEvaluator(node, "//li[@class='']/span[@class='time']/text()").value());
			
			// POST /game/index.php?page=resources&session=2fb52490bdf4 HTTP/1.1  (application/x-www-form-urlencoded)
			// token=9b43e7b25a1a2e9fa4945994dd603ac5&modus=1&type=1
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
