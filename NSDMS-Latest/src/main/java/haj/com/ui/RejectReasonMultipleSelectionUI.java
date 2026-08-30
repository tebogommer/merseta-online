package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.service.RejectReasonMultipleSelectionService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "rejectreasonmultipleselectionUI")
@ViewScoped
public class RejectReasonMultipleSelectionUI extends AbstractUI {

	private RejectReasonMultipleSelectionService service = new RejectReasonMultipleSelectionService();
	private List<RejectReasonMultipleSelection> rejectreasonmultipleselectionList = null;
	private List<RejectReasonMultipleSelection> rejectreasonmultipleselectionfilteredList = null;
	private RejectReasonMultipleSelection rejectreasonmultipleselection = null;
	private LazyDataModel<RejectReasonMultipleSelection> dataModel; 

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
	 * Initialize method to get all RejectReasonMultipleSelection and prepare a for a create of a new RejectReasonMultipleSelection
 	 * @author TechFinium 
 	 * @see    RejectReasonMultipleSelection
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		rejectreasonmultipleselectionInfo();
	}

	/**
	 * Get all RejectReasonMultipleSelection for data table
 	 * @author TechFinium 
 	 * @see    RejectReasonMultipleSelection
 	 */
	public void rejectreasonmultipleselectionInfo() {
	 
			
			 dataModel = new LazyDataModel<RejectReasonMultipleSelection>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<RejectReasonMultipleSelection> retorno = new  ArrayList<RejectReasonMultipleSelection>();
			   
			   @Override 
			   public List<RejectReasonMultipleSelection> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allRejectReasonMultipleSelection(RejectReasonMultipleSelection.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(RejectReasonMultipleSelection.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(RejectReasonMultipleSelection obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public RejectReasonMultipleSelection getRowData(String rowKey) {
			        for(RejectReasonMultipleSelection obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert RejectReasonMultipleSelection into database
 	 * @author TechFinium 
 	 * @see    RejectReasonMultipleSelection
 	 */
	public void rejectreasonmultipleselectionInsert() {
		try {
				 service.create(this.rejectreasonmultipleselection);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 rejectreasonmultipleselectionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update RejectReasonMultipleSelection in database
 	 * @author TechFinium 
 	 * @see    RejectReasonMultipleSelection
 	 */
    public void rejectreasonmultipleselectionUpdate() {
		try {
				 service.update(this.rejectreasonmultipleselection);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 rejectreasonmultipleselectionInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete RejectReasonMultipleSelection from database
 	 * @author TechFinium 
 	 * @see    RejectReasonMultipleSelection
 	 */
	public void rejectreasonmultipleselectionDelete() {
		try {
			 service.delete(this.rejectreasonmultipleselection);
			  prepareNew();
			 rejectreasonmultipleselectionInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of RejectReasonMultipleSelection 
 	 * @author TechFinium 
 	 * @see    RejectReasonMultipleSelection
 	 */
	public void prepareNew() {
		rejectreasonmultipleselection = new RejectReasonMultipleSelection();
	}

/*
    public List<SelectItem> getRejectReasonMultipleSelectionDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	rejectreasonmultipleselectionInfo();
    	for (RejectReasonMultipleSelection ug : rejectreasonmultipleselectionList) {
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
    public List<RejectReasonMultipleSelection> complete(String desc) {
		List<RejectReasonMultipleSelection> l = null;
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
    
    public List<RejectReasonMultipleSelection> getRejectReasonMultipleSelectionList() {
		return rejectreasonmultipleselectionList;
	}
	public void setRejectReasonMultipleSelectionList(List<RejectReasonMultipleSelection> rejectreasonmultipleselectionList) {
		this.rejectreasonmultipleselectionList = rejectreasonmultipleselectionList;
	}
	public RejectReasonMultipleSelection getRejectreasonmultipleselection() {
		return rejectreasonmultipleselection;
	}
	public void setRejectreasonmultipleselection(RejectReasonMultipleSelection rejectreasonmultipleselection) {
		this.rejectreasonmultipleselection = rejectreasonmultipleselection;
	}

    public List<RejectReasonMultipleSelection> getRejectReasonMultipleSelectionfilteredList() {
		return rejectreasonmultipleselectionfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param rejectreasonmultipleselectionfilteredList the new rejectreasonmultipleselectionfilteredList list
 	 * @see    RejectReasonMultipleSelection
 	 */	
	public void setRejectReasonMultipleSelectionfilteredList(List<RejectReasonMultipleSelection> rejectreasonmultipleselectionfilteredList) {
		this.rejectreasonmultipleselectionfilteredList = rejectreasonmultipleselectionfilteredList;
	}

	
	public LazyDataModel<RejectReasonMultipleSelection> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<RejectReasonMultipleSelection> dataModel) {
		this.dataModel = dataModel;
	}
	
}
