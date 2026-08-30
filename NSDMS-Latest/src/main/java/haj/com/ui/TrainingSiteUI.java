package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.TrainingSite;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TrainingSiteService;

@ManagedBean(name = "trainingsiteUI")
@ViewScoped
public class TrainingSiteUI extends AbstractUI {

	private TrainingSiteService service = new TrainingSiteService();
	private List<TrainingSite> trainingsiteList = null;
	private List<TrainingSite> trainingsitefilteredList = null;
	private TrainingSite trainingsite = null;
	private LazyDataModel<TrainingSite> dataModel; 

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
	 * Initialize method to get all TrainingSite and prepare a for a create of a new TrainingSite
 	 * @author TechFinium 
 	 * @see    TrainingSite
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		trainingsiteInfo();
	}

	/**
	 * Get all TrainingSite for data table
 	 * @author TechFinium 
 	 * @see    TrainingSite
 	 */
	public void trainingsiteInfo() {
//			dataModel = new TrainingSiteDatamodel();
	}

	/**
	 * Insert TrainingSite into database
 	 * @author TechFinium 
 	 * @see    TrainingSite
 	 */
	public void trainingsiteInsert() {
		try {
				 service.create(this.trainingsite);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingsiteInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TrainingSite in database
 	 * @author TechFinium 
 	 * @see    TrainingSite
 	 */
    public void trainingsiteUpdate() {
		try {
				 service.update(this.trainingsite);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingsiteInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TrainingSite from database
 	 * @author TechFinium 
 	 * @see    TrainingSite
 	 */
	public void trainingsiteDelete() {
		try {
			 service.delete(this.trainingsite);
			  prepareNew();
			 trainingsiteInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TrainingSite 
 	 * @author TechFinium 
 	 * @see    TrainingSite
 	 */
	public void prepareNew() {
		trainingsite = new TrainingSite();
	}

/*
    public List<SelectItem> getTrainingSiteDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	trainingsiteInfo();
    	for (TrainingSite ug : trainingsiteList) {
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
    public List<TrainingSite> complete(String desc) {
		List<TrainingSite> l = null;
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
    
    public List<TrainingSite> getTrainingSiteList() {
		return trainingsiteList;
	}
	public void setTrainingSiteList(List<TrainingSite> trainingsiteList) {
		this.trainingsiteList = trainingsiteList;
	}
	public TrainingSite getTrainingsite() {
		return trainingsite;
	}
	public void setTrainingsite(TrainingSite trainingsite) {
		this.trainingsite = trainingsite;
	}

    public List<TrainingSite> getTrainingSitefilteredList() {
		return trainingsitefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param trainingsitefilteredList the new trainingsitefilteredList list
 	 * @see    TrainingSite
 	 */	
	public void setTrainingSitefilteredList(List<TrainingSite> trainingsitefilteredList) {
		this.trainingsitefilteredList = trainingsitefilteredList;
	}

	
	public LazyDataModel<TrainingSite> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingSite> dataModel) {
		this.dataModel = dataModel;
	}
	
}
