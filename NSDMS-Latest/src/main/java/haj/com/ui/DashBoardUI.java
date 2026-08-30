package haj.com.ui;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Blank;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DashBoardService;

// TODO: Auto-generated Javadoc
/**
 * The Class DashBoardUI.
 */
@ManagedBean(name = "dashBoardUI")
@ViewScoped
public class DashBoardUI extends AbstractUI {

	/** The open tasks. */
	private Long openTasks;

	/** The underway tasks. */
	private Long underwayTasks;

	/** The completed tasks. */
	private Long completedTasks;

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
		this.openTasks = DashBoardService.instance().countTaskForUser(TaskStatusEnum.NotStarted, getSessionUI().getActiveUser());
		this.underwayTasks = DashBoardService.instance().countTaskForUser(TaskStatusEnum.Underway, getSessionUI().getActiveUser());
		this.completedTasks = DashBoardService.instance().completedTaskForThisMonthForUser(getSessionUI().getActiveUser());
	}

	/**
	 * @param divName
	 * @throws Exception
	 */
	public void pageFocusRun(String divName) throws Exception {

		String js = "		setTimeout(function() { " + "			$('html,body').animate({ " + "				scrollTop : $('#" + divName + "').offset().top " + "			}, 1000); " + "		}, 0);";

		super.runClientSideExecute(js);

		divName = null;
	}

	public void populateAllSystemsTasksTable(String typeOfTask) {

		System.out.println("populateAllSystemsTasksTable ran with typeOfTask : " + typeOfTask + " !");
	}

	/**
	 * Gets the open tasks.
	 *
	 * @return the open tasks
	 */
	public Long getOpenTasks() {
		return openTasks;
	}

	/**
	 * Sets the open tasks.
	 *
	 * @param openTasks
	 *            the new open tasks
	 */
	public void setOpenTasks(Long openTasks) {
		this.openTasks = openTasks;
	}

	/**
	 * Gets the completed tasks.
	 *
	 * @return the completed tasks
	 */
	public Long getCompletedTasks() {
		return completedTasks;
	}

	/**
	 * Sets the completed tasks.
	 *
	 * @param completedTasks
	 *            the new completed tasks
	 */
	public void setCompletedTasks(Long completedTasks) {
		this.completedTasks = completedTasks;
	}

	/**
	 * Gets the underway tasks.
	 *
	 * @return the underway tasks
	 */
	public Long getUnderwayTasks() {
		return underwayTasks;
	}

	/**
	 * Sets the underway tasks.
	 *
	 * @param underwayTasks
	 *            the new underway tasks
	 */
	public void setUnderwayTasks(Long underwayTasks) {
		this.underwayTasks = underwayTasks;
	}

}
