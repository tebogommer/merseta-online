package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.WorkplaceApprovalUnitStandart;
import haj.com.service.WorkplaceApprovalUnitStandartService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "workplaceapprovalunitstandartUI")
@ViewScoped
public class WorkplaceApprovalUnitStandartUI extends AbstractUI {

	private WorkplaceApprovalUnitStandartService service = new WorkplaceApprovalUnitStandartService();
	private List<WorkplaceApprovalUnitStandart> workplaceapprovalunitstandartList = null;
	private List<WorkplaceApprovalUnitStandart> workplaceapprovalunitstandartfilteredList = null;
	private WorkplaceApprovalUnitStandart workplaceapprovalunitstandart = null;
	private LazyDataModel<WorkplaceApprovalUnitStandart> dataModel; 

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
	 * Initialize method to get all WorkplaceApprovalUnitStandart and prepare a for a create of a new WorkplaceApprovalUnitStandart
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalUnitStandart
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplaceapprovalunitstandartInfo();
	}

	/**
	 * Get all WorkplaceApprovalUnitStandart for data table
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalUnitStandart
 	 */
	public void workplaceapprovalunitstandartInfo() {
			//dataModel = new WorkplaceApprovalUnitStandartDatamodel();
	}

	/**
	 * Insert WorkplaceApprovalUnitStandart into database
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalUnitStandart
 	 */
	public void workplaceapprovalunitstandartInsert() {
		try {
				 service.create(this.workplaceapprovalunitstandart);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplaceapprovalunitstandartInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkplaceApprovalUnitStandart in database
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalUnitStandart
 	 */
    public void workplaceapprovalunitstandartUpdate() {
		try {
				 service.update(this.workplaceapprovalunitstandart);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplaceapprovalunitstandartInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkplaceApprovalUnitStandart from database
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalUnitStandart
 	 */
	public void workplaceapprovalunitstandartDelete() {
		try {
			 service.delete(this.workplaceapprovalunitstandart);
			  prepareNew();
			 workplaceapprovalunitstandartInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkplaceApprovalUnitStandart 
 	 * @author TechFinium 
 	 * @see    WorkplaceApprovalUnitStandart
 	 */
	public void prepareNew() {
		workplaceapprovalunitstandart = new WorkplaceApprovalUnitStandart();
	}

/*
    public List<SelectItem> getWorkplaceApprovalUnitStandartDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplaceapprovalunitstandartInfo();
    	for (WorkplaceApprovalUnitStandart ug : workplaceapprovalunitstandartList) {
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
    public List<WorkplaceApprovalUnitStandart> complete(String desc) {
		List<WorkplaceApprovalUnitStandart> l = null;
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
    
    public List<WorkplaceApprovalUnitStandart> getWorkplaceApprovalUnitStandartList() {
		return workplaceapprovalunitstandartList;
	}
	public void setWorkplaceApprovalUnitStandartList(List<WorkplaceApprovalUnitStandart> workplaceapprovalunitstandartList) {
		this.workplaceapprovalunitstandartList = workplaceapprovalunitstandartList;
	}
	public WorkplaceApprovalUnitStandart getWorkplaceapprovalunitstandart() {
		return workplaceapprovalunitstandart;
	}
	public void setWorkplaceapprovalunitstandart(WorkplaceApprovalUnitStandart workplaceapprovalunitstandart) {
		this.workplaceapprovalunitstandart = workplaceapprovalunitstandart;
	}

    public List<WorkplaceApprovalUnitStandart> getWorkplaceApprovalUnitStandartfilteredList() {
		return workplaceapprovalunitstandartfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplaceapprovalunitstandartfilteredList the new workplaceapprovalunitstandartfilteredList list
 	 * @see    WorkplaceApprovalUnitStandart
 	 */	
	public void setWorkplaceApprovalUnitStandartfilteredList(List<WorkplaceApprovalUnitStandart> workplaceapprovalunitstandartfilteredList) {
		this.workplaceapprovalunitstandartfilteredList = workplaceapprovalunitstandartfilteredList;
	}

	
	public LazyDataModel<WorkplaceApprovalUnitStandart> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceApprovalUnitStandart> dataModel) {
		this.dataModel = dataModel;
	}
	
}
