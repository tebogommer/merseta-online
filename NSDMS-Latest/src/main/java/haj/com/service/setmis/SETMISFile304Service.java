package haj.com.service.setmis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import haj.com.dao.SetmisDAO;
import haj.com.dataextract.bean.SETMISFile304Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile100;
import haj.com.entity.SetmisFile304;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile304Service extends AbstractService {

	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	public List<SETMISFile304Bean> extractSETMISFile304Bean() throws Exception {
		return dao.extractSETMISFile304Bean();
	}

	public int extractSetmisFile304Insert() throws Exception {
		return dao.extractSetmisFile304Insert();
	}

	public List<SetmisFile304> allSetmisFile304() throws Exception {
		return dao.allSetmisFile304();
	}

	boolean numberOrNot(String input) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	public void extractSetmisFile304Validation(List<SetmisFile304> setmisFile304) throws Exception {
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

		for (SetmisFile304 file304Bean : setmisFile304) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- Non NQF Interv Code Start-------------------------------- */
			/* Content Rules */
			if (file304Bean.getNonNQFIntervCode() == null || file304Bean.getNonNQFIntervCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Non NQF Interv Code field Blank or null");
				extractError.setFileName("Setmis File 304");
				extractError.setTargetClass(SetmisFile100.class.getName());
				extractError.setFileId(file304Bean.getId());
				errorList.add(extractError);
			}
			if (file304Bean.getNonNQFIntervCode() != null) {

				if (file304Bean.getNonNQFIntervCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Code starts with 'blank space'");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file304Bean.getNonNQFIntervCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile304, file304Bean.getNonNQFIntervCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Non NQF Interv Code: " + file304Bean.getNonNQFIntervCode());
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile304, file304Bean.getNonNQFIntervETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Non NQF Interv ETQA Id: " + file304Bean.getNonNQFIntervETQEId());
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Non NQF Interv Code End-------------------------------- */

			/* ----------------- Non NQF Interv Name Start-------------------------------- */
			/* Content Rules */
			if (file304Bean.getNonNQFIntervETQEId() == null || file304Bean.getNonNQFIntervETQEId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Non NQF Interv Name field Blank or null");
				extractError.setFileName("Setmis File 304");
				extractError.setTargetClass(SetmisFile304.class.getName());
				extractError.setFileId(file304Bean.getId());
				errorList.add(extractError);
			}
			if (file304Bean.getNonNQFIntervETQEId() != null) {

				if (file304Bean.getNonNQFIntervETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Name starts with 'blank space'");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file304Bean.getNonNQFIntervETQEId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Name contains character not in " + pattern);
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Non NQF Interv Name End-------------------------------- */
			
			/* ----------------- Subfield ID Start-------------------------------- */
			/* Content Rules */
			if (file304Bean.getSubfieldId() == null || file304Bean.getSubfieldId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Subfield ID field Blank or null");
				extractError.setFileName("Setmis File 304");
				extractError.setTargetClass(SetmisFile304.class.getName());
				extractError.setFileId(file304Bean.getId());
				errorList.add(extractError);
			}
			if (file304Bean.getSubfieldId() != null) {

				if (file304Bean.getSubfieldId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Subfield ID starts with 'blank space'");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
			}			
			/* ----------------- Subfield ID End-------------------------------- */
			
			/* ----------------- Non NQF Interv Reg Start Date Start-------------------------------- */
			/* Content Rules */
			if (file304Bean.getNonNQFIntervRegStartDate() != null) {

				/* Business Rules (Compliance) */
				if (file304Bean.getNonNQFIntervRegStartDate().after(file304Bean.getNonNQFIntervRegEndDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Reg Start Date Is Greater Than Non NQF Interv Reg End Date ");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
				if (file304Bean.getNonNQFIntervRegStartDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Reg Start Date is greater than today's date");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Non NQF Interv Reg Start Date End-------------------------------- */
			
			/* ----------------- Non NQF Interv Reg End Date Start-------------------------------- */
			/* Content Rules */
			if (file304Bean.getNonNQFIntervRegEndDate() != null) {
				
				/* Business Rules (Compliance) */
				if (file304Bean.getNonNQFIntervRegEndDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Reg End Date is greater than today's date");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Non NQF Interv Reg End Date End-------------------------------- */

			/* ----------------- Non NQF Interv ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file304Bean.getNonNQFIntervETQEId() == null || file304Bean.getNonNQFIntervETQEId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Non NQF Interv ETQE ID field Blank or null");
				extractError.setFileName("Setmis File 304");
				extractError.setTargetClass(SetmisFile304.class.getName());
				extractError.setFileId(file304Bean.getId());
				errorList.add(extractError);
			}
			if (file304Bean.getNonNQFIntervETQEId() != null) {

				if (file304Bean.getNonNQFIntervETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
			}			
			/* ----------------- Non NQF Interv ETQE ID End-------------------------------- */
			
			/* ----------------- Non NQF Interv Status ID Start-------------------------------- */
			/* Content Rules */
			if (file304Bean.getNonNQFIntervStatusId() == null || file304Bean.getNonNQFIntervStatusId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Non NQF Interv Status ID field Blank or null");
				extractError.setFileName("Setmis File 304");
				extractError.setTargetClass(SetmisFile304.class.getName());
				extractError.setFileId(file304Bean.getId());
				errorList.add(extractError);
			}
			if (file304Bean.getNonNQFIntervStatusId() != null) {
				
				if (file304Bean.getNonNQFIntervStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Status ID starts with 'blank space'");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (!file304Bean.getNonNQFIntervStatusId().equals("2") && file304Bean.getNonNQFIntervRegEndDate() != null ) {
					
					extractError = new ExtractErrorList();
					extractError.setNote("If Non NQF Interv Status Id has a value of 2 then Non NQF Interv Reg End Date may not be blank");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
				if (file304Bean.getNonNQFIntervStatusId().equals("2") && file304Bean.getNonNQFIntervRegEndDate() == null ) {
					extractError = new ExtractErrorList();
					extractError.setNote("If Non NQF Interv Status Id does not have a value of 2 then Non NQF Interv Reg End Date must be blank");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
			}			
			/* ----------------- Non NQF Interv Status ID End-------------------------------- */
			
			/* ----------------- Non NQF Interv Credit Start-------------------------------- */
			/* Content Rules */
			if (file304Bean.getNonNQFIntervCredit() == null || file304Bean.getNonNQFIntervCredit().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Non NQF Interv Credit field Blank or null");
				extractError.setFileName("Setmis File 304");
				extractError.setTargetClass(SetmisFile304.class.getName());
				extractError.setFileId(file304Bean.getId());
				errorList.add(extractError);
			}
			if (file304Bean.getNonNQFIntervCredit() != null) {
				
				if (file304Bean.getNonNQFIntervCredit().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Credit starts with 'blank space'");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (Double.parseDouble(file304Bean.getNonNQFIntervCredit()) < 1 && Double.parseDouble(file304Bean.getNonNQFIntervCredit()) > 480) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non NQF Interv Credit may not be Less than 1 and Greater than 480");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Non NQF Interv Credit End-------------------------------- */
			
			/* ----------------- Learning Programme Type ID Start-------------------------------- */
			/* Content Rules */
			if (file304Bean.getLearningProgrammeTypeId() == null || file304Bean.getLearningProgrammeTypeId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Learning Programme Type ID field Blank or null");
				extractError.setFileName("Setmis File 304");
				extractError.setTargetClass(SetmisFile304.class.getName());
				extractError.setFileId(file304Bean.getId());
				errorList.add(extractError);
			}
			if (file304Bean.getLearningProgrammeTypeId() != null) {
				
				if (file304Bean.getLearningProgrammeTypeId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Learning Programme Type ID starts with 'blank space'");
					extractError.setFileName("Setmis File 304");
					extractError.setTargetClass(SetmisFile304.class.getName());
					extractError.setFileId(file304Bean.getId());
					errorList.add(extractError);
				}
			}			
			/* ----------------- Learning Programme Type ID End-------------------------------- */
		}
		if (errorList.size() > 0) {
			dao.createBatch(errorList);
		}
	}
}