package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SkillsIdentification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SkillsIdentificationService;

@ManagedBean(name = "skillsidentificationUI")
@ViewScoped
public class SkillsIdentificationUI extends AbstractUI {

	private SkillsIdentificationService service = new SkillsIdentificationService();
	private List<SkillsIdentification> skillsidentificationList = null;
	private List<SkillsIdentification> skillsidentificationfilteredList = null;
	private SkillsIdentification skillsidentification = null;
	private LazyDataModel<SkillsIdentification> dataModel; 

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
	 * Initialize method to get all SkillsIdentification and prepare a for a create of a new SkillsIdentification
 	 * @author TechFinium 
 	 * @see    SkillsIdentification
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		skillsidentificationInfo();
	}

	/**
	 * Get all SkillsIdentification for data table
 	 * @author TechFinium 
 	 * @see    SkillsIdentification
 	 */
	public void skillsidentificationInfo() {
	 
			
			 dataModel = new LazyDataModel<SkillsIdentification>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SkillsIdentification> retorno = new  ArrayList<SkillsIdentification>();
			   
			   @Override 
			   public List<SkillsIdentification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allSkillsIdentification(SkillsIdentification.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SkillsIdentification.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SkillsIdentification obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SkillsIdentification getRowData(String rowKey) {
			        for(SkillsIdentification obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SkillsIdentification into database
 	 * @author TechFinium 
 	 * @see    SkillsIdentification
 	 */
	public void skillsidentificationInsert() {
		try {
				 service.create(this.skillsidentification);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillsidentificationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SkillsIdentification in database
 	 * @author TechFinium 
 	 * @see    SkillsIdentification
 	 */
    public void skillsidentificationUpdate() {
		try {
				 service.update(this.skillsidentification);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 skillsidentificationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SkillsIdentification from database
 	 * @author TechFinium 
 	 * @see    SkillsIdentification
 	 */
	public void skillsidentificationDelete() {
		try {
			 service.delete(this.skillsidentification);
			  prepareNew();
			 skillsidentificationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SkillsIdentification 
 	 * @author TechFinium 
 	 * @see    SkillsIdentification
 	 */
	public void prepareNew() {
		skillsidentification = new SkillsIdentification();
	}

/*
    public List<SelectItem> getSkillsIdentificationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	skillsidentificationInfo();
    	for (SkillsIdentification ug : skillsidentificationList) {
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
    public List<SkillsIdentification> complete(String desc) {
		List<SkillsIdentification> l = null;
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
    
    public List<SkillsIdentification> getSkillsIdentificationList() {
		return skillsidentificationList;
	}
	public void setSkillsIdentificationList(List<SkillsIdentification> skillsidentificationList) {
		this.skillsidentificationList = skillsidentificationList;
	}
	public SkillsIdentification getSkillsidentification() {
		return skillsidentification;
	}
	public void setSkillsidentification(SkillsIdentification skillsidentification) {
		this.skillsidentification = skillsidentification;
	}

    public List<SkillsIdentification> getSkillsIdentificationfilteredList() {
		return skillsidentificationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param skillsidentificationfilteredList the new skillsidentificationfilteredList list
 	 * @see    SkillsIdentification
 	 */	
	public void setSkillsIdentificationfilteredList(List<SkillsIdentification> skillsidentificationfilteredList) {
		this.skillsidentificationfilteredList = skillsidentificationfilteredList;
	}

	
	public LazyDataModel<SkillsIdentification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SkillsIdentification> dataModel) {
		this.dataModel = dataModel;
	}
	
}
