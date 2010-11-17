package com.ogame.terminal.services.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ogame.terminal.domain.Building;
import com.ogame.terminal.services.BuildingParser;
import com.ogame.terminal.services.DataProcessor;


public class BuildingParserImpl extends BaseDataParser implements BuildingParser {
	String token = null;

	@Override
	public void parse(DataProcessor processor) {
		try {

			Node node = new XHtmlRootNode(processor).root();
			NodeList nodes = new XPathEvaluator(node, "//ul/li[starts-with(@id,\"button\")]//span[@class='level']").evaluate();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node buildingNode = nodes.item(i);
				String name = new XPathValueEvaluator(buildingNode, "./span[@class='textlabel']/text()").value();
				if (name == null) {
					// try somewhere else
					name = new XPathValueEvaluator(node, "//ul/li[starts-with(@id,\"button\")]//div[@title]/@title").value();
				}
				
				if (name == null) {
					throw new RuntimeException();
				}
				
				String lvl = null;
				try {
					lvl = new XPathValueEvaluator (buildingNode, "./text()").value(1);
				} catch (IndexOutOfBoundsException e) {
					// Let's try another trick :)
					lvl = new XPathValueEvaluator(buildingNode, "./text()").value();
				}
				
				Building building = new Building(name.trim(), Integer.parseInt(lvl.trim()));
				if (!getDataManager().getUser().getActiveColony().getBuildings().contains(building))
					getDataManager().getUser().getActiveColony().getBuildings().add(building);

				if (token == null) {
					String value = new XPathValueEvaluator(node, "//ul/li[starts-with(@id,\"button\")]//a[starts-with(@class,'fastBuild')]/@onclick").value();
					if (value == null) {
						continue;
					}
					
					int indexOf = value.indexOf("token");
					if (indexOf != -1) {
						value = value.substring(indexOf + "token".length() + 1);
						value = value.substring(0, value.lastIndexOf('\''));

						token = value;
						System.out.println ("Found token: " + token);
					}
				}


				// POST /game/index.php?page=resources&session=2fb52490bdf4&ajax=1 HTTP/1.1
				// 
				/*
				 * 
				 * 

<input type="hidden" name="modus" value="1">
<input type="hidden" name="type" value="1">

        <div id="demolish1" style="display: none;">
    <table cellpadding="0" cellspacing="0" class="demolishinfo">
    <tr>
        <td class="res">Metal:</td>
        <td class="value ">11.677</td>
    </tr>
    <tr>
        <td class="res">Cristal:</td>
        <td class="value ">2.919</td>
    </tr>
</table>        </div>

<div id="resources_1_Xlarge" class="pic">
</div>
<div id="content">
<h2>Mina de Metal</h2>
<span class="level">
Nivel 15
        <span class="undermark">
        </span>

</span>

<a id="close"
   class='close_details'
   href="#"
   onclick="gfSlider.hide(getElementByIdWithCache('detail')); return false;">
</a>
<div class="clearfloat"></div>
<div id="costswrapper" style="margin-top:10px;">
Necesar pentru a imbunatati la nivelul 16:    <div id="costs">
        <ul id="resources">
            <li class="metal tipsStandard" title="|26.273 Metal">
                <img src="img/layout/ressourcen_metall.gif"><br>
                <span class="cost ">
                    26.273                </span>
            </li>
            <li class="metal tipsStandard" title="|6.568 Cristal">
                <img src="img/layout/ressourcen_kristal.gif"><br>
                <span class="cost ">
                    6.568                </span>
            </li>
        </ul>

        <a class="build-it"
           href="#"
           onClick="sendBuildRequest();"
>
            <span class="textlabel">Dezvolta</span>
        </a>

        <br class="all"/>
    </div>

    <div id="action">
        <ul>
            <li>
                Durata productiei:                <span class="time">
                    4h 22m 44s                </span>
            </li>
            <li class="">



                                        Energie Necesara:
                    <span class="time">
                    109                </span>


            </li>
            <li class="techtree">
                <a href="#"
                   class="tipsStandard"
                   title="|Nici o cerinta disponibila"
                   onClick="
    return false;
">
                    <span class="disabled"></span>
                    <span class="label">
                        Cerinte                    </span>
                </a>
            </li>
            <li class="demolish">
                <a class="tipsDemolishcosts"
                    title="Deconstructia costa:"
                    href="#demolish1"
                    rel="#demolish1"
                    onClick="                             demolishBuilding(1,'Degradeaza Mina de Metal cu un nivel?');
return false;">
                    <span class="pic"></span>
                    <span class="label">
                        Darama                    </span>
                </a>
            </li>
        </ul>
    </div>
</div>
</div>
<br clear="all"/>
<div id="description">
    <p>
        <a class="tipsStandard help"
            href="#" onClick="popupWindow('index.php?page=techinfo&session=2fb52490bdf4&techID=1','techinfo','auto','no','0','0','no','680','600','yes');"
            title="|Mai multe detalii">
        </a>
        <span>Folosite in extractia minereului de fier, minele de metal sunt prioritate pentru toate imperiile in crestere.</span>
    </p>


</div>

<script>

</script>
				 */
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
