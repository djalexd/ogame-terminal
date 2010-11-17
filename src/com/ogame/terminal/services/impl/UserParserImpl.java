package com.ogame.terminal.services.impl;

import org.w3c.dom.Node;

import com.ogame.terminal.services.DataProcessor;
import com.ogame.terminal.services.UserParser;

public class UserParserImpl extends BaseDataParser implements UserParser {

	@Override
	public void parse(DataProcessor processor) {
		try {			
			Node root = new XHtmlRootNode(processor).root();
			getDataManager().getUser().setName(new XPathValueEvaluator(root, "//span[@class='textBeefy']/text()").value().trim());

			/*
			<div id="playerName">
            	Jucator: <span class="textBeefy">fanitza</span>
        	</div>
			 */
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
