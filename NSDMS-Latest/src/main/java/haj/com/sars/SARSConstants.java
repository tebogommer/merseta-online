package haj.com.sars;

import java.text.SimpleDateFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class SARSConstants.
 */
public final class SARSConstants {
     
     /** The Constant LEVY_HEADING. */
	//										   ARRIVAL_DATE_1|SETA_CODE|REF_NO|ARRIVAL DATE_2|MANDATORY_LEVY|DISCRETIONARY_LEVY|ADMIN_LEVY|INTEREST|PENALTY|TOTAL|SARS CONTROL DIGIT_1|SARS CONTROL_DIGIT_2|SCHEME_YEAR
     public final static String LEVY_HEADING = "ARRIVAL_DATE_1|SETA_CODE|REF_NO|ARRIVAL_DATE_2|MANDATORY_LEVY|DISCRETIONARY_LEVY|ADMIN_LEVY|INTEREST|PENALTY|TOTAL|SARS_CONTROL_DIGIT_1|SARS_CONTROL_DIGIT_2|SCHEME_YEAR";
     
     /** The Constant EMPLOYER_HEADING. */
     public final static String EMPLOYER_HEADING = "REF_NO|SARS_DATA_1|SARS_DATA_2|SARS_DATA_3|REGISTERED_NAME_OF_ENTITY|SARS_DATA_4|SARS_DATA_5|SARS_DATA_6|SARS_DATA_7|COMPANY_REGISTRATION_NUMBER|SARS_CODE_1|SARS_CODE_2|SARS_CODE_3|SARS_CODE_4|SARS_CODE_5|SARS_CODE_6|SARS_CODE_7|SARS_CODE_8|SARS_CODE_9|SARS_CODE_10|SARS_CODE_11|SARS_CODE_12|SARS_CODE_13|SARS_CODE_14|SARS_CODE_15|SARS_CODE_16|TRADING_NAME|SARS_DATA_8|SARS_DATA_9|SARS_DATA_10|SARS_DATA_11|SARS_DATA_12|SARS_DATA_13|SARS_DATA_14|SARS_DATA_15|SARS_DATA_16|SARS_DATA_17|SARS_DATA_18|SARS_DATA_19|SARS_DATA_20|SARS_DATA_21|SARS_DATA_22|SARS_DATA_23|SARS_DATA_24|SARS_DATA_25|SIC_CODE_2|SARS_DATA_26|SARS_DATA_27|SARS_DATA_28|SARS_DATA_29|SARS_DATA_30|SARS_DATA_31|SARS_DATA_32|SARS_DATA_33|SARS_DATA_34|SARS_DATA_35|SARS_DATA_36|SARS_DATA_37|TRADING_STATUS|SARS_DATA_38|SARS_DATA_39|SARS_DATA_40";
     /** The Constant EMPLOYER_HEADING version two to include post codes. Not used yet */
     public final static String EMPLOYER_HEADING_VERSION_TWO = "REF_NO|SARS_DATA_1|SARS_DATA_2|SARS_DATA_3|REGISTERED_NAME_OF_ENTITY|SARS_DATA_4|SARS_DATA_5|SARS_DATA_6|SARS_DATA_7|COMPANY_REGISTRATION_NUMBER|SARS_CODE_1|SARS_CODE_2|SARS_CODE_3|SARS_CODE_4|SARS_CODE_5|SARS_CODE_6|SARS_CODE_7|SARS_CODE_8|SARS_CODE_9|SARS_CODE_10|SARS_CODE_11|SARS_CODE_12|SARS_CODE_13|SARS_CODE_14|SARS_CODE_15|SARS_CODE_16|TRADING_NAME|SARS_DATA_8|SARS_DATA_9|SARS_DATA_10|SARS_DATA_11|SARS_DATA_12|SARS_DATA_13|SARS_DATA_14|SARS_DATA_15|SARS_DATA_16|SARS_DATA_17|SARS_DATA_18|SARS_DATA_19|SARS_DATA_20|SARS_DATA_21|SARS_DATA_22|SARS_DATA_23|SARS_DATA_24|SARS_DATA_25|SIC_CODE_2|SARS_DATA_26|SARS_DATA_27|SARS_DATA_28|SARS_DATA_29|SARS_DATA_30|SARS_DATA_31|SARS_DATA_32|SARS_DATA_33|SARS_DATA_34|SARS_DATA_35|SARS_DATA_36|SARS_DATA_37|TRADING_STATUS|SARS_DATA_38|SARS_DATA_39|SARS_DATA_40";

     /** The Constant ZIP_FOLDER. */
     public final static String ZIP_FOLDER = findZipFolder(); 
     
     /** The Constant UNZIP_FOLDER. */
     public final static String UNZIP_FOLDER =  findUnzipFolder(); 
     
     /** The Constant LEVY_FILE_NAME. */
     public final static String LEVY_FILE_NAME = "17-sdl.zip";
     
     /** The Constant sdf. */
     public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
     
     /** The Constant sdf2. */
     public final static SimpleDateFormat sdf2 = new SimpleDateFormat("MMMMyyyy");
     
     /** The Constant sdf3. */
     public final static SimpleDateFormat sdf3 = new SimpleDateFormat("MMMM yyyy");
     
     /** The Constant sdf4. */
     public final static SimpleDateFormat sdf4 = new SimpleDateFormat("MMM-yy");
     
     /** The Constant sdf5. */
     public final static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy");
     
     public final static SimpleDateFormat sdf6 = new SimpleDateFormat("MM");
	
     public static final SimpleDateFormat ddMMyy = new SimpleDateFormat("ddMMyy");
     public static final SimpleDateFormat yyyyMMddS = new SimpleDateFormat("yyyyMMddS");
     public static final SimpleDateFormat dds = new SimpleDateFormat("ddS");
     public static final SimpleDateFormat S = new SimpleDateFormat("S");
     public static final SimpleDateFormat dd_MMMM_yyyy = new SimpleDateFormat("dd MMM yyyy");
     /**
      * Find zip folder.
      *
      * @return the string
      */
     private static String findZipFolder() {
		String folder = findProperty("ZIP_FOLDER");
		if (folder==null) folder =	"/Users/hendrik/Downloads/SARS/zip/";
		return folder;
	}


	/**
	 * Find unzip folder.
	 *
	 * @return the string
	 */
	private static String findUnzipFolder() {
		String folder = findProperty("UNZIP_FOLDER");
		if (folder==null) folder =	"/Users/hendrik/Downloads/SARS/unzip/";
		return folder;
	}
	
	
	/**
	 * Find property.
	 *
	 * @param prop the prop
	 * @return the string
	 */
	private static String findProperty(String prop) {
		if (System.getProperties().get("DD-PROPERTIES") != null && ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(prop) !=null) {
			return ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(prop);
		}
			return null;
	}

	public static String getStatus(String tradingStatus) {
		String status = "Unknown";
		if (tradingStatus ==null) status = "Unknown";
		else if ("0".equals(tradingStatus .trim())) status = "Unknown";
		else if ("D".equals(tradingStatus .trim())) status = "Unknown";
		else if ("A".equals(tradingStatus .trim()))  status = "Active";
		else if ("S".equals(tradingStatus .trim()))  status = "Stopped Trading";
		else if ("Y".equals(tradingStatus .trim()))  status = "Deregistered";
		else if ("B".equals(tradingStatus .trim()))  status = "Estate";
		else if ("X".equals(tradingStatus .trim()))  status = "Cannot be traced";
		return status;
	}

}
