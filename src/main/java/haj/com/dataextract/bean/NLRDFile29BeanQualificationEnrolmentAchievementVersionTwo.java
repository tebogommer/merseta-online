package haj.com.dataextract.bean;

import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

public class NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo implements IDataEntity {

	/**
	 * NLRD Files 
	 * 
	 * Version Two Bean
	 * 
	 * 30 December 2019
	 * 
	 * Enhanced extract added for File 29 NLRD Qualification Enrolment/Achievement
	 * 
	 * @author Arno
	 */

	/*
	 * SUBSTRING('', 1, 15) as nationalId,
	 * SUBSTRING('', 1, 20) as personAlternateId,
	 * SUBSTRING('', 1, 3) as alternativeIdType,
	 * SUBSTRING('', 1, 10) as qualificationId,
	 * SUBSTRING('', 1, 1) as learnerAchievementStatusId,
	 * SUBSTRING('', 1, 20) as assessorRegistrationNumber,
	 * SUBSTRING('', 1, 3) as learnerAchievementTypeId,
	 * '' as learnerAchievementDate,
	 * '' as learnerEnrolledDate,
	 * SUBSTRING('', 1, 3) as honoursClassification,
	 * SUBSTRING('', 1, 2) as partof,
	 * SUBSTRING('', 1, 10) as learnershipId,
	 * SUBSTRING('', 1, 20) as providerCode,
	 * SUBSTRING('', 1, 10) as providerEtqaId,
	 * SUBSTRING('', 1, 10) as assessorEtqaId,
	 * '' as certificationDate,
	 * '' as dateStamp
	 */

	/** File 29 - Qualification Enrolment/Achievement */

	/** National_Id * Required: C */
	@CSVAnnotation(length = 15)
	private String nationalId;

	/** Person_Alternate_Id * Required: C */
	@CSVAnnotation(length = 20)
	private String personAlternateId;

	/** Alternative_Id_Type * Required: Y */
	@CSVAnnotation(length = 3)
	private String alternativeIdType;

	/** Qualification_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String qualificationId;

	/** Learner_Achievement_Status_Id * Required: Y */
	@CSVAnnotation(length = 3)
	private String learnerAchievementStatusId;

	/** Assessor_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String assessorRegistrationNumber;

	/** Learner_Achievement_Type_Id * Required: Y */
	@CSVAnnotation(length = 3)
	private String learnerAchievementTypeId;

	/** Learner_Achievement_Date * Required: C */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date learnerAchievementDate;

	/** Learner_Enrolled_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date learnerEnrolledDate;

	/** Honours_Classification * Required: N */
	@CSVAnnotation(length = 3)
	private String honoursClassification;

	/** Part_of * Required: Y */
	@CSVAnnotation(length = 2)
	private String partof;

	/** Learnership_Id * Required: C */
	@CSVAnnotation(length = 10)
	private String learnershipId;

	/** Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerCode;

	/** Provider_Etqa_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerEtqaId;

	/** Assessor_Etqa_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String assessorEtqaId;

	/** Certification_Date * Required: N */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date certificationDate;

	/** Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;

	public NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo(String nationalId, String personAlternateId, String alternativeIdType, String qualificationId, String learnerAchievementStatusId, String assessorRegistrationNumber, String learnerAchievementTypeId, Date learnerAchievementDate, Date learnerEnrolledDate, String honoursClassification, String partof, String learnershipId, String providerCode, String providerEtqaId, String assessorEtqaId, Date certificationDate, Date dateStamp) {
		super();
		this.nationalId = nationalId;
		this.personAlternateId = personAlternateId;
		this.alternativeIdType = alternativeIdType;
		this.qualificationId = qualificationId;
		this.learnerAchievementStatusId = learnerAchievementStatusId;
		this.assessorRegistrationNumber = assessorRegistrationNumber;
		this.learnerAchievementTypeId = learnerAchievementTypeId;
		this.learnerAchievementDate = learnerAchievementDate;
		this.learnerEnrolledDate = learnerEnrolledDate;
		this.honoursClassification = honoursClassification;
		this.partof = partof;
		this.learnershipId = learnershipId;
		this.providerCode = providerCode;
		this.providerEtqaId = providerEtqaId;
		this.assessorEtqaId = assessorEtqaId;
		this.certificationDate = certificationDate;
		this.dateStamp = dateStamp;
	}

	public NLRDFile29BeanQualificationEnrolmentAchievementVersionTwo() {
		super();
	}

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

	public String getAlternativeIdType() {
		return alternativeIdType;
	}

	public void setAlternativeIdType(String alternativeIdType) {
		this.alternativeIdType = alternativeIdType;
	}

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getLearnerAchievementStatusId() {
		return learnerAchievementStatusId;
	}

	public void setLearnerAchievementStatusId(String learnerAchievementStatusId) {
		this.learnerAchievementStatusId = learnerAchievementStatusId;
	}

	public String getAssessorRegistrationNumber() {
		return assessorRegistrationNumber;
	}

	public void setAssessorRegistrationNumber(String assessorRegistrationNumber) {
		this.assessorRegistrationNumber = assessorRegistrationNumber;
	}

	public String getLearnerAchievementTypeId() {
		return learnerAchievementTypeId;
	}

	public void setLearnerAchievementTypeId(String learnerAchievementTypeId) {
		this.learnerAchievementTypeId = learnerAchievementTypeId;
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

	public String getHonoursClassification() {
		return honoursClassification;
	}

	public void setHonoursClassification(String honoursClassification) {
		this.honoursClassification = honoursClassification;
	}

	public String getPartof() {
		return partof;
	}

	public void setPartof(String partof) {
		this.partof = partof;
	}

	public String getLearnershipId() {
		return learnershipId;
	}

	public void setLearnershipId(String learnershipId) {
		this.learnershipId = learnershipId;
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