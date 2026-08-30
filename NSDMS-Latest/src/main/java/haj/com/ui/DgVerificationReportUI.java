package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.DgVerificationReportBean;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgVerificationService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.WspService;

@ManagedBean(name = "dgVerificationReportUI")
@ViewScoped
public class DgVerificationReportUI extends AbstractUI {
	
	/* Service Levels */
	private HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
	private WspService wspService = new WspService();
	private DgVerificationService dgVerificationService = new DgVerificationService();
	
	/* Lazy Data Models */
	private List<DgVerificationReportBean> dgVerificationReportList;
	
	/* Array Lists */
	private List<Integer> financialYears;
	
	/* Vars */
	private Integer selectedYear;
	private boolean displayNormalDownload = false;
	private boolean displayReport;
	
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
		populateInformation();
	}
	
	private void populateInformation() throws Exception {
		if (getSessionUI().getUser() == null || getSessionUI().getUser().getDgVerificationReport() == null || !getSessionUI().getUser().getDgVerificationReport()) {
			super.redirectToDashboard();
		} else {
			displayReport = false;
			populatesDistinctFinancialYears();
		}
	}
	
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (!financialYears.isEmpty()) {
			selectedYear = financialYears.get(0);
			displayNormalDownload = true;
		} else {
			displayNormalDownload = false;
		}
	}
	
	public void generateReport(){
		try {
			// count for display
			displayNormalDownload = true;
			displayReport = true;
			runReport();
			addInfoMessage("Report Generated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runReport() throws Exception{
		dgVerificationReportList = dgVerificationService.getAllVerificationsByFinYear(selectedYear);
	}

	/* getters and Seters */
	public boolean isDisplayNormalDownload() {
		return displayNormalDownload;
	}

	public void setDisplayNormalDownload(boolean displayNormalDownload) {
		this.displayNormalDownload = displayNormalDownload;
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

	public List<DgVerificationReportBean> getDgVerificationReportList() {
		return dgVerificationReportList;
	}

	public void setDgVerificationReportList(List<DgVerificationReportBean> dgVerificationReportList) {
		this.dgVerificationReportList = dgVerificationReportList;
	}

	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

	
}
