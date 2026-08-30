package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.UsersResponsibilities;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UsersResponsibilitiesService;

@ManagedBean(name = "usersresponsibilitiesUI")
@ViewScoped
public class UsersResponsibilitiesUI extends AbstractUI {

	private UsersResponsibilitiesService service = new UsersResponsibilitiesService();
	private List<UsersResponsibilities> usersresponsibilitiesList = null;
	private List<UsersResponsibilities> usersresponsibilitiesfilteredList = null;
	private UsersResponsibilities usersresponsibilities = null;
	private LazyDataModel<UsersResponsibilities> dataModel; 

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
	 * Initialize method to get all UsersResponsibilities and prepare a for a create of a new UsersResponsibilities
 	 * @author TechFinium 
 	 * @see    UsersResponsibilities
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		usersresponsibilitiesInfo();
	}

	/**
	 * Get all UsersResponsibilities for data table
 	 * @author TechFinium 
 	 * @see    UsersResponsibilities
 	 */
	public void usersresponsibilitiesInfo() {
	 
			
			 dataModel = new LazyDataModel<UsersResponsibilities>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UsersResponsibilities> retorno = new  ArrayList<UsersResponsibilities>();
			   
			   @Override 
			   public List<UsersResponsibilities> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUsersResponsibilitiesResolve(UsersResponsibilities.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UsersResponsibilities.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UsersResponsibilities obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UsersResponsibilities getRowData(String rowKey) {
			        for(UsersResponsibilities obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UsersResponsibilities into database
 	 * @author TechFinium 
 	 * @see    UsersResponsibilities
 	 */
	public void usersresponsibilitiesInsert() {
		try {
				 service.create(this.usersresponsibilities);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 usersresponsibilitiesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UsersResponsibilities in database
 	 * @author TechFinium 
 	 * @see    UsersResponsibilities
 	 */
    public void usersresponsibilitiesUpdate() {
		try {
				 service.update(this.usersresponsibilities);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 usersresponsibilitiesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UsersResponsibilities from database
 	 * @author TechFinium 
 	 * @see    UsersResponsibilities
 	 */
	public void usersresponsibilitiesDelete() {
		try {
			 service.delete(this.usersresponsibilities);
			  prepareNew();
			 usersresponsibilitiesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UsersResponsibilities 
 	 * @author TechFinium 
 	 * @see    UsersResponsibilities
 	 */
	public void prepareNew() {
		usersresponsibilities = new UsersResponsibilities();
	}

/*
    public List<SelectItem> getUsersResponsibilitiesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	usersresponsibilitiesInfo();
    	for (UsersResponsibilities ug : usersresponsibilitiesList) {
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
    public List<UsersResponsibilities> complete(String desc) {
		List<UsersResponsibilities> l = null;
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
    
    public List<UsersResponsibilities> getUsersResponsibilitiesList() {
		return usersresponsibilitiesList;
	}
	public void setUsersResponsibilitiesList(List<UsersResponsibilities> usersresponsibilitiesList) {
		this.usersresponsibilitiesList = usersresponsibilitiesList;
	}
	public UsersResponsibilities getUsersresponsibilities() {
		return usersresponsibilities;
	}
	public void setUsersresponsibilities(UsersResponsibilities usersresponsibilities) {
		this.usersresponsibilities = usersresponsibilities;
	}

    public List<UsersResponsibilities> getUsersResponsibilitiesfilteredList() {
		return usersresponsibilitiesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param usersresponsibilitiesfilteredList the new usersresponsibilitiesfilteredList list
 	 * @see    UsersResponsibilities
 	 */	
	public void setUsersResponsibilitiesfilteredList(List<UsersResponsibilities> usersresponsibilitiesfilteredList) {
		this.usersresponsibilitiesfilteredList = usersresponsibilitiesfilteredList;
	}

	
	public LazyDataModel<UsersResponsibilities> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UsersResponsibilities> dataModel) {
		this.dataModel = dataModel;
	}
	
}
