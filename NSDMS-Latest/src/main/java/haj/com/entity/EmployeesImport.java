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

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "employees_import")
public class EmployeesImport implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** The create date. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	/** The unique id. */
	// Foreign system unique_id
	@Column(name = "unique_id", length = 200)
	@CSVAnnotation(name = "unique_id", className = String.class)
	private String uniqueId;

	/** The sdl number. */
	// SDL Number
	@Column(name = "sdl_number")
	@CSVAnnotation(name = "entity_id", className = String.class)
	private String sdlNumber;

	/** The site number. */
	// Site Number
	@Column(name = "site_number", length = 50)
	@CSVAnnotation(name = "site_name", className = String.class)
	private String siteNumber;

	/** The municipality. */
	// Municipality
	@Column(name = "municipality")
	@CSVAnnotation(name = "municipality_code", className = String.class)
	private String municipality;

	/** The employee ID. */
	// Employee's ID
	@Column(name = "employee_id")
	@CSVAnnotation(name = "employee_id", className = String.class)
	private String employeeID;

	/** The id type. */
	// ID Type
	@Column(name = "id_type")
	@CSVAnnotation(name = "id_type", className = String.class)
	private String idType;

	/** The first name. */
	// First Name
	@Column(name = "first_name", length = 100)
	@CSVAnnotation(name = "first_name", className = String.class)
	private String firstName;

	/** The last name. */
	// Last Name
	@Column(name = "last_name", length = 100)
	@CSVAnnotation(name = "last_name", className = String.class)
	private String lastName;

	/** The date of birth. */
	// Date of Birth
	@Column(name = "date_of_birth", length = 30)
	@Temporal(TemporalType.TIMESTAMP)
	@CSVAnnotation(name = "date_of_birth", className = Date.class, datePattern = "dd-MM-yyyy")
	private Date dateOfBirth;

	/** The gender. */
	// Gender
	@Column(name = "gender", length = 100)
	@CSVAnnotation(name = "gender", className = String.class)
	private String gender;

	/** The equity. */
	// Equity
	@Column(name = "equity", length = 100)
	@CSVAnnotation(name = "equity", className = String.class)
	private String equity;

	/** The disability. */
	// Disability
	@Column(name = "disability", length = 100)
	@CSVAnnotation(name = "disability", className = String.class)
	private String disability;

	/** The ofo code. */
	// OFO Code
	@Column(name = "ofo_code", length = 100)
	@CSVAnnotation(name = "ofo_code", className = String.class)
	private String ofoCode;

	@Column(name = "specialisation_code", length = 100)
	@CSVAnnotation(name = "specialisation_code", className = String.class)
	private String specialisationCode;

	/** The job title. */
	// Job Title
	@Column(name = "job_title", length = 100)
	@CSVAnnotation(name = "job_title", className = String.class)
	private String jobTitle;

	/** The occupation category. */
	// Occupation
	@Column(name = "occupation_category", length = 100)
	@CSVAnnotation(name = "occupation_category", className = String.class)
	private String occupationCategory;

	/** The highest qual title. */
	// Highest Qual Title
	@Column(name = "highest_qual_code", length = 100)
	@CSVAnnotation(name = "highest_qual_code", className = String.class)
	private String highestQualTitle;

	/** The highest qual type. */
	// Highest Qual Type
	@Column(name = "highest_qual_type", length = 100)
	@CSVAnnotation(name = "highest_qual_type", className = String.class)
	private String highestQualType;

	/** The employment type. */
	// Employment Type
	@Column(name = "employment_type", length = 100)
	@CSVAnnotation(name = "employment_type", className = String.class)
	private String employmentType;

	/** The employment status. */
	// Employment Status
	@Column(name = "employment_status", length = 100)
	@CSVAnnotation(name = "employment_status", className = String.class)
	private String employmentStatus;

	/** The nationality. */
	// Nationality
	@Column(name = "nationality", length = 100)
	@CSVAnnotation(name = "nationality", className = String.class)
	private String nationality;

	/** The imported. */
	@Column(name = "imported", columnDefinition = "BIT default false")
	private Boolean imported;

	/** The error. */
	@Column(name = "error", length = 1000)
	private String error;

	/** The error sort. */
	@Column(name = "error_sort", columnDefinition = "BIGINT(20) default 999999")
	private Long errorSort;

	/** The employee. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", nullable = true)
	private Employees employee;

	/** The pivotal training done. */
	@Transient
	private List<EmployeesImportTraining> pivotalTrainingDone;

	/** The pivotal training planned. */
	@Transient
	private List<EmployeesImportTraining> pivotalTrainingPlanned;

	/** The nonpivotal training done. */
	@Transient
	private List<EmployeesImportTraining> nonpivotalTrainingDone;

	/** The nonpivotal training planned. */
	@Transient
	private List<EmployeesImportTraining> nonpivotalTrainingPlanned;

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
		EmployeesImport other = (EmployeesImport) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
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

	/**
	 * Gets the sdl number.
	 *
	 * @return the sdl number
	 */
	public String getSdlNumber() {
		return sdlNumber;
	}

	/**
	 * Sets the sdl number.
	 *
	 * @param sdlNumber
	 *            the new sdl number
	 */
	public void setSdlNumber(String sdlNumber) {
		this.sdlNumber = sdlNumber;
	}

	/**
	 * Gets the municipality.
	 *
	 * @return the municipality
	 */
	public String getMunicipality() {
		return municipality;
	}

	/**
	 * Sets the municipality.
	 *
	 * @param municipality
	 *            the new municipality
	 */
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	/**
	 * Gets the employee ID.
	 *
	 * @return the employee ID
	 */
	public String getEmployeeID() {
		return employeeID;
	}

	/**
	 * Sets the employee ID.
	 *
	 * @param employeeID
	 *            the new employee ID
	 */
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	/**
	 * Gets the id type.
	 *
	 * @return the id type
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * Sets the id type.
	 *
	 * @param idType
	 *            the new id type
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the date of birth.
	 *
	 * @param dateOfBirth
	 *            the new date of birth
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender
	 *            the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the equity.
	 *
	 * @return the equity
	 */
	public String getEquity() {
		return equity;
	}

	/**
	 * Sets the equity.
	 *
	 * @param equity
	 *            the new equity
	 */
	public void setEquity(String equity) {
		this.equity = equity;
	}

	/**
	 * Gets the disability.
	 *
	 * @return the disability
	 */
	public String getDisability() {
		return disability;
	}

	/**
	 * Sets the disability.
	 *
	 * @param disability
	 *            the new disability
	 */
	public void setDisability(String disability) {
		this.disability = disability;
	}

	/**
	 * Gets the ofo code.
	 *
	 * @return the ofo code
	 */
	public String getOfoCode() {
		return ofoCode;
	}

	/**
	 * Sets the ofo code.
	 *
	 * @param ofoCode
	 *            the new ofo code
	 */
	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	/**
	 * Gets the job title.
	 *
	 * @return the job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title.
	 *
	 * @param jobTitle
	 *            the new job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * Gets the highest qual title.
	 *
	 * @return the highest qual title
	 */
	public String getHighestQualTitle() {
		return highestQualTitle;
	}

	/**
	 * Sets the highest qual title.
	 *
	 * @param highestQualTitle
	 *            the new highest qual title
	 */
	public void setHighestQualTitle(String highestQualTitle) {
		this.highestQualTitle = highestQualTitle;
	}

	/**
	 * Gets the highest qual type.
	 *
	 * @return the highest qual type
	 */
	public String getHighestQualType() {
		return highestQualType;
	}

	/**
	 * Sets the highest qual type.
	 *
	 * @param highestQualType
	 *            the new highest qual type
	 */
	public void setHighestQualType(String highestQualType) {
		this.highestQualType = highestQualType;
	}

	/**
	 * Gets the employment type.
	 *
	 * @return the employment type
	 */
	public String getEmploymentType() {
		return employmentType;
	}

	/**
	 * Sets the employment type.
	 *
	 * @param employmentType
	 *            the new employment type
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * Gets the employment status.
	 *
	 * @return the employment status
	 */
	public String getEmploymentStatus() {
		return employmentStatus;
	}

	/**
	 * Sets the employment status.
	 *
	 * @param employmentStatus
	 *            the new employment status
	 */
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	/**
	 * Gets the occupation category.
	 *
	 * @return the occupation category
	 */
	public String getOccupationCategory() {
		return occupationCategory;
	}

	/**
	 * Sets the occupation category.
	 *
	 * @param occupationCategory
	 *            the new occupation category
	 */
	public void setOccupationCategory(String occupationCategory) {
		this.occupationCategory = occupationCategory;
	}

	/**
	 * Gets the site number.
	 *
	 * @return the site number
	 */
	public String getSiteNumber() {
		return siteNumber;
	}

	/**
	 * Sets the site number.
	 *
	 * @param siteNumber
	 *            the new site number
	 */
	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp
	 *            the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Sets the nationality.
	 *
	 * @param nationality
	 *            the new nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the imported.
	 *
	 * @return the imported
	 */
	public Boolean getImported() {
		return imported;
	}

	/**
	 * Sets the imported.
	 *
	 * @param imported
	 *            the new imported
	 */
	public void setImported(Boolean imported) {
		this.imported = imported;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	public String getErrorS() {
		if (error != null) return error.replaceAll("<li>", "").replaceAll("</li>", ",");
		return "";
	}

	/**
	 * Sets the error.
	 *
	 * @param error
	 *            the new error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Gets the error sort.
	 *
	 * @return the error sort
	 */
	public Long getErrorSort() {
		return errorSort;
	}

	/**
	 * Sets the error sort.
	 *
	 * @param errorSort
	 *            the new error sort
	 */
	public void setErrorSort(Long errorSort) {
		this.errorSort = errorSort;
	}

	/**
	 * Gets the employee.
	 *
	 * @return the employee
	 */
	public Employees getEmployee() {
		return employee;
	}

	/**
	 * Sets the employee.
	 *
	 * @param employee
	 *            the new employee
	 */
	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	/**
	 * Gets the pivotal training done.
	 *
	 * @return the pivotal training done
	 */
	public List<EmployeesImportTraining> getPivotalTrainingDone() {
		return pivotalTrainingDone;
	}

	/**
	 * Sets the pivotal training done.
	 *
	 * @param pivotalTrainingDone
	 *            the new pivotal training done
	 */
	public void setPivotalTrainingDone(List<EmployeesImportTraining> pivotalTrainingDone) {
		this.pivotalTrainingDone = pivotalTrainingDone;
	}

	/**
	 * Gets the pivotal training planned.
	 *
	 * @return the pivotal training planned
	 */
	public List<EmployeesImportTraining> getPivotalTrainingPlanned() {
		return pivotalTrainingPlanned;
	}

	/**
	 * Sets the pivotal training planned.
	 *
	 * @param pivotalTrainingPlanned
	 *            the new pivotal training planned
	 */
	public void setPivotalTrainingPlanned(List<EmployeesImportTraining> pivotalTrainingPlanned) {
		this.pivotalTrainingPlanned = pivotalTrainingPlanned;
	}

	/**
	 * Gets the nonpivotal training done.
	 *
	 * @return the nonpivotal training done
	 */
	public List<EmployeesImportTraining> getNonpivotalTrainingDone() {
		return nonpivotalTrainingDone;
	}

	/**
	 * Sets the nonpivotal training done.
	 *
	 * @param nonpivotalTrainingDone
	 *            the new nonpivotal training done
	 */
	public void setNonpivotalTrainingDone(List<EmployeesImportTraining> nonpivotalTrainingDone) {
		this.nonpivotalTrainingDone = nonpivotalTrainingDone;
	}

	/**
	 * Gets the nonpivotal training planned.
	 *
	 * @return the nonpivotal training planned
	 */
	public List<EmployeesImportTraining> getNonpivotalTrainingPlanned() {
		return nonpivotalTrainingPlanned;
	}

	/**
	 * Sets the nonpivotal training planned.
	 *
	 * @param nonpivotalTrainingPlanned
	 *            the new nonpivotal training planned
	 */
	public void setNonpivotalTrainingPlanned(List<EmployeesImportTraining> nonpivotalTrainingPlanned) {
		this.nonpivotalTrainingPlanned = nonpivotalTrainingPlanned;
	}

	/**
	 * Gets the unique id.
	 *
	 * @return the unique id
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Sets the unique id.
	 *
	 * @param uniqueId
	 *            the new unique id
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Gets the company.
	 *
	 * @return the unique id
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

	public String getSpecialisationCode() {
		return specialisationCode;
	}

	public void setSpecialisationCode(String specialisationCode) {
		this.specialisationCode = specialisationCode;
	}

}
