package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.NsdpSummaryReportBean;
import haj.com.entity.NsdpQuarterReporting;
import haj.com.entity.lookup.FinancialYears;
import haj.com.entity.lookup.NsdpReportConfig;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.NsdpQuarterReportingService;
import haj.com.service.lookup.FinancialYearsService;
import haj.com.service.lookup.NsdpReportConfigService;

@ManagedBean(name = "nsdpReportingUI")
@ViewScoped
public class NsdpReportingUI extends AbstractUI {
	
	/* Entity Level */
	private FinancialYears selectedFinancialYears = null;
	private FinancialYears selectedFinancialYearsForDownload = null;
	private NsdpReportConfig selectedNsdpReportConfig = null;
	private NsdpReportConfig selectedNsdpReportConfigForDownload = null;
	private NsdpQuarterReporting selectedNsdpQuarterReporting = null;
	
	/* Service Level */
	private FinancialYearsService financialYearsService = new FinancialYearsService();
	private NsdpReportConfigService nsdpReportConfigService = new NsdpReportConfigService();
	private NsdpQuarterReportingService nsdpQuarterReportingService = new NsdpQuarterReportingService();
	
	/* Lazy Data Models */
	private LazyDataModel<FinancialYears> financialYearsDataModel;
	private LazyDataModel<NsdpReportConfig> nsdpReportConfigDataModel;
	private LazyDataModel<NsdpQuarterReporting> nsdpQuarterReportingDataModel;
	
