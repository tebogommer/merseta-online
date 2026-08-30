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
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Email;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyTypeEnum;
import haj.com.entity.lookup.SICCode;
import haj.com.entity.lookup.Seta;
import haj.com.framework.IDataEntity;
import haj.com.validators.CheckTelNumber;

@Entity
@Table(name = "non_seta_company")
@AuditTable(value = "non_seta_company_hist")
@Audited
public class NonSetaCompany implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@Column(name = "company_name", length = 70, nullable = false)
	private String companyName;
	
	@Column(name = "company_guid", length = 100, nullable = true)
	@Size(min = 1, max = 100, message = "Company guid can't be more than 100 characters")
	private String companyGuid;
	
	@Column(name = "levy_number", length = 10)
	private String levyNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "residential_address_id", nullable = true)
	private Address residentialAddress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "postal_address_id", nullable = true)
	private Address postalAddress;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "registered_address_id", nullable = true)
	private Address registeredAddress;
	
	@Column(name = "company_registration_number", length = 40, nullable = true)
	private String companyRegistrationNumber;
	
	@CheckTelNumber(message = "Tel number not valid")
	@Column(name = "tel_number", length = 20, nullable = true)
	private String telNumber;
	
	@CheckTelNumber(message = "Fax number not valid")
	@Column(name = "fax_number", length = 20, nullable = true)
	private String faxNumber;

	@Column(name = "email", length = 50, nullable = true)
	@Size(min = 1, max = 50, message = "Email address can't be less than 1 character or more than 50 characters")
	@Email(message = "Please enter a valid Email Address")
	private String email;
	
	@Enumerated
	@Column(name = "company_status")
	private CompanyStatusEnum companyStatus;
	
	@Enumerated
	@Column(name = "approval_enum")
	private ApprovalEnum approvalEnum;
	
	@Column(name = "accreditation_number", nullable = true)
	private String accreditationNumber;
	
	@Column(name = "company_number", nullable = true)
	private String companyNumber;
	
	/** The company type. */
	@Enumerated
	@Column(name = "company_type")
	private CompanyTypeEnum companyType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sic_code_id", nullable = true)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	private SICCode sicCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "seta_id", nullable = true)
	private Seta seta;
	
	/** The docs. */
	@Transient
	private List<Doc> docs;
	
	/** The done search. */
	@Transient
	private boolean doneSearch;
	
	/** The reg done. */
	@Transient
	private boolean regDone;
	
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
		NonSetaCompany other = (NonSetaCompany) obj;
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

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyGuid() {
		return companyGuid;
	}

	public void setCompanyGuid(String companyGuid) {
		this.companyGuid = companyGuid;
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

	public Address getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(Address registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getCompanyRegistrationNumber() {
		return companyRegistrationNumber;
	}

	public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
		this.companyRegistrationNumber = companyRegistrationNumber;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CompanyStatusEnum getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(CompanyStatusEnum companyStatus) {
		this.companyStatus = companyStatus;
	}

	public ApprovalEnum getApprovalEnum() {
		return approvalEnum;
	}

	public void setApprovalEnum(ApprovalEnum approvalEnum) {
		this.approvalEnum = approvalEnum;
	}

	public String getAccreditationNumber() {
		return accreditationNumber;
	}

	public void setAccreditationNumber(String accreditationNumber) {
		this.accreditationNumber = accreditationNumber;
	}

	public boolean isDoneSearch() {
		return doneSearch;
	}

	public void setDoneSearch(boolean doneSearch) {
		this.doneSearch = doneSearch;
	}

	public boolean isRegDone() {
		return regDone;
	}

	public void setRegDone(boolean regDone) {
		this.regDone = regDone;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	public CompanyTypeEnum getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyTypeEnum companyType) {
		this.companyType = companyType;
	}

	public SICCode getSicCode() {
		return sicCode;
	}

	public void setSicCode(SICCode sicCode) {
		this.sicCode = sicCode;
	}

	public Seta getSeta() {
		return seta;
	}

	public void setSeta(Seta seta) {
		this.seta = seta;
	}

	public String getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}
}
