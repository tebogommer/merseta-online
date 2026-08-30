package haj.com.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.ReconSchemeYears;
import haj.com.bean.SchemeYearIdentifierBean;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class percentageoftotalleviespaidbychamberUI.
 */
@ManagedBean(name = "percentageOfTotalLeviesPaidByChamberUI")
@ViewScoped
public class PercentageOfTotalLeviesPaidByChamberUI extends AbstractUI {

	private List<ReconSchemeYears> sarsYears;

	private SarsFilesService sarsFilesService = new SarsFilesService();
	private ReportService reportService = new ReportService();

	private SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM yyyy");

	private ByChamberReportBean percentageOfTotalLevies;
	private ByChamberReportBean percentageOfTotalLeviesPre;

	private ByChamberReportBean percentageBeanPre = new ByChamberReportBean();
	private ByChamberReportBean percentageBean = new ByChamberReportBean();

	private Integer dateSelected;

	private String dataNow = "";
	private String dataPre = "";
	
	private String headingDateNow = "";
	private String headingDatePre = "";
	private String headingForMainSchemeYearSelected = "";
	private String headingForPreviousSchemeYearCalc = "";
	
	private SchemeYearIdentifierBean schemeYearInfo;
	private SchemeYearIdentifierBean schemeYearInfoPre;

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
	 * Initialize method to get all percentageoftotalleviespaidbychamber and prepare
	 * a for a create of a new percentageoftotalleviespaidbychamber.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see percentageoftotalleviespaidbychamber
	 */
	private void runInit() throws Exception {
		this.setSarsYears(sarsFilesService.schemeYears());
	}

	public void message() {
		addErrorMessage("dsfghhdh");
	}

	public void populateBean() {
		try {
			
			schemeYearInfo = reportService.findSchemeYearInfo(dateSelected);
			String startDate = sdf2.format(GenericUtility.getFirstDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfo.getStartOfYear(),1)));
			String endDate = sdf2.format(GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfo.getEndOfYear(), 1))));
//			String startDate = sdf2.format(schemeYearInfo.getStartOfYear());
//			String endDate = sdf2.format(schemeYearInfo.getEndOfYear());
			headingForMainSchemeYearSelected = startDate + " - " + endDate;
			
			schemeYearInfoPre = reportService.findSchemeYearInfo(dateSelected - 1);
			startDate = sdf2.format(GenericUtility.getFirstDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfoPre.getStartOfYear(),1)));
			endDate = sdf2.format(GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfoPre.getEndOfYear(), 1))));
//			startDate = sdf2.format(schemeYearInfoPre.getStartOfYear());
//			endDate = sdf2.format(schemeYearInfoPre.getEndOfYear());
			headingForPreviousSchemeYearCalc = startDate + " - " + endDate;
			
			//chamberrevenuebymonth = reportService.findChamberRevenueByMonth(GenericUtility.getStartOfDay(, );
			percentageOfTotalLevies = reportService.findLevyContributionsBetweenDates(GenericUtility.getFirstDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfo.getStartOfYear(),1)), GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfo.getEndOfYear(), 1))));
