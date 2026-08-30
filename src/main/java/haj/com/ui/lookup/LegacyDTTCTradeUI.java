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
import haj.com.entity.lookup.LegacyDTTCApproval;
import haj.com.entity.lookup.LegacyDTTCTrade;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyDTTCTradeService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacydttctradeUI")
@ViewScoped
public class LegacyDTTCTradeUI extends AbstractUI {

	private LegacyDTTCTradeService service = new LegacyDTTCTradeService();
	private List<LegacyDTTCTrade> legacydttctradeList = null;
	private List<LegacyDTTCTrade> legacydttctradefilteredList = null;
	private LegacyDTTCTrade legacydttctrade = null;
	private LazyDataModel<LegacyDTTCTrade> dataModel;

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
	 * Initialize method to get all LegacyDTTCTrade and prepare a for a create of a
	 * new LegacyDTTCTrade
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCTrade
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacydttctradeInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyDTTCTrade.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyDTTCTrade for data table
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCTrade
	 */
	public void legacydttctradeInfo() {

		dataModel = new LazyDataModel<LegacyDTTCTrade>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyDTTCTrade> retorno = new ArrayList<LegacyDTTCTrade>();

			@Override
			public List<LegacyDTTCTrade> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyDTTCTrade(LegacyDTTCTrade.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyDTTCTrade.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyDTTCTrade obj) {
				return obj.getId();
			}

			@Override
			public LegacyDTTCTrade getRowData(String rowKey) {
				for (LegacyDTTCTrade obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyDTTCTrade into database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCTrade
	 */
	public void legacydttctradeInsert() {
		try {
			service.create(this.legacydttctrade);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttctradeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyDTTCTrade in database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCTrade
	 */
	public void legacydttctradeUpdate() {
		try {
			service.update(this.legacydttctrade);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttctradeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyDTTCTrade from database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCTrade
	 */
	public void legacydttctradeDelete() {
		try {
			service.delete(this.legacydttctrade);
			prepareNew();
			legacydttctradeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyDTTCTrade
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCTrade
	 */
	public void prepareNew() {
		legacydttctrade = new LegacyDTTCTrade();
	}

	/*
	 * public List<SelectItem> getLegacyDTTCTradeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacydttctradeInfo(); for (LegacyDTTCTrade ug : legacydttctradeList) { //
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
	public List<LegacyDTTCTrade> complete(String desc) {
		List<LegacyDTTCTrade> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyDTTCTrade> getLegacyDTTCTradeList() {
		return legacydttctradeList;
	}

	public void setLegacyDTTCTradeList(List<LegacyDTTCTrade> legacydttctradeList) {
		this.legacydttctradeList = legacydttctradeList;
	}

	public LegacyDTTCTrade getLegacydttctrade() {
		return legacydttctrade;
	}

	public void setLegacydttctrade(LegacyDTTCTrade legacydttctrade) {
		this.legacydttctrade = legacydttctrade;
	}

	public List<LegacyDTTCTrade> getLegacyDTTCTradefilteredList() {
		return legacydttctradefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacydttctradefilteredList
	 *            the new legacydttctradefilteredList list
	 * @see LegacyDTTCTrade
	 */
	public void setLegacyDTTCTradefilteredList(List<LegacyDTTCTrade> legacydttctradefilteredList) {
		this.legacydttctradefilteredList = legacydttctradefilteredList;
	}

	public LazyDataModel<LegacyDTTCTrade> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyDTTCTrade> dataModel) {
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
			List<LegacyDTTCTrade> csvDataList = csvUtil.getObjects(LegacyDTTCTrade.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacydttctradeInfo();
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
			legacydttctradeInfo();
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
			legacydttctradeInfo();
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

	public List<LegacyDTTCTrade> getLegacydttctradeList() {
		return legacydttctradeList;
	}

	public void setLegacydttctradeList(List<LegacyDTTCTrade> legacydttctradeList) {
		this.legacydttctradeList = legacydttctradeList;
	}

	public List<LegacyDTTCTrade> getLegacydttctradefilteredList() {
		return legacydttctradefilteredList;
	}

	public void setLegacydttctradefilteredList(List<LegacyDTTCTrade> legacydttctradefilteredList) {
		this.legacydttctradefilteredList = legacydttctradefilteredList;
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
