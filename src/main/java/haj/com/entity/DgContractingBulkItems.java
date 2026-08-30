package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
import haj.com.entity.enums.BulkApprovalEnum;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "dg_contracting_bulk_items")
public class DgContractingBulkItems implements IDataEntity {

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
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "active_contracts_id", nullable = true)
	private ActiveContracts activeContracts;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dg_contracting_bulk_entry_id", nullable = true)
	private DgContractingBulkEntry dgContractingBulkEntry;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@Enumerated
	@Column(name = "status_assigned")
	private ApprovalEnum statusAssigned;
	
	@Enumerated
	@Column(name = "bulk_approval")
	private BulkApprovalEnum bulkApproval;
	
	@Enumerated
	@Column(name = "withdrawal_appeal_enum")
	private DiscretionalWithdrawalAppealEnum withdrawalAppealEnum;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approval_user_id", nullable = true)
	private Users approvalUser;
	
	@Column(name = "processed", columnDefinition = "BIT default false")
	private Boolean processed;
	
	@Transient
	private List<RejectReasons> rejectReasonsList;
	
	@Transient
	private String rejectionReasonsToString;
	
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
		DgContractingBulkItems other = (DgContractingBulkItems) obj;
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

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public ApprovalEnum getStatusAssigned() {
		return statusAssigned;
	}

	public void setStatusAssigned(ApprovalEnum statusAssigned) {
		this.statusAssigned = statusAssigned;
	}

	public Users getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(Users approvalUser) {
		this.approvalUser = approvalUser;
	}

	public DgContractingBulkEntry getDgContractingBulkEntry() {
		return dgContractingBulkEntry;
	}

	public void setDgContractingBulkEntry(DgContractingBulkEntry dgContractingBulkEntry) {
		this.dgContractingBulkEntry = dgContractingBulkEntry;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public BulkApprovalEnum getBulkApproval() {
		return bulkApproval;
	}

	public void setBulkApproval(BulkApprovalEnum bulkApproval) {
		this.bulkApproval = bulkApproval;
	}

	public List<RejectReasons> getRejectReasonsList() {
		return rejectReasonsList;
	}

	public void setRejectReasonsList(List<RejectReasons> rejectReasonsList) {
		this.rejectReasonsList = rejectReasonsList;
	}

	public String getRejectionReasonsToString() {
		return rejectionReasonsToString;
	}

	public void setRejectionReasonsToString(String rejectionReasonsToString) {
		this.rejectionReasonsToString = rejectionReasonsToString;
	}

	public DiscretionalWithdrawalAppealEnum getWithdrawalAppealEnum() {
		return withdrawalAppealEnum;
	}

	public void setWithdrawalAppealEnum(DiscretionalWithdrawalAppealEnum withdrawalAppealEnum) {
		this.withdrawalAppealEnum = withdrawalAppealEnum;
	}

}
