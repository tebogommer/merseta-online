package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyOrganisationSitesService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyorganisationsitesUI")
@ViewScoped
public class LegacyOrganisationSitesUI extends AbstractUI {

	private LegacyOrganisationSitesService service = new LegacyOrganisationSitesService();
	private List<LegacyOrganisationSites> legacyorganisationsitesList = null;
	private List<LegacyOrganisationSites> legacyorganisationsitesfilteredList = null;
	private LegacyOrganisationSites legacyorganisationsites = null;
	private LazyDataModel<LegacyOrganisationSites> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;
	private boolean displayDownload = false;
	
	/* Reporting START */
	private int totalProcessed;
	private int totalNotProcessed;
	
	private int nonDuplicateSiteNumber;
	private int duplicateSiteNumber;
	private boolean duplicateSiteNumberDownloadDisplay = false;
	private LazyDataModel<LegacyOrganisationSites> duplicateSiteNumberDataModel;
	
	private int mainSdlNumberOnSars;
	private int mainSdlNumberNotOnSars;
	private boolean mainSdlNumberNotOnSarsDownloadDisplay = false;
	private LazyDataModel<LegacyOrganisationSites> mainSdlNumberNotOnSarsDataModel;
	
	private int linkedSdlNumberOnSars;
	private int linkedSdlNumberNotOnSars;
	private boolean linkedSdlNumberNotOnSarsDownloadDisplay = false;
	private LazyDataModel<LegacyOrganisationSites> linkedSdlNumberNotOnSarsDataModel;
	
