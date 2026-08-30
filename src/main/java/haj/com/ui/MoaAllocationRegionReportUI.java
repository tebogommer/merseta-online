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
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.WspService;
import haj.com.service.lookup.RegionTownService;

@ManagedBean(name = "moaAllocationRegionReportUI")
@ViewScoped
public class MoaAllocationRegionReportUI extends AbstractUI {
	
	/* Service Levels */
	private HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
	private WspService wspService = new WspService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	
	/* Lazy Data Models */
	private LazyDataModel<ActiveContracts> activeContractsDataModel;
	
	/* Array Lists */
	private List<Integer> financialYears;
	
	/* Vars */
	private Integer selectedYear;
	private boolean clearFilters = false;
	private boolean cloCrmUser = false;
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
		if (getSessionUI().getRole() == null || getSessionUI().getRole().getCloCrmReporting() == null || !getSessionUI().getRole().getCloCrmReporting()) {
			super.redirectToDashboard();
		} else {
			HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
			if (hce == null) {
				super.redirectToDashboard();
			} else {
				cloCrmUser = RegionTownService.instance().checkIfCrmCloUser(hce);
				if (cloCrmUser) {
					// populates distinct fin years
					populatesDistinctFinancialYears();
					activeContractsDataModelInfo();
				}
			}
		}
	}
	
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (!financialYears.isEmpty()) {
			selectedYear = financialYears.get(0);
			if (activeContractsService.countByCloCrmActiveContractsByMoaTypeAndGrantYear(getSessionUI().getActiveUser().getId(), MoaTypeEnum.DGMOA, selectedYear) > 65000) {
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
			if (activeContractsService.countByCloCrmActiveContractsByMoaTypeAndGrantYear(getSessionUI().getActiveUser().getId(), MoaTypeEnum.DGMOA, selectedYear) > 65000) {
				displayNormalDownload = false;
			} else {
				displayNormalDownload = true;
			}
			clearFilters = true;
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
					if (cloCrmUser && selectedYear != null) {
						retorno = activeContractsService.cloCrmActiveContractsRegionReportByMoaTypeAndGrantYear(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId(), MoaTypeEnum.DGMOA, selectedYear);
						activeContractsDataModel.setRowCount(activeContractsService.countCloCrmActiveContractsRegionReportByMoaTypeAndGrantYear(filters));
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

	public boolean isCloCrmUser() {
		return cloCrmUser;
	}

	public void setCloCrmUser(boolean cloCrmUser) {
		this.cloCrmUser = cloCrmUser;
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

	
}
