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
import haj.com.dataextract.bean.SETMISFile400Bean;
import haj.com.dataextract.bean.SETMISFile401Bean;
import haj.com.dataextract.bean.SETMISFile502Bean;
import haj.com.dataextract.bean.SETMISFile502Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile501;
import haj.com.entity.SetmisFile502;
import haj.com.entity.SetmisFile502;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile502Service extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	public List<SETMISFile502Bean> extractSETMISFile502Bean() throws Exception {
		return dao.extractSETMISFile502Bean();
	}

	public int extractSetmisFile502Insert() throws Exception {
		return dao.extractSetmisFile502Insert();
	}

	public List<SetmisFile502> allSetmisFile502() throws Exception {
		return dao.allSetmisFile502();
	}

	@SuppressWarnings("unlikely-arg-type")
	public void extractSetmisFile502Validation(List<SetmisFile502> setmisFile502) throws Exception {
		SETMISFile400Service setmisFile400Service = new SETMISFile400Service();
		SETMISFile401Service setmisFile401Service = new SETMISFile401Service();
		SETMISFile100Service setmisFile100Service = new SETMISFile100Service();
		List<SETMISFile400Bean> setmisFile400 =  setmisFile400Service.extractSETMISFile400Bean();
		List<SETMISFile401Bean> setmisFile401 =  setmisFile401Service.extractSETMISFile401Bean();
		List<SETMISFile100Bean> setmisFile100 =  setmisFile100Service.extractSETMISFile100Bean();
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
		Pattern r3 = Pattern.compile(regexPerson);
		Matcher m;

		for (SetmisFile502 file502Bean : setmisFile502) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file502Bean.getNationalId() != null) {

				if (file502Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file502Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile502, file502Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file502Bean.getNationalId());
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile502, file502Bean.getPersonAlternateId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file502Bean.getPersonAlternateId());
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile502, file502Bean.getAlternativeIdType()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file502Bean.getAlternativeIdType());
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile502, file502Bean.getNonNQFInterventionCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Non NQF Intervention Code: " + file502Bean.getNonNQFInterventionCode());
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile502, file502Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Code: " + file502Bean.getProviderCode());
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile502, file502Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQE ID: " + file502Bean.getProviderETQEId());
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */
			
			/* ----------------- Person Alternate ID Start-------------------------------- */
			/* Content Rules */
			if (file502Bean.getPersonAlternateId() != null) {

				if (file502Bean.getPersonAlternateId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
				m = r3.matcher(file502Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Person Alternate ID End-------------------------------- */
			
			/* ----------------- Alternate ID Type Start-------------------------------- */
			/* Content Rules */
			if (file502Bean.getAlternativeIdType() == null || file502Bean.getAlternativeIdType().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternate ID is blank or null");
				extractError.setFileName("Setmis File 502");
				extractError.setTargetClass(SetmisFile502.class.getName());
				extractError.setFileId(file502Bean.getId());
				errorList.add(extractError);
			}
			
			if (file502Bean.getAlternativeIdType() != null) {

				if (file502Bean.getAlternativeIdType().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
				m = r3.matcher(file502Bean.getAlternativeIdType());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 502");
					extractError.setTargetClass(SetmisFile502.class.getName());
					extractError.setFileId(file502Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file502Bean.getAlternativeIdType().equals("537")) {
					if (!setmisFile400.contains(file502Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 502");
						extractError.setTargetClass(SetmisFile502.class.getName());
						extractError.setFileId(file502Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file502Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 502");
						extractError.setTargetClass(SetmisFile502.class.getName());
						extractError.setFileId(file502Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file502Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 502");
						extractError.setTargetClass(SetmisFile502.class.getName());
						extractError.setFileId(file502Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file502Bean.getProviderCode())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Code Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 502");
						extractError.setTargetClass(SetmisFile502.class.getName());
						extractError.setFileId(file502Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file502Bean.getProviderETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider ETQE ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 502");
						extractError.setTargetClass(SetmisFile502.class.getName());
						extractError.setFileId(file502Bean.getId());
						errorList.add(extractError);
					}
				}
				if (!file502Bean.getAlternativeIdType().equals("537")) {
					if (!setmisFile400.contains(file502Bean.getAlternativeIdType())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 502");
						extractError.setTargetClass(SetmisFile502.class.getName());
						extractError.setFileId(file502Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file502Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 502");
						extractError.setTargetClass(SetmisFile502.class.getName());
						extractError.setFileId(file502Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file502Bean.getPersonAlternateId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 502");
						extractError.setTargetClass(SetmisFile502.class.getName());
						extractError.setFileId(file502Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Alternate ID Type End-------------------------------- */
		}

		if (errorList.size() > 0) {
			dao.createBatch(errorList);

		}
	}
}
