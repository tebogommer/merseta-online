package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.MoaStatusReportBean;
import haj.com.entity.Blank;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.WspService;

@ManagedBean(name = "moaStatusReportUI")
@ViewScoped
public class MoaStatusReportUI extends AbstractUI {

	/* Service Levels */
	private WspService wspService = new WspService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	
	/* Array Lists */
	private List<Integer> financialYears;
	private List<MoaStatusReportBean> moaStatusReportList = new ArrayList<>(); 
	
	/* Vars */
	private Integer selectedYear;
	private boolean displayReport;
	
	/* Enums */
	private MoaTypeEnum moaTypeSelection;
	
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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {	
		displayReport = false;
		moaTypeSelection = MoaTypeEnum.DGMOA;
		populatesDistinctFinancialYears();
	}	
	
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (!financialYears.isEmpty()) {
			selectedYear = financialYears.get(0);
		}
	}
	
	public void generateReport(){
		try {
			moaStatusReportList.clear();
			if (moaTypeSelection == MoaTypeEnum.DGMOA) {
				moaStatusReportList = activeContractsService.populateMoaStatusReportByGrantYear(selectedYear);
			} else {
				// special projects display, for a later stage
			}
			displayReport = true;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			moaStatusReportList.clear();
			displayReport = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and Setters */
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

	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

	public MoaTypeEnum getMoaTypeSelection() {
		return moaTypeSelection;
	}

	public void setMoaTypeSelection(MoaTypeEnum moaTypeSelection) {
		this.moaTypeSelection = moaTypeSelection;
	}

	public List<MoaStatusReportBean> getMoaStatusReportList() {
		return moaStatusReportList;
	}

	public void setMoaStatusReportList(List<MoaStatusReportBean> moaStatusReportList) {
		this.moaStatusReportList = moaStatusReportList;
	}
	
}