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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesImportTraining.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "employees_import_training")
public class EmployeesImportTraining implements IDataEntity {
	
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
	
	/** The employees import. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employees_import_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private EmployeesImport employeesImport;
	
	/** The completed planned. */
	@Enumerated
	@Column(name = "completed_planned")
	private CompletedPlannedEnum completedPlanned;
	
	
	/** The pivotal nonpivotal. */
	// Pivotal Non Pivotal
	@Enumerated
	@Column(name = "pivotal_nonpivotal")
	private PivotNonPivotEnum pivotalNonpivotal;
	
	// NQF Level
//	@Column(name = "nqf_level", length = 100)
//	@CSVAnnotation(name = "nqf_level", className = String.class)
//	private String nqfLevel;
//	
	/** The intervention level. */
	// Intervention Level
	@Column(name = "intervention_level", length = 100)
	@CSVAnnotation(name = "intervention_level", className = String.class)
	private String interventionLevel;
	
	/** The saqa ID. */
	// SAQA ID
	@Column(name = "saqa_id", length = 100)
	@CSVAnnotation(name = "saqa_id", className = String.class)
	private String saqaID;
	
	/** The intervention title. */
	// Intervention Title
	@Column(name = "intervention_title", length = 100)
	@CSVAnnotation(name = "intervention_title", className = String.class)
	private String interventionTitle;
	
	/** The provider name. */
	// Provider Name
	@Column(name = "provider_name", length = 100)
	@CSVAnnotation(name = "provider_name", className = String.class)
	private String providerName;
	
	/** The etqa. */
	// ETQA
	@Column(name = "etqa", length = 100)
	@CSVAnnotation(name = "etqa", className = String.class)
	private String etqa;
	
	/** The accreditation number. */
	// Accreditation Number
	@Column(name = "accreditation_number", length = 100)
	@CSVAnnotation(name = "accreditation_number", className = String.class)
	private String accreditationNumber;
	
	/** The intervention cost. */
	// Intervention Cost
	@Column(name = "intervention_cost", length = 100)
	@CSVAnnotation(name = "intervention_cost", className = String.class)
	private String interventionCost;
	
	/** The source of funding. */
	// Source of funding
	@Column(name = "source_of_funding", length = 100)
	@CSVAnnotation(name = "source_of_funding", className = String.class)
	private String sourceOfFunding;

	
	/** The emp unique id. */
	//Foreign system unique_id of employee
	@Column(name = "emp_unique_id", length=200)
	@CSVAnnotation(name = "emp_unique_id", className = String.class)
	private String empUniqueId;
	
	
	/** The imported. */
	@Column(name = "imported",  columnDefinition="BIT default false")
	private Boolean imported;
	
	
	
	/** The error. */
	@Column(name = "error", length=1000)
	private String error;
	
	/** The error sort. */
	@Column(name = "error_sort",  columnDefinition="BIGINT(20) default 999999")
	private Long errorSort;
	
	
	/** The employee training. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_training_id", nullable = true)
	private EmployeesTraining employeeTraining;
	
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
		EmployeesImportTraining other = (EmployeesImportTraining) obj;
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
	 * @param id            the id to set
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
	 * @param createDate            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the employees import.
	 *
	 * @return the employees import
	 */
	public EmployeesImport getEmployeesImport() {
		return employeesImport;
	}

	/**
	 * Sets the employees import.
	 *
	 * @param employeesImport the new employees import
	 */
	public void setEmployeesImport(EmployeesImport employeesImport) {
		this.employeesImport = employeesImport;
	}

	/**
	 * Gets the completed planned.
	 *
	 * @return the completed planned
	 */
	public CompletedPlannedEnum getCompletedPlanned() {
		return completedPlanned;
	}

	/**
	 * Sets the completed planned.
	 *
	 * @param completedPlanned the new completed planned
	 */
	public void setCompletedPlanned(CompletedPlannedEnum completedPlanned) {
		this.completedPlanned = completedPlanned;
	}


	/**
	 * Gets the intervention level.
	 *
	 * @return the intervention level
	 */
	public String getInterventionLevel() {
		return interventionLevel;
	}

