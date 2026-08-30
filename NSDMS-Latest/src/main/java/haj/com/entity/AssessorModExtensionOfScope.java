package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "assessor_mod_extension_of_scope")
public class AssessorModExtensionOfScope implements IDataEntity
{
	
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
	
	/** The AssessorModeratorApplication. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessor_moderator_application_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private AssessorModeratorApplication assessorModeratorApplication;
	
	@JoinColumn(name="create_users_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private Users createUser;
	

	/** The ETQA Review Committee Schedule. */
	@JoinColumn(name="review_committee_meeting_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
	

	@JoinColumn(name="review_committee_meeting_agenda_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda;
	
	@Enumerated
	@Column(name="status")
	private ApprovalEnum status;
	
	@Column(name="code_of_conduct_accepted")
	private Boolean codeOfConductAccepted;
	
	@Enumerated
	@Column(name="previous_application_type")
	private AssessorModeratorAppTypeEnum previousApplicationType;
	
	@CreationTimestamp
	@Column(name="previous_code_of_conduct_accept_date")
	private Date previousCodeOfConductAcceptDate;
	
	/** The ETQA Review Committee Schedule. */
	@JoinColumn(name="previous_review_committee_meeting_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting previousReviewCommitteeMeeting;
	
	@JoinColumn(name="previous_review_committee_meeting_agenda_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeetingAgenda previousReviewCommitteeMeetingAgenda;
	
	/** The approved date. */
	@Column(name = "approved_date", length = 19,nullable = true)
	private Date approvedDate;
	
	/** The docs. */
	@Transient
	private List<Doc> docs;

    
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
		AssessorModExtensionOfScope other = (AssessorModExtensionOfScope) obj;
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
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public AssessorModeratorApplication getAssessorModeratorApplication() {
		return assessorModeratorApplication;
	}

	public void setAssessorModeratorApplication(AssessorModeratorApplication assessorModeratorApplication) {
		this.assessorModeratorApplication = assessorModeratorApplication;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

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

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public AssessorModeratorAppTypeEnum getPreviousApplicationType() {
		return previousApplicationType;
	}

	public void setPreviousApplicationType(AssessorModeratorAppTypeEnum previousApplicationType) {
		this.previousApplicationType = previousApplicationType;
	}

	public Date getPreviousCodeOfConductAcceptDate() {
		return previousCodeOfConductAcceptDate;
	}

	public void setPreviousCodeOfConductAcceptDate(Date previousCodeOfConductAcceptDate) {
		this.previousCodeOfConductAcceptDate = previousCodeOfConductAcceptDate;
	}

	public ReviewCommitteeMeeting getPreviousReviewCommitteeMeeting() {
		return previousReviewCommitteeMeeting;
	}

	public void setPreviousReviewCommitteeMeeting(ReviewCommitteeMeeting previousReviewCommitteeMeeting) {
		this.previousReviewCommitteeMeeting = previousReviewCommitteeMeeting;
	}

	public ReviewCommitteeMeetingAgenda getPreviousReviewCommitteeMeetingAgenda() {
		return previousReviewCommitteeMeetingAgenda;
	}

	public void setPreviousReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda previousReviewCommitteeMeetingAgenda) {
		this.previousReviewCommitteeMeetingAgenda = previousReviewCommitteeMeetingAgenda;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}





}
