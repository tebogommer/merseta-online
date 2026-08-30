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
import haj.com.dataextract.bean.NLRDFile25Bean;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.NLRDFile25;
import haj.com.entity.NLRDFile25;
import haj.com.entity.SetmisFile100;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class NLRDFile25Service extends AbstractService {
	/** The dao. */
	private NLRDDAO dao = new NLRDDAO();

	public List<NLRDFile25Bean> extractNLRDFile25Bean() throws Exception {
		return dao.extractNLRDFile25Bean();
	}

	public int extractNLRDFile25Insert() throws Exception {
		return dao.extractNLRDFile25Insert();
	}

	public List<NLRDFile25> allNLRDFile25() throws Exception {
		return dao.allNLRDFile25();
	}

	public void extractNLRDFile25Validation(List<NLRDFile25> nlrdFile25) throws Exception {
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

		for (NLRDFile25 file25Bean : nlrdFile25) {
			ExtractErrorList extractError = new ExtractErrorList();
			
			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file25Bean.getNationalId() == null || file25Bean.getNationalId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("National ID is blank or null");
				extractError.setFileName("NLRD File 25");
				extractError.setTargetClass(NLRDFile25.class.getName());
				extractError.setFileId(file25Bean.getId());
				errorList.add(extractError);
			}
			if (file25Bean.getNationalId() != null) {

				if (file25Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("NLRD File 25");
					extractError.setTargetClass(NLRDFile25.class.getName());
					extractError.setFileId(file25Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file25Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("NLRD File 25");
					extractError.setTargetClass(NLRDFile25.class.getName());
					extractError.setFileId(file25Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(nlrdFile25, file25Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file25Bean.getNationalId());
					extractError.setFileName("NLRD File 25");
					extractError.setTargetClass(NLRDFile25.class.getName());
					extractError.setFileId(file25Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile25, file25Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file25Bean.getPersonAlternateId());
					extractError.setFileName("NLRD File 25");
					extractError.setTargetClass(NLRDFile25.class.getName());
					extractError.setFileId(file25Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile25, file25Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file25Bean.getAlternativeIdType());
					extractError.setFileName("NLRD File 25");
					extractError.setTargetClass(NLRDFile25.class.getName());
					extractError.setFileId(file25Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile25, file25Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Code: " + file25Bean.getProviderCode());
					extractError.setFileName("NLRD File 25");
					extractError.setTargetClass(NLRDFile25.class.getName());
					extractError.setFileId(file25Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(nlrdFile25, file25Bean.getProviderEtqaId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQA ID: " + file25Bean.getProviderEtqaId());
					extractError.setFileName("NLRD File 25");
					extractError.setTargetClass(NLRDFile25.class.getName());
					extractError.setFileId(file25Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */
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
