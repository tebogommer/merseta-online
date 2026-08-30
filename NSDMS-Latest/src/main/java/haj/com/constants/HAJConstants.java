package haj.com.constants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import haj.com.entity.enums.UsersStatusEnum;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class HAJConstants.
 */
public class HAJConstants implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long  serialVersionUID = 1L;
	protected static final Log logger           = LogFactory.getLog(HAJConstants.class);

	public static final SimpleDateFormat sdf                  = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat sdfMMM               = new SimpleDateFormat("MMM");
	public static final SimpleDateFormat sdfDDMMYYYY          = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat sdfDDMMYYYY2         = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat sdfDDMMYYYYHHmm      = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public static final SimpleDateFormat sdfYYYYMMDD          = new SimpleDateFormat("yyyy/MM/dd");
	public static final SimpleDateFormat sdfYYYYMMDDHHMMSSSSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static final SimpleDateFormat sdfDDMMMMYYYY        = new SimpleDateFormat("dd MMMM yyyy");
	public static final SimpleDateFormat sdfDDMMMM            = new SimpleDateFormat("dd MMMM");
	public static final SimpleDateFormat sdf2                 = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat sdf3                 = new SimpleDateFormat("dd MMMM yyyy (hh:mm a)");
	public static final SimpleDateFormat sdfDateMonthYear     = new SimpleDateFormat("dd MMMM yyyy");
	public static final SimpleDateFormat sdf4                 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat sdfYYYY              = new SimpleDateFormat("yyyy");
	public static final SimpleDateFormat sdfYY                = new SimpleDateFormat("yy");
	public static final SimpleDateFormat sdfMMMM              = new SimpleDateFormat("MMMM");
	public static final SimpleDateFormat sdfDD                = new SimpleDateFormat("dd");
	public static final SimpleDateFormat sddfMMMMyyyy         = new SimpleDateFormat("MMMM yyyy");
	public static final SimpleDateFormat sdfRemintanceService = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static final String YEAR_FORMAT                      = "9999";
	public static final String TELPHONE_FORMAT                  = "099 999 9999";
	public static final String CELLPHONE_FORMAT                 = "099 999 9999";
	public static final String MERSETA_CALL_CENTRE              = "086 163 7738 ";
	public static final String FAX_NUMBER_FORMAT                = "099 999 9999";
	public static final String companyRegistrationNumberFormat  = "9999/999999/99";
	public static final String companyAccreditationNumberFormat = "25-aa/ACC/9999/99";
	public static final String companyLevyNumberFormat          = "L999999999";
	public static final String companyNNumberFormat             = "N999999999";
	public static final String companyVatNumberFormat           = "ZA999999999?*9";
	public static final String WORKING_DAYS                     = "5";
	public static final String passportNumberFormat             = "[a-zA-Z]{1}\\d{8}";
	public static final String allowOnlyNumber                  = "if(event.which &lt; 48 || event.which &gt; 57) return false;";

	public static String APP_PATH         = (String) System.getProperties().get("DD-APP-PATH");
	public static String ARCHIVE_PATH     = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("ARCHIVE_PATH");
	public static String DOC_PATH         = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("docPath");
	public static String DOC_SERVER       = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("docServer");
	public static String DOC_NAME_GRANTPOLICY       = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("grantPolicy");
	public static String GRANTS_CRITERIA_AND_GUIDELINES       = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("grantsCriteriaAndGuidelines");
	public static String DOC_NAME_GRANTPOLICY_2023       = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("grantPolicy2023");
	public static String DOC_NAME_GRANTCRITERIA       = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("grantCriteria");
	public static String MAIL_SERVER      = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("mailServer");
	public static String APNS_CERTIFICATE = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("APNS_CERTIFICATE");
	public static String PL_LINK          = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("PL");
	public static String INFO_REQ_MAIL    = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("INFO_REQ_MAIL");
	public static String WEBSOCKET_SERVER = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("WEBSOCKET_SERVER");
	public static String GP_END_POINT     = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("GP_END_POINT");
	public static String GP_USER_NAME     = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("GP_USER_NAME");
	public static String GP_PASSWORD        = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("GP_PASSWORD");
	public static int GP_CONNECT_TIMEOUT_IN_MILLIS = getInteger("GP_CONNECT_TIMEOUT_IN_MILLIS");
	public static int GP_ITEM_QUERY_REQUEST_TIMEOUT_IN_MILLIS = getInteger("GP_ITEM_QUERY_REQUEST_TIMEOUT_IN_MILLIS");
	public static int GP_LIST_QUERY_REQUEST_TIMEOUT_IN_MILLIS = getInteger("GP_LIST_QUERY_REQUEST_TIMEOUT_IN_MILLIS");
	public static int GP_UPDATE_REQUEST_TIMEOUT_IN_MILLIS = getInteger("GP_UPDATE_REQUEST_TIMEOUT_IN_MILLIS");
	public static String DEV_TEST_PROD    = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("DEV_TEST_PROD");
	public static String MAIL_FROM        = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("ds_mailserver_mailFrom");
	public static String DATAEXTRACT      = ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty("DATAEXTRACT");

	@SuppressWarnings("unchecked")
	public static Map<String, String>  employeePages = (Map<String, String>) ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).get("employee_pages");
	public static Map<Integer, String> MONTHS        = genereateMonthMap();
	public static List<SelectItem>     userStatusDD  = createUserStatusDD();
	public static Map<String, Object>  countries     = populateCountries();
	public static Map<Locale, String>  flags         = populateFlags();

	public static boolean MAIL_DEBUG                   = getBoolean("MAIL_DEBUG");
	public static boolean APNS_PROD                    = getBoolean("APNS_PROD");
	public static boolean RESTRICT_ACCESS              = getBoolean("RESTRICT_ACCESS");
	public static boolean ENTITY_VALIDATION_ON         = getBoolean("ENTITY_VALIDATION_ON");
	public static boolean COMPANY_SETMIS_VALIDATION_ON = getBoolean("COMPANY_SETMIS_VALIDATION_ON");
	public static boolean ADDRESS_SETMIS_VALIDATION_ON = getBoolean("ADDRESS_SETMIS_VALIDATION_ON");
	public static boolean USERS_SETMIS_VALIDATION_ON   = getBoolean("USERS_SETMIS_VALIDATION_ON");

	public static final String HOSTING_MERSETA_ETQA          = "599";
	public static final String HOSTING_MERSETA_ETQA_SETMIS   = "17";
	public static final String QCTO_QUALIFICATION_TYPE_DESC  = "Occupational Certificate";
	public static final String QCTO_ETQA_CODE                = "823";
	public static final String TRADE_QUALIFICATION_CODE      = "554";
	public static final String OCCUPATIONAL_CERTIFICATE_CODE = "721";
	public static final String PRM                           = "PRM";

	public static final Long CONTACT_PERSON_ID                                             = 8L;
	public static final Long MERSETA_HEAD_OFFICE_ID                                        = 1l;
	public static final Long NON_NQF_EXPLANATION_ID                                        = 40l;
	public static final Long TRAINING_DEVIATION_MOTIVATION_ID                              = 39l;
	public static final Long TRAINING_COMMITEE_MINUTES1_ID                                 = 37l;
	public static final Long TRAINING_COMMITEE_MINUTES2_ID                                 = 38l;
	public static final Long HOSTING_MERSETA                                               = 1l;
	public static final Long NQF_LEVEL_ONE_LEARNERSHIP_ID                                  = 30l;
	public static final Long NQF_LEVEL_TWO_LEARNERSHIP_ID                                  = 31l;
	public static final Long NQF_LEVEL_THREE_LEARNERSHIP_ID                                = 32l;
	public static final Long NQF_LEVEL_FOUR_LEARNERSHIP_ID                                 = 33l;
	public static final Long NQF_LEVEL_FIVE_LEARNERSHIP_ID                                 = 34l;
	public static final Long PRIMARY_ID                                                    = 1l;
	public static final Long LAB_ID                                                        = 3l;
	public static final Long ALT_LAB_ID                                                    = 12l;
	public static final Long EMP_ID                                                        = 4l;
	public static final Long ALT_EMP_ID                                                    = 11l;
	public static final Long DISC_FUNDING_ID                                               = 7l;
	public static final Long MAN_FUNDING_ID                                                = 6l;
	public static final Long YES_ID                                                        = 1l;
	public static final Long NO_ID                                                         = 2l;
	public static final Long SENIOR_MANAGER                                                = 4l;
	public static final Long SOUTH_AFRICAN_NATIONALITY                                     = 1l;
	public static final Long CEO_JOB_TITLE_ID                                              = 41l;
