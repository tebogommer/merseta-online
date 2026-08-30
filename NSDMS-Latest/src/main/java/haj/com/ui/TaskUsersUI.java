package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.TaskUsers;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TaskUsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskUsersUI.
 */
@ManagedBean(name = "taskusersUI")
@ViewScoped
public class TaskUsersUI extends AbstractUI {

	/** The service. */
	private TaskUsersService service = new TaskUsersService();
	
	/** The taskusers list. */
	private List<TaskUsers> taskusersList = null;
	
	/** The taskusersfiltered list. */
	private List<TaskUsers> taskusersfilteredList = null;
	
	/** The taskusers. */
	private TaskUsers taskusers = null;
	
	/** The data model. */
	private LazyDataModel<TaskUsers> dataModel; 

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
	 * Initialize method to get all TaskUsers and prepare a for a create of a new TaskUsers.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    TaskUsers
	 */
	private void runInit() throws Exception {
		prepareNew();
		taskusersInfo();
	}

	/**
	 * Get all TaskUsers for data table.
	 *
	 * @author TechFinium
	 * @see    TaskUsers
	 */
	public void taskusersInfo() {
	 
			
			 dataModel = new LazyDataModel<TaskUsers>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TaskUsers> retorno = new  ArrayList<TaskUsers>();
			   
			   @Override 
			   public List<TaskUsers> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTaskUsers(TaskUsers.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TaskUsers.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TaskUsers obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TaskUsers getRowData(String rowKey) {
			        for(TaskUsers obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TaskUsers into database.
	 *
	 * @author TechFinium
	 * @see    TaskUsers
	 */
	public void taskusersInsert() {
		try {
				 service.create(this.taskusers);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 taskusersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TaskUsers in database.
	 *
	 * @author TechFinium
	 * @see    TaskUsers
	 */
    public void taskusersUpdate() {
		try {
				 service.update(this.taskusers);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 taskusersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TaskUsers from database.
	 *
	 * @author TechFinium
	 * @see    TaskUsers
	 */
	public void taskusersDelete() {
		try {
			 service.delete(this.taskusers);
			  prepareNew();
			 taskusersInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TaskUsers .
	 *
	 * @author TechFinium
	 * @see    TaskUsers
	 */
	public void prepareNew() {
		taskusers = new TaskUsers();
	}

/*
    public List<SelectItem> getTaskUsersDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	taskusersInfo();
    	for (TaskUsers ug : taskusersList) {
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
    public List<TaskUsers> complete(String desc) {
		List<TaskUsers> l = null;
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
     * Gets the task users list.
     *
     * @return the task users list
     */
    public List<TaskUsers> getTaskUsersList() {
		return taskusersList;
	}
	
	/**
	 * Sets the task users list.
	 *
	 * @param taskusersList the new task users list
	 */
	public void setTaskUsersList(List<TaskUsers> taskusersList) {
		this.taskusersList = taskusersList;
	}
	
	/**
	 * Gets the taskusers.
	 *
	 * @return the taskusers
	 */
	public TaskUsers getTaskusers() {
		return taskusers;
	}
	
	/**
	 * Sets the taskusers.
	 *
	 * @param taskusers the new taskusers
	 */
	public void setTaskusers(TaskUsers taskusers) {
		this.taskusers = taskusers;
	}

    /**
     * Gets the task usersfiltered list.
     *
     * @return the task usersfiltered list
     */
    public List<TaskUsers> getTaskUsersfilteredList() {
		return taskusersfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param taskusersfilteredList the new taskusersfilteredList list
	 * @see    TaskUsers
	 */	
	public void setTaskUsersfilteredList(List<TaskUsers> taskusersfilteredList) {
		this.taskusersfilteredList = taskusersfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<TaskUsers> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<TaskUsers> dataModel) {
		this.dataModel = dataModel;
	}
	
}
