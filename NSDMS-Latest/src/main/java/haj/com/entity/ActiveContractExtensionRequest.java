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
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "active_contract_extension_request")
@AuditTable(value = "active_contract_extension_request_hist")
@Audited
public class ActiveContractExtensionRequest implements IDataEntity{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
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
	@JoinColumn(name = "active_aontracts_id", nullable = true)
	private ActiveContracts activeContracts;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_user_id", nullable = true)
	private Users requestUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "orginal_due_date", length = 19)
	private Date orginalDueDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "extenion_date", length = 19)
	private Date extenionDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "entered_date", length = 19)
	private Date enteredDate;
	
	@Column(name = "use_system_generated_Date", columnDefinition = "BIT default true")
	private Boolean useSystemGeneratedDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_updated_date_id", nullable = true)
	private Users userUpdatedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_updated_extension_date", length = 19)
	private Date dateUpdatedExtensionDate;
	
	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approve_user_id", nullable = true)
	private Users approveUser;
	
	@Transient
	private List<Doc> docs;
    
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		ActiveContractExtensionRequest other = (ActiveContractExtensionRequest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	public ActiveContractExtensionRequest() {
		super();
	}
	
	public ActiveContractExtensionRequest(ActiveContracts activeContracts, Users requestUser, Date orginalDueDate, Date extenionDate, Boolean useSystemGeneratedDate, ApprovalEnum status) {
		super();
		this.activeContracts = activeContracts;
		this.requestUser = requestUser;
		this.orginalDueDate = orginalDueDate;
		this.extenionDate = extenionDate;
		this.useSystemGeneratedDate = useSystemGeneratedDate;
		this.status = status;
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
	 * @param id the id to set
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
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	public Users getRequestUser() {
		return requestUser;
	}

	public void setRequestUser(Users requestUser) {
		this.requestUser = requestUser;
	}

	public Date getOrginalDueDate() {
		return orginalDueDate;
	}

	public void setOrginalDueDate(Date orginalDueDate) {
		this.orginalDueDate = orginalDueDate;
	}

	public Date getExtenionDate() {
		return extenionDate;
	}

	public void setExtenionDate(Date extenionDate) {
		this.extenionDate = extenionDate;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Users getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(Users approveUser) {
		this.approveUser = approveUser;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}
	
	public Boolean getUseSystemGeneratedDate() {
		return useSystemGeneratedDate;
	}

	public void setUseSystemGeneratedDate(Boolean useSystemGeneratedDate) {
		this.useSystemGeneratedDate = useSystemGeneratedDate;
	}

	public Date getEnteredDate() {
		return enteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}

	public Users getUserUpdatedDate() {
		return userUpdatedDate;
	}

	public void setUserUpdatedDate(Users userUpdatedDate) {
		this.userUpdatedDate = userUpdatedDate;
	}

	public Date getDateUpdatedExtensionDate() {
		return dateUpdatedExtensionDate;
	}

	public void setDateUpdatedExtensionDate(Date dateUpdatedExtensionDate) {
		this.dateUpdatedExtensionDate = dateUpdatedExtensionDate;
	}

}