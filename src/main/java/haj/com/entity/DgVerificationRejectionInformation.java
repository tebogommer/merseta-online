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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "dg_verification_rejection_information")
public class DgVerificationRejectionInformation implements IDataEntity {

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
	
	/** The User who selected the reasons */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "user_id")
	private Users user;
	
	/** Link To DG verification */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "dg_verification_id", nullable = true)
	private DgVerification dgVerification;
	
	/** Role Link */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "role_id", nullable = true)
	private Roles role;
	
	/** The rejection reason */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "reject_reason_id")
	private RejectReasons rejectReason;

	/** indicator if its the latest entry */
	@Column(name = "latest_entry", columnDefinition = "BIT default false")
	private Boolean latestEntry;
	
	@Column(name = "flat_task_link_id", nullable = true)
	private Long flatTaskLink;
	
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
		DgVerificationRejectionInformation other = (DgVerificationRejectionInformation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public DgVerificationRejectionInformation() {
		super();
	}
	
	public DgVerificationRejectionInformation(Users user, DgVerification dgVerification, Roles role, RejectReasons rejectReason, Boolean latestEntry) {
		super();
		this.user = user;
		this.dgVerification = dgVerification;
		this.role = role;
		this.rejectReason = rejectReason;
		this.latestEntry = latestEntry;
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public RejectReasons getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(RejectReasons rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Boolean getLatestEntry() {
		return latestEntry;
	}

	public void setLatestEntry(Boolean latestEntry) {
		this.latestEntry = latestEntry;
	}

	public DgVerification getDgVerification() {
		return dgVerification;
	}

	public void setDgVerification(DgVerification dgVerification) {
		this.dgVerification = dgVerification;
	}

	public Long getFlatTaskLink() {
		return flatTaskLink;
	}

	public void setFlatTaskLink(Long flatTaskLink) {
		this.flatTaskLink = flatTaskLink;
	}
}