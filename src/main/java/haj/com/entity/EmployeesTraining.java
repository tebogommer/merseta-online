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

import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.Funding;
import haj.com.entity.lookup.InterventionLevel;
import haj.com.entity.lookup.NqfLevels;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesTraining.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "employees_training")
public class EmployeesTraining implements IDataEntity {
	
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

	/** The employee. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Employees employee;

	/** The completed planned. */
	@Enumerated
	@Column(name = "completed_planned")
	private CompletedPlannedEnum completedPlanned;


	/** The pivot non pivot. */
	@Enumerated
	@Column(name = "pivot_non_pivot")
	private PivotNonPivotEnum pivotNonPivot;

	/** The nqf level. */
	// NQF Level
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nqf_level_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private NqfLevels nqfLevel;	

	
	/** The intervention level. */
	// Intervention level
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_level_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private InterventionLevel interventionLevel;
	
	
	/** The qualification. */
	// Highest Qual Title
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Qualification qualification;

	// Highest Qual Type
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "highest_qual_type", nullable = true)
//	@Fetch(FetchMode.JOIN)
//	private QualificationType highestQualType;

	// Intervention Type
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "intervention_type_id", nullable = true)
//	@Fetch(FetchMode.JOIN)
//	private InterventionType interventionType;

	
	/** The intervention title. */
	// Intervention Title
	@Column(name = "intervention_title", length = 100)
	private String interventionTitle;

	/** The provider name. */
	// Provider Name
	@Column(name = "provider_name", length = 100)
	private String providerName;

	/** The etqa. */
	// ETQA
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "etqa_id", nullable = true)
	@Fetch(FetchMode.JOIN)
	private Etqa etqa;

	/** The accreditation number. */
	// Accreditation Number
	@Column(name = "accreditation_number", length = 100)
	private String accreditationNumber;

	/** The intervention cost. */
	// Intervention Cost
	@Column(name = "intervention_cost")
	private Double interventionCost;
	
	/** The source of funding. */
	// Source of funding
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "source_of_funding", nullable = true)	
	@Fetch(FetchMode.JOIN)
	private Funding sourceOfFunding;

		
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
		EmployeesTraining other = (EmployeesTraining) obj;
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
	 * @param employee the new employee
	 */
	public void setEmployee(Employees employee) {
		this.employee = employee;
	}



	/**
	 * Gets the nqf level.
	 *
	 * @return the nqf level
	 */
	public NqfLevels getNqfLevel() {
		return nqfLevel;
	}

	/**
	 * Sets the nqf level.
	 *
	 * @param nqfLevel the new nqf level
	 */
	public void setNqfLevel(NqfLevels nqfLevel) {
		this.nqfLevel = nqfLevel;
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
	public Double getInterventionCost() {
		return interventionCost;
	}

	/**
	 * Sets the intervention cost.
	 *
	 * @param interventionCost the new intervention cost
	 */
	public void setInterventionCost(Double interventionCost) {
		this.interventionCost = interventionCost;
	}

	/**
	 * Gets the source of funding.
	 *
	 * @return the source of funding
	 */
	public Funding getSourceOfFunding() {
		return sourceOfFunding;
	}

	/**
	 * Sets the source of funding.
	 *
	 * @param sourceOfFunding the new source of funding
	 */
	public void setSourceOfFunding(Funding sourceOfFunding) {
		this.sourceOfFunding = sourceOfFunding;
	}

	/**
	 * Gets the pivot non pivot.
	 *
	 * @return the pivot non pivot
	 */
	public PivotNonPivotEnum getPivotNonPivot() {
		return pivotNonPivot;
	}
 
	/**
	 * Sets the pivot non pivot.
	 *
	 * @param pivotNonPivot the new pivot non pivot
	 */
	public void setPivotNonPivot(PivotNonPivotEnum pivotNonPivot) {
		this.pivotNonPivot = pivotNonPivot;
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
	public InterventionLevel getInterventionLevel() {
		return interventionLevel;
	}

	/**
	 * Sets the intervention level.
	 *
	 * @param interventionLevel the new intervention level
	 */
	public void setInterventionLevel(InterventionLevel interventionLevel) {
		this.interventionLevel = interventionLevel;
	}

	/**
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification the new qualification
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * Gets the etqa.
	 *
	 * @return the etqa
	 */
	public Etqa getEtqa() {
		return etqa;
	}

	/**
	 * Sets the etqa.
	 *
	 * @param etqa the new etqa
	 */
	public void setEtqa(Etqa etqa) {
		this.etqa = etqa;
	}

}
