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
import haj.com.entity.lookup.NsdpReportConfig;
import haj.com.framework.IDataEntity;

/**
 * NsdpQuarterReporting.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "nsdp_quarter_reporting")
@AuditTable(value = "nsdp_quarter_reportings_hist")
@Audited
public class NsdpQuarterReporting implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "financial_years_id", nullable = true)
	private FinancialYears financialYears;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "nsdp_report_config_id", nullable = true)
	private NsdpReportConfig nsdpReportConfig;

	@Enumerated
	@Column(name = "fin_year_quarters", length = 19)
	private FinYearQuartersEnum finYearQuarters;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "from_date", length = 19)
	private Date fromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "to_date", length = 19)
	private Date toDate;
	
	@Column(name = "quarter_target_amount", length = 40)
	private Integer quarterTargetAmount;
	
	@Column(name = "quarter_achived_amount", length = 40)
	private Integer quarterAchivedAmount;
	
	@Column(name = "system_update")
	private Boolean systemUpdate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "last_action_user_id", nullable = true)
	private Users lastActionUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_action_date", length = 19)
	private Date lastActionDate;
	
	@Column(name = "fin_year_quarters_look_up_flat_key", length = 19)
	private Long finYearQuartersLookUpFlatKey;
	
	@Column(name = "data_generated")
	private Boolean dataGenerated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_auto_population_ran", length = 19)
	private Date dataAutoPopulationRan;
	
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
		NsdpQuarterReporting other = (NsdpQuarterReporting) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public double getPercentageAchived() {
		try {
			if (this.quarterAchivedAmount != null && this.quarterAchivedAmount != 0 && this.quarterTargetAmount != null && this.quarterTargetAmount != 0) {
				double percentage = Double.valueOf(this.quarterAchivedAmount) / Double.valueOf(this.quarterTargetAmount);
				return percentage * 100;
			}else {
				return 0.00;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0.00;
		}
	}
	
	public int getVarinace() {
		try {
			if (this.quarterAchivedAmount != null && this.quarterAchivedAmount != 0 && this.quarterTargetAmount != null && this.quarterTargetAmount != 0) {
				return (this.quarterTargetAmount - this.quarterAchivedAmount);
			} else if (this.quarterTargetAmount != null && this.quarterTargetAmount != 0) {
				return this.quarterTargetAmount;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
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

	public FinancialYears getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(FinancialYears financialYears) {
		this.financialYears = financialYears;
	}

	public NsdpReportConfig getNsdpReportConfig() {
		return nsdpReportConfig;
	}

	public void setNsdpReportConfig(NsdpReportConfig nsdpReportConfig) {
		this.nsdpReportConfig = nsdpReportConfig;
	}

	public FinYearQuartersEnum getFinYearQuarters() {
		return finYearQuarters;
	}

	public void setFinYearQuarters(FinYearQuartersEnum finYearQuarters) {
		this.finYearQuarters = finYearQuarters;
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

	public Integer getQuarterTargetAmount() {
		return quarterTargetAmount;
	}

	public void setQuarterTargetAmount(Integer quarterTargetAmount) {
		this.quarterTargetAmount = quarterTargetAmount;
	}

	public Integer getQuarterAchivedAmount() {
		return quarterAchivedAmount;
	}

	public void setQuarterAchivedAmount(Integer quarterAchivedAmount) {
		this.quarterAchivedAmount = quarterAchivedAmount;
	}

	public Boolean getSystemUpdate() {
		return systemUpdate;
	}

	public void setSystemUpdate(Boolean systemUpdate) {
		this.systemUpdate = systemUpdate;
	}

	public Users getLastActionUser() {
		return lastActionUser;
	}

	public void setLastActionUser(Users lastActionUser) {
		this.lastActionUser = lastActionUser;
	}

	public Date getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
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

	public Date getDataAutoPopulationRan() {
		return dataAutoPopulationRan;
	}

	public void setDataAutoPopulationRan(Date dataAutoPopulationRan) {
		this.dataAutoPopulationRan = dataAutoPopulationRan;
	}

}