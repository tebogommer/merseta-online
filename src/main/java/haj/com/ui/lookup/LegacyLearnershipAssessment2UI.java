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
import haj.com.entity.lookup.LegacyLearnershipAssessment2;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyLearnershipAssessment2Service;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacylearnershipassessment2UI")
@ViewScoped
public class LegacyLearnershipAssessment2UI extends AbstractUI {

	private LegacyLearnershipAssessment2Service service = new LegacyLearnershipAssessment2Service();
	private List<LegacyLearnershipAssessment2> legacylearnershipassessment2List = null;
	private List<LegacyLearnershipAssessment2> legacylearnershipassessment2filteredList = null;
	private LegacyLearnershipAssessment2 legacylearnershipassessment2 = null;
	private LazyDataModel<LegacyLearnershipAssessment2> dataModel;

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
	 * Initialize method to get all LegacyLearnershipAssessment2 and prepare a for a
	 * create of a new LegacyLearnershipAssessment2
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment2
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacylearnershipassessment2Info();
		countAllEntries();
		reportingMap = reportingService.runReports(LegacyLearnershipAssessment2.class, true);
		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyLearnershipAssessment2 for data table
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment2
	 */
	public void legacylearnershipassessment2Info() {

		dataModel = new LazyDataModel<LegacyLearnershipAssessment2>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyLearnershipAssessment2> retorno = new ArrayList<LegacyLearnershipAssessment2>();

			@Override
			public List<LegacyLearnershipAssessment2> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyLearnershipAssessment2(LegacyLearnershipAssessment2.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyLearnershipAssessment2.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyLearnershipAssessment2 obj) {
				return obj.getId();
			}

			@Override
			public LegacyLearnershipAssessment2 getRowData(String rowKey) {
				for (LegacyLearnershipAssessment2 obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyLearnershipAssessment2 into database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment2
	 */
	public void legacylearnershipassessment2Insert() {
		try {
			service.create(this.legacylearnershipassessment2);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacylearnershipassessment2Info();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyLearnershipAssessment2 in database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment2
	 */
	public void legacylearnershipassessment2Update() {
		try {
			service.update(this.legacylearnershipassessment2);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacylearnershipassessment2Info();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyLearnershipAssessment2 from database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment2
	 */
	public void legacylearnershipassessment2Delete() {
		try {
			service.delete(this.legacylearnershipassessment2);
			prepareNew();
			legacylearnershipassessment2Info();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyLearnershipAssessment2
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment2
	 */
	public void prepareNew() {
		legacylearnershipassessment2 = new LegacyLearnershipAssessment2();
	}

	/*
	 * public List<SelectItem> getLegacyLearnershipAssessment2DD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacylearnershipassessment2Info(); for (LegacyLearnershipAssessment2 ug :
	 * legacylearnershipassessment2List) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyLearnershipAssessment2> complete(String desc) {
		List<LegacyLearnershipAssessment2> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyLearnershipAssessment2> getLegacyLearnershipAssessment2List() {
		return legacylearnershipassessment2List;
	}

	public void setLegacyLearnershipAssessment2List(List<LegacyLearnershipAssessment2> legacylearnershipassessment2List) {
		this.legacylearnershipassessment2List = legacylearnershipassessment2List;
	}

	public LegacyLearnershipAssessment2 getLegacylearnershipassessment2() {
		return legacylearnershipassessment2;
	}

	public void setLegacylearnershipassessment2(LegacyLearnershipAssessment2 legacylearnershipassessment2) {
		this.legacylearnershipassessment2 = legacylearnershipassessment2;
	}

	public List<LegacyLearnershipAssessment2> getLegacyLearnershipAssessment2filteredList() {
		return legacylearnershipassessment2filteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacylearnershipassessment2filteredList
	 *            the new legacylearnershipassessment2filteredList list
	 * @see LegacyLearnershipAssessment2
	 */
	public void setLegacyLearnershipAssessment2filteredList(List<LegacyLearnershipAssessment2> legacylearnershipassessment2filteredList) {
		this.legacylearnershipassessment2filteredList = legacylearnershipassessment2filteredList;
	}

	public LazyDataModel<LegacyLearnershipAssessment2> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyLearnershipAssessment2> dataModel) {
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
			List<LegacyLearnershipAssessment2> csvDataList = csvUtil.getObjects(LegacyLearnershipAssessment2.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacylearnershipassessment2Info();
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
			legacylearnershipassessment2Info();
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

	public void runValidiations() {
		try {
			service.validiationRun();
			addInfoMessage("Action Complete");
			legacylearnershipassessment2Info();
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

	public List<LegacyLearnershipAssessment2> getLegacylearnershipassessment2List() {
		return legacylearnershipassessment2List;
	}

	public void setLegacylearnershipassessment2List(List<LegacyLearnershipAssessment2> legacylearnershipassessment2List) {
		this.legacylearnershipassessment2List = legacylearnershipassessment2List;
	}

	public List<LegacyLearnershipAssessment2> getLegacylearnershipassessment2filteredList() {
		return legacylearnershipassessment2filteredList;
	}

	public void setLegacylearnershipassessment2filteredList(List<LegacyLearnershipAssessment2> legacylearnershipassessment2filteredList) {
		this.legacylearnershipassessment2filteredList = legacylearnershipassessment2filteredList;
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
