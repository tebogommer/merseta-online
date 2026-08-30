package haj.com.entity.lookup;

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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.Users;
import haj.com.entity.enums.NsdpReportRunTypeEnum;
import haj.com.framework.AbstractLookup;

@Entity
@Table(name = "nsdp_report_config")
@AuditTable(value = "nsdp_report_config_hist")
@Audited
public class NsdpReportConfig extends AbstractLookup {

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
	
	@Column(name = "nsdp_outcomes", length = 500)
	private String nsdpOutcomes;
	
	@Column(name = "nsdp_sub_outcomes", length = 500)
	private String nsdpSubOutcomes;

	// known as Output Indicator
	@Column(name = "description", length = 500)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "financial_years_id", nullable = true)
	private FinancialYears financialYears;
	
	@Column(name = "target_amount", length = 40)
	private Integer targetAmount;
	
	@Column(name = "manual_capture")
	private Boolean manualCapture;
	
	@Enumerated
	@Column(name = "nsdp_report_run_type")
	private NsdpReportRunTypeEnum nsdpReportRunType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "last_action_user_id", nullable = true)
	private Users lastActionUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_action_date", length = 19)
	private Date lastActionDate;
	
	@Column(name = "order_number")
	private Integer orderNumber;
	
	public NsdpReportConfig() {
		super();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		NsdpReportConfig other = (NsdpReportConfig) obj;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public FinancialYears getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(FinancialYears financialYears) {
		this.financialYears = financialYears;
	}

	public Integer getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Integer targetAmount) {
		this.targetAmount = targetAmount;
	}

	public Boolean getManualCapture() {
		return manualCapture;
	}

	public void setManualCapture(Boolean manualCapture) {
		this.manualCapture = manualCapture;
	}

	public NsdpReportRunTypeEnum getNsdpReportRunType() {
		return nsdpReportRunType;
	}

	public void setNsdpReportRunType(NsdpReportRunTypeEnum nsdpReportRunType) {
		this.nsdpReportRunType = nsdpReportRunType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getNsdpOutcomes() {
		return nsdpOutcomes;
	}

	public void setNsdpOutcomes(String nsdpOutcomes) {
		this.nsdpOutcomes = nsdpOutcomes;
	}

	public String getNsdpSubOutcomes() {
		return nsdpSubOutcomes;
	}

	public void setNsdpSubOutcomes(String nsdpSubOutcomes) {
		this.nsdpSubOutcomes = nsdpSubOutcomes;
	}

}
