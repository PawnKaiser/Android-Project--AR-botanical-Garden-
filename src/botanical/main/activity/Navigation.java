/* Initially created by Mehdi Moudden and developped by Tarik Gilani *
* M1 Miage - April 2013
* 
*/

package botanical.main.activity;


import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;

import org.w3c.dom.Element;


import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gp1androidproject.R;


@SuppressLint("ShowToast")
public class Navigation extends Activity implements SensorEventListener,TextToSpeech.OnInitListener, OnClickListener {
	
	/*-----------------------------------------------------
	Par Tarik Gilani : Entre le 22/04/2013 et Début Mai 13
	------------------------------------------------------*/
	
	/* Variables GEO */
	/*
	* Tarik 24/04/2013: Allant du principe que les arbres seront espacés au minimum de 1 mètre,
	* Alors la distance minimale pour la MAJ de la position GPS doit être ainsi
	* 
	*/
	private static final long DISTANCE_MINIMALE_PrMAJ_LaPOSITION = 1; // en mètres
	private static final long TEMPS_MINIMAL_PrMAJ_LaPOSITION = 1000; // en Millisecondes
	
	
	
	/* Tarik 26/04/2013: Il y aura une incertitude au niveau de la géolocalisation,
	* Car même étant sur le même point géographique, la nexus ne rendra 
	* pas les mêmes cordonnées, donc, il faut prendre cet aspect en 
	* compte.
	*/
	private static final double BORNE_INCERTITUDE_MIN_POSITION = 0.0000800;
	private static final double BORNE_INCERTITUDE_MAX_POSITION = 0.0000800;
	
	
	
	/* Tarik 26/04/2013: Réglages des paramètres de la voix
	*/
	private static final float PUISSANCE_VOIX = (float) 0.7;
	private static final float TEMPO_VOIX = (float) 0.8;
	private boolean flag=false;
	
	/* Tarik 26/04/2013: Texte Introductif qui sera lu à l'initialisation du TTS
	*/
	private String introText = "Tarik vous souhaite la bienvenue à l'Arboretum !";
	
	
	
	/* Tarik 22/04/2013: Reste des variables */
	protected LocationManager locationManager;
	protected Button afficherPositionGeo;

	private String xmlLatitude;
	private String xmlLongitude;
	
	/* Variables COMPASS */
	private SensorManager mSensorManager;
	private Sensor mAccelerometer, mField;
	private TextView text,geoPosition;
	private float[] mGravity;
	private float[] mMagnetic;
	
	private TextToSpeech tts;
	

	
	/* XML Parsing (Information Display) */
	ImageView image;
	
