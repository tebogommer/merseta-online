package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.RolesService;

// TODO: Auto-generated Javadoc
/**
 * The Class RolesUI.
 */
@ManagedBean(name = "rolesUI")
@ViewScoped
public class RolesUI extends AbstractUI {

	/** The service. */
	private RolesService service = new RolesService();
	
	/** The roles list. */
	private List<Roles> rolesList = null;
	
	/** The rolesfiltered list. */
	private List<Roles> rolesfilteredList = null;
	
	/** The roles. */
	private Roles roles = null;
	
	/** The data model. */
	private LazyDataModel<Roles> dataModel; 

    /**
     * Inits the.
     */
    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all Roles and prepare a for a create of a new Roles.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Roles
	 */
	private void runInit() throws Exception {
		prepareNew();
		rolesInfo();
	}

	/**
	 * Get all Roles for data table.
	 *
	 * @author TechFinium
	 * @see    Roles
	 */
	public void rolesInfo() {
	 
			
			 dataModel = new LazyDataModel<Roles>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Roles> retorno = new  ArrayList<Roles>();
			   
			   @Override 
			   public List<Roles> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allRoles(Roles.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Roles.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Roles obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Roles getRowData(String rowKey) {
			        for(Roles obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Roles into database.
	 *
	 * @author TechFinium
	 * @see    Roles
	 */
	public void rolesInsert() {
		try {
				 service.create(this.roles);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 rolesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Roles in database.
	 *
	 * @author TechFinium
	 * @see    Roles
	 */
    public void rolesUpdate() {
		try {
				 service.update(this.roles);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 rolesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Roles from database.
	 *
	 * @author TechFinium
	 * @see    Roles
	 */
	public void rolesDelete() {
		try {
			 service.delete(this.roles);
			  prepareNew();
			 rolesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Roles .
	 *
	 * @author TechFinium
	 * @see    Roles
	 */
	public void prepareNew() {
		roles = new Roles();
	}

/*
    public List<SelectItem> getRolesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	rolesInfo();
    	for (Roles ug : rolesList) {
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
    public List<Roles> complete(String desc) {
		List<Roles> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    /**
     * Gets the roles list.
     *
     * @return the roles list
     */
    public List<Roles> getRolesList() {
		return rolesList;
	}
	
	/**
	 * Sets the roles list.
	 *
	 * @param rolesList the new roles list
	 */
	public void setRolesList(List<Roles> rolesList) {
		this.rolesList = rolesList;
	}
	
	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public Roles getRoles() {
		return roles;
	}
	
	/**
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public void setRoles(Roles roles) {
		this.roles = roles;
	}

    /**
     * Gets the rolesfiltered list.
     *
     * @return the rolesfiltered list
     */
    public List<Roles> getRolesfilteredList() {
		return rolesfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param rolesfilteredList the new rolesfilteredList list
	 * @see    Roles
	 */	
	public void setRolesfilteredList(List<Roles> rolesfilteredList) {
		this.rolesfilteredList = rolesfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Roles> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Roles> dataModel) {
		this.dataModel = dataModel;
	}
	
}
