package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TabChangeEvent;

import haj.com.bean.MgVerificationReportBean;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MgVerificationService;
import haj.com.service.WspService;

/**
 * The Class MgReportingUI. This UI is used for the creating and displaying or reports regarding the MG verification
 */
@ManagedBean(name = "mgReportingUI")
@ViewScoped
public class MgReportingUI extends AbstractUI {

	/** Service Level */
	private MgVerificationService mgVerificationService = null;
	private WspService wspService = null;
	
	/** Array Lists */
	private List<Integer> financialYears;
	private List<MgVerificationReportBean> mgVerificationReportBean;

	/** Vars */
	private int index; // used for changing tabs
	private Integer selectedYear;

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
		populatesDistinctFinancialYears();
	}

	/**
	 * Populates 
	 */
	private void populateServiceLayers() {
		if (mgVerificationService == null) {
			mgVerificationService = new MgVerificationService();
		}
		if (wspService == null) {
			wspService = new WspService();
		}
	}

	/**
	 * Available financial Years for MG Verifications
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
	 * On Tab Change will populate the required information for reports to
	 * download
	 */
	public void onTabChange(TabChangeEvent event) {
		try {
			displayReport = false;
			clearArrayLists();
			switch (event.getTab().getId()) {
			case "mgVerificationReportTab":
				prepMgVerificationReportBean();
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
		mgVerificationReportBean = null;
	}

	/**
	 * populates default information for report: Dg Report by Financial Year and status
	 * 
	 * @throws Exception
	 */
	public void prepReportDgByFinancialYearAndStatus() throws Exception {
		populatesDistinctFinancialYears();
	}
	
	private void prepMgVerificationReportBean() throws Exception{
		mgVerificationReportBean = new ArrayList<>();
	}
	
	public void populateMgVerificationReportList() {
		try {
			displayReport = true;
			if (selectedYear == null) {
				throw new Exception("Select A Year");
			}
			mgVerificationReportBean = mgVerificationService.allMandatoryVerificationsByFinYearReport(selectedYear);
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	/** Getters and setters */
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

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public List<MgVerificationReportBean> getMgVerificationReportBean() {
		return mgVerificationReportBean;
	}

	public void setMgVerificationReportBean(List<MgVerificationReportBean> mgVerificationReportBean) {
		this.mgVerificationReportBean = mgVerificationReportBean;
	}

}
