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

import org.hibernate.annotations.CreationTimestamp;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.RelationshipToCompany;
import haj.com.entity.lookup.ScopeOfResponsibility;
import haj.com.entity.lookup.SdpType;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.SETMISFieldValidation;

/**
 * SDPCompanyHistory.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "sdp_company_history")
public class SDPCompanyHistory implements IDataEntity,Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of SDPCompanyHistory. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** company of SdpType. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	/** The SdpType. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sdp_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "sdp", fieldValue = "NOT_NULL")
	private Users sdp;

	/** The SdpType Type. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sdp_type_id", nullable = true)
	private SdpType sdpType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "relationship_to_company_id", nullable = true)
	private RelationshipToCompany relationshipToCompany;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scope_of_responsibility_id", nullable = true)
	private ScopeOfResponsibility scopeOfResponsibility;

	/** The Approval status. */
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;
	
	/**The for SDF Company */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="for_sdp_company")
	private SDPCompany forSdpCompany;
	

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
		SDPCompanyHistory other = (SDPCompanyHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Instantiates a new SDF company.
	 */
	public SDPCompanyHistory() {
		super();
	}
	
	public SDPCompanyHistory(SDPCompany sdpCompany) {
		super();
		this.forSdpCompany=sdpCompany;
	}

	/**
	 * Instantiates a new SDF company.
	 *
	 * @param company
	 *            the company
	 * @param sdf
	 *            the sdf
	 */
	public SDPCompanyHistory(Company company, Users sdp) {
		super();
		this.company = company;
		this.sdp = sdp;
	}

	/**
	 * Instantiates a new SDF company.
	 *
	 * @param company
	 *            the company
	 * @param sdf
	 *            the sdf
	 * @param sdfType
	 *            the sdf type
	 */
	public SDPCompanyHistory(Company company, Users sdp, SdpType sdpType) {
		super();
		this.company = company;
		this.sdp = sdp;
		this.sdpType = sdpType;
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
	 * Gets the company of SDF.
	 *
	 * @return the company of SDF
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company of SDF
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Checks if is valid company.
	 *
	 * @return true, if is valid company
	 */
	public boolean isValidCompany() {
		return this.company.getValidCompany().length() == 0;
	}

	public RelationshipToCompany getRelationshipToCompany() {
		return relationshipToCompany;
	}

	public void setRelationshipToCompany(RelationshipToCompany relationshipToCompany) {
		this.relationshipToCompany = relationshipToCompany;
	}

	public ScopeOfResponsibility getScopeOfResponsibility() {
		return scopeOfResponsibility;
	}

	public void setScopeOfResponsibility(ScopeOfResponsibility scopeOfResponsibility) {
		this.scopeOfResponsibility = scopeOfResponsibility;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Users getSdp() {
		return sdp;
	}

	public void setSdp(Users sdp) {
		this.sdp = sdp;
	}

	public SdpType getSdpType() {
		return sdpType;
	}

	public void setSdpType(SdpType sdpType) {
		this.sdpType = sdpType;
	}

	public SDPCompany getForSdpCompany() {
		return forSdpCompany;
	}

	public void setForSdpCompany(SDPCompany forSdpCompany) {
		this.forSdpCompany = forSdpCompany;
	}

}
