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
import haj.com.entity.lookup.LegacyPerson;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyPersonService;
import haj.com.service.lookup.ReportingService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacypersonUI")
@ViewScoped
public class LegacyPersonUI extends AbstractUI {

	private LegacyPersonService service = new LegacyPersonService();
	private List<LegacyPerson> legacypersonList = null;
	private List<LegacyPerson> legacypersonfilteredList = null;
	private LegacyPerson legacyperson = null;
	private LazyDataModel<LegacyPerson> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;
	private boolean displayDownload = false;
	
	/* Business Rules One - Valid RSA ID NUMBERS */
	private boolean displayDownloadNumberEmpty = false;
	private Integer countIdNumberEmpty;
	private LazyDataModel<LegacyPerson> legacyPersonIdNumberEmptyDataModel;
	private boolean displayDownloadNumberValid = false;
	private Integer countIdNumberValid;
	private LazyDataModel<LegacyPerson> legacyPersonIdNumberValidDataModel;
	private boolean displayDownloadNumberInvalid = false;
	private Integer countIdNumberInvalid;
	private LazyDataModel<LegacyPerson> legacyPersonIdNumberInvalidDataModel;
	
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
	 * Initialize method to get all LegacyPerson and prepare a for a create of a
	 * new LegacyPerson
	 * 
	 * @author TechFinium
	 * @see LegacyPerson
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	private void runInit() throws Exception {
		prepareNew();
		legacypersonInfo();
		countAllEntries();
//		runValidIdDataTables();
//		reportingMap = reportingService.runReports(LegacyPerson.class, true);
//		legacyReportingBeans = (List<LegacyReportingBean>) (List<?>)reportingService.extractValues(reportingMap, "count"); 
//		columnMap = reportingService.convertToColumns(reportingMap);
	}

