package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.DescriptionCounterBean;
import haj.com.bean.TaskProcessReport;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.TasksService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "employeeTaskReportingUI")
@ViewScoped
public class EmployeeTaskReportingUI extends AbstractUI {
	
	/* Entity */
	private Users selectedUser;
	private HostingCompanyEmployees hostingCompanyEmployees;
	
	/* Service Level */
	private TasksService tasksService = new TasksService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	
	/* Lazy Data Model */
	private LazyDataModel<HostingCompanyEmployees> hostingCompanyEmployeesDataModel;
	private LazyDataModel<Tasks> dataModelTasks;
	private LazyDataModel<Tasks> dataModelAllTasksLinked;
	
	/* Display Booleans */
	private boolean viewAllTasksUserActioned = false;
	private boolean viewAllTasksUserAssigned = false;
	private boolean taskSummaryReport = false;
	private boolean taskSummaryReportDateRange = false;
	private boolean taskSummaryReportDateRangeReport = false;
	
	/* Array Lists */
	private List<DescriptionCounterBean> taskCounterReportList = new ArrayList<>();
	private List<TaskProcessReport> taskProcessReport = new ArrayList<>();
	
	/* Dates */
	private Date fromDate;
	private Date toDate;

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
	
	private void runInit() throws Exception {
		clearBooleanAndReportDisplays();
		hostingCompanyEmployeesInfo();
	}
	
