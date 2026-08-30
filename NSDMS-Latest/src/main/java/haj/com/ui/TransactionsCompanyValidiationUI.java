package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.TransactionsCompanyValidiation;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TransactionsCompanyValidiationService;

@ManagedBean(name = "transactionscompanyvalidiationUI")
@ViewScoped
public class TransactionsCompanyValidiationUI extends AbstractUI {

	private TransactionsCompanyValidiationService service = new TransactionsCompanyValidiationService();
	private List<TransactionsCompanyValidiation> transactionscompanyvalidiationList = null;
	private List<TransactionsCompanyValidiation> transactionscompanyvalidiationfilteredList = null;
	private TransactionsCompanyValidiation transactionscompanyvalidiation = null;
	private LazyDataModel<TransactionsCompanyValidiation> dataModel; 

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
	 * Initialize method to get all TransactionsCompanyValidiation and prepare a for a create of a new TransactionsCompanyValidiation
 	 * @author TechFinium 
 	 * @see    TransactionsCompanyValidiation
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		transactionscompanyvalidiationInfo();
	}

	/**
	 * Get all TransactionsCompanyValidiation for data table
 	 * @author TechFinium 
 	 * @see    TransactionsCompanyValidiation
 	 */
	public void transactionscompanyvalidiationInfo() {
//			dataModel = new TransactionsCompanyValidiationDatamodel();
	}

	/**
	 * Insert TransactionsCompanyValidiation into database
 	 * @author TechFinium 
 	 * @see    TransactionsCompanyValidiation
 	 */
	public void transactionscompanyvalidiationInsert() {
		try {
				 service.create(this.transactionscompanyvalidiation);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 transactionscompanyvalidiationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TransactionsCompanyValidiation in database
 	 * @author TechFinium 
 	 * @see    TransactionsCompanyValidiation
 	 */
    public void transactionscompanyvalidiationUpdate() {
		try {
				 service.update(this.transactionscompanyvalidiation);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 transactionscompanyvalidiationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TransactionsCompanyValidiation from database
 	 * @author TechFinium 
 	 * @see    TransactionsCompanyValidiation
 	 */
	public void transactionscompanyvalidiationDelete() {
		try {
			 service.delete(this.transactionscompanyvalidiation);
			  prepareNew();
			 transactionscompanyvalidiationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TransactionsCompanyValidiation 
 	 * @author TechFinium 
 	 * @see    TransactionsCompanyValidiation
 	 */
	public void prepareNew() {
		transactionscompanyvalidiation = new TransactionsCompanyValidiation();
	}

/*
    public List<SelectItem> getTransactionsCompanyValidiationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	transactionscompanyvalidiationInfo();
    	for (TransactionsCompanyValidiation ug : transactionscompanyvalidiationList) {
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
    public List<TransactionsCompanyValidiation> complete(String desc) {
		List<TransactionsCompanyValidiation> l = null;
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
    
    public List<TransactionsCompanyValidiation> getTransactionsCompanyValidiationList() {
		return transactionscompanyvalidiationList;
	}
	public void setTransactionsCompanyValidiationList(List<TransactionsCompanyValidiation> transactionscompanyvalidiationList) {
		this.transactionscompanyvalidiationList = transactionscompanyvalidiationList;
	}
	public TransactionsCompanyValidiation getTransactionscompanyvalidiation() {
		return transactionscompanyvalidiation;
	}
	public void setTransactionscompanyvalidiation(TransactionsCompanyValidiation transactionscompanyvalidiation) {
		this.transactionscompanyvalidiation = transactionscompanyvalidiation;
	}

    public List<TransactionsCompanyValidiation> getTransactionsCompanyValidiationfilteredList() {
		return transactionscompanyvalidiationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param transactionscompanyvalidiationfilteredList the new transactionscompanyvalidiationfilteredList list
 	 * @see    TransactionsCompanyValidiation
 	 */	
	public void setTransactionsCompanyValidiationfilteredList(List<TransactionsCompanyValidiation> transactionscompanyvalidiationfilteredList) {
		this.transactionscompanyvalidiationfilteredList = transactionscompanyvalidiationfilteredList;
	}

	
	public LazyDataModel<TransactionsCompanyValidiation> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TransactionsCompanyValidiation> dataModel) {
		this.dataModel = dataModel;
	}
	
}
