package botanical.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gp1androidproject.R;

public class MainMenu extends Activity implements OnClickListener {
	
	//Variables
	
	MediaPlayer mp;
	private boolean flagMusique=false;
	
	//Boutons Menu Principal
	private Button listeArbresButton;
	private Button navigationButton;
	private Button aProposButton;
	
	
	//----------------------------------------------------------------------
	//Tarik 02/05/2013:onCreate [Lancement Menu]
	//----------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainmenu);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		//Initialisation de l'animation
		animationZoomInit();
		
		//Initialisation de la musique
		musicInit();

		
		
		listeArbresButton = (Button) findViewById(R.id.buttonListeArbres);
		navigationButton = (Button) findViewById(R.id.buttonVisite);
		aProposButton = (Button) findViewById(R.id.buttonNous);
		
		//On appelle notre intent Navigation
		navigationButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent navigationIntent = new Intent(MainMenu.this, Navigation.class);
				startActivity(navigationIntent);
				musicStop();
			}
		});
		
		//On appelle notre intent relatif à la liste des Arbres
		listeArbresButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent listeArbresIntent = new Intent(MainMenu.this, ListeArbres.class);
				startActivity(listeArbresIntent);
				musicStop();
			}
		});
		
		//On appelle notre intent Relatif aux informations
		aProposButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent aProposIntent = new Intent(MainMenu.this, Apropos.class);
				startActivity(aProposIntent);
				musicStop();
			}
		});		

		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	//---------------------------------------------------------
	//Tarik 02/05/2013: Initialisation de la musique d'intro
	//---------------------------------------------------------
	private void musicInit()
	{	
		mp = MediaPlayer.create(getBaseContext(), R.raw.main_track);
		mp.start();
		
		/* Tarik 30/04/2013: On affiche l'icône */
		final ImageView musicIcon = (ImageView)findViewById(R.id.musicIcon);
		musicIcon.setVisibility(View.VISIBLE);
		musicIcon.setImageResource(R.drawable.music);
		
		// On désactive la musique ou on la réactive
		musicIcon.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                if(!flagMusique)
                {
                	musicIcon.setImageResource(R.drawable.music);
                	flagMusique=true;
                	
                	//On coupe la musique
                	musicStop();
            		
                }
                else
                {       
                	musicIcon.setImageResource(R.drawable.music_stopped);
                	flagMusique=false;
                	
                	//On rééclenche la musique
                	mp.start();
                }
            }
        });		
	}	
	//---------------------------------------------------------
	//Tarik 02/05/2013: Arrêt de la musique d'intro
	//---------------------------------------------------------
	private void musicStop()
	{
		mp.pause();
	}
	
	//---------------------------------------------------------
	//Tarik 03/05/2013: Animation zoom surTitre
	//---------------------------------------------------------	
	private void animationZoomInit() 
	{
	    Animation a = AnimationUtils.loadAnimation(this, R.drawable.scale);
	    a.reset();
	    TextView tv = (TextView) findViewById(R.id.TextView01);
	    tv.clearAnimation();
	    tv.startAnimation(a);
	}
	
}
