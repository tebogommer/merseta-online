package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WspSdfCompanyHistory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspSdfCompanyHistoryService;

@ManagedBean(name = "wspsdfcompanyhistoryUI")
@ViewScoped
public class WspSdfCompanyHistoryUI extends AbstractUI {

	private WspSdfCompanyHistoryService service = new WspSdfCompanyHistoryService();
	private List<WspSdfCompanyHistory> wspsdfcompanyhistoryList = null;
	private List<WspSdfCompanyHistory> wspsdfcompanyhistoryfilteredList = null;
	private WspSdfCompanyHistory wspsdfcompanyhistory = null;
	private LazyDataModel<WspSdfCompanyHistory> dataModel; 

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
	 * Initialize method to get all WspSdfCompanyHistory and prepare a for a create of a new WspSdfCompanyHistory
 	 * @author TechFinium 
 	 * @see    WspSdfCompanyHistory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		wspsdfcompanyhistoryInfo();
	}

	/**
	 * Get all WspSdfCompanyHistory for data table
 	 * @author TechFinium 
 	 * @see    WspSdfCompanyHistory
 	 */
	public void wspsdfcompanyhistoryInfo() {
//			dataModel = new WspSdfCompanyHistoryDatamodel();
	}

	/**
	 * Insert WspSdfCompanyHistory into database
 	 * @author TechFinium 
 	 * @see    WspSdfCompanyHistory
 	 */
	public void wspsdfcompanyhistoryInsert() {
		try {
				 service.create(this.wspsdfcompanyhistory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspsdfcompanyhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspSdfCompanyHistory in database
 	 * @author TechFinium 
 	 * @see    WspSdfCompanyHistory
 	 */
    public void wspsdfcompanyhistoryUpdate() {
		try {
				 service.update(this.wspsdfcompanyhistory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspsdfcompanyhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspSdfCompanyHistory from database
 	 * @author TechFinium 
 	 * @see    WspSdfCompanyHistory
 	 */
	public void wspsdfcompanyhistoryDelete() {
		try {
			 service.delete(this.wspsdfcompanyhistory);
			  prepareNew();
			 wspsdfcompanyhistoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspSdfCompanyHistory 
 	 * @author TechFinium 
 	 * @see    WspSdfCompanyHistory
 	 */
	public void prepareNew() {
		wspsdfcompanyhistory = new WspSdfCompanyHistory();
	}

/*
    public List<SelectItem> getWspSdfCompanyHistoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspsdfcompanyhistoryInfo();
    	for (WspSdfCompanyHistory ug : wspsdfcompanyhistoryList) {
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
    public List<WspSdfCompanyHistory> complete(String desc) {
		List<WspSdfCompanyHistory> l = null;
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
    
    public List<WspSdfCompanyHistory> getWspSdfCompanyHistoryList() {
		return wspsdfcompanyhistoryList;
	}
	public void setWspSdfCompanyHistoryList(List<WspSdfCompanyHistory> wspsdfcompanyhistoryList) {
		this.wspsdfcompanyhistoryList = wspsdfcompanyhistoryList;
	}
	public WspSdfCompanyHistory getWspsdfcompanyhistory() {
		return wspsdfcompanyhistory;
	}
	public void setWspsdfcompanyhistory(WspSdfCompanyHistory wspsdfcompanyhistory) {
		this.wspsdfcompanyhistory = wspsdfcompanyhistory;
	}

    public List<WspSdfCompanyHistory> getWspSdfCompanyHistoryfilteredList() {
		return wspsdfcompanyhistoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param wspsdfcompanyhistoryfilteredList the new wspsdfcompanyhistoryfilteredList list
 	 * @see    WspSdfCompanyHistory
 	 */	
	public void setWspSdfCompanyHistoryfilteredList(List<WspSdfCompanyHistory> wspsdfcompanyhistoryfilteredList) {
		this.wspsdfcompanyhistoryfilteredList = wspsdfcompanyhistoryfilteredList;
	}

	
	public LazyDataModel<WspSdfCompanyHistory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspSdfCompanyHistory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
