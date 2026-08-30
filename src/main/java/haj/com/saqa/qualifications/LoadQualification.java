package haj.com.saqa.qualifications;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.primefaces.model.UploadedFile;

import haj.com.constants.HAJConstants;
import haj.com.dao.NqfLevelsDAO;
import haj.com.dao.lookup.SaqaQualificationDAO;
import haj.com.datatakeon.GenericDAO;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SaqaQualification;
import haj.com.entity.lookup.SaqaUnitstandard;
import haj.com.entity.lookup.SaqaUsQualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.entity.lookup.temp.QualificationTemp;
import haj.com.entity.lookup.temp.SaqaQualificationTemp;
import haj.com.entity.lookup.temp.SaqaUsQualificationTemp;
import haj.com.entity.lookup.temp.UnitStandardsTemp;
import haj.com.saqa.unitstandards.UNITSTANDARD_TEMP;
import haj.com.service.JAXBService;
import haj.com.service.NqfLevelsService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadQualification.
 */
public class LoadQualification {

	public static String prefix = findPrefixFolder() + "/";

	private static String findPrefixFolder() {
		String folder = findProperty("UNZIP_FOLDER");
		if (folder == null) folder = "/Users/wesleypirie/Desktop/Files/QUALIFICATIONS";
		return folder;
	}

	private static String findProperty(String prop) {
		if (System.getProperties().get("DD-PROPERTIES") != null && ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(prop) != null) {
			return ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(prop);
		}
		return null;
	}
	
	private static Date startTimestamp = new Date();
	private static Date endTimestamp = new Date();
	private static long duration = 0l;
	