//			percentageOfTotalLevies = reportService.findLevyContributionsBetweenDates(schemeYearInfo.getStartOfYear(), schemeYearInfo.getEndOfYear());
			
			if (percentageOfTotalLevies != null) {
				
				if (percentageOfTotalLevies.getBdTotal() != null && percentageOfTotalLevies.getBdAuto() != null) {
					percentageBean.setBdAuto(percentageOfTotalLevies.getBdAuto().divide(percentageOfTotalLevies.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBean.setBdAuto(BigDecimal.valueOf(0));
				}
				
				if (percentageOfTotalLevies.getBdTotal() != null && percentageOfTotalLevies.getBdMetal() != null) {
					percentageBean.setBdMetal(percentageOfTotalLevies.getBdMetal().divide(percentageOfTotalLevies.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBean.setBdMetal(BigDecimal.valueOf(0));
				}
				
				
				if (percentageOfTotalLevies.getBdTotal() != null && percentageOfTotalLevies.getBdMotor() != null) {
					percentageBean.setBdMotor(percentageOfTotalLevies.getBdMotor().divide(percentageOfTotalLevies.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBean.setBdMotor(BigDecimal.valueOf(0));
				}
				
				
				if (percentageOfTotalLevies.getBdTotal() != null && percentageOfTotalLevies.getBdNewTyre() != null) {
					percentageBean.setBdNewTyre(percentageOfTotalLevies.getBdNewTyre().divide(percentageOfTotalLevies.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBean.setBdNewTyre(BigDecimal.valueOf(0));
				}
				
				
				if (percentageOfTotalLevies.getBdTotal() != null && percentageOfTotalLevies.getBdPlastic() != null) {
					percentageBean.setBdPlastic(percentageOfTotalLevies.getBdPlastic().divide(percentageOfTotalLevies.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBean.setBdPlastic(BigDecimal.valueOf(0));
				}
				
				
				if (percentageOfTotalLevies.getBdTotal() != null && percentageOfTotalLevies.getBdUnknown() != null) {
					percentageBean.setBdUnknown(percentageOfTotalLevies.getBdUnknown().divide(percentageOfTotalLevies.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBean.setBdUnknown(BigDecimal.valueOf(0));
				}
				
				
				if (percentageOfTotalLevies.getBdTotal() != null && percentageOfTotalLevies.getBdAcm() != null) {
					percentageBean.setBdAcm(percentageOfTotalLevies.getBdAcm().divide(percentageOfTotalLevies.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBean.setBdAcm(BigDecimal.valueOf(0));
				}
				
			} else {
				percentageOfTotalLevies = new ByChamberReportBean();
				percentageBean.setBdAuto(BigDecimal.valueOf(0));
				percentageBean.setBdMetal(BigDecimal.valueOf(0));
				percentageBean.setBdMotor(BigDecimal.valueOf(0));
				percentageBean.setBdNewTyre(BigDecimal.valueOf(0));
				percentageBean.setBdPlastic(BigDecimal.valueOf(0));
				percentageBean.setBdUnknown(BigDecimal.valueOf(0));
				percentageBean.setBdAcm(BigDecimal.valueOf(0));
			}
			
			percentageOfTotalLeviesPre = reportService.findLevyContributionsBetweenDates(GenericUtility.getFirstDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfoPre.getStartOfYear(),1)), GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfoPre.getEndOfYear(), 1))));
//			percentageOfTotalLeviesPre = reportService.findLevyContributionsBetweenDates(schemeYearInfoPre.getStartOfYear(), schemeYearInfoPre.getEndOfYear());
			
			if (percentageOfTotalLeviesPre != null) {
				if (percentageOfTotalLeviesPre.getBdTotal() != null && percentageOfTotalLeviesPre.getBdAuto() != null) {
					percentageBeanPre.setBdAuto(percentageOfTotalLeviesPre.getBdAuto().divide(percentageOfTotalLeviesPre.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBeanPre.setBdAuto(BigDecimal.valueOf(0.0));
				}
				
				if (percentageOfTotalLeviesPre.getBdTotal() != null && percentageOfTotalLeviesPre.getBdMetal() != null) {
					percentageBeanPre.setBdMetal(percentageOfTotalLeviesPre.getBdMetal().divide(percentageOfTotalLeviesPre.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBeanPre.setBdMetal(BigDecimal.valueOf(0.0));
				}
				
				
				if (percentageOfTotalLeviesPre.getBdTotal() != null && percentageOfTotalLeviesPre.getBdMotor() != null) {
					percentageBeanPre.setBdMotor(percentageOfTotalLeviesPre.getBdMotor().divide(percentageOfTotalLeviesPre.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBeanPre.setBdMotor(BigDecimal.valueOf(0.0));
				}
				
				
				if (percentageOfTotalLeviesPre.getBdTotal() != null && percentageOfTotalLeviesPre.getBdNewTyre() != null) {
					percentageBeanPre.setBdNewTyre(percentageOfTotalLeviesPre.getBdNewTyre().divide(percentageOfTotalLeviesPre.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBeanPre.setBdNewTyre(BigDecimal.valueOf(0.0));
				}
				
				
				if (percentageOfTotalLeviesPre.getBdTotal() != null && percentageOfTotalLeviesPre.getBdPlastic() != null) {
					percentageBeanPre.setBdPlastic(percentageOfTotalLeviesPre.getBdPlastic().divide(percentageOfTotalLeviesPre.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBeanPre.setBdPlastic(BigDecimal.valueOf(0.0));
				}
				
				
				if (percentageOfTotalLeviesPre.getBdTotal() != null && percentageOfTotalLeviesPre.getBdUnknown() != null) {
					percentageBeanPre.setBdUnknown(percentageOfTotalLeviesPre.getBdUnknown().divide(percentageOfTotalLeviesPre.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBeanPre.setBdUnknown(BigDecimal.valueOf(0.0));
				}
				
				
				if (percentageOfTotalLeviesPre.getBdTotal() != null && percentageOfTotalLeviesPre.getBdAcm() != null) {
					percentageBeanPre.setBdAcm(percentageOfTotalLeviesPre.getBdAcm().divide(percentageOfTotalLeviesPre.getBdTotal(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
				} else {
					percentageBeanPre.setBdAcm(BigDecimal.valueOf(0.0));
				}
				
			} else {
				percentageOfTotalLeviesPre = new ByChamberReportBean();
				percentageBeanPre.setBdAuto(BigDecimal.valueOf(0));
				percentageBeanPre.setBdMetal(BigDecimal.valueOf(0));
				percentageBeanPre.setBdMotor(BigDecimal.valueOf(0));
				percentageBeanPre.setBdNewTyre(BigDecimal.valueOf(0));
				percentageBeanPre.setBdPlastic(BigDecimal.valueOf(0));
				percentageBeanPre.setBdUnknown(BigDecimal.valueOf(0));
				percentageBeanPre.setBdAcm(BigDecimal.valueOf(0));
			}

			dataNow = "[";

			dataNow = dataNow + "{ name: 'Auto', y:" + percentageOfTotalLevies.getBdAuto() + " },";
			dataNow = dataNow + "{ name: 'Metal', y:" + percentageOfTotalLevies.getBdMetal() + " },";
			dataNow = dataNow + "{ name: 'Motor', y:" + percentageOfTotalLevies.getBdMotor() + " },";
			dataNow = dataNow + "{ name: 'NewTyre', y:" + percentageOfTotalLevies.getBdNewTyre() + " },";
			dataNow = dataNow + "{ name: 'Plastic', y:" + percentageOfTotalLevies.getBdPlastic() + " },";
			dataNow = dataNow + "{ name: 'Unknown', y:" + percentageOfTotalLevies.getBdUnknown() + " },";
			dataNow = dataNow + "{ name: 'ACM', y:" + percentageOfTotalLevies.getBdAcm() + " },";
			dataNow = dataNow + "]";

			dataPre = "[";

			dataPre = dataPre + "{ name: 'Auto', y:" + percentageOfTotalLeviesPre.getBdAuto() + " },";
			dataPre = dataPre + "{ name: 'Metal', y:" + percentageOfTotalLeviesPre.getBdMetal() + " },";
			dataPre = dataPre + "{ name: 'Motor', y:" + percentageOfTotalLeviesPre.getBdMotor() + " },";
			dataPre = dataPre + "{ name: 'NewTyre', y:" + percentageOfTotalLeviesPre.getBdNewTyre() + " },";
			dataPre = dataPre + "{ name: 'Plastic', y:" + percentageOfTotalLeviesPre.getBdPlastic() + " },";
			dataPre = dataPre + "{ name: 'Unknown', y:" + percentageOfTotalLeviesPre.getBdUnknown() + " },";
			dataPre = dataPre + "{ name: 'ACM', y:" + percentageOfTotalLeviesPre.getBdAcm() + " },";
			dataPre = dataPre + "]";
			
			headingDateNow = sdf.format(schemeYearInfo.getStartOfYear());
			headingDatePre = sdf.format(schemeYearInfoPre.getStartOfYear());
			
			
			

			//TODO FIX GRAPHS TO USE CLIENTSIDEUPDATE
			//super.runClientSideExecute("runReports()");
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

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
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

	public Integer getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Integer dateSelected) {
		this.dateSelected = dateSelected;
	}

	public ByChamberReportBean getPercentageOfTotalLevies() {
		return percentageOfTotalLevies;
	}

	public void setPercentageOfTotalLevies(ByChamberReportBean percentageOfTotalLevies) {
		this.percentageOfTotalLevies = percentageOfTotalLevies;
	}

	public ByChamberReportBean getPercentageBean() {
		return percentageBean;
	}

	public void setPercentageBean(ByChamberReportBean percentageBean) {
		this.percentageBean = percentageBean;
	}

	public ByChamberReportBean getPercentageOfTotalLeviesPre() {
		return percentageOfTotalLeviesPre;
	}

	public void setPercentageOfTotalLeviesPre(ByChamberReportBean percentageOfTotalLeviesPre) {
		this.percentageOfTotalLeviesPre = percentageOfTotalLeviesPre;
	}

	public ByChamberReportBean getPercentageBeanPre() {
		return percentageBeanPre;
	}

	public void setPercentageBeanPre(ByChamberReportBean percentageBeanPre) {
		this.percentageBeanPre = percentageBeanPre;
	}

	public String getDataNow() {
		return dataNow;
	}

	public void setDataNow(String dataNow) {
		this.dataNow = dataNow;
	}

	public String getDataPre() {
		return dataPre;
	}

	public void setDataPre(String dataPre) {
		this.dataPre = dataPre;
	}

	public String getHeadingDateNow() {
		return headingDateNow;
	}

	public void setHeadingDateNow(String headingDateNow) {
		this.headingDateNow = headingDateNow;
	}

	public String getHeadingDatePre() {
		return headingDatePre;
	}

	public void setHeadingDatePre(String headingDatePre) {
		this.headingDatePre = headingDatePre;
	}

	public SchemeYearIdentifierBean getSchemeYearInfo() {
		return schemeYearInfo;
	}

	public void setSchemeYearInfo(SchemeYearIdentifierBean schemeYearInfo) {
		this.schemeYearInfo = schemeYearInfo;
	}

	public SchemeYearIdentifierBean getSchemeYearInfoPre() {
		return schemeYearInfoPre;
	}

	public void setSchemeYearInfoPre(SchemeYearIdentifierBean schemeYearInfoPre) {
		this.schemeYearInfoPre = schemeYearInfoPre;
	}

	public String getHeadingForMainSchemeYearSelected() {
		return headingForMainSchemeYearSelected;
	}

	public void setHeadingForMainSchemeYearSelected(String headingForMainSchemeYearSelected) {
		this.headingForMainSchemeYearSelected = headingForMainSchemeYearSelected;
	}

	public String getHeadingForPreviousSchemeYearCalc() {
		return headingForPreviousSchemeYearCalc;
	}

	public void setHeadingForPreviousSchemeYearCalc(String headingForPreviousSchemeYearCalc) {
		this.headingForPreviousSchemeYearCalc = headingForPreviousSchemeYearCalc;
	}
}
