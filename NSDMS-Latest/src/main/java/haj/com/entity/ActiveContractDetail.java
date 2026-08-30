package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
@Entity
@Table(name = "active_contract_detail")
@AuditTable(value = "active_contract_detail_hist")
@Audited
public class ActiveContractDetail implements IDataEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "active_contracts_id", nullable = true)
	private ActiveContracts activeContracts;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_type_id", nullable = true)
	private InterventionType interventionType;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_implementation_plan_id", nullable = true)
	private ProjectImplementationPlan projectImplementationPlan;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_implementation_plan_learners_id", nullable = true)
	private ProjectImplementationPlanLearners projectImplementationPlanLearners;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;

	@Enumerated(EnumType.STRING)
	@Column(name = "tranche_enum")
	private TrancheEnum trancheEnum;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = true)
	private Users users;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "payment_month", length = 19)
	private Date paymentMonth;

	@Column(name = "additions")
	private Double additions;

	@Column(name = "addendums_ammendments")
	private Double addendumsammendments;

	@Column(name = "correction_to_balances")
	private Double correctiontobalances;

	@Column(name = "accruals")
	private Double accruals;

	@Column(name = "accruals_reversals")
	private Double accrualsreversal;

	@Column(name = "payments")
	private Double payments;

	@Column(name = "no_of_terminations")
	private Double noofterminations;

	@Column(name = "termination_value")
	private Double terminationvalue;

	@Column(name = "write_back")
	private Double writeback;

	@Column(name = "total")
	private BigDecimal total;

	@Column(name = "closing_balance")
	private BigDecimal closingbalance;

	@Column(name = "opening_balance")
	private BigDecimal openingbalance;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Transient
	private List<Doc> docs;

	public ActiveContractDetail() {
		super();
	}

	public ActiveContractDetail(BigDecimal openingbalance) {
		super();
		this.openingbalance = openingbalance;
	}

	public ActiveContractDetail(ActiveContracts activeContracts, BigDecimal openingbalance) {
		super();
		this.activeContracts = activeContracts;
		this.openingbalance = openingbalance;
	}

	public ActiveContractDetail(ActiveContracts activeContracts) {
		super();
		this.activeContracts = activeContracts;
	}

	public ActiveContractDetail(CompanyLearners companyLearners) {
		super();
		this.companyLearners = companyLearners;
	}

	public ActiveContractDetail(ActiveContracts activeContracts, CompanyLearners companyLearners) {
		super();
		this.companyLearners = companyLearners;
		this.activeContracts = activeContracts;
	}
	
	public ActiveContractDetail(ActiveContracts activeContracts, CompanyLearners companyLearners,  ProjectImplementationPlanLearners projectImplementationPlanLearners) {
		super();
		this.activeContracts = activeContracts;
		this.companyLearners = companyLearners;
		this.projectImplementationPlanLearners = projectImplementationPlanLearners;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the paymentMonth
	 */
	public Date getPaymentMonth() {
		return paymentMonth;
	}

	/**
	 * @param paymentMonth
	 *            the paymentMonth to set
	 */
	public void setPaymentMonth(Date paymentMonth) {
		this.paymentMonth = paymentMonth;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * @return the closingbalance
	 */
	public BigDecimal getClosingbalance() {
		return closingbalance;
	}

	/**
	 * @param closingbalance
	 *            the closingbalance to set
	 */
	public void setClosingbalance(BigDecimal closingbalance) {
		this.closingbalance = closingbalance;
	}

	/**
	 * @return the openingbalance
	 */
	public BigDecimal getOpeningbalance() {
		return openingbalance;
	}

	/**
	 * @param openingbalance
	 *            the openingbalance to set
	 */
	public void setOpeningbalance(BigDecimal openingbalance) {
		this.openingbalance = openingbalance;
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
		ActiveContractDetail other = (ActiveContractDetail) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the activeContracts
	 */
	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	/**
	 * @param activeContracts
	 *            the activeContracts to set
	 */
	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	/**
	 * @return the additions
	 */
	public Double getAdditions() {
		return additions;
	}

	/**
	 * @param additions
	 *            the additions to set
	 */
	public void setAdditions(Double additions) {
		this.additions = additions;
	}

	/**
	 * @return the addendumsammendments
	 */
	public Double getAddendumsammendments() {
		return addendumsammendments;
	}

	/**
	 * @param addendumsammendments
	 *            the addendumsammendments to set
	 */
	public void setAddendumsammendments(Double addendumsammendments) {
		this.addendumsammendments = addendumsammendments;
	}

	/**
	 * @return the correctiontobalances
	 */
	public Double getCorrectiontobalances() {
		return correctiontobalances;
	}

	/**
	 * @param correctiontobalances
	 *            the correctiontobalances to set
	 */
	public void setCorrectiontobalances(Double correctiontobalances) {
		this.correctiontobalances = correctiontobalances;
	}

	/**
	 * @return the accruals
	 */
	public Double getAccruals() {
		return accruals;
	}

	/**
	 * @param accruals
	 *            the accruals to set
	 */
	public void setAccruals(Double accruals) {
		this.accruals = accruals;
	}

	/**
	 * @return the accrualsreversal
	 */
	public Double getAccrualsreversal() {
		return accrualsreversal;
	}

	/**
	 * @param accrualsreversal
	 *            the accrualsreversal to set
	 */
	public void setAccrualsreversal(Double accrualsreversal) {
		this.accrualsreversal = accrualsreversal;
	}

	/**
	 * @return the payments
	 */
	public Double getPayments() {
		return payments;
	}

	/**
	 * @param payments
	 *            the payments to set
	 */
	public void setPayments(Double payments) {
		this.payments = payments;
	}

	/**
	 * @return the noofterminations
	 */
	public Double getNoofterminations() {
		return noofterminations;
	}

	/**
	 * @param noofterminations
	 *            the noofterminations to set
	 */
	public void setNoofterminations(Double noofterminations) {
		this.noofterminations = noofterminations;
	}

	/**
	 * @return the terminationvalue
	 */
	public Double getTerminationvalue() {
		return terminationvalue;
	}

	/**
	 * @param terminationvalue
	 *            the terminationvalue to set
	 */
	public void setTerminationvalue(Double terminationvalue) {
		this.terminationvalue = terminationvalue;
	}

	/**
	 * @return the writeback
	 */
	public Double getWriteback() {
		return writeback;
	}

	/**
	 * @param writeback
	 *            the writeback to set
	 */
	public void setWriteback(Double writeback) {
		this.writeback = writeback;
	}

	/**
	 * @return the status
	 */
	public ApprovalEnum getStatus() {
		return status;
	}

	@Transient
	public ApprovalEnum getStatusValid() {
		return status != null ? status : id != null ? ApprovalEnum.Approved : null;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	/**
	 * @return the approvalDate
	 */
	public Date getApprovalDate() {
		return approvalDate;
	}

	/**
	 * @param approvalDate
	 *            the approvalDate to set
	 */
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	/**
	 * @return the docs
	 */
	public List<Doc> getDocs() {
		return docs;
	}

	/**
	 * @param docs
	 *            the docs to set
	 */
	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}

	public ProjectImplementationPlan getProjectImplementationPlan() {
		return projectImplementationPlan;
	}

	public void setProjectImplementationPlan(ProjectImplementationPlan projectImplementationPlan) {
		this.projectImplementationPlan = projectImplementationPlan;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public TrancheEnum getTrancheEnum() {
		return trancheEnum;
	}

	public void setTrancheEnum(TrancheEnum trancheEnum) {
		this.trancheEnum = trancheEnum;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public ProjectImplementationPlanLearners getProjectImplementationPlanLearners() {
		return projectImplementationPlanLearners;
	}

	public void setProjectImplementationPlanLearners(ProjectImplementationPlanLearners projectImplementationPlanLearners) {
		this.projectImplementationPlanLearners = projectImplementationPlanLearners;
	}

}
