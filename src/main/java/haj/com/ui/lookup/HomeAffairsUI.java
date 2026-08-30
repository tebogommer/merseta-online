package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.HomeAffairs;
import haj.com.service.lookup.HomeAffairsService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "homeaffairsUI")
@ViewScoped
public class HomeAffairsUI extends AbstractUI {

	private HomeAffairsService service = new HomeAffairsService();
	private List<HomeAffairs> homeaffairsList = null;
	private List<HomeAffairs> homeaffairsfilteredList = null;
	private HomeAffairs homeaffairs = null;
	private LazyDataModel<HomeAffairs> dataModel;

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
	 * Initialize method to get all HomeAffairs and prepare a for a create of a new
	 * HomeAffairs
	 * 
	 * @author TechFinium
	 * @see HomeAffairs
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		homeaffairsInfo();
		countAllEntries();
	}

	/**
	 * Get all HomeAffairs for data table
	 * 
	 * @author TechFinium
	 * @see HomeAffairs
	 */
	public void homeaffairsInfo() {

		dataModel = new LazyDataModel<HomeAffairs>() {

			private static final long serialVersionUID = 1L;
			private List<HomeAffairs> retorno = new ArrayList<HomeAffairs>();

			@Override
			public List<HomeAffairs> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allHomeAffairs(HomeAffairs.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(HomeAffairs.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(HomeAffairs obj) {
				return obj.getId();
			}

			@Override
			public HomeAffairs getRowData(String rowKey) {
				for (HomeAffairs obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert HomeAffairs into database
	 * 
	 * @author TechFinium
	 * @see HomeAffairs
	 */
	public void homeaffairsInsert() {
		try {
			service.create(this.homeaffairs);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			homeaffairsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update HomeAffairs in database
	 * 
	 * @author TechFinium
	 * @see HomeAffairs
	 */
	public void homeaffairsUpdate() {
		try {
			service.update(this.homeaffairs);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			homeaffairsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete HomeAffairs from database
	 * 
	 * @author TechFinium
	 * @see HomeAffairs
	 */
	public void homeaffairsDelete() {
		try {
			service.delete(this.homeaffairs);
			prepareNew();
			homeaffairsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of HomeAffairs
	 * 
	 * @author TechFinium
	 * @see HomeAffairs
	 */
	public void prepareNew() {
		homeaffairs = new HomeAffairs();
	}

	/*
	 * public List<SelectItem> getHomeAffairsDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * homeaffairsInfo(); for (HomeAffairs ug : homeaffairsList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc the desc
	 * @return the list
	 */
	public List<HomeAffairs> complete(String desc) {
		List<HomeAffairs> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<HomeAffairs> getHomeAffairsList() {
		return homeaffairsList;
	}

	public void setHomeAffairsList(List<HomeAffairs> homeaffairsList) {
		this.homeaffairsList = homeaffairsList;
	}

	public HomeAffairs getHomeaffairs() {
		return homeaffairs;
	}

	public void setHomeaffairs(HomeAffairs homeaffairs) {
		this.homeaffairs = homeaffairs;
	}

	public List<HomeAffairs> getHomeAffairsfilteredList() {
		return homeaffairsfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param homeaffairsfilteredList the new homeaffairsfilteredList list
	 * @see HomeAffairs
	 */
	public void setHomeAffairsfilteredList(List<HomeAffairs> homeaffairsfilteredList) {
		this.homeaffairsfilteredList = homeaffairsfilteredList;
	}

	public LazyDataModel<HomeAffairs> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<HomeAffairs> dataModel) {
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
			List<HomeAffairs> csvDataList = csvUtil.getObjects(HomeAffairs.class, event.getFile().getInputstream(),
					csvTypeSelection);
			logger.info("Finished parsing upload. number of entries: " + csvDataList.size());
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			logger.info("Finished persisting upload");
			homeaffairsInfo();
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
			homeaffairsInfo();
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

	public List<HomeAffairs> getHomeaffairsList() {
		return homeaffairsList;
	}

	public void setHomeaffairsList(List<HomeAffairs> homeaffairsList) {
		this.homeaffairsList = homeaffairsList;
	}

	public List<HomeAffairs> getHomeaffairsfilteredList() {
		return homeaffairsfilteredList;
	}

	public void setHomeaffairsfilteredList(List<HomeAffairs> homeaffairsfilteredList) {
		this.homeaffairsfilteredList = homeaffairsfilteredList;
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
