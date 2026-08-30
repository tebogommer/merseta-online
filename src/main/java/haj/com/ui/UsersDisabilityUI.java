package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.UsersDisability;
import haj.com.service.UsersDisabilityService;
import org.primefaces.model.LazyDataModel;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "usersdisabilityUI")
@ViewScoped
public class UsersDisabilityUI extends AbstractUI {

	private UsersDisabilityService service = new UsersDisabilityService();
	private List<UsersDisability> usersdisabilityList = null;
	private List<UsersDisability> usersdisabilityfilteredList = null;
	private UsersDisability usersdisability = null;
	private LazyDataModel<UsersDisability> dataModel; 

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
	 * Initialize method to get all UsersDisability and prepare a for a create of a new UsersDisability
 	 * @author TechFinium 
 	 * @see    UsersDisability
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		usersdisabilityInfo();
	}

	/**
	 * Get all UsersDisability for data table
 	 * @author TechFinium 
 	 * @see    UsersDisability
 	 */
	public void usersdisabilityInfo() {
			//dataModel = new UsersDisabilityDatamodel();
	}

	/**
	 * Insert UsersDisability into database
 	 * @author TechFinium 
 	 * @see    UsersDisability
 	 */
	public void usersdisabilityInsert() {
		try {
				 service.create(this.usersdisability);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 usersdisabilityInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UsersDisability in database
 	 * @author TechFinium 
 	 * @see    UsersDisability
 	 */
    public void usersdisabilityUpdate() {
		try {
				 service.update(this.usersdisability);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 usersdisabilityInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UsersDisability from database
 	 * @author TechFinium 
 	 * @see    UsersDisability
 	 */
	public void usersdisabilityDelete() {
		try {
			 service.delete(this.usersdisability);
			  prepareNew();
			 usersdisabilityInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UsersDisability 
 	 * @author TechFinium 
 	 * @see    UsersDisability
 	 */
	public void prepareNew() {
		usersdisability = new UsersDisability();
	}

/*
    public List<SelectItem> getUsersDisabilityDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	usersdisabilityInfo();
    	for (UsersDisability ug : usersdisabilityList) {
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
    public List<UsersDisability> complete(String desc) {
		List<UsersDisability> l = null;
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
    
    public List<UsersDisability> getUsersDisabilityList() {
		return usersdisabilityList;
	}
	public void setUsersDisabilityList(List<UsersDisability> usersdisabilityList) {
		this.usersdisabilityList = usersdisabilityList;
	}
	public UsersDisability getUsersdisability() {
		return usersdisability;
	}
	public void setUsersdisability(UsersDisability usersdisability) {
		this.usersdisability = usersdisability;
	}

    public List<UsersDisability> getUsersDisabilityfilteredList() {
		return usersdisabilityfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param usersdisabilityfilteredList the new usersdisabilityfilteredList list
 	 * @see    UsersDisability
 	 */	
	public void setUsersDisabilityfilteredList(List<UsersDisability> usersdisabilityfilteredList) {
		this.usersdisabilityfilteredList = usersdisabilityfilteredList;
	}

	
	public LazyDataModel<UsersDisability> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UsersDisability> dataModel) {
		this.dataModel = dataModel;
	}
	
}
