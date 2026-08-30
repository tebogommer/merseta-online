package haj.com.service.nlrd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.NLRDDAO;
import haj.com.dataextract.bean.NLRDFile26Bean;
import haj.com.dataextract.bean.NLRDFile28Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.NLRDFile26;
import haj.com.entity.NLRDFile28;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class NLRDFile28Service extends AbstractService {
	/** The dao. */
	private NLRDDAO dao = new NLRDDAO();

	public List<NLRDFile28Bean> extractNLRDFile28Bean() throws Exception {
		return dao.extractNLRDFile28Bean();
	}

	public int extractNLRDFile28Insert() throws Exception {
		return dao.extractNLRDFile28Insert();
	}

	public List<NLRDFile28> allNLRDFile28() throws Exception {
		return dao.allNLRDFile28();
	}

	public void extractNLRDFile28Validation(List<NLRDFile28> nlrdFile28) throws Exception {
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

		for (NLRDFile28 file28Bean : nlrdFile28) {
			ExtractErrorList extractError = new ExtractErrorList();
			
			if (file28Bean.getAlternativeIdType() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternative ID Type is blank or null");
				extractError.setFileName("NLRD File 28");
				extractError.setTargetClass(NLRDFile28.class.getName());
				extractError.setFileId(file28Bean.getId());
				errorList.add(extractError);
			}
			if (file28Bean.getLearnershipId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learnership ID is blank or null");
				extractError.setFileName("NLRD File 28");
				extractError.setTargetClass(NLRDFile28.class.getName());
				extractError.setFileId(file28Bean.getId());
				errorList.add(extractError);
			}
			if (file28Bean.getLearnerAchievementStatusId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learnership Achievement Status ID is blank or null");
				extractError.setFileName("NLRD File 28");
				extractError.setTargetClass(NLRDFile28.class.getName());
				extractError.setFileId(file28Bean.getId());
				errorList.add(extractError);
			}
			if (file28Bean.getLearnerAchievementStatusId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learnership Achievement Status ID is blank or null");
				extractError.setFileName("NLRD File 28");
				extractError.setTargetClass(NLRDFile28.class.getName());
				extractError.setFileId(file28Bean.getId());
				errorList.add(extractError);
			}
			if (file28Bean.getLearnerEnrolledDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learnership Enrolment Date is blank or null");
				extractError.setFileName("NLRD File 28");
				extractError.setTargetClass(NLRDFile28.class.getName());
				extractError.setFileId(file28Bean.getId());
				errorList.add(extractError);
			}
			if (file28Bean.getProviderCode() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code is blank or null");
				extractError.setFileName("NLRD File 28");
				extractError.setTargetClass(NLRDFile28.class.getName());
				extractError.setFileId(file28Bean.getId());
				errorList.add(extractError);
			}
			if (file28Bean.getProviderEtqaId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider ETQA ID is blank or null");
				extractError.setFileName("NLRD File 28");
				extractError.setTargetClass(NLRDFile28.class.getName());
				extractError.setFileId(file28Bean.getId());
				errorList.add(extractError);
			}
			
			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file28Bean.getNationalId() != null) {

				if (file28Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("NLRD File 28");
					extractError.setTargetClass(NLRDFile28.class.getName());
					extractError.setFileId(file28Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file28Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("NLRD File 28");
					extractError.setTargetClass(NLRDFile28.class.getName());
					extractError.setFileId(file28Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(nlrdFile28, file28Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file28Bean.getNationalId());
					extractError.setFileName("NLRD File 28");
					extractError.setTargetClass(NLRDFile28.class.getName());
					extractError.setFileId(file28Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile28, file28Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file28Bean.getPersonAlternateId());
					extractError.setFileName("NLRD File 28");
					extractError.setTargetClass(NLRDFile28.class.getName());
					extractError.setFileId(file28Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile28, file28Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file28Bean.getAlternativeIdType());
					extractError.setFileName("NLRD File 28");
					extractError.setTargetClass(NLRDFile28.class.getName());
					extractError.setFileId(file28Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile28, file28Bean.getLearnershipId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Learnership ID: " + file28Bean.getLearnershipId());
					extractError.setFileName("NLRD File 28");
					extractError.setTargetClass(NLRDFile28.class.getName());
					extractError.setFileId(file28Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */
			
			if (file28Bean.getAssessorRegistrationNumber() != null && file28Bean.getAssessorEtqaId() == "599") {
				for (NLRDFile26Bean nlrdFile26Bean : nlrdFile26) {
					if (nlrdFile26Bean.getDesignationRegistrationNumber() == file28Bean.getAssessorRegistrationNumber() && nlrdFile26Bean.getDesignationId() == "1") {
						extractError = new ExtractErrorList();
						extractError.setNote("Assessor Registration Number not the same as NLRD File 26");
						extractError.setFileName("NLRD File 28");
						extractError.setTargetClass(NLRDFile28.class.getName());
						extractError.setFileId(file28Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			if (!file28Bean.getLearnerAchievementStatusId().equals(2) || file28Bean.getLearnerAchievementStatusId().equals(29) && file28Bean.getLearnerAchievementDate() != null) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learner Achievement Date required if Learner Achievement Status not 2 or 29");
				extractError.setFileName("NLRD File 28");
				extractError.setTargetClass(NLRDFile28.class.getName());
				extractError.setFileId(file28Bean.getId());
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
