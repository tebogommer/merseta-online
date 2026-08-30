package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkplaceMonitoringMitigationPlanService;

@ManagedBean(name = "workplacemonitoringmitigationplanUI")
@ViewScoped
public class WorkplaceMonitoringMitigationPlanUI extends AbstractUI {

	private WorkplaceMonitoringMitigationPlanService service = new WorkplaceMonitoringMitigationPlanService();
	private List<WorkplaceMonitoringMitigationPlan> workplacemonitoringmitigationplanList = null;
	private List<WorkplaceMonitoringMitigationPlan> workplacemonitoringmitigationplanfilteredList = null;
	private WorkplaceMonitoringMitigationPlan workplacemonitoringmitigationplan = null;
	private LazyDataModel<WorkplaceMonitoringMitigationPlan> dataModel; 

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
	 * Initialize method to get all WorkplaceMonitoringMitigationPlan and prepare a for a create of a new WorkplaceMonitoringMitigationPlan
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringMitigationPlan
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringmitigationplanInfo();
	}

	/**
	 * Get all WorkplaceMonitoringMitigationPlan for data table
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 */
	public void workplacemonitoringmitigationplanInfo() {
//			dataModel = new WorkplaceMonitoringMitigationPlanDatamodel();
	}

	/**
	 * Insert WorkplaceMonitoringMitigationPlan into database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 */
	public void workplacemonitoringmitigationplanInsert() {
		try {
				 service.create(this.workplacemonitoringmitigationplan);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringmitigationplanInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkplaceMonitoringMitigationPlan in database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 */
    public void workplacemonitoringmitigationplanUpdate() {
		try {
				 service.update(this.workplacemonitoringmitigationplan);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringmitigationplanInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkplaceMonitoringMitigationPlan from database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 */
	public void workplacemonitoringmitigationplanDelete() {
		try {
			 service.delete(this.workplacemonitoringmitigationplan);
			  prepareNew();
			 workplacemonitoringmitigationplanInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkplaceMonitoringMitigationPlan 
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 */
	public void prepareNew() {
		workplacemonitoringmitigationplan = new WorkplaceMonitoringMitigationPlan();
	}

/*
    public List<SelectItem> getWorkplaceMonitoringMitigationPlanDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplacemonitoringmitigationplanInfo();
    	for (WorkplaceMonitoringMitigationPlan ug : workplacemonitoringmitigationplanList) {
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
    public List<WorkplaceMonitoringMitigationPlan> complete(String desc) {
		List<WorkplaceMonitoringMitigationPlan> l = null;
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
    
    public List<WorkplaceMonitoringMitigationPlan> getWorkplaceMonitoringMitigationPlanList() {
		return workplacemonitoringmitigationplanList;
	}
	public void setWorkplaceMonitoringMitigationPlanList(List<WorkplaceMonitoringMitigationPlan> workplacemonitoringmitigationplanList) {
		this.workplacemonitoringmitigationplanList = workplacemonitoringmitigationplanList;
	}
	public WorkplaceMonitoringMitigationPlan getWorkplacemonitoringmitigationplan() {
		return workplacemonitoringmitigationplan;
	}
	public void setWorkplacemonitoringmitigationplan(WorkplaceMonitoringMitigationPlan workplacemonitoringmitigationplan) {
		this.workplacemonitoringmitigationplan = workplacemonitoringmitigationplan;
	}

    public List<WorkplaceMonitoringMitigationPlan> getWorkplaceMonitoringMitigationPlanfilteredList() {
		return workplacemonitoringmitigationplanfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplacemonitoringmitigationplanfilteredList the new workplacemonitoringmitigationplanfilteredList list
 	 * @see    WorkplaceMonitoringMitigationPlan
 	 */	
	public void setWorkplaceMonitoringMitigationPlanfilteredList(List<WorkplaceMonitoringMitigationPlan> workplacemonitoringmitigationplanfilteredList) {
		this.workplacemonitoringmitigationplanfilteredList = workplacemonitoringmitigationplanfilteredList;
	}

	
	public LazyDataModel<WorkplaceMonitoringMitigationPlan> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringMitigationPlan> dataModel) {
		this.dataModel = dataModel;
	}
	
}
