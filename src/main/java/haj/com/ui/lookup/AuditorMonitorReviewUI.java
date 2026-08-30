package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.AuditorMonitorReviewService;

@ManagedBean(name = "auditormonitorreviewUI")
@ViewScoped
public class AuditorMonitorReviewUI extends AbstractUI {

	private AuditorMonitorReviewService service = new AuditorMonitorReviewService();
	private List<AuditorMonitorReview> auditormonitorreviewList = null;
	private List<AuditorMonitorReview> auditormonitorreviewfilteredList = null;
	private AuditorMonitorReview auditormonitorreview = null;
	private LazyDataModel<AuditorMonitorReview> dataModel; 

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
	 * Initialize method to get all AuditorMonitorReview and prepare a for a create of a new AuditorMonitorReview
 	 * @author TechFinium 
 	 * @see    AuditorMonitorReview
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		auditormonitorreviewInfo();
	}

	/**
	 * Get all AuditorMonitorReview for data table
 	 * @author TechFinium 
 	 * @see    AuditorMonitorReview
 	 */
	public void auditormonitorreviewInfo() {
	 
			
			 dataModel = new LazyDataModel<AuditorMonitorReview>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<AuditorMonitorReview> retorno = new  ArrayList<AuditorMonitorReview>();
			   
			   @Override 
			   public List<AuditorMonitorReview> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					
					//filters.put("forProcess", ConfigDocProcessEnum.TP);
					retorno = service.allAuditorMonitorReview(AuditorMonitorReview.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.countWhere(AuditorMonitorReview.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(AuditorMonitorReview obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public AuditorMonitorReview getRowData(String rowKey) {
			        for(AuditorMonitorReview obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert AuditorMonitorReview into database
 	 * @author TechFinium 
 	 * @see    AuditorMonitorReview
 	 */
	public void auditormonitorreviewInsert() {
		try {
				 service.create(this.auditormonitorreview);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 auditormonitorreviewInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AuditorMonitorReview in database
 	 * @author TechFinium 
 	 * @see    AuditorMonitorReview
 	 */
    public void auditormonitorreviewUpdate() {
		try {
				 service.update(this.auditormonitorreview);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 auditormonitorreviewInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AuditorMonitorReview from database
 	 * @author TechFinium 
 	 * @see    AuditorMonitorReview
 	 */
	public void auditormonitorreviewDelete() {
		try {
			 service.delete(this.auditormonitorreview);
			  prepareNew();
			 auditormonitorreviewInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AuditorMonitorReview 
 	 * @author TechFinium 
 	 * @see    AuditorMonitorReview
 	 */
	public void prepareNew() {
		auditormonitorreview = new AuditorMonitorReview();
	}

/*
    public List<SelectItem> getAuditorMonitorReviewDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	auditormonitorreviewInfo();
    	for (AuditorMonitorReview ug : auditormonitorreviewList) {
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
    public List<AuditorMonitorReview> complete(String desc) {
		List<AuditorMonitorReview> l = null;
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
    
    public List<AuditorMonitorReview> getAuditorMonitorReviewList() {
		return auditormonitorreviewList;
	}
	public void setAuditorMonitorReviewList(List<AuditorMonitorReview> auditormonitorreviewList) {
		this.auditormonitorreviewList = auditormonitorreviewList;
	}
	public AuditorMonitorReview getAuditormonitorreview() {
		return auditormonitorreview;
	}
	public void setAuditormonitorreview(AuditorMonitorReview auditormonitorreview) {
		this.auditormonitorreview = auditormonitorreview;
	}

    public List<AuditorMonitorReview> getAuditorMonitorReviewfilteredList() {
		return auditormonitorreviewfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param auditormonitorreviewfilteredList the new auditormonitorreviewfilteredList list
 	 * @see    AuditorMonitorReview
 	 */	
	public void setAuditorMonitorReviewfilteredList(List<AuditorMonitorReview> auditormonitorreviewfilteredList) {
		this.auditormonitorreviewfilteredList = auditormonitorreviewfilteredList;
	}

	
	public LazyDataModel<AuditorMonitorReview> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AuditorMonitorReview> dataModel) {
		this.dataModel = dataModel;
	}
	
}
