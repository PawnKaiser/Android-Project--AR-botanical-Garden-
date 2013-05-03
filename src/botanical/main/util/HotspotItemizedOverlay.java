package botanical.main.util;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

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
		OverlayItem item = mOverlays.get(index);
		  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		  dialog.setTitle(item.getTitle());
		  dialog.setMessage(item.getSnippet());
		  dialog.show();
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
