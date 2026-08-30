package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ModulesTitle;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ModulesTitleService;

@ManagedBean(name = "modulestitleUI")
@ViewScoped
public class ModulesTitleUI extends AbstractUI {

	private ModulesTitleService service = new ModulesTitleService();
	private List<ModulesTitle> modulestitleList = null;
	private List<ModulesTitle> modulestitlefilteredList = null;
	private ModulesTitle modulestitle = null;
	private LazyDataModel<ModulesTitle> dataModel; 

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
	 * Initialize method to get all ModulesTitle and prepare a for a create of a new ModulesTitle
 	 * @author TechFinium 
 	 * @see    ModulesTitle
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		modulestitleInfo();
	}

	/**
	 * Get all ModulesTitle for data table
 	 * @author TechFinium 
 	 * @see    ModulesTitle
 	 */
	public void modulestitleInfo() {
	 
			
			 dataModel = new LazyDataModel<ModulesTitle>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ModulesTitle> retorno = new  ArrayList<ModulesTitle>();
			   
			   @Override 
			   public List<ModulesTitle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allModulesTitle(ModulesTitle.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ModulesTitle.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ModulesTitle obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ModulesTitle getRowData(String rowKey) {
			        for(ModulesTitle obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ModulesTitle into database
 	 * @author TechFinium 
 	 * @see    ModulesTitle
 	 */
	public void modulestitleInsert() {
		try {
				 service.create(this.modulestitle);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 modulestitleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ModulesTitle in database
 	 * @author TechFinium 
 	 * @see    ModulesTitle
 	 */
    public void modulestitleUpdate() {
		try {
				 service.update(this.modulestitle);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 modulestitleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ModulesTitle from database
 	 * @author TechFinium 
 	 * @see    ModulesTitle
 	 */
	public void modulestitleDelete() {
		try {
			 service.delete(this.modulestitle);
			  prepareNew();
			 modulestitleInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ModulesTitle 
 	 * @author TechFinium 
 	 * @see    ModulesTitle
 	 */
	public void prepareNew() {
		modulestitle = new ModulesTitle();
	}

/*
    public List<SelectItem> getModulesTitleDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	modulestitleInfo();
    	for (ModulesTitle ug : modulestitleList) {
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
    public List<ModulesTitle> complete(String desc) {
		List<ModulesTitle> l = null;
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
    
    public List<ModulesTitle> getModulesTitleList() {
		return modulestitleList;
	}
	public void setModulesTitleList(List<ModulesTitle> modulestitleList) {
		this.modulestitleList = modulestitleList;
	}
	public ModulesTitle getModulestitle() {
		return modulestitle;
	}
	public void setModulestitle(ModulesTitle modulestitle) {
		this.modulestitle = modulestitle;
	}

    public List<ModulesTitle> getModulesTitlefilteredList() {
		return modulestitlefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param modulestitlefilteredList the new modulestitlefilteredList list
 	 * @see    ModulesTitle
 	 */	
	public void setModulesTitlefilteredList(List<ModulesTitle> modulestitlefilteredList) {
		this.modulestitlefilteredList = modulestitlefilteredList;
	}

	
	public LazyDataModel<ModulesTitle> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ModulesTitle> dataModel) {
		this.dataModel = dataModel;
	}
	
}
