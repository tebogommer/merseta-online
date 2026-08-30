package haj.com.service.setmis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import haj.com.annotations.ExtractFilename;
import haj.com.dao.BlankDAO;
import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.dataextract.bean.SETMISFile100BeanVersionTwo;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile100;
import haj.com.entity.enums.DhetFileNumberEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.DhetReportingService;
import haj.com.service.JasperService;
import haj.com.utils.CSVUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile100Service extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();
	private BlankDAO blankDAO = new BlankDAO();
	
	/** Service Levels */
	private DhetReportingService dhetReportingService = new DhetReportingService();

	public List<SETMISFile100Bean> extractSETMISFile100Bean() throws Exception {
		return dao.extractSETMISFile100Bean();
	}
	
	public List<SETMISFile100BeanVersionTwo> extractSETMISFile100BeanVersionTwo() throws Exception {
		return dao.extractSETMISFile100BeanVersionTwo();
	}
	
	public List<SETMISFile100BeanVersionTwo> extractSETMISFile100BeanVersionTwoSqlPassed() throws Exception {
		return dao.extractSETMISFile100BeanVersionTwoSqlPassed(dhetReportingService.findByDhetFileNumberEnumReturnSql(DhetFileNumberEnum.SetmisFile100));
	}

	public int extractSetmisFile100Insert() throws Exception {
		return dao.extractSetmisFile100Insert();
	}

	public List<SetmisFile100> allSetmisFile100() throws Exception {
		return dao.allSetmisFile100();
	}

	public void extractSetmisFile100Validation(List<SetmisFile100> setmisFile100) throws Exception {
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

		for (SetmisFile100 file100Bean : setmisFile100) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* --- Provider Code Start --- */
			/* Content Rules */
			if (file100Bean.getProviderCode() == null || file100Bean.getProviderCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code field Blank or null");
				extractError.setFileName("Setmis File 100");
				extractError.setTargetClass(SetmisFile100.class.getName());
				extractError.setFileId(file100Bean.getId());
				errorList.add(extractError);
			}
			if (file100Bean.getProviderCode() != null) {

				if (file100Bean.getProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file100Bean.getProviderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile100, file100Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Codes: " + file100Bean.getProviderCode());
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile100, file100Bean.getProviderETQAId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQA Id: " + file100Bean.getProviderCode());
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/* --- Provider Code End --- */

			/* --- Provider Name Start --- */
			/* Content Rules */
			if (file100Bean.getProviderName() == null || file100Bean.getProviderName().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Name is blank or null");
				extractError.setFileName("Setmis File 100");
				extractError.setTargetClass(SetmisFile100.class.getName());
				extractError.setFileId(file100Bean.getId());
				errorList.add(extractError);
			}
			if (file100Bean.getProviderName() != null) {

				if (file100Bean.getProviderName().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Name starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file100Bean.getProviderName());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Name contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (reservedwords.contains(file100Bean.getProviderName().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Name contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/* --- Provider Name End --- */

			/*
			 * --- Provider Postal Address 1 Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderPostalAddress1() == null || file100Bean.getProviderPostalAddress1().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Postal Address 1 is blank or null");
				extractError.setFileName("Setmis File 100");
				extractError.setTargetClass(SetmisFile100.class.getName());
				extractError.setFileId(file100Bean.getId());
				errorList.add(extractError);
			}

			if (file100Bean.getProviderPostalAddress1() != null) {

				if (file100Bean.getProviderPostalAddress1().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address 1 starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file100Bean.getProviderPostalAddress1());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address 1 contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (reservedwords.contains(file100Bean.getProviderPostalAddress1().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address 1 contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/*
			 * --- Provider Postal Address 1 End ---
			 */

			/*
			 * --- Provider Postal Address 2 Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderPostalAddress2() == null || file100Bean.getProviderPostalAddress2().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Postal Address 2 is blank or null");
				extractError.setFileName("Setmis File 100");
				extractError.setTargetClass(SetmisFile100.class.getName());
				extractError.setFileId(file100Bean.getId());
				errorList.add(extractError);
			}

			if (file100Bean.getProviderPostalAddress2() != null) {

				if (file100Bean.getProviderPostalAddress2().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address 2 starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file100Bean.getProviderPostalAddress2());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address 2 contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */
				if (reservedwords.contains(file100Bean.getProviderPostalAddress2().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address 2 contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/*--- Provider Postal Address 2 End ---*/

			/* --- Provider Postal Address 3 Start --- */
			/* Content Rules */
			if (file100Bean.getProviderPostalAddress3() == null || file100Bean.getProviderPostalAddress3().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Postal Address 3 is blank or null");
				extractError.setFileName("Setmis File 100");
				extractError.setTargetClass(SetmisFile100.class.getName());
				extractError.setFileId(file100Bean.getId());
				errorList.add(extractError);
			}

			if (file100Bean.getProviderPostalAddress3() != null) {

				if (file100Bean.getProviderPostalAddress3().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address 3 starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file100Bean.getProviderPostalAddress3());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address 3 contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (reservedwords.contains(file100Bean.getProviderPostalAddress3().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address 3 contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/*
			 * --- Provider Postal Address 3 End ---
			 */

			/*
			 * --- Provider Postal Address Code Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderPostalAddressCode() == null || file100Bean.getProviderPostalAddressCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Postal Address Code is blank or null");
				extractError.setFileName("Setmis File 100");
				extractError.setTargetClass(SetmisFile100.class.getName());
				extractError.setFileId(file100Bean.getId());
				errorList.add(extractError);
			}

			if (file100Bean.getProviderPostalAddressCode() != null) {

				if (file100Bean.getProviderPostalAddressCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address Code starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file100Bean.getProviderPostalAddressCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file100Bean.getProviderPostalAddressCode().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address Code contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				if (file100Bean.getProviderPostalAddressCode().length() != 4) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Postal Address Code has more or less than 4 characters");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/*
			 * --- Provider Postal Address Code End ---
			 */

			/*
			 * --- Provider Phone Number Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderPhoneNumber() != null) {

				if (file100Bean.getProviderPhoneNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Phone Number starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file100Bean.getProviderPhoneNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Phone Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file100Bean.getProviderPhoneNumber().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Phone Number contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/*
			 * --- Provider Phone Number End ---
			 */

			/*
			 * --- Provider Fax Number Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderFaxNumber() != null) {

				if (file100Bean.getProviderFaxNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Fax Number starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file100Bean.getProviderPhoneNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Fax Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file100Bean.getProviderFaxNumber().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Fax Number contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/* --- Provider Fax Number End --- */

			/*
			 * --- Provider SARS Number Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderSarsNumber() != null) {

				if (file100Bean.getProviderSarsNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider SARS Number starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				m = r.matcher(file100Bean.getProviderSarsNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider SARS Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/* --- Provider SARS Number End --- */

			/*
			 * --- Provider Contact Name Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderContactName() != null) {

				if (file100Bean.getProviderContactName().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Name starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				m = r.matcher(file100Bean.getProviderContactName());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Name contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file100Bean.getProviderContactName().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Name contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/*
			 * --- Provider Contact Name End ---
			 */

			/*
			 * --- Provider Contact Email Address Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderContactEmailAddress() != null) {

				if (file100Bean.getProviderContactEmailAddress().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Email Address starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				m = r3.matcher(file100Bean.getProviderContactEmailAddress());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Email Address contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file100Bean.getProviderContactEmailAddress().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Email Address contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/*
			 * --- Provider Contact Email Address End ---
			 */

			/*
			 * --- Provider Contact Phone Number Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderContactPhoneNumber() != null) {

				if (file100Bean.getProviderContactPhoneNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Phone Number starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file100Bean.getProviderContactPhoneNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Phone Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file100Bean.getProviderContactPhoneNumber().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Phone Number contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/*
			 * --- Provider Contact Phone Number End ---
			 */

			/*
			 * --- Provider Contact Cell Number Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderContactCellNumber() != null) {

				if (file100Bean.getProviderContactCellNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Cell Number starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file100Bean.getProviderContactCellNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Cell Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */

				if (reservedwords.contains(file100Bean.getProviderContactCellNumber().toUpperCase())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Contact Cell Number contains a reserved word " + reservedwords);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
			}
			/*
			 * --- Provider Contact Cell Number End ---
			 */

			/*
			 * --- Provider Accreditation Number Start ---
			 */
			/* Content Rules */
			if (file100Bean.getProviderAccreditationNum() != null) {

				if (file100Bean.getProviderAccreditationNum().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Accreditation Number starts with 'blank space'");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				m = r.matcher(file100Bean.getProviderAccreditationNum());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Accreditation Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				/*
				 * --- Provider Accreditation Number End ---
				 */

				/*
				 * --- Provider Start Date Start ---
				 */
				/* Content Rules */
				if (file100Bean.getProviderStartDate() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Start Date field Blank or null");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				if (file100Bean.getProviderStartDate() != null) {

					if (file100Bean.getProviderStartDate().after(file100Bean.getProviderEndDate())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Start Date is greater than end date  ");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Provider Start Date End --- */

				/* --- Provider End Date Start --- */
				/* Content Rules */
				if (file100Bean.getProviderEndDate() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider End Date field Blank or null");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				/* --- Provider End Date End --- */

				/*
				 * --- ETQE Decision Number Start ---
				 */
				/* Content Rules */
				if (file100Bean.getEtqeDecisionNumber() == null || file100Bean.getEtqeDecisionNumber().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("ETQE Decision Number field Blank or null");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				if (file100Bean.getEtqeDecisionNumber() != null) {

					if (file100Bean.getEtqeDecisionNumber().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("ETQE Decision Number starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file100Bean.getEtqeDecisionNumber());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("ETQE Decision Number contains character not in " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- ETQE Decision Number End --- */

				/* --- Provider Class ID Start --- */
				/* Content Rules */
				if (file100Bean.getProviderClassId() == null || file100Bean.getProviderClassId().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Class ID field Blank or null");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}
				if (file100Bean.getProviderClassId() != null) {

					if (file100Bean.getProviderClassId().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Class ID starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Provider Class ID End --- */

				/* --- Latitude Degree Start --- */
				/* Content Rules */
				if (file100Bean.getLatitudeDegree() != null) {

					if (file100Bean.getLatitudeDegree().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					m = r4.matcher(file100Bean.getLatitudeDegree());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree contains character not in " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if ((file100Bean.getLatitudeDegree() != null && file100Bean.getLongitudeDegree() == null) || (file100Bean.getLatitudeDegree() == null && file100Bean.getLongitudeDegree() != null)) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree Longitude Degree Blank or null " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					if (file100Bean.getLatitudeDegree() != null || file100Bean.getLatitudeMinutes() != null || file100Bean.getLatitudeSeconds() != null) {
						if (file100Bean.getLatitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Degree contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
						if (file100Bean.getLatitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Minutes contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
						if (file100Bean.getLatitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Seconds contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
					}
					if (file100Bean.getLongitudeDegree() != null || file100Bean.getLongitudeDegree() != null || file100Bean.getLatitudeSeconds() != null) {
						if (file100Bean.getLongitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Degree contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
						if (file100Bean.getLongitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Minutes contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
						if (file100Bean.getLongitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Seconds contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
					}
					if (Double.parseDouble(file100Bean.getLatitudeDegree()) > -22 && Double.parseDouble(file100Bean.getLatitudeDegree()) < -35) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree may not be Greater than -22 and Less than -35");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
					Date myDefaultDate = format.parse("12/31/2018");

					if (file100Bean.getDateStamp().after(myDefaultDate) && file100Bean.getLatitudeDegree() == null) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree Value must be provided if Date Stamp has a value greater than 2018-12-31");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Latitude Degree End --- */

				/* --- Latitude Minutes Start --- */
				/* Content Rules */
				if (file100Bean.getLatitudeMinutes() != null) {

					if (file100Bean.getLatitudeMinutes().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Minutes starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (Integer.parseInt(file100Bean.getLatitudeDegree()) < 00 && Integer.parseInt(file100Bean.getLatitudeDegree()) > 59) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Minutes may not be less than 00 and Greater than 59");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Latitude Minutes End --- */

				/* --- Latitude Seconds Start --- */
				/* Content Rules */
				if (file100Bean.getLatitudeSeconds() != null) {

					if (file100Bean.getLatitudeSeconds().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					m = r5.matcher(file100Bean.getLatitudeSeconds());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds contains character not in " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (file100Bean.getLatitudeSeconds().length() != 6) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds must have an excactlt 6 characters");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					if (Double.parseDouble(file100Bean.getLatitudeSeconds()) < 00.000 && Double.parseDouble(file100Bean.getLatitudeSeconds()) > 59.999) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds may not be less than 00.000 and Greater than 59.999");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Latitude Seconds End --- */

				/* --- Longitude Degree Start --- */
				/* Content Rules */
				if (file100Bean.getLongitudeDegree() != null) {

					if (file100Bean.getLongitudeDegree().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Degree starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */
					if ((file100Bean.getLongitudeDegree() != null && file100Bean.getLatitudeDegree() == null) || (file100Bean.getLongitudeDegree() == null && file100Bean.getLatitudeDegree() != null)) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree Longitude Degree Blank or null " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					if (file100Bean.getLatitudeDegree() != null || file100Bean.getLatitudeMinutes() != null || file100Bean.getLatitudeSeconds() != null) {
						if (file100Bean.getLatitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Degree contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
						if (file100Bean.getLatitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Minutes contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
						if (file100Bean.getLatitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Seconds contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
					}
					if (file100Bean.getLongitudeDegree() != null || file100Bean.getLongitudeDegree() != null || file100Bean.getLatitudeSeconds() != null) {
						if (file100Bean.getLongitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Degree contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
						if (file100Bean.getLongitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Minutes contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
						if (file100Bean.getLongitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Seconds contains character not in " + pattern);
							extractError.setFileName("Setmis File 100");
							extractError.setTargetClass(SetmisFile100.class.getName());
							extractError.setFileId(file100Bean.getId());
							errorList.add(extractError);
						}
					}
					if (Double.parseDouble(file100Bean.getLongitudeDegree()) > 33 && Double.parseDouble(file100Bean.getLongitudeDegree()) < 16) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Degree may not be Greater than -22 and Less than -35");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
					Date myDefaultDate = format.parse("12/31/2018");

					if (file100Bean.getDateStamp().after(myDefaultDate) && file100Bean.getLongitudeDegree() == null) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Degree Value must be provided if Date Stamp has a value greater than 2018-12-31");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Longitude Degree End --- */

				/* --- Longitude Minutes Start --- */
				/* Content Rules */
				if (file100Bean.getLongitudeMinutes() != null) {

					if (file100Bean.getLongitudeMinutes().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Minutes starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */
					if (Integer.parseInt(file100Bean.getLongitudeMinutes()) < 00 && Integer.parseInt(file100Bean.getLongitudeMinutes()) > 59) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Minutes may not be less than 00 and Greater than 59");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Longitude Minutes End --- */

				/* --- Longitude Seconds Start --- */
				/* Content Rules */
				if (file100Bean.getLongitudeSeconds() != null) {

					if (file100Bean.getLongitudeSeconds().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					m = r5.matcher(file100Bean.getLongitudeSeconds());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds contains character not in " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (file100Bean.getLongitudeSeconds().length() != 6) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds must have an excactlt 6 characters");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					if (Double.parseDouble(file100Bean.getLongitudeSeconds()) < 00.000 && Double.parseDouble(file100Bean.getLongitudeSeconds()) > 59.999) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds may not be less than 00.000 and Greater than 59.999");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Longitude Seconds End --- */

				/*
				 * --- Provider Physical Address 1 Start ---
				 */
				/* Content Rules */
				if (file100Bean.getProviderPhysicalAddress1() == null || file100Bean.getProviderPhysicalAddress1().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Physical Address 1 is blank or null");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				if (file100Bean.getProviderPhysicalAddress1() != null) {

					if (file100Bean.getProviderPhysicalAddress1().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address 1 starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file100Bean.getProviderPhysicalAddress1());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address 1 contains character not in " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (reservedwords.contains(file100Bean.getProviderPhysicalAddress1().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address 1 contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Provider Physical Address 1 End --- */

				/* --- Provider Physical Address 2 Start --- */
				/* Content Rules */
				if (file100Bean.getProviderPhysicalAddress2() == null || file100Bean.getProviderPhysicalAddress2().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Physical Address 2 is blank or null");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				if (file100Bean.getProviderPhysicalAddress2() != null) {

					if (file100Bean.getProviderPhysicalAddress2().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address 2 starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file100Bean.getProviderPhysicalAddress2());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address 2 contains character not in " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */
					if (reservedwords.contains(file100Bean.getProviderPhysicalAddress2().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address 2 contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Provider Physical Address 2 End --- */

				/* --- Provider Physical Address 3 Start --- */
				/* Content Rules */
				if (file100Bean.getProviderPostalAddress3() == null || file100Bean.getProviderPostalAddress3().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Physical Address 3 is blank or null");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				if (file100Bean.getProviderPostalAddress3() != null) {

					if (file100Bean.getProviderPostalAddress3().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address 3 starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					m = r.matcher(file100Bean.getProviderPostalAddress3());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address 3 contains character not in " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (reservedwords.contains(file100Bean.getProviderPostalAddress3().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address 3 contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Provider Physical Address 3 End --- */

				/* --- Provider Physical Address Code Start --- */
				/* Content Rules */
				if (file100Bean.getProviderPhysicalAddressCode() == null || file100Bean.getProviderPhysicalAddressCode().contentEquals("")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Physical Address Code is blank or null");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile100.class.getName());
					extractError.setFileId(file100Bean.getId());
					errorList.add(extractError);
				}

				if (file100Bean.getProviderPhysicalAddressCode() != null) {

					if (file100Bean.getProviderPhysicalAddressCode().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address Code starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					m = r2.matcher(file100Bean.getProviderPhysicalAddressCode());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address Code contains character not in " + pattern);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}

					/* Business Rules (Compliance) */

					if (reservedwords.contains(file100Bean.getProviderPhysicalAddressCode().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address Code contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}

					if (file100Bean.getProviderPhysicalAddressCode().length() != 4) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Physical Address Code has more or less than 4 characters");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Provider Physical Address Code End --- */

				/* --- Provider Website Address Start --- */
				/* Content Rules */
				if (file100Bean.getProviderWebAddress() != null) {

					if (file100Bean.getProviderWebAddress().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Website Address starts with 'blank space'");
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (reservedwords.contains(file100Bean.getProviderWebAddress().toUpperCase())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Website Address contains a reserved word " + reservedwords);
						extractError.setFileName("Setmis File 100");
						extractError.setTargetClass(SetmisFile100.class.getName());
						extractError.setFileId(file100Bean.getId());
						errorList.add(extractError);
					}
				}
				/* --- Provider Website Address End --- */
			}
		}

		if (errorList.size() > 0) {
			dao.createBatch(errorList);
		}
	}

	// if(!stringCheckIfNotNullOrNotEmpty(file100Bean.getProviderPhysicalAddressCode())){
	// extractError = new ExtractErrorList();
	// extractError.setNote("Provider Physical Address Code is blank or null");
	// extractError.setFileName("Setmis File 100");
	// extractError.setFileId(file100Bean.getId());
	// errorList.add(extractError);

	// extractError = new ExtractErrorList(file100Bean.getId(), "Setmis File 100",
	// "Provider Physical Address Code is blank or null");
	// errorList.add(extractError);
	// }
	// private boolean stringCheckIfNotNullOrNotEmpty(String field){
	// if (field == null || field.contentEquals("")) {
	// return false;
	// } else {
	// return true;
	// }
	// }

	public void runExport() throws Exception {
		List<ExtractErrorList> extractErrorLists = blankDAO.findExportErrors();
		List<IDataEntity> errors = new ArrayList<>();
		for (ExtractErrorList extractErrorList : extractErrorLists) {
			Class<?> c = findCorrectClass(extractErrorList.getFileName());
			if (c != null) {
				IDataEntity dataEntity = blankDAO.getByClassAndKey(c, extractErrorList.getFileId());
				if (dataEntity != null)
					errors.add(dataEntity);
			}
		}
		if (!errors.isEmpty()) {
			String csv = CSVUtil.writeCSVNoAnnotaions(errors, ",", false);
			JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "ExportErrors.csv", "text/csv");
		}
	}

	private Class<?> findCorrectClass(String filename) throws Exception {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
		scanner.addIncludeFilter(new AnnotationTypeFilter(ExtractFilename.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("haj.com.entity")) {
//			System.out.println(bd.getBeanClassName());
			Class<?> c = Class.forName(bd.getBeanClassName());
			ExtractFilename ef = (ExtractFilename) c.getAnnotation(ExtractFilename.class);
			if (ef.fileName().equals(filename)) {
				return c;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// new SETMISFile100Service().runExport();
	}
}
