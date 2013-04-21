package botanical.main.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.mouddeneandroidproject.R;

public class Navigation extends Activity {
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    protected LocationManager locationManager;
    protected Button afficherPositionGeo;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		
		afficherPositionGeo = (Button) findViewById(R.id.bouton_recup_coordGeo);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );

        afficherPositionGeo.setOnClickListener(new OnClickListener() {
    		@Override
            public void onClick(View v) {
                showCurrentLocation();
            }
    });       

    }   
	//----------------------------------------------------------------
	/* Tarik: Méthode pour afficher la position actuelle */
    protected void showCurrentLocation() {

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(Navigation.this, message,
                    Toast.LENGTH_LONG).show();
        }
        else
        {
        	String message = String.format(
                    "Problème Technique: Impossible de charger les cordonnées géographiques"
            );
            Toast.makeText(Navigation.this, message,
                    Toast.LENGTH_LONG).show();	
        }
    }  
    //----------------------------------------------------------------
	private class MyLocationListener implements LocationListener {
	
	    public void onLocationChanged(Location location) {
	        String message = String.format(
	                "New Location \n Longitude: %1$s \n Latitude: %2$s",
	                location.getLongitude(), location.getLatitude()
	        );
	        Toast.makeText(Navigation.this, message, Toast.LENGTH_LONG).show();
	    }
	
	    public void onStatusChanged(String s, int i, Bundle b) {
	        Toast.makeText(Navigation.this, "Provider status changed",
	                Toast.LENGTH_LONG).show();
	    }
	
	    public void onProviderDisabled(String s) {
	        Toast.makeText(Navigation.this,
	                "Provider disabled by the user. GPS turned off",
	                Toast.LENGTH_LONG).show();
	    }
	
	    public void onProviderEnabled(String s) {
	        Toast.makeText(Navigation.this,
	                "Provider enabled by the user. GPS turned on",
	                Toast.LENGTH_LONG).show();
	    }
	
	}

}
