package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ActiveContractDetail;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractDetailService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.WspService;
import haj.com.service.lookup.RegionTownService;

@ManagedBean(name = "moaPaymentsReportUI")
@ViewScoped
public class MoaPaymentsReportUI extends AbstractUI {
	
	/* Service Levels */
	private WspService wspService = new WspService();
	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();
	
	/* Lazy Data Models */
	private LazyDataModel<ActiveContractDetail> activeContractDetailDataModel;
	
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
			moaType = MoaTypeEnum.DGMOA;
			populatesDistinctFinancialYears();
			activeContractDetailDataModelInfo();
		} else {
			super.redirectToDashboard();
		}
	}
	
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (!financialYears.isEmpty()) {
			selectedYear = financialYears.get(0);
			if (activeContractDetailService.countActiveContractDetailReportByMoaTypeAndGrantYear(MoaTypeEnum.DGMOA, selectedYear) > 65000) {
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
			if (activeContractDetailService.countActiveContractDetailReportByMoaTypeAndGrantYear(MoaTypeEnum.DGMOA, selectedYear) > 65000) {
				displayNormalDownload = false;
			} else {
				displayNormalDownload = true;
			}
			activeContractDetailDataModelInfo();
			addInfoMessage("Filter Applied");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void activeContractDetailDataModelInfo() {
		activeContractDetailDataModel = new LazyDataModel<ActiveContractDetail>() {
			private static final long serialVersionUID = 1L;
			private List<ActiveContractDetail> retorno = new ArrayList<>();
			
			@Override
			public List<ActiveContractDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedYear != null) {
						if (moaType == MoaTypeEnum.DGMOA) {
							retorno = activeContractDetailService.activeContractDetailReportByMoaTypeAndGrantYear(first, pageSize, sortField, sortOrder, filters, MoaTypeEnum.DGMOA, selectedYear);
							activeContractDetailDataModel.setRowCount(activeContractDetailService.countActiveContractDetailReportByMoaTypeAndGrantYear(filters));
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
			public Object getRowKey(ActiveContractDetail obj) {
				return obj.getId();
			}
			
			@Override
			public ActiveContractDetail getRowData(String rowKey) {
				for (ActiveContractDetail obj : retorno) {
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

	public LazyDataModel<ActiveContractDetail> getActiveContractDetailDataModel() {
		return activeContractDetailDataModel;
	}

	public void setActiveContractDetailDataModel(LazyDataModel<ActiveContractDetail> activeContractDetailDataModel) {
		this.activeContractDetailDataModel = activeContractDetailDataModel;
	}

	public MoaTypeEnum getMoaType() {
		return moaType;
	}

	public void setMoaType(MoaTypeEnum moaType) {
		this.moaType = moaType;
	}
	
}