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

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Wsp.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "site_visit_report_dispute")
public class SiteVisitReportDispute implements IDataEntity {

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

	/** The site visit report. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "site_visit_report_id", nullable = true)
	private SiteVisitReport siteVisitReport;

	/** The create user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;

	/** The reason for dispute. */
	@Column(name = "reson_for_dispute", columnDefinition = "LONGTEXT")
	private String resonForDispute;
	
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
		SiteVisitReportDispute other = (SiteVisitReportDispute) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new wsp dispute.
	 *
	 * @param wsp
	 *            the siteVisitReport
	 * @param user
	 *            the user
	 * @param disputeReason
	 *            the dispute reason
	 */
	public SiteVisitReportDispute(SiteVisitReport siteVisitReport, Users user, String disputeReason) {
		super();
		this.siteVisitReport = siteVisitReport;
		this.createUser = user;
		this.resonForDispute = disputeReason;
	}

	/**
	 * Instantiates a new wsp dispute.
	 */
	public SiteVisitReportDispute() {
		super();
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
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * Gets the creates the user.
	 *
	 * @return the creates the user
	 */
	public Users getCreateUser() {
		return createUser;
	}

	/**
	 * Sets the creates the user.
	 *
	 * @param createUser
	 *            the new creates the user
	 */
	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	/**
	 * Gets the reson for dispute.
	 *
	 * @return the reson for dispute
	 */
	public String getResonForDispute() {
		return resonForDispute;
	}

	/**
	 * Sets the reson for dispute.
	 *
	 * @param resonForDispute
	 *            the new reson for dispute
	 */
	public void setResonForDispute(String resonForDispute) {
		this.resonForDispute = resonForDispute;
	}
	
	public SiteVisitReport getSiteVisitReport() {
		return siteVisitReport;
	}

	public void setSiteVisitReport(SiteVisitReport siteVisitReport) {
		this.siteVisitReport = siteVisitReport;
	}
}
