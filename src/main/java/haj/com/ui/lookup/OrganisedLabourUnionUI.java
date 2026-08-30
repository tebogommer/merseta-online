package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.OrganisedLabourUnionService;

// TODO: Auto-generated Javadoc
/**
 * The Class OrganisedLabourUnionUI.
 */
@ManagedBean(name = "organisedlabourunionUI")
@ViewScoped
public class OrganisedLabourUnionUI extends AbstractUI {

	/** The service. */
	private OrganisedLabourUnionService service = new OrganisedLabourUnionService();
	
	/** The organisedlabourunion list. */
	private List<OrganisedLabourUnion> organisedlabourunionList = null;
	
	/** The organisedlabourunionfiltered list. */
	private List<OrganisedLabourUnion> organisedlabourunionfilteredList = null;
	
	/** The organisedlabourunion. */
	private OrganisedLabourUnion organisedlabourunion = null;
	
	/** The data model. */
	private LazyDataModel<OrganisedLabourUnion> dataModel; 

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
	 * Initialize method to get all OrganisedLabourUnion and prepare a for a create of a new OrganisedLabourUnion.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    OrganisedLabourUnion
	 */
	private void runInit() throws Exception {
		prepareNew();
		organisedlabourunionInfo();
	}

	/**
	 * Get all OrganisedLabourUnion for data table.
	 *
	 * @author TechFinium
	 * @see    OrganisedLabourUnion
	 */
	public void organisedlabourunionInfo() {
	 
			
			 dataModel = new LazyDataModel<OrganisedLabourUnion>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<OrganisedLabourUnion> retorno = new  ArrayList<OrganisedLabourUnion>();
			   
			   @Override 
			   public List<OrganisedLabourUnion> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allOrganisedLabourUnion(OrganisedLabourUnion.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(OrganisedLabourUnion.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(OrganisedLabourUnion obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public OrganisedLabourUnion getRowData(String rowKey) {
			        for(OrganisedLabourUnion obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert OrganisedLabourUnion into database.
	 *
	 * @author TechFinium
	 * @see    OrganisedLabourUnion
	 */
	public void organisedlabourunionInsert() {
		try {
				 service.create(this.organisedlabourunion);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 organisedlabourunionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update OrganisedLabourUnion in database.
	 *
	 * @author TechFinium
	 * @see    OrganisedLabourUnion
	 */
    public void organisedlabourunionUpdate() {
		try {
				 service.update(this.organisedlabourunion);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 organisedlabourunionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete OrganisedLabourUnion from database.
	 *
	 * @author TechFinium
	 * @see    OrganisedLabourUnion
	 */
	public void organisedlabourunionDelete() {
		try {
			 service.delete(this.organisedlabourunion);
			  prepareNew();
			 organisedlabourunionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of OrganisedLabourUnion .
	 *
	 * @author TechFinium
	 * @see    OrganisedLabourUnion
	 */
	public void prepareNew() {
		organisedlabourunion = new OrganisedLabourUnion();
	}

/*
    public List<SelectItem> getOrganisedLabourUnionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	organisedlabourunionInfo();
    	for (OrganisedLabourUnion ug : organisedlabourunionList) {
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
    public List<OrganisedLabourUnion> complete(String desc) {
		List<OrganisedLabourUnion> l = null;
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
     * Gets the organised labour union list.
     *
     * @return the organised labour union list
     */
    public List<OrganisedLabourUnion> getOrganisedLabourUnionList() {
		return organisedlabourunionList;
	}
	
	/**
	 * Sets the organised labour union list.
	 *
	 * @param organisedlabourunionList the new organised labour union list
	 */
	public void setOrganisedLabourUnionList(List<OrganisedLabourUnion> organisedlabourunionList) {
		this.organisedlabourunionList = organisedlabourunionList;
	}
	
	/**
	 * Gets the organisedlabourunion.
	 *
	 * @return the organisedlabourunion
	 */
	public OrganisedLabourUnion getOrganisedlabourunion() {
		return organisedlabourunion;
	}
	
	/**
	 * Sets the organisedlabourunion.
	 *
	 * @param organisedlabourunion the new organisedlabourunion
	 */
	public void setOrganisedlabourunion(OrganisedLabourUnion organisedlabourunion) {
		this.organisedlabourunion = organisedlabourunion;
	}

    /**
     * Gets the organised labour unionfiltered list.
     *
     * @return the organised labour unionfiltered list
     */
    public List<OrganisedLabourUnion> getOrganisedLabourUnionfilteredList() {
		return organisedlabourunionfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param organisedlabourunionfilteredList the new organisedlabourunionfilteredList list
	 * @see    OrganisedLabourUnion
	 */	
	public void setOrganisedLabourUnionfilteredList(List<OrganisedLabourUnion> organisedlabourunionfilteredList) {
		this.organisedlabourunionfilteredList = organisedlabourunionfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<OrganisedLabourUnion> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<OrganisedLabourUnion> dataModel) {
		this.dataModel = dataModel;
	}
	
}
