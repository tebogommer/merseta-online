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
import haj.com.entity.lookup.LegacyInternship;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyInternshipService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyinternshipUI")
@ViewScoped
public class LegacyInternshipUI extends AbstractUI {

	private LegacyInternshipService service = new LegacyInternshipService();
	private List<LegacyInternship> legacyinternshipList = null;
	private List<LegacyInternship> legacyinternshipfilteredList = null;
	private LegacyInternship legacyinternship = null;
	private LazyDataModel<LegacyInternship> dataModel;

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
	 * Initialize method to get all LegacyInternship and prepare a for a create of a
	 * new LegacyInternship
	 * 
	 * @author TechFinium
	 * @see LegacyInternship
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyinternshipInfo();
		countAllEntries();
//		reportingMap = reportingService.runReports(LegacyInternship.class, true);
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
	 * Get all LegacyInternship for data table
	 * 
	 * @author TechFinium
	 * @see LegacyInternship
	 */
	public void legacyinternshipInfo() {

		dataModel = new LazyDataModel<LegacyInternship>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyInternship> retorno = new ArrayList<LegacyInternship>();

			@Override
			public List<LegacyInternship> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyInternship(LegacyInternship.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyInternship.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyInternship obj) {
				return obj.getId();
			}

			@Override
			public LegacyInternship getRowData(String rowKey) {
				for (LegacyInternship obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
				
	public void prepareLegacyInternship() {
		try {
			if(legacyinternship != null && legacyinternship.getId() != null) {
				super.runClientSideExecute("PF('updateDialog').hide()");
			}else {
				throw new Exception("Error with the legacy internship");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Insert LegacyInternship into database
	 * 
	 * @author TechFinium
	 * @see LegacyInternship
	 */
	public void legacyinternshipInsert() {
		try {
			service.create(this.legacyinternship);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyinternshipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyInternship in database
	 * 
	 * @author TechFinium
	 * @see LegacyInternship
	 */
	public void legacyinternshipUpdate() {
		try {
			service.update(this.legacyinternship);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyinternshipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyInternship from database
	 * 
	 * @author TechFinium
	 * @see LegacyInternship
	 */
	public void legacyinternshipDelete() {
		try {
			service.delete(this.legacyinternship);
			prepareNew();
			legacyinternshipInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyInternship
	 * 
	 * @author TechFinium
	 * @see LegacyInternship
	 */
	public void prepareNew() {
		legacyinternship = new LegacyInternship();
	}

	/*
	 * public List<SelectItem> getLegacyInternshipDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyinternshipInfo(); for (LegacyInternship ug : legacyinternshipList) { //
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
	public List<LegacyInternship> complete(String desc) {
		List<LegacyInternship> l = null;
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

	public List<LegacyInternship> getLegacyInternshipList() {
		return legacyinternshipList;
	}

	public void setLegacyInternshipList(List<LegacyInternship> legacyinternshipList) {
		this.legacyinternshipList = legacyinternshipList;
	}

	public LegacyInternship getLegacyinternship() {
		return legacyinternship;
	}

	public void setLegacyinternship(LegacyInternship legacyinternship) {
		this.legacyinternship = legacyinternship;
	}

	public List<LegacyInternship> getLegacyInternshipfilteredList() {
		return legacyinternshipfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyinternshipfilteredList
	 *            the new legacyinternshipfilteredList list
	 * @see LegacyInternship
	 */
	public void setLegacyInternshipfilteredList(List<LegacyInternship> legacyinternshipfilteredList) {
		this.legacyinternshipfilteredList = legacyinternshipfilteredList;
	}

	public LazyDataModel<LegacyInternship> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyInternship> dataModel) {
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
			List<LegacyInternship> csvDataList = csvUtil.getObjects(LegacyInternship.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyinternshipInfo();
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
			legacyinternshipInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiationRun() {
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
