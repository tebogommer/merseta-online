package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.LegacyProviderApplicationAlterationAudit;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.LegacyProviderApplicationAlterationAuditService;

@ManagedBean(name = "legacyproviderapplicationalterationauditUI")
@ViewScoped
public class LegacyProviderApplicationAlterationAuditUI extends AbstractUI {

	private LegacyProviderApplicationAlterationAuditService service = new LegacyProviderApplicationAlterationAuditService();
	private List<LegacyProviderApplicationAlterationAudit> legacyproviderapplicationalterationauditList = null;
	private List<LegacyProviderApplicationAlterationAudit> legacyproviderapplicationalterationauditfilteredList = null;
	private LegacyProviderApplicationAlterationAudit legacyproviderapplicationalterationaudit = null;
	private LazyDataModel<LegacyProviderApplicationAlterationAudit> dataModel; 

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
	 * Initialize method to get all LegacyProviderApplicationAlterationAudit and prepare a for a create of a new LegacyProviderApplicationAlterationAudit
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationAlterationAudit
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyproviderapplicationalterationauditInfo();
	}

	/**
	 * Get all LegacyProviderApplicationAlterationAudit for data table
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 */
	public void legacyproviderapplicationalterationauditInfo() {
//			dataModel = new LegacyProviderApplicationAlterationAuditDatamodel();
	}

	/**
	 * Insert LegacyProviderApplicationAlterationAudit into database
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 */
	public void legacyproviderapplicationalterationauditInsert() {
		try {
				 service.create(this.legacyproviderapplicationalterationaudit);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 legacyproviderapplicationalterationauditInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LegacyProviderApplicationAlterationAudit in database
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 */
    public void legacyproviderapplicationalterationauditUpdate() {
		try {
				 service.update(this.legacyproviderapplicationalterationaudit);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 legacyproviderapplicationalterationauditInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LegacyProviderApplicationAlterationAudit from database
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 */
	public void legacyproviderapplicationalterationauditDelete() {
		try {
			 service.delete(this.legacyproviderapplicationalterationaudit);
			  prepareNew();
			 legacyproviderapplicationalterationauditInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LegacyProviderApplicationAlterationAudit 
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 */
	public void prepareNew() {
		legacyproviderapplicationalterationaudit = new LegacyProviderApplicationAlterationAudit();
	}

/*
    public List<SelectItem> getLegacyProviderApplicationAlterationAuditDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	legacyproviderapplicationalterationauditInfo();
    	for (LegacyProviderApplicationAlterationAudit ug : legacyproviderapplicationalterationauditList) {
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
    public List<LegacyProviderApplicationAlterationAudit> complete(String desc) {
		List<LegacyProviderApplicationAlterationAudit> l = null;
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
    
    public List<LegacyProviderApplicationAlterationAudit> getLegacyProviderApplicationAlterationAuditList() {
		return legacyproviderapplicationalterationauditList;
	}
	public void setLegacyProviderApplicationAlterationAuditList(List<LegacyProviderApplicationAlterationAudit> legacyproviderapplicationalterationauditList) {
		this.legacyproviderapplicationalterationauditList = legacyproviderapplicationalterationauditList;
	}
	public LegacyProviderApplicationAlterationAudit getLegacyproviderapplicationalterationaudit() {
		return legacyproviderapplicationalterationaudit;
	}
	public void setLegacyproviderapplicationalterationaudit(LegacyProviderApplicationAlterationAudit legacyproviderapplicationalterationaudit) {
		this.legacyproviderapplicationalterationaudit = legacyproviderapplicationalterationaudit;
	}

    public List<LegacyProviderApplicationAlterationAudit> getLegacyProviderApplicationAlterationAuditfilteredList() {
		return legacyproviderapplicationalterationauditfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param legacyproviderapplicationalterationauditfilteredList the new legacyproviderapplicationalterationauditfilteredList list
 	 * @see    LegacyProviderApplicationAlterationAudit
 	 */	
	public void setLegacyProviderApplicationAlterationAuditfilteredList(List<LegacyProviderApplicationAlterationAudit> legacyproviderapplicationalterationauditfilteredList) {
		this.legacyproviderapplicationalterationauditfilteredList = legacyproviderapplicationalterationauditfilteredList;
	}

	
	public LazyDataModel<LegacyProviderApplicationAlterationAudit> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyProviderApplicationAlterationAudit> dataModel) {
		this.dataModel = dataModel;
	}
	
}