	private int notLinkedToCompany;
	private boolean notLinkedToCompanyDownloadDisplay = false;
	private LazyDataModel<LegacyOrganisationSites> notLinkedToCompanyDataModel;
	/* Reporting END */

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
	 * Initialize method to get all LegacyOrganisationSites and prepare a for a
	 * create of a new LegacyOrganisationSites
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationSites
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyorganisationsitesInfo();
		countAllEntries();
		runReporting();
	}

	/**
	 * Get all LegacyOrganisationSites for data table
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationSites
	 */
	public void legacyorganisationsitesInfo() {
		dataModel = new LazyDataModel<LegacyOrganisationSites>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationSites> retorno = new ArrayList<LegacyOrganisationSites>();

			@Override
			public List<LegacyOrganisationSites> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allLegacyOrganisationSites(LegacyOrganisationSites.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyOrganisationSites.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationSites obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationSites getRowData(String rowKey) {
				for (LegacyOrganisationSites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert LegacyOrganisationSites into database
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationSites
	 */
	public void legacyorganisationsitesInsert() {
		try {
			service.create(this.legacyorganisationsites);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyorganisationsitesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyOrganisationSites in database
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationSites
	 */
	public void legacyorganisationsitesUpdate() {
		try {
			service.update(this.legacyorganisationsites);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyorganisationsitesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyOrganisationSites from database
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationSites
	 */
	public void legacyorganisationsitesDelete() {
		try {
			service.delete(this.legacyorganisationsites);
			prepareNew();
			legacyorganisationsitesInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyOrganisationSites
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationSites
	 */
	public void prepareNew() {
		legacyorganisationsites = new LegacyOrganisationSites();
	}



	public void countAllEntries() {
		try {
			displayDownload = false;
			int entriesCount = service.countAllResults();
			if (entriesCount < 65000) {
				displayDownload = true;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepTypeSelection() {
		try {
			csvTypeSelectionList = new ArrayList<>();
			csvTypeSelectionList.add(",");
			csvTypeSelectionList.add(";");
			csvTypeSelectionList.add("|");
			csvTypeSelectionList.add("-");
			csvTypeSelection = ";";
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			if (csvTypeSelection == null || csvTypeSelection.isEmpty()) {
				csvTypeSelection = ",";
			}
			logger.info("Starting file upload");
			List<LegacyOrganisationSites> csvDataList = csvUtil.getObjects(LegacyOrganisationSites.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyorganisationsitesInfo();
			countAllEntries();
			runReporting();
		} catch (ConstraintViolationException e) {
			addErrorMessage("ConstraintViolationException");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} finally {
			csvUtil = new CSVUtil();
		}
	}

	public void clearUploadedEntries() {
		try {
			service.deleteUploadedEntries();
			addInfoMessage("data cleared");
			legacyorganisationsitesInfo();
			countAllEntries();
			runReporting();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void checkSdlNumberAgaintSarsEmployerProfile() {
		try {
			service.checkSdlNumberAgaintSarsEmployerProfile();
			addInfoMessage("Action Complete");
			runReporting();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runReporting() throws Exception {
		runProcessedReporting();
		runDuplicationReport();
		runMainSdlOnSarsReport();
		runLinkedSdlOnSarsReport();
		runNotLinkedToCompanyReport();
	}

	private void runProcessedReporting() throws Exception{
		totalProcessed = service.countAllLegacyOrganisationSitesByProcessedValue(true);
		totalNotProcessed = service.countAllLegacyOrganisationSitesByProcessedValue(false);
	}
	
	private void runDuplicationReport() {
		// counts
		try {
			nonDuplicateSiteNumber = service.countAllLegacyOrganisationSitesDuplicateSiteNumbers(false);
			duplicateSiteNumber = service.countAllLegacyOrganisationSitesDuplicateSiteNumbers(true);
			if (duplicateSiteNumber < 65000) {
				duplicateSiteNumberDownloadDisplay = true;
			} else {
				duplicateSiteNumberDownloadDisplay = false;
			}
		} catch (Exception e) {
			duplicateSiteNumberDownloadDisplay = false;
			nonDuplicateSiteNumber = 0;
			duplicateSiteNumber = 0;
		}
		// lazy load
		duplicateSiteNumberDataModel = new LazyDataModel<LegacyOrganisationSites>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationSites> retorno = new ArrayList<LegacyOrganisationSites>();
			@Override
			public List<LegacyOrganisationSites> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyOrganisationSitesDuplicateSiteNumbers(first, pageSize, sortField, sortOrder, filters, true);
					duplicateSiteNumberDataModel.setRowCount(service.countAllLegacyOrganisationSitesDuplicateSiteNumbers(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyOrganisationSites obj) {
				return obj.getId();
			}
			@Override
			public LegacyOrganisationSites getRowData(String rowKey) {
				for (LegacyOrganisationSites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void runMainSdlOnSarsReport() {
		// counts
		try {
			mainSdlNumberOnSars = service.countAllLegacyOrganisationSitesMainSdlNumberOnSarsEmployerFileByValue(true);
			mainSdlNumberNotOnSars = service.countAllLegacyOrganisationSitesMainSdlNumberOnSarsEmployerFileByValue(false);
			if (mainSdlNumberNotOnSars < 65000) {
				mainSdlNumberNotOnSarsDownloadDisplay = true;
			} else {
				mainSdlNumberNotOnSarsDownloadDisplay = false;
			}
		} catch (Exception e) {
			mainSdlNumberNotOnSarsDownloadDisplay = false;
			mainSdlNumberOnSars = 0;
			mainSdlNumberNotOnSars = 0;
		}
		// lazy load
		mainSdlNumberNotOnSarsDataModel = new LazyDataModel<LegacyOrganisationSites>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationSites> retorno = new ArrayList<LegacyOrganisationSites>();
			@Override
			public List<LegacyOrganisationSites> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyOrganisationSitesMainSdlNumberOnSarsEmployerFileByValue(first, pageSize, sortField, sortOrder, filters, false);
					mainSdlNumberNotOnSarsDataModel.setRowCount(service.countAllLegacyOrganisationSitesMainSdlNumberOnSarsEmployerFileByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyOrganisationSites obj) {
				return obj.getId();
			}
			@Override
			public LegacyOrganisationSites getRowData(String rowKey) {
				for (LegacyOrganisationSites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void runLinkedSdlOnSarsReport() {
		// counts
		try {
			linkedSdlNumberOnSars = service.countAllLegacyOrganisationSitesLinkedSdlNumberOnSarsEmployerFileByValue(true);
			linkedSdlNumberNotOnSars = service.countAllLegacyOrganisationSitesLinkedSdlNumberOnSarsEmployerFileByValue(false);
			if (linkedSdlNumberNotOnSars < 65000) {
				linkedSdlNumberNotOnSarsDownloadDisplay = true;
			} else {
				linkedSdlNumberNotOnSarsDownloadDisplay = false;
			}
		} catch (Exception e) {
			linkedSdlNumberNotOnSarsDownloadDisplay = false;
			linkedSdlNumberOnSars = 0;
			linkedSdlNumberNotOnSars = 0;
		}
		// lazy load
		linkedSdlNumberNotOnSarsDataModel = new LazyDataModel<LegacyOrganisationSites>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationSites> retorno = new ArrayList<LegacyOrganisationSites>();
			@Override
			public List<LegacyOrganisationSites> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyOrganisationSitesLinkedSdlNumberOnSarsEmployerFileByValue(first, pageSize, sortField, sortOrder, filters, false);
					linkedSdlNumberNotOnSarsDataModel.setRowCount(service.countAllLegacyOrganisationSitesLinkedSdlNumberOnSarsEmployerFileByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyOrganisationSites obj) {
				return obj.getId();
			}
			@Override
			public LegacyOrganisationSites getRowData(String rowKey) {
				for (LegacyOrganisationSites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void runNotLinkedToCompanyReport() {
		// counts
		try {
			notLinkedToCompany = service.countAllLegacyOrganisationSitesNotLinkedToCompany();
			if (notLinkedToCompany < 65000) {
				notLinkedToCompanyDownloadDisplay = true;
			} else {
				notLinkedToCompanyDownloadDisplay = false;
			}
		} catch (Exception e) {
			notLinkedToCompanyDownloadDisplay = false;
			notLinkedToCompany = 0;
		}
		
		// lazy load
		notLinkedToCompanyDataModel = new LazyDataModel<LegacyOrganisationSites>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationSites> retorno = new ArrayList<LegacyOrganisationSites>();
			@Override
			public List<LegacyOrganisationSites> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyOrganisationSitesNotLinkedToCompany(first, pageSize, sortField, sortOrder, filters);
					notLinkedToCompanyDataModel.setRowCount(service.countAllLegacyOrganisationSitesNotLinkedToCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyOrganisationSites obj) {
				return obj.getId();
			}
			@Override
			public LegacyOrganisationSites getRowData(String rowKey) {
				for (LegacyOrganisationSites obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/* getters and setters */
	public List<LegacyOrganisationSites> getLegacyOrganisationSitesList() {
		return legacyorganisationsitesList;
	}

	public void setLegacyOrganisationSitesList(List<LegacyOrganisationSites> legacyorganisationsitesList) {
		this.legacyorganisationsitesList = legacyorganisationsitesList;
	}

	public LegacyOrganisationSites getLegacyorganisationsites() {
		return legacyorganisationsites;
	}

	public void setLegacyorganisationsites(LegacyOrganisationSites legacyorganisationsites) {
		this.legacyorganisationsites = legacyorganisationsites;
	}

	public List<LegacyOrganisationSites> getLegacyOrganisationSitesfilteredList() {
		return legacyorganisationsitesfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyorganisationsitesfilteredList
	 *            the new legacyorganisationsitesfilteredList list
	 * @see LegacyOrganisationSites
	 */
	public void setLegacyOrganisationSitesfilteredList(
			List<LegacyOrganisationSites> legacyorganisationsitesfilteredList) {
		this.legacyorganisationsitesfilteredList = legacyorganisationsitesfilteredList;
	}

	public LazyDataModel<LegacyOrganisationSites> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyOrganisationSites> dataModel) {
		this.dataModel = dataModel;
	}

	public List<String> getCsvTypeSelectionList() {
		return csvTypeSelectionList;
	}

	public void setCsvTypeSelectionList(List<String> csvTypeSelectionList) {
		this.csvTypeSelectionList = csvTypeSelectionList;
	}

	public String getCsvTypeSelection() {
		return csvTypeSelection;
	}

	public void setCsvTypeSelection(String csvTypeSelection) {
		this.csvTypeSelection = csvTypeSelection;
	}

	public boolean isDisplayDownload() {
		return displayDownload;
	}

	public void setDisplayDownload(boolean displayDownload) {
		this.displayDownload = displayDownload;
	}

	public List<LegacyOrganisationSites> getLegacyorganisationsitesList() {
		return legacyorganisationsitesList;
	}

	public void setLegacyorganisationsitesList(List<LegacyOrganisationSites> legacyorganisationsitesList) {
		this.legacyorganisationsitesList = legacyorganisationsitesList;
	}

	public List<LegacyOrganisationSites> getLegacyorganisationsitesfilteredList() {
		return legacyorganisationsitesfilteredList;
	}

	public void setLegacyorganisationsitesfilteredList(List<LegacyOrganisationSites> legacyorganisationsitesfilteredList) {
		this.legacyorganisationsitesfilteredList = legacyorganisationsitesfilteredList;
	}

	public int getTotalProcessed() {
		return totalProcessed;
	}

	public void setTotalProcessed(int totalProcessed) {
		this.totalProcessed = totalProcessed;
	}

	public int getTotalNotProcessed() {
		return totalNotProcessed;
	}

	public void setTotalNotProcessed(int totalNotProcessed) {
		this.totalNotProcessed = totalNotProcessed;
	}

	public int getNonDuplicateSiteNumber() {
		return nonDuplicateSiteNumber;
	}

	public void setNonDuplicateSiteNumber(int nonDuplicateSiteNumber) {
		this.nonDuplicateSiteNumber = nonDuplicateSiteNumber;
	}

	public int getDuplicateSiteNumber() {
		return duplicateSiteNumber;
	}

	public void setDuplicateSiteNumber(int duplicateSiteNumber) {
		this.duplicateSiteNumber = duplicateSiteNumber;
	}

	public LazyDataModel<LegacyOrganisationSites> getDuplicateSiteNumberDataModel() {
		return duplicateSiteNumberDataModel;
	}

	public void setDuplicateSiteNumberDataModel(LazyDataModel<LegacyOrganisationSites> duplicateSiteNumberDataModel) {
		this.duplicateSiteNumberDataModel = duplicateSiteNumberDataModel;
	}

	public int getMainSdlNumberOnSars() {
		return mainSdlNumberOnSars;
	}

	public void setMainSdlNumberOnSars(int mainSdlNumberOnSars) {
		this.mainSdlNumberOnSars = mainSdlNumberOnSars;
	}

	public int getMainSdlNumberNotOnSars() {
		return mainSdlNumberNotOnSars;
	}

	public void setMainSdlNumberNotOnSars(int mainSdlNumberNotOnSars) {
		this.mainSdlNumberNotOnSars = mainSdlNumberNotOnSars;
	}

	public LazyDataModel<LegacyOrganisationSites> getMainSdlNumberNotOnSarsDataModel() {
		return mainSdlNumberNotOnSarsDataModel;
	}

	public void setMainSdlNumberNotOnSarsDataModel(LazyDataModel<LegacyOrganisationSites> mainSdlNumberNotOnSarsDataModel) {
		this.mainSdlNumberNotOnSarsDataModel = mainSdlNumberNotOnSarsDataModel;
	}

	public int getLinkedSdlNumberOnSars() {
		return linkedSdlNumberOnSars;
	}

	public void setLinkedSdlNumberOnSars(int linkedSdlNumberOnSars) {
		this.linkedSdlNumberOnSars = linkedSdlNumberOnSars;
	}

	public int getLinkedSdlNumberNotOnSars() {
		return linkedSdlNumberNotOnSars;
	}

	public void setLinkedSdlNumberNotOnSars(int linkedSdlNumberNotOnSars) {
		this.linkedSdlNumberNotOnSars = linkedSdlNumberNotOnSars;
	}

	public LazyDataModel<LegacyOrganisationSites> getLinkedSdlNumberNotOnSarsDataModel() {
		return linkedSdlNumberNotOnSarsDataModel;
	}

	public void setLinkedSdlNumberNotOnSarsDataModel(
			LazyDataModel<LegacyOrganisationSites> linkedSdlNumberNotOnSarsDataModel) {
		this.linkedSdlNumberNotOnSarsDataModel = linkedSdlNumberNotOnSarsDataModel;
	}

	public int getNotLinkedToCompany() {
		return notLinkedToCompany;
	}

	public void setNotLinkedToCompany(int notLinkedToCompany) {
		this.notLinkedToCompany = notLinkedToCompany;
	}

	public LazyDataModel<LegacyOrganisationSites> getNotLinkedToCompanyDataModel() {
		return notLinkedToCompanyDataModel;
	}

	public void setNotLinkedToCompanyDataModel(LazyDataModel<LegacyOrganisationSites> notLinkedToCompanyDataModel) {
		this.notLinkedToCompanyDataModel = notLinkedToCompanyDataModel;
	}

	public boolean isDuplicateSiteNumberDownloadDisplay() {
		return duplicateSiteNumberDownloadDisplay;
	}

	public void setDuplicateSiteNumberDownloadDisplay(boolean duplicateSiteNumberDownloadDisplay) {
		this.duplicateSiteNumberDownloadDisplay = duplicateSiteNumberDownloadDisplay;
	}

	public boolean isMainSdlNumberNotOnSarsDownloadDisplay() {
		return mainSdlNumberNotOnSarsDownloadDisplay;
	}

	public void setMainSdlNumberNotOnSarsDownloadDisplay(boolean mainSdlNumberNotOnSarsDownloadDisplay) {
		this.mainSdlNumberNotOnSarsDownloadDisplay = mainSdlNumberNotOnSarsDownloadDisplay;
	}

	public boolean isLinkedSdlNumberNotOnSarsDownloadDisplay() {
		return linkedSdlNumberNotOnSarsDownloadDisplay;
	}

	public void setLinkedSdlNumberNotOnSarsDownloadDisplay(boolean linkedSdlNumberNotOnSarsDownloadDisplay) {
		this.linkedSdlNumberNotOnSarsDownloadDisplay = linkedSdlNumberNotOnSarsDownloadDisplay;
	}

	public boolean isNotLinkedToCompanyDownloadDisplay() {
		return notLinkedToCompanyDownloadDisplay;
	}

	public void setNotLinkedToCompanyDownloadDisplay(boolean notLinkedToCompanyDownloadDisplay) {
		this.notLinkedToCompanyDownloadDisplay = notLinkedToCompanyDownloadDisplay;
	}

}
