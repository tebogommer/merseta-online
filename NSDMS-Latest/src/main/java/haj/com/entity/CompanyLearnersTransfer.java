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
import org.hibernate.envers.RelationTargetAuditMode;

import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyLearnersTransferRecommendation;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.LearnerTransferApproval;
import haj.com.entity.enums.LearnerTransferTypeEnum;
import haj.com.entity.enums.TransferRequestTypeEnum;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * CompanyUsers.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "company_learners_transfer")
@AuditTable(value = "company_learners_transfer_hist")
@Audited
public class CompanyLearnersTransfer implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	// the company learner link
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;
	
	// Original company assigned to company learner link
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_company_assigned_id", nullable = true)
	private Company currentCompanyAssigned;
	
	// Original provider application linked to company learner
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orginal_training_provider_application_id", nullable = true)
	private TrainingProviderApplication orginalTrainingProviderApplication;

	// company learner will transfer to
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transfer_to_company_id", nullable = true)
	private Company transferToCompany;
	
	// Transfer provider application
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transfer_training_provider_application_id", nullable = true)
	private TrainingProviderApplication transferTrainingProviderApplication;

	// selection: who requested the transfer
	@Enumerated
	@Column(name = "transfer_request_type")
	private TransferRequestTypeEnum transferRequestType;

	// selection: transfer type
	@Enumerated
	@Column(name = "learner_transfer_type")
	private LearnerTransferTypeEnum learnerTransferType;
	
	// who its with in the Workflow
	@Enumerated
	@Column(name = "learner_transfer_approval")
	private LearnerTransferApproval learnerTransferApproval;

	@Column(name = "transfer_reason")
	private String transferReason;
	
	// the Initiator
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;
	
	// the learner 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learner_user_id", nullable = true)
	private Users learnerUser;
	
	// the new company rep user
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transfer_company_representative_user_id", nullable = true)
	private Users transferCompanyRepresentativeUser;
	
	// the MerSETA Admin user 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_user_id", nullable = true)
	private Users adminUser;
	
	// the MerSETA coordinator user 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coordinator_user_id", nullable = true)
	private Users coordinatorUser;
	
	// user who approves
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "approve_user_id", nullable = true)
	private Users approveUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "company_selected_approval_user", nullable = true)
	private Users companySelectedApprovalUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date_company_selected", length = 19)
	private Date approvalDateCompanySelected;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "learner_approved", nullable = true)
	private Users learnerApproved;
	
	@Enumerated
	@Column(name = "company_learner_previous_status")
	private ApprovalEnum companyLearnerPreviousStatus;
	
	@Enumerated
	@Column(name = "learner_previous_status")
	private LearnerStatusEnum learnerPreviousStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date_learner", length = 19)
	private Date approvalDateLearner;
	
	@Column(name = "with_new_provider")
	private Boolean withNewProvider;
	
	@Column(name = "rejection_note", columnDefinition= "LONGTEXT")
	private String rejectionNote;
	
	@Column(name = "site_visit_date", length = 19)
	private Date siteVisitDate;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tasks_id", nullable = true)
	private Tasks tasks;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Transient
	private List<Doc> docs;
	
	@Enumerated
	@Column(name = "recommendation")
	private CompanyLearnersTransferRecommendation recommendation;
	

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
		CompanyLearnersTransfer other = (CompanyLearnersTransfer) obj;
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

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public Company getTransferToCompany() {
		return transferToCompany;
	}

	public void setTransferToCompany(Company transferToCompany) {
		this.transferToCompany = transferToCompany;
	}

	public TransferRequestTypeEnum getTransferRequestType() {
		return transferRequestType;
	}

	public void setTransferRequestType(TransferRequestTypeEnum transferRequestType) {
		this.transferRequestType = transferRequestType;
	}

	public LearnerTransferTypeEnum getLearnerTransferType() {
		return learnerTransferType;
	}

	public void setLearnerTransferType(LearnerTransferTypeEnum learnerTransferType) {
		this.learnerTransferType = learnerTransferType;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public String getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
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

	public Users getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(Users approveUser) {
		this.approveUser = approveUser;
	}

	public LearnerTransferApproval getLearnerTransferApproval() {
		return learnerTransferApproval;
	}

	public void setLearnerTransferApproval(LearnerTransferApproval learnerTransferApproval) {
		this.learnerTransferApproval = learnerTransferApproval;
	}

	public Users getCompanySelectedApprovalUser() {
		return companySelectedApprovalUser;
	}

	public void setCompanySelectedApprovalUser(Users companySelectedApprovalUser) {
		this.companySelectedApprovalUser = companySelectedApprovalUser;
	}

	public Date getApprovalDateCompanySelected() {
		return approvalDateCompanySelected;
	}

	public void setApprovalDateCompanySelected(Date approvalDateCompanySelected) {
		this.approvalDateCompanySelected = approvalDateCompanySelected;
	}

	public Users getLearnerApproved() {
		return learnerApproved;
	}

	public void setLearnerApproved(Users learnerApproved) {
		this.learnerApproved = learnerApproved;
	}

	public Date getApprovalDateLearner() {
		return approvalDateLearner;
	}

	public void setApprovalDateLearner(Date approvalDateLearner) {
		this.approvalDateLearner = approvalDateLearner;
	}

	public Boolean getWithNewProvider() {
		return withNewProvider;
	}

	public void setWithNewProvider(Boolean withNewProvider) {
		this.withNewProvider = withNewProvider;
	}

	public ApprovalEnum getCompanyLearnerPreviousStatus() {
		return companyLearnerPreviousStatus;
	}

	public void setCompanyLearnerPreviousStatus(ApprovalEnum companyLearnerPreviousStatus) {
		this.companyLearnerPreviousStatus = companyLearnerPreviousStatus;
	}

	public LearnerStatusEnum getLearnerPreviousStatus() {
		return learnerPreviousStatus;
	}

	public void setLearnerPreviousStatus(LearnerStatusEnum learnerPreviousStatus) {
		this.learnerPreviousStatus = learnerPreviousStatus;
	}

	public String getRejectionNote() {
		return rejectionNote;
	}

	public void setRejectionNote(String rejectionNote) {
		this.rejectionNote = rejectionNote;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public Date getSiteVisitDate() {
		return siteVisitDate;
	}

	public void setSiteVisitDate(Date siteVisitDate) {
		this.siteVisitDate = siteVisitDate;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	public CompanyLearnersTransferRecommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(CompanyLearnersTransferRecommendation recommendation) {
		this.recommendation = recommendation;
	}

	public Company getCurrentCompanyAssigned() {
		return currentCompanyAssigned;
	}

	public void setCurrentCompanyAssigned(Company currentCompanyAssigned) {
		this.currentCompanyAssigned = currentCompanyAssigned;
	}

	public TrainingProviderApplication getOrginalTrainingProviderApplication() {
		return orginalTrainingProviderApplication;
	}

	public void setOrginalTrainingProviderApplication(TrainingProviderApplication orginalTrainingProviderApplication) {
		this.orginalTrainingProviderApplication = orginalTrainingProviderApplication;
	}

	public TrainingProviderApplication getTransferTrainingProviderApplication() {
		return transferTrainingProviderApplication;
	}

	public void setTransferTrainingProviderApplication(TrainingProviderApplication transferTrainingProviderApplication) {
		this.transferTrainingProviderApplication = transferTrainingProviderApplication;
	}

	public Users getLearnerUser() {
		return learnerUser;
	}

	public void setLearnerUser(Users learnerUser) {
		this.learnerUser = learnerUser;
	}

	public Users getTransferCompanyRepresentativeUser() {
		return transferCompanyRepresentativeUser;
	}

	public void setTransferCompanyRepresentativeUser(Users transferCompanyRepresentativeUser) {
		this.transferCompanyRepresentativeUser = transferCompanyRepresentativeUser;
	}

	public Users getCoordinatorUser() {
		return coordinatorUser;
	}

	public void setCoordinatorUser(Users coordinatorUser) {
		this.coordinatorUser = coordinatorUser;
	}
}
