package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.service.lookup.LegacyAssessorUnitStandardService;
import haj.com.service.lookup.ReportingService;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyassessorunitstandardUI")
@ViewScoped
public class LegacyAssessorUnitStandardUI extends AbstractUI {

	private LegacyAssessorUnitStandardService service = new LegacyAssessorUnitStandardService();
	private List<LegacyAssessorUnitStandard> legacyassessorunitstandardList = null;
	private List<LegacyAssessorUnitStandard> legacyassessorunitstandardfilteredList = null;
	private LegacyAssessorUnitStandard legacyassessorunitstandard = null;
	private LazyDataModel<LegacyAssessorUnitStandard> dataModel;
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
	 * Initialize method to get all LegacyAssessorUnitStandard and prepare a for a
	 * create of a new LegacyAssessorUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorUnitStandard
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyassessorunitstandardInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyAssessorUnitStandard.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyAssessorUnitStandard for data table
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorUnitStandard
	 */
	public void legacyassessorunitstandardInfo() {

		dataModel = new LazyDataModel<LegacyAssessorUnitStandard>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorUnitStandard> retorno = new ArrayList<LegacyAssessorUnitStandard>();

			@Override
			public List<LegacyAssessorUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyAssessorUnitStandard(LegacyAssessorUnitStandard.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyAssessorUnitStandard.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorUnitStandard obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorUnitStandard getRowData(String rowKey) {
				for (LegacyAssessorUnitStandard obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyAssessorUnitStandard into database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorUnitStandard
	 */
	public void legacyassessorunitstandardInsert() {
		try {
			service.create(this.legacyassessorunitstandard);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyassessorunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyAssessorUnitStandard in database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorUnitStandard
	 */
	public void legacyassessorunitstandardUpdate() {
		try {
			service.update(this.legacyassessorunitstandard);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyassessorunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyAssessorUnitStandard from database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorUnitStandard
	 */
	public void legacyassessorunitstandardDelete() {
		try {
			service.delete(this.legacyassessorunitstandard);
			prepareNew();
			legacyassessorunitstandardInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyAssessorUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorUnitStandard
	 */
	public void prepareNew() {
		legacyassessorunitstandard = new LegacyAssessorUnitStandard();
	}

	/*
	 * public List<SelectItem> getLegacyAssessorUnitStandardDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyassessorunitstandardInfo(); for (LegacyAssessorUnitStandard ug :
	 * legacyassessorunitstandardList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyAssessorUnitStandard> complete(String desc) {
		List<LegacyAssessorUnitStandard> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyAssessorUnitStandard> getLegacyAssessorUnitStandardList() {
		return legacyassessorunitstandardList;
	}

	public void setLegacyAssessorUnitStandardList(List<LegacyAssessorUnitStandard> legacyassessorunitstandardList) {
		this.legacyassessorunitstandardList = legacyassessorunitstandardList;
	}

	public LegacyAssessorUnitStandard getLegacyassessorunitstandard() {
		return legacyassessorunitstandard;
	}

	public void setLegacyassessorunitstandard(LegacyAssessorUnitStandard legacyassessorunitstandard) {
		this.legacyassessorunitstandard = legacyassessorunitstandard;
	}

	public List<LegacyAssessorUnitStandard> getLegacyAssessorUnitStandardfilteredList() {
		return legacyassessorunitstandardfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyassessorunitstandardfilteredList
	 *            the new legacyassessorunitstandardfilteredList list
	 * @see LegacyAssessorUnitStandard
	 */
	public void setLegacyAssessorUnitStandardfilteredList(List<LegacyAssessorUnitStandard> legacyassessorunitstandardfilteredList) {
		this.legacyassessorunitstandardfilteredList = legacyassessorunitstandardfilteredList;
	}

	public LazyDataModel<LegacyAssessorUnitStandard> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyAssessorUnitStandard> dataModel) {
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
			List<LegacyAssessorUnitStandard> csvDataList = csvUtil.getObjects(LegacyAssessorUnitStandard.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyassessorunitstandardInfo();
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
			legacyassessorunitstandardInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validiationRun();
			addInfoMessage("Action Complete");
			legacyassessorunitstandardInfo();
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

	public List<LegacyAssessorUnitStandard> getLegacyassessorunitstandardList() {
		return legacyassessorunitstandardList;
	}

	public void setLegacyassessorunitstandardList(List<LegacyAssessorUnitStandard> legacyassessorunitstandardList) {
		this.legacyassessorunitstandardList = legacyassessorunitstandardList;
	}

	public List<LegacyAssessorUnitStandard> getLegacyassessorunitstandardfilteredList() {
		return legacyassessorunitstandardfilteredList;
	}

	public void setLegacyassessorunitstandardfilteredList(List<LegacyAssessorUnitStandard> legacyassessorunitstandardfilteredList) {
		this.legacyassessorunitstandardfilteredList = legacyassessorunitstandardfilteredList;
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
