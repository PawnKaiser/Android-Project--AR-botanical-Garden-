package botanical.main.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import botanical.main.activity.Hotspot;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class HotspotItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public HotspotItemizedOverlay(Drawable defaultMarker) {
		  super(boundCenterBottom(defaultMarker));
		}
	
	
	public HotspotItemizedOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  setmContext(context);
	}
	
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	

	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}
	
	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	
	
	@Override
	protected boolean onTap(int index) {
		//TODO send to the broadcastReceiver to start the hotspot activity
		Intent intent= new Intent(mContext, Hotspot.class); // no param constructor
	    Bundle b= new Bundle();
	    
	    OverlayItem hs = createItem(index);
	    
	    HotSpotModel hsm = new HotSpotModel(hs.getTitle(), hs.getPoint().getLatitudeE6(), hs.getPoint().getLongitudeE6());
	    
	    b.putSerializable("hotspot", hsm);
	    intent.putExtras(b);
	    mContext.startActivity(intent);
	    return true;
	}


	/**
	 * @return the mContext
	 */
	public Context getmContext() {
		return mContext;
	}


	/**
	 * @param mContext the mContext to set
	 */
	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}
	
}
