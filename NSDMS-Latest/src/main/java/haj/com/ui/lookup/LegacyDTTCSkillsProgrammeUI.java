package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.LegacyDTTCSkillsProgramme;
import haj.com.service.lookup.LegacyDTTCSkillsProgrammeService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "legacydttcskillsprogrammeUI")
@ViewScoped
public class LegacyDTTCSkillsProgrammeUI extends AbstractUI {

	private LegacyDTTCSkillsProgrammeService service = new LegacyDTTCSkillsProgrammeService();
	private List<LegacyDTTCSkillsProgramme> legacydttcskillsprogrammeList = null;
	private List<LegacyDTTCSkillsProgramme> legacydttcskillsprogrammefilteredList = null;
	private LegacyDTTCSkillsProgramme legacydttcskillsprogramme = null;
	private LazyDataModel<LegacyDTTCSkillsProgramme> dataModel;

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
	 * Initialize method to get all LegacyDTTCSkillsProgramme and prepare a for
	 * a create of a new LegacyDTTCSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCSkillsProgramme
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		legacydttcskillsprogrammeInfo();
		countAllEntries();
	}

	/**
	 * Get all LegacyDTTCSkillsProgramme for data table
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCSkillsProgramme
	 */
	public void legacydttcskillsprogrammeInfo() {

		dataModel = new LazyDataModel<LegacyDTTCSkillsProgramme>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyDTTCSkillsProgramme> retorno = new ArrayList<LegacyDTTCSkillsProgramme>();

			@Override
			public List<LegacyDTTCSkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allLegacyDTTCSkillsProgramme(LegacyDTTCSkillsProgramme.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(LegacyDTTCSkillsProgramme.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyDTTCSkillsProgramme obj) {
				return obj.getId();
			}

			@Override
			public LegacyDTTCSkillsProgramme getRowData(String rowKey) {
				for (LegacyDTTCSkillsProgramme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert LegacyDTTCSkillsProgramme into database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCSkillsProgramme
	 */
	public void legacydttcskillsprogrammeInsert() {
		try {
			service.create(this.legacydttcskillsprogramme);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttcskillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update LegacyDTTCSkillsProgramme in database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCSkillsProgramme
	 */
	public void legacydttcskillsprogrammeUpdate() {
		try {
			service.update(this.legacydttcskillsprogramme);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			legacydttcskillsprogrammeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete LegacyDTTCSkillsProgramme from database
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCSkillsProgramme
	 */
	public void legacydttcskillsprogrammeDelete() {
		try {
			service.delete(this.legacydttcskillsprogramme);
			prepareNew();
			legacydttcskillsprogrammeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of LegacyDTTCSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCSkillsProgramme
	 */
	public void prepareNew() {
		legacydttcskillsprogramme = new LegacyDTTCSkillsProgramme();
	}

	/*
	 * public List<SelectItem> getLegacyDTTCSkillsProgrammeDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * legacydttcskillsprogrammeInfo(); for (LegacyDTTCSkillsProgramme ug :
	 * legacydttcskillsprogrammeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<LegacyDTTCSkillsProgramme> complete(String desc) {
		List<LegacyDTTCSkillsProgramme> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<LegacyDTTCSkillsProgramme> getLegacyDTTCSkillsProgrammeList() {
		return legacydttcskillsprogrammeList;
	}

	public void setLegacyDTTCSkillsProgrammeList(List<LegacyDTTCSkillsProgramme> legacydttcskillsprogrammeList) {
		this.legacydttcskillsprogrammeList = legacydttcskillsprogrammeList;
	}

	public LegacyDTTCSkillsProgramme getLegacydttcskillsprogramme() {
		return legacydttcskillsprogramme;
	}

	public void setLegacydttcskillsprogramme(LegacyDTTCSkillsProgramme legacydttcskillsprogramme) {
		this.legacydttcskillsprogramme = legacydttcskillsprogramme;
	}

	public List<LegacyDTTCSkillsProgramme> getLegacyDTTCSkillsProgrammefilteredList() {
		return legacydttcskillsprogrammefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param legacydttcskillsprogrammefilteredList
	 *            the new legacydttcskillsprogrammefilteredList list
	 * @see LegacyDTTCSkillsProgramme
	 */
	public void setLegacyDTTCSkillsProgrammefilteredList(
			List<LegacyDTTCSkillsProgramme> legacydttcskillsprogrammefilteredList) {
		this.legacydttcskillsprogrammefilteredList = legacydttcskillsprogrammefilteredList;
	}

	public LazyDataModel<LegacyDTTCSkillsProgramme> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyDTTCSkillsProgramme> dataModel) {
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
			List<LegacyDTTCSkillsProgramme> csvDataList = csvUtil.getObjects(LegacyDTTCSkillsProgramme.class,
					event.getFile().getInputstream(), csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			legacydttcskillsprogrammeInfo();
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
			legacydttcskillsprogrammeInfo();
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
}
