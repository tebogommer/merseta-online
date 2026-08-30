package haj.com.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.CoursewareDistibution;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Modules;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CoursewareDistibutionService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class CoursewareDistributionReportUI.
 * 
 * This UI is for the reporting of CoursewareDistribution
 */
@ManagedBean(name = "coursewareDistributionReportUI")
@ViewScoped
public class CoursewareDistributionReportUI extends AbstractUI {
	
	/** Varibles */
	private ApprovalEnum addRemoveStatus = null;
	
	/** Service Levels */
	private CoursewareDistibutionService coursewareDistibutionService = null;
	
	/** Date Range Filters */
	private Date fromDate;
	private Date toDate;
	private Date searchMonth;
	private Integer searchYear;
	
	/** Lazy Load List */
	private LazyDataModel<CoursewareDistibution> dataModelCoursewareDistibution;
	private LazyDataModel<Company> dataModeCoursewareDistByCompany;
	private LazyDataModel<CoursewareDistibution> companyCourseWareDataModel;
	private LazyDataModel<Modules> mostRequestedCoursewareDataModel;
	private LazyDataModel<CoursewareDistibution> coursewareDataModelByMonthAndYear;
	
	/** Array Lists */
	private List<ApprovalEnum> statusForSelection = null; 
	private List<ApprovalEnum> statusSelected = null;
	