	//----------------------------------------------------------------------
	//onCreate [Lancement]
	//----------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		
		//---------------------------------
		/*Tarik 26/04/2013: Musique */
		//---------------------------------
		//musicInit();
		//---------------------------------
		/*Tarik 26/04/2013: Voix */
		//--------------------------------
		voiceInit();
		//---------------------------------
		/*Tarik 26/04/2013: Boussole */
		//---------------------------------
		compassInit();
		//---------------------------------
		/*Tarik 26/04/2013: Géolocalisation */
		//---------------------------------
		geoInit();
		//----------------------------------
		
	}  

	/*--------------------------------------------------------
	----------------------------------------------------------
	//TARIK 26/04/2013: Initialisateurs de notre Appli
	----------------------------------------------------------
	---------------------------------------------------------*/	
	
	//------------------------------------------------
	//Tarik 27/04/2013: Initialisation de la musique d'intro
	//------------------------------------------------
	/*
	private void musicInit()
	{
		MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.gp1ar_maintracksfx);
		mp.start();
	}
	*/
	//------------------------------------------------
	//Tarik 27/04/2013: Instanciation de notre tts 
	//------------------------------------------------
	private void voiceInit()
	{
		tts = new TextToSpeech(this,this );
	}
	//---------------------------------------------------------------
	//Tarik 27/04/2013: Initialisation de notre géolocalisation
	//---------------------------------------------------------------
	private void geoInit()
	{
		afficherPositionGeo = (Button) findViewById(R.id.bouton_recup_coordGeo);
		geoPosition = (TextView) findViewById(R.id.textView5);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TEMPS_MINIMAL_PrMAJ_LaPOSITION, DISTANCE_MINIMALE_PrMAJ_LaPOSITION,new MyLocationListener());
		
		//Tarik: Affichage dynamique des positions (Sans que l'utilisateur ait à appuyer sur le bouton)
		try {
			showCurrentLocation(locationManager);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Tarik: Affichage statique (dans le cas où le dynamique soit quelque peut dysfonctionnel)/Tout Prévoir.com, lol
		afficherPositionGeo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					showCurrentLocation(locationManager);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}); 
	}
	//------------------------------------------------
	//Tarik 27/04/2013: Initialisation de notre boussole
	//------------------------------------------------
	private void compassInit()
	{
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		text = (TextView) findViewById(R.id.textView3);
	}	
	
	
	
	/*--------------------------------------------------------
	----------------------------------------------------------
	//TARIK: Listeners Text To Speech
	----------------------------------------------------------
	---------------------------------------------------------*/
	
	@Override
	public void onInit(int status) 
	{
		// vérification de la disponibilité  de la synthèse vocale.
		if (status == TextToSpeech.SUCCESS) 
		{
			int result = tts.setLanguage(Locale.FRANCE);
			
			// vérification ici si cette langue est supporté par le terminal et si elle existe
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) 
			Toast.makeText(getBaseContext(), "ERREUR: Le Terminal a un soucis avec la gestion des langues", Toast.LENGTH_SHORT);
			else {
				tts.setSpeechRate((float) TEMPO_VOIX);
				tts.setPitch((float) PUISSANCE_VOIX);
				//tts.speak(introText, TextToSpeech.QUEUE_FLUSH,  null);
			}
		} 
		else
		// si la synthèse vocal n'est pas disponible
		Toast.makeText(getBaseContext(), "ERREUR: La synthèse vocale n'est pas dispo'", Toast.LENGTH_SHORT);
	}
	
	@Override
	public void onDestroy() {
		// On coupe le son
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}
	
	//----------------------------------------------------------------
	/* Tarik 23/04/2013 : Méthode pour lire à voix haute notre texte */
	//----------------------------------------------------------------
	public void lisTexteArbre(String info)
	{
		tts.speak(info, TextToSpeech.QUEUE_FLUSH,  null);
	}
	
	
	
	/*----------------------------------
	------------------------------------
	//TARIK: METHODES DE GEOLOCALISATION
	------------------------------------
	-----------------------------------*/
	
	//----------------------------------------------------------------
	/* Tarik 23/04/2013 : Méthode pour afficher la position actuelle */
	//----------------------------------------------------------------
	
	protected void showCurrentLocation(LocationManager locationManager) throws IOException, ParserConfigurationException, SAXException 
	{
		/* Par Tarik: Si le GPS est désactivé mais par un miracle quelconque
		* on arrive à chopper le Wifi à l'arboretum (Esprit visionnaire quand tu nous tiens...)
		* et bien, on se basera sur le Net pour une plus grande rapidité dans la récupération des positions
		*/

		//Si GPS est inactif et Web est actif -> On se base sur le [Web}
		if ((!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) || (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)))
		{
			Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			String message = String.format(
			"Position Actuelle (Internet) \n Longitude: %1$s \n Latitude: %2$s",
			locationNetwork.getLongitude(), locationNetwork.getLatitude()
			);
			
			//Toast.makeText(Navigation.this, message, Toast.LENGTH_LONG).show();
			geoPosition.setText(message);
			
			
			String castedLong=String.valueOf(locationNetwork.getLongitude());
			String castedLat=String.valueOf(locationNetwork.getLatitude());

			//Tarik 25/04/2013: XML Hurray !
			trouverNotreArbre(castedLong,castedLat);
			
			
		}
		//Si GPS est inactif et Web est inactif aussi -> On se basera évidemment sur le GPS
		else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			Toast.makeText(Navigation.this,"Activation du GPS...", Toast.LENGTH_LONG).show();
			this.turnGPSOn();
			Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			String message = String.format(
			"Position Actuelle GPS \n Longitude: %1$s \n Latitude: %2$s",
			locationGPS.getLongitude(), locationGPS.getLatitude()
			);
			
			//Toast.makeText(Navigation.this, message, Toast.LENGTH_LONG).show();
			geoPosition.setText(message);
			
			String castedLong=String.valueOf(locationGPS.getLongitude());
			String castedLat=String.valueOf(locationGPS.getLatitude());

			//Tarik 25/04/2013: XML Hurray !
			trouverNotreArbre(castedLong,castedLat);
		}
		else
		{
			Toast.makeText(Navigation.this, "En cours de recherche du signal... Patience, ça va venir...", Toast.LENGTH_LONG).show();
			this.turnGPSOn();
		}
	} 
	//----------------------------------------------------------------
	//Tarik 23/04/2013 :Allumer le GPS
	//----------------------------------------------------------------
	private void turnGPSOn()
	{
		String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if(!provider.contains("gps"))
		{
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3")); 
			sendBroadcast(poke);
			Toast.makeText(this, "GPS Activé",Toast.LENGTH_SHORT).show();
		}
	}
	//----------------------------------------------------------------
	//Tarik 23/04/2013 : Location (GEO) Listeners
	//----------------------------------------------------------------
	private class MyLocationListener implements LocationListener {
		
		public void onLocationChanged(Location location) {
			String message = String.format(
			"Nouvelle Position \n Longitude: %1$s \n Latitude: %2$s", location.getLongitude(), location.getLatitude());
			Toast.makeText(Navigation.this, message, Toast.LENGTH_LONG).show();
			
			String castedLong=String.valueOf(location.getLongitude());
			String castedLat=String.valueOf(location.getLatitude());

			//Tarik 25/04/2013: XML Hurray !
			try {
				trouverNotreArbre(castedLong,castedLat);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void onStatusChanged(String s, int i, Bundle b) {
			Toast.makeText(Navigation.this, "Changement d'état du GPS",
			Toast.LENGTH_LONG).show();
		}
		
		public void onProviderDisabled(String s) {
			Toast.makeText(Navigation.this,
			"GPS Désactivé",
			Toast.LENGTH_LONG).show();
		}
		
		public void onProviderEnabled(String s) {
			Toast.makeText(Navigation.this,
			"GPS Activé",
			Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	
	
	/*----------------------------------
	------------------------------------
	//TARIK: METHODES D' ORIENTATION
	------------------------------------
	-----------------------------------*/
	
	//----------------------------------------------------------------
	//Tarik 23/04/2013: MAJ Dynamique de la Direction (azimuth)
	//----------------------------------------------------------------
	private void updateDirection() 
	{
		float[] temp = new float[9];
		float[] R = new float[9];
		SensorManager.getRotationMatrix(temp, null, mGravity, mMagnetic);
		SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_X, SensorManager.AXIS_Z, R);
		float[] values = new float[3];
		SensorManager.getOrientation(R, values);
		
		//On convertit aux degrés
		for (int i=0; i < values.length; i++) {
			Double degrees = (values[i] * 180) / Math.PI;
			values[i] = degrees.floatValue();
		}
		//On affiche la direction + azimut
		text.setText( getDirectionFromDegrees(values[0])+" et l'azimut exact est:  "+ getExactAzimuthForXMLFile(values[0]));
		
	}
	//--------------------------------------------------------------------------------------
	//Tarik 23/04/2013: Petite phase de conversion pour avoir l'Azimuth à partir des Degrés
	//--------------------------------------------------------------------------------------
	private String getDirectionFromDegrees(float degrees) 
	{
		if(degrees >= -22.5 && degrees < 22.5) { return "Nord"; }
		if(degrees >= 22.5 && degrees < 67.5) { return "Nord-Est"; }
		if(degrees >= 67.5 && degrees < 112.5) { return "Est"; }
		if(degrees >= 112.5 && degrees < 157.5) { return "Sud-Est"; }
		if(degrees >= 157.5 || degrees < -157.5) { return "Sud"; }
		if(degrees >= -157.5 && degrees < -112.5) { return "Sud-Ouest"; }
		if(degrees >= -112.5 && degrees < -67.5) { return "Ouest"; }
		if(degrees >= -67.5 && degrees < -22.5) { return "Nord-Ouest"; }

		return null;
	}
	//Tarik 25/04/2013: Affiche que l'azimut
	private float getExactAzimuthForXMLFile(float degrees){ return degrees; }
	
	//----------------------------------------------------------------
	//Tarik 23/04/2013 : Compass Listeners
	//----------------------------------------------------------------
	protected void onResume() {
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, mField, SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		switch(event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			mGravity = event.values.clone();
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			mMagnetic = event.values.clone();
			break;
		default:
			return;
		}
		
		if(mGravity != null && mMagnetic != null) {
			updateDirection();
		}
	}
	//----------------------------------------------------------------
	//Tarik 26/04/2013 : Temps d'affichage de notre toast en dépend
	// de la longueur du texte relatif à notre arbre
	//----------------------------------------------------------------
	public int tempsNecessaireALaLecture(String text)
	{
		int temps = 0;
		
		if (text.length() <= 100)
		temps = 5000;
		else if (text.length() > 100 && text.length() <= 200)
		temps = 12000;
		else if (text.length() > 200 && text.length() <= 300)
		temps = 17000;
		else if (text.length() > 300 && text.length() <= 400)
		temps = 22000;
		else if (text.length() > 400 && text.length() <= 500)
		temps = 27000;
		else if (text.length() > 500 && text.length() <= 600)
		temps = 35000;
		else if (text.length() > 600 && text.length() <= 700)
		temps = 40000;
		else if (text.length() > 700 && text.length() <= 800)
		temps = 50000;
		else
		temps = 100000;
		
		
		return temps;
	}
	//----------------------------------------------------------------
	
	/*----------------------------------
	------------------------------------
	//TARIK: Recupérer Infos notre Arbre
	 * + Gestion du son + autres bonnes
	 * choses
	------------------------------------
	-----------------------------------*/

	//Tarik (26/04/2013): On souhaite chopper l'arbre qui nous interesse en dépend de sa lat & long
	public void trouverNotreArbre(String maLongitudeActuelle, String maLatitudeActuelle) throws IOException, ParserConfigurationException, SAXException
	{
		//Tarik 24/04/2013: On Charge notre petit XML pour le parser ensuite
		InputStream raw = this.getApplicationContext().getAssets().open("XMLLocationData.xml");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(raw);
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getElementsByTagName("hotspot");

		//Tarik 24/04/2013: On parcourt notre 'tit XML bien sympathique
		for (int i = 0; i < nodeList.getLength(); i++) {

			Node node = nodeList.item(i);
			
			Element premierElement = (Element) node;

			NodeList titreListe = premierElement.getElementsByTagName("title");
			NodeList infoListe = premierElement.getElementsByTagName("information");
			NodeList latitudeListe = premierElement.getElementsByTagName("latitude");
			NodeList longitudeListe = premierElement.getElementsByTagName("longitude");
			NodeList imgListe = premierElement.getElementsByTagName("img");
			
			Element titreElement = (Element) titreListe.item(0);
			Element latitudeElement = (Element) latitudeListe.item(0);
			Element longitudeElement = (Element) longitudeListe.item(0);
			Element imgElement = (Element) imgListe.item(0);
			Element infoElement = (Element) infoListe.item(0);
			
			longitudeListe = longitudeElement.getChildNodes();
			latitudeListe = latitudeElement.getChildNodes();
			imgListe = imgElement.getChildNodes();
			infoListe = infoElement.getChildNodes();
			titreListe = titreElement.getChildNodes();
			
			/* Tarik: On teste si mes coords géo correspondent à une enregistrée dans le XML 
			* Mais on convertit d'abord
			* 
			*/
			
			//Pr Tester la différence (Incertitude géographique), on convertit nos strings en double
			
			xmlLatitude = latitudeListe.item(0).getNodeValue();
			xmlLongitude = longitudeListe.item(0).getNodeValue();
			
			//Cordonnées de notre Arbre d'abord
			double xmlTempDoubleLatitude = Double.parseDouble(xmlLatitude);
			double xmlTempDoubleLongitude = Double.parseDouble(xmlLongitude);
			
			//Ensuite les miennes
			double myTempDoubleLatitude = Double.parseDouble(maLatitudeActuelle);
			double myTempDoubleLongitude = Double.parseDouble(maLongitudeActuelle);
			
			//Meryeme & Tarik: Merci à ma chérie pour ce petit if sympathique, et du travail de réflexion (algo) mené derrière.
			if ((xmlLatitude.equals(maLatitudeActuelle) && xmlLongitude.equals(maLongitudeActuelle)) 
					|| 
					((myTempDoubleLatitude - BORNE_INCERTITUDE_MIN_POSITION <= xmlTempDoubleLatitude && xmlTempDoubleLatitude <= myTempDoubleLatitude + BORNE_INCERTITUDE_MAX_POSITION) 
						&& 
						(myTempDoubleLongitude - BORNE_INCERTITUDE_MIN_POSITION <= xmlTempDoubleLongitude && xmlTempDoubleLongitude <= myTempDoubleLongitude + BORNE_INCERTITUDE_MAX_POSITION)) )
			{		
				int treeResID = getResources().getIdentifier(imgListe.item(0).getNodeValue(), "drawable", getPackageName());
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.activity_navigation_treetoast,
				(ViewGroup) findViewById(R.id.toast_layout_root));
				ImageView image = (ImageView) layout.findViewById(R.id.image);
				image.setImageResource(treeResID);
				
				
				/* Tarik 27/04/2013: On affiche l'icône */
				final ImageView audioIcon = (ImageView)findViewById(R.id.audioIcon);
				audioIcon.setVisibility(View.VISIBLE);
				audioIcon.setImageResource(R.drawable.audio);
				
				final String texteArbre= infoListe.item(0).getNodeValue();
				
				// On désactive la voix ou on la réactive
				audioIcon.setOnClickListener(new OnClickListener()
		        {
		            public void onClick(View v)
		            {
		                if(!flag)
		                {
		                	audioIcon.setImageResource(R.drawable.audio);
		                	flag=true;
		                	//On coupe le son
		                	tts.stop();
		                }
		                else
		                {       
		                    audioIcon.setImageResource(R.drawable.audio_stopped);
		                    flag=false;
		                    //On active le son
		                    lisTexteArbre(texteArbre);
		                    
		                }
		            }
		        });
				
				
				//Tarik: 26/04/2013: On affiche notre beau texte, avec le nom de l'arbre d'abord, suivi de sa description ensuite
				TextView text = (TextView) layout.findViewById(R.id.text);
				text.setText(titreListe.item(0).getNodeValue()+"\n\n"+ "\n" + infoListe.item(0).getNodeValue());

				//Tarik: 26/04/2013: On lit notre texte retrived du xml
				tts.speak(infoListe.item(0).getNodeValue(), TextToSpeech.QUEUE_ADD,  null);
				
				
				final Toast toast = new Toast(getApplicationContext());
				toast.setView(layout);
				
				
				
				/* Tarik 26/04/2013: Bah là on définit le temps de l'affichage de l'info de l'arbre
				* Sur une base expérimentale faite avec mes soins qui affiche le texte avec le temps
				* nécessaire pour le lire en dépend de sa longueur, en partant sur une base de 1sec=1000 milsec
				* 
				*/
				
				new CountDownTimer(tempsNecessaireALaLecture(infoListe.item(0).getNodeValue()), 1000)
				{
					public void onTick(long millisUntilFinished) 
					{
						toast.show();
						
						//On cache le spinner quand on trouve notre arbre
						ProgressBar pg = (ProgressBar) findViewById(R.id.progressBar1);
						pg.setVisibility(View.INVISIBLE);
						
					}
					public void onFinish() 
					{
						toast.show();
						
						//On cache le spinner quand on trouve notre arbre
						ProgressBar pg = (ProgressBar) findViewById(R.id.progressBar1);
						pg.setVisibility(View.VISIBLE);
						audioIcon.setVisibility(View.INVISIBLE);
					}
				}.start();
				
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
