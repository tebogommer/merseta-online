package haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.charts.bar.BarChartModel;

import haj.com.bean.ReconSchemeYears;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class transferInUI.
 */
@ManagedBean(name = "transferinUI")
@ViewScoped
public class TransferInUI extends AbstractUI {

	private ReportService reportService = new ReportService();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMM dd");
	private Integer schemeYear;
	private List<ReconSchemeYears> sarsYears;
	private SarsFilesService sarsFilesService = new SarsFilesService();
	private List<ByChamberReportBean> transferInBean;
	private Date fromDate = new Date();
	private Date toDate = new Date();
	private Integer dateSelected;
	private String dateselected1;

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
	 * Initialize method to get all transferIn and prepare a for a create of a new
	 * transferIn.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see transferIn
	 */
	private void runInit() throws Exception {
		this.setSarsYears(sarsFilesService.schemeYears());
	}
	
	public void populateBean()throws Exception {
		popualteDates();
		transferInBean = reportService.findEmployersBySize(fromDate, toDate);
		 if(transferInBean.size() != 0) {
	    		ByChamberReportBean grandTotal = new ByChamberReportBean();
	        	grandTotal.setDescription("Total");
	        	for (ByChamberReportBean byChamberReportBean : transferInBean) {
	        		
	        		if(grandTotal.getAuto() == null){
	        			grandTotal.setAuto(byChamberReportBean.getAuto());
	        			grandTotal.setMetal(byChamberReportBean.getMetal());
	        			grandTotal.setMotor(byChamberReportBean.getMotor());
	        			grandTotal.setNewTyre(byChamberReportBean.getNewTyre());
	        			grandTotal.setPlastic(byChamberReportBean.getPlastic());
	        			grandTotal.setUnknown(byChamberReportBean.getUnknown());
	        		}else{
	        			grandTotal.setAuto(grandTotal.getAuto().add(byChamberReportBean.getAuto()));
	        			grandTotal.setMetal(grandTotal.getMetal().add(byChamberReportBean.getMetal()));
	        			grandTotal.setMotor(grandTotal.getMotor().add(byChamberReportBean.getMotor()));
	        			grandTotal.setNewTyre(grandTotal.getNewTyre().add(byChamberReportBean.getNewTyre()));
	        			grandTotal.setPlastic(grandTotal.getPlastic().add(byChamberReportBean.getPlastic()));
	        			grandTotal.setUnknown(grandTotal.getUnknown().add(byChamberReportBean.getUnknown()));
	        		}
	    		}
	        	transferInBean.add(grandTotal);
	    	}
		 
	 }
	
	private void popualteDates() throws Exception {
		Calendar c = Calendar.getInstance();
		c.set(dateSelected, 01, 01);
		fromDate = GenericUtility.getFirstDateOfYear(c.getTime());
		toDate = GenericUtility.getLastDayOfYear(c.getTime());
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

	public Integer getSchemeYear() {
		return schemeYear;
	}

	public void setSchemeYear(Integer schemeYear) {
		this.schemeYear = schemeYear;
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

	public List<ReconSchemeYears> getSarsYears() {
		return sarsYears;
	}

	public void setSarsYears(List<ReconSchemeYears> sarsYears) {
		this.sarsYears = sarsYears;
	}

	public Integer getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Integer dateSelected) {
		this.dateSelected = dateSelected;
	}

	public String getDateselected1() {
		return dateselected1;
	}

	public void setDateselected1(String dateselected1) {
		this.dateselected1 = dateselected1;
	}

	public List<ByChamberReportBean> getEmployerSizeBean() {
		return transferInBean;
	}

	public void setEmployerSizeBean(List<ByChamberReportBean> employerSizeBean) {
		this.transferInBean = employerSizeBean;
	}

	public SarsFilesService getSarsFilesService() {
		return sarsFilesService;
	}

	public void setSarsFilesService(SarsFilesService sarsFilesService) {
		this.sarsFilesService = sarsFilesService;
	}

}
