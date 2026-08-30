package haj.com.ui;

import java.util.ArrayList;
import java.util.Calendar;
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
import haj.com.bean.WspHistoryBean;
import haj.com.entity.Company;
import haj.com.entity.Wsp;
import haj.com.entity.enums.WSPSearchType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.utils.GenericUtility;

/**
 * The Class WspReportingUI. This UI is used for the creating and displaying or
 * reports regarding the wsp
 */
@ManagedBean(name = "wspHistoryReportUI")
@ViewScoped
public class WspHistoryReportUI extends AbstractUI {

	/** Service Level */
	private WspService wspService = null;
	/** Date Range Filters */
	private Date fromDate;
	private Date toDate;

	private Integer selectedYear;
	private Integer fromYear;
	private Integer toYear;

	/** Data Model Lists */
	private LazyDataModel<WspHistoryBean> dataModel;

	/** The Company */
	private Company company;

	/** Vars */
	private int index; // used for changing tabs

	/** Display Booleans */
	private boolean filterByDateRange = false;
	private boolean filterByFinYear = false;
	private int startYear = 2018;

	private WSPSearchType searchType;

	private ArrayList<WspHistoryBean> wspList = new ArrayList<>();

	/** Array Lists */
	private List<Integer> financialYears = new ArrayList<>();

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
		populateYears();
	}

	public void populateYears() {
		int year = (Calendar.getInstance().get(Calendar.YEAR)) + 1;
		for (int x = 2000; x < year; x++) {
			financialYears.add(x);
		}
	}

	public void prepareCompanyWSPList() {
		try {
			wspList.clear();
			wspList = (ArrayList<WspHistoryBean>) wspService.findWSPHistByCompany(company);
			if (wspList.size() <= 0) {
				throw new Exception("No WSP information found");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public void prepareWSPByFinYearList() {
		try {
			wspList.clear();
			wspList = (ArrayList<WspHistoryBean>) wspService.findWSPHistByFinYear(selectedYear);
			if (wspList.size() <= 0) {
				throw new Exception("No WSP information found");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public void prepareWSPByYearRange() {
		try {

			wspList.clear();
			wspList = (ArrayList<WspHistoryBean>) wspService.findByYearRange(fromYear, toYear);
			if (wspList.size() <= 0) {
				throw new Exception("No WSP information found");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

	}

	public void clearWSPSearchHistory() {
		company = null;
		selectedYear = null;
		fromYear = null;
		toYear = null;
		searchType = null;
		wspList.clear();
	}

	public void clearSearchData() {
		company = null;
		selectedYear = null;
		fromYear = null;
		toYear = null;
		wspList.clear();
	}

	/**
	 * Check to ensure only users with correct permissions can access the page
	 */
	private void permissionCheck() {
//		boolean hasAccess = true;
//		// check if super admin
//		if (!getSessionUI().getUser().getSuperAdmin()) {
//			// check if user has access to page
//			if (!getSessionUI().getUser().getTempReporting()) {
//				hasAccess = false;
//			}
//		}
//		if (!hasAccess) {
//			super.redirectToDashboard();
//		}
	}

	/**
	 * Populates the default paramters for selection
	 * 
	 * @throws Exception
	 */
	private void populateDefaultInformation() throws Exception {
		populateServiceLayers();
		populateDates();
	}

	/**
	 * Populates service levels
	 */
	private void populateServiceLayers() {
		if (wspService == null) {
			wspService = new WspService();
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
	 * Clears array Lists and reporting beans used
	 */
	private void clearArrayLists() throws Exception {
		wspList.clear();

	}

	/**
	 * Runs lazy load of data for report: wsp Report by Financial Year and
	 * status
	 */
	public void wspHistoryData() {
		dataModel = new LazyDataModel<WspHistoryBean>() {
			private static final long serialVersionUID = 1L;
			private List<WspHistoryBean> retorno = new ArrayList<WspHistoryBean>();

			@Override
			public List<WspHistoryBean> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {

					retorno = wspService.wspHistoryData(first, pageSize, sortField, sortOrder, filters, searchType,
							company, selectedYear, fromYear, toYear);
					dataModel.setRowCount(wspService.wspHistoryDataCount(filters, searchType, company, selectedYear,
							fromYear, toYear));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(WspHistoryBean obj) {
				return obj.getId();
			}

			@Override
			public WspHistoryBean getRowData(String rowKey) {
				for (WspHistoryBean obj : retorno) {
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public WSPSearchType getSearchType() {
		return searchType;
	}

	public void setSearchType(WSPSearchType searchType) {
		this.searchType = searchType;
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

	public ArrayList<WspHistoryBean> getWspList() {
		return wspList;
	}

	public void setWspList(ArrayList<WspHistoryBean> wspList) {
		this.wspList = wspList;
	}

	public LazyDataModel<WspHistoryBean> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspHistoryBean> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Integer> getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(List<Integer> financialYears) {
		this.financialYears = financialYears;
	}

}
