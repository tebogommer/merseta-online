package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.SkillsRegistrationUnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SkillsRegistrationUnitStandardsService;

@ManagedBean(name = "skillsregistrationunitstandardsUI")
@ViewScoped
public class SkillsRegistrationUnitStandardsUI extends AbstractUI {

	private SkillsRegistrationUnitStandardsService service = new SkillsRegistrationUnitStandardsService();
	private List<SkillsRegistrationUnitStandards> skillsregistrationunitstandardsList = null;
	private List<SkillsRegistrationUnitStandards> skillsregistrationunitstandardsfilteredList = null;
	private SkillsRegistrationUnitStandards skillsregistrationunitstandards = null;
	private LazyDataModel<SkillsRegistrationUnitStandards> dataModel; 

    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all SkillsRegistrationUnitStandards and prepare a for a create of a new SkillsRegistrationUnitStandards
 	 * @author TechFinium 
 	 * @see    SkillsRegistrationUnitStandards
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		skillsregistrationunitstandardsInfo();
	}

	/**
	 * Get all SkillsRegistrationUnitStandards for data table
 	 * @author TechFinium 
 	 * @see    SkillsRegistrationUnitStandards
 	 */
	public void skillsregistrationunitstandardsInfo() {
	 
			
			 dataModel = new LazyDataModel<SkillsRegistrationUnitStandards>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SkillsRegistrationUnitStandards> retorno = new  ArrayList<SkillsRegistrationUnitStandards>();
			   
			   @Override 
			   public List<SkillsRegistrationUnitStandards> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSkillsRegistrationUnitStandards(SkillsRegistrationUnitStandards.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SkillsRegistrationUnitStandards.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SkillsRegistrationUnitStandards obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SkillsRegistrationUnitStandards getRowData(String rowKey) {
			        for(SkillsRegistrationUnitStandards obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SkillsRegistrationUnitStandards into database
 	 * @author TechFinium 
 	 * @see    SkillsRegistrationUnitStandards
 	 */
	public void skillsregistrationunitstandardsInsert() {
		try {
				 service.create(this.skillsregistrationunitstandards);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillsregistrationunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SkillsRegistrationUnitStandards in database
 	 * @author TechFinium 
 	 * @see    SkillsRegistrationUnitStandards
 	 */
    public void skillsregistrationunitstandardsUpdate() {
		try {
				 service.update(this.skillsregistrationunitstandards);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillsregistrationunitstandardsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SkillsRegistrationUnitStandards from database
 	 * @author TechFinium 
 	 * @see    SkillsRegistrationUnitStandards
 	 */
	public void skillsregistrationunitstandardsDelete() {
		try {
			 service.delete(this.skillsregistrationunitstandards);
			  prepareNew();
			 skillsregistrationunitstandardsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SkillsRegistrationUnitStandards 
 	 * @author TechFinium 
 	 * @see    SkillsRegistrationUnitStandards
 	 */
	public void prepareNew() {
		skillsregistrationunitstandards = new SkillsRegistrationUnitStandards();
	}

/*
    public List<SelectItem> getSkillsRegistrationUnitStandardsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	skillsregistrationunitstandardsInfo();
    	for (SkillsRegistrationUnitStandards ug : skillsregistrationunitstandardsList) {
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
    public List<SkillsRegistrationUnitStandards> complete(String desc) {
		List<SkillsRegistrationUnitStandards> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<SkillsRegistrationUnitStandards> getSkillsRegistrationUnitStandardsList() {
		return skillsregistrationunitstandardsList;
	}
	public void setSkillsRegistrationUnitStandardsList(List<SkillsRegistrationUnitStandards> skillsregistrationunitstandardsList) {
		this.skillsregistrationunitstandardsList = skillsregistrationunitstandardsList;
	}
	public SkillsRegistrationUnitStandards getSkillsregistrationunitstandards() {
		return skillsregistrationunitstandards;
	}
	public void setSkillsregistrationunitstandards(SkillsRegistrationUnitStandards skillsregistrationunitstandards) {
		this.skillsregistrationunitstandards = skillsregistrationunitstandards;
	}

    public List<SkillsRegistrationUnitStandards> getSkillsRegistrationUnitStandardsfilteredList() {
		return skillsregistrationunitstandardsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param skillsregistrationunitstandardsfilteredList the new skillsregistrationunitstandardsfilteredList list
 	 * @see    SkillsRegistrationUnitStandards
 	 */	
	public void setSkillsRegistrationUnitStandardsfilteredList(List<SkillsRegistrationUnitStandards> skillsregistrationunitstandardsfilteredList) {
		this.skillsregistrationunitstandardsfilteredList = skillsregistrationunitstandardsfilteredList;
	}

	
	public LazyDataModel<SkillsRegistrationUnitStandards> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SkillsRegistrationUnitStandards> dataModel) {
		this.dataModel = dataModel;
	}
	
}
