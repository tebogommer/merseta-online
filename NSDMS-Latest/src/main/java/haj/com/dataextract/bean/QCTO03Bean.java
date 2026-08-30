package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class QCTO03Bean  implements IDataEntity {

	/**
	 * Assessment Partner Code 
	 * Required: True
	 * ASSESSMENT PARTNER code refers to the unique ASSESSMENT PARTNER Accreditation number with the QCTO
	 * The first character of the data value must be a valid, non-space character    
	 */
	 @CSVAnnotation(length = 20)
     private String assessmentPartnerCode;
	 
	 /**
		 * Assessment Centre Code  
		 * Required: True
		 * Assessment Centre Code refers to the accreditation number given to the assessment centre where the learner is assessed.
		 * The first character of the data value must be a valid, non-space character    
		 */
	 @CSVAnnotation(length = 20)
     private String assessmentCentreCode;

	 /**
		 * National ID  
		 * Required: Conditional
		 * Either this field or the Learner Alternate ID field must contain a value. 
		 * Valid ID Number    
		 */
	 @CSVAnnotation(length=15)
     private String nationalId;

	 /**
		 * Learner Alternate Id   
		 * Required: Conditional
		 * Either this field or the Learner Alternate ID field must contain a value. 
		 * Valid ID Number    
		 */
	 @CSVAnnotation(length=20)
     private String learnerAlternateID;

	 /**
		 * Qualification Id   
		 * Required: Yes
		 * The first character of the data value must be a valid, non-space character 
		 * The value given must:  
		 * Comply with the actual Qualification-ID format for which the SDP and Assessment Centre is accredited on the QCTO MIS system. 
		 * Must be active, registered Occupational Qualification registered on the OQSF of the NQF
		 */
	 @CSVAnnotation(length=20)
     private String qualificationId;
	 
	 /**
		 * EISA COMPONENT NUMBER    
		 * Required: Yes
		 * The first character of the data value must be a valid, non-space character 
		 * EISA component number must correlate with QAS blueprint (addendum)
		 * For qualifications that are assessed through as trade test the tasks number should be stated
		 */
	 @CSVAnnotation(length = 3)
     private String eISAComponentNumber;
	 
	 /**
		 * EISA COMPONENT ACHIEVEMENT VALUE   
		 * Required: Yes
		 * Allowed Codes to be used when EISA mark is not available
		 * 777 – Absent
		 * 555 – Competent
		 * 999 – Not yet competent

		 * The first character of the data value must be a valid, non-space character 
		 * The mark may not exceed the total paper mark 
		 * Where the actual mark is not indicated in the assessment then a code of 555 will be used to 	indicate competence and 999 will be used to indicate not yet competent 
		 * The first character of the data value must be a valid, non-space character  
		 * Code 777 must be used for absenteeism
		 * The data value may only contain characters that exist in the following range of characters: 1234567890
		 * Example would be 50% obtained be listed as 50
		 */
	 @CSVAnnotation(length = 3)
     private String eISAComponentAchievementValue;
	 
	 /**
		 * Date Assessed   
		 * Required: Yes
		 */
	 @CSVAnnotation(length=8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
     private Date dateAssessed;

	 /**
		 * EISA Percentage obtained   
		 * Required: Yes
		 * Allowed Codes to be used when EISA Percentage is not available
		 * 777 – Absent 
		 * 555 – Competent
		 * 999 – Not yet competent
		 * 222 – Absent for all components
		 * 
		 * Calculate final % as per weighting of EISA components as indicated in QAS Blueprint 	(Addendum)
		 * If absent in any component, the final mark is indicated as code 777
		 * If absent in all components the final mark is indicated as code 222 
		 * The data value may only contain characters that exist in the following range of characters: 1234567890

		 * Example would be 50% obtained to be listed as 50
		 */
	 @CSVAnnotation(length=3)
     private String eISAPercentageObtained;

	 /**
	  * Date Stamp 
	  * Required: True
	  */
	 @CSVAnnotation(length = 8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
     private Date dateStamp;
	 
	 
	 
	 
	 public QCTO03Bean(String assessmentPartnerCode, String assessmentCentreCode, String nationalId, String learnerAlternateID, String qualificationId,String eISAComponentNumber
			 , String eISAComponentAchievementValue, Date dateAssessed, String eISAPercentageObtained,Date dateStamp) {
			super();
			this.assessmentPartnerCode = assessmentPartnerCode;
			this.assessmentCentreCode = assessmentCentreCode;
			this.nationalId = nationalId;
			this.learnerAlternateID = learnerAlternateID;
			this.qualificationId = qualificationId;
			this.eISAComponentNumber = eISAComponentNumber;
			this.eISAComponentAchievementValue = eISAComponentAchievementValue;
			this.dateAssessed = dateAssessed;
			this.eISAComponentNumber = eISAPercentageObtained;
			this.dateStamp = dateStamp;
		}

		public QCTO03Bean() {
			super();
			// TODO Auto-generated constructor stub
		}
	 



	public String getAssessmentPartnerCode() {
		return assessmentPartnerCode;
	}

	public void setAssessmentPartnerCode(String assessmentPartnerCode) {
		this.assessmentPartnerCode = assessmentPartnerCode;
	}

	public String getAssessmentCentreCode() {
		return assessmentCentreCode;
	}

	public void setAssessmentCentreCode(String assessmentCentreCode) {
		this.assessmentCentreCode = assessmentCentreCode;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getLearnerAlternateID() {
		return learnerAlternateID;
	}

	public void setLearnerAlternateID(String learnerAlternateID) {
		this.learnerAlternateID = learnerAlternateID;
	}

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String geteISAComponentNumber() {
		return eISAComponentNumber;
	}

	public void seteISAComponentNumber(String eISAComponentNumber) {
		this.eISAComponentNumber = eISAComponentNumber;
	}

	public String geteISAComponentAchievementValue() {
		return eISAComponentAchievementValue;
	}

	public void seteISAComponentAchievementValue(String eISAComponentAchievementValue) {
		this.eISAComponentAchievementValue = eISAComponentAchievementValue;
	}



	public String geteISAPercentageObtained() {
		return eISAPercentageObtained;
	}

	public void seteISAPercentageObtained(String eISAPercentageObtained) {
		this.eISAPercentageObtained = eISAPercentageObtained;
	}

	public Date getDateAssessed() {
		return dateAssessed;
	}

	public void setDateAssessed(Date dateAssessed) {
		this.dateAssessed = dateAssessed;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}




}


