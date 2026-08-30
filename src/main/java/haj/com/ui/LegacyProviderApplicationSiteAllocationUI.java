package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.LegacyProviderApplicationSiteAllocation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.LegacyProviderApplicationSiteAllocationService;

@ManagedBean(name = "legacyproviderapplicationsiteallocationUI")
@ViewScoped
public class LegacyProviderApplicationSiteAllocationUI extends AbstractUI {

	private LegacyProviderApplicationSiteAllocationService service = new LegacyProviderApplicationSiteAllocationService();
	private List<LegacyProviderApplicationSiteAllocation> legacyproviderapplicationsiteallocationList = null;
	private List<LegacyProviderApplicationSiteAllocation> legacyproviderapplicationsiteallocationfilteredList = null;
	private LegacyProviderApplicationSiteAllocation legacyproviderapplicationsiteallocation = null;
	private LazyDataModel<LegacyProviderApplicationSiteAllocation> dataModel; 

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
	 * Initialize method to get all LegacyProviderApplicationSiteAllocation and prepare a for a create of a new LegacyProviderApplicationSiteAllocation
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationSiteAllocation
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		legacyproviderapplicationsiteallocationInfo();
	}

	/**
	 * Get all LegacyProviderApplicationSiteAllocation for data table
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 */
	public void legacyproviderapplicationsiteallocationInfo() {
//		dataModel = new LegacyProviderApplicationSiteAllocationDatamodel();
	}

	/**
	 * Insert LegacyProviderApplicationSiteAllocation into database
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 */
	public void legacyproviderapplicationsiteallocationInsert() {
		try {
				 service.create(this.legacyproviderapplicationsiteallocation);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 legacyproviderapplicationsiteallocationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LegacyProviderApplicationSiteAllocation in database
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 */
    public void legacyproviderapplicationsiteallocationUpdate() {
		try {
				 service.update(this.legacyproviderapplicationsiteallocation);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 legacyproviderapplicationsiteallocationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LegacyProviderApplicationSiteAllocation from database
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 */
	public void legacyproviderapplicationsiteallocationDelete() {
		try {
			 service.delete(this.legacyproviderapplicationsiteallocation);
			  prepareNew();
			 legacyproviderapplicationsiteallocationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LegacyProviderApplicationSiteAllocation 
 	 * @author TechFinium 
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 */
	public void prepareNew() {
		legacyproviderapplicationsiteallocation = new LegacyProviderApplicationSiteAllocation();
	}

/*
    public List<SelectItem> getLegacyProviderApplicationSiteAllocationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	legacyproviderapplicationsiteallocationInfo();
    	for (LegacyProviderApplicationSiteAllocation ug : legacyproviderapplicationsiteallocationList) {
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
    public List<LegacyProviderApplicationSiteAllocation> complete(String desc) {
		List<LegacyProviderApplicationSiteAllocation> l = null;
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
    
    public List<LegacyProviderApplicationSiteAllocation> getLegacyProviderApplicationSiteAllocationList() {
		return legacyproviderapplicationsiteallocationList;
	}
	public void setLegacyProviderApplicationSiteAllocationList(List<LegacyProviderApplicationSiteAllocation> legacyproviderapplicationsiteallocationList) {
		this.legacyproviderapplicationsiteallocationList = legacyproviderapplicationsiteallocationList;
	}
	public LegacyProviderApplicationSiteAllocation getLegacyproviderapplicationsiteallocation() {
		return legacyproviderapplicationsiteallocation;
	}
	public void setLegacyproviderapplicationsiteallocation(LegacyProviderApplicationSiteAllocation legacyproviderapplicationsiteallocation) {
		this.legacyproviderapplicationsiteallocation = legacyproviderapplicationsiteallocation;
	}

    public List<LegacyProviderApplicationSiteAllocation> getLegacyProviderApplicationSiteAllocationfilteredList() {
		return legacyproviderapplicationsiteallocationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param legacyproviderapplicationsiteallocationfilteredList the new legacyproviderapplicationsiteallocationfilteredList list
 	 * @see    LegacyProviderApplicationSiteAllocation
 	 */	
	public void setLegacyProviderApplicationSiteAllocationfilteredList(List<LegacyProviderApplicationSiteAllocation> legacyproviderapplicationsiteallocationfilteredList) {
		this.legacyproviderapplicationsiteallocationfilteredList = legacyproviderapplicationsiteallocationfilteredList;
	}

	
	public LazyDataModel<LegacyProviderApplicationSiteAllocation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyProviderApplicationSiteAllocation> dataModel) {
		this.dataModel = dataModel;
	}
	
}
