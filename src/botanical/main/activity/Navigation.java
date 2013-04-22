package botanical.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.mouddeneandroidproject.R;

public class Navigation extends Activity {
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    protected LocationManager locationManager;
    //protected Button afficherPositionGeo;
    
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		
		//afficherPositionGeo = (Button) findViewById(R.id.bouton_recup_coordGeo);
		
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );
        showCurrentLocation(locationManager);
        /*
        afficherPositionGeo.setOnClickListener(new OnClickListener() {
    		@Override
            public void onClick(View v) {

                showCurrentLocation(locationManager);
            }
       
    });       
         */
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

    	
    	
    	if ((!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) || (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)))
    	{
    		Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        	String message = String.format(
                    "Position Actuelle (Internet) \n Longitude: %1$s \n Latitude: %2$s",
                    locationNetwork.getLongitude(), locationNetwork.getLatitude()
            );
            Toast.makeText(Navigation.this, message,Toast.LENGTH_LONG).show();	
    	}
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
    		Toast.makeText(Navigation.this, "En cours de recherche du signal... Réessayer", Toast.LENGTH_LONG).show();
    		this.turnGPSOn();
    	}
    		

    } 
    //----------------------------------------------------------------
    //Turn GPS ON [By Tarik]
    private void turnGPSOn(){
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

}
