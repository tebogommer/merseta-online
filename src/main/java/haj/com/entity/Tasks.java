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

import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.RagEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class Tasks.
 */
@Entity
@Table(name = "tasks")
public class Tasks implements IDataEntity {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/** date task created. */
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Transient
	private Date reviewDate;

	/** user created task. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "create_user_id", insertable = true, updatable = true, nullable = true)
	private Users createUser;

	/** last action user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "action_user_id", insertable = true, updatable = true, nullable = true)
	private Users actionUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "previous_task_id", insertable = true, updatable = true, nullable = true)
	private Tasks previousTask;

	/** last action date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "action_date", length = 19)
	private Date actionDate;

	/** status of task. */
	@Enumerated
	@Column(name = "task_status")
	private TaskStatusEnum taskStatus;

	/** task description or detail. */
	@Column(name = "description", columnDefinition = "LONGTEXT")
	private String description;

	/** date user started task. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", length = 19)
	private Date startDate;

	/** date task due. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "due_date", length = 19)
	private Date dueDate;

	/** date task closed or completed. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "completion_date", length = 19)
	private Date completionDate;

	/** page to direct to. */
	@Column(name = "task_direct_page", length = 100, nullable = true)
	public String taskDirectPage;

	/** target key for task. */
	@Column(name = "target_key", nullable = true)
	private Long targetKey;

	/** target class for task. */
	@Column(name = "target_class", nullable = true)
	private String targetClass;

	/**
	 * The company.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	/** The workflow process. */
	@Enumerated
	@Column(name = "workflow_process")
	private ConfigDocProcessEnum workflowProcess;

	/** The process role. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_role_id", nullable = true)
	private ProcessRoles processRole;

	/** The hosting company process. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hosting_company_process_id", nullable = true)
	private HostingCompanyProcess hostingCompanyProcess;

	/** The guid. */
	@Column(name = "guid", length = 100, nullable = true)
	private String guid;

	/** The first in process. */
	@Column(name = "first_in_process")
	private Boolean firstInProcess;

	/** The rag. */
	@Transient
	private RagEnum rag;

	@Transient
	private String css;

	@Transient
	private Integer noDays;

	@Transient
	private Integer noDaysLastAction;

	@Transient
	private Wsp wsp;
	
	@Transient
	private List<TaskUsers> taskUsersList;
	
	/**
	 * Instantiates a new tasks.
	 */
	public Tasks() {
		super();
	}

	/**
	 * Instantiates a new tasks.
	 *
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param targetClass
	 *            the target class
	 * @param taskStatus
	 *            the task status
	 * @param dueDate
	 *            the due date
	 * @param guid
	 *            the guid
	 */
	public Tasks(String description, Users createUser, Long targetKey, String targetClass, TaskStatusEnum taskStatus, Date dueDate, String guid) {
		super();
		this.description = description;
		this.createUser = createUser;
		this.targetKey = targetKey;
		this.taskStatus = taskStatus;
		this.dueDate = dueDate;
		this.guid = guid;
		this.targetClass = targetClass;
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
		Tasks other = (Tasks) obj;
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
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the task description or detail.
	 *
	 * @return the task description or detail
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the short description.
	 *
	 * @return the short description
	 */
	public String getShortDescription() {
		if (description.length() > 50) {
			return description.substring(0, 50);
		}
		return description;
	}

	/**
	 * Sets the task description or detail.
	 *
	 * @param description
	 *            the new task description or detail
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the user created task.
	 *
	 * @return the user created task
	 */
	public Users getCreateUser() {
		return createUser;
	}

	/**
	 * Sets the user created task.
	 *
	 * @param createUser
	 *            the new user created task
	 */
	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}

	/**
	 * Gets the target key for task.
	 *
	 * @return the target key for task
	 */
	public Long getTargetKey() {
		return targetKey;
	}

	/**
	 * Sets the target key for task.
	 *
	 * @param targetKey
	 *            the new target key for task
	 */
	public void setTargetKey(Long targetKey) {
		this.targetKey = targetKey;
	}

	/**
	 * Gets the status of task.
	 *
	 * @return the status of task
	 */
	public TaskStatusEnum getTaskStatus() {
		return taskStatus;
	}

