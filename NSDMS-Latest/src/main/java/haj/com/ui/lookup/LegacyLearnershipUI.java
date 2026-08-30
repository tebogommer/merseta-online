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
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacyTvet;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyLearnershipService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacylearnershipUI")
@ViewScoped
public class LegacyLearnershipUI extends AbstractUI {

	private LegacyLearnershipService service = new LegacyLearnershipService();
	private List<LegacyLearnership> legacylearnershipList = null;
	private List<LegacyLearnership> legacylearnershipfilteredList = null;
	private LegacyLearnership legacylearnership = null;
	private LazyDataModel<LegacyLearnership> dataModel;

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
	private String legacyCode;
	private String newCode;
	private String qualificationCode;

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
	 * Initialize method to get all LegacyLearnership and prepare a for a create of
	 * a new LegacyLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyLearnership
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacylearnershipInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyLearnership.class, true);
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
	 * Get all LegacyLearnership for data table
	 * 
	 * @author TechFinium
	 * @see LegacyLearnership
	 */
	public void legacylearnershipInfo() {

		dataModel = new LazyDataModel<LegacyLearnership>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyLearnership> retorno = new ArrayList<LegacyLearnership>();

			@Override
			public List<LegacyLearnership> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyLearnership(LegacyLearnership.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyLearnership.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyLearnership obj) {
				return obj.getId();
			}

			@Override
			public LegacyLearnership getRowData(String rowKey) {
				for (LegacyLearnership obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyLearnership into database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnership
	 */
	public void legacylearnershipInsert() {
		try {
			service.create(this.legacylearnership);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacylearnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyLearnership in database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnership
	 */
	public void legacylearnershipUpdate() {
		try {
			service.update(this.legacylearnership);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacylearnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyLearnership from database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnership
	 */
	public void legacylearnershipDelete() {
		try {
			service.delete(this.legacylearnership);
			prepareNew();
			legacylearnershipInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyLearnership
	 */
	public void prepareNew() {
		legacylearnership = new LegacyLearnership();
	}
	
	
	public void prepareLegacyLearnership() {
		try {
			if(legacylearnership != null && legacylearnership.getId() != null) {
				if(legacylearnership.getLearnership() == null) {
					legacylearnership.setLearnership(new Learnership());
					super.runClientSideExecute("PF('updateDialog').hide()");
				}
			}else {
				throw new Exception("Error with the legacy learnership");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/*
	 * public List<SelectItem> getLegacyLearnershipDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacylearnershipInfo(); for (LegacyLearnership ug : legacylearnershipList) {
	 * // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyLearnership> complete(String desc) {
		List<LegacyLearnership> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void downloadReport(){
		try {
			service.downloadReport();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<LegacyLearnership> getLegacyLearnershipList() {
		return legacylearnershipList;
	}

	public void setLegacyLearnershipList(List<LegacyLearnership> legacylearnershipList) {
		this.legacylearnershipList = legacylearnershipList;
	}

	public LegacyLearnership getLegacylearnership() {
		return legacylearnership;
	}

	public void setLegacylearnership(LegacyLearnership legacylearnership) {
		this.legacylearnership = legacylearnership;
	}

	public List<LegacyLearnership> getLegacyLearnershipfilteredList() {
		return legacylearnershipfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacylearnershipfilteredList
	 *            the new legacylearnershipfilteredList list
	 * @see LegacyLearnership
	 */
	public void setLegacyLearnershipfilteredList(List<LegacyLearnership> legacylearnershipfilteredList) {
		this.legacylearnershipfilteredList = legacylearnershipfilteredList;
	}

	public LazyDataModel<LegacyLearnership> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyLearnership> dataModel) {
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
			List<LegacyLearnership> csvDataList = csvUtil.getObjects(LegacyLearnership.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacylearnershipInfo();
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
			legacylearnershipInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacylearnershipInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runQualificationValidiations() {
		try {
			service.validateQualification();
			addInfoMessage("Action Complete");
			legacylearnershipInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	
	public void prepAccreditaionUpdate() {
		levyNumber = "";
		accreditationNumber = "";
		legacyCode= "";
		newCode= "";
		qualificationCode= "";
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
			legacylearnershipInfo();
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
	public void legacyLearnershipUpdate() {
		try {
			if(legacyCode.matches("")) {
				throw new Exception("Please legacy data code");
			}
			if(newCode.matches("")) {
				throw new Exception("Please provide new code");
			}
			
			service.legacyLearnershipUpdate(legacyCode, newCode, qualificationCode);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacylearnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
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

	public List<LegacyLearnership> getLegacylearnershipList() {
		return legacylearnershipList;
	}

	public void setLegacylearnershipList(List<LegacyLearnership> legacylearnershipList) {
		this.legacylearnershipList = legacylearnershipList;
	}

	public List<LegacyLearnership> getLegacylearnershipfilteredList() {
		return legacylearnershipfilteredList;
	}

	public void setLegacylearnershipfilteredList(List<LegacyLearnership> legacylearnershipfilteredList) {
		this.legacylearnershipfilteredList = legacylearnershipfilteredList;
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

	public String getLegacyCode() {
		return legacyCode;
	}

	public String getNewCode() {
		return newCode;
	}

	public void setLegacyCode(String legacyCode) {
		this.legacyCode = legacyCode;
	}

	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}

	public String getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

}
