package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Bank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.BankService;

// TODO: Auto-generated Javadoc
/**
 * The Class BankUI.
 */
@ManagedBean(name = "bankUI")
@ViewScoped
public class BankUI extends AbstractUI {

	/** The service. */
	private BankService service = new BankService();
	
	/** The bank list. */
	private List<Bank> bankList = null;
	
	/** The bankfiltered list. */
	private List<Bank> bankfilteredList = null;
	
	/** The bank. */
	private Bank bank = null;
	
	/** The data model. */
	private LazyDataModel<Bank> dataModel; 

    /**
     * Inits the.
     */
    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all Bank and prepare a for a create of a new Bank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Bank
	 */
	private void runInit() throws Exception {
		prepareNew();
		bankInfo();
	}

	/**
	 * Get all Bank for data table.
	 *
	 * @author TechFinium
	 * @see    Bank
	 */
	public void bankInfo() {
	 
			
			 dataModel = new LazyDataModel<Bank>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Bank> retorno = new  ArrayList<Bank>();
			   
			   @Override 
			   public List<Bank> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allBank(Bank.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Bank.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Bank obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Bank getRowData(String rowKey) {
			        for(Bank obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Bank into database.
	 *
	 * @author TechFinium
	 * @see    Bank
	 */
	public void bankInsert() {
		try {
				 service.create(this.bank);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 bankInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Bank in database.
	 *
	 * @author TechFinium
	 * @see    Bank
	 */
    public void bankUpdate() {
		try {
				 service.update(this.bank);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 bankInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Bank from database.
	 *
	 * @author TechFinium
	 * @see    Bank
	 */
	public void bankDelete() {
		try {
			 service.delete(this.bank);
			  prepareNew();
			 bankInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Bank .
	 *
	 * @author TechFinium
	 * @see    Bank
	 */
	public void prepareNew() {
		bank = new Bank();
	}

/*
    public List<SelectItem> getBankDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	bankInfo();
    	for (Bank ug : bankList) {
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
    public List<Bank> complete(String desc) {
		List<Bank> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    /**
     * Gets the bank list.
     *
     * @return the bank list
     */
    public List<Bank> getBankList() {
		return bankList;
	}
	
	/**
	 * Sets the bank list.
	 *
	 * @param bankList the new bank list
	 */
	public void setBankList(List<Bank> bankList) {
		this.bankList = bankList;
	}
	
	/**
	 * Gets the bank.
	 *
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}
	
	/**
	 * Sets the bank.
	 *
	 * @param bank the new bank
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}

    /**
     * Gets the bankfiltered list.
     *
     * @return the bankfiltered list
     */
    public List<Bank> getBankfilteredList() {
		return bankfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param bankfilteredList the new bankfilteredList list
	 * @see    Bank
	 */	
	public void setBankfilteredList(List<Bank> bankfilteredList) {
		this.bankfilteredList = bankfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Bank> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<Bank> dataModel) {
		this.dataModel = dataModel;
	}
	
}
