package haj.com.service.nlrd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.NLRDDAO;
import haj.com.dataextract.bean.NLRDFile26Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.NLRDFile26;
import haj.com.entity.NLRDFile26;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class NLRDFile26Service extends AbstractService {
	/** The dao. */
	private NLRDDAO dao = new NLRDDAO();

	public List<NLRDFile26Bean> extractNLRDFile26Bean() throws Exception {
		return dao.extractNLRDFile26Bean();
	}

	public int extractNLRDFile26Insert() throws Exception {
		return dao.extractNLRDFile26Insert();
	}

	public List<NLRDFile26> allNLRDFile26() throws Exception {
		return dao.allNLRDFile26();
	}

	public void extractNLRDFile26Validation(List<NLRDFile26> nlrdFile26) throws Exception {
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

		for (NLRDFile26 file26Bean : nlrdFile26) {
			ExtractErrorList extractError = new ExtractErrorList();
			
			if (file26Bean.getAlternativeIdType() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternative ID Type is blank or null");
				extractError.setFileName("NLRD File 26");
				extractError.setTargetClass(NLRDFile26.class.getName());
				extractError.setFileId(file26Bean.getId());
				errorList.add(extractError);
			}
			if (file26Bean.getDesignationId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Designation ID Type is blank or null");
				extractError.setFileName("NLRD File 26");
				extractError.setTargetClass(NLRDFile26.class.getName());
				extractError.setFileId(file26Bean.getId());
				errorList.add(extractError);
			}
			if (file26Bean.getDesignationETQAId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Designation ETQA ID Type is blank or null");
				extractError.setFileName("NLRD File 26");
				extractError.setTargetClass(NLRDFile26.class.getName());
				extractError.setFileId(file26Bean.getId());
				errorList.add(extractError);
			}
			if (file26Bean.getStructureStatusId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Structure Status ID is blank or null");
				extractError.setFileName("NLRD File 26");
				extractError.setTargetClass(NLRDFile26.class.getName());
				extractError.setFileId(file26Bean.getId());
				errorList.add(extractError);
			}
			

			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file26Bean.getNationalId() == null || file26Bean.getNationalId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("National ID is blank or null");
				extractError.setFileName("NLRD File 26");
				extractError.setTargetClass(NLRDFile26.class.getName());
				extractError.setFileId(file26Bean.getId());
				errorList.add(extractError);
			}
			if (file26Bean.getNationalId() != null) {

				if (file26Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("NLRD File 26");
					extractError.setTargetClass(NLRDFile26.class.getName());
					extractError.setFileId(file26Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file26Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("NLRD File 26");
					extractError.setTargetClass(NLRDFile26.class.getName());
					extractError.setFileId(file26Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(nlrdFile26, file26Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file26Bean.getNationalId());
					extractError.setFileName("NLRD File 26");
					extractError.setTargetClass(NLRDFile26.class.getName());
					extractError.setFileId(file26Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile26, file26Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file26Bean.getPersonAlternateId());
					extractError.setFileName("NLRD File 26");
					extractError.setTargetClass(NLRDFile26.class.getName());
					extractError.setFileId(file26Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile26, file26Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file26Bean.getAlternativeIdType());
					extractError.setFileName("NLRD File 26");
					extractError.setTargetClass(NLRDFile26.class.getName());
					extractError.setFileId(file26Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile26, file26Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Code: " + file26Bean.getProviderCode());
					extractError.setFileName("NLRD File 26");
					extractError.setTargetClass(NLRDFile26.class.getName());
					extractError.setFileId(file26Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile26, file26Bean.getProviderETQAID()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQA ID: " + file26Bean.getProviderETQAID());
					extractError.setFileName("NLRD File 26");
					extractError.setTargetClass(NLRDFile26.class.getName());
					extractError.setFileId(file26Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */
			
			/* ----------------- Designation ID Start-------------------------------- */
			/* Content Rules */
			if (file26Bean.getDesignationId() == null || file26Bean.getDesignationId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Designation ID is blank or null");
				extractError.setFileName("NLRD File 26");
				extractError.setTargetClass(NLRDFile26.class.getName());
				extractError.setFileId(file26Bean.getId());
				errorList.add(extractError);
			}
			if (file26Bean.getDesignationId() != null) {
				
				/* Business Rules (Compliance) */
				if (Collections.frequency(nlrdFile26, file26Bean.getDesignationId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Designation ID: " + file26Bean.getDesignationId());
					extractError.setFileName("NLRD File 26");
					extractError.setTargetClass(NLRDFile26.class.getName());
					extractError.setFileId(file26Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile26, file26Bean.getDesignationRegistrationNumber()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Designation Registration Number: " + file26Bean.getDesignationRegistrationNumber());
					extractError.setFileName("NLRD File 26");
					extractError.setTargetClass(NLRDFile26.class.getName());
					extractError.setFileId(file26Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Designation ID End-------------------------------- */
			
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
