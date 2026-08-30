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

import haj.com.entity.enums.AgeGroupEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.lookup.DisabilityStatus;
import haj.com.entity.lookup.EmploymentType;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Gender;
import haj.com.entity.lookup.Nationality;
import haj.com.entity.lookup.OccupationCategory;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationType;
import haj.com.framework.IDataEntity;
import haj.com.validators.CheckID;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "employees_history")
public class EmployeesHistory implements IDataEntity,Cloneable {

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
	@Fetch(FetchMode.JOIN)
	private Wsp wsp;

	/** The wsp. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Company company;

	/** The sdl number. */
	@Column(name = "sdl_number", length = 50)
	private String sdlNumber;

	/** The site number. */
	@Column(name = "site_number", length = 50)
	private String siteNumber;

	@Column(name = "site_name", length = 50)
	private String siteName;
	
	/** The sites. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Sites site;

	/** The municipality. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipality_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Municipality municipality;

	/** The id type. */
	@Column(name = "id_type")
	@Enumerated
	private IdPassportEnum idType;

	/** The rsa ID number. */
	@CheckID(message = "RSA ID number not valid")
	@Column(name = "rsa_id_number", length = 13, nullable = true)
	private String rsaIDNumber;

	/** The passport number. */
	@Column(name = "passport_number", length = 30, nullable = true)
	private String passportNumber;

	/** The first name. */
	// First Name
	@Column(name = "first_name", length = 100)
	private String firstName;
	// Last Name

	/** The last name. */
	@Column(name = "last_name", length = 100)
	private String lastName;
	// Date of Birth

	/** The date of birth. */
	@Column(name = "date_of_birth", nullable = true)
	private Date dateOfBirth;

	/** The gender. */
	// Gender
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gender_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Gender gender;

	/** The equity. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equity_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Equity equity;

	/** The disability. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disability_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private DisabilityStatus disability;

	/** The ofo code. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_code_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private OfoCodes ofoCode;

	/** The job title. */
	@Column(name = "job_title", length = 100)
	private String jobTitle;

	/** The occupation category. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "occupation_category_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private OccupationCategory occupationCategory;

	/** The completed training. */
	@Column(name = "completed_training", columnDefinition = "BIT default false")
	private Boolean completedTraining;

	/** The planned training. */
	@Column(name = "planned_training", columnDefinition = "BIT default false")
	private Boolean plannedTraining;

	/** The highest qual title. */
	// Highest Qual Title
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "highest_qual_title", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Qualification highestQualTitle;

