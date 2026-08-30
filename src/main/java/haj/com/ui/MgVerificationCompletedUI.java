package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.MgVerificationCompleted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MgVerificationCompletedService;

@ManagedBean(name = "mgverificationcompletedUI")
@ViewScoped
public class MgVerificationCompletedUI extends AbstractUI {

	private MgVerificationCompletedService service = new MgVerificationCompletedService();
	private List<MgVerificationCompleted> mgverificationcompletedList = null;
	private List<MgVerificationCompleted> mgverificationcompletedfilteredList = null;
	private MgVerificationCompleted mgverificationcompleted = null;
	private LazyDataModel<MgVerificationCompleted> dataModel; 

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
	 * Initialize method to get all MgVerificationCompleted and prepare a for a create of a new MgVerificationCompleted
 	 * @author TechFinium 
 	 * @see    MgVerificationCompleted
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		mgverificationcompletedInfo();
	}

	/**
	 * Get all MgVerificationCompleted for data table
 	 * @author TechFinium 
 	 * @see    MgVerificationCompleted
 	 */
	public void mgverificationcompletedInfo() {
	 
			
			 dataModel = new LazyDataModel<MgVerificationCompleted>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<MgVerificationCompleted> retorno = new  ArrayList<MgVerificationCompleted>();
			   
			   @Override 
			   public List<MgVerificationCompleted> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allMgVerificationCompleted(MgVerificationCompleted.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(MgVerificationCompleted.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(MgVerificationCompleted obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public MgVerificationCompleted getRowData(String rowKey) {
			        for(MgVerificationCompleted obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert MgVerificationCompleted into database
 	 * @author TechFinium 
 	 * @see    MgVerificationCompleted
 	 */
	public void mgverificationcompletedInsert() {
		try {
				 service.create(this.mgverificationcompleted);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 mgverificationcompletedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update MgVerificationCompleted in database
 	 * @author TechFinium 
 	 * @see    MgVerificationCompleted
 	 */
    public void mgverificationcompletedUpdate() {
		try {
				 service.update(this.mgverificationcompleted);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 mgverificationcompletedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete MgVerificationCompleted from database
 	 * @author TechFinium 
 	 * @see    MgVerificationCompleted
 	 */
	public void mgverificationcompletedDelete() {
		try {
			 service.delete(this.mgverificationcompleted);
			  prepareNew();
			 mgverificationcompletedInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of MgVerificationCompleted 
 	 * @author TechFinium 
 	 * @see    MgVerificationCompleted
 	 */
	public void prepareNew() {
		mgverificationcompleted = new MgVerificationCompleted();
	}

/*
    public List<SelectItem> getMgVerificationCompletedDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	mgverificationcompletedInfo();
    	for (MgVerificationCompleted ug : mgverificationcompletedList) {
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
    public List<MgVerificationCompleted> complete(String desc) {
		List<MgVerificationCompleted> l = null;
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
    
    public List<MgVerificationCompleted> getMgVerificationCompletedList() {
		return mgverificationcompletedList;
	}
	public void setMgVerificationCompletedList(List<MgVerificationCompleted> mgverificationcompletedList) {
		this.mgverificationcompletedList = mgverificationcompletedList;
	}
	public MgVerificationCompleted getMgverificationcompleted() {
		return mgverificationcompleted;
	}
	public void setMgverificationcompleted(MgVerificationCompleted mgverificationcompleted) {
		this.mgverificationcompleted = mgverificationcompleted;
	}

    public List<MgVerificationCompleted> getMgVerificationCompletedfilteredList() {
		return mgverificationcompletedfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param mgverificationcompletedfilteredList the new mgverificationcompletedfilteredList list
 	 * @see    MgVerificationCompleted
 	 */	
	public void setMgVerificationCompletedfilteredList(List<MgVerificationCompleted> mgverificationcompletedfilteredList) {
		this.mgverificationcompletedfilteredList = mgverificationcompletedfilteredList;
	}

	
	public LazyDataModel<MgVerificationCompleted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MgVerificationCompleted> dataModel) {
		this.dataModel = dataModel;
	}
	
}
