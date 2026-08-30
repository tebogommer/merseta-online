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

import haj.com.entity.lookup.LegacyModeratorLearnership;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyModeratorLearnershipService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacymoderatorlearnershipUI")
@ViewScoped
public class LegacyModeratorLearnershipUI extends AbstractUI {

	private LegacyModeratorLearnershipService service = new LegacyModeratorLearnershipService();
	private List<LegacyModeratorLearnership> legacymoderatorlearnershipList = null;
	private List<LegacyModeratorLearnership> legacymoderatorlearnershipfilteredList = null;
	private LegacyModeratorLearnership legacymoderatorlearnership = null;
	private LazyDataModel<LegacyModeratorLearnership> dataModel;

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
	 * Initialize method to get all LegacyModeratorLearnership and prepare a for a
	 * create of a new LegacyModeratorLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorLearnership
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacymoderatorlearnershipInfo();
		countAllEntries();
	}

	/**
	 * Get all LegacyModeratorLearnership for data table
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorLearnership
	 */
	public void legacymoderatorlearnershipInfo() {

		dataModel = new LazyDataModel<LegacyModeratorLearnership>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyModeratorLearnership> retorno = new ArrayList<LegacyModeratorLearnership>();

			@Override
			public List<LegacyModeratorLearnership> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLegacyModeratorLearnership(LegacyModeratorLearnership.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyModeratorLearnership.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyModeratorLearnership obj) {
				return obj.getId();
			}

			@Override
			public LegacyModeratorLearnership getRowData(String rowKey) {
				for (LegacyModeratorLearnership obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyModeratorLearnership into database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorLearnership
	 */
	public void legacymoderatorlearnershipInsert() {
		try {
			service.create(this.legacymoderatorlearnership);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatorlearnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyModeratorLearnership in database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorLearnership
	 */
	public void legacymoderatorlearnershipUpdate() {
		try {
			service.update(this.legacymoderatorlearnership);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacymoderatorlearnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyModeratorLearnership from database
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorLearnership
	 */
	public void legacymoderatorlearnershipDelete() {
		try {
			service.delete(this.legacymoderatorlearnership);
			prepareNew();
			legacymoderatorlearnershipInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyModeratorLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorLearnership
	 */
	public void prepareNew() {
		legacymoderatorlearnership = new LegacyModeratorLearnership();
	}

	/*
	 * public List<SelectItem> getLegacyModeratorLearnershipDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacymoderatorlearnershipInfo(); for (LegacyModeratorLearnership ug :
	 * legacymoderatorlearnershipList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<LegacyModeratorLearnership> complete(String desc) {
		List<LegacyModeratorLearnership> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyModeratorLearnership> getLegacyModeratorLearnershipList() {
		return legacymoderatorlearnershipList;
	}

	public void setLegacyModeratorLearnershipList(List<LegacyModeratorLearnership> legacymoderatorlearnershipList) {
		this.legacymoderatorlearnershipList = legacymoderatorlearnershipList;
	}

	public LegacyModeratorLearnership getLegacymoderatorlearnership() {
		return legacymoderatorlearnership;
	}

	public void setLegacymoderatorlearnership(LegacyModeratorLearnership legacymoderatorlearnership) {
		this.legacymoderatorlearnership = legacymoderatorlearnership;
	}

	public List<LegacyModeratorLearnership> getLegacyModeratorLearnershipfilteredList() {
		return legacymoderatorlearnershipfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacymoderatorlearnershipfilteredList the new
	 *                                               legacymoderatorlearnershipfilteredList
	 *                                               list
	 * @see LegacyModeratorLearnership
	 */
	public void setLegacyModeratorLearnershipfilteredList(
			List<LegacyModeratorLearnership> legacymoderatorlearnershipfilteredList) {
		this.legacymoderatorlearnershipfilteredList = legacymoderatorlearnershipfilteredList;
	}

	public LazyDataModel<LegacyModeratorLearnership> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyModeratorLearnership> dataModel) {
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
			List<LegacyModeratorLearnership> csvDataList = csvUtil.getObjects(LegacyModeratorLearnership.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacymoderatorlearnershipInfo();
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
			legacymoderatorlearnershipInfo();
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
			service.validiationRun();
			addInfoMessage("Action Complete");
			legacymoderatorlearnershipInfo();
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

	public List<LegacyModeratorLearnership> getLegacymoderatorlearnershipList() {
		return legacymoderatorlearnershipList;
	}

	public void setLegacymoderatorlearnershipList(List<LegacyModeratorLearnership> legacymoderatorlearnershipList) {
		this.legacymoderatorlearnershipList = legacymoderatorlearnershipList;
	}

	public List<LegacyModeratorLearnership> getLegacymoderatorlearnershipfilteredList() {
		return legacymoderatorlearnershipfilteredList;
	}

	public void setLegacymoderatorlearnershipfilteredList(
			List<LegacyModeratorLearnership> legacymoderatorlearnershipfilteredList) {
		this.legacymoderatorlearnershipfilteredList = legacymoderatorlearnershipfilteredList;
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
