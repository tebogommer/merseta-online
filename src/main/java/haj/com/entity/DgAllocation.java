package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.enums.AllocationStatusEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * DgVerification.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "dg_allocation")
@AuditTable(value = "dg_allocation_hist")
@Audited
public class DgAllocation implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of DgVerification. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "dg_allocation_parent_id", nullable = true)
	private DgAllocationParent dgAllocationParent;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "mandatory_grant_id", nullable = true)
	private MandatoryGrant mandatoryGrant;

	@Column(name = "change_allocation_learners")
	private Integer changeAllocationLearners;
	
	@Column(name = "system_change_allocation_learners", columnDefinition="BIT default 0")
	private Boolean systemChangeAllocationLearners;

	@Column(name = "initial_learners")
	private Integer initialLearners;
	
	@Column(name = "number_disabled")
	private Long numberDisabled;
	
	@Column(name = "number_of_learners")
	private Integer numberOfLearners;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	private InterventionType interventionType;

	@Column(name = "grant_amount")
	private BigDecimal grantAmount;

	@Column(name = "disabled_grant_amount")
	private BigDecimal disabledGrantAmount;

	@Column(name = "total_learners")
	private Integer totalLearners;

	@Column(name = "disabled_total_learners")
	private Integer disabledTotalLearners;

	@Column(name = "employed_learners")
	private Integer employedLearners;

	@Column(name = "unemployed_learners")
	private Integer unemployedLearners;

	@Column(name = "clo_learners")
	private Integer cloLearners;

	@Column(name = "crm_learners")
	private Integer crmLearners;

	@Column(name = "total_levy_amount")
	private BigDecimal totalLevyAmount;

	@Column(name = "requested_amount")
	private BigDecimal requestedAmount;

	@Column(name = "available_amount")
	private BigDecimal availableAmount;

	@Column(name = "recommended_amount")
	private BigDecimal recommendedAmount;

	@Column(name = "max_possible_amount")
	private BigDecimal maxPossibleAmount;

	@Column(name = "max_possible_amount_remaining")
	private BigDecimal maxPossibleAmountRemaining;

	@Column(name = "max_possible_learners_remaining")
	private Integer maxPossibleLearnersRemaining;

	@Column(name = "max_possible_learners")
	private Integer maxPossibleLearners;

	@Column(name = "max_learners")
	private Integer maxLearners;

	@Column(name = "co_funding_learners_awarded")
	private Integer coFundingLearnersAwarded;

	@Column(name = "award_amount")
	private BigDecimal awardAmount;

	@Column(name = "co_funding_award_amount")
	private BigDecimal coFundingAwardAmount;

	@Column(name = "co_funding_running_total")
	private BigDecimal coFundingRunningTotal;

	@Column(name = "running_total")
	private BigDecimal runningTotal;

	@Column(name = "balance_remaining")
	private BigDecimal balanceRemaining;

	@Column(name = "co_funding_grant_amount")
	private BigDecimal coFundingGrantAmount;

	@Column(name = "balance_remaining_with_co_funding")
	private BigDecimal balanceRemainingWithCoFunding;

	@Column(name = "remaining_co_funding_grant_amount")
	private BigDecimal remainingCoFundingGrantAmount;

	@Column(name = "total_award_amount")
	private BigDecimal totalAwardAmount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "allocation_status_enum")
	private AllocationStatusEnum allocationStatusEnum;
	
	@Transient
	private MandatoryGrantRecommendation lastestMandatoryGrantRecommendation;
	
	public DgAllocation() {
		super();
	}

	public DgAllocation(DgAllocationParent dgAllocationParent) {
		super();
		this.dgAllocationParent = dgAllocationParent;
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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		DgAllocation other = (DgAllocation) obj;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}

	public BigDecimal getDisabledGrantAmount() {
		return disabledGrantAmount;
	}

	public void setDisabledGrantAmount(BigDecimal disabledGrantAmount) {
		this.disabledGrantAmount = disabledGrantAmount;
	}

	public Integer getTotalLearners() {
		return totalLearners;
	}

	public void setTotalLearners(Integer totalLearners) {
		this.totalLearners = totalLearners;
	}

	public Integer getDisabledTotalLearners() {
		return disabledTotalLearners;
	}

	public void setDisabledTotalLearners(Integer disabledTotalLearners) {
		this.disabledTotalLearners = disabledTotalLearners;
	}

	public Integer getEmployedLearners() {
		return employedLearners;
	}

	public void setEmployedLearners(Integer employedLearners) {
		this.employedLearners = employedLearners;
	}

	public Integer getUnemployedLearners() {
		return unemployedLearners;
	}

	public void setUnemployedLearners(Integer unemployedLearners) {
		this.unemployedLearners = unemployedLearners;
	}

	public Integer getCloLearners() {
		return cloLearners;
	}

	public void setCloLearners(Integer cloLearners) {
		this.cloLearners = cloLearners;
	}

	public Integer getCrmLearners() {
		return crmLearners;
	}

	public void setCrmLearners(Integer crmLearners) {
		this.crmLearners = crmLearners;
	}

	public BigDecimal getTotalLevyAmount() {
		return totalLevyAmount;
	}

	public void setTotalLevyAmount(BigDecimal totalLevyAmount) {
		this.totalLevyAmount = totalLevyAmount;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public BigDecimal getRecommendedAmount() {
		return recommendedAmount;
	}

	public void setRecommendedAmount(BigDecimal recommendedAmount) {
		this.recommendedAmount = recommendedAmount;
	}

	public Integer getMaxPossibleLearners() {
		return maxPossibleLearners;
	}

	public void setMaxPossibleLearners(Integer maxPossibleLearners) {
		this.maxPossibleLearners = maxPossibleLearners;
	}

	public Integer getMaxLearners() {
		return maxLearners;
	}

	public void setMaxLearners(Integer maxLearners) {
		this.maxLearners = maxLearners;
	}

	public BigDecimal getAwardAmount() {
		return awardAmount;
	}

	public void setAwardAmount(BigDecimal awardAmount) {
		this.awardAmount = awardAmount;
	}

	public BigDecimal getRunningTotal() {
		return runningTotal;
	}

	public void setRunningTotal(BigDecimal runningTotal) {
		this.runningTotal = runningTotal;
	}

	public BigDecimal getBalanceRemaining() {
		return balanceRemaining;
	}

	public void setBalanceRemaining(BigDecimal balanceRemaining) {
		this.balanceRemaining = balanceRemaining;
	}

	public MandatoryGrant getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrant mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
	}

	public BigDecimal getCoFundingGrantAmount() {
		return coFundingGrantAmount;
	}

	public void setCoFundingGrantAmount(BigDecimal coFundingGrantAmount) {
		this.coFundingGrantAmount = coFundingGrantAmount;
	}

	public BigDecimal getBalanceRemainingWithCoFunding() {
		return balanceRemainingWithCoFunding;
	}

	public void setBalanceRemainingWithCoFunding(BigDecimal balanceRemainingWithCoFunding) {
		this.balanceRemainingWithCoFunding = balanceRemainingWithCoFunding;
	}

	public BigDecimal getCoFundingAwardAmount() {
		return coFundingAwardAmount;
	}

	public void setCoFundingAwardAmount(BigDecimal coFundingAwardAmount) {
		this.coFundingAwardAmount = coFundingAwardAmount;
	}

	public BigDecimal getCoFundingRunningTotal() {
		return coFundingRunningTotal;
	}

	public void setCoFundingRunningTotal(BigDecimal coFundingRunningTotal) {
		this.coFundingRunningTotal = coFundingRunningTotal;
	}

	public BigDecimal getRemainingCoFundingGrantAmount() {
		return remainingCoFundingGrantAmount;
	}

	public void setRemainingCoFundingGrantAmount(BigDecimal remainingCoFundingGrantAmount) {
		this.remainingCoFundingGrantAmount = remainingCoFundingGrantAmount;
	}

	public BigDecimal getMaxPossibleAmount() {
		return maxPossibleAmount;
	}

	public void setMaxPossibleAmount(BigDecimal maxPossibleAmount) {
		this.maxPossibleAmount = maxPossibleAmount;
	}

	public BigDecimal getMaxPossibleAmountRemaining() {
		return maxPossibleAmountRemaining;
	}

	public void setMaxPossibleAmountRemaining(BigDecimal maxPossibleAmountRemaining) {
		this.maxPossibleAmountRemaining = maxPossibleAmountRemaining;
	}

	public Integer getMaxPossibleLearnersRemaining() {
		return maxPossibleLearnersRemaining;
	}

	public void setMaxPossibleLearnersRemaining(Integer maxPossibleLearnersRemaining) {
		this.maxPossibleLearnersRemaining = maxPossibleLearnersRemaining;
	}

	public Integer getCoFundingLearnersAwarded() {
		return coFundingLearnersAwarded;
	}

	public void setCoFundingLearnersAwarded(Integer coFundingLearnersAwarded) {
		this.coFundingLearnersAwarded = coFundingLearnersAwarded;
	}

	public BigDecimal getTotalAwardAmount() {
		return totalAwardAmount;
	}

	public void setTotalAwardAmount(BigDecimal totalAwardAmount) {
		this.totalAwardAmount = totalAwardAmount;
	}

	public Long getNumberDisabled() {
		return numberDisabled;
	}

	public void setNumberDisabled(Long numberDisabled) {
		this.numberDisabled = numberDisabled;
	}

	
	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public Integer getNumberOfLearners() {
		return numberOfLearners;
	}

	public void setNumberOfLearners(Integer numberOfLearners) {
		this.numberOfLearners = numberOfLearners;
	}
	
	/**
	 * @return the dgAllocationParent
	 */
	public DgAllocationParent getDgAllocationParent() {
		return dgAllocationParent;
	}

	/**
	 * @param dgAllocationParent
	 *            the dgAllocationParent to set
	 */
	public void setDgAllocationParent(DgAllocationParent dgAllocationParent) {
		this.dgAllocationParent = dgAllocationParent;
	}

	/**
	 * @return the allocationStatusEnum
	 */
	public AllocationStatusEnum getAllocationStatusEnum() {
		return allocationStatusEnum;
	}

	/**
	 * @param allocationStatusEnum the allocationStatusEnum to set
	 */
	public void setAllocationStatusEnum(AllocationStatusEnum allocationStatusEnum) {
		this.allocationStatusEnum = allocationStatusEnum;
	}

	/**
	 * @return the changeAllocationLearners
	 */
	public Integer getChangeAllocationLearners() {
		return changeAllocationLearners;
	}

	/**
	 * @param changeAllocationLearners the changeAllocationLearners to set
	 */
	public void setChangeAllocationLearners(Integer changeAllocationLearners) {
		this.changeAllocationLearners = changeAllocationLearners;
	}

	/**
	 * @return the initialLearners
	 */
	public Integer getInitialLearners() {
		return initialLearners;
	}

	/**
	 * @param initialLearners the initialLearners to set
	 */
	public void setInitialLearners(Integer initialLearners) {
		this.initialLearners = initialLearners;
	}

	public Boolean getSystemChangeAllocationLearners() {
		return systemChangeAllocationLearners;
	}

	public void setSystemChangeAllocationLearners(Boolean systemChangeAllocationLearners) {
		this.systemChangeAllocationLearners = systemChangeAllocationLearners;
	}

	public MandatoryGrantRecommendation getLastestMandatoryGrantRecommendation() {
		return lastestMandatoryGrantRecommendation;
	}

	public void setLastestMandatoryGrantRecommendation(MandatoryGrantRecommendation lastestMandatoryGrantRecommendation) {
		this.lastestMandatoryGrantRecommendation = lastestMandatoryGrantRecommendation;
	}



}
