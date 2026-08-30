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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.enums.AssessorModeratorTypeEnum;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.JobTitle;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.ExportValidation;
import haj.com.validators.exports.SETMISFieldValidation;
import haj.com.validators.exports.services.AssessorModeratorApplicaitonValidationService;

@Entity
@Table(name = "assessor_moderator_application")
@AuditTable(value = "assessor_moderator_application_hist")
@Audited
@ExportValidation(message = "Invalid Assessor/Moderator Profile")
public class AssessorModeratorApplication implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@JoinColumn(name = "users_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@SETMISFieldValidation(process = true, fieldName = "user", fieldValue = "NOT_NULL")
	private Users user;
	
	@JoinColumn(name = "create_user_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private Users createUser;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "job_title_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private JobTitle jobTitle;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "ofo_codes_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private OfoCodes ofoCodes;

	@Enumerated
	@Column(name = "application_type")
	private AssessorModeratorAppTypeEnum applicationType;

	@Column(name = "code_of_conduct_accepted")
	private Boolean codeOfConductAccepted;

	@CreationTimestamp
	@Column(name = "code_of_conduct_accept_date")
	private Date codeOfConductAcceptDate;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	/** Accreditation or certificate Number */
	@Column(name = "certificate_number")
	@SETMISFieldValidation(className = AssessorModeratorApplicaitonValidationService.class, method = "validateCertificateNumber", paramClass = String.class, message = "Validation Failed For SETMIS Certificate Number<ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Value in field may only contain the characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>", fieldName = "certificateNumber", fieldValue = "NOT_NULL")
	private String certificateNumber;

	/** The start date. */
	@Column(name = "start_date", length = 19, nullable = true)
	private Date startDate;

	/** The end date. */
	@Column(name = "end_date", length = 19, nullable = true)
	private Date endDate;

	/** The approved date. */
	@Column(name = "approved_date", length = 19, nullable = true)
	private Date approvedDate;

	/** The ETQA Review Committee Schedule. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "review_committee_meeting_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "review_committee_meeting_agenda_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda;

	@Column(name = "final_rejected", columnDefinition = "BIT default false")
	private Boolean finalRejected;

	@Column(name = "final_approved", columnDefinition = "BIT default false")
	private Boolean finalApproved;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "legacy_assessor_accreditation_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private LegacyAssessorAccreditation legacyAssessorAccreditation;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "legacy_moderator_accreditation_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private LegacyModeratorAccreditation legacyModeratorAccreditation;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "etqa_id", nullable = true)
	private Etqa etqa;
	
	// used to remove from extract
	@Column(name="duplicate_application", columnDefinition = "BIT default false")
	private Boolean duplicateApplication;
	
	@Enumerated
	@Column(name = "type_assessor_moderator")
	private AssessorModeratorTypeEnum assessorModeratorType;
	
	// used for TTC assessors / moderators
	@Column(name="trade_test_center_application", columnDefinition = "BIT default false")
	private Boolean tradeTestCenterApplication;
	
	@Column(name = "assessor_application", columnDefinition = "BIT default false")
	private Boolean assessorApplication;
	
	@Column(name = "moderator_application", columnDefinition = "BIT default false")
	private Boolean moderatorApplication;
	
	@JoinColumn(name = "last_update_user_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private Users lastUpdateUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_date", length = 19)
	private Date lastUpdateDate;

	@Column(name = "system_update", columnDefinition = "BIT default false")
	private Boolean systemUpdate;
	
	@Transient
	private AssessorModReRegistration assessorModReRegistration;

	@Transient
	private String decisionNumber;

	@Transient
	private Date reviewCommitteeDate;

	@Transient
	private Date assessorStatusEffectiveDate;

	@Transient
	private String legacyStatus;

	@Transient
	private boolean initialValidation;
	
	@Transient
	private boolean applyall;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public JobTitle getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(JobTitle jobTitle) {
		this.jobTitle = jobTitle;
	}

	public AssessorModeratorAppTypeEnum getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(AssessorModeratorAppTypeEnum applicationType) {
		this.applicationType = applicationType;
	}

	public Boolean getCodeOfConductAccepted() {
		return codeOfConductAccepted;
	}

	public void setCodeOfConductAccepted(Boolean codeOfConductAccepted) {
		this.codeOfConductAccepted = codeOfConductAccepted;
	}

	public Date getCodeOfConductAcceptDate() {
		return codeOfConductAcceptDate;
	}

	public void setCodeOfConductAcceptDate(Date codeOfConductAcceptDate) {
		this.codeOfConductAcceptDate = codeOfConductAcceptDate;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the reviewCommitteeMeeting
	 */
	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	/**
	 * @param reviewCommitteeMeeting
	 *            the reviewCommitteeMeeting to set
	 */
	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	public ReviewCommitteeMeetingAgenda getReviewCommitteeMeetingAgenda() {
		return reviewCommitteeMeetingAgenda;
	}

	public void setReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda) {
		this.reviewCommitteeMeetingAgenda = reviewCommitteeMeetingAgenda;
	}

	/**
	 * @return the finalRejected
	 */
	public Boolean getFinalRejected() {
		return finalRejected;
	}

	/**
	 * @param finalRejected
	 *            the finalRejected to set
	 */
	public void setFinalRejected(Boolean finalRejected) {
		this.finalRejected = finalRejected;
	}

	public AssessorModReRegistration getAssessorModReRegistration() {
		return assessorModReRegistration;
	}

	public void setAssessorModReRegistration(AssessorModReRegistration assessorModReRegistration) {
		this.assessorModReRegistration = assessorModReRegistration;
	}

	public String getDecisionNumber() {
		return decisionNumber;
	}

	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}

	public Date getReviewCommitteeDate() {
		return reviewCommitteeDate;
	}

	public void setReviewCommitteeDate(Date reviewCommitteeDate) {
		this.reviewCommitteeDate = reviewCommitteeDate;
	}

	public Date getAssessorStatusEffectiveDate() {
		return assessorStatusEffectiveDate;
	}

	public void setAssessorStatusEffectiveDate(Date assessorStatusEffectiveDate) {
		this.assessorStatusEffectiveDate = assessorStatusEffectiveDate;
	}

	public LegacyAssessorAccreditation getLegacyAssessorAccreditation() {
		return legacyAssessorAccreditation;
	}

	public void setLegacyAssessorAccreditation(LegacyAssessorAccreditation legacyAssessorAccreditation) {
		this.legacyAssessorAccreditation = legacyAssessorAccreditation;
	}

	public LegacyModeratorAccreditation getLegacyModeratorAccreditation() {
		return legacyModeratorAccreditation;
	}

	public void setLegacyModeratorAccreditation(LegacyModeratorAccreditation legacyModeratorAccreditation) {
		this.legacyModeratorAccreditation = legacyModeratorAccreditation;
	}

	public Boolean getFinalApproved() {
		return finalApproved;
	}

	public void setFinalApproved(Boolean finalApproved) {
		this.finalApproved = finalApproved;
	}

	public String getLegacyStatus() {
		return legacyStatus;
	}

	public void setLegacyStatus(String legacyStatus) {
		this.legacyStatus = legacyStatus;
	}

	public boolean isInitialValidation() {
		return initialValidation;
	}

	public void setInitialValidation(boolean initialValidation) {
		this.initialValidation = initialValidation;
	}

	public Etqa getEtqa() {
		return etqa;
	}

	public void setEtqa(Etqa etqa) {
		this.etqa = etqa;
	}

	public Boolean getDuplicateApplication() {
		return duplicateApplication;
	}

	public void setDuplicateApplication(Boolean duplicateApplication) {
		this.duplicateApplication = duplicateApplication;
	}

	public boolean isApplyall() {
		return applyall;
	}

	public void setApplyall(boolean applyall) {
		this.applyall = applyall;
	}

	public Boolean getTradeTestCenterApplication() {
		return tradeTestCenterApplication;
	}

	public void setTradeTestCenterApplication(Boolean tradeTestCenterApplication) {
		this.tradeTestCenterApplication = tradeTestCenterApplication;
	}

	public AssessorModeratorTypeEnum getAssessorModeratorType() {
		return assessorModeratorType;
	}

	public void setAssessorModeratorType(AssessorModeratorTypeEnum assessorModeratorType) {
		this.assessorModeratorType = assessorModeratorType;
	}

	public Boolean getAssessorApplication() {
		return assessorApplication;
	}

	public void setAssessorApplication(Boolean assessorApplication) {
		this.assessorApplication = assessorApplication;
	}

	public Boolean getModeratorApplication() {
		return moderatorApplication;
	}

	public void setModeratorApplication(Boolean moderatorApplication) {
		this.moderatorApplication = moderatorApplication;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public Users getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(Users lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Boolean getSystemUpdate() {
		return systemUpdate;
	}

	public void setSystemUpdate(Boolean systemUpdate) {
		this.systemUpdate = systemUpdate;
	}

}
