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
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyProviderAccreditationService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyprovideraccreditationUI")
@ViewScoped
public class LegacyProviderAccreditationUI extends AbstractUI {

	private LegacyProviderAccreditationService service = new LegacyProviderAccreditationService();
	private List<LegacyProviderAccreditation> legacyprovideraccreditationList = null;
	private List<LegacyProviderAccreditation> legacyprovideraccreditationfilteredList = null;
	private LegacyProviderAccreditation legacyprovideraccreditation = null;
	private LazyDataModel<LegacyProviderAccreditation> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;
	private boolean displayDownload = false;
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
	 * Initialize method to get all LegacyProviderAccreditation and prepare a for a
	 * create of a new LegacyProviderAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyProviderAccreditation
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyprovideraccreditationInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyProviderAccreditation.class, true);
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
	 * Get all LegacyProviderAccreditation for data table
	 * 
	 * @author TechFinium
	 * @see LegacyProviderAccreditation
	 */
	public void legacyprovideraccreditationInfo() {

		dataModel = new LazyDataModel<LegacyProviderAccreditation>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyProviderAccreditation> retorno = new ArrayList<LegacyProviderAccreditation>();

			@Override
			public List<LegacyProviderAccreditation> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyProviderAccreditation(LegacyProviderAccreditation.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyProviderAccreditation.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyProviderAccreditation obj) {
				return obj.getId();
			}

			@Override
			public LegacyProviderAccreditation getRowData(String rowKey) {
				for (LegacyProviderAccreditation obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyProviderAccreditation into database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderAccreditation
	 */
	public void legacyprovideraccreditationInsert() {
		try {
			service.runValidiationForEntry(this.legacyprovideraccreditation);
			service.create(this.legacyprovideraccreditation);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyprovideraccreditationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runProcessForEntry(){
		try {
			service.runValidiationForEntry(this.legacyprovideraccreditation);
			service.update(this.legacyprovideraccreditation);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyprovideraccreditationInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyProviderAccreditation in database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderAccreditation
	 */
	public void legacyprovideraccreditationUpdate() {
		try {
			service.update(this.legacyprovideraccreditation);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyprovideraccreditationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyProviderAccreditation from database
	 * 
	 * @author TechFinium
	 * @see LegacyProviderAccreditation
	 */
	public void legacyprovideraccreditationDelete() {
		try {
			service.delete(this.legacyprovideraccreditation);
			prepareNew();
			legacyprovideraccreditationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyProviderAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyProviderAccreditation
	 */
	public void prepareNew() {
		legacyprovideraccreditation = new LegacyProviderAccreditation();
	}

	/*
	 * public List<SelectItem> getLegacyProviderAccreditationDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyprovideraccreditationInfo(); for (LegacyProviderAccreditation ug :
	 * legacyprovideraccreditationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyProviderAccreditation> complete(String desc) {
		List<LegacyProviderAccreditation> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyProviderAccreditation> getLegacyProviderAccreditationList() {
		return legacyprovideraccreditationList;
	}

	public void setLegacyProviderAccreditationList(List<LegacyProviderAccreditation> legacyprovideraccreditationList) {
		this.legacyprovideraccreditationList = legacyprovideraccreditationList;
	}

	public LegacyProviderAccreditation getLegacyprovideraccreditation() {
		return legacyprovideraccreditation;
	}

	public void setLegacyprovideraccreditation(LegacyProviderAccreditation legacyprovideraccreditation) {
		this.legacyprovideraccreditation = legacyprovideraccreditation;
	}

	public List<LegacyProviderAccreditation> getLegacyProviderAccreditationfilteredList() {
		return legacyprovideraccreditationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyprovideraccreditationfilteredList
	 *            the new legacyprovideraccreditationfilteredList list
	 * @see LegacyProviderAccreditation
	 */
	public void setLegacyProviderAccreditationfilteredList(List<LegacyProviderAccreditation> legacyprovideraccreditationfilteredList) {
		this.legacyprovideraccreditationfilteredList = legacyprovideraccreditationfilteredList;
	}

	public LazyDataModel<LegacyProviderAccreditation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyProviderAccreditation> dataModel) {
		this.dataModel = dataModel;
	}

	public void prepTypeSelection() {
		try {
			prepareNew();
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
			List<LegacyProviderAccreditation> csvDataList = csvUtil.getObjects(LegacyProviderAccreditation.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyprovideraccreditationInfo();
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
			prepareNew();
			service.deleteUploadedEntries();
			addInfoMessage("data cleared");
			legacyprovideraccreditationInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runValidiations() {
		try {
			prepareNew();
			service.correctStatusValidiation();
			addInfoMessage("Action complete. Email will be sent when process is complete.");
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

	public List<LegacyProviderAccreditation> getLegacyprovideraccreditationList() {
		return legacyprovideraccreditationList;
	}

	public void setLegacyprovideraccreditationList(List<LegacyProviderAccreditation> legacyprovideraccreditationList) {
		this.legacyprovideraccreditationList = legacyprovideraccreditationList;
	}

	public List<LegacyProviderAccreditation> getLegacyprovideraccreditationfilteredList() {
		return legacyprovideraccreditationfilteredList;
	}

	public void setLegacyprovideraccreditationfilteredList(List<LegacyProviderAccreditation> legacyprovideraccreditationfilteredList) {
		this.legacyprovideraccreditationfilteredList = legacyprovideraccreditationfilteredList;
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
