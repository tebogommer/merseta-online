package haj.com.ui;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.LevyIncomeSummary;
import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.entity.lookup.ReportGenerationProperties;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.service.LevyIncomeSummaryService;
import haj.com.service.ReportService;
import haj.com.service.lookup.ReportGenerationPropertiesService;

@ManagedBean(name = "levyincomebyyearbychamberUI")
@ViewScoped
public class LevyIncomeByYearByChamberUI extends AbstractUI {
	
	/* Entity */
	private ReportGenerationProperties reportGenerationProperties;

	/* Service */
	private ReportService reportService = new ReportService();
	private ReportGenerationPropertiesService reportGenerationPropertiesService = new ReportGenerationPropertiesService();
	private LevyIncomeSummaryService levyIncomSummaryService = new LevyIncomeSummaryService();
	
	/* Array Lists */
	private List<ByChamberReportBean> levyincomebyyearbychamber;
	private List<LevyIncomeSummary> displayLevyList;
	
	/* Vars */
	private Date createDate;
	private Boolean displayGeneration = false; 

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
	 * Initialize method to get all levyincomebyyearbychamber and prepare a for a
	 * create of a new levyincomebyyearbychamber.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see levyincomebyyearbychamber
	 */
	private void runInit() throws Exception {
		// populate properties
		reportGenerationProperties = reportGenerationPropertiesService.findByReportProperty(ReportPropertiesEnum.Levy_Analysis_Scheme_Year_Chamber);
		if (reportGenerationProperties != null && reportGenerationProperties.getGenerationUnderway() != null && !reportGenerationProperties.getGenerationUnderway()) {
			displayGeneration = true;
		} else {
			displayGeneration = false;
		}
//		displayLevyList = levyIncomSummaryService.allLevyIncomeSummary();
		displayLevyList = levyIncomSummaryService.allLevyIncomeSummaryByType(ReportPropertiesEnum.Levy_Analysis_Scheme_Year_Chamber);
		if (!displayLevyList.isEmpty()) {
			createDate = displayLevyList.get(0).getCreateDate();
		}
	}
	
	/*
	 * NEED TO MOVE TO SERVICE LEVEL AFTER SIGN OFF
	 */
	public void populateBean() throws Exception {
		try {
			if (reportGenerationProperties != null && reportGenerationProperties.getGenerationUnderway() != null && reportGenerationProperties.getGenerationUnderway()) {
				addWarningMessage("Generation Already Underway");
			} else {
			 	reportGenerationProperties.setGenerationUnderway(true);
				reportGenerationProperties.setDateGenerationStarted(getNow());
				reportGenerationProperties.setDateLastCompleted(null);
				reportGenerationProperties.setUserStartedGeneration(getSessionUI().getActiveUser());
				reportGenerationPropertiesService.update(reportGenerationProperties);
				
				// kick off generation
				reportService.generateLevyIncomeByYearChamber();
				
				// for page
				reportGenerationProperties = reportGenerationPropertiesService.findByReportProperty(ReportPropertiesEnum.Levy_Analysis_Scheme_Year_Chamber);
				displayLevyList.clear();
				addInfoMessage("Report generation underway. You will be notified apon completion.");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	


	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public List<ByChamberReportBean> getLevyincomebyyearbychamber() {
		return levyincomebyyearbychamber;
	}

	public void setLevyincomebyyearbychamber(List<ByChamberReportBean> levyincomebyyearbychamber) {
		this.levyincomebyyearbychamber = levyincomebyyearbychamber;
	}

	public List<LevyIncomeSummary> getDisplayLevyList() {
		return displayLevyList;
	}

	public void setDisplayLevyList(List<LevyIncomeSummary> displayLevyList) {
		this.displayLevyList = displayLevyList;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getDisplayGeneration() {
		return displayGeneration;
	}

	public void setDisplayGeneration(Boolean displayGeneration) {
		this.displayGeneration = displayGeneration;
	}

	public ReportGenerationProperties getReportGenerationProperties() {
		return reportGenerationProperties;
	}

	public void setReportGenerationProperties(ReportGenerationProperties reportGenerationProperties) {
		this.reportGenerationProperties = reportGenerationProperties;
	}
}
