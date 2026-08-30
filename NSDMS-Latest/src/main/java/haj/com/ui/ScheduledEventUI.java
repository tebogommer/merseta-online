package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.service.ScheduledEventService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "scheduledeventUI")
@ViewScoped
public class ScheduledEventUI extends AbstractUI {

	private ScheduledEventService service = new ScheduledEventService();
	private List<ScheduledEvent> scheduledeventList = null;
	private List<ScheduledEvent> scheduledeventfilteredList = null;
	private ScheduledEvent scheduledevent = null;
	private LazyDataModel<ScheduledEvent> dataModel; 

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
	 * Initialize method to get all ScheduledEvent and prepare a for a create of a new ScheduledEvent
 	 * @author TechFinium 
 	 * @see    ScheduledEvent
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DATE_SCHEDULE) {
			scheduledevent = service.findByKey(getSessionUI().getTask().getTargetKey());
		}else {
			prepareNew();
			scheduledeventInfo();
		}
		
	}

	/**
	 * Get all ScheduledEvent for data table
 	 * @author TechFinium 
 	 * @see    ScheduledEvent
 	 */
	public void scheduledeventInfo() {
			//dataModel = new ScheduledEventDatamodel();
	}

	/**
	 * Insert ScheduledEvent into database
 	 * @author TechFinium 
 	 * @see    ScheduledEvent
 	 */
	public void scheduledeventInsert() {
		try {
				 service.create(this.scheduledevent);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 scheduledeventInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ScheduledEvent in database
 	 * @author TechFinium 
 	 * @see    ScheduledEvent
 	 */
    public void scheduledeventUpdate() {
		try {
				 service.update(this.scheduledevent);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 scheduledeventInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ScheduledEvent from database
 	 * @author TechFinium 
 	 * @see    ScheduledEvent
 	 */
	public void scheduledeventDelete() {
		try {
			 service.delete(this.scheduledevent);
			  prepareNew();
			 scheduledeventInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ScheduledEvent 
 	 * @author TechFinium 
 	 * @see    ScheduledEvent
 	 */
	public void prepareNew() {
		scheduledevent = new ScheduledEvent();
	}

/*
    public List<SelectItem> getScheduledEventDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	scheduledeventInfo();
    	for (ScheduledEvent ug : scheduledeventList) {
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
    public List<ScheduledEvent> complete(String desc) {
		List<ScheduledEvent> l = null;
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
    
    public List<ScheduledEvent> getScheduledEventList() {
		return scheduledeventList;
	}
	public void setScheduledEventList(List<ScheduledEvent> scheduledeventList) {
		this.scheduledeventList = scheduledeventList;
	}
	public ScheduledEvent getScheduledevent() {
		return scheduledevent;
	}
	public void setScheduledevent(ScheduledEvent scheduledevent) {
		this.scheduledevent = scheduledevent;
	}

    public List<ScheduledEvent> getScheduledEventfilteredList() {
		return scheduledeventfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param scheduledeventfilteredList the new scheduledeventfilteredList list
 	 * @see    ScheduledEvent
 	 */	
	public void setScheduledEventfilteredList(List<ScheduledEvent> scheduledeventfilteredList) {
		this.scheduledeventfilteredList = scheduledeventfilteredList;
	}

	
	public LazyDataModel<ScheduledEvent> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ScheduledEvent> dataModel) {
		this.dataModel = dataModel;
	}
	
}
