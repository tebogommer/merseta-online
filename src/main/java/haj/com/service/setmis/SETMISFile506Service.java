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
import haj.com.dataextract.bean.SETMISFile501Bean;
import haj.com.dataextract.bean.SETMISFile506Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.SetmisFile505;
import haj.com.entity.SetmisFile506;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class SETMISFile506Service extends AbstractService {
	/** The dao. */
	private SetmisDAO dao = new SetmisDAO();

	public List<SETMISFile506Bean> extractSETMISFile506Bean() throws Exception {
		return dao.extractSETMISFile506Bean();
	}

	public int extractSetmisFile506Insert() throws Exception {
		return dao.extractSetmisFile506Insert();
	}

	public List<SetmisFile506> allSetmisFile506() throws Exception {
		return dao.allSetmisFile506();
	}

	@SuppressWarnings("unlikely-arg-type")
	public void extractSetmisFile506Validation(List<SetmisFile506> setmisFile506) throws Exception {
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

		for (SetmisFile506 file506Bean : setmisFile506) {
			ExtractErrorList extractError = new ExtractErrorList();

			/* ----------------- National ID Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getNationalId() != null) {

				if (file506Bean.getNationalId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				m = r2.matcher(file506Bean.getNationalId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("National ID contains character not in " + regexNumbers);
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			/* Business Rules (Compliance) */
				if (Collections.frequency(setmisFile506, file506Bean.getNationalId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate National ID: " + file506Bean.getNationalId());
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile506, file506Bean.getPersonAlternativeId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Person Alternate ID: " + file506Bean.getPersonAlternativeId());
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile506, file506Bean.getAternativeIdTypeId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Alternate ID Type: " + file506Bean.getAternativeIdTypeId());
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile506, file506Bean.getQualificationId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Qualification ID: " + file506Bean.getQualificationId());
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile506, file506Bean.getsDLNo()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate SDL No: " + file506Bean.getsDLNo());
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile506, file506Bean.getProviderCode()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider Code: " + file506Bean.getProviderCode());
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				if (Collections.frequency(setmisFile506, file506Bean.getProviderETQEId()) > 1) {
					extractError = new ExtractErrorList();
					extractError.setNote("Duplicate Provider ETQE ID: " + file506Bean.getProviderETQEId());
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- National ID End-------------------------------- */
			
			/* ----------------- Person Alternate ID Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getPersonAlternativeId() != null) {

				if (file506Bean.getPersonAlternativeId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				m = r3.matcher(file506Bean.getPersonAlternativeId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Person Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Person Alternate ID End-------------------------------- */
			
			/* ----------------- Alternate ID Type Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getAternativeIdTypeId() == null || file506Bean.getAternativeIdTypeId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Alternate ID is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}
			
			if (file506Bean.getAternativeIdTypeId() != null) {

				if (file506Bean.getAternativeIdTypeId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				m = r3.matcher(file506Bean.getAternativeIdTypeId());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Alternate ID contains character not in " + regexPerson);
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file506Bean.getAternativeIdTypeId().equals("537")) {
					if (!setmisFile400.contains(file506Bean.getAternativeIdTypeId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 506");
						extractError.setTargetClass(SetmisFile506.class.getName());
						extractError.setFileId(file506Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file506Bean.getPersonAlternativeId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 506");
						extractError.setTargetClass(SetmisFile506.class.getName());
						extractError.setFileId(file506Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file506Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 506");
						extractError.setTargetClass(SetmisFile506.class.getName());
						extractError.setFileId(file506Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file506Bean.getProviderCode())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider Code Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 506");
						extractError.setTargetClass(SetmisFile506.class.getName());
						extractError.setFileId(file506Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file506Bean.getProviderETQEId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Provider ETQE ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 506");
						extractError.setTargetClass(SetmisFile506.class.getName());
						extractError.setFileId(file506Bean.getId());
						errorList.add(extractError);
					}
				}
				if (!file506Bean.getAternativeIdTypeId().equals("537")) {
					if (!setmisFile400.contains(file506Bean.getAternativeIdTypeId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Alternative ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 506");
						extractError.setTargetClass(SetmisFile506.class.getName());
						extractError.setFileId(file506Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file506Bean.getNationalId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("National ID Type Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 506");
						extractError.setTargetClass(SetmisFile506.class.getName());
						extractError.setFileId(file506Bean.getId());
						errorList.add(extractError);
					}
					if (!setmisFile400.contains(file506Bean.getPersonAlternativeId())) {
						extractError = new ExtractErrorList();
						extractError.setNote("Person Alternative ID Must Be In Parent File 400 ");
						extractError.setFileName("Setmis File 506");
						extractError.setTargetClass(SetmisFile506.class.getName());
						extractError.setFileId(file506Bean.getId());
						errorList.add(extractError);
					}
				}
			}
			/* ----------------- Alternate ID Type End-------------------------------- */
			
			/* ----------------- Qualification ID Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getQualificationId() == null || file506Bean.getQualificationId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Qualification ID is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}
			
			if (file506Bean.getQualificationId() != null) {

				if (file506Bean.getQualificationId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Qualification ID starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Qualification ID End-------------------------------- */
			
			/* ----------------- Qualification Achievement Date Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getQualificationAchievementDate() != null) {

			/* Business Rules (Compliance) */
				if (file506Bean.getQualificationAchievementDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Enrolment Status Date is greater than today's date");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Qualification Achievement Date End-------------------------------- */
			
			/* ----------------- Internship Status Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getInternshipStatusId() == null || file506Bean.getInternshipStatusId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Internship Status is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}
			
			if (file506Bean.getInternshipStatusId() != null) {

				if (file506Bean.getInternshipStatusId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Internship Status starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (!file506Bean.getInternshipStatusId().equals("1") && file506Bean.getEndDate() == null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Internship Status ID does not have a value of 1 then End Date may not be blank");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				if (file506Bean.getInternshipStatusId().equals("1") && file506Bean.getEndDate() != null) {
					extractError = new ExtractErrorList();
					extractError.setNote("Internship Status ID has a value of 1 then End Date must be blank");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Internship Status End-------------------------------- */
			
			/* ----------------- Start Date Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getStartDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Start Date is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}

			if (file506Bean.getStartDate() != null) {

				/* Business Rules (Compliance) */
				if (file506Bean.getStartDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Start Date Is Greater Than Today's Date ");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Start Date End-------------------------------- */
			
			/* ----------------- End Date Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getStartDate() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("End Date is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}

			if (file506Bean.getStartDate() != null) {

				/* Business Rules (Compliance) */
				if (file506Bean.getEndDate().after(file506Bean.getStartDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("End Date Is Greater Than Start Date ");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				if (file506Bean.getStartDate().after(getSynchronizedDate())) {
					extractError = new ExtractErrorList();
					extractError.setNote("End Date Is Greater Than Today's Date ");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- End Date End-------------------------------- */
			
			/* ----------------- Site No Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getSiteNo() == null || file506Bean.getSiteNo().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Site No is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}
			
			if (file506Bean.getSiteNo() != null) {

				if (file506Bean.getSiteNo().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site No starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file506Bean.getSiteNo());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Site No contains character not in " + pattern);
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Site No End-------------------------------- */
			
			/* ----------------- Provider Code Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getProviderCode() == null || file506Bean.getProviderCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}
			if (file506Bean.getProviderCode() != null) {

				if (file506Bean.getProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				m = r.matcher(file506Bean.getProviderCode());
				if (m.find()) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code contains character not in " + pattern);
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (setmisFile100.contains(file506Bean.getProviderCode()) && !setmisFile100.contains(file506Bean.getProviderETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID Must Be In Parent File 100  ");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				if (!setmisFile100.contains(file506Bean.getProviderCode()) && setmisFile100.contains(file506Bean.getProviderETQEId())) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code Must Be In Parent File 100 ");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Provider Code End-------------------------------- */
			
			/* ----------------- Provider ETQE ID Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getProviderETQEId() == null || file506Bean.getProviderETQEId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider ETQE ID is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}
			if (file506Bean.getProviderETQEId() != null) {

				if (file506Bean.getProviderETQEId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider ETQE ID starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Provider ETQE ID End-------------------------------- */
			
			/* ----------------- Funding ID Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getFundingId() == null || file506Bean.getFundingId().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Funding ID is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}
			if (file506Bean.getFundingId() != null) {
				
				if (file506Bean.getFundingId().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Funding ID starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}	
			/* ----------------- Funding ID End-------------------------------- */
			
			/* ----------------- Cumulative Spend Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getCumulativeSpend() != null) {

				if (file506Bean.getCumulativeSpend().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Cumulative Spend starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (file506Bean.getCumulativeSpend() != null && !file506Bean.getFundingId().contains("1")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Cumulative Spend may only have a value if Funding ID has a value of 1 ");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Cumulative Spend End-------------------------------- */
			
			/* ----------------- OFO Code Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getoFOCode() != null) {

				if (file506Bean.getoFOCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("OFO Code starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- OFO Code End-------------------------------- */
			
			/* ----------------- Urban Rural ID Start-------------------------------- */
			/* Content Rules */
			if (file506Bean.getUrbanRuralID() == null || file506Bean.getUrbanRuralID().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Urban Rural ID is blank or null");
				extractError.setFileName("Setmis File 506");
				extractError.setTargetClass(SetmisFile506.class.getName());
				extractError.setFileId(file506Bean.getId());
				errorList.add(extractError);
			}

			if (file506Bean.getUrbanRuralID() != null) {

				if (file506Bean.getUrbanRuralID().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Urban Rural ID starts with 'blank space'");
					extractError.setFileName("Setmis File 506");
					extractError.setTargetClass(SetmisFile506.class.getName());
					extractError.setFileId(file506Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Urban Rural ID End-------------------------------- */
		}

		if (errorList.size() > 0) {
			dao.createBatch(errorList);

		}
	}
}
