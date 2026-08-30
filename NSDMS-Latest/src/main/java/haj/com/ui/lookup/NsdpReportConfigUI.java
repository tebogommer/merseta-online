package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.FinancialYears;
import haj.com.entity.lookup.NsdpReportConfig;
import haj.com.service.lookup.FinancialYearsService;
import haj.com.service.lookup.NsdpReportConfigService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "nsdpreportconfigUI")
@ViewScoped
public class NsdpReportConfigUI extends AbstractUI {

	private NsdpReportConfigService service = new NsdpReportConfigService();
	private List<NsdpReportConfig> nsdpreportconfigList = null;
	private List<NsdpReportConfig> nsdpreportconfigfilteredList = null;
	private NsdpReportConfig nsdpreportconfig = null;
	private LazyDataModel<NsdpReportConfig> dataModel;
	
	private FinancialYears selectedFinancialYears = null;
	private FinancialYearsService financialYearsService = new FinancialYearsService();
	private LazyDataModel<FinancialYears> financialYearsDataModel;
	private List<NsdpReportConfig> nsdpListByFinYear = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all NsdpReportConfig and prepare a for a create
	 * of a new NsdpReportConfig
	 * 
	 * @author TechFinium
	 * @see NsdpReportConfig
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getUser().getSuperAdmin() || (getSessionUI().getUser().getNsdpReportConfig() != null && getSessionUI().getUser().getNsdpReportConfig())) {
			prepareNew();
			nsdpreportconfigInfo();
		} else {
			ajaxRedirectToDashboard();
		}
	}
	
	private void clearInfo(){
		selectedFinancialYears = null;
		nsdpListByFinYear.clear();
	}

	/**
	 * Get all NsdpReportConfig for data table
	 * 
	 * @author TechFinium
	 * @see NsdpReportConfig
	 */
	public void nsdpreportconfigInfo() {
		dataModel = new LazyDataModel<NsdpReportConfig>() {
			private static final long serialVersionUID = 1L;
			private List<NsdpReportConfig> retorno = new ArrayList<NsdpReportConfig>();
			@Override
			public List<NsdpReportConfig> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allNsdpReportConfig(NsdpReportConfig.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(NsdpReportConfig.class, filters));
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert NsdpReportConfig into database
	 * 
	 * @author TechFinium
	 * @see NsdpReportConfig
	 */
	public void nsdpreportconfigInsert() {
		try {
			service.createUpdateWithUserInfo(this.nsdpreportconfig, getSessionUI().getActiveUser());
			prepareNew();
			clearInfo();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nsdpreportconfigInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update NsdpReportConfig in database
	 * 
	 * @author TechFinium
	 * @see NsdpReportConfig
	 */
	public void nsdpreportconfigUpdate() {
		try {
			service.createUpdateWithUserInfo(this.nsdpreportconfig, getSessionUI().getActiveUser());
			clearInfo();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			nsdpreportconfigInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete NsdpReportConfig from database
	 * 
	 * @author TechFinium
	 * @see NsdpReportConfig
	 */
	public void nsdpreportconfigDelete() {
		try {
			service.delete(this.nsdpreportconfig);
			clearInfo();
			prepareNew();
			nsdpreportconfigInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of NsdpReportConfig
	 * 
	 * @author TechFinium
	 * @see NsdpReportConfig
	 */
	public void prepareNew() {
		nsdpreportconfig = new NsdpReportConfig();
		nsdpreportconfig.setManualCapture(false);
	}

	/*
	 * public List<SelectItem> getNsdpReportConfigDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * nsdpreportconfigInfo(); for (NsdpReportConfig ug : nsdpreportconfigList)
	 * { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<NsdpReportConfig> complete(String desc) {
		List<NsdpReportConfig> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void prepReorder(){
		try {
			clearInfo();
			runClientSideExecute("PF('reorderDlg').show()");
			financialYearsDataModelInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void closeReorderView(){
		try {
			clearInfo();
			prepareNew();
			nsdpreportconfigInfo();
			runClientSideExecute("PF('reorderDlg').hide()");
			addWarningMessage("View Closed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void financialYearsDataModelInfo() {
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void selectfinancialYearsForOrderUpdate() {
		try {
			nsdpListByFinYear = service.findByFinancialYearsIdWithOrderBy(selectedFinancialYears.getId());
			if (nsdpListByFinYear.isEmpty()) {
				addErrorMessage("No configured data found for selected year");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateOrderListOnAction(){
		try {
			service.updateOrderForConfig(nsdpListByFinYear, getSessionUI().getActiveUser());
			addInfoMessage("Update Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateOrderList(){
		try {
			service.updateOrderForConfig(nsdpListByFinYear, getSessionUI().getActiveUser());
			runClientSideExecute("PF('reorderDlg').hide()");
			clearInfo();
			prepareNew();
			nsdpreportconfigInfo();
			addInfoMessage("Update Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<NsdpReportConfig> getNsdpReportConfigList() {
		return nsdpreportconfigList;
	}

	public void setNsdpReportConfigList(List<NsdpReportConfig> nsdpreportconfigList) {
		this.nsdpreportconfigList = nsdpreportconfigList;
	}

	public NsdpReportConfig getNsdpreportconfig() {
		return nsdpreportconfig;
	}

	public void setNsdpreportconfig(NsdpReportConfig nsdpreportconfig) {
		this.nsdpreportconfig = nsdpreportconfig;
	}

	public List<NsdpReportConfig> getNsdpReportConfigfilteredList() {
		return nsdpreportconfigfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param nsdpreportconfigfilteredList
	 *            the new nsdpreportconfigfilteredList list
	 * @see NsdpReportConfig
	 */
	public void setNsdpReportConfigfilteredList(List<NsdpReportConfig> nsdpreportconfigfilteredList) {
		this.nsdpreportconfigfilteredList = nsdpreportconfigfilteredList;
	}

	public LazyDataModel<NsdpReportConfig> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<NsdpReportConfig> dataModel) {
		this.dataModel = dataModel;
	}

	public List<NsdpReportConfig> getNsdpListByFinYear() {
		return nsdpListByFinYear;
	}

	public void setNsdpListByFinYear(List<NsdpReportConfig> nsdpListByFinYear) {
		this.nsdpListByFinYear = nsdpListByFinYear;
	}

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

}
