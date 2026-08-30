package haj.com.service.setmis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.SETMISFile200Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile200;
import haj.com.entity.SetmisFile200;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile200Service extends AbstractService {

	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	public List<SETMISFile200Bean> extractSETMISFile200Bean() throws Exception {
		return dao.extractSETMISFile200Bean();
	}

	public int extractSetmisFile200Insert() throws Exception {
		return dao.extractSetmisFile200Insert();
	}

	public List<SetmisFile200> allSetmisFile200() throws Exception {
		return dao.allSetmisFile200();
	}

	boolean numberOrNot(String input) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	public void extractSetmisFile200Validation(List<SetmisFile200> setmisFile200) throws Exception {
		List<IDataEntity> errorList = new ArrayList<IDataEntity>();
		List<String> reservedwords = new ArrayList<String>();
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

		for (SetmisFile200 file200Bean : setmisFile200) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- Site Number Start-------------------------------- */
			/* Content Rules */
			if (file200Bean.getSiteNo() == null || file200Bean.getSiteNo().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Site Number field Blank or null");
				extractError.setFileName("Setmis File 200");
				extractError.setTargetClass(SetmisFile200.class.getName());
				extractError.setFileId(file200Bean.getId());
				errorList.add(extractError);
			}
			if (file200Bean.getSiteNo() != null) {

				if (file200Bean.getSiteNo().toString().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site Number starts with 'blank space'");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file200Bean.getSiteNo().toString());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}
				/* ----------------- Site Number End-------------------------------- */

				/* ----------------- SETA ID Start-------------------------------- */
				/* Content Rules */
				if (file200Bean.getsETAId() == null || file200Bean.getsETAId().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("SETA ID is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}
				if (file200Bean.getsETAId() != null) {

					if (file200Bean.getsETAId().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("SETA ID starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

				}
				/* ----------------- SETA ID End-------------------------------- */

				/* ----------------- SIC Code Start-------------------------------- */
				/* Content Rules */
				if (file200Bean.getsICCode() != null) {

					if (file200Bean.getsICCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("SIC Code starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				m = r.matcher(file200Bean.getsICCode().toString());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("SIC Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				/*
				 * ----------------- Employer Registration Number
				 * --------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerRegistrationNumber() != null) {

					if (file200Bean.getEmployerRegistrationNumber().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Registration Number starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

				}
				m = r.matcher(file200Bean.getEmployerRegistrationNumber().toString());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Registration Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				/*
				 * ----------------- Employer Registration Number
				 * --------------------------------
				 */

				/*
				 * ----------------- Employer_Company_Name Start--------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerCompanyName() == null || file200Bean.getEmployerCompanyName().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Company Name is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}
				if (file200Bean.getEmployerCompanyName() != null) {

					if (file200Bean.getEmployerCompanyName().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Company Name starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

				}
				m = r.matcher(file200Bean.getEmployerCompanyName());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Company Name contains character not in " + pattern);
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file200Bean.getEmployerCompanyName().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Company Name contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				/*
				 * ----------------- Employer_Company_Name End--------------------------------
				 */

				/*
				 * ----------------- Employer_Trading_Name Start--------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerTradingName() == null || file200Bean.getEmployerTradingName().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Trading Name is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}
				if (file200Bean.getEmployerTradingName() != null) {

					if (file200Bean.getEmployerTradingName().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Trading Name starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

				}
				m = r.matcher(file200Bean.getEmployerTradingName());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Trading Name contains character not in " + pattern);
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file200Bean.getEmployerTradingName().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Trading Name contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				/*
				 * ----------------- Employer_Trading_Name End--------------------------------
				 */

				/*
				 * ----------------- Employer_Postal_Address_1
				 * Start--------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerPostalAddress1() == null || file200Bean.getEmployerPostalAddress1().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Postal Address 1 is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				if (file200Bean.getEmployerPostalAddress1() != null) {

					if (file200Bean.getEmployerPostalAddress1().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 1 starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file200Bean.getEmployerPostalAddress1());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 1 contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (reservedwords.contains(file200Bean.getEmployerPostalAddress1().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 1 contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}

				/* Business Rules (Compliance) */

				/*
				 * ----------------- Employer_Postal_Address_1
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer_Postal_Address_2
				 * Start--------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerPostalAddress2() == null || file200Bean.getEmployerPostalAddress2().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Postal Address 2 is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				if (file200Bean.getEmployerPostalAddress2() != null) {

					if (file200Bean.getEmployerPostalAddress2().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 2 starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file200Bean.getEmployerPostalAddress2());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 2 contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (reservedwords.contains(file200Bean.getEmployerPostalAddress2().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 2 contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}

				/* Business Rules (Compliance) */

				/*
				 * ----------------- Employer_Postal_Address_2
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer_Postal_Address_3
				 * Start--------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerPostalAddress3() != null) {

					if (file200Bean.getEmployerPostalAddress3().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 3 starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file200Bean.getEmployerPostalAddress3());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 3 contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (reservedwords.contains(file200Bean.getEmployerPostalAddress3().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 3 contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					boolean isNumber = numberOrNot(file200Bean.getEmployerPostalAddress3());

					if (isNumber) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address 3 - can not only be a numbers");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}

				/* Business Rules (Compliance) */

				/*
				 * ----------------- Employer_Postal_Address_Code
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer_Physical_Address_Code
				 * Start--------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerPostalAddressCode() == null || file200Bean.getEmployerPostalAddressCode().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Postal Address Code is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				if (file200Bean.getEmployerPostalAddressCode() != null) {

					if (file200Bean.getEmployerPostalAddressCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address Code starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r2.matcher(file200Bean.getEmployerPostalAddressCode());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address Code contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file200Bean.getEmployerPostalAddressCode().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address Code contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					if (file200Bean.getEmployerPostalAddressCode().length() != 4) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Postal Address Code has more or less than 4 characters");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}

				/* Business Rules (Compliance) */
				/*
				 * ----------------- Employer_Postal_Address_Code
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer Physical Address 1
				 * Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerPhysicalAddress1() == null || file200Bean.getEmployerPhysicalAddress1().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Physical Address 1 is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				if (file200Bean.getEmployerPhysicalAddress1() != null) {

					if (file200Bean.getEmployerPhysicalAddress1().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address 1 starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file200Bean.getEmployerPhysicalAddress1());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address 1 contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (reservedwords.contains(file200Bean.getEmployerPhysicalAddress1().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address 1 contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/*
				 * ----------------- Employer Physical Address 1
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer Physical Address 2
				 * Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerPhysicalAddress2() == null || file200Bean.getEmployerPhysicalAddress2().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Physical Address 2 is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				if (file200Bean.getEmployerPhysicalAddress2() != null) {

					if (file200Bean.getEmployerPhysicalAddress2().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address 2 starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file200Bean.getEmployerPhysicalAddress2());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address 2 contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */
					if (reservedwords.contains(file200Bean.getEmployerPhysicalAddress2().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address 2 contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/*
				 * ----------------- Employer Physical Address 2
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer Physical Address 3
				 * Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerPostalAddress3() == null || file200Bean.getEmployerPostalAddress3().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Physical Address 3 is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				if (file200Bean.getEmployerPostalAddress3() != null) {

					if (file200Bean.getEmployerPostalAddress3().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address 3 starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file200Bean.getEmployerPostalAddress3());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address 3 contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (reservedwords.contains(file200Bean.getEmployerPostalAddress3().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address 3 contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/*
				 * ----------------- Employer Physical Address 3
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer Physical Address Code
				 * Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerPhysicalAddressCode() == null || file200Bean.getEmployerPhysicalAddressCode().toString().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Physical Address Code is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				if (file200Bean.getEmployerPhysicalAddressCode() != null) {

					if (file200Bean.getEmployerPhysicalAddressCode().toString().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address Code starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r2.matcher(file200Bean.getEmployerPhysicalAddressCode());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address Code contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file200Bean.getEmployerPhysicalAddressCode().toString().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address Code contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					if (file200Bean.getEmployerPhysicalAddressCode().length() != 4) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Physical Address Code has more or less than 4 characters");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/*
				 * ----------------- Employer Physical Address Code
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer Phone Number Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerPhoneNumber() != null) {

					if (file200Bean.getEmployerPhoneNumber().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Phone Number starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					m = r2.matcher(file200Bean.getEmployerPhoneNumber());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Phone Number contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file200Bean.getEmployerPhoneNumber().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Phone Number contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/*
				 * ----------------- Employer Phone Number End--------------------------------
				 */

				/*
				 * ----------------- Employer Fax Number Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerFaxNumber() != null) {

					if (file200Bean.getEmployerFaxNumber().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Fax Number starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					m = r2.matcher(file200Bean.getEmployerPhoneNumber());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Fax Number contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file200Bean.getEmployerFaxNumber().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Fax Number contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Employer Fax Number End-------------------------------- */

				/*
				 * ----------------- Employer Contact Name Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerContactName() != null) {

					if (file200Bean.getEmployerContactName().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Name starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					m = r.matcher(file200Bean.getEmployerContactName());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Name contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file200Bean.getEmployerContactName().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Name contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/*
				 * ----------------- Employer Contact Name End--------------------------------
				 */

				/*
				 * ----------------- Employer Contact Email Address
				 * Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerContactEmailAddress() != null) {

					if (file200Bean.getEmployerContactEmailAddress().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Email Address starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					m = r3.matcher(file200Bean.getEmployerContactEmailAddress());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Email Address contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file200Bean.getEmployerContactEmailAddress().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Email Address contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/*
				 * ----------------- Employer Contact Email Address
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer Contact Phone Number
				 * Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerContactPhoneNumber() != null) {

					if (file200Bean.getEmployerContactPhoneNumber().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Phone Number starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					m = r2.matcher(file200Bean.getEmployerContactPhoneNumber());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Phone Number contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file200Bean.getEmployerContactPhoneNumber().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Phone Number contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/*
				 * ----------------- Employer Contact Phone Number
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer Contact Cell Number
				 * Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerContactCellNumber() != null) {

					if (file200Bean.getEmployerContactCellNumber().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Cell Number starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					m = r2.matcher(file200Bean.getEmployerContactCellNumber());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Cell Number contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file200Bean.getEmployerContactCellNumber().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Contact Cell Number contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/*
				 * ----------------- Employer Contact Cell Number
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer Approval Status Id
				 * Start--------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerApprovalStatusId() == null || file200Bean.getEmployerApprovalStatusId().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Company Name is blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}
				if (file200Bean.getEmployerApprovalStatusId() != null) {

					if (file200Bean.getEmployerApprovalStatusId().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Company Name starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					boolean isNumber = numberOrNot(file200Bean.getEmployerApprovalStatusId());

					if (!isNumber) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Approval Status Id - can only be a numbers");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

				}

				/* Business Rules (Compliance) */

				/*
				 * ----------------- Employer Approval Status Id
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer_Approval_Status_Start_Date
				 * Start--------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerApprovalStatusStartDate() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Approval Start Date field Blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}
				if (file200Bean.getEmployerApprovalStatusStartDate() != null) {

					if (file200Bean.getEmployerApprovalStatusStartDate().after(file200Bean.getEmployerApprovalStatusStartDate())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Approval Start Date is greater than end date  ");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}

				/* Business Rules (Compliance) */
				/*
				 * ----------------- Employer_Approval_Status_Start_Date
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer_Approval_Status_End_Date
				 * Start--------------------------------
				 */
				/* Content Rules */
				if (file200Bean.getEmployerApprovalStatusEndDate() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Approval End Date field Blank or null");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				if (file200Bean.getEmployerApprovalStatusEndDate() == null && Integer.parseInt(file200Bean.getEmployerApprovalStatusId()) == 2) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Approval End Date field can not Blank or null if Employer Status ID is 2 (Active)");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				if (file200Bean.getEmployerApprovalStatusEndDate() != null && Integer.parseInt(file200Bean.getEmployerApprovalStatusId()) == 1 || Integer.parseInt(file200Bean.getEmployerApprovalStatusId()) == 3) {
					extractError = new ExtractErrorList();
					extractError.setNote("Employer Approval End Date field must Blank or null if Employer Status ID is 1 (Legacy) or 3 (Inactive)");
					extractError.setFileName("Setmis File 200");
					extractError.setTargetClass(SetmisFile200.class.getName());
					extractError.setFileId(file200Bean.getId());
					errorList.add(extractError);
				}

				/*
				 * ----------------- Employer_Approval_Status_End_Date
				 * End--------------------------------
				 */

				/*
				 * ----------------- Employer_Approval_Status_Num
				 * Start--------------------------------
				 */
				/* Content Rules */

				if (file200Bean.getEmployerApprovalStatusNum() != null) {

					if (file200Bean.getEmployerApprovalStatusNum().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Status Number starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					m = r2.matcher(file200Bean.getEmployerApprovalStatusNum());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Status Number contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file200Bean.getEmployerApprovalStatusNum().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Employer Status Number contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}

				/* Business Rules (Compliance) */
				/*
				 * ----------------- Employer_Approval_Status_Num
				 * End--------------------------------
				 */

				/* ----------------- Province_Code Start-------------------------------- */
				/* Content Rules */

				if (file200Bean.getProvinceCode() != null) {

					if (file200Bean.getProvinceCode() == null) {
						extractError = new ExtractErrorList();
						extractError.setNote("Province Code field Blank or null");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					if (file200Bean.getProvinceCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Province Code starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					m = r2.matcher(file200Bean.getProvinceCode());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Province Code contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */
					if (file200Bean.getCountryCode() == "ZA" && file200Bean.getProvinceCode() == "X") {
						extractError = new ExtractErrorList();
						extractError.setNote("Province Code may not be 'X' if Country Code is 'ZA'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					if (file200Bean.getCountryCode() != "ZA" && file200Bean.getProvinceCode() != "X") {
						extractError = new ExtractErrorList();
						extractError.setNote("Province Code must be 'X' if Country Code is not 'ZA'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

				}

				/* Business Rules (Compliance) */
				/* ----------------- Province_Code End-------------------------------- */

				/* ----------------- Country Code Start-------------------------------- */
				/* Content Rules */

				if (file200Bean.getCountryCode() != null) {

					if (file200Bean.getCountryCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Country Code starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					if (file200Bean.getCountryCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Country Code starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					m = r.matcher(file200Bean.getCountryCode().toString());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Country Code contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

				}

				/* Business Rules (Compliance) */
				/* ----------------- Country Code End-------------------------------- */

				/* ----------------- Latitude Degree Start-------------------------------- */
				/* Content Rules */
				if (file200Bean.getLatitudeDegree() != null) {

					if (file200Bean.getLatitudeDegree().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r4.matcher(file200Bean.getLatitudeDegree());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if ((file200Bean.getLatitudeDegree() != null && file200Bean.getLongitudeDegree() == null) || (file200Bean.getLatitudeDegree() == null && file200Bean.getLongitudeDegree() != null)) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree Longitude Degree Blank or null " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					if (file200Bean.getLatitudeDegree() != null || file200Bean.getLatitudeMinutes() != null || file200Bean.getLatitudeSeconds() != null) {
						if (file200Bean.getLatitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Degree contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
						if (file200Bean.getLatitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Minutes contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
						if (file200Bean.getLatitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Seconds contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
					}
					if (file200Bean.getLongitudeDegree() != null || file200Bean.getLongitudeDegree() != null || file200Bean.getLatitudeSeconds() != null) {
						if (file200Bean.getLongitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Degree contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
						if (file200Bean.getLongitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Minutes contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
						if (file200Bean.getLongitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Seconds contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
					}
					if (Double.parseDouble(file200Bean.getLatitudeDegree()) > -22 && Double.parseDouble(file200Bean.getLatitudeDegree()) < -35) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree may not be Greater than -22 and Less than -35");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
					Date myDefaultDate = format.parse("12/31/2018");

					if (file200Bean.getDateStamp().after(myDefaultDate) && file200Bean.getLatitudeDegree() == null) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree Value must be provided if Date Stamp has a value greater than 2018-12-31");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Latitude Degree End-------------------------------- */

				/* ----------------- Latitude Minutes Start-------------------------------- */
				/* Content Rules */
				if (file200Bean.getLatitudeMinutes() != null) {

					if (file200Bean.getLatitudeMinutes().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Minutes starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (Integer.parseInt(file200Bean.getLatitudeDegree()) < 00 && Integer.parseInt(file200Bean.getLatitudeDegree()) > 59) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Minutes may not be less than 00 and Greater than 59");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Latitude Minutes End-------------------------------- */

				/* ----------------- Latitude Seconds Start-------------------------------- */
				/* Content Rules */
				if (file200Bean.getLatitudeSeconds() != null) {

					if (file200Bean.getLatitudeSeconds().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r5.matcher(file200Bean.getLatitudeSeconds());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (file200Bean.getLatitudeSeconds().length() != 6) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds must have an excactlt 6 characters");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					if (Double.parseDouble(file200Bean.getLatitudeSeconds()) < 00.000 && Double.parseDouble(file200Bean.getLatitudeSeconds()) > 59.999) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds may not be less than 00.000 and Greater than 59.999");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Latitude Seconds End-------------------------------- */

				/* ----------------- Longitude Degree Start-------------------------------- */
				/* Content Rules */
				if (file200Bean.getLongitudeDegree() != null) {

					if (file200Bean.getLongitudeDegree().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Degree starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */
					if ((file200Bean.getLongitudeDegree() != null && file200Bean.getLatitudeDegree() == null) || (file200Bean.getLongitudeDegree() == null && file200Bean.getLatitudeDegree() != null)) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree Longitude Degree Blank or null " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					if (file200Bean.getLatitudeDegree() != null || file200Bean.getLatitudeMinutes() != null || file200Bean.getLatitudeSeconds() != null) {
						if (file200Bean.getLatitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Degree contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
						if (file200Bean.getLatitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Minutes contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
						if (file200Bean.getLatitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Seconds contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
					}
					if (file200Bean.getLongitudeDegree() != null || file200Bean.getLongitudeDegree() != null || file200Bean.getLatitudeSeconds() != null) {
						if (file200Bean.getLongitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Degree contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
						if (file200Bean.getLongitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Minutes contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
						if (file200Bean.getLongitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Seconds contains character not in " + pattern);
							extractError.setFileName("Setmis File 200");
							extractError.setTargetClass(SetmisFile200.class.getName());
							extractError.setFileId(file200Bean.getId());
							errorList.add(extractError);
						}
					}
					if (Double.parseDouble(file200Bean.getLongitudeDegree()) > 33 && Double.parseDouble(file200Bean.getLongitudeDegree()) < 16) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Degree may not be Greater than -22 and Less than -35");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
					Date myDefaultDate = format.parse("12/31/2018");

					if (file200Bean.getDateStamp().after(myDefaultDate) && file200Bean.getLongitudeDegree() == null) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Degree Value must be provided if Date Stamp has a value greater than 2018-12-31");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Longitude Degree End-------------------------------- */

				/* ----------------- Longitude Minutes Start-------------------------------- */
				/* Content Rules */
				if (file200Bean.getLongitudeMinutes() != null) {

					if (file200Bean.getLongitudeMinutes().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Minutes starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */
					if (Integer.parseInt(file200Bean.getLongitudeMinutes()) < 00 && Integer.parseInt(file200Bean.getLongitudeMinutes()) > 59) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Minutes may not be less than 00 and Greater than 59");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Longitude Minutes End-------------------------------- */

				/* ----------------- Longitude Seconds Start-------------------------------- */
				/* Content Rules */
				if (file200Bean.getLongitudeSeconds() != null) {

					if (file200Bean.getLongitudeSeconds().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds starts with 'blank space'");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					m = r5.matcher(file200Bean.getLongitudeSeconds());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds contains character not in " + pattern);
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (file200Bean.getLongitudeSeconds().length() != 6) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds must have an excactlt 6 characters");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
					if (Double.parseDouble(file200Bean.getLongitudeSeconds()) < 00.000 && Double.parseDouble(file200Bean.getLongitudeSeconds()) > 59.999) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds may not be less than 00.000 and Greater than 59.999");
						extractError.setFileName("Setmis File 200");
						extractError.setTargetClass(SetmisFile200.class.getName());
						extractError.setFileId(file200Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Longitude Seconds End-------------------------------- */
			}
		}
		if (errorList.size() > 0) {
			dao.createBatch(errorList);
		}
	}

}