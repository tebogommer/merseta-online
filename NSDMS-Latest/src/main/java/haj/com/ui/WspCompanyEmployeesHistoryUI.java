package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WspCompanyEmployeesHistory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspCompanyEmployeesHistoryService;

@ManagedBean(name = "wspcompanyemployeeshistoryUI")
@ViewScoped
public class WspCompanyEmployeesHistoryUI extends AbstractUI {

	private WspCompanyEmployeesHistoryService service = new WspCompanyEmployeesHistoryService();
	private List<WspCompanyEmployeesHistory> wspcompanyemployeeshistoryList = null;
	private List<WspCompanyEmployeesHistory> wspcompanyemployeeshistoryfilteredList = null;
	private WspCompanyEmployeesHistory wspcompanyemployeeshistory = null;
	private LazyDataModel<WspCompanyEmployeesHistory> dataModel; 

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
	 * Initialize method to get all WspCompanyEmployeesHistory and prepare a for a create of a new WspCompanyEmployeesHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyEmployeesHistory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		wspcompanyemployeeshistoryInfo();
	}

	/**
	 * Get all WspCompanyEmployeesHistory for data table
 	 * @author TechFinium 
 	 * @see    WspCompanyEmployeesHistory
 	 */
	public void wspcompanyemployeeshistoryInfo() {
//			dataModel = new WspCompanyEmployeesHistoryDatamodel();
	}

	/**
	 * Insert WspCompanyEmployeesHistory into database
 	 * @author TechFinium 
 	 * @see    WspCompanyEmployeesHistory
 	 */
	public void wspcompanyemployeeshistoryInsert() {
		try {
				 service.create(this.wspcompanyemployeeshistory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanyemployeeshistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspCompanyEmployeesHistory in database
 	 * @author TechFinium 
 	 * @see    WspCompanyEmployeesHistory
 	 */
    public void wspcompanyemployeeshistoryUpdate() {
		try {
				 service.update(this.wspcompanyemployeeshistory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanyemployeeshistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspCompanyEmployeesHistory from database
 	 * @author TechFinium 
 	 * @see    WspCompanyEmployeesHistory
 	 */
	public void wspcompanyemployeeshistoryDelete() {
		try {
			 service.delete(this.wspcompanyemployeeshistory);
			  prepareNew();
			 wspcompanyemployeeshistoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspCompanyEmployeesHistory 
 	 * @author TechFinium 
 	 * @see    WspCompanyEmployeesHistory
 	 */
	public void prepareNew() {
		wspcompanyemployeeshistory = new WspCompanyEmployeesHistory();
	}

/*
    public List<SelectItem> getWspCompanyEmployeesHistoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspcompanyemployeeshistoryInfo();
    	for (WspCompanyEmployeesHistory ug : wspcompanyemployeeshistoryList) {
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
    public List<WspCompanyEmployeesHistory> complete(String desc) {
		List<WspCompanyEmployeesHistory> l = null;
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
    
    public List<WspCompanyEmployeesHistory> getWspCompanyEmployeesHistoryList() {
		return wspcompanyemployeeshistoryList;
	}
	public void setWspCompanyEmployeesHistoryList(List<WspCompanyEmployeesHistory> wspcompanyemployeeshistoryList) {
		this.wspcompanyemployeeshistoryList = wspcompanyemployeeshistoryList;
	}
	public WspCompanyEmployeesHistory getWspcompanyemployeeshistory() {
		return wspcompanyemployeeshistory;
	}
	public void setWspcompanyemployeeshistory(WspCompanyEmployeesHistory wspcompanyemployeeshistory) {
		this.wspcompanyemployeeshistory = wspcompanyemployeeshistory;
	}

    public List<WspCompanyEmployeesHistory> getWspCompanyEmployeesHistoryfilteredList() {
		return wspcompanyemployeeshistoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param wspcompanyemployeeshistoryfilteredList the new wspcompanyemployeeshistoryfilteredList list
 	 * @see    WspCompanyEmployeesHistory
 	 */	
	public void setWspCompanyEmployeesHistoryfilteredList(List<WspCompanyEmployeesHistory> wspcompanyemployeeshistoryfilteredList) {
		this.wspcompanyemployeeshistoryfilteredList = wspcompanyemployeeshistoryfilteredList;
	}

	
	public LazyDataModel<WspCompanyEmployeesHistory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspCompanyEmployeesHistory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
