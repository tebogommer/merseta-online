package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.RejectReasonsChild;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.RejectReasonsChildService;

// TODO: Auto-generated Javadoc
/**
 * The Class RejectReasonsChildUI.
 */
@ManagedBean(name = "rejectreasonschildUI")
@ViewScoped
public class RejectReasonsChildUI extends AbstractUI {

	/** The service. */
	private RejectReasonsChildService service = new RejectReasonsChildService();
	
	/** The rejectreasonschild list. */
	private List<RejectReasonsChild> rejectreasonschildList = null;
	
	/** The rejectreasonschildfiltered list. */
	private List<RejectReasonsChild> rejectreasonschildfilteredList = null;
	
	/** The rejectreasonschild. */
	private RejectReasonsChild rejectreasonschild = null;
	
	/** The data model. */
	private LazyDataModel<RejectReasonsChild> dataModel; 

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
	 * Initialize method to get all RejectReasonsChild and prepare a for a create of a new RejectReasonsChild.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    RejectReasonsChild
	 */
	private void runInit() throws Exception {
		prepareNew();
		rejectreasonschildInfo();
	}

	/**
	 * Get all RejectReasonsChild for data table.
	 *
	 * @author TechFinium
	 * @see    RejectReasonsChild
	 */
	public void rejectreasonschildInfo() {
	 
			
			 dataModel = new LazyDataModel<RejectReasonsChild>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<RejectReasonsChild> retorno = new  ArrayList<RejectReasonsChild>();
			   
			   @Override 
			   public List<RejectReasonsChild> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allRejectReasonsChild(RejectReasonsChild.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(RejectReasonsChild.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(RejectReasonsChild obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public RejectReasonsChild getRowData(String rowKey) {
			        for(RejectReasonsChild obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert RejectReasonsChild into database.
	 *
	 * @author TechFinium
	 * @see    RejectReasonsChild
	 */
	public void rejectreasonschildInsert() {
		try {
				 service.create(this.rejectreasonschild);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 rejectreasonschildInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update RejectReasonsChild in database.
	 *
	 * @author TechFinium
	 * @see    RejectReasonsChild
	 */
    public void rejectreasonschildUpdate() {
		try {
				 service.update(this.rejectreasonschild);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 rejectreasonschildInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete RejectReasonsChild from database.
	 *
	 * @author TechFinium
	 * @see    RejectReasonsChild
	 */
	public void rejectreasonschildDelete() {
		try {
			 service.delete(this.rejectreasonschild);
			  prepareNew();
			 rejectreasonschildInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of RejectReasonsChild .
	 *
	 * @author TechFinium
	 * @see    RejectReasonsChild
	 */
	public void prepareNew() {
		rejectreasonschild = new RejectReasonsChild();
	}

/*
    public List<SelectItem> getRejectReasonsChildDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	rejectreasonschildInfo();
    	for (RejectReasonsChild ug : rejectreasonschildList) {
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
    public List<RejectReasonsChild> complete(String desc) {
		List<RejectReasonsChild> l = null;
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
     * Gets the reject reasons child list.
     *
     * @return the reject reasons child list
     */
    public List<RejectReasonsChild> getRejectReasonsChildList() {
		return rejectreasonschildList;
	}
	
	/**
	 * Sets the reject reasons child list.
	 *
	 * @param rejectreasonschildList the new reject reasons child list
	 */
	public void setRejectReasonsChildList(List<RejectReasonsChild> rejectreasonschildList) {
		this.rejectreasonschildList = rejectreasonschildList;
	}
	
	/**
	 * Gets the rejectreasonschild.
	 *
	 * @return the rejectreasonschild
	 */
	public RejectReasonsChild getRejectreasonschild() {
		return rejectreasonschild;
	}
	
	/**
	 * Sets the rejectreasonschild.
	 *
	 * @param rejectreasonschild the new rejectreasonschild
	 */
	public void setRejectreasonschild(RejectReasonsChild rejectreasonschild) {
		this.rejectreasonschild = rejectreasonschild;
	}

    /**
     * Gets the reject reasons childfiltered list.
     *
     * @return the reject reasons childfiltered list
     */
    public List<RejectReasonsChild> getRejectReasonsChildfilteredList() {
		return rejectreasonschildfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param rejectreasonschildfilteredList the new rejectreasonschildfilteredList list
	 * @see    RejectReasonsChild
	 */	
	public void setRejectReasonsChildfilteredList(List<RejectReasonsChild> rejectreasonschildfilteredList) {
		this.rejectreasonschildfilteredList = rejectreasonschildfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<RejectReasonsChild> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<RejectReasonsChild> dataModel) {
		this.dataModel = dataModel;
	}
	
}
