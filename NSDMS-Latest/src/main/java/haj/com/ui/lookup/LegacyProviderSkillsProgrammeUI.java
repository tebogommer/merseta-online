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
import haj.com.entity.lookup.LegacyProviderSkillsProgramme;
import haj.com.service.lookup.LegacyProviderSkillsProgrammeService;
import haj.com.service.lookup.ReportingService;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyproviderskillsprogrammeUI")
@ViewScoped
public class LegacyProviderSkillsProgrammeUI extends AbstractUI {

	private LegacyProviderSkillsProgrammeService service = new LegacyProviderSkillsProgrammeService();
	private List<LegacyProviderSkillsProgramme> legacyproviderskillsprogrammeList = null;
	private List<LegacyProviderSkillsProgramme> legacyproviderskillsprogrammefilteredList = null;
	private LegacyProviderSkillsProgramme legacyproviderskillsprogramme = null;
	private LazyDataModel<LegacyProviderSkillsProgramme> dataModel;

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
	 * Initialize method to get all LegacyProviderSkillsProgramme and prepare a for
	 * a create of a new LegacyProviderSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyProviderSkillsProgramme
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyproviderskillsprogrammeInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyProviderSkillsProgramme.class, true);
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
	 * Get all LegacyProviderSkillsProgramme for data table
	 * 
	 * @author TechFinium
	 * @see LegacyProviderSkillsProgramme
	 */
	public void legacyproviderskillsprogrammeInfo() {

		dataModel = new LazyDataModel<LegacyProviderSkillsProgramme>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderSkillsProgramme> retorno = new ArrayList<LegacyProviderSkillsProgramme>();

			@Override
			public List<LegacyProviderSkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyProviderSkillsProgramme(LegacyProviderSkillsProgramme.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyProviderSkillsProgramme.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderSkillsProgramme obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderSkillsProgramme getRowData(String rowKey) {
				for (LegacyProviderSkillsProgramme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyProviderSkillsProgramme into database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderSkillsProgramme
	 */
	public void legacyproviderskillsprogrammeInsert() {
		try {
			service.runValidiationForSingleEntry(this.legacyproviderskillsprogramme);
			service.create(this.legacyproviderskillsprogramme);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyproviderskillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyProviderSkillsProgramme in database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderSkillsProgramme
	 */
	public void legacyproviderskillsprogrammeUpdate() {
		try {
			service.runValidiationForSingleEntry(this.legacyproviderskillsprogramme);
			service.update(this.legacyproviderskillsprogramme);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			prepareNew();
			legacyproviderskillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runProcessForEntry(){
		try {
			service.runValidiationForSingleEntry(this.legacyproviderskillsprogramme);
			service.update(this.legacyproviderskillsprogramme);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyproviderskillsprogrammeInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	/**
	 * Delete LegacyProviderSkillsProgramme from database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderSkillsProgramme
	 */
	public void legacyproviderskillsprogrammeDelete() {
		try {
			service.delete(this.legacyproviderskillsprogramme);
			prepareNew();
			legacyproviderskillsprogrammeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyProviderSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyProviderSkillsProgramme
	 */
	public void prepareNew() {
		legacyproviderskillsprogramme = new LegacyProviderSkillsProgramme();
	}
	
	public void correctData(){
		try {
			prepareNew();
			service.correctData();
			addInfoMessage("Action underway, email notification will be sent on completion");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/*
	 * public List<SelectItem> getLegacyProviderSkillsProgrammeDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyproviderskillsprogrammeInfo(); for (LegacyProviderSkillsProgramme ug :
	 * legacyproviderskillsprogrammeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyProviderSkillsProgramme> complete(String desc) {
		List<LegacyProviderSkillsProgramme> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyProviderSkillsProgramme> getLegacyProviderSkillsProgrammeList() {
		return legacyproviderskillsprogrammeList;
	}

	public void setLegacyProviderSkillsProgrammeList(List<LegacyProviderSkillsProgramme> legacyproviderskillsprogrammeList) {
		this.legacyproviderskillsprogrammeList = legacyproviderskillsprogrammeList;
	}

	public LegacyProviderSkillsProgramme getLegacyproviderskillsprogramme() {
		return legacyproviderskillsprogramme;
	}

	public void setLegacyproviderskillsprogramme(LegacyProviderSkillsProgramme legacyproviderskillsprogramme) {
		this.legacyproviderskillsprogramme = legacyproviderskillsprogramme;
	}

	public List<LegacyProviderSkillsProgramme> getLegacyProviderSkillsProgrammefilteredList() {
		return legacyproviderskillsprogrammefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyproviderskillsprogrammefilteredList
	 *            the new legacyproviderskillsprogrammefilteredList list
	 * @see LegacyProviderSkillsProgramme
	 */
	public void setLegacyProviderSkillsProgrammefilteredList(List<LegacyProviderSkillsProgramme> legacyproviderskillsprogrammefilteredList) {
		this.legacyproviderskillsprogrammefilteredList = legacyproviderskillsprogrammefilteredList;
	}

	public LazyDataModel<LegacyProviderSkillsProgramme> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyProviderSkillsProgramme> dataModel) {
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
			List<LegacyProviderSkillsProgramme> csvDataList = csvUtil.getObjects(LegacyProviderSkillsProgramme.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyproviderskillsprogrammeInfo();
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
			legacyproviderskillsprogrammeInfo();
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

	public List<LegacyProviderSkillsProgramme> getLegacyproviderskillsprogrammeList() {
		return legacyproviderskillsprogrammeList;
	}

	public void setLegacyproviderskillsprogrammeList(List<LegacyProviderSkillsProgramme> legacyproviderskillsprogrammeList) {
		this.legacyproviderskillsprogrammeList = legacyproviderskillsprogrammeList;
	}

	public List<LegacyProviderSkillsProgramme> getLegacyproviderskillsprogrammefilteredList() {
		return legacyproviderskillsprogrammefilteredList;
	}

	public void setLegacyproviderskillsprogrammefilteredList(List<LegacyProviderSkillsProgramme> legacyproviderskillsprogrammefilteredList) {
		this.legacyproviderskillsprogrammefilteredList = legacyproviderskillsprogrammefilteredList;
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