	/**
	 * Sets the status of task.
	 *
	 * @param taskStatus
	 *            the new status of task
	 */
	public void setTaskStatus(TaskStatusEnum taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * Gets the date task created.
	 *
	 * @return the date task created
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the date task created.
	 *
	 * @param createDate
	 *            the new date task created
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the date user started task.
	 *
	 * @return the date user started task
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the date user started task.
	 *
	 * @param startDate
	 *            the new date user started task
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the due date string.
	 *
	 * @return the due date string
	 */
	public String getDueDateString() {
		return GenericUtility.sdf5.format(dueDate);
	}

	/**
	 * Gets the date task due.
	 *
	 * @return the date task due
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * Sets the date task due.
	 *
	 * @param dueDate
	 *            the new date task due
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Gets the date task closed or completed.
	 *
	 * @return the date task closed or completed
	 */
	public Date getCompletionDate() {
		return completionDate;
	}

	/**
	 * Sets the date task closed or completed.
	 *
	 * @param completionDate
	 *            the new date task closed or completed
	 */
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	/**
	 * Gets the page to direct to.
	 *
	 * @return the page to direct to
	 */
	public String getTaskDirectPage() {
		return taskDirectPage;
	}

	/**
	 * Sets the page to direct to.
	 *
	 * @param taskDirectPage
	 *            the new page to direct to
	 */
	public void setTaskDirectPage(String taskDirectPage) {
		this.taskDirectPage = taskDirectPage;
	}

	/**
	 * Gets the target class for task.
	 *
	 * @return the target class for task
	 */
	public String getTargetClass() {
		return targetClass;
	}

	/**
	 * Sets the target class for task.
	 *
	 * @param targetClass
	 *            the new target class for task
	 */
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	/**
	 * Gets the last action user.
	 *
	 * @return the last action user
	 */
	public Users getActionUser() {
		return actionUser;
	}

	/**
	 * Sets the last action user.
	 *
	 * @param actionUser
	 *            the new last action user
	 */
	public void setActionUser(Users actionUser) {
		this.actionUser = actionUser;
	}

	/**
	 * Gets the last action date.
	 *
	 * @return the last action date
	 */
	public Date getActionDate() {
		return actionDate;
	}

	/**
	 * Sets the last action date.
	 *
	 * @param actionDate
	 *            the new last action date
	 */
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
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

	/**
	 * Gets the workflow process.
	 *
	 * @return the workflow process
	 */
	public ConfigDocProcessEnum getWorkflowProcess() {
		return workflowProcess;
	}

	/**
	 * Sets the workflow process.
	 *
	 * @param workflowProcess
	 *            the new workflow process
	 */
	public void setWorkflowProcess(ConfigDocProcessEnum workflowProcess) {
		this.workflowProcess = workflowProcess;
	}

	/**
	 * Gets the guid.
	 *
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Sets the guid.
	 *
	 * @param guid
	 *            the new guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * Gets the process role.
	 *
	 * @return the process role
	 */
	public ProcessRoles getProcessRole() {
		return processRole;
	}

	/**
	 * Sets the process role.
	 *
	 * @param processRole
	 *            the new process role
	 */
	public void setProcessRole(ProcessRoles processRole) {
		this.processRole = processRole;
	}

	/**
	 * Gets the rag.
	 *
	 * @return the rag
	 */
	public RagEnum getRag() {
		return rag;
	}

	/**
	 * Sets the rag.
	 *
	 * @param rag
	 *            the new rag
	 */
	public void setRag(RagEnum rag) {
		this.rag = rag;
	}

	/**
	 * Gets the hosting company process.
	 *
	 * @return the hosting company process
	 */
	public HostingCompanyProcess getHostingCompanyProcess() {
		return hostingCompanyProcess;
	}

	/**
	 * Sets the hosting company process.
	 *
	 * @param hostingCompanyProcess
	 *            the new hosting company process
	 */
	public void setHostingCompanyProcess(HostingCompanyProcess hostingCompanyProcess) {
		this.hostingCompanyProcess = hostingCompanyProcess;
	}

	/**
	 * Gets the first in process.
	 *
	 * @return the first in process
	 */
	public Boolean getFirstInProcess() {
		return firstInProcess;
	}

	/**
	 * Sets the first in process.
	 *
	 * @param firstInProcess
	 *            the new first in process
	 */
	public void setFirstInProcess(Boolean firstInProcess) {
		this.firstInProcess = firstInProcess;
	}

	public Integer getNoDays() {
		if (this.createDate != null && this.getStartDate() != null) {
			try {
				noDays = GenericUtility.getDaysBetweenDates(this.createDate, startDate);
			} catch (Exception e) {
			}
		}
		return noDays;
	}

	public void setNoDays(Integer noDays) {
		this.noDays = noDays;
	}

	public Tasks getPreviousTask() {
		return previousTask;
	}

	public void setPreviousTask(Tasks previousTask) {
		this.previousTask = previousTask;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public Integer getNoDaysLastAction() {
		if (this.createDate != null && this.getActionDate() != null) {
			try {
				noDaysLastAction = GenericUtility.getDaysBetweenDates(this.createDate, actionDate);
			} catch (Exception e) {
			}
		}
		return noDaysLastAction;
	}

	public void setNoDaysLastAction(Integer noDaysLastAction) {
		this.noDaysLastAction = noDaysLastAction;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public List<TaskUsers> getTaskUsersList() {
		return taskUsersList;
	}

	public void setTaskUsersList(List<TaskUsers> taskUsersList) {
		this.taskUsersList = taskUsersList;
	}

}
