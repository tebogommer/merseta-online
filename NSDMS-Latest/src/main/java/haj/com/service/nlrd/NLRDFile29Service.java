package haj.com.service.nlrd;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import haj.com.dao.NLRDDAO;
import haj.com.dataextract.bean.NLRDFile29Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.NLRDFile29;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class NLRDFile29Service extends AbstractService {
	/** The dao. */
	private NLRDDAO dao = new NLRDDAO();

	public List<NLRDFile29Bean> extractNLRDFile29Bean() throws Exception {
		return dao.extractNLRDFile29Bean();
	}

	public int extractNLRDFile29Insert() throws Exception {
		return dao.extractNLRDFile29Insert();
	}

	public List<NLRDFile29> allNLRDFile29() throws Exception {
		return dao.allNLRDFile29();
	}
	
	public Integer combinationFieldCheck(String entityName,String nationalId, String personAlternateId, BigInteger alternativeIdType, Integer qualificationId) throws Exception {
		return dao.combinationFieldCheck(entityName, nationalId, personAlternateId, alternativeIdType, qualificationId);
	}
	
	public Boolean nullorBlank(Object object) throws Exception {
		if (object == null || ((String) object).toString().contentEquals("")) {
			return true;	
		}
		else return false ;
	}
	

	public void extractNLRDFile29Validation(List<NLRDFile29> nlrdFile29) throws Exception {
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

		for (NLRDFile29 file29Bean : nlrdFile29) {
			ExtractErrorList extractError = new ExtractErrorList();
			Long fileId = file29Bean.getId();
			String fileName = "NLRD File 29"; 
			String targetClass = NLRDFile29.class.getName();
			Date today = new Date();
			
			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getNationalId())) {
				extractError = new ExtractErrorList(fileId, fileName, "National ID field Blank or null", targetClass);
				errorList.add(extractError);
			}
			if (file29Bean.getNationalId() != null) {

				if (file29Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "National ID starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
				
				m = r2.matcher(file29Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList(fileId, fileName, "National ID contains character not in " + regexNumbers , targetClass);
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(nlrdFile29, file29Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList(fileId, fileName, "Duplicate National ID: " + file29Bean.getNationalId(), targetClass);
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile29, file29Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList(fileId, fileName, "Duplicate Person Alternate ID: " + file29Bean.getPersonAlternateId(), targetClass);
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile29, file29Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList(fileId, fileName, "Duplicate Alternate ID Type: " + file29Bean.getAlternativeIdType(), targetClass);
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile29, file29Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList(fileId, fileName, "Duplicate Provider Code: " + file29Bean.getProviderCode(), targetClass);
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile29, file29Bean.getProviderEtqaId()) > 1) {
					extractError = new ExtractErrorList(fileId, fileName, "Duplicate Provider ETQA ID: " + file29Bean.getProviderEtqaId(), targetClass);
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */
			
			/* ----------------- Person Alternate Id Start-------------------------------- */
			/* Content Rules */
			
			if (file29Bean.getPersonAlternateId() != null) {

				if (file29Bean.getPersonAlternateId().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Person Alternate Id starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
			}
			
			/* ----------------- Person Alternate Id End-------------------------------- */
				
			
			
			/* ----------------- Alternative Id Type Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getAlternativeIdType())) {
				extractError = new ExtractErrorList(fileId, fileName, "Alternative ID field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getAlternativeIdType() != null) {

				if (file29Bean.getAlternativeIdType().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Alternative ID starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
			}
			
			/* ----------------- Alternative Id Type End-------------------------------- */
			
			
			/* ----------------- Qualification Id Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getQualificationId())) {
				extractError = new ExtractErrorList(fileId, fileName, "Qualification ID field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getQualificationId() != null) {

				if (file29Bean.getQualificationId().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Qualification ID starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
			}
			
			/* ----------------- Qualification Id End-------------------------------- */
			
			/* ----------------- Learner Achievement Status Id Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getLearnerAchievementStatusId())) {
				extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Status Id field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getLearnerAchievementStatusId() != null) {

				if (file29Bean.getLearnerAchievementStatusId().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Status Id starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
			}
			
			/* ----------------- Learner Achievement Status Id End-------------------------------- */
			
			
			/* ----------------- Assessor Registration Number Start-------------------------------- */
			/* Content Rules */
			
			if (file29Bean.getAssessorRegistrationNumber() != null) {

				if (file29Bean.getAssessorRegistrationNumber().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Assessor Registration Number starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
			}
			
			/* ----------------- Assessor Registration Number End-------------------------------- */
			
			/* ----------------- Learner Achievement Type Id Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getLearnerAchievementTypeId())) {
				extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Type Id field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getLearnerAchievementTypeId() != null) {

				if (file29Bean.getLearnerAchievementTypeId().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Date starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
			}
			
			/* ----------------- Learner Achievement Type Id End-------------------------------- */
			
			
			/* ----------------- Learner Achievement Date Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getLearnerAchievementDate())) {
				extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Date field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getLearnerAchievementDate() != null) {

				if (file29Bean.getLearnerAchievementDate().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Date starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
				
				if (file29Bean.getLearnerAchievementDate() == null && file29Bean.getLearnerAchievementStatusId().equals(2) ) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Date is required if Learner Achievement Status Id = 2 " , targetClass);
					errorList.add(extractError);
				}
				
				if (file29Bean.getLearnerAchievementDate() == null && file29Bean.getLearnerAchievementStatusId().equals(29)) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Date is required if Learner Achievement Status Id = 29 " , targetClass);
					errorList.add(extractError);
				}
				
				if (file29Bean.getLearnerAchievementDate() != null && file29Bean.getLearnerAchievementStatusId().equals(3)) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Date is not allowed if Learner Achievement Status Id = 3 " , targetClass);
					errorList.add(extractError);
				}
				
				SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
				Date minDate = format.parse("01/01/1900");

				if (file29Bean.getLearnerAchievementDate() != null && file29Bean.getLearnerAchievementDate().before(minDate)) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Date is before Minimum date " + minDate.toString() , targetClass);
					errorList.add(extractError);
				}
				
				if (file29Bean.getLearnerAchievementDate() != null && file29Bean.getLearnerAchievementDate().after(today)) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Achievement Date is cant be after Maximum date: " + today.toString() , targetClass);
					errorList.add(extractError);
				}
				
			}
			
			/* ----------------- Learner Achievement Date End-------------------------------- */
			
			
			/* ----------------- Learner Enrolled Date Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getLearnerEnrolledDate())) {
				extractError = new ExtractErrorList(fileId, fileName, "Learner Enrolled Type Id field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getLearnerEnrolledDate() != null) {

				if (file29Bean.getLearnerEnrolledDate().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Enrolled Date starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
				
				SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
				Date minDate = format.parse("01/01/1900");

				if (file29Bean.getLearnerEnrolledDate() != null && file29Bean.getLearnerEnrolledDate().before(minDate)) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Enrolled Date is before Minimum date " + minDate.toString() , targetClass);
					errorList.add(extractError);
				}
				
				if (file29Bean.getLearnerEnrolledDate() != null && file29Bean.getLearnerEnrolledDate().after(today)) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Enrolled Date is cant be after Maximum date: " + today.toString() , targetClass);
					errorList.add(extractError);
				}
				
				if (file29Bean.getLearnerEnrolledDate() != null && file29Bean.getLearnerAchievementDate().after(file29Bean.getLearnerEnrolledDate())) {
					extractError = new ExtractErrorList(fileId, fileName, "Learner Enrolled Date is cant be after " + file29Bean.getLearnerAchievementDate().toString() , targetClass);
					errorList.add(extractError);
				}
				
			}
			
			/* ----------------- Learner Enrolled Date End-------------------------------- */
			
			/* ----------------- Honours Classification Start-------------------------------- */
			/* Content Rules */
			
			if (file29Bean.getHonoursClassification() != null) {

				if (file29Bean.getHonoursClassification().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Honours Classification starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
			}
			
			/* ----------------- Honours Classification End-------------------------------- */
			
			/* ----------------- Assessor Registration Number Start-------------------------------- */
			/* Content Rules */
			
			if (file29Bean.getAssessorRegistrationNumber() != null) {

				if (file29Bean.getAssessorRegistrationNumber().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Assessor Registration Number starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
			}
			
			/* ----------------- Assessor Registration Number End-------------------------------- */
			
			
			/* ----------------- Part_of Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getPartof())) {
				extractError = new ExtractErrorList(fileId, fileName, "'Part of' field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getPartof() != null) {

				if (file29Bean.getPartof().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "'Part of' starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
				
				if (!file29Bean.getPartof().equals("1") || !file29Bean.getPartof().equals("3")) {
					extractError = new ExtractErrorList(fileId, fileName, "'Part of' may only have a value of 1 or 3 " , targetClass);
					errorList.add(extractError);
				}
				
				if (file29Bean.getLearnershipId() == null && file29Bean.getPartof().equals("3")) {
					extractError = new ExtractErrorList(fileId, fileName, "'Part of' has a value of 3 then a valid Learnership Id must be supplied. " , targetClass);
					errorList.add(extractError);
				}
				
			}
			
			/* ----------------- Part_of End-------------------------------- */
			
			/* ----------------- Learnership Id Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getLearnershipId())) {
				extractError = new ExtractErrorList(fileId, fileName, "Learnership Id field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getLearnershipId() != null) {

				if (file29Bean.getLearnershipId().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Learnership Id starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
				
			}
			/* ----------------- Learnership Id End-------------------------------- */
			
			/* ----------------- Provider Code Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getProviderCode())) {
				extractError = new ExtractErrorList(fileId, fileName, "Provider Code Id field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getProviderCode() != null) {

				if (file29Bean.getProviderCode().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Provider Code starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
				
			}
			/* ----------------- Provider Code End-------------------------------- */
			
			/* ----------------- Provider Etqa Id Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getProviderEtqaId())) {
				extractError = new ExtractErrorList(fileId, fileName, "Provider Etqa Id field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getProviderEtqaId() != null) {

				if (file29Bean.getProviderEtqaId().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Provider Etqa Id starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
				
			}
			/* ----------------- Provider Etqa Id End-------------------------------- */
			
			/* ----------------- Assessor Etqa Id Start-------------------------------- */
			/* Content Rules */
			
			if (nullorBlank(file29Bean.getAssessorEtqaId())) {
				extractError = new ExtractErrorList(fileId, fileName, "Assessor Etqa field Blank or null", targetClass);
				errorList.add(extractError);
			}
			
			if (file29Bean.getAssessorEtqaId() != null) {

				if (file29Bean.getAssessorEtqaId().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Assessor Etqa starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
				
			}
			/* ----------------- Assessor Etqa Id End-------------------------------- */
			
			/* ----------------- Certification Date Start-------------------------------- */
			/* Content Rules */
			
			if (file29Bean.getCertificationDate() != null) {

				if (file29Bean.getCertificationDate().toString().startsWith(" ")) {
					extractError = new ExtractErrorList(fileId, fileName, "Certification Date starts with 'blank space'" , targetClass);
					errorList.add(extractError);
				}
				
			}
			/* ----------------- Certification Date End-------------------------------- */
			
			
            Integer comboCheck = combinationFieldCheck(NLRDFile29.class.getName(), file29Bean.getNationalId(), file29Bean.getPersonAlternateId(), file29Bean.getAlternativeIdType(), file29Bean.getQualificationId());
			
			if (comboCheck > 1) {
				extractError = new ExtractErrorList(fileId, fileName, "The combination National Id, Person Alternate Id, Alternative Id Type and Qualification Id is not unique.", targetClass);
				errorList.add(extractError);
			}
			
			if (!file29Bean.getPartof().equals("1") || !file29Bean.getPartof().equals("3")) {
				extractError = new ExtractErrorList(fileId, fileName, "'Part of' may only have a value of 1 or 3.", targetClass);
				errorList.add(extractError);
			}
			
			
			if (errorList.size() > 0) {
				dao.createBatch(errorList);
			}
		}
	}
	
	
}
