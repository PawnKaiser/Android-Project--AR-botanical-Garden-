package botanical.main.util;

import java.io.Serializable;

public class HotSpotModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; 
	private double longitude;
	private double latitude;
	
	private String text;
	
	private String ressource;
	
	
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
	 * @param id
	 * @param longitude
	 * @param latitude
	 * @param trees
	 */
	public HotSpotModel(String id, double longitude, double latitude) {
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
	}




	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @return the lagitude
	 */
	public double getLagitude() {
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
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param lagitude the lagitude to set
	 */
	public void setLagitude(double lagitude) {
		this.latitude = lagitude;
	}

	



	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}




	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}




	/**
	 * @return the ressource
	 */
	public String getRessource() {
		return ressource;
	}




	/**
	 * @param ressource the ressource to set
	 */
	public void setRessource(String ressource) {
		this.ressource = ressource;
	}

	
	
	
	
}
