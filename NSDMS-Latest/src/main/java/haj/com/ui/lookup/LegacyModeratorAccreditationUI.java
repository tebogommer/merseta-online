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
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyModeratorAccreditationService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacymoderatoraccreditationUI")
@ViewScoped
public class LegacyModeratorAccreditationUI extends AbstractUI {

	private LegacyModeratorAccreditationService service = new LegacyModeratorAccreditationService();
	private List<LegacyModeratorAccreditation> legacymoderatoraccreditationList = null;
	private List<LegacyModeratorAccreditation> legacymoderatoraccreditationfilteredList = null;
	private LegacyModeratorAccreditation legacymoderatoraccreditation = null;
	private LazyDataModel<LegacyModeratorAccreditation> dataModel;

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
	 * Initialize method to get all LegacyModeratorAccreditation and prepare a for a
	 * create of a new LegacyModeratorAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorAccreditation
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacymoderatoraccreditationInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyModeratorAccreditation.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyModeratorAccreditation for data table
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorAccreditation
	 */
	public void legacymoderatoraccreditationInfo() {

		dataModel = new LazyDataModel<LegacyModeratorAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyModeratorAccreditation> retorno = new ArrayList<LegacyModeratorAccreditation>();

			@Override
			public List<LegacyModeratorAccreditation> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyModeratorAccreditation(LegacyModeratorAccreditation.class, first,
							pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyModeratorAccreditation.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyModeratorAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyModeratorAccreditation getRowData(String rowKey) {
				for (LegacyModeratorAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyModeratorAccreditation into database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorAccreditation
	 */
	public void legacymoderatoraccreditationInsert() {
		try {
			service.create(this.legacymoderatoraccreditation);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatoraccreditationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyModeratorAccreditation in database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorAccreditation
	 */
	public void legacymoderatoraccreditationUpdate() {
		try {
			service.update(this.legacymoderatoraccreditation);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatoraccreditationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyModeratorAccreditation from database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorAccreditation
	 */
	public void legacymoderatoraccreditationDelete() {
		try {
			service.delete(this.legacymoderatoraccreditation);
			prepareNew();
			legacymoderatoraccreditationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyModeratorAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorAccreditation
	 */
	public void prepareNew() {
		legacymoderatoraccreditation = new LegacyModeratorAccreditation();
	}

	/*
	 * public List<SelectItem> getLegacyModeratorAccreditationDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacymoderatoraccreditationInfo(); for (LegacyModeratorAccreditation ug :
	 * legacymoderatoraccreditationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<LegacyModeratorAccreditation> complete(String desc) {
		List<LegacyModeratorAccreditation> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyModeratorAccreditation> getLegacyModeratorAccreditationList() {
		return legacymoderatoraccreditationList;
	}

	public void setLegacyModeratorAccreditationList(
			List<LegacyModeratorAccreditation> legacymoderatoraccreditationList) {
		this.legacymoderatoraccreditationList = legacymoderatoraccreditationList;
	}

	public LegacyModeratorAccreditation getLegacymoderatoraccreditation() {
		return legacymoderatoraccreditation;
	}

	public void setLegacymoderatoraccreditation(LegacyModeratorAccreditation legacymoderatoraccreditation) {
		this.legacymoderatoraccreditation = legacymoderatoraccreditation;
	}

	public List<LegacyModeratorAccreditation> getLegacyModeratorAccreditationfilteredList() {
		return legacymoderatoraccreditationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacymoderatoraccreditationfilteredList the new
	 *                                                 legacymoderatoraccreditationfilteredList
	 *                                                 list
	 * @see LegacyModeratorAccreditation
	 */
	public void setLegacyModeratorAccreditationfilteredList(
			List<LegacyModeratorAccreditation> legacymoderatoraccreditationfilteredList) {
		this.legacymoderatoraccreditationfilteredList = legacymoderatoraccreditationfilteredList;
	}

	public LazyDataModel<LegacyModeratorAccreditation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyModeratorAccreditation> dataModel) {
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
			List<LegacyModeratorAccreditation> csvDataList = csvUtil.getObjects(LegacyModeratorAccreditation.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacymoderatoraccreditationInfo();
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
			legacymoderatoraccreditationInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void countAllEntries(){
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
			legacymoderatoraccreditationInfo();
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

	public List<LegacyModeratorAccreditation> getLegacymoderatoraccreditationList() {
		return legacymoderatoraccreditationList;
	}

	public void setLegacymoderatoraccreditationList(List<LegacyModeratorAccreditation> legacymoderatoraccreditationList) {
		this.legacymoderatoraccreditationList = legacymoderatoraccreditationList;
	}

	public List<LegacyModeratorAccreditation> getLegacymoderatoraccreditationfilteredList() {
		return legacymoderatoraccreditationfilteredList;
	}

	public void setLegacymoderatoraccreditationfilteredList(
			List<LegacyModeratorAccreditation> legacymoderatoraccreditationfilteredList) {
		this.legacymoderatoraccreditationfilteredList = legacymoderatoraccreditationfilteredList;
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
