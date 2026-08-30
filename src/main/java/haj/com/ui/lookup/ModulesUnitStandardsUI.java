package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ModulesUnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ModulesUnitStandardsService;

@ManagedBean(name = "modulesunitstandardsUI")
@ViewScoped
public class ModulesUnitStandardsUI extends AbstractUI {

	private ModulesUnitStandardsService service = new ModulesUnitStandardsService();
	private List<ModulesUnitStandards> modulesunitstandardsList = null;
	private List<ModulesUnitStandards> modulesunitstandardsfilteredList = null;
	private ModulesUnitStandards modulesunitstandards = null;
	private LazyDataModel<ModulesUnitStandards> dataModel; 

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
	 * Initialize method to get all ModulesUnitStandards and prepare a for a create of a new ModulesUnitStandards
 	 * @author TechFinium 
 	 * @see    ModulesUnitStandards
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		modulesunitstandardsInfo();
	}

	/**
	 * Get all ModulesUnitStandards for data table
 	 * @author TechFinium 
 	 * @see    ModulesUnitStandards
 	 */
	public void modulesunitstandardsInfo() {
	 
			
			 dataModel = new LazyDataModel<ModulesUnitStandards>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ModulesUnitStandards> retorno = new  ArrayList<ModulesUnitStandards>();
			   
			   @Override 
			   public List<ModulesUnitStandards> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allModulesUnitStandards(ModulesUnitStandards.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ModulesUnitStandards.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ModulesUnitStandards obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ModulesUnitStandards getRowData(String rowKey) {
			        for(ModulesUnitStandards obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ModulesUnitStandards into database
 	 * @author TechFinium 
 	 * @see    ModulesUnitStandards
 	 */
	public void modulesunitstandardsInsert() {
		try {
				 service.create(this.modulesunitstandards);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 modulesunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ModulesUnitStandards in database
 	 * @author TechFinium 
 	 * @see    ModulesUnitStandards
 	 */
    public void modulesunitstandardsUpdate() {
		try {
				 service.update(this.modulesunitstandards);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 modulesunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ModulesUnitStandards from database
 	 * @author TechFinium 
 	 * @see    ModulesUnitStandards
 	 */
	public void modulesunitstandardsDelete() {
		try {
			 service.delete(this.modulesunitstandards);
			  prepareNew();
			 modulesunitstandardsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ModulesUnitStandards 
 	 * @author TechFinium 
 	 * @see    ModulesUnitStandards
 	 */
	public void prepareNew() {
		modulesunitstandards = new ModulesUnitStandards();
	}

/*
    public List<SelectItem> getModulesUnitStandardsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	modulesunitstandardsInfo();
    	for (ModulesUnitStandards ug : modulesunitstandardsList) {
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
    public List<ModulesUnitStandards> complete(String desc) {
		List<ModulesUnitStandards> l = null;
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
    
    public List<ModulesUnitStandards> getModulesUnitStandardsList() {
		return modulesunitstandardsList;
	}
	public void setModulesUnitStandardsList(List<ModulesUnitStandards> modulesunitstandardsList) {
		this.modulesunitstandardsList = modulesunitstandardsList;
	}
	public ModulesUnitStandards getModulesunitstandards() {
		return modulesunitstandards;
	}
	public void setModulesunitstandards(ModulesUnitStandards modulesunitstandards) {
		this.modulesunitstandards = modulesunitstandards;
	}

    public List<ModulesUnitStandards> getModulesUnitStandardsfilteredList() {
		return modulesunitstandardsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param modulesunitstandardsfilteredList the new modulesunitstandardsfilteredList list
 	 * @see    ModulesUnitStandards
 	 */	
	public void setModulesUnitStandardsfilteredList(List<ModulesUnitStandards> modulesunitstandardsfilteredList) {
		this.modulesunitstandardsfilteredList = modulesunitstandardsfilteredList;
	}

	
	public LazyDataModel<ModulesUnitStandards> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ModulesUnitStandards> dataModel) {
		this.dataModel = dataModel;
	}
	
}
