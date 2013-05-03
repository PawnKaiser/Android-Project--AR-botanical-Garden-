package botanical.main.util;

import java.util.ArrayList;

public class HotSpotModel {
	
	private String id; 
	private float longitude;
	private float latitude;
	private ArrayList<Node> trees = new ArrayList<Node>();
	
	
	/**
	 * @param id
	 * @param longitude
	 * @param lagitude
	 */
	public HotSpotModel(String id, float longitude, float latitude) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}
	/**
	 * @return the lagitude
	 */
	public float getLagitude() {
		return latitude;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param lagitude the lagitude to set
	 */
	public void setLagitude(float lagitude) {
		this.latitude = lagitude;
	}

	
	
	
	
}
