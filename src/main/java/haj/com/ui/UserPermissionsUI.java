package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.UserPermissions;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UserPermissionsService;

@ManagedBean(name = "userpermissionsUI")
@ViewScoped
public class UserPermissionsUI extends AbstractUI {

	private UserPermissionsService service = new UserPermissionsService();
	private List<UserPermissions> userpermissionsList = null;
	private List<UserPermissions> userpermissionsfilteredList = null;
	private UserPermissions userpermissions = null;
	private LazyDataModel<UserPermissions> dataModel; 

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
	 * Initialize method to get all UserPermissions and prepare a for a create of a new UserPermissions
 	 * @author TechFinium 
 	 * @see    UserPermissions
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		userpermissionsInfo();
	}

	/**
	 * Get all UserPermissions for data table
 	 * @author TechFinium 
 	 * @see    UserPermissions
 	 */
	public void userpermissionsInfo() {
//			dataModel = new UserPermissionsDatamodel();
	}

	/**
	 * Insert UserPermissions into database
 	 * @author TechFinium 
 	 * @see    UserPermissions
 	 */
	public void userpermissionsInsert() {
		try {
				 service.create(this.userpermissions);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 userpermissionsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UserPermissions in database
 	 * @author TechFinium 
 	 * @see    UserPermissions
 	 */
    public void userpermissionsUpdate() {
		try {
				 service.update(this.userpermissions);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 userpermissionsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UserPermissions from database
 	 * @author TechFinium 
 	 * @see    UserPermissions
 	 */
	public void userpermissionsDelete() {
		try {
			 service.delete(this.userpermissions);
			  prepareNew();
			 userpermissionsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UserPermissions 
 	 * @author TechFinium 
 	 * @see    UserPermissions
 	 */
	public void prepareNew() {
		userpermissions = new UserPermissions();
	}

/*
    public List<SelectItem> getUserPermissionsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userpermissionsInfo();
    	for (UserPermissions ug : userpermissionsList) {
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
    public List<UserPermissions> complete(String desc) {
		List<UserPermissions> l = null;
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
    
    public List<UserPermissions> getUserPermissionsList() {
		return userpermissionsList;
	}
	public void setUserPermissionsList(List<UserPermissions> userpermissionsList) {
		this.userpermissionsList = userpermissionsList;
	}
	public UserPermissions getUserpermissions() {
		return userpermissions;
	}
	public void setUserpermissions(UserPermissions userpermissions) {
		this.userpermissions = userpermissions;
	}

    public List<UserPermissions> getUserPermissionsfilteredList() {
		return userpermissionsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param userpermissionsfilteredList the new userpermissionsfilteredList list
 	 * @see    UserPermissions
 	 */	
	public void setUserPermissionsfilteredList(List<UserPermissions> userpermissionsfilteredList) {
		this.userpermissionsfilteredList = userpermissionsfilteredList;
	}

	
	public LazyDataModel<UserPermissions> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UserPermissions> dataModel) {
		this.dataModel = dataModel;
	}
	
}
