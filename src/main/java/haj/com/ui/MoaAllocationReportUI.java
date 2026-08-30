package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ActiveContracts;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.WspService;

@ManagedBean(name = "moaAllocationReportUI")
@ViewScoped
public class MoaAllocationReportUI extends AbstractUI {
	
	/* Service Levels */
	private WspService wspService = new WspService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	
	/* Lazy Data Models */
	private LazyDataModel<ActiveContracts> activeContractsDataModel;
	
	/* Array Lists */
	private List<Integer> financialYears;
	
	/* Vars */
	private MoaTypeEnum moaType;
	private Integer selectedYear;
	private boolean displayNormalDownload = false;
	
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
		populateInformation();
	}
	
	private void populateInformation() throws Exception {
		if (getSessionUI().isAdmin() || getSessionUI().getActiveUser().getMoaRegisterAccess()) {
			// populates distinct fin years
			moaType = MoaTypeEnum.DGMOA;
			populatesDistinctFinancialYears();
			activeContractsDataModelInfo();
		} else {
			super.redirectToDashboard();
		}
	}
	
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (!financialYears.isEmpty()) {
			selectedYear = financialYears.get(0);
			if (activeContractsService.countActiveContractsReportByMoaTypeAndGrantYear(MoaTypeEnum.DGMOA, selectedYear) > 65000) {
				displayNormalDownload = false;
			} else {
				displayNormalDownload = true;
			}
		} else {
			displayNormalDownload = false;
		}
	}
	
	public void selectFinYear(){
		try {
			// count for display
			if (activeContractsService.countActiveContractsReportByMoaTypeAndGrantYear(MoaTypeEnum.DGMOA, selectedYear) > 65000) {
				displayNormalDownload = false;
			} else {
				displayNormalDownload = true;
			}
			activeContractsDataModelInfo();
			addInfoMessage("Filter Applied");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void activeContractsDataModelInfo() {
		activeContractsDataModel = new LazyDataModel<ActiveContracts>() {
			private static final long serialVersionUID = 1L;
			private List<ActiveContracts> retorno = new ArrayList<>();
			@Override
			public List<ActiveContracts> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedYear != null) {
						if (moaType == MoaTypeEnum.DGMOA) {
							retorno = activeContractsService.activeContractsReportByMoaTypeAndGrantYear(first, pageSize, sortField, sortOrder, filters, MoaTypeEnum.DGMOA, selectedYear);
							activeContractsDataModel.setRowCount(activeContractsService.countActiveContractsReportByMoaTypeAndGrantYear(filters));
						} else {
							// special projects
						}
					}
				} catch (Exception e) {
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

	/* getters and Seters */
	public boolean isDisplayNormalDownload() {
		return displayNormalDownload;
	}

	public void setDisplayNormalDownload(boolean displayNormalDownload) {
		this.displayNormalDownload = displayNormalDownload;
	}

	public LazyDataModel<ActiveContracts> getActiveContractsDataModel() {
		return activeContractsDataModel;
	}

	public void setActiveContractsDataModel(LazyDataModel<ActiveContracts> activeContractsDataModel) {
		this.activeContractsDataModel = activeContractsDataModel;
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

	public MoaTypeEnum getMoaType() {
		return moaType;
	}

	public void setMoaType(MoaTypeEnum moaType) {
		this.moaType = moaType;
	}
	
}