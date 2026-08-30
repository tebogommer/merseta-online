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

import haj.com.entity.enums.OpenClosedEnum;
import haj.com.entity.enums.StatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.framework.IDataEntity;

@Entity
@Table(name = "workplace_monitoring_mitigation_plan")
@AuditTable(value = "workplace_monitoring_mitigation_plan_hist")
@Audited
public class WorkplaceMonitoringMitigationPlan implements IDataEntity {
	
	/** The Constant serialVersionUID. */
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
	
	@Column(name = "system_generated", columnDefinition = "BIT default false")
	private Boolean systemGenerated;
	
	@Column(name = "system_generated_message", nullable = true)
	private String systemGeneratedMessage;
	
	@Column(name = "soft_deleted", columnDefinition = "BIT default false")
	private Boolean softDeleted;
	
	// used to see if CLO provided all information against Plan
	@Column(name = "all_info_provided", columnDefinition = "BIT default false")
	private Boolean allInfoProvided;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workplace_monitoring_site_visit", nullable = true)
	private WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit;
	
	/* Learner Survey Start*/
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learner_survey_link_id", nullable = true)
	private WorkplaceMonitoringLearnerSurvey learnerSurveyLink;
	
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learner_survey_answer_link_id", nullable = true)
	private WorkplaceMonitoringLearnerSurveyAnswers learnerSurveyAnswerLink;
	/* Learner Survey End*/
	
	/* Grant Compliance Survey Start*/
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discretionary_grant_compliance_link_survey_id", nullable = true)
	private WorkplaceMonitoringDiscretionaryGrantComplianceSurvey discretionaryGrantComplianceLinkSurvey;
	/* Grant Compliance Survey End*/
	
	@Enumerated
	@Column(name = "status")
	private StatusEnum status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_submitted", length = 19)
	private Date dateSubmitted;
	
	@Enumerated
	@Column(name = "non_compliance_issue")
	private YesNoEnum nonComplianceIssue;
	
	@Column(name = "action_plan")
	private String actionPlan;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "due_date", length = 19)
	private Date dueDate;
	
	@Column(name = "responsible")
	private String responsible;
	
	@Enumerated
	@Column(name = "open_closed")
	private OpenClosedEnum openClosedEnum;
	
	@Column(name = "can_action", columnDefinition = "BIT default false")
	private Boolean canAction;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_action_user", nullable = true)
	private Users lastActionUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_action_date", length = 19)
	private Date lastActionDate;
	
	@Transient
	private List<Doc> docs;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkplaceMonitoringMitigationPlan other = (WorkplaceMonitoringMitigationPlan) obj;
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

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public YesNoEnum getNonComplianceIssue() {
		return nonComplianceIssue;
	}

	public void setNonComplianceIssue(YesNoEnum nonComplianceIssue) {
		this.nonComplianceIssue = nonComplianceIssue;
	}

	public String getActionPlan() {
		return actionPlan;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public OpenClosedEnum getOpenClosedEnum() {
		return openClosedEnum;
	}

	public void setOpenClosedEnum(OpenClosedEnum openClosedEnum) {
		this.openClosedEnum = openClosedEnum;
	}

	public Boolean getSystemGenerated() {
		return systemGenerated;
	}

	public void setSystemGenerated(Boolean systemGenerated) {
		this.systemGenerated = systemGenerated;
	}

	public Users getLastActionUser() {
		return lastActionUser;
	}

	public void setLastActionUser(Users lastActionUser) {
		this.lastActionUser = lastActionUser;
	}

	public Date getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

	public Users getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	public String getSystemGeneratedMessage() {
		return systemGeneratedMessage;
	}

	public void setSystemGeneratedMessage(String systemGeneratedMessage) {
		this.systemGeneratedMessage = systemGeneratedMessage;
	}

	public Boolean getSoftDeleted() {
		return softDeleted;
	}

	public void setSoftDeleted(Boolean softDeleted) {
		this.softDeleted = softDeleted;
	}

	public WorkplaceMonitoringSiteVisit getWorkplaceMonitoringSiteVisit() {
		return workplaceMonitoringSiteVisit;
	}

	public void setWorkplaceMonitoringSiteVisit(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit) {
		this.workplaceMonitoringSiteVisit = workplaceMonitoringSiteVisit;
	}

	public WorkplaceMonitoringLearnerSurvey getLearnerSurveyLink() {
		return learnerSurveyLink;
	}

	public void setLearnerSurveyLink(WorkplaceMonitoringLearnerSurvey learnerSurveyLink) {
		this.learnerSurveyLink = learnerSurveyLink;
	}

	public WorkplaceMonitoringLearnerSurveyAnswers getLearnerSurveyAnswerLink() {
		return learnerSurveyAnswerLink;
	}

	public void setLearnerSurveyAnswerLink(WorkplaceMonitoringLearnerSurveyAnswers learnerSurveyAnswerLink) {
		this.learnerSurveyAnswerLink = learnerSurveyAnswerLink;
	}

	public WorkplaceMonitoringDiscretionaryGrantComplianceSurvey getDiscretionaryGrantComplianceLinkSurvey() {
		return discretionaryGrantComplianceLinkSurvey;
	}

	public void setDiscretionaryGrantComplianceLinkSurvey(
			WorkplaceMonitoringDiscretionaryGrantComplianceSurvey discretionaryGrantComplianceLinkSurvey) {
		this.discretionaryGrantComplianceLinkSurvey = discretionaryGrantComplianceLinkSurvey;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public List<Doc> getDocs() {
		return docs;
	}

	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	public Boolean getAllInfoProvided() {
		return allInfoProvided;
	}

	public void setAllInfoProvided(Boolean allInfoProvided) {
		this.allInfoProvided = allInfoProvided;
	}

	public Boolean getCanAction() {
		return canAction;
	}

	public void setCanAction(Boolean canAction) {
		this.canAction = canAction;
	}
	
}