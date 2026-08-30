package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.ScheduledEventUsers;
import haj.com.service.ScheduledEventUsersService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "scheduledEvenViewtUI")
@ViewScoped
public class ScheduledEventUsersUI extends AbstractUI {

	private ScheduledEventUsersService service = new ScheduledEventUsersService();
	private List<ScheduledEventUsers> scheduledeventusersList = null;
	private List<ScheduledEventUsers> scheduledeventusersfilteredList = null;
	private ScheduledEventUsers scheduledeventusers = null;
	private LazyDataModel<ScheduledEventUsers> dataModel; 

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
	 * Initialize method to get all ScheduledEventUsers and prepare a for a create of a new ScheduledEventUsers
 	 * @author TechFinium 
 	 * @see    ScheduledEventUsers
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		scheduledeventusersInfo();
	}

	/**
	 * Get all ScheduledEventUsers for data table
 	 * @author TechFinium 
 	 * @see    ScheduledEventUsers
 	 */
	public void scheduledeventusersInfo() {
			//dataModel = new ScheduledEventUsersDatamodel();
	}

	/**
	 * Insert ScheduledEventUsers into database
 	 * @author TechFinium 
 	 * @see    ScheduledEventUsers
 	 */
	public void scheduledeventusersInsert() {
		try {
				 service.create(this.scheduledeventusers);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 scheduledeventusersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ScheduledEventUsers in database
 	 * @author TechFinium 
 	 * @see    ScheduledEventUsers
 	 */
    public void scheduledeventusersUpdate() {
		try {
				 service.update(this.scheduledeventusers);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 scheduledeventusersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ScheduledEventUsers from database
 	 * @author TechFinium 
 	 * @see    ScheduledEventUsers
 	 */
	public void scheduledeventusersDelete() {
		try {
			 service.delete(this.scheduledeventusers);
			  prepareNew();
			 scheduledeventusersInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ScheduledEventUsers 
 	 * @author TechFinium 
 	 * @see    ScheduledEventUsers
 	 */
	public void prepareNew() {
		//scheduledeventusers = new ScheduledEventUsers();
	}

/*
    public List<SelectItem> getScheduledEventUsersDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	scheduledeventusersInfo();
    	for (ScheduledEventUsers ug : scheduledeventusersList) {
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
    public List<ScheduledEventUsers> complete(String desc) {
		List<ScheduledEventUsers> l = null;
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
    
    public List<ScheduledEventUsers> getScheduledEventUsersList() {
		return scheduledeventusersList;
	}
	public void setScheduledEventUsersList(List<ScheduledEventUsers> scheduledeventusersList) {
		this.scheduledeventusersList = scheduledeventusersList;
	}
	public ScheduledEventUsers getScheduledeventusers() {
		return scheduledeventusers;
	}
	public void setScheduledeventusers(ScheduledEventUsers scheduledeventusers) {
		this.scheduledeventusers = scheduledeventusers;
	}

    public List<ScheduledEventUsers> getScheduledEventUsersfilteredList() {
		return scheduledeventusersfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param scheduledeventusersfilteredList the new scheduledeventusersfilteredList list
 	 * @see    ScheduledEventUsers
 	 */	
	public void setScheduledEventUsersfilteredList(List<ScheduledEventUsers> scheduledeventusersfilteredList) {
		this.scheduledeventusersfilteredList = scheduledeventusersfilteredList;
	}

	
	public LazyDataModel<ScheduledEventUsers> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ScheduledEventUsers> dataModel) {
		this.dataModel = dataModel;
	}
	
}
