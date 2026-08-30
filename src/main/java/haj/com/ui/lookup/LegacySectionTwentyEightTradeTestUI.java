package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.bean.ColumnBeans;
import haj.com.bean.LegacyReportingBean;
import haj.com.entity.lookup.LegacySectionTwentyEightTradeTest;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.service.lookup.LegacySectionTwentyEightTradeTestService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacysectiontwentyeighttradetestUI")
@ViewScoped
public class LegacySectionTwentyEightTradeTestUI extends AbstractUI {

	private LegacySectionTwentyEightTradeTestService service = new LegacySectionTwentyEightTradeTestService();
	private List<LegacySectionTwentyEightTradeTest> legacysectiontwentyeighttradetestList = null;
	private List<LegacySectionTwentyEightTradeTest> legacysectiontwentyeighttradetestfilteredList = null;
	private LegacySectionTwentyEightTradeTest legacysectiontwentyeighttradetest = null;
	private LazyDataModel<LegacySectionTwentyEightTradeTest> dataModel;

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
	 * Initialize method to get all LegacySectionTwentyEightTradeTest and prepare a
	 * for a create of a new LegacySectionTwentyEightTradeTest
	 * 
	 * @author TechFinium
	 * @see LegacySectionTwentyEightTradeTest
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacysectiontwentyeighttradetestInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacySectionTwentyEightTradeTest.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacySectionTwentyEightTradeTest for data table
	 * 
	 * @author TechFinium
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public void legacysectiontwentyeighttradetestInfo() {

		dataModel = new LazyDataModel<LegacySectionTwentyEightTradeTest>() {

			private static final long serialVersionUID = 1L;
			private List<LegacySectionTwentyEightTradeTest> retorno = new ArrayList<LegacySectionTwentyEightTradeTest>();

			@Override
			public List<LegacySectionTwentyEightTradeTest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacySectionTwentyEightTradeTest(LegacySectionTwentyEightTradeTest.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacySectionTwentyEightTradeTest.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacySectionTwentyEightTradeTest obj) {
				return obj.getId();
			}

			@Override
			public LegacySectionTwentyEightTradeTest getRowData(String rowKey) {
				for (LegacySectionTwentyEightTradeTest obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void prepareLegacySECTTwentyEight() {
		try {
			if(legacysectiontwentyeighttradetest != null && legacysectiontwentyeighttradetest.getId() != null) {
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
	 * Insert LegacySectionTwentyEightTradeTest into database
	 * 
	 * @author TechFinium
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public void legacysectiontwentyeighttradetestInsert() {
		try {
			service.create(this.legacysectiontwentyeighttradetest);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacysectiontwentyeighttradetestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacySectionTwentyEightTradeTest in database
	 * 
	 * @author TechFinium
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public void legacysectiontwentyeighttradetestUpdate() {
		try {
			service.update(this.legacysectiontwentyeighttradetest);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacysectiontwentyeighttradetestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacySectionTwentyEightTradeTest from database
	 * 
	 * @author TechFinium
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public void legacysectiontwentyeighttradetestDelete() {
		try {
			service.delete(this.legacysectiontwentyeighttradetest);
			prepareNew();
			legacysectiontwentyeighttradetestInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacySectionTwentyEightTradeTest
	 * 
	 * @author TechFinium
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public void prepareNew() {
		legacysectiontwentyeighttradetest = new LegacySectionTwentyEightTradeTest();
	}

	/*
	 * public List<SelectItem> getLegacySectionTwentyEightTradeTestDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacysectiontwentyeighttradetestInfo(); for
	 * (LegacySectionTwentyEightTradeTest ug :
	 * legacysectiontwentyeighttradetestList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacySectionTwentyEightTradeTest> complete(String desc) {
		List<LegacySectionTwentyEightTradeTest> l = null;
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


	public List<LegacySectionTwentyEightTradeTest> getLegacySectionTwentyEightTradeTestList() {
		return legacysectiontwentyeighttradetestList;
	}

	public void setLegacySectionTwentyEightTradeTestList(List<LegacySectionTwentyEightTradeTest> legacysectiontwentyeighttradetestList) {
		this.legacysectiontwentyeighttradetestList = legacysectiontwentyeighttradetestList;
	}

	public LegacySectionTwentyEightTradeTest getLegacysectiontwentyeighttradetest() {
		return legacysectiontwentyeighttradetest;
	}

	public void setLegacysectiontwentyeighttradetest(LegacySectionTwentyEightTradeTest legacysectiontwentyeighttradetest) {
		this.legacysectiontwentyeighttradetest = legacysectiontwentyeighttradetest;
	}

	public List<LegacySectionTwentyEightTradeTest> getLegacySectionTwentyEightTradeTestfilteredList() {
		return legacysectiontwentyeighttradetestfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacysectiontwentyeighttradetestfilteredList
	 *            the new legacysectiontwentyeighttradetestfilteredList list
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public void setLegacySectionTwentyEightTradeTestfilteredList(List<LegacySectionTwentyEightTradeTest> legacysectiontwentyeighttradetestfilteredList) {
		this.legacysectiontwentyeighttradetestfilteredList = legacysectiontwentyeighttradetestfilteredList;
	}

	public LazyDataModel<LegacySectionTwentyEightTradeTest> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacySectionTwentyEightTradeTest> dataModel) {
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
			List<LegacySectionTwentyEightTradeTest> csvDataList = csvUtil.getObjects(LegacySectionTwentyEightTradeTest.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacysectiontwentyeighttradetestInfo();
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
			legacysectiontwentyeighttradetestInfo();
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

	public List<LegacySectionTwentyEightTradeTest> getLegacysectiontwentyeighttradetestList() {
		return legacysectiontwentyeighttradetestList;
	}

	public void setLegacysectiontwentyeighttradetestList(List<LegacySectionTwentyEightTradeTest> legacysectiontwentyeighttradetestList) {
		this.legacysectiontwentyeighttradetestList = legacysectiontwentyeighttradetestList;
	}

	public List<LegacySectionTwentyEightTradeTest> getLegacysectiontwentyeighttradetestfilteredList() {
		return legacysectiontwentyeighttradetestfilteredList;
	}

	public void setLegacysectiontwentyeighttradetestfilteredList(List<LegacySectionTwentyEightTradeTest> legacysectiontwentyeighttradetestfilteredList) {
		this.legacysectiontwentyeighttradetestfilteredList = legacysectiontwentyeighttradetestfilteredList;
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
