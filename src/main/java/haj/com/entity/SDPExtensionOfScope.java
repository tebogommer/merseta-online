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
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "sdp_extension_of_scope")
@AuditTable(value = "sdp_extension_of_scope_hist")
@Audited
public class SDPExtensionOfScope implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The SDF. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = true)
	private Users users;

	/**
	 * skills programme route will be used for obtaining the unit standards?
	 */
	@Enumerated
	@Column(name = "use_skill_programme_route")
	private YesNoEnum useSkillProgrammeRoute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id ", nullable = true)
	@Fetch(FetchMode.JOIN)
	private SkillsProgram skillsProgram;

	@Column(name = "site_visit_date", nullable = true)
	private Date siteVisitDate;

	@Column(name = "site_visit_report_date", nullable = true)
	private Date siteVisitReportDate;

	/** The Approval status. */
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;

	/** The ETQA Review Committee Schedule. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "review_committee_meeting_id")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name = "review_committee_meeting_agenda_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda;

	/** Note. */
	@Column(name = "note", columnDefinition = "LONGTEXT")
	private String note;

	/** The approved date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	private Date approvedDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	TrainingProviderApplication trainingProviderApplication;

	@Column(name = "previous_code_of_conduct_accept_date")
	private Date previousCodeOfConductAcceptDate;

	@Column(name = "recommended_date")
	private Date recommendedDate;

	@Column(name = "recommended_to_revire_comm_date")
	private Date recommendedToReviewCommDate;

	@Column(name = "site_visit_done", columnDefinition = "BIT default false")
	private Boolean siteVisitDone;
	/** The Approval status. */
	@Enumerated
	@Column(name = "previous_approval_status")
	private ApprovalEnum previousApprovalStatus;

	@Column(name = "site_visit_comment")
	private String siteVisitComment;

	/** Quality Assuror */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quality_assurance_user_id", nullable = true)
	private Users qualityAssuranceUser;

	/** Manager: Quality Assurance */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_qa_user_id", nullable = true)
	private Users managerQualityAssurance;

	/** Senior Manager: Quality Assurance & Partnerships */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "senio_manager_qa_user_id", nullable = true)
	private Users seniorManagerQualityAssurance;

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SDPExtensionOfScope other = (SDPExtensionOfScope) obj;
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
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	public YesNoEnum getUseSkillProgrammeRoute() {
		return useSkillProgrammeRoute;
	}

	public void setUseSkillProgrammeRoute(YesNoEnum useSkillProgrammeRoute) {
		this.useSkillProgrammeRoute = useSkillProgrammeRoute;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public Date getSiteVisitDate() {
		return siteVisitDate;
	}

	public void setSiteVisitDate(Date siteVisitDate) {
		this.siteVisitDate = siteVisitDate;
	}

	public Date getSiteVisitReportDate() {
		return siteVisitReportDate;
	}

	public void setSiteVisitReportDate(Date siteVisitReportDate) {
		this.siteVisitReportDate = siteVisitReportDate;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public Date getPreviousCodeOfConductAcceptDate() {
		return previousCodeOfConductAcceptDate;
	}

	public void setPreviousCodeOfConductAcceptDate(Date previousCodeOfConductAcceptDate) {
		this.previousCodeOfConductAcceptDate = previousCodeOfConductAcceptDate;
	}

	public Date getRecommendedDate() {
		return recommendedDate;
	}

	public void setRecommendedDate(Date recommendedDate) {
		this.recommendedDate = recommendedDate;
	}

	public Date getRecommendedToReviewCommDate() {
		return recommendedToReviewCommDate;
	}

	public void setRecommendedToReviewCommDate(Date recommendedToReviewCommDate) {
		this.recommendedToReviewCommDate = recommendedToReviewCommDate;
	}

	public Boolean getSiteVisitDone() {
		return siteVisitDone;
	}

	public void setSiteVisitDone(Boolean siteVisitDone) {
		this.siteVisitDone = siteVisitDone;
	}

	public ApprovalEnum getPreviousApprovalStatus() {
		return previousApprovalStatus;
	}

	public void setPreviousApprovalStatus(ApprovalEnum previousApprovalStatus) {
		this.previousApprovalStatus = previousApprovalStatus;
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

	public Users getManagerQualityAssurance() {
		return managerQualityAssurance;
	}

	public void setManagerQualityAssurance(Users managerQualityAssurance) {
		this.managerQualityAssurance = managerQualityAssurance;
	}

	public Users getSeniorManagerQualityAssurance() {
		return seniorManagerQualityAssurance;
	}

	public void setSeniorManagerQualityAssurance(Users seniorManagerQualityAssurance) {
		this.seniorManagerQualityAssurance = seniorManagerQualityAssurance;
	}

	public ReviewCommitteeMeetingAgenda getReviewCommitteeMeetingAgenda() {
		return reviewCommitteeMeetingAgenda;
	}

	public void setReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda) {
		this.reviewCommitteeMeetingAgenda = reviewCommitteeMeetingAgenda;
	}

}
