package botanical.main.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Compass extends Service {
	
	private float azimuth;
	
	/**
	 * @return the azimuth
	 */
	public float getAzimuth() {
		return azimuth;
	}

	public Compass() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
