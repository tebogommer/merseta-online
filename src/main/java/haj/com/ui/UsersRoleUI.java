package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.UsersRole;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UsersRoleService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersRoleUI.
 */
@ManagedBean(name = "usersroleUI")
@ViewScoped
public class UsersRoleUI extends AbstractUI {

	/** The service. */
	private UsersRoleService service = new UsersRoleService();
	
	/** The usersrole list. */
	private List<UsersRole> usersroleList = null;
	
	/** The usersrolefiltered list. */
	private List<UsersRole> usersrolefilteredList = null;
	
	/** The usersrole. */
	private UsersRole usersrole = null;
	
	/** The data model. */
	private LazyDataModel<UsersRole> dataModel; 

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
	 * Initialize method to get all UsersRole and prepare a for a create of a new UsersRole.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    UsersRole
	 */
	private void runInit() throws Exception {
		prepareNew();
		usersroleInfo();
	}

	/**
	 * Get all UsersRole for data table.
	 *
	 * @author TechFinium
	 * @see    UsersRole
	 */
	public void usersroleInfo() {
	 
			
			 dataModel = new LazyDataModel<UsersRole>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UsersRole> retorno = new  ArrayList<UsersRole>();
			   
			   @Override 
			   public List<UsersRole> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUsersRole(UsersRole.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UsersRole.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UsersRole obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UsersRole getRowData(String rowKey) {
			        for(UsersRole obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UsersRole into database.
	 *
	 * @author TechFinium
	 * @see    UsersRole
	 */
	public void usersroleInsert() {
		try {
				 service.create(this.usersrole);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 usersroleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UsersRole in database.
	 *
	 * @author TechFinium
	 * @see    UsersRole
	 */
    public void usersroleUpdate() {
		try {
				 service.update(this.usersrole);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 usersroleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UsersRole from database.
	 *
	 * @author TechFinium
	 * @see    UsersRole
	 */
	public void usersroleDelete() {
		try {
			 service.delete(this.usersrole);
			  prepareNew();
			 usersroleInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UsersRole .
	 *
	 * @author TechFinium
	 * @see    UsersRole
	 */
	public void prepareNew() {
		usersrole = new UsersRole();
	}

/*
    public List<SelectItem> getUsersRoleDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	usersroleInfo();
    	for (UsersRole ug : usersroleList) {
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
    public List<UsersRole> complete(String desc) {
		List<UsersRole> l = null;
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
     * Gets the users role list.
     *
     * @return the users role list
     */
    public List<UsersRole> getUsersRoleList() {
		return usersroleList;
	}
	
	/**
	 * Sets the users role list.
	 *
	 * @param usersroleList the new users role list
	 */
	public void setUsersRoleList(List<UsersRole> usersroleList) {
		this.usersroleList = usersroleList;
	}
	
	/**
	 * Gets the usersrole.
	 *
	 * @return the usersrole
	 */
	public UsersRole getUsersrole() {
		return usersrole;
	}
	
	/**
	 * Sets the usersrole.
	 *
	 * @param usersrole the new usersrole
	 */
	public void setUsersrole(UsersRole usersrole) {
		this.usersrole = usersrole;
	}

    /**
     * Gets the users rolefiltered list.
     *
     * @return the users rolefiltered list
     */
    public List<UsersRole> getUsersRolefilteredList() {
		return usersrolefilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param usersrolefilteredList the new usersrolefilteredList list
	 * @see    UsersRole
	 */	
	public void setUsersRolefilteredList(List<UsersRole> usersrolefilteredList) {
		this.usersrolefilteredList = usersrolefilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<UsersRole> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<UsersRole> dataModel) {
		this.dataModel = dataModel;
	}
	
}
