package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.service.lookup.LegacySECTTwentyEightService;
import haj.com.service.lookup.LegacySectionTwentyEightTradeTestService;
import haj.com.service.lookup.ReportingService;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacySectionTwentyEightUI")
@ViewScoped
public class LegacySectionTwentyEightUI extends AbstractUI {

	private LegacySECTTwentyEightService service = new LegacySECTTwentyEightService();
	private LegacySectionTwentyEightTradeTestService legacySectionTwentyEightTradeTestService = new LegacySectionTwentyEightTradeTestService();
	private List<LegacySECTTwentyEight> legacysecttwentyeightList = null;
	private List<LegacySECTTwentyEight> legacysecttwentyeightfilteredList = null;
	private LegacySECTTwentyEight legacysecttwentyeight = null;
	private LazyDataModel<LegacySECTTwentyEight> dataModel;

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
	 * Initialize method to get all LegacySECTTwentyEight and prepare a for a create
	 * of a new LegacySECTTwentyEight
	 * 
	 * @author TechFinium
	 * @see LegacySECTTwentyEight
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacysecttwentyeightInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacySECTTwentyEight.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacySECTTwentyEight for data table
	 * 
	 * @author TechFinium
	 * @see LegacySECTTwentyEight
	 */
	public void legacysecttwentyeightInfo() {

		dataModel = new LazyDataModel<LegacySECTTwentyEight>() {

			private static final long serialVersionUID = 1L;
			private List<LegacySECTTwentyEight> retorno = new ArrayList<LegacySECTTwentyEight>();

			@Override
			public List<LegacySECTTwentyEight> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLegacySECTTwentyEight(LegacySECTTwentyEight.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacySECTTwentyEight.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacySECTTwentyEight obj) {
				return obj.getId();
			}

			@Override
			public LegacySECTTwentyEight getRowData(String rowKey) {
				for (LegacySECTTwentyEight obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}
	
	public void prepareLegacySECTTwentyEight() {
		try {
			if(legacysecttwentyeight != null && legacysecttwentyeight.getId() != null) {
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
	 * Insert LegacySECTTwentyEight into database
	 * 
	 * @author TechFinium
	 * @see LegacySECTTwentyEight
	 */
	public void legacysecttwentyeightInsert() {
		try {
			service.create(this.legacysecttwentyeight);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacysecttwentyeightInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacySECTTwentyEight in database
	 * 
	 * @author TechFinium
	 * @see LegacySECTTwentyEight
	 */
	public void legacysecttwentyeightUpdate() {
		try {
			service.update(this.legacysecttwentyeight);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacysecttwentyeightInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacySECTTwentyEight from database
	 * 
	 * @author TechFinium
	 * @see LegacySECTTwentyEight
	 */
	public void legacysecttwentyeightDelete() {
		try {
			service.delete(this.legacysecttwentyeight);
			prepareNew();
			legacysecttwentyeightInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void onRowToggleTwentyEightModel(LegacySECTTwentyEight legacySecttwentyeight) {
		try {
			this.legacysecttwentyeight = legacySecttwentyeight;
			this.legacysecttwentyeight.setLegacysectiontwentyeighttradetestList(legacySectionTwentyEightTradeTestService.findByLegacySECTTwentyEight(legacySecttwentyeight));
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}

	/**
	 * Create new instance of LegacySECTTwentyEight
	 * 
	 * @author TechFinium
	 * @see LegacySECTTwentyEight
	 */
	public void prepareNew() {
		legacysecttwentyeight = new LegacySECTTwentyEight();
	}

	/*
	 * public List<SelectItem> getLegacySECTTwentyEightDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacysecttwentyeightInfo(); for (LegacySECTTwentyEight ug :
	 * legacysecttwentyeightList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<LegacySECTTwentyEight> complete(String desc) {
		List<LegacySECTTwentyEight> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacySECTTwentyEight> getLegacySECTTwentyEightList() {
		return legacysecttwentyeightList;
	}

	public void setLegacySECTTwentyEightList(List<LegacySECTTwentyEight> legacysecttwentyeightList) {
		this.legacysecttwentyeightList = legacysecttwentyeightList;
	}

	public LegacySECTTwentyEight getLegacysecttwentyeight() {
		return legacysecttwentyeight;
	}

	public void setLegacysecttwentyeight(LegacySECTTwentyEight legacysecttwentyeight) {
		this.legacysecttwentyeight = legacysecttwentyeight;
	}

	public List<LegacySECTTwentyEight> getLegacySECTTwentyEightfilteredList() {
		return legacysecttwentyeightfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacysecttwentyeightfilteredList the new
	 *                                          legacysecttwentyeightfilteredList
	 *                                          list
	 * @see LegacySECTTwentyEight
	 */
	public void setLegacySECTTwentyEightfilteredList(List<LegacySECTTwentyEight> legacysecttwentyeightfilteredList) {
		this.legacysecttwentyeightfilteredList = legacysecttwentyeightfilteredList;
	}

	public LazyDataModel<LegacySECTTwentyEight> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacySECTTwentyEight> dataModel) {
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
			List<LegacySECTTwentyEight> csvDataList = csvUtil.getObjects(LegacySECTTwentyEight.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacysecttwentyeightInfo();
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
			legacysecttwentyeightInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runValidiationRun(){
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

	public List<LegacySECTTwentyEight> getLegacysecttwentyeightList() {
		return legacysecttwentyeightList;
	}

	public void setLegacysecttwentyeightList(List<LegacySECTTwentyEight> legacysecttwentyeightList) {
		this.legacysecttwentyeightList = legacysecttwentyeightList;
	}

	public List<LegacySECTTwentyEight> getLegacysecttwentyeightfilteredList() {
		return legacysecttwentyeightfilteredList;
	}

	public void setLegacysecttwentyeightfilteredList(List<LegacySECTTwentyEight> legacysecttwentyeightfilteredList) {
		this.legacysecttwentyeightfilteredList = legacysecttwentyeightfilteredList;
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
