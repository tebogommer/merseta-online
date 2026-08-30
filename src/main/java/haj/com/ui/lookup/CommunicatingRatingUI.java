package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.CommunicatingRating;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.CommunicatingRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatingRatingUI.
 */
@ManagedBean(name = "communicatingratingUI")
@ViewScoped
public class CommunicatingRatingUI extends AbstractUI {

	/** The service. */
	private CommunicatingRatingService service = new CommunicatingRatingService();
	
	/** The communicatingrating list. */
	private List<CommunicatingRating> communicatingratingList = null;
	
	/** The communicatingratingfiltered list. */
	private List<CommunicatingRating> communicatingratingfilteredList = null;
	
	/** The communicatingrating. */
	private CommunicatingRating communicatingrating = null;
	
	/** The data model. */
	private LazyDataModel<CommunicatingRating> dataModel; 

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
	 * Initialize method to get all CommunicatingRating and prepare a for a create of a new CommunicatingRating.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    CommunicatingRating
	 */
	private void runInit() throws Exception {
		prepareNew();
		communicatingratingInfo();
	}

	/**
	 * Get all CommunicatingRating for data table.
	 *
	 * @author TechFinium
	 * @see    CommunicatingRating
	 */
	public void communicatingratingInfo() {
	 
			
			 dataModel = new LazyDataModel<CommunicatingRating>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<CommunicatingRating> retorno = new  ArrayList<CommunicatingRating>();
			   
			   @Override 
			   public List<CommunicatingRating> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allCommunicatingRating(CommunicatingRating.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(CommunicatingRating.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(CommunicatingRating obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public CommunicatingRating getRowData(String rowKey) {
			        for(CommunicatingRating obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert CommunicatingRating into database.
	 *
	 * @author TechFinium
	 * @see    CommunicatingRating
	 */
	public void communicatingratingInsert() {
		try {
				 service.create(this.communicatingrating);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 communicatingratingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CommunicatingRating in database.
	 *
	 * @author TechFinium
	 * @see    CommunicatingRating
	 */
    public void communicatingratingUpdate() {
		try {
				 service.update(this.communicatingrating);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 communicatingratingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CommunicatingRating from database.
	 *
	 * @author TechFinium
	 * @see    CommunicatingRating
	 */
	public void communicatingratingDelete() {
		try {
			 service.delete(this.communicatingrating);
			  prepareNew();
			 communicatingratingInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CommunicatingRating .
	 *
	 * @author TechFinium
	 * @see    CommunicatingRating
	 */
	public void prepareNew() {
		communicatingrating = new CommunicatingRating();
	}

/*
    public List<SelectItem> getCommunicatingRatingDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	communicatingratingInfo();
    	for (CommunicatingRating ug : communicatingratingList) {
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
    public List<CommunicatingRating> complete(String desc) {
		List<CommunicatingRating> l = null;
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
     * Gets the communicating rating list.
     *
     * @return the communicating rating list
     */
    public List<CommunicatingRating> getCommunicatingRatingList() {
		return communicatingratingList;
	}
	
	/**
	 * Sets the communicating rating list.
	 *
	 * @param communicatingratingList the new communicating rating list
	 */
	public void setCommunicatingRatingList(List<CommunicatingRating> communicatingratingList) {
		this.communicatingratingList = communicatingratingList;
	}
	
	/**
	 * Gets the communicatingrating.
	 *
	 * @return the communicatingrating
	 */
	public CommunicatingRating getCommunicatingrating() {
		return communicatingrating;
	}
	
	/**
	 * Sets the communicatingrating.
	 *
	 * @param communicatingrating the new communicatingrating
	 */
	public void setCommunicatingrating(CommunicatingRating communicatingrating) {
		this.communicatingrating = communicatingrating;
	}

    /**
     * Gets the communicating ratingfiltered list.
     *
     * @return the communicating ratingfiltered list
     */
    public List<CommunicatingRating> getCommunicatingRatingfilteredList() {
		return communicatingratingfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param communicatingratingfilteredList the new communicatingratingfilteredList list
	 * @see    CommunicatingRating
	 */	
	public void setCommunicatingRatingfilteredList(List<CommunicatingRating> communicatingratingfilteredList) {
		this.communicatingratingfilteredList = communicatingratingfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<CommunicatingRating> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<CommunicatingRating> dataModel) {
		this.dataModel = dataModel;
	}
	
}
