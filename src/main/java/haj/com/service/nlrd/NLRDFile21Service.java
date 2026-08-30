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
import haj.com.dataextract.bean.NLRDFile21Bean;
import haj.com.dataextract.bean.SETMISFile100Bean;
import haj.com.entity.ExtractErrorList;
import haj.com.entity.NLRDFile21;
import haj.com.entity.NLRDFile21;
import haj.com.entity.SetmisFile100;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class NLRDFile21Service extends AbstractService {
	/** The dao. */
	private NLRDDAO dao = new NLRDDAO();

	public List<NLRDFile21Bean> extractNLRDFile21Bean() throws Exception {
		return dao.extractNLRDFile21Bean();
	}

	public int extractNLRDFile21Insert() throws Exception {
		return dao.extractNLRDFile21Insert();
	}

	public List<NLRDFile21> allNLRDFile21() throws Exception {
		return dao.allNLRDFile21();
	}

	public void extractNLRDFile21Validation(List<NLRDFile21> nlrdFile21) throws Exception {
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

		for (NLRDFile21 file21Bean : nlrdFile21) {
			ExtractErrorList extractError = new ExtractErrorList();

			if (file21Bean.getEtqaId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("ETQA ID field Blank or null");
				extractError.setFileName("NRLD File 21");
				extractError.setTargetClass(NLRDFile21.class.getName());
				extractError.setFileId(file21Bean.getId());
				errorList.add(extractError);
			}
			if (file21Bean.getProviderTypeId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Type ID field Blank or null");
				extractError.setFileName("NRLD File 21");
				extractError.setTargetClass(NLRDFile21.class.getName());
				extractError.setFileId(file21Bean.getId());
				errorList.add(extractError);
			}
			if (file21Bean.getProviderAddress1() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Address 1 field Blank or null");
				extractError.setFileName("NRLD File 21");
				extractError.setTargetClass(NLRDFile21.class.getName());
				extractError.setFileId(file21Bean.getId());
				errorList.add(extractError);
			}
			if (file21Bean.getProviderAddress2() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Address 2 field Blank or null");
				extractError.setFileName("NRLD File 21");
				extractError.setTargetClass(NLRDFile21.class.getName());
				extractError.setFileId(file21Bean.getId());
				errorList.add(extractError);
			}
			if (file21Bean.getProviderPostalCode() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Postal Code field Blank or null");
				extractError.setFileName("NRLD File 21");
				extractError.setTargetClass(NLRDFile21.class.getName());
				extractError.setFileId(file21Bean.getId());
				errorList.add(extractError);
			}
			if (file21Bean.getStructureStatusId() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Structure Status ID field Blank or null");
				extractError.setFileName("NRLD File 21");
				extractError.setTargetClass(NLRDFile21.class.getName());
				extractError.setFileId(file21Bean.getId());
				errorList.add(extractError);
			}
			if (file21Bean.getProvinceCode() == null ) {
				extractError = new ExtractErrorList();
				extractError.setNote("Province Code field Blank or null");
				extractError.setFileName("NRLD File 21");
				extractError.setTargetClass(NLRDFile21.class.getName());
				extractError.setFileId(file21Bean.getId());
				errorList.add(extractError);
			}
			/* ----------------- Provider Code Start-------------------------------- */
			/* Content Rules */
			if (file21Bean.getProviderCode() == null || file21Bean.getProviderCode().contentEquals("")) {
				extractError = new ExtractErrorList();
				extractError.setNote("Provider Code field Blank or null");
				extractError.setFileName("NRLD File 21");
				extractError.setTargetClass(NLRDFile21.class.getName());
				extractError.setFileId(file21Bean.getId());
				errorList.add(extractError);
			}
			if (file21Bean.getProviderCode() != null) {

				if (file21Bean.getProviderCode().startsWith(" ")) {
					extractError = new ExtractErrorList();
					extractError.setNote("Provider Code starts with 'blank space'");
					extractError.setFileName("NRLD File 21");
					extractError.setTargetClass(NLRDFile21.class.getName());
					extractError.setFileId(file21Bean.getId());
					errorList.add(extractError);
				}
				/* Business Rules (Compliance) */
				if (Collections.frequency(nlrdFile21, file21Bean.getEtqaId()) == 599) {
					extractError = new ExtractErrorList();
					extractError.setNote("Non Primary Provider ETQA Id: " + file21Bean.getEtqaId());
					extractError.setFileName("NRLD File 21");
					extractError.setTargetClass(NLRDFile21.class.getName());
					extractError.setFileId(file21Bean.getId());
					errorList.add(extractError);
				}
			}
			/* ----------------- Provider Code End-------------------------------- */
			
			/* ----------------- Latitude Degree Start-------------------------------- */
			/* Content Rules */
				if (file21Bean.getLatitudeDegree() != null) {

					if (file21Bean.getLatitudeDegree().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree starts with 'blank space'");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					m = r4.matcher(file21Bean.getLatitudeDegree());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree contains character not in " + pattern);
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if ((file21Bean.getLatitudeDegree() != null && file21Bean.getLongitudeDegree() == null) || (file21Bean.getLatitudeDegree() == null && file21Bean.getLongitudeDegree() != null)) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree Longitude Degree Blank or null " + pattern);
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					if (file21Bean.getLatitudeDegree() != null || file21Bean.getLatitudeMinutes() != null || file21Bean.getLatitudeSeconds() != null) {
						if (file21Bean.getLatitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Degree contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
						if (file21Bean.getLatitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Minutes contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
						if (file21Bean.getLatitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Seconds contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
					}
					if (file21Bean.getLongitudeDegree() != null || file21Bean.getLongitudeDegree() != null || file21Bean.getLatitudeSeconds() != null) {
						if (file21Bean.getLongitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Degree contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
						if (file21Bean.getLongitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Minutes contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
						if (file21Bean.getLongitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Seconds contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
					}
					if (Double.parseDouble(file21Bean.getLatitudeDegree()) > -22 && Double.parseDouble(file21Bean.getLatitudeDegree()) < -35) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree may not be Greater than -22 and Less than -35");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
					Date myDefaultDate = format.parse("12/31/2018");

					if (file21Bean.getDateStamp().after(myDefaultDate) && file21Bean.getLatitudeDegree() == null) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree Value must be provided if Date Stamp has a value greater than 2018-12-31");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Latitude Degree End-------------------------------- */
				
				/* ----------------- Latitude Minutes Start-------------------------------- */
				/* Content Rules */
				if (file21Bean.getLatitudeMinutes() != null) {

					if (file21Bean.getLatitudeMinutes().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Minutes starts with 'blank space'");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (Integer.parseInt(file21Bean.getLatitudeDegree()) < 00 && Integer.parseInt(file21Bean.getLatitudeDegree()) > 59) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Minutes may not be less than 00 and Greater than 59");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Latitude Minutes End-------------------------------- */
				
				/* ----------------- Latitude Seconds Start-------------------------------- */
				/* Content Rules */
				if (file21Bean.getLatitudeSeconds() != null) {

					if (file21Bean.getLatitudeSeconds().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds starts with 'blank space'");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					m = r5.matcher(file21Bean.getLatitudeSeconds());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds contains character not in " + pattern);
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (file21Bean.getLatitudeSeconds().length() != 6) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds must have an excactlt 6 characters");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					if (Double.parseDouble(file21Bean.getLatitudeSeconds()) < 00.000 && Double.parseDouble(file21Bean.getLatitudeSeconds()) > 59.999) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Seconds may not be less than 00.000 and Greater than 59.999");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Latitude Seconds End-------------------------------- */
				
				/* ----------------- Longitude Degree Start-------------------------------- */
				/* Content Rules */
				if (file21Bean.getLongitudeDegree() != null) {

					if (file21Bean.getLongitudeDegree().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Degree starts with 'blank space'");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
				
				/* Business Rules (Compliance) */
					if ((file21Bean.getLongitudeDegree() != null && file21Bean.getLatitudeDegree() == null) || (file21Bean.getLongitudeDegree() == null && file21Bean.getLatitudeDegree() != null)) {
						extractError = new ExtractErrorList();
						extractError.setNote("Latitude Degree Longitude Degree Blank or null " + pattern);
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					if (file21Bean.getLatitudeDegree() != null || file21Bean.getLatitudeMinutes() != null || file21Bean.getLatitudeSeconds() != null) {
						if (file21Bean.getLatitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Degree contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
						if (file21Bean.getLatitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Minutes contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
						if (file21Bean.getLatitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Latitude Seconds contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
					}
					if (file21Bean.getLongitudeDegree() != null || file21Bean.getLongitudeDegree() != null || file21Bean.getLatitudeSeconds() != null) {
						if (file21Bean.getLongitudeDegree() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Degree contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
						if (file21Bean.getLongitudeMinutes() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Minutes contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
						if (file21Bean.getLongitudeSeconds() == null) {
							extractError = new ExtractErrorList();
							extractError.setNote("Longitude Seconds contains character not in " + pattern);
							extractError.setFileName("NRLD File 21");
							extractError.setTargetClass(NLRDFile21.class.getName());
							extractError.setFileId(file21Bean.getId());
							errorList.add(extractError);
						}
					}
					if (Double.parseDouble(file21Bean.getLongitudeDegree()) > 33 && Double.parseDouble(file21Bean.getLongitudeDegree()) < 16) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Degree may not be Greater than -22 and Less than -35");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
					Date myDefaultDate = format.parse("12/31/2018");

					if (file21Bean.getDateStamp().after(myDefaultDate) && file21Bean.getLongitudeDegree() == null) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Degree Value must be provided if Date Stamp has a value greater than 2018-12-31");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Longitude Degree End-------------------------------- */
				
				/* ----------------- Longitude Minutes Start-------------------------------- */
				/* Content Rules */
				if (file21Bean.getLongitudeMinutes() != null) {

					if (file21Bean.getLongitudeMinutes().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Minutes starts with 'blank space'");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
				
				/* Business Rules (Compliance) */
					if (Integer.parseInt(file21Bean.getLongitudeMinutes()) < 00 && Integer.parseInt(file21Bean.getLongitudeMinutes()) > 59) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Minutes may not be less than 00 and Greater than 59");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Longitude Minutes End-------------------------------- */
				
				/* ----------------- Longitude Seconds Start-------------------------------- */
				/* Content Rules */
				if (file21Bean.getLongitudeSeconds() != null) {

					if (file21Bean.getLongitudeSeconds().startsWith(" ")) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds starts with 'blank space'");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					m = r5.matcher(file21Bean.getLongitudeSeconds());
					if (m.find()) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds contains character not in " + pattern);
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					/* Business Rules (Compliance) */
					if (file21Bean.getLongitudeSeconds().length() != 6) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds must have an excactlt 6 characters");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
					if (Double.parseDouble(file21Bean.getLongitudeSeconds()) < 00.000 && Double.parseDouble(file21Bean.getLongitudeSeconds()) > 59.999) {
						extractError = new ExtractErrorList();
						extractError.setNote("Longitude Seconds may not be less than 00.000 and Greater than 59.999");
						extractError.setFileName("NRLD File 21");
						extractError.setTargetClass(NLRDFile21.class.getName());
						extractError.setFileId(file21Bean.getId());
						errorList.add(extractError);
					}
				}
				/* ----------------- Longitude Seconds End-------------------------------- */

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
