package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.PrioritisationScale;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.PrioritisationScaleService;

@ManagedBean(name = "prioritisationscaleUI")
@ViewScoped
public class PrioritisationScaleUI extends AbstractUI {

	private PrioritisationScaleService service = new PrioritisationScaleService();
	private List<PrioritisationScale> prioritisationscaleList = null;
	private List<PrioritisationScale> prioritisationscalefilteredList = null;
	private PrioritisationScale prioritisationscale = null;
	private LazyDataModel<PrioritisationScale> dataModel; 

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
	 * Initialize method to get all PrioritisationScale and prepare a for a create of a new PrioritisationScale
 	 * @author TechFinium 
 	 * @see    PrioritisationScale
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		prioritisationscaleInfo();
	}

	/**
	 * Get all PrioritisationScale for data table
 	 * @author TechFinium 
 	 * @see    PrioritisationScale
 	 */
	public void prioritisationscaleInfo() {
	 
			
			 dataModel = new LazyDataModel<PrioritisationScale>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<PrioritisationScale> retorno = new  ArrayList<PrioritisationScale>();
			   
			   @Override 
			   public List<PrioritisationScale> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allPrioritisationScale(PrioritisationScale.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(PrioritisationScale.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(PrioritisationScale obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public PrioritisationScale getRowData(String rowKey) {
			        for(PrioritisationScale obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert PrioritisationScale into database
 	 * @author TechFinium 
 	 * @see    PrioritisationScale
 	 */
	public void prioritisationscaleInsert() {
		try {
				 service.create(this.prioritisationscale);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 prioritisationscaleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update PrioritisationScale in database
 	 * @author TechFinium 
 	 * @see    PrioritisationScale
 	 */
    public void prioritisationscaleUpdate() {
		try {
				 service.update(this.prioritisationscale);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 prioritisationscaleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete PrioritisationScale from database
 	 * @author TechFinium 
 	 * @see    PrioritisationScale
 	 */
	public void prioritisationscaleDelete() {
		try {
			 service.delete(this.prioritisationscale);
			  prepareNew();
			 prioritisationscaleInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of PrioritisationScale 
 	 * @author TechFinium 
 	 * @see    PrioritisationScale
 	 */
	public void prepareNew() {
		prioritisationscale = new PrioritisationScale();
	}

/*
    public List<SelectItem> getPrioritisationScaleDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	prioritisationscaleInfo();
    	for (PrioritisationScale ug : prioritisationscaleList) {
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
    public List<PrioritisationScale> complete(String desc) {
		List<PrioritisationScale> l = null;
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
    
    public List<PrioritisationScale> getPrioritisationScaleList() {
		return prioritisationscaleList;
	}
	public void setPrioritisationScaleList(List<PrioritisationScale> prioritisationscaleList) {
		this.prioritisationscaleList = prioritisationscaleList;
	}
	public PrioritisationScale getPrioritisationscale() {
		return prioritisationscale;
	}
	public void setPrioritisationscale(PrioritisationScale prioritisationscale) {
		this.prioritisationscale = prioritisationscale;
	}

    public List<PrioritisationScale> getPrioritisationScalefilteredList() {
		return prioritisationscalefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param prioritisationscalefilteredList the new prioritisationscalefilteredList list
 	 * @see    PrioritisationScale
 	 */	
	public void setPrioritisationScalefilteredList(List<PrioritisationScale> prioritisationscalefilteredList) {
		this.prioritisationscalefilteredList = prioritisationscalefilteredList;
	}

	
	public LazyDataModel<PrioritisationScale> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<PrioritisationScale> dataModel) {
		this.dataModel = dataModel;
	}
	
}
