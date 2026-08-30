package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.MaritalStatus;
import haj.com.service.lookup.MaritalStatusService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "maritalstatusUI")
@ViewScoped
public class MaritalStatusUI extends AbstractUI {

	private MaritalStatusService service = new MaritalStatusService();
	private List<MaritalStatus> maritalstatusList = null;
	private List<MaritalStatus> maritalstatusfilteredList = null;
	private MaritalStatus maritalstatus = null;
	private LazyDataModel<MaritalStatus> dataModel; 

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
	 * Initialize method to get all MaritalStatus and prepare a for a create of a new MaritalStatus
 	 * @author TechFinium 
 	 * @see    MaritalStatus
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		maritalstatusInfo();
	}

	/**
	 * Get all MaritalStatus for data table
 	 * @author TechFinium 
 	 * @see    MaritalStatus
 	 */
	public void maritalstatusInfo() {
	 
			
			 dataModel = new LazyDataModel<MaritalStatus>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<MaritalStatus> retorno = new  ArrayList<MaritalStatus>();
			   
			   @Override 
			   public List<MaritalStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allMaritalStatus(MaritalStatus.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(MaritalStatus.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(MaritalStatus obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public MaritalStatus getRowData(String rowKey) {
			        for(MaritalStatus obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert MaritalStatus into database
 	 * @author TechFinium 
 	 * @see    MaritalStatus
 	 */
	public void maritalstatusInsert() {
		try {
				 service.create(this.maritalstatus);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 maritalstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update MaritalStatus in database
 	 * @author TechFinium 
 	 * @see    MaritalStatus
 	 */
    public void maritalstatusUpdate() {
		try {
				 service.update(this.maritalstatus);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 maritalstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete MaritalStatus from database
 	 * @author TechFinium 
 	 * @see    MaritalStatus
 	 */
	public void maritalstatusDelete() {
		try {
			 service.delete(this.maritalstatus);
			  prepareNew();
			 maritalstatusInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of MaritalStatus 
 	 * @author TechFinium 
 	 * @see    MaritalStatus
 	 */
	public void prepareNew() {
		maritalstatus = new MaritalStatus();
	}

/*
    public List<SelectItem> getMaritalStatusDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	maritalstatusInfo();
    	for (MaritalStatus ug : maritalstatusList) {
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
    public List<MaritalStatus> complete(String desc) {
		List<MaritalStatus> l = null;
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
    
    public List<MaritalStatus> getMaritalStatusList() {
		return maritalstatusList;
	}
	public void setMaritalStatusList(List<MaritalStatus> maritalstatusList) {
		this.maritalstatusList = maritalstatusList;
	}
	public MaritalStatus getMaritalstatus() {
		return maritalstatus;
	}
	public void setMaritalstatus(MaritalStatus maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

    public List<MaritalStatus> getMaritalStatusfilteredList() {
		return maritalstatusfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param maritalstatusfilteredList the new maritalstatusfilteredList list
 	 * @see    MaritalStatus
 	 */	
	public void setMaritalStatusfilteredList(List<MaritalStatus> maritalstatusfilteredList) {
		this.maritalstatusfilteredList = maritalstatusfilteredList;
	}

	
	public LazyDataModel<MaritalStatus> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MaritalStatus> dataModel) {
		this.dataModel = dataModel;
	}
	
}
