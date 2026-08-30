package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.PreviousSchools;
import haj.com.service.lookup.PreviousSchoolsService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "previousschoolsUI")
@ViewScoped
public class PreviousSchoolsUI extends AbstractUI {

	private PreviousSchoolsService service = new PreviousSchoolsService();
	private List<PreviousSchools> previousschoolsList = null;
	private List<PreviousSchools> previousschoolsfilteredList = null;
	private PreviousSchools previousschools = null;
	private LazyDataModel<PreviousSchools> dataModel;

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
	 * Initialize method to get all PreviousSchools and prepare a for a create of a
	 * new PreviousSchools
	 * 
	 * @author TechFinium
	 * @see PreviousSchools
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		previousschoolsInfo();
		countAllEntries();
	}

	/**
	 * Get all PreviousSchools for data table
	 * 
	 * @author TechFinium
	 * @see PreviousSchools
	 */
	public void previousschoolsInfo() {

		dataModel = new LazyDataModel<PreviousSchools>() {

			private static final long serialVersionUID = 1L;
			private List<PreviousSchools> retorno = new ArrayList<PreviousSchools>();

			@Override
			public List<PreviousSchools> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allPreviousSchools(PreviousSchools.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(PreviousSchools.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(PreviousSchools obj) {
				return obj.getId();
			}

			@Override
			public PreviousSchools getRowData(String rowKey) {
				for (PreviousSchools obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert PreviousSchools into database
	 * 
	 * @author TechFinium
	 * @see PreviousSchools
	 */
	public void previousschoolsInsert() {
		try {
			service.create(this.previousschools);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			previousschoolsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update PreviousSchools in database
	 * 
	 * @author TechFinium
	 * @see PreviousSchools
	 */
	public void previousschoolsUpdate() {
		try {
			service.update(this.previousschools);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			previousschoolsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete PreviousSchools from database
	 * 
	 * @author TechFinium
	 * @see PreviousSchools
	 */
	public void previousschoolsDelete() {
		try {
			service.delete(this.previousschools);
			prepareNew();
			previousschoolsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of PreviousSchools
	 * 
	 * @author TechFinium
	 * @see PreviousSchools
	 */
	public void prepareNew() {
		previousschools = new PreviousSchools();
	}

	/*
	 * public List<SelectItem> getPreviousSchoolsDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * previousschoolsInfo(); for (PreviousSchools ug : previousschoolsList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return
	 * l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<PreviousSchools> complete(String desc) {
		List<PreviousSchools> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<PreviousSchools> getPreviousSchoolsList() {
		return previousschoolsList;
	}

	public void setPreviousSchoolsList(List<PreviousSchools> previousschoolsList) {
		this.previousschoolsList = previousschoolsList;
	}

	public PreviousSchools getPreviousschools() {
		return previousschools;
	}

	public void setPreviousschools(PreviousSchools previousschools) {
		this.previousschools = previousschools;
	}

	public List<PreviousSchools> getPreviousSchoolsfilteredList() {
		return previousschoolsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param previousschoolsfilteredList the new previousschoolsfilteredList list
	 * @see PreviousSchools
	 */
	public void setPreviousSchoolsfilteredList(List<PreviousSchools> previousschoolsfilteredList) {
		this.previousschoolsfilteredList = previousschoolsfilteredList;
	}

	public LazyDataModel<PreviousSchools> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<PreviousSchools> dataModel) {
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
			List<PreviousSchools> csvDataList = csvUtil.getObjects(PreviousSchools.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			previousschoolsInfo();
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
			previousschoolsInfo();
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

	public List<PreviousSchools> getPreviousschoolsList() {
		return previousschoolsList;
	}

	public void setPreviousschoolsList(List<PreviousSchools> previousschoolsList) {
		this.previousschoolsList = previousschoolsList;
	}

	public List<PreviousSchools> getPreviousschoolsfilteredList() {
		return previousschoolsfilteredList;
	}

	public void setPreviousschoolsfilteredList(List<PreviousSchools> previousschoolsfilteredList) {
		this.previousschoolsfilteredList = previousschoolsfilteredList;
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
