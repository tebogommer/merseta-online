package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyEmployees.
 */
@Entity
@Table(name = "hosting_company_departments")
public class HostingCompanyDepartments implements IDataEntity {

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

	/** The department. */
	@Column(name = "department", length = 500)
	private String department;
	/**
	 * The hosting company.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hosting_company_id")
	@Fetch(FetchMode.JOIN)
	private HostingCompany hostingCompany;

	
	/** The parent department. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hosting_company_department_parent_id")
	@Fetch(FetchMode.JOIN)
	private HostingCompanyDepartments parentDepartment;
	
	/**
	 * The create date.
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

    /** The hosting company department processes. */
    @Transient
	private List<HostingCompanyDepartmentProcess> hostingCompanyDepartmentProcesses;


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
		HostingCompanyDepartments other = (HostingCompanyDepartments) obj;
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

	/**
	 * Gets the department.
	 *
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Sets the department.
	 *
	 * @param department the new department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * Gets the hosting company department processes.
	 *
	 * @return the hosting company department processes
	 */
	public List<HostingCompanyDepartmentProcess> getHostingCompanyDepartmentProcesses() {
		return hostingCompanyDepartmentProcesses;
	}

	/**
	 * Sets the hosting company department processes.
	 *
	 * @param hostingCompanyDepartmentProcesses the new hosting company department processes
	 */
	public void setHostingCompanyDepartmentProcesses(
			List<HostingCompanyDepartmentProcess> hostingCompanyDepartmentProcesses) {
		this.hostingCompanyDepartmentProcesses = hostingCompanyDepartmentProcesses;
	}

	/**
	 * Gets the parent department.
	 *
	 * @return the parent department
	 */
	public HostingCompanyDepartments getParentDepartment() {
		return parentDepartment;
	}

	/**
	 * Sets the parent department.
	 *
	 * @param parentDepartment the new parent department
	 */
	public void setParentDepartment(HostingCompanyDepartments parentDepartment) {
		this.parentDepartment = parentDepartment;
	}




}
