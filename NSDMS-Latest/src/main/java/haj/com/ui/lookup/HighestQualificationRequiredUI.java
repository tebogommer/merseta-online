package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.HighestQualificationRequired;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.HighestQualificationRequiredService;

@ManagedBean(name = "highestqualificationrequiredUI")
@ViewScoped
public class HighestQualificationRequiredUI extends AbstractUI {

	private HighestQualificationRequiredService service = new HighestQualificationRequiredService();
	private List<HighestQualificationRequired> highestqualificationrequiredList = null;
	private List<HighestQualificationRequired> highestqualificationrequiredfilteredList = null;
	private HighestQualificationRequired highestqualificationrequired = null;
	private LazyDataModel<HighestQualificationRequired> dataModel; 

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
	 * Initialize method to get all HighestQualificationRequired and prepare a for a create of a new HighestQualificationRequired
 	 * @author TechFinium 
 	 * @see    HighestQualificationRequired
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		highestqualificationrequiredInfo();
	}

	/**
	 * Get all HighestQualificationRequired for data table
 	 * @author TechFinium 
 	 * @see    HighestQualificationRequired
 	 */
	public void highestqualificationrequiredInfo() {
	 
			
			 dataModel = new LazyDataModel<HighestQualificationRequired>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<HighestQualificationRequired> retorno = new  ArrayList<HighestQualificationRequired>();
			   
			   @Override 
			   public List<HighestQualificationRequired> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allHighestQualificationRequired(HighestQualificationRequired.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(HighestQualificationRequired.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(HighestQualificationRequired obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public HighestQualificationRequired getRowData(String rowKey) {
			        for(HighestQualificationRequired obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert HighestQualificationRequired into database
 	 * @author TechFinium 
 	 * @see    HighestQualificationRequired
 	 */
	public void highestqualificationrequiredInsert() {
		try {
				 service.create(this.highestqualificationrequired);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 highestqualificationrequiredInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update HighestQualificationRequired in database
 	 * @author TechFinium 
 	 * @see    HighestQualificationRequired
 	 */
    public void highestqualificationrequiredUpdate() {
		try {
				 service.update(this.highestqualificationrequired);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 highestqualificationrequiredInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete HighestQualificationRequired from database
 	 * @author TechFinium 
 	 * @see    HighestQualificationRequired
 	 */
	public void highestqualificationrequiredDelete() {
		try {
			 service.delete(this.highestqualificationrequired);
			  prepareNew();
			 highestqualificationrequiredInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of HighestQualificationRequired 
 	 * @author TechFinium 
 	 * @see    HighestQualificationRequired
 	 */
	public void prepareNew() {
		highestqualificationrequired = new HighestQualificationRequired();
	}

/*
    public List<SelectItem> getHighestQualificationRequiredDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	highestqualificationrequiredInfo();
    	for (HighestQualificationRequired ug : highestqualificationrequiredList) {
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
    public List<HighestQualificationRequired> complete(String desc) {
		List<HighestQualificationRequired> l = null;
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
    
    public List<HighestQualificationRequired> getHighestQualificationRequiredList() {
		return highestqualificationrequiredList;
	}
	public void setHighestQualificationRequiredList(List<HighestQualificationRequired> highestqualificationrequiredList) {
		this.highestqualificationrequiredList = highestqualificationrequiredList;
	}
	public HighestQualificationRequired getHighestqualificationrequired() {
		return highestqualificationrequired;
	}
	public void setHighestqualificationrequired(HighestQualificationRequired highestqualificationrequired) {
		this.highestqualificationrequired = highestqualificationrequired;
	}

    public List<HighestQualificationRequired> getHighestQualificationRequiredfilteredList() {
		return highestqualificationrequiredfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param highestqualificationrequiredfilteredList the new highestqualificationrequiredfilteredList list
 	 * @see    HighestQualificationRequired
 	 */	
	public void setHighestQualificationRequiredfilteredList(List<HighestQualificationRequired> highestqualificationrequiredfilteredList) {
		this.highestqualificationrequiredfilteredList = highestqualificationrequiredfilteredList;
	}

	
	public LazyDataModel<HighestQualificationRequired> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<HighestQualificationRequired> dataModel) {
		this.dataModel = dataModel;
	}
	
}
