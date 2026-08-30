package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.service.TrainingProviderSkillsSetService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "trainingproviderskillssetUI")
@ViewScoped
public class TrainingProviderSkillsSetUI extends AbstractUI {

	private TrainingProviderSkillsSetService service = new TrainingProviderSkillsSetService();
	private List<TrainingProviderSkillsSet> trainingproviderskillssetList = null;
	private List<TrainingProviderSkillsSet> trainingproviderskillssetfilteredList = null;
	private TrainingProviderSkillsSet trainingproviderskillsset = null;
	private LazyDataModel<TrainingProviderSkillsSet> dataModel; 

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
	 * Initialize method to get all TrainingProviderSkillsSet and prepare a for a create of a new TrainingProviderSkillsSet
 	 * @author TechFinium 
 	 * @see    TrainingProviderSkillsSet
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		trainingproviderskillssetInfo();
	}

	/**
	 * Get all TrainingProviderSkillsSet for data table
 	 * @author TechFinium 
 	 * @see    TrainingProviderSkillsSet
 	 */
	public void trainingproviderskillssetInfo() {
			//dataModel = new TrainingProviderSkillsSetDatamodel();
	}

	/**
	 * Insert TrainingProviderSkillsSet into database
 	 * @author TechFinium 
 	 * @see    TrainingProviderSkillsSet
 	 */
	public void trainingproviderskillssetInsert() {
		try {
				 service.create(this.trainingproviderskillsset);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingproviderskillssetInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TrainingProviderSkillsSet in database
 	 * @author TechFinium 
 	 * @see    TrainingProviderSkillsSet
 	 */
    public void trainingproviderskillssetUpdate() {
		try {
				 service.update(this.trainingproviderskillsset);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingproviderskillssetInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TrainingProviderSkillsSet from database
 	 * @author TechFinium 
 	 * @see    TrainingProviderSkillsSet
 	 */
	public void trainingproviderskillssetDelete() {
		try {
			 service.delete(this.trainingproviderskillsset);
			  prepareNew();
			 trainingproviderskillssetInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TrainingProviderSkillsSet 
 	 * @author TechFinium 
 	 * @see    TrainingProviderSkillsSet
 	 */
	public void prepareNew() {
		trainingproviderskillsset = new TrainingProviderSkillsSet();
	}

/*
    public List<SelectItem> getTrainingProviderSkillsSetDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	trainingproviderskillssetInfo();
    	for (TrainingProviderSkillsSet ug : trainingproviderskillssetList) {
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
    public List<TrainingProviderSkillsSet> complete(String desc) {
		List<TrainingProviderSkillsSet> l = null;
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
    
    public List<TrainingProviderSkillsSet> getTrainingProviderSkillsSetList() {
		return trainingproviderskillssetList;
	}
	public void setTrainingProviderSkillsSetList(List<TrainingProviderSkillsSet> trainingproviderskillssetList) {
		this.trainingproviderskillssetList = trainingproviderskillssetList;
	}
	public TrainingProviderSkillsSet getTrainingproviderskillsset() {
		return trainingproviderskillsset;
	}
	public void setTrainingproviderskillsset(TrainingProviderSkillsSet trainingproviderskillsset) {
		this.trainingproviderskillsset = trainingproviderskillsset;
	}

    public List<TrainingProviderSkillsSet> getTrainingProviderSkillsSetfilteredList() {
		return trainingproviderskillssetfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param trainingproviderskillssetfilteredList the new trainingproviderskillssetfilteredList list
 	 * @see    TrainingProviderSkillsSet
 	 */	
	public void setTrainingProviderSkillsSetfilteredList(List<TrainingProviderSkillsSet> trainingproviderskillssetfilteredList) {
		this.trainingproviderskillssetfilteredList = trainingproviderskillssetfilteredList;
	}

	
	public LazyDataModel<TrainingProviderSkillsSet> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderSkillsSet> dataModel) {
		this.dataModel = dataModel;
	}
	
}
