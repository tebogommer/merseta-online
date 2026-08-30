package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "levy_income_summary")
public class LevyIncomeSummary implements IDataEntity {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Blank. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@Column(name="description", columnDefinition="LONGTEXT")
    private String description;

	@Column(name="auto", columnDefinition="LONGTEXT")
    private BigDecimal auto;
	
	@Column(name="metal", columnDefinition="LONGTEXT")
    private BigDecimal metal;
	
	@Column(name="motor", columnDefinition="LONGTEXT")
    private BigDecimal motor;
	
	@Column(name="newTyre", columnDefinition="LONGTEXT")
    private BigDecimal newTyre;
	
	@Column(name="plastic", columnDefinition="LONGTEXT")
    private BigDecimal plastic;
	
	@Column(name="unknown", columnDefinition="LONGTEXT")
    private BigDecimal unknown;
	
	@Column(name="acm", columnDefinition="LONGTEXT")
    private BigDecimal acm;
	
	@Column(name="total", columnDefinition="LONGTEXT")
    private BigDecimal total;
	
	@Enumerated
	@Column(name = "report_property")
	private ReportPropertiesEnum reportProperty;
    
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
		LevyIncomeSummary other = (LevyIncomeSummary) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public LevyIncomeSummary(String description, BigDecimal auto, BigDecimal metal, BigDecimal motor, BigDecimal newTyre, BigDecimal plastic, BigDecimal unknown, BigDecimal total) {
		super();
		this.description = description;
		this.auto = auto;
		this.metal = metal;
		this.motor = motor;
		this.newTyre = newTyre;
		this.plastic = plastic;
		this.unknown = unknown;
		this.total = total;
	}

	public LevyIncomeSummary(String description, BigDecimal auto, BigDecimal metal, BigDecimal motor, BigDecimal newTyre, BigDecimal plastic, BigDecimal unknown, BigDecimal acm, BigDecimal total) {
		super();
		this.description = description;
		this.auto = auto;
		this.metal = metal;
		this.motor = motor;
		this.newTyre = newTyre;
		this.plastic = plastic;
		this.unknown = unknown;
		this.acm = acm;
		this.total = total;
	}

	public LevyIncomeSummary() {
		super();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAuto() {
		return auto;
	}

	public void setAuto(BigDecimal auto) {
		this.auto = auto;
	}

	public BigDecimal getMetal() {
		return metal;
	}

	public void setMetal(BigDecimal metal) {
		this.metal = metal;
	}

	public BigDecimal getMotor() {
		return motor;
	}

	public void setMotor(BigDecimal motor) {
		this.motor = motor;
	}

	public BigDecimal getNewTyre() {
		return newTyre;
	}

	public void setNewTyre(BigDecimal newTyre) {
		this.newTyre = newTyre;
	}

	public BigDecimal getPlastic() {
		return plastic;
	}

	public void setPlastic(BigDecimal plastic) {
		this.plastic = plastic;
	}

	public BigDecimal getUnknown() {
		return unknown;
	}

	public void setUnknown(BigDecimal unknown) {
		this.unknown = unknown;
	}

	public BigDecimal getAcm() {
		return acm;
	}

	public void setAcm(BigDecimal acm) {
		this.acm = acm;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public ReportPropertiesEnum getReportProperty() {
		return reportProperty;
	}

	public void setReportProperty(ReportPropertiesEnum reportProperty) {
		this.reportProperty = reportProperty;
	}
}
