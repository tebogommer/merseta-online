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

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "payment_request")
public class PaymentRequest implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of ProjectTermination. */
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
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tranch_number")
	private TrancheEnum tranchNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "active_contracts_id", nullable = true)
	private ActiveContracts activeContracts;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "active_contract_detail_id", nullable = true)
	private ActiveContractDetail activeContractDetail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_implementation_plan_id", nullable = true)
	private ProjectImplementationPlan projectImplementationPlan;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "slo_user_id", nullable = true)
	private Users sloUser;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regional_manager_user_id", nullable = true)
	private Users regionalManagerUser;
	
	@Column(name = "rejection_reason", length = 200, nullable = true)
	private String rejectionReasonText;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approval_user_id", nullable = true)
	private Users approvalUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19, nullable = true)
	private Date approvalDate;
	
	@Column(name = "total_payment")
	private BigDecimal totalPayment;
	
	@Column(name = "final_payment_for_contract")
	private Boolean finalPaymentForContract;

	@Transient
	private List<Doc> docs;

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
		PaymentRequest other = (PaymentRequest) obj;
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

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public Users getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(Users approvalUser) {
		this.approvalUser = approvalUser;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Users getSloUser() {
		return sloUser;
	}

	public void setSloUser(Users sloUser) {
		this.sloUser = sloUser;
	}

	public Users getRegionalManagerUser() {
		return regionalManagerUser;
	}

	public void setRegionalManagerUser(Users regionalManagerUser) {
		this.regionalManagerUser = regionalManagerUser;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ActiveContractDetail getActiveContractDetail() {
		return activeContractDetail;
	}

	public void setActiveContractDetail(ActiveContractDetail activeContractDetail) {
		this.activeContractDetail = activeContractDetail;
	}

	public TrancheEnum getTranchNumber() {
		return tranchNumber;
	}

	public void setTranchNumber(TrancheEnum tranchNumber) {
		this.tranchNumber = tranchNumber;
	}

	public String getRejectionReasonText() {
		return rejectionReasonText;
	}

	public void setRejectionReasonText(String rejectionReasonText) {
		this.rejectionReasonText = rejectionReasonText;
	}

	public Boolean getFinalPaymentForContract() {
		return finalPaymentForContract;
	}

	public void setFinalPaymentForContract(Boolean finalPaymentForContract) {
		this.finalPaymentForContract = finalPaymentForContract;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public ProjectImplementationPlan getProjectImplementationPlan() {
		return projectImplementationPlan;
	}

	public void setProjectImplementationPlan(ProjectImplementationPlan projectImplementationPlan) {
		this.projectImplementationPlan = projectImplementationPlan;
	}
}