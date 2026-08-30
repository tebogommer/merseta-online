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

import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyAssessorLearnershipService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacyassessorlearnershipUI")
@ViewScoped
public class LegacyAssessorLearnershipUI extends AbstractUI {

	private LegacyAssessorLearnershipService service = new LegacyAssessorLearnershipService();
	private List<LegacyAssessorLearnership> legacyassessorlearnershipList = null;
	private List<LegacyAssessorLearnership> legacyassessorlearnershipfilteredList = null;
	private LegacyAssessorLearnership legacyassessorlearnership = null;
	private LazyDataModel<LegacyAssessorLearnership> dataModel;

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
	 * Initialize method to get all LegacyAssessorLearnership and prepare a for a
	 * create of a new LegacyAssessorLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorLearnership
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyassessorlearnershipInfo();
		countAllEntries();
	}

	/**
	 * Get all LegacyAssessorLearnership for data table
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorLearnership
	 */
	public void legacyassessorlearnershipInfo() {

		dataModel = new LazyDataModel<LegacyAssessorLearnership>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyAssessorLearnership> retorno = new ArrayList<LegacyAssessorLearnership>();

			@Override
			public List<LegacyAssessorLearnership> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLegacyAssessorLearnership(LegacyAssessorLearnership.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyAssessorLearnership.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyAssessorLearnership obj) {
				return obj.getId();
			}

			@Override
			public LegacyAssessorLearnership getRowData(String rowKey) {
				for (LegacyAssessorLearnership obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyAssessorLearnership into database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorLearnership
	 */
	public void legacyassessorlearnershipInsert() {
		try {
			service.create(this.legacyassessorlearnership);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyassessorlearnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyAssessorLearnership in database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorLearnership
	 */
	public void legacyassessorlearnershipUpdate() {
		try {
			service.update(this.legacyassessorlearnership);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacyassessorlearnershipInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyAssessorLearnership from database
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorLearnership
	 */
	public void legacyassessorlearnershipDelete() {
		try {
			service.delete(this.legacyassessorlearnership);
			prepareNew();
			legacyassessorlearnershipInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyAssessorLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorLearnership
	 */
	public void prepareNew() {
		legacyassessorlearnership = new LegacyAssessorLearnership();
	}

	/*
	 * public List<SelectItem> getLegacyAssessorLearnershipDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacyassessorlearnershipInfo(); for (LegacyAssessorLearnership ug :
	 * legacyassessorlearnershipList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<LegacyAssessorLearnership> complete(String desc) {
		List<LegacyAssessorLearnership> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void runValidiations(){
		try {
			service.applyValidiations();
			addInfoMessage("Action Complete");
			legacyassessorlearnershipInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<LegacyAssessorLearnership> getLegacyAssessorLearnershipList() {
		return legacyassessorlearnershipList;
	}

	public void setLegacyAssessorLearnershipList(List<LegacyAssessorLearnership> legacyassessorlearnershipList) {
		this.legacyassessorlearnershipList = legacyassessorlearnershipList;
	}

	public LegacyAssessorLearnership getLegacyassessorlearnership() {
		return legacyassessorlearnership;
	}

	public void setLegacyassessorlearnership(LegacyAssessorLearnership legacyassessorlearnership) {
		this.legacyassessorlearnership = legacyassessorlearnership;
	}

	public List<LegacyAssessorLearnership> getLegacyAssessorLearnershipfilteredList() {
		return legacyassessorlearnershipfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacyassessorlearnershipfilteredList the new
	 *                                              legacyassessorlearnershipfilteredList
	 *                                              list
	 * @see LegacyAssessorLearnership
	 */
	public void setLegacyAssessorLearnershipfilteredList(
			List<LegacyAssessorLearnership> legacyassessorlearnershipfilteredList) {
		this.legacyassessorlearnershipfilteredList = legacyassessorlearnershipfilteredList;
	}

	public LazyDataModel<LegacyAssessorLearnership> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyAssessorLearnership> dataModel) {
		this.dataModel = dataModel;
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
			List<LegacyAssessorLearnership> csvDataList = csvUtil.getObjects(LegacyAssessorLearnership.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacyassessorlearnershipInfo();
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
			legacyassessorlearnershipInfo();
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

	public List<LegacyAssessorLearnership> getLegacyassessorlearnershipList() {
		return legacyassessorlearnershipList;
	}

	public void setLegacyassessorlearnershipList(List<LegacyAssessorLearnership> legacyassessorlearnershipList) {
		this.legacyassessorlearnershipList = legacyassessorlearnershipList;
	}

	public List<LegacyAssessorLearnership> getLegacyassessorlearnershipfilteredList() {
		return legacyassessorlearnershipfilteredList;
	}

	public void setLegacyassessorlearnershipfilteredList(
			List<LegacyAssessorLearnership> legacyassessorlearnershipfilteredList) {
		this.legacyassessorlearnershipfilteredList = legacyassessorlearnershipfilteredList;
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
