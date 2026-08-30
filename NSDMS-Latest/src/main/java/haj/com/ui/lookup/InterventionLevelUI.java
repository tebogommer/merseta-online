package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.InterventionLevel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.InterventionLevelService;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionLevelUI.
 */
@ManagedBean(name = "interventionlevelUI")
@ViewScoped
public class InterventionLevelUI extends AbstractUI {

	/** The service. */
	private InterventionLevelService service = new InterventionLevelService();
	
	/** The interventionlevel list. */
	private List<InterventionLevel> interventionlevelList = null;
	
	/** The interventionlevelfiltered list. */
	private List<InterventionLevel> interventionlevelfilteredList = null;
	
	/** The interventionlevel. */
	private InterventionLevel interventionlevel = null;
	
	/** The data model. */
	private LazyDataModel<InterventionLevel> dataModel; 

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
	 * Initialize method to get all InterventionLevel and prepare a for a create of a new InterventionLevel.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    InterventionLevel
	 */
	private void runInit() throws Exception {
		prepareNew();
		interventionlevelInfo();
	}

	/**
	 * Get all InterventionLevel for data table.
	 *
	 * @author TechFinium
	 * @see    InterventionLevel
	 */
	public void interventionlevelInfo() {
	 
			
			 dataModel = new LazyDataModel<InterventionLevel>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<InterventionLevel> retorno = new  ArrayList<InterventionLevel>();
			   
			   @Override 
			   public List<InterventionLevel> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allInterventionLevel(InterventionLevel.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(InterventionLevel.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(InterventionLevel obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public InterventionLevel getRowData(String rowKey) {
			        for(InterventionLevel obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert InterventionLevel into database.
	 *
	 * @author TechFinium
	 * @see    InterventionLevel
	 */
	public void interventionlevelInsert() {
		try {
				 service.create(this.interventionlevel);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 interventionlevelInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update InterventionLevel in database.
	 *
	 * @author TechFinium
	 * @see    InterventionLevel
	 */
    public void interventionlevelUpdate() {
		try {
				 service.update(this.interventionlevel);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 interventionlevelInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete InterventionLevel from database.
	 *
	 * @author TechFinium
	 * @see    InterventionLevel
	 */
	public void interventionlevelDelete() {
		try {
			 service.delete(this.interventionlevel);
			  prepareNew();
			 interventionlevelInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of InterventionLevel .
	 *
	 * @author TechFinium
	 * @see    InterventionLevel
	 */
	public void prepareNew() {
		interventionlevel = new InterventionLevel();
	}

/*
    public List<SelectItem> getInterventionLevelDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	interventionlevelInfo();
    	for (InterventionLevel ug : interventionlevelList) {
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
    public List<InterventionLevel> complete(String desc) {
		List<InterventionLevel> l = null;
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
     * Gets the intervention level list.
     *
     * @return the intervention level list
     */
    public List<InterventionLevel> getInterventionLevelList() {
		return interventionlevelList;
	}
	
	/**
	 * Sets the intervention level list.
	 *
	 * @param interventionlevelList the new intervention level list
	 */
	public void setInterventionLevelList(List<InterventionLevel> interventionlevelList) {
		this.interventionlevelList = interventionlevelList;
	}
	
	/**
	 * Gets the interventionlevel.
	 *
	 * @return the interventionlevel
	 */
	public InterventionLevel getInterventionlevel() {
		return interventionlevel;
	}
	
	/**
	 * Sets the interventionlevel.
	 *
	 * @param interventionlevel the new interventionlevel
	 */
	public void setInterventionlevel(InterventionLevel interventionlevel) {
		this.interventionlevel = interventionlevel;
	}

    /**
     * Gets the intervention levelfiltered list.
     *
     * @return the intervention levelfiltered list
     */
    public List<InterventionLevel> getInterventionLevelfilteredList() {
		return interventionlevelfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param interventionlevelfilteredList the new interventionlevelfilteredList list
	 * @see    InterventionLevel
	 */	
	public void setInterventionLevelfilteredList(List<InterventionLevel> interventionlevelfilteredList) {
		this.interventionlevelfilteredList = interventionlevelfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<InterventionLevel> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<InterventionLevel> dataModel) {
		this.dataModel = dataModel;
	}
	
}
