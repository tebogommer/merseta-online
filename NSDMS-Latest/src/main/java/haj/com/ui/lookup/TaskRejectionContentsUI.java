package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.TaskRejectionContents;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.TaskRejectionContentsService;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskRejectionContentsUI.
 */
@ManagedBean(name = "taskrejectioncontentsUI")
@ViewScoped
public class TaskRejectionContentsUI extends AbstractUI {

	/** The service. */
	private TaskRejectionContentsService service = new TaskRejectionContentsService();
	
	/** The taskrejectioncontents list. */
	private List<TaskRejectionContents> taskrejectioncontentsList = null;
	
	/** The taskrejectioncontentsfiltered list. */
	private List<TaskRejectionContents> taskrejectioncontentsfilteredList = null;
	
	/** The taskrejectioncontents. */
	private TaskRejectionContents taskrejectioncontents = null;
	
	/** The data model. */
	private LazyDataModel<TaskRejectionContents> dataModel; 

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
	 * Initialize method to get all TaskRejectionContents and prepare a for a create of a new TaskRejectionContents.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    TaskRejectionContents
	 */
	private void runInit() throws Exception {
		prepareNew();
		taskrejectioncontentsInfo();
	}

	/**
	 * Get all TaskRejectionContents for data table.
	 *
	 * @author TechFinium
	 * @see    TaskRejectionContents
	 */
	public void taskrejectioncontentsInfo() {
	 
			
			 dataModel = new LazyDataModel<TaskRejectionContents>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TaskRejectionContents> retorno = new  ArrayList<TaskRejectionContents>();
			   
			   @Override 
			   public List<TaskRejectionContents> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTaskRejectionContents(TaskRejectionContents.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TaskRejectionContents.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TaskRejectionContents obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TaskRejectionContents getRowData(String rowKey) {
			        for(TaskRejectionContents obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TaskRejectionContents into database.
	 *
	 * @author TechFinium
	 * @see    TaskRejectionContents
	 */
	public void taskrejectioncontentsInsert() {
		try {
				 service.create(this.taskrejectioncontents);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 taskrejectioncontentsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TaskRejectionContents in database.
	 *
	 * @author TechFinium
	 * @see    TaskRejectionContents
	 */
    public void taskrejectioncontentsUpdate() {
		try {
				 service.update(this.taskrejectioncontents);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 taskrejectioncontentsInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TaskRejectionContents from database.
	 *
	 * @author TechFinium
	 * @see    TaskRejectionContents
	 */
	public void taskrejectioncontentsDelete() {
		try {
			 service.delete(this.taskrejectioncontents);
			  prepareNew();
			 taskrejectioncontentsInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TaskRejectionContents .
	 *
	 * @author TechFinium
	 * @see    TaskRejectionContents
	 */
	public void prepareNew() {
		taskrejectioncontents = new TaskRejectionContents();
	}

/*
    public List<SelectItem> getTaskRejectionContentsDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	taskrejectioncontentsInfo();
    	for (TaskRejectionContents ug : taskrejectioncontentsList) {
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
    public List<TaskRejectionContents> complete(String desc) {
		List<TaskRejectionContents> l = null;
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
     * Gets the task rejection contents list.
     *
     * @return the task rejection contents list
     */
    public List<TaskRejectionContents> getTaskRejectionContentsList() {
		return taskrejectioncontentsList;
	}
	
	/**
	 * Sets the task rejection contents list.
	 *
	 * @param taskrejectioncontentsList the new task rejection contents list
	 */
	public void setTaskRejectionContentsList(List<TaskRejectionContents> taskrejectioncontentsList) {
		this.taskrejectioncontentsList = taskrejectioncontentsList;
	}
	
	/**
	 * Gets the taskrejectioncontents.
	 *
	 * @return the taskrejectioncontents
	 */
	public TaskRejectionContents getTaskrejectioncontents() {
		return taskrejectioncontents;
	}
	
	/**
	 * Sets the taskrejectioncontents.
	 *
	 * @param taskrejectioncontents the new taskrejectioncontents
	 */
	public void setTaskrejectioncontents(TaskRejectionContents taskrejectioncontents) {
		this.taskrejectioncontents = taskrejectioncontents;
	}

    /**
     * Gets the task rejection contentsfiltered list.
     *
     * @return the task rejection contentsfiltered list
     */
    public List<TaskRejectionContents> getTaskRejectionContentsfilteredList() {
		return taskrejectioncontentsfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param taskrejectioncontentsfilteredList the new taskrejectioncontentsfilteredList list
	 * @see    TaskRejectionContents
	 */	
	public void setTaskRejectionContentsfilteredList(List<TaskRejectionContents> taskrejectioncontentsfilteredList) {
		this.taskrejectioncontentsfilteredList = taskrejectioncontentsfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<TaskRejectionContents> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<TaskRejectionContents> dataModel) {
		this.dataModel = dataModel;
	}
	
}
