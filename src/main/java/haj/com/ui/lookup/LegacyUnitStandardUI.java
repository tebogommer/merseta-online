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
import haj.com.entity.lookup.LegacyTvet;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.service.lookup.LegacyUnitStandardService;
import haj.com.service.lookup.ReportingService;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyunitstandardUI")
@ViewScoped
public class LegacyUnitStandardUI extends AbstractUI {

	private LegacyUnitStandardService service = new LegacyUnitStandardService();
	private List<LegacyUnitStandard> legacyunitstandardList = null;
	private List<LegacyUnitStandard> legacyunitstandardfilteredList = null;
	private LegacyUnitStandard legacyunitstandard = null;
	private LazyDataModel<LegacyUnitStandard> dataModel;

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
	 * Initialize method to get all LegacyUnitStandard and prepare a for a create of
	 * a new LegacyUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandard
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyunitstandardInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyUnitStandard.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyUnitStandard for data table
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandard
	 */
	public void legacyunitstandardInfo() {

		dataModel = new LazyDataModel<LegacyUnitStandard>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyUnitStandard> retorno = new ArrayList<LegacyUnitStandard>();

			@Override
			public List<LegacyUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyUnitStandard(LegacyUnitStandard.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyUnitStandard.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyUnitStandard obj) {
				return obj.getId();
			}

			@Override
			public LegacyUnitStandard getRowData(String rowKey) {
				for (LegacyUnitStandard obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyUnitStandard into database
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandard
	 */
	public void legacyunitstandardInsert() {
		try {
			service.create(this.legacyunitstandard);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyUnitStandard in database
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandard
	 */
	public void legacyunitstandardUpdate() {
		try {
			service.update(this.legacyunitstandard);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyUnitStandard from database
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandard
	 */
	public void legacyunitstandardDelete() {
		try {
			service.delete(this.legacyunitstandard);
			prepareNew();
			legacyunitstandardInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandard
	 */
	public void prepareNew() {
		legacyunitstandard = new LegacyUnitStandard();
	}

	/*
	 * public List<SelectItem> getLegacyUnitStandardDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyunitstandardInfo(); for (LegacyUnitStandard ug :
	 * legacyunitstandardList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyUnitStandard> complete(String desc) {
		List<LegacyUnitStandard> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void prepAccreditaionUpdate() {
		levyNumber = "";
		accreditationNumber = "";
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
			legacyunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runQualificationValidiations() {
		try {
			service.validateQualification();
			addInfoMessage("Action Complete");
			legacyunitstandardInfo();
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

	public List<LegacyUnitStandard> getLegacyUnitStandardList() {
		return legacyunitstandardList;
	}

	public void setLegacyUnitStandardList(List<LegacyUnitStandard> legacyunitstandardList) {
		this.legacyunitstandardList = legacyunitstandardList;
	}

	public LegacyUnitStandard getLegacyunitstandard() {
		return legacyunitstandard;
	}

	public void setLegacyunitstandard(LegacyUnitStandard legacyunitstandard) {
		this.legacyunitstandard = legacyunitstandard;
	}

	public List<LegacyUnitStandard> getLegacyUnitStandardfilteredList() {
		return legacyunitstandardfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyunitstandardfilteredList
	 *            the new legacyunitstandardfilteredList list
	 * @see LegacyUnitStandard
	 */
	public void setLegacyUnitStandardfilteredList(List<LegacyUnitStandard> legacyunitstandardfilteredList) {
		this.legacyunitstandardfilteredList = legacyunitstandardfilteredList;
	}

	public LazyDataModel<LegacyUnitStandard> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyUnitStandard> dataModel) {
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
			List<LegacyUnitStandard> csvDataList = csvUtil.getObjects(LegacyUnitStandard.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyunitstandardInfo();
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
			legacyunitstandardInfo();
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

	public List<LegacyUnitStandard> getLegacyunitstandardList() {
		return legacyunitstandardList;
	}

	public void setLegacyunitstandardList(List<LegacyUnitStandard> legacyunitstandardList) {
		this.legacyunitstandardList = legacyunitstandardList;
	}

	public List<LegacyUnitStandard> getLegacyunitstandardfilteredList() {
		return legacyunitstandardfilteredList;
	}

	public void setLegacyunitstandardfilteredList(List<LegacyUnitStandard> legacyunitstandardfilteredList) {
		this.legacyunitstandardfilteredList = legacyunitstandardfilteredList;
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
