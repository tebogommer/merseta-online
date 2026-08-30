package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.ScheduleChangesLog;
import haj.com.service.ScheduleChangesLogService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "schedulechangeslogUI")
@ViewScoped
public class ScheduleChangesLogUI extends AbstractUI {

	private ScheduleChangesLogService service = new ScheduleChangesLogService();
	private List<ScheduleChangesLog> schedulechangeslogList = null;
	private List<ScheduleChangesLog> schedulechangeslogfilteredList = null;
	private ScheduleChangesLog schedulechangeslog = null;
	private LazyDataModel<ScheduleChangesLog> dataModel; 

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
	 * Initialize method to get all ScheduleChangesLog and prepare a for a create of a new ScheduleChangesLog
 	 * @author TechFinium 
 	 * @see    ScheduleChangesLog
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		schedulechangeslogInfo();
	}

	/**
	 * Get all ScheduleChangesLog for data table
 	 * @author TechFinium 
 	 * @see    ScheduleChangesLog
 	 */
	public void schedulechangeslogInfo() {
	 
			
			 dataModel = new LazyDataModel<ScheduleChangesLog>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<ScheduleChangesLog> retorno = new  ArrayList<ScheduleChangesLog>();
			   
			   @Override 
			   public List<ScheduleChangesLog> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allScheduleChangesLog(ScheduleChangesLog.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(ScheduleChangesLog.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(ScheduleChangesLog obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public ScheduleChangesLog getRowData(String rowKey) {
			        for(ScheduleChangesLog obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert ScheduleChangesLog into database
 	 * @author TechFinium 
 	 * @see    ScheduleChangesLog
 	 */
	public void schedulechangeslogInsert() {
		try {
				 service.create(this.schedulechangeslog);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 schedulechangeslogInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ScheduleChangesLog in database
 	 * @author TechFinium 
 	 * @see    ScheduleChangesLog
 	 */
    public void schedulechangeslogUpdate() {
		try {
				 service.update(this.schedulechangeslog);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 schedulechangeslogInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ScheduleChangesLog from database
 	 * @author TechFinium 
 	 * @see    ScheduleChangesLog
 	 */
	public void schedulechangeslogDelete() {
		try {
			 service.delete(this.schedulechangeslog);
			  prepareNew();
			 schedulechangeslogInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ScheduleChangesLog 
 	 * @author TechFinium 
 	 * @see    ScheduleChangesLog
 	 */
	public void prepareNew() {
		schedulechangeslog = new ScheduleChangesLog();
	}

/*
    public List<SelectItem> getScheduleChangesLogDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	schedulechangeslogInfo();
    	for (ScheduleChangesLog ug : schedulechangeslogList) {
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
    public List<ScheduleChangesLog> complete(String desc) {
		List<ScheduleChangesLog> l = null;
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
    
    public List<ScheduleChangesLog> getScheduleChangesLogList() {
		return schedulechangeslogList;
	}
	public void setScheduleChangesLogList(List<ScheduleChangesLog> schedulechangeslogList) {
		this.schedulechangeslogList = schedulechangeslogList;
	}
	public ScheduleChangesLog getSchedulechangeslog() {
		return schedulechangeslog;
	}
	public void setSchedulechangeslog(ScheduleChangesLog schedulechangeslog) {
		this.schedulechangeslog = schedulechangeslog;
	}

    public List<ScheduleChangesLog> getScheduleChangesLogfilteredList() {
		return schedulechangeslogfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param schedulechangeslogfilteredList the new schedulechangeslogfilteredList list
 	 * @see    ScheduleChangesLog
 	 */	
	public void setScheduleChangesLogfilteredList(List<ScheduleChangesLog> schedulechangeslogfilteredList) {
		this.schedulechangeslogfilteredList = schedulechangeslogfilteredList;
	}

	
	public LazyDataModel<ScheduleChangesLog> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ScheduleChangesLog> dataModel) {
		this.dataModel = dataModel;
	}
	
}
