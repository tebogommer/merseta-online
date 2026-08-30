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

import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyAssessorAccreditationService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyassessoraccreditationUI")
@ViewScoped
public class LegacyAssessorAccreditationUI extends AbstractUI {

	private LegacyAssessorAccreditationService service = new LegacyAssessorAccreditationService();
	private List<LegacyAssessorAccreditation> legacyassessoraccreditationList = null;
	private List<LegacyAssessorAccreditation> legacyassessoraccreditationfilteredList = null;
	private LegacyAssessorAccreditation legacyassessoraccreditation = null;
	private LazyDataModel<LegacyAssessorAccreditation> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;

	private boolean displayDownload = false;

	/* Reporting START */
	private int totalProcessed;
	private int totalNotProcessed;

	private int onHomeAffairs;
	private int notOnHomeAffairs;

	private int validRSAID;
	private int notValidRSAID;

	private LazyDataModel<LegacyAssessorAccreditation> onHomeAffairsDataModel;
	private LazyDataModel<LegacyAssessorAccreditation> notOnHomeAffairsDataModel;

	private LazyDataModel<LegacyAssessorAccreditation> validRSAIDDataModel;
	private LazyDataModel<LegacyAssessorAccreditation> notValidRSAIDDataModel;
	//reporting
	private ReportingService reportingService = new ReportingService();
	private List<LegacyReportingBean> legacyReportingBeans = new ArrayList<>();
	private Map<String, List<Object>> reportingMap;
	private Map<String, ColumnBeans> columnMap; 

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
	 * Initialize method to get all LegacyAssessorAccreditation and prepare a for a
	 * create of a new LegacyAssessorAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyassessoraccreditationInfo();
		countAllEntries();
		runReporting();
//		reportingMap = reportingService.runReports(LegacyAssessorAccreditation.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>)reportingService.extractValues(reportingMap, "count"); 
//		columnMap = reportingService.convertToColumns(reportingMap);
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
			List<LegacyAssessorAccreditation> csvDataList = csvUtil.getObjects(LegacyAssessorAccreditation.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyassessoraccreditationInfo();
			countAllEntries();
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
			legacyassessoraccreditationInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action underway, email notification will be sent on completion");
			legacyassessoraccreditationInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runStatusFix() {
		try {
			service.fixStatusOnLegacyAssessorAccreditation();
			addInfoMessage("Action underway, email notification will be sent on completion");
			legacyassessoraccreditationInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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

	/**
	 * Get all LegacyAssessorAccreditation for data table
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 */
	public void legacyassessoraccreditationInfo() {

		dataModel = new LazyDataModel<LegacyAssessorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorAccreditation> retorno = new ArrayList<LegacyAssessorAccreditation>();

			@Override
			public List<LegacyAssessorAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyAssessorAccreditation(LegacyAssessorAccreditation.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyAssessorAccreditation.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorAccreditation getRowData(String rowKey) {
				for (LegacyAssessorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyAssessorAccreditation into database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 */
	public void legacyassessoraccreditationInsert() {
		try {
			service.create(this.legacyassessoraccreditation);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyassessoraccreditationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyAssessorAccreditation in database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 */
	public void legacyassessoraccreditationUpdate() {
		try {
			service.update(this.legacyassessoraccreditation);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyassessoraccreditationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyAssessorAccreditation from database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 */
	public void legacyassessoraccreditationDelete() {
		try {
			service.delete(this.legacyassessoraccreditation);
			prepareNew();
			legacyassessoraccreditationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyAssessorAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 */
	public void prepareNew() {
		legacyassessoraccreditation = new LegacyAssessorAccreditation();
	}

	/*
	 * public List<SelectItem> getLegacyAssessorAccreditationDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyassessoraccreditationInfo(); for (LegacyAssessorAccreditation ug :
	 * legacyassessoraccreditationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<LegacyAssessorAccreditation> complete(String desc) {
		List<LegacyAssessorAccreditation> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyAssessorAccreditation> getLegacyAssessorAccreditationList() {
		return legacyassessoraccreditationList;
	}

	public void setLegacyAssessorAccreditationList(List<LegacyAssessorAccreditation> legacyassessoraccreditationList) {
		this.legacyassessoraccreditationList = legacyassessoraccreditationList;
	}

	public LegacyAssessorAccreditation getLegacyassessoraccreditation() {
		return legacyassessoraccreditation;
	}

	public void setLegacyassessoraccreditation(LegacyAssessorAccreditation legacyassessoraccreditation) {
		this.legacyassessoraccreditation = legacyassessoraccreditation;
	}

	public List<LegacyAssessorAccreditation> getLegacyAssessorAccreditationfilteredList() {
		return legacyassessoraccreditationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyassessoraccreditationfilteredList the new
	 *                                                legacyassessoraccreditationfilteredList
	 *                                                list
	 * @see LegacyAssessorAccreditation
	 */
	public void setLegacyAssessorAccreditationfilteredList(
			List<LegacyAssessorAccreditation> legacyassessoraccreditationfilteredList) {
		this.legacyassessoraccreditationfilteredList = legacyassessoraccreditationfilteredList;
	}

	public LazyDataModel<LegacyAssessorAccreditation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyAssessorAccreditation> dataModel) {
		this.dataModel = dataModel;
	}

	public List<LegacyAssessorAccreditation> getLegacyassessoraccreditationList() {
		return legacyassessoraccreditationList;
	}

	public void setLegacyassessoraccreditationList(List<LegacyAssessorAccreditation> legacyassessoraccreditationList) {
		this.legacyassessoraccreditationList = legacyassessoraccreditationList;
	}

	public List<LegacyAssessorAccreditation> getLegacyassessoraccreditationfilteredList() {
		return legacyassessoraccreditationfilteredList;
	}

	public void setLegacyassessoraccreditationfilteredList(
			List<LegacyAssessorAccreditation> legacyassessoraccreditationfilteredList) {
		this.legacyassessoraccreditationfilteredList = legacyassessoraccreditationfilteredList;
	}

	public CSVUtil getCsvUtil() {
		return csvUtil;
	}

	public void setCsvUtil(CSVUtil csvUtil) {
		this.csvUtil = csvUtil;
	}

	public boolean isDisplayDownload() {
		return displayDownload;
	}

	public void setDisplayDownload(boolean displayDownload) {
		this.displayDownload = displayDownload;
	}

	public void runReporting() throws Exception {
		runProcessedReporting();
		runHomeAffairsReporting();
		runValidRSAIDReporting();
		legacyassessoraccreditationOnHomeAffairs();
		legacyassessoraccreditationNotOnHomeAffairs();
		legacyassessoraccreditationValidRSAIDDataModel();
		legacyassessoraccreditationNotValidRSAIDDataModel();

	}

	private void runProcessedReporting() throws Exception {
		totalProcessed = service.countAllLegacyAssessorAccreditationByProcessedValue(true);
		totalNotProcessed = service.countAllLegacyAssessorAccreditationByProcessedValue(false);
	}

	private void runHomeAffairsReporting() throws Exception {
		onHomeAffairs = service.countAllLegacyAssessorAccreditationByAppearsOnHomeAffairsDataValue(true);
		notOnHomeAffairs = service.countAllLegacyAssessorAccreditationByAppearsOnHomeAffairsDataValue(false);
	}

	private void runValidRSAIDReporting() throws Exception {
		validRSAID = service.countAllLegacyAssessorAccreditationByValidRSAIDValue(true);
		notValidRSAID = service.countAllLegacyAssessorAccreditationByValidRSAIDValue(false);
	}

	/**
	 * Get all onHomeAffairs for data table
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 */
	public void legacyassessoraccreditationOnHomeAffairs() {

		onHomeAffairsDataModel = new LazyDataModel<LegacyAssessorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorAccreditation> retorno = new ArrayList<LegacyAssessorAccreditation>();

			@Override
			public List<LegacyAssessorAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyAssessorAccreditationOnHomeAffairs(first, pageSize, sortField, sortOrder,
							filters, true);
					onHomeAffairsDataModel
							.setRowCount(service.countallLegacyAssessorAccreditationOnHomeAffairs(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorAccreditation getRowData(String rowKey) {
				for (LegacyAssessorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Get all onHomeAffairs for data table
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 */
	public void legacyassessoraccreditationNotOnHomeAffairs() {

		notOnHomeAffairsDataModel = new LazyDataModel<LegacyAssessorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorAccreditation> retorno = new ArrayList<LegacyAssessorAccreditation>();

			@Override
			public List<LegacyAssessorAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyAssessorAccreditationNotOnHomeAffairs(first, pageSize, sortField,
							sortOrder, filters, false);
					notOnHomeAffairsDataModel
							.setRowCount(service.countallLegacyAssessorAccreditationNotOnHomeAffairs(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorAccreditation getRowData(String rowKey) {
				for (LegacyAssessorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Get all ValidRSAID for data table
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 */
	public void legacyassessoraccreditationValidRSAIDDataModel() {

		validRSAIDDataModel = new LazyDataModel<LegacyAssessorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorAccreditation> retorno = new ArrayList<LegacyAssessorAccreditation>();

			@Override
			public List<LegacyAssessorAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyAssessorAccreditationValidRSAID(first, pageSize, sortField, sortOrder,
							filters, true);
					validRSAIDDataModel.setRowCount(service.countallLegacyAssessorAccreditationValidRSAID(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorAccreditation getRowData(String rowKey) {
				for (LegacyAssessorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Get all InvalidRSAID for data table
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorAccreditation
	 */
	public void legacyassessoraccreditationNotValidRSAIDDataModel() {

		notValidRSAIDDataModel = new LazyDataModel<LegacyAssessorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorAccreditation> retorno = new ArrayList<LegacyAssessorAccreditation>();

			@Override
			public List<LegacyAssessorAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyAssessorAccreditationInvalidRSAID(first, pageSize, sortField, sortOrder,
							filters, false);
					notValidRSAIDDataModel
							.setRowCount(service.countallLegacyAssessorAccreditationInvalidRSAID(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorAccreditation getRowData(String rowKey) {
				for (LegacyAssessorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

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

	public int getOnHomeAffairs() {
		return onHomeAffairs;
	}

	public void setOnHomeAffairs(int onHomeAffairs) {
		this.onHomeAffairs = onHomeAffairs;
	}

	public int getNotOnHomeAffairs() {
		return notOnHomeAffairs;
	}

	public void setNotOnHomeAffairs(int notOnHomeAffairs) {
		this.notOnHomeAffairs = notOnHomeAffairs;
	}

	public int getValidRSAID() {
		return validRSAID;
	}

	public void setValidRSAID(int validRSAID) {
		this.validRSAID = validRSAID;
	}

	public int getNotValidRSAID() {
		return notValidRSAID;
	}

	public void setNotValidRSAID(int notValidRSAID) {
		this.notValidRSAID = notValidRSAID;
	}

	public LazyDataModel<LegacyAssessorAccreditation> getOnHomeAffairsDataModel() {
		return onHomeAffairsDataModel;
	}

	public void setOnHomeAffairsDataModel(LazyDataModel<LegacyAssessorAccreditation> onHomeAffairsDataModel) {
		this.onHomeAffairsDataModel = onHomeAffairsDataModel;
	}

	public LazyDataModel<LegacyAssessorAccreditation> getNotOnHomeAffairsDataModel() {
		return notOnHomeAffairsDataModel;
	}

	public void setNotOnHomeAffairsDataModel(LazyDataModel<LegacyAssessorAccreditation> notOnHomeAffairsDataModel) {
		this.notOnHomeAffairsDataModel = notOnHomeAffairsDataModel;
	}

	public LazyDataModel<LegacyAssessorAccreditation> getValidRSAIDDataModel() {
		return validRSAIDDataModel;
	}

	public void setValidRSAIDDataModel(LazyDataModel<LegacyAssessorAccreditation> validRSAIDDataModel) {
		this.validRSAIDDataModel = validRSAIDDataModel;
	}

	public LazyDataModel<LegacyAssessorAccreditation> getNotValidRSAIDDataModel() {
		return notValidRSAIDDataModel;
	}

	public void setNotValidRSAIDDataModel(LazyDataModel<LegacyAssessorAccreditation> notValidRSAIDDataModel) {
		this.notValidRSAIDDataModel = notValidRSAIDDataModel;
	}


	public List<LegacyReportingBean> getLegacyReportingBeans() {
		return legacyReportingBeans;
	}

	public void setLegacyReportingBeans(List<LegacyReportingBean> legacyReportingBeans) {
		this.legacyReportingBeans = legacyReportingBeans;
	}

	public Map<String, List<Object>> getReportingMap() {
		return reportingMap;
	}

	public void setReportingMap(Map<String, List<Object>> reportingMap) {
		this.reportingMap = reportingMap;
	}

	public Map<String, ColumnBeans> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, ColumnBeans> columnMap) {
		this.columnMap = columnMap;
	}
}
