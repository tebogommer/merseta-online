package haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.ReconSchemeYears;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;

// TODO: Auto-generated Javadoc
/**
 * The Class grantlistgenerationUI.
 */
@ManagedBean(name = "grantlistgenerationUI")
@ViewScoped
public class GrantListGenerationUI extends AbstractUI {

	private List<Integer> schemeYear;
	private List<ReconSchemeYears> sarsYears;
	private SarsFilesService sarsFilesService = new SarsFilesService();

	private ReportService reportService = new ReportService();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMM dd");
	private List<ByChamberReportBean> grantListGeneration;
	private Date fromDate;
	private Date toDate;

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
	 * Initialize method to get all grantlistgeneration and prepare a for a create
	 * of a new grantlistgeneration.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see grantlistgeneration
	 */
	private void runInit() throws Exception {
		this.setSarsYears(sarsFilesService.schemeYears());
	}
	
	public void populateBean() throws Exception {
		grantListGeneration = reportService.findEmployersBySize(fromDate, toDate);
	}


	public List<ReconSchemeYears> getSarsYears() {
		return sarsYears;
	}

	public void setSarsYears(List<ReconSchemeYears> sarsYears) {
		this.sarsYears = sarsYears;
	}

	public SarsFilesService getSarsFilesService() {
		return sarsFilesService;
	}

	public void setSarsFilesService(SarsFilesService sarsFilesService) {
		this.sarsFilesService = sarsFilesService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public List<ByChamberReportBean> getAfterLevyImportReport() {
		return grantListGeneration;
	}

	public void setAfterLevyImportReport(List<ByChamberReportBean> afterLevyImportReport) {
		this.grantListGeneration = afterLevyImportReport;
	}

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

	public List<Integer> getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(List<Integer> schemeYear) {
		this.schemeYear = schemeYear;
	}

}
