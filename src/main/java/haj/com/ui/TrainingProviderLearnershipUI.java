package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.TrainingProviderLearnership;
import haj.com.service.TrainingProviderLearnershipService;
import org.primefaces.model.LazyDataModel;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "trainingproviderlearnershipUI")
@ViewScoped
public class TrainingProviderLearnershipUI extends AbstractUI {

	private TrainingProviderLearnershipService service = new TrainingProviderLearnershipService();
	private List<TrainingProviderLearnership> trainingproviderlearnershipList = null;
	private List<TrainingProviderLearnership> trainingproviderlearnershipfilteredList = null;
	private TrainingProviderLearnership trainingproviderlearnership = null;
	private LazyDataModel<TrainingProviderLearnership> dataModel; 

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
	 * Initialize method to get all TrainingProviderLearnership and prepare a for a create of a new TrainingProviderLearnership
 	 * @author TechFinium 
 	 * @see    TrainingProviderLearnership
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		//trainingproviderlearnershipInfo();
	}

	/**
	 * Get all TrainingProviderLearnership for data table
 	 * @author TechFinium 
 	 * @see    TrainingProviderLearnership
 	 */
	/*public void trainingproviderlearnershipInfo() {
			dataModel = new TrainingProviderLearnershipDatamodel();
	}*/

	/**
	 * Insert TrainingProviderLearnership into database
 	 * @author TechFinium 
 	 * @see    TrainingProviderLearnership
 	 */
	public void trainingproviderlearnershipInsert() {
		try {
				 service.create(this.trainingproviderlearnership);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 //trainingproviderlearnershipInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TrainingProviderLearnership in database
 	 * @author TechFinium 
 	 * @see    TrainingProviderLearnership
 	 */
    public void trainingproviderlearnershipUpdate() {
		try {
				 service.update(this.trainingproviderlearnership);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 //trainingproviderlearnershipInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TrainingProviderLearnership from database
 	 * @author TechFinium 
 	 * @see    TrainingProviderLearnership
 	 */
	public void trainingproviderlearnershipDelete() {
		try {
			 service.delete(this.trainingproviderlearnership);
			  prepareNew();
			 //trainingproviderlearnershipInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TrainingProviderLearnership 
 	 * @author TechFinium 
 	 * @see    TrainingProviderLearnership
 	 */
	public void prepareNew() {
		trainingproviderlearnership = new TrainingProviderLearnership();
	}

/*
    public List<SelectItem> getTrainingProviderLearnershipDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	trainingproviderlearnershipInfo();
    	for (TrainingProviderLearnership ug : trainingproviderlearnershipList) {
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
    public List<TrainingProviderLearnership> complete(String desc) {
		List<TrainingProviderLearnership> l = null;
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
    
    public List<TrainingProviderLearnership> getTrainingProviderLearnershipList() {
		return trainingproviderlearnershipList;
	}
	public void setTrainingProviderLearnershipList(List<TrainingProviderLearnership> trainingproviderlearnershipList) {
		this.trainingproviderlearnershipList = trainingproviderlearnershipList;
	}
	public TrainingProviderLearnership getTrainingproviderlearnership() {
		return trainingproviderlearnership;
	}
	public void setTrainingproviderlearnership(TrainingProviderLearnership trainingproviderlearnership) {
		this.trainingproviderlearnership = trainingproviderlearnership;
	}

    public List<TrainingProviderLearnership> getTrainingProviderLearnershipfilteredList() {
		return trainingproviderlearnershipfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param trainingproviderlearnershipfilteredList the new trainingproviderlearnershipfilteredList list
 	 * @see    TrainingProviderLearnership
 	 */	
	public void setTrainingProviderLearnershipfilteredList(List<TrainingProviderLearnership> trainingproviderlearnershipfilteredList) {
		this.trainingproviderlearnershipfilteredList = trainingproviderlearnershipfilteredList;
	}

	
	public LazyDataModel<TrainingProviderLearnership> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderLearnership> dataModel) {
		this.dataModel = dataModel;
	}
	
}
