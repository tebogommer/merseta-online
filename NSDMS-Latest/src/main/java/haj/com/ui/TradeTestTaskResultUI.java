package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.TradeTestTaskResult;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TradeTestTaskResultService;

@ManagedBean(name = "tradetesttaskresultUI")
@ViewScoped
public class TradeTestTaskResultUI extends AbstractUI {

	private TradeTestTaskResultService service = new TradeTestTaskResultService();
	private List<TradeTestTaskResult> tradetesttaskresultList = null;
	private List<TradeTestTaskResult> tradetesttaskresultfilteredList = null;
	private TradeTestTaskResult tradetesttaskresult = null;
	private LazyDataModel<TradeTestTaskResult> dataModel; 

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
	 * Initialize method to get all TradeTestTaskResult and prepare a for a create of a new TradeTestTaskResult
 	 * @author TechFinium 
 	 * @see    TradeTestTaskResult
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		tradetesttaskresultInfo();
	}

	/**
	 * Get all TradeTestTaskResult for data table
 	 * @author TechFinium 
 	 * @see    TradeTestTaskResult
 	 */
	public void tradetesttaskresultInfo() {
//			dataModel = new TradeTestTaskResultDatamodel();
	}

	/**
	 * Insert TradeTestTaskResult into database
 	 * @author TechFinium 
 	 * @see    TradeTestTaskResult
 	 */
	public void tradetesttaskresultInsert() {
		try {
				 service.create(this.tradetesttaskresult);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 tradetesttaskresultInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TradeTestTaskResult in database
 	 * @author TechFinium 
 	 * @see    TradeTestTaskResult
 	 */
    public void tradetesttaskresultUpdate() {
		try {
				 service.update(this.tradetesttaskresult);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 tradetesttaskresultInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TradeTestTaskResult from database
 	 * @author TechFinium 
 	 * @see    TradeTestTaskResult
 	 */
	public void tradetesttaskresultDelete() {
		try {
			 service.delete(this.tradetesttaskresult);
			  prepareNew();
			 tradetesttaskresultInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TradeTestTaskResult 
 	 * @author TechFinium 
 	 * @see    TradeTestTaskResult
 	 */
	public void prepareNew() {
		tradetesttaskresult = new TradeTestTaskResult();
	}

/*
    public List<SelectItem> getTradeTestTaskResultDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	tradetesttaskresultInfo();
    	for (TradeTestTaskResult ug : tradetesttaskresultList) {
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
    public List<TradeTestTaskResult> complete(String desc) {
		List<TradeTestTaskResult> l = null;
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
    
    public List<TradeTestTaskResult> getTradeTestTaskResultList() {
		return tradetesttaskresultList;
	}
	public void setTradeTestTaskResultList(List<TradeTestTaskResult> tradetesttaskresultList) {
		this.tradetesttaskresultList = tradetesttaskresultList;
	}
	public TradeTestTaskResult getTradetesttaskresult() {
		return tradetesttaskresult;
	}
	public void setTradetesttaskresult(TradeTestTaskResult tradetesttaskresult) {
		this.tradetesttaskresult = tradetesttaskresult;
	}

    public List<TradeTestTaskResult> getTradeTestTaskResultfilteredList() {
		return tradetesttaskresultfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param tradetesttaskresultfilteredList the new tradetesttaskresultfilteredList list
 	 * @see    TradeTestTaskResult
 	 */	
	public void setTradeTestTaskResultfilteredList(List<TradeTestTaskResult> tradetesttaskresultfilteredList) {
		this.tradetesttaskresultfilteredList = tradetesttaskresultfilteredList;
	}

	
	public LazyDataModel<TradeTestTaskResult> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TradeTestTaskResult> dataModel) {
		this.dataModel = dataModel;
	}
	
}
