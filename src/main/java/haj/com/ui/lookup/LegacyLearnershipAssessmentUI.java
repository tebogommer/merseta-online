package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.lookup.LegacyLearnershipAssessment;
import haj.com.service.lookup.LegacyLearnershipAssessmentService;
import haj.com.service.lookup.ReportingService;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacylearnershipassessmentUI")
@ViewScoped
public class LegacyLearnershipAssessmentUI extends AbstractUI {

	private LegacyLearnershipAssessmentService service = new LegacyLearnershipAssessmentService();
	private List<LegacyLearnershipAssessment> legacylearnershipassessmentList = null;
	private List<LegacyLearnershipAssessment> legacylearnershipassessmentfilteredList = null;
	private LegacyLearnershipAssessment legacylearnershipassessment = null;
	private LazyDataModel<LegacyLearnershipAssessment> dataModel;

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
	 * Initialize method to get all LegacyLearnershipAssessment and prepare a for a
	 * create of a new LegacyLearnershipAssessment
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacylearnershipassessmentInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyLearnershipAssessment.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyLearnershipAssessment for data table
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment
	 */
	public void legacylearnershipassessmentInfo() {
		dataModel = new LazyDataModel<LegacyLearnershipAssessment>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyLearnershipAssessment> retorno = new ArrayList<LegacyLearnershipAssessment>();
			@Override
			public List<LegacyLearnershipAssessment> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyLearnershipAssessment(LegacyLearnershipAssessment.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyLearnershipAssessment.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyLearnershipAssessment obj) {
				return obj.getId();
			}
			@Override
			public LegacyLearnershipAssessment getRowData(String rowKey) {
				for (LegacyLearnershipAssessment obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert LegacyLearnershipAssessment into database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment
	 */
	public void legacylearnershipassessmentInsert() {
		try {
			service.create(this.legacylearnershipassessment);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacylearnershipassessmentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyLearnershipAssessment in database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment
	 */
	public void legacylearnershipassessmentUpdate() {
		try {
			service.update(this.legacylearnershipassessment);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacylearnershipassessmentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyLearnershipAssessment from database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment
	 */
	public void legacylearnershipassessmentDelete() {
		try {
			service.delete(this.legacylearnershipassessment);
			prepareNew();
			legacylearnershipassessmentInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyLearnershipAssessment
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment
	 */
	public void prepareNew() {
		legacylearnershipassessment = new LegacyLearnershipAssessment();
	}

	/*
	 * public List<SelectItem> getLegacyLearnershipAssessmentDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacylearnershipassessmentInfo(); for (LegacyLearnershipAssessment ug :
	 * legacylearnershipassessmentList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyLearnershipAssessment> complete(String desc) {
		List<LegacyLearnershipAssessment> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyLearnershipAssessment> getLegacyLearnershipAssessmentList() {
		return legacylearnershipassessmentList;
	}

	public void setLegacyLearnershipAssessmentList(List<LegacyLearnershipAssessment> legacylearnershipassessmentList) {
		this.legacylearnershipassessmentList = legacylearnershipassessmentList;
	}

	public LegacyLearnershipAssessment getLegacylearnershipassessment() {
		return legacylearnershipassessment;
	}

	public void setLegacylearnershipassessment(LegacyLearnershipAssessment legacylearnershipassessment) {
		this.legacylearnershipassessment = legacylearnershipassessment;
	}

	public List<LegacyLearnershipAssessment> getLegacyLearnershipAssessmentfilteredList() {
		return legacylearnershipassessmentfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacylearnershipassessmentfilteredList
	 *            the new legacylearnershipassessmentfilteredList list
	 * @see LegacyLearnershipAssessment
	 */
	public void setLegacyLearnershipAssessmentfilteredList(List<LegacyLearnershipAssessment> legacylearnershipassessmentfilteredList) {
		this.legacylearnershipassessmentfilteredList = legacylearnershipassessmentfilteredList;
	}

	public LazyDataModel<LegacyLearnershipAssessment> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyLearnershipAssessment> dataModel) {
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
			List<LegacyLearnershipAssessment> csvDataList = csvUtil.getObjects(LegacyLearnershipAssessment.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacylearnershipassessmentInfo();
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
			legacylearnershipassessmentInfo();
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
			legacylearnershipassessmentInfo();
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

	public List<LegacyLearnershipAssessment> getLegacylearnershipassessmentList() {
		return legacylearnershipassessmentList;
	}

	public void setLegacylearnershipassessmentList(List<LegacyLearnershipAssessment> legacylearnershipassessmentList) {
		this.legacylearnershipassessmentList = legacylearnershipassessmentList;
	}

	public List<LegacyLearnershipAssessment> getLegacylearnershipassessmentfilteredList() {
		return legacylearnershipassessmentfilteredList;
	}

	public void setLegacylearnershipassessmentfilteredList(List<LegacyLearnershipAssessment> legacylearnershipassessmentfilteredList) {
		this.legacylearnershipassessmentfilteredList = legacylearnershipassessmentfilteredList;
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
