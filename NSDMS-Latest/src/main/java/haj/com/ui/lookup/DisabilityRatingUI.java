package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.DisabilityRating;
import haj.com.service.lookup.DisabilityRatingService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "disabilityratingUI")
@ViewScoped
public class DisabilityRatingUI extends AbstractUI {

	private DisabilityRatingService service = new DisabilityRatingService();
	private List<DisabilityRating> disabilityratingList = null;
	private List<DisabilityRating> disabilityratingfilteredList = null;
	private DisabilityRating disabilityrating = null;
	private LazyDataModel<DisabilityRating> dataModel; 

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
	 * Initialize method to get all DisabilityRating and prepare a for a create of a new DisabilityRating
 	 * @author TechFinium 
 	 * @see    DisabilityRating
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		disabilityratingInfo();
	}

	/**
	 * Get all DisabilityRating for data table
 	 * @author TechFinium 
 	 * @see    DisabilityRating
 	 */
	public void disabilityratingInfo() {
	 
			
			 dataModel = new LazyDataModel<DisabilityRating>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<DisabilityRating> retorno = new  ArrayList<DisabilityRating>();
			   
			   @Override 
			   public List<DisabilityRating> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allDisabilityRating(DisabilityRating.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(DisabilityRating.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(DisabilityRating obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public DisabilityRating getRowData(String rowKey) {
			        for(DisabilityRating obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert DisabilityRating into database
 	 * @author TechFinium 
 	 * @see    DisabilityRating
 	 */
	public void disabilityratingInsert() {
		try {
				 service.create(this.disabilityrating);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 disabilityratingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update DisabilityRating in database
 	 * @author TechFinium 
 	 * @see    DisabilityRating
 	 */
    public void disabilityratingUpdate() {
		try {
				 service.update(this.disabilityrating);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 disabilityratingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete DisabilityRating from database
 	 * @author TechFinium 
 	 * @see    DisabilityRating
 	 */
	public void disabilityratingDelete() {
		try {
			 service.delete(this.disabilityrating);
			  prepareNew();
			 disabilityratingInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of DisabilityRating 
 	 * @author TechFinium 
 	 * @see    DisabilityRating
 	 */
	public void prepareNew() {
		disabilityrating = new DisabilityRating();
	}

/*
    public List<SelectItem> getDisabilityRatingDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	disabilityratingInfo();
    	for (DisabilityRating ug : disabilityratingList) {
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
    public List<DisabilityRating> complete(String desc) {
		List<DisabilityRating> l = null;
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
    
    public List<DisabilityRating> getDisabilityRatingList() {
		return disabilityratingList;
	}
	public void setDisabilityRatingList(List<DisabilityRating> disabilityratingList) {
		this.disabilityratingList = disabilityratingList;
	}
	public DisabilityRating getDisabilityrating() {
		return disabilityrating;
	}
	public void setDisabilityrating(DisabilityRating disabilityrating) {
		this.disabilityrating = disabilityrating;
	}

    public List<DisabilityRating> getDisabilityRatingfilteredList() {
		return disabilityratingfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param disabilityratingfilteredList the new disabilityratingfilteredList list
 	 * @see    DisabilityRating
 	 */	
	public void setDisabilityRatingfilteredList(List<DisabilityRating> disabilityratingfilteredList) {
		this.disabilityratingfilteredList = disabilityratingfilteredList;
	}

	
	public LazyDataModel<DisabilityRating> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DisabilityRating> dataModel) {
		this.dataModel = dataModel;
	}
	
}
