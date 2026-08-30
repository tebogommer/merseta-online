package haj.com.service.nlrd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.NLRDDAO;
import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.NLRDFile27Bean;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.NLRDFile27;
import haj.com.entity.SetmisFile100;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class NLRDFile27Service extends AbstractService {
	/** The dao. */
	private NLRDDAO dao = new NLRDDAO();

	public List<NLRDFile27Bean> extractNLRDFile27Bean() throws Exception {
		return dao.extractNLRDFile27Bean();
	}

	public int extractNLRDFile27Insert() throws Exception {
		return dao.extractNLRDFile27Insert();
	}

	public List<NLRDFile27> allNLRDFile27() throws Exception {
		return dao.allNLRDFile27();
	}

	public Integer combinationFieldCheck(String entityName, String learnershipId, Integer qualificationId, Integer unitStandardId, String designationId, String designationRegistrationNumber, String designationETQAId) throws Exception {
		return dao.combinationFieldCheck(entityName, learnershipId, qualificationId, unitStandardId, designationId, designationRegistrationNumber, designationETQAId);
	}

	public void extractNLRDFile27Validation(List<NLRDFile27> nlrdFile27) throws Exception {
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

		for (NLRDFile27 file27Bean : nlrdFile27) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- Learnership ID Start-------------------------------- */
			/* Content Rules */
			if (file27Bean.getLearnershipId() == null || file27Bean.getLearnershipId().toString().contentEquals("")) {
				extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "Learnership ID field Blank or null");
				errorList.add(extractError);
			}
			if (file27Bean.getLearnershipId() != null) {
				if (file27Bean.getLearnershipId().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "Learnership ID starts with 'blank space'");
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file27Bean.getLearnershipId() == null && file27Bean.getQualificationId() == null && file27Bean.getUnitStandardId() == null) {
					extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "Learnership ID and Qualification ID and UnitStandard ID is Null. It is compulsory that at least one of a Learnership ID or a Qualification ID or a Unit Standard ID must be provided for each record.");
					errorList.add(extractError);
				}
			}
			/* ----------------- Learnership ID End-------------------------------- */

			if (Collections.frequency(nlrdFile27, file27Bean.getLearnershipId()) > 1) {
				extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "Duplicate National ID: " + file27Bean.getLearnershipId());
				errorList.add(extractError);
			}

			if (Collections.frequency(nlrdFile27, file27Bean.getQualificationId()) > 1) {
				extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "Duplicate Qualification ID: " + file27Bean.getQualificationId());
				errorList.add(extractError);
			}

			if (Collections.frequency(nlrdFile27, file27Bean.getUnitStandardId()) > 1) {
				extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "Duplicate Unit Standard ID: " + file27Bean.getUnitStandardId());
				errorList.add(extractError);
			}

			if (Collections.frequency(nlrdFile27, file27Bean.getDesignationId()) > 1) {
				extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "Duplicate Designation ID: " + file27Bean.getDesignationId());
				errorList.add(extractError);
			}

			if (Collections.frequency(nlrdFile27, file27Bean.getDesignationRegistrationNumber()) > 1) {
				extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "Duplicate Designation Registration Number: " + file27Bean.getDesignationRegistrationNumber());
				errorList.add(extractError);
			}

			if (Collections.frequency(nlrdFile27, file27Bean.getDesignationETQAId()) > 1) {
				extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "Duplicate Designation ETQA ID: " + file27Bean.getDesignationETQAId());
				errorList.add(extractError);
			}

			Integer comboCheck = combinationFieldCheck(NLRDFile27.class.getName(), file27Bean.getLearnershipId(), file27Bean.getQualificationId(), file27Bean.getUnitStandardId(), file27Bean.getDesignationId(), file27Bean.getDesignationRegistrationNumber(), file27Bean.getDesignationETQAId());
			if (comboCheck > 1) {
				extractError = new ExtractErrorList(file27Bean.getId(), "NLRD File 27", "The combination Learnership Id, Qualification Id, Unit_Standard Id, Designation Id, Designation Registration Number and Designation ETQA Id must be unique.");
				errorList.add(extractError);
			}

			if (errorList.size() > 0) {
				dao.createBatch(errorList);
			}
		}
	}

//	if(!stringCheckIfNotNullOrNotEmpty(file100Bean.getProviderPhysicalAddressCode())){
//		extractError = new ExtractErrorList();
//		extractError.setNote("Provider Physical Address Code is blank or null");
//		extractError.setFileName("Setmis File 100");
//		extractError.setFileId(file100Bean.getId());
//		errorList.add(extractError);

//		extractError = new ExtractErrorList(file100Bean.getId(), "Setmis File 100", "Provider Physical Address Code is blank or null");
//		errorList.add(extractError);
//	}
//	private boolean stringCheckIfNotNullOrNotEmpty(String field){
//		if (field == null || field.contentEquals("")) {
//			return false;
//		} else {
//			return true;
//		}
//	}

}
