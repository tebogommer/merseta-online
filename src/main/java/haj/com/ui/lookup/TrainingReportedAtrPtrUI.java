package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.TrainingReportedAtrPtr;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.TrainingReportedAtrPtrService;

@ManagedBean(name = "trainingreportedatrptrUI")
@ViewScoped
public class TrainingReportedAtrPtrUI extends AbstractUI {

	private TrainingReportedAtrPtrService service = new TrainingReportedAtrPtrService();
	private List<TrainingReportedAtrPtr> trainingreportedatrptrList = null;
	private List<TrainingReportedAtrPtr> trainingreportedatrptrfilteredList = null;
	private TrainingReportedAtrPtr trainingreportedatrptr = null;
	private LazyDataModel<TrainingReportedAtrPtr> dataModel; 

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
	 * Initialize method to get all TrainingReportedAtrPtr and prepare a for a create of a new TrainingReportedAtrPtr
 	 * @author TechFinium 
 	 * @see    TrainingReportedAtrPtr
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		trainingreportedatrptrInfo();
	}

	/**
	 * Get all TrainingReportedAtrPtr for data table
 	 * @author TechFinium 
 	 * @see    TrainingReportedAtrPtr
 	 */
	public void trainingreportedatrptrInfo() {
	 
			
			 dataModel = new LazyDataModel<TrainingReportedAtrPtr>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TrainingReportedAtrPtr> retorno = new  ArrayList<TrainingReportedAtrPtr>();
			   
			   @Override 
			   public List<TrainingReportedAtrPtr> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTrainingReportedAtrPtr(TrainingReportedAtrPtr.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TrainingReportedAtrPtr.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TrainingReportedAtrPtr obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TrainingReportedAtrPtr getRowData(String rowKey) {
			        for(TrainingReportedAtrPtr obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TrainingReportedAtrPtr into database
 	 * @author TechFinium 
 	 * @see    TrainingReportedAtrPtr
 	 */
	public void trainingreportedatrptrInsert() {
		try {
				 service.create(this.trainingreportedatrptr);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingreportedatrptrInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TrainingReportedAtrPtr in database
 	 * @author TechFinium 
 	 * @see    TrainingReportedAtrPtr
 	 */
    public void trainingreportedatrptrUpdate() {
		try {
				 service.update(this.trainingreportedatrptr);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingreportedatrptrInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TrainingReportedAtrPtr from database
 	 * @author TechFinium 
 	 * @see    TrainingReportedAtrPtr
 	 */
	public void trainingreportedatrptrDelete() {
		try {
			 service.delete(this.trainingreportedatrptr);
			  prepareNew();
			 trainingreportedatrptrInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TrainingReportedAtrPtr 
 	 * @author TechFinium 
 	 * @see    TrainingReportedAtrPtr
 	 */
	public void prepareNew() {
		trainingreportedatrptr = new TrainingReportedAtrPtr();
	}

/*
    public List<SelectItem> getTrainingReportedAtrPtrDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	trainingreportedatrptrInfo();
    	for (TrainingReportedAtrPtr ug : trainingreportedatrptrList) {
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
    public List<TrainingReportedAtrPtr> complete(String desc) {
		List<TrainingReportedAtrPtr> l = null;
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
    
    public List<TrainingReportedAtrPtr> getTrainingReportedAtrPtrList() {
		return trainingreportedatrptrList;
	}
	public void setTrainingReportedAtrPtrList(List<TrainingReportedAtrPtr> trainingreportedatrptrList) {
		this.trainingreportedatrptrList = trainingreportedatrptrList;
	}
	public TrainingReportedAtrPtr getTrainingreportedatrptr() {
		return trainingreportedatrptr;
	}
	public void setTrainingreportedatrptr(TrainingReportedAtrPtr trainingreportedatrptr) {
		this.trainingreportedatrptr = trainingreportedatrptr;
	}

    public List<TrainingReportedAtrPtr> getTrainingReportedAtrPtrfilteredList() {
		return trainingreportedatrptrfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param trainingreportedatrptrfilteredList the new trainingreportedatrptrfilteredList list
 	 * @see    TrainingReportedAtrPtr
 	 */	
	public void setTrainingReportedAtrPtrfilteredList(List<TrainingReportedAtrPtr> trainingreportedatrptrfilteredList) {
		this.trainingreportedatrptrfilteredList = trainingreportedatrptrfilteredList;
	}

	
	public LazyDataModel<TrainingReportedAtrPtr> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingReportedAtrPtr> dataModel) {
		this.dataModel = dataModel;
	}
	
}
