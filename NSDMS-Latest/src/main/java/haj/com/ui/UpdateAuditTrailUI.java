package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.UpdateAuditTrail;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UpdateAuditTrailService;

@ManagedBean(name = "updateaudittrailUI")
@ViewScoped
public class UpdateAuditTrailUI extends AbstractUI {

	private UpdateAuditTrailService service = new UpdateAuditTrailService();
	private List<UpdateAuditTrail> updateaudittrailList = null;
	private List<UpdateAuditTrail> updateaudittrailfilteredList = null;
	private UpdateAuditTrail updateaudittrail = null;
	private LazyDataModel<UpdateAuditTrail> dataModel; 

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
	 * Initialize method to get all UpdateAuditTrail and prepare a for a create of a new UpdateAuditTrail
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrail
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		updateaudittrailInfo();
	}

	/**
	 * Get all UpdateAuditTrail for data table
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrail
 	 */
	public void updateaudittrailInfo() {
	 
			
			 dataModel = new LazyDataModel<UpdateAuditTrail>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UpdateAuditTrail> retorno = new  ArrayList<UpdateAuditTrail>();
			   
			   @Override 
			   public List<UpdateAuditTrail> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUpdateAuditTrail(UpdateAuditTrail.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UpdateAuditTrail.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UpdateAuditTrail obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UpdateAuditTrail getRowData(String rowKey) {
			        for(UpdateAuditTrail obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UpdateAuditTrail into database
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrail
 	 */
	public void updateaudittrailInsert() {
		try {
				 service.create(this.updateaudittrail);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 updateaudittrailInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UpdateAuditTrail in database
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrail
 	 */
    public void updateaudittrailUpdate() {
		try {
				 service.update(this.updateaudittrail);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 updateaudittrailInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UpdateAuditTrail from database
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrail
 	 */
	public void updateaudittrailDelete() {
		try {
			 service.delete(this.updateaudittrail);
			  prepareNew();
			 updateaudittrailInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UpdateAuditTrail 
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrail
 	 */
	public void prepareNew() {
		updateaudittrail = new UpdateAuditTrail();
	}

/*
    public List<SelectItem> getUpdateAuditTrailDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	updateaudittrailInfo();
    	for (UpdateAuditTrail ug : updateaudittrailList) {
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
    public List<UpdateAuditTrail> complete(String desc) {
		List<UpdateAuditTrail> l = null;
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
    
    public List<UpdateAuditTrail> getUpdateAuditTrailList() {
		return updateaudittrailList;
	}
	public void setUpdateAuditTrailList(List<UpdateAuditTrail> updateaudittrailList) {
		this.updateaudittrailList = updateaudittrailList;
	}
	public UpdateAuditTrail getUpdateaudittrail() {
		return updateaudittrail;
	}
	public void setUpdateaudittrail(UpdateAuditTrail updateaudittrail) {
		this.updateaudittrail = updateaudittrail;
	}

    public List<UpdateAuditTrail> getUpdateAuditTrailfilteredList() {
		return updateaudittrailfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param updateaudittrailfilteredList the new updateaudittrailfilteredList list
 	 * @see    UpdateAuditTrail
 	 */	
	public void setUpdateAuditTrailfilteredList(List<UpdateAuditTrail> updateaudittrailfilteredList) {
		this.updateaudittrailfilteredList = updateaudittrailfilteredList;
	}

	
	public LazyDataModel<UpdateAuditTrail> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UpdateAuditTrail> dataModel) {
		this.dataModel = dataModel;
	}
	
}