	/** Booleans */
	private boolean filterByDateRange = false;
	private boolean filterByStatus = false;
	private boolean displayReport = false;
	private boolean searching=false;
	private Company selectedCompany;

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
		permissionCheck();
		populateDefaultInformation();
		courseWareDistByCompanyInfo();
		mostRequestedCourseware();
	}

	/**
	 * Permissions check
	 */
	private void permissionCheck() throws Exception{
		boolean hasAccess = true;
		if (getSessionUI().isExternalParty()) {
			hasAccess = false;
		}
		if (!hasAccess) {
			super.redirectToDashboard();
		}
	}
	
	/**
	 * Populates additional information for reporting page
	 * @throws Exception
	 */
	private void populateDefaultInformation() throws Exception{
		populateServiceLayers();
		populateDates();
		populateStatusList();
	}

	/**
	 * Populates Service Levels
	 * @throws Exception
	 */
	private void populateServiceLayers() throws Exception{
		if (coursewareDistibutionService == null) {
			coursewareDistibutionService = new CoursewareDistibutionService();
		}
	}
	
	/**
	 * Populates date parameters
	 * @throws Exception
	 */
	private void populateDates() throws Exception{
		fromDate = GenericUtility.getFirstDayOfMonth(new Date());
		toDate = GenericUtility.getStartOfDay(new Date());
	}
	
	/**
	 * Populates list of available status for selection
	 * clear out selected status lists
	 * @throws Exception
	 */
	private void populateStatusList() throws Exception{
		statusForSelection = coursewareDistibutionService.getStatusUsed();
		statusSelected = new ArrayList<>();
//		statusSelectionDualList = new DualListModel<ApprovalEnum>(coursewareDistibutionService.getStatusUsed(), new ArrayList<ApprovalEnum>());
	}
	
	public void hideReportResults(){
		displayReport = false;
	}
	
	public void addStatusForFilter() {
		try {
			displayReport = false;
			boolean newEntry = true;
			if (addRemoveStatus == null) {
				throw new Exception("Unable to assigned status, contact support!");
			}else {
				for (ApprovalEnum status : statusSelected) {
					if (status == addRemoveStatus) {
						newEntry = false;
						break;
					}
				}
				if (newEntry) {
					statusSelected.add(addRemoveStatus);
					addInfoMessage("Status Added");
				}else {
					addWarningMessage("Status Already Selected");
				}
				addRemoveStatus = null;
			}			
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}
	
	public void removeStatusForFilter(){
		try {
			displayReport = false;
			statusSelected.remove(addRemoveStatus);
			addRemoveStatus = null;
			addInfoMessage("Status removed from filter");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Generates the report
	 */
	public void generateCoursewareDistibutionReport(){
		try {
			String error = coursewareDistibutionService.validateParamaters(filterByDateRange,fromDate,toDate,filterByStatus,statusSelected);
			if (error == "") {
				if (filterByDateRange) {
					fromDate = GenericUtility.getStartOfDay(fromDate);
					toDate = GenericUtility.getStartOfDay(toDate);
				}
				reportInfo();
				addInfoMessage("Report Generated");
			} else {
				displayReport = false;
				addErrorMessage("Validation Error Has Accured: " + error);
			}
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Populates the data table with the reporting results
	 * @throws Exception
	 */
	private void reportInfo() throws Exception{
		displayReport = true;
		dataModelCoursewareDistibution = new LazyDataModel<CoursewareDistibution>() {
			private static final long serialVersionUID = 1L;
			private List<CoursewareDistibution> retorno = new ArrayList<CoursewareDistibution>();
			@Override
			public List<CoursewareDistibution> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = coursewareDistibutionService.coursewareDistibutionReportByFilters(first, pageSize, sortField, sortOrder, filters, filterByDateRange, fromDate, toDate, filterByStatus, statusSelected);
					dataModelCoursewareDistibution.setRowCount(coursewareDistibutionService.countCoursewareDistibutionReportByFilters(filters, filterByDateRange, fromDate, toDate, filterByStatus, statusSelected));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CoursewareDistibution obj) {
				return obj.getId();
			}
			@Override
			public CoursewareDistibution getRowData(String rowKey) {
				for (CoursewareDistibution obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void clearSelectCourseware()
	{
		selectedCompany=null;
	}
	
	public void loadCompanyCoursewareDistribution()
	{
		try {
			companyCoursewareDistInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public void searchCoursewareByYearOrMon()
	{
		try {
			
			if(searchMonth==null && searchYear==null)
			{
				throw new ValidationException("Please specify the month or yaer. Both search criteria can be use in combination");
			}
			coursewareInfoByMonthOrYear();
			searching=true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void clearYearAndMon()
	{
		searchMonth=null;
		searchYear=null;
		coursewareDataModelByMonthAndYear=null;
		searching=false;
	}
	
	private void coursewareInfoByMonthOrYear() throws Exception{
		coursewareDataModelByMonthAndYear = new LazyDataModel<CoursewareDistibution>() {
			private static final long serialVersionUID = 1L;
			private List<CoursewareDistibution> retorno = new ArrayList<CoursewareDistibution>();
			@Override
			public List<CoursewareDistibution> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					
					String searchType="";
					if(searchMonth !=null)
					{
						
						Calendar cal = Calendar.getInstance();
						cal.setTime(searchMonth);
						int month = cal.get(Calendar.MONTH)+1;
						filters.put("month", month);
						searchType="MON";
					}
					if(searchYear !=null){filters.put("year", searchYear);searchType="YR";}
					if(searchMonth !=null && searchYear !=null){searchType="MON_YR";}
					retorno = coursewareDistibutionService.coursewareDistibutionbyYearAndMonth(first, pageSize, sortField, sortOrder, filters,searchType);
					
					coursewareDataModelByMonthAndYear.setRowCount(coursewareDistibutionService.countCoursewareDistibutionbyYearAndMonth(filters,searchType));
					
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CoursewareDistibution obj) {
				return obj.getId();
			}
			@Override
			public CoursewareDistibution getRowData(String rowKey) {
				for (CoursewareDistibution obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void companyCoursewareDistInfo() throws Exception{
		displayReport = true;
		companyCourseWareDataModel = new LazyDataModel<CoursewareDistibution>() {
			private static final long serialVersionUID = 1L;
			private List<CoursewareDistibution> retorno = new ArrayList<CoursewareDistibution>();
			@Override
			public List<CoursewareDistibution> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {

					filters.put("companyID", selectedCompany.getId());
					retorno = coursewareDistibutionService.companyCoursewareDistibution(first, pageSize, sortField, sortOrder, filters);
					companyCourseWareDataModel.setRowCount(coursewareDistibutionService.countCompanyCoursewareDistibution(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CoursewareDistibution obj) {
				return obj.getId();
			}
			@Override
			public CoursewareDistibution getRowData(String rowKey) {
				for (CoursewareDistibution obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void mostRequestedCourseware() throws Exception{
		displayReport = true;
		mostRequestedCoursewareDataModel = new LazyDataModel<Modules>() {
			private static final long serialVersionUID = 1L;
			private List<Modules> retorno = new ArrayList<Modules>();
			@Override
			public List<Modules> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = coursewareDistibutionService.mostRequestedCoursewareDistibution(first, pageSize, sortField, sortOrder, filters);
					mostRequestedCoursewareDataModel.setRowCount(coursewareDistibutionService.mostRequestedCoursewareCount(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}
			@Override
			public Object getRowKey(Modules obj) {
				return obj.getId();
			}
			@Override
			public Modules getRowData(String rowKey) {
				for (Modules obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void courseWareDistByCompanyInfo() throws Exception{
		displayReport = true;
		dataModeCoursewareDistByCompany = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = coursewareDistibutionService.coursewareDistibutionReportByCompany(first, pageSize, sortField, sortOrder, filters);
					dataModeCoursewareDistByCompany.setRowCount(coursewareDistibutionService.countCoursewareDistibutionReportByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}
			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}
			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
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

	public LazyDataModel<CoursewareDistibution> getDataModelCoursewareDistibution() {
		return dataModelCoursewareDistibution;
	}

	public void setDataModelCoursewareDistibution(LazyDataModel<CoursewareDistibution> dataModelCoursewareDistibution) {
		this.dataModelCoursewareDistibution = dataModelCoursewareDistibution;
	}

	public boolean isFilterByDateRange() {
		return filterByDateRange;
	}

	public void setFilterByDateRange(boolean filterByDateRange) {
		this.filterByDateRange = filterByDateRange;
	}

	public boolean isFilterByStatus() {
		return filterByStatus;
	}

	public void setFilterByStatus(boolean filterByStatus) {
		this.filterByStatus = filterByStatus;
	}

	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

	public List<ApprovalEnum> getStatusForSelection() {
		return statusForSelection;
	}

	public void setStatusForSelection(List<ApprovalEnum> statusForSelection) {
		this.statusForSelection = statusForSelection;
	}

	public List<ApprovalEnum> getStatusSelected() {
		return statusSelected;
	}

	public void setStatusSelected(List<ApprovalEnum> statusSelected) {
		this.statusSelected = statusSelected;
	}

	public ApprovalEnum getAddRemoveStatus() {
		return addRemoveStatus;
	}

	public void setAddRemoveStatus(ApprovalEnum addRemoveStatus) {
		this.addRemoveStatus = addRemoveStatus;
	}

	public LazyDataModel<Company> getDataModeCoursewareDistByCompany() {
		return dataModeCoursewareDistByCompany;
	}

	public void setDataModeCoursewareDistByCompany(LazyDataModel<Company> dataModeCoursewareDistByCompany) {
		this.dataModeCoursewareDistByCompany = dataModeCoursewareDistByCompany;
	}

	

	public LazyDataModel<CoursewareDistibution> getCompanyCourseWareDataModel() {
		return companyCourseWareDataModel;
	}

	public void setCompanyCourseWareDataModel(LazyDataModel<CoursewareDistibution> companyCourseWareDataModel) {
		this.companyCourseWareDataModel = companyCourseWareDataModel;
	}
	
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public LazyDataModel<Modules> getMostRequestedCoursewareDataModel() {
		return mostRequestedCoursewareDataModel;
	}

	public void setMostRequestedCoursewareDataModel(LazyDataModel<Modules> mostRequestedCoursewareDataModel) {
		this.mostRequestedCoursewareDataModel = mostRequestedCoursewareDataModel;
	}

	public Date getSearchMonth() {
		return searchMonth;
	}

	public void setSearchMonth(Date searchMonth) {
		this.searchMonth = searchMonth;
	}

	public Integer getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(Integer searchYear) {
		this.searchYear = searchYear;
	}

	public CoursewareDistibutionService getCoursewareDistibutionService() {
		return coursewareDistibutionService;
	}

	public void setCoursewareDistibutionService(CoursewareDistibutionService coursewareDistibutionService) {
		this.coursewareDistibutionService = coursewareDistibutionService;
	}

	public LazyDataModel<CoursewareDistibution> getCoursewareDataModelByMonthAndYear() {
		return coursewareDataModelByMonthAndYear;
	}

	public void setCoursewareDataModelByMonthAndYear(
			LazyDataModel<CoursewareDistibution> coursewareDataModelByMonthAndYear) {
		this.coursewareDataModelByMonthAndYear = coursewareDataModelByMonthAndYear;
	}

	public boolean isSearching() {
		return searching;
	}

	public void setSearching(boolean searching) {
		this.searching = searching;
	}



	
//	public DualListModel<ApprovalEnum> getStatusSelectionDualList() {
//		return statusSelectionDualList;
//	}
//
//	public void setStatusSelectionDualList(DualListModel<ApprovalEnum> statusSelectionDualList) {
//		this.statusSelectionDualList = statusSelectionDualList;
//	}

}
