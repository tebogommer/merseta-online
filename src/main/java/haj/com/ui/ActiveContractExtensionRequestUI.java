package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.ActiveContractExtensionRequest;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractExtensionRequestService;

@ManagedBean(name = "activecontractextensionrequestUI")
@ViewScoped
public class ActiveContractExtensionRequestUI extends AbstractUI {

	private ActiveContractExtensionRequestService service = new ActiveContractExtensionRequestService();
	private List<ActiveContractExtensionRequest> activecontractextensionrequestList = null;
	private List<ActiveContractExtensionRequest> activecontractextensionrequestfilteredList = null;
	private ActiveContractExtensionRequest activecontractextensionrequest = null;
	private LazyDataModel<ActiveContractExtensionRequest> dataModel; 

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
	 * Initialize method to get all ActiveContractExtensionRequest and prepare a for a create of a new ActiveContractExtensionRequest
 	 * @author TechFinium 
 	 * @see    ActiveContractExtensionRequest
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		activecontractextensionrequestInfo();
	}

	/**
	 * Get all ActiveContractExtensionRequest for data table
 	 * @author TechFinium 
 	 * @see    ActiveContractExtensionRequest
 	 */
	public void activecontractextensionrequestInfo() {
//			dataModel = new ActiveContractExtensionRequestDatamodel();
	}

	/**
	 * Insert ActiveContractExtensionRequest into database
 	 * @author TechFinium 
 	 * @see    ActiveContractExtensionRequest
 	 */
	public void activecontractextensionrequestInsert() {
		try {
				 service.create(this.activecontractextensionrequest);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 activecontractextensionrequestInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ActiveContractExtensionRequest in database
 	 * @author TechFinium 
 	 * @see    ActiveContractExtensionRequest
 	 */
    public void activecontractextensionrequestUpdate() {
		try {
				 service.update(this.activecontractextensionrequest);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 activecontractextensionrequestInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ActiveContractExtensionRequest from database
 	 * @author TechFinium 
 	 * @see    ActiveContractExtensionRequest
 	 */
	public void activecontractextensionrequestDelete() {
		try {
			 service.delete(this.activecontractextensionrequest);
			  prepareNew();
			 activecontractextensionrequestInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ActiveContractExtensionRequest 
 	 * @author TechFinium 
 	 * @see    ActiveContractExtensionRequest
 	 */
	public void prepareNew() {
		activecontractextensionrequest = new ActiveContractExtensionRequest();
	}

/*
    public List<SelectItem> getActiveContractExtensionRequestDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	activecontractextensionrequestInfo();
    	for (ActiveContractExtensionRequest ug : activecontractextensionrequestList) {
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
    public List<ActiveContractExtensionRequest> complete(String desc) {
		List<ActiveContractExtensionRequest> l = null;
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
    
    public List<ActiveContractExtensionRequest> getActiveContractExtensionRequestList() {
		return activecontractextensionrequestList;
	}
	public void setActiveContractExtensionRequestList(List<ActiveContractExtensionRequest> activecontractextensionrequestList) {
		this.activecontractextensionrequestList = activecontractextensionrequestList;
	}
	public ActiveContractExtensionRequest getActivecontractextensionrequest() {
		return activecontractextensionrequest;
	}
	public void setActivecontractextensionrequest(ActiveContractExtensionRequest activecontractextensionrequest) {
		this.activecontractextensionrequest = activecontractextensionrequest;
	}

    public List<ActiveContractExtensionRequest> getActiveContractExtensionRequestfilteredList() {
		return activecontractextensionrequestfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param activecontractextensionrequestfilteredList the new activecontractextensionrequestfilteredList list
 	 * @see    ActiveContractExtensionRequest
 	 */	
	public void setActiveContractExtensionRequestfilteredList(List<ActiveContractExtensionRequest> activecontractextensionrequestfilteredList) {
		this.activecontractextensionrequestfilteredList = activecontractextensionrequestfilteredList;
	}

	
	public LazyDataModel<ActiveContractExtensionRequest> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ActiveContractExtensionRequest> dataModel) {
		this.dataModel = dataModel;
	}
	
}
