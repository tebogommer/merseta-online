package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.service.lookup.LegacySkillsProgrammeService;
import haj.com.service.lookup.ReportingService;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyskillsprogrammeUI")
@ViewScoped
public class LegacySkillsProgrammeUI extends AbstractUI {

	private LegacySkillsProgrammeService service = new LegacySkillsProgrammeService();
	private List<LegacySkillsProgramme> legacyskillsprogrammeList = null;
	private List<LegacySkillsProgramme> legacyskillsprogrammefilteredList = null;
	private LegacySkillsProgramme legacyskillsprogramme = null;
	private LazyDataModel<LegacySkillsProgramme> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;

	private boolean displayDownload = false;
	// reporting
	private ReportingService reportingService = new ReportingService();
	private List<LegacyReportingBean> legacyReportingBeans = new ArrayList<>();
	private Map<String, List<Object>> reportingMap;
	private Map<String, ColumnBeans> columnMap;
	
	private String levyNumber;
	private String accreditationNumber;

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
	 * Initialize method to get all LegacySkillsProgramme and prepare a for a create
	 * of a new LegacySkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgramme
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyskillsprogrammeInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyUnitStandard.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacySkillsProgramme for data table
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgramme
	 */
	public void legacyskillsprogrammeInfo() {

		dataModel = new LazyDataModel<LegacySkillsProgramme>() {

			private static final long serialVersionUID = 1L;
			private List<LegacySkillsProgramme> retorno = new ArrayList<LegacySkillsProgramme>();

			@Override
			public List<LegacySkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacySkillsProgramme(LegacySkillsProgramme.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacySkillsProgramme.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacySkillsProgramme obj) {
				return obj.getId();
			}

			@Override
			public LegacySkillsProgramme getRowData(String rowKey) {
				for (LegacySkillsProgramme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacySkillsProgramme into database
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgramme
	 */
	public void legacyskillsprogrammeInsert() {
		try {
			service.create(this.legacyskillsprogramme);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyskillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacySkillsProgramme in database
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgramme
	 */
	public void legacyskillsprogrammeUpdate() {
		try {
			service.update(this.legacyskillsprogramme);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyskillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Update LegacySkillsProgramme in database
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgramme
	 */
	public void legacyAccreditationUpdate() {
		try {
			if(levyNumber.matches("")) {
				throw new Exception("Please provide SDL number");
			}
			if(accreditationNumber.matches("")) {
				throw new Exception("Please provide accreditation number");
			}
			service.legacyAccreditationUpdate(levyNumber, accreditationNumber);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyskillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacySkillsProgramme from database
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgramme
	 */
	public void legacyskillsprogrammeDelete() {
		try {
			service.delete(this.legacyskillsprogramme);
			prepareNew();
			legacyskillsprogrammeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	
	public void runQualificationValidiations() {
		try {
			service.validateQualification();
			addInfoMessage("Action Complete");
			legacyskillsprogrammeInfo();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runSkillsSetValidiations() {
		try {
			service.runSkillsSetValidiations();
			addInfoMessage("Action Complete");
			legacyskillsprogrammeInfo();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	/**
	 * Create new instance of LegacySkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgramme
	 */
	public void prepareNew() {
		legacyskillsprogramme = new LegacySkillsProgramme();
	}

	public void prepAccreditaionUpdate() {
		levyNumber = "";
		accreditationNumber = "";
	}
	/*
	 * public List<SelectItem> getLegacySkillsProgrammeDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyskillsprogrammeInfo(); for (LegacySkillsProgramme ug :
	 * legacyskillsprogrammeList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacySkillsProgramme> complete(String desc) {
		List<LegacySkillsProgramme> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacySkillsProgramme> getLegacySkillsProgrammeList() {
		return legacyskillsprogrammeList;
	}

	public void setLegacySkillsProgrammeList(List<LegacySkillsProgramme> legacyskillsprogrammeList) {
		this.legacyskillsprogrammeList = legacyskillsprogrammeList;
	}

	public LegacySkillsProgramme getLegacyskillsprogramme() {
		return legacyskillsprogramme;
	}

	public void setLegacyskillsprogramme(LegacySkillsProgramme legacyskillsprogramme) {
		this.legacyskillsprogramme = legacyskillsprogramme;
	}

	public List<LegacySkillsProgramme> getLegacySkillsProgrammefilteredList() {
		return legacyskillsprogrammefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyskillsprogrammefilteredList
	 *            the new legacyskillsprogrammefilteredList list
	 * @see LegacySkillsProgramme
	 */
	public void setLegacySkillsProgrammefilteredList(List<LegacySkillsProgramme> legacyskillsprogrammefilteredList) {
		this.legacyskillsprogrammefilteredList = legacyskillsprogrammefilteredList;
	}

	public LazyDataModel<LegacySkillsProgramme> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacySkillsProgramme> dataModel) {
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
			List<LegacySkillsProgramme> csvDataList = csvUtil.getObjects(LegacySkillsProgramme.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyskillsprogrammeInfo();
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
			legacyskillsprogrammeInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacyskillsprogrammeInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReport(){
		try {
			service.downloadReport();
		} catch (Exception e) {
			e.printStackTrace();
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

	public List<LegacySkillsProgramme> getLegacyskillsprogrammeList() {
		return legacyskillsprogrammeList;
	}

	public void setLegacyskillsprogrammeList(List<LegacySkillsProgramme> legacyskillsprogrammeList) {
		this.legacyskillsprogrammeList = legacyskillsprogrammeList;
	}

	public List<LegacySkillsProgramme> getLegacyskillsprogrammefilteredList() {
		return legacyskillsprogrammefilteredList;
	}

	public void setLegacyskillsprogrammefilteredList(List<LegacySkillsProgramme> legacyskillsprogrammefilteredList) {
		this.legacyskillsprogrammefilteredList = legacyskillsprogrammefilteredList;
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

	public String getLevyNumber() {
		return levyNumber;
	}

	public String getAccreditationNumber() {
		return accreditationNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	public void setAccreditationNumber(String accreditationNumber) {
		this.accreditationNumber = accreditationNumber;
	}
}
