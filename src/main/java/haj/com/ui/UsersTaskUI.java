package haj.com.ui;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.Tasks;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TasksService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersTaskUI.
 */
@ManagedBean(name = "usersTaskUI")
@ViewScoped
public class UsersTaskUI extends AbstractUI {

	/** The tasks. */
	public List<Tasks> tasks;

	/** The tasks history. */
	public List<Tasks> tasksHistory;

	/** The task. */
	private Tasks task;

	/** The company. */
	private Company company;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		taskInfo();
	}

	/**
	 * Task info.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void taskInfo() throws Exception {
		this.tasks = TasksService.instance().findTasksByUserIncomplete(getSessionUI().getActiveUser());
	}

	/**
	 * Findtask history.
	 */
	public void findtaskHistory() {
		try {
			this.tasksHistory = TasksService.instance().findTasksByTypeAndKey(task.getWorkflowProcess(), task.getTargetKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the tasks.
	 *
	 * @return the tasks
	 */
	public List<Tasks> getTasks() {
		return tasks;
	}

	/**
	 * Sets the tasks.
	 *
	 * @param tasks
	 *            the new tasks
	 */
	public void setTasks(List<Tasks> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Gets the task.
	 *
	 * @return the task
	 */
	public Tasks getTask() {
		return task;
	}

	/**
	 * Sets the task.
	 *
	 * @param task
	 *            the new task
	 */
	public void setTask(Tasks task) {
		this.task = task;
	}

	/**
	 * View company info.
	 */
	public void viewCompanyInfo() {
		try {
			super.redirect("/pages/externalparty/companyinfo.jsf?id=" + company.getCompanyGuid());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void interSetaTransfer() {
		try {
			super.redirect("/intersetatransfer.jsf?id=" + company.getCompanyGuid());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Task redirect.
	 */
	public void taskRedirect() {
		try {
			this.task = TasksService.instance().findByKey(task.getId());
			
			if (task.getTaskStatus() == TaskStatusEnum.Underway && !task.getActionUser().equals(getSessionUI().getActiveUser())) throw new Exception("This task is already with someone else.");
			
			if (this.task != null && this.task.getId() != null) {
				if (task.getStartDate() == null) {
					task.setStartDate(new Date());
				}
				if (task.getTaskStatus() == TaskStatusEnum.NotStarted) {
					task.setTaskStatus(TaskStatusEnum.Underway);
				}
				task.setActionDate(new Date());
				task.setActionUser(getSessionUI().getUser());
				TasksService.instance().update(task);
				getSessionUI().setTask(task);
				storeClientInfo();
				super.redirect(task.getWorkflowProcess().getRedirectPage() + "?id=" + task.getGuid());
			} else {
				throw new Exception(getEntryLanguage("unable.to.locate.task.please.contact.support"));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the tasks history.
	 *
	 * @return the tasks history
	 */
	public List<Tasks> getTasksHistory() {
		return tasksHistory;
	}

	/**
	 * Sets the tasks history.
	 *
	 * @param tasksHistory
	 *            the new tasks history
	 */
	public void setTasksHistory(List<Tasks> tasksHistory) {
		this.tasksHistory = tasksHistory;
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

	public void gotoLevyScreen() {
		super.redirect("/pages/externalparty/sarslevydetail.jsf?id=" + company.getCompanyGuid());
	}

}