// Added for JIRA #2201
	public static final Long COO_JOB_TITLE_ID                                              = 43l;
	public static final Long Senior_Manager_Quality_Assurance_and_Partnerships_ID          = 175l;
	public static final Long Manager_Levy_And_Grant_ID                                     = 139l;
	public static final Long Senior_Manager_Client_Service_ID                              = 168l;
	public static final Long Manager_Legal_and_Contract_Management_ID                      = 168l;
	public static final Long Chief_Operations_Officer_ID                                   = 43l;
	public static final Long Senior_Manager_Administration_ID                              = 167l;
	public static final Long Manager_Quality_Assurance_ID                                  = 146l;
	public static final Long QUALITY_ASSUROR_ROLE_ID                                       = 8l;
	public static final Long Manager_Learning_Administration_ID                            = 137l;
	public static final Long Coordinator_Curriculum_and_Learning_Programmes_Development_ID = 83l;
	public static final Long Chambers_Manager_ID                                           = 132l;
	public static final Long ASSESSOR_MOD_MEETING_SCHEDULE_TYPE_ID                         = 3l;
	public static final Long SDP_MEETING_SCHEDULE_TYPE_ID                                  = 1l;
	public static final Long APPRENTICESHIP_ID                                             = 24l;
	public static final Long ARPL_ID                                                       = 25l;
	public static final Long LEARNERSHIP_ID                                                = 1l;
	public static final Long CREDIT_BEARING_SHORT_COURSE                                   = 28L;
	public static final Long NON_CREDIT_BEARING_SHORT_COURSE                               = 48L;
	public static final Long OCCUPATIONAL_CERTIFICATE_ID                                   = 55L;
	public static final Long NO_DISIBILITY                                                 = 9l;
	public static final Long WSP_ENROLEMNT_STATUS_WSP_NEW                                  = 11l;
	public static final Long WSP_ENROLEMNT_IN_PROG                                         = 12l;
	public static final Long MAX_FILE_SIZE                                                 = 4194304L;
	public static final Long MAX_FIRST_NAME_SIZE                                           = 26L;
	public static final Long MAX_MIDDLE_NAME_SIZE                                          = 45L;
	public static final Long MAX_LAST_NAME_SIZE                                            = 45L;
	public static final Long MAX_EMAIL_SIZE                                                = 50L;
	public static final Long MAX_COMPANY_NAME_SIZE                                         = 70L;
	public static final Long MAX_COMPANY_TRADE_NAME_SIZE                                   = 70L;
	public static final Long MAX_ADDRESS_LINE_SIZE                                         = 50L;
	public static final Long MAX_ADDRESS_CODE_SIZE                                         = 4L;
	public static final Long MAX_TAX_NUMBER                                                = 9L;
	public static final Long MAX_VAX_NUMBER                                                = 13L;
	public static final Long MAX_RSA_ID_NUMBER                                             = 13L;
	public static final Long MAX_PASSPORT_NUMBER                                           = 9L;
	public static final Long MAX_FAX_NUMBER                                                = 10L;
	public static final Long MAX_NUMBER_OF_EMPLOYEES_SIZE                                  = 10L;
	public static final Long MIN_BANK_ACCOUNT_NUMBER                                       = 6L;
	public static final Long MAX_BANK_ACCOUNT_NUMBER                                       = 11L;
	public static final Long MIN_BANK_BRANCH_NUMBER                                        = 2L;
	public static final Long MAX_BANK_BRANCH_NUMBER                                        = 6L;
	public static final Long MAX_BANK_HOLDER                                               = 50L;
	public static final Long MAX_SITE_NUMBER                                               = 10L;
	public static final Long MAX_SITE_NAME                                                 = 10L;
	public static final Long INTERVENTION_TYPE_APPRENTICESIPS_ID                           = 24L;
	public static final Long INTERVENTION_BURSARIES_HET_ID                                 = 26L;
	public static final Long INTERVENTION_BURSARIES_TVET_ID                                = 27L;
	public static final Long INTERVENTION_BURSARIES_UNEMPLOYED_ID                          = 54L;

	public static final long ADMIN_ROLE_ID                                  = 1;
	public static final long COORDINATOR_ROLE_ID                            = 2;
	public static final long CLIENT_SERVICE_ADMIN_ROLE_ID                   = 9;
	public static final long CLIENT_SERVICE_COORDINATOR_ROLE_ID             = 11;
	public static final long ACCREDITATION_STATUS_FULL_ACCREDITATION        = 1;
	public static final long ACCREDITATION_STATUS_NON_ACTIVE                = 2;
	public static final long ACCREDITATION_STATUS_PROVISIONAL_ACCREDITATION = 3;
	public static final long ACCREDITATION_STATUS_APPLICATION               = 4;

	public static final Integer DG_ALLOCATION_FOCUS_YEAR   = getInteger("ALLOCATION_FOCUS_YEAR");
	public static final int     NO_ROWS                    = 21;
	public static final int     NO_ROWS_CHK                = 20;
	public static final int     MAX_IMG_WIDHT              = 100;
	public static final int     WSP_SKILLS_GAP_SECTION_3   = 3;
	public static final int     WSP_SKILLS_GAP_SECTION_4   = 4;
	public static final int     SKILLS_SET                 = 38;
	public static final int     SKILLS_PROGRAM             = 37;
	public static final List<Long> SKILLS_PROGRAM_LIST = Arrays.asList(37L, 90L, 91L);
	public static final List<Long> SKILLS_SET_LIST = Arrays.asList(38L, 92L, 93L);
	public static final int     tvet                       = 35;
	public static double        TOTAL_CPD                  = 35.0;
	public static double        TOTAL_STRUCTURED           = 21.0;
	public static double        TOTAL_UNSTRUCTURED         = 14.0;
	public static String        EMAIL_TEMPLATE             = getTemplate();
	public static final String  BANKING_DETAILS_REGEX_TEXT = ".*[a-zA-Z_-].*";

	// table: company_user_position static information
	public static final long COMPANY_USER_POSITION_CEO_DIRECTOR_ID           = 1;
	public static final long COMPANY_USER_POSITION_CFO_FINANCE_MANAGER_ID    = 2;
	public static final long COMPANY_USER_POSITION_HUMAN_RESOURCE_MANAGER_ID = 3;
	public static final long COMPANY_USER_POSITION_TRAINING_MANAGER_ID       = 4;

	// table: job_title static Information
	public static final long JOB_TITLE_MANAGER_PROGRAMMES_IMPLEMENTATION_ID             = 142;
	public static final long JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID              = 175;
	public static final long JOB_TITLE_MANAGER_QUALITY_ADMINISTRATION_ID                = 145;
	public static final long JOB_TITLE_MANAGER_QUALITY_ASSURANCE_ID                     = 146;
	public static final long JOB_TITLE_SENIOR_MANAGER_CLIENT_SERVICE_ID                 = 168;
	public static final long JOB_TITLE_SENIOR_MANAGER_ADMINISTRATION_ID                 = 167;
	public static final long JOB_TITLE_SENIOR_MANAGER_PROGRAMMES_IMPLEMENTATION_ID      = 173;
	public static final long JOB_TITLE_SENIOR_MANAGER_FINANCIAL_MANAGEMENT_REPORTING_ID = 169;
	public static final long JOB_TITLE_MANAGER_LEVY_GRANTS_ID                           = 139;
	public static final long JOB_TITLE_COORDINATOR_LEVY_GRANTS_ID                       = 142;
	public static final long JOB_TITLE_COORDINATOR_QUALITY_ADMINISTRATION_ID            = 87;
	public static final long JOB_TITLE_CHIEF_EXECUTIVE_OFFICER_ID                       = 41;
	public static final long JOB_TITLE_CHIEF_FINANCIAL_OFFICER_ID                       = 42;
	public static final long JOB_TITLE_CHIEF_OPERATIONS_OFFICER_ID                      = 43;
	public static final long JOB_TITLE_CHIEF_INFORMATION_OFFICER_ID                     = 181;

	// table: designated_trade static Information
	public static final long DESIGNATED_TRADE_METAL_AND_PLASTICS_ID = 2l;
	public static final long DESIGNATED_TRADE_MOTOR_TIME_BASED_ID   = 3l;
	public static final long DESIGNATED_TRADE_MOTOR_CBMT_ID         = 4l;
	public static final long DESIGNATED_TRADE_AUTO_TIME_BASED_ID    = 5l;

	// table: gender static information
	public static final long GENDER_MALE_ID   = 1;
	public static final long GENDER_FEMALE_ID = 2;

	// table: equity static information
	public static final long EQUITY_BLACK_AFRICAN_ID = 1;
	public static final long EQUITY_COLOURED_ID      = 2;
	public static final long EQUITY_INDIAN_ASIAN_ID  = 3;
	public static final long EQUITY_WHITE_ID         = 6;

	// table: roles static information
	public static final long ROLES_CLIENT_LIAISON_OFFICER_ID   = 6;
	public static final long ROLES_CLIENT_RELATIONS_MANAGER_ID = 7;

	// QMR Reporting
	public static final int QMR_AGE_YOUTH = 35;

	public static final String CATHSSETA_HEAD_OFFICE = "011 217 0600";
	
	public static final Long   PRIMARY_SDP_ID        	= 1l;
	public static final Long   SECONDARY_SDP_ID        	= 2l;
	
	
	public static final Long   CHAMBER_UNKNOWN_ID       = 7l;

	/**
	 * The countries.
	 * @return the map
	 */
	private static Map<String, Object> populateCountries() {
		Map<String, Object> countries = new LinkedHashMap<String, Object>();
		countries.put("Select Language", Locale.UK);
		countries.put("English", Locale.UK);
		countries.put("Zulu", new Locale("zu", "ZA")); // label, value
		countries.put("Xhosa", new Locale("xh", "ZA")); // label, value
		countries.put("Northern Sotho", new Locale("nso", "ZA")); // label, value
		countries.put("Southern Sotho", new Locale("st", "ZA")); // label, value
		countries.put("Tswana", new Locale("tn", "ZA")); // label, value
		countries.put("Tsonga", new Locale("ts", "ZA")); // label, value
		countries.put("Swazi", new Locale("ss", "ZA")); // label, value
		countries.put("Venda", new Locale("ve", "ZA")); // label, value
		countries.put("Ndebele", new Locale("nd", "ZA")); // label, value
		countries.put("Afrikaans", new Locale("af", "ZA")); // label, value
		return countries;

		// passportNumberFormat
	}

	/**
	 * The flags.
	 * @return the map
	 */

	private static Map<Locale, String> populateFlags() {
		Map<Locale, String> flags = new LinkedHashMap<Locale, String>();
		flags.put(Locale.UK, "flag-icon-gb");
		flags.put(new Locale("zu_ZA", "ZA"), "flag-icon-za"); // label, value
		flags.put(new Locale("xh_ZA", "ZA"), "flag-icon-za"); // label, value
		flags.put(new Locale("nso_ZA", "ZA"), "flag-icon-za"); // label, value
		flags.put(new Locale("st_ZA", "ZA"), "flag-icon-za"); // label, value
		flags.put(new Locale("tn_ZA", "ZA"), "flag-icon-za"); // label, value
		flags.put(new Locale("ts_ZA", "ZA"), "flag-icon-za"); // label, value
		flags.put(new Locale("ss_ZA", "ZA"), "flag-icon-za"); // label, value
		flags.put(new Locale("ve_ZA", "ZA"), "flag-icon-za"); // label, value
		flags.put(new Locale("nd_ZA", "ZA"), "flag-icon-za"); // label, value
		flags.put(new Locale("af_ZA", "ZA"), "flag-icon-za"); // label, value
		return flags;
	}

	/**
	 * Genereate month map.
	 * @return the map
	 */
	private static Map<Integer, String> genereateMonthMap() {
		Map<Integer, String> m = new HashMap<Integer, String>();
		m.put(1, "Jan");
		m.put(2, "Feb");
		m.put(3, "March");
		m.put(4, "April");
		m.put(5, "May");
		m.put(6, "Jun");
		m.put(7, "Jul");
		m.put(8, "Aug");
		m.put(9, "Sep");
		m.put(10, "Oct");
		m.put(11, "Nov");
		m.put(12, "Dec");

		return m;
	}

	/**
	 * Creates the user status DD.
	 * @return the list
	 */
	private static List<SelectItem> createUserStatusDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		for (UsersStatusEnum val : UsersStatusEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}
		return l;
	}

	/**
	 * Gets the template.
	 * @return the template
	 */
	private static String getTemplate() {
		String html = null;
		try {
			html = GenericUtility.readFile(HAJConstants.APP_PATH + "/emailTemplate/content.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}

	public static String getCellphoneFormat() {
		return CELLPHONE_FORMAT;
	}

	private static boolean getBoolean(String property) {
		boolean rtnVal = false;
		try {
			rtnVal = Boolean.parseBoolean(((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(property).trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnVal;
	}

	private static Integer getInteger(String property) {
		Integer value = null;
		try {
			value = Integer.valueOf(((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(property).trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	private static Long getLong(String property) {
		Long value = null;
		try {
			value = Long.valueOf(((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(property).trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}