package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.MeetingAgenda;
import haj.com.service.lookup.MeetingAgendaService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "meetingAgendaUI")
@ViewScoped
public class MeetingAgendaUI extends AbstractUI {

	private MeetingAgendaService service = new MeetingAgendaService();
	private List<MeetingAgenda> meetingAgendaList = null;
	private List<MeetingAgenda> meetingAgendafilteredList = null;
	private MeetingAgenda meetingAgenda = null;
	private LazyDataModel<MeetingAgenda> dataModel; 
	
	/**The Decision Number syntax  */
	public String DECISION_NUM_SYNTAX = "ETQA/YY/XXXX/99";

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
	 * Initialize method to get all MeetingAgenda and prepare a for a create of a new MeetingAgenda
 	 * @author TechFinium 
 	 * @see    MeetingAgenda
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		meetingAgendaInfo();
	}

	/**
	 * Get all MeetingAgenda for data table
 	 * @author TechFinium 
 	 * @see    MeetingAgenda
 	 */
	public void meetingAgendaInfo() {
	 
			
			 dataModel = new LazyDataModel<MeetingAgenda>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<MeetingAgenda> retorno = new  ArrayList<MeetingAgenda>();
			   
			   @Override 
			   public List<MeetingAgenda> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allMeetingAgenda(MeetingAgenda.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(MeetingAgenda.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(MeetingAgenda obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public MeetingAgenda getRowData(String rowKey) {
			        for(MeetingAgenda obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert MeetingAgenda into database
 	 * @author TechFinium 
 	 * @see    MeetingAgenda
 	 */
	public void meetingAgendaInsert() {
		try {
				 service.create(this.meetingAgenda);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 meetingAgendaInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update MeetingAgenda in database
 	 * @author TechFinium 
 	 * @see    MeetingAgenda
 	 */
    public void meetingAgendaUpdate() {
		try {
				 service.update(this.meetingAgenda);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 meetingAgendaInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete MeetingAgenda from database
 	 * @author TechFinium 
 	 * @see    MeetingAgenda
 	 */
	public void meetingAgendaDelete() {
		try {
			 service.delete(this.meetingAgenda);
			  prepareNew();
			 meetingAgendaInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of MeetingAgenda 
 	 * @author TechFinium 
 	 * @see    MeetingAgenda
 	 */
	public void prepareNew() {
		meetingAgenda = new MeetingAgenda();
	}

/*
    public List<SelectItem> getMeetingAgendaDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	meetingAgendaInfo();
    	for (MeetingAgenda ug : meetingAgendaList) {
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
    public List<MeetingAgenda> complete(String desc) {
		List<MeetingAgenda> l = null;
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
    
    public List<MeetingAgenda> getMeetingAgendaList() {
		return meetingAgendaList;
	}
	public void setMeetingAgendaList(List<MeetingAgenda> meetingAgendaList) {
		this.meetingAgendaList = meetingAgendaList;
	}
	public MeetingAgenda getMeetingAgenda() {
		return meetingAgenda;
	}
	public void setMeetingAgenda(MeetingAgenda meetingAgenda) {
		this.meetingAgenda = meetingAgenda;
	}

    public List<MeetingAgenda> getMeetingAgendafilteredList() {
		return meetingAgendafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param meetingAgendafilteredList the new meetingAgendafilteredList list
 	 * @see    MeetingAgenda
 	 */	
	public void setMeetingAgendafilteredList(List<MeetingAgenda> meetingAgendafilteredList) {
		this.meetingAgendafilteredList = meetingAgendafilteredList;
	}

	
	public LazyDataModel<MeetingAgenda> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MeetingAgenda> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * @return the dECISION_NUM_SYNTAX
	 */
	public String getDECISION_NUM_SYNTAX() {
		return DECISION_NUM_SYNTAX;
	}

	/**
	 * @param dECISION_NUM_SYNTAX the dECISION_NUM_SYNTAX to set
	 */
	public void setDECISION_NUM_SYNTAX(String dECISION_NUM_SYNTAX) {
		DECISION_NUM_SYNTAX = dECISION_NUM_SYNTAX;
	}
	
}
