package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.Training;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.TrainingService;

@ManagedBean(name = "trainingUI")
@ViewScoped
public class TrainingUI extends AbstractUI {

	private TrainingService service = new TrainingService();
	private List<Training> trainingList = null;
	private List<Training> trainingfilteredList = null;
	private Training training = null;
	private LazyDataModel<Training> dataModel; 

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
	 * Initialize method to get all Training and prepare a for a create of a new Training
 	 * @author TechFinium 
 	 * @see    Training
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		trainingInfo();
	}

	/**
	 * Get all Training for data table
 	 * @author TechFinium 
 	 * @see    Training
 	 */
	public void trainingInfo() {
	 
			
			 dataModel = new LazyDataModel<Training>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Training> retorno = new  ArrayList<Training>();
			   
			   @Override 
			   public List<Training> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTraining(Training.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(Training.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Training obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Training getRowData(String rowKey) {
			        for(Training obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert Training into database
 	 * @author TechFinium 
 	 * @see    Training
 	 */
	public void trainingInsert() {
		try {
				 service.create(this.training);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update Training in database
 	 * @author TechFinium 
 	 * @see    Training
 	 */
    public void trainingUpdate() {
		try {
				 service.update(this.training);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete Training from database
 	 * @author TechFinium 
 	 * @see    Training
 	 */
	public void trainingDelete() {
		try {
			 service.delete(this.training);
			  prepareNew();
			 trainingInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of Training 
 	 * @author TechFinium 
 	 * @see    Training
 	 */
	public void prepareNew() {
		training = new Training();
	}

/*
    public List<SelectItem> getTrainingDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	trainingInfo();
    	for (Training ug : trainingList) {
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
    public List<Training> complete(String desc) {
		List<Training> l = null;
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
    
    public List<Training> getTrainingList() {
		return trainingList;
	}
	public void setTrainingList(List<Training> trainingList) {
		this.trainingList = trainingList;
	}
	public Training getTraining() {
		return training;
	}
	public void setTraining(Training training) {
		this.training = training;
	}

    public List<Training> getTrainingfilteredList() {
		return trainingfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param trainingfilteredList the new trainingfilteredList list
 	 * @see    Training
 	 */	
	public void setTrainingfilteredList(List<Training> trainingfilteredList) {
		this.trainingfilteredList = trainingfilteredList;
	}

	
	public LazyDataModel<Training> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Training> dataModel) {
		this.dataModel = dataModel;
	}
	
}
