package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * review_committee_meeting_users.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "review_committee_meeting_users")
public class ReviewCommitteeMeetingUsers implements IDataEntity
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
	
	/**The User*/
	@JoinColumn(name="users_id")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private Users user;
	
	/** The ETQA Review Committee Schedule. */
	@JoinColumn(name="review_committee_meeting_id")
	@ManyToOne
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
    
	public ReviewCommitteeMeetingUsers(Users user, ReviewCommitteeMeeting reviewCommitteeMeeting) {
		super();
		this.user = user;
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}
	
	

	public ReviewCommitteeMeetingUsers() {
		super();
		// TODO Auto-generated constructor stub
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
		ReviewCommitteeMeetingUsers other = (ReviewCommitteeMeetingUsers) obj;
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

	


}
