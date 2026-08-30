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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RelationshipToCompany;
import haj.com.entity.lookup.SDFType;
import haj.com.entity.lookup.ScopeOfResponsibility;
import haj.com.entity.lookup.SdpType;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.ExportValidation;
import haj.com.validators.exports.SETMISFieldValidation;

/**
 * SDPCompany.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "sdp_company")
@AuditTable(value = "sdp_company_hist")
@Audited
@ExportValidation(message = "Invalid SDP Profile")
public class SDPCompany implements IDataEntity, Cloneable {

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

	/** company of SdpType. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "company", fieldValue = "NOT_NULL")
	private Company company;

	/** The SdpType. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sdp_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "sdp", fieldValue = "NOT_NULL")
	private Users sdp;
	
	/* Training Site */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_site_id", nullable = true)
	private TrainingSite trainingSite;

	/** The SdpType Type. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spf_type_id", nullable = true)
	private SdpType sdpType;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "relationship_to_company_id", nullable = true)
	private RelationshipToCompany relationshipToCompany;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scope_of_responsibility_id", nullable = true)
	private ScopeOfResponsibility scopeOfResponsibility;

	/** The Approval status. */
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;
	
	@Transient
	private Boolean canAlter;
	
	@Transient
	private Boolean canAction;
	
	
	/*
	 * Index:
	 * CREATE INDEX sdpcompany_sdp_company_site ON sdp_company (sdp_id, company_id, training_site_id);
	 * CREATE INDEX sdpcompany_sdp_company ON sdp_company (sdp_id, company_id);
	 */

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
		SDPCompany other = (SDPCompany) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new SDF company.
	 */
	public SDPCompany() {
		super();
	}

	/**
	 * Instantiates a new SDF company.
	 *
	 * @param company
	 *            the company
	 * @param sdf
	 *            the sdf
	 */
	public SDPCompany(Company company, Users sdp) {
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
	public SDPCompany(Company company, Users sdp, SdpType sdpType) {
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

	public TrainingSite getTrainingSite() {
		return trainingSite;
	}

	public void setTrainingSite(TrainingSite trainingSite) {
		this.trainingSite = trainingSite;
	}

	public SdpType getSdpType() {
		return sdpType;
	}

	public void setSdpType(SdpType sdpType) {
		this.sdpType = sdpType;
	}

	public Boolean getCanAlter() {
		return canAlter;
	}

	public void setCanAlter(Boolean canAlter) {
		this.canAlter = canAlter;
	}

	public Boolean getCanAction() {
		return canAction;
	}

	public void setCanAction(Boolean canAction) {
		this.canAction = canAction;
	}

}
