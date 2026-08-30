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
import haj.com.entity.lookup.LegacyTvet;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyTvetService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacytvetUI")
@ViewScoped
public class LegacyTvetUI extends AbstractUI {

	private LegacyTvetService service = new LegacyTvetService();
	private List<LegacyTvet> legacytvetList = null;
	private List<LegacyTvet> legacytvetfilteredList = null;
	private LegacyTvet legacytvet = null;
	private LazyDataModel<LegacyTvet> dataModel;

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
	 * Initialize method to get all LegacyTvet and prepare a for a create of a new
	 * LegacyTvet
	 * 
	 * @author TechFinium
	 * @see LegacyTvet
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacytvetInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyTvet.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyTvet for data table
	 * 
	 * @author TechFinium
	 * @see LegacyTvet
	 */
	public void legacytvetInfo() {

		dataModel = new LazyDataModel<LegacyTvet>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyTvet> retorno = new ArrayList<LegacyTvet>();

			@Override
			public List<LegacyTvet> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyTvet(LegacyTvet.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyTvet.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyTvet obj) {
				return obj.getId();
			}

			@Override
			public LegacyTvet getRowData(String rowKey) {
				for (LegacyTvet obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void prepareLegacyTvet() {
		try {
			if(legacytvet != null && legacytvet.getId() != null) {
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
	 * Insert LegacyTvet into database
	 * 
	 * @author TechFinium
	 * @see LegacyTvet
	 */
	public void legacytvetInsert() {
		try {
			service.create(this.legacytvet);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacytvetInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyTvet in database
	 * 
	 * @author TechFinium
	 * @see LegacyTvet
	 */
	public void legacytvetUpdate() {
		try {
			service.update(this.legacytvet);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacytvetInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyTvet from database
	 * 
	 * @author TechFinium
	 * @see LegacyTvet
	 */
	public void legacytvetDelete() {
		try {
			service.delete(this.legacytvet);
			prepareNew();
			legacytvetInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyTvet
	 * 
	 * @author TechFinium
	 * @see LegacyTvet
	 */
	public void prepareNew() {
		legacytvet = new LegacyTvet();
	}

	/*
	 * public List<SelectItem> getLegacyTvetDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacytvetInfo(); for (LegacyTvet ug : legacytvetList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyTvet> complete(String desc) {
		List<LegacyTvet> l = null;
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

	public List<LegacyTvet> getLegacyTvetList() {
		return legacytvetList;
	}

	public void setLegacyTvetList(List<LegacyTvet> legacytvetList) {
		this.legacytvetList = legacytvetList;
	}

	public LegacyTvet getLegacytvet() {
		return legacytvet;
	}

	public void setLegacytvet(LegacyTvet legacytvet) {
		this.legacytvet = legacytvet;
	}

	public List<LegacyTvet> getLegacyTvetfilteredList() {
		return legacytvetfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacytvetfilteredList
	 *            the new legacytvetfilteredList list
	 * @see LegacyTvet
	 */
	public void setLegacyTvetfilteredList(List<LegacyTvet> legacytvetfilteredList) {
		this.legacytvetfilteredList = legacytvetfilteredList;
	}

	public LazyDataModel<LegacyTvet> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyTvet> dataModel) {
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
			List<LegacyTvet> csvDataList = csvUtil.getObjects(LegacyTvet.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacytvetInfo();
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
			legacytvetInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validiationRun();
			addInfoMessage("Action Complete");
			legacytvetInfo();
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

	public List<LegacyTvet> getLegacytvetList() {
		return legacytvetList;
	}

	public void setLegacytvetList(List<LegacyTvet> legacytvetList) {
		this.legacytvetList = legacytvetList;
	}

	public List<LegacyTvet> getLegacytvetfilteredList() {
		return legacytvetfilteredList;
	}

	public void setLegacytvetfilteredList(List<LegacyTvet> legacytvetfilteredList) {
		this.legacytvetfilteredList = legacytvetfilteredList;
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
