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

import haj.com.entity.lookup.LegacyLearnershipAssessment3;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyLearnershipAssessment3Service;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacylearnershipassessment3UI")
@ViewScoped
public class LegacyLearnershipAssessment3UI extends AbstractUI {

	private LegacyLearnershipAssessment3Service service = new LegacyLearnershipAssessment3Service();
	private List<LegacyLearnershipAssessment3> legacylearnershipassessment3List = null;
	private List<LegacyLearnershipAssessment3> legacylearnershipassessment3filteredList = null;
	private LegacyLearnershipAssessment3 legacylearnershipassessment3 = null;
	private LazyDataModel<LegacyLearnershipAssessment3> dataModel;

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
	 * Initialize method to get all LegacyLearnershipAssessment3 and prepare a for a
	 * create of a new LegacyLearnershipAssessment3
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment3
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacylearnershipassessment3Info();
		countAllEntries();
	}

	/**
	 * Get all LegacyLearnershipAssessment3 for data table
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment3
	 */
	public void legacylearnershipassessment3Info() {

		dataModel = new LazyDataModel<LegacyLearnershipAssessment3>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyLearnershipAssessment3> retorno = new ArrayList<LegacyLearnershipAssessment3>();

			@Override
			public List<LegacyLearnershipAssessment3> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allLegacyLearnershipAssessment3(LegacyLearnershipAssessment3.class, first,
							pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyLearnershipAssessment3.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyLearnershipAssessment3 obj) {
				return obj.getId();
			}

			@Override
			public LegacyLearnershipAssessment3 getRowData(String rowKey) {
				for (LegacyLearnershipAssessment3 obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyLearnershipAssessment3 into database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment3
	 */
	public void legacylearnershipassessment3Insert() {
		try {
			service.create(this.legacylearnershipassessment3);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacylearnershipassessment3Info();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyLearnershipAssessment3 in database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment3
	 */
	public void legacylearnershipassessment3Update() {
		try {
			service.update(this.legacylearnershipassessment3);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacylearnershipassessment3Info();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyLearnershipAssessment3 from database
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment3
	 */
	public void legacylearnershipassessment3Delete() {
		try {
			service.delete(this.legacylearnershipassessment3);
			prepareNew();
			legacylearnershipassessment3Info();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyLearnershipAssessment3
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment3
	 */
	public void prepareNew() {
		legacylearnershipassessment3 = new LegacyLearnershipAssessment3();
	}

	/*
	 * public List<SelectItem> getLegacyLearnershipAssessment3DD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacylearnershipassessment3Info(); for (LegacyLearnershipAssessment3 ug :
	 * legacylearnershipassessment3List) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<LegacyLearnershipAssessment3> complete(String desc) {
		List<LegacyLearnershipAssessment3> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyLearnershipAssessment3> getLegacyLearnershipAssessment3List() {
		return legacylearnershipassessment3List;
	}

	public void setLegacyLearnershipAssessment3List(
			List<LegacyLearnershipAssessment3> legacylearnershipassessment3List) {
		this.legacylearnershipassessment3List = legacylearnershipassessment3List;
	}

	public LegacyLearnershipAssessment3 getLegacylearnershipassessment3() {
		return legacylearnershipassessment3;
	}

	public void setLegacylearnershipassessment3(LegacyLearnershipAssessment3 legacylearnershipassessment3) {
		this.legacylearnershipassessment3 = legacylearnershipassessment3;
	}

	public List<LegacyLearnershipAssessment3> getLegacyLearnershipAssessment3filteredList() {
		return legacylearnershipassessment3filteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacylearnershipassessment3filteredList the new
	 *                                                 legacylearnershipassessment3filteredList
	 *                                                 list
	 * @see LegacyLearnershipAssessment3
	 */
	public void setLegacyLearnershipAssessment3filteredList(
			List<LegacyLearnershipAssessment3> legacylearnershipassessment3filteredList) {
		this.legacylearnershipassessment3filteredList = legacylearnershipassessment3filteredList;
	}

	public LazyDataModel<LegacyLearnershipAssessment3> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyLearnershipAssessment3> dataModel) {
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
			List<LegacyLearnershipAssessment3> csvDataList = csvUtil.getObjects(LegacyLearnershipAssessment3.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacylearnershipassessment3Info();
		} catch (ConstraintViolationException e) {
			addErrorMessage("ConstraintViolationException");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} finally {
			csvUtil = new CSVUtil();
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

	public void clearUploadedEntries() {
		try {
			service.deleteUploadedEntries();
			addInfoMessage("data cleared");
			legacylearnershipassessment3Info();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runValidiations(){
		try {
			service.validiationRun();
			addInfoMessage("Action Complete");
			legacylearnershipassessment3Info();
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

	public List<LegacyLearnershipAssessment3> getLegacylearnershipassessment3List() {
		return legacylearnershipassessment3List;
	}

	public void setLegacylearnershipassessment3List(List<LegacyLearnershipAssessment3> legacylearnershipassessment3List) {
		this.legacylearnershipassessment3List = legacylearnershipassessment3List;
	}

	public List<LegacyLearnershipAssessment3> getLegacylearnershipassessment3filteredList() {
		return legacylearnershipassessment3filteredList;
	}

	public void setLegacylearnershipassessment3filteredList(
			List<LegacyLearnershipAssessment3> legacylearnershipassessment3filteredList) {
		this.legacylearnershipassessment3filteredList = legacylearnershipassessment3filteredList;
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
