package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import haj.com.entity.lookup.MeetingAgenda;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * ReviewCommitteeMeetingAgenda.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "Review_committee_meeting_agenda")
@AuditTable(value = "Review_committee_Meeting_agenda_hist")
public class ReviewCommitteeMeetingAgenda implements IDataEntity
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of ReviewCommitteeMeetingAgenda. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The ReviewCommitteeMeeting. */
	@ManyToOne(fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="review_committee_meeting_id", nullable=true)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
	
	/** The meetingAgenda. */
	@ManyToOne(fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="meeting_agenda_id", nullable=true)
	private MeetingAgenda meetingAgenda;
	
	/**The decisionNumber. */
	@Column(name = "decision_number")
	private String decisionNumber;
	
	@Column(name = "deleted", columnDefinition = "BIT default false")
	private Boolean deleted;
	
	
	
    
	public ReviewCommitteeMeetingAgenda() {
		super();
		this.deleted=false;
		// TODO Auto-generated constructor stub
	}

	public ReviewCommitteeMeetingAgenda(ReviewCommitteeMeeting reviewCommitteeMeeting,
			MeetingAgenda meetingAgenda, String decisionNumber) {
		super();
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
		this.meetingAgenda = meetingAgenda;
		this.decisionNumber = decisionNumber;
		this.deleted=false;
	}

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
		ReviewCommitteeMeetingAgenda other = (ReviewCommitteeMeetingAgenda) obj;
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

	

	public String getDecisionNumber() {
		return decisionNumber;
	}

	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}

	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	/**
	 * @return the meetingAgenda
	 */
	public MeetingAgenda getMeetingAgenda() {
		return meetingAgenda;
	}

	/**
	 * @param meetingAgenda the meetingAgenda to set
	 */
	public void setMeetingAgenda(MeetingAgenda meetingAgenda) {
		this.meetingAgenda = meetingAgenda;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	

}