	/**
	 * Sets the intervention level.
	 *
	 * @param interventionLevel the new intervention level
	 */
	public void setInterventionLevel(String interventionLevel) {
		this.interventionLevel = interventionLevel;
	}

	/**
	 * Gets the saqa ID.
	 *
	 * @return the saqa ID
	 */
	public String getSaqaID() {
		return saqaID;
	}

	/**
	 * Sets the saqa ID.
	 *
	 * @param saqaID the new saqa ID
	 */
	public void setSaqaID(String saqaID) {
		this.saqaID = saqaID;
	}

	/**
	 * Gets the intervention title.
	 *
	 * @return the intervention title
	 */
	public String getInterventionTitle() {
		return interventionTitle;
	}

	/**
	 * Sets the intervention title.
	 *
	 * @param interventionTitle the new intervention title
	 */
	public void setInterventionTitle(String interventionTitle) {
		this.interventionTitle = interventionTitle;
	}

	/**
	 * Gets the provider name.
	 *
	 * @return the provider name
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * Sets the provider name.
	 *
	 * @param providerName the new provider name
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * Gets the etqa.
	 *
	 * @return the etqa
	 */
	public String getEtqa() {
		return etqa;
	}

	/**
	 * Sets the etqa.
	 *
	 * @param etqa the new etqa
	 */
	public void setEtqa(String etqa) {
		this.etqa = etqa;
	}

	/**
	 * Gets the accreditation number.
	 *
	 * @return the accreditation number
	 */
	public String getAccreditationNumber() {
		return accreditationNumber;
	}

	/**
	 * Sets the accreditation number.
	 *
	 * @param accreditationNumber the new accreditation number
	 */
	public void setAccreditationNumber(String accreditationNumber) {
		this.accreditationNumber = accreditationNumber;
	}

	/**
	 * Gets the intervention cost.
	 *
	 * @return the intervention cost
	 */
	public String getInterventionCost() {
		return interventionCost;
	}

	/**
	 * Sets the intervention cost.
	 *
	 * @param interventionCost the new intervention cost
	 */
	public void setInterventionCost(String interventionCost) {
		this.interventionCost = interventionCost;
	}

	/**
	 * Gets the source of funding.
	 *
	 * @return the source of funding
	 */
	public String getSourceOfFunding() {
		return sourceOfFunding;
	}

	/**
	 * Sets the source of funding.
	 *
	 * @param sourceOfFunding the new source of funding
	 */
	public void setSourceOfFunding(String sourceOfFunding) {
		this.sourceOfFunding = sourceOfFunding;
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
	 * @param imported the new imported
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

	/**
	 * Sets the error.
	 *
	 * @param error the new error
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
	 * @param errorSort the new error sort
	 */
	public void setErrorSort(Long errorSort) {
		this.errorSort = errorSort;
	}



	/**
	 * Gets the emp unique id.
	 *
	 * @return the emp unique id
	 */
	public String getEmpUniqueId() {
		return empUniqueId;
	}

	/**
	 * Sets the emp unique id.
	 *
	 * @param empUniqueId the new emp unique id
	 */
	public void setEmpUniqueId(String empUniqueId) {
		this.empUniqueId = empUniqueId;
	}

	/**
	 * Gets the employee training.
	 *
	 * @return the employee training
	 */
	public EmployeesTraining getEmployeeTraining() {
		return employeeTraining;
	}

	/**
	 * Sets the employee training.
	 *
	 * @param employeeTraining the new employee training
	 */
	public void setEmployeeTraining(EmployeesTraining employeeTraining) {
		this.employeeTraining = employeeTraining;
	}

	/**
	 * Gets the pivotal nonpivotal.
	 *
	 * @return the pivotal nonpivotal
	 */
	public PivotNonPivotEnum getPivotalNonpivotal() {
		return pivotalNonpivotal;
	}

	/**
	 * Sets the pivotal nonpivotal.
	 *
	 * @param pivotalNonpivotal the new pivotal nonpivotal
	 */
	public void setPivotalNonpivotal(PivotNonPivotEnum pivotalNonpivotal) {
		this.pivotalNonpivotal = pivotalNonpivotal;
	}




}
