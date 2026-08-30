package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ModulesCategory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ModulesCategoryService;

@ManagedBean(name = "modulescategoryUI")
@ViewScoped
public class ModulesCategoryUI extends AbstractUI {

	private ModulesCategoryService service = new ModulesCategoryService();
	private List<ModulesCategory> modulescategoryList = null;
	private List<ModulesCategory> modulescategoryfilteredList = null;
	private ModulesCategory modulescategory = null;
	private LazyDataModel<ModulesCategory> dataModel; 

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
	 * Initialize method to get all ModulesCategory and prepare a for a create of a new ModulesCategory
 	 * @author TechFinium 
 	 * @see    ModulesCategory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		modulescategoryInfo();
	}

	/**
	 * Get all ModulesCategory for data table
 	 * @author TechFinium 
 	 * @see    ModulesCategory
 	 */
	public void modulescategoryInfo() {
	 
			
			 dataModel = new LazyDataModel<ModulesCategory>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ModulesCategory> retorno = new  ArrayList<ModulesCategory>();
			   
			   @Override 
			   public List<ModulesCategory> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allModulesCategory(ModulesCategory.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ModulesCategory.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ModulesCategory obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ModulesCategory getRowData(String rowKey) {
			        for(ModulesCategory obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ModulesCategory into database
 	 * @author TechFinium 
 	 * @see    ModulesCategory
 	 */
	public void modulescategoryInsert() {
		try {
				 service.create(this.modulescategory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 modulescategoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ModulesCategory in database
 	 * @author TechFinium 
 	 * @see    ModulesCategory
 	 */
    public void modulescategoryUpdate() {
		try {
				 service.update(this.modulescategory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 modulescategoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ModulesCategory from database
 	 * @author TechFinium 
 	 * @see    ModulesCategory
 	 */
	public void modulescategoryDelete() {
		try {
			 service.delete(this.modulescategory);
			  prepareNew();
			 modulescategoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ModulesCategory 
 	 * @author TechFinium 
 	 * @see    ModulesCategory
 	 */
	public void prepareNew() {
		modulescategory = new ModulesCategory();
	}

/*
    public List<SelectItem> getModulesCategoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	modulescategoryInfo();
    	for (ModulesCategory ug : modulescategoryList) {
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
    public List<ModulesCategory> complete(String desc) {
		List<ModulesCategory> l = null;
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
    
    public List<ModulesCategory> getModulesCategoryList() {
		return modulescategoryList;
	}
	public void setModulesCategoryList(List<ModulesCategory> modulescategoryList) {
		this.modulescategoryList = modulescategoryList;
	}
	public ModulesCategory getModulescategory() {
		return modulescategory;
	}
	public void setModulescategory(ModulesCategory modulescategory) {
		this.modulescategory = modulescategory;
	}

    public List<ModulesCategory> getModulesCategoryfilteredList() {
		return modulescategoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param modulescategoryfilteredList the new modulescategoryfilteredList list
 	 * @see    ModulesCategory
 	 */	
	public void setModulesCategoryfilteredList(List<ModulesCategory> modulescategoryfilteredList) {
		this.modulescategoryfilteredList = modulescategoryfilteredList;
	}

	
	public LazyDataModel<ModulesCategory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ModulesCategory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
