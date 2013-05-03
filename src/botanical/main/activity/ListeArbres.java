package botanical.main.activity;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gp1androidproject.R;


public class ListeArbres extends Activity
{
	private ListView listView;
	
	//----------------------------------------------------------------------
	//Tarik 02/05/2013:onCreate [Lancement ListeArbres]
	//----------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listearbres);
		listView = (ListView) findViewById(R.id.treesList);
		
		//Charger xml
		try {
			chargerXML();
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
	//----------------------------------------------------------------------
	//Tarik 02/05/2013: on charge les éléments du xml [Lancement ListeArbres]
	//----------------------------------------------------------------------
	public void chargerXML() throws IOException, ParserConfigurationException, SAXException
	{
			String[] trees = getResources().getStringArray(R.array.trees_array);
	        listView.setAdapter(new ArrayAdapter<String>(this,
	                android.R.layout.simple_list_item_1, trees));
	        listView.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {

	            	Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	                        Toast.LENGTH_SHORT).show();
	                }
	              });
	}
}
