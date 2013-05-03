package botanical.main.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import botanical.main.Parser.DomParser;
import botanical.main.services.GPS;
import botanical.main.util.HotSpotModel;
import botanical.main.util.HotspotItemizedOverlay;

import com.example.mouddeneandroidproject.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * navigation activity
 * @author mouddene
 *
 */
public class Navigation extends MapActivity {
	
	ArrayList<HotSpotModel> hotspots = new ArrayList<HotSpotModel>();

	
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
        MyLocationOverlay mylocationOverlay = new MyLocationOverlay(this, mapView);
        mylocationOverlay.enableMyLocation();
        mapView.getOverlays().add(mylocationOverlay);
        
        //add all hotspot location
        
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		HotspotItemizedOverlay itemizedoverlay = new HotspotItemizedOverlay(drawable, this);
		OverlayItem overlayitem;
        
        /***********************************************************************
         * loop recovering all the hotspot data.
         */
		hotspots =new DomParser(this).parseHotspot();
		 
		
        for (Iterator<HotSpotModel> iterator = hotspots.iterator(); iterator.hasNext();) {
			  HotSpotModel hsm = iterator.next();  
			  GeoPoint p = new GeoPoint((int)(hsm.getLagitude() * 1e6),(int)(hsm.getLongitude() * 1e6));
			  overlayitem = new OverlayItem(p, hsm.getId(), "");
			  itemizedoverlay.addOverlay(overlayitem);	  
		}
        
		mapOverlays.add(itemizedoverlay);
	
        

		
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
