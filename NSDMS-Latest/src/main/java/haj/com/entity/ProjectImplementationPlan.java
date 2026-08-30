package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "project_implementation_plan")
public class ProjectImplementationPlan implements IDataEntity {

	/** Unique Id of ProcessRoles. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of Object. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	private InterventionType interventionType;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dg_allocation_id", nullable = true)
	private DgAllocation dgAllocation;

	@Column(name = "intervention_type_description")
	private String interventionTypeDescription;

	@Column(name = "number_of_learning_interventions")
	private BigInteger numberOfLearningInterventions;

	@Column(name = "fully_funded_learner_awarded")
	private Integer fullyFundedLearnerAwarded;

	@Column(name = "learners_with_disability")
	private BigDecimal learnersWithDisability;

	@Column(name = "learners_without_disability")
	private BigDecimal learnersWithoutDisability;

	@Column(name = "learners_with_disability_allowance_value")
	private Double learnersWithDisabilityAllowanceValue;

	@Column(name = "learners_without_disability_allowance_value")
	private Double learnersWithoutDisabilityAllowanceValue;

	@Column(name = "total_funding_value")
	private Double totalFundingValue;

	@Column(name = "recoverable_amount")
	private Double recoverableAmount;

	@Column(name = "total_award_amount")
	private BigDecimal totalAwardAmount;

	@Column(name = "co_funding_learners_awarded")
	private Integer coFundingLearnersAwarded;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "projected_recruitment_date", length = 19)
	private Date projectedRecruitmentDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "projected_induction_date", length = 19)
	private Date projectedInductionDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "projected_registration_date", length = 19)
	private Date projectedRegistrationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "estimated_progress_date", length = 19)
	private Date estimatedProgressDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "estimated_completion_date", length = 19)
	private Date estimatedCompletionDate;

	@Enumerated
	@Column(name = "accredited_provider_identified")
	private YesNoEnum accreditedProviderIdentified;

	@Enumerated
	@Column(name = "logistical_aspects_addressed")
	private YesNoEnum logisticalAspectsAddressed;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "active_contracts_id", nullable = true)
	private ActiveContracts activeContracts;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Transient
	private List<Doc> docs;

	@Transient
	private DgAllocationParent dgAllocationParent;

	@Transient
	private SDFCompany sdfCompany;

	@Transient
	private SDFCompany secondarySdfCompany;

	@Transient
	private Users cloUser;
	
	@Transient
	private Double totalApprovedPayments;
	
	@Transient
	private Double totalOutstandingPayments;
	
	@Transient
	private String interventionQualificationDesc;
	
	public ProjectImplementationPlan() {
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
		ProjectImplementationPlan other = (ProjectImplementationPlan) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	public String getInterventionTypeDescription() {
		return interventionTypeDescription;
	}

	public void setInterventionTypeDescription(String interventionTypeDescription) {
		this.interventionTypeDescription = interventionTypeDescription;
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

	public BigInteger getNumberOfLearningInterventions() {
		return numberOfLearningInterventions;
	}

	public void setNumberOfLearningInterventions(BigInteger numberOfLearningInterventions) {
		this.numberOfLearningInterventions = numberOfLearningInterventions;
	}

	public BigDecimal getLearnersWithDisability() {
		return learnersWithDisability;
	}

	public void setLearnersWithDisability(BigDecimal learnersWithDisability) {
		this.learnersWithDisability = learnersWithDisability;
	}

	public BigDecimal getLearnersWithoutDisability() {
		return learnersWithoutDisability;
	}

	public void setLearnersWithoutDisability(BigDecimal learnersWithoutDisability) {
		this.learnersWithoutDisability = learnersWithoutDisability;
	}

	public Double getLearnersWithDisabilityAllowanceValue() {
		return learnersWithDisabilityAllowanceValue;
	}

	public void setLearnersWithDisabilityAllowanceValue(Double learnersWithDisabilityAllowanceValue) {
		this.learnersWithDisabilityAllowanceValue = learnersWithDisabilityAllowanceValue;
	}

	public Double getLearnersWithoutDisabilityAllowanceValue() {
		return learnersWithoutDisabilityAllowanceValue;
	}

	public void setLearnersWithoutDisabilityAllowanceValue(Double learnersWithoutDisabilityAllowanceValue) {
		this.learnersWithoutDisabilityAllowanceValue = learnersWithoutDisabilityAllowanceValue;
	}

	public Double getTotalFundingValue() {
		return totalFundingValue;
	}

	public void setTotalFundingValue(Double totalFundingValue) {
		this.totalFundingValue = totalFundingValue;
	}

	public Date getProjectedRecruitmentDate() {
		return projectedRecruitmentDate;
	}

	public void setProjectedRecruitmentDate(Date projectedRecruitmentDate) {
		this.projectedRecruitmentDate = projectedRecruitmentDate;
	}

	public Date getProjectedInductionDate() {
		return projectedInductionDate;
	}

	public void setProjectedInductionDate(Date projectedInductionDate) {
		this.projectedInductionDate = projectedInductionDate;
	}

	public Date getProjectedRegistrationDate() {
		return projectedRegistrationDate;
	}

	public void setProjectedRegistrationDate(Date projectedRegistrationDate) {
		this.projectedRegistrationDate = projectedRegistrationDate;
	}

	public Date getEstimatedProgressDate() {
		return estimatedProgressDate;
	}

	public void setEstimatedProgressDate(Date estimatedProgressDate) {
		this.estimatedProgressDate = estimatedProgressDate;
	}

	public Date getEstimatedCompletionDate() {
		return estimatedCompletionDate;
	}

	public void setEstimatedCompletionDate(Date estimatedCompletionDate) {
		this.estimatedCompletionDate = estimatedCompletionDate;
	}

	public YesNoEnum getAccreditedProviderIdentified() {
		return accreditedProviderIdentified;
	}

	public void setAccreditedProviderIdentified(YesNoEnum accreditedProviderIdentified) {
		this.accreditedProviderIdentified = accreditedProviderIdentified;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	/**
	 * @return the logisticalAspectsAddressed
	 */
	public YesNoEnum getLogisticalAspectsAddressed() {
		return logisticalAspectsAddressed;
	}

