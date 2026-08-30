package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jfree.data.gantt.TaskSeries;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.StatusReportBean;
import haj.com.bean.TaskUserReportBean;
import haj.com.bean.WSPReport;
import haj.com.entity.Company;
import haj.com.entity.SDFCompany;
import haj.com.entity.Wsp;
import haj.com.entity.enums.WSPSearchType;
import haj.com.entity.lookup.DGYear;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DashBoardService;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.service.lookup.DGYearService;
import haj.com.utils.GenericUtility;

/**
 * The Class WspReportingUI. This UI is used for the creating and displaying or
 * reports regarding the wsp
 */
@ManagedBean(name = "wspReportingUI")
@ViewScoped
public class WspReportingUI extends AbstractUI {

	/** Service Level */
	private WspService wspService = null;
	private TasksService tasksService = null;

	/** Date Range Filters */
	private Date fromDate;
	private Date toDate;

	/** Array Lists */
	private List<Integer> financialYears;

	private Integer selectedYear;
	private Integer fromYear;
	private Integer toYear;

	/** Data Model Lists */
	private LazyDataModel<Wsp> dataModelWspByFinancialYear;

	/** Data Model Lists */
	private LazyDataModel<Wsp> financialYearDataModel;

	/** Data Model Lists */
	private LazyDataModel<Wsp> financialYearRangeDataModel;

	/** The Company */
	private Company company;
	/** Company WSP Lists */
	private List<Wsp> companyWSPList;

	/** Report Bean Lists */
	private List<TaskUserReportBean> tasksReportBean;
	private List<StatusReportBean> statusReportBean;
	private List<WSPReport> wspReports;

	/** Vars */
	private int index; // used for changing tabs

	/** Display Booleans */
	private boolean filterByDateRange = false;
	private boolean filterByFinYear = false;
	private boolean displayReport = false;

	private int startYear = 2018;

	private WSPSearchType searchType;

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
	 * runInit()
	 * 
	 * @throws Exception
	 */
	private void runInit() throws Exception {
		permissionCheck();
		populateDefaultInformation();
		companyWSPList = new ArrayList<>();
	}

