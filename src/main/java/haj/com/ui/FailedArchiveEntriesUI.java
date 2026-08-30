package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.FailedArchiveEntries;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.FailedArchiveEntriesService;

@ManagedBean(name = "failedarchiveentriesUI")
@ViewScoped
public class FailedArchiveEntriesUI extends AbstractUI {

	private FailedArchiveEntriesService service = new FailedArchiveEntriesService();
	private List<FailedArchiveEntries> failedarchiveentriesList = null;
	private List<FailedArchiveEntries> failedarchiveentriesfilteredList = null;
	private FailedArchiveEntries failedarchiveentries = null;
	private LazyDataModel<FailedArchiveEntries> dataModel; 

    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all FailedArchiveEntries and prepare a for a create of a new FailedArchiveEntries
 	 * @author TechFinium 
 	 * @see    FailedArchiveEntries
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		failedarchiveentriesInfo();
	}

	/**
	 * Get all FailedArchiveEntries for data table
 	 * @author TechFinium 
 	 * @see    FailedArchiveEntries
 	 */
	public void failedarchiveentriesInfo() {
//			dataModel = new FailedArchiveEntriesDatamodel();
	}

	/**
	 * Insert FailedArchiveEntries into database
 	 * @author TechFinium 
 	 * @see    FailedArchiveEntries
 	 */
	public void failedarchiveentriesInsert() {
		try {
				 service.create(this.failedarchiveentries);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 failedarchiveentriesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update FailedArchiveEntries in database
 	 * @author TechFinium 
 	 * @see    FailedArchiveEntries
 	 */
    public void failedarchiveentriesUpdate() {
		try {
				 service.update(this.failedarchiveentries);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 failedarchiveentriesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete FailedArchiveEntries from database
 	 * @author TechFinium 
 	 * @see    FailedArchiveEntries
 	 */
	public void failedarchiveentriesDelete() {
		try {
			 service.delete(this.failedarchiveentries);
			  prepareNew();
			 failedarchiveentriesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of FailedArchiveEntries 
 	 * @author TechFinium 
 	 * @see    FailedArchiveEntries
 	 */
	public void prepareNew() {
		failedarchiveentries = new FailedArchiveEntries();
	}

/*
    public List<SelectItem> getFailedArchiveEntriesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	failedarchiveentriesInfo();
    	for (FailedArchiveEntries ug : failedarchiveentriesList) {
    		// l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc()));
		}
    	return l;
    }
  */
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<FailedArchiveEntries> complete(String desc) {
		List<FailedArchiveEntries> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<FailedArchiveEntries> getFailedArchiveEntriesList() {
		return failedarchiveentriesList;
	}
	public void setFailedArchiveEntriesList(List<FailedArchiveEntries> failedarchiveentriesList) {
		this.failedarchiveentriesList = failedarchiveentriesList;
	}
	public FailedArchiveEntries getFailedarchiveentries() {
		return failedarchiveentries;
	}
	public void setFailedarchiveentries(FailedArchiveEntries failedarchiveentries) {
		this.failedarchiveentries = failedarchiveentries;
	}

    public List<FailedArchiveEntries> getFailedArchiveEntriesfilteredList() {
		return failedarchiveentriesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param failedarchiveentriesfilteredList the new failedarchiveentriesfilteredList list
 	 * @see    FailedArchiveEntries
 	 */	
	public void setFailedArchiveEntriesfilteredList(List<FailedArchiveEntries> failedarchiveentriesfilteredList) {
		this.failedarchiveentriesfilteredList = failedarchiveentriesfilteredList;
	}

	
	public LazyDataModel<FailedArchiveEntries> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<FailedArchiveEntries> dataModel) {
		this.dataModel = dataModel;
	}
	
}
