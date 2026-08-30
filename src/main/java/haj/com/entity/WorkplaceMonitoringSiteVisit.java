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
@Table(name = "workplace_monitoring_site_visit")
@AuditTable(value = "workplace_monitoring_site_visit_hist")
@Audited
public class WorkplaceMonitoringSiteVisit implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

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
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "monitoring_date", length = 19)
	private Date monitoringDate;
	
	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approved_date", length = 19)
	private Date approvedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approved_user_id", nullable = true)
	private Users approvedUser;
	
	/* Indicator who was the CLO */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clo_user_id", nullable = true)
	private Users cloUser;
	
	/* Indicator who was the CLO By Manager */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "manager_assigned_clo_user_id", nullable = true)
	private Users managerAssignedCloUser;
	
	/* Indicator who was the CRM */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crm_user_id", nullable = true)
	private Users crmUser;
	
	/* Indicator who was the CRM By Manager */
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "manager_assigned_crm_user_id", nullable = true)
	private Users managerAssignedCrmUser;
	
	/* When the monitoring is in sign off state */
	@Column(name = "sign_off_state", columnDefinition = "BIT default false")
	private Boolean signOffState;
	
	/* Indicator if non compliances Identified */
	@Column(name = "non_compliances_identified", columnDefinition = "BIT default false")
	private Boolean nonCompliancesIdentified;
	
	/* When non compliance issues identified and in holding area */
	@Column(name = "non_compliance_holding_area", columnDefinition = "BIT default false")
	private Boolean nonComplianceHoldingArea;
	
	/* Date when submitted for non-compliance approval */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "non_compliance_submitted_date", length = 19)
	private Date nonComplianceSubmittedDate;
	
	/* User final approved non-compliance approval */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_compliance_submitted_user", nullable = true)
	private Users nonComplianceSubmittedUser;
	
	/* Date final approved non-compliance approval */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "non_compliance_approval_ate", length = 19)
	private Date nonComplianceApprovalDate;
	
	/* User final approved non-compliance approval */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "non_compliance_approval_user_id", nullable = true)
	private Users nonComplianceApprovalUser;
	
	/* 
	 * This is used when the monitoring date has not passed and no tasks have generated 
	 * Alternatively is when the date changes and task is closed awaiting for monitoring date
	 */
	@Column(name = "awaiting_initiation", columnDefinition = "BIT default false")
	private Boolean awaitingInitiation;
	
	@Transient
	private List<Doc> docs;

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkplaceMonitoringSiteVisit other = (WorkplaceMonitoringSiteVisit) obj;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public Date getMonitoringDate() {
		return monitoringDate;
	}

	public void setMonitoringDate(Date monitoringDate) {
		this.monitoringDate = monitoringDate;
	}

	public ApprovalEnum getStatus() {
		return status;
	}

	public void setStatus(ApprovalEnum status) {
		this.status = status;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Users getCloUser() {
		return cloUser;
	}

	public void setCloUser(Users cloUser) {
		this.cloUser = cloUser;
	}

	public Users getCrmUser() {
		return crmUser;
	}

	public void setCrmUser(Users crmUser) {
		this.crmUser = crmUser;
	}

	public Boolean getSignOffState() {
		return signOffState;
	}

	public void setSignOffState(Boolean signOffState) {
		this.signOffState = signOffState;
	}

	public Boolean getNonComplianceHoldingArea() {
		return nonComplianceHoldingArea;
	}

	public void setNonComplianceHoldingArea(Boolean nonComplianceHoldingArea) {
		this.nonComplianceHoldingArea = nonComplianceHoldingArea;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Boolean getAwaitingInitiation() {
		return awaitingInitiation;
	}

	public void setAwaitingInitiation(Boolean awaitingInitiation) {
		this.awaitingInitiation = awaitingInitiation;
	}

	public Users getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(Users approvedUser) {
		this.approvedUser = approvedUser;
	}

	public Users getManagerAssignedCloUser() {
		return managerAssignedCloUser;
	}

	public void setManagerAssignedCloUser(Users managerAssignedCloUser) {
		this.managerAssignedCloUser = managerAssignedCloUser;
	}

	public Users getManagerAssignedCrmUser() {
		return managerAssignedCrmUser;
	}

	public void setManagerAssignedCrmUser(Users managerAssignedCrmUser) {
		this.managerAssignedCrmUser = managerAssignedCrmUser;
	}

	public Boolean getNonCompliancesIdentified() {
		return nonCompliancesIdentified;
	}

	public void setNonCompliancesIdentified(Boolean nonCompliancesIdentified) {
		this.nonCompliancesIdentified = nonCompliancesIdentified;
	}

	public Date getNonComplianceSubmittedDate() {
		return nonComplianceSubmittedDate;
	}

	public void setNonComplianceSubmittedDate(Date nonComplianceSubmittedDate) {
		this.nonComplianceSubmittedDate = nonComplianceSubmittedDate;
	}

	public Date getNonComplianceApprovalDate() {
		return nonComplianceApprovalDate;
	}

	public void setNonComplianceApprovalDate(Date nonComplianceApprovalDate) {
		this.nonComplianceApprovalDate = nonComplianceApprovalDate;
	}

	public Users getNonComplianceApprovalUser() {
		return nonComplianceApprovalUser;
	}

	public void setNonComplianceApprovalUser(Users nonComplianceApprovalUser) {
		this.nonComplianceApprovalUser = nonComplianceApprovalUser;
	}

	public Users getNonComplianceSubmittedUser() {
		return nonComplianceSubmittedUser;
	}

	public void setNonComplianceSubmittedUser(Users nonComplianceSubmittedUser) {
		this.nonComplianceSubmittedUser = nonComplianceSubmittedUser;
	}
	
}