package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.DgVerificationReportBean;
import haj.com.bean.StatusReportBean;
import haj.com.bean.TaskUserReportBean;
import haj.com.bean.WSPReport;
import haj.com.entity.DgVerification;
import haj.com.entity.MandatoryGrant;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DashBoardService;
import haj.com.service.DgVerificationService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.MandatoryGrantRecommendationService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.utils.GenericUtility;

/**
 * The Class DgReportingUI. This UI is used for the creating and displaying or reports regarding the DG verification
 */
@ManagedBean(name = "dgReportingUI")
@ViewScoped
public class DgReportingUI extends AbstractUI {

	/** Service Level */
	private DgVerificationService dgVerificationService = null;
	private MandatoryGrantService mandatoryGrantService = null;
	private MandatoryGrantDetailService mandatoryGrantDetailService = null;
	private MandatoryGrantRecommendationService grantRecommendationService = null;
	private TasksService tasksService = null;
	private WspService wspService = null;

	/** Date Range Filters */
	private Date fromDate;
	private Date toDate;

	/** Array Lists */
	private List<Integer> financialYears;
	private Integer selectedYear;
	private List<WSPReport> wspReports;
	private List<DgVerificationReportBean> dgVerificationReportList;

	/** Data Model Lists */
	private LazyDataModel<DgVerification> dataModelDgByFinancialYear;
	private LazyDataModel<MandatoryGrant> dataModelMandatoryGrant;
	
	/** Report Bean Lists */
	private List<TaskUserReportBean> tasksReportBean;
	private List<StatusReportBean> statusReportBean;

	/** Vars */
	private int index; // used for changing tabs

	/** Display Booleans */
	private boolean filterByDateRange = false;
	private boolean filterByFinYear = false;
	private boolean displayReport = false;

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
	 * Populates 
	 */
	private void populateServiceLayers() {
		if (dgVerificationService == null) {
			dgVerificationService = new DgVerificationService();
		}
		if (tasksService == null) {
			tasksService = new TasksService();
		}
		if (mandatoryGrantDetailService == null) {
			mandatoryGrantDetailService = new MandatoryGrantDetailService();
		}
		if (mandatoryGrantService == null) {
			mandatoryGrantService = new MandatoryGrantService();
		}
		if (grantRecommendationService == null) {
			grantRecommendationService = new MandatoryGrantRecommendationService();
		}
		if (wspService == null) {
			wspService = new WspService();
		}
	}

	/**
	 * Available financial Years for DG Verifications
	 * 
	 * Temp added 2019, will need to derive from dg in future
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
			displayReport = false;
			clearArrayLists();
//			switch (index) {
			switch (event.getTab().getId()) {
			case "dgByFinYearTab":
				prepReportDgByFinancialYearAndStatus();
				break;
			case "tasksByEntity":
				prepTasksReport();
				break;
			case "detailDgInfo":
				prepDgDetailReport();
				break;
			case "statusCountDg":
				prepStatusReport();
				break;
			case "targetReportDG":
				prepDgReportByTypeStatusCompanySize();
				break;
			case "dgVerificationReportTab":
				prepDgVerificationReportList();
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
	private void clearArrayLists() throws Exception{
		tasksReportBean = null;
		statusReportBean = null;
		dgVerificationReportList = null;
	}

	/**
	 * populates default information for report: Dg Report by Financial Year and status
	 * 
	 * @throws Exception
	 */
	public void prepReportDgByFinancialYearAndStatus() throws Exception {
		populatesDistinctFinancialYears();
	}

