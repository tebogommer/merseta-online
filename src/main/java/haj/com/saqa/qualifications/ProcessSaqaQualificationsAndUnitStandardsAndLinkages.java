package haj.com.saqa.qualifications;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ProcessSaqaQualificationsAndUnitStandardsAndLinkages {

	private static final String FILE_NAME_QUALIFICATION = "QUALIFICATION.xml";
	private static final String FILE_NAME_UNIT_STANDARDS = "UNITSTANDARD.xml";
	
	private String pathPrefix = "";
	
	private File unitStandardsXML = null;
	private File qualificationsXML = null;
	
	private InputStream targetInputStreamUnitStandards = null;
	private InputStream targetInputStreamQualifications = null;
	
	public ProcessSaqaQualificationsAndUnitStandardsAndLinkages() {
		
		try {
			pathPrefix = "D:/MersetaFiles/";
			
			unitStandardsXML = new File(pathPrefix + FILE_NAME_UNIT_STANDARDS);
			qualificationsXML = new File(pathPrefix + FILE_NAME_QUALIFICATION);
			
			targetInputStreamUnitStandards = new FileInputStream(unitStandardsXML);
			targetInputStreamQualifications = new FileInputStream(qualificationsXML);
			
			LoadQualification.loadAndProcessUnitStandards(targetInputStreamUnitStandards);
			LoadQualification.loadAndProcessQualifications(targetInputStreamQualifications);
			
		} catch (Exception exception){
			
			System.out.println("****************************");
			System.out.println("****************************" + exception.getMessage());
			System.out.println("****************************");
			
			exception.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		new ProcessSaqaQualificationsAndUnitStandardsAndLinkages();
	}

}
