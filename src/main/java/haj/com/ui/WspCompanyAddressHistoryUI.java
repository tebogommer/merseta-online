package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WspCompanyAddressHistory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspCompanyAddressHistoryService;

@ManagedBean(name = "wspcompanyaddresshistoryUI")
@ViewScoped
public class WspCompanyAddressHistoryUI extends AbstractUI {

	private WspCompanyAddressHistoryService service = new WspCompanyAddressHistoryService();
	private List<WspCompanyAddressHistory> wspcompanyaddresshistoryList = null;
	private List<WspCompanyAddressHistory> wspcompanyaddresshistoryfilteredList = null;
	private WspCompanyAddressHistory wspcompanyaddresshistory = null;
	private LazyDataModel<WspCompanyAddressHistory> dataModel; 

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
	 * Initialize method to get all WspCompanyAddressHistory and prepare a for a create of a new WspCompanyAddressHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyAddressHistory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		wspcompanyaddresshistoryInfo();
	}

	/**
	 * Get all WspCompanyAddressHistory for data table
 	 * @author TechFinium 
 	 * @see    WspCompanyAddressHistory
 	 */
	public void wspcompanyaddresshistoryInfo() {
//			dataModel = new WspCompanyAddressHistoryDatamodel();
	}

	/**
	 * Insert WspCompanyAddressHistory into database
 	 * @author TechFinium 
 	 * @see    WspCompanyAddressHistory
 	 */
	public void wspcompanyaddresshistoryInsert() {
		try {
				 service.create(this.wspcompanyaddresshistory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanyaddresshistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspCompanyAddressHistory in database
 	 * @author TechFinium 
 	 * @see    WspCompanyAddressHistory
 	 */
    public void wspcompanyaddresshistoryUpdate() {
		try {
				 service.update(this.wspcompanyaddresshistory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanyaddresshistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspCompanyAddressHistory from database
 	 * @author TechFinium 
 	 * @see    WspCompanyAddressHistory
 	 */
	public void wspcompanyaddresshistoryDelete() {
		try {
			 service.delete(this.wspcompanyaddresshistory);
			  prepareNew();
			 wspcompanyaddresshistoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspCompanyAddressHistory 
 	 * @author TechFinium 
 	 * @see    WspCompanyAddressHistory
 	 */
	public void prepareNew() {
		wspcompanyaddresshistory = new WspCompanyAddressHistory();
	}

/*
    public List<SelectItem> getWspCompanyAddressHistoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspcompanyaddresshistoryInfo();
    	for (WspCompanyAddressHistory ug : wspcompanyaddresshistoryList) {
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
    public List<WspCompanyAddressHistory> complete(String desc) {
		List<WspCompanyAddressHistory> l = null;
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
    
    public List<WspCompanyAddressHistory> getWspCompanyAddressHistoryList() {
		return wspcompanyaddresshistoryList;
	}
	public void setWspCompanyAddressHistoryList(List<WspCompanyAddressHistory> wspcompanyaddresshistoryList) {
		this.wspcompanyaddresshistoryList = wspcompanyaddresshistoryList;
	}
	public WspCompanyAddressHistory getWspcompanyaddresshistory() {
		return wspcompanyaddresshistory;
	}
	public void setWspcompanyaddresshistory(WspCompanyAddressHistory wspcompanyaddresshistory) {
		this.wspcompanyaddresshistory = wspcompanyaddresshistory;
	}

    public List<WspCompanyAddressHistory> getWspCompanyAddressHistoryfilteredList() {
		return wspcompanyaddresshistoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param wspcompanyaddresshistoryfilteredList the new wspcompanyaddresshistoryfilteredList list
 	 * @see    WspCompanyAddressHistory
 	 */	
	public void setWspCompanyAddressHistoryfilteredList(List<WspCompanyAddressHistory> wspcompanyaddresshistoryfilteredList) {
		this.wspcompanyaddresshistoryfilteredList = wspcompanyaddresshistoryfilteredList;
	}

	
	public LazyDataModel<WspCompanyAddressHistory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspCompanyAddressHistory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
