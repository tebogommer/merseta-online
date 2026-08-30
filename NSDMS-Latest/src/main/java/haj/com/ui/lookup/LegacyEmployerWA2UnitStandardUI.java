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

import haj.com.entity.lookup.LegacyEmployerWA2UnitStandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyEmployerWA2UnitStandardService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyemployerwa2unitstandardUI")
@ViewScoped
public class LegacyEmployerWA2UnitStandardUI extends AbstractUI {

	private LegacyEmployerWA2UnitStandardService service = new LegacyEmployerWA2UnitStandardService();
	private List<LegacyEmployerWA2UnitStandard> legacyemployerwa2unitstandardList = null;
	private List<LegacyEmployerWA2UnitStandard> legacyemployerwa2unitstandardfilteredList = null;
	private LegacyEmployerWA2UnitStandard legacyemployerwa2unitstandard = null;
	private LazyDataModel<LegacyEmployerWA2UnitStandard> dataModel;

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
	 * Initialize method to get all LegacyEmployerWA2UnitStandard and prepare a
	 * for a create of a new LegacyEmployerWA2UnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2UnitStandard
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyemployerwa2unitstandardInfo();
		countAllEntries();
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

	/**
	 * Get all LegacyEmployerWA2UnitStandard for data table
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public void legacyemployerwa2unitstandardInfo() {

		dataModel = new LazyDataModel<LegacyEmployerWA2UnitStandard>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyEmployerWA2UnitStandard> retorno = new ArrayList<LegacyEmployerWA2UnitStandard>();

			@Override
			public List<LegacyEmployerWA2UnitStandard> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyEmployerWA2UnitStandard(LegacyEmployerWA2UnitStandard.class, first,
							pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyEmployerWA2UnitStandard.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyEmployerWA2UnitStandard obj) {
				return obj.getId();
			}

			@Override
			public LegacyEmployerWA2UnitStandard getRowData(String rowKey) {
				for (LegacyEmployerWA2UnitStandard obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyEmployerWA2UnitStandard into database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public void legacyemployerwa2unitstandardInsert() {
		try {
			service.create(this.legacyemployerwa2unitstandard);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2unitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyEmployerWA2UnitStandard in database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public void legacyemployerwa2unitstandardUpdate() {
		try {
			service.update(this.legacyemployerwa2unitstandard);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyemployerwa2unitstandardInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyEmployerWA2UnitStandard from database
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public void legacyemployerwa2unitstandardDelete() {
		try {
			service.delete(this.legacyemployerwa2unitstandard);
			prepareNew();
			legacyemployerwa2unitstandardInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyEmployerWA2UnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public void prepareNew() {
		legacyemployerwa2unitstandard = new LegacyEmployerWA2UnitStandard();
	}

	/*
	 * public List<SelectItem> getLegacyEmployerWA2UnitStandardDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyemployerwa2unitstandardInfo(); for (LegacyEmployerWA2UnitStandard
	 * ug : legacyemployerwa2unitstandardList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyEmployerWA2UnitStandard> complete(String desc) {
		List<LegacyEmployerWA2UnitStandard> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyEmployerWA2UnitStandard> getLegacyEmployerWA2UnitStandardList() {
		return legacyemployerwa2unitstandardList;
	}

	public void setLegacyEmployerWA2UnitStandardList(
			List<LegacyEmployerWA2UnitStandard> legacyemployerwa2unitstandardList) {
		this.legacyemployerwa2unitstandardList = legacyemployerwa2unitstandardList;
	}

	public LegacyEmployerWA2UnitStandard getLegacyemployerwa2unitstandard() {
		return legacyemployerwa2unitstandard;
	}

	public void setLegacyemployerwa2unitstandard(LegacyEmployerWA2UnitStandard legacyemployerwa2unitstandard) {
		this.legacyemployerwa2unitstandard = legacyemployerwa2unitstandard;
	}

	public List<LegacyEmployerWA2UnitStandard> getLegacyEmployerWA2UnitStandardfilteredList() {
		return legacyemployerwa2unitstandardfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyemployerwa2unitstandardfilteredList
	 *            the new legacyemployerwa2unitstandardfilteredList list
	 * @see LegacyEmployerWA2UnitStandard
	 */
	public void setLegacyEmployerWA2UnitStandardfilteredList(
			List<LegacyEmployerWA2UnitStandard> legacyemployerwa2unitstandardfilteredList) {
		this.legacyemployerwa2unitstandardfilteredList = legacyemployerwa2unitstandardfilteredList;
	}

	public LazyDataModel<LegacyEmployerWA2UnitStandard> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyEmployerWA2UnitStandard> dataModel) {
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
			List<LegacyEmployerWA2UnitStandard> csvDataList = csvUtil.getObjects(LegacyEmployerWA2UnitStandard.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyemployerwa2unitstandardInfo();
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
			legacyemployerwa2unitstandardInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runValidiations(){
		try {
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacyemployerwa2unitstandardInfo();
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

	public boolean isDisplayDownload() {
		return displayDownload;
	}

	public void setDisplayDownload(boolean displayDownload) {
		this.displayDownload = displayDownload;
	}

}
