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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RelationshipToCompany;
import haj.com.entity.lookup.SDFType;
import haj.com.entity.lookup.ScopeOfResponsibility;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.ExportValidation;
import haj.com.validators.exports.SETMISFieldValidation;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "sdf_company")
@ExportValidation(message = "Invalid SDF Profile")
public class SDFCompany implements IDataEntity, Cloneable {

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

	/** company of SDF. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "company", fieldValue = "NOT_NULL")
	private Company company;

	/** The SDF. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sdf_id", nullable = true)
	@SETMISFieldValidation(process = true, fieldName = "sdf", fieldValue = "NOT_NULL")
	private Users sdf;

	/** The SDF Type. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sdf_type_id", nullable = true)
	private SDFType sdfType;

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
	
	@Transient
	private RegionTown regionTown;
	
	@Transient
	private String rejectReason;
	
	@Transient
	private WspStatusEnum wspStatusEnum;
	
	@Transient
	private ExtensionRequest extensionRequest;

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
		SDFCompany other = (SDFCompany) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new SDF company.
	 */
	public SDFCompany() {
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
	public SDFCompany(Company company, Users sdf) {
		super();
		this.company = company;
		this.sdf = sdf;
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
	public SDFCompany(Company company, Users sdf, SDFType sdfType) {
		super();
		this.company = company;
		this.sdf = sdf;
		this.sdfType = sdfType;
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
	 * Gets the sdf.
	 *
	 * @return the SDF
	 */
	public Users getSdf() {
		return sdf;
	}

	/**
	 * Sets the sdf.
	 *
	 * @param sdf
	 *            the new SDF
	 */
	public void setSdf(Users sdf) {
		this.sdf = sdf;
	}

	/**
	 * Gets the sdf type.
	 *
	 * @return the sdf type
	 */
	public SDFType getSdfType() {
		return sdfType;
	}

	/**
	 * Sets the sdf type.
	 *
	 * @param sdfType
	 *            the new sdf type
	 */
	public void setSdfType(SDFType sdfType) {
		this.sdfType = sdfType;
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

	public RegionTown getRegionTown() {
		return regionTown;
	}

	public void setRegionTown(RegionTown regionTown) {
		this.regionTown = regionTown;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public WspStatusEnum getWspStatusEnum() {
		return wspStatusEnum;
	}

	public void setWspStatusEnum(WspStatusEnum wspStatusEnum) {
		this.wspStatusEnum = wspStatusEnum;
	}

	public ExtensionRequest getExtensionRequest() {
		return extensionRequest;
	}

	public void setExtensionRequest(ExtensionRequest extensionRequest) {
		this.extensionRequest = extensionRequest;
	}
}
