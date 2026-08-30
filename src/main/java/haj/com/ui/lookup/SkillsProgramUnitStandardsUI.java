package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SkillsProgramUnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SkillsProgramUnitStandardsService;

@ManagedBean(name = "skillsprogramunitstandardsUI")
@ViewScoped
public class SkillsProgramUnitStandardsUI extends AbstractUI {

	private SkillsProgramUnitStandardsService service = new SkillsProgramUnitStandardsService();
	private List<SkillsProgramUnitStandards> skillsprogramunitstandardsList = null;
	private List<SkillsProgramUnitStandards> skillsprogramunitstandardsfilteredList = null;
	private SkillsProgramUnitStandards skillsprogramunitstandards = null;
	private LazyDataModel<SkillsProgramUnitStandards> dataModel; 

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
	 * Initialize method to get all SkillsProgramUnitStandards and prepare a for a create of a new SkillsProgramUnitStandards
 	 * @author TechFinium 
 	 * @see    SkillsProgramUnitStandards
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		skillsprogramunitstandardsInfo();
	}

	/**
	 * Get all SkillsProgramUnitStandards for data table
 	 * @author TechFinium 
 	 * @see    SkillsProgramUnitStandards
 	 */
	public void skillsprogramunitstandardsInfo() {
	 
			
			 dataModel = new LazyDataModel<SkillsProgramUnitStandards>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SkillsProgramUnitStandards> retorno = new  ArrayList<SkillsProgramUnitStandards>();
			   
			   @Override 
			   public List<SkillsProgramUnitStandards> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSkillsProgramUnitStandards(SkillsProgramUnitStandards.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SkillsProgramUnitStandards.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SkillsProgramUnitStandards obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SkillsProgramUnitStandards getRowData(String rowKey) {
			        for(SkillsProgramUnitStandards obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SkillsProgramUnitStandards into database
 	 * @author TechFinium 
 	 * @see    SkillsProgramUnitStandards
 	 */
	public void skillsprogramunitstandardsInsert() {
		try {
				 service.create(this.skillsprogramunitstandards);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillsprogramunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SkillsProgramUnitStandards in database
 	 * @author TechFinium 
 	 * @see    SkillsProgramUnitStandards
 	 */
    public void skillsprogramunitstandardsUpdate() {
		try {
				 service.update(this.skillsprogramunitstandards);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillsprogramunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SkillsProgramUnitStandards from database
 	 * @author TechFinium 
 	 * @see    SkillsProgramUnitStandards
 	 */
	public void skillsprogramunitstandardsDelete() {
		try {
			 service.delete(this.skillsprogramunitstandards);
			  prepareNew();
			 skillsprogramunitstandardsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SkillsProgramUnitStandards 
 	 * @author TechFinium 
 	 * @see    SkillsProgramUnitStandards
 	 */
	public void prepareNew() {
		skillsprogramunitstandards = new SkillsProgramUnitStandards();
	}

/*
    public List<SelectItem> getSkillsProgramUnitStandardsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	skillsprogramunitstandardsInfo();
    	for (SkillsProgramUnitStandards ug : skillsprogramunitstandardsList) {
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
    public List<SkillsProgramUnitStandards> complete(String desc) {
		List<SkillsProgramUnitStandards> l = null;
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
    
    public List<SkillsProgramUnitStandards> getSkillsProgramUnitStandardsList() {
		return skillsprogramunitstandardsList;
	}
	public void setSkillsProgramUnitStandardsList(List<SkillsProgramUnitStandards> skillsprogramunitstandardsList) {
		this.skillsprogramunitstandardsList = skillsprogramunitstandardsList;
	}
	public SkillsProgramUnitStandards getSkillsprogramunitstandards() {
		return skillsprogramunitstandards;
	}
	public void setSkillsprogramunitstandards(SkillsProgramUnitStandards skillsprogramunitstandards) {
		this.skillsprogramunitstandards = skillsprogramunitstandards;
	}

    public List<SkillsProgramUnitStandards> getSkillsProgramUnitStandardsfilteredList() {
		return skillsprogramunitstandardsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param skillsprogramunitstandardsfilteredList the new skillsprogramunitstandardsfilteredList list
 	 * @see    SkillsProgramUnitStandards
 	 */	
	public void setSkillsProgramUnitStandardsfilteredList(List<SkillsProgramUnitStandards> skillsprogramunitstandardsfilteredList) {
		this.skillsprogramunitstandardsfilteredList = skillsprogramunitstandardsfilteredList;
	}

	
	public LazyDataModel<SkillsProgramUnitStandards> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SkillsProgramUnitStandards> dataModel) {
		this.dataModel = dataModel;
	}
	
}
