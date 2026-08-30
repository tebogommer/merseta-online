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
import haj.com.entity.lookup.LegacyProviderQualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyProviderQualificationService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyproviderqualificationUI")
@ViewScoped
public class LegacyProviderQualificationUI extends AbstractUI {

	private LegacyProviderQualificationService service = new LegacyProviderQualificationService();
	private List<LegacyProviderQualification> legacyproviderqualificationList = null;
	private List<LegacyProviderQualification> legacyproviderqualificationfilteredList = null;
	private LegacyProviderQualification legacyproviderqualification = null;
	private LazyDataModel<LegacyProviderQualification> dataModel;

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
	 * Initialize method to get all LegacyProviderQualification and prepare a for a
	 * create of a new LegacyProviderQualification
	 * 
	 * @author TechFinium
	 * @see LegacyProviderQualification
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyproviderqualificationInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyProviderQualification.class, true);
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
	 * Get all LegacyProviderQualification for data table
	 * 
	 * @author TechFinium
	 * @see LegacyProviderQualification
	 */
	public void legacyproviderqualificationInfo() {

		dataModel = new LazyDataModel<LegacyProviderQualification>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderQualification> retorno = new ArrayList<LegacyProviderQualification>();

			@Override
			public List<LegacyProviderQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyProviderQualification(LegacyProviderQualification.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyProviderQualification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderQualification obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderQualification getRowData(String rowKey) {
				for (LegacyProviderQualification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyProviderQualification into database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderQualification
	 */
	public void legacyproviderqualificationInsert() {
		try {
			service.processSingleEntry(this.legacyproviderqualification);
			service.create(this.legacyproviderqualification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyproviderqualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyProviderQualification in database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderQualification
	 */
	public void legacyproviderqualificationUpdate() {
		try {
			service.processSingleEntry(this.legacyproviderqualification);
			service.update(this.legacyproviderqualification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyproviderqualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runProcessForEntry(){
		try {
			service.processSingleEntry(this.legacyproviderqualification);
			service.update(this.legacyproviderqualification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyproviderqualificationInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyProviderQualification from database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderQualification
	 */
	public void legacyproviderqualificationDelete() {
		try {
			service.delete(this.legacyproviderqualification);
			prepareNew();
			legacyproviderqualificationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyProviderQualification
	 * 
	 * @author TechFinium
	 * @see LegacyProviderQualification
	 */
	public void prepareNew() {
		legacyproviderqualification = new LegacyProviderQualification();
	}

	/*
	 * public List<SelectItem> getLegacyProviderQualificationDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyproviderqualificationInfo(); for (LegacyProviderQualification ug :
	 * legacyproviderqualificationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyProviderQualification> complete(String desc) {
		List<LegacyProviderQualification> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyProviderQualification> getLegacyProviderQualificationList() {
		return legacyproviderqualificationList;
	}

	public void setLegacyProviderQualificationList(List<LegacyProviderQualification> legacyproviderqualificationList) {
		this.legacyproviderqualificationList = legacyproviderqualificationList;
	}

	public LegacyProviderQualification getLegacyproviderqualification() {
		return legacyproviderqualification;
	}

	public void setLegacyproviderqualification(LegacyProviderQualification legacyproviderqualification) {
		this.legacyproviderqualification = legacyproviderqualification;
	}

	public List<LegacyProviderQualification> getLegacyProviderQualificationfilteredList() {
		return legacyproviderqualificationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyproviderqualificationfilteredList
	 *            the new legacyproviderqualificationfilteredList list
	 * @see LegacyProviderQualification
	 */
	public void setLegacyProviderQualificationfilteredList(List<LegacyProviderQualification> legacyproviderqualificationfilteredList) {
		this.legacyproviderqualificationfilteredList = legacyproviderqualificationfilteredList;
	}

	public LazyDataModel<LegacyProviderQualification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyProviderQualification> dataModel) {
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
			List<LegacyProviderQualification> csvDataList = csvUtil.getObjects(LegacyProviderQualification.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyproviderqualificationInfo();
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
			legacyproviderqualificationInfo();
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

	public List<LegacyProviderQualification> getLegacyproviderqualificationList() {
		return legacyproviderqualificationList;
	}

	public void setLegacyproviderqualificationList(List<LegacyProviderQualification> legacyproviderqualificationList) {
		this.legacyproviderqualificationList = legacyproviderqualificationList;
	}

	public List<LegacyProviderQualification> getLegacyproviderqualificationfilteredList() {
		return legacyproviderqualificationfilteredList;
	}

	public void setLegacyproviderqualificationfilteredList(List<LegacyProviderQualification> legacyproviderqualificationfilteredList) {
		this.legacyproviderqualificationfilteredList = legacyproviderqualificationfilteredList;
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
