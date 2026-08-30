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
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Bank;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "banking_details")
@AuditTable(value = "banking_details_hist")
@Audited
public class BankingDetails implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of BankingDetails. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** Create Date of BankingDetails. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "confirmation_date", length = 19)
	private Date confirmationDate;

	/**
	 * The bank acc number. bank account number cannot be empty
	 */
	@Column(name = "bank_acc_number", length = 100, nullable = false)
	@NotEmpty(message = "please enter bank account number")
	private String bankAccNumber;

	/**
	 * The branch code. branch code cannot be empty
	 */
	@Column(name = "branch_code", length = 100, nullable = false)
	@NotEmpty(message = "branch code cannot be empty, please enter one")
	private String branchCode;

	/**
	 * The bank holder. bank holder cannot be empty
	 */
	@Column(name = "bank_holder", length = 100, nullable = false)
	@NotEmpty(message = "bank holder cannot be empty")
	private String bankHolder;

	/**
	 * The swift code. swift code cannot be empty.
	 */
	@Column(name = "swift_code", length = 100, nullable = true)
	// @NotEmpty(message="please enter a swift code associated with the bank")
	private String swiftCode;

	/**
	 * The bank. bank id cannot be null
	 */
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bank_id", nullable = true)
	private Bank bank;

	/** The company. */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Column(name = "notification_sent_regarding_original")
	private Boolean notificationSentRegardingOriginal;

	@Column(name = "are_original_required")
	private Boolean areOriginalRequired;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "approval_user_id", nullable = true)
	private Users approvalUser;

	@Transient
	private List<Doc> docs;
	
	@Transient
	private List<Doc> oldDocs;
	
	@Transient
	private String rejectReasons;

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
		BankingDetails other = (BankingDetails) obj;
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

	/**
	 * Gets the bank acc number. bank account number cannot be null
	 * 
	 * @return the bank acc number
	 */

	public String getBankAccNumber() {
		return bankAccNumber;
	}

	/**
	 * Gets the masked bank acc number.
	 *
	 * @return the bank acc number
	 */
	@Transient
	public String getMaskedBankAccNumber() {
		if (bankAccNumber != null && bankAccNumber.length() > 0) return "*********" + bankAccNumber.substring(bankAccNumber.length() - 4, bankAccNumber.length());
		else return "";
	}

	/**
	 * Sets the bank acc number.
	 *
	 * @param bankAccNumber
	 *            the new bank acc number
	 */
	public void setBankAccNumber(String bankAccNumber) {
		this.bankAccNumber = bankAccNumber;
	}

	/**
	 * Gets the branch code.
	 *
	 * @return the branch code
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * Sets the branch code.
	 *
	 * @param branchCode
	 *            the new branch code
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * Gets the bank holder.
	 *
	 * @return the bank holder
	 */
	public String getBankHolder() {
		return bankHolder;
	}

	/**
	 * Sets the bank holder.
	 *
	 * @param bankHolder
	 *            the new bank holder
	 */
	public void setBankHolder(String bankHolder) {
		this.bankHolder = bankHolder;
	}

	/**
	 * Gets the swift code.
	 *
	 * @return the swift code
	 */
	public String getSwiftCode() {
		return swiftCode;
	}

	/**
	 * Sets the swift code.
	 *
	 * @param swiftCode
	 *            the new swift code
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * Gets the bank.
	 *
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * Sets the bank.
	 *
	 * @param bank
	 *            the new bank
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public List<Doc> getOldDocs() {
		return oldDocs;
	}

	public void setOldDocs(List<Doc> oldDocs) {
		this.oldDocs = oldDocs;
	}

	public Boolean getAreOriginalRequired() {
		return areOriginalRequired;
	}

	public void setAreOriginalRequired(Boolean areOriginalRequired) {
		this.areOriginalRequired = areOriginalRequired;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public Boolean getNotificationSentRegardingOriginal() {
		return notificationSentRegardingOriginal;
	}

	public void setNotificationSentRegardingOriginal(Boolean notificationSentRegardingOriginal) {
		this.notificationSentRegardingOriginal = notificationSentRegardingOriginal;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Users getApprovalUser() {
		return approvalUser;
	}

	public void setApprovalUser(Users approvalUser) {
		this.approvalUser = approvalUser;
	}

	public String getRejectReasons() {
		return rejectReasons;
	}

	public void setRejectReasons(String rejectReasons) {
		this.rejectReasons = rejectReasons;
	}
}
