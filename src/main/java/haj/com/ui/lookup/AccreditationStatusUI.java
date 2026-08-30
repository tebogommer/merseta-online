package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.AccreditationStatus;
import haj.com.service.lookup.AccreditationStatusService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "accreditationstatusUI")
@ViewScoped
public class AccreditationStatusUI extends AbstractUI {

	private AccreditationStatusService service = new AccreditationStatusService();
	private List<AccreditationStatus> accreditationstatusList = null;
	private List<AccreditationStatus> accreditationstatusfilteredList = null;
	private AccreditationStatus accreditationstatus = null;
	private LazyDataModel<AccreditationStatus> dataModel; 

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
	 * Initialize method to get all AccreditationStatus and prepare a for a create of a new AccreditationStatus
 	 * @author TechFinium 
 	 * @see    AccreditationStatus
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		accreditationstatusInfo();
	}

	/**
	 * Get all AccreditationStatus for data table
 	 * @author TechFinium 
 	 * @see    AccreditationStatus
 	 */
	public void accreditationstatusInfo() {
	 
			
			 dataModel = new LazyDataModel<AccreditationStatus>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AccreditationStatus> retorno = new  ArrayList<AccreditationStatus>();
			   
			   @Override 
			   public List<AccreditationStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allAccreditationStatus(AccreditationStatus.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(AccreditationStatus.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AccreditationStatus obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AccreditationStatus getRowData(String rowKey) {
			        for(AccreditationStatus obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert AccreditationStatus into database
 	 * @author TechFinium 
 	 * @see    AccreditationStatus
 	 */
	public void accreditationstatusInsert() {
		try {
				 service.create(this.accreditationstatus);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 accreditationstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AccreditationStatus in database
 	 * @author TechFinium 
 	 * @see    AccreditationStatus
 	 */
    public void accreditationstatusUpdate() {
		try {
				 service.update(this.accreditationstatus);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 accreditationstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AccreditationStatus from database
 	 * @author TechFinium 
 	 * @see    AccreditationStatus
 	 */
	public void accreditationstatusDelete() {
		try {
			 service.delete(this.accreditationstatus);
			  prepareNew();
			 accreditationstatusInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AccreditationStatus 
 	 * @author TechFinium 
 	 * @see    AccreditationStatus
 	 */
	public void prepareNew() {
		accreditationstatus = new AccreditationStatus();
	}

/*
    public List<SelectItem> getAccreditationStatusDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	accreditationstatusInfo();
    	for (AccreditationStatus ug : accreditationstatusList) {
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
    public List<AccreditationStatus> complete(String desc) {
		List<AccreditationStatus> l = null;
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
    
    public List<AccreditationStatus> getAccreditationStatusList() {
		return accreditationstatusList;
	}
	public void setAccreditationStatusList(List<AccreditationStatus> accreditationstatusList) {
		this.accreditationstatusList = accreditationstatusList;
	}
	public AccreditationStatus getAccreditationstatus() {
		return accreditationstatus;
	}
	public void setAccreditationstatus(AccreditationStatus accreditationstatus) {
		this.accreditationstatus = accreditationstatus;
	}

    public List<AccreditationStatus> getAccreditationStatusfilteredList() {
		return accreditationstatusfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param accreditationstatusfilteredList the new accreditationstatusfilteredList list
 	 * @see    AccreditationStatus
 	 */	
	public void setAccreditationStatusfilteredList(List<AccreditationStatus> accreditationstatusfilteredList) {
		this.accreditationstatusfilteredList = accreditationstatusfilteredList;
	}

	
	public LazyDataModel<AccreditationStatus> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AccreditationStatus> dataModel) {
		this.dataModel = dataModel;
	}
	
}