	/**
	 * Load NQF.
	 *
	 * @param file
	 *            the file
	 */
	public static void loadNQF(String file) {
		try {
			System.out.println("Loading file: " + file);
			GenericDAO dao = new GenericDAO();
			try {
				dao.nonSelectSql("delete from saqa_qualification where id in (16260,16077)");
			} catch (Exception e) {
				// TODO: handle exception
			}

			String xml = new String(Files.readAllBytes(new File(prefix + file).toPath()), Charset.forName("UTF-8"));
			xml = xml.replaceAll("[\u0000-\u001f]", "");


			Map<Integer, SaqaQualification> m = new HashMap<Integer, SaqaQualification>();
			List<SaqaQualification> existingQual = new SaqaQualificationDAO().allSaqaQualification();

			for (SaqaQualification n : existingQual) {
				m.put(n.getQualificationid(), n);
			}

			haj.com.saqa.qualifications.SAQAQUALIFICATIONS q = (haj.com.saqa.qualifications.SAQAQUALIFICATIONS) JAXBService.unmarshalltoObject(xml, new haj.com.saqa.qualifications.SAQAQUALIFICATIONS());
			List<QUALIFICATION> l = q.getQUALIFICATION();
			for (QUALIFICATION qual : l) {
				if (m.containsKey(qual.getQualificationid())) {
					qual.setId(m.get(qual.getQualificationid()).getId());
					qual.setLastdateforenrolment2(JAXBService.convertXMLtoDate(qual.getLastdateforenrolment()));
					dao.update(qual);
//					List<USQUALIFICATIONLINK> l2 = qual.getUSQUALIFICATIONLINK();
//					for (USQUALIFICATIONLINK usqualificationlink : l2) {
//						usqualificationlink.setQualification(m.get(qual.getQualificationid()));
//						dao.create(usqualificationlink);
//					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// ******************************************************************************************************************************************************************************** //
	
	private static final boolean PRINT_OUT_SYSOUTS = true;
	
	/**
	 * Sean Nell 
	 * 
	 * This method will import Qualification and Unit Standards Quarterly into Merseta Lookup tables 
	 * 
	 * Temp tables are populated before updating existing qualifications and unit standards and their linkages
	 * 
	 * Temp tables are populated before inserting new qualifications and unit standards and their linkages
	 * 
	 * 2019/02/26 and 2019/02/26
	 * 
	 * @throws Exception
	 * 
	 **/
	public static void loadAndProcessUnitStandards(InputStream fileUnitStandards) throws Exception {
		try {	
			
			// send notification on start of process
			for (String emails : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emails, "UNIT STANDARD IMPORT STARTED", "Unit Standard Processes Started on: " + HAJConstants.PL_LINK, null);
			}
			
			if(fileUnitStandards == null){	
				throw new Exception("No Unit Standards XML file was provided to process ");
			}
			
			startTimestamp = new Date(System.currentTimeMillis());
			
			printoutMessage("*** ");
			printoutMessage("*** PROCESSING XML FILE TO IMPORT / UPDATE EXISTING LOOKUP DATA LINKED UNIT STANDARDS ");
			
			GenericDAO dao = new GenericDAO();
			
			if(fileUnitStandards != null) {
				
				printoutMessage("*** ");
				printoutMessage("*** Deleting all from saqa_unitstandard_temp");
				
				dao.nonSelectSql("delete from saqa_unitstandard_temp");
				
				printoutMessage("*** ");
				printoutMessage("*** All temp unit standards deleted successfully ");
				printoutMessage("*** ");
			}
			
			printoutMessage("*** Loading file Unit Standards (If empty or null not uploaded) : " + fileUnitStandards);
			printoutMessage("*** ");
			
			String xmlUnitStandards = "";
			
			boolean doUpdateCreateOfUnitStandards = false;
						
			// ***************************************************************************************************************** //
			// CREATING TEMP UNIT STANDARDS                                                                                      // 
			// ***************************************************************************************************************** //
			
			if(fileUnitStandards != null) {
			
				printoutMessage("*** Getting unit standards xml string from XML file inputstream ");
				
				xmlUnitStandards = new String(getBytes(fileUnitStandards), Charset.forName("UTF-8"));
				
				printoutMessage("*** Got xml string from Unit Standards XML file inputstream with length : " + xmlUnitStandards.length());
				
				xmlUnitStandards = xmlUnitStandards.replaceAll("[\u0000-\u001f]", "");
				
				printoutMessage("*** Replaced all [\u0000-\u001f] caharacters with a space in unit standards xml string now with length : " + xmlUnitStandards.length());
	
				fileUnitStandards = null;
				
				printoutMessage("*** ");
				printoutMessage("*** Uploaded Unit Standards File set to null for garbage collection ");			
				
				printoutMessage("*** ");
				printoutMessage("*** Unmarshalling XML file to : " + "haj.com.saqa.unitstandards.SAQAUNITSTANDARD_TEMP object u list ");
				
				haj.com.saqa.unitstandards.SAQAUNITSTANDARD_TEMP u = (haj.com.saqa.unitstandards.SAQAUNITSTANDARD_TEMP) JAXBService.unmarshalltoObject(xmlUnitStandards, new haj.com.saqa.unitstandards.SAQAUNITSTANDARD_TEMP());
	
				List<UNITSTANDARD_TEMP> unitStandardsFromXMLList = u.getUNITSTANDARD();
				
				printoutMessage("*** Unmarshalled Unit Standards List size : " + unitStandardsFromXMLList.size());
				
				printoutMessage("*** Creating " + unitStandardsFromXMLList.size() + " entries in table saqa_unitstandard ");
				
				for (UNITSTANDARD_TEMP unitStandard : unitStandardsFromXMLList) {
					
					unitStandard.setLastdateforenrolment2(JAXBService.convertXMLtoDate(unitStandard.getLASTDATEFORENROLMENT()));
					unitStandard.setUsregistrationendDate(JAXBService.convertXMLtoDate(unitStandard.getUSREGISTRATIONENDDATE()));
					unitStandard.setUsregistrationstartDate(JAXBService.convertXMLtoDate(unitStandard.getUSREGISTRATIONSTARTDATE()));
					
					dao.create(unitStandard);
				}
				
				printoutMessage("*** Created all unit standards in saqa_unitstandard : " + unitStandardsFromXMLList.size());
				printoutMessage("*** ");
				
				doUpdateCreateOfUnitStandards = true;
			}
			
			// ***************************************************************************************************************** //
			// UPDATING EXISTING UNIT STANDARDS WITH UPDATED DATA AND INSERTING NEW UNIT STANDARDS THAT DONT EXIST BELOW         //
			// ***************************************************************************************************************** //
			
			if(doUpdateCreateOfUnitStandards == true) {
				
				int counterCreatedUnitStandards = 0;
				int counterUpdatedUnitStandards = 0;
				
				List<UnitStandardsTemp> existingTempUnitStandards = new SaqaQualificationDAO().allUnitStandardsTemp();
				
				for(UnitStandardsTemp unitStandardTemp : existingTempUnitStandards) {
					
					List<UnitStandards> existingUnitStandardsList = dao.findUnitStandardByUnitStandardId(unitStandardTemp.getCode());
					
					int existingListSize  = existingUnitStandardsList.size();
					
					if(existingListSize > 0){
						
						UnitStandards existingUnitStandards = existingUnitStandardsList.get(0);
						
						existingUnitStandards.setLastDateForEnrolment(unitStandardTemp.getLastDateForEnrolment());
						existingUnitStandards.setUsregistrationstartDate(unitStandardTemp.getUsregistrationendDate());
						existingUnitStandards.setUsregistrationendDate(unitStandardTemp.getUsregistrationendDate());
						
						dao.update(existingUnitStandards);
						
						printoutMessage("*** UPDATED UNIT STANDARD WITH ID : " + existingUnitStandards.getCode());
						
						counterUpdatedUnitStandards++;
					}
					
					printoutMessage("*** ");
					printoutMessage("*** UPDATED EXISTING UNIT STANDARDS : " + counterUpdatedUnitStandards);
					printoutMessage("*** ");
					
					if(existingListSize == 0){
						
						SaqaUnitstandard unitStandards = new SaqaUnitstandard();
						
						unitStandards.setAbetbanddescription(unitStandardTemp.getAbetbanddescription());
						unitStandards.setEtqaacronym(unitStandardTemp.getEtqaacronym());
						unitStandards.setEtqaname(unitStandardTemp.getEtqaname());
						unitStandards.setField(unitStandardTemp.getField());
						unitStandards.setFielddescription(unitStandardTemp.getFielddescription());
						unitStandards.setNqfleveldescription(unitStandardTemp.getNqfleveldescription());
						unitStandards.setNqflevelg2description(unitStandardTemp.getNqflevelg2description());
						unitStandards.setProvidercode(unitStandardTemp.getProvidercode());
						unitStandards.setProvideretqaid(unitStandardTemp.getProvideretqaid());
						unitStandards.setProvidername(unitStandardTemp.getProvidername());
						unitStandards.setRegistrationstatusdesc(unitStandardTemp.getRegistrationstatusdesc());
						unitStandards.setSaqadecisionnumber(unitStandardTemp.getSaqadecisionnumber());
						unitStandards.setSgbname(unitStandardTemp.getSgbname());
						unitStandards.setSubfielddescription(unitStandardTemp.getSubfielddescription());
						unitStandards.setTrainoutperiod(unitStandardTemp.getTrainoutperiod());
						unitStandards.setTransitionperiod(unitStandardTemp.getTransitionperiod());
						unitStandards.setUnitstandardid(unitStandardTemp.getCode());
						unitStandards.setUnitstdaccreditationoptions(unitStandardTemp.getUnitstdaccreditationoptions());
						unitStandards.setUnitstdassessorcriteria(unitStandards.getUnitstdassessorcriteria());
						unitStandards.setUnitstdccfocollecting(unitStandardTemp.getUnitstdccfocollecting());
						unitStandards.setUnitstdccfocommunicating(unitStandardTemp.getUnitstdccfocommunicating());
						unitStandards.setUnitstdccfocontributing(unitStandardTemp.getUnitstdccfocontributing());
						unitStandards.setUnitstdccfodemonstrating(unitStandardTemp.getUnitstdccfodemonstrating());
						unitStandards.setUnitstdccfoidentifying(unitStandardTemp.getUnitstdccfoidentifying());
						unitStandards.setUnitstdccfoorganizing(unitStandardTemp.getUnitstdccfoorganizing());
						unitStandards.setUnitstdccfoscience(unitStandardTemp.getUnitstdccfoscience());
						unitStandards.setUnitstdccfoworking(unitStandardTemp.getUnitstdccfoworking());
						unitStandards.setUnitstddevelopmentaloutcome(unitStandardTemp.getUnitstddevelopmentaloutcome());
						unitStandards.setUnitstdembeddedknowledge(unitStandardTemp.getUnitstdembeddedknowledge());
						unitStandards.setUnitstdlearningassumptions(unitStandardTemp.getUnitstdlearningassumptions());
						unitStandards.setUnitstdlinkages(unitStandardTemp.getUnitstdlinkages());
						unitStandards.setUnitstdnotes(unitStandardTemp.getUnitstdnotes());
						unitStandards.setUnitstdnumberofcredits(unitStandardTemp.getUnitstdnumberofcredits()!= null ? new Integer(unitStandardTemp.getUnitstdnumberofcredits()) : null);
						unitStandards.setUnitstdoutcomeheader(unitStandardTemp.getUnitstdoutcomeheader());
						unitStandards.setUnitstdpurpose(unitStandardTemp.getUnitstdpurpose());
						unitStandards.setUnitstdrange(unitStandardTemp.getUnitstdrange());
						unitStandards.setUnitstdtitle(unitStandardTemp.getTitle());
						unitStandards.setUnitstdtypedesc(unitStandardTemp.getUnitstdtypedesc());
						unitStandards.setSubfielddescription(unitStandardTemp.getSubfielddescription());
						
						unitStandards.setLastDateForEnrolment(unitStandardTemp.getLastDateForEnrolment());
						unitStandards.setUsregistrationstartDate(unitStandardTemp.getUsregistrationendDate());
						unitStandards.setUsregistrationendDate(unitStandardTemp.getUsregistrationendDate());
						
						dao.create(unitStandards);
						
						printoutMessage("*** CREATED UNIT STANDARD WITH UNIT STANDARD ID : " + unitStandardTemp.getCode());
						
						counterCreatedUnitStandards++;
					}
				}
				
				printoutMessage("*** ");
				printoutMessage("*** CREATED NEW UNIT STANDARDS : " + counterCreatedUnitStandards);
				printoutMessage("*** ");
				
				printoutMessage("*** ");
				printoutMessage("*** Finished inserting new Unit Standards and updating existing Unit Standards ");
				printoutMessage("*** ");
			}
			
			// send notification on start of process
			for (String emails : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emails, "UNIT STANDARD IMPORT COMPLETED", "Unit Standard Processes completed on: " + HAJConstants.PL_LINK, null);
			}
			
			endTimestamp = new Date(System.currentTimeMillis());
			
			duration  = endTimestamp.getTime() - startTimestamp.getTime();

			long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
			long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
			
			printoutMessage("*** Time taken - Days : " + diffInDays + " - Hours : " + diffInHours + " - Minutes : " + diffInMinutes + " - Seconds : " + diffInSeconds);
			printoutMessage("*** ");
			
			
			
			throw new Exception("Processing successful !");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			endTimestamp = new Date(System.currentTimeMillis());
			
			duration  = endTimestamp.getTime() - startTimestamp.getTime();

			long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
			long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
			
			printoutMessage("*** ");
			printoutMessage("*** Finished with exception : " + e.getMessage());
			printoutMessage("*** ");
			printoutMessage("*** Time taken - Days : " + diffInDays + " - Hours : " + diffInHours + " - Minutes : " + diffInMinutes + " - Seconds : " + diffInSeconds);
			printoutMessage("*** ");
			
			throw new Exception("Error occurred processing XML file : " + e.getMessage());
		}
	}
	
	public static void loadAndProcessQualifications(InputStream fileQualification) throws Exception {
		
		try {
			
			
			// send notification on start of process
			for (String emails : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emails, "QUALIFICATION IMPORT STARTED", "Qualification Processes Started on: " + HAJConstants.PL_LINK, null);
			}
		
			if(fileQualification == null){
				
				throw new Exception("No Qualification XML file was provided to process ");
			}
			
			startTimestamp = new Date(System.currentTimeMillis());
			
			printoutMessage("*** ");
			printoutMessage("*** PROCESSING XML FILE TO IMPORT / UPDATE EXISTING LOOKUP DATA FOR SAQA QUALIFICATIONS AND PROCESSING LINKAGES ");
			
			GenericDAO dao = new GenericDAO();
			
			if(fileQualification != null) {
								
				printoutMessage("*** ");
				printoutMessage("*** Deleting all from saqa_us_qualification_temp");
				
				dao.nonSelectSql("delete from saqa_us_qualification_temp");
				
				printoutMessage("*** ");
				printoutMessage("*** Deleting all from saqa_qualification_temp");
				
				dao.nonSelectSql("delete from saqa_qualification_temp");
				
				printoutMessage("*** ");
				printoutMessage("*** All temp Qualifications deleted successfully ");
				printoutMessage("*** ");
			}
			
			printoutMessage("*** Loading file Qualification  (If empty or null not uploaded) : " + fileQualification);
			printoutMessage("*** ");
			
			boolean doUpdateCreateOfQualifications = false;
			boolean doUpdateCreateLinkages = false;
			
			// ***************************************************************************************************************** //
			// CREATING TEMP QUALIFICATIONS                                                                                      // 
			// ***************************************************************************************************************** //
			
			String xmlQualifications = "";
			
			if(fileQualification != null) {
				
				printoutMessage("*** Getting qualifications xml string from XML file inputstream ");
				
				xmlQualifications = new String(getBytes(fileQualification), Charset.forName("UTF-8"));
				
				printoutMessage("*** Got xml string from Qualifications XML file inputstream with length : " + xmlQualifications.length());
				
				xmlQualifications = xmlQualifications.replaceAll("[\u0000-\u001f]", "");
				
				printoutMessage("*** Replaced all [\u0000-\u001f] caharacters with a space in xml string now with length : " + xmlQualifications.length());
	
				fileQualification = null;
				
				printoutMessage("*** ");
				printoutMessage("*** Uploaded Qualifications File set to null for garbage collection ");			
				
				printoutMessage("*** ");
				printoutMessage("*** Unmarshalling XML file to : " + "haj.com.saqa.qualifications.SAQAQUALIFICATIONS_TEMP object q list ");
				
				haj.com.saqa.qualifications.SAQAQUALIFICATIONS_TEMP q = (haj.com.saqa.qualifications.SAQAQUALIFICATIONS_TEMP) JAXBService.unmarshalltoObject(xmlQualifications, new haj.com.saqa.qualifications.SAQAQUALIFICATIONS_TEMP());
				
				printoutMessage("*** Unmarshalled XML file to q list ");
				
				List<QUALIFICATION_TEMP> qualificationFromXMLList = q.getQUALIFICATION();
				
				printoutMessage("*** Unmarshalled Qualififcation List size : " + qualificationFromXMLList.size());
				
				printoutMessage("*** Creating " + qualificationFromXMLList.size() + " entries in table saqa_qualification_temp ");
				
				// creates temp information
				for (QUALIFICATION_TEMP qual : qualificationFromXMLList) {
					
					qual.setLastdateforachievement2(JAXBService.convertXMLtoDate(qual.getLastdateforachievement()));
					qual.setLastdateforenrolment2(JAXBService.convertXMLtoDate(qual.getLastdateforenrolment()));
					qual.setQualregistrationstartDate(JAXBService.convertXMLtoDate(qual.getQualregistrationstartdate()));
					qual.setQualregistrationendDate(JAXBService.convertXMLtoDate(qual.getQualregistrationenddate()));
					
					dao.create(qual);
					
					List<USQUALIFICATIONLINK_TEMP> l2 = qual.getUSQUALIFICATIONLINK();
					
					for (USQUALIFICATIONLINK_TEMP usqualificationlink : l2) {
						
						List<QualificationTemp> qualificationTemp = dao.findQualificationTempByQualificationId(qual.getQualificationid());
						
						usqualificationlink.setNQFLEVELDESCRIPTION(usqualificationlink.getNQFLEVELDESCRIPTION());
						usqualificationlink.setNQFLEVELG2DESCRIPTION(usqualificationlink.getNQFLEVELG2DESCRIPTION());
						usqualificationlink.setQUALIFICATIONID(usqualificationlink.getQUALIFICATIONID());
						usqualificationlink.setQualificationTemp(qualificationTemp.get(0));
						usqualificationlink.setQUALIFICATIONID(usqualificationlink.getQualificationid());
						usqualificationlink.setUNITSTANDARDID(usqualificationlink.getUNITSTANDARDID());
						usqualificationlink.setUNITSTDNUMBEROFCREDITS(usqualificationlink.getUNITSTDNUMBEROFCREDITS());
						usqualificationlink.setUNITSTDTITLE(usqualificationlink.getUNITSTDTITLE());
						usqualificationlink.setUSQUALTYPEDESCRIPTION(usqualificationlink.getUSQUALTYPEDESCRIPTION());
												
						dao.create(usqualificationlink);
					}
				}
				
				printoutMessage("*** Created all qualifications in saqa_qualification_temp : " + qualificationFromXMLList.size());
				printoutMessage("*** Created all linkages in saqa_unitstandard_temp ");
				
				doUpdateCreateOfQualifications = true;
				doUpdateCreateLinkages = true;
			}
			
			// ***************************************************************************************************************** //
			// UPDATING EXISTING QUALIFICATIONS WITH UPDATED DATA AND INSERTING NEW QUALIFICATIONS THAT DONT EXIST BELOW         //
			// ***************************************************************************************************************** //
			
			printoutMessage("*** ");
			printoutMessage("*** DOING UPDATE TO ALL EXISTING QUALIFICATIONS ");
			printoutMessage("*** ");
			
			if(doUpdateCreateOfQualifications == true){
				
				int counterCreatedQualifications = 0;
				int counterUpdatedQualifications = 0;
								
				List<QualificationTemp> existingTempQualifications = new SaqaQualificationDAO().allQualificationTemp();
				
				for(QualificationTemp saqaQualTemp : existingTempQualifications) {
					
					List<Qualification> existingQualificationList = dao.findQualificationByQualificationId(saqaQualTemp.getCode());
					
					int existingListSize  = existingQualificationList.size();
					
					if(existingListSize > 0){
						
						Qualification anExistingQualification = existingQualificationList.get(0);
						
						anExistingQualification.setLastdateforachievement(saqaQualTemp.getLastdateforachievement());
						anExistingQualification.setLastDateForEnrolment(saqaQualTemp.getLastDateForEnrolment2());
						anExistingQualification.setQualregistrationstartDate(saqaQualTemp.getQualregistrationstartDate());
						anExistingQualification.setQualregistrationendDate(saqaQualTemp.getQualregistrationendDate());
						
						anExistingQualification.setRegistrationstatuscode(saqaQualTemp.getRegistrationstatuscode());
						anExistingQualification.setRegistrationstatusdesc(saqaQualTemp.getRegistrationstatusdesc());
						anExistingQualification.setQualificationtypeid(saqaQualTemp.getQualificationtypeid());
						
						dao.update(anExistingQualification);
						
						printoutMessage("*** UPDATED QUALIFICATION WITH ID : " + anExistingQualification.getCode());
						
						counterUpdatedQualifications++;
					}
				}
				
				printoutMessage("*** ");
				printoutMessage("*** UPDATED EXISTING QUALIFICATIONS : " + counterUpdatedQualifications);
				printoutMessage("*** ");
				
				printoutMessage("*** ");
				printoutMessage("*** CREATING MISSING QUALIFICATIONS ");
				printoutMessage("*** ");
				
				List<SaqaQualificationTemp> existingTempSaqaQualifications = new SaqaQualificationDAO().allSaqaQualificationTemp();
				
				for(SaqaQualificationTemp saqaQualTemp : existingTempSaqaQualifications){
					
					List<Qualification> existingSaqaQualificationList = dao.findQualificationByQualificationId(saqaQualTemp.getQualificationid());
					
					int existingListSize = existingSaqaQualificationList.size();
					
					if(existingListSize == 0){
						
						printoutMessage("*** CREATING QUALIFICATION WITH ID : " + saqaQualTemp.getQualificationid());
						
						SaqaQualification newSaqaQualification = new SaqaQualification();
						
						newSaqaQualification.setAbetbanddescription(saqaQualTemp.getAbetbanddescription());
						newSaqaQualification.setEloacqualassessmentcriteria(saqaQualTemp.getEloacqualassessmentcriteria());
						newSaqaQualification.setEloacqualificationoutcome(saqaQualTemp.getEloacqualificationoutcome());
						newSaqaQualification.setEtqaacronym(saqaQualTemp.getEtqaacronym());
						newSaqaQualification.setEtqaid(saqaQualTemp.getEtqaid());
						newSaqaQualification.setEtqaname(saqaQualTemp.getEtqaname());
						newSaqaQualification.setField(saqaQualTemp.getField());
						newSaqaQualification.setFielddescription(saqaQualTemp.getFielddescription());
						newSaqaQualification.setIslearningprogramme(saqaQualTemp.getIslearningprogramme());
						newSaqaQualification.setLearningprogrammequal(saqaQualTemp.getLearningprogrammequal());
						newSaqaQualification.setNqflevel(saqaQualTemp.getNqflevel());
						newSaqaQualification.setNqfleveldescription(saqaQualTemp.getNqfleveldescription());
						newSaqaQualification.setNqflevelg2description(saqaQualTemp.getNqflevelg2description());
						newSaqaQualification.setNqflevelId(saqaQualTemp.getNqflevelId());
						newSaqaQualification.setProvidercode(saqaQualTemp.getProvidercode());
						newSaqaQualification.setProvideretqaid(saqaQualTemp.getProvideretqaid());
						newSaqaQualification.setProvidername(saqaQualTemp.getProvidername());
						newSaqaQualification.setQualarticulationoptions(saqaQualTemp.getQualarticulationoptions());
						newSaqaQualification.setQualassessorcriteria(saqaQualTemp.getQualassessorcriteria());
						newSaqaQualification.setQualificationclassdesc(saqaQualTemp.getQualificationclassdesc());
						newSaqaQualification.setQualificationnotes(saqaQualTemp.getQualificationnotes());
						newSaqaQualification.setQualificationpurpose(saqaQualTemp.getQualificationpurpose());
						newSaqaQualification.setQualificationid(saqaQualTemp.getQualificationid());
						newSaqaQualification.setQualificationidString(saqaQualTemp.getQualificationid().toString());
						
						newSaqaQualification.setQualificationminimumcredits(saqaQualTemp.getQualificationminimumcredits());
						newSaqaQualification.setQualificationtitle(saqaQualTemp.getQualificationtitle());
						newSaqaQualification.setQualificationtypedesc(saqaQualTemp.getQualificationtypedesc());
						newSaqaQualification.setQualintlbenchmarkingmemo(saqaQualTemp.getQualintlbenchmarkingmemo());
						newSaqaQualification.setQuallearningassumedinplace(saqaQualTemp.getQuallearningassumedinplace());
						newSaqaQualification.setQualmoderationoptions(saqaQualTemp.getQualmoderationoptions());
						newSaqaQualification.setQualrulesofcombination(saqaQualTemp.getQualrulesofcombination());
						newSaqaQualification.setRecognizeprevlearningflag(saqaQualTemp.getRecognizeprevlearningflag());
						newSaqaQualification.setRegistrationstatusdesc(saqaQualTemp.getRegistrationstatusdesc());
						newSaqaQualification.setSaqadecisionnumber(saqaQualTemp.getSaqadecisionnumber());
						newSaqaQualification.setSgbname(saqaQualTemp.getSgbname());
						newSaqaQualification.setSubfielddescription(saqaQualTemp.getSubfielddescription());
						newSaqaQualification.setTrainoutperiod(saqaQualTemp.getTrainoutperiod());
						newSaqaQualification.setTransitionperiod(saqaQualTemp.getTransitionperiod());
						newSaqaQualification.setWorkplaceApprovalRequired(saqaQualTemp.getWorkplaceApprovalRequired());
						newSaqaQualification.setSubfielddescription(saqaQualTemp.getSubfielddescription());
								
						newSaqaQualification.setLastdateforachievement(saqaQualTemp.getLastdateforachievement());
						newSaqaQualification.setLastDateForEnrolment(saqaQualTemp.getLastDateForEnrolment());
						newSaqaQualification.setQualregistrationstartDate(saqaQualTemp.getQualregistrationstartDate());
						newSaqaQualification.setQualregistrationendDate(saqaQualTemp.getQualregistrationendDate());
						
						newSaqaQualification.setQualificationtypeid(saqaQualTemp.getQualificationtypeid());
						newSaqaQualification.setRegistrationstatuscode(saqaQualTemp.getRegistrationstatuscode());
						newSaqaQualification.setRegistrationstatusdesc(saqaQualTemp.getRegistrationstatusdesc());
						if (saqaQualTemp.getNqflevelId() != null && !saqaQualTemp.getNqflevelId().isEmpty()) {
							newSaqaQualification.setNqflevel(NqfLevelsService.instance().findByCode(saqaQualTemp.getNqflevelId().trim()));
						} else if (saqaQualTemp.getNqflevelg2description() != null && !saqaQualTemp.getNqflevelg2description().isEmpty()) {
							newSaqaQualification.setNqflevel(NqfLevelsService.instance().findBySaqaDescription(saqaQualTemp.getNqflevelg2description().trim()));
						}
						dao.create(newSaqaQualification);
						printoutMessage("*** CREATED QUALIFICATION WITH QUALIFICATION ID : " + saqaQualTemp.getQualificationid());
						counterCreatedQualifications++;
					}
				}
				
				printoutMessage("*** ");
				printoutMessage("*** CREATED NEW QUALIFICATIONS : " + counterCreatedQualifications);
				printoutMessage("*** ");
				
				printoutMessage("*** ");
				printoutMessage("*** Finished inserting new Qualifications and updating existing Qualifications ");
				printoutMessage("*** ");
			}
			
			if(doUpdateCreateLinkages == true) {
				
				printoutMessage("*** ");
				printoutMessage("*** DOING UPDATE OR CREATE OF QUALIFICATION AND UNIT STANDARDS LINKAGES ");
				printoutMessage("*** ");
				
				List<SaqaUsQualificationTemp> existingLinkages = dao.findAllSaqaUsQualifationsTemp();
				
				printoutMessage("*** ");
				printoutMessage("*** EXISTING TEMP LINKAGES : " + existingLinkages.size());
				printoutMessage("*** ");
				
				int counterLinkagesUpdated = 0;
				int counterLinkagesCreated = 0;
				
				for(SaqaUsQualificationTemp linkageTemp : existingLinkages){
					
					int qualificationId = linkageTemp.getQualificationid();
					int uniStandardsId = linkageTemp.getUnitstandardid();
					
					printoutMessage("*** FINDING LINKAGE WITH QUAL ID : " + qualificationId + " AND US ID : " + uniStandardsId);
					printoutMessage("*** ");
					
					List<SaqaUsQualification> saqaUsQualification = dao.findSaqaUsQualificationByQualificationIdAndUnitStandardId(qualificationId, uniStandardsId);
					
					if(saqaUsQualification.size() > 0){
						
						// UPDATE linkage - Nothing to update currently...
						
						printoutMessage("*** IN UPDATING EXISTING LINKAGE - HAVE NOTHING TO UPDATE CURRENTLY - SO MOVING ON !");
						
						counterLinkagesUpdated++;
						
					} else {
						
						// CREATE LINKAGE - Create new linkages for all newly created qualifications and unit standards...
						
						printoutMessage("*** CREATING LINKAGE - WITH QUAL ID : " + qualificationId + "  AND  US ID : " + uniStandardsId);
						printoutMessage("*** ");
						printoutMessage("*** FINDING SAQA QUALIFICATION - WITH LINKAGE'S QUAL ID : " + qualificationId);
						
						List<SaqaQualification> existingSaqaQualification = dao.findSaqaQualificationByQualificationId(qualificationId);
						
						printoutMessage("*** FOUND SAQA QUALIFICATION WITH QUAL ID OF QUAL ID : " + qualificationId + " AS : existingSaqaQualification.get(0) : " + existingSaqaQualification.get(0));
						
						SaqaUsQualification newSaqaUsQualification = new SaqaUsQualification();
						
						newSaqaUsQualification.setNqfleveldescription(linkageTemp.getNqfleveldescription());
						newSaqaUsQualification.setNqflevelg2description(linkageTemp.getNqflevelg2description());
						newSaqaUsQualification.setQualificationid(linkageTemp.getQualificationid());
						newSaqaUsQualification.setUnitstandardid(linkageTemp.getUnitstandardid());
						newSaqaUsQualification.setUnitstdnumberofcredits(linkageTemp.getUnitstdnumberofcredits());
						newSaqaUsQualification.setUnitstdtitle(linkageTemp.getUnitstdtitle());
						newSaqaUsQualification.setUsqualtypedescription(linkageTemp.getUsqualtypedescription());
						
						newSaqaUsQualification.setSaqaQualification(existingSaqaQualification.get(0));
						
						dao.create(newSaqaUsQualification);
						
						printoutMessage("*** CREATED LINKAGE - WITH QUAL ID : " + qualificationId + "  AND  US ID : " + uniStandardsId);
						
						counterLinkagesCreated++;
					}
				}
				
				printoutMessage("*** ");
				printoutMessage("*** FINISHED UPDATE OR CREATE OF QUALIFICATION AND UNIT STANDARDS LINKAGES ");
				printoutMessage("*** ");
				printoutMessage("*** NUMBER OF LINKAGES CREATED : " + counterLinkagesCreated);
				printoutMessage("*** NUMBER OF LINKAGES UPDATED : " + counterLinkagesUpdated);
				printoutMessage("*** ");
			}
			
			// send notification on start of process
			for (String emails : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emails, "QUALIFICATION IMPORT ENDED", "Qualification Processes ended on: " + HAJConstants.PL_LINK, null);
			}
			
			endTimestamp = new Date(System.currentTimeMillis());
			
			duration  = endTimestamp.getTime() - startTimestamp.getTime();
	
			long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
			long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
			
			printoutMessage("*** Time taken - Days : " + diffInDays + " - Hours : " + diffInHours + " - Minutes : " + diffInMinutes + " - Seconds : " + diffInSeconds);
			printoutMessage("*** ");
			
			throw new Exception("Processing successful !");
			
		} catch (Exception exception){
			
			exception.printStackTrace();
			
			endTimestamp = new Date(System.currentTimeMillis());
			
			duration  = endTimestamp.getTime() - startTimestamp.getTime();
			
			long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
			long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
			
			printoutMessage("*** ");
			printoutMessage("*** Finished with exception : " + exception.getMessage());
			printoutMessage("*** ");
			printoutMessage("*** Time taken - Days : " + diffInDays + " - Hours : " + diffInHours + " - Minutes : " + diffInMinutes + " - Seconds : " + diffInSeconds);
			printoutMessage("*** ");
			
			throw new Exception("Error occurred processing XML file : " + exception.getMessage());
		}
	}
	
