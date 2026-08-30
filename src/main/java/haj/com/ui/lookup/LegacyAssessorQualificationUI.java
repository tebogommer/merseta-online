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

import haj.com.bean.ColumnBean;
import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyAssessorQualificationService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyassessorqualificationUI")
@ViewScoped
public class LegacyAssessorQualificationUI extends AbstractUI {

	private LegacyAssessorQualificationService service = new LegacyAssessorQualificationService();
	private ReportingService reportingService = new ReportingService();
	private List<LegacyAssessorQualification> legacyassessorqualificationList = null;
	private List<LegacyAssessorQualification> legacyassessorqualificationfilteredList = null;
	private LegacyAssessorQualification legacyassessorqualification = null;
	private LazyDataModel<LegacyAssessorQualification> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;
	private List<LegacyReportingBean> legacyReportingBeans = new ArrayList<>();
	private Map<String, List<Object>> reportingMap;
	private Map<String, ColumnBeans> columnMap; 

	private boolean displayDownload = false;

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
	 * Initialize method to get all LegacyAssessorQualification and prepare a for a
	 * create of a new LegacyAssessorQualification
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorQualification
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	private void runInit() throws Exception {
		prepareNew();
		legacyassessorqualificationInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyAssessorQualification.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>)reportingService.extractValues(reportingMap, "count"); 
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyAssessorQualification for data table
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorQualification
	 */
	public void legacyassessorqualificationInfo() {

		dataModel = new LazyDataModel<LegacyAssessorQualification>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorQualification> retorno = new ArrayList<LegacyAssessorQualification>();

			@Override
			public List<LegacyAssessorQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyAssessorQualification(LegacyAssessorQualification.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyAssessorQualification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorQualification obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorQualification getRowData(String rowKey) {
				for (LegacyAssessorQualification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyAssessorQualification into database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorQualification
	 */
	public void legacyassessorqualificationInsert() {
		try {
			service.create(this.legacyassessorqualification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyassessorqualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyAssessorQualification in database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorQualification
	 */
	public void legacyassessorqualificationUpdate() {
		try {
			service.update(this.legacyassessorqualification);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyassessorqualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyAssessorQualification from database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorQualification
	 */
	public void legacyassessorqualificationDelete() {
		try {
			service.delete(this.legacyassessorqualification);
			prepareNew();
			legacyassessorqualificationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyAssessorQualification
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorQualification
	 */
	public void prepareNew() {
		legacyassessorqualification = new LegacyAssessorQualification();
	}

	/*
	 * public List<SelectItem> getLegacyAssessorQualificationDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyassessorqualificationInfo(); for (LegacyAssessorQualification ug :
	 * legacyassessorqualificationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyAssessorQualification> complete(String desc) {
		List<LegacyAssessorQualification> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyAssessorQualification> getLegacyAssessorQualificationList() {
		return legacyassessorqualificationList;
	}

	public void setLegacyAssessorQualificationList(List<LegacyAssessorQualification> legacyassessorqualificationList) {
		this.legacyassessorqualificationList = legacyassessorqualificationList;
	}

	public LegacyAssessorQualification getLegacyassessorqualification() {
		return legacyassessorqualification;
	}

	public void setLegacyassessorqualification(LegacyAssessorQualification legacyassessorqualification) {
		this.legacyassessorqualification = legacyassessorqualification;
	}

	public List<LegacyAssessorQualification> getLegacyAssessorQualificationfilteredList() {
		return legacyassessorqualificationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyassessorqualificationfilteredList
	 *            the new legacyassessorqualificationfilteredList list
	 * @see LegacyAssessorQualification
	 */
	public void setLegacyAssessorQualificationfilteredList(List<LegacyAssessorQualification> legacyassessorqualificationfilteredList) {
		this.legacyassessorqualificationfilteredList = legacyassessorqualificationfilteredList;
	}

	public LazyDataModel<LegacyAssessorQualification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyAssessorQualification> dataModel) {
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
			List<LegacyAssessorQualification> csvDataList = csvUtil.getObjects(LegacyAssessorQualification.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyassessorqualificationInfo();
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
			legacyassessorqualificationInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validiationRun();
			addInfoMessage("Action Complete");
			legacyassessorqualificationInfo();
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

	public List<LegacyAssessorQualification> getLegacyassessorqualificationList() {
		return legacyassessorqualificationList;
	}

	public void setLegacyassessorqualificationList(List<LegacyAssessorQualification> legacyassessorqualificationList) {
		this.legacyassessorqualificationList = legacyassessorqualificationList;
	}

	public List<LegacyAssessorQualification> getLegacyassessorqualificationfilteredList() {
		return legacyassessorqualificationfilteredList;
	}

	public void setLegacyassessorqualificationfilteredList(List<LegacyAssessorQualification> legacyassessorqualificationfilteredList) {
		this.legacyassessorqualificationfilteredList = legacyassessorqualificationfilteredList;
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
