package botanical.main.contentProvider;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public final class XMLParserClass{


    final static String ROOT_NODE = "locationData";
    final static String ELEMENT_HOTSPOT = "hotspot";
    final static String ELEMENT_ID = "id";
    final static String ELEMENT_LONGITUDE = "longitude";
    final static String ELEMENT_LATITUDE = "latitude";

    private static final String TAG="SampleParser";


    public XMLParserClass() {}

    /**
     * @param response The XML string which represents the complete news data
     * @return news The complete data
     */
    public static XMLHotSpotsArrayClass parse(String response) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp;
        try {
            sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            SampleDataHandler dataHandler = new SampleDataHandler();
            xr.setContentHandler(dataHandler);
            InputSource source = new InputSource(new StringReader(response)); 
            xr.parse(source);
            XMLHotSpotsArrayClass result = dataHandler.getData();
            return result;
        } catch (ParserConfigurationException e) {
            Log.e(TAG, "parse", e);
        } catch (SAXException e) {
            Log.e(TAG, "parse", e);
        } catch (IOException e) {
            Log.e(TAG, "parse", e);
        } 
        return null;
    }

    static class SampleDataHandler extends DefaultHandler {
        /**
         * 
         */
        private static final String TAG="NewsDataHandler";
        /**
         * 
         */
        private XMLHotSpotsArrayClass data;
        /**
         * 
         */
        private XMLHotSpot tempElement;
        /**
         * 
         */
        private boolean readingLatitude;
        /**
         * 
         */
        private boolean readingLongitude;
        /**
         * 
         */
        private boolean readingID;
        /**
         * 
         */
        public XMLHotSpotsArrayClass getData(){
            return data;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.xml.sax.helpers.DefaultHandler#endDocument()
         */
        @Override
        public void endDocument() throws SAXException {
            Log.d(TAG, "endDocument Finished parsing response");
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
         * java.lang.String, java.lang.String)
         */
        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if(qName.equalsIgnoreCase(ELEMENT_HOTSPOT)){
                data.addHotspot(tempElement);
            }else if(qName.equalsIgnoreCase(ELEMENT_ID)){
                readingID = false;
            }else if(qName.equalsIgnoreCase(ELEMENT_LONGITUDE)){
                readingLongitude = false;
            }else if(qName.equalsIgnoreCase(ELEMENT_LATITUDE)){
                readingLatitude = false;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.xml.sax.helpers.DefaultHandler#startDocument()
         */
        @Override
        public void startDocument() throws SAXException {
            data = new XMLHotSpotsArrayClass();
            Log.d(TAG, "startDocument Started parsing response");
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
         * java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {
            if(qName.equalsIgnoreCase(ROOT_NODE)){
                //data.setData(new ArrayList<NewsElement>());
            }else if(qName.equalsIgnoreCase(ELEMENT_LATITUDE)){
                readingLatitude=true;                
            }else if(qName.equalsIgnoreCase(ELEMENT_HOTSPOT)){
                tempElement = new XMLHotSpot();
            }else if(qName.equalsIgnoreCase(ELEMENT_ID)){
                readingID=true;
            }else if(qName.equalsIgnoreCase(ELEMENT_LONGITUDE)){
                readingLongitude=true;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
         */
        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            String chars = new String(ch, start, length);    
            chars = chars.trim(); 
            if(readingID){
                try{
                    tempElement.setId(Integer.parseInt(chars));
                }catch(Exception e){
                    Log.e(TAG, "characters[Parsing ID]", e);
                    tempElement.setId(-1);
                }
            }
            else if(readingLatitude){
                try{
                    tempElement.setLatitude(Integer.parseInt(chars));
                }catch(Exception e){
                    Log.e(TAG, "characters[Parsing Age]", e);
                    tempElement.setLatitude(-1);
                }
            }else if(readingLongitude){
            	try{
                    tempElement.setLongitude(Integer.parseInt(chars));
                }catch(Exception e){
                    Log.e(TAG, "characters[Parsing Age]", e);
                    tempElement.setLongitude(-1);
                }
                //tempElement.setName(chars);
            }
        }
    }
}