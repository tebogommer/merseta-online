package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.WorkPlaceApprovalSkillsProgramme;
import haj.com.service.WorkPlaceApprovalSkillsProgrammeService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "workplaceapprovalskillsprogrammeUI")
@ViewScoped
public class WorkPlaceApprovalSkillsProgrammeUI extends AbstractUI {

	private WorkPlaceApprovalSkillsProgrammeService service = new WorkPlaceApprovalSkillsProgrammeService();
	private List<WorkPlaceApprovalSkillsProgramme> workplaceapprovalskillsprogrammeList = null;
	private List<WorkPlaceApprovalSkillsProgramme> workplaceapprovalskillsprogrammefilteredList = null;
	private WorkPlaceApprovalSkillsProgramme workplaceapprovalskillsprogramme = null;
	private LazyDataModel<WorkPlaceApprovalSkillsProgramme> dataModel; 

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
	 * Initialize method to get all WorkPlaceApprovalSkillsProgramme and prepare a for a create of a new WorkPlaceApprovalSkillsProgramme
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalSkillsProgramme
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplaceapprovalskillsprogrammeInfo();
	}

	/**
	 * Get all WorkPlaceApprovalSkillsProgramme for data table
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 */
	public void workplaceapprovalskillsprogrammeInfo() {
			//dataModel = new WorkPlaceApprovalSkillsProgrammeDatamodel();
	}

	/**
	 * Insert WorkPlaceApprovalSkillsProgramme into database
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 */
	public void workplaceapprovalskillsprogrammeInsert() {
		try {
				 service.create(this.workplaceapprovalskillsprogramme);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplaceapprovalskillsprogrammeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkPlaceApprovalSkillsProgramme in database
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 */
    public void workplaceapprovalskillsprogrammeUpdate() {
		try {
				 service.update(this.workplaceapprovalskillsprogramme);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplaceapprovalskillsprogrammeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkPlaceApprovalSkillsProgramme from database
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 */
	public void workplaceapprovalskillsprogrammeDelete() {
		try {
			 service.delete(this.workplaceapprovalskillsprogramme);
			  prepareNew();
			 workplaceapprovalskillsprogrammeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkPlaceApprovalSkillsProgramme 
 	 * @author TechFinium 
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 */
	public void prepareNew() {
		workplaceapprovalskillsprogramme = new WorkPlaceApprovalSkillsProgramme();
	}

/*
    public List<SelectItem> getWorkPlaceApprovalSkillsProgrammeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplaceapprovalskillsprogrammeInfo();
    	for (WorkPlaceApprovalSkillsProgramme ug : workplaceapprovalskillsprogrammeList) {
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
    public List<WorkPlaceApprovalSkillsProgramme> complete(String desc) {
		List<WorkPlaceApprovalSkillsProgramme> l = null;
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
    
    public List<WorkPlaceApprovalSkillsProgramme> getWorkPlaceApprovalSkillsProgrammeList() {
		return workplaceapprovalskillsprogrammeList;
	}
	public void setWorkPlaceApprovalSkillsProgrammeList(List<WorkPlaceApprovalSkillsProgramme> workplaceapprovalskillsprogrammeList) {
		this.workplaceapprovalskillsprogrammeList = workplaceapprovalskillsprogrammeList;
	}
	public WorkPlaceApprovalSkillsProgramme getWorkplaceapprovalskillsprogramme() {
		return workplaceapprovalskillsprogramme;
	}
	public void setWorkplaceapprovalskillsprogramme(WorkPlaceApprovalSkillsProgramme workplaceapprovalskillsprogramme) {
		this.workplaceapprovalskillsprogramme = workplaceapprovalskillsprogramme;
	}

    public List<WorkPlaceApprovalSkillsProgramme> getWorkPlaceApprovalSkillsProgrammefilteredList() {
		return workplaceapprovalskillsprogrammefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplaceapprovalskillsprogrammefilteredList the new workplaceapprovalskillsprogrammefilteredList list
 	 * @see    WorkPlaceApprovalSkillsProgramme
 	 */	
	public void setWorkPlaceApprovalSkillsProgrammefilteredList(List<WorkPlaceApprovalSkillsProgramme> workplaceapprovalskillsprogrammefilteredList) {
		this.workplaceapprovalskillsprogrammefilteredList = workplaceapprovalskillsprogrammefilteredList;
	}

	
	public LazyDataModel<WorkPlaceApprovalSkillsProgramme> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkPlaceApprovalSkillsProgramme> dataModel) {
		this.dataModel = dataModel;
	}
	
}
