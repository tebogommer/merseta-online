package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.GrantSspReportBean;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.lookup.FinancialYears;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.lookup.FinancialYearsService;

@ManagedBean(name = "grantSspReportingUI")
@ViewScoped
public class GrantSspReportingUI extends AbstractUI {

	/* Entity Levels */
	private FinancialYears selectedFinancialYears = null;
	
	/* Service Levels */
	private FinancialYearsService financialYearsService = new FinancialYearsService();
	private MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();
	
	/* Lazy Data Models */
	private LazyDataModel<FinancialYears> financialYearsDataModel;
	
	/* Array Lists */
	private List<GrantSspReportBean> reportList = new ArrayList<>();
	
	/* Vars */
	private boolean displayReport = false;
	private String reportType = "";
	
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
		if (getSessionUI().getActiveUser().getSspReporting() == null || !getSessionUI().getActiveUser().getSspReporting()) {
			ajaxRedirectToDashboard();
		} else {
			reportType = "";
			displayReport = false;
			selectedFinancialYearsInfo();
		}	
	}
	
	private void selectedFinancialYearsInfo() {
		financialYearsDataModel = new LazyDataModel<FinancialYears>() {
			private static final long serialVersionUID = 1L;
			private List<FinancialYears> retorno = new ArrayList<>();
			@Override
			public List<FinancialYears> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = financialYearsService.allFinancialYears(FinancialYears.class, first, pageSize, sortField, sortOrder, filters);
					financialYearsDataModel.setRowCount(financialYearsService.count(FinancialYears.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(FinancialYears obj) {
				return obj.getId();
			}
			@Override
			public FinancialYears getRowData(String rowKey) {
				for (FinancialYears obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	private void clearResults(){
		selectedFinancialYears = null;
		reportList.clear();
		displayReport = false;
		reportType = "";
	}
	
	public void clearReport(){
		try {
			clearResults();
			addWarningMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void generateAtrReportByFinYear(){
		try {
			if (selectedFinancialYears == null || selectedFinancialYears.getGrantFocusYear() == null) {
				clearResults();
				addErrorMessage("Unable to locate financial year or grant focus year. Contact support!");
			} else {
				reportList = mandatoryGrantDetailService.populateSspReportingByGrantYearAndStatusLists(selectedFinancialYears.getGrantFocusYear(), WspStatusEnum.getWspStatusSspReportList(), WspReportEnum.getWspReportAtrSspReportingList());
				displayReport = true;
				reportType = "ATR";
				if (reportList.isEmpty()) {
					addWarningMessage("No Results Found");
					clearResults();
				}else {
					addInfoMessage("Action Complete");
				}
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateWspReportByFinYear(){
		try {
			if (selectedFinancialYears == null || selectedFinancialYears.getGrantFocusYear() == null) {
				clearResults();
				addErrorMessage("Unable to locate financial year or grant focus year. Contact support!");
			} else {
				reportList = mandatoryGrantDetailService.populateSspReportingByGrantYearAndStatusLists(selectedFinancialYears.getGrantFocusYear(), WspStatusEnum.getWspStatusSspReportList(), WspReportEnum.getWspReportWspSspReportingList());
				displayReport = true;
				reportType = "WSP";
				if (reportList.isEmpty()) {
					addWarningMessage("No Results Found");
					clearResults();
				}else {
					addInfoMessage("Action Complete");
				}
			}
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

	public List<GrantSspReportBean> getReportList() {
		return reportList;
	}

	public void setReportList(List<GrantSspReportBean> reportList) {
		this.reportList = reportList;
	}

	public LazyDataModel<FinancialYears> getFinancialYearsDataModel() {
		return financialYearsDataModel;
	}

	public void setFinancialYearsDataModel(LazyDataModel<FinancialYears> financialYearsDataModel) {
		this.financialYearsDataModel = financialYearsDataModel;
	}

	public FinancialYears getSelectedFinancialYears() {
		return selectedFinancialYears;
	}

	public void setSelectedFinancialYears(FinancialYears selectedFinancialYears) {
		this.selectedFinancialYears = selectedFinancialYears;
	}

	
}
