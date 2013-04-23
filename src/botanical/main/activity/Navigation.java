package botanical.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mouddeneandroidproject.R;

@SuppressWarnings("deprecation")
public class Navigation extends Activity implements SensorEventListener {
	
	/* Par Tarik: 22/04/2013 */
	
	private static final long DISTANCE_MINIMALE_PrMAJ_LaPOSITION = 1; // en mètres
	private static final long TEMPS_MINIMAL_PrMAJ_LaPOSITION = 1000; // en Millisecondes
	
	protected LocationManager locationManager;
	protected Button afficherPositionGeo;
	
	private SensorManager mgr;
	private Sensor compass;
	private TextView text;
	
	
	//onCreate [Lancement] -------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
	
		/* Boussole */
		mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        compass = mgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        text = (TextView) findViewById(R.id.textView3);
        
        
		/* Géolocalisation */
		afficherPositionGeo = (Button) findViewById(R.id.bouton_recup_coordGeo);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(
		LocationManager.GPS_PROVIDER,
		TEMPS_MINIMAL_PrMAJ_LaPOSITION,
		DISTANCE_MINIMALE_PrMAJ_LaPOSITION,
		new MyLocationListener()
		);
		//Tarik: Affichage dynamique des positions (Sans que l'utilisateur ait à appuyer sur le bouton)
		showCurrentLocation(locationManager);
		
		//Tarik: Affichage statique (dans le cas où le dynamique soit quelque peut dysfonctionnel)/Tout Prévoir.com, lol
		afficherPositionGeo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				showCurrentLocation(locationManager);
			}
		});       
	}   
	 @Override
	    protected void onResume() {
	        mgr.registerListener(this, compass, SensorManager.SENSOR_DELAY_NORMAL);
	      super.onResume();
	    }

	    @Override
	    protected void onPause() {
	        mgr.unregisterListener(this, compass);
	      super.onPause();
	    }

	  public void onAccuracyChanged(Sensor sensor, int accuracy) {

	  }

	  public void onSensorChanged(SensorEvent event) {
	            String msg = String.format("X: %8.4f\nY: %8.4f\nZ: %8.4f",
	              event.values[0], event.values[1], event.values[2]);
	        text.setText(msg);
	        text.invalidate();
	  }
	//----------------------------------------------------------------
	/* Tarik: Méthode pour afficher la position actuelle */
	//----------------------------------------------------------------
	
	protected void showCurrentLocation(LocationManager locationManager) 
	{
		

		/* Par Tarik: Si le GPS est désactivé mais par un miracle quelconque
		* on arrive à chopper le Wifi à l'arboretum (Esprit visionnaire quand tu nous tiens...)
		* et bien, on se basera sur le Net pour une plus grande rapidité dans la récupération des positions
		*/

		//Si GPS est inactif et Web est actif -> On se base sur le [Web}
		if ((!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) || (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)))
		{
			Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			String message = String.format(
			"Position Actuelle (Internet) \n Longitude: %1$s \n Latitude: %2$s",
			locationNetwork.getLongitude(), locationNetwork.getLatitude()
			);
			Toast.makeText(Navigation.this, message,Toast.LENGTH_LONG).show();	
		}
		//Si GPS est inactif et Web est inactif aussi -> On se basera évidemment sur le GPS
		else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			Toast.makeText(Navigation.this,"Activation du GPS...", Toast.LENGTH_LONG).show();
			this.turnGPSOn();
			Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			String message = String.format(
			"Position Actuelle GPS \n Longitude: %1$s \n Latitude: %2$s",
			locationGPS.getLongitude(), locationGPS.getLatitude()
			);
			Toast.makeText(Navigation.this, message, Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(Navigation.this, "En cours de recherche du signal... Patience, ça va venir...", Toast.LENGTH_LONG).show();
			this.turnGPSOn();
		}
		

	} 
	//----------------------------------------------------------------
	//Turn GPS ON [By Tarik]
	private void turnGPSOn()
	{
		String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if(!provider.contains("gps"))
		{
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3")); 
			sendBroadcast(poke);
			Toast.makeText(this, "GPS Activé",Toast.LENGTH_SHORT).show();
		}
	}
	//----------------------------------------------------------------
	//Get the Azimuth [By Tarik]
	private float getAzimuth()
	{
		float RR[] = new float[9];
		float orientation[] = new float[3];
		
		float [] orientation1 = SensorManager.getOrientation(RR, orientation);
		float azimuth = orientation1[0];
		
		return azimuth;
	}
	
	//----------------------------------------------------------------
	//Location (GEO) Listener
	private class MyLocationListener implements LocationListener {
		
		public void onLocationChanged(Location location) {
			String message = String.format(
			"Nouvelle Position \n Longitude: %1$s \n Latitude: %2$s",
			location.getLongitude(), location.getLatitude()
			);
			Toast.makeText(Navigation.this, message, Toast.LENGTH_LONG).show();
		}
		
		public void onStatusChanged(String s, int i, Bundle b) {
			Toast.makeText(Navigation.this, "Changement d'état du GPS",
			Toast.LENGTH_LONG).show();
		}
		
		public void onProviderDisabled(String s) {
			Toast.makeText(Navigation.this,
			"GPS Désactivé",
			Toast.LENGTH_LONG).show();
		}
		
		public void onProviderEnabled(String s) {
			Toast.makeText(Navigation.this,
			"GPS Activé",
			Toast.LENGTH_LONG).show();
		}
		
	}
	//----------------------------------------------------------------
	//----------------------------------------------------------------

}
