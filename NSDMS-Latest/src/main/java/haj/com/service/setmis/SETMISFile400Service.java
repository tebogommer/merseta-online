package haj.com.service.setmis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.SETMISFile400Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile100;
import haj.com.entity.SetmisFile304;
import haj.com.entity.SetmisFile400;
import haj.com.entity.SetmisFile401;
import haj.com.entity.SetmisFile500;
import haj.com.entity.SetmisFile501;
import haj.com.entity.SetmisFile503;
import haj.com.entity.SetmisFile505;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile400Service extends AbstractService {

	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();
	
	SETMISFile500Service setmisFile500Service = new SETMISFile500Service();
	List<SetmisFile500> setmisFile500List = new ArrayList<SetmisFile500>();
	
	SETMISFile501Service setmisFile501Service = new SETMISFile501Service();
	List<SetmisFile501> setmisFile501List = new ArrayList<SetmisFile501>();
	
	SETMISFile503Service setmisFile503Service = new SETMISFile503Service();
	List<SetmisFile503> setmisFile503List = new ArrayList<SetmisFile503>();
	
	SETMISFile505Service setmisFile505Service = new SETMISFile505Service();
	List<SetmisFile505> setmisFile505List = new ArrayList<SetmisFile505>();
	
	

	public List<SETMISFile400Bean> extractSETMISFile400Bean() throws Exception {
		return dao.extractSETMISFile400Bean();
	}

	public int extractSetmisFile400Insert() throws Exception {
		return dao.extractSetmisFile400Insert();
	}

	public List<SetmisFile400> allSetmisFile400() throws Exception {
		return dao.allSetmisFile400();
	}

	boolean numberOrNot(String input) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	String genderFromId(String string) {
		if (Integer.parseInt(string) > 4) {
			return "M";
		} else
			return "F";
	}
	
		
	public Integer allActiveContractDetailSetmisFile401(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		return dao.allActiveContractDetailSetmisFile401( alternateIdTypeId, personAlternateId, nationalId, providerCode, providerETQEId);
	}
	
	public Integer allActiveContractDetailSetmisFile500(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		return dao.allActiveContractDetailSetmisFile500( alternateIdTypeId, personAlternateId, nationalId, providerCode, providerETQEId);
	}
	
	public Integer allActiveContractDetailSetmisFile501(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		return dao.allActiveContractDetailSetmisFile501( alternateIdTypeId, personAlternateId, nationalId, providerCode, providerETQEId);
	}
	
	public Integer allActiveContractDetailSetmisFile503(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		return dao.allActiveContractDetailSetmisFile503( alternateIdTypeId, personAlternateId, nationalId, providerCode, providerETQEId);
	}
	
	public Integer allActiveContractDetailSetmisFile505(String alternateIdTypeId, String personAlternateId, String nationalId,String providerCode, String providerETQEId) throws Exception {
		return dao.allActiveContractDetailSetmisFile505( alternateIdTypeId, personAlternateId, nationalId, providerCode, providerETQEId);
	}
	
	
	@SuppressWarnings("unlikely-arg-type")
	public void extractSetmisFile400Validation(List<SetmisFile400> setmisFile400) throws Exception {
		List<IDataEntity> errorList = new ArrayList<IDataEntity>();
		List<String> reservedwords = new ArrayList<String>();
		SETMISFile100Service setmisFile100Service = new SETMISFile100Service();
		List<SetmisFile100> setmisFile100 = new ArrayList<SetmisFile100>();
		setmisFile100 = setmisFile100Service.allSetmisFile100();
		
		setmisFile500List = setmisFile500Service.allSetmisFile500();
		setmisFile501List = setmisFile501Service.allSetmisFile501();
		setmisFile503List = setmisFile503Service.allSetmisFile503();
		setmisFile505List = setmisFile505Service.allSetmisFile505();

		reservedwords.add("UNKNOWN");
		reservedwords.add("AS ABOVE");
		reservedwords.add("SOOS BO");
		reservedwords.add("DELETE");
		reservedwords.add("N/A");
		reservedwords.add("NA");
		reservedwords.add("U");
		reservedwords.add("NONE");
		reservedwords.add("GEEN");
		reservedwords.add("0");
		reservedwords.add("TEST");
		reservedwords.add("ONTBREEK");
		reservedwords.add("NIL");
		reservedwords.add("NILL");
		reservedwords.add("-");
		reservedwords.add("ZZZ");
		reservedwords.add("XXX");
		reservedwords.add("ADDRES");
		String pattern = "ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._-";
		String regex = "[^A-Z0-9@#&+\\(\\)\\s/\\\\:\\._\\-]";
		String regexNumbers = "[^0-9()/-]";
		String regexLatitude = "[^0-9-]";
		String regexLatitudeSeconds = "[^0-9.]";
		String regexEmail = "[^ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@]";
		Pattern r = Pattern.compile(regex);
		Pattern r2 = Pattern.compile(regexNumbers);
		Pattern r3 = Pattern.compile(regexEmail);
		Pattern r4 = Pattern.compile(regexLatitude);
		Pattern r5 = Pattern.compile(regexLatitudeSeconds);
		Matcher m;

		for (SetmisFile400 file400Bean : setmisFile400) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- National Id Start-------------------------------- */
			/* Content Rules */

			if (Integer.parseInt(file400Bean.getpOPIActStatusID()) == 2) {
				extractError = new ExtractErrorList();
				extractError.setNote("National Id must be blank if POPI Act Status ID has a value of '2'.");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);

				if (file400Bean.getNationalId() != null && Integer.parseInt(file400Bean.getpOPIActStatusID()) != 2) {

					if (file400Bean.getNationalId().toString().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("National Id starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}

					boolean isNumber = numberOrNot(file400Bean.getNationalId());
					if (!isNumber) {
						if (Math.ceil(Double.parseDouble(file400Bean.getNationalId())) == Math.floor(Double.parseDouble(file400Bean.getNationalId()))) {
							extractError = new ExtractErrorList();
							extractError.setNote("National Id field may only contain whole numbers");
							extractError.setFileName("Setmis File 400");
							extractError.setTargetClass(SetmisFile400.class.getName());
							extractError.setFileId(file400Bean.getId());
							errorList.add(extractError);
						}
					}
					/* Business Rules (Compliance) */

					if (file400Bean.getNationalId().toString().length() != 13 || file400Bean.getNationalId().toString().length() >= 13 || file400Bean.getNationalId().toString().length() <= 13) {
						extractError = new ExtractErrorList();
						extractError.setNote("National Id must have a length of exactly 13 characters");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
					
					if (file400Bean.getNationalId().substring(6, 11) == "0000") {
						extractError = new ExtractErrorList();
						extractError.setNote("Field value may not have characters 0000 from characters 7 to 10");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}

					if (file400Bean.getNationalId().toString().startsWith("0000")) {
						extractError = new ExtractErrorList();
						extractError.setNote("National Id Field may not have characters '0000' from characters 1 to 4");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}

					List<String> repeatList = new ArrayList<>();
					repeatList.add("1111111111111");
					repeatList.add("2222222222222");
					repeatList.add("3333333333333");
					repeatList.add("4444444444444");
					repeatList.add("5555555555555");
					repeatList.add("6666666666666");
					repeatList.add("7777777777777");
					repeatList.add("8888888888888");
					repeatList.add("9999999999999");

					if (repeatList.contains(file400Bean.getNationalId().toString())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National Id Field may not have repetitive numbers");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}

			}

			/* ----------------- National Id End-------------------------------- */

			/*
			 * ----------------- Person Alternate Id Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getPersonAlternateId() != null) {

				if (file400Bean.getPersonAlternateId() != null) {

					if (file400Bean.getPersonAlternateId().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternate Id starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}
				m = r.matcher(file400Bean.getPersonAlternateId().toString());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate Id contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				if (reservedwords.contains(file400Bean.getPersonAlternateId().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate Id contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				if (file400Bean.getPersonAlternateId() != null && Integer.parseInt(file400Bean.getAlternativeIdType()) == 533) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate Id contains a value, Alternate Id Type Id may not = '533'");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				if (file400Bean.getPersonAlternateId() == null && Integer.parseInt(file400Bean.getAlternativeIdType()) != 533) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate Id contains no value, Alternate Id Type Id must = '533'");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

			}
			/* ----------------- Person Alternate Id End-------------------------------- */

			/*
			 * ----------------- Alternate Id Type Id Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getPersonAlternateId() != null) {

				if (file400Bean.getPersonAlternateId() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate Id Type Id field Blank or null");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				if (file400Bean.getPersonAlternateId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate Id Type Id starts with 'blank space'");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

			Integer set401AltId = allActiveContractDetailSetmisFile401(file400Bean.getAlternativeIdType(), file400Bean.getPersonAlternateId(), file400Bean.getNationalId(), file400Bean.getPersonPreviousProviderCode(), file400Bean.getPersonPreviousProviderETQEId());
			Integer set500AltId = allActiveContractDetailSetmisFile500(file400Bean.getAlternativeIdType(), file400Bean.getPersonAlternateId(), file400Bean.getNationalId(), file400Bean.getPersonPreviousProviderCode(), file400Bean.getPersonPreviousProviderETQEId());
			Integer set501AltId = allActiveContractDetailSetmisFile501(file400Bean.getAlternativeIdType(), file400Bean.getPersonAlternateId(), file400Bean.getNationalId(), file400Bean.getPersonPreviousProviderCode(), file400Bean.getPersonPreviousProviderETQEId());
			Integer set503AltId = allActiveContractDetailSetmisFile503(file400Bean.getAlternativeIdType(), file400Bean.getPersonAlternateId(), file400Bean.getNationalId(), file400Bean.getPersonPreviousProviderCode(), file400Bean.getPersonPreviousProviderETQEId());
			Integer set505AltId = allActiveContractDetailSetmisFile505(file400Bean.getAlternativeIdType(), file400Bean.getPersonAlternateId(), file400Bean.getNationalId(), file400Bean.getPersonPreviousProviderCode(), file400Bean.getPersonPreviousProviderETQEId());

			
				if (file400Bean.getAlternativeIdType().equals("537") && set500AltId == 0 && set505AltId == 0 ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Combination of Alternate Id Type Id, Person Alternate Id, National Id, Provider Code and Provider ETQE Id must be associated with at least one record in one of the following files 500, 501, 503, 505");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				
				if (!file400Bean.getAlternativeIdType().equals("537") && set500AltId == 0 && set505AltId == 0 && set401AltId == 0) {
					extractError = new ExtractErrorList();
					extractError.setNote("Combination of Alternate Id Type Id, Person Alternate Id, National Id, Provider Code and Provider ETQE Id must be associated with at least one record in one of the following files 400, 500, 501, 503, 505");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				
				
				if ( file400Bean.getPersonAlternateId().length() > 9) {

				String nationalIdFirstTen = " ";
				nationalIdFirstTen = file400Bean.getPersonAlternateId().substring(0, 9);

				if (file400Bean.getPersonAlternateId().equals("537") && Collections.frequency(setmisFile400, file400Bean.getAlternativeIdType() + nationalIdFirstTen + file400Bean.getProviderCode() + file400Bean.getPersonPreviousProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate combination of Alternate Id Type Id, National Id, Provider Code and Provider ETQE Id: " + file400Bean.getAlternativeIdType() + nationalIdFirstTen + file400Bean.getProviderCode() + file400Bean.getPersonPreviousProviderETQEId());
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				if (!file400Bean.getPersonAlternateId().equals("537") && Collections.frequency(setmisFile400, file400Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate combination of Alternate Id Type Id, National Id: " + file400Bean.getAlternativeIdType() + nationalIdFirstTen);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				if (file400Bean.getPersonAlternateId().equals("537") && Collections.frequency(setmisFile400, file400Bean.getAlternativeIdType() + file400Bean.getPersonAlternateId() + file400Bean.getProviderCode() + file400Bean.getPersonPreviousProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate combination of Alternate Id Type Id, Person Alternate Id, Provider Code and Provider ETQE Id : " + file400Bean.getAlternativeIdType() + file400Bean.getPersonAlternateId() + file400Bean.getProviderCode() + file400Bean.getPersonPreviousProviderETQEId());
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				if (!file400Bean.getPersonAlternateId().equals("537") && Integer.parseInt(file400Bean.getAlternativeIdType()) != 533 && Collections.frequency(setmisFile400, file400Bean.getAlternativeIdType() + file400Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate combination of Alternate Id Type Id, Person Alternate Id : " + file400Bean.getAlternativeIdType() + file400Bean.getPersonAlternateId());
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				if (file400Bean.getPersonAlternateId().equals("537") && Collections.frequency(setmisFile400, file400Bean.getAlternativeIdType() + file400Bean.getPersonAlternateId() + file400Bean.getNationalId() + file400Bean.getProviderCode() + file400Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate combination of Alternate Id Type Id, Person Alternate Id, National Id, Provider Code and Provider ETQE Id : " + file400Bean.getAlternativeIdType() + file400Bean.getPersonAlternateId() + file400Bean.getNationalId());
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				if (file400Bean.getPersonAlternateId().equals("537") && Collections.frequency(setmisFile400, file400Bean.getAlternativeIdType() + file400Bean.getPersonAlternateId() + file400Bean.getNationalId() + file400Bean.getProviderCode() + file400Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate combination of Alternate Id Type Id, Person Alternate Id, National Id, Provider Code and Provider ETQE Id : " + file400Bean.getAlternativeIdType() + file400Bean.getPersonAlternateId() + file400Bean.getNationalId());
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				
				}

			}

			/* ----------------- Alternate Id Type Id End-------------------------------- */

			/*
			 * ----------------- Equity Code Start--------------------------------
			 */
			/* Content Rules */
			if (file400Bean.getEquityCode() != null) {

				if (file400Bean.getEquityCode() == null || file400Bean.getEquityCode().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Equity Code field Blank or null");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				if (file400Bean.getEquityCode() != null) {

					if (file400Bean.getEquityCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Equity Code starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}

				m = r.matcher(file400Bean.getEquityCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Equity Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

			}

			/* ----------------- Equity Code End-------------------------------- */

			/*
			 * ----------------- Nationality Code Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getNationalityCode() != null) {

				if (file400Bean.getNationalityCode() == null || file400Bean.getNationalityCode().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Nationality Code field Blank or null");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				if (file400Bean.getNationalityCode() != null) {

					if (file400Bean.getNationalityCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Nationality Code starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}

				m = r.matcher(file400Bean.getNationalityCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Nationality Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

			}

			/* Business Rules (Compliance) */

			/* ----------------- Nationality Code End-------------------------------- */

			/*
			 * ----------------- Home Language Code Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getHomeLanguageCode() != null) {

				if (file400Bean.getHomeLanguageCode() == null || file400Bean.getHomeLanguageCode().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Home Language Code field Blank or null");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				if (file400Bean.getHomeLanguageCode() != null) {

					if (file400Bean.getHomeLanguageCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Home Language Code starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}

				m = r.matcher(file400Bean.getHomeLanguageCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Home Language Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

			}

			/* Business Rules (Compliance) */

			/* ----------------- Home Language Code End-------------------------------- */

			/*
			 * ----------------- Gender Code Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getGenderCode() != null) {

				if (file400Bean.getGenderCode() == null || file400Bean.getGenderCode().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Gender Code field Blank or null");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				if (file400Bean.getGenderCode() != null) {

					if (file400Bean.getGenderCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Gender Code starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}

				m = r.matcher(file400Bean.getGenderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Gender Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				
				if (file400Bean.getNationalId().length() > 6 ) {
					
				Character gender = file400Bean.getNationalId().charAt(6);

				if (file400Bean.getNationalId() != null &&  gender.toString().toUpperCase() != file400Bean.getGenderCode().toUpperCase()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Gender Code does not match gender character in National ID");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				}
			}

			/* Business Rules (Compliance) */

			/* ----------------- Gender Code End-------------------------------- */

			/*
			 * ----------------- Citizen Resident Status Code
			 * Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getCitizenResidentStatusCode() == null || file400Bean.getCitizenResidentStatusCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Citizen Resident Status Code field Blank or null");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			if (file400Bean.getCitizenResidentStatusCode() != null) {

				if (file400Bean.getCitizenResidentStatusCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Citizen Resident Status Code starts with 'blank space'");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
			}

			m = r.matcher(file400Bean.getCitizenResidentStatusCode());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Citizen Resident Status Code contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Citizen Resident Status Code
			 * End--------------------------------
			 */

			/*
			 * ----------------- Filler01 Start--------------------------------
			 */
			/* Business Rules (Compliance) */

			if (file400Bean.getFiller01() != null) {
				extractError = new ExtractErrorList();
				extractError.setNote("This is a filler field and must be left blank");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			/* ----------------- Filler01 End-------------------------------- */

			/*
			 * ----------------- Filler02 Start--------------------------------
			 */
			/* Business Rules (Compliance) */

			if (file400Bean.getFiller02() != null) {
				extractError = new ExtractErrorList();
				extractError.setNote("This is a filler field and must be left blank");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			/* ----------------- Filler02 End-------------------------------- */

			/*
			 * ----------------- Person Last Name Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getPersonLastName() != null) {

				if (file400Bean.getPersonLastName() != null) {

					if (file400Bean.getPersonLastName().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Last Name starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}

				m = r.matcher(file400Bean.getPersonLastName());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Last Name contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

			}

			/* Business Rules (Compliance) */

			if (reservedwords.contains(file400Bean.getPersonLastName().toUpperCase())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name contains a reserved word " + reservedwords);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonLastName() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name must be blank if POPI Act Status ID has a value of 2");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonLastName() == null && Integer.parseInt(file400Bean.getpOPIActStatusID()) != 2) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name must be provided if POPI Act Status ID does not have a value of 2");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonLastName() == file400Bean.getPersonFirstName()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name should not have the same value as Person First Name");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			/* ----------------- Person Last Name End-------------------------------- */

			/*
			 * ----------------- Person First Name Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getPersonFirstName() != null) {

				if (file400Bean.getPersonFirstName() != null) {

					if (file400Bean.getPersonFirstName().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Last Name starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}

				m = r.matcher(file400Bean.getPersonFirstName());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Last Name contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

			}

			/* Business Rules (Compliance) */

			if (reservedwords.contains(file400Bean.getPersonFirstName().toUpperCase())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name contains a reserved word " + reservedwords);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonFirstName() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person First Name must be blank if POPI Act Status ID has a value of 2");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonFirstName() == null && Integer.parseInt(file400Bean.getpOPIActStatusID()) != 2) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person First Name must be provided if POPI Act Status ID does not have a value of 2");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonFirstName() == file400Bean.getPersonMiddleName()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person First Name should not have the same value as Person Middle Name");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			/* ----------------- Person First Name End-------------------------------- */

			/*
			 * ----------------- Person Middle Name Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getPersonMiddleName() != null) {

				if (file400Bean.getPersonMiddleName() != null) {

					if (file400Bean.getPersonMiddleName().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Last Name starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}

				m = r.matcher(file400Bean.getPersonMiddleName());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Last Name contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

			}

			/* Business Rules (Compliance) */

			if (reservedwords.contains(file400Bean.getPersonMiddleName().toUpperCase())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name contains a reserved word " + reservedwords);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonMiddleName() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person First Name must be blank if POPI Act Status ID has a value of 2");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonMiddleName() == null && Integer.parseInt(file400Bean.getpOPIActStatusID()) != 2) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person First Name must be provided if POPI Act Status ID does not have a value of 2");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonLastName() == file400Bean.getPersonMiddleName()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Middle Name should not have the same value as Person Last Name");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			/* ----------------- Person Middle Name End-------------------------------- */

			/*
			 * ----------------- Person Title Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getPersonTitle() != null) {

				if (file400Bean.getPersonTitle() != null) {

					if (file400Bean.getPersonTitle().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Title starts with 'blank space'");
						extractError.setFileName("Setmis File 400");
						extractError.setTargetClass(SetmisFile400.class.getName());
						extractError.setFileId(file400Bean.getId());
						errorList.add(extractError);
					}
				}

				m = r.matcher(file400Bean.getPersonTitle());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Title contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

			}

			/* Business Rules (Compliance) */

			/* ----------------- Person Title End-------------------------------- */

			/*
			 * ----------------- Person Birth Date Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getPersonBirthDate() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Birth Date field Blank or null");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonBirthDate().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Birth Date starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
				
			if (file400Bean.getPersonBirthDate().getYear() < 1850) {
				extractError = new ExtractErrorList();
				extractError.setNote("Year component must be greater than 1850");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getNationalId().length() > 7) {
				
			
			String birthDateFromId = file400Bean.getNationalId().substring(2, 7);

			if (file400Bean.getNationalId() != null && !file400Bean.getPersonBirthDate().toGMTString().equals(birthDateFromId)) {
				extractError = new ExtractErrorList();
				extractError.setNote("Year component must be greater than 1850");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			if (file400Bean.getPersonBirthDate() != null) {
				Calendar birthDay = Calendar.getInstance();
				birthDay.setTimeInMillis(file400Bean.getPersonBirthDate().getTime());
				long currentTime = System.currentTimeMillis();
				Calendar now = Calendar.getInstance();
				now.setTimeInMillis(currentTime);
				int age = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

				if (age <= 16) {
					extractError = new ExtractErrorList();
					extractError.setNote("Age may not be less than 16 years");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
			}

			/* Business Rules (Compliance) */

			/* ----------------- Person Birth Date End-------------------------------- */

			/*
			 * ----------------- Person Home Address 1 Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonHomeAddress1() != null) {

			if (file400Bean.getPersonHomeAddress1().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Address 1 starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonHomeAddress1());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Address 1 contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonHomeAddress1() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Address 1 must be blank if POPI Act Status ID has a value of '2'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Home Address 1 End--------------------------------
			 */

			/*
			 * ----------------- Person Home Address 2 Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonHomeAddress2() != null) {

			if (file400Bean.getPersonHomeAddress2().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Address 2 starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonHomeAddress2());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Address 2 contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonHomeAddress2() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Address 2 must be blank if POPI Act Status ID has a value of '2'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Home Address 2 End--------------------------------
			 */

			/*
			 * ----------------- Person Home Address 3 Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonHomeAddress3() != null) {

			if (file400Bean.getPersonHomeAddress3().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Address 3 starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonHomeAddress3());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Address 3 contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonHomeAddress3() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Address 3 must be blank if POPI Act Status ID has a value of '2'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Home Address 3 End--------------------------------
			 */

			/*
			 * ----------------- Person Postal Address 1
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonPostalAddress1() != null) {

			if (file400Bean.getPersonPostalAddress1().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Address 1 starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonPostalAddress1());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Address 1 character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPostalAddress1() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Address 1 must be blank if POPI Act Status ID has a value of '2'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			}
			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Postal Address 1 End--------------------------------
			 */

			/*
			 * ----------------- Person Postal Address 2
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonPostalAddress2() != null) {

			if (file400Bean.getPersonPostalAddress2().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Address 2 starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonPostalAddress2());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Address 2 character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPostalAddress2() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Address 2 must be blank if POPI Act Status ID has a value of '2'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Postal Address 2 End--------------------------------
			 */

			/*
			 * ----------------- Person Postal Address 3
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonPostalAddress3() != null) {

			if (file400Bean.getPersonPostalAddress3().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Address 3 starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonPostalAddress3());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Address 3 character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPostalAddress3() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Address 3 must be blank if POPI Act Status ID has a value of '2'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Postal Address 3 End--------------------------------
			 */

			/*
			 * ----------------- Person Home Address Postal Code
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonHomeAddrPostalCode() != null) {

			if (file400Bean.getPersonHomeAddrPostalCode().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote(" Person Home Addr Postal Code starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getPersonHomeAddrPostalCode());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Home Addr Postal Code contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (Integer.parseInt(file400Bean.getPersonHomeAddrPostalCode()) > 4 || Integer.parseInt(file400Bean.getPersonHomeAddrPostalCode()) < 4) {
				extractError = new ExtractErrorList();
				extractError.setNote("Length of Person Home Addr Postal Code must be exactly 4 characters");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Home Address Postal Code
			 * End--------------------------------
			 */

			/*
			 * ----------------- Person Postal Address Postal Code
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonPostalAddrPostalCode() != null) {

			if (file400Bean.getPersonPostalAddrPostalCode().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote(" Person Postal Addr Postal Code starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getPersonPostalAddrPostalCode());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Postal Addr Postal Code contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (Integer.parseInt(file400Bean.getPersonPostalAddrPostalCode()) > 4 || Integer.parseInt(file400Bean.getPersonPostalAddrPostalCode()) < 4) {
				extractError = new ExtractErrorList();
				extractError.setNote("Length of Person Postal Addr Postal Code must be exactly 4 characters");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Postal Address Postal Code
			 * End--------------------------------
			 */

			/*
			 * ----------------- Person Phone Number Start--------------------------------
			 *
			 * /* Content Rules
			 */
			if (file400Bean.getPersonPhoneNumber() != null) {

			if (file400Bean.getPersonPhoneNumber().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote(" Person Phone Number starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getPersonPhoneNumber());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Phone Number contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonPhoneNumber());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Phone Number characters not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/* ----------------- Person Phone Number End-------------------------------- */

			/*
			 * ----------------- Person Cell Phone Number
			 * Start--------------------------------
			 *
			 * /* Content Rules
			 */
			if (file400Bean.getPersonCellPhoneNumber() != null) {

			if (file400Bean.getPersonCellPhoneNumber().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Cell Phone Number starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getPersonCellPhoneNumber());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Cell Phone Number contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonCellPhoneNumber());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Cell Phone Number characters not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Cell Phone Number
			 * End--------------------------------
			 */

			/*
			 * ----------------- Person Fax Number Start--------------------------------
			 *
			 * /* Content Rules
			 */
			
			if (file400Bean.getPersonFaxNumber() != null) {

			if (file400Bean.getPersonFaxNumber().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Fax Number starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getPersonFaxNumber());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Fax Number contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonFaxNumber());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Fax Number characters not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/* ----------------- Person Fax Number End-------------------------------- */

			/*
			 * ----------------- Person Email Address Start--------------------------------
			 */
			/* Content Rules */
			if (file400Bean.getPersonEmailAddress() != null) {

				if (file400Bean.getPersonEmailAddress().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Email Address starts with 'blank space'");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				m = r3.matcher(file400Bean.getPersonEmailAddress());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Email Address contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file400Bean.getPersonEmailAddress().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Email Address contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Person Email Address End-------------------------------- */

			/*
			 * ----------------- Province Code Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getProvinceCode() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("Province Code field Blank or null");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			if (file400Bean.getProvinceCode() != null) {

			if (file400Bean.getProvinceCode().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Province Code starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getProvinceCode().toString());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Province Code contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/* ----------------- Province Code End-------------------------------- */

			/*
			 * ----------------- Provider Code Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getProviderCode() != null) {

			if (file400Bean.getProviderCode().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getProviderCode().toString());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (reservedwords.contains(file400Bean.getProviderCode().toUpperCase())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code contains a reserved word " + reservedwords);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (!setmisFile100.contains(file400Bean.getProviderCode()) && setmisFile100.contains(file400Bean.getProviderETQEId())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code Must Be In Parent File 100 ");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			if (setmisFile100.contains(file400Bean.getProviderCode()) && !setmisFile100.contains(file400Bean.getProviderETQEId())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider ETQE ID Must Be In Parent File 100 ");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/* ----------------- Provider Code End-------------------------------- */

			/*
			 * ----------------- Provider ETQE Id Start--------------------------------
			 */
			/* Content Rules */

			if (file400Bean.getProviderETQEId() == null || file400Bean.getProviderETQEId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider ETQE Id field Blank or null");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			if (file400Bean.getProviderETQEId() != null) {

				if (file400Bean.getProviderETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE Id starts with 'blank space'");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file400Bean.getProviderETQEId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE Id contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
			}

			/* Business Rules (Compliance) */

			/* ----------------- Provider ETQE Id End-------------------------------- */

			/*
			 * ----------------- Person Previous Lastname
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonPreviousLastName() != null) {

			if (file400Bean.getPersonPreviousLastName().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonPreviousLastName());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			/* Business Rules (Compliance) */

			if (reservedwords.contains(file400Bean.getPersonPreviousLastName().toUpperCase())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name contains a reserved word " + reservedwords);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPreviousLastName() != null && file400Bean.getpOPIActStatusID().equals("2")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Previous Last Name must be blank if POPI Act Status ID has a value of '2'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/*
			 * ----------------- Person Previous Lastname *
			 * End--------------------------------
			 */

			/*
			 * ----------------- Person Previous Alternate Id
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonPreviousAlternateId() != null) {

			if (file400Bean.getPersonPreviousAlternateId().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Previous Alternate Id with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonPreviousAlternateId());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Previous Alternate Id contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			/* Business Rules (Compliance) */

			if (reservedwords.contains(file400Bean.getPersonPreviousAlternateId().toUpperCase())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Previous Alternate Id contains a reserved word " + reservedwords);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPreviousAlternateId() != null && file400Bean.getPersonPreviousAlternativeIdType() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("A value must be provided for Person Previous Alternative Id Type");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPreviousAlternateId() != null && file400Bean.getPersonPreviousProviderCode() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("A value must be provided for Person Previous Provider Code");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPreviousAlternateId() != null && file400Bean.getPersonPreviousProviderETQEId() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("A value must be provided for Person Previous Provider ETQE ID");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPreviousAlternativeIdType() != null && file400Bean.getPersonPreviousAlternateId() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("A value must be provided for Person Previous Alternative Id");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPreviousProviderCode() != null && file400Bean.getPersonPreviousAlternateId() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("A value must be provided for Person Previous Alternative Id");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPreviousProviderETQEId() != null && file400Bean.getPersonPreviousAlternateId() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("A value must be provided for Person Previous Alternative Id");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPreviousAlternateId() != null && file400Bean.getPersonPreviousAlternativeIdType().equals("533")) {
				extractError = new ExtractErrorList();
				extractError.setNote("If Person Previous Alternate Id has a value then Person Previous Alternate Id Type Id may not = '533'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (file400Bean.getPersonPreviousAlternateId() == null && !file400Bean.getPersonPreviousAlternativeIdType().equals("533")) {
				extractError = new ExtractErrorList();
				extractError.setNote("If Person Previous Alternate Id has IS blank then Person Previous Alternate Id Type Id must = '533'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			}

			/*
			 * ----------------- Person Previous Alternate Id
			 * End--------------------------------
			 */

			/*
			 * ----------------- Person Previous AlternateId Type Id
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonPreviousAlternativeIdType() != null) {

			if (file400Bean.getPersonPreviousAlternativeIdType().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Previous Alternative Id Type starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getPersonPreviousAlternativeIdType());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Previous Alternative Id Type contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Previous AlternateId Type Id
			 * End--------------------------------
			 */

			/*
			 * ----------------- Person Previous Provider Code
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonPreviousProviderCode() != null) {

			if (file400Bean.getPersonPreviousProviderCode().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Last Name starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r.matcher(file400Bean.getPersonPreviousProviderCode());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Previous Alternative Id Type contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			/* Business Rules (Compliance) */

			if (reservedwords.contains(file400Bean.getPersonPreviousProviderCode().toUpperCase())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Alternate Id contains a reserved word " + reservedwords);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (!setmisFile100.contains(file400Bean.getPersonPreviousProviderCode()) && setmisFile100.contains(file400Bean.getPersonPreviousProviderETQEId())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code Must Be In Parent File 100");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			if (setmisFile100.contains(file400Bean.getPersonPreviousProviderCode()) && !setmisFile100.contains(file400Bean.getPersonPreviousProviderETQEId())) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Previous Provider ETQE ID Must Be In Parent File 100");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/*
			 * ----------------- Person Previous Provider Code
			 * End--------------------------------
			 */

			/*
			 * ----------------- Person Previous Provider ETQE Id
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getPersonPreviousProviderETQEId() != null) {

			if (file400Bean.getPersonPreviousProviderETQEId().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Previous Provider ETQE ID starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getPersonPreviousProviderETQEId());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Person Previous Provider ETQE ID contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Person Previous Provider ETQE Id
			 * End--------------------------------
			 */

			/*
			 * ----------------- Seeing Rating Id Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getSeeingRatingId() != null) {

			if (file400Bean.getSeeingRatingId().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Seeing Rating Id starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getSeeingRatingId());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Seeing Rating Id  contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/* ----------------- Seeing Rating Id End-------------------------------- */

			/*
			 * ----------------- Hearing Rating Id Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getHearingRatingId() != null) {

			if (file400Bean.getHearingRatingId().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Hearing Rating Id starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getHearingRatingId());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Hearing Rating Id  contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/* ----------------- Hearing Rating Id End-------------------------------- */

			/*
			 * ----------------- Walking Rating Id Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getWalkingRatingId() != null) {

			if (file400Bean.getWalkingRatingId().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Walking Rating Id starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getWalkingRatingId());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Walking Rating Id  contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/* ----------------- Walking Rating Id End-------------------------------- */

			/*
			 * ----------------- Remembering Rating Id Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getRememberingRatingId() != null) {

			if (file400Bean.getRememberingRatingId().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Remembering Rating Id starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getRememberingRatingId());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Remembering Rating Id  contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Remembering Rating Id End--------------------------------
			 */

			/*
			 * ----------------- Communicating Rating Id
			 * Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getCommunicatingRatingId() != null) {

			if (file400Bean.getCommunicatingRatingId().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Communicating Rating Id starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getCommunicatingRatingId());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Communicating Rating Id  contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/*
			 * ----------------- Communicating Rating Id End--------------------------------
			 */

			/*
			 * ----------------- Self Care Rating Id Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getSelfcareRatingId() != null) {

			if (file400Bean.getSelfcareRatingId().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Self Care Rating Id starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getSelfcareRatingId());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Self Care Rating Id  contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* Business Rules (Compliance) */

			/* ----------------- Self Care Rating Id End-------------------------------- */

			/*
			 * ----------------- Last School EMIS No. Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getLastSchoolEMISNo() != null) {

			if (file400Bean.getLastSchoolEMISNo().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Last School EMIS No starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getLastSchoolEMISNo());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Last School EMIS No contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			

			/* Business Rules (Compliance) */

			SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
			Date myDefaultDate = format.parse("12/31/2018");

			if (file400Bean.getLastSchoolEMISNo() == null && file400Bean.getDateStamp().after(myDefaultDate)) {
				extractError = new ExtractErrorList();
				extractError.setNote("Last School EMIS No. must be provided if Date Stamp has a value great than 2018/12/31");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}
			
			}

			/* ----------------- Last School EMIS No. End-------------------------------- */

			/*
			 * ----------------- Last School Year Start--------------------------------
			 */
			/* Content Rules */
			
			if (file400Bean.getLastSchoolYear() != null) {

			if (file400Bean.getLastSchoolYear().toString().startsWith(" ")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Last School Year starts with 'blank space'");
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			m = r2.matcher(file400Bean.getLastSchoolYear());
			if (m.find()) {
				extractError = new ExtractErrorList();
				extractError.setNote("Last School Year contains character not in " + pattern);
				extractError.setFileName("Setmis File 400");
				extractError.setTargetClass(SetmisFile400.class.getName());
				extractError.setFileId(file400Bean.getId());
				errorList.add(extractError);
			}

			/* Business Rules (Compliance) */
			SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
			Date myDefaultDate = format.parse("12/31/2018");

				if (file400Bean.getLastSchoolYear() == null && file400Bean.getDateStamp().after(myDefaultDate)) {
					extractError = new ExtractErrorList();
					extractError.setNote("Last School Year must be provided if Date Stamp has a value great than 2018/12/31");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);

			}
				
			}
			
			/* ----------------- Last School Year End-------------------------------- */
				
				
				/*
				 * ----------------- STATSSA Area Code Start--------------------------------
				 */
				/* Content Rules */
				
			if (file400Bean.getsTATSSAAreaCode() != null) {
				
				if (file400Bean.getsTATSSAAreaCode().toString().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("STATSSA Area Code starts with 'blank space'");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file400Bean.getsTATSSAAreaCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("STATSSA Area Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				
				/* Business Rules (Compliance) */
				
				SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
				Date myDefaultDate = format.parse("12/31/2018");
				
				if (file400Bean.getsTATSSAAreaCode() == null && file400Bean.getDateStamp().after(myDefaultDate)) {
					extractError = new ExtractErrorList();
					extractError.setNote("STATSSA Area Code must be provided if Date Stamp has a value great than 2018/12/31");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);

			}
				
			}
				
				/* ----------------- STATSSA Area Code End-------------------------------- */	
				
				
				
				/*
				 * ----------------- POPI Act Status ID Start--------------------------------
				 */
				/* Content Rules */
			
			if (file400Bean.getpOPIActStatusID() != null) {
				
				if (file400Bean.getpOPIActStatusID().toString().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("POPI Act Status ID starts with 'blank space'");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file400Bean.getpOPIActStatusID());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("POPI Act Status ID contains character not in " + pattern);
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				
			}
				
				/* ----------------- POPI Act Status ID End-------------------------------- */	
				
				/*
				 * ----------------- POPI Act Status Date Start--------------------------------
				 */
				/* Content Rules */
			
			if (file400Bean.getpOPIActStatusDate() != null) {
				
				if (file400Bean.getpOPIActStatusDate().toString().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("POPI Act Status Date starts with 'blank space'");
					extractError.setFileName("Setmis File 400");
					extractError.setTargetClass(SetmisFile400.class.getName());
					extractError.setFileId(file400Bean.getId());
					errorList.add(extractError);
				}
				
				
			}
				/* Business Rules (Compliance) */

				/* ----------------- POPI Act Status Date End-------------------------------- */	
				

		}
		if (errorList.size() > 0) {
			dao.createBatch(errorList);
		}
	}
}
