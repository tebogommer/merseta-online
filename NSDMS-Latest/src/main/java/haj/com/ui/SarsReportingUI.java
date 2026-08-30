package  haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.SarsLevyReportBean;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SarsLevyDetailsService;
import haj.com.utils.GenericUtility;

/*
 * This UI is used for the following reports:
 * SARS Report By Scheme Year Summary (/MerSETA/src/main/webapp/admin/sarsReportBySchemeYearSummary.xhtml)
 * Summary of SARS Levy Detail By Arrival Dates
 * SARS Summary Over Multiple Scheme Year Report
 */
@ManagedBean(name = "sarsReportingUI")
@ViewScoped
public class SarsReportingUI extends AbstractUI {
	
	/* Service levels */
	private SarsLevyDetailsService sarsLevyDetailsService;

	/* Array Lists */
	private List<SarsLevyReportBean> sarsLevyReportResults = null;
	private List<Integer> distinctSchemeYearList = null;
	
	/* Booleans */
	private boolean reportGeneratedWithoutError = false;
	
	/* Date selections */
	private Date fromDateSelection;
	private Date toDateSelection;
	
	/* Scheme Year selections */
	private Integer fromSchemeYear;
	private Integer toSchemeYear;
	
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
		populateServiceLevels();
		populateDefaultInformation();
	}

	private void populateServiceLevels() throws Exception{
		if (sarsLevyDetailsService == null) {
			sarsLevyDetailsService = new SarsLevyDetailsService();
		}
	}
	
	public void populateDefaultInformation() throws Exception{
		
		// set to default for result section not to display
		reportGeneratedWithoutError = false;
		
		// locates distinct Scheme years for selection
		distinctSchemeYearList = sarsLevyDetailsService.locateDistinctSchemeYears();
		
		// new instance of result list
		sarsLevyReportResults = new ArrayList<>();
		
		// pre populate the dates
		fromDateSelection = GenericUtility.deductYearsfromDate(getNow(), 1);
		toDateSelection = getNow();
		
	}
	
	/*
	 * Generate Report: SARS Report By Scheme Year Summary
	 */
	public void generateSchemeYearSummaryReport(){
		try {
			sarsLevyReportResults = sarsLevyDetailsService.schemeYearSummaryReport();
			addInfoMessage("Generation Complete");
			reportGeneratedWithoutError = true;
		} catch (Exception e) {
			reportGeneratedWithoutError = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/*
	 * Generate Report: Summary of SARS Levy Detail By Arrival Dates
	 */
	public void generateLevyDetailByArrivalDate(){
		try {
			dateValidiations();
//			sarsLevyReportResults = sarsLevyDetailsService.summaryReportBetweenArrivalDates(fromDateSelection, toDateSelection);
			// Version 2
			sarsLevyReportResults = sarsLevyDetailsService.summaryReportBetweenArrivalDatesVersionTwo(fromDateSelection, toDateSelection);
			addInfoMessage("Generation Complete");
			reportGeneratedWithoutError = true;
		} catch (Exception e) {
			reportGeneratedWithoutError = false;
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void dateValidiations() throws Exception{
		if (fromDateSelection == null || toDateSelection == null) {
			throw new Exception("Validiation Expection: Provide Arrival From and To Date Before Proceeding.");
		}
		if (fromDateSelection.after(toDateSelection)) {
			throw new Exception("Validiation Expection: Please Ensure From Date Selection Is Before To Date Selection");
		}
		if (toDateSelection.before(fromDateSelection)) {
			throw new Exception("Validiation Expection: Please Ensure To Date Selection Is After From Date Selection");
		}
	}

	/*
	 * Generate Report: SARS Summary Over Multiple Scheme Year Report
	 */
	public void generateLevyDetailOverMultipleSchemeYears(){
		try {
			schemeyearSelectionValidiation();
			// Version One
//			sarsLevyReportResults = sarsLevyDetailsService.multipleSchemeYearSummaryReport(fromSchemeYear, toSchemeYear);
			// Version Two
			sarsLevyReportResults = sarsLevyDetailsService.multipleSchemeYearSummaryReportVersionTwo(fromSchemeYear, toSchemeYear);
			addInfoMessage("Generation Complete");
			reportGeneratedWithoutError = true;
		} catch (Exception e) {
			reportGeneratedWithoutError = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void generateLevyDetailOverMultipleSchemeYearsAndArriDates(){
		try {
			schemeyearSelectionValidiation();
			sarsLevyReportResults = sarsLevyDetailsService.multipleSchemeYearSummaryReportVersionTwo(fromSchemeYear, toSchemeYear, fromDateSelection, toDateSelection);
			addInfoMessage("Generation Complete");
			reportGeneratedWithoutError = true;
		} catch (Exception e) {
			reportGeneratedWithoutError = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void schemeyearSelectionValidiation() throws Exception{
		if (fromSchemeYear == null || toSchemeYear == null) {
			throw new Exception("Validiation Expection: Provide Scheme Year From and To Selection Before Proceeding.");
		}
		if (fromSchemeYear > toSchemeYear) {
			throw new Exception("Validiation Expection: Please Ensure From Scheme Year Selection Is Before The To Scheme Year Selection");
		}
		if (toSchemeYear < fromSchemeYear) {
			throw new Exception("Validiation Expection: Please Ensure To Scheme Year Selection Is After From Selection");
		}
	}

	/* Getters and setters */
	public List<SarsLevyReportBean> getSarsLevyReportResults() {
		return sarsLevyReportResults;
	}

	public void setSarsLevyReportResults(List<SarsLevyReportBean> sarsLevyReportResults) {
		this.sarsLevyReportResults = sarsLevyReportResults;
	}

	public boolean isReportGeneratedWithoutError() {
		return reportGeneratedWithoutError;
	}

	public void setReportGeneratedWithoutError(boolean reportGeneratedWithoutError) {
		this.reportGeneratedWithoutError = reportGeneratedWithoutError;
	}

	public Date getFromDateSelection() {
		return fromDateSelection;
	}

	public void setFromDateSelection(Date fromDateSelection) {
		this.fromDateSelection = fromDateSelection;
	}

	public Date getToDateSelection() {
		return toDateSelection;
	}

	public void setToDateSelection(Date toDateSelection) {
		this.toDateSelection = toDateSelection;
	}

	public List<Integer> getDistinctSchemeYearList() {
		return distinctSchemeYearList;
	}

	public void setDistinctSchemeYearList(List<Integer> distinctSchemeYearList) {
		this.distinctSchemeYearList = distinctSchemeYearList;
	}

	public Integer getFromSchemeYear() {
		return fromSchemeYear;
	}

	public void setFromSchemeYear(Integer fromSchemeYear) {
		this.fromSchemeYear = fromSchemeYear;
	}

	public Integer getToSchemeYear() {
		return toSchemeYear;
	}

	public void setToSchemeYear(Integer toSchemeYear) {
		this.toSchemeYear = toSchemeYear;
	}

	
}
