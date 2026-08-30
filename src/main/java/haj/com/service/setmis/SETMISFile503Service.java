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
import haj.com.dataextract.bean.SETMISFile304Bean;
import haj.com.dataextract.bean.SETMISFile400Bean;
import haj.com.dataextract.bean.SETMISFile401Bean;
import haj.com.dataextract.bean.SETMISFile500Bean;
import haj.com.dataextract.bean.SETMISFile501Bean;
import haj.com.dataextract.bean.SETMISFile502Bean;
import haj.com.dataextract.bean.SETMISFile503Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile503;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile503Service extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	public List<SETMISFile503Bean> extractSETMISFile503Bean() throws Exception {
		return dao.extractSETMISFile503Bean();
	}

	public int extractSetmisFile503Insert() throws Exception {
		return dao.extractSetmisFile503Insert();
	}

	public List<SetmisFile503> allSetmisFile503() throws Exception {
		return dao.allSetmisFile503();
	}

	@SuppressWarnings("unlikely-arg-type")
	public void extractSetmisFile503Validation(List<SetmisFile503> setmisFile503) throws Exception {
		SETMISFile100Service setmisFile100Service = new SETMISFile100Service();
		SETMISFile304Service setmisFile304Service = new SETMISFile304Service();
		SETMISFile400Service setmisFile400Service = new SETMISFile400Service();
		SETMISFile401Service setmisFile401Service = new SETMISFile401Service();
		SETMISFile500Service setmisFile500Service = new SETMISFile500Service();
		SETMISFile501Service setmisFile501Service = new SETMISFile501Service();
		SETMISFile502Service setmisFile502Service = new SETMISFile502Service();
		List<SETMISFile100Bean> setmisFile100 =  setmisFile100Service.extractSETMISFile100Bean();
		List<SETMISFile304Bean> setmisFile304 =  setmisFile304Service.extractSETMISFile304Bean();
		List<SETMISFile400Bean> setmisFile400 =  setmisFile400Service.extractSETMISFile400Bean();
		List<SETMISFile401Bean> setmisFile401 =  setmisFile401Service.extractSETMISFile401Bean();
		List<SETMISFile500Bean> setmisFile500 =  setmisFile500Service.extractSETMISFile500Bean();
		List<SETMISFile501Bean> setmisFile501 =  setmisFile501Service.extractSETMISFile501Bean();
		List<SETMISFile502Bean> setmisFile502 =  setmisFile502Service.extractSETMISFile502Bean();
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
		String pattern = "ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:. -";
		String regex = "[^A-Z0-9@#&+\\(\\)\\s/\\\\:\\. \\-]";
		String regexPerson = "[^A-Z0-9@ ]";
		String regexNumbers = "[^0-9]";
		Pattern r = Pattern.compile(regex);
		Pattern r2 = Pattern.compile(regexNumbers);
		Pattern r3 = Pattern.compile(regexPerson);
		Matcher m;

		for (SetmisFile503 file503Bean : setmisFile503) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getNationalId() == null || file503Bean.getNationalId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("National ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getNationalId() != null) {

				if (file503Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setFileId(file503Bean.getId());
					extractError.setTargetClass(SetmisFile503.class.getName());
					errorList.add(extractError);
				}
				m = r2.matcher(file503Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile503, file503Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file503Bean.getNationalId());
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile503, file503Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file503Bean.getPersonAlternateId());
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile503, file503Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file503Bean.getAlternativeIdType());
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile503, file503Bean.getUnitStandardId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Unit Standard ID: " + file503Bean.getUnitStandardId());
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile503, file503Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Code: " + file503Bean.getProviderCode());
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile503, file503Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQE ID: " + file503Bean.getProviderETQEId());
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile503, file503Bean.getNonNQFIntervCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Non NQF Interv Code: " + file503Bean.getNonNQFIntervCode());
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile503, file503Bean.getNonNQFIntervETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Non NQF Interv ETQE ID: " + file503Bean.getNonNQFIntervETQEId());
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */

			/* ----------------- Person Alternate ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getPersonAlternateId() != null) {

				if (file503Bean.getPersonAlternateId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				m = r3.matcher(file503Bean.getPersonAlternateId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Person Alternate ID End-------------------------------- */
			
			/* ----------------- Alternate ID Type Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getAlternativeIdType() == null || file503Bean.getAlternativeIdType().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternate ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getAlternativeIdType() != null) {

				if (file503Bean.getAlternativeIdType().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file503Bean.getAlternativeIdType().equals("537")) {
					if (!setmisFile400.contains(file503Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file503Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file503Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file503Bean.getProviderCode())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Code Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file503Bean.getProviderETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider ETQE ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
				}
				if (!file503Bean.getAlternativeIdType().equals("537")) {
					if (!setmisFile400.contains(file503Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file503Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file503Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Alternate ID Type End-------------------------------- */
			
			/* ----------------- Unitstandard ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getUnitStandardId() == null || file503Bean.getUnitStandardId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Unitstandard ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getUnitStandardId() != null) {

				if (file503Bean.getUnitStandardId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Unitstandard ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Unitstandard ID End-------------------------------- */
			
			/* ----------------- Enrolment Status ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getEnrolmentStatusId() == null || file503Bean.getEnrolmentStatusId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrolment Status ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getEnrolmentStatusId() != null) {

				if (file503Bean.getEnrolmentStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (!file503Bean.getEnrolmentStatusId().equals("10") && file503Bean.getEnrolmentStatusReasonId() != null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Reason ID must be blank");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getEnrolmentStatusId().equals("10") && file503Bean.getEnrolmentStatusReasonId() == null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Reason ID must have a value");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getEnrolmentStatusId().equals("15") && file503Bean.getCertificateNumber() == null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number must be provided");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (!file503Bean.getEnrolmentStatusId().equals("15") && file503Bean.getCertificateNumber() != null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number must be blank");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Enrolment Status ID End-------------------------------- */
			
			/* ----------------- Assessor Registration Number Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getAssessorRegistrationNumber() != null) {

				if (file503Bean.getAssessorRegistrationNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor Registration Number starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file503Bean.getAssessorRegistrationNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor Registration Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				for (SETMISFile401Bean file401Bean : setmisFile401) {
					if (file401Bean.getDesignationId().equals("1") && (file503Bean.getAssessorRegistrationNumber() == file401Bean.getDesignationRegistrationNumber() && file503Bean.getAssessorETQEId() != file401Bean.getDesignationETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Assessor ETQE ID and Designation ETQE ID do not match. Assessor ETQE ID: " + file503Bean.getAssessorETQEId() + "  DesignationETQEId: " + file401Bean.getDesignationETQEId());
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (file401Bean.getDesignationId().equals("1") && (file503Bean.getAssessorETQEId() == file401Bean.getDesignationETQEId() && file503Bean.getAssessorRegistrationNumber() != file401Bean.getDesignationRegistrationNumber())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Assessor Registration Number and Designation Registration Number do not match. Assessor Registration Number: " + file503Bean.getAssessorRegistrationNumber() + "  DesignationRegistrationNumber: " + file401Bean.getDesignationRegistrationNumber());
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Assessor Registration Number End-------------------------------- */
			
			/* ----------------- Enrolment Type ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getEnrolmentTypeId() == null || file503Bean.getEnrolmentTypeId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrolment Type ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getEnrolmentTypeId() != null) {

				if (file503Bean.getEnrolmentTypeId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Type ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Enrolment Type ID End-------------------------------- */
			
			/* ----------------- Enrolment Status Date Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getEnrolmentStatusDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrolment Status Date is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getEnrolmentStatusDate() != null) {

				/* Business Rules (Compliance) */
				if (file503Bean.getEnrolmentStatusDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Date is greater than today's date");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			 /* ----------------- Enrolment Status Date End--------------------------------*/
			
			/* ----------------- Enrolment Date Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getEnrolmentDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrolment Date is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getEnrolmentDate() != null) {
				
				/* Business Rules (Compliance) */
				if (file503Bean.getEnrolmentDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Date is greater than today's date");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getEnrolmentDate().after(file503Bean.getMostRecentRegistrationDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Date is greater than Most Recent Registration Date");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getEnrolmentDate().after(file503Bean.getEnrolmentStatusDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Date is greater than Enrolment Status Date");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Enrolment Date End--------------------------------*/
			
			/* ----------------- Part Of Id Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getPartOfId() == null || file503Bean.getPartOfId().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Part Of Id is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getPartOfId() != null) {

				if (file503Bean.getPartOfId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Part Of Id starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file503Bean.getPartOfId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Part Of Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (!file503Bean.getPartOfId().equals("1") || !file503Bean.getPartOfId().equals("2") || !file503Bean.getPartOfId().equals("3") || !file503Bean.getPartOfId().equals("4") || !file503Bean.getPartOfId().equals("5") ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Part Of Id may only contain values 1,2,3,4,5");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Part Of Id End-------------------------------- */
			
			/* ----------------- Qualification ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getQualificationId() == null || file503Bean.getQualificationId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Qualification ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getQualificationId() != null) {

				if (file503Bean.getQualificationId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Qualification ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file503Bean.getPartOfId().equals("2") && file503Bean.getQualificationId() == null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Qualification ID must have a value");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (!file503Bean.getPartOfId().equals("2") && file503Bean.getQualificationId() != null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Qualification ID must not have a value");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Qualification ID End-------------------------------- */
			
			/* ----------------- Learnership ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getLearnershipId() == null || file503Bean.getLearnershipId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learnership ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}

			if (file503Bean.getLearnershipId() != null) {

				if (file503Bean.getLearnershipId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learnership ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file503Bean.getPartOfId().equals("3") && file503Bean.getLearnershipId() == null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learnership ID must have a value");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (!file503Bean.getPartOfId().equals("3") && file503Bean.getLearnershipId() != null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learnership ID must not have a value");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Learnership ID End-------------------------------- */
			
			/* ----------------- Provider Code Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getProviderCode() == null || file503Bean.getProviderCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}

			if (file503Bean.getProviderCode() != null) {

				if (file503Bean.getProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file503Bean.getProviderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (setmisFile100.contains(file503Bean.getProviderCode()) && !setmisFile100.contains(file503Bean.getProviderETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID Must Be In Parent File 100  ");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (!setmisFile100.contains(file503Bean.getProviderCode()) && setmisFile100.contains(file503Bean.getProviderETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code Must Be In Parent File 100 ");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Provider Code End-------------------------------- */
			
			/* ----------------- Provider ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getProviderETQEId() == null || file503Bean.getProviderETQEId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider ETQE ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}

			if (file503Bean.getProviderETQEId() != null) {

				if (file503Bean.getProviderETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Provider ETQE ID End-------------------------------- */
			
			/* ----------------- Assessor ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getAssessorETQEId() != null) {

				if (file503Bean.getAssessorETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Assessor ETQE ID End-------------------------------- */
			
			/* ----------------- Enrolment Status Reason ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getEnrolmentStatusReasonId() != null) {

				if (file503Bean.getEnrolmentStatusReasonId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Reason ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Enrolment Status Reason ID End-------------------------------- */
			
			/* ----------------- Most Recent Registration Date Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getMostRecentRegistrationDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Most Recent Registration Date is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}

			if (file503Bean.getMostRecentRegistrationDate() != null) {

				/* Business Rules (Compliance) */
				if (file503Bean.getMostRecentRegistrationDate().after(file503Bean.getEnrolmentStatusDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Most Recent Registration Date Is Greater Than Enrolment Date ");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getMostRecentRegistrationDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Most Recent Registration Date Is Greater Than Today's Date ");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Most Recent Registration Date End-------------------------------- */
			
			/* ----------------- Economic Status ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getEconomicStatusId() == null || file503Bean.getEconomicStatusId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Economic Status ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}

			if (file503Bean.getEconomicStatusId() != null) {

				if (file503Bean.getEconomicStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Economic Status ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Economic Status ID End-------------------------------- */
			
			/* ----------------- Cumulative Spend Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getCumulativeSpend() != null && file503Bean.getFundingId() != null) {

				if (file503Bean.getCumulativeSpend().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Cumulative Spend starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file503Bean.getCumulativeSpend() != null && !file503Bean.getFundingId().contains("1")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Cumulative Spend may only have a value if Funding ID has a value of 1 ");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Cumulative Spend End-------------------------------- */
			
			/* ----------------- Certificate Number Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getCertificateNumber() != null) {

				if (file503Bean.getCertificateNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file503Bean.getCertificateNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Certificate Number End-------------------------------- */
			
			/* ----------------- Funding ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getFundingId() == null || file503Bean.getFundingId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Funding ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}

			if (file503Bean.getFundingId() != null) {

				if (file503Bean.getFundingId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Funding ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */					// 1234 - arno 
				if (!file503Bean.getFundingId().equals("5") && file503Bean.getQualificationId() != null) {
					if (!setmisFile501.contains(file503Bean.getQualificationId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Qualification ID (" + file503Bean.getQualificationId() +") is not in Parent File 501 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile501.contains(file503Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID (" + file503Bean.getNationalId() +") is not in Parent File 501 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile501.contains(file503Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternate ID (" + file503Bean.getPersonAlternateId() +") is not in Parent File 501 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile501.contains(file503Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternate ID Type (" + file503Bean.getAlternativeIdType() +") is not in Parent File 501 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile501.contains(file503Bean.getProviderCode())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Code (" + file503Bean.getProviderCode() +") is not in Parent File 501 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile501.contains(file503Bean.getProviderETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider ETQE ID (" + file503Bean.getProviderETQEId() +") is not in Parent File 501 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
				}
				if (!file503Bean.getFundingId().equals("5") && file503Bean.getLearnershipId() != null) {
					if (!setmisFile500.contains(file503Bean.getLearnershipId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Learnership ID (" + file503Bean.getLearnershipId() +") is not in Parent File 500 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile500.contains(file503Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID (" + file503Bean.getNationalId() +") is not in Parent File 500 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile500.contains(file503Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternate ID (" + file503Bean.getPersonAlternateId() +") is not in Parent File 500 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile500.contains(file503Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternate ID Type (" + file503Bean.getAlternativeIdType() +") is not in Parent File 500 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile500.contains(file503Bean.getProviderCode())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Code (" + file503Bean.getProviderCode() +") is not in Parent File 500 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile500.contains(file503Bean.getProviderETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider ETQE ID (" + file503Bean.getProviderETQEId() +") is not in Parent File 500 ");
						extractError.setFileName("Setmis File 503");
						extractError.setTargetClass(SetmisFile503.class.getName());
						extractError.setFileId(file503Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Funding ID End-------------------------------- */
			
			/* ----------------- OFO Code Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getoFOCode() != null) {

				if (file503Bean.getoFOCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file503Bean.getoFOCode() != null && file503Bean.getLearnershipId() == null && !setmisFile500.contains(file503Bean.getoFOCode())) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code must be blank if Learnership ID is not blank and a related record for the learner exists in Parent File 500");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getoFOCode() != null && file503Bean.getQualificationId() == null && !setmisFile501.contains(file503Bean.getoFOCode())) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code must be blank if Qualification ID is not blank and a related record for the learner exists in Parent File 501");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
				Date myDefaultDate = format.parse("12/31/2018");
				
				if (file503Bean.getDateStamp().after(myDefaultDate) && file503Bean.getoFOCode() == null && file503Bean.getLearnershipId() == null && !setmisFile500.contains(file503Bean.getoFOCode())) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code Value must not be blank if Date Stamp has a value greater than 2018-12-31 Learnership ID is not blank and a related record for learner does not exist in Parent File 500");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getDateStamp().after(myDefaultDate) && file503Bean.getoFOCode() == null && file503Bean.getQualificationId() == null && !setmisFile500.contains(file503Bean.getoFOCode())) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code Value must not be blank if Date Stamp has a value greater than 2018-12-31 Qualification ID is not blank and a related record for learner does not exist in Parent File 501");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getDateStamp().after(myDefaultDate) && file503Bean.getoFOCode() == null && file503Bean.getQualificationId() == null && file503Bean.getLearnershipId() == null && file503Bean.getNonNQFIntervCode() == null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Value must be provided if Date Stamp has a value greater than 2018-12-31 and Qualification Id is blank and Learnership Id is blank and Non NQF Interv Code is blank");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getoFOCode() != null && file503Bean.getNonNQFIntervCode() == null && !setmisFile502.contains(file503Bean.getoFOCode())) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code must be blank if Non NQF Interv Code is not blank and a related record for the learner exists in Parent File 502");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (file503Bean.getDateStamp().after(myDefaultDate) && file503Bean.getoFOCode() == null && file503Bean.getNonNQFIntervCode() == null && !setmisFile502.contains(file503Bean.getoFOCode())) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code may not be blank if Date Stamp >= 2018-12-31 and Non NQF Interv Code is not blank and a related record for learner does not exist in Parent File 502");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- OFO Code End-------------------------------- */
			
			/* ----------------- Site No Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getSiteNo() == null || file503Bean.getSiteNo().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Site No is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}
			if (file503Bean.getSiteNo() != null) {

				if (file503Bean.getSiteNo().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site No starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file503Bean.getSiteNo());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site No contains character not in " + pattern);
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Site No End-------------------------------- */
			
			/* ----------------- Non NQF Interv Code Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getNonNQFIntervCode() != null) {

				if (file503Bean.getNonNQFIntervCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Code starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file503Bean.getNonNQFIntervCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file503Bean.getNonNQFIntervCode() == null && !file503Bean.getPartOfId().equals("5") ) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Code may not have a value if Part Of Id <> 5 ");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (setmisFile304.contains(file503Bean.getNonNQFIntervCode()) && !setmisFile304.contains(file503Bean.getNonNQFIntervETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv ETQE ID Must Be In Parent File 304  ");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
				if (!setmisFile304.contains(file503Bean.getNonNQFIntervCode()) && setmisFile304.contains(file503Bean.getNonNQFIntervETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Code Must Be In Parent File 304 ");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Non NQF Interv Code End-------------------------------- */
			
			/* ----------------- Non NQF Interv Code Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getNonNQFIntervETQEId() != null) {

				if (file503Bean.getNonNQFIntervETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National End-------------------------------- */
			
			/* ----------------- Urban Rural ID Start-------------------------------- */
			/* Content Rules */
			if (file503Bean.getUrbanRuralId() == null || file503Bean.getUrbanRuralId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Urban Rural ID is blank or null");
				extractError.setFileName("Setmis File 503");
				extractError.setTargetClass(SetmisFile503.class.getName());
				extractError.setFileId(file503Bean.getId());
				errorList.add(extractError);
			}

			if (file503Bean.getUrbanRuralId() != null) {

				if (file503Bean.getUrbanRuralId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Urban Rural ID starts with 'blank space'");
					extractError.setFileName("Setmis File 503");
					extractError.setTargetClass(SetmisFile503.class.getName());
					extractError.setFileId(file503Bean.getId());
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
