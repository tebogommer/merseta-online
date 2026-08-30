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
import haj.com.dataextract.bean.SETMISFile500Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile401;
import haj.com.entity.SetmisFile500;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile500Service extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	public List<SETMISFile500Bean> extractSETMISFile500Bean() throws Exception {
		return dao.extractSETMISFile500Bean();
	}

	public int extractSetmisFile500Insert() throws Exception {
		return dao.extractSetmisFile500Insert();
	}

	public List<SetmisFile500> allSetmisFile500() throws Exception {
		return dao.allSetmisFile500();
	}

	@SuppressWarnings("unlikely-arg-type")
	public void extractSetmisFile500Validation(List<SetmisFile500> setmisFile500) throws Exception {
		SETMISFile400Service setmisFile400Service = new SETMISFile400Service();
		SETMISFile401Service setmisFile401Service = new SETMISFile401Service();
		SETMISFile100Service setmisFile100Service = new SETMISFile100Service();
		List<SETMISFile400Bean> setmisFile400 =  setmisFile400Service.extractSETMISFile400Bean();
		List<SETMISFile401Bean> setmisFile401 =  setmisFile401Service.extractSETMISFile401Bean();
		List<SETMISFile100Bean> setmisFile100 =  setmisFile100Service.extractSETMISFile100Bean();
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

		for (SetmisFile500 file500Bean : setmisFile500) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getNationalId() == null || file500Bean.getNationalId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("National ID is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}

			if (file500Bean.getNationalId() != null) {

				if (file500Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file500Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile500, file500Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file500Bean.getNationalId());
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile500, file500Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file500Bean.getPersonAlternateId());
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile500, file500Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file500Bean.getAlternativeIdType());
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile500, file500Bean.getLearnershipId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Learnership ID: " + file500Bean.getLearnershipId());
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile500, file500Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Code: " + file500Bean.getProviderCode());
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile500, file500Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQE ID: " + file500Bean.getProviderETQEId());
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */
			
			/* ----------------- Person Alternate ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getPersonAlternateId() != null) {

				if (file500Bean.getPersonAlternateId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				m = r3.matcher(file500Bean.getPersonAlternateId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Person Alternate ID End-------------------------------- */
			
			
			/* ----------------- Alternate ID Type Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getAlternativeIdType() == null || file500Bean.getAlternativeIdType().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternate ID is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}
			
			if (file500Bean.getAlternativeIdType() != null) {

				if (file500Bean.getAlternativeIdType().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				m = r3.matcher(file500Bean.getAlternativeIdType());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file500Bean.getAlternativeIdType().equals("537")) {
					if (!setmisFile400.contains(file500Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file500Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file500Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file500Bean.getProviderCode())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Code Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file500Bean.getProviderETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider ETQE ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
				}
				if (!file500Bean.getAlternativeIdType().equals("537")) {
					if (!setmisFile400.contains(file500Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file500Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file500Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Alternate ID Type End-------------------------------- */
			
			/* ----------------- Learnership ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getLearnershipId() == null || file500Bean.getLearnershipId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learnership ID is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}

			if (file500Bean.getLearnershipId() != null) {

				if (file500Bean.getLearnershipId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learnership ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Learnership ID End-------------------------------- */
			
			/* ----------------- Assessor Registration Number Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getAssessorRegistrationNumber() != null) {

				if (file500Bean.getAssessorRegistrationNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor Registration Number starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file500Bean.getAssessorRegistrationNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor Registration Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				for (SETMISFile401Bean file401Bean : setmisFile401) {
					if (file401Bean.getDesignationId().equals("1") && (file500Bean.getAssessorRegistrationNumber() == file401Bean.getDesignationRegistrationNumber() && file500Bean.getAssessorETQEId() != file401Bean.getDesignationETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Assessor ETQE ID and Designation ETQE ID do not match. Assessor ETQE ID: " + file500Bean.getAssessorETQEId() + "  DesignationETQEId: " + file401Bean.getDesignationETQEId());
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
					if (file401Bean.getDesignationId().equals("1") && (file500Bean.getAssessorETQEId() == file401Bean.getDesignationETQEId() && file500Bean.getAssessorRegistrationNumber() != file401Bean.getDesignationRegistrationNumber())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Assessor Registration Number and Designation Registration Number do not match. Assessor Registration Number: " + file500Bean.getAssessorRegistrationNumber() + "  DesignationRegistrationNumber: " + file401Bean.getDesignationRegistrationNumber());
						extractError.setFileName("Setmis File 500");
						extractError.setTargetClass(SetmisFile500.class.getName());
						extractError.setFileId(file500Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Assessor Registration Number End-------------------------------- */
			
			/* ----------------- Enrolment Status Date Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getEnrolmentStatusDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrolment Status Date is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}
			if (file500Bean.getEnrolmentStatusDate() != null) {

				/* Business Rules (Compliance) */
				if (file500Bean.getEnrolmentStatusDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Date is greater than today's date");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Enrolment Status Date End-------------------------------- */
			
			/* ----------------- Enrolment Date Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getEnrolmentDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrolment Date is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}
			if (file500Bean.getEnrolmentDate() != null) {
				
				/* Business Rules (Compliance) */
				
				if (file500Bean.getEnrolmentDate().after(file500Bean.getMostRecentRegistrationDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Date Is Greater Than Most Recent Registration Date ");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (file500Bean.getEnrolmentDate().after(file500Bean.getEnrolmentStatusDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Date Is Greater Than Enrolment Status Date ");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (file500Bean.getEnrolmentDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Date Is Greater Than Today's Date");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Enrolment Date End-------------------------------- */
			
			/* ----------------- Enrolment Status ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getEnrolmentStatusId() == null || file500Bean.getEnrolmentStatusId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrolment Status ID is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}
			if (file500Bean.getEnrolmentStatusId() != null) {

				if (file500Bean.getEnrolmentStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (!file500Bean.getEnrolmentStatusId().equals("10") && file500Bean.getEnrolmentStatusReasonId() != null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Reason ID must be blank");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (file500Bean.getEnrolmentStatusId().equals("10") && file500Bean.getEnrolmentStatusReasonId() == null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Reason ID must have a value");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (file500Bean.getEnrolmentStatusId().equals("15") && file500Bean.getCertificateNumber() == null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number must be provided");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (!file500Bean.getEnrolmentStatusId().equals("15") && file500Bean.getCertificateNumber() != null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number must be blank");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				
			}
			/* ----------------- Enrolment Status ID End-------------------------------- */
			
			/* ----------------- Provider Code Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getProviderCode() == null || file500Bean.getProviderCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}

			if (file500Bean.getProviderCode() != null) {

				if (file500Bean.getProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file500Bean.getProviderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (setmisFile100.contains(file500Bean.getProviderCode()) && !setmisFile100.contains(file500Bean.getProviderETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID Must Be In Parent File 100  ");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (!setmisFile100.contains(file500Bean.getProviderCode()) && setmisFile100.contains(file500Bean.getProviderETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code Must Be In Parent File 100 ");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Provider Code End-------------------------------- */
			
			/* ----------------- Provider ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getProviderETQEId() == null || file500Bean.getProviderETQEId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider ETQE ID is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}

			if (file500Bean.getProviderETQEId() != null) {

				if (file500Bean.getProviderETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Provider ETQE ID End-------------------------------- */
			
			/* ----------------- Assessor ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getAssessorETQEId() != null) {

				if (file500Bean.getAssessorETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Assessor ETQE ID End-------------------------------- */
			
			/* ----------------- Enrolment Status Reason ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getEnrolmentStatusReasonId() != null) {

				if (file500Bean.getEnrolmentStatusReasonId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Reason ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Enrolment Status Reason ID End-------------------------------- */
			
			/* ----------------- Most Recent Registration Date Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getMostRecentRegistrationDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Most Recent Registration Date is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}

			if (file500Bean.getMostRecentRegistrationDate() != null) {

				/* Business Rules (Compliance) */
				if (file500Bean.getMostRecentRegistrationDate().after(file500Bean.getEnrolmentStatusDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Most Recent Registration Date Is Greater Than Enrolment Date ");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				if (file500Bean.getMostRecentRegistrationDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Most Recent Registration Date Is Greater Than Today's Date ");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Most Recent Registration Date End-------------------------------- */
			
			/* ----------------- Certificate Number Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getCertificateNumber() != null) {

				if (file500Bean.getCertificateNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file500Bean.getCertificateNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Certificate Number End-------------------------------- */
			
			/* ----------------- Economic Status ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getEconomicStatusId() == null || file500Bean.getEconomicStatusId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Economic Status ID is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}

			if (file500Bean.getEconomicStatusId() != null) {

				if (file500Bean.getEconomicStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Economic Status ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Economic Status ID End-------------------------------- */
			
			/* ----------------- Funding ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getFundingId() == null || file500Bean.getFundingId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Funding ID is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}

			if (file500Bean.getFundingId() != null) {

				if (file500Bean.getFundingId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Funding ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile500, file500Bean.getFundingId()) == 1 || Collections.frequency(setmisFile500, file500Bean.getFundingId()) == 2 || Collections.frequency(setmisFile500, file500Bean.getFundingId()) == 4) {
					extractError = new ExtractErrorList();
					extractError.setNote("Funding ID must only have a value of 1,2 or 4 ");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Funding ID End-------------------------------- */
			
			/* ----------------- Cumulative Spend Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getCumulativeSpend() != null) {

				if (file500Bean.getCumulativeSpend().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Cumulative Spend starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file500Bean.getCumulativeSpend() != null && !file500Bean.getFundingId().contains("1")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Cumulative Spend may only have a value if Funding ID has a value of 1 ");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Cumulative Spend End-------------------------------- */
			
			/* ----------------- OFO Code Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getoFOCode() != null) {

				if (file500Bean.getoFOCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
				Date myDefaultDate = format.parse("12/31/2018");

				if (file500Bean.getDateStamp().after(myDefaultDate) && file500Bean.getoFOCode() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code Value must be provided if Date Stamp has a value greater than 2018-12-31");
					extractError.setFileName("Setmis File 100");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- OFO Code End-------------------------------- */
			
			/* ----------------- Site No Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getSiteNo() == null || file500Bean.getSiteNo().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Site No is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}

			if (file500Bean.getSiteNo() != null) {

				if (file500Bean.getSiteNo().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site No starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file500Bean.getSiteNo());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site No contains character not in " + pattern);
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Site No End-------------------------------- */
			
			/* ----------------- Urban Rural ID Start-------------------------------- */
			/* Content Rules */
			if (file500Bean.getUrbanRuralId() == null || file500Bean.getUrbanRuralId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Urban Rural ID is blank or null");
				extractError.setFileName("Setmis File 500");
				extractError.setTargetClass(SetmisFile500.class.getName());
				extractError.setFileId(file500Bean.getId());
				errorList.add(extractError);
			}

			if (file500Bean.getUrbanRuralId() != null) {

				if (file500Bean.getUrbanRuralId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Urban Rural ID starts with 'blank space'");
					extractError.setFileName("Setmis File 500");
					extractError.setTargetClass(SetmisFile500.class.getName());
					extractError.setFileId(file500Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Urban Rural ID End-------------------------------- */
		}

		if (errorList.size() > 0) {
			dao.createBatch(errorList);

		}
	}
}