	public static void onlyProcessQualifications() throws Exception {
		
		try {
		
			GenericDAO dao = new GenericDAO();
				
			boolean doUpdateCreateOfQualifications = true;
			boolean doUpdateCreateLinkages = true;

			// ***************************************************************************************************************** //
			// UPDATING EXISTING QUALIFICATIONS WITH UPDATED DATA AND INSERTING NEW QUALIFICATIONS THAT DONT EXIST BELOW         //
			// ***************************************************************************************************************** //
			
			printoutMessage("*** ");
			printoutMessage("*** DOING UPDATE TO ALL EXISTING QUALIFICATIONS ");
			printoutMessage("*** ");
			
			if(doUpdateCreateOfQualifications == true){
				
				int counterCreatedQualifications = 0;
				int counterUpdatedQualifications = 0;
								
				List<QualificationTemp> existingTempQualifications = new SaqaQualificationDAO().allQualificationTemp();
				
				for(QualificationTemp saqaQualTemp : existingTempQualifications) {
					
					List<Qualification> existingQualificationList = dao.findQualificationByQualificationId(saqaQualTemp.getCode());
					
					int existingListSize  = existingQualificationList.size();
					
					if(existingListSize > 0){
						
						Qualification anExistingQualification = existingQualificationList.get(0);
						
						anExistingQualification.setLastdateforachievement(saqaQualTemp.getLastdateforachievement());
						anExistingQualification.setLastDateForEnrolment(saqaQualTemp.getLastDateForEnrolment2());
						anExistingQualification.setQualregistrationstartDate(saqaQualTemp.getQualregistrationstartDate());
						anExistingQualification.setQualregistrationendDate(saqaQualTemp.getQualregistrationendDate());
						
						anExistingQualification.setRegistrationstatuscode(saqaQualTemp.getRegistrationstatuscode());
						anExistingQualification.setRegistrationstatusdesc(saqaQualTemp.getRegistrationstatusdesc());
						anExistingQualification.setQualificationtypeid(saqaQualTemp.getQualificationtypeid());
						
						dao.update(anExistingQualification);
						
						printoutMessage("*** UPDATED QUALIFICATION WITH ID : " + anExistingQualification.getCode());
						
						counterUpdatedQualifications++;
					}
				}
				
				printoutMessage("*** ");
				printoutMessage("*** UPDATED EXISTING QUALIFICATIONS : " + counterUpdatedQualifications);
				printoutMessage("*** ");
				
				printoutMessage("*** ");
				printoutMessage("*** CREATING MISSING QUALIFICATIONS ");
				printoutMessage("*** ");
				
				List<SaqaQualificationTemp> existingTempSaqaQualifications = new SaqaQualificationDAO().allSaqaQualificationTemp();
				
				for(SaqaQualificationTemp saqaQualTemp : existingTempSaqaQualifications){
					
					List<Qualification> existingSaqaQualificationList = dao.findQualificationByQualificationId(saqaQualTemp.getQualificationid());
					
					int existingListSize = existingSaqaQualificationList.size();
					
					if(existingListSize == 0){
						
						printoutMessage("*** CREATING QUALIFICATION WITH ID : " + saqaQualTemp.getQualificationid());
						
						SaqaQualification newSaqaQualification = new SaqaQualification();
						
						newSaqaQualification.setAbetbanddescription(saqaQualTemp.getAbetbanddescription());
						newSaqaQualification.setEloacqualassessmentcriteria(saqaQualTemp.getEloacqualassessmentcriteria());
						newSaqaQualification.setEloacqualificationoutcome(saqaQualTemp.getEloacqualificationoutcome());
						newSaqaQualification.setEtqaacronym(saqaQualTemp.getEtqaacronym());
						newSaqaQualification.setEtqaid(saqaQualTemp.getEtqaid());
						newSaqaQualification.setEtqaname(saqaQualTemp.getEtqaname());
						newSaqaQualification.setField(saqaQualTemp.getField());
						newSaqaQualification.setFielddescription(saqaQualTemp.getFielddescription());
						newSaqaQualification.setIslearningprogramme(saqaQualTemp.getIslearningprogramme());
						newSaqaQualification.setLearningprogrammequal(saqaQualTemp.getLearningprogrammequal());
						newSaqaQualification.setNqflevel(saqaQualTemp.getNqflevel());
						newSaqaQualification.setNqfleveldescription(saqaQualTemp.getNqfleveldescription());
						newSaqaQualification.setNqflevelg2description(saqaQualTemp.getNqflevelg2description());
						newSaqaQualification.setNqflevelId(saqaQualTemp.getNqflevelId());
						newSaqaQualification.setProvidercode(saqaQualTemp.getProvidercode());
						newSaqaQualification.setProvideretqaid(saqaQualTemp.getProvideretqaid());
						newSaqaQualification.setProvidername(saqaQualTemp.getProvidername());
						newSaqaQualification.setQualarticulationoptions(saqaQualTemp.getQualarticulationoptions());
						newSaqaQualification.setQualassessorcriteria(saqaQualTemp.getQualassessorcriteria());
						newSaqaQualification.setQualificationclassdesc(saqaQualTemp.getQualificationclassdesc());
						newSaqaQualification.setQualificationnotes(saqaQualTemp.getQualificationnotes());
						newSaqaQualification.setQualificationpurpose(saqaQualTemp.getQualificationpurpose());
						newSaqaQualification.setQualificationid(saqaQualTemp.getQualificationid());
						newSaqaQualification.setQualificationidString(saqaQualTemp.getQualificationid().toString());
						
						newSaqaQualification.setQualificationminimumcredits(saqaQualTemp.getQualificationminimumcredits());
						newSaqaQualification.setQualificationtitle(saqaQualTemp.getQualificationtitle());
						newSaqaQualification.setQualificationtypedesc(saqaQualTemp.getQualificationtypedesc());
						newSaqaQualification.setQualintlbenchmarkingmemo(saqaQualTemp.getQualintlbenchmarkingmemo());
						newSaqaQualification.setQuallearningassumedinplace(saqaQualTemp.getQuallearningassumedinplace());
						newSaqaQualification.setQualmoderationoptions(saqaQualTemp.getQualmoderationoptions());
						newSaqaQualification.setQualrulesofcombination(saqaQualTemp.getQualrulesofcombination());
						newSaqaQualification.setRecognizeprevlearningflag(saqaQualTemp.getRecognizeprevlearningflag());
						newSaqaQualification.setRegistrationstatusdesc(saqaQualTemp.getRegistrationstatusdesc());
						newSaqaQualification.setSaqadecisionnumber(saqaQualTemp.getSaqadecisionnumber());
						newSaqaQualification.setSgbname(saqaQualTemp.getSgbname());
						newSaqaQualification.setSubfielddescription(saqaQualTemp.getSubfielddescription());
						newSaqaQualification.setTrainoutperiod(saqaQualTemp.getTrainoutperiod());
						newSaqaQualification.setTransitionperiod(saqaQualTemp.getTransitionperiod());
						newSaqaQualification.setWorkplaceApprovalRequired(saqaQualTemp.getWorkplaceApprovalRequired());
						newSaqaQualification.setSubfielddescription(saqaQualTemp.getSubfielddescription());
								
						newSaqaQualification.setLastdateforachievement(saqaQualTemp.getLastdateforachievement());
						newSaqaQualification.setLastDateForEnrolment(saqaQualTemp.getLastDateForEnrolment());
						newSaqaQualification.setQualregistrationstartDate(saqaQualTemp.getQualregistrationstartDate());
						newSaqaQualification.setQualregistrationendDate(saqaQualTemp.getQualregistrationendDate());
						
						newSaqaQualification.setQualificationtypeid(saqaQualTemp.getQualificationtypeid());
						newSaqaQualification.setRegistrationstatuscode(saqaQualTemp.getRegistrationstatuscode());
						newSaqaQualification.setRegistrationstatusdesc(saqaQualTemp.getRegistrationstatusdesc());
						if (saqaQualTemp.getNqflevelId() != null && !saqaQualTemp.getNqflevelId().isEmpty()) {
							newSaqaQualification.setNqflevel(NqfLevelsService.instance().findByCode(saqaQualTemp.getNqflevelId().trim()));
						} else if (saqaQualTemp.getNqflevelg2description() != null && !saqaQualTemp.getNqflevelg2description().isEmpty()) {
							newSaqaQualification.setNqflevel(NqfLevelsService.instance().findBySaqaDescription(saqaQualTemp.getNqflevelg2description().trim()));
						}
						dao.create(newSaqaQualification);
						printoutMessage("*** CREATED QUALIFICATION WITH QUALIFICATION ID : " + saqaQualTemp.getQualificationid());
						counterCreatedQualifications++;
					}
				}
				
				printoutMessage("*** ");
				printoutMessage("*** CREATED NEW QUALIFICATIONS : " + counterCreatedQualifications);
				printoutMessage("*** ");
				
				printoutMessage("*** ");
				printoutMessage("*** Finished inserting new Qualifications and updating existing Qualifications ");
				printoutMessage("*** ");
			}
			
			if(doUpdateCreateLinkages == true) {
				
				printoutMessage("*** ");
				printoutMessage("*** DOING UPDATE OR CREATE OF QUALIFICATION AND UNIT STANDARDS LINKAGES ");
				printoutMessage("*** ");
				
				List<SaqaUsQualificationTemp> existingLinkages = dao.findAllSaqaUsQualifationsTemp();
				
				printoutMessage("*** ");
				printoutMessage("*** EXISTING TEMP LINKAGES : " + existingLinkages.size());
				printoutMessage("*** ");
				
				int counterLinkagesUpdated = 0;
				int counterLinkagesCreated = 0;
				
				for(SaqaUsQualificationTemp linkageTemp : existingLinkages){
					
					int qualificationId = linkageTemp.getQualificationid();
					int uniStandardsId = linkageTemp.getUnitstandardid();
					
					printoutMessage("*** FINDING LINKAGE WITH QUAL ID : " + qualificationId + " AND US ID : " + uniStandardsId);
					printoutMessage("*** ");
					
					List<SaqaUsQualification> saqaUsQualification = dao.findSaqaUsQualificationByQualificationIdAndUnitStandardId(qualificationId, uniStandardsId);
					
					if(saqaUsQualification.size() > 0){
						
						// UPDATE linkage - Nothing to update currently...
						
						printoutMessage("*** IN UPDATING EXISTING LINKAGE - HAVE NOTHING TO UPDATE CURRENTLY - SO MOVING ON !");
						
						counterLinkagesUpdated++;
						
					} else {
						
						// CREATE LINKAGE - Create new linkages for all newly created qualifications and unit standards...
						
						printoutMessage("*** CREATING LINKAGE - WITH QUAL ID : " + qualificationId + "  AND  US ID : " + uniStandardsId);
						printoutMessage("*** ");
						printoutMessage("*** FINDING SAQA QUALIFICATION - WITH LINKAGE'S QUAL ID : " + qualificationId);
						
						List<SaqaQualification> existingSaqaQualification = dao.findSaqaQualificationByQualificationId(qualificationId);
						
						printoutMessage("*** FOUND SAQA QUALIFICATION WITH QUAL ID OF QUAL ID : " + qualificationId + " AS : existingSaqaQualification.get(0) : " + existingSaqaQualification.get(0));
						
						SaqaUsQualification newSaqaUsQualification = new SaqaUsQualification();
						
						newSaqaUsQualification.setNqfleveldescription(linkageTemp.getNqfleveldescription());
						newSaqaUsQualification.setNqflevelg2description(linkageTemp.getNqflevelg2description());
						newSaqaUsQualification.setQualificationid(linkageTemp.getQualificationid());
						newSaqaUsQualification.setUnitstandardid(linkageTemp.getUnitstandardid());
						newSaqaUsQualification.setUnitstdnumberofcredits(linkageTemp.getUnitstdnumberofcredits());
						newSaqaUsQualification.setUnitstdtitle(linkageTemp.getUnitstdtitle());
						newSaqaUsQualification.setUsqualtypedescription(linkageTemp.getUsqualtypedescription());
						
						newSaqaUsQualification.setSaqaQualification(existingSaqaQualification.get(0));
						
						dao.create(newSaqaUsQualification);
						
						printoutMessage("*** CREATED LINKAGE - WITH QUAL ID : " + qualificationId + "  AND  US ID : " + uniStandardsId);
						
						counterLinkagesCreated++;
					}
				}
				
				printoutMessage("*** ");
				printoutMessage("*** FINISHED UPDATE OR CREATE OF QUALIFICATION AND UNIT STANDARDS LINKAGES ");
				printoutMessage("*** ");
				printoutMessage("*** NUMBER OF LINKAGES CREATED : " + counterLinkagesCreated);
				printoutMessage("*** NUMBER OF LINKAGES UPDATED : " + counterLinkagesUpdated);
				printoutMessage("*** ");
			}
			
			endTimestamp = new Date(System.currentTimeMillis());
			
			duration  = endTimestamp.getTime() - startTimestamp.getTime();
	
			long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
			long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
			
			printoutMessage("*** Time taken - Days : " + diffInDays + " - Hours : " + diffInHours + " - Minutes : " + diffInMinutes + " - Seconds : " + diffInSeconds);
			printoutMessage("*** ");
			
			throw new Exception("Processing successful !");
			
		} catch (Exception exception){
			
			exception.printStackTrace();
			
			endTimestamp = new Date(System.currentTimeMillis());
			
			duration  = endTimestamp.getTime() - startTimestamp.getTime();
			
			long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
			long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
			
			printoutMessage("*** ");
			printoutMessage("*** Finished with exception : " + exception.getMessage());
			printoutMessage("*** ");
			printoutMessage("*** Time taken - Days : " + diffInDays + " - Hours : " + diffInHours + " - Minutes : " + diffInMinutes + " - Seconds : " + diffInSeconds);
			printoutMessage("*** ");
			
			throw new Exception("Error occurred processing XML file : " + exception.getMessage());
		}
	}
	
