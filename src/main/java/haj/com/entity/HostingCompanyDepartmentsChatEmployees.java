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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.lookup.Roles;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyEmployees.
 */
@Entity
@Table(name = "hosting_company_departments_chat_employees")
public class HostingCompanyDepartmentsChatEmployees implements IDataEntity {

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

	/** The hosting company departments. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hosting_company_department_id")
	@Fetch(FetchMode.JOIN)
	private HostingCompanyDepartments hostingCompanyDepartments;

	/** The hosting company employees. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hosting_company_employees_id")
	@Fetch(FetchMode.JOIN)
	private HostingCompanyEmployees hostingCompanyEmployees;

	/**
	 * The create date.
	 */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;





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
		HostingCompanyDepartmentsChatEmployees other = (HostingCompanyDepartmentsChatEmployees) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Gets the hosting company departments.
	 *
	 * @return the hosting company departments
	 */
	public HostingCompanyDepartments getHostingCompanyDepartments() {
		return hostingCompanyDepartments;
	}

	/**
	 * Sets the hosting company departments.
	 *
	 * @param hostingCompanyDepartments the new hosting company departments
	 */
	public void setHostingCompanyDepartments(HostingCompanyDepartments hostingCompanyDepartments) {
		this.hostingCompanyDepartments = hostingCompanyDepartments;
	}

	/**
	 * Gets the hosting company employees.
	 *
	 * @return the hosting company employees
	 */
	public HostingCompanyEmployees getHostingCompanyEmployees() {
		return hostingCompanyEmployees;
	}

	/**
	 * Sets the hosting company employees.
	 *
	 * @param hostingCompanyEmployees the new hosting company employees
	 */
	public void setHostingCompanyEmployees(HostingCompanyEmployees hostingCompanyEmployees) {
		this.hostingCompanyEmployees = hostingCompanyEmployees;
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






}
