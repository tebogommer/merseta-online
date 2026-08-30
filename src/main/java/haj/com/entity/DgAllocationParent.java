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

import haj.com.entity.enums.AllocationStatusEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CategorizationEnum;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.entity.lookup.Region;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * DgVerification.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "dg_allocation_parent")
@AuditTable(value = "dg_allocation_parent_hist")
@Audited
public class DgAllocationParent implements IDataEntity {

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "acceptance_date", length = 19)
	private Date acceptanceDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "wsp_id", nullable = true)
	private Wsp wsp;

	@Enumerated
	@Column(name = "company_categorization")
	private CategorizationEnum companyCategorization;

	@Column(name = "dg_levy_amount")
	private BigDecimal dgLevyAmount;
	
	@Column(name = "dg_levy_original_amount")
	private BigDecimal dgLevyOriginalAmount;

	@Column(name = "available_co_funding_amount")
	private BigDecimal availableCoFundingAmount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "allocation_status_enum")
	private AllocationStatusEnum allocationStatusEnum;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "allocation_parent_id", nullable = true)
	private DgAllocationParent allocationParent;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_appealed", length = 19)
	private Date dateAppealed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_appealed", nullable = true)
	private Users userAppealed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_approve_rejected_appeal", nullable = true)
	private Users userApproveRejectedAppeal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_appealed_approved_rejected", length = 19)
	private Date dateAppealedApprovedRejected;

	@Enumerated
	@Column(name = "appeal_status")
	private ApprovalEnum appealStatus;

	@Enumerated
	@Column(name = "discretional_withdrawal_appeal_enum")
	private DiscretionalWithdrawalAppealEnum discretionalWithdrawalAppealEnum;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "submission_user_id", nullable = true)
	private Users submissionUser;

	@Column(name = "submission_date", length = 19)
	private Date submissionDate;

	@Column(name = "already_requested", columnDefinition = "BIT default false")
	private Boolean alreadyRequested;

	@Column(name = "dont_allocate", columnDefinition = "BIT default false")
	private Boolean dontAllocate;

	@Transient
	private List<Doc> docs;
	
	@Transient
	private Region region;	
	
	@Transient
	private ActiveContracts activeContracts;	
	
	@Transient
	private String stringStatus;	
	
	@Transient
	private String reason;
	
	@Transient
	private Double contractValue;

	@Transient
	private Users cloUser;
	
	public DgAllocationParent(Wsp wsp, BigDecimal dgLevyAmount) {
		super();
		this.wsp = wsp;
		this.companyCategorization = wsp.getCompany().getCategorization();
		this.dgLevyAmount = dgLevyAmount;
	}

	public DgAllocationParent() {
		super();
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
		DgAllocationParent other = (DgAllocationParent) obj;
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
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * @param wsp
	 *            the wsp to set
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
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
	 * @return the companyCategorization
	 */
	public CategorizationEnum getCompanyCategorization() {
		return companyCategorization;
	}

	/**
	 * @param companyCategorization
	 *            the companyCategorization to set
	 */
	public void setCompanyCategorization(CategorizationEnum companyCategorization) {
		this.companyCategorization = companyCategorization;
	}

	/**
	 * @return the dgLevyAmount
	 */
	public BigDecimal getDgLevyAmount() {
		return dgLevyAmount;
	}

	/**
	 * @param dgLevyAmount
	 *            the dgLevyAmount to set
	 */
	public void setDgLevyAmount(BigDecimal dgLevyAmount) {
		this.dgLevyAmount = dgLevyAmount;
	}

	/**
	 * @return the availableCoFundingAmount
	 */
	public BigDecimal getAvailableCoFundingAmount() {
		return availableCoFundingAmount;
	}

	/**
	 * @param availableCoFundingAmount
	 *            the availableCoFundingAmount to set
	 */
	public void setAvailableCoFundingAmount(BigDecimal availableCoFundingAmount) {
		this.availableCoFundingAmount = availableCoFundingAmount;
	}

	/**
	 * @return the allocationStatusEnum
	 */
	public AllocationStatusEnum getAllocationStatusEnum() {
		return allocationStatusEnum;
	}

	/**
	 * @param allocationStatusEnum
	 *            the allocationStatusEnum to set
	 */
	public void setAllocationStatusEnum(AllocationStatusEnum allocationStatusEnum) {
		this.allocationStatusEnum = allocationStatusEnum;
	}

	/**
	 * @return the allocationParent
	 */
	public DgAllocationParent getAllocationParent() {
		return allocationParent;
	}

	/**
	 * @param allocationParent
	 *            the allocationParent to set
	 */
	public void setAllocationParent(DgAllocationParent allocationParent) {
		this.allocationParent = allocationParent;
	}

	/**
	 * @return the discretionalWithdrawalAppealEnum
	 */
	public DiscretionalWithdrawalAppealEnum getDiscretionalWithdrawalAppealEnum() {
		return discretionalWithdrawalAppealEnum;
	}

	/**
	 * @param discretionalWithdrawalAppealEnum
	 *            the discretionalWithdrawalAppealEnum to set
	 */
	public void setDiscretionalWithdrawalAppealEnum(DiscretionalWithdrawalAppealEnum discretionalWithdrawalAppealEnum) {
		this.discretionalWithdrawalAppealEnum = discretionalWithdrawalAppealEnum;
	}

	public ApprovalEnum getAppealStatus() {
		return appealStatus;
	}

	public void setAppealStatus(ApprovalEnum appealStatus) {
		this.appealStatus = appealStatus;
	}

	public Date getDateAppealed() {
		return dateAppealed;
	}

	public void setDateAppealed(Date dateAppealed) {
		this.dateAppealed = dateAppealed;
	}

	public Users getUserAppealed() {
		return userAppealed;
	}

	public void setUserAppealed(Users userAppealed) {
		this.userAppealed = userAppealed;
	}

	public Users getUserApproveRejectedAppeal() {
		return userApproveRejectedAppeal;
	}

	public void setUserApproveRejectedAppeal(Users userApproveRejectedAppeal) {
		this.userApproveRejectedAppeal = userApproveRejectedAppeal;
	}

	public Date getDateAppealedApprovedRejected() {
		return dateAppealedApprovedRejected;
	}

	public void setDateAppealedApprovedRejected(Date dateAppealedApprovedRejected) {
		this.dateAppealedApprovedRejected = dateAppealedApprovedRejected;
	}

	/**
	 * @return the acceptanceDate
	 */
	public Date getAcceptanceDate() {
		return acceptanceDate;
	}

	@Transient
	public Date getDueDate() {
		if (acceptanceDate != null) return GenericUtility.addDaysToDate(acceptanceDate, 30);
		else return null;
	}

	/**
	 * @param acceptanceDate
	 *            the acceptanceDate to set
	 */
	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}

	/**
	 * @return the submissionUser
	 */
	public Users getSubmissionUser() {
		return submissionUser;
	}

	/**
	 * @param submissionUser the submissionUser to set
	 */
	public void setSubmissionUser(Users submissionUser) {
		this.submissionUser = submissionUser;
	}

	/**
	 * @return the submissionDate
	 */
	public Date getSubmissionDate() {
		return submissionDate;
	}

	/**
	 * @param submissionDate the submissionDate to set
	 */
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	/**
	 * @return the alreadyRequested
	 */
	public Boolean getAlreadyRequested() {
		return alreadyRequested;
	}

	/**
	 * @param alreadyRequested the alreadyRequested to set
	 */
	public void setAlreadyRequested(Boolean alreadyRequested) {
		this.alreadyRequested = alreadyRequested;
	}

	/**
	 * @return the dontAllocate
	 */
	public Boolean getDontAllocate() {
		return dontAllocate;
	}

	/**
	 * @param dontAllocate the dontAllocate to set
	 */
	public void setDontAllocate(Boolean dontAllocate) {
		this.dontAllocate = dontAllocate;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
	
	public String getStringStatus() {
		return stringStatus;
	}

	public void setStringStatus(String stringStatus) {
		this.stringStatus = stringStatus;
	}

	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	public Double getContractValue() {
		return contractValue;
	}

	public void setContractValue(Double contractValue) {
		this.contractValue = contractValue;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Users getCloUser() {
		return cloUser;
	}

	public void setCloUser(Users cloUser) {
		this.cloUser = cloUser;
	}

	public BigDecimal getDgLevyOriginalAmount() {
		return dgLevyOriginalAmount;
	}

	public void setDgLevyOriginalAmount(BigDecimal dgLevyOriginalAmount) {
		this.dgLevyOriginalAmount = dgLevyOriginalAmount;
	}
	
}
