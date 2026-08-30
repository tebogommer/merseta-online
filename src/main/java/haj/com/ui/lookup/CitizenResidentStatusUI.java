package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.CitizenResidentStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.CitizenResidentStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class CitizenResidentStatusUI.
 */
@ManagedBean(name = "citizenresidentstatusUI")
@ViewScoped
public class CitizenResidentStatusUI extends AbstractUI {

	/** The service. */
	private CitizenResidentStatusService service = new CitizenResidentStatusService();
	
	/** The citizenresidentstatus list. */
	private List<CitizenResidentStatus> citizenresidentstatusList = null;
	
	/** The citizenresidentstatusfiltered list. */
	private List<CitizenResidentStatus> citizenresidentstatusfilteredList = null;
	
	/** The citizenresidentstatus. */
	private CitizenResidentStatus citizenresidentstatus = null;
	
	/** The data model. */
	private LazyDataModel<CitizenResidentStatus> dataModel; 

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
	 * Initialize method to get all CitizenResidentStatus and prepare a for a create of a new CitizenResidentStatus.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    CitizenResidentStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		citizenresidentstatusInfo();
	}

	/**
	 * Get all CitizenResidentStatus for data table.
	 *
	 * @author TechFinium
	 * @see    CitizenResidentStatus
	 */
	public void citizenresidentstatusInfo() {
	 
			
			 dataModel = new LazyDataModel<CitizenResidentStatus>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<CitizenResidentStatus> retorno = new  ArrayList<CitizenResidentStatus>();
			   
			   @Override 
			   public List<CitizenResidentStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allCitizenResidentStatus(CitizenResidentStatus.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(CitizenResidentStatus.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(CitizenResidentStatus obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public CitizenResidentStatus getRowData(String rowKey) {
			        for(CitizenResidentStatus obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert CitizenResidentStatus into database.
	 *
	 * @author TechFinium
	 * @see    CitizenResidentStatus
	 */
	public void citizenresidentstatusInsert() {
		try {
				 service.create(this.citizenresidentstatus);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 citizenresidentstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CitizenResidentStatus in database.
	 *
	 * @author TechFinium
	 * @see    CitizenResidentStatus
	 */
    public void citizenresidentstatusUpdate() {
		try {
				 service.update(this.citizenresidentstatus);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 citizenresidentstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CitizenResidentStatus from database.
	 *
	 * @author TechFinium
	 * @see    CitizenResidentStatus
	 */
	public void citizenresidentstatusDelete() {
		try {
			 service.delete(this.citizenresidentstatus);
			  prepareNew();
			 citizenresidentstatusInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CitizenResidentStatus .
	 *
	 * @author TechFinium
	 * @see    CitizenResidentStatus
	 */
	public void prepareNew() {
		citizenresidentstatus = new CitizenResidentStatus();
	}

/*
    public List<SelectItem> getCitizenResidentStatusDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	citizenresidentstatusInfo();
    	for (CitizenResidentStatus ug : citizenresidentstatusList) {
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
    public List<CitizenResidentStatus> complete(String desc) {
		List<CitizenResidentStatus> l = null;
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
     * Gets the citizen resident status list.
     *
     * @return the citizen resident status list
     */
    public List<CitizenResidentStatus> getCitizenResidentStatusList() {
		return citizenresidentstatusList;
	}
	
	/**
	 * Sets the citizen resident status list.
	 *
	 * @param citizenresidentstatusList the new citizen resident status list
	 */
	public void setCitizenResidentStatusList(List<CitizenResidentStatus> citizenresidentstatusList) {
		this.citizenresidentstatusList = citizenresidentstatusList;
	}
	
	/**
	 * Gets the citizenresidentstatus.
	 *
	 * @return the citizenresidentstatus
	 */
	public CitizenResidentStatus getCitizenresidentstatus() {
		return citizenresidentstatus;
	}
	
	/**
	 * Sets the citizenresidentstatus.
	 *
	 * @param citizenresidentstatus the new citizenresidentstatus
	 */
	public void setCitizenresidentstatus(CitizenResidentStatus citizenresidentstatus) {
		this.citizenresidentstatus = citizenresidentstatus;
	}

    /**
     * Gets the citizen resident statusfiltered list.
     *
     * @return the citizen resident statusfiltered list
     */
    public List<CitizenResidentStatus> getCitizenResidentStatusfilteredList() {
		return citizenresidentstatusfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param citizenresidentstatusfilteredList the new citizenresidentstatusfilteredList list
	 * @see    CitizenResidentStatus
	 */	
	public void setCitizenResidentStatusfilteredList(List<CitizenResidentStatus> citizenresidentstatusfilteredList) {
		this.citizenresidentstatusfilteredList = citizenresidentstatusfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<CitizenResidentStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<CitizenResidentStatus> dataModel) {
		this.dataModel = dataModel;
	}
	
}
