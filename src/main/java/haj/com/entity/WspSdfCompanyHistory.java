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

@Entity
@Table(name = "wsp_sdf_company_history")
public class WspSdfCompanyHistory implements IDataEntity, Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	/** The Id of SDFCompany Entity where referenced */
	@Column(name = "orginal_sdf_company_id")
	private Long orginalSDFCompanyId;
	
	/** target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/** target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;
	
	/** The link to CompanyMainHistory */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_company_main_history_id", nullable = true)
	private WspCompanyMainHistory wspCompanyMainHistory;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "orginal_create_date", length = 19)
	private Date orginalCreateDate;

	/** company of SDF. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	// to be used when company turned into flat id
	@Transient
	private Company companyObject;

	/** The SDF. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sdf_id", nullable = true)
	private Users sdf;
	
	
	// to be used when sdf turned into flat id
	@Transient
	private Users sdfObject;

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
		WspSdfCompanyHistory other = (WspSdfCompanyHistory) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new SDF company.
	 */
	public WspSdfCompanyHistory() {
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
	public WspSdfCompanyHistory(Company company, Users sdf) {
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
	public WspSdfCompanyHistory(Company company, Users sdf, SDFType sdfType) {
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

	public Long getOrginalSDFCompanyId() {
		return orginalSDFCompanyId;
	}

	public void setOrginalSDFCompanyId(Long orginalSDFCompanyId) {
		this.orginalSDFCompanyId = orginalSDFCompanyId;
	}

	public Date getOrginalCreateDate() {
		return orginalCreateDate;
	}

	public void setOrginalCreateDate(Date orginalCreateDate) {
		this.orginalCreateDate = orginalCreateDate;
	}

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

	public Company getCompanyObject() {
		return companyObject;
	}

	public void setCompanyObject(Company companyObject) {
		this.companyObject = companyObject;
	}

	public Users getSdfObject() {
		return sdfObject;
	}

	public void setSdfObject(Users sdfObject) {
		this.sdfObject = sdfObject;
	}

	public WspCompanyMainHistory getWspCompanyMainHistory() {
		return wspCompanyMainHistory;
	}

	public void setWspCompanyMainHistory(WspCompanyMainHistory wspCompanyMainHistory) {
		this.wspCompanyMainHistory = wspCompanyMainHistory;
	}
}
