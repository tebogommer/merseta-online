package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WorkplaceMonitoringLearnerPayments;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WorkplaceMonitoringLearnerPaymentsService;

@ManagedBean(name = "workplacemonitoringlearnerpaymentsUI")
@ViewScoped
public class WorkplaceMonitoringLearnerPaymentsUI extends AbstractUI {

	private WorkplaceMonitoringLearnerPaymentsService service = new WorkplaceMonitoringLearnerPaymentsService();
	private List<WorkplaceMonitoringLearnerPayments> workplacemonitoringlearnerpaymentsList = null;
	private List<WorkplaceMonitoringLearnerPayments> workplacemonitoringlearnerpaymentsfilteredList = null;
	private WorkplaceMonitoringLearnerPayments workplacemonitoringlearnerpayments = null;
	private LazyDataModel<WorkplaceMonitoringLearnerPayments> dataModel; 

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
	 * Initialize method to get all WorkplaceMonitoringLearnerPayments and prepare a for a create of a new WorkplaceMonitoringLearnerPayments
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerPayments
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacemonitoringlearnerpaymentsInfo();
	}

	/**
	 * Get all WorkplaceMonitoringLearnerPayments for data table
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 */
	public void workplacemonitoringlearnerpaymentsInfo() {
//			dataModel = new WorkplaceMonitoringLearnerPaymentsDatamodel();
	}

	/**
	 * Insert WorkplaceMonitoringLearnerPayments into database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 */
	public void workplacemonitoringlearnerpaymentsInsert() {
		try {
				 service.create(this.workplacemonitoringlearnerpayments);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringlearnerpaymentsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkplaceMonitoringLearnerPayments in database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 */
    public void workplacemonitoringlearnerpaymentsUpdate() {
		try {
				 service.update(this.workplacemonitoringlearnerpayments);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacemonitoringlearnerpaymentsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkplaceMonitoringLearnerPayments from database
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 */
	public void workplacemonitoringlearnerpaymentsDelete() {
		try {
			 service.delete(this.workplacemonitoringlearnerpayments);
			  prepareNew();
			 workplacemonitoringlearnerpaymentsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkplaceMonitoringLearnerPayments 
 	 * @author TechFinium 
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 */
	public void prepareNew() {
		workplacemonitoringlearnerpayments = new WorkplaceMonitoringLearnerPayments();
	}

/*
    public List<SelectItem> getWorkplaceMonitoringLearnerPaymentsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplacemonitoringlearnerpaymentsInfo();
    	for (WorkplaceMonitoringLearnerPayments ug : workplacemonitoringlearnerpaymentsList) {
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
    public List<WorkplaceMonitoringLearnerPayments> complete(String desc) {
		List<WorkplaceMonitoringLearnerPayments> l = null;
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
    
    public List<WorkplaceMonitoringLearnerPayments> getWorkplaceMonitoringLearnerPaymentsList() {
		return workplacemonitoringlearnerpaymentsList;
	}
	public void setWorkplaceMonitoringLearnerPaymentsList(List<WorkplaceMonitoringLearnerPayments> workplacemonitoringlearnerpaymentsList) {
		this.workplacemonitoringlearnerpaymentsList = workplacemonitoringlearnerpaymentsList;
	}
	public WorkplaceMonitoringLearnerPayments getWorkplacemonitoringlearnerpayments() {
		return workplacemonitoringlearnerpayments;
	}
	public void setWorkplacemonitoringlearnerpayments(WorkplaceMonitoringLearnerPayments workplacemonitoringlearnerpayments) {
		this.workplacemonitoringlearnerpayments = workplacemonitoringlearnerpayments;
	}

    public List<WorkplaceMonitoringLearnerPayments> getWorkplaceMonitoringLearnerPaymentsfilteredList() {
		return workplacemonitoringlearnerpaymentsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param workplacemonitoringlearnerpaymentsfilteredList the new workplacemonitoringlearnerpaymentsfilteredList list
 	 * @see    WorkplaceMonitoringLearnerPayments
 	 */	
	public void setWorkplaceMonitoringLearnerPaymentsfilteredList(List<WorkplaceMonitoringLearnerPayments> workplacemonitoringlearnerpaymentsfilteredList) {
		this.workplacemonitoringlearnerpaymentsfilteredList = workplacemonitoringlearnerpaymentsfilteredList;
	}

	
	public LazyDataModel<WorkplaceMonitoringLearnerPayments> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkplaceMonitoringLearnerPayments> dataModel) {
		this.dataModel = dataModel;
	}
	
}
