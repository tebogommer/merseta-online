package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.TrainingProviderDocParent;
import haj.com.service.TrainingProviderDocParentService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "trainingproviderdocparentUI")
@ViewScoped
public class TrainingProviderDocParentUI extends AbstractUI {

	private TrainingProviderDocParentService service = new TrainingProviderDocParentService();
	private List<TrainingProviderDocParent> trainingproviderdocparentList = null;
	private List<TrainingProviderDocParent> trainingproviderdocparentfilteredList = null;
	private TrainingProviderDocParent trainingproviderdocparent = null;
	private LazyDataModel<TrainingProviderDocParent> dataModel; 

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
	 * Initialize method to get all TrainingProviderDocParent and prepare a for a create of a new TrainingProviderDocParent
 	 * @author TechFinium 
 	 * @see    TrainingProviderDocParent
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		trainingproviderdocparentInfo();
	}

	/**
	 * Get all TrainingProviderDocParent for data table
 	 * @author TechFinium 
 	 * @see    TrainingProviderDocParent
 	 */
	public void trainingproviderdocparentInfo() {
			//dataModel = new TrainingProviderDocParentDatamodel();
	}

	/**
	 * Insert TrainingProviderDocParent into database
 	 * @author TechFinium 
 	 * @see    TrainingProviderDocParent
 	 */
	public void trainingproviderdocparentInsert() {
		try {
				 service.create(this.trainingproviderdocparent);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingproviderdocparentInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update TrainingProviderDocParent in database
 	 * @author TechFinium 
 	 * @see    TrainingProviderDocParent
 	 */
    public void trainingproviderdocparentUpdate() {
		try {
				 service.update(this.trainingproviderdocparent);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 trainingproviderdocparentInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete TrainingProviderDocParent from database
 	 * @author TechFinium 
 	 * @see    TrainingProviderDocParent
 	 */
	public void trainingproviderdocparentDelete() {
		try {
			 service.delete(this.trainingproviderdocparent);
			  prepareNew();
			 trainingproviderdocparentInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of TrainingProviderDocParent 
 	 * @author TechFinium 
 	 * @see    TrainingProviderDocParent
 	 */
	public void prepareNew() {
		trainingproviderdocparent = new TrainingProviderDocParent();
	}

/*
    public List<SelectItem> getTrainingProviderDocParentDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	trainingproviderdocparentInfo();
    	for (TrainingProviderDocParent ug : trainingproviderdocparentList) {
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
    public List<TrainingProviderDocParent> complete(String desc) {
		List<TrainingProviderDocParent> l = null;
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
    
    public List<TrainingProviderDocParent> getTrainingProviderDocParentList() {
		return trainingproviderdocparentList;
	}
	public void setTrainingProviderDocParentList(List<TrainingProviderDocParent> trainingproviderdocparentList) {
		this.trainingproviderdocparentList = trainingproviderdocparentList;
	}
	public TrainingProviderDocParent getTrainingproviderdocparent() {
		return trainingproviderdocparent;
	}
	public void setTrainingproviderdocparent(TrainingProviderDocParent trainingproviderdocparent) {
		this.trainingproviderdocparent = trainingproviderdocparent;
	}

    public List<TrainingProviderDocParent> getTrainingProviderDocParentfilteredList() {
		return trainingproviderdocparentfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param trainingproviderdocparentfilteredList the new trainingproviderdocparentfilteredList list
 	 * @see    TrainingProviderDocParent
 	 */	
	public void setTrainingProviderDocParentfilteredList(List<TrainingProviderDocParent> trainingproviderdocparentfilteredList) {
		this.trainingproviderdocparentfilteredList = trainingproviderdocparentfilteredList;
	}

	
	public LazyDataModel<TrainingProviderDocParent> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderDocParent> dataModel) {
		this.dataModel = dataModel;
	}
	
}
