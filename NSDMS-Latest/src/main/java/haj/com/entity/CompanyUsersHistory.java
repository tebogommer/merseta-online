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
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.entity.lookup.Designation;
import haj.com.entity.lookup.UserResponsibility;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * CompanyUsersHistory.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_users_history")
public class CompanyUsersHistory implements IDataEntity {

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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	/** The position. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id", nullable = true)
	private CompanyUserPosition position;

	/** The position. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_responsibility_id", nullable = true)
	private UserResponsibility userResponsibility;

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

	@Transient
	private List<UsersResponsibilities> selectedResponsibilities;
	
	/** The Approval status. */
	@Enumerated
	@Column(name = "approval_status")
	private ApprovalEnum approvalStatus;
	
	/** The for company user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "for_company_user", nullable = true)
	private CompanyUsers forCompanyUser;
	
	@Column(name = "no_responsibility")
	private Boolean noResponsibility;
	
	/**Designation*/
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "designation_id", nullable = true)
	private Designation designation;

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
		CompanyUsersHistory other = (CompanyUsersHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Instantiates a new company users.
	 */
	public CompanyUsersHistory() {
		super();
	}
	
	public CompanyUsersHistory(CompanyUsers companyUsers) {
		super();
		this.forCompanyUser=companyUsers;
	}

	/**
	 * Instantiates a new company users.
	 *
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 */
	public CompanyUsersHistory(Users user, Company company) {
		super();
		this.user = user;
		this.company = company;
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
	public CompanyUsersHistory(Users user, Company company, ConfigDocProcessEnum companyUserType) {
		super();
		this.user = user;
		this.company = company;
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
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
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

	public UserResponsibility getUserResponsibility() {
		return userResponsibility;
	}

	public void setUserResponsibility(UserResponsibility userResponsibility) {
		this.userResponsibility = userResponsibility;
	}

	public List<UsersResponsibilities> getSelectedResponsibilities() {
		return selectedResponsibilities;
	}

	public void setSelectedResponsibilities(List<UsersResponsibilities> selectedResponsibilities) {
		this.selectedResponsibilities = selectedResponsibilities;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public CompanyUsers getForCompanyUser() {
		return forCompanyUser;
	}

	public void setForCompanyUser(CompanyUsers forCompanyUser) {
		this.forCompanyUser = forCompanyUser;
	}

	public Boolean getNoResponsibility() {
		return noResponsibility;
	}

	public void setNoResponsibility(Boolean noResponsibility) {
		this.noResponsibility = noResponsibility;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

}
