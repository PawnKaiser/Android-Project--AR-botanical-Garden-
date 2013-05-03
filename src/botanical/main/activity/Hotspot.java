package botanical.main.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import botanical.main.services.TTS;
import botanical.main.util.HotSpotModel;

import com.example.mouddeneandroidproject.R;

public class Hotspot extends Activity {
	
	
	private HotSpotModel hsm  ;
	private View image;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotspot);
		
		
		Intent intent = new Intent(this, TTS.class);
		PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
		
		
		Bundle b = this.getIntent().getExtras();
		if(b!=null)
	        hsm = (HotSpotModel)getIntent().getSerializableExtra("hotspot");
		
		if(hsm != null){
		
			ImageView  image =  (ImageView) findViewById(R.id.imageView1);
			TextView  text =  (TextView) findViewById(R.id.textView1);
			
			text.setBackgroundColor(Color.argb(255, 255, 255, 255));
			
			
			if(hsm.getId().equals("Sequoia")){
				image.setImageResource(R.drawable.sequoia);
				text.setText(R.string.Sequoia);
			}
			
			if(hsm.getId().equals("Erablepeau")){
				image.setImageResource(R.drawable.erablepeau);
				text.setText(R.string.Sequoia);
			}
			
			if(hsm.getId().equals("PlatanedOrient")){
				image.setImageResource(R.drawable.platanedorient);
				text.setText(R.string.Sequoia);
			}
			
			
			if(hsm.getId().equals("HêtreFauFayard")){
				image.setImageResource(R.drawable.platanedorient);
				text.setText(R.string.Sequoia);
			}
			
			
			if(hsm.getId().equals("Rosierchataigné")){
				image.setImageResource(R.drawable.rosier);
				text.setText(R.string.Sequoia);
			}
			
			
			if(hsm.getId().equals("MahoniadeBeal")){
				image.setImageResource(R.drawable.mahonia);
				text.setText(R.string.Sequoia);
			}
			
			
		}
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotspot, menu);
		return true;
	}

}
