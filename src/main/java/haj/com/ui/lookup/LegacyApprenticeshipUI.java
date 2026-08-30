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
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyApprenticeshipService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyapprenticeshipUI")
@ViewScoped
public class LegacyApprenticeshipUI extends AbstractUI {

	private LegacyApprenticeshipService service = new LegacyApprenticeshipService();
	private List<LegacyApprenticeship> legacyapprenticeshipList = null;
	private List<LegacyApprenticeship> legacyapprenticeshipfilteredList = null;
	private LegacyApprenticeship legacyapprenticeship = null;
	private LazyDataModel<LegacyApprenticeship> dataModel;

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
	 * Initialize method to get all LegacyApprenticeship and prepare a for a create
	 * of a new LegacyApprenticeship
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeship
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyapprenticeshipInfo();
//		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyApprenticeship.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyApprenticeship for data table
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeship
	 */
	public void legacyapprenticeshipInfo() {

		dataModel = new LazyDataModel<LegacyApprenticeship>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyApprenticeship> retorno = new ArrayList<LegacyApprenticeship>();

			@Override
			public List<LegacyApprenticeship> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyApprenticeship(LegacyApprenticeship.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyApprenticeship.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyApprenticeship obj) {
				return obj.getId();
			}

			@Override
			public LegacyApprenticeship getRowData(String rowKey) {
				for (LegacyApprenticeship obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void prepareLegacyApprenticeship() {
		try {
			if(legacyapprenticeship != null && legacyapprenticeship.getId() != null) {
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
	 * Insert LegacyApprenticeship into database
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeship
	 */
	public void legacyapprenticeshipInsert() {
		try {
			service.create(this.legacyapprenticeship);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyapprenticeshipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyApprenticeship in database
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeship
	 */
	public void legacyapprenticeshipUpdate() {
		try {
			service.update(this.legacyapprenticeship);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyapprenticeshipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyApprenticeship from database
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeship
	 */
	public void legacyapprenticeshipDelete() {
		try {
			service.delete(this.legacyapprenticeship);
			prepareNew();
			legacyapprenticeshipInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
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

	/**
	 * Create new instance of LegacyApprenticeship
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeship
	 */
	public void prepareNew() {
		legacyapprenticeship = new LegacyApprenticeship();
	}

	/*
	 * public List<SelectItem> getLegacyApprenticeshipDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyapprenticeshipInfo(); for (LegacyApprenticeship ug :
	 * legacyapprenticeshipList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyApprenticeship> complete(String desc) {
		List<LegacyApprenticeship> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyApprenticeship> getLegacyApprenticeshipList() {
		return legacyapprenticeshipList;
	}

	public void setLegacyApprenticeshipList(List<LegacyApprenticeship> legacyapprenticeshipList) {
		this.legacyapprenticeshipList = legacyapprenticeshipList;
	}

	public LegacyApprenticeship getLegacyapprenticeship() {
		return legacyapprenticeship;
	}

	public void setLegacyapprenticeship(LegacyApprenticeship legacyapprenticeship) {
		this.legacyapprenticeship = legacyapprenticeship;
	}

	public List<LegacyApprenticeship> getLegacyApprenticeshipfilteredList() {
		return legacyapprenticeshipfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyapprenticeshipfilteredList
	 *            the new legacyapprenticeshipfilteredList list
	 * @see LegacyApprenticeship
	 */
	public void setLegacyApprenticeshipfilteredList(List<LegacyApprenticeship> legacyapprenticeshipfilteredList) {
		this.legacyapprenticeshipfilteredList = legacyapprenticeshipfilteredList;
	}

	public LazyDataModel<LegacyApprenticeship> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyApprenticeship> dataModel) {
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
			List<LegacyApprenticeship> csvDataList = csvUtil.getObjects(LegacyApprenticeship.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyapprenticeshipInfo();
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
			legacyapprenticeshipInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacyapprenticeshipInfo();
			countAllEntries();
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
