package  haj.com.wsp.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WspSignoff;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspSignoffService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspSignoffUI.
 */
@ManagedBean(name = "wspsignoffUI")
@ViewScoped
public class WspSignoffUI extends AbstractUI {

	/** The service. */
	private WspSignoffService service = new WspSignoffService();
	
	/** The wspsignoff list. */
	private List<WspSignoff> wspsignoffList = null;
	
	/** The wspsignofffiltered list. */
	private List<WspSignoff> wspsignofffilteredList = null;
	
	/** The wspsignoff. */
	private WspSignoff wspsignoff = null;
	
	/** The data model. */
	private LazyDataModel<WspSignoff> dataModel; 

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
	 * Initialize method to get all WspSignoff and prepare a for a create of a new WspSignoff.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    WspSignoff
	 */
	private void runInit() throws Exception {
		prepareNew();
		wspsignoffInfo();
	}

	/**
	 * Get all WspSignoff for data table.
	 *
	 * @author TechFinium
	 * @see    WspSignoff
	 */
	public void wspsignoffInfo() {
	 
			
			 dataModel = new LazyDataModel<WspSignoff>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<WspSignoff> retorno = new  ArrayList<WspSignoff>();
			   
			   @Override 
			   public List<WspSignoff> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allWspSignoff(WspSignoff.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(WspSignoff.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(WspSignoff obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public WspSignoff getRowData(String rowKey) {
			        for(WspSignoff obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert WspSignoff into database.
	 *
	 * @author TechFinium
	 * @see    WspSignoff
	 */
	public void wspsignoffInsert() {
		try {
				 service.create(this.wspsignoff);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspsignoffInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspSignoff in database.
	 *
	 * @author TechFinium
	 * @see    WspSignoff
	 */
    public void wspsignoffUpdate() {
		try {
				 service.update(this.wspsignoff);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspsignoffInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspSignoff from database.
	 *
	 * @author TechFinium
	 * @see    WspSignoff
	 */
	public void wspsignoffDelete() {
		try {
			 service.delete(this.wspsignoff);
			  prepareNew();
			 wspsignoffInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspSignoff .
	 *
	 * @author TechFinium
	 * @see    WspSignoff
	 */
	public void prepareNew() {
		wspsignoff = new WspSignoff();
	}

/*
    public List<SelectItem> getWspSignoffDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspsignoffInfo();
    	for (WspSignoff ug : wspsignoffList) {
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
    public List<WspSignoff> complete(String desc) {
		List<WspSignoff> l = null;
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
     * Gets the wsp signoff list.
     *
     * @return the wsp signoff list
     */
    public List<WspSignoff> getWspSignoffList() {
		return wspsignoffList;
	}
	
	/**
	 * Sets the wsp signoff list.
	 *
	 * @param wspsignoffList the new wsp signoff list
	 */
	public void setWspSignoffList(List<WspSignoff> wspsignoffList) {
		this.wspsignoffList = wspsignoffList;
	}
	
	/**
	 * Gets the wspsignoff.
	 *
	 * @return the wspsignoff
	 */
	public WspSignoff getWspsignoff() {
		return wspsignoff;
	}
	
	/**
	 * Sets the wspsignoff.
	 *
	 * @param wspsignoff the new wspsignoff
	 */
	public void setWspsignoff(WspSignoff wspsignoff) {
		this.wspsignoff = wspsignoff;
	}

    /**
     * Gets the wsp signofffiltered list.
     *
     * @return the wsp signofffiltered list
     */
    public List<WspSignoff> getWspSignofffilteredList() {
		return wspsignofffilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param wspsignofffilteredList the new wspsignofffilteredList list
	 * @see    WspSignoff
	 */	
	public void setWspSignofffilteredList(List<WspSignoff> wspsignofffilteredList) {
		this.wspsignofffilteredList = wspsignofffilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<WspSignoff> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<WspSignoff> dataModel) {
		this.dataModel = dataModel;
	}
	
}