	public void hostingCompanyEmployeesInfo() {
		hostingCompanyEmployeesDataModel = new LazyDataModel<HostingCompanyEmployees>() {
			private static final long serialVersionUID = 1L;
			private List<HostingCompanyEmployees> retorno = new ArrayList<>();
			@Override
			public List<HostingCompanyEmployees> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = hostingCompanyEmployeesService.allHostingCompanyEmployeesIgnoreCertainUsers(first, pageSize, sortField, sortOrder, filters);
					hostingCompanyEmployeesDataModel.setRowCount(hostingCompanyEmployeesService.countAllHostingCompanyEmployeesIgnoreCertainUsers(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(HostingCompanyEmployees obj) {
				return obj.getId();
			}
			@Override
			public HostingCompanyEmployees getRowData(String rowKey) {
				for (HostingCompanyEmployees obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	private void clearBooleanAndReportDisplays() {
		viewAllTasksUserActioned = false;
		viewAllTasksUserAssigned = false;
		taskSummaryReport = false;
		taskSummaryReportDateRange = false;
		taskSummaryReportDateRangeReport = false;
		
		taskCounterReportList = new ArrayList<>();
		taskProcessReport = new ArrayList<>();
		
		fromDate = null;
		toDate = null;
	}

	public void viewAllTasksUserAction(){
		try {
			clearBooleanAndReportDisplays();
			dataModelTasksInfo();
			viewAllTasksUserActioned = true;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void dataModelTasksInfo() {
		dataModelTasks = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();
			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.isEmpty()) {
						sortField = "createDate";
						sortOrder = SortOrder.ASCENDING;
					}
					retorno = tasksService.allTasksByActionUser(first, pageSize, sortField, sortOrder, filters, selectedUser.getId());
					dataModelTasks.setRowCount(tasksService.countAllTasksByActionUser(filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void viewAllTasksUserAssignedTo(){
		try {
			clearBooleanAndReportDisplays();
			dataModelAllTasksLinkedInfo();
			viewAllTasksUserAssigned = true;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void dataModelAllTasksLinkedInfo() {
		dataModelAllTasksLinked = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<>();
			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.isEmpty()) {
						sortField = "createDate";
						sortOrder = SortOrder.ASCENDING;
					}
					retorno = tasksService.allTasksUserLinked(first, pageSize, sortField, sortOrder, filters, selectedUser.getId());
					dataModelAllTasksLinked.setRowCount(tasksService.countAllTasksUserLinked(filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void generateTaskSummaryReport(){
		try {
			clearBooleanAndReportDisplays();
			taskCounterReportList = tasksService.generateReportSummaryForUser(selectedUser.getId(), false, null, null);
			taskProcessReport = tasksService.populateTaskReportByHostingComapnyProcess(selectedUser.getId(), false, null, null);
			taskSummaryReport = true;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepDateRangeReports(){
		try {
			clearBooleanAndReportDisplays();
			taskSummaryReportDateRange = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateReportWithDateRanges(){
		try {
			validateDate();
			taskCounterReportList = tasksService.generateReportSummaryForUser(selectedUser.getId(), true, GenericUtility.getStartOfDay(fromDate), GenericUtility.getEndOfDay(toDate));
			taskProcessReport = tasksService.populateTaskReportByHostingComapnyProcess(selectedUser.getId(), true, GenericUtility.getStartOfDay(fromDate), GenericUtility.getEndOfDay(toDate));
			taskSummaryReportDateRangeReport = true;
			addInfoMessage("Generation Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void validateDate() throws Exception {
		StringBuilder errors = new StringBuilder();
		if (fromDate == null) {
			errors.append("Please provide from date.");
		}
		if (toDate == null) {
			errors.append("Please provide to date.");
		}
		if (fromDate != null && toDate != null) {
			if (fromDate.after(toDate)) {
				errors.append("From date can not be before to date.");
			}
		}
		if (!errors.toString().isEmpty()) {
			throw new Exception(errors.toString());
		}
	}

	/* Getters and Setters */
	public LazyDataModel<HostingCompanyEmployees> getHostingCompanyEmployeesDataModel() {
		return hostingCompanyEmployeesDataModel;
	}

	public void setHostingCompanyEmployeesDataModel(LazyDataModel<HostingCompanyEmployees> hostingCompanyEmployeesDataModel) {
		this.hostingCompanyEmployeesDataModel = hostingCompanyEmployeesDataModel;
	}

	public LazyDataModel<Tasks> getDataModelTasks() {
		return dataModelTasks;
	}

	public void setDataModelTasks(LazyDataModel<Tasks> dataModelTasks) {
		this.dataModelTasks = dataModelTasks;
	}

	public LazyDataModel<Tasks> getDataModelAllTasksLinked() {
		return dataModelAllTasksLinked;
	}

	public void setDataModelAllTasksLinked(LazyDataModel<Tasks> dataModelAllTasksLinked) {
		this.dataModelAllTasksLinked = dataModelAllTasksLinked;
	}

	public Users getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(Users selectedUser) {
		this.selectedUser = selectedUser;
	}

	public HostingCompanyEmployees getHostingCompanyEmployees() {
		return hostingCompanyEmployees;
	}

	public void setHostingCompanyEmployees(HostingCompanyEmployees hostingCompanyEmployees) {
		this.hostingCompanyEmployees = hostingCompanyEmployees;
	}

	public boolean isViewAllTasksUserAssigned() {
		return viewAllTasksUserAssigned;
	}

	public void setViewAllTasksUserAssigned(boolean viewAllTasksUserAssigned) {
		this.viewAllTasksUserAssigned = viewAllTasksUserAssigned;
	}

	public boolean isViewAllTasksUserActioned() {
		return viewAllTasksUserActioned;
	}

	public void setViewAllTasksUserActioned(boolean viewAllTasksUserActioned) {
		this.viewAllTasksUserActioned = viewAllTasksUserActioned;
	}

	public boolean isTaskSummaryReport() {
		return taskSummaryReport;
	}

	public void setTaskSummaryReport(boolean taskSummaryReport) {
		this.taskSummaryReport = taskSummaryReport;
	}

	public boolean isTaskSummaryReportDateRange() {
		return taskSummaryReportDateRange;
	}

	public void setTaskSummaryReportDateRange(boolean taskSummaryReportDateRange) {
		this.taskSummaryReportDateRange = taskSummaryReportDateRange;
	}

	public List<DescriptionCounterBean> getTaskCounterReportList() {
		return taskCounterReportList;
	}

	public void setTaskCounterReportList(List<DescriptionCounterBean> taskCounterReportList) {
		this.taskCounterReportList = taskCounterReportList;
	}

	public List<TaskProcessReport> getTaskProcessReport() {
		return taskProcessReport;
	}

	public void setTaskProcessReport(List<TaskProcessReport> taskProcessReport) {
		this.taskProcessReport = taskProcessReport;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public boolean isTaskSummaryReportDateRangeReport() {
		return taskSummaryReportDateRangeReport;
	}

	public void setTaskSummaryReportDateRangeReport(boolean taskSummaryReportDateRangeReport) {
		this.taskSummaryReportDateRangeReport = taskSummaryReportDateRangeReport;
	}

}
