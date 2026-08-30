package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WspCompanyHistory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspCompanyHistoryService;

@ManagedBean(name = "wspcompanyhistoryUI")
@ViewScoped
public class WspCompanyHistoryUI extends AbstractUI {

	private WspCompanyHistoryService service = new WspCompanyHistoryService();
	private List<WspCompanyHistory> wspcompanyhistoryList = null;
	private List<WspCompanyHistory> wspcompanyhistoryfilteredList = null;
	private WspCompanyHistory wspcompanyhistory = null;
	private LazyDataModel<WspCompanyHistory> dataModel; 

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
	 * Initialize method to get all WspCompanyHistory and prepare a for a create of a new WspCompanyHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyHistory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		wspcompanyhistoryInfo();
	}

	/**
	 * Get all WspCompanyHistory for data table
 	 * @author TechFinium 
 	 * @see    WspCompanyHistory
 	 */
	public void wspcompanyhistoryInfo() {
//			dataModel = new WspCompanyHistoryDatamodel();
	}

	/**
	 * Insert WspCompanyHistory into database
 	 * @author TechFinium 
 	 * @see    WspCompanyHistory
 	 */
	public void wspcompanyhistoryInsert() {
		try {
				 service.create(this.wspcompanyhistory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanyhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspCompanyHistory in database
 	 * @author TechFinium 
 	 * @see    WspCompanyHistory
 	 */
    public void wspcompanyhistoryUpdate() {
		try {
				 service.update(this.wspcompanyhistory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanyhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspCompanyHistory from database
 	 * @author TechFinium 
 	 * @see    WspCompanyHistory
 	 */
	public void wspcompanyhistoryDelete() {
		try {
			 service.delete(this.wspcompanyhistory);
			  prepareNew();
			 wspcompanyhistoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspCompanyHistory 
 	 * @author TechFinium 
 	 * @see    WspCompanyHistory
 	 */
	public void prepareNew() {
		wspcompanyhistory = new WspCompanyHistory();
	}

/*
    public List<SelectItem> getWspCompanyHistoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspcompanyhistoryInfo();
    	for (WspCompanyHistory ug : wspcompanyhistoryList) {
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
    public List<WspCompanyHistory> complete(String desc) {
		List<WspCompanyHistory> l = null;
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
    
    public List<WspCompanyHistory> getWspCompanyHistoryList() {
		return wspcompanyhistoryList;
	}
	public void setWspCompanyHistoryList(List<WspCompanyHistory> wspcompanyhistoryList) {
		this.wspcompanyhistoryList = wspcompanyhistoryList;
	}
	public WspCompanyHistory getWspcompanyhistory() {
		return wspcompanyhistory;
	}
	public void setWspcompanyhistory(WspCompanyHistory wspcompanyhistory) {
		this.wspcompanyhistory = wspcompanyhistory;
	}

    public List<WspCompanyHistory> getWspCompanyHistoryfilteredList() {
		return wspcompanyhistoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param wspcompanyhistoryfilteredList the new wspcompanyhistoryfilteredList list
 	 * @see    WspCompanyHistory
 	 */	
	public void setWspCompanyHistoryfilteredList(List<WspCompanyHistory> wspcompanyhistoryfilteredList) {
		this.wspcompanyhistoryfilteredList = wspcompanyhistoryfilteredList;
	}

	
	public LazyDataModel<WspCompanyHistory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspCompanyHistory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
