package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.TrainingDeliveryMethod;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.TrainingDeliveryMethodService;

@ManagedBean(name = "trainingdeliverymethodUI")
@ViewScoped
public class TrainingDeliveryMethodUI extends AbstractUI {

	private TrainingDeliveryMethodService service = new TrainingDeliveryMethodService();
	private List<TrainingDeliveryMethod> trainingdeliverymethodList = null;
	private List<TrainingDeliveryMethod> trainingdeliverymethodfilteredList = null;
	private TrainingDeliveryMethod trainingdeliverymethod = null;
	private LazyDataModel<TrainingDeliveryMethod> dataModel; 

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
	 * Initialize method to get all TrainingDeliveryMethod and prepare a for a create of a new TrainingDeliveryMethod
 	 * @author TechFinium 
 	 * @see    TrainingDeliveryMethod
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		trainingdeliverymethodInfo();
	}

	/**
	 * Get all TrainingDeliveryMethod for data table
 	 * @author TechFinium 
 	 * @see    TrainingDeliveryMethod
 	 */
	public void trainingdeliverymethodInfo() {
	 
			
			 dataModel = new LazyDataModel<TrainingDeliveryMethod>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TrainingDeliveryMethod> retorno = new  ArrayList<TrainingDeliveryMethod>();
			   
			   @Override 
			   public List<TrainingDeliveryMethod> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTrainingDeliveryMethod(TrainingDeliveryMethod.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TrainingDeliveryMethod.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TrainingDeliveryMethod obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TrainingDeliveryMethod getRowData(String rowKey) {
			        for(TrainingDeliveryMethod obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TrainingDeliveryMethod into database
 	 * @author TechFinium 
 	 * @see    TrainingDeliveryMethod
 	 */
	public void trainingdeliverymethodInsert() {
		try {
				 service.create(this.trainingdeliverymethod);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingdeliverymethodInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TrainingDeliveryMethod in database
 	 * @author TechFinium 
 	 * @see    TrainingDeliveryMethod
 	 */
    public void trainingdeliverymethodUpdate() {
		try {
				 service.update(this.trainingdeliverymethod);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingdeliverymethodInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TrainingDeliveryMethod from database
 	 * @author TechFinium 
 	 * @see    TrainingDeliveryMethod
 	 */
	public void trainingdeliverymethodDelete() {
		try {
			 service.delete(this.trainingdeliverymethod);
			  prepareNew();
			 trainingdeliverymethodInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TrainingDeliveryMethod 
 	 * @author TechFinium 
 	 * @see    TrainingDeliveryMethod
 	 */
	public void prepareNew() {
		trainingdeliverymethod = new TrainingDeliveryMethod();
	}

/*
    public List<SelectItem> getTrainingDeliveryMethodDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	trainingdeliverymethodInfo();
    	for (TrainingDeliveryMethod ug : trainingdeliverymethodList) {
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
    public List<TrainingDeliveryMethod> complete(String desc) {
		List<TrainingDeliveryMethod> l = null;
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
    
    public List<TrainingDeliveryMethod> getTrainingDeliveryMethodList() {
		return trainingdeliverymethodList;
	}
	public void setTrainingDeliveryMethodList(List<TrainingDeliveryMethod> trainingdeliverymethodList) {
		this.trainingdeliverymethodList = trainingdeliverymethodList;
	}
	public TrainingDeliveryMethod getTrainingdeliverymethod() {
		return trainingdeliverymethod;
	}
	public void setTrainingdeliverymethod(TrainingDeliveryMethod trainingdeliverymethod) {
		this.trainingdeliverymethod = trainingdeliverymethod;
	}

    public List<TrainingDeliveryMethod> getTrainingDeliveryMethodfilteredList() {
		return trainingdeliverymethodfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param trainingdeliverymethodfilteredList the new trainingdeliverymethodfilteredList list
 	 * @see    TrainingDeliveryMethod
 	 */	
	public void setTrainingDeliveryMethodfilteredList(List<TrainingDeliveryMethod> trainingdeliverymethodfilteredList) {
		this.trainingdeliverymethodfilteredList = trainingdeliverymethodfilteredList;
	}

	
	public LazyDataModel<TrainingDeliveryMethod> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingDeliveryMethod> dataModel) {
		this.dataModel = dataModel;
	}
	
}