	/** The highest qual type. */
	// Highest Qual Type
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "highest_qual_type", nullable = true)
	@Fetch(FetchMode.JOIN)
	private QualificationType highestQualType;

	/** The employment type. */
	// Employment Type
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employment_type_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private EmploymentType employmentType;
	// Employment Status

	/** The employment status. */
	@Column(name = "employment_status")
	@Enumerated
	private EmploymentStatusEnum employmentStatus;

	/** The nationality. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nationality_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Nationality nationality;

	/** The employed un employed. */
	@Enumerated
	@Column(name = "employed_unemployed")
	private EmployedUnEmployedEnum employedUnEmployed;

	/** The age group. */
	@Enumerated
	@Column(name = "age_group")
	private AgeGroupEnum ageGroup;

	/** The pivotal training done. */
	@Transient
	private List<EmployeesTraining> pivotalTrainingDone;

	/** The pivotal training planned. */
	@Transient
	private List<EmployeesTraining> pivotalTrainingPlanned;

	/** The nonpivotal training done. */
	@Transient
	private List<EmployeesTraining> nonpivotalTrainingDone;

	/** The nonpivotal training planned. */
	@Transient
	private List<EmployeesTraining> nonpivotalTrainingPlanned;
	

	/**The Approval Status*/
	@Enumerated
	@Column(name="approval_status")
	private ApprovalEnum approvalStatus;
	
	/**The For Employees*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="for_employees")
	private Employees forEmployees;


	public EmployeesHistory(Employees employees) 
	{
		this.forEmployees=employees;
	}
	
	

	public EmployeesHistory() {
		super();
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
		EmployeesHistory other = (EmployeesHistory) obj;
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
	 * Gets the municipality.
	 *
	 * @return the municipality
	 */
	public Municipality getMunicipality() {
		return municipality;
	}

	/**
	 * Sets the municipality.
	 *
	 * @param municipality
	 *            the new municipality
	 */
	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}

	/**
	 * Gets the id type.
	 *
	 * @return the id type
	 */
	public IdPassportEnum getIdType() {
		return idType;
	}

	/**
	 * Sets the id type.
	 *
	 * @param idType
	 *            the new id type
	 */
	public void setIdType(IdPassportEnum idType) {
		this.idType = idType;
	}

	/**
	 * Gets the rsa ID number.
	 *
	 * @return the rsa ID number
	 */
	public String getRsaIDNumber() {
		return rsaIDNumber;
	}

	/**
	 * Sets the rsa ID number.
	 *
	 * @param rsaIDNumber
	 *            the new rsa ID number
	 */
	public void setRsaIDNumber(String rsaIDNumber) {
		this.rsaIDNumber = rsaIDNumber;
	}

	/**
	 * Gets the passport number.
	 *
	 * @return the passport number
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 *
	 * @param passportNumber
	 *            the new passport number
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
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
	public Gender getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender
	 *            the new gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Gets the equity.
	 *
	 * @return the equity
	 */
	public Equity getEquity() {
		return equity;
	}

	/**
	 * Sets the equity.
	 *
	 * @param equity
	 *            the new equity
	 */
	public void setEquity(Equity equity) {
		this.equity = equity;
	}

	/**
	 * Gets the disability.
	 *
	 * @return the disability
	 */
	public DisabilityStatus getDisability() {
		return disability;
	}

	/**
	 * Sets the disability.
	 *
	 * @param disability
	 *            the new disability
	 */
	public void setDisability(DisabilityStatus disability) {
		this.disability = disability;
	}

	/**
	 * Gets the ofo code.
	 *
	 * @return the ofo code
	 */
	public OfoCodes getOfoCode() {
		return ofoCode;
	}

	/**
	 * Sets the ofo code.
	 *
	 * @param ofoCode
	 *            the new ofo code
	 */
	public void setOfoCode(OfoCodes ofoCode) {
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
	 * Gets the completed training.
	 *
	 * @return the completed training
	 */
	public Boolean getCompletedTraining() {
		return completedTraining;
	}

	/**
	 * Sets the completed training.
	 *
	 * @param completedTraining
	 *            the new completed training
	 */
	public void setCompletedTraining(Boolean completedTraining) {
		this.completedTraining = completedTraining;
	}

	/**
	 * Gets the planned training.
	 *
	 * @return the planned training
	 */
	public Boolean getPlannedTraining() {
		return plannedTraining;
	}

	/**
	 * Sets the planned training.
	 *
	 * @param plannedTraining
	 *            the new planned training
	 */
	public void setPlannedTraining(Boolean plannedTraining) {
		this.plannedTraining = plannedTraining;
	}

	/**
	 * Gets the highest qual title.
	 *
	 * @return the highest qual title
	 */
	public Qualification getHighestQualTitle() {
		return highestQualTitle;
	}

	/**
	 * Sets the highest qual title.
	 *
	 * @param highestQualTitle
	 *            the new highest qual title
	 */
	public void setHighestQualTitle(Qualification highestQualTitle) {
		this.highestQualTitle = highestQualTitle;
	}

	/**
	 * Gets the highest qual type.
	 *
	 * @return the highest qual type
	 */
	public QualificationType getHighestQualType() {
		return highestQualType;
	}

	/**
	 * Sets the highest qual type.
	 *
	 * @param highestQualType
	 *            the new highest qual type
	 */
	public void setHighestQualType(QualificationType highestQualType) {
		this.highestQualType = highestQualType;
	}

	/**
	 * Gets the employment type.
	 *
	 * @return the employment type
	 */
	public EmploymentType getEmploymentType() {
		return employmentType;
	}

	/**
	 * Sets the employment type.
	 *
	 * @param employmentType
	 *            the new employment type
	 */
	public void setEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * Gets the employment status.
	 *
	 * @return the employment status
	 */
	public EmploymentStatusEnum getEmploymentStatus() {
		return employmentStatus;
	}

	/**
	 * Sets the employment status.
	 *
	 * @param employmentStatus
	 *            the new employment status
	 */
	public void setEmploymentStatus(EmploymentStatusEnum employmentStatus) {
		this.employmentStatus = employmentStatus;
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
	 * Gets the occupation category.
	 *
	 * @return the occupation category
	 */
	public OccupationCategory getOccupationCategory() {
		return occupationCategory;
	}

	/**
	 * Sets the occupation category.
	 *
	 * @param occupationCategory
	 *            the new occupation category
	 */
	public void setOccupationCategory(OccupationCategory occupationCategory) {
		this.occupationCategory = occupationCategory;
	}

	/**
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public Nationality getNationality() {
		return nationality;
	}

	/**
	 * Sets the nationality.
	 *
	 * @param nationality
	 *            the new nationality
	 */
	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	/**
	 * Gets the pivotal training done.
	 *
	 * @return the pivotal training done
	 */
	public List<EmployeesTraining> getPivotalTrainingDone() {
		return pivotalTrainingDone;
	}

	/**
	 * Sets the pivotal training done.
	 *
	 * @param pivotalTrainingDone
	 *            the new pivotal training done
	 */
	public void setPivotalTrainingDone(List<EmployeesTraining> pivotalTrainingDone) {
		this.pivotalTrainingDone = pivotalTrainingDone;
	}

	/**
	 * Gets the pivotal training planned.
	 *
	 * @return the pivotal training planned
	 */
	public List<EmployeesTraining> getPivotalTrainingPlanned() {
		return pivotalTrainingPlanned;
	}

	/**
	 * Sets the pivotal training planned.
	 *
	 * @param pivotalTrainingPlanned
	 *            the new pivotal training planned
	 */
	public void setPivotalTrainingPlanned(List<EmployeesTraining> pivotalTrainingPlanned) {
		this.pivotalTrainingPlanned = pivotalTrainingPlanned;
	}

	/**
	 * Gets the nonpivotal training done.
	 *
	 * @return the nonpivotal training done
	 */
	public List<EmployeesTraining> getNonpivotalTrainingDone() {
		return nonpivotalTrainingDone;
	}

	/**
	 * Sets the nonpivotal training done.
	 *
	 * @param nonpivotalTrainingDone
	 *            the new nonpivotal training done
	 */
	public void setNonpivotalTrainingDone(List<EmployeesTraining> nonpivotalTrainingDone) {
		this.nonpivotalTrainingDone = nonpivotalTrainingDone;
	}

	/**
	 * Gets the nonpivotal training planned.
	 *
	 * @return the nonpivotal training planned
	 */
	public List<EmployeesTraining> getNonpivotalTrainingPlanned() {
		return nonpivotalTrainingPlanned;
	}

	/**
	 * Sets the nonpivotal training planned.
	 *
	 * @param nonpivotalTrainingPlanned
	 *            the new nonpivotal training planned
	 */
	public void setNonpivotalTrainingPlanned(List<EmployeesTraining> nonpivotalTrainingPlanned) {
		this.nonpivotalTrainingPlanned = nonpivotalTrainingPlanned;
	}

	/**
	 * Gets the employed un employed.
	 *
	 * @return the employed un employed
	 */
	public EmployedUnEmployedEnum getEmployedUnEmployed() {
		return employedUnEmployed;
	}

	/**
	 * Sets the employed un employed.
	 *
	 * @param employedUnEmployed
	 *            the new employed un employed
	 */
	public void setEmployedUnEmployed(EmployedUnEmployedEnum employedUnEmployed) {
		this.employedUnEmployed = employedUnEmployed;
	}

	/**
	 * Gets the age group.
	 *
	 * @return the age group
	 */
	public AgeGroupEnum getAgeGroup() {
		return ageGroup;
	}

	/**
	 * Sets the age group.
	 *
	 * @param ageGroup
	 *            the new age group
	 */
	public void setAgeGroup(AgeGroupEnum ageGroup) {
		this.ageGroup = ageGroup;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Sites getSite() {
		return site;
	}

	public void setSite(Sites site) {
		this.site = site;
	}

	public ApprovalEnum getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalEnum approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Employees getForEmployees() {
		return forEmployees;
	}

	public void setForEmployees(Employees forEmployees) {
		this.forEmployees = forEmployees;
	}

}
