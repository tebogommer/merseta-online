package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkplaceMonitoringSiteVisitService;

@ManagedBean(name = "workplacemonitoringsitevisitUI")
@ViewScoped
public class WorkplaceMonitoringSiteVisitUI extends AbstractUI {

	private WorkplaceMonitoringSiteVisitService service = new WorkplaceMonitoringSiteVisitService();
	private List<WorkplaceMonitoringSiteVisit> workplacemonitoringsitevisitList = null;
	private List<WorkplaceMonitoringSiteVisit> workplacemonitoringsitevisitfilteredList = null;
	private WorkplaceMonitoringSiteVisit workplacemonitoringsitevisit = null;
	private LazyDataModel<WorkplaceMonitoringSiteVisit> dataModel; 

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
	 * Initialize method to get all WorkplaceMonitoringSiteVisit and prepare a for a create of a new WorkplaceMonitoringSiteVisit
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringSiteVisit
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringsitevisitInfo();
	}

	/**
	 * Get all WorkplaceMonitoringSiteVisit for data table
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringSiteVisit
 	 */
	public void workplacemonitoringsitevisitInfo() {
//			dataModel = new WorkplaceMonitoringSiteVisitDatamodel();
	}

	/**
	 * Insert WorkplaceMonitoringSiteVisit into database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringSiteVisit
 	 */
	public void workplacemonitoringsitevisitInsert() {
		try {
				 service.create(this.workplacemonitoringsitevisit);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringsitevisitInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkplaceMonitoringSiteVisit in database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringSiteVisit
 	 */
    public void workplacemonitoringsitevisitUpdate() {
		try {
				 service.update(this.workplacemonitoringsitevisit);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringsitevisitInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkplaceMonitoringSiteVisit from database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringSiteVisit
 	 */
	public void workplacemonitoringsitevisitDelete() {
		try {
			 service.delete(this.workplacemonitoringsitevisit);
			  prepareNew();
			 workplacemonitoringsitevisitInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkplaceMonitoringSiteVisit 
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringSiteVisit
 	 */
	public void prepareNew() {
		workplacemonitoringsitevisit = new WorkplaceMonitoringSiteVisit();
	}

/*
    public List<SelectItem> getWorkplaceMonitoringSiteVisitDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplacemonitoringsitevisitInfo();
    	for (WorkplaceMonitoringSiteVisit ug : workplacemonitoringsitevisitList) {
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
    public List<WorkplaceMonitoringSiteVisit> complete(String desc) {
		List<WorkplaceMonitoringSiteVisit> l = null;
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
    
    public List<WorkplaceMonitoringSiteVisit> getWorkplaceMonitoringSiteVisitList() {
		return workplacemonitoringsitevisitList;
	}
	public void setWorkplaceMonitoringSiteVisitList(List<WorkplaceMonitoringSiteVisit> workplacemonitoringsitevisitList) {
		this.workplacemonitoringsitevisitList = workplacemonitoringsitevisitList;
	}
	public WorkplaceMonitoringSiteVisit getWorkplacemonitoringsitevisit() {
		return workplacemonitoringsitevisit;
	}
	public void setWorkplacemonitoringsitevisit(WorkplaceMonitoringSiteVisit workplacemonitoringsitevisit) {
		this.workplacemonitoringsitevisit = workplacemonitoringsitevisit;
	}

    public List<WorkplaceMonitoringSiteVisit> getWorkplaceMonitoringSiteVisitfilteredList() {
		return workplacemonitoringsitevisitfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplacemonitoringsitevisitfilteredList the new workplacemonitoringsitevisitfilteredList list
 	 * @see    WorkplaceMonitoringSiteVisit
 	 */	
	public void setWorkplaceMonitoringSiteVisitfilteredList(List<WorkplaceMonitoringSiteVisit> workplacemonitoringsitevisitfilteredList) {
		this.workplacemonitoringsitevisitfilteredList = workplacemonitoringsitevisitfilteredList;
	}

	
	public LazyDataModel<WorkplaceMonitoringSiteVisit> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringSiteVisit> dataModel) {
		this.dataModel = dataModel;
	}
	
}
