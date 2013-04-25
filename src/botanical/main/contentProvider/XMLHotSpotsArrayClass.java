package botanical.main.contentProvider;

import java.util.ArrayList;


public class XMLHotSpotsArrayClass {

    private ArrayList<XMLHotSpot> hotspots;

    /**
     * @return the hotspots
     */
    public ArrayList<XMLHotSpot> getHotspots() {
        return hotspots;
    }

    /**
     * @param Hotspots to set
     */
    public void setHotSpots(ArrayList<XMLHotSpot> hotspots) {
        this.hotspots = hotspots;
    }

    /**
     * @param item The item to add
     */
    public void addHotspot(XMLHotSpot item){
        if(null == hotspots){
        	hotspots = new ArrayList<XMLHotSpot>();
        }
        hotspots.add(item);
    }
}
