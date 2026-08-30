package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.UserChangeRequest;
import haj.com.service.UserChangeRequestService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "userchangerequestUI")
@ViewScoped
public class UserChangeRequestUI extends AbstractUI {

	private UserChangeRequestService service = new UserChangeRequestService();
	private List<UserChangeRequest> userchangerequestList = null;
	private List<UserChangeRequest> userchangerequestfilteredList = null;
	private UserChangeRequest userchangerequest = null;
	private LazyDataModel<UserChangeRequest> dataModel; 

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
	 * Initialize method to get all UserChangeRequest and prepare a for a create of a new UserChangeRequest
 	 * @author TechFinium 
 	 * @see    UserChangeRequest
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		userchangerequestInfo();
	}

	/**
	 * Get all UserChangeRequest for data table
 	 * @author TechFinium 
 	 * @see    UserChangeRequest
 	 */
	public void userchangerequestInfo() {
			//dataModel = new UserChangeRequestDatamodel();
	}

	/**
	 * Insert UserChangeRequest into database
 	 * @author TechFinium 
 	 * @see    UserChangeRequest
 	 */
	public void userchangerequestInsert() {
		try {
				 service.create(this.userchangerequest);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 userchangerequestInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UserChangeRequest in database
 	 * @author TechFinium 
 	 * @see    UserChangeRequest
 	 */
    public void userchangerequestUpdate() {
		try {
				 service.update(this.userchangerequest);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 userchangerequestInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UserChangeRequest from database
 	 * @author TechFinium 
 	 * @see    UserChangeRequest
 	 */
	public void userchangerequestDelete() {
		try {
			 service.delete(this.userchangerequest);
			  prepareNew();
			 userchangerequestInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UserChangeRequest 
 	 * @author TechFinium 
 	 * @see    UserChangeRequest
 	 */
	public void prepareNew() {
		userchangerequest = new UserChangeRequest();
	}

/*
    public List<SelectItem> getUserChangeRequestDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userchangerequestInfo();
    	for (UserChangeRequest ug : userchangerequestList) {
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
    public List<UserChangeRequest> complete(String desc) {
		List<UserChangeRequest> l = null;
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
    
    public List<UserChangeRequest> getUserChangeRequestList() {
		return userchangerequestList;
	}
	public void setUserChangeRequestList(List<UserChangeRequest> userchangerequestList) {
		this.userchangerequestList = userchangerequestList;
	}
	public UserChangeRequest getUserchangerequest() {
		return userchangerequest;
	}
	public void setUserchangerequest(UserChangeRequest userchangerequest) {
		this.userchangerequest = userchangerequest;
	}

    public List<UserChangeRequest> getUserChangeRequestfilteredList() {
		return userchangerequestfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param userchangerequestfilteredList the new userchangerequestfilteredList list
 	 * @see    UserChangeRequest
 	 */	
	public void setUserChangeRequestfilteredList(List<UserChangeRequest> userchangerequestfilteredList) {
		this.userchangerequestfilteredList = userchangerequestfilteredList;
	}

	
	public LazyDataModel<UserChangeRequest> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UserChangeRequest> dataModel) {
		this.dataModel = dataModel;
	}
	
}
