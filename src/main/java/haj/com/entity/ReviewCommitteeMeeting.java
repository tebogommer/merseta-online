package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.MeetingTypeEnum;
import haj.com.entity.lookup.MeetingAgenda;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "review_committee_meeting")
@AuditTable(value = "review_committee_meeting_hist")
public class ReviewCommitteeMeeting implements IDataEntity
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
	
	/** title */
	@Column(name="title", columnDefinition="LONGTEXT") 
    private String title;
	
	/**Review from date time */
	@Column(name = "from_date_time", length = 19)
	private Date fromDateTime;
	
	/**Review to date time */
	@Column(name = "to_date_time", length = 19)
	private Date toDateTime;
	
	/**The MeetingNumber. */
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "meeting_number")
	private Long meetingNumber;
	
	
	/** additional Info. */
	@Column(name="additional_info", columnDefinition="LONGTEXT") 
    private String additionalInfo;
	
	/** venue. */
	@Column(name="venue", columnDefinition="LONGTEXT") 
    private String venue;
	
	/** The user. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;
	
	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Transient
	private ArrayList<MeetingAgenda> meetingAgendaList;
	
	@Transient
	private ArrayList<ReviewCommitteeMeetingAgenda> reviewCommitteeMeetingAgendaList;
	
	@Transient
	private ArrayList<ReviewCommitteeMeetingUsers> reviewCommitteeMeetingUsersList;
	
	
	public Long getTargetKey() {
		return targetKey;
	}

	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	/**  target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/**  target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;

   

    
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
		ReviewCommitteeMeeting other = (ReviewCommitteeMeeting) obj;
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
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getFromDateTime() {
		return fromDateTime;
	}

	public void setFromDateTime(Date fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	public Date getToDateTime() {
		return toDateTime;
	}

	public void setToDateTime(Date toDateTime) {
		this.toDateTime = toDateTime;
	}

	public Long getMeetingNumber() {
		return meetingNumber;
	}

	public void setMeetingNumber(Long meetingNumber) {
		this.meetingNumber = meetingNumber;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}


	/**
	 * @return the meetingAgendaList
	 */
	public ArrayList<MeetingAgenda> getMeetingAgendaList() {
		return meetingAgendaList;
	}

	/**
	 * @param meetingAgendaList the meetingAgendaList to set
	 */
	public void setMeetingAgendaList(ArrayList<MeetingAgenda> meetingAgendaList) {
		this.meetingAgendaList = meetingAgendaList;
	}

	
	
	/**
	 * @return the reviewCommitteeMeetingAgendaList
	 */
	public ArrayList<ReviewCommitteeMeetingAgenda> getReviewCommitteeMeetingAgendaList() {
		return reviewCommitteeMeetingAgendaList;
	}

	/**
	 * @param reviewCommitteeMeetingAgendaList the reviewCommitteeMeetingAgendaList to set
	 */
	public void setReviewCommitteeMeetingAgendaList(
			ArrayList<ReviewCommitteeMeetingAgenda> reviewCommitteeMeetingAgendaList) {
		this.reviewCommitteeMeetingAgendaList = reviewCommitteeMeetingAgendaList;
	}

	/**
	 * @return the reviewCommitteeMeetingUsersList
	 */
	public ArrayList<ReviewCommitteeMeetingUsers> getReviewCommitteeMeetingUsersList() {
		return reviewCommitteeMeetingUsersList;
	}

	/**
	 * @param reviewCommitteeMeetingUsersList the reviewCommitteeMeetingUsersList to set
	 */
	public void setReviewCommitteeMeetingUsersList(ArrayList<ReviewCommitteeMeetingUsers> reviewCommitteeMeetingUsersList) {
		this.reviewCommitteeMeetingUsersList = reviewCommitteeMeetingUsersList;
	}

	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

}
