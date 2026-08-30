package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
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
import haj.com.entity.SDFCompany;
import haj.com.entity.lookup.LegacyDTTCTrade;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyEmployerWA2WorkplaceService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyWorkplaceApprovalUI")
@ViewScoped
public class LegacyWorkplaceApprovalUI extends AbstractUI {

	private LegacyEmployerWA2WorkplaceService service = new LegacyEmployerWA2WorkplaceService();
	private List<LegacyEmployerWA2Workplace> legacyemployerwa2workplaceList = null;
	private List<LegacyEmployerWA2Workplace> legacyemployerwa2workplacefilteredList = null;
	private LegacyEmployerWA2Workplace legacyemployerwa2workplace = null;
	private LazyDataModel<LegacyEmployerWA2Workplace> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;

	private boolean displayDownload = false;
	// reporting
	private ReportingService reportingService = new ReportingService();
	private List<LegacyReportingBean> legacyReportingBeans = new ArrayList<>();
	private Map<String, List<Object>> reportingMap;
	private Map<String, ColumnBeans> columnMap;
	
	private SDFCompany globalSsdfCompany;
	private LegacyEmployerWA2Workplace legacyEmployerWA2Workplace;
	
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
	 * Initialize method to get all LegacyEmployerWA2Workplace and prepare a for a
	 * create of a new LegacyEmployerWA2Workplace
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Workplace
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		globalSsdfCompany = CompanyInfoUI.getGlobalSsdfCompany();
		prepareNew();
		legacyemployerwa2workplaceInfo();
		countAllEntries();
		reportingMap = reportingService.runReports(LegacyEmployerWA2Workplace.class, true);
		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>) reportingService.extractValues(reportingMap, "count");
		columnMap = reportingService.convertToColumns(reportingMap);
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
	 * Get all LegacyEmployerWA2Workplace for data table
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Workplace
	 */
	public void legacyemployerwa2workplaceInfo() {

		dataModel = new LazyDataModel<LegacyEmployerWA2Workplace>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyEmployerWA2Workplace> retorno = new ArrayList<LegacyEmployerWA2Workplace>();

			@Override
			public List<LegacyEmployerWA2Workplace> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					//filters.put("sdlNo", globalSsdfCompany.getCompany().getLevyNumber());
					filters.put("linkedSdl", globalSsdfCompany.getCompany().getLevyNumber());
					retorno = service.allLegacyEmployerWA2Workplace(LegacyEmployerWA2Workplace.class, first, pageSize, sortField, sortOrder, filters);
					for(LegacyEmployerWA2Workplace legacyEmployerWA2Workplace:retorno) {
						service.populateData(legacyEmployerWA2Workplace);
					}
					dataModel.setRowCount(service.count(LegacyEmployerWA2Workplace.class, filters));
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyEmployerWA2Workplace obj) {
				return obj.getId();
			}

			@Override
			public LegacyEmployerWA2Workplace getRowData(String rowKey) {
				for (LegacyEmployerWA2Workplace obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyEmployerWA2Workplace into database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Workplace
	 */
	public void legacyemployerwa2workplaceInsert() {
		try {
			service.create(this.legacyemployerwa2workplace);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2workplaceInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyEmployerWA2Workplace in database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Workplace
	 */
	public void legacyemployerwa2workplaceUpdate() {
		try {
			service.update(this.legacyemployerwa2workplace);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2workplaceInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyEmployerWA2Workplace from database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Workplace
	 */
	public void legacyemployerwa2workplaceDelete() {
		try {
			service.delete(this.legacyemployerwa2workplace);
			prepareNew();
			legacyemployerwa2workplaceInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyEmployerWA2Workplace
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2Workplace
	 */
	public void prepareNew() {
		legacyemployerwa2workplace = new LegacyEmployerWA2Workplace();
	}

	/*
	 * public List<SelectItem> getLegacyEmployerWA2WorkplaceDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyemployerwa2workplaceInfo(); for (LegacyEmployerWA2Workplace ug :
	 * legacyemployerwa2workplaceList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyEmployerWA2Workplace> complete(String desc) {
		List<LegacyEmployerWA2Workplace> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyEmployerWA2Workplace> getLegacyEmployerWA2WorkplaceList() {
		return legacyemployerwa2workplaceList;
	}

	public void setLegacyEmployerWA2WorkplaceList(List<LegacyEmployerWA2Workplace> legacyemployerwa2workplaceList) {
		this.legacyemployerwa2workplaceList = legacyemployerwa2workplaceList;
	}

	public LegacyEmployerWA2Workplace getLegacyemployerwa2workplace() {
		return legacyemployerwa2workplace;
	}

	public void setLegacyemployerwa2workplace(LegacyEmployerWA2Workplace legacyemployerwa2workplace) {
		this.legacyemployerwa2workplace = legacyemployerwa2workplace;
	}

	public List<LegacyEmployerWA2Workplace> getLegacyEmployerWA2WorkplacefilteredList() {
		return legacyemployerwa2workplacefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyemployerwa2workplacefilteredList
	 *            the new legacyemployerwa2workplacefilteredList list
	 * @see LegacyEmployerWA2Workplace
	 */
	public void setLegacyEmployerWA2WorkplacefilteredList(List<LegacyEmployerWA2Workplace> legacyemployerwa2workplacefilteredList) {
		this.legacyemployerwa2workplacefilteredList = legacyemployerwa2workplacefilteredList;
	}

	public LazyDataModel<LegacyEmployerWA2Workplace> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyEmployerWA2Workplace> dataModel) {
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
			List<LegacyEmployerWA2Workplace> csvDataList = csvUtil.getObjects(LegacyEmployerWA2Workplace.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyemployerwa2workplaceInfo();
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
			legacyemployerwa2workplaceInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validiationRun();
			addInfoMessage("Action Complete");
			legacyemployerwa2workplaceInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void redirectLegacyEmployerWA2Workplace() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("legacyId", this.legacyEmployerWA2Workplace.getId(), true);
		super.ajaxRedirect("/pages/workplaceapprovallegacy.jsf");
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

	public LegacyEmployerWA2Workplace getLegacyEmployerWA2Workplace() {
		return legacyEmployerWA2Workplace;
	}

	public void setLegacyEmployerWA2Workplace(LegacyEmployerWA2Workplace legacyEmployerWA2Workplace) {
		this.legacyEmployerWA2Workplace = legacyEmployerWA2Workplace;
	}
}
