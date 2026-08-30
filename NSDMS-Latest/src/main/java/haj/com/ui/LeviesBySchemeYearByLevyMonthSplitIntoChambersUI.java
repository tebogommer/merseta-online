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
 * The Class leviesbyschemeyearbylevymonthsplitintochambersUI.
 */
@ManagedBean(name = "leviesbyschemeyearbylevymonthsplitintochambersUI")
@ViewScoped
public class LeviesBySchemeYearByLevyMonthSplitIntoChambersUI extends AbstractUI {
	
	private List<ReconSchemeYears> sarsYears;
	private SarsFilesService sarsFilesService = new SarsFilesService();

	private ReportService reportService = new ReportService();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMM dd");
	private List<ByChamberReportBean> leviesbyschemeyearbylevymonthsplitintochambers;
	private String fromDateText;
	private Integer dateSelected; 
	private SchemeYearIdentifierBean schemeYearInfo;

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
	 * Initialize method to get all leviesbyschemeyearbylevymonthsplitintochambers and prepare a for a create of a new leviesbyschemeyearbylevymonthsplitintochambers.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    leviesbyschemeyearbylevymonthsplitintochambers
	 */
	private void runInit() throws Exception {
		this.setSarsYears(sarsFilesService.schemeYears());	
	}

	public void populateBean() throws Exception {
		schemeYearInfo = reportService.findSchemeYearInfo(dateSelected);
		fromDateText = sdf.format(schemeYearInfo.getStartOfYear());
		leviesbyschemeyearbylevymonthsplitintochambers = reportService.findChamberRevenueByMonth(schemeYearInfo.getStartOfYear(), schemeYearInfo.getEndOfYear());
	
		 if(!leviesbyschemeyearbylevymonthsplitintochambers.isEmpty()) {
	    		ByChamberReportBean grandTotal = new ByChamberReportBean();
	        	grandTotal.setDescription("Total");
	        	for (ByChamberReportBean byChamberReportBean : leviesbyschemeyearbylevymonthsplitintochambers) {
	        		if(grandTotal.getBdAuto() == null){
	        			grandTotal.setBdAuto(byChamberReportBean.getBdAuto());
	        			grandTotal.setBdMetal(byChamberReportBean.getBdMetal());
	        			grandTotal.setBdMotor(byChamberReportBean.getBdMotor());
	        			grandTotal.setBdNewTyre(byChamberReportBean.getBdNewTyre());
	        			grandTotal.setBdPlastic(byChamberReportBean.getBdPlastic());
	        			grandTotal.setBdUnknown(byChamberReportBean.getBdUnknown());
	        		}else{
	        			grandTotal.setBdAuto((grandTotal.getBdAuto()).add(byChamberReportBean.getBdAuto()));
	        			grandTotal.setBdMetal((grandTotal.getBdMetal()).add(byChamberReportBean.getBdMetal()));
	        			grandTotal.setBdMotor((grandTotal.getBdMotor()).add(byChamberReportBean.getBdMotor()));
	        			grandTotal.setBdNewTyre((grandTotal.getBdNewTyre()).add(byChamberReportBean.getBdNewTyre()));
	        			grandTotal.setBdPlastic((grandTotal.getBdPlastic()).add(byChamberReportBean.getBdPlastic()));
	        			grandTotal.setBdUnknown((grandTotal.getBdUnknown()).add(byChamberReportBean.getBdUnknown()));
	        		}
	    		}
	        	leviesbyschemeyearbylevymonthsplitintochambers.add(grandTotal);
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

	public List<ByChamberReportBean> getLeviesbyschemeyearbylevymonthsplitintochambers() {
		return leviesbyschemeyearbylevymonthsplitintochambers;
	}

	public void setLeviesbyschemeyearbylevymonthsplitintochambers(
			List<ByChamberReportBean> leviesbyschemeyearbylevymonthsplitintochambers) {
		this.leviesbyschemeyearbylevymonthsplitintochambers = leviesbyschemeyearbylevymonthsplitintochambers;
	}

	public Integer getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Integer dateSelected) {
		this.dateSelected = dateSelected;
	}

	public String getFromDateText() {
		return fromDateText;
	}

	public void setFromDateText(String fromDateText) {
		this.fromDateText = fromDateText;
	}

	public SchemeYearIdentifierBean getSchemeYearInfo() {
		return schemeYearInfo;
	}

	public void setSchemeYearInfo(SchemeYearIdentifierBean schemeYearInfo) {
		this.schemeYearInfo = schemeYearInfo;
	}
}
