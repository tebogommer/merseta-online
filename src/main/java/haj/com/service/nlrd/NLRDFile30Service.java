package haj.com.service.nlrd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.NLRDDAO;
import haj.com.dataextract.bean.NLRDFile26Bean;
import haj.com.dataextract.bean.NLRDFile30Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.NLRDFile30;
import haj.com.entity.NLRDFile30;
import haj.com.entity.NLRDFile30;
import haj.com.entity.NLRDFile30;
import haj.com.entity.NLRDFile30;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class NLRDFile30Service extends AbstractService {
	/** The dao. */
	private NLRDDAO dao = new NLRDDAO();

	public List<NLRDFile30Bean> extractNLRDFile30Bean() throws Exception {
		return dao.extractNLRDFile30Bean();
	}

	public int extractNLRDFile30Insert() throws Exception {
		return dao.extractNLRDFile30Insert();
	}

	public List<NLRDFile30> allNLRDFile30() throws Exception {
		return dao.allNLRDFile30();
	}

	public void extractNLRDFile30Validation(List<NLRDFile30> nlrdFile30) throws Exception {
		List<IDataEntity> errorList = new ArrayList<IDataEntity>();
		List<String> reservedwords = new ArrayList<String>();
		NLRDFile26Service nlrdFile26Service = new NLRDFile26Service();
		List<NLRDFile26Bean> nlrdFile26 =  nlrdFile26Service.extractNLRDFile26Bean();
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

		for (NLRDFile30 file30Bean : nlrdFile30) {
			ExtractErrorList extractError = new ExtractErrorList();
			
			if (file30Bean.getAlternativeIdType() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternative ID Type field Blank or null");
				extractError.setFileName("NRLD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
				errorList.add(extractError);
			}
			if (file30Bean.getUnitStandardId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("UnitStandard ID Type field Blank or null");
				extractError.setFileName("NRLD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
				errorList.add(extractError);
			}
			if (file30Bean.getLearnerAchievementStatusId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learner Achievement Status ID field Blank or null");
				extractError.setFileName("NRLD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
				errorList.add(extractError);
			}
			if (file30Bean.getLearnerAchievementTypeId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learner Achievement Type ID field Blank or null");
				extractError.setFileName("NRLD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
				errorList.add(extractError);
			}
			if (file30Bean.getPartof() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Part Of field Blank or null");
				extractError.setFileName("NRLD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
				errorList.add(extractError);
			}
			if (file30Bean.getProviderCode() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code field Blank or null");
				extractError.setFileName("NRLD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
				errorList.add(extractError);
			}
			if (file30Bean.getProviderEtqaId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider ETQA ID field Blank or null");
				extractError.setFileName("NRLD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
				errorList.add(extractError);
			}
			if (file30Bean.getAssessorEtqaId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Assessor ETQA ID field Blank or null");
				extractError.setFileName("NRLD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
				errorList.add(extractError);
			}
			
			if (file30Bean.getNationalId() != null) {

				if (file30Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("NLRD File 30");
					extractError.setTargetClass(NLRDFile30.class.getName());
					extractError.setFileId(file30Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile30, file30Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file30Bean.getNationalId());
					extractError.setFileName("NLRD File 30");
					extractError.setTargetClass(NLRDFile30.class.getName());
					extractError.setFileId(file30Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile30, file30Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file30Bean.getPersonAlternateId());
					extractError.setFileName("NLRD File 30");
					extractError.setTargetClass(NLRDFile30.class.getName());
					extractError.setFileId(file30Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile30, file30Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file30Bean.getAlternativeIdType());
					extractError.setFileName("NLRD File 30");
					extractError.setTargetClass(NLRDFile30.class.getName());
					extractError.setFileId(file30Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile30, file30Bean.getUnitStandardId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Unitstandard ID: " + file30Bean.getLearnershipId());
					extractError.setFileName("NLRD File 30");
					extractError.setTargetClass(NLRDFile30.class.getName());
					extractError.setFileId(file30Bean.getId());
					errorList.add(extractError);
				}
			}
			if (file30Bean.getAssessorRegistrationNumber() != null && file30Bean.getAssessorEtqaId() == "599") {
				for (NLRDFile26Bean nlrdFile26Bean : nlrdFile26) {
					if (nlrdFile26Bean.getDesignationRegistrationNumber() == file30Bean.getAssessorRegistrationNumber() && nlrdFile26Bean.getDesignationId() == "1") {
						extractError = new ExtractErrorList();
						extractError.setNote("Assessor Registration Number not the same as NLRD File 26");
						extractError.setFileName("NLRD File 30");
						extractError.setTargetClass(NLRDFile30.class.getName());
						extractError.setFileId(file30Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			if (file30Bean.getPartof() != "1" || file30Bean.getPartof() != "2" || file30Bean.getPartof() != "3") {
				extractError = new ExtractErrorList();
				extractError.setNote("Part of may only contain 1, 2 or 3");
				extractError.setFileName("NLRD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
				errorList.add(extractError);
			}
			if (!file30Bean.getLearnerAchievementStatusId().equals(2) || file30Bean.getLearnerAchievementStatusId().equals(29) && file30Bean.getLearnerAchievementDate() != null) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learner Achievement Date required if Learner Achievement Status not 2 or 29");
				extractError.setFileName("NLRD File 30");
				extractError.setTargetClass(NLRDFile30.class.getName());
				extractError.setFileId(file30Bean.getId());
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
