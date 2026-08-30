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
import haj.com.entity.lookup.LegacyEmployerWA2Learnership;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyEmployerWA2LearnershipService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyemployerwa2learnershipUI")
@ViewScoped
public class LegacyEmployerWA2LearnershipUI extends AbstractUI {

	private LegacyEmployerWA2LearnershipService service = new LegacyEmployerWA2LearnershipService();
	private List<LegacyEmployerWA2Learnership> legacyemployerwa2learnershipList = null;
	private List<LegacyEmployerWA2Learnership> legacyemployerwa2learnershipfilteredList = null;
	private LegacyEmployerWA2Learnership legacyemployerwa2learnership = null;
	private LazyDataModel<LegacyEmployerWA2Learnership> dataModel;

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
	 * Initialize method to get all LegacyEmployerWA2Learnership and prepare a for a
	 * create of a new LegacyEmployerWA2Learnership
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Learnership
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyemployerwa2learnershipInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyEmployerWA2Learnership.class, true);
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
	 * Get all LegacyEmployerWA2Learnership for data table
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Learnership
	 */
	public void legacyemployerwa2learnershipInfo() {

		dataModel = new LazyDataModel<LegacyEmployerWA2Learnership>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyEmployerWA2Learnership> retorno = new ArrayList<LegacyEmployerWA2Learnership>();

			@Override
			public List<LegacyEmployerWA2Learnership> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyEmployerWA2Learnership(LegacyEmployerWA2Learnership.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyEmployerWA2Learnership.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyEmployerWA2Learnership obj) {
				return obj.getId();
			}

			@Override
			public LegacyEmployerWA2Learnership getRowData(String rowKey) {
				for (LegacyEmployerWA2Learnership obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyEmployerWA2Learnership into database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Learnership
	 */
	public void legacyemployerwa2learnershipInsert() {
		try {
			service.create(this.legacyemployerwa2learnership);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2learnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyEmployerWA2Learnership in database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Learnership
	 */
	public void legacyemployerwa2learnershipUpdate() {
		try {
			service.update(this.legacyemployerwa2learnership);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2learnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyEmployerWA2Learnership from database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Learnership
	 */
	public void legacyemployerwa2learnershipDelete() {
		try {
			service.delete(this.legacyemployerwa2learnership);
			prepareNew();
			legacyemployerwa2learnershipInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyEmployerWA2Learnership
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Learnership
	 */
	public void prepareNew() {
		legacyemployerwa2learnership = new LegacyEmployerWA2Learnership();
	}

	/*
	 * public List<SelectItem> getLegacyEmployerWA2LearnershipDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyemployerwa2learnershipInfo(); for (LegacyEmployerWA2Learnership ug :
	 * legacyemployerwa2learnershipList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyEmployerWA2Learnership> complete(String desc) {
		List<LegacyEmployerWA2Learnership> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyEmployerWA2Learnership> getLegacyEmployerWA2LearnershipList() {
		return legacyemployerwa2learnershipList;
	}

	public void setLegacyEmployerWA2LearnershipList(List<LegacyEmployerWA2Learnership> legacyemployerwa2learnershipList) {
		this.legacyemployerwa2learnershipList = legacyemployerwa2learnershipList;
	}

	public LegacyEmployerWA2Learnership getLegacyemployerwa2learnership() {
		return legacyemployerwa2learnership;
	}

	public void setLegacyemployerwa2learnership(LegacyEmployerWA2Learnership legacyemployerwa2learnership) {
		this.legacyemployerwa2learnership = legacyemployerwa2learnership;
	}

	public List<LegacyEmployerWA2Learnership> getLegacyEmployerWA2LearnershipfilteredList() {
		return legacyemployerwa2learnershipfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyemployerwa2learnershipfilteredList
	 *            the new legacyemployerwa2learnershipfilteredList list
	 * @see LegacyEmployerWA2Learnership
	 */
	public void setLegacyEmployerWA2LearnershipfilteredList(List<LegacyEmployerWA2Learnership> legacyemployerwa2learnershipfilteredList) {
		this.legacyemployerwa2learnershipfilteredList = legacyemployerwa2learnershipfilteredList;
	}

	public LazyDataModel<LegacyEmployerWA2Learnership> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyEmployerWA2Learnership> dataModel) {
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
			List<LegacyEmployerWA2Learnership> csvDataList = csvUtil.getObjects(LegacyEmployerWA2Learnership.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyemployerwa2learnershipInfo();
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
			legacyemployerwa2learnershipInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacyemployerwa2learnershipInfo();
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
