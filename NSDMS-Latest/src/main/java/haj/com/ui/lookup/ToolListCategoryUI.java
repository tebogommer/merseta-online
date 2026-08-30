package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ToolListCategory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ToolListCategoryService;

@ManagedBean(name = "toollistcategoryUI")
@ViewScoped
public class ToolListCategoryUI extends AbstractUI {

	private ToolListCategoryService service = new ToolListCategoryService();
	private List<ToolListCategory> toollistcategoryList = null;
	private List<ToolListCategory> toollistcategoryfilteredList = null;
	private ToolListCategory toollistcategory = null;
	private LazyDataModel<ToolListCategory> dataModel; 

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
	 * Initialize method to get all ToolListCategory and prepare a for a create of a new ToolListCategory
 	 * @author TechFinium 
 	 * @see    ToolListCategory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		toollistcategoryInfo();
	}

	/**
	 * Get all ToolListCategory for data table
 	 * @author TechFinium 
 	 * @see    ToolListCategory
 	 */
	public void toollistcategoryInfo() {
	 
			
			 dataModel = new LazyDataModel<ToolListCategory>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ToolListCategory> retorno = new  ArrayList<ToolListCategory>();
			   
			   @Override 
			   public List<ToolListCategory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allToolListCategory(ToolListCategory.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ToolListCategory.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ToolListCategory obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ToolListCategory getRowData(String rowKey) {
			        for(ToolListCategory obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ToolListCategory into database
 	 * @author TechFinium 
 	 * @see    ToolListCategory
 	 */
	public void toollistcategoryInsert() {
		try {
				 service.create(this.toollistcategory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 toollistcategoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ToolListCategory in database
 	 * @author TechFinium 
 	 * @see    ToolListCategory
 	 */
    public void toollistcategoryUpdate() {
		try {
				 service.update(this.toollistcategory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 toollistcategoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ToolListCategory from database
 	 * @author TechFinium 
 	 * @see    ToolListCategory
 	 */
	public void toollistcategoryDelete() {
		try {
			 service.delete(this.toollistcategory);
			  prepareNew();
			 toollistcategoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ToolListCategory 
 	 * @author TechFinium 
 	 * @see    ToolListCategory
 	 */
	public void prepareNew() {
		toollistcategory = new ToolListCategory();
	}

/*
    public List<SelectItem> getToolListCategoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	toollistcategoryInfo();
    	for (ToolListCategory ug : toollistcategoryList) {
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
    public List<ToolListCategory> complete(String desc) {
		List<ToolListCategory> l = null;
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
    
    public List<ToolListCategory> getToolListCategoryList() {
		return toollistcategoryList;
	}
	public void setToolListCategoryList(List<ToolListCategory> toollistcategoryList) {
		this.toollistcategoryList = toollistcategoryList;
	}
	public ToolListCategory getToollistcategory() {
		return toollistcategory;
	}
	public void setToollistcategory(ToolListCategory toollistcategory) {
		this.toollistcategory = toollistcategory;
	}

    public List<ToolListCategory> getToolListCategoryfilteredList() {
		return toollistcategoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param toollistcategoryfilteredList the new toollistcategoryfilteredList list
 	 * @see    ToolListCategory
 	 */	
	public void setToolListCategoryfilteredList(List<ToolListCategory> toollistcategoryfilteredList) {
		this.toollistcategoryfilteredList = toollistcategoryfilteredList;
	}

	
	public LazyDataModel<ToolListCategory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ToolListCategory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
