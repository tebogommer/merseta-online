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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * SDPReAccreditation.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "sdp_re_accreditation")
@AuditTable(value = "sdp_re_accreditation_hist")
@Audited
public class SDPReAccreditation implements IDataEntity
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
	
	/** The SDF. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = true)
	private Users users;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	private TrainingProviderApplication trainingProviderApplication;
	
	/** The Approval status. */
	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	/** The ETQA Review Committee Schedule. */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@JoinColumn(name="review_committee_meeting_id")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;

	/** The approved date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@Enumerated
	@Column(name = "previours_accreditation_application_type")
	private AccreditationApplicationTypeEnum previoursAccreditationApplicationType;
	
	@Column(name = "previours_site_visit_report_date", nullable = true)
	private Date previoursSiteVisitReportDate;

	@Column(name = "previours_site_visit_date", nullable = true)
	private Date previoursSiteVisitDate;
	
	@Column(name = "previours_site_visit_comment")
	private String previoursSiteVisitComment;
	
	/** The approved date. */
	@Column(name = "approved_date", length = 19)
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
		SDPReAccreditation other = (SDPReAccreditation) obj;
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

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}


	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public AccreditationApplicationTypeEnum getPrevioursAccreditationApplicationType() {
		return previoursAccreditationApplicationType;
	}

	public void setPrevioursAccreditationApplicationType(
			AccreditationApplicationTypeEnum previoursAccreditationApplicationType) {
		this.previoursAccreditationApplicationType = previoursAccreditationApplicationType;
	}


	/**
	 * @return the previoursSiteVisitDate
	 */
	public Date getPrevioursSiteVisitDate() {
		return previoursSiteVisitDate;
	}

	/**
	 * @param previoursSiteVisitDate the previoursSiteVisitDate to set
	 */
	public void setPrevioursSiteVisitDate(Date previoursSiteVisitDate) {
		this.previoursSiteVisitDate = previoursSiteVisitDate;
	}

	/**
	 * @return the previoursSiteVisitComment
	 */
	public String getPrevioursSiteVisitComment() {
		return previoursSiteVisitComment;
	}

	/**
	 * @param previoursSiteVisitComment the previoursSiteVisitComment to set
	 */
	public void setPrevioursSiteVisitComment(String previoursSiteVisitComment) {
		this.previoursSiteVisitComment = previoursSiteVisitComment;
	}

	/**
	 * @return the previoursSiteVisitReportDate
	 */
	public Date getPrevioursSiteVisitReportDate() {
		return previoursSiteVisitReportDate;
	}

	/**
	 * @param previoursSiteVisitReportDate the previoursSiteVisitReportDate to set
	 */
	public void setPrevioursSiteVisitReportDate(Date previoursSiteVisitReportDate) {
		this.previoursSiteVisitReportDate = previoursSiteVisitReportDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	

}
