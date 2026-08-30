package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WspCompanyMainHistory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspCompanyMainHistoryService;

@ManagedBean(name = "wspcompanymainhistoryUI")
@ViewScoped
public class WspCompanyMainHistoryUI extends AbstractUI {

	private WspCompanyMainHistoryService service = new WspCompanyMainHistoryService();
	private List<WspCompanyMainHistory> wspcompanymainhistoryList = null;
	private List<WspCompanyMainHistory> wspcompanymainhistoryfilteredList = null;
	private WspCompanyMainHistory wspcompanymainhistory = null;
	private LazyDataModel<WspCompanyMainHistory> dataModel; 

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
	 * Initialize method to get all WspCompanyMainHistory and prepare a for a create of a new WspCompanyMainHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyMainHistory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		wspcompanymainhistoryInfo();
	}

	/**
	 * Get all WspCompanyMainHistory for data table
 	 * @author TechFinium 
 	 * @see    WspCompanyMainHistory
 	 */
	public void wspcompanymainhistoryInfo() {
//			dataModel = new WspCompanyMainHistoryDatamodel();
	}

	/**
	 * Insert WspCompanyMainHistory into database
 	 * @author TechFinium 
 	 * @see    WspCompanyMainHistory
 	 */
	public void wspcompanymainhistoryInsert() {
		try {
				 service.create(this.wspcompanymainhistory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanymainhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspCompanyMainHistory in database
 	 * @author TechFinium 
 	 * @see    WspCompanyMainHistory
 	 */
    public void wspcompanymainhistoryUpdate() {
		try {
				 service.update(this.wspcompanymainhistory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanymainhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspCompanyMainHistory from database
 	 * @author TechFinium 
 	 * @see    WspCompanyMainHistory
 	 */
	public void wspcompanymainhistoryDelete() {
		try {
			 service.delete(this.wspcompanymainhistory);
			  prepareNew();
			 wspcompanymainhistoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspCompanyMainHistory 
 	 * @author TechFinium 
 	 * @see    WspCompanyMainHistory
 	 */
	public void prepareNew() {
		wspcompanymainhistory = new WspCompanyMainHistory();
	}

/*
    public List<SelectItem> getWspCompanyMainHistoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspcompanymainhistoryInfo();
    	for (WspCompanyMainHistory ug : wspcompanymainhistoryList) {
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
    public List<WspCompanyMainHistory> complete(String desc) {
		List<WspCompanyMainHistory> l = null;
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
    
    public List<WspCompanyMainHistory> getWspCompanyMainHistoryList() {
		return wspcompanymainhistoryList;
	}
	public void setWspCompanyMainHistoryList(List<WspCompanyMainHistory> wspcompanymainhistoryList) {
		this.wspcompanymainhistoryList = wspcompanymainhistoryList;
	}
	public WspCompanyMainHistory getWspcompanymainhistory() {
		return wspcompanymainhistory;
	}
	public void setWspcompanymainhistory(WspCompanyMainHistory wspcompanymainhistory) {
		this.wspcompanymainhistory = wspcompanymainhistory;
	}

    public List<WspCompanyMainHistory> getWspCompanyMainHistoryfilteredList() {
		return wspcompanymainhistoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param wspcompanymainhistoryfilteredList the new wspcompanymainhistoryfilteredList list
 	 * @see    WspCompanyMainHistory
 	 */	
	public void setWspCompanyMainHistoryfilteredList(List<WspCompanyMainHistory> wspcompanymainhistoryfilteredList) {
		this.wspcompanymainhistoryfilteredList = wspcompanymainhistoryfilteredList;
	}

	
	public LazyDataModel<WspCompanyMainHistory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspCompanyMainHistory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
