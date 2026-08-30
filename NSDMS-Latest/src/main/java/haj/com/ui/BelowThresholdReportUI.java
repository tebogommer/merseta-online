package  haj.com.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;

import haj.com.bean.BelowThresReportBean;
import haj.com.bean.SchemeYearIdentifierBean;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;
import haj.com.utils.GenericUtility;

/**
 * The Class leviesbyschemeyearbylevymonthsplitintochambersUI.
 */
@ManagedBean(name = "belowThresholdReportUI")
@ViewScoped
public class BelowThresholdReportUI extends AbstractUI {
	
	/* Beans */
	private SchemeYearIdentifierBean schemeYearIdentifierBean = new SchemeYearIdentifierBean();
	
	/* Service Levels */
	private ReportService 	 reportService 		= new ReportService();
	private SarsFilesService sarsFilesService 	= new SarsFilesService();
	
	/* Dual List */
	private DualListModel<Integer> schemeYearDualList;
	
	/* Array Lists */
	private List<Integer> 				sarsYears;
	private List<BelowThresReportBean> 	reportBean;
	private List<BelowThresReportBean> 	allSchmeYearsBeanList;
	
	/* Vars */
	private Integer lastestSchemeYear = 0;
	private Integer countFilesUploadedForLastestSchemeYear = 0;
	
