package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.framework.IDataEntity;

/**
 * The Class SarsLevyDetailCalculation.
 */
@Entity
@Table(name = "sars_levy_detail_calculation")
@AuditTable(value = "sars_levy_detail_calculation_hist")
@Audited
public class SarsLevyDetailCalculation implements IDataEntity {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of SarsLevyDetailCalculation. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of SarsLevyDetailCalculation. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	/** The scheme year for when the calculations must apply */
	@Column(name = "for_scheme_year", length = 19, nullable = false)
	private Integer forSchemeYear;
	
	@Column(name = "mandatory_percentage")
	private Double mandatoryPercentage;
	
	@Column(name = "discretionary_percentage")
	private Double discretionaryPercentage;
	
	@Column(name = "qcto_percentage")
	private Double qctoPercentage;
	
	@Column(name = "admin_percentage")
	private Double adminPercentage;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_action_date", length = 19)
	private Date lastActionDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_action_user_id", nullable = true)
	private Users lastActionUser;
	
	@Transient
	private Double totalPercentage;
	
	@Transient
	private Double mandatoryAmount;
	
	@Transient
	private Double discretionaryAmount;
	
	@Transient
	private Double qctoAmount;
	
	@Transient
	private Double adminAmount;
	
	@Transient
	private Double totalAmount;
	
    
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
		SarsLevyDetailCalculation other = (SarsLevyDetailCalculation) obj;
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
	 * @param id the id to set
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
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getForSchemeYear() {
		return forSchemeYear;
	}

	public void setForSchemeYear(Integer forSchemeYear) {
		this.forSchemeYear = forSchemeYear;
	}

	public Double getMandatoryPercentage() {
		return mandatoryPercentage;
	}

	public void setMandatoryPercentage(Double mandatoryPercentage) {
		this.mandatoryPercentage = mandatoryPercentage;
	}

	public Double getDiscretionaryPercentage() {
		return discretionaryPercentage;
	}

	public void setDiscretionaryPercentage(Double discretionaryPercentage) {
		this.discretionaryPercentage = discretionaryPercentage;
	}

	public Double getQctoPercentage() {
		return qctoPercentage;
	}

	public void setQctoPercentage(Double qctoPercentage) {
		this.qctoPercentage = qctoPercentage;
	}

	public Double getAdminPercentage() {
		return adminPercentage;
	}

	public void setAdminPercentage(Double adminPercentage) {
		this.adminPercentage = adminPercentage;
	}

	public Double getTotalPercentage() {
		return totalPercentage;
	}

	public void setTotalPercentage(Double totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	public Double getMandatoryAmount() {
		return mandatoryAmount;
	}

	public void setMandatoryAmount(Double mandatoryAmount) {
		this.mandatoryAmount = mandatoryAmount;
	}

	public Double getDiscretionaryAmount() {
		return discretionaryAmount;
	}

	public void setDiscretionaryAmount(Double discretionaryAmount) {
		this.discretionaryAmount = discretionaryAmount;
	}

	public Double getQctoAmount() {
		return qctoAmount;
	}

	public void setQctoAmount(Double qctoAmount) {
		this.qctoAmount = qctoAmount;
	}

	public Double getAdminAmount() {
		return adminAmount;
	}

	public void setAdminAmount(Double adminAmount) {
		this.adminAmount = adminAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

	public Users getLastActionUser() {
		return lastActionUser;
	}

	public void setLastActionUser(Users lastActionUser) {
		this.lastActionUser = lastActionUser;
	}
	
}