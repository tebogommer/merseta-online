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
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyProviderTrade;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyProviderTradeService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyprovidertradeUI")
@ViewScoped
public class LegacyProviderTradeUI extends AbstractUI {

	private LegacyProviderTradeService service = new LegacyProviderTradeService();
	private List<LegacyProviderTrade> legacyprovidertradeList = null;
	private List<LegacyProviderTrade> legacyprovidertradefilteredList = null;
	private LegacyProviderTrade legacyprovidertrade = null;
	private LazyDataModel<LegacyProviderTrade> dataModel;

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
	 * Initialize method to get all LegacyProviderTrade and prepare a for a create
	 * of a new LegacyProviderTrade
	 * 
	 * @author TechFinium
	 * @see LegacyProviderTrade
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyprovidertradeInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyProviderTrade.class, true);
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
	 * Get all LegacyProviderTrade for data table
	 * 
	 * @author TechFinium
	 * @see LegacyProviderTrade
	 */
	public void legacyprovidertradeInfo() {

		dataModel = new LazyDataModel<LegacyProviderTrade>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderTrade> retorno = new ArrayList<LegacyProviderTrade>();

			@Override
			public List<LegacyProviderTrade> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyProviderTrade(LegacyProviderTrade.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyProviderTrade.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderTrade obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderTrade getRowData(String rowKey) {
				for (LegacyProviderTrade obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyProviderTrade into database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderTrade
	 */
	public void legacyprovidertradeInsert() {
		try {
			service.create(this.legacyprovidertrade);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyprovidertradeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyProviderTrade in database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderTrade
	 */
	public void legacyprovidertradeUpdate() {
		try {
			service.update(this.legacyprovidertrade);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyprovidertradeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyProviderTrade from database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderTrade
	 */
	public void legacyprovidertradeDelete() {
		try {
			service.delete(this.legacyprovidertrade);
			prepareNew();
			legacyprovidertradeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyProviderTrade
	 * 
	 * @author TechFinium
	 * @see LegacyProviderTrade
	 */
	public void prepareNew() {
		legacyprovidertrade = new LegacyProviderTrade();
	}

	/*
	 * public List<SelectItem> getLegacyProviderTradeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyprovidertradeInfo(); for (LegacyProviderTrade ug :
	 * legacyprovidertradeList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyProviderTrade> complete(String desc) {
		List<LegacyProviderTrade> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyProviderTrade> getLegacyProviderTradeList() {
		return legacyprovidertradeList;
	}

	public void setLegacyProviderTradeList(List<LegacyProviderTrade> legacyprovidertradeList) {
		this.legacyprovidertradeList = legacyprovidertradeList;
	}

	public LegacyProviderTrade getLegacyprovidertrade() {
		return legacyprovidertrade;
	}

	public void setLegacyprovidertrade(LegacyProviderTrade legacyprovidertrade) {
		this.legacyprovidertrade = legacyprovidertrade;
	}

	public List<LegacyProviderTrade> getLegacyProviderTradefilteredList() {
		return legacyprovidertradefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyprovidertradefilteredList
	 *            the new legacyprovidertradefilteredList list
	 * @see LegacyProviderTrade
	 */
	public void setLegacyProviderTradefilteredList(List<LegacyProviderTrade> legacyprovidertradefilteredList) {
		this.legacyprovidertradefilteredList = legacyprovidertradefilteredList;
	}

	public LazyDataModel<LegacyProviderTrade> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyProviderTrade> dataModel) {
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
			List<LegacyProviderTrade> csvDataList = csvUtil.getObjects(LegacyProviderTrade.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyprovidertradeInfo();
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
			legacyprovidertradeInfo();
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

	public List<LegacyProviderTrade> getLegacyprovidertradeList() {
		return legacyprovidertradeList;
	}

	public void setLegacyprovidertradeList(List<LegacyProviderTrade> legacyprovidertradeList) {
		this.legacyprovidertradeList = legacyprovidertradeList;
	}

	public List<LegacyProviderTrade> getLegacyprovidertradefilteredList() {
		return legacyprovidertradefilteredList;
	}

	public void setLegacyprovidertradefilteredList(List<LegacyProviderTrade> legacyprovidertradefilteredList) {
		this.legacyprovidertradefilteredList = legacyprovidertradefilteredList;
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
