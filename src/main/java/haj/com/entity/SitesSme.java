package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
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
import haj.com.entity.enums.SmeTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;
import haj.com.validators.exports.ExportValidation;
import haj.com.validators.exports.SETMISFieldValidation;
import haj.com.validators.exports.services.UserValidationService;

@Entity
@Table(name = "sites_sme")
@AuditTable(value = "sites_sme_hist")
@Audited
@ExportValidation(message = "Invalid Mentor Profile")
public class SitesSme implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The company. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	//@SETMISFieldValidation(process = true , fieldName = "company", fieldValue = "NOT_NULL")
	private Company company;

	/** The Site */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "sites_id", nullable = true)
	//@SETMISFieldValidation(process = true , fieldName = "sites", fieldValue = "NOT_NULL")
	private Sites sites;

	@Column(name = "use_company_address")
	private Boolean useCompanyAddress;

	/** The first name. */
	@Column(name = "first_name", length = 100, nullable = false)
	//@SETMISFieldValidation(className = UserValidationService.class, method = "validateName", paramClass = String.class, message = "Validation Failed For SETMIS First Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL</li></ul>", fieldName = "firstName", fieldValue = "NOT_NULL")
	private String firstName;

	/** The last name. */
	@Column(name = "last_name", length = 100, nullable = false)
	//@SETMISFieldValidation(className = UserValidationService.class, method = "validateName", paramClass = String.class, message = "Validation Failed For SETMIS Last Name <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided </li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ`' -</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or 0 or TEST or %ONTBREEK% or NIL</li></ul>", fieldName = "lastName", fieldValue = "NOT_NULL")
	private String lastName;

	@Column(name = "identity_number", length = 100, nullable = true)
	//@SETMISFieldValidation(className = UserValidationService.class, method = "validateID", paramClass = String.class, message = "Validation Failed For SETMIS ID Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters 1234567890</li><li>Value must have a length of exactly 13</li><li>Field value may not have characters 0000 from characters 7 to 10</li><li>Field may not have characters 0000 from characters 1 to 4</li><li>Uppercase value in field may not contain characters 1111111111111 or 2222222222222 or 3333333333333 or 4444444444444 or 5555555555555 or 6666666666666 or 7777777777777 or 8888888888888 or 9999999999999</li></ul>", fieldName = "identityNumber", fieldValue = "NOT_NULL")
	private String identityNumber;

	@Column(name = "passport_number", length = 100, nullable = true)
	//@SETMISFieldValidation(className = UserValidationService.class, method = "validateAltID", paramClass = String.class, message = "Validation Failed For SETMIS Passport Number <ul><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890@_</li><li>Uppercase value in field may not contain characters %UNKNOWN% or %AS ABOVE% or %SOOS BO% or %DELETE% or N/A or NA or U or NONE or GEEN or 0 or TEST or %ONTBREEK% or NIL or -</li></ul>", fieldName = "passportNumber", fieldValue = "NOT_NULL")
	private String passportNumber;

	@Column(name = "nlrd_number", length = 100, nullable = true)
	private String nlrdNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@Enumerated(EnumType.STRING)
	@Column(name = "sme_type")
	private SmeTypeEnum smeType;

	/**
	 * If user is already on the database
	 */
	@Column(name = "existing_user")
	private Boolean existingUser;

	/** The residential address if no site assigned. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "residential_address_id", nullable = true)
	//@SETMISFieldValidation(process = true , fieldName = "residentialAddress", fieldValue = "NOT_NULL")
	private Address residentialAddress;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delete_user_id", nullable = true)
	//@SETMISFieldValidation(process = true , fieldName = "deleteUser", fieldValue = "NOT_NULL")
	private Users deleteUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approve_user_id", nullable = true)
	//@SETMISFieldValidation(process = true , fieldName = "approveUser", fieldValue = "NOT_NULL")
	private Users approveUser;

	// approval enum
	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	private Date approvedDate;

	/**
	 * If the SME is active or de actived
	 */
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "lock_sme")
	private Boolean lockSme;
	
	@Column(name = "soft_delete")
	private Boolean softDelete;
	/**  target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/**  target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;
	
	/**  target class for task. */
	@Column(name = "reason", nullable = true)
	private String reason;
	
	@Transient
	private List<Doc> docs;
	
	@Transient
	private List<SmeQualifications> smeQualificationsList;
	
	public SitesSme() {
		super();
	}

	public SitesSme(Company company) {
		super();
		this.company = company;
	}

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
		SitesSme other = (SitesSme) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Sites getSites() {
		return sites;
	}

	public void setSites(Sites sites) {
		this.sites = sites;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public Users getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(Users deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Boolean getUseCompanyAddress() {
		return useCompanyAddress;
	}

	public void setUseCompanyAddress(Boolean useCompanyAddress) {
		this.useCompanyAddress = useCompanyAddress;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getExistingUser() {
		return existingUser;
	}

	public void setExistingUser(Boolean existingUser) {
		this.existingUser = existingUser;
	}

	/**
	 * @return the nlrdNumber
	 */
	public String getNlrdNumber() {
		return nlrdNumber;
	}

	/**
	 * @param nlrdNumber
	 *            the nlrdNumber to set
	 */
	public void setNlrdNumber(String nlrdNumber) {
		this.nlrdNumber = nlrdNumber;
	}

	/**
	 * @return the qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * @param qualification
	 *            the qualification to set
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * @return the smeType
	 */
	public SmeTypeEnum getSmeType() {
		return smeType;
	}

	/**
	 * @param smeType
	 *            the smeType to set
	 */
	public void setSmeType(SmeTypeEnum smeType) {
		this.smeType = smeType;
	}

	public List<SmeQualifications> getSmeQualificationsList() {
		return smeQualificationsList;
	}

	public void setSmeQualificationsList(List<SmeQualifications> smeQualificationsList) {
		this.smeQualificationsList = smeQualificationsList;
	}

	public Users getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(Users approveUser) {
		this.approveUser = approveUser;
	}

	public Boolean getSoftDelete() {
		return softDelete;
	}

	public void setSoftDelete(Boolean softDelete) {
		this.softDelete = softDelete;
	}

	/**
	 * @return the targetKey
	 */
	public Long getTargetKey() {
		return targetKey;
	}

	/**
	 * @param targetKey the targetKey to set
	 */
	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	/**
	 * @return the targetClass
	 */
	public String getTargetClass() {
		return targetClass;
	}

	/**
	 * @param targetClass the targetClass to set
	 */
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getLockSme() {
		return lockSme;
	}

	public void setLockSme(Boolean lockSme) {
		this.lockSme = lockSme;
	}
}
