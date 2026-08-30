package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.NqfDesigStatus;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.NqfDesigStatusService;

// TODO: Auto-generated Javadoc
/**
 * The Class NqfDesigStatusUI.
 */
@ManagedBean(name = "nqfdesigstatusUI")
@ViewScoped
public class NqfDesigStatusUI extends AbstractUI {

	/** The service. */
	private NqfDesigStatusService service = new NqfDesigStatusService();
	
	/** The nqfdesigstatus list. */
	private List<NqfDesigStatus> nqfdesigstatusList = null;
	
	/** The nqfdesigstatusfiltered list. */
	private List<NqfDesigStatus> nqfdesigstatusfilteredList = null;
	
	/** The nqfdesigstatus. */
	private NqfDesigStatus nqfdesigstatus = null;
	
	/** The data model. */
	private LazyDataModel<NqfDesigStatus> dataModel; 

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
	 * Initialize method to get all NqfDesigStatus and prepare a for a create of a new NqfDesigStatus.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    NqfDesigStatus
	 */
	private void runInit() throws Exception {
		prepareNew();
		nqfdesigstatusInfo();
	}

	/**
	 * Get all NqfDesigStatus for data table.
	 *
	 * @author TechFinium
	 * @see    NqfDesigStatus
	 */
	public void nqfdesigstatusInfo() {
	 
			
			 dataModel = new LazyDataModel<NqfDesigStatus>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<NqfDesigStatus> retorno = new  ArrayList<NqfDesigStatus>();
			   
			   @Override 
			   public List<NqfDesigStatus> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allNqfDesigStatus(NqfDesigStatus.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(NqfDesigStatus.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(NqfDesigStatus obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public NqfDesigStatus getRowData(String rowKey) {
			        for(NqfDesigStatus obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert NqfDesigStatus into database.
	 *
	 * @author TechFinium
	 * @see    NqfDesigStatus
	 */
	public void nqfdesigstatusInsert() {
		try {
				 service.create(this.nqfdesigstatus);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 nqfdesigstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update NqfDesigStatus in database.
	 *
	 * @author TechFinium
	 * @see    NqfDesigStatus
	 */
    public void nqfdesigstatusUpdate() {
		try {
				 service.update(this.nqfdesigstatus);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 nqfdesigstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete NqfDesigStatus from database.
	 *
	 * @author TechFinium
	 * @see    NqfDesigStatus
	 */
	public void nqfdesigstatusDelete() {
		try {
			 service.delete(this.nqfdesigstatus);
			  prepareNew();
			 nqfdesigstatusInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of NqfDesigStatus .
	 *
	 * @author TechFinium
	 * @see    NqfDesigStatus
	 */
	public void prepareNew() {
		nqfdesigstatus = new NqfDesigStatus();
	}

/*
    public List<SelectItem> getNqfDesigStatusDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	nqfdesigstatusInfo();
    	for (NqfDesigStatus ug : nqfdesigstatusList) {
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
    public List<NqfDesigStatus> complete(String desc) {
		List<NqfDesigStatus> l = null;
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
     * Gets the nqf desig status list.
     *
     * @return the nqf desig status list
     */
    public List<NqfDesigStatus> getNqfDesigStatusList() {
		return nqfdesigstatusList;
	}
	
	/**
	 * Sets the nqf desig status list.
	 *
	 * @param nqfdesigstatusList the new nqf desig status list
	 */
	public void setNqfDesigStatusList(List<NqfDesigStatus> nqfdesigstatusList) {
		this.nqfdesigstatusList = nqfdesigstatusList;
	}
	
	/**
	 * Gets the nqfdesigstatus.
	 *
	 * @return the nqfdesigstatus
	 */
	public NqfDesigStatus getNqfdesigstatus() {
		return nqfdesigstatus;
	}
	
	/**
	 * Sets the nqfdesigstatus.
	 *
	 * @param nqfdesigstatus the new nqfdesigstatus
	 */
	public void setNqfdesigstatus(NqfDesigStatus nqfdesigstatus) {
		this.nqfdesigstatus = nqfdesigstatus;
	}

    /**
     * Gets the nqf desig statusfiltered list.
     *
     * @return the nqf desig statusfiltered list
     */
    public List<NqfDesigStatus> getNqfDesigStatusfilteredList() {
		return nqfdesigstatusfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param nqfdesigstatusfilteredList the new nqfdesigstatusfilteredList list
	 * @see    NqfDesigStatus
	 */	
	public void setNqfDesigStatusfilteredList(List<NqfDesigStatus> nqfdesigstatusfilteredList) {
		this.nqfdesigstatusfilteredList = nqfdesigstatusfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<NqfDesigStatus> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<NqfDesigStatus> dataModel) {
		this.dataModel = dataModel;
	}
	
}
