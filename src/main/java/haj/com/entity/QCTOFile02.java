package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

// TODO: Auto-generated Javadoc
/**
 * QCTOFile02.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "qcto_file_02")
public class QCTOFile02 implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of QCTOFile02. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

/*	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id", nullable=true)
	private Company company;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderItems> orderItemses = new HashSet<OrderItems>(0);
	*/
	
	@Column(name = "assessment_partner_code")
	private String  assessmentPartnerCode;
	
	@Column(name = "enrolled_sdp_code")
	private String enrolledSDPCode;
	
	@Column(name = "assessment_centre_code")
	private String  assessmentCentreCode;
	
	@Column(name = "national_id")
	private String nationalId;
	
	@Column(name = "learner_alternate_id")
	private String learnerAlternateID;
	
	@Column(name = "qualification_id")
	private String qualificationId;
	
	@Column(name = "learner_readiness_for_eisa_type_id")
	private String learnerReadinessForEISATypeId;
	
	@Column(name = "module_code")
	private String moduleCode;
	
	@Column(name = "module_achievement_status")
	private String moduleAchievementStatus;
	
	@Column(name = "employment_status")
	private String employmentStatus;
	
	@Column(name = "learner_modular_achievement_type_id")
	private String learnerModularAchievementTypeID;
	
	@Column(name = "learner_modular_achievement_date")
	private Date learnermodularAchievementDate;
	
	@Column(name = "learner_enrolled_date")
	private Date learnerEnrolledDate;
	
	@Column(name = "expected_training_completion_date")
	private Date expectedTrainingCompletionDate;
	
	@Column(name = "linked_to_workplace_point_of_entry_qualification")
	private String linkedToWorkplacePointOfEntryQualification;
	
	@Column(name = "qualification_entry_requirement_status")
	private String qualificationEntryRequirementStatus;
	
	@Column(name = "flc")
	private String flc;
	
	@Column(name = "flc_statement_of_result_number")
	private String flcStatementOfResultNumber;
	
	@Column(name = "statement_of_result_status")
	private String statementOfResultStatus;
	
	@Column(name = "statement_of_result_issue_date")
	private Date statmentOfResultIssueDate;
	
	@Column(name = "date_stamp")
	private Date dateStamp;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QCTOFile02 other = (QCTOFile02) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	public String getFlc() {
		return flc;
	}

	public void setFlc(String flc) {
		this.flc = flc;
	}

	public String getFlcStatementOfResultNumber() {
		return flcStatementOfResultNumber;
	}

	public void setFlcStatementOfResultNumber(String flcStatementOfResultNumber) {
		this.flcStatementOfResultNumber = flcStatementOfResultNumber;
	}

	public String getStatementOfResultStatus() {
		return statementOfResultStatus;
	}

	public void setStatementOfResultStatus(String statementOfResultStatus) {
		this.statementOfResultStatus = statementOfResultStatus;
	}

	public Date getStatmentOfResultIssueDate() {
		return statmentOfResultIssueDate;
	}

	public void setStatmentOfResultIssueDate(Date statmentOfResultIssueDate) {
		this.statmentOfResultIssueDate = statmentOfResultIssueDate;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public Date getLearnermodularAchievementDate() {
		return learnermodularAchievementDate;
	}

	public void setLearnermodularAchievementDate(Date learnermodularAchievementDate) {
		this.learnermodularAchievementDate = learnermodularAchievementDate;
	}
}
