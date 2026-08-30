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
import haj.com.entity.lookup.LegacyEmployerWA2Trade;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyEmployerWA2TradeService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyemployerwa2tradeUI")
@ViewScoped
public class LegacyEmployerWA2TradeUI extends AbstractUI {

	private LegacyEmployerWA2TradeService service = new LegacyEmployerWA2TradeService();
	private List<LegacyEmployerWA2Trade> legacyemployerwa2tradeList = null;
	private List<LegacyEmployerWA2Trade> legacyemployerwa2tradefilteredList = null;
	private LegacyEmployerWA2Trade legacyemployerwa2trade = null;
	private LazyDataModel<LegacyEmployerWA2Trade> dataModel;

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
	 * Initialize method to get all LegacyEmployerWA2Trade and prepare a for a
	 * create of a new LegacyEmployerWA2Trade
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Trade
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyemployerwa2tradeInfo();
		countAllEntries();
		prepTypeSelection();
//		reportingMap = reportingService.runReports(LegacyEmployerWA2Trade.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyEmployerWA2Trade for data table
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Trade
	 */
	public void legacyemployerwa2tradeInfo() {

		dataModel = new LazyDataModel<LegacyEmployerWA2Trade>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyEmployerWA2Trade> retorno = new ArrayList<LegacyEmployerWA2Trade>();

			@Override
			public List<LegacyEmployerWA2Trade> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyEmployerWA2Trade(LegacyEmployerWA2Trade.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyEmployerWA2Trade.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyEmployerWA2Trade obj) {
				return obj.getId();
			}

			@Override
			public LegacyEmployerWA2Trade getRowData(String rowKey) {
				for (LegacyEmployerWA2Trade obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyEmployerWA2Trade into database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Trade
	 */
	public void legacyemployerwa2tradeInsert() {
		try {
			service.create(this.legacyemployerwa2trade);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2tradeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyEmployerWA2Trade in database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Trade
	 */
	public void legacyemployerwa2tradeUpdate() {
		try {
			service.update(this.legacyemployerwa2trade);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2tradeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyEmployerWA2Trade from database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Trade
	 */
	public void legacyemployerwa2tradeDelete() {
		try {
			service.delete(this.legacyemployerwa2trade);
			prepareNew();
			legacyemployerwa2tradeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyEmployerWA2Trade
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Trade
	 */
	public void prepareNew() {
		legacyemployerwa2trade = new LegacyEmployerWA2Trade();
	}

	/*
	 * public List<SelectItem> getLegacyEmployerWA2TradeDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyemployerwa2tradeInfo(); for (LegacyEmployerWA2Trade ug :
	 * legacyemployerwa2tradeList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyEmployerWA2Trade> complete(String desc) {
		List<LegacyEmployerWA2Trade> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyEmployerWA2Trade> getLegacyEmployerWA2TradeList() {
		return legacyemployerwa2tradeList;
	}

	public void setLegacyEmployerWA2TradeList(List<LegacyEmployerWA2Trade> legacyemployerwa2tradeList) {
		this.legacyemployerwa2tradeList = legacyemployerwa2tradeList;
	}

	public LegacyEmployerWA2Trade getLegacyemployerwa2trade() {
		return legacyemployerwa2trade;
	}

	public void setLegacyemployerwa2trade(LegacyEmployerWA2Trade legacyemployerwa2trade) {
		this.legacyemployerwa2trade = legacyemployerwa2trade;
	}

	public List<LegacyEmployerWA2Trade> getLegacyEmployerWA2TradefilteredList() {
		return legacyemployerwa2tradefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyemployerwa2tradefilteredList
	 *            the new legacyemployerwa2tradefilteredList list
	 * @see LegacyEmployerWA2Trade
	 */
	public void setLegacyEmployerWA2TradefilteredList(List<LegacyEmployerWA2Trade> legacyemployerwa2tradefilteredList) {
		this.legacyemployerwa2tradefilteredList = legacyemployerwa2tradefilteredList;
	}

	public LazyDataModel<LegacyEmployerWA2Trade> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyEmployerWA2Trade> dataModel) {
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
				csvTypeSelection = ",";
			}
			logger.info("Starting file upload");
			List<LegacyEmployerWA2Trade> csvDataList = csvUtil.getObjects(LegacyEmployerWA2Trade.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyemployerwa2tradeInfo();
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
			legacyemployerwa2tradeInfo();
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

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacyemployerwa2tradeInfo();
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
