package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AccreditationStatus;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.entity.lookup.ProviderClass;
import haj.com.entity.lookup.ProviderStatus;
import haj.com.entity.lookup.ProviderType;
import haj.com.entity.lookup.RelationshipToCompany;
import haj.com.entity.lookup.ScopeOfResponsibility;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.ExportValidation;
import haj.com.validators.exports.SETMISFieldValidation;

// TODO: Auto-generated Javadoc

/**
 *
 * @author Techfinium
 */
@Entity
@Table(name = "training_provider_application")
@AuditTable(value = "training_provider_application_hist")
@Audited
@ExportValidation(message = "Invalid Training Provider Profile")
public class TrainingProviderApplication implements IDataEntity, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** company of SDP. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@SETMISFieldValidation(process = true , fieldName = "company", fieldValue = "NOT_NULL")
	private Company company;
	
	/** company of SDP. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_seta_company_id", nullable = true)
	private NonSetaCompany nonSetaCompany;

	/** The SDF. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = true)
	@SETMISFieldValidation(process = true , fieldName = "users", fieldValue = "NOT_NULL")
	private Users users;

	/** The Approval status. */
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;
	
	@Enumerated
	@Column(name = "previous_status")
	private ApprovalEnum previousStatus;
	
	@Enumerated
	@Column(name = "status_before_suspension")
	private ApprovalEnum statusBeforeSuspension;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_class", nullable = true)
	private ProviderClass providerClass;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accreditation_status", nullable = true)
	private AccreditationStatus accreditationStatus;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_type", nullable = true)
	private ProviderType providerType;
	
	/*
	 * When to set:
	 * 
	 * When final approval = 510
	 * De-accreditation process final approval = 512
	 * 511 = Re-reg process
	 * 515 = final rejection
	 */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_status_id", nullable = true)
	private ProviderStatus providerStatus;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "etqa_id", nullable = true)
	private Etqa etqa;

	@Column(name = "accreditation_number", length = 100, nullable = true)
	private String accreditationNumber;

	@Column(name = "accreditation_period", length = 100, nullable = true)
	private String accreditationPeriod;

	/**Accreditation End Date*/
	@Column(name = "expiriy_date", nullable = true)
	private Date expiriyDate;

	@Column(name = "start_date", nullable = true)
	private Date startDate;

	@Column(name = "site_visit_date", nullable = true)
	private Date siteVisitDate;

	@Column(name = "site_visit_report_date", nullable = true)
	private Date siteVisitReportDate;

	@Enumerated
	@Column(name = "accreditation_application_type")
	private AccreditationApplicationTypeEnum accreditationApplicationTypeEnum;

	@Column(name = "training_assessment", columnDefinition = "BIT default false")
	private Boolean trainingAssessment;

	@Column(name = "assessment_only", columnDefinition = "BIT default false")
	private Boolean assessmentOnly;
	
	/**Accreditation or certificate Number*/
	@Column(name="certificate_number")
	private String certificateNumber;
	
	/** The approved date. */
	@Column(name = "approved_date", length = 19)
	private Date approvedDate;
	
	/** The ETQA Review Committee Date. */
	@CreationTimestamp
	@Column(name = "etqa_eview_committee_date", length = 19)
	private Date etqaReviewCommitteeDate;
	
	/**skills programme route will be 
	 * used for obtaining the unit standards?*/
	@Enumerated
	@Column(name = "use_skill_programme_route")
	private YesNoEnum useSkillProgrammeRoute ;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id ", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsProgram skillsProgram;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_set_id ", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsSet skillsSet;

	@Column(name="code_of_conduct_accepted")
	private Boolean codeOfConductAccepted;
	
	@CreationTimestamp
	@Column(name="code_of_conduct_accept_date")
	private Date codeOfConductAcceptDate;
	
	/** The ETQA Review Committee Schedule. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name="review_committee_meeting_id")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
	
	/** Flag to check if facilitator, Assessor or Moderetor is available**/
	@Column(name = "facilitator_assessor_mod_available", columnDefinition = "BIT default false")
	private Boolean facilitatorAssessorModAvailable;
	
	@Column(name = "site_visit_comment")
	private String siteVisitComment;
	
	/**Quality Assuror*/
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quality_assurance_user_id", nullable = true)
	@SETMISFieldValidation(process = true , fieldName = "qualityAssuranceUser", fieldValue = "NOT_NULL")
	private Users qualityAssuranceUser;
	
	/**Manager: Quality Assurance*/
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_qa_user_id", nullable = true)
	@SETMISFieldValidation(process = true , fieldName = "managerQualityAssurance", fieldValue = "NOT_NULL")
	private Users managerQualityAssurance;
	
	/**Senior Manager: Quality Assurance & Partnerships */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "senio_manager_qa_user_id", nullable = true)
	@SETMISFieldValidation(process = true , fieldName = "seniorManagerQualityAssurance", fieldValue = "NOT_NULL")
	private Users seniorManagerQualityAssurance;
	
	@Column(name = "site_visit_done", columnDefinition = "BIT default false")
	private Boolean siteVisitDone;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name="review_committee_meeting_agenda_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda;
	
	@Column(name = "final_rejected", columnDefinition = "BIT default false")
	private Boolean finalRejected;
	
	@Column(name="final_reject_date")
	private Date finalRejectDate;
	
	@Column(name="recommended_date")
	private Date recommendedDate;
	
	@Column(name="recommended_to_revire_comm_date")
	private Date recommendedToReviewCommDate;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relationship_to_company_id", nullable = true)
    private RelationshipToCompany relationshipToCompany;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scope_of_responsibility_id", nullable = true)
	private ScopeOfResponsibility scopeOfResponsibility;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_provider_accreditation_id", nullable = true)
	private LegacyProviderAccreditation legacyProviderAccreditation;
	
	// used to remove from extract
	@Column(name="duplicate_application", columnDefinition = "BIT default false")
	private Boolean duplicateApplication;
	
	// Training Site linked to application
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_site_id", nullable = true)
	private TrainingSite trainingSite;

	/**
	 * @return the etqaReviewCommitteeDate
	 */
	public Date getEtqaReviewCommitteeDate() {
		return etqaReviewCommitteeDate;
	}

	/**
	 * @param etqaReviewCommitteeDate the etqaReviewCommitteeDate to set
	 */
	public void setEtqaReviewCommitteeDate(Date etqaReviewCommitteeDate) {
		this.etqaReviewCommitteeDate = etqaReviewCommitteeDate;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
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
		TrainingProviderApplication other = (TrainingProviderApplication) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new SDF company.
	 */
	public TrainingProviderApplication() {
		super();
	}

	public TrainingProviderApplication(Users users) {
		super();
		this.users = users;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the company of SDF.
	 *
	 * @return the company of SDF
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company of SDF
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public ProviderClass getProviderClass() {
		return providerClass;
	}

	public void setProviderClass(ProviderClass providerClass) {
		this.providerClass = providerClass;
	}

	public ProviderType getProviderType() {
		return providerType;
	}

	public void setProviderType(ProviderType providerType) {
		this.providerType = providerType;
	}

	public Etqa getEtqa() {
		return etqa;
	}

	public void setEtqa(Etqa etqa) {
		this.etqa = etqa;
	}

	public String getAccreditationNumber() {
		return accreditationNumber;
	}

	public void setAccreditationNumber(String accreditationNumber) {
		this.accreditationNumber = accreditationNumber;
	}

	public String getAccreditationPeriod() {
		return accreditationPeriod;
	}

	public void setAccreditationPeriod(String accreditationPeriod) {
		this.accreditationPeriod = accreditationPeriod;
	}

	public Date getExpiriyDate() {
		return expiriyDate;
	}

	public void setExpiriyDate(Date expiriyDate) {
		this.expiriyDate = expiriyDate;
	}

	public AccreditationApplicationTypeEnum getAccreditationApplicationTypeEnum() {
		return accreditationApplicationTypeEnum;
	}

	public void setAccreditationApplicationTypeEnum(AccreditationApplicationTypeEnum accreditationApplicationTypeEnum) {
		this.accreditationApplicationTypeEnum = accreditationApplicationTypeEnum;
	}

	public Boolean getTrainingAssessment() {
		return trainingAssessment;
	}

	public void setTrainingAssessment(Boolean trainingAssessment) {
		this.trainingAssessment = trainingAssessment;
	}

	public Boolean getAssessmentOnly() {
		return assessmentOnly;
	}

	public void setAssessmentOnly(Boolean assessmentOnly) {
		this.assessmentOnly = assessmentOnly;
	}

	public AccreditationStatus getAccreditationStatus() {
		return accreditationStatus;
	}

	public void setAccreditationStatus(AccreditationStatus accreditationStatus) {
		this.accreditationStatus = accreditationStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the siteVisitDate
	 */
	public Date getSiteVisitDate() {
		return siteVisitDate;
	}

	/**
	 * @param siteVisitDate
	 *            the siteVisitDate to set
	 */
	public void setSiteVisitDate(Date siteVisitDate) {
		this.siteVisitDate = siteVisitDate;
	}

	/**
	 * @return the siteVisitReportDate
	 */
	public Date getSiteVisitReportDate() {
		return siteVisitReportDate;
	}

	/**
	 * @param siteVisitReportDate
	 *            the siteVisitReportDate to set
	 */
	public void setSiteVisitReportDate(Date siteVisitReportDate) {
		this.siteVisitReportDate = siteVisitReportDate;
	}
	
	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	/**
	 * @return the useSkillProgrammeRoute
	 */
	public YesNoEnum getUseSkillProgrammeRoute() {
		return useSkillProgrammeRoute;
	}

	/**
	 * @param useSkillProgrammeRoute the useSkillProgrammeRoute to set
	 */
	public void setUseSkillProgrammeRoute(YesNoEnum useSkillProgrammeRoute) {
		this.useSkillProgrammeRoute = useSkillProgrammeRoute;
	}

	/**
	 * @return the skillsSet
	 */
	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	/**
	 * @param skillsSet the skillsSet to set
	 */
	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
	}

	/**
	 * @return the codeOfConductAccepted
	 */
	public Boolean getCodeOfConductAccepted() {
		return codeOfConductAccepted;
	}

	/**
	 * @param codeOfConductAccepted the codeOfConductAccepted to set
	 */
	public void setCodeOfConductAccepted(Boolean codeOfConductAccepted) {
		this.codeOfConductAccepted = codeOfConductAccepted;
	}

	/**
	 * @return the codeOfConductAcceptDate
	 */
	public Date getCodeOfConductAcceptDate() {
		return codeOfConductAcceptDate;
	}

	/**
	 * @param codeOfConductAcceptDate the codeOfConductAcceptDate to set
	 */
	public void setCodeOfConductAcceptDate(Date codeOfConductAcceptDate) {
		this.codeOfConductAcceptDate = codeOfConductAcceptDate;
	}

	/**
	 * @return the reviewCommitteeMeeting
	 */
	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	/**
	 * @param reviewCommitteeMeeting the reviewCommitteeMeeting to set
	 */
	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	/**
	 * @return the skillsProgram
	 */
	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	/**
	 * @param skillsProgram the skillsProgram to set
	 */
	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	/**
	 * @return the facilitatorAssessorModAvailable
	 */
	public Boolean getFacilitatorAssessorModAvailable() {
		return facilitatorAssessorModAvailable;
	}

	/**
	 * @param facilitatorAssessorModAvailable the facilitatorAssessorModAvailable to set
	 */
	public void setFacilitatorAssessorModAvailable(Boolean facilitatorAssessorModAvailable) {
		this.facilitatorAssessorModAvailable = facilitatorAssessorModAvailable;
	}

	public NonSetaCompany getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(NonSetaCompany nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

	public String getSiteVisitComment() {
		return siteVisitComment;
	}

	public void setSiteVisitComment(String siteVisitComment) {
		this.siteVisitComment = siteVisitComment;
	}

	public Users getQualityAssuranceUser() {
		return qualityAssuranceUser;
	}

	public void setQualityAssuranceUser(Users qualityAssuranceUser) {
		this.qualityAssuranceUser = qualityAssuranceUser;
	}

	public Boolean getSiteVisitDone() {
		return siteVisitDone;
	}

	public void setSiteVisitDone(Boolean siteVisitDone) {
		this.siteVisitDone = siteVisitDone;
	}

	public ReviewCommitteeMeetingAgenda getReviewCommitteeMeetingAgenda() {
		return reviewCommitteeMeetingAgenda;
	}

	public void setReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda) {
		this.reviewCommitteeMeetingAgenda = reviewCommitteeMeetingAgenda;
	}

	public Boolean getFinalRejected() {
		return finalRejected;
	}

	public void setFinalRejected(Boolean finalRejected) {
		this.finalRejected = finalRejected;
	}

	public Date getFinalRejectDate() {
		return finalRejectDate;
	}

	public void setFinalRejectDate(Date finalRejectDate) {
		this.finalRejectDate = finalRejectDate;
	}

	/**
	 * @return the managerQualityAssurance
	 */
	public Users getManagerQualityAssurance() {
		return managerQualityAssurance;
	}

	/**
	 * @param managerQualityAssurance the managerQualityAssurance to set
	 */
	public void setManagerQualityAssurance(Users managerQualityAssurance) {
		this.managerQualityAssurance = managerQualityAssurance;
	}

	/**
	 * @return the seniorManagerQualityAssurance
	 */
	public Users getSeniorManagerQualityAssurance() {
		return seniorManagerQualityAssurance;
	}

	/**
	 * @param seniorManagerQualityAssurance the seniorManagerQualityAssurance to set
	 */
	public void setSeniorManagerQualityAssurance(Users seniorManagerQualityAssurance) {
		this.seniorManagerQualityAssurance = seniorManagerQualityAssurance;
	}

	/**
	 * @return the recommendedDate
	 */
	public Date getRecommendedDate() {
		return recommendedDate;
	}

	/**
	 * @param recommendedDate the recommendedDate to set
	 */
	public void setRecommendedDate(Date recommendedDate) {
		this.recommendedDate = recommendedDate;
	}

	/**
	 * @return the recommendedToReviewCommDate
	 */
	public Date getRecommendedToReviewCommDate() {
		return recommendedToReviewCommDate;
	}

	/**
	 * @param recommendedToReviewCommDate the recommendedToReviewCommDate to set
	 */
	public void setRecommendedToReviewCommDate(Date recommendedToReviewCommDate) {
		this.recommendedToReviewCommDate = recommendedToReviewCommDate;
	}

	public RelationshipToCompany getRelationshipToCompany() {
		return relationshipToCompany;
	}

	public void setRelationshipToCompany(RelationshipToCompany relationshipToCompany) {
		this.relationshipToCompany = relationshipToCompany;
	}

	public ScopeOfResponsibility getScopeOfResponsibility() {
		return scopeOfResponsibility;
	}

	public void setScopeOfResponsibility(ScopeOfResponsibility scopeOfResponsibility) {
		this.scopeOfResponsibility = scopeOfResponsibility;
	}

	public LegacyProviderAccreditation getLegacyProviderAccreditation() {
		return legacyProviderAccreditation;
	}

	public void setLegacyProviderAccreditation(LegacyProviderAccreditation legacyProviderAccreditation) {
		this.legacyProviderAccreditation = legacyProviderAccreditation;
	}

	public ProviderStatus getProviderStatus() {
		return providerStatus;
	}

	public void setProviderStatus(ProviderStatus providerStatus) {
		this.providerStatus = providerStatus;
	}

	public ApprovalEnum getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(ApprovalEnum previousStatus) {
		this.previousStatus = previousStatus;
	}

	public ApprovalEnum getStatusBeforeSuspension() {
		return statusBeforeSuspension;
	}

	public void setStatusBeforeSuspension(ApprovalEnum statusBeforeSuspension) {
		this.statusBeforeSuspension = statusBeforeSuspension;
	}

	public Boolean getDuplicateApplication() {
		return duplicateApplication;
	}

	public void setDuplicateApplication(Boolean duplicateApplication) {
		this.duplicateApplication = duplicateApplication;
	}

	public TrainingSite getTrainingSite() {
		return trainingSite;
	}

	public void setTrainingSite(TrainingSite trainingSite) {
		this.trainingSite = trainingSite;
	}

	

}
