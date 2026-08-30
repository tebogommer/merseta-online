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

import haj.com.entity.lookup.LegacyDTTCUnitStandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyDTTCUnitStandardService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacydttcunitstandardUI")
@ViewScoped
public class LegacyDTTCUnitStandardUI extends AbstractUI {

	private LegacyDTTCUnitStandardService service = new LegacyDTTCUnitStandardService();
	private List<LegacyDTTCUnitStandard> legacydttcunitstandardList = null;
	private List<LegacyDTTCUnitStandard> legacydttcunitstandardfilteredList = null;
	private LegacyDTTCUnitStandard legacydttcunitstandard = null;
	private LazyDataModel<LegacyDTTCUnitStandard> dataModel;

	private CSVUtil csvUtil = new CSVUtil();
	private List<String> csvTypeSelectionList = new ArrayList<>();
	private String csvTypeSelection;

	private boolean displayDownload = false;
	
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
	 * Initialize method to get all LegacyDTTCUnitStandard and prepare a for a
	 * create of a new LegacyDTTCUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCUnitStandard
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacydttcunitstandardInfo();
		countAllEntries();
	}

	/**
	 * Get all LegacyDTTCUnitStandard for data table
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCUnitStandard
	 */
	public void legacydttcunitstandardInfo() {

		dataModel = new LazyDataModel<LegacyDTTCUnitStandard>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyDTTCUnitStandard> retorno = new ArrayList<LegacyDTTCUnitStandard>();

			@Override
			public List<LegacyDTTCUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLegacyDTTCUnitStandard(LegacyDTTCUnitStandard.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyDTTCUnitStandard.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyDTTCUnitStandard obj) {
				return obj.getId();
			}

			@Override
			public LegacyDTTCUnitStandard getRowData(String rowKey) {
				for (LegacyDTTCUnitStandard obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyDTTCUnitStandard into database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCUnitStandard
	 */
	public void legacydttcunitstandardInsert() {
		try {
			service.create(this.legacydttcunitstandard);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttcunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyDTTCUnitStandard in database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCUnitStandard
	 */
	public void legacydttcunitstandardUpdate() {
		try {
			service.update(this.legacydttcunitstandard);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttcunitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyDTTCUnitStandard from database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCUnitStandard
	 */
	public void legacydttcunitstandardDelete() {
		try {
			service.delete(this.legacydttcunitstandard);
			prepareNew();
			legacydttcunitstandardInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyDTTCUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCUnitStandard
	 */
	public void prepareNew() {
		legacydttcunitstandard = new LegacyDTTCUnitStandard();
	}

	/*
	 * public List<SelectItem> getLegacyDTTCUnitStandardDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacydttcunitstandardInfo(); for (LegacyDTTCUnitStandard ug :
	 * legacydttcunitstandardList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyDTTCUnitStandard> complete(String desc) {
		List<LegacyDTTCUnitStandard> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyDTTCUnitStandard> getLegacyDTTCUnitStandardList() {
		return legacydttcunitstandardList;
	}

	public void setLegacyDTTCUnitStandardList(List<LegacyDTTCUnitStandard> legacydttcunitstandardList) {
		this.legacydttcunitstandardList = legacydttcunitstandardList;
	}

	public LegacyDTTCUnitStandard getLegacydttcunitstandard() {
		return legacydttcunitstandard;
	}

	public void setLegacydttcunitstandard(LegacyDTTCUnitStandard legacydttcunitstandard) {
		this.legacydttcunitstandard = legacydttcunitstandard;
	}

	public List<LegacyDTTCUnitStandard> getLegacyDTTCUnitStandardfilteredList() {
		return legacydttcunitstandardfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacydttcunitstandardfilteredList
	 *            the new legacydttcunitstandardfilteredList list
	 * @see LegacyDTTCUnitStandard
	 */
	public void setLegacyDTTCUnitStandardfilteredList(List<LegacyDTTCUnitStandard> legacydttcunitstandardfilteredList) {
		this.legacydttcunitstandardfilteredList = legacydttcunitstandardfilteredList;
	}

	public LazyDataModel<LegacyDTTCUnitStandard> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyDTTCUnitStandard> dataModel) {
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
			List<LegacyDTTCUnitStandard> csvDataList = csvUtil.getObjects(LegacyDTTCUnitStandard.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacydttcunitstandardInfo();
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
			legacydttcunitstandardInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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
	
	public void runValidiations(){
		try {
			service.validiationRun();
			addInfoMessage("Action Complete");
			legacydttcunitstandardInfo();
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

	public List<LegacyDTTCUnitStandard> getLegacydttcunitstandardList() {
		return legacydttcunitstandardList;
	}

	public void setLegacydttcunitstandardList(List<LegacyDTTCUnitStandard> legacydttcunitstandardList) {
		this.legacydttcunitstandardList = legacydttcunitstandardList;
	}

	public List<LegacyDTTCUnitStandard> getLegacydttcunitstandardfilteredList() {
		return legacydttcunitstandardfilteredList;
	}

	public void setLegacydttcunitstandardfilteredList(List<LegacyDTTCUnitStandard> legacydttcunitstandardfilteredList) {
		this.legacydttcunitstandardfilteredList = legacydttcunitstandardfilteredList;
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
}
