package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.text.ParseException;
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

import haj.com.constants.HAJConstants;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ContractStatusEnum;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.Department;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Validity;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
@Entity
@Table(name = "active_contracts")
@AuditTable(value = "active_contracts_hist")
@Audited
public class ActiveContracts implements IDataEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dg_allocation_parent_id", nullable = true)
	private DgAllocationParent dgAllocationParent;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	@Column(unique = true, name = "ref_no")
	private String refno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	private Date startdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "original_end_date", length = 19)
	private Date originalenddate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "amendment_date", length = 19)
	private Date amendmentenddate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "revise_date", length = 19)
	private Date reviseddate;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "validity_id", nullable = true)
	private Validity validity;

	@Enumerated(EnumType.STRING)
	@Column(name = "moa_type")
	private MoaTypeEnum moaType;

	@Enumerated(EnumType.STRING)
	@Column(name = "reminder")
	private YesNoEnum reminder;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sign_date", length = 19)
	private Date signdate;

	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", nullable = true)
	private Department department;

	@Enumerated(EnumType.STRING)
	@Column(name = "contract_status_enum")
	private ContractStatusEnum contractStatusEnum;

	@Column(name = "project_name", columnDefinition = "LONGTEXT")
	private String projectname;

	@Column(name = "tranch_intervals")
	private Integer tranchintervals;

	@Column(name = "no_of_learners")
	private Integer nooflearners;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "intervention_id", nullable = true)
	private InterventionType intervention;

	@Column(name = "contract_value")
	private Double contractvalue;

	@Column(name = "accrual_raised")
	private Double accrualraised;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Transient
	private List<Doc> docs;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sdf_id", nullable = true)
	private Users sdf;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sdf_sign_date", length = 19)
	private Date sdfSignDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clo_id", nullable = true)
	private Users clo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "clo_sign_date", length = 19)
	private Date cloSignDate;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crm_id", nullable = true)
	private Users crm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "crm_sign_date", length = 19)
	private Date crmSignDate;

	@Column(name = "recoverable_amount")
	private Double recoverableAmount;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_responsible_moa_sign_off_id", nullable = true)
	private Users userResponsibleMoaSignOff;
	
	@Column(name = "sign_off_state", nullable = true)
	private Boolean signOffState;
	
	@Column(name = "awaiting_batch_sign_off", nullable = true)
	private Boolean awaitingBatchSignOff;
	
	@Column(name = "eletronic_signoff", nullable = true, columnDefinition = "BIT default false")
	private Boolean eletronicSignoff;
	
	@Enumerated
	@Column(name = "withdrawal_appeal_enum")
	private DiscretionalWithdrawalAppealEnum withdrawalAppealEnum;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "withdrawn_user_id", nullable = true)
	private Users withdrawnUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "withdrawn_date", length = 19)
	private Date withdrawnDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rejection_user", nullable = true)
	private Users rejectionUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rejection_date", length = 19)
	private Date rejectionDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "projected_registration_date_start", length = 19)
	private Date projectedRegistrationDateStart;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "projected_registration_date_end", length = 19)
	private Date projectedRegistrationDateEnd;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deadline_date", length = 19)
	private Date deadlineDate;
	
	// indicator if extension request or termination underway
	@Column(name = "etension_termination_workflow_active")
	private Boolean extensionTerminationWorkflowActive;
	
	// used for the scheduler
	@Column(name = "submitted", nullable = true)
	private Boolean submitted;

	/**
	 * @return the signdate
	 */
	public Date getSigndate() {
		return signdate;
	}

	/**
	 * @param signdate
	 *            the signdate to set
	 */
	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	/**
	 * @return the activeContractDetails
	 */
	public List<ActiveContractDetail> getActiveContractDetails() {
		return activeContractDetails;
	}

	/**
	 * @param activeContractDetails
	 *            the activeContractDetails to set
	 */
	public void setActiveContractDetails(List<ActiveContractDetail> activeContractDetails) {
		this.activeContractDetails = activeContractDetails;
	}

	@Transient
	private List<ActiveContractDetail> activeContractDetails;

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
		ActiveContracts other = (ActiveContracts) obj;
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
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return the refno
	 */
	public String getRefno() {
		return refno;
	}

	@Transient
	public String getRefnoAuto() {
		Integer finYear = null;
		if (dgAllocationParent != null && dgAllocationParent.getWsp() != null && dgAllocationParent.getWsp().getFinYearNonNull() != null) {
			finYear = dgAllocationParent.getWsp().getFinYearNonNull();
		}
		if (finYear == null) {
			finYear = HAJConstants.DG_ALLOCATION_FOCUS_YEAR;
		}
		if (dgAllocationParent != null) return dgAllocationParent.getWsp().getCompany().getLevyNumber() + "DGYR" + finYear;
		else return "";
	}

	@Transient
	public Date getEndDateAuto() {
		try {
			Integer finYear = null;
			if (dgAllocationParent != null && dgAllocationParent.getWsp() != null && dgAllocationParent.getWsp().getFinYearNonNull() != null) {
				finYear = dgAllocationParent.getWsp().getFinYearNonNull();
			}
			if (finYear == null) {
				finYear = HAJConstants.DG_ALLOCATION_FOCUS_YEAR;
			}
			if (dgAllocationParent != null) return GenericUtility.addMonthsToDate(GenericUtility.sdf6.parse("30-03-" + finYear), 54);
			else return GenericUtility.addMonthsToDate(startdate, 54);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param refno
	 *            the refno to set
	 */
	public void setRefno(String refno) {
		this.refno = refno;
	}

	/**
	 * @return the startdate
	 */
	public Date getStartdate() {
		return startdate;
	}

	/**
	 * @param startdate
	 *            the startdate to set
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	/**
	 * @return the originalenddate
	 */
	public Date getOriginalenddate() {
		return originalenddate;
	}

	/**
	 * @param originalenddate
	 *            the originalenddate to set
	 */
	public void setOriginalenddate(Date originalenddate) {
		this.originalenddate = originalenddate;
	}

	/**
	 * @return the amendmentenddate
	 */
	public Date getAmendmentenddate() {
		return amendmentenddate;
	}

	/**
	 * @param amendmentenddate
	 *            the amendmentenddate to set
	 */
	public void setAmendmentenddate(Date amendmentenddate) {
		this.amendmentenddate = amendmentenddate;
	}

	/**
	 * @return the reviseddate
	 */
	public Date getReviseddate() {
		return reviseddate;
	}

	/**
	 * @param reviseddate
	 *            the reviseddate to set
	 */
	public void setReviseddate(Date reviseddate) {
		this.reviseddate = reviseddate;
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
	 * @return the validity
	 */
	public Validity getValidity() {
		return validity;
	}

	/**
	 * @param validity
	 *            the validity to set
	 */
	public void setValidity(Validity validity) {
		this.validity = validity;
	}

	/**
	 * @return the reminder
	 */
	public YesNoEnum getReminder() {
		return reminder;
	}

	/**
	 * @param reminder
	 *            the reminder to set
	 */
	public void setReminder(YesNoEnum reminder) {
		this.reminder = reminder;
	}

	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return the contractStatusEnum
	 */
	public ContractStatusEnum getContractStatusEnum() {
		return contractStatusEnum;
	}

	/**
	 * @param contractStatusEnum
	 *            the contractStatusEnum to set
	 */
	public void setContractStatusEnum(ContractStatusEnum contractStatusEnum) {
		this.contractStatusEnum = contractStatusEnum;
	}

	/**
	 * @return the projectname
	 */
	public String getProjectname() {
		return projectname;
	}

	/**
	 * @param projectname
	 *            the projectname to set
	 */
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	/**
	 * @return the tranchintervals
	 */
	public Integer getTranchintervals() {
		return tranchintervals;
	}

	/**
	 * @param tranchintervals
	 *            the tranchintervals to set
	 */
	public void setTranchintervals(Integer tranchintervals) {
		this.tranchintervals = tranchintervals;
	}

	/**
	 * @return the nooflearners
	 */
	public Integer getNooflearners() {
		return nooflearners;
	}

	/**
	 * @param nooflearners
	 *            the nooflearners to set
	 */
	public void setNooflearners(Integer nooflearners) {
		this.nooflearners = nooflearners;
	}

	/**
	 * @return the intervention
	 */
	public InterventionType getIntervention() {
		return intervention;
	}

	/**
	 * @param intervention
	 *            the intervention to set
	 */
	public void setIntervention(InterventionType intervention) {
		this.intervention = intervention;
	}

	/**
	 * @return the contractvalue
	 */
	public Double getContractvalue() {
		return contractvalue;
	}

	/**
	 * @param contractvalue
	 *            the contractvalue to set
	 */
	public void setContractvalue(Double contractvalue) {
		this.contractvalue = contractvalue;
	}

	/**
	 * @return the accrualraised
	 */
	public Double getAccrualraised() {
		return accrualraised;
	}

	/**
	 * @param accrualraised
	 *            the accrualraised to set
	 */
	public void setAccrualraised(Double accrualraised) {
		this.accrualraised = accrualraised;
	}

	/**
	 * @return the status
	 */
	public ApprovalEnum getStatus() {
		return status;
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
	 * @return the moaType
	 */
	public MoaTypeEnum getMoaType() {
		return moaType;
	}

	/**
	 * @param moaType
	 *            the moaType to set
	 */
	public void setMoaType(MoaTypeEnum moaType) {
		this.moaType = moaType;
	}

	/**
	 * @return the sdf
	 */
	public Users getSdf() {
		return sdf;
	}

	/**
	 * @param sdf
	 *            the sdf to set
	 */
	public void setSdf(Users sdf) {
		this.sdf = sdf;
	}

	/**
	 * @return the sdfSignDate
	 */
	public Date getSdfSignDate() {
		return sdfSignDate;
	}

	/**
	 * @param sdfSignDate
	 *            the sdfSignDate to set
	 */
	public void setSdfSignDate(Date sdfSignDate) {
		this.sdfSignDate = sdfSignDate;
	}

	/**
	 * @return the clo
	 */
	public Users getClo() {
		return clo;
	}

	/**
	 * @param clo
	 *            the clo to set
	 */
	public void setClo(Users clo) {
		this.clo = clo;
	}

	/**
	 * @return the cloSignDate
	 */
	public Date getCloSignDate() {
		return cloSignDate;
	}

	/**
	 * @param cloSignDate
	 *            the cloSignDate to set
	 */
	public void setCloSignDate(Date cloSignDate) {
		this.cloSignDate = cloSignDate;
	}

	/**
	 * @return the crm
	 */
	public Users getCrm() {
		return crm;
	}

	/**
	 * @param crm
	 *            the crm to set
	 */
	public void setCrm(Users crm) {
		this.crm = crm;
	}

	/**
	 * @return the crmSignDate
	 */
	public Date getCrmSignDate() {
		return crmSignDate;
	}

	/**
	 * @param crmSignDate
	 *            the crmSignDate to set
	 */
	public void setCrmSignDate(Date crmSignDate) {
		this.crmSignDate = crmSignDate;
	}

	public Double getRecoverableAmount() {
		return recoverableAmount;
	}

	public void setRecoverableAmount(Double recoverableAmount) {
		this.recoverableAmount = recoverableAmount;
	}

	public Users getUserResponsibleMoaSignOff() {
		return userResponsibleMoaSignOff;
	}

	public void setUserResponsibleMoaSignOff(Users userResponsibleMoaSignOff) {
		this.userResponsibleMoaSignOff = userResponsibleMoaSignOff;
	}

	public Boolean getSignOffState() {
		return signOffState;
	}

	public void setSignOffState(Boolean signOffState) {
		this.signOffState = signOffState;
	}

	public Boolean getAwaitingBatchSignOff() {
		return awaitingBatchSignOff;
	}

	public void setAwaitingBatchSignOff(Boolean awaitingBatchSignOff) {
		this.awaitingBatchSignOff = awaitingBatchSignOff;
	}

	public DiscretionalWithdrawalAppealEnum getWithdrawalAppealEnum() {
		return withdrawalAppealEnum;
	}

	public void setWithdrawalAppealEnum(DiscretionalWithdrawalAppealEnum withdrawalAppealEnum) {
		this.withdrawalAppealEnum = withdrawalAppealEnum;
	}

	public Users getWithdrawnUser() {
		return withdrawnUser;
	}

	public void setWithdrawnUser(Users withdrawnUser) {
		this.withdrawnUser = withdrawnUser;
	}

	public Date getWithdrawnDate() {
		return withdrawnDate;
	}

	public void setWithdrawnDate(Date withdrawnDate) {
		this.withdrawnDate = withdrawnDate;
	}

	public Boolean getEletronicSignoff() {
		return eletronicSignoff;
	}

	public void setEletronicSignoff(Boolean eletronicSignoff) {
		this.eletronicSignoff = eletronicSignoff;
	}

	public Users getRejectionUser() {
		return rejectionUser;
	}

	public void setRejectionUser(Users rejectionUser) {
		this.rejectionUser = rejectionUser;
	}

	public Date getRejectionDate() {
		return rejectionDate;
	}

	public void setRejectionDate(Date rejectionDate) {
		this.rejectionDate = rejectionDate;
	}

	public Date getProjectedRegistrationDateStart() {
		return projectedRegistrationDateStart;
	}

	public void setProjectedRegistrationDateStart(Date projectedRegistrationDateStart) {
		this.projectedRegistrationDateStart = projectedRegistrationDateStart;
	}

	public Date getProjectedRegistrationDateEnd() {
		return projectedRegistrationDateEnd;
	}

	public void setProjectedRegistrationDateEnd(Date projectedRegistrationDateEnd) {
		this.projectedRegistrationDateEnd = projectedRegistrationDateEnd;
	}

	public Boolean getExtensionTerminationWorkflowActive() {
		return extensionTerminationWorkflowActive;
	}

	public void setExtensionTerminationWorkflowActive(Boolean extensionTerminationWorkflowActive) {
		this.extensionTerminationWorkflowActive = extensionTerminationWorkflowActive;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

}
