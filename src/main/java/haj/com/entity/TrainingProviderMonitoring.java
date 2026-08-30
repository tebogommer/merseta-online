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
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * Blank.
 *
 * @author Techfinium
 */
@Entity
@Table(name = "training_provider_monitoring")
public class TrainingProviderMonitoring implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of CompanyUsers. */
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
	@JoinColumn(name = "user_id", nullable = true)
	private Users user;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	private TrainingProviderApplication trainingProviderApplication;

	/** The company. */
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "monitoring_date", length = 19)
	private Date monitoringDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "initiating_qa_id", nullable = true)
	private Users initiatingQA;

	@Column(name = "audit", nullable = true)
	private Boolean audit;
	
	@Column(name = "qa_comments", columnDefinition="LONGTEXT")
	private String qaComments;
	
	@Column(name = "qa_recommendation", columnDefinition="LONGTEXT")
	private String qaRecommendation;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spd_user_id", nullable = true)
	private Users spdUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "spd_user_date_submission", length = 19)
	private Date spdUserDateSubmission;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "qa_date_submission", length = 19)
	private Date qaDateSubmission;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qa_manager_id", nullable = true)
	private Users qaManager;
	
	@Transient
	private List<Doc> docs;
	
	@Transient
	private List<AuditorMonitorReview> auditorMonitorReviewList;
	
	@Transient
	private String rejectionReasons;
	
	@Transient
	private List<RejectReasons> rejectionReasonsList;

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
		TrainingProviderMonitoring other = (TrainingProviderMonitoring) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/**
	 * Instantiates a new company users.
	 */
	public TrainingProviderMonitoring() {
		super();
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public Date getMonitoringDate() {
		return monitoringDate;
	}

	public void setMonitoringDate(Date monitoringDate) {
		this.monitoringDate = monitoringDate;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Users getInitiatingQA() {
		return initiatingQA;
	}

	public void setInitiatingQA(Users initiatingQA) {
		this.initiatingQA = initiatingQA;
	}

	public List<AuditorMonitorReview> getAuditorMonitorReviewList() {
		return auditorMonitorReviewList;
	}

	public void setAuditorMonitorReviewList(List<AuditorMonitorReview> auditorMonitorReviewList) {
		this.auditorMonitorReviewList = auditorMonitorReviewList;
	}

	public Boolean getAudit() {
		return audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
	}

	public String getQaComments() {
		return qaComments;
	}

	public void setQaComments(String qaComments) {
		this.qaComments = qaComments;
	}

	public String getQaRecommendation() {
		return qaRecommendation;
	}

	public void setQaRecommendation(String qaRecommendation) {
		this.qaRecommendation = qaRecommendation;
	}

	public Users getSpdUser() {
		return spdUser;
	}

	public void setSpdUser(Users spdUser) {
		this.spdUser = spdUser;
	}

	public Date getSpdUserDateSubmission() {
		return spdUserDateSubmission;
	}

	public void setSpdUserDateSubmission(Date spdUserDateSubmission) {
		this.spdUserDateSubmission = spdUserDateSubmission;
	}

	public Date getQaDateSubmission() {
		return qaDateSubmission;
	}

	public void setQaDateSubmission(Date qaDateSubmission) {
		this.qaDateSubmission = qaDateSubmission;
	}

	public Users getQaManager() {
		return qaManager;
	}

	public void setQaManager(Users qaManager) {
		this.qaManager = qaManager;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public String getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(String rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}

	public List<RejectReasons> getRejectionReasonsList() {
		return rejectionReasonsList;
	}

	public void setRejectionReasonsList(List<RejectReasons> rejectionReasonsList) {
		this.rejectionReasonsList = rejectionReasonsList;
	}

}
