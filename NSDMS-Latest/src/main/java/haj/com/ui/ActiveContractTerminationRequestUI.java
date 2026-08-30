package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.ActiveContractTerminationRequest;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractTerminationRequestService;

@ManagedBean(name = "activecontractterminationrequestUI")
@ViewScoped
public class ActiveContractTerminationRequestUI extends AbstractUI {

	private ActiveContractTerminationRequestService service = new ActiveContractTerminationRequestService();
	private List<ActiveContractTerminationRequest> activecontractterminationrequestList = null;
	private List<ActiveContractTerminationRequest> activecontractterminationrequestfilteredList = null;
	private ActiveContractTerminationRequest activecontractterminationrequest = null;
	private LazyDataModel<ActiveContractTerminationRequest> dataModel; 

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
	 * Initialize method to get all ActiveContractTerminationRequest and prepare a for a create of a new ActiveContractTerminationRequest
 	 * @author TechFinium 
 	 * @see    ActiveContractTerminationRequest
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		activecontractterminationrequestInfo();
	}

	/**
	 * Get all ActiveContractTerminationRequest for data table
 	 * @author TechFinium 
 	 * @see    ActiveContractTerminationRequest
 	 */
	public void activecontractterminationrequestInfo() {
//			dataModel = new ActiveContractTerminationRequestDatamodel();
	}

	/**
	 * Insert ActiveContractTerminationRequest into database
 	 * @author TechFinium 
 	 * @see    ActiveContractTerminationRequest
 	 */
	public void activecontractterminationrequestInsert() {
		try {
				 service.create(this.activecontractterminationrequest);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 activecontractterminationrequestInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ActiveContractTerminationRequest in database
 	 * @author TechFinium 
 	 * @see    ActiveContractTerminationRequest
 	 */
    public void activecontractterminationrequestUpdate() {
		try {
				 service.update(this.activecontractterminationrequest);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 activecontractterminationrequestInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ActiveContractTerminationRequest from database
 	 * @author TechFinium 
 	 * @see    ActiveContractTerminationRequest
 	 */
	public void activecontractterminationrequestDelete() {
		try {
			 service.delete(this.activecontractterminationrequest);
			  prepareNew();
			 activecontractterminationrequestInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ActiveContractTerminationRequest 
 	 * @author TechFinium 
 	 * @see    ActiveContractTerminationRequest
 	 */
	public void prepareNew() {
		activecontractterminationrequest = new ActiveContractTerminationRequest();
	}

/*
    public List<SelectItem> getActiveContractTerminationRequestDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	activecontractterminationrequestInfo();
    	for (ActiveContractTerminationRequest ug : activecontractterminationrequestList) {
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
    public List<ActiveContractTerminationRequest> complete(String desc) {
		List<ActiveContractTerminationRequest> l = null;
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
    
    public List<ActiveContractTerminationRequest> getActiveContractTerminationRequestList() {
		return activecontractterminationrequestList;
	}
	public void setActiveContractTerminationRequestList(List<ActiveContractTerminationRequest> activecontractterminationrequestList) {
		this.activecontractterminationrequestList = activecontractterminationrequestList;
	}
	public ActiveContractTerminationRequest getActivecontractterminationrequest() {
		return activecontractterminationrequest;
	}
	public void setActivecontractterminationrequest(ActiveContractTerminationRequest activecontractterminationrequest) {
		this.activecontractterminationrequest = activecontractterminationrequest;
	}

    public List<ActiveContractTerminationRequest> getActiveContractTerminationRequestfilteredList() {
		return activecontractterminationrequestfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param activecontractterminationrequestfilteredList the new activecontractterminationrequestfilteredList list
 	 * @see    ActiveContractTerminationRequest
 	 */	
	public void setActiveContractTerminationRequestfilteredList(List<ActiveContractTerminationRequest> activecontractterminationrequestfilteredList) {
		this.activecontractterminationrequestfilteredList = activecontractterminationrequestfilteredList;
	}

	
	public LazyDataModel<ActiveContractTerminationRequest> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ActiveContractTerminationRequest> dataModel) {
		this.dataModel = dataModel;
	}
	
}
