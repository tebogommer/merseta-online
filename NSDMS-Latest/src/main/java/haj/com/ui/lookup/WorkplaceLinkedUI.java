package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.WorkplaceLinked;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.WorkplaceLinkedService;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkplaceLinkedUI.
 */
@ManagedBean(name = "workplacelinkedUI")
@ViewScoped
public class WorkplaceLinkedUI extends AbstractUI {

	/** The service. */
	private WorkplaceLinkedService service = new WorkplaceLinkedService();
	
	/** The workplacelinked list. */
	private List<WorkplaceLinked> workplacelinkedList = null;
	
	/** The workplacelinkedfiltered list. */
	private List<WorkplaceLinked> workplacelinkedfilteredList = null;
	
	/** The workplacelinked. */
	private WorkplaceLinked workplacelinked = null;
	
	/** The data model. */
	private LazyDataModel<WorkplaceLinked> dataModel; 

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
	 * Initialize method to get all WorkplaceLinked and prepare a for a create of a new WorkplaceLinked.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    WorkplaceLinked
	 */
	private void runInit() throws Exception {
		prepareNew();
		workplacelinkedInfo();
	}

	/**
	 * Get all WorkplaceLinked for data table.
	 *
	 * @author TechFinium
	 * @see    WorkplaceLinked
	 */
	public void workplacelinkedInfo() {
	 
			
			 dataModel = new LazyDataModel<WorkplaceLinked>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<WorkplaceLinked> retorno = new  ArrayList<WorkplaceLinked>();
			   
			   @Override 
			   public List<WorkplaceLinked> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allWorkplaceLinked(WorkplaceLinked.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(WorkplaceLinked.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(WorkplaceLinked obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public WorkplaceLinked getRowData(String rowKey) {
			        for(WorkplaceLinked obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert WorkplaceLinked into database.
	 *
	 * @author TechFinium
	 * @see    WorkplaceLinked
	 */
	public void workplacelinkedInsert() {
		try {
				 service.create(this.workplacelinked);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacelinkedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WorkplaceLinked in database.
	 *
	 * @author TechFinium
	 * @see    WorkplaceLinked
	 */
    public void workplacelinkedUpdate() {
		try {
				 service.update(this.workplacelinked);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 workplacelinkedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WorkplaceLinked from database.
	 *
	 * @author TechFinium
	 * @see    WorkplaceLinked
	 */
	public void workplacelinkedDelete() {
		try {
			 service.delete(this.workplacelinked);
			  prepareNew();
			 workplacelinkedInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WorkplaceLinked .
	 *
	 * @author TechFinium
	 * @see    WorkplaceLinked
	 */
	public void prepareNew() {
		workplacelinked = new WorkplaceLinked();
	}

/*
    public List<SelectItem> getWorkplaceLinkedDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	workplacelinkedInfo();
    	for (WorkplaceLinked ug : workplacelinkedList) {
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
    public List<WorkplaceLinked> complete(String desc) {
		List<WorkplaceLinked> l = null;
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
     * Gets the workplace linked list.
     *
     * @return the workplace linked list
     */
    public List<WorkplaceLinked> getWorkplaceLinkedList() {
		return workplacelinkedList;
	}
	
	/**
	 * Sets the workplace linked list.
	 *
	 * @param workplacelinkedList the new workplace linked list
	 */
	public void setWorkplaceLinkedList(List<WorkplaceLinked> workplacelinkedList) {
		this.workplacelinkedList = workplacelinkedList;
	}
	
	/**
	 * Gets the workplacelinked.
	 *
	 * @return the workplacelinked
	 */
	public WorkplaceLinked getWorkplacelinked() {
		return workplacelinked;
	}
	
	/**
	 * Sets the workplacelinked.
	 *
	 * @param workplacelinked the new workplacelinked
	 */
	public void setWorkplacelinked(WorkplaceLinked workplacelinked) {
		this.workplacelinked = workplacelinked;
	}

    /**
     * Gets the workplace linkedfiltered list.
     *
     * @return the workplace linkedfiltered list
     */
    public List<WorkplaceLinked> getWorkplaceLinkedfilteredList() {
		return workplacelinkedfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param workplacelinkedfilteredList the new workplacelinkedfilteredList list
	 * @see    WorkplaceLinked
	 */	
	public void setWorkplaceLinkedfilteredList(List<WorkplaceLinked> workplacelinkedfilteredList) {
		this.workplacelinkedfilteredList = workplacelinkedfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<WorkplaceLinked> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<WorkplaceLinked> dataModel) {
		this.dataModel = dataModel;
	}
	
}
