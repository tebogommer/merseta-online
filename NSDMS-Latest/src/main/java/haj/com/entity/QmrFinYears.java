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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.enums.FinYearQuartersEnum;
import haj.com.entity.lookup.FinYearQuartersLookUp;
import haj.com.entity.lookup.FinancialYears;
import haj.com.framework.IDataEntity;

/**
 * QmrFinYears.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "qmr_fin_years")
@AuditTable(value = "qmr_fin_years_hist")
@Audited
public class QmrFinYears implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of QmrFinYears. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@Column(name = "fin_year_start", length = 10)
	private Integer finYearStart;
	
	@Column(name = "fin_year_end", length = 10)
	private Integer finYearEnd;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_date", length = 19)
	private Date fromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "to_date", length = 19)
	private Date toDate;
	
	// date used when the data must generate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_generation_date", length = 19)
	private Date dataGenerationDate;

	@Enumerated
	@Column(name = "fin_year_quarters", length = 19)
	private FinYearQuartersEnum finYearQuarters;
	
	@Column(name = "fin_year_quarters_look_up_flat_key", length = 19)
	private Long finYearQuartersLookUpFlatKey;
	
	// indicator if data generated 
	@Column(name = "data_generated", columnDefinition = "BIT default false")
	private Boolean dataGenerated;
	
	// date data generated
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_data_generated", length = 19)
	private Date dateDataGenerated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_for_generation", length = 19)
	private Date dateForGeneration;
	
	@Column(name = "ref_no", length = 10)
	private String refNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "financial_years_id", nullable = true)
	private FinancialYears financialYears;
	
	@Transient
	private FinYearQuartersLookUp finYearQuartersLookUp;

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
		QmrFinYears other = (QmrFinYears) obj;
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

	public Integer getFinYearStart() {
		return finYearStart;
	}

	public void setFinYearStart(Integer finYearStart) {
		this.finYearStart = finYearStart;
	}

	public Integer getFinYearEnd() {
		return finYearEnd;
	}

	public void setFinYearEnd(Integer finYearEnd) {
		this.finYearEnd = finYearEnd;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public FinYearQuartersEnum getFinYearQuarters() {
		return finYearQuarters;
	}

	public void setFinYearQuarters(FinYearQuartersEnum finYearQuarters) {
		this.finYearQuarters = finYearQuarters;
	}

	public Long getFinYearQuartersLookUpFlatKey() {
		return finYearQuartersLookUpFlatKey;
	}

	public void setFinYearQuartersLookUpFlatKey(Long finYearQuartersLookUpFlatKey) {
		this.finYearQuartersLookUpFlatKey = finYearQuartersLookUpFlatKey;
	}

	public FinYearQuartersLookUp getFinYearQuartersLookUp() {
		return finYearQuartersLookUp;
	}

	public void setFinYearQuartersLookUp(FinYearQuartersLookUp finYearQuartersLookUp) {
		this.finYearQuartersLookUp = finYearQuartersLookUp;
	}

	public Boolean getDataGenerated() {
		return dataGenerated;
	}

	public void setDataGenerated(Boolean dataGenerated) {
		this.dataGenerated = dataGenerated;
	}

	public Date getDateDataGenerated() {
		return dateDataGenerated;
	}

	public void setDateDataGenerated(Date dateDataGenerated) {
		this.dateDataGenerated = dateDataGenerated;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public Date getDataGenerationDate() {
		return dataGenerationDate;
	}

	public void setDataGenerationDate(Date dataGenerationDate) {
		this.dataGenerationDate = dataGenerationDate;
	}

	public FinancialYears getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(FinancialYears financialYears) {
		this.financialYears = financialYears;
	}

	public Date getDateForGeneration() {
		return dateForGeneration;
	}

	public void setDateForGeneration(Date dateForGeneration) {
		this.dateForGeneration = dateForGeneration;
	}

}
