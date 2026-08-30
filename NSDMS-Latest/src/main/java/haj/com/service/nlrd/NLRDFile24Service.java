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
import haj.com.dataextract.bean.NLRDFile24Bean;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.NLRDFile24;
import haj.com.entity.NLRDFile24;
import haj.com.entity.SetmisFile100;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class NLRDFile24Service extends AbstractService {
	/** The dao. */
	private NLRDDAO dao = new NLRDDAO();

	public List<NLRDFile24Bean> extractNLRDFile24Bean() throws Exception {
		return dao.extractNLRDFile24Bean();
	}

	public int extractNLRDFile24Insert() throws Exception {
		return dao.extractNLRDFile24Insert();
	}

	public List<NLRDFile24> allNLRDFile24() throws Exception {
		return dao.allNLRDFile24();
	}

	public void extractNLRDFile24Validation(List<NLRDFile24> nlrdFile24) throws Exception {
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

		for (NLRDFile24 file24Bean : nlrdFile24) {
			ExtractErrorList extractError = new ExtractErrorList();
			
			if (file24Bean.getProviderEtqaId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider ETQA ID field Blank or null");
				extractError.setFileName("NLRD File 24");
				extractError.setTargetClass(NLRDFile24.class.getName());
				extractError.setFileId(file24Bean.getId());
				errorList.add(extractError);
			}
			if (file24Bean.getProviderAccredStartDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Accred Start Date field Blank or null");
				extractError.setFileName("NLRD File 24");
				extractError.setTargetClass(NLRDFile24.class.getName());
				extractError.setFileId(file24Bean.getId());
				errorList.add(extractError);
			}
			if (file24Bean.getProviderAccredEndDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Accred End Date field Blank or null");
				extractError.setFileName("NLRD File 24");
				extractError.setTargetClass(NLRDFile24.class.getName());
				extractError.setFileId(file24Bean.getId());
				errorList.add(extractError);
			}
			if (file24Bean.getProviderAccredStatusCode() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Accred Status Code field Blank or null");
				extractError.setFileName("NLRD File 24");
				extractError.setTargetClass(NLRDFile24.class.getName());
				extractError.setFileId(file24Bean.getId());
				errorList.add(extractError);
			}

			/* ----------------- Learnership ID Start-------------------------------- */
			/* Content Rules */
			if (file24Bean.getLearnershipId() == null || file24Bean.getLearnershipId().toString().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learnership ID field Blank or null");
				extractError.setFileName("NLRD File 24");
				extractError.setTargetClass(NLRDFile24.class.getName());
				extractError.setFileId(file24Bean.getId());
				errorList.add(extractError);
			}
			if (file24Bean.getLearnershipId() != null) {

				if (file24Bean.getLearnershipId().toString().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learnership ID starts with 'blank space'");
					extractError.setFileName("NLRD File 24");
					extractError.setTargetClass(NLRDFile24.class.getName());
					extractError.setFileId(file24Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file24Bean.getLearnershipId() == null && file24Bean.getQualificationId() == null && file24Bean.getUnitStandardId() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learnership ID and Qualification ID and UnitStandard ID is Null");
					extractError.setFileName("NLRD File 24");
					extractError.setTargetClass(NLRDFile24.class.getName());
					extractError.setFileId(file24Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Learnership ID End-------------------------------- */
			
			/* ----------------- Provider Code Start-------------------------------- */
			/* Content Rules */
			if (file24Bean.getProviderCode() == null || file24Bean.getProviderCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code field Blank or null");
				extractError.setFileName("NRLD File 24");
				extractError.setTargetClass(NLRDFile24.class.getName());
				extractError.setFileId(file24Bean.getId());
				errorList.add(extractError);
			}
			if (file24Bean.getProviderCode() != null) {

				if (file24Bean.getProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code starts with 'blank space'");
					extractError.setFileName("NRLD File 24");
					extractError.setTargetClass(NLRDFile24.class.getName());
					extractError.setFileId(file24Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (Collections.frequency(nlrdFile24, file24Bean.getProviderEtqaId()) == 599) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non Primary Provider ETQA Id: " + file24Bean.getProviderEtqaId());
					extractError.setFileName("NRLD File 24");
					extractError.setTargetClass(NLRDFile24.class.getName());
					extractError.setFileId(file24Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Provider Code End-------------------------------- */

			/* ----------------- UnitStandard Code Start-------------------------------- */
			/* Content Rules */
			if (file24Bean.getUnitStandardId() != null) {

			/* Business Rules (Compliance) */
				if (Collections.frequency(nlrdFile24, file24Bean.getLearnershipId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learnership ID Must Be Unique : " + file24Bean.getLearnershipId());
					extractError.setFileName("NLRD File 24");
					extractError.setTargetClass(NLRDFile24.class.getName());
					extractError.setFileId(file24Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile24, file24Bean.getQualificationId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Qualification ID Must Be Unique : " + file24Bean.getQualificationId());
					extractError.setFileName("NLRD File 24");
					extractError.setTargetClass(NLRDFile24.class.getName());
					extractError.setFileId(file24Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile24, file24Bean.getUnitStandardId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Unitstandard ID Must Be Unique : " + file24Bean.getUnitStandardId());
					extractError.setFileName("NLRD File 24");
					extractError.setTargetClass(NLRDFile24.class.getName());
					extractError.setFileId(file24Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile24, file24Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code Must Be Unique : " + file24Bean.getProviderCode());
					extractError.setFileName("NLRD File 24");
					extractError.setTargetClass(NLRDFile24.class.getName());
					extractError.setFileId(file24Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile24, file24Bean.getProviderEtqaId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID Must Be Unique : " + file24Bean.getProviderEtqaId());
					extractError.setFileName("NLRD File 24");
					extractError.setTargetClass(NLRDFile24.class.getName());
					extractError.setFileId(file24Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- UnitStandard Code End-------------------------------- */
			
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
