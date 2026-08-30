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

import haj.com.entity.lookup.LegacyOrganisationLevyPaying;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyOrganisationLevyPayingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyorganisationlevypayingUI")
@ViewScoped
public class LegacyOrganisationLevyPayingUI extends AbstractUI {

	private LegacyOrganisationLevyPayingService service = new LegacyOrganisationLevyPayingService();
	private List<LegacyOrganisationLevyPaying> legacyorganisationlevypayingList = null;
	private List<LegacyOrganisationLevyPaying> legacyorganisationlevypayingfilteredList = null;
	private LegacyOrganisationLevyPaying legacyorganisationlevypaying = null;
	private LazyDataModel<LegacyOrganisationLevyPaying> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;
	private boolean displayDownload = false;

	/* Business Rules One - SDL number on SARS file */
	// empty / unable to process
	private boolean displayDownloadSdlNumberOnSarsEmployerFileEmpty = false;
	private Integer countSdlNumberOnSarsEmployerFileEmpty;
	private LazyDataModel<LegacyOrganisationLevyPaying> sdlNumberOnSarsEmployerFileEmptyDataModel;

	// on SARS employer file - true
	private boolean displayDownloadSdlNumberOnSarsEmployerFile = false;
	private Integer countSdlNumberOnSarsEmployerFile;
	private LazyDataModel<LegacyOrganisationLevyPaying> sdlNumberOnSarsEmployerFileDataModel;

	// not on SARS employer file - false
	private boolean displayDownloadSdlNumberNotOnSarsEmployerFile = false;
	private Integer countSdlNumberNotOnSarsEmployerFile;
	private LazyDataModel<LegacyOrganisationLevyPaying> sdlNumberNotOnSarsEmployerFileDataModel;

	/* Business Rules Two - Main SDL number on SARS file */
	// empty / unable to process
	private boolean displayDownloadMainSdlNumberOnSarsEmployerFileEmpty = false;
	private Integer countMainSdlNumberOnSarsEmployerFileEmpty;
	private LazyDataModel<LegacyOrganisationLevyPaying> mainSdlNumberOnSarsEmployerFileEmptyDataModel;

	// on SARS employer file - true -
	private boolean displayDownloadMainSdlNumberOnSarsEmployerFile = false;
	private Integer countMainSdlNumberOnSarsEmployerFile;
	private LazyDataModel<LegacyOrganisationLevyPaying> mainSdlNumberOnSarsEmployerFileDataModel;

