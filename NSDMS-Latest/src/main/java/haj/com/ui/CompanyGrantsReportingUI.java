package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.GrantsSubmissionReportBean;
import haj.com.bean.WspCompanyReportBean;
import haj.com.entity.Wsp;
import haj.com.entity.WspSignoff;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspService;
import haj.com.service.WspSignoffService;

/**
 * The Class CompanyGrantsReportingUI. This UI is used for the creating and displaying or reports regarding the Company WSP submissions tally and summary
 */
@ManagedBean(name = "companyGrantsReportingUI")
@ViewScoped
public class CompanyGrantsReportingUI extends AbstractUI {

	/** Service Level */
	private WspService wspService = null;
	WspSignoffService wspSignoffService = null;
	
	/** Array Lists */
	private List<WspCompanyReportBean> grantSubmissionSummaryList = new ArrayList<>();
	private List<WspCompanyReportBean> grantSubmissionTallyList = new ArrayList<>();
	private List<GrantsSubmissionReportBean> grantsSubmissionReportList = new ArrayList<>();
	private List<Integer> financialYears;
	
	/** Lazy Data Models */
	private LazyDataModel<Wsp> dataModelWspByFinancialYear;

	/** Vars */
	private Integer selectedYear;

	/** Display Booleans */
	private boolean displaySubmissionsReport = false;
	private boolean displayTallyReport = false;
	private boolean displayWspFinYearReport = false;

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
		populateDefaultInformation();
	}
	

	/**
	 * Populates the default paramters for selection
	 * 
	 * @throws Exception
	 */
	private void populateDefaultInformation() throws Exception {
		populateServiceLayers();
		clearInformation();
		clearFinYearSelection();
	}

	/**
	 * Populates 
	 */
	private void populateServiceLayers() {
		if (wspService == null) {
			wspService = new WspService();
		}
		if (wspSignoffService == null) {
			wspSignoffService = new WspSignoffService();
		}
	}

	/**
	 * Clears array Lists and reporting beans used
	 */
	private void clearInformation() throws Exception {
		grantSubmissionSummaryList = new ArrayList<>();
		grantSubmissionTallyList = new ArrayList<>();
		displaySubmissionsReport = false;
		displayTallyReport = false;
		displayWspFinYearReport = false;
	}
	
	public void clearFinYearSelection() throws Exception{
		selectedYear = null;
		financialYears = wspService.findDictinctFinYears();
		if (!financialYears.isEmpty()) {
			selectedYear = financialYears.get(0);
		}
	}
	
	/**
	 * On Tab Change will populate the required information for reports to
	 * download
	 */
	public void onTabChange(TabChangeEvent event) {
		try {
			clearInformation();
			clearFinYearSelection();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateSubmissionsReport(){
		try {
			clearInformation();
			List <WspStatusEnum> entryList = WspStatusEnum.getWspStatusSspReportList();
			int counter = 1;
			for (WspStatusEnum entry : entryList) {
				System.out.println("WspStatusEnum.getWspStatusSspReportList():: entry 1:: "+entry.ordinal());
			}
			System.out.println("WspStatusEnum.getWspStatusSspReportList():: Value 1:: "+WspStatusEnum.getWspStatusSspReportList());
			grantSubmissionSummaryList = wspService.populateGrantSubmissionReportByCompany(WspStatusEnum.getWspStatusSspReportList());
			displaySubmissionsReport = true;
			addInfoMessage("Generation Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateTallyReport(){
		try {
			clearInformation();
            grantSubmissionTallyList = wspService.populateGrantSubmissionReportByCompanySummary(WspStatusEnum.getWspStatusEnumSubmittedList());
            displayTallyReport = true;
			addInfoMessage("Generation Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateGrantsByFinYearReport(){
		try {
			clearInformation();
			dataModelWspByFinancialYearInfo();
			displayWspFinYearReport = true;
			addInfoMessage("Generation Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void dataModelWspByFinancialYearInfo() {
		dataModelWspByFinancialYear = new LazyDataModel<Wsp>() {
			private static final long serialVersionUID = 1L;
			private List<Wsp> retorno = new ArrayList<>();
			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = wspService.wspReportByFinancialYearWithAllResolves(first, pageSize, sortField, sortOrder, filters, selectedYear, null);
					dataModelWspByFinancialYear.setRowCount(wspService.countWspReportByFinancialYear(filters, selectedYear, null));
				} catch (Exception e) {
					e.printStackTrace();
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
	
	public void generateGrantsByFinYearReportVersionTwo(){
		try {
			clearInformation();
			grantsSubmissionReportList = wspService.populateGrantsSubmissionReportByGrantYear(selectedYear);
			displayWspFinYearReport = true;
			addInfoMessage("Generation Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public boolean isDisplaySubmissionsReport() {
		return displaySubmissionsReport;
	}

	public void setDisplaySubmissionsReport(boolean displaySubmissionsReport) {
		this.displaySubmissionsReport = displaySubmissionsReport;
	}

	public boolean isDisplayTallyReport() {
		return displayTallyReport;
	}

	public void setDisplayTallyReport(boolean displayTallyReport) {
		this.displayTallyReport = displayTallyReport;
	}

	public List<WspCompanyReportBean> getGrantSubmissionSummaryList() {
		return grantSubmissionSummaryList;
	}

	public void setGrantSubmissionSummaryList(List<WspCompanyReportBean> grantSubmissionSummaryList) {
		this.grantSubmissionSummaryList = grantSubmissionSummaryList;
	}

	public List<WspCompanyReportBean> getGrantSubmissionTallyList() {
		return grantSubmissionTallyList;
	}

	public void setGrantSubmissionTallyList(List<WspCompanyReportBean> grantSubmissionTallyList) {
		this.grantSubmissionTallyList = grantSubmissionTallyList;
	}

	public List<Integer> getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(List<Integer> financialYears) {
		this.financialYears = financialYears;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public LazyDataModel<Wsp> getDataModelWspByFinancialYear() {
		return dataModelWspByFinancialYear;
	}

	public void setDataModelWspByFinancialYear(LazyDataModel<Wsp> dataModelWspByFinancialYear) {
		this.dataModelWspByFinancialYear = dataModelWspByFinancialYear;
	}

	public boolean isDisplayWspFinYearReport() {
		return displayWspFinYearReport;
	}

	public void setDisplayWspFinYearReport(boolean displayWspFinYearReport) {
		this.displayWspFinYearReport = displayWspFinYearReport;
	}

	public List<GrantsSubmissionReportBean> getGrantsSubmissionReportList() {
		return grantsSubmissionReportList;
	}

	public void setGrantsSubmissionReportList(List<GrantsSubmissionReportBean> grantsSubmissionReportList) {
		this.grantsSubmissionReportList = grantsSubmissionReportList;
	}

}