	/**
	 * @param logisticalAspectsAddressed
	 *            the logisticalAspectsAddressed to set
	 */
	public void setLogisticalAspectsAddressed(YesNoEnum logisticalAspectsAddressed) {
		this.logisticalAspectsAddressed = logisticalAspectsAddressed;
	}

	public Integer getCoFundingLearnersAwarded() {
		return coFundingLearnersAwarded;
	}

	public void setCoFundingLearnersAwarded(Integer coFundingLearnersAwarded) {
		this.coFundingLearnersAwarded = coFundingLearnersAwarded;
	}

	public Integer getFullyFundedLearnerAwarded() {
		return fullyFundedLearnerAwarded;
	}

	public void setFullyFundedLearnerAwarded(Integer fullyFundedLearnerAwarded) {
		this.fullyFundedLearnerAwarded = fullyFundedLearnerAwarded;
	}

	public BigDecimal getTotalAwardAmount() {
		return totalAwardAmount;
	}

	public void setTotalAwardAmount(BigDecimal totalAwardAmount) {
		this.totalAwardAmount = totalAwardAmount;
	}

	public DgAllocationParent getDgAllocationParent() {
		return dgAllocationParent;
	}

	public void setDgAllocationParent(DgAllocationParent dgAllocationParent) {
		this.dgAllocationParent = dgAllocationParent;
	}

	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	public SDFCompany getSdfCompany() {
		return sdfCompany;
	}

	public void setSdfCompany(SDFCompany sdfCompany) {
		this.sdfCompany = sdfCompany;
	}

	public Users getCloUser() {
		return cloUser;
	}

	public void setCloUser(Users cloUser) {
		this.cloUser = cloUser;
	}

	public SDFCompany getSecondarySdfCompany() {
		return secondarySdfCompany;
	}

	public void setSecondarySdfCompany(SDFCompany secondarySdfCompany) {
		this.secondarySdfCompany = secondarySdfCompany;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public DgAllocation getDgAllocation() {
		return dgAllocation;
	}

	public void setDgAllocation(DgAllocation dgAllocation) {
		this.dgAllocation = dgAllocation;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public Double getRecoverableAmount() {
		return recoverableAmount;
	}

	public void setRecoverableAmount(Double recoverableAmount) {
		this.recoverableAmount = recoverableAmount;
	}

	public Double getTotalApprovedPayments() {
		return totalApprovedPayments;
	}

	public void setTotalApprovedPayments(Double totalApprovedPayments) {
		this.totalApprovedPayments = totalApprovedPayments;
	}

	public Double getTotalOutstandingPayments() {
		return totalOutstandingPayments;
	}

	public void setTotalOutstandingPayments(Double totalOutstandingPayments) {
		this.totalOutstandingPayments = totalOutstandingPayments;
	}

	public String getInterventionQualificationDesc() {
		return interventionQualificationDesc;
	}

	public void setInterventionQualificationDesc(String interventionQualificationDesc) {
		this.interventionQualificationDesc = interventionQualificationDesc;
	}

}
