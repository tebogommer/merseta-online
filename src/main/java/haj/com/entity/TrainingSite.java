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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.SETMISFieldValidation;

@Entity
@Table(name = "training_site")
@AuditTable(value = "training_site_hist")
@Audited
public class TrainingSite implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of TrainingSite. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@SETMISFieldValidation(process = true , fieldName = "company", fieldValue = "NOT_NULL")
	private Company company;
	
	@Column(name = "site_guid", length = 100, nullable = true)
	private String siteGuid;
	
	@Column(name = "site_name", length = 70, nullable = false)
	private String siteName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "residential_address_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "residentialAddress", fieldValue = "true")
	private Address residentialAddress;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postal_address_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "postalAddress", fieldValue = "true")
	private Address postalAddress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_action_user_id", nullable = true)
	@SETMISFieldValidation(process = true , fieldName = "lastActionUser", fieldValue = "NOT_NULL")
	private Users lastActionUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_action_user_date", length = 19)
	private Date lastActionUserDate;
	
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;
	
	@Transient
	private RegionTown regionTown;

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
		TrainingSite other = (TrainingSite) obj;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getSiteGuid() {
		return siteGuid;
	}

	public void setSiteGuid(String siteGuid) {
		this.siteGuid = siteGuid;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public Users getLastActionUser() {
		return lastActionUser;
	}

	public void setLastActionUser(Users lastActionUser) {
		this.lastActionUser = lastActionUser;
	}

	public Date getLastActionUserDate() {
		return lastActionUserDate;
	}

	public void setLastActionUserDate(Date lastActionUserDate) {
		this.lastActionUserDate = lastActionUserDate;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public RegionTown getRegionTown() {
		return regionTown;
	}

	public void setRegionTown(RegionTown regionTown) {
		this.regionTown = regionTown;
	}

}
