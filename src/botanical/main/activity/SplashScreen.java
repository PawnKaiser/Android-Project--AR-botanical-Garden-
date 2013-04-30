package botanical.main.activity;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import botanical.main.broadcastReceiver.NavigationBR;

import com.example.gp1androidproject.R;
/**
* 
* @author mouddene
*
*/
public class SplashScreen extends Activity {
	
	private ProgressBar pb;
	private int mProgressStatus = 0;	
	private Handler mHandler = new Handler();
	public static final String BROADCAST = "android.location.PROVIDERS_CHANGED";
	private NavigationBR broadcatReceiver = new NavigationBR(); 
	protected ProgressDialog mProgressDialog;



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		/**
		* todo task
		*  	XML animation
		*  	startinf the navigation activty with :
		* 			parse the location data file
		* 			passing a list of the hotspot data and bounding box
		* 		stating the broadcast receiver 	
		*/
		pb =  (ProgressBar) findViewById(R.id.progressBar1);
		// Start lengthy operation in a background thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (mProgressStatus < 100) {
					try {
						mProgressStatus = doWork();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// Update the progress bar
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							pb.setProgress(mProgressStatus);
						}
					});
				}
			}
		}).start();
		
	}
	
	/**
	* This method enables the Broadcast receiver for
	* 'android.intent.action.TIME_TICK' intent. This intent get
	* broadcasted every minute.
	*
	* @param view
	*/
	public void registerBroadcastReceiver() {
		// we have to create an unique string to identify the intent
		IntentFilter filtre = new IntentFilter("mouddene.el.mehdi.broadcast");
		registerReceiver(broadcatReceiver, filtre);
	}

	/**
	* This method disables the Broadcast receiver
	*
	* @param view
	*/
	public void unregisterBroadcastReceiver() {

		this.unregisterReceiver(broadcatReceiver);

		Toast.makeText(this,"unregistered broadcst receiver", Toast.LENGTH_SHORT)
		.show();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		IntentFilter filter = new IntentFilter();
		filter.addAction("SOME_ACTION");
		//  registerReceiver(receiver, filter);
	}
	
	/**
	* 
	*	Helper method to start the animation on the splash screen
	*/
	private void AnimateandSlideShow() {
		//todo

	}
	
	/**
	* this method used in the loop iterate the tasks that should be done 
	* to start the next activity
	* @return
	 * @throws InterruptedException 
	*/
	protected int doWork() throws InterruptedException {
		
		
		if(mProgressStatus == 0){
			// starting XML animation
			AnimateandSlideShow();
			this.runOnUiThread(
			new Runnable(){
				public void run(){
					TextView txtView = (TextView)findViewById(R.id.textView2);
					txtView.setText("Chargement de l'animation ...");
				}
			});	
			return 20;
		}
		else
		if(mProgressStatus == 20){
			this.runOnUiThread(
			new Runnable(){
				public void run(){
					TextView txtView = (TextView)findViewById(R.id.textView2);
					txtView.setText("G�n�ration des Donn�es ...");
				}
			});
			
			return 40;
		}else
		
		if(mProgressStatus == 60){
			this.runOnUiThread(
			new Runnable(){
				public void run(){
					TextView txtView = (TextView)findViewById(R.id.textView2);
					txtView.setText("Lancement du Broadcast Receiver ...");
				}
			});
			return 80;
			
		}
		else{
			Thread.sleep(1000);
			// starting Navigation activity		
			this.runOnUiThread(
			new Runnable(){
				public void run(){
					TextView txtView = (TextView)findViewById(R.id.textView2);
					txtView.setText("Application pr�te, Lancement ...");
				}
			});
			Intent intent = new Intent(this, Navigation.class);
			startActivity(intent);
			return 100;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}
	
	
	@Override
	public void onDestroy() {
        finish();   
		super.onDestroy();
	}
}