	/**
	 * Populates the report for download: Dg Report by Financial Year and status
	 */
	public void populateDgByFinancialYearAndStatus() {
		try {
			displayReport = true;
			if (selectedYear == null) {
				throw new Exception("Select A Year");
			}
			dgByFinYearAndStatusInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Runs lazy load of data for report: Dg Report by Financial Year and status
	 */
	private void dgByFinYearAndStatusInfo() {
		//dataModelMandatoryGrant
		dataModelDgByFinancialYear = new LazyDataModel<DgVerification>() {
			private static final long serialVersionUID = 1L;
			private List<DgVerification> retorno = new ArrayList<DgVerification>();
			@Override
			public List<DgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = dgVerificationService.allDgVerificationByFinYear(first, pageSize, sortField, sortOrder, filters, selectedYear);
					dataModelDgByFinancialYear.setRowCount(dgVerificationService.countallDgVerificationByFinYear(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(DgVerification obj) {
				return obj.getId();
			}
			@Override
			public DgVerification getRowData(String rowKey) {
				for (DgVerification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	/**
	 * Preps report: Tasks For DG report
	 * @throws Exception
	 */
	private void prepTasksReport() throws Exception{
		tasksReportBean = new ArrayList<>();
	}
	
	/**
	 * Generates the report:  Tasks For DG report
	 */
	public void generateTasksReport(){
		try {
			if (selectedYear == null) {
				throw new Exception("Select a year");
			}
			tasksReportBean = tasksService.locateTaskSummaryEmployeesByTargetClassDG(DgVerification.class.getName(), selectedYear);
			displayReport = true;
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Preps report: DG verification Detail Report
	 */
	private void prepDgDetailReport() {
		
	}
	
	/**
	 * Generates the report: DG verification detail report
	 */
	public void generateDgVerificationDetailReport(){
		try {
			if (selectedYear == null) {
				throw new Exception("Select a year");
			}
			mandatoryGrantDetailFinYearAndStatusInfo();
			displayReport = true;
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Runs lazy load of data for report: DG verification detail
	 */
	private void mandatoryGrantDetailFinYearAndStatusInfo() {
		dataModelMandatoryGrant = new LazyDataModel<MandatoryGrant>() {
			private static final long serialVersionUID = 1L;
			private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();
			@Override
			public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {

					retorno = mandatoryGrantService.allMandatoryGrantByFinYear(first, pageSize, sortField, sortOrder, filters, selectedYear);
					retorno.forEach(mg -> {
						try {
							if (DgReportingUI.this.grantRecommendationService.findByMG(mg) != null) {
								mg.setGrantRecommendations(grantRecommendationService.findByMG(mg));
							}
							mandatoryGrantService.populateAdditionalInformationForReporting(mg);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					dataModelMandatoryGrant.setRowCount(mandatoryGrantService.countAllMandatoryGrantByFinYear(filters));
//					retorno = mandatoryGrantService.allMandatoryGrantWhereDgApproved(first, pageSize, sortField, sortOrder, filters);
//					dataModelMandatoryGrant.setRowCount(mandatoryGrantService.countAllMandatoryGrantWhereDgApproved(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(MandatoryGrant obj) {
				return obj.getId();
			}
			@Override
			public MandatoryGrant getRowData(String rowKey) {
				for (MandatoryGrant obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void prepStatusReport() throws Exception{
		statusReportBean = new ArrayList<>();
	}
	
	public void generateStatusReport(){
		try {
			if (selectedYear == null) {
				throw new Exception("Select a year");
			}
			statusReportBean = dgVerificationService.populateStatusReportDg(selectedYear);
			displayReport = true;
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void prepDgVerificationReportList() throws Exception{
		dgVerificationReportList = new ArrayList<>();
	}
	
	/**
	 * Populates the report for download: Monitoring DG Targets
	 */
	public void populateDgVerificationReportList() {
		try {
			displayReport = true;
			if (selectedYear == null) {
				throw new Exception("Select A Year");
			}
			if (dgVerificationService == null) {
				dgVerificationService = new DgVerificationService();
			}
			dgVerificationReportList = dgVerificationService.getAllVerificationsByFinYear(selectedYear);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void prepDgReportByTypeStatusCompanySize() throws Exception{
		wspReports = new ArrayList<>();
	}
	
	/**
	 * Populates the report for download: Monitoring DG Targets
	 */
	public void populateDgReportByTypeStatusCompanySize() {
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

	public LazyDataModel<DgVerification> getDataModelDgByFinancialYear() {
		return dataModelDgByFinancialYear;
	}

	public void setDataModelDgByFinancialYear(LazyDataModel<DgVerification> dataModelDgByFinancialYear) {
		this.dataModelDgByFinancialYear = dataModelDgByFinancialYear;
	}

	public LazyDataModel<MandatoryGrant> getDataModelMandatoryGrant() {
		return dataModelMandatoryGrant;
	}

	public void setDataModelMandatoryGrant(LazyDataModel<MandatoryGrant> dataModelMandatoryGrant) {
		this.dataModelMandatoryGrant = dataModelMandatoryGrant;
	}

	public List<StatusReportBean> getStatusReportBean() {
		return statusReportBean;
	}

	public void setStatusReportBean(List<StatusReportBean> statusReportBean) {
		this.statusReportBean = statusReportBean;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public List<WSPReport> getWspReports() {
		return wspReports;
	}

	public void setWspReports(List<WSPReport> wspReports) {
		this.wspReports = wspReports;
	}

	public List<DgVerificationReportBean> getDgVerificationReportList() {
		return dgVerificationReportList;
	}

	public void setDgVerificationReportList(List<DgVerificationReportBean> dgVerificationReportList) {
		this.dgVerificationReportList = dgVerificationReportList;
	}

}
