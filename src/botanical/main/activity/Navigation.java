package botanical.main.activity;

import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import botanical.main.Parser.DomParser;
import botanical.main.services.GPS;

import com.example.mouddeneandroidproject.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * navigation activity
 * @author mouddene
 *
 */
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
        
        populateMap(mapView);
     
	}

	
	
	private void populateMap(MapView mapView){
		
		 //set the local position in the map
      //  MyLocationOverlay mylocationOverlay = new MyLocationOverlay(this, mapView);
      //  mylocationOverlay.enableMyLocation();
      //  mapView.getOverlays().add(mylocationOverlay);
        
        //add all hotspot location
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
        HotspotItemizedOverlay itemizedoverlay = new HotspotItemizedOverlay(drawable, this);
        
        
        /***********************************************************************
         * loop recovering all the hotspot data.
         */
        
        for (Iterator iterator = SplashScreen.hotspots.iterator(); iterator.hasNext();) {
			 overlay = () iterator.next();
			
		}
       
        
       
        
        GeoPoint point = new GeoPoint((int)(15.194226 * 1e6),(int)(54.778701 * 1e6));
        OverlayItem overlayitem = new OverlayItem(point, "Test Mido!", "it's work!");
        
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        
        GeoPoint geoPoint2 = new GeoPoint(-17528941, -149826891);
        OverlayItem overlayitem2 = new OverlayItem(geoPoint2, "Hello from", "Moorea");
        itemizedoverlay.addOverlay(overlayitem2);
		
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
