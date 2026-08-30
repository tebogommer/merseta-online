package  haj.com.wsp.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WspDispute;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspDisputeService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspDisputeUI.
 */
@ManagedBean(name = "wspdisputeUI")
@ViewScoped
public class WspDisputeUI extends AbstractUI {

	/** The service. */
	private WspDisputeService service = new WspDisputeService();
	
	/** The wspdispute list. */
	private List<WspDispute> wspdisputeList = null;
	
	/** The wspdisputefiltered list. */
	private List<WspDispute> wspdisputefilteredList = null;
	
	/** The wspdispute. */
	private WspDispute wspdispute = null;
	
	/** The data model. */
	private LazyDataModel<WspDispute> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all WspDispute and prepare a for a create of a new WspDispute.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    WspDispute
	 */
	private void runInit() throws Exception {
		prepareNew();
		wspdisputeInfo();
	}

	/**
	 * Get all WspDispute for data table.
	 *
	 * @author TechFinium
	 * @see    WspDispute
	 */
	public void wspdisputeInfo() {
	 
			
			 dataModel = new LazyDataModel<WspDispute>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<WspDispute> retorno = new  ArrayList<WspDispute>();
			   
			   @Override 
			   public List<WspDispute> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allWspDispute(WspDispute.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(WspDispute.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(WspDispute obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public WspDispute getRowData(String rowKey) {
			        for(WspDispute obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert WspDispute into database.
	 *
	 * @author TechFinium
	 * @see    WspDispute
	 */
	public void wspdisputeInsert() {
		try {
				 service.create(this.wspdispute);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspdisputeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspDispute in database.
	 *
	 * @author TechFinium
	 * @see    WspDispute
	 */
    public void wspdisputeUpdate() {
		try {
				 service.update(this.wspdispute);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspdisputeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspDispute from database.
	 *
	 * @author TechFinium
	 * @see    WspDispute
	 */
	public void wspdisputeDelete() {
		try {
			 service.delete(this.wspdispute);
			  prepareNew();
			 wspdisputeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspDispute .
	 *
	 * @author TechFinium
	 * @see    WspDispute
	 */
	public void prepareNew() {
		wspdispute = new WspDispute();
	}

/*
    public List<SelectItem> getWspDisputeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspdisputeInfo();
    	for (WspDispute ug : wspdisputeList) {
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
    public List<WspDispute> complete(String desc) {
		List<WspDispute> l = null;
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
    
    /**
     * Gets the wsp dispute list.
     *
     * @return the wsp dispute list
     */
    public List<WspDispute> getWspDisputeList() {
		return wspdisputeList;
	}
	
	/**
	 * Sets the wsp dispute list.
	 *
	 * @param wspdisputeList the new wsp dispute list
	 */
	public void setWspDisputeList(List<WspDispute> wspdisputeList) {
		this.wspdisputeList = wspdisputeList;
	}
	
	/**
	 * Gets the wspdispute.
	 *
	 * @return the wspdispute
	 */
	public WspDispute getWspdispute() {
		return wspdispute;
	}
	
	/**
	 * Sets the wspdispute.
	 *
	 * @param wspdispute the new wspdispute
	 */
	public void setWspdispute(WspDispute wspdispute) {
		this.wspdispute = wspdispute;
	}

    /**
     * Gets the wsp disputefiltered list.
     *
     * @return the wsp disputefiltered list
     */
    public List<WspDispute> getWspDisputefilteredList() {
		return wspdisputefilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param wspdisputefilteredList the new wspdisputefilteredList list
	 * @see    WspDispute
	 */	
	public void setWspDisputefilteredList(List<WspDispute> wspdisputefilteredList) {
		this.wspdisputefilteredList = wspdisputefilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<WspDispute> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<WspDispute> dataModel) {
		this.dataModel = dataModel;
	}
	
}