	private Double thresholdMinAmount = 0.01;
	private Double thresholdMaxAmount = 4000.0;
	private Double thresholdProRataMinAmount = 0.01;
	private Double thresholdProRataMaxAmount = 0.00;
	
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
		populateUniqueSchemeYearsAndDualList();	
	}
	
	private void populateUniqueSchemeYearsAndDualList() throws Exception {
		sarsYears = reportService.findSchemeYears();
		Collections.reverse(sarsYears);
		lastestSchemeYear = reportService.findLastestSchemeYear().get(0);
		schemeYearDualList = new DualListModel<>(sarsYears, new ArrayList<>());
		schemeYearIdentifierBean = reportService.findSchemeYearInfo(getNow());
		if (schemeYearIdentifierBean != null && schemeYearIdentifierBean.getSchemeYear() != null) {
			lastestSchemeYear = schemeYearIdentifierBean.getSchemeYear();
		}
		if (schemeYearIdentifierBean != null && schemeYearIdentifierBean.getStartOfYear() != null && schemeYearIdentifierBean.getEndOfYear() != null) {
//			Date fromDate = GenericUtility.getStartOfDay(schemeYearIdentifierBean.getStartOfYear());
//			Date toDate = GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(schemeYearIdentifierBean.getEndOfYear()));
			Date fromDate = GenericUtility.getStartOfDay(GenericUtility.addMonthsToDate(schemeYearIdentifierBean.getStartOfYear(), 1));
			Date toDate = GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth((GenericUtility.addMonthsToDate(schemeYearIdentifierBean.getEndOfYear(), 1))));
			countFilesUploadedForLastestSchemeYear = sarsFilesService.countSarsFilesWhereForMonthBetweenDates(fromDate, toDate);
			calculateProRataThresholdAmounts();
		}
	}
	
	private void calculateProRataThresholdAmounts() throws Exception {
		thresholdProRataMinAmount = 0.0;
		if (countFilesUploadedForLastestSchemeYear == null || countFilesUploadedForLastestSchemeYear == 0 || thresholdMaxAmount == null || thresholdMaxAmount == 0.0) {
			thresholdProRataMaxAmount = 0.0;
		} else {
			/*
			 * Formula
			 * 4000x10/12
			 * 4000 - MAx threshold amount (thresholdMaxAmount)
			 * 10 - Levies uploaded for the latest scheme year (countFilesUploadedForLastestSchemeYear)
			 * 12 - months in the year
			 */
			thresholdProRataMaxAmount = ( (thresholdMaxAmount * countFilesUploadedForLastestSchemeYear) / 12 );
		}
	}

	public void generateReport(){
		try {
			if (!schemeYearDualList.getTarget().isEmpty()) {
				reportBean = new ArrayList<>();
				Collections.sort(schemeYearDualList.getTarget());
				Collections.reverse(schemeYearDualList.getTarget());
				
				// version one
//				if (thresholdProRataMaxAmount == 0.0) {
//					reportBean = reportService.generateBelowThresholdReport(schemeYearDualList.getTarget(), lastestSchemeYear,thresholdMinAmount, (thresholdMaxAmount * -1), thresholdProRataMinAmount, thresholdProRataMaxAmount);
//				} else {
//					reportBean = reportService.generateBelowThresholdReport(schemeYearDualList.getTarget(), lastestSchemeYear,thresholdMinAmount, (thresholdMaxAmount * -1), thresholdProRataMinAmount, (thresholdProRataMaxAmount * -1));
//				}
				
				// version two
				reportBean = reportService.generateBelowThresholdReportVersionTwo(schemeYearDualList.getTarget(), thresholdMinAmount, thresholdMaxAmount);
				addInfoMessage("Action Complete");
			} else {
				reportBean = new ArrayList<>();
				addWarningMessage("Please Select Atleast One DHET Scheme Year before proceeding.");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateAllSchmeYearsView(){
		try {
			allSchmeYearsBeanList = reportService.generateAllSchemeYearsView(sarsYears, thresholdMinAmount, thresholdMaxAmount);
			runClientSideExecute("PF('allShemeYearsDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* getters and setters */
	public List<Integer> getSarsYears() {
		return sarsYears;
	}

	public void setSarsYears(List<Integer> sarsYears) {
		this.sarsYears = sarsYears;
	}

	public DualListModel<Integer> getSchemeYearDualList() {
		return schemeYearDualList;
	}

	public void setSchemeYearDualList(DualListModel<Integer> schemeYearDualList) {
		this.schemeYearDualList = schemeYearDualList;
	}

	public Integer getLastestSchemeYear() {
		return lastestSchemeYear;
	}

	public void setLastestSchemeYear(Integer lastestSchemeYear) {
		this.lastestSchemeYear = lastestSchemeYear;
	}

	public List<BelowThresReportBean> getReportBean() {
		return reportBean;
	}

	public void setReportBean(List<BelowThresReportBean> reportBean) {
		this.reportBean = reportBean;
	}

	public SchemeYearIdentifierBean getSchemeYearIdentifierBean() {
		return schemeYearIdentifierBean;
	}

	public void setSchemeYearIdentifierBean(SchemeYearIdentifierBean schemeYearIdentifierBean) {
		this.schemeYearIdentifierBean = schemeYearIdentifierBean;
	}

	public Integer getCountFilesUploadedForLastestSchemeYear() {
		return countFilesUploadedForLastestSchemeYear;
	}

	public void setCountFilesUploadedForLastestSchemeYear(Integer countFilesUploadedForLastestSchemeYear) {
		this.countFilesUploadedForLastestSchemeYear = countFilesUploadedForLastestSchemeYear;
	}

	public Double getThresholdMinAmount() {
		return thresholdMinAmount;
	}

	public void setThresholdMinAmount(Double thresholdMinAmount) {
		this.thresholdMinAmount = thresholdMinAmount;
	}

	public Double getThresholdMaxAmount() {
		return thresholdMaxAmount;
	}

	public void setThresholdMaxAmount(Double thresholdMaxAmount) {
		this.thresholdMaxAmount = thresholdMaxAmount;
	}

	public Double getThresholdProRataMinAmount() {
		return thresholdProRataMinAmount;
	}

	public void setThresholdProRataMinAmount(Double thresholdProRataMinAmount) {
		this.thresholdProRataMinAmount = thresholdProRataMinAmount;
	}

	public Double getThresholdProRataMaxAmount() {
		return thresholdProRataMaxAmount;
	}

	public void setThresholdProRataMaxAmount(Double thresholdProRataMaxAmount) {
		this.thresholdProRataMaxAmount = thresholdProRataMaxAmount;
	}

	public List<BelowThresReportBean> getAllSchmeYearsBeanList() {
		return allSchmeYearsBeanList;
	}

	public void setAllSchmeYearsBeanList(List<BelowThresReportBean> allSchmeYearsBeanList) {
		this.allSchmeYearsBeanList = allSchmeYearsBeanList;
	}
}
