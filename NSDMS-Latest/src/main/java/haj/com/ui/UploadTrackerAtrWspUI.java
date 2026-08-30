package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.UploadTrackerAtrWsp;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UploadTrackerAtrWspService;

@ManagedBean(name = "uploadtrackeratrwspUI")
@ViewScoped
public class UploadTrackerAtrWspUI extends AbstractUI {

	private UploadTrackerAtrWspService service = new UploadTrackerAtrWspService();
	private List<UploadTrackerAtrWsp> uploadtrackeratrwspList = null;
	private List<UploadTrackerAtrWsp> uploadtrackeratrwspfilteredList = null;
	private UploadTrackerAtrWsp uploadtrackeratrwsp = null;
	private LazyDataModel<UploadTrackerAtrWsp> dataModel; 

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
	 * Initialize method to get all UploadTrackerAtrWsp and prepare a for a create of a new UploadTrackerAtrWsp
 	 * @author TechFinium 
 	 * @see    UploadTrackerAtrWsp
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		uploadtrackeratrwspInfo();
	}

	/**
	 * Get all UploadTrackerAtrWsp for data table
 	 * @author TechFinium 
 	 * @see    UploadTrackerAtrWsp
 	 */
	public void uploadtrackeratrwspInfo() {
//			dataModel = new UploadTrackerAtrWspDatamodel();
	}

	/**
	 * Insert UploadTrackerAtrWsp into database
 	 * @author TechFinium 
 	 * @see    UploadTrackerAtrWsp
 	 */
	public void uploadtrackeratrwspInsert() {
		try {
				 service.create(this.uploadtrackeratrwsp);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 uploadtrackeratrwspInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UploadTrackerAtrWsp in database
 	 * @author TechFinium 
 	 * @see    UploadTrackerAtrWsp
 	 */
    public void uploadtrackeratrwspUpdate() {
		try {
				 service.update(this.uploadtrackeratrwsp);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 uploadtrackeratrwspInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UploadTrackerAtrWsp from database
 	 * @author TechFinium 
 	 * @see    UploadTrackerAtrWsp
 	 */
	public void uploadtrackeratrwspDelete() {
		try {
			 service.delete(this.uploadtrackeratrwsp);
			  prepareNew();
			 uploadtrackeratrwspInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UploadTrackerAtrWsp 
 	 * @author TechFinium 
 	 * @see    UploadTrackerAtrWsp
 	 */
	public void prepareNew() {
		uploadtrackeratrwsp = new UploadTrackerAtrWsp();
	}

/*
    public List<SelectItem> getUploadTrackerAtrWspDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	uploadtrackeratrwspInfo();
    	for (UploadTrackerAtrWsp ug : uploadtrackeratrwspList) {
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
    public List<UploadTrackerAtrWsp> complete(String desc) {
		List<UploadTrackerAtrWsp> l = null;
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
    
    public List<UploadTrackerAtrWsp> getUploadTrackerAtrWspList() {
		return uploadtrackeratrwspList;
	}
	public void setUploadTrackerAtrWspList(List<UploadTrackerAtrWsp> uploadtrackeratrwspList) {
		this.uploadtrackeratrwspList = uploadtrackeratrwspList;
	}
	public UploadTrackerAtrWsp getUploadtrackeratrwsp() {
		return uploadtrackeratrwsp;
	}
	public void setUploadtrackeratrwsp(UploadTrackerAtrWsp uploadtrackeratrwsp) {
		this.uploadtrackeratrwsp = uploadtrackeratrwsp;
	}

    public List<UploadTrackerAtrWsp> getUploadTrackerAtrWspfilteredList() {
		return uploadtrackeratrwspfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param uploadtrackeratrwspfilteredList the new uploadtrackeratrwspfilteredList list
 	 * @see    UploadTrackerAtrWsp
 	 */	
	public void setUploadTrackerAtrWspfilteredList(List<UploadTrackerAtrWsp> uploadtrackeratrwspfilteredList) {
		this.uploadtrackeratrwspfilteredList = uploadtrackeratrwspfilteredList;
	}

	
	public LazyDataModel<UploadTrackerAtrWsp> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UploadTrackerAtrWsp> dataModel) {
		this.dataModel = dataModel;
	}
	
}
