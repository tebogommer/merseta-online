package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.SdpCompanyActions;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SdpCompanyActionsService;

@ManagedBean(name = "sdpcompanyactionsUI")
@ViewScoped
public class SdpCompanyActionsUI extends AbstractUI {

	private SdpCompanyActionsService service = new SdpCompanyActionsService();
	private List<SdpCompanyActions> sdpcompanyactionsList = null;
	private List<SdpCompanyActions> sdpcompanyactionsfilteredList = null;
	private SdpCompanyActions sdpcompanyactions = null;
	private LazyDataModel<SdpCompanyActions> dataModel; 

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
	 * Initialize method to get all SdpCompanyActions and prepare a for a create of a new SdpCompanyActions
 	 * @author TechFinium 
 	 * @see    SdpCompanyActions
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		sdpcompanyactionsInfo();
	}

	/**
	 * Get all SdpCompanyActions for data table
 	 * @author TechFinium 
 	 * @see    SdpCompanyActions
 	 */
	public void sdpcompanyactionsInfo() {
//			dataModel = new SdpCompanyActionsDatamodel();
	}

	/**
	 * Insert SdpCompanyActions into database
 	 * @author TechFinium 
 	 * @see    SdpCompanyActions
 	 */
	public void sdpcompanyactionsInsert() {
		try {
				 service.create(this.sdpcompanyactions);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sdpcompanyactionsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SdpCompanyActions in database
 	 * @author TechFinium 
 	 * @see    SdpCompanyActions
 	 */
    public void sdpcompanyactionsUpdate() {
		try {
				 service.update(this.sdpcompanyactions);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sdpcompanyactionsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SdpCompanyActions from database
 	 * @author TechFinium 
 	 * @see    SdpCompanyActions
 	 */
	public void sdpcompanyactionsDelete() {
		try {
			 service.delete(this.sdpcompanyactions);
			  prepareNew();
			 sdpcompanyactionsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SdpCompanyActions 
 	 * @author TechFinium 
 	 * @see    SdpCompanyActions
 	 */
	public void prepareNew() {
		sdpcompanyactions = new SdpCompanyActions();
	}

/*
    public List<SelectItem> getSdpCompanyActionsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sdpcompanyactionsInfo();
    	for (SdpCompanyActions ug : sdpcompanyactionsList) {
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
    public List<SdpCompanyActions> complete(String desc) {
		List<SdpCompanyActions> l = null;
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
    
    public List<SdpCompanyActions> getSdpCompanyActionsList() {
		return sdpcompanyactionsList;
	}
	public void setSdpCompanyActionsList(List<SdpCompanyActions> sdpcompanyactionsList) {
		this.sdpcompanyactionsList = sdpcompanyactionsList;
	}
	public SdpCompanyActions getSdpcompanyactions() {
		return sdpcompanyactions;
	}
	public void setSdpcompanyactions(SdpCompanyActions sdpcompanyactions) {
		this.sdpcompanyactions = sdpcompanyactions;
	}

    public List<SdpCompanyActions> getSdpCompanyActionsfilteredList() {
		return sdpcompanyactionsfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param sdpcompanyactionsfilteredList the new sdpcompanyactionsfilteredList list
 	 * @see    SdpCompanyActions
 	 */	
	public void setSdpCompanyActionsfilteredList(List<SdpCompanyActions> sdpcompanyactionsfilteredList) {
		this.sdpcompanyactionsfilteredList = sdpcompanyactionsfilteredList;
	}

	
	public LazyDataModel<SdpCompanyActions> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SdpCompanyActions> dataModel) {
		this.dataModel = dataModel;
	}
	
}
