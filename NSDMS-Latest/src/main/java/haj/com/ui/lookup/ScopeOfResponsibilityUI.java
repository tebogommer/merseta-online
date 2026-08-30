package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ScopeOfResponsibility;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ScopeOfResponsibilityService;

@ManagedBean(name = "scopeofresponsibilityUI")
@ViewScoped
public class ScopeOfResponsibilityUI extends AbstractUI {

	private ScopeOfResponsibilityService service = new ScopeOfResponsibilityService();
	private List<ScopeOfResponsibility> scopeofresponsibilityList = null;
	private List<ScopeOfResponsibility> scopeofresponsibilityfilteredList = null;
	private ScopeOfResponsibility scopeofresponsibility = null;
	private LazyDataModel<ScopeOfResponsibility> dataModel; 

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
	 * Initialize method to get all ScopeOfResponsibility and prepare a for a create of a new ScopeOfResponsibility
 	 * @author TechFinium 
 	 * @see    ScopeOfResponsibility
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		scopeofresponsibilityInfo();
	}

	/**
	 * Get all ScopeOfResponsibility for data table
 	 * @author TechFinium 
 	 * @see    ScopeOfResponsibility
 	 */
	public void scopeofresponsibilityInfo() {
	 
			
			 dataModel = new LazyDataModel<ScopeOfResponsibility>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ScopeOfResponsibility> retorno = new  ArrayList<ScopeOfResponsibility>();
			   
			   @Override 
			   public List<ScopeOfResponsibility> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allScopeOfResponsibility(ScopeOfResponsibility.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ScopeOfResponsibility.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ScopeOfResponsibility obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ScopeOfResponsibility getRowData(String rowKey) {
			        for(ScopeOfResponsibility obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ScopeOfResponsibility into database
 	 * @author TechFinium 
 	 * @see    ScopeOfResponsibility
 	 */
	public void scopeofresponsibilityInsert() {
		try {
				 service.create(this.scopeofresponsibility);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 scopeofresponsibilityInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ScopeOfResponsibility in database
 	 * @author TechFinium 
 	 * @see    ScopeOfResponsibility
 	 */
    public void scopeofresponsibilityUpdate() {
		try {
				 service.update(this.scopeofresponsibility);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 scopeofresponsibilityInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ScopeOfResponsibility from database
 	 * @author TechFinium 
 	 * @see    ScopeOfResponsibility
 	 */
	public void scopeofresponsibilityDelete() {
		try {
			 service.delete(this.scopeofresponsibility);
			  prepareNew();
			 scopeofresponsibilityInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ScopeOfResponsibility 
 	 * @author TechFinium 
 	 * @see    ScopeOfResponsibility
 	 */
	public void prepareNew() {
		scopeofresponsibility = new ScopeOfResponsibility();
	}

/*
    public List<SelectItem> getScopeOfResponsibilityDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	scopeofresponsibilityInfo();
    	for (ScopeOfResponsibility ug : scopeofresponsibilityList) {
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
    public List<ScopeOfResponsibility> complete(String desc) {
		List<ScopeOfResponsibility> l = null;
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
    
    public List<ScopeOfResponsibility> getScopeOfResponsibilityList() {
		return scopeofresponsibilityList;
	}
	public void setScopeOfResponsibilityList(List<ScopeOfResponsibility> scopeofresponsibilityList) {
		this.scopeofresponsibilityList = scopeofresponsibilityList;
	}
	public ScopeOfResponsibility getScopeofresponsibility() {
		return scopeofresponsibility;
	}
	public void setScopeofresponsibility(ScopeOfResponsibility scopeofresponsibility) {
		this.scopeofresponsibility = scopeofresponsibility;
	}

    public List<ScopeOfResponsibility> getScopeOfResponsibilityfilteredList() {
		return scopeofresponsibilityfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param scopeofresponsibilityfilteredList the new scopeofresponsibilityfilteredList list
 	 * @see    ScopeOfResponsibility
 	 */	
	public void setScopeOfResponsibilityfilteredList(List<ScopeOfResponsibility> scopeofresponsibilityfilteredList) {
		this.scopeofresponsibilityfilteredList = scopeofresponsibilityfilteredList;
	}

	
	public LazyDataModel<ScopeOfResponsibility> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ScopeOfResponsibility> dataModel) {
		this.dataModel = dataModel;
	}
	
}
