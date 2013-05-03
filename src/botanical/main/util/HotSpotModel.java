package botanical.main.util;

import java.util.ArrayList;

public class HotSpotModel {
	
	private String id; 
	private double longitude;
	private double latitude;
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
	 * @return the trees
	 */
	public ArrayList<Node> getTrees() {
		return trees;
	}

	/**
	 * @param trees the trees to set
	 */
	public void setTrees(ArrayList<Node> trees) {
		this.trees = trees;
	}

	
	
	
	
}
