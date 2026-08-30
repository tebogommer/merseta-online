package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.LevyDetailMgPaymentsAlterAudit;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.LevyDetailMgPaymentsAlterAuditService;

@ManagedBean(name = "levydetailmgpaymentsalterauditUI")
@ViewScoped
public class LevyDetailMgPaymentsAlterAuditUI extends AbstractUI {

	private LevyDetailMgPaymentsAlterAuditService service = new LevyDetailMgPaymentsAlterAuditService();
	private List<LevyDetailMgPaymentsAlterAudit> levydetailmgpaymentsalterauditList = null;
	private List<LevyDetailMgPaymentsAlterAudit> levydetailmgpaymentsalterauditfilteredList = null;
	private LevyDetailMgPaymentsAlterAudit levydetailmgpaymentsalteraudit = null;
	private LazyDataModel<LevyDetailMgPaymentsAlterAudit> dataModel; 

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
	 * Initialize method to get all LevyDetailMgPaymentsAlterAudit and prepare a for a create of a new LevyDetailMgPaymentsAlterAudit
 	 * @author TechFinium 
 	 * @see    LevyDetailMgPaymentsAlterAudit
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		levydetailmgpaymentsalterauditInfo();
	}

	/**
	 * Get all LevyDetailMgPaymentsAlterAudit for data table
 	 * @author TechFinium 
 	 * @see    LevyDetailMgPaymentsAlterAudit
 	 */
	public void levydetailmgpaymentsalterauditInfo() {
//			dataModel = new LevyDetailMgPaymentsAlterAuditDatamodel();
	}

	/**
	 * Insert LevyDetailMgPaymentsAlterAudit into database
 	 * @author TechFinium 
 	 * @see    LevyDetailMgPaymentsAlterAudit
 	 */
	public void levydetailmgpaymentsalterauditInsert() {
		try {
				 service.create(this.levydetailmgpaymentsalteraudit);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 levydetailmgpaymentsalterauditInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LevyDetailMgPaymentsAlterAudit in database
 	 * @author TechFinium 
 	 * @see    LevyDetailMgPaymentsAlterAudit
 	 */
    public void levydetailmgpaymentsalterauditUpdate() {
		try {
				 service.update(this.levydetailmgpaymentsalteraudit);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 levydetailmgpaymentsalterauditInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LevyDetailMgPaymentsAlterAudit from database
 	 * @author TechFinium 
 	 * @see    LevyDetailMgPaymentsAlterAudit
 	 */
	public void levydetailmgpaymentsalterauditDelete() {
		try {
			 service.delete(this.levydetailmgpaymentsalteraudit);
			  prepareNew();
			 levydetailmgpaymentsalterauditInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LevyDetailMgPaymentsAlterAudit 
 	 * @author TechFinium 
 	 * @see    LevyDetailMgPaymentsAlterAudit
 	 */
	public void prepareNew() {
		levydetailmgpaymentsalteraudit = new LevyDetailMgPaymentsAlterAudit();
	}

/*
    public List<SelectItem> getLevyDetailMgPaymentsAlterAuditDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	levydetailmgpaymentsalterauditInfo();
    	for (LevyDetailMgPaymentsAlterAudit ug : levydetailmgpaymentsalterauditList) {
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
    public List<LevyDetailMgPaymentsAlterAudit> complete(String desc) {
		List<LevyDetailMgPaymentsAlterAudit> l = null;
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
    
    public List<LevyDetailMgPaymentsAlterAudit> getLevyDetailMgPaymentsAlterAuditList() {
		return levydetailmgpaymentsalterauditList;
	}
	public void setLevyDetailMgPaymentsAlterAuditList(List<LevyDetailMgPaymentsAlterAudit> levydetailmgpaymentsalterauditList) {
		this.levydetailmgpaymentsalterauditList = levydetailmgpaymentsalterauditList;
	}
	public LevyDetailMgPaymentsAlterAudit getLevydetailmgpaymentsalteraudit() {
		return levydetailmgpaymentsalteraudit;
	}
	public void setLevydetailmgpaymentsalteraudit(LevyDetailMgPaymentsAlterAudit levydetailmgpaymentsalteraudit) {
		this.levydetailmgpaymentsalteraudit = levydetailmgpaymentsalteraudit;
	}

    public List<LevyDetailMgPaymentsAlterAudit> getLevyDetailMgPaymentsAlterAuditfilteredList() {
		return levydetailmgpaymentsalterauditfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param levydetailmgpaymentsalterauditfilteredList the new levydetailmgpaymentsalterauditfilteredList list
 	 * @see    LevyDetailMgPaymentsAlterAudit
 	 */	
	public void setLevyDetailMgPaymentsAlterAuditfilteredList(List<LevyDetailMgPaymentsAlterAudit> levydetailmgpaymentsalterauditfilteredList) {
		this.levydetailmgpaymentsalterauditfilteredList = levydetailmgpaymentsalterauditfilteredList;
	}

	
	public LazyDataModel<LevyDetailMgPaymentsAlterAudit> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LevyDetailMgPaymentsAlterAudit> dataModel) {
		this.dataModel = dataModel;
	}
	
}
