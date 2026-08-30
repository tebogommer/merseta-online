package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Tasks;
import haj.com.entity.enums.TaskStatusAndTypeEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TasksService;

// TODO: Auto-generated Javadoc
/**
 * The Class TasksUI.
 */
@ManagedBean(name = "tasksUI")
@ViewScoped
public class TasksUI extends AbstractUI {

	/** The tasks list. */
	private List<Tasks> tasksList = null;

	/** The tasksfiltered list. */
	private List<Tasks> tasksfilteredList = null;

	/** The tasks. */
	private Tasks tasks = null;

	/** The data model. */
	private LazyDataModel<Tasks> dataModel;

	private String typeOfTask = "";

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
	 * Initialize method to get all Tasks and prepare a for a create of a new Tasks.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Tasks
	 */
	private void runInit() throws Exception {

		prepareNew();

		// tasksInfo("");
	}

	/**
	 * @param typeOfTask
	 */
	public void populateAllSystemsTasksTable(String typeOfTask) {

		logger.info("populateAllSystemsTasksTable called with type as : " + typeOfTask);

		tasksInfo(typeOfTask);
	}

	/**
	 * Get all Tasks for data table.
	 *
	 * @author TechFinium
	 * @see Tasks
	 * 
	 * @param typeOfTask
	 */
	public void tasksInfo(String typeOfTask) {

		if (typeOfTask == null) {

			this.setTypeOfTask("");

		} else {

			this.setTypeOfTask(typeOfTask);
		}

		dataModel = new LazyDataModel<Tasks>() {

			private static final long serialVersionUID = 1L;

			private List<Tasks> retorno = new ArrayList<Tasks>();
			private List<Tasks> combined = new ArrayList<Tasks>();
			private List<Tasks> tasksAllOfTypeForEmployee = new ArrayList<Tasks>();
			private List<Tasks> tasksAllOfTypeForExternal = new ArrayList<Tasks>();

			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = new ArrayList<>();
					if (typeOfTask.equals(TaskStatusAndTypeEnum.EXTERNAL_OPEN_TASKS.getRegistrationName())) {

						retorno = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, false, TaskStatusEnum.NotStarted);

						dataModel.setRowCount(TasksService.instance().countByType(Tasks.class, filters, false, TaskStatusEnum.NotStarted));

					} else if (typeOfTask.equals(TaskStatusAndTypeEnum.EXTERNAL_UNDERWAY_TASKS.getRegistrationName())) {

						retorno = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, false, TaskStatusEnum.Underway);

						dataModel.setRowCount(TasksService.instance().countByType(Tasks.class, filters, false, TaskStatusEnum.Underway));

					} else if (typeOfTask.equals(TaskStatusAndTypeEnum.EXTERNAL_COMPLETED_TASKS.getRegistrationName())) {

						retorno = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, false, TaskStatusEnum.Completed);

						dataModel.setRowCount(TasksService.instance().countByType(Tasks.class, filters, false, TaskStatusEnum.Completed));

					} else if (typeOfTask.equals(TaskStatusAndTypeEnum.EMPLOYEES_OPEN_TASKS.getRegistrationName())) {

						retorno = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, true, TaskStatusEnum.NotStarted);

						dataModel.setRowCount(TasksService.instance().countByType(Tasks.class, filters, true, TaskStatusEnum.NotStarted));

					} else if (typeOfTask.equals(TaskStatusAndTypeEnum.EMPLOYEES_UNDERWAY_TASKS.getRegistrationName())) {

						retorno = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, true, TaskStatusEnum.Underway);

						dataModel.setRowCount(TasksService.instance().countByType(Tasks.class, filters, true, TaskStatusEnum.Underway));

					} else if (typeOfTask.equals(TaskStatusAndTypeEnum.EMPLOYEES_COMPLETED_TASKS.getRegistrationName())) {

						retorno = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, true, TaskStatusEnum.Completed);

						dataModel.setRowCount(TasksService.instance().countByType(Tasks.class, filters, true, TaskStatusEnum.Completed));

					} else if (typeOfTask.equals(TaskStatusAndTypeEnum.ALL_OPEN_TASKS.getRegistrationName())) {

						tasksAllOfTypeForEmployee = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, true, TaskStatusEnum.NotStarted);
						tasksAllOfTypeForExternal = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, false, TaskStatusEnum.NotStarted);

						retorno.addAll(tasksAllOfTypeForEmployee);
						retorno.addAll(tasksAllOfTypeForExternal);

						dataModel.setRowCount(TasksService.instance().countByType(Tasks.class, filters, null, TaskStatusEnum.NotStarted));

