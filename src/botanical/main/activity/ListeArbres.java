package botanical.main.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gp1androidproject.R;


public class ListeArbres extends Activity
{
	
	public static final String[] titles = new String[] { 
		   "Charme",
           "Erable peau serpent", 
           "Hêtre Corse",
           "Platane d'Orient",
           "Rosier châtaigne"
           
           /*
           "Peuplier noir", 
           "Pin deFau Fayard",
           "Mahonia de Beal", 
           "Forsythia blanc",
           "Bois de fer",
           "Podocarpe des Andes", 
           "Hêtre Rouge", 
           "Gincko",
           "Petit Houx / Fragon",
           "Saule Pleureur Piquont", 
           "Arbre aux mouchoirs", 
           "Paulownia / Cyprès",
           "Kolwitzia "
           */
	
	};

    public static final String[] descriptions = new String[] {
           "Les charmes constituent un genre d'arbres et d'arbustes de la famille des Bétulacées (sous-famille des Coryloïdées) des régions tempérées de l'hémisphère nord, d'Asie mineure et d'Europe. Ce genre (Carpinus) compte une trentaine d'espèces. Une forêt constituée principalement de charmes est appelée une charmaie ou une charmeraie. ",
           "Le nom Érable à peau de serpent est un nom générique donné à différentes espèces d'érable en raison de leur écorce striée. Certaines de ces espèces sont aussi appelées 'érable jaspé' (car leur écorce ressemble également au jaspe). Ces érables appartiennent à la section Macrantha de la classification des érables et sont tous originaires d'Asie à l'exception de Acer", 
           "Le Hêtre commun (Fagus sylvatica), couramment désigné simplement comme le Hêtre et parfois appelé fayard est une espèced'arbre à feuilles caduques, indigène d'Europe, appartenant à la famille des Fagaceae, tout comme le chêne et le châtaignier",
           "Le platane d'Orient est une espèce d'arbre de la famille des Platanacées utilisée comme arbre d'ornement. Cette espèce anciennement introduite en Europe occidentale a été supplantée par le platane commun",
           "Le rosier châtaigne (Rosa roxburghii) est une espèce de rosier, classé dans le sous-genre Platyrhodon, originaire de Chine"
           
           /*
           "Le Peuplier noir est un grand arbre pouvant atteindre 30 à 35 m de hauteur1 et dont la longévité est importante (200 ans, voire jusqu'à environ 400 ans pour les spécimens les plus âgés. Les formes varient beaucoup selon le contexte, en particulier selon que l'arbre ait poussé seul et isolé.",
           "Décoratif par son feuillage persistant et par sa superbe floraison en grappes compactes, il illumine les endroits peu ensoleillés et s’utilise en massif, groupe, isolé, haie basse, rocaille, bordure ou peut être cultivé en bac dans lequel il se comportera très bien.",
           "Cet arbrisseau atteint 1 à 2 mètres de haut. Les feuilles sont opposées, simples, longues de 6 à 10 cm pour 4 à 4,5 cm de large et pubescentes sur les deux faces. Les fleurs blanches apparaissent au début du printemps avant l'apparition des nouvelles feuilles. Elles ont un pétale composé de quatre lobes et font environ 1 cm de long. Les fruits sont ronds, ailés et ont un diamètre de 2 à3 cm.",
           "Bois de fer est le nom ou le surnom donné localement à diverses espèces d'arbres à travers le monde ou au bois que ces arbres produisent le gaïac, bois produit par des arbres du genre Guaiacum : Guaiacum officinale ou Guaiacum sanctum ",
           "Les Podocarpaceae ('Podocarpacées') sont une famille de conifères qui compte 185 espèces réparties en 18 genres ",
           "Le hêtre à grandes feuilles est un arbre de taille moyenne pouvant atteindre 25 m de hauteur. Son tronc est droit et sa cime est large. Ses racines sont très étalées.L'écorce est mince et lisse, de couleur gris bleu pâle. Il reste lisse avec l'âge mais devient plus foncé1. Les feuilles sont ovales et mesurent de 6 à 14 cm. Elles sont simples, alternes et dentées grossièrement. Elles sont cireuses et rigides au toucher. Elles sont divisées par 9 à 14 nervures rectilignes et parallèles de chaque côté. Le dessus est de couleur vert satiné et le dessous plus pâle. Les feuilles des petits arbres et des branches inférieures des arbres forestiers matures s'assèchent à l'automne et restent sur l'arbre tout l'hiver", 
           "Le Ginkgo biloba ou « arbre aux quarante écus » ou « arbre aux mille écus » est la seule espèce actuelle de la famille des Ginkgoaceae. Il est la seule espèce actuelle de la division des ginkgophyta. On en connaît sept autres espèces maintenant fossiles et le ginkgo est considéré comme une forme panchronique (dite aussi, en termes plus communs, fossile vivant). C'est la plus ancienne famille d'arbres connue, puisqu'elle serait apparue il y a plus de 270 millions d'années. Elle existait déjà une quarantaine de millions d'années avant l'apparition des dinosaures.",
           "Le fragon faux houx ou petit-houx (Ruscus aculeatus) est un arbuste dioïque de la famille des Asparagaceae (ou des Liliaceae, selon la classification classique) poussant dans l'aire méditerranéenne-atlantique",
           "Le saule (Salix) est un genre d'arbres, d'arbustes, d'arbrisseaux de la famille des Salicacées (Salicaceae). Il comprend 360espèces environ, réparties à travers le monde, principalement dans les zones fraîches et humides des régions tempérées et froides de l'hémisphère nord",
           "Davidia involucrata, aussi appelé arbre aux mouchoirs, à cause de ses larges bractées blanches, est un arbre de la famille desNyssaceae originaire de Chine. La plante a été découverte en 1869 par le missionnaire botaniste français Jean-Pierre Armand David(1826-1900). Sa première introduction en France a été effectuée en 1897",
           "Le kamon , est un insigne héraldique initialement utilisé par les clans de samouraïs pour se reconnaître plus facilement sur les champs de bataille. Ils sont généralement sous forme de dessins stylisés à l'intérieur d'une forme géométrique. Leur utilisation remonte à l'époque Kamakura. Durant l'époque d'Edo, seuls les daimyos avaient le droit d'en posséder deux à la fois. Dès le début de l'ère Meiji, leur utilisation se répandit parmi le peuple",
           "Kolkwitzia amabilis est un arbuste de la famille des Caprifoliaceae selon la classification classique, ou des Linnaeaceae selon laclassification phylogénétique. C'est la seule espèce acceptée à l'heure actuelle du genre Kolkwitzia"
           */
    };


