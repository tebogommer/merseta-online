package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import haj.com.framework.IDataEntity;


// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyCompanies.
 */
@Entity
@Table(name = "hosting_company_companies")
public class HostingCompanyCompanies implements IDataEntity
{
	
	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The id.
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;


	/**
	 * The company.
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id",  nullable=false)
	private Company company; 
	
	/**
	 * The hosting company.
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hosting_company_id", nullable=false)
	private HostingCompany hostingCompany;
	
    /**
	 * The create date.
	 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date", length=19)
    private Date createDate;
    
//    @Enumerated
//    @Column(name="employee_level")
//    private EmployeeLevelEnum employeeLevel;


    /**
 * The active.
 */
@Column(name="active")
    private Boolean active;
    
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
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}



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

	/* (non-Javadoc)
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
		HostingCompanyCompanies other = (HostingCompanyCompanies) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	/**
	 * Gets the hosting company.
	 *
	 * @return the hosting company
	 */
	public HostingCompany getHostingCompany() {
		return hostingCompany;
	}

	/**
	 * Sets the hosting company.
	 *
	 * @param hostingCompany
	 *            the new hosting company
	 */
	public void setHostingCompany(HostingCompany hostingCompany) {
		this.hostingCompany = hostingCompany;
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
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

//	public EmployeeLevelEnum getEmployeeLevel() {
//		return employeeLevel;
//	}
//
//	public void setEmployeeLevel(EmployeeLevelEnum employeeLevel) {
//		this.employeeLevel = employeeLevel;
//	}

	/**
 * Gets the active.
 *
 * @return the active
 */
public Boolean getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active
	 *            the new active
	 */
	public void setActive(Boolean active) {
		this.active = active;
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




}
