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
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyExperientialService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyexperientialUI")
@ViewScoped
public class LegacyExperientialUI extends AbstractUI {

	private LegacyExperientialService service = new LegacyExperientialService();
	private List<LegacyExperiential> legacyexperientialList = null;
	private List<LegacyExperiential> legacyexperientialfilteredList = null;
	private LegacyExperiential legacyexperiential = null;
	private LazyDataModel<LegacyExperiential> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;

	private boolean displayDownload = false;
	//reporting
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
	 * Initialize method to get all LegacyExperiential and prepare a for a create of
	 * a new LegacyExperiential
	 * 
	 * @author TechFinium
	 * @see LegacyExperiential
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyexperientialInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyExperiential.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>)reportingService.extractValues(reportingMap, "count"); 
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
	 * Get all LegacyExperiential for data table
	 * 
	 * @author TechFinium
	 * @see LegacyExperiential
	 */
	public void legacyexperientialInfo() {

		dataModel = new LazyDataModel<LegacyExperiential>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyExperiential> retorno = new ArrayList<LegacyExperiential>();

			@Override
			public List<LegacyExperiential> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLegacyExperiential(LegacyExperiential.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyExperiential.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyExperiential obj) {
				return obj.getId();
			}

			@Override
			public LegacyExperiential getRowData(String rowKey) {
				for (LegacyExperiential obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}
	
	public void prepareLegacyExperiential() {
		try {
			if(legacyexperiential != null && legacyexperiential.getId() != null) {
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
	 * Insert LegacyExperiential into database
	 * 
	 * @author TechFinium
	 * @see LegacyExperiential
	 */
	public void legacyexperientialInsert() {
		try {
			service.create(this.legacyexperiential);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyexperientialInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyExperiential in database
	 * 
	 * @author TechFinium
	 * @see LegacyExperiential
	 */
	public void legacyexperientialUpdate() {
		try {
			service.update(this.legacyexperiential);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyexperientialInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyExperiential from database
	 * 
	 * @author TechFinium
	 * @see LegacyExperiential
	 */
	public void legacyexperientialDelete() {
		try {
			service.delete(this.legacyexperiential);
			prepareNew();
			legacyexperientialInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyExperiential
	 * 
	 * @author TechFinium
	 * @see LegacyExperiential
	 */
	public void prepareNew() {
		legacyexperiential = new LegacyExperiential();
	}

	/*
	 * public List<SelectItem> getLegacyExperientialDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyexperientialInfo(); for (LegacyExperiential ug :
	 * legacyexperientialList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<LegacyExperiential> complete(String desc) {
		List<LegacyExperiential> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyExperiential> getLegacyExperientialList() {
		return legacyexperientialList;
	}

	public void setLegacyExperientialList(List<LegacyExperiential> legacyexperientialList) {
		this.legacyexperientialList = legacyexperientialList;
	}

	public LegacyExperiential getLegacyexperiential() {
		return legacyexperiential;
	}

	public void setLegacyexperiential(LegacyExperiential legacyexperiential) {
		this.legacyexperiential = legacyexperiential;
	}

	public List<LegacyExperiential> getLegacyExperientialfilteredList() {
		return legacyexperientialfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyexperientialfilteredList the new legacyexperientialfilteredList
	 *                                       list
	 * @see LegacyExperiential
	 */
	public void setLegacyExperientialfilteredList(List<LegacyExperiential> legacyexperientialfilteredList) {
		this.legacyexperientialfilteredList = legacyexperientialfilteredList;
	}

	public LazyDataModel<LegacyExperiential> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyExperiential> dataModel) {
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
			List<LegacyExperiential> csvDataList = csvUtil.getObjects(LegacyExperiential.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyexperientialInfo();
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
			legacyexperientialInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacyexperientialInfo();
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

	public List<LegacyExperiential> getLegacyexperientialList() {
		return legacyexperientialList;
	}

	public void setLegacyexperientialList(List<LegacyExperiential> legacyexperientialList) {
		this.legacyexperientialList = legacyexperientialList;
	}

	public List<LegacyExperiential> getLegacyexperientialfilteredList() {
		return legacyexperientialfilteredList;
	}

	public void setLegacyexperientialfilteredList(List<LegacyExperiential> legacyexperientialfilteredList) {
		this.legacyexperientialfilteredList = legacyexperientialfilteredList;
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
