package botanical.main.util;

/**
 * 
 * @author mouddene
 *
 */
public class Node {

	private String name;
	private int bearing;
	private String path;
	/**
	 * @param name
	 * @param bearing
	 */
	public Node(String name, int bearing) {
		this.setName(name);
		this.setBearing(bearing);
	}
	
	
	/**
	 * @param name
	 * @param bearing
	 * @param path
	 */
	public Node(String name, int bearing, String path) {
		this.name = name;
		this.bearing = bearing;
		this.path = path;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the bearing
	 */
	public int getBearing() {
		return bearing;
	}
	/**
	 * @param bearing the bearing to set
	 */
	public void setBearing(int bearing) {
		this.bearing = bearing;
	}
	
	
	
	
}