	// not on SARS employer file - false
	private boolean displayDownloadMainSdlNumberNotOnSarsEmployerFile = false;
	private Integer countMainSdlNumberNotOnSarsEmployerFile;
	private LazyDataModel<LegacyOrganisationLevyPaying> mainSdlNumberNotOnSarsEmployerFileDataModel;

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
	 * Initialize method to get all LegacyOrganisationLevyPaying and prepare a
	 * for a create of a new LegacyOrganisationLevyPaying
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationLevyPaying
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyorganisationlevypayingInfo();
		countAllEntries();
		runSdlDataTables();
	}

	/**
	 * Get all LegacyOrganisationLevyPaying for data table
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationLevyPaying
	 */
	public void legacyorganisationlevypayingInfo() {

		dataModel = new LazyDataModel<LegacyOrganisationLevyPaying>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationLevyPaying> retorno = new ArrayList<LegacyOrganisationLevyPaying>();

			@Override
			public List<LegacyOrganisationLevyPaying> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyOrganisationLevyPaying(LegacyOrganisationLevyPaying.class, first,
							pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyOrganisationLevyPaying.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationLevyPaying obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyOrganisationLevyPaying into database
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationLevyPaying
	 */
	public void legacyorganisationlevypayingInsert() {
		try {
			service.create(this.legacyorganisationlevypaying);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyorganisationlevypayingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyOrganisationLevyPaying in database
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationLevyPaying
	 */
	public void legacyorganisationlevypayingUpdate() {
		try {
			service.update(this.legacyorganisationlevypaying);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyorganisationlevypayingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyOrganisationLevyPaying from database
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationLevyPaying
	 */
	public void legacyorganisationlevypayingDelete() {
		try {
			service.delete(this.legacyorganisationlevypaying);
			prepareNew();
			legacyorganisationlevypayingInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyOrganisationLevyPaying
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationLevyPaying
	 */
	public void prepareNew() {
		legacyorganisationlevypaying = new LegacyOrganisationLevyPaying();
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyOrganisationLevyPaying> complete(String desc) {
		List<LegacyOrganisationLevyPaying> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void checkSdlNumberAgaintSarsEmployerProfile() {
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
			csvTypeSelection = ",";
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
			List<LegacyOrganisationLevyPaying> csvDataList = csvUtil.getObjects(LegacyOrganisationLevyPaying.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries");
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyorganisationlevypayingInfo();
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
			legacyorganisationlevypayingInfo();
			countAllEntries();
			runSdlDataTables();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Business Rule One - SDL number on Sars file */
	public void runSdlDataTables() throws Exception {
		sdlNumberOnSarsEmployerFileEmptyDataModelInfo();
		sdlNumberOnSarsEmployerFileDataModelInfo();
		sdlNumberNotOnSarsEmployerFileDataModelInfo();
		mainSdlNumberOnSarsEmployerFileEmptyDataModelInfo();
		mainSdlNumberOnSarsEmployerFileDataModelInfo();
		mainSdlNumberNotOnSarsEmployerFileDataModelInfo();
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
		sdlNumberOnSarsEmployerFileEmptyDataModel = new LazyDataModel<LegacyOrganisationLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationLevyPaying> retorno = new ArrayList<LegacyOrganisationLevyPaying>();

			@Override
			public List<LegacyOrganisationLevyPaying> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allSdlNumberOnSarsEmployerFileEmpty(first, pageSize, sortField, sortOrder,
							filters);
					sdlNumberOnSarsEmployerFileEmptyDataModel
							.setRowCount(service.countAllSdlNumberOnSarsEmployerFileEmpty(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationLevyPaying obj : retorno) {
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
		sdlNumberOnSarsEmployerFileDataModel = new LazyDataModel<LegacyOrganisationLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationLevyPaying> retorno = new ArrayList<LegacyOrganisationLevyPaying>();

			@Override
			public List<LegacyOrganisationLevyPaying> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
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
			public Object getRowKey(LegacyOrganisationLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationLevyPaying obj : retorno) {
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
		sdlNumberNotOnSarsEmployerFileDataModel = new LazyDataModel<LegacyOrganisationLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationLevyPaying> retorno = new ArrayList<LegacyOrganisationLevyPaying>();

			@Override
			public List<LegacyOrganisationLevyPaying> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
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
			public Object getRowKey(LegacyOrganisationLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationLevyPaying obj : retorno) {
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
		mainSdlNumberOnSarsEmployerFileEmptyDataModel = new LazyDataModel<LegacyOrganisationLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationLevyPaying> retorno = new ArrayList<LegacyOrganisationLevyPaying>();

			@Override
			public List<LegacyOrganisationLevyPaying> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allMainSdlNumberOnSarsEmployerFileEmpty(first, pageSize, sortField, sortOrder,
							filters);
					mainSdlNumberOnSarsEmployerFileEmptyDataModel
							.setRowCount(service.countAllMainSdlNumberOnSarsEmployerFileEmpty(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationLevyPaying obj : retorno) {
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
		mainSdlNumberOnSarsEmployerFileDataModel = new LazyDataModel<LegacyOrganisationLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationLevyPaying> retorno = new ArrayList<LegacyOrganisationLevyPaying>();

			@Override
			public List<LegacyOrganisationLevyPaying> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allMainSdlNumberOnSarsEmployerFileByValue(first, pageSize, sortField, sortOrder,
							filters, true);
					mainSdlNumberOnSarsEmployerFileDataModel
							.setRowCount(service.countAllMainSdlNumberOnSarsEmployerFileByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationLevyPaying obj : retorno) {
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
		mainSdlNumberNotOnSarsEmployerFileDataModel = new LazyDataModel<LegacyOrganisationLevyPaying>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyOrganisationLevyPaying> retorno = new ArrayList<LegacyOrganisationLevyPaying>();

			@Override
			public List<LegacyOrganisationLevyPaying> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allMainSdlNumberOnSarsEmployerFileByValue(first, pageSize, sortField, sortOrder,
							filters, false);
					mainSdlNumberNotOnSarsEmployerFileDataModel
							.setRowCount(service.countAllMainSdlNumberOnSarsEmployerFileByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyOrganisationLevyPaying obj) {
				return obj.getId();
			}

			@Override
			public LegacyOrganisationLevyPaying getRowData(String rowKey) {
				for (LegacyOrganisationLevyPaying obj : retorno) {
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

	public List<LegacyOrganisationLevyPaying> getLegacyOrganisationLevyPayingList() {
		return legacyorganisationlevypayingList;
	}

	public void setLegacyOrganisationLevyPayingList(
			List<LegacyOrganisationLevyPaying> legacyorganisationlevypayingList) {
		this.legacyorganisationlevypayingList = legacyorganisationlevypayingList;
	}

	public LegacyOrganisationLevyPaying getLegacyorganisationlevypaying() {
		return legacyorganisationlevypaying;
	}

	public void setLegacyorganisationlevypaying(LegacyOrganisationLevyPaying legacyorganisationlevypaying) {
		this.legacyorganisationlevypaying = legacyorganisationlevypaying;
	}

	public List<LegacyOrganisationLevyPaying> getLegacyOrganisationLevyPayingfilteredList() {
		return legacyorganisationlevypayingfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyorganisationlevypayingfilteredList
	 *            the new legacyorganisationlevypayingfilteredList list
	 * @see LegacyOrganisationLevyPaying
	 */
	public void setLegacyOrganisationLevyPayingfilteredList(
			List<LegacyOrganisationLevyPaying> legacyorganisationlevypayingfilteredList) {
		this.legacyorganisationlevypayingfilteredList = legacyorganisationlevypayingfilteredList;
	}

	public LazyDataModel<LegacyOrganisationLevyPaying> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyOrganisationLevyPaying> dataModel) {
		this.dataModel = dataModel;
	}

	public List<LegacyOrganisationLevyPaying> getLegacyorganisationlevypayingList() {
		return legacyorganisationlevypayingList;
	}

	public void setLegacyorganisationlevypayingList(List<LegacyOrganisationLevyPaying> legacyorganisationlevypayingList) {
		this.legacyorganisationlevypayingList = legacyorganisationlevypayingList;
	}

	public List<LegacyOrganisationLevyPaying> getLegacyorganisationlevypayingfilteredList() {
		return legacyorganisationlevypayingfilteredList;
	}

	public void setLegacyorganisationlevypayingfilteredList(
			List<LegacyOrganisationLevyPaying> legacyorganisationlevypayingfilteredList) {
		this.legacyorganisationlevypayingfilteredList = legacyorganisationlevypayingfilteredList;
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

	public LazyDataModel<LegacyOrganisationLevyPaying> getSdlNumberOnSarsEmployerFileEmptyDataModel() {
		return sdlNumberOnSarsEmployerFileEmptyDataModel;
	}

	public void setSdlNumberOnSarsEmployerFileEmptyDataModel(
			LazyDataModel<LegacyOrganisationLevyPaying> sdlNumberOnSarsEmployerFileEmptyDataModel) {
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

	public LazyDataModel<LegacyOrganisationLevyPaying> getSdlNumberOnSarsEmployerFileDataModel() {
		return sdlNumberOnSarsEmployerFileDataModel;
	}

	public void setSdlNumberOnSarsEmployerFileDataModel(
			LazyDataModel<LegacyOrganisationLevyPaying> sdlNumberOnSarsEmployerFileDataModel) {
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

	public LazyDataModel<LegacyOrganisationLevyPaying> getSdlNumberNotOnSarsEmployerFileDataModel() {
		return sdlNumberNotOnSarsEmployerFileDataModel;
	}

	public void setSdlNumberNotOnSarsEmployerFileDataModel(
			LazyDataModel<LegacyOrganisationLevyPaying> sdlNumberNotOnSarsEmployerFileDataModel) {
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

	public LazyDataModel<LegacyOrganisationLevyPaying> getMainSdlNumberOnSarsEmployerFileEmptyDataModel() {
		return mainSdlNumberOnSarsEmployerFileEmptyDataModel;
	}

	public void setMainSdlNumberOnSarsEmployerFileEmptyDataModel(
			LazyDataModel<LegacyOrganisationLevyPaying> mainSdlNumberOnSarsEmployerFileEmptyDataModel) {
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

	public LazyDataModel<LegacyOrganisationLevyPaying> getMainSdlNumberOnSarsEmployerFileDataModel() {
		return mainSdlNumberOnSarsEmployerFileDataModel;
	}

	public void setMainSdlNumberOnSarsEmployerFileDataModel(
			LazyDataModel<LegacyOrganisationLevyPaying> mainSdlNumberOnSarsEmployerFileDataModel) {
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

	public LazyDataModel<LegacyOrganisationLevyPaying> getMainSdlNumberNotOnSarsEmployerFileDataModel() {
		return mainSdlNumberNotOnSarsEmployerFileDataModel;
	}

	public void setMainSdlNumberNotOnSarsEmployerFileDataModel(
			LazyDataModel<LegacyOrganisationLevyPaying> mainSdlNumberNotOnSarsEmployerFileDataModel) {
		this.mainSdlNumberNotOnSarsEmployerFileDataModel = mainSdlNumberNotOnSarsEmployerFileDataModel;
	}
}
