package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class QCTO02Bean  implements IDataEntity {


	/**
	 * Assessment Partner Code 
	 * Required: True
	 * ASSESSMENT PARTNER code refers to the unique ASSESSMENT PARTNER Accreditation number with the QCTO
	 * The first character of the data value must be a valid, non-space character    
	 */
	@CSVAnnotation(length = 20)
     private String  assessmentPartnerCode;
	
	/**
	 * Enrolled SDP Code 
	 * Required: True
	 * ASSESSMENT PARTNER code refers to the unique ASSESSMENT PARTNER Accreditation number with the QCTO
	 * The first character of the data value must be a valid, non-space character    
	 */
	@CSVAnnotation(length = 20)
     private String enrolledSDPCode;
	
	/**
	 * Assessment Centre Code  
	 * Required: True
	 * Assessment Centre Code refers to the accreditation number given to the assessment centre where the learner is assessed.
	 * The first character of the data value must be a valid, non-space character    
	 */
	@CSVAnnotation(length = 20)
	private String  assessmentCentreCode;
 
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
	* Learner Readiness for EISA Type Id   
 	* Required: Yes
 	* 1 = Enrolled
 	* 2 = RPL for Access to EISA
 	* 3 = Mixed Mode to EISA
 	* 4 = SDP Training and assessment for readiness to EISA
 	* 5 = SDP e-learning training and assessment for readiness to EISA
 	* 
 	*/
	@CSVAnnotation(length=3)
	private String learnerReadinessForEISATypeId;

	/**
	* Module Code  
 	* Required: Yes
 	* As per curriculum document, the first character of the data value must be a valid, non-space character 
 	*/
	@CSVAnnotation(length=20)
	private String moduleCode;
	
	/**
	* Module Achievement Status 
 	* Required: Yes
 	* Allowed Codes
 	* 01 = Enrolled
 	* 02 = Competent/Achieved
 	* 03 = Not yet competent/not yet achieved
 	* 04 = Withdrawn
 	*/
	@CSVAnnotation(length=3)
	private String moduleAchievementStatus;
	
	/**
	* Employment Status 
	* Required: Yes
 	* Allowed Codes
 	* 01 = Employed
 	* 02 = Not employed
 	* 03 = Other
 	*/
	@CSVAnnotation(length=3)
	private String employmentStatus;
	
	/**
	* Learner Modular Achievement Type ID 
	* Required: Yes
 	* Allowed Codes
 	* 01 = Formative Assessment 
 	* 02 = RPL
 	*/
	@CSVAnnotation(length=3)
	private String learnerModularAchievementTypeID;
	
	/**
	* Learner Modular Achievement Date  
	* Required: Conditional
 	*
 	*/
	@CSVAnnotation(length=8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
	private Date learnerModularAchievementDate;
	
	/**
	* Learner Enrolled Date   
	* Required: Yes
 	*/
	@CSVAnnotation(length=8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
	private Date learnerEnrolledDate;
	
	/**
	* Expected Training Completion Date   
	* Required: Yes
 	*/
	@CSVAnnotation(length=8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
	private Date expectedTrainingCompletionDate;
	
	/**
	* Linked to a workplace at point of entry to the qualification
	* Required: Yes
 	* Allowed Codes
 	* Y = Yes
 	* N = No 
 	*/
	@CSVAnnotation(length=2)
	private String linkedToWorkplacePointOfEntryQualification;
	
	/**
	* Qualification entry requirement status
	* Required: Yes
 	* Allowed Codes
 	* 01 = Meet entry requirements as stipulated in the qualifications document
 	* 02 = RPL – Skills and experience (no formal qualification)
 	* 03 = RPL with qualification at lower level than required
 	* 04 = Other
 	*/
	@CSVAnnotation(length=3)
	private String qualificationEntryRequirementStatus;
	
	/**
	* FLC
	* Required: Yes
 	* Allowed Codes
 	* 01 = FLC certificate (competent)
 	* 02 = RPL 
 	* 03 = Grade 12/NCV –Level 4 Mathematics(ML)/English with Mathematics/Mathematical Literacy, English HL/FAL or First or Second Language pass
 	* 04 = Not yet competent
 	* 05 = FLC not completed yet
 	* 06 = Not applicable (qualification on NQF 5 and above)
 	* 07 = Enrolled for FLC
 	* 08 = N3 Mathematics and Business Language
 	*/
	@CSVAnnotation(length=3)
	private String FLC;
	
	/**
	* FLC Statement of result number
	* Required: Yes
	* The following may be supplied
	* NSC/SC or NCV Certificate number or N3 Certificate Number
	* FLC Statement of Results Number
	* RPL – provide code RPLYYYYMMDD
	* (Where RPL is done to indicate recognition for the FLC requirements, then the number supplied should be RPL followed by the date of RPL assessment)
 	*/
	@CSVAnnotation(length=15)
	private String fLCSORNumber;
	
	/**
	* STATEMENT OF RESULTS STATUS
	* Required: Yes
 	* Allowed Codes
 	* 01 = Statement of Results issued
 	* 02 = Statement of Results not yet issued
 	*/
	@CSVAnnotation(length=3)
	private String sorSTATUS;
	
	/**
	* STATEMENT OF RESULTS ISSUE DATE   
	* Required: Yes
 	*/
	@CSVAnnotation(length=8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
	private Date sorIssueDate;
	
	/**
	  * Date Stamp 
	  * Required: True
	  */
	 @CSVAnnotation(length = 8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
    private Date dateStamp;
	 
	 
	 
	 
	 public QCTO02Bean(String assessmentPartnerCode, String enrolledSDPCode, String assessmentCentreCode, String nationalId, String learnerAlternateID, String qualificationId,String learnerReadinessForEISATypeId
			 , String moduleCode, String moduleAchievementStatus, String employmentStatus, String learnerModularAchievementTypeID, Date learnerModularAchievementDate, Date learnerEnrolledDate, Date expectedTrainingCompletionDate
			 , String linkedToWorkplacePointOfEntryQualification, String qualificationEntryRequirementStatus, String FLC , String fLCSORNumber , String sorSTATUS , Date sorIssueDate , Date dateStamp) {
			super();
			this.assessmentPartnerCode = assessmentPartnerCode;
			this.enrolledSDPCode = enrolledSDPCode;
			this.assessmentCentreCode = assessmentCentreCode;
			this.nationalId = nationalId;
			this.learnerAlternateID = learnerAlternateID;
			this.qualificationId = qualificationId;
			this.learnerReadinessForEISATypeId = learnerReadinessForEISATypeId;
			this.moduleCode = moduleCode;
			this.moduleAchievementStatus = moduleAchievementStatus;
			this.employmentStatus = employmentStatus;
			this.learnerModularAchievementTypeID = learnerModularAchievementTypeID;
			this.learnerModularAchievementDate = learnerModularAchievementDate;
			this.learnerEnrolledDate = learnerEnrolledDate;
			this.expectedTrainingCompletionDate = expectedTrainingCompletionDate;
			this.linkedToWorkplacePointOfEntryQualification = linkedToWorkplacePointOfEntryQualification;
			this.qualificationEntryRequirementStatus = qualificationEntryRequirementStatus;
			this.FLC  = FLC;
			this.fLCSORNumber  = fLCSORNumber;
			this.sorSTATUS  = sorSTATUS;
			this.sorIssueDate  = sorIssueDate;
			this.dateStamp = dateStamp;
		}

		public QCTO02Bean() {
			super();
			// TODO Auto-generated constructor stub
		}
	 

	public String getAssessmentPartnerCode() {
		return assessmentPartnerCode;
	}

	public void setAssessmentPartnerCode(String assessmentPartnerCode) {
		this.assessmentPartnerCode = assessmentPartnerCode;
	}

	public String getEnrolledSDPCode() {
		return enrolledSDPCode;
	}

	public void setEnrolledSDPCode(String enrolledSDPCode) {
		this.enrolledSDPCode = enrolledSDPCode;
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

	public String getLearnerReadinessForEISATypeId() {
		return learnerReadinessForEISATypeId;
	}

	public void setLearnerReadinessForEISATypeId(String learnerReadinessForEISATypeId) {
		this.learnerReadinessForEISATypeId = learnerReadinessForEISATypeId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleAchievementStatus() {
		return moduleAchievementStatus;
	}

	public void setModuleAchievementStatus(String moduleAchievementStatus) {
		this.moduleAchievementStatus = moduleAchievementStatus;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getLearnerModularAchievementTypeID() {
		return learnerModularAchievementTypeID;
	}

	public void setLearnerModularAchievementTypeID(String learnerModularAchievementTypeID) {
		this.learnerModularAchievementTypeID = learnerModularAchievementTypeID;
	}

	public Date getLearnerModularAchievementDate() {
		return learnerModularAchievementDate;
	}

	public void setLearnerModularAchievementDate(Date learnerModularAchievementDate) {
		this.learnerModularAchievementDate = learnerModularAchievementDate;
	}

	public Date getLearnerEnrolledDate() {
		return learnerEnrolledDate;
	}

	public void setLearnerEnrolledDate(Date learnerEnrolledDate) {
		this.learnerEnrolledDate = learnerEnrolledDate;
	}

	public Date getExpectedTrainingCompletionDate() {
		return expectedTrainingCompletionDate;
	}

	public void setExpectedTrainingCompletionDate(Date expectedTrainingCompletionDate) {
		this.expectedTrainingCompletionDate = expectedTrainingCompletionDate;
	}

	public String getLinkedToWorkplacePointOfEntryQualification() {
		return linkedToWorkplacePointOfEntryQualification;
	}

	public void setLinkedToWorkplacePointOfEntryQualification(String linkedToWorkplacePointOfEntryQualification) {
		this.linkedToWorkplacePointOfEntryQualification = linkedToWorkplacePointOfEntryQualification;
	}

	public String getQualificationEntryRequirementStatus() {
		return qualificationEntryRequirementStatus;
	}

	public void setQualificationEntryRequirementStatus(String qualificationEntryRequirementStatus) {
		this.qualificationEntryRequirementStatus = qualificationEntryRequirementStatus;
	}

	public String getFLC() {
		return FLC;
	}

	public void setFLC(String fLC) {
		FLC = fLC;
	}

	public String getfLCSORNumber() {
		return fLCSORNumber;
	}

	public void setfLCSORNumber(String fLCSORNumber) {
		this.fLCSORNumber = fLCSORNumber;
	}

	public String getSorSTATUS() {
		return sorSTATUS;
	}

	public void setSorSTATUS(String sorSTATUS) {
		this.sorSTATUS = sorSTATUS;
	}

	public Date getSorIssueDate() {
		return sorIssueDate;
	}

	public void setSorIssueDate(Date sorIssueDate) {
		this.sorIssueDate = sorIssueDate;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}





}


