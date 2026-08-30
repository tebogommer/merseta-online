package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.UpdateAuditTrailChanges;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.UpdateAuditTrailChangesService;

@ManagedBean(name = "updateaudittrailchangesUI")
@ViewScoped
public class UpdateAuditTrailChangesUI extends AbstractUI {

	private UpdateAuditTrailChangesService service = new UpdateAuditTrailChangesService();
	private List<UpdateAuditTrailChanges> updateaudittrailchangesList = null;
	private List<UpdateAuditTrailChanges> updateaudittrailchangesfilteredList = null;
	private UpdateAuditTrailChanges updateaudittrailchanges = null;
	private LazyDataModel<UpdateAuditTrailChanges> dataModel; 

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
	 * Initialize method to get all UpdateAuditTrailChanges and prepare a for a create of a new UpdateAuditTrailChanges
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrailChanges
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		updateaudittrailchangesInfo();
	}

	/**
	 * Get all UpdateAuditTrailChanges for data table
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrailChanges
 	 */
	public void updateaudittrailchangesInfo() {
	 
			
			 dataModel = new LazyDataModel<UpdateAuditTrailChanges>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<UpdateAuditTrailChanges> retorno = new  ArrayList<UpdateAuditTrailChanges>();
			   
			   @Override 
			   public List<UpdateAuditTrailChanges> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allUpdateAuditTrailChanges(UpdateAuditTrailChanges.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(UpdateAuditTrailChanges.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(UpdateAuditTrailChanges obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public UpdateAuditTrailChanges getRowData(String rowKey) {
			        for(UpdateAuditTrailChanges obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert UpdateAuditTrailChanges into database
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrailChanges
 	 */
	public void updateaudittrailchangesInsert() {
		try {
				 service.create(this.updateaudittrailchanges);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 updateaudittrailchangesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UpdateAuditTrailChanges in database
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrailChanges
 	 */
    public void updateaudittrailchangesUpdate() {
		try {
				 service.update(this.updateaudittrailchanges);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 updateaudittrailchangesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UpdateAuditTrailChanges from database
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrailChanges
 	 */
	public void updateaudittrailchangesDelete() {
		try {
			 service.delete(this.updateaudittrailchanges);
			  prepareNew();
			 updateaudittrailchangesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UpdateAuditTrailChanges 
 	 * @author TechFinium 
 	 * @see    UpdateAuditTrailChanges
 	 */
	public void prepareNew() {
		updateaudittrailchanges = new UpdateAuditTrailChanges();
	}

/*
    public List<SelectItem> getUpdateAuditTrailChangesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	updateaudittrailchangesInfo();
    	for (UpdateAuditTrailChanges ug : updateaudittrailchangesList) {
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
    public List<UpdateAuditTrailChanges> complete(String desc) {
		List<UpdateAuditTrailChanges> l = null;
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
    
    public List<UpdateAuditTrailChanges> getUpdateAuditTrailChangesList() {
		return updateaudittrailchangesList;
	}
	public void setUpdateAuditTrailChangesList(List<UpdateAuditTrailChanges> updateaudittrailchangesList) {
		this.updateaudittrailchangesList = updateaudittrailchangesList;
	}
	public UpdateAuditTrailChanges getUpdateaudittrailchanges() {
		return updateaudittrailchanges;
	}
	public void setUpdateaudittrailchanges(UpdateAuditTrailChanges updateaudittrailchanges) {
		this.updateaudittrailchanges = updateaudittrailchanges;
	}

    public List<UpdateAuditTrailChanges> getUpdateAuditTrailChangesfilteredList() {
		return updateaudittrailchangesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param updateaudittrailchangesfilteredList the new updateaudittrailchangesfilteredList list
 	 * @see    UpdateAuditTrailChanges
 	 */	
	public void setUpdateAuditTrailChangesfilteredList(List<UpdateAuditTrailChanges> updateaudittrailchangesfilteredList) {
		this.updateaudittrailchangesfilteredList = updateaudittrailchangesfilteredList;
	}

	
	public LazyDataModel<UpdateAuditTrailChanges> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UpdateAuditTrailChanges> dataModel) {
		this.dataModel = dataModel;
	}
	
}
