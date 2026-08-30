package  haj.com.ui;

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
 * The Class chamberrevenuebymonthUI.
 */
@ManagedBean(name = "chamberrevenuebymonthUI")
@ViewScoped
public class ChamberRevenueByMonthUI extends AbstractUI {
	
	private List<ReconSchemeYears> sarsYears;
	private SarsFilesService sarsFilesService = new SarsFilesService();

	private ReportService reportService = new ReportService();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMM dd");
	private List<ByChamberReportBean> chamberrevenuebymonth;
	private List<ByChamberReportBean> chamberrevenuebymonthPrevious;
	
	private Integer dateSelected; 
	
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
	 * Initialize method to get all chamberrevenuebymonth and prepare a for a create of a new chamberrevenuebymonth.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    chamberrevenuebymonth
	 */
	private void runInit() throws Exception {
		this.setSarsYears(sarsFilesService.schemeYears());
	}
	
	public void populateBean()throws Exception {
		try {
			schemeYearInfo = reportService.findSchemeYearInfo(dateSelected);
			schemeYearInfoPre = reportService.findSchemeYearInfo(dateSelected-1);
			populateChamberrevenuebymonth();
			populateChamberrevenuebymonthPrevious();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateChamberrevenuebymonthPrevious() throws Exception{
		chamberrevenuebymonthPrevious = reportService.findChamberRevenueByMonth(GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfoPre.getStartOfYear(),1))), GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfoPre.getEndOfYear(), 1))));
//		chamberrevenuebymonthPrevious = reportService.findChamberRevenueByMonth(GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.deductMonthsFromDate(schemeYearInfoPre.getStartOfYear(), 1))), schemeYearInfoPre.getEndOfYear());
//		chamberrevenuebymonthPrevious = reportService.findChamberRevenueByMonthAndSchemeYear(GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.deductMonthsFromDate(schemeYearInfoPre.getStartOfYear(), 1))), schemeYearInfoPre.getEndOfYear(), schemeYearInfoPre.getSchemeYear());
		 if(!chamberrevenuebymonthPrevious.isEmpty()) {
	    		ByChamberReportBean grandTotalPre = new ByChamberReportBean();
	    		grandTotalPre.setDescription("Total");
	        	for (ByChamberReportBean byChamberReportBean : chamberrevenuebymonthPrevious) {
	        		if(grandTotalPre.getBdAuto() == null){
	        			grandTotalPre.setBdAuto(byChamberReportBean.getBdAuto());
	        			grandTotalPre.setBdMetal(byChamberReportBean.getBdMetal());
	        			grandTotalPre.setBdMotor(byChamberReportBean.getBdMotor());
	        			grandTotalPre.setBdNewTyre(byChamberReportBean.getBdNewTyre());
	        			grandTotalPre.setBdPlastic(byChamberReportBean.getBdPlastic());
	        			grandTotalPre.setBdUnknown(byChamberReportBean.getBdUnknown());
	        			grandTotalPre.setBdAcm(byChamberReportBean.getBdAcm());
	        		}else{
	        			grandTotalPre.setBdAuto((grandTotalPre.getBdAuto()).add(byChamberReportBean.getBdAuto()));
	        			grandTotalPre.setBdMetal((grandTotalPre.getBdMetal()).add(byChamberReportBean.getBdMetal()));
	        			grandTotalPre.setBdMotor((grandTotalPre.getBdMotor()).add(byChamberReportBean.getBdMotor()));
	        			grandTotalPre.setBdNewTyre((grandTotalPre.getBdNewTyre()).add(byChamberReportBean.getBdNewTyre()));
	        			grandTotalPre.setBdPlastic((grandTotalPre.getBdPlastic()).add(byChamberReportBean.getBdPlastic()));
	        			grandTotalPre.setBdUnknown((grandTotalPre.getBdUnknown()).add(byChamberReportBean.getBdUnknown()));
	        			grandTotalPre.setBdAcm((grandTotalPre.getBdAcm()).add(byChamberReportBean.getBdAcm()));
	        		}
	    		}
	        	chamberrevenuebymonthPrevious.add(grandTotalPre);
	    	}
	}
	
	private void populateChamberrevenuebymonth() throws Exception{
		chamberrevenuebymonth = reportService.findChamberRevenueByMonth(GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfo.getStartOfYear(),1))), GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(GenericUtility.addMonthsToDate(schemeYearInfo.getEndOfYear(), 1))));
