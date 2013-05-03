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

import com.example.gp1androidproject.R;
/**
* 
* @author mouddene and Tarik Gilani modified it for a sake of simplicity [03/05/2013]
*
*/
public class SplashScreen extends Activity {
	
	private ProgressBar pb;
	private int mProgressStatus = 0;	
	private Handler mHandler = new Handler();
	protected ProgressDialog mProgressDialog;



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

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
	
	@Override
	protected void onResume() {
		super.onResume();

		IntentFilter filter = new IntentFilter();
		filter.addAction("SOME_ACTION");
		//  registerReceiver(receiver, filter);
	}
	
	private void AnimateandSlideShow() {
		//todo

	}
	
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
					txtView.setText("Génération des Données ...");
				}
			});
			
			return 40;
		}else
		
		if(mProgressStatus == 40){
			this.runOnUiThread(
			new Runnable(){
				public void run(){
					TextView txtView = (TextView)findViewById(R.id.textView2);
					txtView.setText("Lancement des services ...");
				}
			});
			return 80;
			
		}
		else{
			Thread.sleep(1000);	
			this.runOnUiThread(
			new Runnable(){
				public void run(){
					TextView txtView = (TextView)findViewById(R.id.textView2);
					txtView.setText("Application prête, Lancement ...");
				}
			});
			Intent intent = new Intent(this, MainMenu.class);
			startActivity(intent);
			finish();
			return 100;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}
	
	
	@Override
	public void onDestroy() {
        finish();   
		super.onDestroy();
	}
}


