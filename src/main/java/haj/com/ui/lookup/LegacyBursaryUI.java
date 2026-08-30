package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.service.lookup.LegacyBursaryService;
import haj.com.service.lookup.ReportingService;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacybursaryUI")
@ViewScoped
public class LegacyBursaryUI extends AbstractUI {

	private LegacyBursaryService service = new LegacyBursaryService();
	private List<LegacyBursary> legacybursaryList = null;
	private List<LegacyBursary> legacybursaryfilteredList = null;
	private LegacyBursary legacybursary = null;
	private LazyDataModel<LegacyBursary> dataModel;

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
	 * Initialize method to get all LegacyBursary and prepare a for a create of a
	 * new LegacyBursary
	 * 
	 * @author TechFinium
	 * @see LegacyBursary
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacybursaryInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyBursary.class, true);
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
	 * Get all LegacyBursary for data table
	 * 
	 * @author TechFinium
	 * @see LegacyBursary
	 */
	public void legacybursaryInfo() {

		dataModel = new LazyDataModel<LegacyBursary>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyBursary> retorno = new ArrayList<LegacyBursary>();

			@Override
			public List<LegacyBursary> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyBursary(LegacyBursary.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyBursary.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyBursary obj) {
				return obj.getId();
			}

			@Override
			public LegacyBursary getRowData(String rowKey) {
				for (LegacyBursary obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	public void prepareLegacyBursary() {
		try {
			if(legacybursary != null && legacybursary.getId() != null) {
				super.runClientSideExecute("PF('updateDialog').hide()");		
			}else {
				throw new Exception("Error with the legacy learnership");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Insert LegacyBursary into database
	 * 
	 * @author TechFinium
	 * @see LegacyBursary
	 */
	public void legacybursaryInsert() {
		try {
			service.create(this.legacybursary);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacybursaryInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyBursary in database
	 * 
	 * @author TechFinium
	 * @see LegacyBursary
	 */
	public void legacybursaryUpdate() {
		try {
			service.update(this.legacybursary);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacybursaryInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyBursary from database
	 * 
	 * @author TechFinium
	 * @see LegacyBursary
	 */
	public void legacybursaryDelete() {
		try {
			service.delete(this.legacybursary);
			prepareNew();
			legacybursaryInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyBursary
	 * 
	 * @author TechFinium
	 * @see LegacyBursary
	 */
	public void prepareNew() {
		legacybursary = new LegacyBursary();
	}

	/*
	 * public List<SelectItem> getLegacyBursaryDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacybursaryInfo(); for (LegacyBursary ug : legacybursaryList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return
	 * l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyBursary> complete(String desc) {
		List<LegacyBursary> l = null;
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

	public List<LegacyBursary> getLegacyBursaryList() {
		return legacybursaryList;
	}

	public void setLegacyBursaryList(List<LegacyBursary> legacybursaryList) {
		this.legacybursaryList = legacybursaryList;
	}

	public LegacyBursary getLegacybursary() {
		return legacybursary;
	}

	public void setLegacybursary(LegacyBursary legacybursary) {
		this.legacybursary = legacybursary;
	}

	public List<LegacyBursary> getLegacyBursaryfilteredList() {
		return legacybursaryfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacybursaryfilteredList
	 *            the new legacybursaryfilteredList list
	 * @see LegacyBursary
	 */
	public void setLegacyBursaryfilteredList(List<LegacyBursary> legacybursaryfilteredList) {
		this.legacybursaryfilteredList = legacybursaryfilteredList;
	}

	public LazyDataModel<LegacyBursary> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyBursary> dataModel) {
		this.dataModel = dataModel;
	}

	public void prepTypeSelection() {
		try {
			csvTypeSelectionList = new ArrayList<>();
			csvTypeSelectionList.add(",");
			csvTypeSelectionList.add(";");
			csvTypeSelectionList.add("|");
			csvTypeSelectionList.add("-");
			csvTypeSelection = ";";
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			if (csvTypeSelection == null || csvTypeSelection.isEmpty()) {
				csvTypeSelection = ";";
			}
			logger.info("Starting file upload");
			List<LegacyBursary> csvDataList = csvUtil.getObjects(LegacyBursary.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacybursaryInfo();
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
			legacybursaryInfo();
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
