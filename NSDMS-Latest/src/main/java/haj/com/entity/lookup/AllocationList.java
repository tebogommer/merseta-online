package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.OfoCodes;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * CitizenResidentStatus Citizen_Resident_Status_Code.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "allocation_list")
public class AllocationList extends AbstractLookup {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "total_allocation_for_year")
	private Long totalAllocationForYear;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	private InterventionType interventionType;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_codes_id", nullable = true)
	private OfoCodes ofoCodes;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private Qualification qualification;

	@Enumerated(EnumType.STRING)
	@Column(name = "ssp_priority")
	private YesNoEnum sspPriority;

	@Enumerated(EnumType.STRING)
	@Column(name = "employment_status")
	private EmploymentStatusEnum employmentStatus;
	
	@Column(name = "fin_year")
	private Integer finYear;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dgYear_id", nullable = true)
	private DGYear dgYear;

	@Transient
	private Long numberAllocated;

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
		AllocationList other = (AllocationList) obj;
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
	 * @return the totalAllocationForYear
	 */
	public Long getTotalAllocationForYear() {
		return totalAllocationForYear;
	}

	/**
	 * @param totalAllocationForYear
	 *            the totalAllocationForYear to set
	 */
	public void setTotalAllocationForYear(Long totalAllocationForYear) {
		this.totalAllocationForYear = totalAllocationForYear;
	}

	/**
	 * @return the interventionType
	 */
	public InterventionType getInterventionType() {
		return interventionType;
	}

	/**
	 * @param interventionType
	 *            the interventionType to set
	 */
	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
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
	 * @return the sspPriority
	 */
	public YesNoEnum getSspPriority() {
		return sspPriority;
	}

	/**
	 * @param sspPriority
	 *            the sspPriority to set
	 */
	public void setSspPriority(YesNoEnum sspPriority) {
		this.sspPriority = sspPriority;
	}

	/**
	 * @return the ofoCodes
	 */
	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	/**
	 * @param ofoCodes
	 *            the ofoCodes to set
	 */
	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public EmploymentStatusEnum getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(EmploymentStatusEnum employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	/**
	 * @return the numberAllocated
	 */
	public Long getNumberAllocated() {
		return numberAllocated;
	}

	/**
	 * @param numberAllocated
	 *            the numberAllocated to set
	 */
	public void setNumberAllocated(Long numberAllocated) {
		this.numberAllocated = numberAllocated;
	}

	public Integer getFinYear() {
		return finYear;
	}

	public void setFinYear(Integer finYear) {
		this.finYear = finYear;
	}

	public DGYear getDgYear() {
		return dgYear;
	}

	public void setDgYear(DGYear dgYear) {
		this.dgYear = dgYear;
	}

}
