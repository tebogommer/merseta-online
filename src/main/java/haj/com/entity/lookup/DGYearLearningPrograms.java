package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.entity.OfoCodes;
import haj.com.framework.AbstractLookup;

// TODO: Auto-generated Javadoc
/**
 * DGYear.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "dg_year_learning_programs")
public class DGYearLearningPrograms extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Description of AbetBand. */
	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "allocation_amount")
	private BigDecimal allocationAmount;

	@Column(name = "number_of_employed_learners")
	private Integer numberOfEmployedLearners;

	@Column(name = "number_of_unemployed_learners")
	private Integer numberOfUnemployedLearners;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dg_year_id", nullable = false)
	private DGYear dgYear;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = false)
	private InterventionType interventionType;

	public DGYearLearningPrograms() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime  = 31;
		int       result = 1;
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
		DGYearLearningPrograms other = (DGYearLearningPrograms) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAllocationAmount() {
		return allocationAmount;
	}

	public void setAllocationAmount(BigDecimal allocationAmount) {
		this.allocationAmount = allocationAmount;
	}

	public Integer getNumberOfEmployedLearners() {
		return numberOfEmployedLearners;
	}

	public void setNumberOfEmployedLearners(Integer numberOfEmployedLearners) {
		this.numberOfEmployedLearners = numberOfEmployedLearners;
	}

	public Integer getNumberOfUnemployedLearners() {
		return numberOfUnemployedLearners;
	}

	public void setNumberOfUnemployedLearners(Integer numberOfUnemployedLearners) {
		this.numberOfUnemployedLearners = numberOfUnemployedLearners;
	}

	public DGYear getDgYear() {
		return dgYear;
	}

	public void setDgYear(DGYear dgYear) {
		this.dgYear = dgYear;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}
}
