package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.NonSetaCompanyUsers;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.NonSetaCompanyUsersService;

@ManagedBean(name = "nonsetacompanyusersUI")
@ViewScoped
public class NonSetaCompanyUsersUI extends AbstractUI {

	private NonSetaCompanyUsersService service = new NonSetaCompanyUsersService();
	private List<NonSetaCompanyUsers> nonsetacompanyusersList = null;
	private List<NonSetaCompanyUsers> nonsetacompanyusersfilteredList = null;
	private NonSetaCompanyUsers nonsetacompanyusers = null;
	private LazyDataModel<NonSetaCompanyUsers> dataModel; 

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
	 * Initialize method to get all NonSetaCompanyUsers and prepare a for a create of a new NonSetaCompanyUsers
 	 * @author TechFinium 
 	 * @see    NonSetaCompanyUsers
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		nonsetacompanyusersInfo();
	}

	/**
	 * Get all NonSetaCompanyUsers for data table
 	 * @author TechFinium 
 	 * @see    NonSetaCompanyUsers
 	 */
	public void nonsetacompanyusersInfo() {
//			dataModel = new NonSetaCompanyUsersDatamodel();
	}

	/**
	 * Insert NonSetaCompanyUsers into database
 	 * @author TechFinium 
 	 * @see    NonSetaCompanyUsers
 	 */
	public void nonsetacompanyusersInsert() {
		try {
				 service.create(this.nonsetacompanyusers);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 nonsetacompanyusersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update NonSetaCompanyUsers in database
 	 * @author TechFinium 
 	 * @see    NonSetaCompanyUsers
 	 */
    public void nonsetacompanyusersUpdate() {
		try {
				 service.update(this.nonsetacompanyusers);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 nonsetacompanyusersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete NonSetaCompanyUsers from database
 	 * @author TechFinium 
 	 * @see    NonSetaCompanyUsers
 	 */
	public void nonsetacompanyusersDelete() {
		try {
			 service.delete(this.nonsetacompanyusers);
			  prepareNew();
			 nonsetacompanyusersInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of NonSetaCompanyUsers 
 	 * @author TechFinium 
 	 * @see    NonSetaCompanyUsers
 	 */
	public void prepareNew() {
		nonsetacompanyusers = new NonSetaCompanyUsers();
	}

/*
    public List<SelectItem> getNonSetaCompanyUsersDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	nonsetacompanyusersInfo();
    	for (NonSetaCompanyUsers ug : nonsetacompanyusersList) {
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
    public List<NonSetaCompanyUsers> complete(String desc) {
		List<NonSetaCompanyUsers> l = null;
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
    
    public List<NonSetaCompanyUsers> getNonSetaCompanyUsersList() {
		return nonsetacompanyusersList;
	}
	public void setNonSetaCompanyUsersList(List<NonSetaCompanyUsers> nonsetacompanyusersList) {
		this.nonsetacompanyusersList = nonsetacompanyusersList;
	}
	public NonSetaCompanyUsers getNonsetacompanyusers() {
		return nonsetacompanyusers;
	}
	public void setNonsetacompanyusers(NonSetaCompanyUsers nonsetacompanyusers) {
		this.nonsetacompanyusers = nonsetacompanyusers;
	}

    public List<NonSetaCompanyUsers> getNonSetaCompanyUsersfilteredList() {
		return nonsetacompanyusersfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param nonsetacompanyusersfilteredList the new nonsetacompanyusersfilteredList list
 	 * @see    NonSetaCompanyUsers
 	 */	
	public void setNonSetaCompanyUsersfilteredList(List<NonSetaCompanyUsers> nonsetacompanyusersfilteredList) {
		this.nonsetacompanyusersfilteredList = nonsetacompanyusersfilteredList;
	}

	
	public LazyDataModel<NonSetaCompanyUsers> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<NonSetaCompanyUsers> dataModel) {
		this.dataModel = dataModel;
	}
	
}
