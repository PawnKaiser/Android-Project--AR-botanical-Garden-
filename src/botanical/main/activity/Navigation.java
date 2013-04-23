package botanical.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

public class Navigation extends Activity implements SensorEventListener {
	
	/* Par Tarik: 22/04/2013 */
	
	private static final long DISTANCE_MINIMALE_PrMAJ_LaPOSITION = 1; // en mètres
	private static final long TEMPS_MINIMAL_PrMAJ_LaPOSITION = 1000; // en Millisecondes
	
	protected LocationManager locationManager;
	protected Button afficherPositionGeo;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer, mField;
	private TextView text,text2;
	
	private float[] mGravity;
	private float[] mMagnetic;
	
	//----------------------------------------------------------------------
	//onCreate [Lancement]
	//----------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		
		/*Tarik: Boussole */
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		text = (TextView) findViewById(R.id.textView3);
		text2 = (TextView) findViewById(R.id.textView4);
		
		/*Tarik: Géolocalisation */
		afficherPositionGeo = (Button) findViewById(R.id.bouton_recup_coordGeo);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TEMPS_MINIMAL_PrMAJ_LaPOSITION, DISTANCE_MINIMALE_PrMAJ_LaPOSITION,new MyLocationListener());
		
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
	
	/*----------------------------------
	------------------------------------
	//TARIK: METHODES DE GEOLOCALISATION
	------------------------------------
	-----------------------------------*/
	
	//----------------------------------------------------------------
	/* Tarik 23/04/2013 : Méthode pour afficher la position actuelle */
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
	////Tarik 23/04/2013 : Location (GEO) Listeners
	//----------------------------------------------------------------
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
	
	/*----------------------------------
	------------------------------------
	//TARIK: METHODES D' ORIENTATION
	------------------------------------
	-----------------------------------*/
	
	//----------------------------------------------------------------
	//Tarik 23/04/2013: MAJ Dynamique de la Direction (azimuth)
	//----------------------------------------------------------------
	private void updateDirection() 
	{
		float[] temp = new float[9];
		float[] R = new float[9];
		//Load rotation matrix into R
		SensorManager.getRotationMatrix(temp, null, mGravity, mMagnetic);
		//Remap to camera's point-of-view
		SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_X, SensorManager.AXIS_Z, R);
		//Return the orientation values
		float[] values = new float[3];
		SensorManager.getOrientation(R, values);
		//Convert to degrees
		for (int i=0; i < values.length; i++) {
			Double degrees = (values[i] * 180) / Math.PI;
			values[i] = degrees.floatValue();
		}
		//Display the compass direction
		text.setText( getDirectionFromDegrees(values[0]) );
		//Display the raw values
		text2.setText(String.format("Azimuth: %1$1.2f, Pitch: %2$1.2f, Roll: %3$1.2f",
		values[0], values[1], values[2]));
	}
	//--------------------------------------------------------------------------------------
	//Tarik 23/04/2013: Petite phase de conversion pour avoir l'Azimuth à partir des Degrés
	//--------------------------------------------------------------------------------------
	private String getDirectionFromDegrees(float degrees) 
	{
		if(degrees >= -22.5 && degrees < 22.5) { return "Nord"; }
		if(degrees >= 22.5 && degrees < 67.5) { return "Nord-Est"; }
		if(degrees >= 67.5 && degrees < 112.5) { return "Est"; }
		if(degrees >= 112.5 && degrees < 157.5) { return "Sud-Est"; }
		if(degrees >= 157.5 || degrees < -157.5) { return "Sud"; }
		if(degrees >= -157.5 && degrees < -112.5) { return "Sud-Ouest"; }
		if(degrees >= -112.5 && degrees < -67.5) { return "Ouest"; }
		if(degrees >= -67.5 && degrees < -22.5) { return "Nord-Ouest"; }

		return null;
	}
	//----------------------------------------------------------------
	//Tarik 23/04/2013 : Compass Listeners
	//----------------------------------------------------------------
	protected void onResume() {
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, mField, SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		switch(event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			mGravity = event.values.clone();
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			mMagnetic = event.values.clone();
			break;
		default:
			return;
		}
		
		if(mGravity != null && mMagnetic != null) {
			updateDirection();
		}
	}
	//----------------------------------------------------------------

}
