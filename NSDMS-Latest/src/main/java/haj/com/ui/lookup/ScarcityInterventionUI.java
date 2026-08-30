package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.InterventionType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.InterventionTypeService;

// TODO: Auto-generated Javadoc
/**
 * The Class ScarcityInterventionUI.
 */
@ManagedBean(name = "scarcityinterventionUI")
@ViewScoped
public class ScarcityInterventionUI extends AbstractUI {

	/** The service. */
	private InterventionTypeService service = new InterventionTypeService();
	
	/** The scarcityintervention list. */
	private List<InterventionType> scarcityinterventionList = null;
	
	/** The scarcityinterventionfiltered list. */
	private List<InterventionType> scarcityinterventionfilteredList = null;
	
	/** The scarcityintervention. */
	private InterventionType scarcityintervention = null;
	
	/** The data model. */
	private LazyDataModel<InterventionType> dataModel; 

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
	 * Initialize method to get all InterventionType and prepare a for a create of a new InterventionType.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    InterventionType
	 */
	private void runInit() throws Exception {
		prepareNew();
		scarcityinterventionInfo();
	}

	/**
	 * Get all InterventionType for data table.
	 *
	 * @author TechFinium
	 * @see    InterventionType
	 */
	public void scarcityinterventionInfo() {
	 
			
			 dataModel = new LazyDataModel<InterventionType>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<InterventionType> retorno = new  ArrayList<InterventionType>();
			   
			   @Override 
			   public List<InterventionType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allInterventionType(InterventionType.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(InterventionType.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(InterventionType obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public InterventionType getRowData(String rowKey) {
			        for(InterventionType obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert InterventionType into database.
	 *
	 * @author TechFinium
	 * @see    InterventionType
	 */
	public void scarcityinterventionInsert() {
		try {
				 service.create(this.scarcityintervention);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 scarcityinterventionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update InterventionType in database.
	 *
	 * @author TechFinium
	 * @see    InterventionType
	 */
    public void scarcityinterventionUpdate() {
		try {
				 service.update(this.scarcityintervention);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 scarcityinterventionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete InterventionType from database.
	 *
	 * @author TechFinium
	 * @see    InterventionType
	 */
	public void scarcityinterventionDelete() {
		try {
			 service.delete(this.scarcityintervention);
			  prepareNew();
			 scarcityinterventionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of InterventionType .
	 *
	 * @author TechFinium
	 * @see    InterventionType
	 */
	public void prepareNew() {
		scarcityintervention = new InterventionType();
	}

/*
    public List<SelectItem> getScarcityInterventionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	scarcityinterventionInfo();
    	for (InterventionType ug : scarcityinterventionList) {
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
    public List<InterventionType> complete(String desc) {
		List<InterventionType> l = null;
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
     * Gets the scarcity intervention list.
     *
     * @return the scarcity intervention list
     */
    public List<InterventionType> getScarcityInterventionList() {
		return scarcityinterventionList;
	}
	
	/**
	 * Sets the scarcity intervention list.
	 *
	 * @param scarcityinterventionList the new scarcity intervention list
	 */
	public void setScarcityInterventionList(List<InterventionType> scarcityinterventionList) {
		this.scarcityinterventionList = scarcityinterventionList;
	}
	
	/**
	 * Gets the scarcityintervention.
	 *
	 * @return the scarcityintervention
	 */
	public InterventionType getScarcityintervention() {
		return scarcityintervention;
	}
	
	/**
	 * Sets the scarcityintervention.
	 *
	 * @param scarcityintervention the new scarcityintervention
	 */
	public void setScarcityintervention(InterventionType scarcityintervention) {
		this.scarcityintervention = scarcityintervention;
	}

    /**
     * Gets the scarcity interventionfiltered list.
     *
     * @return the scarcity interventionfiltered list
     */
    public List<InterventionType> getScarcityInterventionfilteredList() {
		return scarcityinterventionfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param scarcityinterventionfilteredList the new scarcityinterventionfilteredList list
	 * @see    InterventionType
	 */	
	public void setScarcityInterventionfilteredList(List<InterventionType> scarcityinterventionfilteredList) {
		this.scarcityinterventionfilteredList = scarcityinterventionfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<InterventionType> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<InterventionType> dataModel) {
		this.dataModel = dataModel;
	}
	
}
