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
import haj.com.entity.lookup.LegacyDTTCApproval;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyDTTCApprovalService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacydttcapprovalUI")
@ViewScoped
public class LegacyDTTCApprovalUI extends AbstractUI {

	private LegacyDTTCApprovalService service = new LegacyDTTCApprovalService();
	private List<LegacyDTTCApproval> legacydttcapprovalList = null;
	private List<LegacyDTTCApproval> legacydttcapprovalfilteredList = null;
	private LegacyDTTCApproval legacydttcapproval = null;
	private LazyDataModel<LegacyDTTCApproval> dataModel;

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
	 * Initialize method to get all LegacyDTTCApproval and prepare a for a
	 * create of a new LegacyDTTCApproval
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCApproval
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacydttcapprovalInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyDTTCApproval.class, true);
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
	 * Get all LegacyDTTCApproval for data table
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCApproval
	 */
	public void legacydttcapprovalInfo() {

		dataModel = new LazyDataModel<LegacyDTTCApproval>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyDTTCApproval> retorno = new ArrayList<LegacyDTTCApproval>();

			@Override
			public List<LegacyDTTCApproval> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLegacyDTTCApproval(LegacyDTTCApproval.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyDTTCApproval.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyDTTCApproval obj) {
				return obj.getId();
			}

			@Override
			public LegacyDTTCApproval getRowData(String rowKey) {
				for (LegacyDTTCApproval obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyDTTCApproval into database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCApproval
	 */
	public void legacydttcapprovalInsert() {
		try {
			service.create(this.legacydttcapproval);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttcapprovalInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyDTTCApproval in database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCApproval
	 */
	public void legacydttcapprovalUpdate() {
		try {
			service.update(this.legacydttcapproval);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttcapprovalInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyDTTCApproval from database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCApproval
	 */
	public void legacydttcapprovalDelete() {
		try {
			service.delete(this.legacydttcapproval);
			prepareNew();
			legacydttcapprovalInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyDTTCApproval
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCApproval
	 */
	public void prepareNew() {
		legacydttcapproval = new LegacyDTTCApproval();
	}

	/*
	 * public List<SelectItem> getLegacyDTTCApprovalDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacydttcapprovalInfo(); for (LegacyDTTCApproval ug :
	 * legacydttcapprovalList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyDTTCApproval> complete(String desc) {
		List<LegacyDTTCApproval> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyDTTCApproval> getLegacyDTTCApprovalList() {
		return legacydttcapprovalList;
	}

	public void setLegacyDTTCApprovalList(List<LegacyDTTCApproval> legacydttcapprovalList) {
		this.legacydttcapprovalList = legacydttcapprovalList;
	}

	public LegacyDTTCApproval getLegacydttcapproval() {
		return legacydttcapproval;
	}

	public void setLegacydttcapproval(LegacyDTTCApproval legacydttcapproval) {
		this.legacydttcapproval = legacydttcapproval;
	}

	public List<LegacyDTTCApproval> getLegacyDTTCApprovalfilteredList() {
		return legacydttcapprovalfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacydttcapprovalfilteredList
	 *            the new legacydttcapprovalfilteredList list
	 * @see LegacyDTTCApproval
	 */
	public void setLegacyDTTCApprovalfilteredList(List<LegacyDTTCApproval> legacydttcapprovalfilteredList) {
		this.legacydttcapprovalfilteredList = legacydttcapprovalfilteredList;
	}

	public LazyDataModel<LegacyDTTCApproval> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyDTTCApproval> dataModel) {
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
			List<LegacyDTTCApproval> csvDataList = csvUtil.getObjects(LegacyDTTCApproval.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacydttcapprovalInfo();
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
			legacydttcapprovalInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacydttcapprovalInfo();
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

	public List<LegacyDTTCApproval> getLegacydttcapprovalList() {
		return legacydttcapprovalList;
	}

	public void setLegacydttcapprovalList(List<LegacyDTTCApproval> legacydttcapprovalList) {
		this.legacydttcapprovalList = legacydttcapprovalList;
	}

	public List<LegacyDTTCApproval> getLegacydttcapprovalfilteredList() {
		return legacydttcapprovalfilteredList;
	}

	public void setLegacydttcapprovalfilteredList(List<LegacyDTTCApproval> legacydttcapprovalfilteredList) {
		this.legacydttcapprovalfilteredList = legacydttcapprovalfilteredList;
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
