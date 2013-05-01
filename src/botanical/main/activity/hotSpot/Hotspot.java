package botanical.main.activity.hotSpot;

import com.example.mouddeneandroidproject.R;
import com.example.mouddeneandroidproject.R.layout;
import com.example.mouddeneandroidproject.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Hotspot extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotspot);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotspot, menu);
		return true;
	}

}
