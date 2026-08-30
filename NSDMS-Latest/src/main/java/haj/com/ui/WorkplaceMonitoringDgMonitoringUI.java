package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WorkplaceMonitoringDgMonitoring;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkplaceMonitoringDgMonitoringService;

@ManagedBean(name = "workplacemonitoringdgmonitoringUI")
@ViewScoped
public class WorkplaceMonitoringDgMonitoringUI extends AbstractUI {

	private WorkplaceMonitoringDgMonitoringService service = new WorkplaceMonitoringDgMonitoringService();
	private List<WorkplaceMonitoringDgMonitoring> workplacemonitoringdgmonitoringList = null;
	private List<WorkplaceMonitoringDgMonitoring> workplacemonitoringdgmonitoringfilteredList = null;
	private WorkplaceMonitoringDgMonitoring workplacemonitoringdgmonitoring = null;
	private LazyDataModel<WorkplaceMonitoringDgMonitoring> dataModel; 

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
	 * Initialize method to get all WorkplaceMonitoringDgMonitoring and prepare a for a create of a new WorkplaceMonitoringDgMonitoring
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDgMonitoring
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringdgmonitoringInfo();
	}

	/**
	 * Get all WorkplaceMonitoringDgMonitoring for data table
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 */
	public void workplacemonitoringdgmonitoringInfo() {
//			dataModel = new WorkplaceMonitoringDgMonitoringDatamodel();
	}

	/**
	 * Insert WorkplaceMonitoringDgMonitoring into database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 */
	public void workplacemonitoringdgmonitoringInsert() {
		try {
				 service.create(this.workplacemonitoringdgmonitoring);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringdgmonitoringInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkplaceMonitoringDgMonitoring in database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 */
    public void workplacemonitoringdgmonitoringUpdate() {
		try {
				 service.update(this.workplacemonitoringdgmonitoring);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringdgmonitoringInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkplaceMonitoringDgMonitoring from database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 */
	public void workplacemonitoringdgmonitoringDelete() {
		try {
			 service.delete(this.workplacemonitoringdgmonitoring);
			  prepareNew();
			 workplacemonitoringdgmonitoringInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkplaceMonitoringDgMonitoring 
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 */
	public void prepareNew() {
		workplacemonitoringdgmonitoring = new WorkplaceMonitoringDgMonitoring();
	}

/*
    public List<SelectItem> getWorkplaceMonitoringDgMonitoringDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplacemonitoringdgmonitoringInfo();
    	for (WorkplaceMonitoringDgMonitoring ug : workplacemonitoringdgmonitoringList) {
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
    public List<WorkplaceMonitoringDgMonitoring> complete(String desc) {
		List<WorkplaceMonitoringDgMonitoring> l = null;
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
    
    public List<WorkplaceMonitoringDgMonitoring> getWorkplaceMonitoringDgMonitoringList() {
		return workplacemonitoringdgmonitoringList;
	}
	public void setWorkplaceMonitoringDgMonitoringList(List<WorkplaceMonitoringDgMonitoring> workplacemonitoringdgmonitoringList) {
		this.workplacemonitoringdgmonitoringList = workplacemonitoringdgmonitoringList;
	}
	public WorkplaceMonitoringDgMonitoring getWorkplacemonitoringdgmonitoring() {
		return workplacemonitoringdgmonitoring;
	}
	public void setWorkplacemonitoringdgmonitoring(WorkplaceMonitoringDgMonitoring workplacemonitoringdgmonitoring) {
		this.workplacemonitoringdgmonitoring = workplacemonitoringdgmonitoring;
	}

    public List<WorkplaceMonitoringDgMonitoring> getWorkplaceMonitoringDgMonitoringfilteredList() {
		return workplacemonitoringdgmonitoringfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplacemonitoringdgmonitoringfilteredList the new workplacemonitoringdgmonitoringfilteredList list
 	 * @see    WorkplaceMonitoringDgMonitoring
 	 */	
	public void setWorkplaceMonitoringDgMonitoringfilteredList(List<WorkplaceMonitoringDgMonitoring> workplacemonitoringdgmonitoringfilteredList) {
		this.workplacemonitoringdgmonitoringfilteredList = workplacemonitoringdgmonitoringfilteredList;
	}

	
	public LazyDataModel<WorkplaceMonitoringDgMonitoring> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringDgMonitoring> dataModel) {
		this.dataModel = dataModel;
	}
	
}
