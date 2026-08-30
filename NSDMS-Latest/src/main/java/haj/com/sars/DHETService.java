package haj.com.sars;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import haj.com.utils.GenericUtility;

public class DHETService {

		 private static final String USER_AGENT = "Mozilla/5.0";
		 private static String baseUrlString = "http://www.dhet.gov.za/merseta/";
		 private static String page = "reports.html";
		 private static SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
		 private static String location = "/Users/hendrik/Downloads/temp/";
		 
		 private static String sendingGetRequest(String urlString) throws Exception {
				  
			  URL url = new URL(urlString);
			  HttpURLConnection con = (HttpURLConnection) url.openConnection();
			 
			  // By default it is GET request
			  con.setRequestMethod("GET");
			 
			  //add request header
			  con.setRequestProperty("User-Agent", USER_AGENT);
			 
	//		  int responseCode = con.getResponseCode();
	//		  System.out.println("Sending get request : "+ url);
	//		  System.out.println("Response code : "+ responseCode);
			 
			  // Reading response from input Stream
			  BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			  String output;
			  StringBuffer response = new StringBuffer();
			 
			  while ((output = in.readLine()) != null) {
			   response.append(output);
			  }
			  in.close();
			 
			  //printing result from response
			  //  System.out.println(response.toString());
			   return response.toString();
			 }

		 
		  private  static String findLatestHREF() throws Exception {
			  String x = sendingGetRequest(DHETService.baseUrlString+DHETService.page);
			  x = x.substring(x.indexOf("DOWNLOADS"));
			  x = x.substring(x.indexOf("<a"), x.indexOf("</a>"));
			  return x;
		  }
		  
		  
		  public  static void downloadLatestHREF() throws Exception {
		      String x = findLatestHREF();
		      String month = (x.substring(x.indexOf(">")+1)).trim().replace(" ", "_");
		      String fname = null;
		      String[] valuesInQuotes = StringUtils.substringsBetween(x , "\"", "\"");
		      for (String string : valuesInQuotes) {
				fname = string;
				break;
			  }
		      month = month + fname.substring(fname.indexOf('.')).trim();
		      System.out.println(fname+"\t"+month);
		      downloadFile(baseUrlString+fname,location);
		     
		  }
	
		  
		  
	  
		   public static void downloadFile(String fileURL, String saveDir) throws Exception {
		        URL url = new URL(fileURL.replaceAll(" ", "%20"));
		        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		        int responseCode = httpConn.getResponseCode();
		 // http://www.dhet.gov.za/merseta/Content/Nov/DOWN/NOV%202017%2017-sdl.zip
		        // always check HTTP response code first
		        if (responseCode == HttpURLConnection.HTTP_OK) {
		            String fileName = "";
		            String disposition = httpConn.getHeaderField("Content-Disposition");
		            String contentType = httpConn.getContentType();
		            int contentLength = httpConn.getContentLength();
		 
		            if (disposition != null) {
		                // extracts file name from header field
		                int index = disposition.indexOf("filename=");
		                if (index > 0) {
		                    fileName = disposition.substring(index + 10,
		                            disposition.length() - 1);
		                }
		            } else {
		                // extracts file name from URL
		                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
		                        fileURL.length());
		            }
		 
		            System.out.println("Content-Type = " + contentType);
		            System.out.println("Content-Disposition = " + disposition);
		            System.out.println("Content-Length = " + contentLength);
		            System.out.println("fileName = " + fileName);
		 
		            // opens input stream from the HTTP connection
		            InputStream inputStream = httpConn.getInputStream();
		            String saveFilePath = saveDir + File.separator + fileName;
		             
		            // opens an output stream to save into file
		            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
		 
		            int bytesRead = -1;
		            byte[] buffer = new byte[1024];
		            while ((bytesRead = inputStream.read(buffer)) != -1) {
		                outputStream.write(buffer, 0, bytesRead);
		            }
		 
		            outputStream.close();
		            inputStream.close();
		 
		            System.out.println("File downloaded");
		        } else {
		            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		        }
		        httpConn.disconnect();
		    }
		
		  
		  public static Date findLatestMonth() throws Exception { 
			  String x = findLatestHREF();
			  x = x.substring(x.indexOf(">")+1);
			  return GenericUtility.getStartOfDay(sdf.parse(x));
		  }
		 
		  public static void main( String[] args ) {
			  try {
				
				   DHETService.downloadLatestHREF();
			} catch (Exception e) {
				e.printStackTrace();
			}
			  System.exit(0);
		  }
	
}
