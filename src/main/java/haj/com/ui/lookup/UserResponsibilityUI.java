package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.UserResponsibility;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.UserResponsibilityService;

@ManagedBean(name = "userresponsibilityUI")
@ViewScoped
public class UserResponsibilityUI extends AbstractUI {

	private UserResponsibilityService service = new UserResponsibilityService();
	private List<UserResponsibility> userresponsibilityList = null;
	private List<UserResponsibility> userresponsibilityfilteredList = null;
	private UserResponsibility userresponsibility = null;
	private LazyDataModel<UserResponsibility> dataModel; 

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
	 * Initialize method to get all UserResponsibility and prepare a for a create of a new UserResponsibility
 	 * @author TechFinium 
 	 * @see    UserResponsibility
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		userresponsibilityInfo();
	}

	/**
	 * Get all UserResponsibility for data table
 	 * @author TechFinium 
 	 * @see    UserResponsibility
 	 */
	public void userresponsibilityInfo() {
	 
			
			 dataModel = new LazyDataModel<UserResponsibility>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UserResponsibility> retorno = new  ArrayList<UserResponsibility>();
			   
			   @Override 
			   public List<UserResponsibility> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUserResponsibility(UserResponsibility.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UserResponsibility.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UserResponsibility obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UserResponsibility getRowData(String rowKey) {
			        for(UserResponsibility obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UserResponsibility into database
 	 * @author TechFinium 
 	 * @see    UserResponsibility
 	 */
	public void userresponsibilityInsert() {
		try {
				 service.create(this.userresponsibility);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 userresponsibilityInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UserResponsibility in database
 	 * @author TechFinium 
 	 * @see    UserResponsibility
 	 */
    public void userresponsibilityUpdate() {
		try {
				 service.update(this.userresponsibility);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 userresponsibilityInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UserResponsibility from database
 	 * @author TechFinium 
 	 * @see    UserResponsibility
 	 */
	public void userresponsibilityDelete() {
		try {
			 service.delete(this.userresponsibility);
			  prepareNew();
			 userresponsibilityInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UserResponsibility 
 	 * @author TechFinium 
 	 * @see    UserResponsibility
 	 */
	public void prepareNew() {
		userresponsibility = new UserResponsibility();
	}

/*
    public List<SelectItem> getUserResponsibilityDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userresponsibilityInfo();
    	for (UserResponsibility ug : userresponsibilityList) {
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
    public List<UserResponsibility> complete(String desc) {
		List<UserResponsibility> l = null;
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
    
    public List<UserResponsibility> getUserResponsibilityList() {
		return userresponsibilityList;
	}
	public void setUserResponsibilityList(List<UserResponsibility> userresponsibilityList) {
		this.userresponsibilityList = userresponsibilityList;
	}
	public UserResponsibility getUserresponsibility() {
		return userresponsibility;
	}
	public void setUserresponsibility(UserResponsibility userresponsibility) {
		this.userresponsibility = userresponsibility;
	}

    public List<UserResponsibility> getUserResponsibilityfilteredList() {
		return userresponsibilityfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param userresponsibilityfilteredList the new userresponsibilityfilteredList list
 	 * @see    UserResponsibility
 	 */	
	public void setUserResponsibilityfilteredList(List<UserResponsibility> userresponsibilityfilteredList) {
		this.userresponsibilityfilteredList = userresponsibilityfilteredList;
	}

	
	public LazyDataModel<UserResponsibility> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UserResponsibility> dataModel) {
		this.dataModel = dataModel;
	}
	
}
