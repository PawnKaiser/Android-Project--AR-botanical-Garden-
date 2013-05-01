package botanical.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import botanical.main.services.GPS;

import com.example.mouddeneandroidproject.R;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class Navigation extends MapActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		
		startService(new Intent(this, GPS.class));
		Toast.makeText(this, "GPS service started", Toast.LENGTH_LONG).show();
		
		//fetch the map view from the layout
        MapView mapView = (MapView) findViewById(R.id.mapView);
        
        //hybrid map
        
        //set the local position in the map
        MyLocationOverlay mylocationOverlay = new MyLocationOverlay(this, mapView);
        mylocationOverlay.enableMyLocation();
        mapView.getOverlays().add(mylocationOverlay);
        	



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
