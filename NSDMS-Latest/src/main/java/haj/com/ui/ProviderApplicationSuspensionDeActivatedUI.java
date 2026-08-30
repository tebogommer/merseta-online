package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.ProviderApplicationSuspensionDeActivated;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ProviderApplicationSuspensionDeActivatedService;

@ManagedBean(name = "providerapplicationsuspensiondeactivatedUI")
@ViewScoped
public class ProviderApplicationSuspensionDeActivatedUI extends AbstractUI {

	private ProviderApplicationSuspensionDeActivatedService service = new ProviderApplicationSuspensionDeActivatedService();
	private List<ProviderApplicationSuspensionDeActivated> providerapplicationsuspensiondeactivatedList = null;
	private List<ProviderApplicationSuspensionDeActivated> providerapplicationsuspensiondeactivatedfilteredList = null;
	private ProviderApplicationSuspensionDeActivated providerapplicationsuspensiondeactivated = null;
	private LazyDataModel<ProviderApplicationSuspensionDeActivated> dataModel; 

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
	 * Initialize method to get all ProviderApplicationSuspensionDeActivated and prepare a for a create of a new ProviderApplicationSuspensionDeActivated
 	 * @author TechFinium 
 	 * @see    ProviderApplicationSuspensionDeActivated
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		providerapplicationsuspensiondeactivatedInfo();
	}

	/**
	 * Get all ProviderApplicationSuspensionDeActivated for data table
 	 * @author TechFinium 
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 */
	public void providerapplicationsuspensiondeactivatedInfo() {
//		dataModel = new ProviderApplicationSuspensionDeActivatedDatamodel();
	}

	/**
	 * Insert ProviderApplicationSuspensionDeActivated into database
 	 * @author TechFinium 
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 */
	public void providerapplicationsuspensiondeactivatedInsert() {
		try {
				 service.create(this.providerapplicationsuspensiondeactivated);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 providerapplicationsuspensiondeactivatedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ProviderApplicationSuspensionDeActivated in database
 	 * @author TechFinium 
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 */
    public void providerapplicationsuspensiondeactivatedUpdate() {
		try {
				 service.update(this.providerapplicationsuspensiondeactivated);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 providerapplicationsuspensiondeactivatedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ProviderApplicationSuspensionDeActivated from database
 	 * @author TechFinium 
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 */
	public void providerapplicationsuspensiondeactivatedDelete() {
		try {
			 service.delete(this.providerapplicationsuspensiondeactivated);
			  prepareNew();
			 providerapplicationsuspensiondeactivatedInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ProviderApplicationSuspensionDeActivated 
 	 * @author TechFinium 
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 */
	public void prepareNew() {
		providerapplicationsuspensiondeactivated = new ProviderApplicationSuspensionDeActivated();
	}

/*
    public List<SelectItem> getProviderApplicationSuspensionDeActivatedDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	providerapplicationsuspensiondeactivatedInfo();
    	for (ProviderApplicationSuspensionDeActivated ug : providerapplicationsuspensiondeactivatedList) {
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
    public List<ProviderApplicationSuspensionDeActivated> complete(String desc) {
		List<ProviderApplicationSuspensionDeActivated> l = null;
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
    
    public List<ProviderApplicationSuspensionDeActivated> getProviderApplicationSuspensionDeActivatedList() {
		return providerapplicationsuspensiondeactivatedList;
	}
	public void setProviderApplicationSuspensionDeActivatedList(List<ProviderApplicationSuspensionDeActivated> providerapplicationsuspensiondeactivatedList) {
		this.providerapplicationsuspensiondeactivatedList = providerapplicationsuspensiondeactivatedList;
	}
	public ProviderApplicationSuspensionDeActivated getProviderapplicationsuspensiondeactivated() {
		return providerapplicationsuspensiondeactivated;
	}
	public void setProviderapplicationsuspensiondeactivated(ProviderApplicationSuspensionDeActivated providerapplicationsuspensiondeactivated) {
		this.providerapplicationsuspensiondeactivated = providerapplicationsuspensiondeactivated;
	}

    public List<ProviderApplicationSuspensionDeActivated> getProviderApplicationSuspensionDeActivatedfilteredList() {
		return providerapplicationsuspensiondeactivatedfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param providerapplicationsuspensiondeactivatedfilteredList the new providerapplicationsuspensiondeactivatedfilteredList list
 	 * @see    ProviderApplicationSuspensionDeActivated
 	 */	
	public void setProviderApplicationSuspensionDeActivatedfilteredList(List<ProviderApplicationSuspensionDeActivated> providerapplicationsuspensiondeactivatedfilteredList) {
		this.providerapplicationsuspensiondeactivatedfilteredList = providerapplicationsuspensiondeactivatedfilteredList;
	}

	
	public LazyDataModel<ProviderApplicationSuspensionDeActivated> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ProviderApplicationSuspensionDeActivated> dataModel) {
		this.dataModel = dataModel;
	}
	
}
