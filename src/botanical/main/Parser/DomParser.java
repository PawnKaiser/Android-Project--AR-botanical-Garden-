package botanical.main.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import botanical.main.util.HotSpotModel;

/**
 * @author mouddene
 * this class handel all hotspot in the XML Location Data
 * on 
 */

public class DomParser {

	Context mc;
	
	/**
	 * @param mc
	 */
	public DomParser(Context mc) {
		this.mc = mc;
	}

	public   ArrayList<HotSpotModel> parseHotspot(){
		ArrayList<HotSpotModel> hotspots = new ArrayList<HotSpotModel>();
		try {
			
			InputStream is = mc.getApplicationContext().getAssets().open("location.xml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			
			
		 
			doc.getDocumentElement().normalize();
			 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		 
			NodeList nList = doc.getElementsByTagName("hotspot");
		 
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		 		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
		 
					String id        = eElement.getAttribute("id").intern();
					String latitude  = eElement.getElementsByTagName("lat").item(0).getTextContent();
					String longitude = eElement.getElementsByTagName("lon").item(0).getTextContent();
					hotspots.add(new HotSpotModel(id, Float.parseFloat(longitude), Float.parseFloat(latitude)));
					
				}
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
			return hotspots;
	}
	
	
	
	
	 public double DMSToDecimal(String hemisphereOUmeridien,double degres,double minutes,double secondes)
	    {
	            double LatOrLon=0;
	            double signe=1.0;

	            if((hemisphereOUmeridien.equals("W"))||(hemisphereOUmeridien.equals("S"))) {signe=-1.0;}                
	            LatOrLon = signe*(Math.floor(degres) + Math.floor(minutes)/60.0 + secondes/3600.0);

	            return(LatOrLon);               
	    }
	
	
	
}
