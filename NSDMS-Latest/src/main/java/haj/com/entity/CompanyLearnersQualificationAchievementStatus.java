package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.ancientprogramming.fixedformat4j.annotation.Field;
import com.ancientprogramming.fixedformat4j.annotation.Record;

import haj.com.framework.IDataEntity;

@Entity
@Table(name = "cl_qualification_achievement")
@AuditTable(value = "cl_qualification_achievement_hist")
@Audited
@Record
public class CompanyLearnersQualificationAchievementStatus implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of CompanyUsers. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "administration_of_aqp_learners_id", nullable = true)
	private AdministrationOfAQPLearners administrationOfAQPLearners;

	// Assessment Partner Code TEXT 20
	@Column(name = "assessment_partner_code", length = 20)
	private String assessmentPartnerCode;

	// Enrolled SDP Code TEXT 20
	@Column(name = "enrolled_sdp_code", length = 20)
	private String enrolledSDPCode;

	// Assessment Centre Code TEXT 20
	@Column(name = "assessment_centre_code", length = 20)
	private String assessmentCentreCode;

	// National Id TEXT 15
	@Column(name = "national_id", length = 15)
	private String nationalID;

	// Learner Alternate Id TEXT 20
	@Column(name = "learner_alternate_id", length = 20)
	private String learnerAlternateId;

	// Qualification Id TEXT 20
	@Column(name = "qualification_id", length = 20)
	private String qualificationId;

	// Learner Readiness for EISA Type Id TEXT 3
	@Column(name = "learner_readiness_for_eisa_type_id", length = 3)
	private String learnerReadinessforEISATypeId;

	// Module Code TEXT 20
	@Column(name = "module_code", length = 20)
	private String moduleCode;

	// Module Achievement Status TEXT 3
	@Column(name = "module_achievement_status", length = 3)
	private String moduleAchievementStatus;

	// Employment Status TEXT 3
	@Column(name = "employment_status", length = 3)
	private String employmentStatus;

	// Learner Modular Achievement Type Id TEXT 3
	@Column(name = "learner_modular_achievement_type_id", length = 3)
	private String learnerModularAchievementTypeId;

	// Learner Modular Achievement Date DATE 8
	@Column(name = "learner_modular_achievement_date", length = 8)
	private String learnerModularAchievementDate;

	// Learner Enrolled Date DATE 8
	@Column(name = "learner_enrolled_date", length = 8)
	private String learnerEnrolledDate;

	// Expected Training Completion Date TEXT 8
	@Column(name = "expected_training_completion_date", length = 8)
	private String expectedTrainingCompletionDate;

	// Linked to a workplace at point of entry to the qualification TEXT 2
	@Column(name = "linked_to_workplace", length = 2)
	private String linkedToWorkplace;

	// Qualification entry requirement status TEXT 3
	@Column(name = "qualification_entry_requirement", length = 3)
	private String qualificationEntryRequirement;

	// FLC TEXT 3
	@Column(name = "flc", length = 3)
	private String flc;

	// FLC Statement of result number TEXT 15
	@Column(name = "flc_statement_result_number", length = 15)
	private String flcStatementResultNumber;

	// Statement of Results Status TEXT 3
	@Column(name = "statement_results_status", length = 3)
	private String statementResultsStatus;

	// Statement of Results Issue Date TEXT 8
	@Column(name = "statement_results_issue_date", length = 8)
	private String statementResultsIssueDate;

	// Date Stamp DATE 8
	@Column(name = "current_date_stamp", length = 8)
	private String dateStamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Field(offset = 1, length = 20)
	public String getAssessmentPartnerCode() {
		return assessmentPartnerCode;
	}

	public void setAssessmentPartnerCode(String assessmentPartnerCode) {
		this.assessmentPartnerCode = assessmentPartnerCode;
	}

	@Field(offset = 21, length = 20)
	public String getEnrolledSDPCode() {
		return enrolledSDPCode;
	}

	public void setEnrolledSDPCode(String enrolledSDPCode) {
		this.enrolledSDPCode = enrolledSDPCode;
	}

	@Field(offset = 41, length = 20)
	public String getAssessmentCentreCode() {
		return assessmentCentreCode;
	}

	public void setAssessmentCentreCode(String assessmentCentreCode) {
		this.assessmentCentreCode = assessmentCentreCode;
	}

	@Field(offset = 61, length = 15)
	public String getNationalID() {
		return nationalID;
	}

	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}

	@Field(offset = 76, length = 20)
	public String getLearnerAlternateId() {
		return learnerAlternateId;
	}

	public void setLearnerAlternateId(String learnerAlternateId) {
		this.learnerAlternateId = learnerAlternateId;
	}

	@Field(offset = 96, length = 20)
	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	@Field(offset = 116, length = 3)
	public String getLearnerReadinessforEISATypeId() {
		return learnerReadinessforEISATypeId;
	}

	public void setLearnerReadinessforEISATypeId(String learnerReadinessforEISATypeId) {
		this.learnerReadinessforEISATypeId = learnerReadinessforEISATypeId;
	}

	@Field(offset = 119, length = 20)
	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	@Field(offset = 139, length = 3)
	public String getModuleAchievementStatus() {
		return moduleAchievementStatus;
	}

	public void setModuleAchievementStatus(String moduleAchievementStatus) {
		this.moduleAchievementStatus = moduleAchievementStatus;
	}

	@Field(offset = 142, length = 3)
	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	@Field(offset = 145, length = 3)
	public String getLearnerModularAchievementTypeId() {
		return learnerModularAchievementTypeId;
	}

	public void setLearnerModularAchievementTypeId(String learnerModularAchievementTypeId) {
		this.learnerModularAchievementTypeId = learnerModularAchievementTypeId;
	}

	@Field(offset = 148, length = 8)
	public String getLearnerModularAchievementDate() {
		return learnerModularAchievementDate;
	}

	public void setLearnerModularAchievementDate(String learnerModularAchievementDate) {
		this.learnerModularAchievementDate = learnerModularAchievementDate;
	}

	@Field(offset = 156, length = 8)
	public String getLearnerEnrolledDate() {
		return learnerEnrolledDate;
	}

	public void setLearnerEnrolledDate(String learnerEnrolledDate) {
		this.learnerEnrolledDate = learnerEnrolledDate;
	}

	@Field(offset = 164, length = 8)
	public String getExpectedTrainingCompletionDate() {
		return expectedTrainingCompletionDate;
	}

	public void setExpectedTrainingCompletionDate(String expectedTrainingCompletionDate) {
		this.expectedTrainingCompletionDate = expectedTrainingCompletionDate;
	}

	@Field(offset = 172, length = 2)
	public String getLinkedToWorkplace() {
		return linkedToWorkplace;
	}

	public void setLinkedToWorkplace(String linkedToWorkplace) {
		this.linkedToWorkplace = linkedToWorkplace;
	}

	@Field(offset = 174, length = 3)
	public String getQualificationEntryRequirement() {
		return qualificationEntryRequirement;
	}

	public void setQualificationEntryRequirement(String qualificationEntryRequirement) {
		this.qualificationEntryRequirement = qualificationEntryRequirement;
	}

	@Field(offset = 177, length = 3)
	public String getFlc() {
		return flc;
	}

	public void setFlc(String flc) {
		this.flc = flc;
	}

	@Field(offset = 180, length = 15)
	public String getFlcStatementResultNumber() {
		return flcStatementResultNumber;
	}

	public void setFlcStatementResultNumber(String flcStatementResultNumber) {
		this.flcStatementResultNumber = flcStatementResultNumber;
	}

	@Field(offset = 195, length = 3)
	public String getStatementResultsStatus() {
		return statementResultsStatus;
	}

	public void setStatementResultsStatus(String statementResultsStatus) {
		this.statementResultsStatus = statementResultsStatus;
	}

	@Field(offset = 198, length = 8)
	public String getStatementResultsIssueDate() {
		return statementResultsIssueDate;
	}

	public void setStatementResultsIssueDate(String statementResultsIssueDate) {
		this.statementResultsIssueDate = statementResultsIssueDate;
	}

	@Field(offset = 206, length = 8)
	public String getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(String dateStamp) {
		this.dateStamp = dateStamp;
	}

	/**
	 * @return the administrationOfAQPLearners
	 */
	public AdministrationOfAQPLearners getAdministrationOfAQPLearners() {
		return administrationOfAQPLearners;
	}

	/**
	 * @param administrationOfAQPLearners
	 *            the administrationOfAQPLearners to set
	 */
	public void setAdministrationOfAQPLearners(AdministrationOfAQPLearners administrationOfAQPLearners) {
		this.administrationOfAQPLearners = administrationOfAQPLearners;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CompanyLearnersQualificationAchievementStatus other = (CompanyLearnersQualificationAchievementStatus) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}
