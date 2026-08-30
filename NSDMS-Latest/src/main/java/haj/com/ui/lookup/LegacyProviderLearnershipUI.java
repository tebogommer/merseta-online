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
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyProviderLearnership;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyProviderLearnershipService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyproviderlearnershipUI")
@ViewScoped
public class LegacyProviderLearnershipUI extends AbstractUI {

	private LegacyProviderLearnershipService service = new LegacyProviderLearnershipService();
	private List<LegacyProviderLearnership> legacyproviderlearnershipList = null;
	private List<LegacyProviderLearnership> legacyproviderlearnershipfilteredList = null;
	private LegacyProviderLearnership legacyproviderlearnership = null;
	private LazyDataModel<LegacyProviderLearnership> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;
	private boolean displayDownload = false;
	// reporting
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
	 * Initialize method to get all LegacyProviderLearnership and prepare a for a
	 * create of a new LegacyProviderLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyProviderLearnership
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyproviderlearnershipInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyProviderLearnership.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
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

	/**
	 * Get all LegacyProviderLearnership for data table
	 * 
	 * @author TechFinium
	 * @see LegacyProviderLearnership
	 */
	public void legacyproviderlearnershipInfo() {

		dataModel = new LazyDataModel<LegacyProviderLearnership>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderLearnership> retorno = new ArrayList<LegacyProviderLearnership>();

			@Override
			public List<LegacyProviderLearnership> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyProviderLearnership(LegacyProviderLearnership.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyProviderLearnership.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderLearnership obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderLearnership getRowData(String rowKey) {
				for (LegacyProviderLearnership obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyProviderLearnership into database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderLearnership
	 */
	public void legacyproviderlearnershipInsert() {
		try {
			service.create(this.legacyproviderlearnership);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyproviderlearnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyProviderLearnership in database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderLearnership
	 */
	public void legacyproviderlearnershipUpdate() {
		try {
			service.update(this.legacyproviderlearnership);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyproviderlearnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyProviderLearnership from database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderLearnership
	 */
	public void legacyproviderlearnershipDelete() {
		try {
			service.delete(this.legacyproviderlearnership);
			prepareNew();
			legacyproviderlearnershipInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyProviderLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyProviderLearnership
	 */
	public void prepareNew() {
		legacyproviderlearnership = new LegacyProviderLearnership();
	}

	/*
	 * public List<SelectItem> getLegacyProviderLearnershipDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyproviderlearnershipInfo(); for (LegacyProviderLearnership ug :
	 * legacyproviderlearnershipList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyProviderLearnership> complete(String desc) {
		List<LegacyProviderLearnership> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyProviderLearnership> getLegacyProviderLearnershipList() {
		return legacyproviderlearnershipList;
	}

	public void setLegacyProviderLearnershipList(List<LegacyProviderLearnership> legacyproviderlearnershipList) {
		this.legacyproviderlearnershipList = legacyproviderlearnershipList;
	}

	public LegacyProviderLearnership getLegacyproviderlearnership() {
		return legacyproviderlearnership;
	}

	public void setLegacyproviderlearnership(LegacyProviderLearnership legacyproviderlearnership) {
		this.legacyproviderlearnership = legacyproviderlearnership;
	}

	public List<LegacyProviderLearnership> getLegacyProviderLearnershipfilteredList() {
		return legacyproviderlearnershipfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyproviderlearnershipfilteredList
	 *            the new legacyproviderlearnershipfilteredList list
	 * @see LegacyProviderLearnership
	 */
	public void setLegacyProviderLearnershipfilteredList(List<LegacyProviderLearnership> legacyproviderlearnershipfilteredList) {
		this.legacyproviderlearnershipfilteredList = legacyproviderlearnershipfilteredList;
	}

	public LazyDataModel<LegacyProviderLearnership> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyProviderLearnership> dataModel) {
		this.dataModel = dataModel;
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
			List<LegacyProviderLearnership> csvDataList = csvUtil.getObjects(LegacyProviderLearnership.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyproviderlearnershipInfo();
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
			legacyproviderlearnershipInfo();
			countAllEntries();
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

	public List<LegacyProviderLearnership> getLegacyproviderlearnershipList() {
		return legacyproviderlearnershipList;
	}

	public void setLegacyproviderlearnershipList(List<LegacyProviderLearnership> legacyproviderlearnershipList) {
		this.legacyproviderlearnershipList = legacyproviderlearnershipList;
	}

	public List<LegacyProviderLearnership> getLegacyproviderlearnershipfilteredList() {
		return legacyproviderlearnershipfilteredList;
	}

	public void setLegacyproviderlearnershipfilteredList(List<LegacyProviderLearnership> legacyproviderlearnershipfilteredList) {
		this.legacyproviderlearnershipfilteredList = legacyproviderlearnershipfilteredList;
	}

	public boolean isDisplayDownload() {
		return displayDownload;
	}

	public void setDisplayDownload(boolean displayDownload) {
		this.displayDownload = displayDownload;
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
