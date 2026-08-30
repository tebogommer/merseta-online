package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.NambDecisionHistory;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.NambDecisionHistoryService;

@ManagedBean(name = "nambdecisionhistoryUI")
@ViewScoped
public class NambDecisionHistoryUI extends AbstractUI {

	private NambDecisionHistoryService service = new NambDecisionHistoryService();
	private List<NambDecisionHistory> nambdecisionhistoryList = null;
	private List<NambDecisionHistory> nambdecisionhistoryfilteredList = null;
	private NambDecisionHistory nambdecisionhistory = null;
	private LazyDataModel<NambDecisionHistory> dataModel; 

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
	 * Initialize method to get all NambDecisionHistory and prepare a for a create of a new NambDecisionHistory
 	 * @author TechFinium 
 	 * @see    NambDecisionHistory
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		nambdecisionhistoryInfo();
	}

	/**
	 * Get all NambDecisionHistory for data table
 	 * @author TechFinium 
 	 * @see    NambDecisionHistory
 	 */
	public void nambdecisionhistoryInfo() {
//		dataModel = new NambDecisionHistoryDatamodel();
	}

	/**
	 * Insert NambDecisionHistory into database
 	 * @author TechFinium 
 	 * @see    NambDecisionHistory
 	 */
	public void nambdecisionhistoryInsert() {
		try {
				 service.create(this.nambdecisionhistory);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 nambdecisionhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update NambDecisionHistory in database
 	 * @author TechFinium 
 	 * @see    NambDecisionHistory
 	 */
    public void nambdecisionhistoryUpdate() {
		try {
				 service.update(this.nambdecisionhistory);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 nambdecisionhistoryInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete NambDecisionHistory from database
 	 * @author TechFinium 
 	 * @see    NambDecisionHistory
 	 */
	public void nambdecisionhistoryDelete() {
		try {
			 service.delete(this.nambdecisionhistory);
			  prepareNew();
			 nambdecisionhistoryInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of NambDecisionHistory 
 	 * @author TechFinium 
 	 * @see    NambDecisionHistory
 	 */
	public void prepareNew() {
		nambdecisionhistory = new NambDecisionHistory();
	}

/*
    public List<SelectItem> getNambDecisionHistoryDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	nambdecisionhistoryInfo();
    	for (NambDecisionHistory ug : nambdecisionhistoryList) {
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
    public List<NambDecisionHistory> complete(String desc) {
		List<NambDecisionHistory> l = null;
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
    
    public List<NambDecisionHistory> getNambDecisionHistoryList() {
		return nambdecisionhistoryList;
	}
	public void setNambDecisionHistoryList(List<NambDecisionHistory> nambdecisionhistoryList) {
		this.nambdecisionhistoryList = nambdecisionhistoryList;
	}
	public NambDecisionHistory getNambdecisionhistory() {
		return nambdecisionhistory;
	}
	public void setNambdecisionhistory(NambDecisionHistory nambdecisionhistory) {
		this.nambdecisionhistory = nambdecisionhistory;
	}

    public List<NambDecisionHistory> getNambDecisionHistoryfilteredList() {
		return nambdecisionhistoryfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param nambdecisionhistoryfilteredList the new nambdecisionhistoryfilteredList list
 	 * @see    NambDecisionHistory
 	 */	
	public void setNambDecisionHistoryfilteredList(List<NambDecisionHistory> nambdecisionhistoryfilteredList) {
		this.nambdecisionhistoryfilteredList = nambdecisionhistoryfilteredList;
	}

	
	public LazyDataModel<NambDecisionHistory> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<NambDecisionHistory> dataModel) {
		this.dataModel = dataModel;
	}
	
}