   public static final Integer[] images = 
	   { 
	   
	   R.drawable.charme,
	   R.drawable.erablepeauserpent,
	   R.drawable.hetrecorse,
	   R.drawable.plataneorient,
	   R.drawable.rosierchataignier
	   
	   /*
	    * 	   R.drawable.pinfaufayard,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorient,
	   R.drawable.plataneorientù
	   
	   */
	   };

   ListView listView;
   List<RowItem> rowItems;

	
	//----------------------------------------------------------------------
	//Tarik 02/05/2013:onCreate [Lancement ListeArbres]
	//----------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listearbres);
		
		
		rowItems = new ArrayList<RowItem>();
	       for (int i = 0; i < titles.length; i++) {
	           RowItem item = new RowItem(images[i], titles[i], descriptions[i]);
	           rowItems.add(item);
	       }

	       listView = (ListView) findViewById(R.id.treesList);
	       CustomListViewAdapter adapter = new CustomListViewAdapter(this,
	               R.layout.activity_listitem, rowItems);
	       listView.setAdapter(adapter);
	       listView.setOnItemClickListener(new OnItemClickListener() {
	    	    public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
	    	    {
	    	          Toast.makeText(getApplicationContext(), ((TextView) view).getText(),Toast.LENGTH_SHORT).show();
	            }
	        });	
		
	}
}
