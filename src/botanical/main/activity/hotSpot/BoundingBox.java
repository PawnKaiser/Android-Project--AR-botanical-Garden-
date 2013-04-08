package botanical.main.activity.hotSpot;

public class BoundingBox {
	
	private float minLat;
	private float minLog;
	private float maxLat;
	private float maxLog;

	public BoundingBox(float latitude, float longitude ) {
		this.minLat = (float) (latitude  - 0.0001) ;
		this.minLog = (float) (longitude - 0.0001);
		this.maxLat = (float) (latitude  + 0.0001);
		this.maxLog = (float) (longitude + 0.0001);
	}

	/**
	 * @return the minLat
	 */
	public float getMinLat() {
		return minLat;
	}

	/**
	 * @return the minLog
	 */
	public float getMinLog() {
		return minLog;
	}

	/**
	 * @return the maxLat
	 */
	public float getMaxLat() {
		return maxLat;
	}

	/**
	 * @return the maxLog
	 */
	public float getMaxLog() {
		return maxLog;
	}
}