	public void prepareCompanyWSPList() {
		try {

			companyWSPList.clear();
			int currentYear = GenericUtility.yearOfDate(new Date());
			for (int x = startYear; x <= currentYear; x++) {
				ArrayList<Wsp> wsps = (ArrayList<Wsp>) wspService.findByCompanyAndFinancialYear(company.getId(), x + 1);
				if (wsps != null && wsps.size() > 0) {
					companyWSPList.addAll(wsps);
				} else {
					Wsp wsp = new Wsp();
					// wsp.setFinYear(x+1);
					companyWSPList.add(wsp);
				}
			}

			if (companyWSPList.size() <= 0) {
				throw new Exception("No WSP information found");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public void clearWSPSearchHistory() {
		companyWSPList.clear();
		company = null;
		selectedYear = null;
		fromYear = null;
		toYear = null;
		searchType = null;
	}

	/**
	 * Check to ensure only users with correct permissions can access the page
	 */
	private void permissionCheck() {
		boolean hasAccess = true;
		// check if super admin
		if (!getSessionUI().getUser().getSuperAdmin()) {
			// check if user has access to page
			if (!getSessionUI().getUser().getTempReporting()) {
				hasAccess = false;
			}
		}
		if (!hasAccess) {
			super.redirectToDashboard();
		}
	}

	/**
	 * Populates the default paramters for selection
	 * 
	 * @throws Exception
	 */
	private void populateDefaultInformation() throws Exception {
		populateServiceLayers();
		populateDates();
		populatesDistinctFinancialYears();
	}

	/**
	 * Populates service levels
	 */
	private void populateServiceLayers() {
		if (wspService == null) {
			wspService = new WspService();
		}
		if (tasksService == null) {
			tasksService = new TasksService();
		}
	}

	/**
	 * Available financial Years for wsp
	 * 
	 * @throws Exception
	 */
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (financialYears.size() != 0) {
			selectedYear = financialYears.get(0);
		}
	}

	/**
	 * Populates the filter by dates for specific reports
	 */
	public void populateDates() throws Exception {
		fromDate = GenericUtility.getFirstDayOfMonth(new Date());
		toDate = GenericUtility.getStartOfDay(new Date());
	}

	/**
	 * On Tab Change will populate the required information for reports to
	 * download
	 */
	public void onTabChange(TabChangeEvent event) {
		try {
			// should be set to false when switching between tabs
			displayReport = false;
			clearArrayLists();
			// switch (index) {
			switch (event.getTab().getId()) {
			case "wspByFinYearTab":
				prepReportWspByFinancialYearAndStatus();
				// super.runClientSideUpdate(":mainForm:tabView:wspByFinYearTab");
				break;
			case "tasksByWsp":
				prepTasksReport();
				// super.runClientSideUpdate(":mainForm:tabView:tasksByWsp");
				break;
			case "statusCountWsp":
				prepStatusReport();
				// super.runClientSideUpdate(":mainForm:tabView:statusCountWsp");
				break;
			case "targetReportDG":
				prepGrantsReportByTypeStatusCompanySize();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Clears array Lists and reporting beans used
	 */
	private void clearArrayLists() throws Exception {
		tasksReportBean = null;
		statusReportBean = null;
	}

	/**
	 * populates default information for report: Wsp Report by Financial Year
	 * and status
	 * 
	 * @throws Exception
	 */
	public void prepReportWspByFinancialYearAndStatus() throws Exception {
		populatesDistinctFinancialYears();
	}

	/**
	 * Populates the report for download: Wsp Report by Financial Year and
	 * status
	 */
	public void populateWspByFinancialYearAndStatus() {
		try {
			displayReport = true;
			if (selectedYear == null) {
				throw new Exception("Select Financial Year Before Proceeding");
			}
			wspByFinYearAndStatusInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Runs lazy load of data for report: wsp Report by Financial Year and
	 * status
	 */
	private void wspByFinYearAndStatusInfo() {
		dataModelWspByFinancialYear = new LazyDataModel<Wsp>() {
			private static final long serialVersionUID = 1L;
			private List<Wsp> retorno = new ArrayList<Wsp>();

			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {

					retorno = wspService.wspReportByFinancialYear(first, pageSize, sortField, sortOrder, filters,
							selectedYear, null);
					retorno.forEach(mg -> {
						try {
							wspService.populateInformationForReport(mg);
							// mg.setGrantRecommendations(grantRecommendationService.findByMG(mg));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});

					dataModelWspByFinancialYear
							.setRowCount(wspService.countWspReportByFinancialYear(filters, selectedYear, null));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Wsp obj) {
				return obj.getId();
			}

			@Override
			public Wsp getRowData(String rowKey) {
				for (Wsp obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void searchWspByFinYearInfo() {
		wspByFinYearInfo();
	}

	public void searchWspByFinYearRangeInfo() {
		wspByFinYearRangeInfo();
	}

	/**
	 * Runs lazy load of data for report: wsp Report by Financial Year
	 */
	private void wspByFinYearInfo() {
		financialYearDataModel = new LazyDataModel<Wsp>() {
			private static final long serialVersionUID = 1L;
			private List<Wsp> retorno = new ArrayList<Wsp>();

			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {

					filters.put("year", selectedYear);
					retorno = wspService.wspByFinancialYear(first, pageSize, sortField, sortOrder, filters);
					retorno.forEach(mg -> {
						try {
							wspService.populateInformationForReport(mg);
							// mg.setGrantRecommendations(grantRecommendationService.findByMG(mg));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});

					financialYearDataModel.setRowCount(wspService.countWspByFinancialYear(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Wsp obj) {
				return obj.getId();
			}

			@Override
			public Wsp getRowData(String rowKey) {
				for (Wsp obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Runs lazy load of data for report: wsp Report by Financial Year
	 */
	private void wspByFinYearRangeInfo() {
		financialYearRangeDataModel = new LazyDataModel<Wsp>() {
			private static final long serialVersionUID = 1L;
			private List<Wsp> retorno = new ArrayList<Wsp>();

			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {

					filters.put("fromYear", fromYear);
					filters.put("toYear", toYear);
					retorno = wspService.wspByFinancialYearRange(first, pageSize, sortField, sortOrder, filters);
					retorno.forEach(mg -> {
						try {
							wspService.populateInformationForReport(mg);
							// mg.setGrantRecommendations(grantRecommendationService.findByMG(mg));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});

					financialYearRangeDataModel.setRowCount(wspService.countWspByFinancialYearRange(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Wsp obj) {
				return obj.getId();
			}

			@Override
			public Wsp getRowData(String rowKey) {
				for (Wsp obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	private void prepTasksReport() throws Exception {
		tasksReportBean = new ArrayList<>();
	}

	public void generateTasksReport() {
		try {
			if (selectedYear == null) {
				throw new Exception("Select A Year Before Proceeding");
			}
			tasksReportBean = tasksService.locateTaskSummaryEmployeesByTargetClassWsp(Wsp.class.getName(),
					selectedYear);
			displayReport = true;
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void prepStatusReport() throws Exception {
		statusReportBean = new ArrayList<>();
	}

	public void generateStatusReport() {
		try {
			if (selectedYear == null) {
				throw new Exception("Select A Fin Year");
			}
			statusReportBean = wspService.populateStatusReportWspByYear(selectedYear);
			displayReport = true;
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void prepGrantsReportByTypeStatusCompanySize() throws Exception{
		wspReports = new ArrayList<>();
	}
	
	/**
	 * Populates the report for download: Monitoring DG Targets
	 */
	public void populateGrantsReportByTypeStatusCompanySize() {
		try {
			displayReport = true;
			if (selectedYear == null) {
				throw new Exception("Select A Year");
			}
			wspReports = DashBoardService.instance().generateReportGrantStatusReportByCompanySizes(selectedYear);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	/** Getters and setters */
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

	public List<Integer> getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(List<Integer> financialYears) {
		this.financialYears = financialYears;
	}

	public boolean isFilterByDateRange() {
		return filterByDateRange;
	}

	public void setFilterByDateRange(boolean filterByDateRange) {
		this.filterByDateRange = filterByDateRange;
	}

	public boolean isFilterByFinYear() {
		return filterByFinYear;
	}

	public void setFilterByFinYear(boolean filterByFinYear) {
		this.filterByFinYear = filterByFinYear;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public LazyDataModel<Wsp> getDataModelWspByFinancialYear() {
		return dataModelWspByFinancialYear;
	}

	public void setDataModelWspByFinancialYear(LazyDataModel<Wsp> dataModelWspByFinancialYear) {
		this.dataModelWspByFinancialYear = dataModelWspByFinancialYear;
	}

	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

	public List<TaskUserReportBean> getTasksReportBean() {
		return tasksReportBean;
	}

	public void setTasksReportBean(List<TaskUserReportBean> tasksReportBean) {
		this.tasksReportBean = tasksReportBean;
	}

	public List<StatusReportBean> getStatusReportBean() {
		return statusReportBean;
	}

	public void setStatusReportBean(List<StatusReportBean> statusReportBean) {
		this.statusReportBean = statusReportBean;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Wsp> getCompanyWSPList() {
		return companyWSPList;
	}

	public void setCompanyWSPList(List<Wsp> companyWSPList) {
		this.companyWSPList = companyWSPList;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public LazyDataModel<Wsp> getFinancialYearDataModel() {
		return financialYearDataModel;
	}

	public void setFinancialYearDataModel(LazyDataModel<Wsp> financialYearDataModel) {
		this.financialYearDataModel = financialYearDataModel;
	}

	public WSPSearchType getSearchType() {
		return searchType;
	}

	public void setSearchType(WSPSearchType searchType) {
		this.searchType = searchType;
	}

	public LazyDataModel<Wsp> getFinancialYearRangeDataModel() {
		return financialYearRangeDataModel;
	}

	public void setFinancialYearRangeDataModel(LazyDataModel<Wsp> financialYearRangeDataModel) {
		this.financialYearRangeDataModel = financialYearRangeDataModel;
	}

	public Integer getFromYear() {
		return fromYear;
	}

	public void setFromYear(Integer fromYear) {
		this.fromYear = fromYear;
	}

	public Integer getToYear() {
		return toYear;
	}

	public void setToYear(Integer toYear) {
		this.toYear = toYear;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public List<WSPReport> getWspReports() {
		return wspReports;
	}

	public void setWspReports(List<WSPReport> wspReports) {
		this.wspReports = wspReports;
	}

}
