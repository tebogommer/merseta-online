package haj.com.service;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


// TODO: Auto-generated Javadoc
/**
 * The Class ParseXMLUtil.
 */
public class ParseXMLUtil {

	/**
	 * Parses the xml.
	 *
	 * @param xml the xml
	 * @return the map
	 * @throws Exception the exception
	 */
	public static Map<String,String> parseXml(String xml) throws Exception {
		final Map<String,String> m = new LinkedHashMap<String,String>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		DefaultHandler handler = new DefaultHandler() {
		 String prop = null;	 
	
		 
			public void startElement(String uri, String localName,String qName, 
		                Attributes attributes) throws SAXException {
				prop = qName;
			}
		 
			public void endElement(String uri, String localName,
				String qName) throws SAXException {
		 
		 
			}
		 
			public void characters(char ch[], int start, int length) throws SAXException {
				    String x = new String(ch, start, length).trim();
				    if (x.length()>0) {
				    	if (m.containsKey(prop)) {
				    		prop += " ";
				    	}
				    	
				    		m.put(prop, x);
				    	
				    	
				    }
			}
		 
		    };
		       saxParser.parse(IOUtils.toInputStream(xml, "UTF-8"), handler);
		      
		return m;
	}
	
    /**
     * Convert XML file to string.
     *
     * @param fname the fname
     * @return the string
     */
    public static String convertXMLFileToString(String fname)  {
    	String xml = "";
    	 try {
			Path file = Paths.get(fname);
			BufferedReader reader = Files.newBufferedReader(file,
					Charset.defaultCharset());
			String lineFromFile = "";
			while ((lineFromFile = reader.readLine()) != null) {
				xml += lineFromFile.trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
    }
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			String xml = "/Users/hendrik/Documents/workspaceKeplerNew/service/WebContent/xsd/test.xml";
			Map<String,String> m = parseXml(convertXMLFileToString(xml));
			for (Entry<String, String> e : m.entrySet()) {
				System.out.println(e.getKey() + " : " + e.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
