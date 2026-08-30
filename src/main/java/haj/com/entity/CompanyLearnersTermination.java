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
import haj.com.entity.enums.CreatedByEnum;
import haj.com.entity.enums.SignoffByEnum;
import haj.com.entity.enums.TerminationTypeEnum;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "company_learners_termination")
public class CompanyLearnersTermination implements IDataEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;
	
	
	@Column(name = "investigate_date", length = 19)
	private Date investigateDate;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "investigator_user_id", nullable = true)
	private Users investigatorUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_learners_id", nullable = true)
	private CompanyLearners companyLearners;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_working_day_of_learner", length = 19)
	private Date lastWorkingDayOfLearner;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "approval_date", length = 19)
	private Date approvalDate;

	@Enumerated
	@Column(name = "status")
	private ApprovalEnum status;
	
	@Enumerated
	@Column(name = "termination_type_enum")
	private TerminationTypeEnum terminationTypeEnum;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", nullable = true)
	private Users createUser;
	
	/** The ETQA Review Committee Schedule. */
	@JoinColumn(name="review_committee_meeting_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeeting reviewCommitteeMeeting;
	
	@JoinColumn(name="review_committee_meeting_agenda_id")
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_learners_termination_parent_id", nullable = true)
	private CompanyLearnersTermination companyLearnersTerminationParent;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	private TrainingProviderApplication trainingProviderApplication;
	
	/**  task id link. */
	@ManyToOne(fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="task_id", nullable=true)
	private Tasks task;
	
	@Enumerated
	@Column(name = "signoff_by_enum")
	private SignoffByEnum signoffByEnum;
	
	@Enumerated
	@Column(name = "created_by_enum")
	private CreatedByEnum createdByEnum;

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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CompanyLearnersTermination other = (CompanyLearnersTermination) obj;
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

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
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

	public Date getLastWorkingDayOfLearner() {
		return lastWorkingDayOfLearner;
	}

	public void setLastWorkingDayOfLearner(Date lastWorkingDayOfLearner) {
		this.lastWorkingDayOfLearner = lastWorkingDayOfLearner;
	}

	public TerminationTypeEnum getTerminationTypeEnum() {
		return terminationTypeEnum;
	}

	public void setTerminationTypeEnum(TerminationTypeEnum terminationTypeEnum) {
		this.terminationTypeEnum = terminationTypeEnum;
	}

	public Date getInvestigateDate() {
		return investigateDate;
	}

	public void setInvestigateDate(Date investigateDate) {
		this.investigateDate = investigateDate;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the reviewCommitteeMeeting
	 */
	public ReviewCommitteeMeeting getReviewCommitteeMeeting() {
		return reviewCommitteeMeeting;
	}

	/**
	 * @param reviewCommitteeMeeting the reviewCommitteeMeeting to set
	 */
	public void setReviewCommitteeMeeting(ReviewCommitteeMeeting reviewCommitteeMeeting) {
		this.reviewCommitteeMeeting = reviewCommitteeMeeting;
	}

	/**
	 * @return the reviewCommitteeMeetingAgenda
	 */
	public ReviewCommitteeMeetingAgenda getReviewCommitteeMeetingAgenda() {
		return reviewCommitteeMeetingAgenda;
	}

	/**
	 * @param reviewCommitteeMeetingAgenda the reviewCommitteeMeetingAgenda to set
	 */
	public void setReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda) {
		this.reviewCommitteeMeetingAgenda = reviewCommitteeMeetingAgenda;
	}

	/**
	 * @return the companyLearnersTerminationParent
	 */
	public CompanyLearnersTermination getCompanyLearnersTerminationParent() {
		return companyLearnersTerminationParent;
	}

	/**
	 * @param companyLearnersTerminationParent the companyLearnersTerminationParent to set
	 */
	public void setCompanyLearnersTerminationParent(CompanyLearnersTermination companyLearnersTerminationParent) {
		this.companyLearnersTerminationParent = companyLearnersTerminationParent;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public Tasks getTask() {
		return task;
	}

	public void setTask(Tasks task) {
		this.task = task;
	}

	public Users getInvestigatorUser() {
		return investigatorUser;
	}

	public void setInvestigatorUser(Users investigatorUser) {
		this.investigatorUser = investigatorUser;
	}

	public SignoffByEnum getSignoffByEnum() {
		return signoffByEnum;
	}

	public CreatedByEnum getCreatedByEnum() {
		return createdByEnum;
	}

	public void setSignoffByEnum(SignoffByEnum signoffByEnum) {
		this.signoffByEnum = signoffByEnum;
	}

	public void setCreatedByEnum(CreatedByEnum createdByEnum) {
		this.createdByEnum = createdByEnum;
	}
}
