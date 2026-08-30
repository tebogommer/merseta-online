package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.LegacyOrganisationNonLevyPaying;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.service.lookup.LegacyOrganisationNonLevyPayingService;
import haj.com.utils.CSVUtil;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyorganisationnonlevypayingUI")
@ViewScoped
public class LegacyOrganisationNonLevyPayingUI extends AbstractUI {

	private LegacyOrganisationNonLevyPayingService service = new LegacyOrganisationNonLevyPayingService();
	private List<LegacyOrganisationNonLevyPaying> legacyorganisationnonlevypayingList = null;
	private List<LegacyOrganisationNonLevyPaying> legacyorganisationnonlevypayingfilteredList = null;
	private LegacyOrganisationNonLevyPaying legacyorganisationnonlevypaying = null;
	private LazyDataModel<LegacyOrganisationNonLevyPaying> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;
	private boolean displayDownload = false;
	
	/* Business Rules One - SDL number on SARS file */
	// empty / unable to process
	private boolean displayDownloadSdlNumberOnSarsEmployerFileEmpty = false;
	private Integer countSdlNumberOnSarsEmployerFileEmpty;
	private LazyDataModel<LegacyOrganisationNonLevyPaying> sdlNumberOnSarsEmployerFileEmptyDataModel;

	// on SARS employer file - true
	private boolean displayDownloadSdlNumberOnSarsEmployerFile = false;
	private Integer countSdlNumberOnSarsEmployerFile;
	private LazyDataModel<LegacyOrganisationNonLevyPaying> sdlNumberOnSarsEmployerFileDataModel;

	// not on SARS employer file - false
	private boolean displayDownloadSdlNumberNotOnSarsEmployerFile = false;
	private Integer countSdlNumberNotOnSarsEmployerFile;
	private LazyDataModel<LegacyOrganisationNonLevyPaying> sdlNumberNotOnSarsEmployerFileDataModel;

	/* Business Rules Two - Main SDL number on SARS file */
	// empty / unable to process
	private boolean displayDownloadMainSdlNumberOnSarsEmployerFileEmpty = false;
	private Integer countMainSdlNumberOnSarsEmployerFileEmpty;
	private LazyDataModel<LegacyOrganisationNonLevyPaying> mainSdlNumberOnSarsEmployerFileEmptyDataModel;

	// on SARS employer file - true
	private boolean displayDownloadMainSdlNumberOnSarsEmployerFile = false;
	private Integer countMainSdlNumberOnSarsEmployerFile;
	private LazyDataModel<LegacyOrganisationNonLevyPaying> mainSdlNumberOnSarsEmployerFileDataModel;

