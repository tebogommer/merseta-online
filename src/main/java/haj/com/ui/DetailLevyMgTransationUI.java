package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.LevyDetailMgPaymentsAlterAudit;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsFiles;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.LevyDetailMgPaymentsAlterAuditService;
import haj.com.service.SarsFilesService;
import haj.com.service.SarsLevyDetailsService;

/**
 * The Class DetailLevyMgTransationUI.
 */
@ManagedBean(name = "detailLevyMgTransationUI")
@ViewScoped
public class DetailLevyMgTransationUI extends AbstractUI {

	private SarsFiles sarsFile = null;
	private SarsLevyDetails sarsLevyDetails = null;

	private SarsFilesService sarsFilesService = new SarsFilesService();
	private SarsLevyDetailsService sarsLevyDetailsService = new SarsLevyDetailsService();
	private LevyDetailMgPaymentsAlterAuditService levyDetailMgPaymentsAlterAuditService = new LevyDetailMgPaymentsAlterAuditService();

	private LazyDataModel<SarsFiles> sarsFilesDataModel;
	private LazyDataModel<SarsLevyDetails> sarsLevyDetailsDataModel;
	private LazyDataModel<LevyDetailMgPaymentsAlterAudit> levyDetailMgPaymentsAlterAuditDataModel;

	/* Boolean */
	private boolean hasAccess = false;

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
		validiateAccess();
		if (hasAccess) {
			sarsFilesDataModelInfo();
			levyDetailMgPaymentsAlterAuditDataModelInfo();
		} else {
			ajaxRedirectToDashboard();
		}
	}

	private void validiateAccess() {
		hasAccess = false;
		if (getSessionUI().isAdmin()) {
			hasAccess = true;
		}else if (getSessionUI() != null && getSessionUI().getActiveUser() != null && getSessionUI().getActiveUser().getGpBankingDetails() != null && getSessionUI().getActiveUser().getGpBankingDetails()) {
			hasAccess = true;
		}
	}

	public void sarsFilesDataModelInfo() {
		sarsFilesDataModel = new LazyDataModel<SarsFiles>() {
			private static final long serialVersionUID = 1L;
			private List<SarsFiles> retorno = new ArrayList<SarsFiles>();

			@Override
			public List<SarsFiles> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "forMonth";
						sortOrder = sortOrder.DESCENDING;
					}
					retorno = sarsFilesService.allSarsFiles(SarsFiles.class, first, pageSize, sortField, sortOrder, filters);
					sarsFilesDataModel.setRowCount(sarsFilesService.count(SarsFiles.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SarsFiles obj) {
				return obj.getId();
			}

			@Override
			public SarsFiles getRowData(String rowKey) {
				for (SarsFiles obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void levyDetailMgPaymentsAlterAuditDataModelInfo() {
		levyDetailMgPaymentsAlterAuditDataModel = new LazyDataModel<LevyDetailMgPaymentsAlterAudit>() {
			private static final long serialVersionUID = 1L;
			private List<LevyDetailMgPaymentsAlterAudit> retorno = new ArrayList<>();
			@Override
			public List<LevyDetailMgPaymentsAlterAudit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = levyDetailMgPaymentsAlterAuditService.allLevyDetailMgPaymentsAlterAudit(LevyDetailMgPaymentsAlterAudit.class, first, pageSize, sortField, sortOrder, filters);
					levyDetailMgPaymentsAlterAuditDataModel.setRowCount(levyDetailMgPaymentsAlterAuditService.count(LevyDetailMgPaymentsAlterAudit.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LevyDetailMgPaymentsAlterAudit obj) {
				return obj.getId();
			}

			@Override
			public LevyDetailMgPaymentsAlterAudit getRowData(String rowKey) {
				for (LevyDetailMgPaymentsAlterAudit obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void selectSarsFile(){
		try {
			if (sarsFile.getCanProcessMgPayments()) {
				sarsLevyDetailsDataModelInfo();
				addInfoMessage("Action Complete");
			} else {
				addErrorMessage("Sars File is unable to process MG Transactions. If incorrect contact support!");
				sarsFile = null;
			}
		} catch (Exception e) {
			addInfoMessage("Action Complete");
		}
	}
	
	public void sarsLevyDetailsDataModelInfo() {
		sarsLevyDetailsDataModel = new LazyDataModel<SarsLevyDetails>() {
			private static final long serialVersionUID = 1L;
			private List<SarsLevyDetails> retorno = new ArrayList<SarsLevyDetails>();

			@Override
			public List<SarsLevyDetails> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = sarsLevyDetailsService.allSarsLevyDetailsBySarsFileId(first, pageSize, sortField, sortOrder, filters, sarsFile.getId());
					sarsLevyDetailsDataModel.setRowCount(sarsLevyDetailsService.countAllSarsLevyDetailsBySarsFileId(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SarsLevyDetails obj) {
				return obj.getId();
			}

			@Override
			public SarsLevyDetails getRowData(String rowKey) {
				for (SarsLevyDetails obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void updateSarsLevyDetail(){
		try {
			if (sarsLevyDetails == null) {
				addErrorMessage("Unable to locate information to update. Contact Support!");
			} else {
				sarsLevyDetailsService.updateMgProcess(sarsLevyDetails);
				levyDetailMgPaymentsAlterAuditService.createAudit(getSessionUI().getActiveUser(), sarsLevyDetails, sarsFile);
				sarsLevyDetails = null;
				sarsLevyDetailsDataModelInfo();
				levyDetailMgPaymentsAlterAuditDataModelInfo();
				addInfoMessage("Action Complete");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public SarsFiles getSarsFile() {
		return sarsFile;
	}

	public void setSarsFile(SarsFiles sarsFile) {
		this.sarsFile = sarsFile;
	}

	public SarsLevyDetails getSarsLevyDetails() {
		return sarsLevyDetails;
	}

	public void setSarsLevyDetails(SarsLevyDetails sarsLevyDetails) {
		this.sarsLevyDetails = sarsLevyDetails;
	}

	public LazyDataModel<SarsFiles> getSarsFilesDataModel() {
		return sarsFilesDataModel;
	}

	public void setSarsFilesDataModel(LazyDataModel<SarsFiles> sarsFilesDataModel) {
		this.sarsFilesDataModel = sarsFilesDataModel;
	}

	public LazyDataModel<SarsLevyDetails> getSarsLevyDetailsDataModel() {
		return sarsLevyDetailsDataModel;
	}

	public void setSarsLevyDetailsDataModel(LazyDataModel<SarsLevyDetails> sarsLevyDetailsDataModel) {
		this.sarsLevyDetailsDataModel = sarsLevyDetailsDataModel;
	}

	public LazyDataModel<LevyDetailMgPaymentsAlterAudit> getLevyDetailMgPaymentsAlterAuditDataModel() {
		return levyDetailMgPaymentsAlterAuditDataModel;
	}

	public void setLevyDetailMgPaymentsAlterAuditDataModel(
			LazyDataModel<LevyDetailMgPaymentsAlterAudit> levyDetailMgPaymentsAlterAuditDataModel) {
		this.levyDetailMgPaymentsAlterAuditDataModel = levyDetailMgPaymentsAlterAuditDataModel;
	}

}
