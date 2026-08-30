package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.UserQualifications;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UserQualificationsService;

// TODO: Auto-generated Javadoc
/**
 * The Class UserQualificationsUI.
 */
@ManagedBean(name = "userqualificationsUI")
@ViewScoped
public class UserQualificationsUI extends AbstractUI {

	/** The service. */
	private UserQualificationsService service = new UserQualificationsService();
	
	/** The userqualifications list. */
	private List<UserQualifications> userqualificationsList = null;
	
	/** The userqualificationsfiltered list. */
	private List<UserQualifications> userqualificationsfilteredList = null;
	
	/** The userqualifications. */
	private UserQualifications userqualifications = null;
	
	/** The data model. */
	private LazyDataModel<UserQualifications> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all UserQualifications and prepare a for a create of a new UserQualifications.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    UserQualifications
	 */
	private void runInit() throws Exception {
		prepareNew();
		userqualificationsInfo();
	}

	/**
	 * Get all UserQualifications for data table.
	 *
	 * @author TechFinium
	 * @see    UserQualifications
	 */
	public void userqualificationsInfo() {
	 
			
			 dataModel = new LazyDataModel<UserQualifications>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UserQualifications> retorno = new  ArrayList<UserQualifications>();
			   
			   @Override 
			   public List<UserQualifications> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUserQualifications(UserQualifications.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UserQualifications.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UserQualifications obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UserQualifications getRowData(String rowKey) {
			        for(UserQualifications obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UserQualifications into database.
	 *
	 * @author TechFinium
	 * @see    UserQualifications
	 */
	public void userqualificationsInsert() {
		try {
				 service.create(this.userqualifications);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 userqualificationsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UserQualifications in database.
	 *
	 * @author TechFinium
	 * @see    UserQualifications
	 */
    public void userqualificationsUpdate() {
		try {
				 service.update(this.userqualifications);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 userqualificationsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UserQualifications from database.
	 *
	 * @author TechFinium
	 * @see    UserQualifications
	 */
	public void userqualificationsDelete() {
		try {
			 service.delete(this.userqualifications);
			  prepareNew();
			 userqualificationsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UserQualifications .
	 *
	 * @author TechFinium
	 * @see    UserQualifications
	 */
	public void prepareNew() {
		userqualifications = new UserQualifications();
	}

/*
    public List<SelectItem> getUserQualificationsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userqualificationsInfo();
    	for (UserQualifications ug : userqualificationsList) {
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
    public List<UserQualifications> complete(String desc) {
		List<UserQualifications> l = null;
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
    
    /**
     * Gets the user qualifications list.
     *
     * @return the user qualifications list
     */
    public List<UserQualifications> getUserQualificationsList() {
		return userqualificationsList;
	}
	
	/**
	 * Sets the user qualifications list.
	 *
	 * @param userqualificationsList the new user qualifications list
	 */
	public void setUserQualificationsList(List<UserQualifications> userqualificationsList) {
		this.userqualificationsList = userqualificationsList;
	}
	
	/**
	 * Gets the userqualifications.
	 *
	 * @return the userqualifications
	 */
	public UserQualifications getUserqualifications() {
		return userqualifications;
	}
	
	/**
	 * Sets the userqualifications.
	 *
	 * @param userqualifications the new userqualifications
	 */
	public void setUserqualifications(UserQualifications userqualifications) {
		this.userqualifications = userqualifications;
	}

    /**
     * Gets the user qualificationsfiltered list.
     *
     * @return the user qualificationsfiltered list
     */
    public List<UserQualifications> getUserQualificationsfilteredList() {
		return userqualificationsfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param userqualificationsfilteredList the new userqualificationsfilteredList list
	 * @see    UserQualifications
	 */	
	public void setUserQualificationsfilteredList(List<UserQualifications> userqualificationsfilteredList) {
		this.userqualificationsfilteredList = userqualificationsfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<UserQualifications> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<UserQualifications> dataModel) {
		this.dataModel = dataModel;
	}
	
}