	/* Beans */
	private NsdpSummaryReportBean nsdpSummaryReportBean = null;
	
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
		selectedFinancialYearsInfo();
	}

	private void selectedFinancialYearsInfo() {
		financialYearsDataModel = new LazyDataModel<FinancialYears>() {
			private static final long serialVersionUID = 1L;
			private List<FinancialYears> retorno = new ArrayList<>();
			@Override
			public List<FinancialYears> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = financialYearsService.allFinancialYears(FinancialYears.class, first, pageSize, sortField, sortOrder, filters);
					financialYearsDataModel.setRowCount(financialYearsService.count(FinancialYears.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(FinancialYears obj) {
				return obj.getId();
			}
			@Override
			public FinancialYears getRowData(String rowKey) {
				for (FinancialYears obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void selectFinancialYear(){
		try {
			selectedNsdpReportConfig = null;
			selectedNsdpQuarterReporting = null;
			nsdpSummaryReportBean = null;
			nsdpReportConfigDataModelInfo();
			addInfoMessage("Financial Year Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void nsdpReportConfigDataModelInfo() {
		nsdpReportConfigDataModel = new LazyDataModel<NsdpReportConfig>() {
			private static final long serialVersionUID = 1L;
			private List<NsdpReportConfig> retorno = new ArrayList<>();
			@Override
			public List<NsdpReportConfig> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.isEmpty()) {
						sortField = "orderNumber";
						sortOrder = sortOrder.ASCENDING;
					}
					retorno = nsdpReportConfigService.allNsdpReportConfigByFinancialYear(first, pageSize, sortField, sortOrder, filters, selectedFinancialYears.getId());
					nsdpReportConfigDataModel.setRowCount(nsdpReportConfigService.countAllNsdpReportConfigByFinancialYear(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(NsdpReportConfig obj) {
				return obj.getId();
			}
			@Override
			public NsdpReportConfig getRowData(String rowKey) {
				for (NsdpReportConfig obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void viewReportData(){
		try {
			selectedNsdpQuarterReporting = null;
			nsdpSummaryReportBean = null;
			if (nsdpQuarterReportingService.countByNsdpReportConfigId(selectedNsdpReportConfig.getId()) == 0) {
				// generate data
				nsdpQuarterReportingService.generateForNsdpReportingConfig(selectedNsdpReportConfig, getSessionUI().getActiveUser());
			}else {
				// update count data if app 
				nsdpQuarterReportingService.updateCountsForReportDisplay(selectedNsdpReportConfig.getId());
			}
			generateConfigReport();
			addInfoMessage("Report Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepUpdateQuarterInformation(){
		try {
			runClientSideExecute("PF('updateQuarterInfoDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateQuarterInformation(){
		try {
			nsdpQuarterReportingService.createUpdateWithUserInfo(selectedNsdpQuarterReporting, getSessionUI().getActiveUser()); 
			selectedNsdpQuarterReporting = null;
			runClientSideExecute("PF('updateQuarterInfoDlg').hide()");
			generateConfigReport();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void generateConfigReport() throws Exception {
		// populate summary bean
		nsdpSummaryReportBean = nsdpQuarterReportingService.generateNsdpSummaryReportBean(selectedNsdpReportConfig);
		
		// read in quarter data
		nsdpQuarterReportingDataModelInfo();
	}

	private void nsdpQuarterReportingDataModelInfo(){
		nsdpQuarterReportingDataModel = new LazyDataModel<NsdpQuarterReporting>() {
			private static final long serialVersionUID = 1L;
			private List<NsdpQuarterReporting> retorno = new ArrayList<>();
			@Override
			public List<NsdpQuarterReporting> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = nsdpQuarterReportingService.allNsdpQuarterReportingByNsdpReportConfigId(first, pageSize, sortField, sortOrder, filters, selectedNsdpReportConfig.getId());
					nsdpQuarterReportingDataModel.setRowCount(nsdpQuarterReportingService.countAllNsdpQuarterReportingByNsdpReportConfigId(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(NsdpQuarterReporting obj) {
				return obj.getId();
			}
			@Override
			public NsdpQuarterReporting getRowData(String rowKey) {
				for (NsdpQuarterReporting obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void downloadReportByFinancialYear(){
		try {
			nsdpQuarterReportingService.downloadNsdpReportIntoExcel(selectedFinancialYearsForDownload, null, getSessionUI().getActiveUser());
			selectedFinancialYearsForDownload = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReportByConfig(){
		try {
			nsdpQuarterReportingService.downloadNsdpReportIntoExcel(null, selectedNsdpReportConfigForDownload, getSessionUI().getActiveUser());
			selectedNsdpReportConfigForDownload = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and Setters */
	public FinancialYears getSelectedFinancialYears() {
		return selectedFinancialYears;
	}

	public void setSelectedFinancialYears(FinancialYears selectedFinancialYears) {
		this.selectedFinancialYears = selectedFinancialYears;
	}

	public LazyDataModel<FinancialYears> getFinancialYearsDataModel() {
		return financialYearsDataModel;
	}

	public void setFinancialYearsDataModel(LazyDataModel<FinancialYears> financialYearsDataModel) {
		this.financialYearsDataModel = financialYearsDataModel;
	}

	public NsdpReportConfig getSelectedNsdpReportConfig() {
		return selectedNsdpReportConfig;
	}

	public void setSelectedNsdpReportConfig(NsdpReportConfig selectedNsdpReportConfig) {
		this.selectedNsdpReportConfig = selectedNsdpReportConfig;
	}

	public LazyDataModel<NsdpReportConfig> getNsdpReportConfigDataModel() {
		return nsdpReportConfigDataModel;
	}

	public void setNsdpReportConfigDataModel(LazyDataModel<NsdpReportConfig> nsdpReportConfigDataModel) {
		this.nsdpReportConfigDataModel = nsdpReportConfigDataModel;
	}

	public LazyDataModel<NsdpQuarterReporting> getNsdpQuarterReportingDataModel() {
		return nsdpQuarterReportingDataModel;
	}

	public void setNsdpQuarterReportingDataModel(LazyDataModel<NsdpQuarterReporting> nsdpQuarterReportingDataModel) {
		this.nsdpQuarterReportingDataModel = nsdpQuarterReportingDataModel;
	}

	public NsdpQuarterReporting getSelectedNsdpQuarterReporting() {
		return selectedNsdpQuarterReporting;
	}

	public void setSelectedNsdpQuarterReporting(NsdpQuarterReporting selectedNsdpQuarterReporting) {
		this.selectedNsdpQuarterReporting = selectedNsdpQuarterReporting;
	}

	public NsdpSummaryReportBean getNsdpSummaryReportBean() {
		return nsdpSummaryReportBean;
	}

	public void setNsdpSummaryReportBean(NsdpSummaryReportBean nsdpSummaryReportBean) {
		this.nsdpSummaryReportBean = nsdpSummaryReportBean;
	}

	public FinancialYears getSelectedFinancialYearsForDownload() {
		return selectedFinancialYearsForDownload;
	}

	public void setSelectedFinancialYearsForDownload(FinancialYears selectedFinancialYearsForDownload) {
		this.selectedFinancialYearsForDownload = selectedFinancialYearsForDownload;
	}

	public NsdpReportConfig getSelectedNsdpReportConfigForDownload() {
		return selectedNsdpReportConfigForDownload;
	}

	public void setSelectedNsdpReportConfigForDownload(NsdpReportConfig selectedNsdpReportConfigForDownload) {
		this.selectedNsdpReportConfigForDownload = selectedNsdpReportConfigForDownload;
	}
	
}