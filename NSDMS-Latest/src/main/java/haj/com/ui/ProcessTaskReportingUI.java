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
import haj.com.constants.HAJConstants;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.Users;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.HostingCompanyProcessService;
import haj.com.service.TasksService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "processTaskReportingUI")
@ViewScoped
public class ProcessTaskReportingUI extends AbstractUI {
	
	/* entity level */
	private Users selectedUser;
	private HostingCompanyEmployees hostingCompanyEmployees;
	private HostingCompanyProcess selectedHostingCompanyProcess = null;
	
	/* service level */
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private HostingCompanyProcessService hostingCompanyProcessService = new HostingCompanyProcessService();
	private TasksService tasksService = new TasksService();
	
	/* lazy data model */
	private LazyDataModel<HostingCompanyProcess> hostingCompanyProcessDataMoel;
	private LazyDataModel<HostingCompanyEmployees> hostingCompanyEmployeesDataModel;
	
	/* Array Lists */
	private List<DescriptionCounterBean> taskCounterReportList = new ArrayList<>();
	private List<DescriptionCounterBean> taskCounterEmployeeReportList = new ArrayList<>();
	
	/* Display Vars */
	private Boolean displaySummaryReport = false;
	private Boolean prepDateRangeReport = false;
	private Boolean displayDateRangeReport = false;
	private Boolean displayEmployeeReport = false;
	
	/* Vars */
	private Date fromDate = null;
	private Date toDate = null;
	private String dateResults = null;
	
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
		hostingCompanyProcessDataMoelInfo();
	}
	
	public void hostingCompanyProcessDataMoelInfo() {
		hostingCompanyProcessDataMoel = new LazyDataModel<HostingCompanyProcess>() {
			private static final long serialVersionUID = 1L;
			private List<HostingCompanyProcess> retorno = new ArrayList<>();
			@Override
			public List<HostingCompanyProcess> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = hostingCompanyProcessService.allHostingCompanyProcess(HostingCompanyProcess.class, first, pageSize, sortField, sortOrder, filters);
					hostingCompanyProcessDataMoel.setRowCount(hostingCompanyProcessService.count(HostingCompanyProcess.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(HostingCompanyProcess obj) {
				return obj.getId();
			}
			@Override
			public HostingCompanyProcess getRowData(String rowKey) {
				for (HostingCompanyProcess obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	private void clearSummaryReport(){
		displaySummaryReport = false;
		displayEmployeeReport = false;
		taskCounterReportList.clear();
		taskCounterEmployeeReportList.clear();
	}
	
	private void clearDateRangeReport(){
		prepDateRangeReport = false;
		displayDateRangeReport = false;
		displayEmployeeReport = false;
		dateResults = null;
		fromDate = null;
		toDate = null;
		taskCounterReportList.clear();
		taskCounterEmployeeReportList.clear();
	}
	
	private void clearAllReportInfo(){
		clearSummaryReport();
		clearDateRangeReport();
	}
	
	public void generateSummaryReport(){
		try {
			clearAllReportInfo();
			taskCounterReportList = tasksService.generateReportSummaryForHostingCompanyProcess(selectedHostingCompanyProcess.getId(), null, false, null, null);
			displaySummaryReport = true;
			hostingCompanyEmployeesInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepDateRangeReporting(){
		try {
			clearAllReportInfo();
			prepDateRangeReport = true;
			fromDate = GenericUtility.deductDaysFromDate(getNow(), 7);
			toDate = getNow();
			displayEmployeeReport = false;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runDateRangeReporting(){
		try {
			dateResults = generateDateResultsToString();
			taskCounterReportList = tasksService.generateReportSummaryForHostingCompanyProcess(selectedHostingCompanyProcess.getId(), null, true, fromDate, toDate);
			displayDateRangeReport = true;
			displayEmployeeReport = false;
			taskCounterEmployeeReportList.clear();
			hostingCompanyEmployeesInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private String generateDateResultsToString(){
		StringBuilder builder = new StringBuilder();
		builder.append(HAJConstants.sdfDDMMYYYY2.format(fromDate));
		builder.append(" - ");
		builder.append(HAJConstants.sdfDDMMYYYY2.format(toDate));
		return builder.toString();
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
	
	public void generateSummaryEmployeeReport(){
		try {
			taskCounterEmployeeReportList = tasksService.generateReportSummaryForHostingCompanyProcess(selectedHostingCompanyProcess.getId(), selectedUser.getId(), false, null, null);
			displayEmployeeReport = true;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runDateRangeEmployeeReporting(){
		try {
			taskCounterEmployeeReportList = tasksService.generateReportSummaryForHostingCompanyProcess(selectedHostingCompanyProcess.getId(), selectedUser.getId(), true, fromDate, toDate);
			displayEmployeeReport = true;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and Setters */
	public HostingCompanyProcess getSelectedHostingCompanyProcess() {
		return selectedHostingCompanyProcess;
	}

	public void setSelectedHostingCompanyProcess(HostingCompanyProcess selectedHostingCompanyProcess) {
		this.selectedHostingCompanyProcess = selectedHostingCompanyProcess;
	}

	public LazyDataModel<HostingCompanyProcess> getHostingCompanyProcessDataMoel() {
		return hostingCompanyProcessDataMoel;
	}

	public void setHostingCompanyProcessDataMoel(LazyDataModel<HostingCompanyProcess> hostingCompanyProcessDataMoel) {
		this.hostingCompanyProcessDataMoel = hostingCompanyProcessDataMoel;
	}

	public Boolean getDisplaySummaryReport() {
		return displaySummaryReport;
	}

	public void setDisplaySummaryReport(Boolean displaySummaryReport) {
		this.displaySummaryReport = displaySummaryReport;
	}

	public Boolean getPrepDateRangeReport() {
		return prepDateRangeReport;
	}

	public void setPrepDateRangeReport(Boolean prepDateRangeReport) {
		this.prepDateRangeReport = prepDateRangeReport;
	}

	public Boolean getDisplayDateRangeReport() {
		return displayDateRangeReport;
	}

	public void setDisplayDateRangeReport(Boolean displayDateRangeReport) {
		this.displayDateRangeReport = displayDateRangeReport;
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

	public String getDateResults() {
		return dateResults;
	}

	public void setDateResults(String dateResults) {
		this.dateResults = dateResults;
	}

	public List<DescriptionCounterBean> getTaskCounterReportList() {
		return taskCounterReportList;
	}

	public void setTaskCounterReportList(List<DescriptionCounterBean> taskCounterReportList) {
		this.taskCounterReportList = taskCounterReportList;
	}

	public LazyDataModel<HostingCompanyEmployees> getHostingCompanyEmployeesDataModel() {
		return hostingCompanyEmployeesDataModel;
	}

	public void setHostingCompanyEmployeesDataModel(
			LazyDataModel<HostingCompanyEmployees> hostingCompanyEmployeesDataModel) {
		this.hostingCompanyEmployeesDataModel = hostingCompanyEmployeesDataModel;
	}

	public List<DescriptionCounterBean> getTaskCounterEmployeeReportList() {
		return taskCounterEmployeeReportList;
	}

	public void setTaskCounterEmployeeReportList(List<DescriptionCounterBean> taskCounterEmployeeReportList) {
		this.taskCounterEmployeeReportList = taskCounterEmployeeReportList;
	}

	public Boolean getDisplayEmployeeReport() {
		return displayEmployeeReport;
	}

	public void setDisplayEmployeeReport(Boolean displayEmployeeReport) {
		this.displayEmployeeReport = displayEmployeeReport;
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
	
	
	
}
