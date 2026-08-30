package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Tasks;
import haj.com.framework.AbstractUI;
import haj.com.service.TasksService;

// TODO: Auto-generated Javadoc
/**
 * The Class CountUI.
 */
@ManagedBean(name = "countUI")
@ViewScoped
public class CountUI extends AbstractUI {

	/** The outstanding tasks. */
	public Long outstandingTasks;

	/** The list. */
	private List<Tasks> list;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		// service = new TasksService(getSessionUI().getMap());
		outStandingTasks();
	}

	/**
	 * Out standing tasks.
	 */
	public void outStandingTasks() {
		try {
			list = new ArrayList<>();
//			list = TasksService.instance().findTasksByUserIncomplete(getSessionUI().getActiveUser());
			outstandingTasks = (long) 0;
//			outstandingTasks = (long) list.size();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the outstanding tasks.
	 *
	 * @return the outstanding tasks
	 */
	public Long getOutstandingTasks() {
		return outstandingTasks;
	}

	/**
	 * Sets the outstanding tasks.
	 *
	 * @param outstandingTasks
	 *            the new outstanding tasks
	 */
	public void setOutstandingTasks(Long outstandingTasks) {
		this.outstandingTasks = outstandingTasks;
	}

	/**
	 * Gets the list short.
	 *
	 * @return the list short
	 */
	public List<Tasks> getListShort() {
		if (list != null) return new ArrayList<>();
		if (list.size() > 3) return list.subList(0, 3);
		return getList();
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public List<Tasks> getList() {
		return list;
	}

	/**
	 * Sets the list.
	 *
	 * @param list
	 *            the new list
	 */
	public void setList(List<Tasks> list) {
		this.list = list;
	}

}
