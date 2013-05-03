package botanical.main.services;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Toast;

/*--------------------------*/
//Tarik Gilani, le 03/05
/*--------------------------*/


@SuppressLint("ShowToast")
public class TTS extends Service implements OnInitListener
{
	private TextToSpeech tts;
	
	private static final float PUISSANCE_VOIX = (float) 0.7;
	private static final float TEMPO_VOIX = (float) 0.8;
	private String introText = "Service TTS Activé.";
	
	
	public TTS() {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate()
	{
		voiceInit();
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
		
		// On coupe le son
		tts.stop();
		tts.shutdown();
	}

	@SuppressLint("ShowToast")
	@Override
	public void onInit(int arg0) {
		// vérification de la disponibilité  de la synthèse vocale.
		if (arg0 == TextToSpeech.SUCCESS) 
		{
			int result = tts.setLanguage(Locale.FRANCE);
			
			// vérification ici si cette langue est supporté par le terminal et si elle existe
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) 
			Toast.makeText(getBaseContext(), "ERREUR: Le Terminal a un soucis avec la gestion des langues", Toast.LENGTH_SHORT);
			else {
				tts.setSpeechRate(TEMPO_VOIX);
				tts.setPitch(PUISSANCE_VOIX);
				tts.speak(introText, TextToSpeech.QUEUE_FLUSH,  null);
			}
		} 
		else
		// si la synthèse vocal n'est pas disponible
		Toast.makeText(getBaseContext(), "ERREUR: La synthèse vocale n'est pas dispo'", Toast.LENGTH_SHORT);
		
	}	
	//------------------------------------------------
	//Tarik 27/04/2013: Instanciation de notre tts 
	//------------------------------------------------
	private void voiceInit()
	{
		tts = new TextToSpeech(this,this );
	}
	
	//----------------------------------------------------------------
	/* Tarik 23/04/2013 : Méthode pour lire à voix haute notre texte */
	//----------------------------------------------------------------
	public void lisTexteArbre(String info)
	{
		tts.speak(info, TextToSpeech.QUEUE_FLUSH,  null);
	}	
}