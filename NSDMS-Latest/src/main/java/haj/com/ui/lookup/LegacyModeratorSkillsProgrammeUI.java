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
import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyModeratorSkillsProgrammeService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacymoderatorskillsprogrammeUI")
@ViewScoped
public class LegacyModeratorSkillsProgrammeUI extends AbstractUI {

	private LegacyModeratorSkillsProgrammeService service = new LegacyModeratorSkillsProgrammeService();
	private List<LegacyModeratorSkillsProgramme> legacymoderatorskillsprogrammeList = null;
	private List<LegacyModeratorSkillsProgramme> legacymoderatorskillsprogrammefilteredList = null;
	private LegacyModeratorSkillsProgramme legacymoderatorskillsprogramme = null;
	private LazyDataModel<LegacyModeratorSkillsProgramme> dataModel;

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
	 * Initialize method to get all LegacyModeratorSkillsProgramme and prepare a for
	 * a create of a new LegacyModeratorSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorSkillsProgramme
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacymoderatorskillsprogrammeInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyModeratorSkillsProgramme.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyModeratorSkillsProgramme for data table
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorSkillsProgramme
	 */
	public void legacymoderatorskillsprogrammeInfo() {

		dataModel = new LazyDataModel<LegacyModeratorSkillsProgramme>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyModeratorSkillsProgramme> retorno = new ArrayList<LegacyModeratorSkillsProgramme>();

			@Override
			public List<LegacyModeratorSkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyModeratorSkillsProgramme(LegacyModeratorSkillsProgramme.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyModeratorSkillsProgramme.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyModeratorSkillsProgramme obj) {
				return obj.getId();
			}

			@Override
			public LegacyModeratorSkillsProgramme getRowData(String rowKey) {
				for (LegacyModeratorSkillsProgramme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyModeratorSkillsProgramme into database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorSkillsProgramme
	 */
	public void legacymoderatorskillsprogrammeInsert() {
		try {
			service.create(this.legacymoderatorskillsprogramme);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatorskillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyModeratorSkillsProgramme in database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorSkillsProgramme
	 */
	public void legacymoderatorskillsprogrammeUpdate() {
		try {
			service.update(this.legacymoderatorskillsprogramme);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatorskillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyModeratorSkillsProgramme from database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorSkillsProgramme
	 */
	public void legacymoderatorskillsprogrammeDelete() {
		try {
			service.delete(this.legacymoderatorskillsprogramme);
			prepareNew();
			legacymoderatorskillsprogrammeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyModeratorSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorSkillsProgramme
	 */
	public void prepareNew() {
		legacymoderatorskillsprogramme = new LegacyModeratorSkillsProgramme();
	}

	/*
	 * public List<SelectItem> getLegacyModeratorSkillsProgrammeDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacymoderatorskillsprogrammeInfo(); for (LegacyModeratorSkillsProgramme ug
	 * : legacymoderatorskillsprogrammeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyModeratorSkillsProgramme> complete(String desc) {
		List<LegacyModeratorSkillsProgramme> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyModeratorSkillsProgramme> getLegacyModeratorSkillsProgrammeList() {
		return legacymoderatorskillsprogrammeList;
	}

	public void setLegacyModeratorSkillsProgrammeList(List<LegacyModeratorSkillsProgramme> legacymoderatorskillsprogrammeList) {
		this.legacymoderatorskillsprogrammeList = legacymoderatorskillsprogrammeList;
	}

	public LegacyModeratorSkillsProgramme getLegacymoderatorskillsprogramme() {
		return legacymoderatorskillsprogramme;
	}

	public void setLegacymoderatorskillsprogramme(LegacyModeratorSkillsProgramme legacymoderatorskillsprogramme) {
		this.legacymoderatorskillsprogramme = legacymoderatorskillsprogramme;
	}

	public List<LegacyModeratorSkillsProgramme> getLegacyModeratorSkillsProgrammefilteredList() {
		return legacymoderatorskillsprogrammefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacymoderatorskillsprogrammefilteredList
	 *            the new legacymoderatorskillsprogrammefilteredList list
	 * @see LegacyModeratorSkillsProgramme
	 */
	public void setLegacyModeratorSkillsProgrammefilteredList(List<LegacyModeratorSkillsProgramme> legacymoderatorskillsprogrammefilteredList) {
		this.legacymoderatorskillsprogrammefilteredList = legacymoderatorskillsprogrammefilteredList;
	}

	public LazyDataModel<LegacyModeratorSkillsProgramme> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyModeratorSkillsProgramme> dataModel) {
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
			List<LegacyModeratorSkillsProgramme> csvDataList = csvUtil.getObjects(LegacyModeratorSkillsProgramme.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacymoderatorskillsprogrammeInfo();
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
			legacymoderatorskillsprogrammeInfo();
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
			legacymoderatorskillsprogrammeInfo();
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

	public List<LegacyModeratorSkillsProgramme> getLegacymoderatorskillsprogrammeList() {
		return legacymoderatorskillsprogrammeList;
	}

	public void setLegacymoderatorskillsprogrammeList(List<LegacyModeratorSkillsProgramme> legacymoderatorskillsprogrammeList) {
		this.legacymoderatorskillsprogrammeList = legacymoderatorskillsprogrammeList;
	}

	public List<LegacyModeratorSkillsProgramme> getLegacymoderatorskillsprogrammefilteredList() {
		return legacymoderatorskillsprogrammefilteredList;
	}

	public void setLegacymoderatorskillsprogrammefilteredList(List<LegacyModeratorSkillsProgramme> legacymoderatorskillsprogrammefilteredList) {
		this.legacymoderatorskillsprogrammefilteredList = legacymoderatorskillsprogrammefilteredList;
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
