package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.AllocationChange;
import haj.com.service.lookup.AllocationChangeService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "allocationchangeUI")
@ViewScoped
public class AllocationChangeUI extends AbstractUI {

	private AllocationChangeService service = new AllocationChangeService();
	private List<AllocationChange> allocationchangeList = null;
	private List<AllocationChange> allocationchangefilteredList = null;
	private AllocationChange allocationchange = null;
	private LazyDataModel<AllocationChange> dataModel; 

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
	 * Initialize method to get all AllocationChange and prepare a for a create of a new AllocationChange
 	 * @author TechFinium 
 	 * @see    AllocationChange
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		allocationchangeInfo();
	}

	/**
	 * Get all AllocationChange for data table
 	 * @author TechFinium 
 	 * @see    AllocationChange
 	 */
	public void allocationchangeInfo() {
	 
			
			 dataModel = new LazyDataModel<AllocationChange>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AllocationChange> retorno = new  ArrayList<AllocationChange>();
			   
			   @Override 
			   public List<AllocationChange> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAllocationChange(AllocationChange.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(AllocationChange.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AllocationChange obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AllocationChange getRowData(String rowKey) {
			        for(AllocationChange obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert AllocationChange into database
 	 * @author TechFinium 
 	 * @see    AllocationChange
 	 */
	public void allocationchangeInsert() {
		try {
				 service.create(this.allocationchange);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 allocationchangeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AllocationChange in database
 	 * @author TechFinium 
 	 * @see    AllocationChange
 	 */
    public void allocationchangeUpdate() {
		try {
				 service.update(this.allocationchange);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 allocationchangeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AllocationChange from database
 	 * @author TechFinium 
 	 * @see    AllocationChange
 	 */
	public void allocationchangeDelete() {
		try {
			 service.delete(this.allocationchange);
			  prepareNew();
			 allocationchangeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AllocationChange 
 	 * @author TechFinium 
 	 * @see    AllocationChange
 	 */
	public void prepareNew() {
		allocationchange = new AllocationChange();
	}

/*
    public List<SelectItem> getAllocationChangeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	allocationchangeInfo();
    	for (AllocationChange ug : allocationchangeList) {
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
    public List<AllocationChange> complete(String desc) {
		List<AllocationChange> l = null;
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
    
    public List<AllocationChange> getAllocationChangeList() {
		return allocationchangeList;
	}
	public void setAllocationChangeList(List<AllocationChange> allocationchangeList) {
		this.allocationchangeList = allocationchangeList;
	}
	public AllocationChange getAllocationchange() {
		return allocationchange;
	}
	public void setAllocationchange(AllocationChange allocationchange) {
		this.allocationchange = allocationchange;
	}

    public List<AllocationChange> getAllocationChangefilteredList() {
		return allocationchangefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param allocationchangefilteredList the new allocationchangefilteredList list
 	 * @see    AllocationChange
 	 */	
	public void setAllocationChangefilteredList(List<AllocationChange> allocationchangefilteredList) {
		this.allocationchangefilteredList = allocationchangefilteredList;
	}

	
	public LazyDataModel<AllocationChange> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AllocationChange> dataModel) {
		this.dataModel = dataModel;
	}
	
}