	/**
	 * @param message
	 */
	private static void printoutMessage(String message){
		
		if(PRINT_OUT_SYSOUTS) {
			
			System.out.println(message);
		}
	}
	
	
	/**
	 * @param is
	 * @return byte [] 
	 * @throws IOException
	 */
	private static byte[] getBytes(InputStream is) throws IOException {

	    int len;
	    int size = 1024;
	    byte[] buf;
	    
	    if (is instanceof ByteArrayInputStream) {
	    	
	    	size = is.available();
	    	buf = new byte[size];
	    	len = is.read(buf, 0, size);
	    	
	    } else {
	    	
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	buf = new byte[size];
	    	while ((len = is.read(buf, 0, size)) != -1)
	    		bos.write(buf, 0, len);
	    	buf = bos.toByteArray();
	    }
	    
	    return buf;
	}
	
	// ******************************************************************************************************************************************************************************** //

	/**
	 * This was the intial load...all post 2009 stuff
	 * 
	 * @param file
	 */
	public static void loadNQFInital(String file) {
		try {

			GenericDAO dao = new GenericDAO();
			String xml = new String(Files.readAllBytes(new File(prefix + file).toPath()), Charset.forName("UTF-8"));
			xml = xml.replaceAll("[\u0000-\u001f]", "");

			haj.com.saqa.qualifications.SAQAQUALIFICATIONS q = (haj.com.saqa.qualifications.SAQAQUALIFICATIONS) JAXBService.unmarshalltoObject(xml, new haj.com.saqa.qualifications.SAQAQUALIFICATIONS());
			List<QUALIFICATION> l = q.getQUALIFICATION();
			for (QUALIFICATION qual : l) {
				dao.create(qual);
				List<USQUALIFICATIONLINK> l2 = qual.getUSQUALIFICATIONLINK();
				for (USQUALIFICATIONLINK usqualificationlink : l2) {
//					usqualificationlink.setQualification(qual);
					dao.create(usqualificationlink);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Update ETQID.
	 *
	 * @param file
	 *            the file
	 */
	public static void updateETQID(String file) {
		try {
			System.out.println("updateETQID file: " + file);
			SaqaQualificationDAO dao = new SaqaQualificationDAO();
			String xml = new String(Files.readAllBytes(new File(prefix + file).toPath()), Charset.forName("UTF-8"));
			xml = xml.replaceAll("[\u0000-\u001f]", "");

			haj.com.saqa.qualifications.SAQAQUALIFICATIONS q = (haj.com.saqa.qualifications.SAQAQUALIFICATIONS) JAXBService.unmarshalltoObject(xml, new haj.com.saqa.qualifications.SAQAQUALIFICATIONS());
			List<QUALIFICATION> l = q.getQUALIFICATION();
			for (QUALIFICATION qual : l) {
				SaqaQualification s = dao.findByQualID(qual.getQualificationid());
				if (s != null) {
					s.setEtqaid(qual.getEtqaid());
					dao.update(s);
				}

			}
		} catch (org.xml.sax.SAXParseException spe) {
			System.out.println();
			spe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Update NQF id.
	 *
	 * @param file
	 *            the file
	 */
	public static void updateNQFId(String file) {
		try {
			System.out.println("updateNQFId file: " + file);
			SaqaQualificationDAO dao = new SaqaQualificationDAO();
			String xml = new String(Files.readAllBytes(new File(prefix + file).toPath()), Charset.forName("UTF-8"));
			xml = xml.replaceAll("[\u0000-\u001f]", "");

			haj.com.saqa.qualifications.SAQAQUALIFICATIONS q = (haj.com.saqa.qualifications.SAQAQUALIFICATIONS) JAXBService.unmarshalltoObject(xml, new haj.com.saqa.qualifications.SAQAQUALIFICATIONS());
			List<QUALIFICATION> l = q.getQUALIFICATION();
			for (QUALIFICATION qual : l) {
				SaqaQualification s = dao.findByQualID(qual.getQualificationid());
				if (s != null) {
					s.setNqflevelId(qual.getNqflevelg2Id());
					dao.update(s);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Update NQFFK.
	 */
	public static void updateNQFFK() {
		try {
			System.out.println("updateNQFFK ");
			NqfLevelsDAO nqfdao = new NqfLevelsDAO();
			Map<String, NqfLevels> m = new HashMap<String, NqfLevels>();

			List<NqfLevels> l2 = nqfdao.allNqfLevels();
			for (NqfLevels n : l2) {
				m.put(n.getCode().trim(), n);
			}

			SaqaQualificationDAO dao = new SaqaQualificationDAO();
			List<SaqaQualification> l = dao.allSaqaQualification();
			for (SaqaQualification saqaQualification : l) {
				saqaQualification.setNqflevel(m.get(saqaQualification.getNqflevelId().trim()));
				dao.update(saqaQualification);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void updateNQFFKpre2009() {
		try {
			System.out.println("updateNQFFKpre2009");
			NqfLevelsDAO nqfdao = new NqfLevelsDAO();
			Map<String, NqfLevels> m = new HashMap<String, NqfLevels>();

			List<NqfLevels> l2 = nqfdao.allNqfLevels();
			for (NqfLevels n : l2) {
				if (n.getPre2009Description() != null) m.put(n.getPre2009Description().trim(), n);
			}

			SaqaQualificationDAO dao = new SaqaQualificationDAO();
			List<SaqaQualification> l = dao.allSaqaQualificationWithNoNqfLink();
			for (SaqaQualification saqaQualification : l) {
				saqaQualification.setNqflevel(m.get(saqaQualification.getNqfleveldescription().trim()));
				dao.update(saqaQualification);
			}

			dao.nonSelectSql("update saqa_qualification set qualificationid_string = qualificationid where qualificationid_string is null");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void loadPre2009() {

		loadNQF("Level NA Pre-2009 was L1/QUALIFICATION.xml");
		loadNQF("Level NA Pre-2009 was L2/QUALIFICATION.xml");
		loadNQF("Level NA Pre-2009 was L3/QUALIFICATION.xml");
		loadNQF("Level NA Pre-2009 was L4/QUALIFICATION.xml");
		loadNQF("Level NA Pre-2009 was L5/QUALIFICATION.xml");
		loadNQF("Level NA Pre-2009 was L6/QUALIFICATION.xml");
		loadNQF("Level NA Pre-2009 was L7/QUALIFICATION.xml");
		loadNQF("Level NA Pre-2009 was L8+/QUALIFICATION.xml");
		loadNQF("Level TBA Pre-2009 was L1/QUALIFICATION.xml");
		loadNQF("Level TBA Pre-2009 was L2/QUALIFICATION.xml");
		loadNQF("Level TBA Pre-2009 was L3/QUALIFICATION.xml");
		loadNQF("Level TBA Pre-2009 was L4/QUALIFICATION.xml");
		loadNQF("Level TBA Pre-2009 was L5/QUALIFICATION.xml");
		loadNQF("Level TBA Pre-2009 was L6/QUALIFICATION.xml");
		loadNQF("Level TBA Pre-2009 was L7/QUALIFICATION.xml");
		loadNQF("Level TBAPre-2009 BelowL1/QUALIFICATION.xml");
		loadNQF("Level TBAPre-2009 was L8+/QUALIFICATION.xml");

		updateETQID("Level NA Pre-2009 was L1/QUALIFICATION.xml");
		updateETQID("Level NA Pre-2009 was L2/QUALIFICATION.xml");
		updateETQID("Level NA Pre-2009 was L3/QUALIFICATION.xml");
		updateETQID("Level NA Pre-2009 was L4/QUALIFICATION.xml");
		updateETQID("Level NA Pre-2009 was L5/QUALIFICATION.xml");
		updateETQID("Level NA Pre-2009 was L6/QUALIFICATION.xml");
		updateETQID("Level NA Pre-2009 was L7/QUALIFICATION.xml");
		updateETQID("Level NA Pre-2009 was L8+/QUALIFICATION.xml");
		updateETQID("Level TBA Pre-2009 was L1/QUALIFICATION.xml");
		updateETQID("Level TBA Pre-2009 was L2/QUALIFICATION.xml");
		updateETQID("Level TBA Pre-2009 was L3/QUALIFICATION.xml");
		updateETQID("Level TBA Pre-2009 was L4/QUALIFICATION.xml");
		updateETQID("Level TBA Pre-2009 was L5/QUALIFICATION.xml");
		updateETQID("Level TBA Pre-2009 was L6/QUALIFICATION.xml");
		updateETQID("Level TBA Pre-2009 was L7/QUALIFICATION.xml");
		updateETQID("Level TBAPre-2009 BelowL1/QUALIFICATION.xml");
		updateETQID("Level TBAPre-2009 was L8+/QUALIFICATION.xml");

		updateNQFId("Level NA Pre-2009 was L1/QUALIFICATION.xml");
		updateNQFId("Level NA Pre-2009 was L2/QUALIFICATION.xml");
		updateNQFId("Level NA Pre-2009 was L3/QUALIFICATION.xml");
		updateNQFId("Level NA Pre-2009 was L4/QUALIFICATION.xml");
		updateNQFId("Level NA Pre-2009 was L5/QUALIFICATION.xml");
		updateNQFId("Level NA Pre-2009 was L6/QUALIFICATION.xml");
		updateNQFId("Level NA Pre-2009 was L7/QUALIFICATION.xml");
		updateNQFId("Level NA Pre-2009 was L8+/QUALIFICATION.xml");
		updateNQFId("Level TBA Pre-2009 was L1/QUALIFICATION.xml");
		updateNQFId("Level TBA Pre-2009 was L2/QUALIFICATION.xml");
		updateNQFId("Level TBA Pre-2009 was L3/QUALIFICATION.xml");
		updateNQFId("Level TBA Pre-2009 was L4/QUALIFICATION.xml");
		updateNQFId("Level TBA Pre-2009 was L5/QUALIFICATION.xml");
		updateNQFId("Level TBA Pre-2009 was L6/QUALIFICATION.xml");
		updateNQFId("Level TBA Pre-2009 was L7/QUALIFICATION.xml");
		updateNQFId("Level TBAPre-2009 BelowL1/QUALIFICATION.xml");
		updateNQFId("Level TBAPre-2009 was L8+/QUALIFICATION.xml");

		updateNQFFKpre2009();

	}

	public static void loadAll() {
		File inputFile = new File(findPrefixFolder());
		for (File file : inputFile.listFiles()) {
			if (file.isDirectory()) {
				if (Integer.parseInt(file.getName()) > 12) {
					loadNQF("/" + file.getName() + "/QUALIFICATION.xml");
				}
			}
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println("START");
		// updateNQFFK();
		/*
		 * updateNQFId("NQF1/QUALIFICATION.xml"); updateNQFId("NQF1/QUALIFICATION.xml");
		 * updateNQFId("NQF2/QUALIFICATION.xml"); updateNQFId("NQF3/QUALIFICATION.xml");
		 * updateNQFId("NQF4/QUALIFICATION.xml"); updateNQFId("NQF5/QUALIFICATION.xml");
		 * updateNQFId("NQF6/QUALIFICATION.xml"); updateNQFId("NQF7/QUALIFICATION.xml");
		 * updateNQFId("NQF8/QUALIFICATION.xml"); updateNQFId("NQF9/QUALIFICATION.xml");
		 * updateNQFId("NQF10/QUALIFICATION.xml");
		 * updateNQFId("NotApplicable/QUALIFICATION.xml");
		 * updateNQFId("Undefined/QUALIFICATION.xml");
		 * 
		 * updateETQID("NQF1/QUALIFICATION.xml"); updateETQID("NQF1/QUALIFICATION.xml");
		 * updateETQID("NQF2/QUALIFICATION.xml"); updateETQID("NQF3/QUALIFICATION.xml");
		 * updateETQID("NQF4/QUALIFICATION.xml"); updateETQID("NQF5/QUALIFICATION.xml");
		 * updateETQID("NQF6/QUALIFICATION.xml"); updateETQID("NQF7/QUALIFICATION.xml");
		 * updateETQID("NQF8/QUALIFICATION.xml"); updateETQID("NQF9/QUALIFICATION.xml");
		 * updateETQID("NQF10/QUALIFICATION.xml");
		 * updateETQID("NotApplicable/QUALIFICATION.xml");
		 * updateETQID("Undefined/QUALIFICATION.xml");
		 * loadNQF("NQF1/QUALIFICATION.xml"); loadNQF("NQF2/QUALIFICATION.xml");
		 * loadNQF("NQF3/QUALIFICATION.xml"); loadNQF("NQF4/QUALIFICATION.xml");
		 * loadNQF("NQF5/QUALIFICATION.xml"); loadNQF("NQF6/QUALIFICATION.xml");
		 * loadNQF("NQF7/QUALIFICATION.xml"); loadNQF("NQF8/QUALIFICATION.xml");
		 * loadNQF("NQF9/QUALIFICATION.xml"); loadNQF("NQF10/QUALIFICATION.xml");
		 * loadNQF("NotApplicable/QUALIFICATION.xml");
		 * loadNQF("Undefined/QUALIFICATION.xml");
		 */

		loadAll();

		System.out.println("DONE");
		System.exit(0);
	}	
}

/*
 *
 * ALTER TABLE `nqf_levels` ADD COLUMN `pre_2009_description` VARCHAR(100) NULL
 * AFTER `highest_qualification_required_id`;
 * 
 * UPDATE `nqf_levels` SET `pre_2009_description`='Level: 1' WHERE `id`='2';
 * UPDATE `nqf_levels` SET `pre_2009_description`='Level: 2' WHERE `id`='3';
 * UPDATE `nqf_levels` SET `pre_2009_description`='Level: 3' WHERE `id`='4';
 * UPDATE `nqf_levels` SET `pre_2009_description`='Level: 4' WHERE `id`='5';
 * UPDATE `nqf_levels` SET `pre_2009_description`='Level: 5' WHERE `id`='6';
 * UPDATE `nqf_levels` SET `pre_2009_description`='Level: 6' WHERE `id`='7';
 * UPDATE `nqf_levels` SET `pre_2009_description`='Level: 7' WHERE `id`='8';
 * UPDATE `nqf_levels` SET `pre_2009_description`='Level: 8 and above' WHERE
 * `id`='9';
 * 
 * 
 * DROP TABLE `saqa_us_qualification_temp`; DROP TABLE
 * `saqa_qualification_temp`;
 * 
 * CREATE TABLE `saqa_qualification_temp` ( `id` bigint(20) NOT NULL
 * AUTO_INCREMENT, `abetbanddescription` longtext, `eloacqualassessmentcriteria`
 * longtext, `eloacqualificationoutcome` longtext, `etqaacronym` longtext,
 * `etqaname` longtext, `field` longtext, `fielddescription` longtext,
 * `islearningprogramme` longtext, `learningprogrammequal` int(11) DEFAULT NULL,
 * `nqfleveldescription` longtext, `nqflevelg2DESCRIPTION` longtext,
 * `providercode` longtext, `provideretqaid` int(11) DEFAULT NULL,
 * `providername` longtext, `qualarticulationoptions` longtext,
 * `qualassessorcriteria` longtext, `qualificationclassdesc` longtext,
 * `qualificationid` int(11) DEFAULT NULL, `qualificationminimumcredits` int(11)
 * DEFAULT NULL, `qualificationnotes` longtext, `qualificationpurpose` longtext,
 * `qualificationtitle` longtext, `qualificationtypedesc` longtext,
 * `qualintlbenchmarkingmemo` longtext, `quallearningassumedinplace` longtext,
 * `qualmoderationoptions` longtext, `qualrulesofcombination` longtext,
 * `recognizeprevlearningflag` longtext, `registrationstatusdesc` longtext,
 * `saqadecisionnumber` longtext, `sgbname` longtext, `subfielddescription`
 * longtext, `trainoutperiod` int(11) DEFAULT NULL, `transitionperiod` int(11)
 * DEFAULT NULL, `etqaid` varchar(255) DEFAULT NULL, `nqf_level_id` bigint(20)
 * DEFAULT NULL, `nqflevelId` varchar(255) DEFAULT NULL,
 * `qualificationid_string` varchar(255) DEFAULT NULL, `nqflevelg2Id`
 * varchar(255) DEFAULT NULL, PRIMARY KEY (`id`), KEY `qualificationid`
 * (`qualificationid`), KEY `FKfj0xu2o28s49sgpld3c2ju0ni` (`nqf_level_id`),
 * CONSTRAINT `saqa_qualification_temp_ibfk_1` FOREIGN KEY (`nqf_level_id`)
 * REFERENCES `nqf_levels` (`id`) ) ENGINE=InnoDB AUTO_INCREMENT=4392 DEFAULT
 * CHARSET=latin1;
 * 
 * 
 * CREATE TABLE `saqa_us_qualification_temp` ( `id` bigint(20) NOT NULL
 * AUTO_INCREMENT, `nqfleveldescription` varchar(255) DEFAULT NULL,
 * `nqflevelg2DESCRIPTION` varchar(255) DEFAULT NULL, `qualificationid` int(11)
 * DEFAULT NULL, `unitstandardid` int(11) DEFAULT NULL, `unitstdnumberofcredits`
 * int(11) DEFAULT NULL, `unitstdtitle` varchar(500) DEFAULT NULL,
 * `usqualtypedescription` varchar(100) DEFAULT NULL, `qualification_id`
 * bigint(20) DEFAULT NULL, PRIMARY KEY (`id`), KEY
 * `FK7r1l6n11xmg4uf5ri7pkdta7x` (`qualification_id`), CONSTRAINT
 * `FK921gfqc92cmox3j60m6ndajcn` FOREIGN KEY (`qualification_id`) REFERENCES
 * `saqa_qualification_temp` (`id`), CONSTRAINT
 * `saqa_us_qualification_temp_ibfk_1` FOREIGN KEY (`qualification_id`)
 * REFERENCES `saqa_qualification_temp` (`id`) ) ENGINE=InnoDB
 * AUTO_INCREMENT=6560 DEFAULT CHARSET=latin1;
 * 
 */
