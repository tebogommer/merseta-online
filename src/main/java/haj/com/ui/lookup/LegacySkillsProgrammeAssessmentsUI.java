package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.lookup.LegacySkillsProgrammeAssessments;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.service.lookup.LegacySkillsProgrammeAssessmentsService;
import haj.com.service.lookup.ReportingService;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyskillsprogrammeassessmentsUI")
@ViewScoped
public class LegacySkillsProgrammeAssessmentsUI extends AbstractUI {

	private LegacySkillsProgrammeAssessmentsService service = new LegacySkillsProgrammeAssessmentsService();
	private List<LegacySkillsProgrammeAssessments> legacyskillsprogrammeassessmentsList = null;
	private List<LegacySkillsProgrammeAssessments> legacyskillsprogrammeassessmentsfilteredList = null;
	private LegacySkillsProgrammeAssessments legacyskillsprogrammeassessments = null;
	private LazyDataModel<LegacySkillsProgrammeAssessments> dataModel;

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
	 * Initialize method to get all LegacySkillsProgrammeAssessments and prepare a
	 * for a create of a new LegacySkillsProgrammeAssessments
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgrammeAssessments
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyskillsprogrammeassessmentsInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacySkillsProgrammeAssessments.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacySkillsProgrammeAssessments for data table
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgrammeAssessments
	 */
	public void legacyskillsprogrammeassessmentsInfo() {

		dataModel = new LazyDataModel<LegacySkillsProgrammeAssessments>() {

			private static final long serialVersionUID = 1L;
			private List<LegacySkillsProgrammeAssessments> retorno = new ArrayList<LegacySkillsProgrammeAssessments>();

			@Override
			public List<LegacySkillsProgrammeAssessments> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacySkillsProgrammeAssessments(LegacySkillsProgrammeAssessments.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacySkillsProgrammeAssessments.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacySkillsProgrammeAssessments obj) {
				return obj.getId();
			}

			@Override
			public LegacySkillsProgrammeAssessments getRowData(String rowKey) {
				for (LegacySkillsProgrammeAssessments obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacySkillsProgrammeAssessments into database
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgrammeAssessments
	 */
	public void legacyskillsprogrammeassessmentsInsert() {
		try {
			service.create(this.legacyskillsprogrammeassessments);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyskillsprogrammeassessmentsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacySkillsProgrammeAssessments in database
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgrammeAssessments
	 */
	public void legacyskillsprogrammeassessmentsUpdate() {
		try {
			service.update(this.legacyskillsprogrammeassessments);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyskillsprogrammeassessmentsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacySkillsProgrammeAssessments from database
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgrammeAssessments
	 */
	public void legacyskillsprogrammeassessmentsDelete() {
		try {
			service.delete(this.legacyskillsprogrammeassessments);
			prepareNew();
			legacyskillsprogrammeassessmentsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacySkillsProgrammeAssessments
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgrammeAssessments
	 */
	public void prepareNew() {
		legacyskillsprogrammeassessments = new LegacySkillsProgrammeAssessments();
	}

	/*
	 * public List<SelectItem> getLegacySkillsProgrammeAssessmentsDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyskillsprogrammeassessmentsInfo(); for (LegacySkillsProgrammeAssessments
	 * ug : legacyskillsprogrammeassessmentsList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacySkillsProgrammeAssessments> complete(String desc) {
		List<LegacySkillsProgrammeAssessments> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacySkillsProgrammeAssessments> getLegacySkillsProgrammeAssessmentsList() {
		return legacyskillsprogrammeassessmentsList;
	}

	public void setLegacySkillsProgrammeAssessmentsList(List<LegacySkillsProgrammeAssessments> legacyskillsprogrammeassessmentsList) {
		this.legacyskillsprogrammeassessmentsList = legacyskillsprogrammeassessmentsList;
	}

	public LegacySkillsProgrammeAssessments getLegacyskillsprogrammeassessments() {
		return legacyskillsprogrammeassessments;
	}

	public void setLegacyskillsprogrammeassessments(LegacySkillsProgrammeAssessments legacyskillsprogrammeassessments) {
		this.legacyskillsprogrammeassessments = legacyskillsprogrammeassessments;
	}

	public List<LegacySkillsProgrammeAssessments> getLegacySkillsProgrammeAssessmentsfilteredList() {
		return legacyskillsprogrammeassessmentsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyskillsprogrammeassessmentsfilteredList
	 *            the new legacyskillsprogrammeassessmentsfilteredList list
	 * @see LegacySkillsProgrammeAssessments
	 */
	public void setLegacySkillsProgrammeAssessmentsfilteredList(List<LegacySkillsProgrammeAssessments> legacyskillsprogrammeassessmentsfilteredList) {
		this.legacyskillsprogrammeassessmentsfilteredList = legacyskillsprogrammeassessmentsfilteredList;
	}

	public LazyDataModel<LegacySkillsProgrammeAssessments> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacySkillsProgrammeAssessments> dataModel) {
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
			List<LegacySkillsProgrammeAssessments> csvDataList = csvUtil.getObjects(LegacySkillsProgrammeAssessments.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyskillsprogrammeassessmentsInfo();
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
			legacyskillsprogrammeassessmentsInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiationRun() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
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

	public List<LegacySkillsProgrammeAssessments> getLegacyskillsprogrammeassessmentsList() {
		return legacyskillsprogrammeassessmentsList;
	}

	public void setLegacyskillsprogrammeassessmentsList(List<LegacySkillsProgrammeAssessments> legacyskillsprogrammeassessmentsList) {
		this.legacyskillsprogrammeassessmentsList = legacyskillsprogrammeassessmentsList;
	}

	public List<LegacySkillsProgrammeAssessments> getLegacyskillsprogrammeassessmentsfilteredList() {
		return legacyskillsprogrammeassessmentsfilteredList;
	}

	public void setLegacyskillsprogrammeassessmentsfilteredList(List<LegacySkillsProgrammeAssessments> legacyskillsprogrammeassessmentsfilteredList) {
		this.legacyskillsprogrammeassessmentsfilteredList = legacyskillsprogrammeassessmentsfilteredList;
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
