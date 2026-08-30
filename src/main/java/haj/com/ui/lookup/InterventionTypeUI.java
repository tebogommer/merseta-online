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
 * The Class InterventionTypeUI.
 */
@ManagedBean(name = "interventiontypeUI")
@ViewScoped
public class InterventionTypeUI extends AbstractUI {

	/** The service. */
	private InterventionTypeService service = new InterventionTypeService();
	
	/** The interventiontype list. */
	private List<InterventionType> interventiontypeList = null;
	
	/** The interventiontypefiltered list. */
	private List<InterventionType> interventiontypefilteredList = null;
	
	/** The interventiontype. */
	private InterventionType interventiontype = null;
	
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
		interventiontypeInfo();
	}

	/**
	 * Get all InterventionType for data table.
	 *
	 * @author TechFinium
	 * @see    InterventionType
	 */
	public void interventiontypeInfo() {
	 
			
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
	public void interventiontypeInsert() {
		try {
				 service.create(this.interventiontype);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 interventiontypeInfo();
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
    public void interventiontypeUpdate() {
		try {
				 service.update(this.interventiontype);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 interventiontypeInfo();
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
	public void interventiontypeDelete() {
		try {
			 service.delete(this.interventiontype);
			  prepareNew();
			 interventiontypeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
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
		interventiontype = new InterventionType();
	}

/*
    public List<SelectItem> getInterventionTypeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	interventiontypeInfo();
    	for (InterventionType ug : interventiontypeList) {
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
     * Gets the intervention type list.
     *
     * @return the intervention type list
     */
    public List<InterventionType> getInterventionTypeList() {
		return interventiontypeList;
	}
	
	/**
	 * Sets the intervention type list.
	 *
	 * @param interventiontypeList the new intervention type list
	 */
	public void setInterventionTypeList(List<InterventionType> interventiontypeList) {
		this.interventiontypeList = interventiontypeList;
	}
	
	/**
	 * Gets the interventiontype.
	 *
	 * @return the interventiontype
	 */
	public InterventionType getInterventiontype() {
		return interventiontype;
	}
	
	/**
	 * Sets the interventiontype.
	 *
	 * @param interventiontype the new interventiontype
	 */
	public void setInterventiontype(InterventionType interventiontype) {
		this.interventiontype = interventiontype;
	}

    /**
     * Gets the intervention typefiltered list.
     *
     * @return the intervention typefiltered list
     */
    public List<InterventionType> getInterventionTypefilteredList() {
		return interventiontypefilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param interventiontypefilteredList the new interventiontypefilteredList list
	 * @see    InterventionType
	 */	
	public void setInterventionTypefilteredList(List<InterventionType> interventiontypefilteredList) {
		this.interventiontypefilteredList = interventiontypefilteredList;
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
