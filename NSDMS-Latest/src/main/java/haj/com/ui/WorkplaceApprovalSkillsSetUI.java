package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.WorkplaceApprovalSkillsSet;
import haj.com.service.WorkplaceApprovalSkillsSetService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "workplaceapprovalskillssetUI")
@ViewScoped
public class WorkplaceApprovalSkillsSetUI extends AbstractUI {

	private WorkplaceApprovalSkillsSetService service = new WorkplaceApprovalSkillsSetService();
	private List<WorkplaceApprovalSkillsSet> workplaceapprovalskillssetList = null;
	private List<WorkplaceApprovalSkillsSet> workplaceapprovalskillssetfilteredList = null;
	private WorkplaceApprovalSkillsSet workplaceapprovalskillsset = null;
	private LazyDataModel<WorkplaceApprovalSkillsSet> dataModel; 

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
	 * Initialize method to get all WorkplaceApprovalSkillsSet and prepare a for a create of a new WorkplaceApprovalSkillsSet
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalSkillsSet
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplaceapprovalskillssetInfo();
	}

	/**
	 * Get all WorkplaceApprovalSkillsSet for data table
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalSkillsSet
 	 */
	public void workplaceapprovalskillssetInfo() {
			//dataModel = new WorkplaceApprovalSkillsSetDatamodel();
	}

	/**
	 * Insert WorkplaceApprovalSkillsSet into database
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalSkillsSet
 	 */
	public void workplaceapprovalskillssetInsert() {
		try {
				 service.create(this.workplaceapprovalskillsset);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplaceapprovalskillssetInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkplaceApprovalSkillsSet in database
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalSkillsSet
 	 */
    public void workplaceapprovalskillssetUpdate() {
		try {
				 service.update(this.workplaceapprovalskillsset);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplaceapprovalskillssetInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkplaceApprovalSkillsSet from database
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalSkillsSet
 	 */
	public void workplaceapprovalskillssetDelete() {
		try {
			 service.delete(this.workplaceapprovalskillsset);
			  prepareNew();
			 workplaceapprovalskillssetInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkplaceApprovalSkillsSet 
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalSkillsSet
 	 */
	public void prepareNew() {
		workplaceapprovalskillsset = new WorkplaceApprovalSkillsSet();
	}

/*
    public List<SelectItem> getWorkplaceApprovalSkillsSetDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplaceapprovalskillssetInfo();
    	for (WorkplaceApprovalSkillsSet ug : workplaceapprovalskillssetList) {
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
    public List<WorkplaceApprovalSkillsSet> complete(String desc) {
		List<WorkplaceApprovalSkillsSet> l = null;
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
    
    public List<WorkplaceApprovalSkillsSet> getWorkplaceApprovalSkillsSetList() {
		return workplaceapprovalskillssetList;
	}
	public void setWorkplaceApprovalSkillsSetList(List<WorkplaceApprovalSkillsSet> workplaceapprovalskillssetList) {
		this.workplaceapprovalskillssetList = workplaceapprovalskillssetList;
	}
	public WorkplaceApprovalSkillsSet getWorkplaceapprovalskillsset() {
		return workplaceapprovalskillsset;
	}
	public void setWorkplaceapprovalskillsset(WorkplaceApprovalSkillsSet workplaceapprovalskillsset) {
		this.workplaceapprovalskillsset = workplaceapprovalskillsset;
	}

    public List<WorkplaceApprovalSkillsSet> getWorkplaceApprovalSkillsSetfilteredList() {
		return workplaceapprovalskillssetfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplaceapprovalskillssetfilteredList the new workplaceapprovalskillssetfilteredList list
 	 * @see    WorkplaceApprovalSkillsSet
 	 */	
	public void setWorkplaceApprovalSkillsSetfilteredList(List<WorkplaceApprovalSkillsSet> workplaceapprovalskillssetfilteredList) {
		this.workplaceapprovalskillssetfilteredList = workplaceapprovalskillssetfilteredList;
	}

	
	public LazyDataModel<WorkplaceApprovalSkillsSet> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceApprovalSkillsSet> dataModel) {
		this.dataModel = dataModel;
	}
	
}
