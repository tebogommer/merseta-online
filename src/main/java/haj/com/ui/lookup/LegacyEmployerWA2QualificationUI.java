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
import haj.com.entity.lookup.LegacyEmployerWA2Qualification;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyEmployerWA2QualificationService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyemployerwa2qualificationUI")
@ViewScoped
public class LegacyEmployerWA2QualificationUI extends AbstractUI {

	private LegacyEmployerWA2QualificationService service = new LegacyEmployerWA2QualificationService();
	private List<LegacyEmployerWA2Qualification> legacyemployerwa2qualificationList = null;
	private List<LegacyEmployerWA2Qualification> legacyemployerwa2qualificationfilteredList = null;
	private LegacyEmployerWA2Qualification legacyemployerwa2qualification = null;
	private LazyDataModel<LegacyEmployerWA2Qualification> dataModel;

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
	 * Initialize method to get all LegacyEmployerWA2Qualification and prepare a for
	 * a create of a new LegacyEmployerWA2Qualification
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Qualification
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyemployerwa2qualificationInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyEmployerWA2Qualification.class, true);
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
	 * Get all LegacyEmployerWA2Qualification for data table
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Qualification
	 */
	public void legacyemployerwa2qualificationInfo() {

		dataModel = new LazyDataModel<LegacyEmployerWA2Qualification>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyEmployerWA2Qualification> retorno = new ArrayList<LegacyEmployerWA2Qualification>();

			@Override
			public List<LegacyEmployerWA2Qualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyEmployerWA2Qualification(LegacyEmployerWA2Qualification.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyEmployerWA2Qualification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyEmployerWA2Qualification obj) {
				return obj.getId();
			}

			@Override
			public LegacyEmployerWA2Qualification getRowData(String rowKey) {
				for (LegacyEmployerWA2Qualification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyEmployerWA2Qualification into database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Qualification
	 */
	public void legacyemployerwa2qualificationInsert() {
		try {
			service.create(this.legacyemployerwa2qualification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2qualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyEmployerWA2Qualification in database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Qualification
	 */
	public void legacyemployerwa2qualificationUpdate() {
		try {
			service.update(this.legacyemployerwa2qualification);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2qualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyEmployerWA2Qualification from database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Qualification
	 */
	public void legacyemployerwa2qualificationDelete() {
		try {
			service.delete(this.legacyemployerwa2qualification);
			prepareNew();
			legacyemployerwa2qualificationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyEmployerWA2Qualification
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Qualification
	 */
	public void prepareNew() {
		legacyemployerwa2qualification = new LegacyEmployerWA2Qualification();
	}

	/*
	 * public List<SelectItem> getLegacyEmployerWA2QualificationDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyemployerwa2qualificationInfo(); for (LegacyEmployerWA2Qualification ug
	 * : legacyemployerwa2qualificationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyEmployerWA2Qualification> complete(String desc) {
		List<LegacyEmployerWA2Qualification> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyEmployerWA2Qualification> getLegacyEmployerWA2QualificationList() {
		return legacyemployerwa2qualificationList;
	}

	public void setLegacyEmployerWA2QualificationList(List<LegacyEmployerWA2Qualification> legacyemployerwa2qualificationList) {
		this.legacyemployerwa2qualificationList = legacyemployerwa2qualificationList;
	}

	public LegacyEmployerWA2Qualification getLegacyemployerwa2qualification() {
		return legacyemployerwa2qualification;
	}

	public void setLegacyemployerwa2qualification(LegacyEmployerWA2Qualification legacyemployerwa2qualification) {
		this.legacyemployerwa2qualification = legacyemployerwa2qualification;
	}

	public List<LegacyEmployerWA2Qualification> getLegacyEmployerWA2QualificationfilteredList() {
		return legacyemployerwa2qualificationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyemployerwa2qualificationfilteredList
	 *            the new legacyemployerwa2qualificationfilteredList list
	 * @see LegacyEmployerWA2Qualification
	 */
	public void setLegacyEmployerWA2QualificationfilteredList(List<LegacyEmployerWA2Qualification> legacyemployerwa2qualificationfilteredList) {
		this.legacyemployerwa2qualificationfilteredList = legacyemployerwa2qualificationfilteredList;
	}

	public LazyDataModel<LegacyEmployerWA2Qualification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyEmployerWA2Qualification> dataModel) {
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
			List<LegacyEmployerWA2Qualification> csvDataList = csvUtil.getObjects(LegacyEmployerWA2Qualification.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyemployerwa2qualificationInfo();
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
			legacyemployerwa2qualificationInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacyemployerwa2qualificationInfo();
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
