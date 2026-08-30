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
import haj.com.entity.lookup.LegacyProviderUnitStandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyProviderUnitStandardService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyproviderunitstandardUI")
@ViewScoped
public class LegacyProviderUnitStandardUI extends AbstractUI {

	private LegacyProviderUnitStandardService service = new LegacyProviderUnitStandardService();
	private List<LegacyProviderUnitStandard> legacyproviderunitstandardList = null;
	private List<LegacyProviderUnitStandard> legacyproviderunitstandardfilteredList = null;
	private LegacyProviderUnitStandard legacyproviderunitstandard = null;
	private LazyDataModel<LegacyProviderUnitStandard> dataModel;

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
	 * Initialize method to get all LegacyProviderUnitStandard and prepare a for a
	 * create of a new LegacyProviderUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyProviderUnitStandard
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyproviderunitstandardInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyProviderUnitStandard.class, true);
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
	 * Get all LegacyProviderUnitStandard for data table
	 * 
	 * @author TechFinium
	 * @see LegacyProviderUnitStandard
	 */
	public void legacyproviderunitstandardInfo() {

		dataModel = new LazyDataModel<LegacyProviderUnitStandard>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderUnitStandard> retorno = new ArrayList<LegacyProviderUnitStandard>();

			@Override
			public List<LegacyProviderUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyProviderUnitStandard(LegacyProviderUnitStandard.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyProviderUnitStandard.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderUnitStandard obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderUnitStandard getRowData(String rowKey) {
				for (LegacyProviderUnitStandard obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyProviderUnitStandard into database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderUnitStandard
	 */
	public void legacyproviderunitstandardInsert() {
		try {
			service.create(this.legacyproviderunitstandard);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyproviderunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runProcessForEntry(){
		try {
			service.runValidiationForSingleEntry(this.legacyproviderunitstandard);
			service.update(this.legacyproviderunitstandard);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyproviderunitstandardInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyProviderUnitStandard in database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderUnitStandard
	 */
	public void legacyproviderunitstandardUpdate() {
		try {
			service.update(this.legacyproviderunitstandard);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			prepareNew();
			legacyproviderunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyProviderUnitStandard from database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderUnitStandard
	 */
	public void legacyproviderunitstandardDelete() {
		try {
			service.delete(this.legacyproviderunitstandard);
			prepareNew();
			legacyproviderunitstandardInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyProviderUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyProviderUnitStandard
	 */
	public void prepareNew() {
		legacyproviderunitstandard = new LegacyProviderUnitStandard();
	}

	/*
	 * public List<SelectItem> getLegacyProviderUnitStandardDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyproviderunitstandardInfo(); for (LegacyProviderUnitStandard ug :
	 * legacyproviderunitstandardList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyProviderUnitStandard> complete(String desc) {
		List<LegacyProviderUnitStandard> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyProviderUnitStandard> getLegacyProviderUnitStandardList() {
		return legacyproviderunitstandardList;
	}

	public void setLegacyProviderUnitStandardList(List<LegacyProviderUnitStandard> legacyproviderunitstandardList) {
		this.legacyproviderunitstandardList = legacyproviderunitstandardList;
	}

	public LegacyProviderUnitStandard getLegacyproviderunitstandard() {
		return legacyproviderunitstandard;
	}

	public void setLegacyproviderunitstandard(LegacyProviderUnitStandard legacyproviderunitstandard) {
		this.legacyproviderunitstandard = legacyproviderunitstandard;
	}

	public List<LegacyProviderUnitStandard> getLegacyProviderUnitStandardfilteredList() {
		return legacyproviderunitstandardfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyproviderunitstandardfilteredList
	 *            the new legacyproviderunitstandardfilteredList list
	 * @see LegacyProviderUnitStandard
	 */
	public void setLegacyProviderUnitStandardfilteredList(List<LegacyProviderUnitStandard> legacyproviderunitstandardfilteredList) {
		this.legacyproviderunitstandardfilteredList = legacyproviderunitstandardfilteredList;
	}

	public LazyDataModel<LegacyProviderUnitStandard> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyProviderUnitStandard> dataModel) {
		this.dataModel = dataModel;
	}

	public void prepTypeSelection() {
		try {
			prepareNew();
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
			List<LegacyProviderUnitStandard> csvDataList = csvUtil.getObjects(LegacyProviderUnitStandard.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyproviderunitstandardInfo();
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
			prepareNew();
			service.deleteUploadedEntries();
			addInfoMessage("data cleared");
			legacyproviderunitstandardInfo();
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

	public List<LegacyProviderUnitStandard> getLegacyproviderunitstandardList() {
		return legacyproviderunitstandardList;
	}

	public void setLegacyproviderunitstandardList(List<LegacyProviderUnitStandard> legacyproviderunitstandardList) {
		this.legacyproviderunitstandardList = legacyproviderunitstandardList;
	}

	public List<LegacyProviderUnitStandard> getLegacyproviderunitstandardfilteredList() {
		return legacyproviderunitstandardfilteredList;
	}

	public void setLegacyproviderunitstandardfilteredList(List<LegacyProviderUnitStandard> legacyproviderunitstandardfilteredList) {
		this.legacyproviderunitstandardfilteredList = legacyproviderunitstandardfilteredList;
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
