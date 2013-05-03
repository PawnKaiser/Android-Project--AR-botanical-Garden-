package botanical.main.Parser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author mouddene
 * this class handel all hotspot in the XML Location Data
 * on 
 */

public class DomParser {
	
	private String uri ="";
	private ArrayList<HotSpotModel> hotspots = new ArrayList<HotSpotModel>();
	/**
	 * 
	 * @param uri
	 */
	public DomParser(String uri) {
		super();
		this.uri = uri;
	}



	public ArrayList<HotSpotModel> parseHotspot(){
		try {
			 
			File fXmlFile = new File(uri);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		 
			
			doc.getDocumentElement().normalize();
		 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("hotspot");
		 
			System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					String id        = eElement.getElementsByTagName("id").item(0).getTextContent();
					String latitude  = eElement.getElementsByTagName("latitude").item(0).getTextContent();
					String longitude = eElement.getElementsByTagName("longitude").item(0).getTextContent();
					hotspots.add(new HotSpotModel(id, Float.parseFloat(longitude), Float.parseFloat(latitude)));
				}
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
			return hotspots;
	}
	

}
