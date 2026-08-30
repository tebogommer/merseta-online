package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ActiveContracts;
import haj.com.entity.Blank;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.WspService;
import haj.com.service.lookup.RegionTownService;

/**
 * The Class PipTaskReportUI.
 */
@ManagedBean(name = "pipTaskReportUI")
@ViewScoped
public class PipTaskReportUI extends AbstractUI {

	/* Service Levels */
	private WspService wspService = new WspService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();

	/* Lazy Data Model */
	private LazyDataModel<ActiveContracts> dataModel;
	
	/* Array Lists */
	private List<Integer> financialYears;
	
	/* Vars */
	private Integer selectedYear;
	private Integer yearForReport;
	private boolean displayReport;
	
	/* Enums */
	private MoaTypeEnum moaTypeSelection;

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
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
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
			yearForReport = selectedYear;
			displayReport = true;
			dataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void dataModelInfo() {
//		dataModel = new ActiveContractsDatamodel();
		dataModel = new LazyDataModel<ActiveContracts>() {
			private static final long serialVersionUID = 1L;
			private List<ActiveContracts> retorno = new ArrayList<>();
			@Override
			public List<ActiveContracts> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
//					retorno = activeContractsService.allActiveContractsSearch(ActiveContracts.class, first, pageSize, sortField, sortOrder, filters);
					retorno = activeContractsService.allActiveContractsSearchByFinYear(ActiveContracts.class, first, pageSize, sortField, sortOrder, filters, yearForReport, moaTypeSelection);
					for (ActiveContracts activeContracts : retorno) {
						if (activeContracts.getDgAllocationParent() != null) activeContracts.getDgAllocationParent().getWsp().getCompany().setRegionTown(RegionTownService.instance().findByTown(activeContracts.getDgAllocationParent().getWsp().getCompany().getResidentialAddress().getTown()));
					}
//					dataModel.setRowCount(activeContractsService.countSearch(ActiveContracts.class, filters));
					dataModel.setRowCount(activeContractsService.countSearchByFinYear(ActiveContracts.class, filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(ActiveContracts obj) {
				return obj.getId();
			}
			@Override
			public ActiveContracts getRowData(String rowKey) {
				for (ActiveContracts obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void downloadPipTaskTrackerReport(){
		try {
			activeContractsService.downloadPipTaskTrackerReport(yearForReport);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	/* getters and setters */
	public LazyDataModel<ActiveContracts> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ActiveContracts> dataModel) {
		this.dataModel = dataModel;
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

	public Integer getYearForReport() {
		return yearForReport;
	}

	public void setYearForReport(Integer yearForReport) {
		this.yearForReport = yearForReport;
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

}