//		chamberrevenuebymonth = reportService.findChamberRevenueByMonth(GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.deductMonthsFromDate(schemeYearInfo.getStartOfYear(), 1))), schemeYearInfo.getEndOfYear());
//		chamberrevenuebymonth = reportService.findChamberRevenueByMonthAndSchemeYear(GenericUtility.getStartOfDay(GenericUtility.getFirstDayOfMonth(GenericUtility.deductMonthsFromDate(schemeYearInfo.getStartOfYear(), 1))), schemeYearInfo.getEndOfYear(), schemeYearInfo.getSchemeYear());
		 if(!chamberrevenuebymonth.isEmpty()) {
	    		ByChamberReportBean grandTotal = new ByChamberReportBean();
	        	grandTotal.setDescription("Total");
	        	for (ByChamberReportBean byChamberReportBean : chamberrevenuebymonth) {
	        		if(grandTotal.getBdAuto() == null){
	        			grandTotal.setBdAuto(byChamberReportBean.getBdAuto());
	        			grandTotal.setBdMetal(byChamberReportBean.getBdMetal());
	        			grandTotal.setBdMotor(byChamberReportBean.getBdMotor());
	        			grandTotal.setBdNewTyre(byChamberReportBean.getBdNewTyre());
	        			grandTotal.setBdPlastic(byChamberReportBean.getBdPlastic());
	        			grandTotal.setBdUnknown(byChamberReportBean.getBdUnknown());
	        			grandTotal.setBdAcm(byChamberReportBean.getBdAcm());
	        		}else{
	        			grandTotal.setBdAuto((grandTotal.getBdAuto()).add(byChamberReportBean.getBdAuto()));
	        			grandTotal.setBdMetal((grandTotal.getBdMetal()).add(byChamberReportBean.getBdMetal()));
	        			grandTotal.setBdMotor((grandTotal.getBdMotor()).add(byChamberReportBean.getBdMotor()));
	        			grandTotal.setBdNewTyre((grandTotal.getBdNewTyre()).add(byChamberReportBean.getBdNewTyre()));
	        			grandTotal.setBdPlastic((grandTotal.getBdPlastic()).add(byChamberReportBean.getBdPlastic()));
	        			grandTotal.setBdUnknown((grandTotal.getBdUnknown()).add(byChamberReportBean.getBdUnknown()));
	        			grandTotal.setBdAcm((grandTotal.getBdAcm()).add(byChamberReportBean.getBdAcm()));
	        		}
	    		}
	        	chamberrevenuebymonth.add(grandTotal);
	    	}
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

	public Integer getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Integer dateSelected) {
		this.dateSelected = dateSelected;
	}

	public List<ByChamberReportBean> getChamberrevenuebymonth() {
		return chamberrevenuebymonth;
	}

	public void setChamberrevenuebymonth(List<ByChamberReportBean> chamberrevenuebymonth) {
		this.chamberrevenuebymonth = chamberrevenuebymonth;
	}

	public List<ByChamberReportBean> getChamberrevenuebymonthPrevious() {
		return chamberrevenuebymonthPrevious;
	}

	public void setChamberrevenuebymonthPrevious(List<ByChamberReportBean> chamberrevenuebymonthPrevious) {
		this.chamberrevenuebymonthPrevious = chamberrevenuebymonthPrevious;
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

	
}
