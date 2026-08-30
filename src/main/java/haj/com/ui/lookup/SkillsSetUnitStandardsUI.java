package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SkillsSetUnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SkillsSetUnitStandardsService;

@ManagedBean(name = "skillssetunitstandardsUI")
@ViewScoped
public class SkillsSetUnitStandardsUI extends AbstractUI {

	private SkillsSetUnitStandardsService service = new SkillsSetUnitStandardsService();
	private List<SkillsSetUnitStandards> skillssetunitstandardsList = null;
	private List<SkillsSetUnitStandards> skillssetunitstandardsfilteredList = null;
	private SkillsSetUnitStandards skillssetunitstandards = null;
	private LazyDataModel<SkillsSetUnitStandards> dataModel; 

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
	 * Initialize method to get all SkillsSetUnitStandards and prepare a for a create of a new SkillsSetUnitStandards
 	 * @author TechFinium 
 	 * @see    SkillsSetUnitStandards
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		skillssetunitstandardsInfo();
	}

	/**
	 * Get all SkillsSetUnitStandards for data table
 	 * @author TechFinium 
 	 * @see    SkillsSetUnitStandards
 	 */
	public void skillssetunitstandardsInfo() {
	 
			
			 dataModel = new LazyDataModel<SkillsSetUnitStandards>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SkillsSetUnitStandards> retorno = new  ArrayList<SkillsSetUnitStandards>();
			   
			   @Override 
			   public List<SkillsSetUnitStandards> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSkillsSetUnitStandards(SkillsSetUnitStandards.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SkillsSetUnitStandards.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SkillsSetUnitStandards obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SkillsSetUnitStandards getRowData(String rowKey) {
			        for(SkillsSetUnitStandards obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SkillsSetUnitStandards into database
 	 * @author TechFinium 
 	 * @see    SkillsSetUnitStandards
 	 */
	public void skillssetunitstandardsInsert() {
		try {
				 service.create(this.skillssetunitstandards);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillssetunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SkillsSetUnitStandards in database
 	 * @author TechFinium 
 	 * @see    SkillsSetUnitStandards
 	 */
    public void skillssetunitstandardsUpdate() {
		try {
				 service.update(this.skillssetunitstandards);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillssetunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SkillsSetUnitStandards from database
 	 * @author TechFinium 
 	 * @see    SkillsSetUnitStandards
 	 */
	public void skillssetunitstandardsDelete() {
		try {
			 service.delete(this.skillssetunitstandards);
			  prepareNew();
			 skillssetunitstandardsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SkillsSetUnitStandards 
 	 * @author TechFinium 
 	 * @see    SkillsSetUnitStandards
 	 */
	public void prepareNew() {
		skillssetunitstandards = new SkillsSetUnitStandards();
	}

/*
    public List<SelectItem> getSkillsSetUnitStandardsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	skillssetunitstandardsInfo();
    	for (SkillsSetUnitStandards ug : skillssetunitstandardsList) {
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
    public List<SkillsSetUnitStandards> complete(String desc) {
		List<SkillsSetUnitStandards> l = null;
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
    
    public List<SkillsSetUnitStandards> getSkillsSetUnitStandardsList() {
		return skillssetunitstandardsList;
	}
	public void setSkillsSetUnitStandardsList(List<SkillsSetUnitStandards> skillssetunitstandardsList) {
		this.skillssetunitstandardsList = skillssetunitstandardsList;
	}
	public SkillsSetUnitStandards getSkillssetunitstandards() {
		return skillssetunitstandards;
	}
	public void setSkillssetunitstandards(SkillsSetUnitStandards skillssetunitstandards) {
		this.skillssetunitstandards = skillssetunitstandards;
	}

    public List<SkillsSetUnitStandards> getSkillsSetUnitStandardsfilteredList() {
		return skillssetunitstandardsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param skillssetunitstandardsfilteredList the new skillssetunitstandardsfilteredList list
 	 * @see    SkillsSetUnitStandards
 	 */	
	public void setSkillsSetUnitStandardsfilteredList(List<SkillsSetUnitStandards> skillssetunitstandardsfilteredList) {
		this.skillssetunitstandardsfilteredList = skillssetunitstandardsfilteredList;
	}

	
	public LazyDataModel<SkillsSetUnitStandards> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SkillsSetUnitStandards> dataModel) {
		this.dataModel = dataModel;
	}
	
}
