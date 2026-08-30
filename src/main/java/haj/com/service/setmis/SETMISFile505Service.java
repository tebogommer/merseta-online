package haj.com.service.setmis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.dataextract.bean.SETMISFile400Bean;
import haj.com.dataextract.bean.SETMISFile401Bean;
import haj.com.dataextract.bean.SETMISFile501Bean;
import haj.com.dataextract.bean.SETMISFile505Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile502;
import haj.com.entity.SetmisFile505;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile505Service extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	public List<SETMISFile505Bean> extractSETMISFile505Bean() throws Exception {
		return dao.extractSETMISFile505Bean();
	}

	public int extractSetmisFile505Insert() throws Exception {
		return dao.extractSetmisFile505Insert();
	}

	public List<SetmisFile505> allSetmisFile505() throws Exception {
		return dao.allSetmisFile505();
	}

	@SuppressWarnings("unlikely-arg-type")
	public void extractSetmisFile505Validation(List<SetmisFile505> setmisFile505) throws Exception {
		SETMISFile400Service setmisFile400Service = new SETMISFile400Service();
		SETMISFile401Service setmisFile401Service = new SETMISFile401Service();
		SETMISFile100Service setmisFile100Service = new SETMISFile100Service();
		SETMISFile501Service setmisFile501Service = new SETMISFile501Service();
		List<SETMISFile400Bean> setmisFile400 =  setmisFile400Service.extractSETMISFile400Bean();
		List<SETMISFile401Bean> setmisFile401 =  setmisFile401Service.extractSETMISFile401Bean();
		List<SETMISFile100Bean> setmisFile100 =  setmisFile100Service.extractSETMISFile100Bean();
		List<SETMISFile501Bean> setmisFile501 =  setmisFile501Service.extractSETMISFile501Bean();
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
		String regexPerson = "[^A-Z0-9@_]";
		String regexNumbers = "[^0-9]";
		Pattern r = Pattern.compile(regex);
		Pattern r2 = Pattern.compile(regexNumbers);
		Pattern r3 = Pattern.compile(regexPerson);
		Matcher m;

		for (SetmisFile505 file505Bean : setmisFile505) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getNationalId() != null) {

				if (file505Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file505Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile505, file505Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file505Bean.getNationalId());
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile505, file505Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file505Bean.getPersonAlternateId());
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile505, file505Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file505Bean.getAlternativeIdType());
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile505, file505Bean.getQualificationId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Qualification ID: " + file505Bean.getQualificationId());
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile505, file505Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Code: " + file505Bean.getProviderCode());
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile505, file505Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQE ID: " + file505Bean.getProviderETQEId());
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile505, file505Bean.getTradeTestNumber()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Trade Test Number: " + file505Bean.getTradeTestNumber());
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file505Bean.getNationalId()) == 0) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID Must Be In Parent File 501 ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file505Bean.getPersonAlternateId()) == 0) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID Must Be In Parent File 501 ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file505Bean.getAlternativeIdType()) == 0) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID Type Must Be In Parent File 501 ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file505Bean.getQualificationId()) == 0) {
					extractError = new ExtractErrorList();
					extractError.setNote("Qualification ID Must Be In Parent File 501 ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file505Bean.getProviderCode()) == 0) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code Must Be In Parent File 501 ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file505Bean.getProviderETQEId()) == 0) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID Must Be In Parent File 501 ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */
			
			/* ----------------- Person Alternate ID Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getPersonAlternateId() != null) {

				if (file505Bean.getPersonAlternateId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				m = r3.matcher(file505Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Person Alternate ID End-------------------------------- */
			
			/* ----------------- Alternate ID Type Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getAlternativeIdType() == null || file505Bean.getAlternativeIdType().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternate ID is blank or null");
				extractError.setFileName("Setmis File 505");
				extractError.setTargetClass(SetmisFile505.class.getName());
				extractError.setFileId(file505Bean.getId());
				errorList.add(extractError);
			}
			
			if (file505Bean.getAlternativeIdType() != null) {

				if (file505Bean.getAlternativeIdType().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file505Bean.getAlternativeIdType().equals("537")) {
					if (!setmisFile400.contains(file505Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file505Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file505Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file505Bean.getProviderCode())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Code Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file505Bean.getProviderETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider ETQE ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
				}
				if (!file505Bean.getAlternativeIdType().equals("537")) {
					if (!setmisFile400.contains(file505Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file505Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file505Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Alternate ID Type End-------------------------------- */
			
			/* ----------------- Qualification ID Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getQualificationId() == null || file505Bean.getQualificationId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Qualification ID is blank or null");
				extractError.setFileName("Setmis File 505");
				extractError.setTargetClass(SetmisFile505.class.getName());
				extractError.setFileId(file505Bean.getId());
				errorList.add(extractError);
			}
			
			if (file505Bean.getQualificationId() != null) {

				if (file505Bean.getQualificationId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Qualification ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Qualification ID End-------------------------------- */
			
			/* ----------------- Trade Test Number Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getTradeTestNumber() == null || file505Bean.getTradeTestNumber().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Trade Test Number is blank or null");
				extractError.setFileName("Setmis File 505");
				extractError.setTargetClass(SetmisFile505.class.getName());
				extractError.setFileId(file505Bean.getId());
				errorList.add(extractError);
			}
			
			if (file505Bean.getTradeTestNumber() != null) {

				if (file505Bean.getTradeTestNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Trade Test Number starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Trade Test Number End-------------------------------- */
			
			/* ----------------- Trade Test Result ID Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getTradeTestResultId() == null || file505Bean.getTradeTestResultId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Trade Test Result ID is blank or null");
				extractError.setFileName("Setmis File 505");
				extractError.setTargetClass(SetmisFile505.class.getName());
				extractError.setFileId(file505Bean.getId());
				errorList.add(extractError);
			}
			
			if (file505Bean.getTradeTestResultId() != null) {

				if (file505Bean.getTradeTestResultId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Trade Test Result ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (file505Bean.getTradeTestResultId().equals("2") && file505Bean.getTradeTestResultReasonId() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Trade Test Result ID may not be blank");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (!file505Bean.getTradeTestResultId().equals("2") && file505Bean.getTradeTestResultReasonId() != null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Trade Test Result ID must be blank");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Trade Test Result ID End-------------------------------- */
			
			/* ----------------- Trade Test Provider Code Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getTradeTestProviderCode() == null || file505Bean.getTradeTestProviderCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Trade Test Provider Code is blank or null");
				extractError.setFileName("Setmis File 505");
				extractError.setTargetClass(SetmisFile505.class.getName());
				extractError.setFileId(file505Bean.getId());
				errorList.add(extractError);
			}
			
			if (file505Bean.getTradeTestProviderCode() != null) {

				if (file505Bean.getTradeTestProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Trade Test Provider Code starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (!setmisFile100.contains(file505Bean.getTradeTestProviderCode()) && setmisFile100.contains(file505Bean.getTradeTestProviderETQEId()) ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Trade Test Provider Code Must Be In Parent File 100 ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (setmisFile100.contains(file505Bean.getTradeTestProviderCode()) && !setmisFile100.contains(file505Bean.getTradeTestProviderETQEId()) ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Trade Test Provider ETQE ID Must Be In Parent File 100 ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Trade Test Provider Code End-------------------------------- */
			
			/* ----------------- Assessor Registration Number Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getAssessorRegistrationNumber() != null) {

				if (file505Bean.getAssessorRegistrationNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor Registration Number starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file505Bean.getAssessorRegistrationNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor Registration Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				for (SETMISFile401Bean file401Bean : setmisFile401) {
					if (file401Bean.getDesignationId().equals("1") && (file505Bean.getAssessorRegistrationNumber() == file401Bean.getDesignationRegistrationNumber() && file505Bean.getAssessorETQEId() != file401Bean.getDesignationETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Assessor ETQE ID and Designation ETQE ID do not match. Assessor ETQE ID: " + file505Bean.getAssessorETQEId() + "  DesignationETQEId: " + file401Bean.getDesignationETQEId());
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
					if (file401Bean.getDesignationId().equals("1") && (file505Bean.getAssessorETQEId() == file401Bean.getDesignationETQEId() && file505Bean.getAssessorRegistrationNumber() != file401Bean.getDesignationRegistrationNumber())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Assessor Registration Number and Designation Registration Number do not match. Assessor Registration Number: " + file505Bean.getAssessorRegistrationNumber() + "  DesignationRegistrationNumber: " + file401Bean.getDesignationRegistrationNumber());
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Assessor Registration Number End-------------------------------- */
			
			/* ----------------- Moderator Registration Number Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getModeratorRegistrationNumber() != null) {
				
				if (file505Bean.getModeratorRegistrationNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Moderator Registration Number starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file505Bean.getModeratorRegistrationNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Moderator Registration Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				for (SETMISFile401Bean file401Bean : setmisFile401) {
					if (file401Bean.getDesignationId().equals("1") && (file505Bean.getModeratorRegistrationNumber() == file401Bean.getDesignationRegistrationNumber() && file505Bean.getModeratorETQEId() != file401Bean.getDesignationETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Moderator ETQE ID and Designation ETQE ID do not match. Moderator ETQE ID: " + file505Bean.getModeratorETQEId() + "  DesignationETQEId: " + file401Bean.getDesignationETQEId());
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
					if (file401Bean.getDesignationId().equals("1") && (file505Bean.getModeratorETQEId() == file401Bean.getDesignationETQEId() && file505Bean.getModeratorRegistrationNumber() != file401Bean.getDesignationRegistrationNumber())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Moderator Registration Number and Designation Registration Number do not match. Moderator Registration Number: " + file505Bean.getModeratorRegistrationNumber() + "  DesignationRegistrationNumber: " + file401Bean.getDesignationRegistrationNumber());
						extractError.setFileName("Setmis File 505");
						extractError.setTargetClass(SetmisFile505.class.getName());
						extractError.setFileId(file505Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Moderator Registration Number End-------------------------------- */
			
			/* ----------------- Trade Test Date Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getTradeTestDate() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("Trade Test Date is blank or null");
				extractError.setFileName("Setmis File 505");
				extractError.setTargetClass(SetmisFile505.class.getName());
				extractError.setFileId(file505Bean.getId());
				errorList.add(extractError);
			}
			if (file505Bean.getTradeTestDate() != null) {
				
				/* Business Rules (Compliance) */
				if (GenericUtility.sdfA.parse(file505Bean.getTradeTestDate()).after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Trade Test Date is greater than today's date");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Trade Test Date End-------------------------------- */
			
			/* ----------------- Trade Test Result Reason ID Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getTradeTestResultReasonId() == null) {
				extractError = new ExtractErrorList();
				extractError.setNote("Trade Test Result Reason ID is blank or null");
				extractError.setFileName("Setmis File 505");
				extractError.setTargetClass(SetmisFile505.class.getName());
				extractError.setFileId(file505Bean.getId());
				errorList.add(extractError);
			}
			if (file505Bean.getTradeTestDate() != null) {
				
				if (file505Bean.getTradeTestDate().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Trade Test Result Reason ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Trade Test Result Reason ID End-------------------------------- */
			
			/* ----------------- Provider Code Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getProviderCode() != null) {

				if (file505Bean.getProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file505Bean.getProviderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (setmisFile100.contains(file505Bean.getProviderCode()) && !setmisFile100.contains(file505Bean.getProviderETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID Must Be In Parent File 100  ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
				if (!setmisFile100.contains(file505Bean.getProviderCode()) && setmisFile100.contains(file505Bean.getProviderETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code Must Be In Parent File 100 ");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Provider Code End-------------------------------- */
			
			/* ----------------- Provider ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getProviderETQEId() != null) {

				if (file505Bean.getProviderETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Provider ETQE ID End-------------------------------- */
			
			/* ----------------- Trade Test Provider ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getTradeTestProviderETQEId() == null || file505Bean.getTradeTestProviderETQEId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider ETQE ID is blank or null");
				extractError.setFileName("Setmis File 505");
				extractError.setTargetClass(SetmisFile505.class.getName());
				extractError.setFileId(file505Bean.getId());
				errorList.add(extractError);
			}
			
			if (file505Bean.getTradeTestProviderETQEId() != null) {
				
				if (file505Bean.getTradeTestProviderETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Trade Test Provider ETQE ID End-------------------------------- */
			
			/* ----------------- Assessor ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getAssessorETQEId() != null) {

				if (file505Bean.getAssessorETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Assessor ETQE ID End-------------------------------- */
			
			/* ----------------- Moderator ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file505Bean.getModeratorETQEId() != null) {
				
				if (file505Bean.getModeratorETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Moderator ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 505");
					extractError.setTargetClass(SetmisFile505.class.getName());
					extractError.setFileId(file505Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Moderator ETQE ID End-------------------------------- */
		}

		if (errorList.size() > 0) {
			dao.createBatch(errorList);

		}
	}
}
