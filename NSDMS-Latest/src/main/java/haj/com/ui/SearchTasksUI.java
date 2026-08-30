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
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SearchTasksService;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchTasksUI.
 */
@ManagedBean(name = "searchtasksUI")
@ViewScoped
public class SearchTasksUI extends AbstractUI {

	private LazyDataModel<Tasks> dataModel;
	private SearchTasksService service = new SearchTasksService();
	private UsersService userService = new UsersService();
	private ConfigDocProcessEnum configDoc;
	private Tasks tasks = new Tasks();
	private List<Users> userList = new ArrayList<>();
	private List<Tasks> tasksFilteredList = new ArrayList<>();

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
	 * Initialize method to get all SearchTasks and prepare a for a create of a new
	 * SearchTasks.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see SearchTasks
	 */
	private void runInit() throws Exception {
		tasksInfo();
//		findUserByTask();
	}

	public void findUserByTask() {
		try {
			userList = userService.findUserByTask(tasks);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void tasksInfo() {
		dataModel = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();

			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if (configDoc == null) {
						retorno = service.allTasksDesc(first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.countAllTasksDesc(filters));
					} else {
						retorno = service.allTasksDescByConfigDoc(first, pageSize, sortField, sortOrder, filters, configDoc);
						dataModel.setRowCount(service.countAllTasksDescByConfigDoc(filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public LazyDataModel<Tasks> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Tasks> dataModel) {
		this.dataModel = dataModel;
	}

	public ConfigDocProcessEnum getConfigDoc() {
		return configDoc;
	}

	public void setConfigDoc(ConfigDocProcessEnum configDoc) {
		this.configDoc = configDoc;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	public List<Users> getUserList() {
		return userList;
	}

	public void setUserList(List<Users> userList) {
		this.userList = userList;
	}

	public List<Tasks> getTasksFilteredList() {
		return tasksFilteredList;
	}

	public void setTasksFilteredList(List<Tasks> tasksFilteredList) {
		this.tasksFilteredList = tasksFilteredList;
	}

}
