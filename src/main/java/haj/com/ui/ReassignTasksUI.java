package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.TaskUsers;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.UsersRole;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TaskUsersService;
import haj.com.service.TasksService;
import haj.com.service.UsersRoleService;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class TasksUI.
 */
@ManagedBean(name = "reassigntasksUI")
@ViewScoped
public class ReassignTasksUI extends AbstractUI {

	/** The tasks list. */
	private List<Tasks> tasksList = null;

	/** The tasksfiltered list. */
	private List<Tasks> tasksfilteredList = null;

	/** The tasks. */
	private Tasks tasks = null;

	/** The data model. */
	private LazyDataModel<Tasks> dataModel;
	private UsersRoleService usersRoleService = new UsersRoleService();
	private List<UsersRole> usersRoles;
	private List<UsersRole> selectedUsersRoles;
	private TaskUsersService taskUsersService = new TaskUsersService();
	private List<TaskUsers> taskUsers;
	private List<TaskUsers> selectedTaskUsers;
	private Users selectedUser = null;
	private ConfigDocProcessEnum configDocProcessEnum;
	private List<TaskUsers> taskUsersAssigned = new ArrayList<>();
	
	private LazyDataModel<Users> userDataModel;
	private UsersService usersService = new UsersService();

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
		userDataModelInfo();
		prepareNew();
		tasksInfo();
	}
	
	public void userDataModelInfo() {
		userDataModel = new LazyDataModel<Users>() {
			private static final long serialVersionUID = 1L;
			private List<Users> retorno = new ArrayList<>();
			@Override
			public List<Users> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = usersService.allUsers(Users.class, first, pageSize, sortField, sortOrder, filters);
					userDataModel.setRowCount(usersService.count(Users.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(Users obj) {
				return obj.getId();
			}
			@Override
			public Users getRowData(String rowKey) {
				for (Users obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void viewUsersTasks(){
		try {
			tasksInfo();
			addInfoMessage("User Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Get all Tasks for data table.
	 *
	 * @author TechFinium
	 * @see Tasks
	 */
	public void tasksInfo() {

		dataModel = new LazyDataModel<Tasks>() {

			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();

			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if (selectedUser == null) {
						retorno = TasksService.instance().allTasksNoSortWithCompleted(Tasks.class, first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(TasksService.instance().countWithCompleted(Tasks.class, filters));
					} else {
						retorno = TasksService.instance().allTasksWhereUserAssigned(first, pageSize, sortField, sortOrder, filters, selectedUser);
						dataModel.setRowCount(TasksService.instance().countAllTasksWhereUserAssigned(Tasks.class, filters));

					}
				} catch (Exception e) {
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
			tasksInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeUserSelect() {
		try {
			taskUsers = taskUsersService.findByTask(tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeUsers() {
		try {
			taskUsersService.deleteBatch(selectedTaskUsers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void taskSelect() {
		try {
			usersRoles = usersRoleService.findByProcess(tasks.getProcessRole(), tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void findTaskUsers() {
		try {
			taskUsersAssigned = taskUsersService.findByTask(tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reassignTask() {
		try {
			TasksService.instance().assignUsersToTask(selectedUsersRoles, tasks, true);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
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
			tasksInfo();
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
			tasksInfo();
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
			tasksInfo();
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

	public List<UsersRole> getSelectedUsersRoles() {
		return selectedUsersRoles;
	}

	public void setSelectedUsersRoles(List<UsersRole> selectedUsersRoles) {
		this.selectedUsersRoles = selectedUsersRoles;
	}

	public List<UsersRole> getUsersRoles() {
		return usersRoles;
	}

	public void setUsersRoles(List<UsersRole> usersRoles) {
		this.usersRoles = usersRoles;
	}

	public List<TaskUsers> getTaskUsers() {
		return taskUsers;
	}

	public void setTaskUsers(List<TaskUsers> taskUsers) {
		this.taskUsers = taskUsers;
	}

	public List<TaskUsers> getSelectedTaskUsers() {
		return selectedTaskUsers;
	}

	public void setSelectedTaskUsers(List<TaskUsers> selectedTaskUsers) {
		this.selectedTaskUsers = selectedTaskUsers;
	}

	public Users getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(Users selectedUser) {
		this.selectedUser = selectedUser;
	}

	public ConfigDocProcessEnum getConfigDocProcessEnum() {
		return configDocProcessEnum;
	}

	public void setConfigDocProcessEnum(ConfigDocProcessEnum configDocProcessEnum) {
		this.configDocProcessEnum = configDocProcessEnum;
	}

	public List<TaskUsers> getTaskUsersAssigned() {
		return taskUsersAssigned;
	}

	public void setTaskUsersAssigned(List<TaskUsers> taskUsersAssigned) {
		this.taskUsersAssigned = taskUsersAssigned;
	}

	public LazyDataModel<Users> getUserDataModel() {
		return userDataModel;
	}

	public void setUserDataModel(LazyDataModel<Users> userDataModel) {
		this.userDataModel = userDataModel;
	}

}
