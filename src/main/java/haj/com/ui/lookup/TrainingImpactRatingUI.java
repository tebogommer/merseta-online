package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.TrainingImpactRating;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.TrainingImpactRatingService;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingImpactRatingUI.
 */
@ManagedBean(name = "trainingimpactratingUI")
@ViewScoped
public class TrainingImpactRatingUI extends AbstractUI {

	/** The service. */
	private TrainingImpactRatingService service = new TrainingImpactRatingService();
	
	/** The trainingimpactrating list. */
	private List<TrainingImpactRating> trainingimpactratingList = null;
	
	/** The trainingimpactratingfiltered list. */
	private List<TrainingImpactRating> trainingimpactratingfilteredList = null;
	
	/** The trainingimpactrating. */
	private TrainingImpactRating trainingimpactrating = null;
	
	/** The data model. */
	private LazyDataModel<TrainingImpactRating> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all TrainingImpactRating and prepare a for a create of a new TrainingImpactRating.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    TrainingImpactRating
	 */
	private void runInit() throws Exception {
		prepareNew();
		trainingimpactratingInfo();
	}

	/**
	 * Get all TrainingImpactRating for data table.
	 *
	 * @author TechFinium
	 * @see    TrainingImpactRating
	 */
	public void trainingimpactratingInfo() {
	 
			
			 dataModel = new LazyDataModel<TrainingImpactRating>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TrainingImpactRating> retorno = new  ArrayList<TrainingImpactRating>();
			   
			   @Override 
			   public List<TrainingImpactRating> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allTrainingImpactRating(TrainingImpactRating.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(TrainingImpactRating.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TrainingImpactRating obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TrainingImpactRating getRowData(String rowKey) {
			        for(TrainingImpactRating obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert TrainingImpactRating into database.
	 *
	 * @author TechFinium
	 * @see    TrainingImpactRating
	 */
	public void trainingimpactratingInsert() {
		try {
				 service.create(this.trainingimpactrating);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingimpactratingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TrainingImpactRating in database.
	 *
	 * @author TechFinium
	 * @see    TrainingImpactRating
	 */
    public void trainingimpactratingUpdate() {
		try {
				 service.update(this.trainingimpactrating);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingimpactratingInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TrainingImpactRating from database.
	 *
	 * @author TechFinium
	 * @see    TrainingImpactRating
	 */
	public void trainingimpactratingDelete() {
		try {
			 service.delete(this.trainingimpactrating);
			  prepareNew();
			 trainingimpactratingInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TrainingImpactRating .
	 *
	 * @author TechFinium
	 * @see    TrainingImpactRating
	 */
	public void prepareNew() {
		trainingimpactrating = new TrainingImpactRating();
	}

/*
    public List<SelectItem> getTrainingImpactRatingDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	trainingimpactratingInfo();
    	for (TrainingImpactRating ug : trainingimpactratingList) {
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
    public List<TrainingImpactRating> complete(String desc) {
		List<TrainingImpactRating> l = null;
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
    
    /**
     * Gets the training impact rating list.
     *
     * @return the training impact rating list
     */
    public List<TrainingImpactRating> getTrainingImpactRatingList() {
		return trainingimpactratingList;
	}
	
	/**
	 * Sets the training impact rating list.
	 *
	 * @param trainingimpactratingList the new training impact rating list
	 */
	public void setTrainingImpactRatingList(List<TrainingImpactRating> trainingimpactratingList) {
		this.trainingimpactratingList = trainingimpactratingList;
	}
	
	/**
	 * Gets the trainingimpactrating.
	 *
	 * @return the trainingimpactrating
	 */
	public TrainingImpactRating getTrainingimpactrating() {
		return trainingimpactrating;
	}
	
	/**
	 * Sets the trainingimpactrating.
	 *
	 * @param trainingimpactrating the new trainingimpactrating
	 */
	public void setTrainingimpactrating(TrainingImpactRating trainingimpactrating) {
		this.trainingimpactrating = trainingimpactrating;
	}

    /**
     * Gets the training impact ratingfiltered list.
     *
     * @return the training impact ratingfiltered list
     */
    public List<TrainingImpactRating> getTrainingImpactRatingfilteredList() {
		return trainingimpactratingfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param trainingimpactratingfilteredList the new trainingimpactratingfilteredList list
	 * @see    TrainingImpactRating
	 */	
	public void setTrainingImpactRatingfilteredList(List<TrainingImpactRating> trainingimpactratingfilteredList) {
		this.trainingimpactratingfilteredList = trainingimpactratingfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<TrainingImpactRating> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<TrainingImpactRating> dataModel) {
		this.dataModel = dataModel;
	}
	
}
