package botanical.main.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * @author mouddene
 * this service provides the system services TTS and ...
 */
public class SystemServicesHandler extends Service {
	public SystemServicesHandler() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}