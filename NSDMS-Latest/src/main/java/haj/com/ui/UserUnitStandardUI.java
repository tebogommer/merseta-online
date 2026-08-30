package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.UserUnitStandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UserUnitStandardService;

// TODO: Auto-generated Javadoc
/**
 * The Class UserUnitStandardUI.
 */
@ManagedBean(name = "userunitstandardUI")
@ViewScoped
public class UserUnitStandardUI extends AbstractUI {

	/** The service. */
	private UserUnitStandardService service = new UserUnitStandardService();
	
	/** The userunitstandard list. */
	private List<UserUnitStandard> userunitstandardList = null;
	
	/** The userunitstandardfiltered list. */
	private List<UserUnitStandard> userunitstandardfilteredList = null;
	
	/** The userunitstandard. */
	private UserUnitStandard userunitstandard = null;
	
	/** The data model. */
	private LazyDataModel<UserUnitStandard> dataModel; 

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
	 * Initialize method to get all UserUnitStandard and prepare a for a create of a new UserUnitStandard.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    UserUnitStandard
	 */
	private void runInit() throws Exception {
		prepareNew();
		userunitstandardInfo();
	}

	/**
	 * Get all UserUnitStandard for data table.
	 *
	 * @author TechFinium
	 * @see    UserUnitStandard
	 */
	public void userunitstandardInfo() {
	 
			
			 dataModel = new LazyDataModel<UserUnitStandard>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UserUnitStandard> retorno = new  ArrayList<UserUnitStandard>();
			   
			   @Override 
			   public List<UserUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUserUnitStandard(UserUnitStandard.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UserUnitStandard.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UserUnitStandard obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UserUnitStandard getRowData(String rowKey) {
			        for(UserUnitStandard obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UserUnitStandard into database.
	 *
	 * @author TechFinium
	 * @see    UserUnitStandard
	 */
	public void userunitstandardInsert() {
		try {
				 service.create(this.userunitstandard);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 userunitstandardInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UserUnitStandard in database.
	 *
	 * @author TechFinium
	 * @see    UserUnitStandard
	 */
    public void userunitstandardUpdate() {
		try {
				 service.update(this.userunitstandard);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 userunitstandardInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UserUnitStandard from database.
	 *
	 * @author TechFinium
	 * @see    UserUnitStandard
	 */
	public void userunitstandardDelete() {
		try {
			 service.delete(this.userunitstandard);
			  prepareNew();
			 userunitstandardInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UserUnitStandard .
	 *
	 * @author TechFinium
	 * @see    UserUnitStandard
	 */
	public void prepareNew() {
		userunitstandard = new UserUnitStandard();
	}

/*
    public List<SelectItem> getUserUnitStandardDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userunitstandardInfo();
    	for (UserUnitStandard ug : userunitstandardList) {
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
    public List<UserUnitStandard> complete(String desc) {
		List<UserUnitStandard> l = null;
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
     * Gets the user unit standard list.
     *
     * @return the user unit standard list
     */
    public List<UserUnitStandard> getUserUnitStandardList() {
		return userunitstandardList;
	}
	
	/**
	 * Sets the user unit standard list.
	 *
	 * @param userunitstandardList the new user unit standard list
	 */
	public void setUserUnitStandardList(List<UserUnitStandard> userunitstandardList) {
		this.userunitstandardList = userunitstandardList;
	}
	
	/**
	 * Gets the userunitstandard.
	 *
	 * @return the userunitstandard
	 */
	public UserUnitStandard getUserunitstandard() {
		return userunitstandard;
	}
	
	/**
	 * Sets the userunitstandard.
	 *
	 * @param userunitstandard the new userunitstandard
	 */
	public void setUserunitstandard(UserUnitStandard userunitstandard) {
		this.userunitstandard = userunitstandard;
	}

    /**
     * Gets the user unit standardfiltered list.
     *
     * @return the user unit standardfiltered list
     */
    public List<UserUnitStandard> getUserUnitStandardfilteredList() {
		return userunitstandardfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param userunitstandardfilteredList the new userunitstandardfilteredList list
	 * @see    UserUnitStandard
	 */	
	public void setUserUnitStandardfilteredList(List<UserUnitStandard> userunitstandardfilteredList) {
		this.userunitstandardfilteredList = userunitstandardfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<UserUnitStandard> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<UserUnitStandard> dataModel) {
		this.dataModel = dataModel;
	}
	
}
