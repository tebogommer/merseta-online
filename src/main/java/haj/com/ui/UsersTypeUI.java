package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.UsersType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UsersTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersTypeUI.
 */
@ManagedBean(name = "userstypeUI")
@ViewScoped
public class UsersTypeUI extends AbstractUI {

	/** The service. */
	private UsersTypeService service = new UsersTypeService();
	
	/** The userstype list. */
	private List<UsersType> userstypeList = null;
	
	/** The userstypefiltered list. */
	private List<UsersType> userstypefilteredList = null;
	
	/** The userstype. */
	private UsersType userstype = null;
	
	/** The data model. */
	private LazyDataModel<UsersType> dataModel; 

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
	 * Initialize method to get all UsersType and prepare a for a create of a new UsersType.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    UsersType
	 */
	private void runInit() throws Exception {
		prepareNew();
		userstypeInfo();
	}

	/**
	 * Get all UsersType for data table.
	 *
	 * @author TechFinium
	 * @see    UsersType
	 */
	public void userstypeInfo() {
	 
			
			 dataModel = new LazyDataModel<UsersType>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UsersType> retorno = new  ArrayList<UsersType>();
			   
			   @Override 
			   public List<UsersType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUsersType(UsersType.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UsersType.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UsersType obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UsersType getRowData(String rowKey) {
			        for(UsersType obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UsersType into database.
	 *
	 * @author TechFinium
	 * @see    UsersType
	 */
	public void userstypeInsert() {
		try {
				 service.create(this.userstype);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 userstypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UsersType in database.
	 *
	 * @author TechFinium
	 * @see    UsersType
	 */
    public void userstypeUpdate() {
		try {
				 service.update(this.userstype);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 userstypeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UsersType from database.
	 *
	 * @author TechFinium
	 * @see    UsersType
	 */
	public void userstypeDelete() {
		try {
			 service.delete(this.userstype);
			  prepareNew();
			 userstypeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UsersType .
	 *
	 * @author TechFinium
	 * @see    UsersType
	 */
	public void prepareNew() {
		userstype = new UsersType();
	}

/*
    public List<SelectItem> getUsersTypeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userstypeInfo();
    	for (UsersType ug : userstypeList) {
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
    public List<UsersType> complete(String desc) {
		List<UsersType> l = null;
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
     * Gets the users type list.
     *
     * @return the users type list
     */
    public List<UsersType> getUsersTypeList() {
		return userstypeList;
	}
	
	/**
	 * Sets the users type list.
	 *
	 * @param userstypeList the new users type list
	 */
	public void setUsersTypeList(List<UsersType> userstypeList) {
		this.userstypeList = userstypeList;
	}
	
	/**
	 * Gets the userstype.
	 *
	 * @return the userstype
	 */
	public UsersType getUserstype() {
		return userstype;
	}
	
	/**
	 * Sets the userstype.
	 *
	 * @param userstype the new userstype
	 */
	public void setUserstype(UsersType userstype) {
		this.userstype = userstype;
	}

    /**
     * Gets the users typefiltered list.
     *
     * @return the users typefiltered list
     */
    public List<UsersType> getUsersTypefilteredList() {
		return userstypefilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param userstypefilteredList the new userstypefilteredList list
	 * @see    UsersType
	 */	
	public void setUsersTypefilteredList(List<UsersType> userstypefilteredList) {
		this.userstypefilteredList = userstypefilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<UsersType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<UsersType> dataModel) {
		this.dataModel = dataModel;
	}
	
}
