package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.ProviderAccredStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.ProviderAccredStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderAccredStatusUI.
 */
@ManagedBean(name = "provideraccredstatusUI")
@ViewScoped
public class ProviderAccredStatusUI extends AbstractUI {

	/** The service. */
	private ProviderAccredStatusService service = new ProviderAccredStatusService();
	
	/** The provideraccredstatus list. */
	private List<ProviderAccredStatus> provideraccredstatusList = null;
	
	/** The provideraccredstatusfiltered list. */
	private List<ProviderAccredStatus> provideraccredstatusfilteredList = null;
	
	/** The provideraccredstatus. */
	private ProviderAccredStatus provideraccredstatus = null;
	
	/** The data model. */
	private LazyDataModel<ProviderAccredStatus> dataModel;

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
	 * Initialize method to get all ProviderAccredStatus and prepare a for a create of a new ProviderAccredStatus.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    ProviderAccredStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		provideraccredstatusInfo();
	}

	/**
	 * Get all ProviderAccredStatus for data table.
	 *
	 * @author TechFinium
	 * @see    ProviderAccredStatus
	 */
	public void provideraccredstatusInfo() {
	 
			
			 dataModel = new LazyDataModel<ProviderAccredStatus>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ProviderAccredStatus> retorno = new  ArrayList<ProviderAccredStatus>();
			   
			   @Override 
			   public List<ProviderAccredStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allProviderAccredStatus(ProviderAccredStatus.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ProviderAccredStatus.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ProviderAccredStatus obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ProviderAccredStatus getRowData(String rowKey) {
			        for(ProviderAccredStatus obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ProviderAccredStatus into database.
	 *
	 * @author TechFinium
	 * @see    ProviderAccredStatus
	 */
	public void provideraccredstatusInsert() {
		try {
				 service.create(this.provideraccredstatus);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 provideraccredstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ProviderAccredStatus in database.
	 *
	 * @author TechFinium
	 * @see    ProviderAccredStatus
	 */
    public void provideraccredstatusUpdate() {
		try {
				 service.update(this.provideraccredstatus);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 provideraccredstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ProviderAccredStatus from database.
	 *
	 * @author TechFinium
	 * @see    ProviderAccredStatus
	 */
	public void provideraccredstatusDelete() {
		try {
			 service.delete(this.provideraccredstatus);
			  prepareNew();
			 provideraccredstatusInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ProviderAccredStatus .
	 *
	 * @author TechFinium
	 * @see    ProviderAccredStatus
	 */
	public void prepareNew() {
		provideraccredstatus = new ProviderAccredStatus();
	}

/*
    public List<SelectItem> getProviderAccredStatusDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	provideraccredstatusInfo();
    	for (ProviderAccredStatus ug : provideraccredstatusList) {
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
    public List<ProviderAccredStatus> complete(String desc) {
		List<ProviderAccredStatus> l = null;
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
     * Gets the provider accred status list.
     *
     * @return the provider accred status list
     */
    public List<ProviderAccredStatus> getProviderAccredStatusList() {
		return provideraccredstatusList;
	}
	
	/**
	 * Sets the provider accred status list.
	 *
	 * @param provideraccredstatusList the new provider accred status list
	 */
	public void setProviderAccredStatusList(List<ProviderAccredStatus> provideraccredstatusList) {
		this.provideraccredstatusList = provideraccredstatusList;
	}
	
	/**
	 * Gets the provideraccredstatus.
	 *
	 * @return the provideraccredstatus
	 */
	public ProviderAccredStatus getProvideraccredstatus() {
		return provideraccredstatus;
	}
	
	/**
	 * Sets the provideraccredstatus.
	 *
	 * @param provideraccredstatus the new provideraccredstatus
	 */
	public void setProvideraccredstatus(ProviderAccredStatus provideraccredstatus) {
		this.provideraccredstatus = provideraccredstatus;
	}

    /**
     * Gets the provider accred statusfiltered list.
     *
     * @return the provider accred statusfiltered list
     */
    public List<ProviderAccredStatus> getProviderAccredStatusfilteredList() {
		return provideraccredstatusfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param provideraccredstatusfilteredList the new provideraccredstatusfilteredList list
	 * @see    ProviderAccredStatus
	 */	
	public void setProviderAccredStatusfilteredList(List<ProviderAccredStatus> provideraccredstatusfilteredList) {
		this.provideraccredstatusfilteredList = provideraccredstatusfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<ProviderAccredStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<ProviderAccredStatus> dataModel) {
		this.dataModel = dataModel;
	}
	
}
