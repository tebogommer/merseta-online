package haj.com.service.setmis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.SETMISFile401Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile400;
import haj.com.entity.SetmisFile401;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile401Service extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	public List<SETMISFile401Bean> extractSETMISFile401Bean() throws Exception {
		return dao.extractSETMISFile401Bean();
	}

	public int extractSetmisFile401Insert() throws Exception {
		return dao.extractSetmisFile401Insert();
	}

	public List<SetmisFile401> allSetmisFile401() throws Exception {
		return dao.allSetmisFile401();
	}

	public void extractSetmisFile401Validation(List<SetmisFile401> setmisFile401) throws Exception {
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

		for (SetmisFile401 file401Bean : setmisFile401) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.getNationalId() == null || file401Bean.getNationalId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("National ID is blank or null");
				extractError.setFileName("Setmis File 401");
				extractError.setTargetClass(SetmisFile401.class.getName());
				extractError.setFileId(file401Bean.getId());
				errorList.add(extractError);
			}
			if (file401Bean.getNationalId() != null) {

				if (file401Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file401Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile401, file401Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file401Bean.getNationalId());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile401, file401Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file401Bean.getPersonAlternateId());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile401, file401Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file401Bean.getAlternativeIdType());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile401, file401Bean.getDesignationId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Designation ID: " + file401Bean.getDesignationId());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile401, file401Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Code: " + file401Bean.getProviderCode());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile401, file401Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQE ID: " + file401Bean.getProviderETQEId());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */

			/* ----------------- Person Alternate ID Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.getPersonAlternateId() != null) {

				if (file401Bean.getPersonAlternateId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file401Bean.getPersonAlternateId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Person Alternate ID End-------------------------------- */
			
			/* ----------------- Alternate ID Type Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.getAlternativeIdType() == null || file401Bean.getAlternativeIdType().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternate ID Type is blank or null");
				extractError.setFileName("Setmis File 401");
				extractError.setTargetClass(SetmisFile401.class.getName());
				extractError.setFileId(file401Bean.getId());
				errorList.add(extractError);
			}
			if (file401Bean.getAlternativeIdType() != null) {

				if (file401Bean.getAlternativeIdType().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID Type starts with 'blank space'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file401Bean.getAlternativeIdType());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID Type contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Integer.parseInt(file401Bean.getAlternativeIdType()) != 537) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID Type not equal to 537");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Alternate ID Type End-------------------------------- */
			
			/* ----------------- Designation ID Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.getDesignationId() == null || file401Bean.getDesignationId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Designation ID is blank or null");
				extractError.setFileName("Setmis File 401");
				extractError.setTargetClass(SetmisFile401.class.getName());
				extractError.setFileId(file401Bean.getId());
				errorList.add(extractError);
			}
			if (file401Bean.getDesignationId() != null) {

				if (file401Bean.getDesignationId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Designation ID starts with 'blank space'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Designation ID End-------------------------------- */
			
			/* ----------------- Designation Registration Number Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.getDesignationRegistrationNumber() == null || file401Bean.getDesignationRegistrationNumber().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Designation Registration Number Code is blank or null");
				extractError.setFileName("Setmis File 401");
				extractError.setTargetClass(SetmisFile401.class.getName());
				extractError.setFileId(file401Bean.getId());
				errorList.add(extractError);
			}
			if (file401Bean.getDesignationRegistrationNumber() != null) {

				if (file401Bean.getDesignationRegistrationNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Designation Registration Number starts with 'blank space'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file401Bean.getDesignationRegistrationNumber());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Designation Registration Number contains character not in " + pattern);
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile401, file401Bean.getDesignationRegistrationNumber()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Designation Registration Number Must Be Unique : " + file401Bean.getDesignationRegistrationNumber());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile401, file401Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code Must Be Unique: " + file401Bean.getProviderCode());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile401, file401Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID Must Be Unique: " + file401Bean.getProviderETQEId());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Designation Registration Number End-------------------------------- */
			
			/* ----------------- Designation Start Date Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.getDesignationStartDate() != null) {

				if (file401Bean.getDesignationStartDate().after(file401Bean.getDesignationEndDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Designation Start Date is greater than end date  ");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Designation Start Date End-------------------------------- */
			
			/* ----------------- Designation Structure Status ID Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.getDesignationStructureStatusId() == null || file401Bean.getDesignationStructureStatusId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Designation Structure Status ID is blank or null");
				extractError.setFileName("Setmis File 401");
				extractError.setTargetClass(SetmisFile401.class.getName());
				extractError.setFileId(file401Bean.getId());
				errorList.add(extractError);
			}
			if (file401Bean.getDesignationStructureStatusId() != null) {

				if (file401Bean.getDesignationStructureStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Designation Structure Status ID starts with 'blank space'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (Integer.parseInt(file401Bean.getDesignationStructureStatusId()) == 501 || Integer.parseInt(file401Bean.getDesignationStructureStatusId()) == 505 && file401Bean.getDesignationEndDate() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Designation End Date may not be blank'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Integer.parseInt(file401Bean.getDesignationStructureStatusId()) != 501 || Integer.parseInt(file401Bean.getDesignationStructureStatusId()) != 505 && file401Bean.getDesignationEndDate() != null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Designation End Date must be blank'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Designation Structure Status ID End-------------------------------- */
			
			/* ----------------- ETQE Decision Number Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.geteTQEDecisionNumber() != null) {

				if (file401Bean.geteTQEDecisionNumber().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("ETQE Decision Number starts with 'blank space' ");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile401, file401Bean.geteTQEDecisionNumber()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("ETQE Decision Number Must Be Unique : " + file401Bean.geteTQEDecisionNumber());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile401, file401Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code Must Be Unique : " + file401Bean.getProviderCode());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile401, file401Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID Must Be Unique : " + file401Bean.getProviderETQEId());
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- ETQE Decision Number End-------------------------------- */
			
			/* ----------------- Provider Code Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.getProviderCode() != null) {

				if (file401Bean.getProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code starts with 'blank space'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file401Bean.getProviderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Provider Code End-------------------------------- */
			
			/* ----------------- Provider ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file401Bean.getProviderETQEId() != null) {

				if (file401Bean.getProviderETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 401");
					extractError.setTargetClass(SetmisFile401.class.getName());
					extractError.setFileId(file401Bean.getId());
					errorList.add(extractError);
				}
			}
			
			/* ----------------- Provider ETQE ID End-------------------------------- */
		}
		if (errorList.size() > 0) {
			dao.createBatch(errorList);

		}
	}
}
