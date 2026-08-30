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

import haj.com.entity.lookup.LegacyDTTCQualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.LegacyDTTCQualificationService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacydttcqualificationUI")
@ViewScoped
public class LegacyDTTCQualificationUI extends AbstractUI {

	private LegacyDTTCQualificationService service = new LegacyDTTCQualificationService();
	private List<LegacyDTTCQualification> legacydttcqualificationList = null;
	private List<LegacyDTTCQualification> legacydttcqualificationfilteredList = null;
	private LegacyDTTCQualification legacydttcqualification = null;
	private LazyDataModel<LegacyDTTCQualification> dataModel;

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
	 * Initialize method to get all LegacyDTTCQualification and prepare a for a
	 * create of a new LegacyDTTCQualification
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCQualification
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacydttcqualificationInfo();
		countAllEntries();
	}

	/**
	 * Get all LegacyDTTCQualification for data table
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCQualification
	 */
	public void legacydttcqualificationInfo() {

		dataModel = new LazyDataModel<LegacyDTTCQualification>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyDTTCQualification> retorno = new ArrayList<LegacyDTTCQualification>();

			@Override
			public List<LegacyDTTCQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLegacyDTTCQualification(LegacyDTTCQualification.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyDTTCQualification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyDTTCQualification obj) {
				return obj.getId();
			}

			@Override
			public LegacyDTTCQualification getRowData(String rowKey) {
				for (LegacyDTTCQualification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyDTTCQualification into database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCQualification
	 */
	public void legacydttcqualificationInsert() {
		try {
			service.create(this.legacydttcqualification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttcqualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyDTTCQualification in database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCQualification
	 */
	public void legacydttcqualificationUpdate() {
		try {
			service.update(this.legacydttcqualification);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttcqualificationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyDTTCQualification from database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCQualification
	 */
	public void legacydttcqualificationDelete() {
		try {
			service.delete(this.legacydttcqualification);
			prepareNew();
			legacydttcqualificationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyDTTCQualification
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCQualification
	 */
	public void prepareNew() {
		legacydttcqualification = new LegacyDTTCQualification();
	}

	/*
	 * public List<SelectItem> getLegacyDTTCQualificationDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacydttcqualificationInfo(); for (LegacyDTTCQualification ug :
	 * legacydttcqualificationList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyDTTCQualification> complete(String desc) {
		List<LegacyDTTCQualification> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyDTTCQualification> getLegacyDTTCQualificationList() {
		return legacydttcqualificationList;
	}

	public void setLegacyDTTCQualificationList(List<LegacyDTTCQualification> legacydttcqualificationList) {
		this.legacydttcqualificationList = legacydttcqualificationList;
	}

	public LegacyDTTCQualification getLegacydttcqualification() {
		return legacydttcqualification;
	}

	public void setLegacydttcqualification(LegacyDTTCQualification legacydttcqualification) {
		this.legacydttcqualification = legacydttcqualification;
	}

	public List<LegacyDTTCQualification> getLegacyDTTCQualificationfilteredList() {
		return legacydttcqualificationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacydttcqualificationfilteredList
	 *            the new legacydttcqualificationfilteredList list
	 * @see LegacyDTTCQualification
	 */
	public void setLegacyDTTCQualificationfilteredList(
			List<LegacyDTTCQualification> legacydttcqualificationfilteredList) {
		this.legacydttcqualificationfilteredList = legacydttcqualificationfilteredList;
	}

	public LazyDataModel<LegacyDTTCQualification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyDTTCQualification> dataModel) {
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
			List<LegacyDTTCQualification> csvDataList = csvUtil.getObjects(LegacyDTTCQualification.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacydttcqualificationInfo();
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
			legacydttcqualificationInfo();
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
			service.validateRsaIdNumbers();
			addInfoMessage("Action Complete");
			legacydttcqualificationInfo();
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

	public List<LegacyDTTCQualification> getLegacydttcqualificationList() {
		return legacydttcqualificationList;
	}

	public void setLegacydttcqualificationList(List<LegacyDTTCQualification> legacydttcqualificationList) {
		this.legacydttcqualificationList = legacydttcqualificationList;
	}

	public List<LegacyDTTCQualification> getLegacydttcqualificationfilteredList() {
		return legacydttcqualificationfilteredList;
	}

	public void setLegacydttcqualificationfilteredList(List<LegacyDTTCQualification> legacydttcqualificationfilteredList) {
		this.legacydttcqualificationfilteredList = legacydttcqualificationfilteredList;
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
