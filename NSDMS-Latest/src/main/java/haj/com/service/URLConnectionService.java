package haj.com.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

// TODO: Auto-generated Javadoc
/**
 * The Class URLConnectionService.
 */
public class URLConnectionService implements Serializable {
	
	/** The logger. */
	private static  Log logger = LogFactory.getLog(URLConnectionService.class);
	
	/** The Constant keystoreFile. */
	//private static final String keystoreFile = EWSConstants.APP_PATH+"/WEB-INF/classes/sbsa.ks";
	private static final String keystoreFile = "/Users/hendrik/Documents/workspaceLuna/ExternalWebservices/src/sbsa.ks";
   
/*	public static String post(String service,String xml) throws Exception {
        StringBuffer buffer = new StringBuffer();
		URL url = new URL(service);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
      
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(xml);
        out.close();

        BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    connection.getInputStream()));
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            buffer.append(decodedString.trim()); 
        }
        in.close();
        return buffer.toString();
	}
	*/
	
	/**
 * Post old.
 *
 * @param urlStr the url str
 * @param xmlStr the xml str
 * @return the string
 */
@SuppressWarnings("deprecation")
	public static String postOld(String urlStr,String xmlStr) {
        String msgResponse="";
        try {
        	
 /*       	System.setProperty("javax.net.ssl.keyStore",                    "/Users/hendrik/Documents/workspaceLuna/ExternalWebservices/src/sbsa.jks");
            System.setProperty("javax.net.ssl.keyStorePassword",            "Metra001");
            System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
   */     	
        	
              String requestURL=urlStr;
              URL url = new URL(requestURL);
              HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
              
     	     //dumpl all cert info

              
              
                if (con instanceof HttpsURLConnection) {
                      ((HttpsURLConnection) con).setRequestMethod("POST");
                      
   
                      con.setHostnameVerifier(new HostnameVerifier() {
 						@Override
						public boolean verify(String arg0, SSLSession arg1) {
							return true;
						}
                      });
                      
                }
                
            
                
              
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestProperty("Content-type", "text/plain");
                con.connect();
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                DataInputStream file = new DataInputStream(new StringBufferInputStream(xmlStr));
                int c;
                while ((c = file.read()) != -1) {
                      out.writeByte(c);
                }
                InputStream in = new BufferedInputStream(con.getInputStream());
                StringBuffer sb = new StringBuffer();
                int d;
                while ((d = in.read()) != -1)
                      sb.append((char) d);
                in.close();
                msgResponse=sb.toString();
          } catch (Exception e) {
        	  logger.fatal(e);
          }
          return msgResponse;
    }
	
    /**
     * Convert XML file to string.
     *
     * @param fname the fname
     * @return the string
     */
    public static String convertXMLFileToString(String fname)  {
    	String xml = "";
    	String path = "/Users/hendrik/Documents/workspaceKeplerNew/ExternalWebservices/WebContent/xml/";
    	 try {
			Path file = Paths.get(path + fname);
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
			//String service = "http://196.8.131.130/MRA/service/GetHomeloanSettlement";
			//String xml = "GetHomeloanSettlement.xml";

			String service = "https://www.homeloans1.standardbank.co.za/MRA/service/MRAPingSBSA";
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><MRAPingSBSA userId=\"MRA010\" password=\"MRA101\" version=\"1.0\"/>";
			String result = post(service,xml);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Post.
	 *
	 * @param url the url
	 * @param xml the xml
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String post(String url,String xml) throws Exception {
	
		
		 String msgResponse="";	
		
	try {
		
	    KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream instream = new FileInputStream(keystoreFile);
        try {
           trustStore.load(instream, "Metra001".toCharArray());
        	
        } finally {
            try { instream.close(); } catch (Exception ignore) {}
        }

        TrustStrategy trustStrategy = new TrustStrategy() {

          

			@Override
			public boolean isTrusted(java.security.cert.X509Certificate[] arg0,
					String arg1) throws java.security.cert.CertificateException {
				return true;
			}

        };

        SSLSocketFactory sslsf = new SSLSocketFactory("TLS", null, null, trustStore, null,
                trustStrategy, new AllowAllHostnameVerifier());
        Scheme https = new Scheme("https", 443, sslsf);
        HttpClient httpclient =  new DefaultHttpClient();
        
        httpclient.getConnectionManager().getSchemeRegistry().register(https);
    

	//	 HttpClient httpclient = WebClientDevWrapper.wrapClient(new DefaultHttpClient());
	
			
        logger.info("===>POSTING XML <====");
        
  
        HttpPost httpost = new HttpPost(url);
  

        StringEntity strent= new StringEntity(xml);
        
        httpost.setEntity(strent);
		   logger.info("====================>About to post to  SBSA  <==================== ");

        HttpResponse responses = httpclient.execute(httpost);
        logger.info("====================>Done post to  SBSA  <==================== ");
        HttpEntity entity2 = responses.getEntity();
        if (entity2 != null) {
        	 logger.info("Response content length: " + entity2.getContentLength());
        }
        InputStream is = entity2.getContent();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
	       StringBuffer responseX = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        responseX.append(line);
	       // responseX.append('\r');
	      }
	      rd.close();
	      msgResponse = responseX.toString();
		} catch (Exception e) {
		  logger.fatal(e);
		  throw e;
		}	
     
		  return msgResponse;	
			
	}

    /**
     * Post.
     *
     * @param service the service
     * @param xml the xml
     * @param test the test
     * @return the string
     * @throws Exception the exception
     */
    public static String post(String service,String xml, boolean test) throws Exception {
        StringBuffer buffer = new StringBuffer();
       // Authenticator.setDefault(new MyAuthenticator());
        URL url = new URL(service);
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();      
  //      URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
      
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(xml);
        out.close();

        BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    connection.getInputStream()));
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            buffer.append(decodedString.trim()); 
        }
        in.close();
       
        return buffer.toString();
       }


    /**
     * The Class MyAuthenticator.
     */
    static class MyAuthenticator extends Authenticator
    {
      
      /* (non-Javadoc)
       * @see java.net.Authenticator#getPasswordAuthentication()
       */
      public PasswordAuthentication getPasswordAuthentication()
      {
        // I haven't checked getRequestingScheme() here, since for NTLM
        // and Negotiate, the usrname and password are all the same.
        System.err.println("Feeding username and password for " + getRequestingScheme());
        return (new PasswordAuthentication("metra\\MetraWS", "Metra001".toCharArray()));
      }
    }
	
}