	/**
	 * Get all LegacyPerson for data table
	 * 
	 * @author TechFinium
	 * @see LegacyPerson
	 */
	public void legacypersonInfo() {
		dataModel = new LazyDataModel<LegacyPerson>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyPerson> retorno = new ArrayList<LegacyPerson>();
			@Override
			public List<LegacyPerson> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyPerson(LegacyPerson.class, first, pageSize, sortField, sortOrder,filters);
					dataModel.setRowCount(service.count(LegacyPerson.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyPerson obj) {
				return obj.getId();
			}
			@Override
			public LegacyPerson getRowData(String rowKey) {
				for (LegacyPerson obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert LegacyPerson into database
	 * 
	 * @author TechFinium
	 * @see LegacyPerson
	 */
	public void legacypersonInsert() {
		try {
			service.create(this.legacyperson);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacypersonInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyPerson in database
	 * 
	 * @author TechFinium
	 * @see LegacyPerson
	 */
	public void legacypersonUpdate() {
		try {
			service.update(this.legacyperson);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacypersonInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyPerson from database
	 * 
	 * @author TechFinium
	 * @see LegacyPerson
	 */
	public void legacypersonDelete() {
		try {
			service.delete(this.legacyperson);
			prepareNew();
			legacypersonInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyPerson
	 * 
	 * @author TechFinium
	 * @see LegacyPerson
	 */
	public void prepareNew() {
		legacyperson = new LegacyPerson();
	}
	
	public void countAllEntries(){
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
			List<LegacyPerson> csvDataList = csvUtil.getObjects(LegacyPerson.class, event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacypersonInfo();
			countAllEntries();
			runValidIdDataTables();
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
			legacypersonInfo();
			countAllEntries();
			runValidIdDataTables();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* Business Rule One - Valid RSA ID numbers */
	public void runValidIdDataTables() throws Exception{
		legacyPersonIdNumberEmptyInfo();
		legacyPersonIdNumberValidInfo();
		legacyPersonIdNumberInvalidInfo();
	}
	
	public void runBusinessRuleValidiationRun(){
		try {
			service.validateRsaIdNumbers();
			runValidIdDataTables();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void legacyPersonIdNumberEmptyInfo() {
		try {
			countIdNumberEmpty = service.countAllValidRsaIdNumberEmpty();
			if (countIdNumberEmpty < 65000) {
				displayDownloadNumberEmpty = true;
	        } else {
	        	displayDownloadNumberEmpty = false;
			}
		} catch (Exception e) {
			displayDownloadNumberEmpty = false;
			countIdNumberEmpty = 0;
		}
		legacyPersonIdNumberEmptyDataModel = new LazyDataModel<LegacyPerson>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyPerson> retorno = new ArrayList<LegacyPerson>();
			@Override
			public List<LegacyPerson> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyPersonRsaIdNumberEmpty(first, pageSize, sortField, sortOrder,filters);
					legacyPersonIdNumberEmptyDataModel.setRowCount(service.countAllLegacyPersonRsaIdNumberEmpty(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyPerson obj) {
				return obj.getId();
			}
			@Override
			public LegacyPerson getRowData(String rowKey) {
				for (LegacyPerson obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void legacyPersonIdNumberValidInfo() {
		try {
			countIdNumberValid = service.countAllValidRsaIdNumberByValue(true);
			if (countIdNumberValid < 65000) {
				displayDownloadNumberValid = true;
	        } else {
	        	displayDownloadNumberValid = false;
			}
		} catch (Exception e) {
			displayDownloadNumberValid = false;
			countIdNumberValid = 0;
		}
		legacyPersonIdNumberValidDataModel = new LazyDataModel<LegacyPerson>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyPerson> retorno = new ArrayList<LegacyPerson>();
			@Override
			public List<LegacyPerson> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyPersonRsaIdNumberByValue(first, pageSize, sortField, sortOrder,filters, true);
					legacyPersonIdNumberValidDataModel.setRowCount(service.countAllLegacyPersonRsaIdNumberByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyPerson obj) {
				return obj.getId();
			}
			@Override
			public LegacyPerson getRowData(String rowKey) {
				for (LegacyPerson obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void legacyPersonIdNumberInvalidInfo() {
		try {
			countIdNumberInvalid = service.countAllValidRsaIdNumberByValue(false);
			if (countIdNumberInvalid < 65000) {
				displayDownloadNumberInvalid = true;
	        } else {
	        	displayDownloadNumberInvalid = false;
			}
		} catch (Exception e) {
			displayDownloadNumberInvalid = false;
			countIdNumberInvalid = 0;
		}
		legacyPersonIdNumberInvalidDataModel = new LazyDataModel<LegacyPerson>() {
			private static final long serialVersionUID = 1L;
			private List<LegacyPerson> retorno = new ArrayList<LegacyPerson>();
			@Override
			public List<LegacyPerson> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLegacyPersonRsaIdNumberByValue(first, pageSize, sortField, sortOrder,filters, false);
					legacyPersonIdNumberInvalidDataModel.setRowCount(service.countAllLegacyPersonRsaIdNumberByValue(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(LegacyPerson obj) {
				return obj.getId();
			}
			@Override
			public LegacyPerson getRowData(String rowKey) {
				for (LegacyPerson obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	/* getters and setters */
	public List<LegacyPerson> getLegacyPersonList() {
		return legacypersonList;
	}

	public void setLegacyPersonList(List<LegacyPerson> legacypersonList) {
		this.legacypersonList = legacypersonList;
	}

	public LegacyPerson getLegacyperson() {
		return legacyperson;
	}

	public void setLegacyperson(LegacyPerson legacyperson) {
		this.legacyperson = legacyperson;
	}

	public List<LegacyPerson> getLegacyPersonfilteredList() {
		return legacypersonfilteredList;
	}

	public void setLegacyPersonfilteredList(List<LegacyPerson> legacypersonfilteredList) {
		this.legacypersonfilteredList = legacypersonfilteredList;
	}

	public LazyDataModel<LegacyPerson> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyPerson> dataModel) {
		this.dataModel = dataModel;
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

	public Integer getCountIdNumberEmpty() {
		return countIdNumberEmpty;
	}

	public void setCountIdNumberEmpty(Integer countIdNumberEmpty) {
		this.countIdNumberEmpty = countIdNumberEmpty;
	}

	public Integer getCountIdNumberValid() {
		return countIdNumberValid;
	}

	public void setCountIdNumberValid(Integer countIdNumberValid) {
		this.countIdNumberValid = countIdNumberValid;
	}

	public Integer getCountIdNumberInvalid() {
		return countIdNumberInvalid;
	}

	public void setCountIdNumberInvalid(Integer countIdNumberInvalid) {
		this.countIdNumberInvalid = countIdNumberInvalid;
	}

	public boolean isDisplayDownload() {
		return displayDownload;
	}

	public void setDisplayDownload(boolean displayDownload) {
		this.displayDownload = displayDownload;
	}

	public boolean isDisplayDownloadNumberEmpty() {
		return displayDownloadNumberEmpty;
	}

	public void setDisplayDownloadNumberEmpty(boolean displayDownloadNumberEmpty) {
		this.displayDownloadNumberEmpty = displayDownloadNumberEmpty;
	}

	public boolean isDisplayDownloadNumberValid() {
		return displayDownloadNumberValid;
	}

	public void setDisplayDownloadNumberValid(boolean displayDownloadNumberValid) {
		this.displayDownloadNumberValid = displayDownloadNumberValid;
	}

	public boolean isDisplayDownloadNumberInvalid() {
		return displayDownloadNumberInvalid;
	}

	public void setDisplayDownloadNumberInvalid(boolean displayDownloadNumberInvalid) {
		this.displayDownloadNumberInvalid = displayDownloadNumberInvalid;
	}

	public LazyDataModel<LegacyPerson> getLegacyPersonIdNumberEmptyDataModel() {
		return legacyPersonIdNumberEmptyDataModel;
	}

	public void setLegacyPersonIdNumberEmptyDataModel(LazyDataModel<LegacyPerson> legacyPersonIdNumberEmptyDataModel) {
		this.legacyPersonIdNumberEmptyDataModel = legacyPersonIdNumberEmptyDataModel;
	}

	public LazyDataModel<LegacyPerson> getLegacyPersonIdNumberValidDataModel() {
		return legacyPersonIdNumberValidDataModel;
	}

	public void setLegacyPersonIdNumberValidDataModel(LazyDataModel<LegacyPerson> legacyPersonIdNumberValidDataModel) {
		this.legacyPersonIdNumberValidDataModel = legacyPersonIdNumberValidDataModel;
	}

	public LazyDataModel<LegacyPerson> getLegacyPersonIdNumberInvalidDataModel() {
		return legacyPersonIdNumberInvalidDataModel;
	}

	public void setLegacyPersonIdNumberInvalidDataModel(LazyDataModel<LegacyPerson> legacyPersonIdNumberInvalidDataModel) {
		this.legacyPersonIdNumberInvalidDataModel = legacyPersonIdNumberInvalidDataModel;
	}

	public List<LegacyReportingBean> getLegacyReportingBeans() {
		return legacyReportingBeans;
	}

	public void setLegacyReportingBeans(List<LegacyReportingBean> legacyReportingBeans) {
		this.legacyReportingBeans = legacyReportingBeans;
	}

	public Map<String, ColumnBeans> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, ColumnBeans> columnMap) {
		this.columnMap = columnMap;
	}
}
