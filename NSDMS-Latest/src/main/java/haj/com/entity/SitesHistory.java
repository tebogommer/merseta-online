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
import org.hibernate.validator.constraints.Email;

import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Company.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "sites_history")
public class SitesHistory implements IDataEntity,Cloneable {

	public SitesHistory() {
		super();
	}

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	

	public SitesHistory(Sites entity)
	{
		super();
		this.forSites=entity;
	}

	/** Unique Id of Sites. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Sites. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The company name of Sites. */
	@Column(name = "company_name", length = 70)
	//@Size(min = 1, max = 40, message = "Company can't be more than 40 characters")
	private String companyName;

	/** The guid of Sites. */
	@Column(name = "site_number", length = 100, nullable = true)
	private String siteNumber;
	
	/** The guid of Sites. */
	@Column(name = "company_guid", length = 100, nullable = true)
	private String companyGuid;

	/** The registered address of Sites. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "registered_address_id", nullable = true)
	private Address registeredAddress;

	/** The company registration number of Sites. */
	@Column(name = "company_registration_number", length = 40)
	private String companyRegistrationNumber;

	/** The tel number of Sites. */
	@Column(name = "tel_number", length = 20, nullable = true)
	private String telNumber;

	/** The fax number of Sites. */
	@Column(name = "fax_number", length = 20)
	private String faxNumber;

	/** The email of Sites. */
	@Column(name = "email", length = 100, nullable = true)
	@Email(message = "Please enter a valid Email Address")
	private String email;

	/** The number of employees of Sites. */
	@Column(name = "number_of_employees")
	private Integer numberOfEmployees;

	/** The levy number of Sites. */
	@Column(name = "levy_number", length = 40)
	private String levyNumber;

	/** The form user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "form_user_id", nullable = true)
	private Users formUser;

	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	/** The Site status. */
	@Enumerated
	@Column(name = "site_status")
	private CompanyStatusEnum siteStatus;
	
	/**The For Sites*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "for_site_id", nullable = true)
	private Sites forSites;
	
	@Column(name = "for_site_flat_id", nullable = true)
	private Long forSiteFlatId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */

	/** The done search. */
	@Transient
	private boolean doneSearch;
	
	/** The existing company. */
	@Transient
	private boolean existingCompany;
	
	/** The temp update. */
	@Transient
	private boolean tempUpdate;
	
	/** The reg done. */
	@Transient
	private boolean regDone;


	/* (non-Javadoc)
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
		SitesHistory other = (SitesHistory) obj;
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
	 * @param id the new id
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
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName the new company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * Gets the company guid.
	 *
	 * @return the company guid
	 */
	public String getCompanyGuid() {
		return companyGuid;
	}

	/**
	 * Sets the company guid.
	 *
	 * @param companyGuid the new company guid
	 */
	public void setCompanyGuid(String companyGuid) {
		this.companyGuid = companyGuid;
	}

	/**
	 * Gets the registered address.
	 *
	 * @return the registered address
	 */
	public Address getRegisteredAddress() {
		return registeredAddress;
	}

	/**
	 * Sets the registered address.
	 *
	 * @param registeredAddress the new registered address
	 */
	public void setRegisteredAddress(Address registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	/**
	 * Gets the company registration number.
	 *
	 * @return the company registration number
	 */
	public String getCompanyRegistrationNumber() {
		return companyRegistrationNumber;
	}

	/**
	 * Sets the company registration number.
	 *
	 * @param companyRegistrationNumber the new company registration number
	 */
	public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
		this.companyRegistrationNumber = companyRegistrationNumber;
	}

	/**
	 * Gets the tel number.
	 *
	 * @return the tel number
	 */
	public String getTelNumber() {
		return telNumber;
	}

	/**
	 * Sets the tel number.
	 *
	 * @param telNumber the new tel number
	 */
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	/**
	 * Gets the fax number.
	 *
	 * @return the fax number
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * Sets the fax number.
	 *
	 * @param faxNumber the new fax number
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the number of employees.
	 *
	 * @return the number of employees
	 */
	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	/**
	 * Sets the number of employees.
	 *
	 * @param numberOfEmployees the new number of employees
	 */
	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	/**
	 * Gets the levy number.
	 *
	 * @return the levy number
	 */
	public String getLevyNumber() {
		return levyNumber;
	}

	/**
	 * Sets the levy number.
	 *
	 * @param levyNumber the new levy number
	 */
	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	/**
	 * Gets the form user.
	 *
	 * @return the form user
	 */
	public Users getFormUser() {
		return formUser;
	}

	/**
	 * Sets the form user.
	 *
	 * @param formUser the new form user
	 */
	public void setFormUser(Users formUser) {
		this.formUser = formUser;
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
	 * @param company the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Checks if is done search.
	 *
	 * @return true, if is done search
	 */
	public boolean isDoneSearch() {
		return doneSearch;
	}

	/**
	 * Sets the done search.
	 *
	 * @param doneSearch the new done search
	 */
	public void setDoneSearch(boolean doneSearch) {
		this.doneSearch = doneSearch;
	}

	/**
	 * Checks if is existing company.
	 *
	 * @return true, if is existing company
	 */
	public boolean isExistingCompany() {
		return existingCompany;
	}

	/**
	 * Sets the existing company.
	 *
	 * @param existingCompany the new existing company
	 */
	public void setExistingCompany(boolean existingCompany) {
		this.existingCompany = existingCompany;
	}

	/**
	 * Checks if is temp update.
	 *
	 * @return true, if is temp update
	 */
	public boolean isTempUpdate() {
		return tempUpdate;
	}

	/**
	 * Sets the temp update.
	 *
	 * @param tempUpdate the new temp update
	 */
	public void setTempUpdate(boolean tempUpdate) {
		this.tempUpdate = tempUpdate;
	}

	/**
	 * Checks if is reg done.
	 *
	 * @return true, if is reg done
	 */
	public boolean isRegDone() {
		return regDone;
	}

	/**
	 * Sets the reg done.
	 *
	 * @param regDone the new reg done
	 */
	public void setRegDone(boolean regDone) {
		this.regDone = regDone;
	}

	public String getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}

	public CompanyStatusEnum getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(CompanyStatusEnum siteStatus) {
		this.siteStatus = siteStatus;
	}

	public Sites getForSites() {
		return forSites;
	}

	public void setForSites(Sites forSites) {
		this.forSites = forSites;
	}

	public Long getForSiteFlatId() {
		return forSiteFlatId;
	}

	public void setForSiteFlatId(Long forSiteFlatId) {
		this.forSiteFlatId = forSiteFlatId;
	}

}
