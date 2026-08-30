package haj.com.dataextract.bean;



import java.math.BigInteger;
import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class NLRDFile28Bean  implements IDataEntity {
	
	
	/** 
	 * NLRD Files spec as at 18 May 2018
	 */
	
	/** 
	 * File 28 - Learnership Enrolment/Achievement    
	 */
	
	/**	
	 * National_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 15)
	private String nationalId;
	 
	/**	
	 * Person_Alternate_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 20)
	private String personAlternateId;
	 
	/**	
	 * Alternative_Id_Type	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 3)
	private BigInteger alternativeIdType;
	 
	/**	
	 * Learnership_Id	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private String learnershipId;
	 
	/**	
	 * Learner_Achievement_Status_Id	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 3)
	private BigInteger learnerAchievementStatusId;
	 
	/**	
	 * Assessor_Registration_Number	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 20)
	private String assessorRegistrationNumber;
	 
	/**	
	 * Learner_Achievement_Date	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date learnerAchievementDate;
	 
	/**	
	 * Learner_Enrolled_Date	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date learnerEnrolledDate;
	 
	/**	
	 * Provider_Code	
	 * Required:	Y	
	 */	
	@CSVAnnotation(length = 20)
	private String providerCode;
	 
	/**	
	 * Provider_Etqa_Id	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private String providerEtqaId;
	 
	/**	
	 * Assessor_Etqa_Id	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private String assessorEtqaId;

	/**	
	 * Certification_Date	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date certificationDate;
	 
	/**	
	 * Date_Stamp	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;


		public String getNationalId() {
			return nationalId;
		}

		public void setNationalId(String nationalId) {
			this.nationalId = nationalId;
		}

		public String getPersonAlternateId() {
			return personAlternateId;
		}

		public void setPersonAlternateId(String personAlternateId) {
			this.personAlternateId = personAlternateId;
		}

		public BigInteger getAlternativeIdType() {
			return alternativeIdType;
		}

		public void setAlternativeIdType(BigInteger alternativeIdType) {
			this.alternativeIdType = alternativeIdType;
		}

		public String getLearnershipId() {
			return learnershipId;
		}

		public void setLearnershipId(String learnershipId) {
			this.learnershipId = learnershipId;
		}

		public BigInteger getLearnerAchievementStatusId() {
			return learnerAchievementStatusId;
		}

		public void setLearnerAchievementStatusId(BigInteger learnerAchievementStatusId) {
			this.learnerAchievementStatusId = learnerAchievementStatusId;
		}

		public String getAssessorRegistrationNumber() {
			return assessorRegistrationNumber;
		}

		public void setAssessorRegistrationNumber(String assessorRegistrationNumber) {
			this.assessorRegistrationNumber = assessorRegistrationNumber;
		}

		public Date getLearnerAchievementDate() {
			return learnerAchievementDate;
		}

		public void setLearnerAchievementDate(Date learnerAchievementDate) {
			this.learnerAchievementDate = learnerAchievementDate;
		}

		public Date getLearnerEnrolledDate() {
			return learnerEnrolledDate;
		}

		public void setLearnerEnrolledDate(Date learnerEnrolledDate) {
			this.learnerEnrolledDate = learnerEnrolledDate;
		}

		public String getProviderCode() {
			return providerCode;
		}

		public void setProviderCode(String providerCode) {
			this.providerCode = providerCode;
		}

		public String getProviderEtqaId() {
			return providerEtqaId;
		}

		public void setProviderEtqaId(String providerEtqaId) {
			this.providerEtqaId = providerEtqaId;
		}

		public String getAssessorEtqaId() {
			return assessorEtqaId;
		}

		public void setAssessorEtqaId(String assessorEtqaId) {
			this.assessorEtqaId = assessorEtqaId;
		}

		public Date getCertificationDate() {
			return certificationDate;
		}

		public void setCertificationDate(Date certificationDate) {
			this.certificationDate = certificationDate;
		}

		public Date getDateStamp() {
			return dateStamp;
		}

		public void setDateStamp(Date dateStamp) {
			this.dateStamp = dateStamp;
		}

		
}