//						return combined;

					} else if (typeOfTask.equals(TaskStatusAndTypeEnum.ALL_UNDERWAY_TASKS.getRegistrationName())) {

						tasksAllOfTypeForEmployee = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, true, TaskStatusEnum.Underway);
						tasksAllOfTypeForExternal = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, false, TaskStatusEnum.Underway);

						retorno.addAll(tasksAllOfTypeForEmployee);
						retorno.addAll(tasksAllOfTypeForExternal);

						dataModel.setRowCount(TasksService.instance().countByType(Tasks.class, filters, null, TaskStatusEnum.Underway));

//						return combined;

					} else if (typeOfTask.equals(TaskStatusAndTypeEnum.ALL_COMPLETED_TASKS.getRegistrationName())) {

						tasksAllOfTypeForEmployee = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, true, TaskStatusEnum.Completed);
						tasksAllOfTypeForExternal = TasksService.instance().allTasksByType(Tasks.class, first, pageSize, sortField, sortOrder, filters, false, TaskStatusEnum.Completed);

						retorno.addAll(tasksAllOfTypeForEmployee);
						retorno.addAll(tasksAllOfTypeForExternal);

						dataModel.setRowCount(TasksService.instance().countByType(Tasks.class, filters, null, TaskStatusEnum.Completed));

//						return combined;

					} else {

						retorno = TasksService.instance().allTasks(Tasks.class, first, pageSize, sortField, sortOrder, filters);

						dataModel.setRowCount(TasksService.instance().countNotCompleted(Tasks.class, filters));
					}

				} catch (Exception e) {

					e.printStackTrace();

					logger.fatal(e);
				}

				return retorno;
			}

			@Override
			public Object getRowKey(Tasks obj) {
				return obj.getId();
			}

			@Override
			public Tasks getRowData(String rowKey) {
				for (Tasks obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void resetTask() {
		try {
			this.tasks.setActionUser(null);
			this.tasks.setActionDate(null);
			this.tasks.setStartDate(null);
			this.tasks.setTaskStatus(TaskStatusEnum.NotStarted);
			TasksService.instance().update(tasks);
			tasksInfo("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert Tasks into database.
	 *
	 * @author TechFinium
	 * @see Tasks
	 */
	public void tasksInsert() {
		try {
			TasksService.instance().create(this.tasks);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			tasksInfo("");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Tasks in database.
	 *
	 * @author TechFinium
	 * @see Tasks
	 */
	public void tasksUpdate() {
		try {
			TasksService.instance().update(this.tasks);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			tasksInfo("");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Tasks from database.
	 *
	 * @author TechFinium
	 * @see Tasks
	 */
	public void tasksDelete() {
		try {
			TasksService.instance().delete(this.tasks);
			prepareNew();
			tasksInfo("");
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Tasks .
	 *
	 * @author TechFinium
	 * @see Tasks
	 */
	public void prepareNew() {
		tasks = new Tasks();
	}

	/*
	 * public List<SelectItem> getTasksDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * tasksInfo(); for (Tasks ug : tasksList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Tasks> complete(String desc) {
		List<Tasks> l = null;
		try {
			l = TasksService.instance().findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the tasks list.
	 *
	 * @return the tasks list
	 */
	public List<Tasks> getTasksList() {
		return tasksList;
	}

	/**
	 * Sets the tasks list.
	 *
	 * @param tasksList
	 *            the new tasks list
	 */
	public void setTasksList(List<Tasks> tasksList) {
		this.tasksList = tasksList;
	}

	/**
	 * Gets the tasks.
	 *
	 * @return the tasks
	 */
	public Tasks getTasks() {
		return tasks;
	}

	/**
	 * Sets the tasks.
	 *
	 * @param tasks
	 *            the new tasks
	 */
	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	/**
	 * Gets the tasksfiltered list.
	 *
	 * @return the tasksfiltered list
	 */
	public List<Tasks> getTasksfilteredList() {
		return tasksfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param tasksfilteredList
	 *            the new tasksfilteredList list
	 * @see Tasks
	 */
	public void setTasksfilteredList(List<Tasks> tasksfilteredList) {
		this.tasksfilteredList = tasksfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Tasks> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<Tasks> dataModel) {
		this.dataModel = dataModel;
	}

	public String getTypeOfTask() {
		return typeOfTask;
	}

	public void setTypeOfTask(String typeOfTask) {
		this.typeOfTask = typeOfTask;
	}

}
