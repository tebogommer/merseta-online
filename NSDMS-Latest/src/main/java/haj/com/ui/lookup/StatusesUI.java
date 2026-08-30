package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Statuses;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.StatusesService;

@ManagedBean(name = "statusesUI")
@ViewScoped
public class StatusesUI extends AbstractUI {

	private StatusesService service = new StatusesService();
	private List<Statuses> statusesList = null;
	private List<Statuses> statusesfilteredList = null;
	private Statuses statuses = null;
	private LazyDataModel<Statuses> dataModel; 

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
	 * Initialize method to get all Statuses and prepare a for a create of a new Statuses
 	 * @author TechFinium 
 	 * @see    Statuses
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		statusesInfo();
	}

	/**
	 * Get all Statuses for data table
 	 * @author TechFinium 
 	 * @see    Statuses
 	 */
	public void statusesInfo() {
	 
			
			 dataModel = new LazyDataModel<Statuses>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Statuses> retorno = new  ArrayList<Statuses>();
			   
			   @Override 
			   public List<Statuses> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allStatuses(Statuses.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Statuses.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Statuses obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Statuses getRowData(String rowKey) {
			        for(Statuses obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Statuses into database
 	 * @author TechFinium 
 	 * @see    Statuses
 	 */
	public void statusesInsert() {
		try {
				 service.create(this.statuses);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 statusesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Statuses in database
 	 * @author TechFinium 
 	 * @see    Statuses
 	 */
    public void statusesUpdate() {
		try {
				 service.update(this.statuses);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 statusesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Statuses from database
 	 * @author TechFinium 
 	 * @see    Statuses
 	 */
	public void statusesDelete() {
		try {
			 service.delete(this.statuses);
			  prepareNew();
			 statusesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Statuses 
 	 * @author TechFinium 
 	 * @see    Statuses
 	 */
	public void prepareNew() {
		statuses = new Statuses();
	}

/*
    public List<SelectItem> getStatusesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	statusesInfo();
    	for (Statuses ug : statusesList) {
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
    public List<Statuses> complete(String desc) {
		List<Statuses> l = null;
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
    
    public List<Statuses> getStatusesList() {
		return statusesList;
	}
	public void setStatusesList(List<Statuses> statusesList) {
		this.statusesList = statusesList;
	}
	public Statuses getStatuses() {
		return statuses;
	}
	public void setStatuses(Statuses statuses) {
		this.statuses = statuses;
	}

    public List<Statuses> getStatusesfilteredList() {
		return statusesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param statusesfilteredList the new statusesfilteredList list
 	 * @see    Statuses
 	 */	
	public void setStatusesfilteredList(List<Statuses> statusesfilteredList) {
		this.statusesfilteredList = statusesfilteredList;
	}

	
	public LazyDataModel<Statuses> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Statuses> dataModel) {
		this.dataModel = dataModel;
	}
	
}
