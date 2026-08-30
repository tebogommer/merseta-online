package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.ProviderApplicationTradeTestAssessorsLink;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ProviderApplicationTradeTestAssessorsLinkService;

@ManagedBean(name = "providerapplicationtradetestassessorslinkUI")
@ViewScoped
public class ProviderApplicationTradeTestAssessorsLinkUI extends AbstractUI {

	private ProviderApplicationTradeTestAssessorsLinkService service = new ProviderApplicationTradeTestAssessorsLinkService();
	private List<ProviderApplicationTradeTestAssessorsLink> providerapplicationtradetestassessorslinkList = null;
	private List<ProviderApplicationTradeTestAssessorsLink> providerapplicationtradetestassessorslinkfilteredList = null;
	private ProviderApplicationTradeTestAssessorsLink providerapplicationtradetestassessorslink = null;
	private LazyDataModel<ProviderApplicationTradeTestAssessorsLink> dataModel; 

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
	 * Initialize method to get all ProviderApplicationTradeTestAssessorsLink and prepare a for a create of a new ProviderApplicationTradeTestAssessorsLink
 	 * @author TechFinium 
 	 * @see    ProviderApplicationTradeTestAssessorsLink
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		providerapplicationtradetestassessorslinkInfo();
	}

	/**
	 * Get all ProviderApplicationTradeTestAssessorsLink for data table
 	 * @author TechFinium 
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 */
	public void providerapplicationtradetestassessorslinkInfo() {
//		dataModel = new ProviderApplicationTradeTestAssessorsLinkDatamodel();
	}

	/**
	 * Insert ProviderApplicationTradeTestAssessorsLink into database
 	 * @author TechFinium 
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 */
	public void providerapplicationtradetestassessorslinkInsert() {
		try {
				 service.create(this.providerapplicationtradetestassessorslink);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 providerapplicationtradetestassessorslinkInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ProviderApplicationTradeTestAssessorsLink in database
 	 * @author TechFinium 
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 */
    public void providerapplicationtradetestassessorslinkUpdate() {
		try {
				 service.update(this.providerapplicationtradetestassessorslink);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 providerapplicationtradetestassessorslinkInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ProviderApplicationTradeTestAssessorsLink from database
 	 * @author TechFinium 
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 */
	public void providerapplicationtradetestassessorslinkDelete() {
		try {
			 service.delete(this.providerapplicationtradetestassessorslink);
			  prepareNew();
			 providerapplicationtradetestassessorslinkInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ProviderApplicationTradeTestAssessorsLink 
 	 * @author TechFinium 
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 */
	public void prepareNew() {
		providerapplicationtradetestassessorslink = new ProviderApplicationTradeTestAssessorsLink();
	}

/*
    public List<SelectItem> getProviderApplicationTradeTestAssessorsLinkDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	providerapplicationtradetestassessorslinkInfo();
    	for (ProviderApplicationTradeTestAssessorsLink ug : providerapplicationtradetestassessorslinkList) {
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
    public List<ProviderApplicationTradeTestAssessorsLink> complete(String desc) {
		List<ProviderApplicationTradeTestAssessorsLink> l = null;
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
    
    public List<ProviderApplicationTradeTestAssessorsLink> getProviderApplicationTradeTestAssessorsLinkList() {
		return providerapplicationtradetestassessorslinkList;
	}
	public void setProviderApplicationTradeTestAssessorsLinkList(List<ProviderApplicationTradeTestAssessorsLink> providerapplicationtradetestassessorslinkList) {
		this.providerapplicationtradetestassessorslinkList = providerapplicationtradetestassessorslinkList;
	}
	public ProviderApplicationTradeTestAssessorsLink getProviderapplicationtradetestassessorslink() {
		return providerapplicationtradetestassessorslink;
	}
	public void setProviderapplicationtradetestassessorslink(ProviderApplicationTradeTestAssessorsLink providerapplicationtradetestassessorslink) {
		this.providerapplicationtradetestassessorslink = providerapplicationtradetestassessorslink;
	}

    public List<ProviderApplicationTradeTestAssessorsLink> getProviderApplicationTradeTestAssessorsLinkfilteredList() {
		return providerapplicationtradetestassessorslinkfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param providerapplicationtradetestassessorslinkfilteredList the new providerapplicationtradetestassessorslinkfilteredList list
 	 * @see    ProviderApplicationTradeTestAssessorsLink
 	 */	
	public void setProviderApplicationTradeTestAssessorsLinkfilteredList(List<ProviderApplicationTradeTestAssessorsLink> providerapplicationtradetestassessorslinkfilteredList) {
		this.providerapplicationtradetestassessorslinkfilteredList = providerapplicationtradetestassessorslinkfilteredList;
	}

	
	public LazyDataModel<ProviderApplicationTradeTestAssessorsLink> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ProviderApplicationTradeTestAssessorsLink> dataModel) {
		this.dataModel = dataModel;
	}
	
}
