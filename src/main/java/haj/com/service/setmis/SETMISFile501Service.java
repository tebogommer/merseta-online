package haj.com.service.setmis;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.SETMISFile501Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile100;
import haj.com.entity.SetmisFile200;
import haj.com.entity.SetmisFile400;
import haj.com.entity.SetmisFile500;
import haj.com.entity.SetmisFile501;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile501Service extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	public Integer allActiveProviderCode(String entityName, String providerCode, String providerETQEId) throws Exception {
		return dao.allActiveProviderCode(entityName, providerCode, providerETQEId);
	}

	public Integer allActiveRegNumberFile401(String assessorRegistrationNumber, String providerETQEId) throws Exception {
		return dao.allActiveRegNumberFile401(assessorRegistrationNumber, providerETQEId);
	}

	public Integer allActiveSdlNumbersFile200(String sDLNo, BigInteger siteNo) throws Exception {
		return dao.allActiveSdlNumbersFile200(sDLNo, siteNo);
	}

	public Integer allAltIdType(String entityName, String alternativeIdType, String personAlternateId, String nationalId, String providerCode, String providerETQEId) throws Exception {
		return dao.allAltIdType(entityName, alternativeIdType, personAlternateId, nationalId, providerCode, providerETQEId);
	}

	public List<SETMISFile501Bean> extractSETMISFile501Bean() throws Exception {
		return dao.extractSETMISFile501Bean();
	}

	public int extractSetmisFile501Insert() throws Exception {
		return dao.extractSetmisFile501Insert();
	}

	public List<SetmisFile501> allSetmisFile501() throws Exception {
		return dao.allSetmisFile501();
	}

	public void extractSetmisFile501Validation(List<SetmisFile501> setmisFile501) throws Exception {
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
		Matcher m;

		for (SetmisFile501 file501Bean : setmisFile501) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file501Bean.getNationalId() == null || file501Bean.getNationalId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("National ID is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}
			if (file501Bean.getNationalId() != null) {

				if (file501Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file501Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile501, file501Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file501Bean.getNationalId());
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file501Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file501Bean.getPersonAlternateId());
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file501Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file501Bean.getAlternativeIdType());
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file501Bean.getQualificationId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Designation ID: " + file501Bean.getQualificationId());
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file501Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Code: " + file501Bean.getProviderCode());
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile501, file501Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQE ID: " + file501Bean.getProviderETQEId());
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */

			/*
			 * ----------------- Person Alternate ID Start--------------------------------
			 */
			/* Content Rules */
			if (file501Bean.getPersonAlternateId() != null) {

				if (file501Bean.getPersonAlternateId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file501Bean.getPersonAlternateId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Person Alternate ID End-------------------------------- */

			/* ----------------- Alternate ID Type Start-------------------------------- */
			/* Content Rules */
			if (file501Bean.getAlternativeIdType() == null || file501Bean.getAlternativeIdType().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternate ID Type is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}
			if (file501Bean.getAlternativeIdType() != null) {

				if (file501Bean.getAlternativeIdType().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID Type starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file501Bean.getAlternativeIdType());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID Type contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (SetmisFile400.class.getName() != null && file501Bean.getAlternativeIdType() != null && file501Bean.getPersonAlternateId() != null && file501Bean.getNationalId() != null && file501Bean.getProviderCode() != null && file501Bean.getProviderETQEId() != null) {

					Integer altId400CheckIdEq537 = allAltIdType(SetmisFile400.class.getName(), file501Bean.getAlternativeIdType(), file501Bean.getPersonAlternateId(), file501Bean.getNationalId(), file501Bean.getProviderCode(), file501Bean.getProviderETQEId());
					if (Integer.parseInt(file501Bean.getAlternativeIdType()) == 537 && altId400CheckIdEq537 <= 0) {
						extractError = new ExtractErrorList();
						extractError.setNote("If Alternate Id Type Id = 537 then the combination of Alternate Id Type Id, Person Alternate Id, National Id, Provider Code and Provider ETQE Id must exist in parent file 400 - Person");
						extractError.setFileName("Setmis File 501");
						extractError.setTargetClass(SetmisFile501.class.getName());
						extractError.setFileId(file501Bean.getId());
						errorList.add(extractError);
					}

					Integer altId400CheckIdNot537 = allAltIdType(SetmisFile400.class.getName(), file501Bean.getAlternativeIdType(), file501Bean.getPersonAlternateId(), file501Bean.getNationalId(), file501Bean.getProviderCode(), file501Bean.getProviderETQEId());
					if (Integer.parseInt(file501Bean.getAlternativeIdType()) != 537 && altId400CheckIdNot537 <= 0) {
						extractError = new ExtractErrorList();
						extractError.setNote("If Alternate Id Type Id not equal to 537 then the combination of Alternate Id Type Id, National Id and Person Alternate Id must exist in parent file 400 - Person");
						extractError.setFileName("Setmis File 501");
						extractError.setTargetClass(SetmisFile501.class.getName());
						extractError.setFileId(file501Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Alternate ID Type End-------------------------------- */

			/* ----------------- Qualification ID Start-------------------------------- */
			/* Content Rules */
			if (file501Bean.getQualificationId() == null || file501Bean.getQualificationId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Qualification ID is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getQualificationId() != null) {

				if (file501Bean.getQualificationId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Qualification ID starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}

			/* ----------------- Qualification ID End-------------------------------- */

			/*
			 * ----------------- Enrollment Status Id Start--------------------------------
			 */
			/* Content Rules */

			if (file501Bean.getEnrolmentStatusId() == null || file501Bean.getEnrolmentStatusId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrollment Status ID is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getEnrolmentStatusId() != null) {

				if (file501Bean.getEnrolmentStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Status ID starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getEnrolmentStatusId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Status ID contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (!file501Bean.getEnrolmentStatusId().equals("10") && file501Bean.getEnrolmentStatusReasonId() != null) {
					extractError = new ExtractErrorList();
					extractError.setNote("If Enrolment Status Id does not have a value of 10 then Enrolment Status Reason Id must be blank");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (file501Bean.getEnrolmentStatusId().equals("10") && file501Bean.getEnrolmentStatusReasonId() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("If Enrolment Status Id has a value of 10 then Enrolment Status Reason Id must have a value");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (file501Bean.getEnrolmentStatusId().equals("15") && file501Bean.getCertificateNumber() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("If Enrolment Status Id has a value of 15 then Certificate Number must have a value");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (!file501Bean.getEnrolmentStatusId().equals("15") && file501Bean.getCertificateNumber() != null) {
					extractError = new ExtractErrorList();
					extractError.setNote("If Enrolment Status Id has a value of 15 then Certificate Number must be blank");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}
			/* Business Rules (Compliance) */

			/* ----------------- Enrollment Status Id End-------------------------------- */

			/*
			 * ----------------- Assessor Registration Number
			 * Start--------------------------------
			 */
			/* Content Rules */

			if (file501Bean.getAssessorRegistrationNumber() != null) {

				if (file501Bean.getAssessorRegistrationNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor Registration Number starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file501Bean.getAssessorRegistrationNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor Registration Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				/* Business Rules (Compliance) */
				Integer setAssesReg = allActiveRegNumberFile401(file501Bean.getAssessorRegistrationNumber(), file501Bean.getPracticalProviderETQEId());
				if (setAssesReg <= 0) {
					extractError = new ExtractErrorList();
					extractError.setNote("The combination Assessor Registration Number and Assessor ETQE Id must exist in parent file 401");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}

			/*
			 * ----------------- Assessor Registration Number
			 * End--------------------------------
			 */

			/* ----------------- Enrolment Type Id Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getEnrolmentTypeId() == null || file501Bean.getEnrolmentTypeId().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrolment Type Id is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getEnrolmentTypeId() != null) {

				if (file501Bean.getEnrolmentTypeId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Type Id starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file501Bean.getEnrolmentTypeId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Type Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}

			/* ----------------- Enrolment Type Id End-------------------------------- */

			/*
			 * ----------------- Enrollment Status Date
			 * Start--------------------------------
			 */
			/* Content Rules */

			if (file501Bean.getEnrolmentStatusDate() == null || file501Bean.getEnrolmentStatusDate().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrollment Status Date blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getEnrolmentStatusDate() != null) {

				if (file501Bean.getEnrolmentStatusDate().toString().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Status Date starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (file501Bean.getEnrolmentStatusDate().after(new Date())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Status Date is greater than todays date ");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (GenericUtility.yearOfDate(file501Bean.getEnrolmentStatusDate()) < 1900) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Status Start Date is less than 1900 ");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}
			/*
			 * ----------------- Enrollment Status Date End--------------------------------
			 */

			/* ----------------- Enrollment Date Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getEnrolmentDate() == null || file501Bean.getEnrolmentDate().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Enrollment Date is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getEnrolmentDate() != null) {

				if (file501Bean.getEnrolmentDate().toString().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Date starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (file501Bean.getMostRecentRegistrationDate().after(file501Bean.getEnrolmentDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Date may not be greater than Most Recent Registration Date");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (file501Bean.getEnrolmentStatusDate().after(file501Bean.getEnrolmentDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Date may not be greater than Enrolment Status Date");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (file501Bean.getEnrolmentDate().after(new Date())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Date is greater than todays date ");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (GenericUtility.yearOfDate(file501Bean.getEnrolmentDate()) < 1900) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrollment Start Date is less than 1900 ");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}

			/* ----------------- Enrollment Date End-------------------------------- */

			/* ----------------- Part Of Id Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getPartOfId() == null || file501Bean.getPartOfId().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Part Of Id is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getPartOfId() != null) {

				if (file501Bean.getPartOfId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Part Of Id starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file501Bean.getPartOfId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Part Of Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (!file501Bean.getPartOfId().equals("1") || !file501Bean.getPartOfId().equals("3") || !file501Bean.getPartOfId().equals("4")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Part Of Id may only contain values 1,3,4");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}

			/* ----------------- Part Of Id End-------------------------------- */

			/* ----------------- Learnership Id Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getLearnershipId() != null) {

				if (file501Bean.getLearnershipId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learnership Id starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getLearnershipId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learnership Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}
			if (file501Bean.getLearnershipId() == null && file501Bean.getPartOfId().equals("3")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learnership Id must have a value if 'Part Of Id' = 3 ");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getLearnershipId() != null && !file501Bean.getPartOfId().equals("3")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learnership Id may not have a value if 'Part Of Id' does not equal 3");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			/* ----------------- Learnership Id End-------------------------------- */

			/* ----------------- Provider Code Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getProviderCode() == null || file501Bean.getProviderCode().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getProviderCode() != null) {

				if (file501Bean.getProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file501Bean.getProviderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (SetmisFile100.class.getName() != null && file501Bean.getProviderCode() != null && file501Bean.getProviderETQEId() != null) {

					Integer setProvCode = allActiveProviderCode(SetmisFile100.class.getName(), file501Bean.getProviderCode(), file501Bean.getProviderETQEId());

					if (setProvCode <= 0) {
						extractError = new ExtractErrorList();
						extractError.setNote("The combination of Provider Code and Provider ETQE Id must exist on 101 - Provider");
						extractError.setFileName("Setmis File 501");
						extractError.setTargetClass(SetmisFile501.class.getName());
						extractError.setFileId(file501Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Provider Code End-------------------------------- */

			/* ----------------- Provider ETQE ID Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getProviderETQEId() == null || file501Bean.getProviderETQEId().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getProviderETQEId() != null) {

				if (file501Bean.getProviderETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getProviderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}

			/* ----------------- Provider ETQE ID End-------------------------------- */

			/* ----------------- Assessor ETQE Id Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getProviderETQEId() != null) {

				if (file501Bean.getProviderETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor ETQE Id starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getProviderETQEId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Assessor ETQE Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}
			/* ----------------- Assessor ETQE Id End-------------------------------- */

			/*
			 * ----------------- Enrolment Status Reason Id
			 * Start--------------------------------
			 */
			/* Content Rules */

			if (file501Bean.getEnrolmentStatusReasonId() != null) {

				if (file501Bean.getEnrolmentStatusReasonId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Reason Id starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getEnrolmentStatusReasonId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Reason Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}

			/*
			 * ----------------- Enrolment Status Reason Id
			 * End--------------------------------
			 */

			/*
			 * ----------------- Most Recent Registration Date
			 * Start--------------------------------
			 */
			/* Content Rules */

			if (file501Bean.getMostRecentRegistrationDate() == null || file501Bean.getMostRecentRegistrationDate().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Most Recent Registration Date is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getMostRecentRegistrationDate() != null) {

				if (file501Bean.getMostRecentRegistrationDate().toString().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Most Recent Registration Date starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */

				if (file501Bean.getEnrolmentDate().after(file501Bean.getMostRecentRegistrationDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Most Recent Registration Date may not be greater than Most Recent Registration Date");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (GenericUtility.yearOfDate(file501Bean.getMostRecentRegistrationDate()) < 1900) {
					extractError = new ExtractErrorList();
					extractError.setNote("Most Recent Registration Date is less than 1900 ");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (file501Bean.getMostRecentRegistrationDate().after(new Date())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Most Recent Registration Date is greater than todays date ");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}
			/*
			 * ----------------- Most Recent Registration Date
			 * End--------------------------------
			 */

			/* ----------------- Certificate Number Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getCertificateNumber() != null) {

				if (file501Bean.getCertificateNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r.matcher(file501Bean.getCertificateNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Certificate Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Certificate Number End-------------------------------- */

			/* ----------------- Economic Status Id Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getEconomicStatusId() == null || file501Bean.getEconomicStatusId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Economic Status Id is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getEconomicStatusId() != null) {

				if (file501Bean.getEconomicStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Economic Status Id starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getEconomicStatusId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Economic Status Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}

			/* ----------------- Economic Status Id End-------------------------------- */

			/* ----------------- SDL No Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getsDLNo() != null) {

				if (file501Bean.getsDLNo().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("SDL No starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (!file501Bean.getsDLNo().startsWith("L") || !file501Bean.getsDLNo().startsWith("N")) {
					extractError = new ExtractErrorList();
					extractError.setNote("SDL No starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getsDLNo().subSequence(1, 10));
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("SDL No contains character not in " + regexNumbers + "after 'N' or 'L'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}

			if (file501Bean.getsDLNo() == null && file501Bean.getEconomicStatusId().equals("1") || file501Bean.getEconomicStatusId().equals("3")) {
				extractError = new ExtractErrorList();
				extractError.setNote("SDL No must be provided if Economic Status Id has a value of 1 or 3");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (SetmisFile200.class.getName() != null && file501Bean.getsDLNo() != null && file501Bean.getSiteNo() != null) {

				Integer setSdlFile200 = allActiveSdlNumbersFile200(file501Bean.getsDLNo().toString(), file501Bean.getSiteNo());
				if (setSdlFile200 <= 0) {
					extractError = new ExtractErrorList();
					extractError.setNote("The combination of SDL No and Site No must exist on 200 - Employer");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}

			/* ----------------- SDL No End-------------------------------- */

			/* ----------------- Site No Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getSiteNo() != null) {

				if (file501Bean.getSiteNo().toString().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site No starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r.matcher(file501Bean.getSiteNo().toString());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site No contains character not in " + pattern);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Site No End-------------------------------- */

			/*
			 * ----------------- Practical Provider Code
			 * Start--------------------------------
			 */
			/* Content Rules */

			if (file501Bean.getPracticalProviderCode() != null) {

				if (file501Bean.getPracticalProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Practical Provider Code starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r.matcher(file501Bean.getPracticalProviderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Practical Provider Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (SetmisFile100.class.getName() != null && file501Bean.getProviderCode() != null && file501Bean.getProviderETQEId() != null) {

					Integer setProvCode = allActiveProviderCode(SetmisFile100.class.getName(), file501Bean.getProviderCode(), file501Bean.getProviderETQEId());
					if (setProvCode <= 0) {
						extractError = new ExtractErrorList();
						extractError.setNote("The combination of Practical Provider Code and Practical Provider ETQE Id must exist on 101 - Provider as Provider Code and Provider ETQE ID");
						extractError.setFileName("Setmis File 501");
						extractError.setTargetClass(SetmisFile501.class.getName());
						extractError.setFileId(file501Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/*
			 * ----------------- Practical Provider Code End--------------------------------
			 */

			/*
			 * ----------------- Practical Provider ETQE Id
			 * Start--------------------------------
			 */
			/* Content Rules */

			if (file501Bean.getPracticalProviderETQEId() != null) {

				if (file501Bean.getEconomicStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Practical Provider ETQE Id starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getPracticalProviderETQEId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Practical Provider ETQE Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}

			/*
			 * ----------------- Practical Provider ETQE Id
			 * End--------------------------------
			 */

			/* ----------------- Funding Id Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getFundingId() == null || file501Bean.getFundingId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Funding Id is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getFundingId() != null) {

				if (file501Bean.getFundingId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Funding Id starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getFundingId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Funding Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (!file501Bean.getFundingId().equals("1") || !file501Bean.getFundingId().equals("2") || !file501Bean.getFundingId().equals("4")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Funding Id may only have a value of 1, 2 or 4");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}

			/* Business Rules (Compliance) */

			/* ----------------- Funding Id End-------------------------------- */

			/* ----------------- Cumulative Spend Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getCumulativeSpending() != null) {

				if (file501Bean.getCumulativeSpending().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Cumulative Spend starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getCumulativeSpending());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Cumulative Spend contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				if (!file501Bean.getCumulativeSpending().equals("1") || !file501Bean.getFundingId().equals("2") || !file501Bean.getFundingId().equals("4")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Cumulative Spend may only have a value of 1, 2 or 4");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
				if (file501Bean.getFundingId() != null && !file501Bean.getFundingId().equals(" ")) {

					if (file501Bean.getCumulativeSpending() != null && !file501Bean.getFundingId().equals("1")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Cumulative Spend may only have a value if Funding Id has a value of 1");
						extractError.setFileName("Setmis File 501");
						extractError.setTargetClass(SetmisFile501.class.getName());
						extractError.setFileId(file501Bean.getId());
						errorList.add(extractError);
					}
				}
			}

			/* Business Rules (Compliance) */

			/* ----------------- Cumulative Spend End-------------------------------- */

			/* ----------------- OFO Code Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getoFOCode() != null) {

				if (file501Bean.getoFOCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getoFOCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}
			/* Business Rules (Compliance) */
			SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
			Date myDefaultDate = format.parse("12/31/2018");
			if (file501Bean.getoFOCode() == null && file501Bean.getDateStamp().after(myDefaultDate)) {
				extractError = new ExtractErrorList();
				extractError.setNote("OFO Code starts with 'blank space'");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			/* ----------------- OFO Code End-------------------------------- */

			/* ----------------- Urban Rural ID Start-------------------------------- */
			/* Content Rules */

			if (file501Bean.getUrbanRuralId() == null || file501Bean.getUrbanRuralId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Urban Rural ID is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getUrbanRuralId() != null) {

				if (file501Bean.getUrbanRuralId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Urban Rural ID starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getUrbanRuralId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Urban Rural ID contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

			}

			/* ----------------- Urban Rural ID End-------------------------------- */

			/*
			 * ----------------- Learning Programme Type Id
			 * Start--------------------------------
			 */
			/* Content Rules */
if (file501Bean.getSiteNo() != null) {
	
			if (file501Bean.getLearningProgrammeTypeId() == null || file501Bean.getSiteNo().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learning Programme Type Id is blank or null");
				extractError.setFileName("Setmis File 501");
				extractError.setTargetClass(SetmisFile501.class.getName());
				extractError.setFileId(file501Bean.getId());
				errorList.add(extractError);
			}

			if (file501Bean.getLearningProgrammeTypeId() != null) {

				if (file501Bean.getLearningProgrammeTypeId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learning Programme Type Id starts with 'blank space'");
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}

				m = r2.matcher(file501Bean.getLearningProgrammeTypeId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learning Programme Type Id contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 501");
					extractError.setTargetClass(SetmisFile501.class.getName());
					extractError.setFileId(file501Bean.getId());
					errorList.add(extractError);
				}
			}
}

			/*
			 * ----------------- Learning Programme Type Id
			 * End--------------------------------
			 */

		}
		if (errorList.size() > 0) {
			dao.createBatch(errorList);

		}
	}
}
