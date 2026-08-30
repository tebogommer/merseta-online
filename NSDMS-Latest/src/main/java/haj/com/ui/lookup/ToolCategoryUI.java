package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ToolCategory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ToolCategoryService;

@ManagedBean(name = "toolcategoryUI")
@ViewScoped
public class ToolCategoryUI extends AbstractUI {

	private ToolCategoryService service = new ToolCategoryService();
	private List<ToolCategory> toolcategoryList = null;
	private List<ToolCategory> toolcategoryfilteredList = null;
	private ToolCategory toolcategory = null;
	private LazyDataModel<ToolCategory> dataModel; 

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
	 * Initialize method to get all ToolCategory and prepare a for a create of a new ToolCategory
 	 * @author TechFinium 
 	 * @see    ToolCategory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		toolcategoryInfo();
	}

	/**
	 * Get all ToolCategory for data table
 	 * @author TechFinium 
 	 * @see    ToolCategory
 	 */
	public void toolcategoryInfo() {
	 
			
			 dataModel = new LazyDataModel<ToolCategory>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ToolCategory> retorno = new  ArrayList<ToolCategory>();
			   
			   @Override 
			   public List<ToolCategory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allToolCategory(ToolCategory.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ToolCategory.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ToolCategory obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ToolCategory getRowData(String rowKey) {
			        for(ToolCategory obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ToolCategory into database
 	 * @author TechFinium 
 	 * @see    ToolCategory
 	 */
	public void toolcategoryInsert() {
		try {
				 service.create(this.toolcategory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 toolcategoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ToolCategory in database
 	 * @author TechFinium 
 	 * @see    ToolCategory
 	 */
    public void toolcategoryUpdate() {
		try {
				 service.update(this.toolcategory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 toolcategoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ToolCategory from database
 	 * @author TechFinium 
 	 * @see    ToolCategory
 	 */
	public void toolcategoryDelete() {
		try {
			 service.delete(this.toolcategory);
			  prepareNew();
			 toolcategoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ToolCategory 
 	 * @author TechFinium 
 	 * @see    ToolCategory
 	 */
	public void prepareNew() {
		toolcategory = new ToolCategory();
	}

/*
    public List<SelectItem> getToolCategoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	toolcategoryInfo();
    	for (ToolCategory ug : toolcategoryList) {
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
    public List<ToolCategory> complete(String desc) {
		List<ToolCategory> l = null;
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
    
    public List<ToolCategory> getToolCategoryList() {
		return toolcategoryList;
	}
	public void setToolCategoryList(List<ToolCategory> toolcategoryList) {
		this.toolcategoryList = toolcategoryList;
	}
	public ToolCategory getToolcategory() {
		return toolcategory;
	}
	public void setToolcategory(ToolCategory toolcategory) {
		this.toolcategory = toolcategory;
	}

    public List<ToolCategory> getToolCategoryfilteredList() {
		return toolcategoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param toolcategoryfilteredList the new toolcategoryfilteredList list
 	 * @see    ToolCategory
 	 */	
	public void setToolCategoryfilteredList(List<ToolCategory> toolcategoryfilteredList) {
		this.toolcategoryfilteredList = toolcategoryfilteredList;
	}

	
	public LazyDataModel<ToolCategory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ToolCategory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
