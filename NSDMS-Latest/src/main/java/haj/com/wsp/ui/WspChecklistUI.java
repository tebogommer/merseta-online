package  haj.com.wsp.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WspChecklist;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspChecklistService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspChecklistUI.
 */
@ManagedBean(name = "wspchecklistUI")
@ViewScoped
public class WspChecklistUI extends AbstractUI {

	/** The service. */
	private WspChecklistService service = new WspChecklistService();
	
	/** The wspchecklist list. */
	private List<WspChecklist> wspchecklistList = null;
	
	/** The wspchecklistfiltered list. */
	private List<WspChecklist> wspchecklistfilteredList = null;
	
	/** The wspchecklist. */
	private WspChecklist wspchecklist = null;
	
	/** The data model. */
	private LazyDataModel<WspChecklist> dataModel; 

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
	 * Initialize method to get all WspChecklist and prepare a for a create of a new WspChecklist.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    WspChecklist
	 */
	private void runInit() throws Exception {
		prepareNew();
		wspchecklistInfo();
	}

	/**
	 * Get all WspChecklist for data table.
	 *
	 * @author TechFinium
	 * @see    WspChecklist
	 */
	public void wspchecklistInfo() {
	 
			
			 dataModel = new LazyDataModel<WspChecklist>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<WspChecklist> retorno = new  ArrayList<WspChecklist>();
			   
			   @Override 
			   public List<WspChecklist> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allWspChecklist(WspChecklist.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(WspChecklist.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(WspChecklist obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public WspChecklist getRowData(String rowKey) {
			        for(WspChecklist obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert WspChecklist into database.
	 *
	 * @author TechFinium
	 * @see    WspChecklist
	 */
	public void wspchecklistInsert() {
		try {
				 service.create(this.wspchecklist);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspchecklistInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspChecklist in database.
	 *
	 * @author TechFinium
	 * @see    WspChecklist
	 */
    public void wspchecklistUpdate() {
		try {
				 service.update(this.wspchecklist);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspchecklistInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspChecklist from database.
	 *
	 * @author TechFinium
	 * @see    WspChecklist
	 */
	public void wspchecklistDelete() {
		try {
			 service.delete(this.wspchecklist);
			  prepareNew();
			 wspchecklistInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspChecklist .
	 *
	 * @author TechFinium
	 * @see    WspChecklist
	 */
	public void prepareNew() {
		wspchecklist = new WspChecklist();
	}

/*
    public List<SelectItem> getWspChecklistDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspchecklistInfo();
    	for (WspChecklist ug : wspchecklistList) {
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
    public List<WspChecklist> complete(String desc) {
		List<WspChecklist> l = null;
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
     * Gets the wsp checklist list.
     *
     * @return the wsp checklist list
     */
    public List<WspChecklist> getWspChecklistList() {
		return wspchecklistList;
	}
	
	/**
	 * Sets the wsp checklist list.
	 *
	 * @param wspchecklistList the new wsp checklist list
	 */
	public void setWspChecklistList(List<WspChecklist> wspchecklistList) {
		this.wspchecklistList = wspchecklistList;
	}
	
	/**
	 * Gets the wspchecklist.
	 *
	 * @return the wspchecklist
	 */
	public WspChecklist getWspchecklist() {
		return wspchecklist;
	}
	
	/**
	 * Sets the wspchecklist.
	 *
	 * @param wspchecklist the new wspchecklist
	 */
	public void setWspchecklist(WspChecklist wspchecklist) {
		this.wspchecklist = wspchecklist;
	}

    /**
     * Gets the wsp checklistfiltered list.
     *
     * @return the wsp checklistfiltered list
     */
    public List<WspChecklist> getWspChecklistfilteredList() {
		return wspchecklistfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param wspchecklistfilteredList the new wspchecklistfilteredList list
	 * @see    WspChecklist
	 */	
	public void setWspChecklistfilteredList(List<WspChecklist> wspchecklistfilteredList) {
		this.wspchecklistfilteredList = wspchecklistfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<WspChecklist> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<WspChecklist> dataModel) {
		this.dataModel = dataModel;
	}
	
}
