package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.UserLearnership;
import haj.com.service.UserLearnershipService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "userlearnershipUI")
@ViewScoped
public class UserLearnershipUI extends AbstractUI {

	private UserLearnershipService service = new UserLearnershipService();
	private List<UserLearnership> userlearnershipList = null;
	private List<UserLearnership> userlearnershipfilteredList = null;
	private UserLearnership userlearnership = null;
	private LazyDataModel<UserLearnership> dataModel; 

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
	 * Initialize method to get all UserLearnership and prepare a for a create of a new UserLearnership
 	 * @author TechFinium 
 	 * @see    UserLearnership
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		//userlearnershipInfo();
	}

	/**
	 * Get all UserLearnership for data table
 	 * @author TechFinium 
 	 * @see    UserLearnership
 	 */
	/*
	 * public void userlearnershipInfo() { dataModel = new
	 * UserLearnershipDatamodel(); }
	 */
	/**
	 * Insert UserLearnership into database
 	 * @author TechFinium 
 	 * @see    UserLearnership
 	 */
	public void userlearnershipInsert() {
		try {
				 service.create(this.userlearnership);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 //userlearnershipInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UserLearnership in database
 	 * @author TechFinium 
 	 * @see    UserLearnership
 	 */
    public void userlearnershipUpdate() {
		try {
				 service.update(this.userlearnership);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 //userlearnershipInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UserLearnership from database
 	 * @author TechFinium 
 	 * @see    UserLearnership
 	 */
	public void userlearnershipDelete() {
		try {
			 service.delete(this.userlearnership);
			  prepareNew();
			 //userlearnershipInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UserLearnership 
 	 * @author TechFinium 
 	 * @see    UserLearnership
 	 */
	public void prepareNew() {
		userlearnership = new UserLearnership();
	}

/*
    public List<SelectItem> getUserLearnershipDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userlearnershipInfo();
    	for (UserLearnership ug : userlearnershipList) {
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
    public List<UserLearnership> complete(String desc) {
		List<UserLearnership> l = null;
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
    
    public List<UserLearnership> getUserLearnershipList() {
		return userlearnershipList;
	}
	public void setUserLearnershipList(List<UserLearnership> userlearnershipList) {
		this.userlearnershipList = userlearnershipList;
	}
	public UserLearnership getUserlearnership() {
		return userlearnership;
	}
	public void setUserlearnership(UserLearnership userlearnership) {
		this.userlearnership = userlearnership;
	}

    public List<UserLearnership> getUserLearnershipfilteredList() {
		return userlearnershipfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param userlearnershipfilteredList the new userlearnershipfilteredList list
 	 * @see    UserLearnership
 	 */	
	public void setUserLearnershipfilteredList(List<UserLearnership> userlearnershipfilteredList) {
		this.userlearnershipfilteredList = userlearnershipfilteredList;
	}

	
	public LazyDataModel<UserLearnership> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UserLearnership> dataModel) {
		this.dataModel = dataModel;
	}
	
}
