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

import haj.com.bean.StatusReportBean;
import haj.com.bean.TaskUserReportBean;
import haj.com.bean.WSPReport;
import haj.com.entity.Company;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgVerification;
import haj.com.entity.Wsp;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DgAllocationParentService;
import haj.com.service.DgAllocationService;
import haj.com.service.DgVerificationService;
import haj.com.service.WspService;
import haj.com.utils.GenericUtility;

/**
 * The Class DgAllocationReportingUI. This UI is used for the creating and displaying or reports regarding the DG Allocation Section
 */
@ManagedBean(name = "dgAllocationReportingUI")
@ViewScoped
public class DgAllocationReportingUI extends AbstractUI {
	
	/** Entity Level */
	private Company selectedCompany = null;
	private Wsp selectedWsp = null;

	/** Service Level */
	private WspService wspService = null;
	private DgAllocationService dgAllocationService = null;
	private DgAllocationParentService dgAllocationParentService = null;
	private CompanyService companyService = null;
	private DgVerificationService dgVerificationService = null;

	/** Date Range Filters */
	private Date fromDate;
	private Date toDate;

	/** Array Lists */
	private List<Integer> financialYears;
	private Integer selectedYear;
	private List<WSPReport> wspReports;
	private List<DgAllocation> forcastAllocationList;

	/** Data Model Lists */
	private LazyDataModel<DgAllocation> dataModelDgAllocationDetailed;
	private LazyDataModel<Company> companyDataModel;
	private LazyDataModel<DgVerification> dgVerificationDataModel;
	
	/** Report Bean Lists */
	private List<TaskUserReportBean> tasksReportBean;
	private List<StatusReportBean> statusReportBean;

	/** Vars */
	private int index; // used for changing tabs

	/** Display Booleans */
	private boolean filterByDateRange = false;
	private boolean filterByFinYear = false;
	private boolean displayDgVerification = false;
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
		if (wspService == null) {
			wspService = new WspService();
		}
		if (dgAllocationService == null) {
			dgAllocationService = new DgAllocationService();
		}
		if (dgAllocationParentService == null) {
			dgAllocationParentService = new DgAllocationParentService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (dgVerificationService == null) {
			dgVerificationService = new DgVerificationService();
		}
	}

	/**
	 * Available financial Years for Reporting Pafe
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
			displayDgVerification = false;
			clearArrayLists();
			clearObjects();
			switch (event.getTab().getId()) {
			case "dgByFinYearTab":
				prepReportDgByFinancialYearAndStatus();
				break;
			case "dgBForcastTab":
				avalaibleCompanies();
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
		forcastAllocationList = null;
	}
	
	private void clearObjects() throws Exception{
		selectedCompany = null;
		selectedWsp = null;
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
	 * Populates the report for download: DG Allocation Detailed Report
	 */
	public void populateDgAllocationDetailedReportByFinYear() {
		try {
			displayReport = true;
			if (selectedYear == null) {
				throw new Exception("Select A Year");
			}
			dgAllocationDetailedByFinYearAndStatusInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Runs lazy load of data for report: Dg Allocation Detailed Report by Financial Year
	 */
	private void dgAllocationDetailedByFinYearAndStatusInfo() {
		dataModelDgAllocationDetailed = new LazyDataModel<DgAllocation>() {
			private static final long serialVersionUID = 1L;
			private List<DgAllocation> retorno = new ArrayList<DgAllocation>();
			@Override
			public List<DgAllocation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = dgAllocationService.allDgAllocationListByFinYear(first, pageSize, sortField, sortOrder, filters, selectedYear);
					dataModelDgAllocationDetailed.setRowCount(dgAllocationService.countAllDgAllocationListByFinYear(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(DgAllocation obj) {
				return obj.getId();
			}
			@Override
			public DgAllocation getRowData(String rowKey) {
				for (DgAllocation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void rerunComapnySelection(){
		try {
			avalaibleCompanies();
			displayDgVerification = false;
			displayReport = false;
			addInfoMessage("Company List Re-populated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void avalaibleCompanies() {
		companyDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = companyService.allCompanyByWspYearForDg(Company.class, first, pageSize, sortField, sortOrder, filters, selectedYear);
					companyDataModel.setRowCount(companyService.countAllCompanyByWspYearForDg(Company.class, filters));
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
	
	public void selectCompany(){
		try {
			dgVerificationDataModelInfo();
			displayDgVerification = true;
			displayReport = false;
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Lazy load for wsp
	 * 
	 * @throws Exception
	 */
	private void dgVerificationDataModelInfo() throws Exception {
		dgVerificationDataModel = new LazyDataModel<DgVerification>() {
			private static final long serialVersionUID = 1L;
			private List<DgVerification> retorno = new ArrayList<DgVerification>();
			@Override
			public List<DgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = dgVerificationService.allDgVerificationByCompany(first, pageSize, sortField, sortOrder, filters, selectedCompany);
					dgVerificationDataModel.setRowCount(dgVerificationService.countAllDgVerificationByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
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
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}
	
	public void populateDgAllocationForcatByWsp() {
		try {
			displayReport = true;
			if (selectedYear == null) {
				throw new Exception("Select A Year");
			}
			Wsp wsp = wspService.findByKey(selectedWsp.getId());
			forcastAllocationList = dgAllocationService.doAllocationForecastReportingByWsp(wsp);
			addInfoMessage("Report Generated");
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

	public LazyDataModel<DgAllocation> getDataModelDgAllocationDetailed() {
		return dataModelDgAllocationDetailed;
	}

	public void setDataModelDgAllocationDetailed(LazyDataModel<DgAllocation> dataModelDgAllocationDetailed) {
		this.dataModelDgAllocationDetailed = dataModelDgAllocationDetailed;
	}

	public List<DgAllocation> getForcastAllocationList() {
		return forcastAllocationList;
	}

	public void setForcastAllocationList(List<DgAllocation> forcastAllocationList) {
		this.forcastAllocationList = forcastAllocationList;
	}

	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public Wsp getSelectedWsp() {
		return selectedWsp;
	}

	public void setSelectedWsp(Wsp selectedWsp) {
		this.selectedWsp = selectedWsp;
	}

	public LazyDataModel<DgVerification> getDgVerificationDataModel() {
		return dgVerificationDataModel;
	}

	public void setDgVerificationDataModel(LazyDataModel<DgVerification> dgVerificationDataModel) {
		this.dgVerificationDataModel = dgVerificationDataModel;
	}

	public boolean isDisplayDgVerification() {
		return displayDgVerification;
	}

	public void setDisplayDgVerification(boolean displayDgVerification) {
		this.displayDgVerification = displayDgVerification;
	}

}