	// not on SARS employer file - false
	private boolean displayDownloadMainSdlNumberNotOnSarsEmployerFile = false;
	private Integer countMainSdlNumberNotOnSarsEmployerFile;
	private LazyDataModel<LegacyOrganisationNonLevyPaying> mainSdlNumberNotOnSarsEmployerFileDataModel;

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
	 * Initialize method to get all LegacyOrganisationNonLevyPaying and prepare
	 * a for a create of a new LegacyOrganisationNonLevyPaying
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationNonLevyPaying
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyorganisationnonlevypayingInfo();
		countAllEntries();
		runSdlDataTables();
	}

	/**
	 * Get all LegacyOrganisationNonLevyPaying for data table
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationNonLevyPaying
	 */
	public void legacyorganisationnonlevypayingInfo() {

		dataModel = new LazyDataModel<LegacyOrganisationNonLevyPaying>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationNonLevyPaying> retorno = new ArrayList<LegacyOrganisationNonLevyPaying>();

			@Override
			public List<LegacyOrganisationNonLevyPaying> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyOrganisationNonLevyPaying(LegacyOrganisationNonLevyPaying.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyOrganisationNonLevyPaying.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationNonLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationNonLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationNonLevyPaying obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyOrganisationNonLevyPaying into database
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationNonLevyPaying
	 */
	public void legacyorganisationnonlevypayingInsert() {
		try {
			service.create(this.legacyorganisationnonlevypaying);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyorganisationnonlevypayingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyOrganisationNonLevyPaying in database
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationNonLevyPaying
	 */
	public void legacyorganisationnonlevypayingUpdate() {
		try {
			service.update(this.legacyorganisationnonlevypaying);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyorganisationnonlevypayingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyOrganisationNonLevyPaying from database
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationNonLevyPaying
	 */
	public void legacyorganisationnonlevypayingDelete() {
		try {
			service.delete(this.legacyorganisationnonlevypaying);
			prepareNew();
			legacyorganisationnonlevypayingInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyOrganisationNonLevyPaying
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationNonLevyPaying
	 */
	public void prepareNew() {
		legacyorganisationnonlevypaying = new LegacyOrganisationNonLevyPaying();
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyOrganisationNonLevyPaying> complete(String desc) {
		List<LegacyOrganisationNonLevyPaying> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void checkSdlNumberAgaintSarsEmployerProfile(){
		try {
			service.checkSdlNumberAgaintSarsEmployerProfile();
			addInfoMessage("Action Complete");
			runSdlDataTables();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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
			List<LegacyOrganisationNonLevyPaying> csvDataList = csvUtil.getObjects( LegacyOrganisationNonLevyPaying.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyorganisationnonlevypayingInfo();
			countAllEntries();
			runSdlDataTables();
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
			legacyorganisationnonlevypayingInfo();
			countAllEntries();
			runSdlDataTables();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	/* Business Rule One - SDL number on Sars file */
	public void runSdlDataTables() throws Exception {
//		sdlNumberOnSarsEmployerFileEmptyDataModelInfo();
//		sdlNumberOnSarsEmployerFileDataModelInfo();
//		sdlNumberNotOnSarsEmployerFileDataModelInfo();
//		mainSdlNumberOnSarsEmployerFileEmptyDataModelInfo();
//		mainSdlNumberOnSarsEmployerFileDataModelInfo();
//		mainSdlNumberNotOnSarsEmployerFileDataModelInfo();
	}

	public void sdlNumberOnSarsEmployerFileEmptyDataModelInfo() {
		try {
			countSdlNumberOnSarsEmployerFileEmpty = service.countAllSdlNumberOnSarsEmployerFileEmpty();
			if (countSdlNumberOnSarsEmployerFileEmpty < 65000) {
				displayDownloadSdlNumberOnSarsEmployerFileEmpty = true;
			} else {
				displayDownloadSdlNumberOnSarsEmployerFileEmpty = false;
			}
		} catch (Exception e) {
			displayDownloadSdlNumberOnSarsEmployerFileEmpty = false;
			countSdlNumberOnSarsEmployerFileEmpty = 0;
		}
		sdlNumberOnSarsEmployerFileEmptyDataModel = new LazyDataModel<LegacyOrganisationNonLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationNonLevyPaying> retorno = new ArrayList<LegacyOrganisationNonLevyPaying>();

			@Override
			public List<LegacyOrganisationNonLevyPaying> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSdlNumberOnSarsEmployerFileEmpty(first, pageSize, sortField, sortOrder, filters);
					sdlNumberOnSarsEmployerFileEmptyDataModel.setRowCount(service.countAllSdlNumberOnSarsEmployerFileEmpty(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationNonLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationNonLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationNonLevyPaying obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void sdlNumberOnSarsEmployerFileDataModelInfo() {
		try {
			countSdlNumberOnSarsEmployerFile = service.countAllSdlNumberOnSarsEmployerFileByValue(true);
			if (countSdlNumberOnSarsEmployerFile < 65000) {
				displayDownloadSdlNumberOnSarsEmployerFile = true;
			} else {
				displayDownloadSdlNumberOnSarsEmployerFile = false;
			}
		} catch (Exception e) {
			displayDownloadSdlNumberOnSarsEmployerFile = false;
			countSdlNumberOnSarsEmployerFile = 0;
		}
		sdlNumberOnSarsEmployerFileDataModel = new LazyDataModel<LegacyOrganisationNonLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationNonLevyPaying> retorno = new ArrayList<LegacyOrganisationNonLevyPaying>();

			@Override
			public List<LegacyOrganisationNonLevyPaying> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSdlNumberOnSarsEmployerFileByValue(first, pageSize, sortField, sortOrder,
							filters, true);
					sdlNumberOnSarsEmployerFileDataModel
							.setRowCount(service.countAllSdlNumberOnSarsEmployerFileByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationNonLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationNonLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationNonLevyPaying obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void sdlNumberNotOnSarsEmployerFileDataModelInfo() {
		try {
			countSdlNumberNotOnSarsEmployerFile = service.countAllSdlNumberOnSarsEmployerFileByValue(false);
			if (countSdlNumberNotOnSarsEmployerFile < 65000) {
				displayDownloadSdlNumberNotOnSarsEmployerFile = true;
			} else {
				displayDownloadSdlNumberNotOnSarsEmployerFile = false;
			}
		} catch (Exception e) {
			displayDownloadSdlNumberNotOnSarsEmployerFile = false;
			countSdlNumberNotOnSarsEmployerFile = 0;
		}
		sdlNumberNotOnSarsEmployerFileDataModel = new LazyDataModel<LegacyOrganisationNonLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationNonLevyPaying> retorno = new ArrayList<LegacyOrganisationNonLevyPaying>();

			@Override
			public List<LegacyOrganisationNonLevyPaying> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSdlNumberOnSarsEmployerFileByValue(first, pageSize, sortField, sortOrder,
							filters, false);
					sdlNumberNotOnSarsEmployerFileDataModel
							.setRowCount(service.countAllSdlNumberOnSarsEmployerFileByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationNonLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationNonLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationNonLevyPaying obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void mainSdlNumberOnSarsEmployerFileEmptyDataModelInfo() {
		try {
			countMainSdlNumberOnSarsEmployerFileEmpty = service.countAllMainSdlNumberOnSarsEmployerFileEmpty();
			if (countMainSdlNumberOnSarsEmployerFileEmpty < 65000) {
				displayDownloadMainSdlNumberOnSarsEmployerFileEmpty = true;
			} else {
				displayDownloadMainSdlNumberOnSarsEmployerFileEmpty = false;
			}
		} catch (Exception e) {
			displayDownloadMainSdlNumberOnSarsEmployerFileEmpty = false;
			countMainSdlNumberOnSarsEmployerFileEmpty = 0;
		}
		mainSdlNumberOnSarsEmployerFileEmptyDataModel = new LazyDataModel<LegacyOrganisationNonLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationNonLevyPaying> retorno = new ArrayList<LegacyOrganisationNonLevyPaying>();

			@Override
			public List<LegacyOrganisationNonLevyPaying> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allMainSdlNumberOnSarsEmployerFileEmpty(first, pageSize, sortField, sortOrder,filters);
					mainSdlNumberOnSarsEmployerFileEmptyDataModel.setRowCount(service.countAllMainSdlNumberOnSarsEmployerFileEmpty(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationNonLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationNonLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationNonLevyPaying obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void mainSdlNumberOnSarsEmployerFileDataModelInfo() {
		try {
			countMainSdlNumberOnSarsEmployerFile = service.countAllMainSdlNumberOnSarsEmployerFileValue(true);
			if (countMainSdlNumberOnSarsEmployerFile < 65000) {
				displayDownloadMainSdlNumberOnSarsEmployerFile = true;
			} else {
				displayDownloadMainSdlNumberOnSarsEmployerFile = false;
			}
		} catch (Exception e) {
			displayDownloadMainSdlNumberOnSarsEmployerFile = false;
			countMainSdlNumberOnSarsEmployerFile = 0;
		}
		mainSdlNumberOnSarsEmployerFileDataModel = new LazyDataModel<LegacyOrganisationNonLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationNonLevyPaying> retorno = new ArrayList<LegacyOrganisationNonLevyPaying>();

			@Override
			public List<LegacyOrganisationNonLevyPaying> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allMainSdlNumberOnSarsEmployerFileByValue(first, pageSize, sortField, sortOrder,filters, true);
					mainSdlNumberOnSarsEmployerFileDataModel.setRowCount(service.countAllMainSdlNumberOnSarsEmployerFileByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationNonLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationNonLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationNonLevyPaying obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public void mainSdlNumberNotOnSarsEmployerFileDataModelInfo() {
		try {
			countMainSdlNumberNotOnSarsEmployerFile = service.countAllMainSdlNumberOnSarsEmployerFileValue(false);
			if (countMainSdlNumberNotOnSarsEmployerFile < 65000) {
				displayDownloadMainSdlNumberNotOnSarsEmployerFile = true;
			} else {
				displayDownloadMainSdlNumberNotOnSarsEmployerFile = false;
			}
		} catch (Exception e) {
			displayDownloadMainSdlNumberNotOnSarsEmployerFile = false;
			countMainSdlNumberNotOnSarsEmployerFile = 0;
		}
		mainSdlNumberNotOnSarsEmployerFileDataModel = new LazyDataModel<LegacyOrganisationNonLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationNonLevyPaying> retorno = new ArrayList<LegacyOrganisationNonLevyPaying>();

			@Override
			public List<LegacyOrganisationNonLevyPaying> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allMainSdlNumberOnSarsEmployerFileByValue(first, pageSize, sortField, sortOrder, filters, false);
					mainSdlNumberNotOnSarsEmployerFileDataModel.setRowCount(service.countAllMainSdlNumberOnSarsEmployerFileByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationNonLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationNonLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationNonLevyPaying obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	/* getters and setters */
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

	public List<LegacyOrganisationNonLevyPaying> getLegacyOrganisationNonLevyPayingList() {
		return legacyorganisationnonlevypayingList;
	}

	public void setLegacyOrganisationNonLevyPayingList(
			List<LegacyOrganisationNonLevyPaying> legacyorganisationnonlevypayingList) {
		this.legacyorganisationnonlevypayingList = legacyorganisationnonlevypayingList;
	}

	public LegacyOrganisationNonLevyPaying getLegacyorganisationnonlevypaying() {
		return legacyorganisationnonlevypaying;
	}

	public void setLegacyorganisationnonlevypaying(LegacyOrganisationNonLevyPaying legacyorganisationnonlevypaying) {
		this.legacyorganisationnonlevypaying = legacyorganisationnonlevypaying;
	}

	public List<LegacyOrganisationNonLevyPaying> getLegacyOrganisationNonLevyPayingfilteredList() {
		return legacyorganisationnonlevypayingfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyorganisationnonlevypayingfilteredList
	 *            the new legacyorganisationnonlevypayingfilteredList list
	 * @see LegacyOrganisationNonLevyPaying
	 */
	public void setLegacyOrganisationNonLevyPayingfilteredList(
			List<LegacyOrganisationNonLevyPaying> legacyorganisationnonlevypayingfilteredList) {
		this.legacyorganisationnonlevypayingfilteredList = legacyorganisationnonlevypayingfilteredList;
	}

	public LazyDataModel<LegacyOrganisationNonLevyPaying> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyOrganisationNonLevyPaying> dataModel) {
		this.dataModel = dataModel;
	}

	public List<LegacyOrganisationNonLevyPaying> getLegacyorganisationnonlevypayingList() {
		return legacyorganisationnonlevypayingList;
	}

	public void setLegacyorganisationnonlevypayingList(
			List<LegacyOrganisationNonLevyPaying> legacyorganisationnonlevypayingList) {
		this.legacyorganisationnonlevypayingList = legacyorganisationnonlevypayingList;
	}

	public List<LegacyOrganisationNonLevyPaying> getLegacyorganisationnonlevypayingfilteredList() {
		return legacyorganisationnonlevypayingfilteredList;
	}

	public void setLegacyorganisationnonlevypayingfilteredList(
			List<LegacyOrganisationNonLevyPaying> legacyorganisationnonlevypayingfilteredList) {
		this.legacyorganisationnonlevypayingfilteredList = legacyorganisationnonlevypayingfilteredList;
	}

	public boolean isDisplayDownloadSdlNumberOnSarsEmployerFileEmpty() {
		return displayDownloadSdlNumberOnSarsEmployerFileEmpty;
	}

	public void setDisplayDownloadSdlNumberOnSarsEmployerFileEmpty(
			boolean displayDownloadSdlNumberOnSarsEmployerFileEmpty) {
		this.displayDownloadSdlNumberOnSarsEmployerFileEmpty = displayDownloadSdlNumberOnSarsEmployerFileEmpty;
	}

	public Integer getCountSdlNumberOnSarsEmployerFileEmpty() {
		return countSdlNumberOnSarsEmployerFileEmpty;
	}

	public void setCountSdlNumberOnSarsEmployerFileEmpty(Integer countSdlNumberOnSarsEmployerFileEmpty) {
		this.countSdlNumberOnSarsEmployerFileEmpty = countSdlNumberOnSarsEmployerFileEmpty;
	}

	public LazyDataModel<LegacyOrganisationNonLevyPaying> getSdlNumberOnSarsEmployerFileEmptyDataModel() {
		return sdlNumberOnSarsEmployerFileEmptyDataModel;
	}

	public void setSdlNumberOnSarsEmployerFileEmptyDataModel(
			LazyDataModel<LegacyOrganisationNonLevyPaying> sdlNumberOnSarsEmployerFileEmptyDataModel) {
		this.sdlNumberOnSarsEmployerFileEmptyDataModel = sdlNumberOnSarsEmployerFileEmptyDataModel;
	}

	public boolean isDisplayDownloadSdlNumberOnSarsEmployerFile() {
		return displayDownloadSdlNumberOnSarsEmployerFile;
	}

	public void setDisplayDownloadSdlNumberOnSarsEmployerFile(boolean displayDownloadSdlNumberOnSarsEmployerFile) {
		this.displayDownloadSdlNumberOnSarsEmployerFile = displayDownloadSdlNumberOnSarsEmployerFile;
	}

	public Integer getCountSdlNumberOnSarsEmployerFile() {
		return countSdlNumberOnSarsEmployerFile;
	}

	public void setCountSdlNumberOnSarsEmployerFile(Integer countSdlNumberOnSarsEmployerFile) {
		this.countSdlNumberOnSarsEmployerFile = countSdlNumberOnSarsEmployerFile;
	}

	public LazyDataModel<LegacyOrganisationNonLevyPaying> getSdlNumberOnSarsEmployerFileDataModel() {
		return sdlNumberOnSarsEmployerFileDataModel;
	}

	public void setSdlNumberOnSarsEmployerFileDataModel(
			LazyDataModel<LegacyOrganisationNonLevyPaying> sdlNumberOnSarsEmployerFileDataModel) {
		this.sdlNumberOnSarsEmployerFileDataModel = sdlNumberOnSarsEmployerFileDataModel;
	}

	public boolean isDisplayDownloadSdlNumberNotOnSarsEmployerFile() {
		return displayDownloadSdlNumberNotOnSarsEmployerFile;
	}

	public void setDisplayDownloadSdlNumberNotOnSarsEmployerFile(boolean displayDownloadSdlNumberNotOnSarsEmployerFile) {
		this.displayDownloadSdlNumberNotOnSarsEmployerFile = displayDownloadSdlNumberNotOnSarsEmployerFile;
	}

	public Integer getCountSdlNumberNotOnSarsEmployerFile() {
		return countSdlNumberNotOnSarsEmployerFile;
	}

	public void setCountSdlNumberNotOnSarsEmployerFile(Integer countSdlNumberNotOnSarsEmployerFile) {
		this.countSdlNumberNotOnSarsEmployerFile = countSdlNumberNotOnSarsEmployerFile;
	}

	public LazyDataModel<LegacyOrganisationNonLevyPaying> getSdlNumberNotOnSarsEmployerFileDataModel() {
		return sdlNumberNotOnSarsEmployerFileDataModel;
	}

	public void setSdlNumberNotOnSarsEmployerFileDataModel(
			LazyDataModel<LegacyOrganisationNonLevyPaying> sdlNumberNotOnSarsEmployerFileDataModel) {
		this.sdlNumberNotOnSarsEmployerFileDataModel = sdlNumberNotOnSarsEmployerFileDataModel;
	}

	public boolean isDisplayDownloadMainSdlNumberOnSarsEmployerFileEmpty() {
		return displayDownloadMainSdlNumberOnSarsEmployerFileEmpty;
	}

	public void setDisplayDownloadMainSdlNumberOnSarsEmployerFileEmpty(
			boolean displayDownloadMainSdlNumberOnSarsEmployerFileEmpty) {
		this.displayDownloadMainSdlNumberOnSarsEmployerFileEmpty = displayDownloadMainSdlNumberOnSarsEmployerFileEmpty;
	}

	public Integer getCountMainSdlNumberOnSarsEmployerFileEmpty() {
		return countMainSdlNumberOnSarsEmployerFileEmpty;
	}

	public void setCountMainSdlNumberOnSarsEmployerFileEmpty(Integer countMainSdlNumberOnSarsEmployerFileEmpty) {
		this.countMainSdlNumberOnSarsEmployerFileEmpty = countMainSdlNumberOnSarsEmployerFileEmpty;
	}

	public LazyDataModel<LegacyOrganisationNonLevyPaying> getMainSdlNumberOnSarsEmployerFileEmptyDataModel() {
		return mainSdlNumberOnSarsEmployerFileEmptyDataModel;
	}

	public void setMainSdlNumberOnSarsEmployerFileEmptyDataModel(
			LazyDataModel<LegacyOrganisationNonLevyPaying> mainSdlNumberOnSarsEmployerFileEmptyDataModel) {
		this.mainSdlNumberOnSarsEmployerFileEmptyDataModel = mainSdlNumberOnSarsEmployerFileEmptyDataModel;
	}

	public boolean isDisplayDownloadMainSdlNumberOnSarsEmployerFile() {
		return displayDownloadMainSdlNumberOnSarsEmployerFile;
	}

	public void setDisplayDownloadMainSdlNumberOnSarsEmployerFile(boolean displayDownloadMainSdlNumberOnSarsEmployerFile) {
		this.displayDownloadMainSdlNumberOnSarsEmployerFile = displayDownloadMainSdlNumberOnSarsEmployerFile;
	}

	public Integer getCountMainSdlNumberOnSarsEmployerFile() {
		return countMainSdlNumberOnSarsEmployerFile;
	}

	public void setCountMainSdlNumberOnSarsEmployerFile(Integer countMainSdlNumberOnSarsEmployerFile) {
		this.countMainSdlNumberOnSarsEmployerFile = countMainSdlNumberOnSarsEmployerFile;
	}

	public LazyDataModel<LegacyOrganisationNonLevyPaying> getMainSdlNumberOnSarsEmployerFileDataModel() {
		return mainSdlNumberOnSarsEmployerFileDataModel;
	}

	public void setMainSdlNumberOnSarsEmployerFileDataModel(
			LazyDataModel<LegacyOrganisationNonLevyPaying> mainSdlNumberOnSarsEmployerFileDataModel) {
		this.mainSdlNumberOnSarsEmployerFileDataModel = mainSdlNumberOnSarsEmployerFileDataModel;
	}

	public boolean isDisplayDownloadMainSdlNumberNotOnSarsEmployerFile() {
		return displayDownloadMainSdlNumberNotOnSarsEmployerFile;
	}

	public void setDisplayDownloadMainSdlNumberNotOnSarsEmployerFile(
			boolean displayDownloadMainSdlNumberNotOnSarsEmployerFile) {
		this.displayDownloadMainSdlNumberNotOnSarsEmployerFile = displayDownloadMainSdlNumberNotOnSarsEmployerFile;
	}

	public Integer getCountMainSdlNumberNotOnSarsEmployerFile() {
		return countMainSdlNumberNotOnSarsEmployerFile;
	}

	public void setCountMainSdlNumberNotOnSarsEmployerFile(Integer countMainSdlNumberNotOnSarsEmployerFile) {
		this.countMainSdlNumberNotOnSarsEmployerFile = countMainSdlNumberNotOnSarsEmployerFile;
	}

	public LazyDataModel<LegacyOrganisationNonLevyPaying> getMainSdlNumberNotOnSarsEmployerFileDataModel() {
		return mainSdlNumberNotOnSarsEmployerFileDataModel;
	}

	public void setMainSdlNumberNotOnSarsEmployerFileDataModel(
			LazyDataModel<LegacyOrganisationNonLevyPaying> mainSdlNumberNotOnSarsEmployerFileDataModel) {
		this.mainSdlNumberNotOnSarsEmployerFileDataModel = mainSdlNumberNotOnSarsEmployerFileDataModel;
	}

}
