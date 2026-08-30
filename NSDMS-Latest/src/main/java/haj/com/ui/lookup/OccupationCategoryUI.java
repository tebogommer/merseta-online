package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.OccupationCategory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.OccupationCategoryService;

// TODO: Auto-generated Javadoc
/**
 * The Class OccupationCategoryUI.
 */
@ManagedBean(name = "occupationcategoryUI")
@ViewScoped
public class OccupationCategoryUI extends AbstractUI {

	/** The service. */
	private OccupationCategoryService service = new OccupationCategoryService();
	
	/** The occupationcategory list. */
	private List<OccupationCategory> occupationcategoryList = null;
	
	/** The occupationcategoryfiltered list. */
	private List<OccupationCategory> occupationcategoryfilteredList = null;
	
	/** The occupationcategory. */
	private OccupationCategory occupationcategory = null;
	
	/** The data model. */
	private LazyDataModel<OccupationCategory> dataModel; 

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
	 * Initialize method to get all OccupationCategory and prepare a for a create of a new OccupationCategory.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    OccupationCategory
	 */
	private void runInit() throws Exception {
		prepareNew();
		occupationcategoryInfo();
	}

	/**
	 * Get all OccupationCategory for data table.
	 *
	 * @author TechFinium
	 * @see    OccupationCategory
	 */
	public void occupationcategoryInfo() {
	 
			
			 dataModel = new LazyDataModel<OccupationCategory>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<OccupationCategory> retorno = new  ArrayList<OccupationCategory>();
			   
			   @Override 
			   public List<OccupationCategory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allOccupationCategory(OccupationCategory.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(OccupationCategory.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(OccupationCategory obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public OccupationCategory getRowData(String rowKey) {
			        for(OccupationCategory obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert OccupationCategory into database.
	 *
	 * @author TechFinium
	 * @see    OccupationCategory
	 */
	public void occupationcategoryInsert() {
		try {
				 service.create(this.occupationcategory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 occupationcategoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update OccupationCategory in database.
	 *
	 * @author TechFinium
	 * @see    OccupationCategory
	 */
    public void occupationcategoryUpdate() {
		try {
				 service.update(this.occupationcategory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 occupationcategoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete OccupationCategory from database.
	 *
	 * @author TechFinium
	 * @see    OccupationCategory
	 */
	public void occupationcategoryDelete() {
		try {
			 service.delete(this.occupationcategory);
			  prepareNew();
			 occupationcategoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of OccupationCategory .
	 *
	 * @author TechFinium
	 * @see    OccupationCategory
	 */
	public void prepareNew() {
		occupationcategory = new OccupationCategory();
	}

/*
    public List<SelectItem> getOccupationCategoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	occupationcategoryInfo();
    	for (OccupationCategory ug : occupationcategoryList) {
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
    public List<OccupationCategory> complete(String desc) {
		List<OccupationCategory> l = null;
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
     * Gets the occupation category list.
     *
     * @return the occupation category list
     */
    public List<OccupationCategory> getOccupationCategoryList() {
		return occupationcategoryList;
	}
	
	/**
	 * Sets the occupation category list.
	 *
	 * @param occupationcategoryList the new occupation category list
	 */
	public void setOccupationCategoryList(List<OccupationCategory> occupationcategoryList) {
		this.occupationcategoryList = occupationcategoryList;
	}
	
	/**
	 * Gets the occupationcategory.
	 *
	 * @return the occupationcategory
	 */
	public OccupationCategory getOccupationcategory() {
		return occupationcategory;
	}
	
	/**
	 * Sets the occupationcategory.
	 *
	 * @param occupationcategory the new occupationcategory
	 */
	public void setOccupationcategory(OccupationCategory occupationcategory) {
		this.occupationcategory = occupationcategory;
	}

    /**
     * Gets the occupation categoryfiltered list.
     *
     * @return the occupation categoryfiltered list
     */
    public List<OccupationCategory> getOccupationCategoryfilteredList() {
		return occupationcategoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param occupationcategoryfilteredList the new occupationcategoryfilteredList list
	 * @see    OccupationCategory
	 */	
	public void setOccupationCategoryfilteredList(List<OccupationCategory> occupationcategoryfilteredList) {
		this.occupationcategoryfilteredList = occupationcategoryfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<OccupationCategory> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<OccupationCategory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
