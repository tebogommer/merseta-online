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
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyApprenticeshipTradeTestService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyapprenticeshiptradetestUI")
@ViewScoped
public class LegacyApprenticeshipTradeTestUI extends AbstractUI {

	private LegacyApprenticeshipTradeTestService service = new LegacyApprenticeshipTradeTestService();
	private List<LegacyApprenticeshipTradeTest> legacyapprenticeshiptradetestList = null;
	private List<LegacyApprenticeshipTradeTest> legacyapprenticeshiptradetestfilteredList = null;
	private LegacyApprenticeshipTradeTest legacyapprenticeshiptradetest = null;
	private LazyDataModel<LegacyApprenticeshipTradeTest> dataModel;

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
	 * Initialize method to get all LegacyApprenticeshipTradeTest and prepare a for
	 * a create of a new LegacyApprenticeshipTradeTest
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeshipTradeTest
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		// prepareNew();
		legacyapprenticeshiptradetestInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyApprenticeshipTradeTest.class, true);
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
	 * Get all LegacyApprenticeshipTradeTest for data table
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeshipTradeTest
	 */
	public void legacyapprenticeshiptradetestInfo() {
		dataModel = new LazyDataModel<LegacyApprenticeshipTradeTest>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyApprenticeshipTradeTest> retorno = new ArrayList<LegacyApprenticeshipTradeTest>();

			@Override
			public List<LegacyApprenticeshipTradeTest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyApprenticeshipTradeTest(LegacyApprenticeshipTradeTest.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyApprenticeshipTradeTest.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyApprenticeshipTradeTest obj) {
				return obj.getId();
			}

			@Override
			public LegacyApprenticeshipTradeTest getRowData(String rowKey) {
				for (LegacyApprenticeshipTradeTest obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert LegacyApprenticeshipTradeTest into database
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeshipTradeTest
	 */
	public void legacyapprenticeshiptradetestInsert() {
		try {
			service.create(this.legacyapprenticeshiptradetest);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyapprenticeshiptradetestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyApprenticeshipTradeTest in database
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeshipTradeTest
	 */
	public void legacyapprenticeshiptradetestUpdate() {
		try {
			service.update(this.legacyapprenticeshiptradetest);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyapprenticeshiptradetestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyApprenticeshipTradeTest from database
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeshipTradeTest
	 */
	public void legacyapprenticeshiptradetestDelete() {
		try {
			service.delete(this.legacyapprenticeshiptradetest);
			prepareNew();
			legacyapprenticeshiptradetestInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyApprenticeshipTradeTest
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeshipTradeTest
	 */
	public void prepareNew() {
		legacyapprenticeshiptradetest = new LegacyApprenticeshipTradeTest();
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyApprenticeshipTradeTest> complete(String desc) {
		List<LegacyApprenticeshipTradeTest> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyApprenticeshipTradeTest> getLegacyApprenticeshipTradeTestList() {
		return legacyapprenticeshiptradetestList;
	}

	public void setLegacyApprenticeshipTradeTestList(List<LegacyApprenticeshipTradeTest> legacyapprenticeshiptradetestList) {
		this.legacyapprenticeshiptradetestList = legacyapprenticeshiptradetestList;
	}

	public LegacyApprenticeshipTradeTest getLegacyapprenticeshiptradetest() {
		return legacyapprenticeshiptradetest;
	}

	public void setLegacyapprenticeshiptradetest(LegacyApprenticeshipTradeTest legacyapprenticeshiptradetest) {
		this.legacyapprenticeshiptradetest = legacyapprenticeshiptradetest;
	}

	public List<LegacyApprenticeshipTradeTest> getLegacyApprenticeshipTradeTestfilteredList() {
		return legacyapprenticeshiptradetestfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyapprenticeshiptradetestfilteredList
	 *            the new legacyapprenticeshiptradetestfilteredList list
	 * @see LegacyApprenticeshipTradeTest
	 */
	public void setLegacyApprenticeshipTradeTestfilteredList(List<LegacyApprenticeshipTradeTest> legacyapprenticeshiptradetestfilteredList) {
		this.legacyapprenticeshiptradetestfilteredList = legacyapprenticeshiptradetestfilteredList;
	}

	public LazyDataModel<LegacyApprenticeshipTradeTest> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyApprenticeshipTradeTest> dataModel) {
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
			List<LegacyApprenticeshipTradeTest> csvDataList = csvUtil.getObjects(LegacyApprenticeshipTradeTest.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyapprenticeshiptradetestInfo();
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
			legacyapprenticeshiptradetestInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacyapprenticeshiptradetestInfo();
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