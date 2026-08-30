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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.SmeTypeEnum;
import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.entity.lookup.Designation;
import haj.com.entity.lookup.UserResponsibility;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "non_seta_company_users")
public class NonSetaCompanyUsers implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of CompanyUsers. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The user. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

	/** The company. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_seta_company_id", nullable = false)
	private NonSetaCompany nonSetaCompany;

	/** The position. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id", nullable = true)
	private CompanyUserPosition position;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/**
	 * The workflow process.
	 */
	@Enumerated
	@Column(name = "company_user_type")
	private ConfigDocProcessEnum companyUserType;
	
	/**Designation*/
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id", nullable = true)
	private Designation designation;
	
	/**flag to check if contact 
	 *person was existing in the system*/
	@Column(name = "existing_user", columnDefinition = "BIT default false")
	private Boolean existingUser;
	
	@Enumerated
	@Column(name = "sme_type")
	private SmeTypeEnum smeTypeEnum;
	
	@Enumerated
	@Column(name = "assessor_mod_type")
	private AssessorModType assessorModType;

	/** The Approval status. */
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;

	@Column(name = "no_responsibility")
	private Boolean noResponsibility;
	
	@Transient
	private String companyUserTypeDes;
	
	@Transient
	private String sdfLevel;

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
		NonSetaCompanyUsers other = (NonSetaCompanyUsers) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new company users.
	 */
	public NonSetaCompanyUsers() {
		super();
	}

	/**
	 * Instantiates a new company users.
	 *
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 */
	public NonSetaCompanyUsers(Users user, NonSetaCompany nonSetaCompany) {
		super();
		this.user = user;
		this.nonSetaCompany = nonSetaCompany;
	}

	/**
	 * Instantiates a new company users.
	 *
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 * @param companyUserType
	 *            the company user type
	 */
	public NonSetaCompanyUsers(Users user, NonSetaCompany nonSetaCompany, ConfigDocProcessEnum companyUserType) {
		super();
		this.user = user;
		this.nonSetaCompany = nonSetaCompany;
		this.companyUserType = companyUserType;
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
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the company user type.
	 *
	 * @return the company user type
	 */
	public ConfigDocProcessEnum getCompanyUserType() {
		return companyUserType;
	}

	/**
	 * Sets the company user type.
	 *
	 * @param companyUserType
	 *            the new company user type
	 */
	public void setCompanyUserType(ConfigDocProcessEnum companyUserType) {
		this.companyUserType = companyUserType;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public CompanyUserPosition getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position
	 *            the new position
	 */
	public void setPosition(CompanyUserPosition position) {
		this.position = position;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Boolean getNoResponsibility() {
		return noResponsibility;
	}

	public void setNoResponsibility(Boolean noResponsibility) {
		this.noResponsibility = noResponsibility;
	}

	public String getCompanyUserTypeDes() {
		return companyUserTypeDes;
	}

	public void setCompanyUserTypeDes(String companyUserTypeDes) {
		this.companyUserTypeDes = companyUserTypeDes;
	}

	public String getSdfLevel() {
		return sdfLevel;
	}

	public void setSdfLevel(String sdfLevel) {
		this.sdfLevel = sdfLevel;
	}

	/**
	 * @return the designation
	 */
	public Designation getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	/**
	 * @return the existingUser
	 */
	public Boolean getExistingUser() {
		return existingUser;
	}

	/**
	 * @param existingUser the existingUser to set
	 */
	public void setExistingUser(Boolean existingUser) {
		this.existingUser = existingUser;
	}

	/**
	 * @return the smeTypeEnum
	 */
	public SmeTypeEnum getSmeTypeEnum() {
		return smeTypeEnum;
	}

	/**
	 * @param smeTypeEnum the smeTypeEnum to set
	 */
	public void setSmeTypeEnum(SmeTypeEnum smeTypeEnum) {
		this.smeTypeEnum = smeTypeEnum;
	}

	public AssessorModType getAssessorModType() {
		return assessorModType;
	}

	public void setAssessorModType(AssessorModType assessorModType) {
		this.assessorModType = assessorModType;
	}

	public NonSetaCompany getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(NonSetaCompany nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

}
