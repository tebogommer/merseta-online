package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WspCompanyTrainingComitteeHistory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspCompanyTrainingComitteeHistoryService;

@ManagedBean(name = "wspcompanytrainingcomitteehistoryUI")
@ViewScoped
public class WspCompanyTrainingComitteeHistoryUI extends AbstractUI {

	private WspCompanyTrainingComitteeHistoryService service = new WspCompanyTrainingComitteeHistoryService();
	private List<WspCompanyTrainingComitteeHistory> wspcompanytrainingcomitteehistoryList = null;
	private List<WspCompanyTrainingComitteeHistory> wspcompanytrainingcomitteehistoryfilteredList = null;
	private WspCompanyTrainingComitteeHistory wspcompanytrainingcomitteehistory = null;
	private LazyDataModel<WspCompanyTrainingComitteeHistory> dataModel; 

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
	 * Initialize method to get all WspCompanyTrainingComitteeHistory and prepare a for a create of a new WspCompanyTrainingComitteeHistory
 	 * @author TechFinium 
 	 * @see    WspCompanyTrainingComitteeHistory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		wspcompanytrainingcomitteehistoryInfo();
	}

	/**
	 * Get all WspCompanyTrainingComitteeHistory for data table
 	 * @author TechFinium 
 	 * @see    WspCompanyTrainingComitteeHistory
 	 */
	public void wspcompanytrainingcomitteehistoryInfo() {
//			dataModel = new WspCompanyTrainingComitteeHistoryDatamodel();
	}

	/**
	 * Insert WspCompanyTrainingComitteeHistory into database
 	 * @author TechFinium 
 	 * @see    WspCompanyTrainingComitteeHistory
 	 */
	public void wspcompanytrainingcomitteehistoryInsert() {
		try {
				 service.create(this.wspcompanytrainingcomitteehistory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanytrainingcomitteehistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspCompanyTrainingComitteeHistory in database
 	 * @author TechFinium 
 	 * @see    WspCompanyTrainingComitteeHistory
 	 */
    public void wspcompanytrainingcomitteehistoryUpdate() {
		try {
				 service.update(this.wspcompanytrainingcomitteehistory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspcompanytrainingcomitteehistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspCompanyTrainingComitteeHistory from database
 	 * @author TechFinium 
 	 * @see    WspCompanyTrainingComitteeHistory
 	 */
	public void wspcompanytrainingcomitteehistoryDelete() {
		try {
			 service.delete(this.wspcompanytrainingcomitteehistory);
			  prepareNew();
			 wspcompanytrainingcomitteehistoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspCompanyTrainingComitteeHistory 
 	 * @author TechFinium 
 	 * @see    WspCompanyTrainingComitteeHistory
 	 */
	public void prepareNew() {
		wspcompanytrainingcomitteehistory = new WspCompanyTrainingComitteeHistory();
	}

/*
    public List<SelectItem> getWspCompanyTrainingComitteeHistoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspcompanytrainingcomitteehistoryInfo();
    	for (WspCompanyTrainingComitteeHistory ug : wspcompanytrainingcomitteehistoryList) {
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
    public List<WspCompanyTrainingComitteeHistory> complete(String desc) {
		List<WspCompanyTrainingComitteeHistory> l = null;
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
    
    public List<WspCompanyTrainingComitteeHistory> getWspCompanyTrainingComitteeHistoryList() {
		return wspcompanytrainingcomitteehistoryList;
	}
	public void setWspCompanyTrainingComitteeHistoryList(List<WspCompanyTrainingComitteeHistory> wspcompanytrainingcomitteehistoryList) {
		this.wspcompanytrainingcomitteehistoryList = wspcompanytrainingcomitteehistoryList;
	}
	public WspCompanyTrainingComitteeHistory getWspcompanytrainingcomitteehistory() {
		return wspcompanytrainingcomitteehistory;
	}
	public void setWspcompanytrainingcomitteehistory(WspCompanyTrainingComitteeHistory wspcompanytrainingcomitteehistory) {
		this.wspcompanytrainingcomitteehistory = wspcompanytrainingcomitteehistory;
	}

    public List<WspCompanyTrainingComitteeHistory> getWspCompanyTrainingComitteeHistoryfilteredList() {
		return wspcompanytrainingcomitteehistoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param wspcompanytrainingcomitteehistoryfilteredList the new wspcompanytrainingcomitteehistoryfilteredList list
 	 * @see    WspCompanyTrainingComitteeHistory
 	 */	
	public void setWspCompanyTrainingComitteeHistoryfilteredList(List<WspCompanyTrainingComitteeHistory> wspcompanytrainingcomitteehistoryfilteredList) {
		this.wspcompanytrainingcomitteehistoryfilteredList = wspcompanytrainingcomitteehistoryfilteredList;
	}

	
	public LazyDataModel<WspCompanyTrainingComitteeHistory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspCompanyTrainingComitteeHistory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
