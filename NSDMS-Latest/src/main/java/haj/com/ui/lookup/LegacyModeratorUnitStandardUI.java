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
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.entity.lookup.LegacyModeratorUnitStandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyModeratorUnitStandardService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacymoderatorunitstandardUI")
@ViewScoped
public class LegacyModeratorUnitStandardUI extends AbstractUI {

	private LegacyModeratorUnitStandardService service = new LegacyModeratorUnitStandardService();
	private List<LegacyModeratorUnitStandard> legacymoderatorunitstandardList = null;
	private List<LegacyModeratorUnitStandard> legacymoderatorunitstandardfilteredList = null;
	private LegacyModeratorUnitStandard legacymoderatorunitstandard = null;
	private LazyDataModel<LegacyModeratorUnitStandard> dataModel;

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
	 * Initialize method to get all LegacyModeratorUnitStandard and prepare a for a
	 * create of a new LegacyModeratorUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorUnitStandard
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacymoderatorunitstandardInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyModeratorAccreditation.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyModeratorUnitStandard for data table
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorUnitStandard
	 */
	public void legacymoderatorunitstandardInfo() {

		dataModel = new LazyDataModel<LegacyModeratorUnitStandard>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyModeratorUnitStandard> retorno = new ArrayList<LegacyModeratorUnitStandard>();

			@Override
			public List<LegacyModeratorUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyModeratorUnitStandard(LegacyModeratorUnitStandard.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyModeratorUnitStandard.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyModeratorUnitStandard obj) {
				return obj.getId();
			}

			@Override
			public LegacyModeratorUnitStandard getRowData(String rowKey) {
				for (LegacyModeratorUnitStandard obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyModeratorUnitStandard into database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorUnitStandard
	 */
	public void legacymoderatorunitstandardInsert() {
		try {
			service.create(this.legacymoderatorunitstandard);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatorunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyModeratorUnitStandard in database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorUnitStandard
	 */
	public void legacymoderatorunitstandardUpdate() {
		try {
			service.update(this.legacymoderatorunitstandard);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatorunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyModeratorUnitStandard from database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorUnitStandard
	 */
	public void legacymoderatorunitstandardDelete() {
		try {
			service.delete(this.legacymoderatorunitstandard);
			prepareNew();
			legacymoderatorunitstandardInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyModeratorUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorUnitStandard
	 */
	public void prepareNew() {
		legacymoderatorunitstandard = new LegacyModeratorUnitStandard();
	}

	/*
	 * public List<SelectItem> getLegacyModeratorUnitStandardDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacymoderatorunitstandardInfo(); for (LegacyModeratorUnitStandard ug :
	 * legacymoderatorunitstandardList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyModeratorUnitStandard> complete(String desc) {
		List<LegacyModeratorUnitStandard> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyModeratorUnitStandard> getLegacyModeratorUnitStandardList() {
		return legacymoderatorunitstandardList;
	}

	public void setLegacyModeratorUnitStandardList(List<LegacyModeratorUnitStandard> legacymoderatorunitstandardList) {
		this.legacymoderatorunitstandardList = legacymoderatorunitstandardList;
	}

	public LegacyModeratorUnitStandard getLegacymoderatorunitstandard() {
		return legacymoderatorunitstandard;
	}

	public void setLegacymoderatorunitstandard(LegacyModeratorUnitStandard legacymoderatorunitstandard) {
		this.legacymoderatorunitstandard = legacymoderatorunitstandard;
	}

	public List<LegacyModeratorUnitStandard> getLegacyModeratorUnitStandardfilteredList() {
		return legacymoderatorunitstandardfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacymoderatorunitstandardfilteredList
	 *            the new legacymoderatorunitstandardfilteredList list
	 * @see LegacyModeratorUnitStandard
	 */
	public void setLegacyModeratorUnitStandardfilteredList(List<LegacyModeratorUnitStandard> legacymoderatorunitstandardfilteredList) {
		this.legacymoderatorunitstandardfilteredList = legacymoderatorunitstandardfilteredList;
	}

	public LazyDataModel<LegacyModeratorUnitStandard> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyModeratorUnitStandard> dataModel) {
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
			List<LegacyModeratorUnitStandard> csvDataList = csvUtil.getObjects(LegacyModeratorUnitStandard.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacymoderatorunitstandardInfo();
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
			legacymoderatorunitstandardInfo();
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
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacymoderatorunitstandardInfo();
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

	public List<LegacyModeratorUnitStandard> getLegacymoderatorunitstandardList() {
		return legacymoderatorunitstandardList;
	}

	public void setLegacymoderatorunitstandardList(List<LegacyModeratorUnitStandard> legacymoderatorunitstandardList) {
		this.legacymoderatorunitstandardList = legacymoderatorunitstandardList;
	}

	public List<LegacyModeratorUnitStandard> getLegacymoderatorunitstandardfilteredList() {
		return legacymoderatorunitstandardfilteredList;
	}

	public void setLegacymoderatorunitstandardfilteredList(List<LegacyModeratorUnitStandard> legacymoderatorunitstandardfilteredList) {
		this.legacymoderatorunitstandardfilteredList = legacymoderatorunitstandardfilteredList;
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
