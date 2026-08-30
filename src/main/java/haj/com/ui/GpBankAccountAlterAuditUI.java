package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.GpBankAccountAlterAudit;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.GpBankAccountAlterAuditService;

@ManagedBean(name = "gpbankaccountalterauditUI")
@ViewScoped
public class GpBankAccountAlterAuditUI extends AbstractUI {

	private GpBankAccountAlterAuditService service = new GpBankAccountAlterAuditService();
	private List<GpBankAccountAlterAudit> gpbankaccountalterauditList = null;
	private List<GpBankAccountAlterAudit> gpbankaccountalterauditfilteredList = null;
	private GpBankAccountAlterAudit gpbankaccountalteraudit = null;
	private LazyDataModel<GpBankAccountAlterAudit> dataModel; 

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
	 * Initialize method to get all GpBankAccountAlterAudit and prepare a for a create of a new GpBankAccountAlterAudit
 	 * @author TechFinium 
 	 * @see    GpBankAccountAlterAudit
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		gpbankaccountalterauditInfo();
	}

	/**
	 * Get all GpBankAccountAlterAudit for data table
 	 * @author TechFinium 
 	 * @see    GpBankAccountAlterAudit
 	 */
	public void gpbankaccountalterauditInfo() {
//			dataModel = new GpBankAccountAlterAuditDatamodel();
	}

	/**
	 * Insert GpBankAccountAlterAudit into database
 	 * @author TechFinium 
 	 * @see    GpBankAccountAlterAudit
 	 */
	public void gpbankaccountalterauditInsert() {
		try {
				 service.create(this.gpbankaccountalteraudit);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 gpbankaccountalterauditInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update GpBankAccountAlterAudit in database
 	 * @author TechFinium 
 	 * @see    GpBankAccountAlterAudit
 	 */
    public void gpbankaccountalterauditUpdate() {
		try {
				 service.update(this.gpbankaccountalteraudit);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 gpbankaccountalterauditInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete GpBankAccountAlterAudit from database
 	 * @author TechFinium 
 	 * @see    GpBankAccountAlterAudit
 	 */
	public void gpbankaccountalterauditDelete() {
		try {
			 service.delete(this.gpbankaccountalteraudit);
			  prepareNew();
			 gpbankaccountalterauditInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of GpBankAccountAlterAudit 
 	 * @author TechFinium 
 	 * @see    GpBankAccountAlterAudit
 	 */
	public void prepareNew() {
		gpbankaccountalteraudit = new GpBankAccountAlterAudit();
	}

/*
    public List<SelectItem> getGpBankAccountAlterAuditDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	gpbankaccountalterauditInfo();
    	for (GpBankAccountAlterAudit ug : gpbankaccountalterauditList) {
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
    public List<GpBankAccountAlterAudit> complete(String desc) {
		List<GpBankAccountAlterAudit> l = null;
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
    
    public List<GpBankAccountAlterAudit> getGpBankAccountAlterAuditList() {
		return gpbankaccountalterauditList;
	}
	public void setGpBankAccountAlterAuditList(List<GpBankAccountAlterAudit> gpbankaccountalterauditList) {
		this.gpbankaccountalterauditList = gpbankaccountalterauditList;
	}
	public GpBankAccountAlterAudit getGpbankaccountalteraudit() {
		return gpbankaccountalteraudit;
	}
	public void setGpbankaccountalteraudit(GpBankAccountAlterAudit gpbankaccountalteraudit) {
		this.gpbankaccountalteraudit = gpbankaccountalteraudit;
	}

    public List<GpBankAccountAlterAudit> getGpBankAccountAlterAuditfilteredList() {
		return gpbankaccountalterauditfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param gpbankaccountalterauditfilteredList the new gpbankaccountalterauditfilteredList list
 	 * @see    GpBankAccountAlterAudit
 	 */	
	public void setGpBankAccountAlterAuditfilteredList(List<GpBankAccountAlterAudit> gpbankaccountalterauditfilteredList) {
		this.gpbankaccountalterauditfilteredList = gpbankaccountalterauditfilteredList;
	}

	
	public LazyDataModel<GpBankAccountAlterAudit> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<GpBankAccountAlterAudit> dataModel) {
		this.dataModel = dataModel;
	}
	
}
