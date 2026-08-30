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

import haj.com.entity.lookup.PostCodeLink;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.PostCodeLinkService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "postcodelinkUI")
@ViewScoped
public class PostCodeLinkUI extends AbstractUI {

	/* Entity */
	private PostCodeLink postcodelink = null;

	/* Service Level */
	private PostCodeLinkService service = new PostCodeLinkService();

	/* Array Lists */
	private List<PostCodeLink> postcodelinkList = null;
	private List<PostCodeLink> postcodelinkfilteredList = null;
	private List<String> csvTypeSelectionList = new ArrayList<>();

	/* Lazy Data Model */
	private LazyDataModel<PostCodeLink> dataModel;
	
	/* Util */
	private CSVUtil csvUtil = new CSVUtil();
	
	/* Vars */
	private String csvTypeSelection;
	private boolean displayDownload = false;;
	

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
	 * Initialize method to get all PostCodeLink and prepare a for a create of a
	 * new PostCodeLink
	 * 
	 * @author TechFinium
	 * @see PostCodeLink
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		postcodelinkInfo();
		countAllEntries();
	}

	/**
	 * Get all PostCodeLink for data table
	 * 
	 * @author TechFinium
	 * @see PostCodeLink
	 */
	public void postcodelinkInfo() {

		dataModel = new LazyDataModel<PostCodeLink>() {

			private static final long serialVersionUID = 1L;
			private List<PostCodeLink> retorno = new ArrayList<PostCodeLink>();

			@Override
			public List<PostCodeLink> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allPostCodeLink(PostCodeLink.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(PostCodeLink.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(PostCodeLink obj) {
				return obj.getId();
			}

			@Override
			public PostCodeLink getRowData(String rowKey) {
				for (PostCodeLink obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert PostCodeLink into database
	 * 
	 * @author TechFinium
	 * @see PostCodeLink
	 */
	public void postcodelinkInsert() {
		try {
			service.create(this.postcodelink);
			prepareNew();
			countAllEntries();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			postcodelinkInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update PostCodeLink in database
	 * 
	 * @author TechFinium
	 * @see PostCodeLink
	 */
	public void postcodelinkUpdate() {
		try {
			service.update(this.postcodelink);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			postcodelinkInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete PostCodeLink from database
	 * 
	 * @author TechFinium
	 * @see PostCodeLink
	 */
	public void postcodelinkDelete() {
		try {
			service.delete(this.postcodelink);
			prepareNew();
			postcodelinkInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of PostCodeLink
	 * 
	 * @author TechFinium
	 * @see PostCodeLink
	 */
	public void prepareNew() {
		postcodelink = new PostCodeLink();
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<PostCodeLink> complete(String desc) {
		List<PostCodeLink> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
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
			List<PostCodeLink> csvDataList = csvUtil.getObjects(PostCodeLink.class, event.getFile().getInputstream(), csvTypeSelection);
			service.saveCsvUploadData(csvDataList);
			addInfoMessage("Upload Complete");
			postcodelinkInfo();
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
			postcodelinkInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void runValidiations() {
		try {
			service.runValidiations();
			addInfoMessage("Action Complete");
			postcodelinkInfo();
			countAllEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runValidiationForEntry() {
		try {
			service.runValidiationForEntry(this.postcodelink);
			prepareNew();
			postcodelinkInfo();
			addInfoMessage("Action Complete");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateMissingPostCodes() {
		try {
			service.populateMissingPostCodeInformation(0, 10000);
			addInfoMessage("Action Complete");
			postcodelinkInfo();
			countAllEntries();
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

	public List<PostCodeLink> getPostCodeLinkList() {
		return postcodelinkList;
	}

	public void setPostCodeLinkList(List<PostCodeLink> postcodelinkList) {
		this.postcodelinkList = postcodelinkList;
	}

	public PostCodeLink getPostcodelink() {
		return postcodelink;
	}

	public void setPostcodelink(PostCodeLink postcodelink) {
		this.postcodelink = postcodelink;
	}

	public List<PostCodeLink> getPostCodeLinkfilteredList() {
		return postcodelinkfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param postcodelinkfilteredList
	 *            the new postcodelinkfilteredList list
	 * @see PostCodeLink
	 */
	public void setPostCodeLinkfilteredList(List<PostCodeLink> postcodelinkfilteredList) {
		this.postcodelinkfilteredList = postcodelinkfilteredList;
	}

	public LazyDataModel<PostCodeLink> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<PostCodeLink> dataModel) {
		this.dataModel = dataModel;
	}

	public String getCsvTypeSelection() {
		return csvTypeSelection;
	}

	public void setCsvTypeSelection(String csvTypeSelection) {
		this.csvTypeSelection = csvTypeSelection;
	}

	public List<String> getCsvTypeSelectionList() {
		return csvTypeSelectionList;
	}

	public void setCsvTypeSelectionList(List<String> csvTypeSelectionList) {
		this.csvTypeSelectionList = csvTypeSelectionList;
	}

	public boolean isDisplayDownload() {
		return displayDownload;
	}

	public void setDisplayDownload(boolean displayDownload) {
		this.displayDownload = displayDownload;
	}

}
