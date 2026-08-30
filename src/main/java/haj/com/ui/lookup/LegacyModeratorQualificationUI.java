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
import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyModeratorQualificationService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacymoderatorqualificationUI")
@ViewScoped
public class LegacyModeratorQualificationUI extends AbstractUI {

	private LegacyModeratorQualificationService service = new LegacyModeratorQualificationService();
	private List<LegacyModeratorQualification> legacymoderatorqualificationList = null;
	private List<LegacyModeratorQualification> legacymoderatorqualificationfilteredList = null;
	private LegacyModeratorQualification legacymoderatorqualification = null;
	private LazyDataModel<LegacyModeratorQualification> dataModel;

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
	 * Initialize method to get all LegacyModeratorQualification and prepare a for a
	 * create of a new LegacyModeratorQualification
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorQualification
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacymoderatorqualificationInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyModeratorQualification.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyModeratorQualification for data table
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorQualification
	 */
	public void legacymoderatorqualificationInfo() {

		dataModel = new LazyDataModel<LegacyModeratorQualification>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyModeratorQualification> retorno = new ArrayList<LegacyModeratorQualification>();

			@Override
			public List<LegacyModeratorQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyModeratorQualification(LegacyModeratorQualification.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyModeratorQualification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyModeratorQualification obj) {
				return obj.getId();
			}

			@Override
			public LegacyModeratorQualification getRowData(String rowKey) {
				for (LegacyModeratorQualification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyModeratorQualification into database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorQualification
	 */
	public void legacymoderatorqualificationInsert() {
		try {
			service.create(this.legacymoderatorqualification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatorqualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyModeratorQualification in database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorQualification
	 */
	public void legacymoderatorqualificationUpdate() {
		try {
			service.update(this.legacymoderatorqualification);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatorqualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyModeratorQualification from database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorQualification
	 */
	public void legacymoderatorqualificationDelete() {
		try {
			service.delete(this.legacymoderatorqualification);
			prepareNew();
			legacymoderatorqualificationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyModeratorQualification
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorQualification
	 */
	public void prepareNew() {
		legacymoderatorqualification = new LegacyModeratorQualification();
	}

	/*
	 * public List<SelectItem> getLegacyModeratorQualificationDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacymoderatorqualificationInfo(); for (LegacyModeratorQualification ug :
	 * legacymoderatorqualificationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyModeratorQualification> complete(String desc) {
		List<LegacyModeratorQualification> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyModeratorQualification> getLegacyModeratorQualificationList() {
		return legacymoderatorqualificationList;
	}

	public void setLegacyModeratorQualificationList(List<LegacyModeratorQualification> legacymoderatorqualificationList) {
		this.legacymoderatorqualificationList = legacymoderatorqualificationList;
	}

	public LegacyModeratorQualification getLegacymoderatorqualification() {
		return legacymoderatorqualification;
	}

	public void setLegacymoderatorqualification(LegacyModeratorQualification legacymoderatorqualification) {
		this.legacymoderatorqualification = legacymoderatorqualification;
	}

	public List<LegacyModeratorQualification> getLegacyModeratorQualificationfilteredList() {
		return legacymoderatorqualificationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacymoderatorqualificationfilteredList
	 *            the new legacymoderatorqualificationfilteredList list
	 * @see LegacyModeratorQualification
	 */
	public void setLegacyModeratorQualificationfilteredList(List<LegacyModeratorQualification> legacymoderatorqualificationfilteredList) {
		this.legacymoderatorqualificationfilteredList = legacymoderatorqualificationfilteredList;
	}

	public LazyDataModel<LegacyModeratorQualification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyModeratorQualification> dataModel) {
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
			List<LegacyModeratorQualification> csvDataList = csvUtil.getObjects(LegacyModeratorQualification.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacymoderatorqualificationInfo();
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
			legacymoderatorqualificationInfo();
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
			legacymoderatorqualificationInfo();
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

	public List<LegacyModeratorQualification> getLegacymoderatorqualificationList() {
		return legacymoderatorqualificationList;
	}

	public void setLegacymoderatorqualificationList(List<LegacyModeratorQualification> legacymoderatorqualificationList) {
		this.legacymoderatorqualificationList = legacymoderatorqualificationList;
	}

	public List<LegacyModeratorQualification> getLegacymoderatorqualificationfilteredList() {
		return legacymoderatorqualificationfilteredList;
	}

	public void setLegacymoderatorqualificationfilteredList(List<LegacyModeratorQualification> legacymoderatorqualificationfilteredList) {
		this.legacymoderatorqualificationfilteredList = legacymoderatorqualificationfilteredList;
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
