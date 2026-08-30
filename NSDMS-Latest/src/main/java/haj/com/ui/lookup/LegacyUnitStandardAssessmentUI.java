package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.entity.lookup.LegacyUnitStandardAssessment;
import haj.com.service.lookup.LegacyUnitStandardAssessmentService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyunitstandardassessmentUI")
@ViewScoped
public class LegacyUnitStandardAssessmentUI extends AbstractUI {

	private LegacyUnitStandardAssessmentService service = new LegacyUnitStandardAssessmentService();
	private List<LegacyUnitStandardAssessment> legacyunitstandardassessmentList = null;
	private List<LegacyUnitStandardAssessment> legacyunitstandardassessmentfilteredList = null;
	private LegacyUnitStandardAssessment legacyunitstandardassessment = null;
	private LazyDataModel<LegacyUnitStandardAssessment> dataModel;

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
	 * Initialize method to get all LegacyUnitStandardAssessment and prepare a for a
	 * create of a new LegacyUnitStandardAssessment
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandardAssessment
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyunitstandardassessmentInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyUnitStandardAssessment.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyUnitStandardAssessment for data table
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandardAssessment
	 */
	public void legacyunitstandardassessmentInfo() {

		dataModel = new LazyDataModel<LegacyUnitStandardAssessment>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyUnitStandardAssessment> retorno = new ArrayList<LegacyUnitStandardAssessment>();

			@Override
			public List<LegacyUnitStandardAssessment> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyUnitStandardAssessment(LegacyUnitStandardAssessment.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyUnitStandardAssessment.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyUnitStandardAssessment obj) {
				return obj.getId();
			}

			@Override
			public LegacyUnitStandardAssessment getRowData(String rowKey) {
				for (LegacyUnitStandardAssessment obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyUnitStandardAssessment into database
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandardAssessment
	 */
	public void legacyunitstandardassessmentInsert() {
		try {
			service.create(this.legacyunitstandardassessment);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyunitstandardassessmentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyUnitStandardAssessment in database
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandardAssessment
	 */
	public void legacyunitstandardassessmentUpdate() {
		try {
			service.update(this.legacyunitstandardassessment);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyunitstandardassessmentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyUnitStandardAssessment from database
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandardAssessment
	 */
	public void legacyunitstandardassessmentDelete() {
		try {
			service.delete(this.legacyunitstandardassessment);
			prepareNew();
			legacyunitstandardassessmentInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyUnitStandardAssessment
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandardAssessment
	 */
	public void prepareNew() {
		legacyunitstandardassessment = new LegacyUnitStandardAssessment();
	}

	/*
	 * public List<SelectItem> getLegacyUnitStandardAssessmentDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyunitstandardassessmentInfo(); for (LegacyUnitStandardAssessment ug :
	 * legacyunitstandardassessmentList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyUnitStandardAssessment> complete(String desc) {
		List<LegacyUnitStandardAssessment> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyUnitStandardAssessment> getLegacyUnitStandardAssessmentList() {
		return legacyunitstandardassessmentList;
	}

	public void setLegacyUnitStandardAssessmentList(List<LegacyUnitStandardAssessment> legacyunitstandardassessmentList) {
		this.legacyunitstandardassessmentList = legacyunitstandardassessmentList;
	}

	public LegacyUnitStandardAssessment getLegacyunitstandardassessment() {
		return legacyunitstandardassessment;
	}

	public void setLegacyunitstandardassessment(LegacyUnitStandardAssessment legacyunitstandardassessment) {
		this.legacyunitstandardassessment = legacyunitstandardassessment;
	}

	public List<LegacyUnitStandardAssessment> getLegacyUnitStandardAssessmentfilteredList() {
		return legacyunitstandardassessmentfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyunitstandardassessmentfilteredList
	 *            the new legacyunitstandardassessmentfilteredList list
	 * @see LegacyUnitStandardAssessment
	 */
	public void setLegacyUnitStandardAssessmentfilteredList(List<LegacyUnitStandardAssessment> legacyunitstandardassessmentfilteredList) {
		this.legacyunitstandardassessmentfilteredList = legacyunitstandardassessmentfilteredList;
	}

	public LazyDataModel<LegacyUnitStandardAssessment> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyUnitStandardAssessment> dataModel) {
		this.dataModel = dataModel;
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
			List<LegacyUnitStandardAssessment> csvDataList = csvUtil.getObjects(LegacyUnitStandardAssessment.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyunitstandardassessmentInfo();
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
			legacyunitstandardassessmentInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validiationRun();
			addInfoMessage("Action Complete");
			legacyunitstandardassessmentInfo();
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

	public List<LegacyUnitStandardAssessment> getLegacyunitstandardassessmentList() {
		return legacyunitstandardassessmentList;
	}

	public void setLegacyunitstandardassessmentList(List<LegacyUnitStandardAssessment> legacyunitstandardassessmentList) {
		this.legacyunitstandardassessmentList = legacyunitstandardassessmentList;
	}

	public List<LegacyUnitStandardAssessment> getLegacyunitstandardassessmentfilteredList() {
		return legacyunitstandardassessmentfilteredList;
	}

	public void setLegacyunitstandardassessmentfilteredList(List<LegacyUnitStandardAssessment> legacyunitstandardassessmentfilteredList) {
		this.legacyunitstandardassessmentfilteredList = legacyunitstandardassessmentfilteredList;
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
