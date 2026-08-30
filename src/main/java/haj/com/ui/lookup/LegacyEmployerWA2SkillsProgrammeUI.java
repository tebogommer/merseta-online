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
import haj.com.entity.lookup.LegacyEmployerWA2SkillsProgramme;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyEmployerWA2SkillsProgrammeService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyemployerwa2skillsprogrammeUI")
@ViewScoped
public class LegacyEmployerWA2SkillsProgrammeUI extends AbstractUI {

	private LegacyEmployerWA2SkillsProgrammeService service = new LegacyEmployerWA2SkillsProgrammeService();
	private List<LegacyEmployerWA2SkillsProgramme> legacyemployerwa2skillsprogrammeList = null;
	private List<LegacyEmployerWA2SkillsProgramme> legacyemployerwa2skillsprogrammefilteredList = null;
	private LegacyEmployerWA2SkillsProgramme legacyemployerwa2skillsprogramme = null;
	private LazyDataModel<LegacyEmployerWA2SkillsProgramme> dataModel;

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
	 * Initialize method to get all LegacyEmployerWA2SkillsProgramme and prepare a
	 * for a create of a new LegacyEmployerWA2SkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2SkillsProgramme
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyemployerwa2skillsprogrammeInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyEmployerWA2SkillsProgramme.class, true);
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
	 * Get all LegacyEmployerWA2SkillsProgramme for data table
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2SkillsProgramme
	 */
	public void legacyemployerwa2skillsprogrammeInfo() {

		dataModel = new LazyDataModel<LegacyEmployerWA2SkillsProgramme>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyEmployerWA2SkillsProgramme> retorno = new ArrayList<LegacyEmployerWA2SkillsProgramme>();

			@Override
			public List<LegacyEmployerWA2SkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyEmployerWA2SkillsProgramme(LegacyEmployerWA2SkillsProgramme.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyEmployerWA2SkillsProgramme.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyEmployerWA2SkillsProgramme obj) {
				return obj.getId();
			}

			@Override
			public LegacyEmployerWA2SkillsProgramme getRowData(String rowKey) {
				for (LegacyEmployerWA2SkillsProgramme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyEmployerWA2SkillsProgramme into database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2SkillsProgramme
	 */
	public void legacyemployerwa2skillsprogrammeInsert() {
		try {
			service.create(this.legacyemployerwa2skillsprogramme);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2skillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyEmployerWA2SkillsProgramme in database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2SkillsProgramme
	 */
	public void legacyemployerwa2skillsprogrammeUpdate() {
		try {
			service.update(this.legacyemployerwa2skillsprogramme);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2skillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyEmployerWA2SkillsProgramme from database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2SkillsProgramme
	 */
	public void legacyemployerwa2skillsprogrammeDelete() {
		try {
			service.delete(this.legacyemployerwa2skillsprogramme);
			prepareNew();
			legacyemployerwa2skillsprogrammeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyEmployerWA2SkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2SkillsProgramme
	 */
	public void prepareNew() {
		legacyemployerwa2skillsprogramme = new LegacyEmployerWA2SkillsProgramme();
	}

	/*
	 * public List<SelectItem> getLegacyEmployerWA2SkillsProgrammeDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyemployerwa2skillsprogrammeInfo(); for (LegacyEmployerWA2SkillsProgramme
	 * ug : legacyemployerwa2skillsprogrammeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyEmployerWA2SkillsProgramme> complete(String desc) {
		List<LegacyEmployerWA2SkillsProgramme> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyEmployerWA2SkillsProgramme> getLegacyEmployerWA2SkillsProgrammeList() {
		return legacyemployerwa2skillsprogrammeList;
	}

	public void setLegacyEmployerWA2SkillsProgrammeList(List<LegacyEmployerWA2SkillsProgramme> legacyemployerwa2skillsprogrammeList) {
		this.legacyemployerwa2skillsprogrammeList = legacyemployerwa2skillsprogrammeList;
	}

	public LegacyEmployerWA2SkillsProgramme getLegacyemployerwa2skillsprogramme() {
		return legacyemployerwa2skillsprogramme;
	}

	public void setLegacyemployerwa2skillsprogramme(LegacyEmployerWA2SkillsProgramme legacyemployerwa2skillsprogramme) {
		this.legacyemployerwa2skillsprogramme = legacyemployerwa2skillsprogramme;
	}

	public List<LegacyEmployerWA2SkillsProgramme> getLegacyEmployerWA2SkillsProgrammefilteredList() {
		return legacyemployerwa2skillsprogrammefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyemployerwa2skillsprogrammefilteredList
	 *            the new legacyemployerwa2skillsprogrammefilteredList list
	 * @see LegacyEmployerWA2SkillsProgramme
	 */
	public void setLegacyEmployerWA2SkillsProgrammefilteredList(List<LegacyEmployerWA2SkillsProgramme> legacyemployerwa2skillsprogrammefilteredList) {
		this.legacyemployerwa2skillsprogrammefilteredList = legacyemployerwa2skillsprogrammefilteredList;
	}

	public LazyDataModel<LegacyEmployerWA2SkillsProgramme> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyEmployerWA2SkillsProgramme> dataModel) {
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
			List<LegacyEmployerWA2SkillsProgramme> csvDataList = csvUtil.getObjects(LegacyEmployerWA2SkillsProgramme.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyemployerwa2skillsprogrammeInfo();
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
			legacyemployerwa2skillsprogrammeInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacyemployerwa2skillsprogrammeInfo();
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
