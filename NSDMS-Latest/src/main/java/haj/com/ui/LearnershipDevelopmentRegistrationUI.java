package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.LearnershipDevelopmentRegistrationService;

@ManagedBean(name = "learnershipdevelopmentregistrationUI")
@ViewScoped
public class LearnershipDevelopmentRegistrationUI extends AbstractUI {

	private LearnershipDevelopmentRegistrationService service = new LearnershipDevelopmentRegistrationService();
	private List<LearnershipDevelopmentRegistration> learnershipdevelopmentregistrationList = null;
	private List<LearnershipDevelopmentRegistration> learnershipdevelopmentregistrationfilteredList = null;
	private LearnershipDevelopmentRegistration learnershipdevelopmentregistration = null;
	private LazyDataModel<LearnershipDevelopmentRegistration> dataModel; 

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
	 * Initialize method to get all LearnershipDevelopmentRegistration and prepare a for a create of a new LearnershipDevelopmentRegistration
 	 * @author TechFinium 
 	 * @see    LearnershipDevelopmentRegistration
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		learnershipdevelopmentregistrationInfo();
	}

	/**
	 * Get all LearnershipDevelopmentRegistration for data table
 	 * @author TechFinium 
 	 * @see    LearnershipDevelopmentRegistration
 	 */
	public void learnershipdevelopmentregistrationInfo() {
	 
			
			 dataModel = new LazyDataModel<LearnershipDevelopmentRegistration>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<LearnershipDevelopmentRegistration> retorno = new  ArrayList<LearnershipDevelopmentRegistration>();
			   
			   @Override 
			   public List<LearnershipDevelopmentRegistration> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allLearnershipDevelopmentRegistration(LearnershipDevelopmentRegistration.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(LearnershipDevelopmentRegistration.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(LearnershipDevelopmentRegistration obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public LearnershipDevelopmentRegistration getRowData(String rowKey) {
			        for(LearnershipDevelopmentRegistration obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert LearnershipDevelopmentRegistration into database
 	 * @author TechFinium 
 	 * @see    LearnershipDevelopmentRegistration
 	 */
	public void learnershipdevelopmentregistrationInsert() {
		try {
				 service.create(this.learnershipdevelopmentregistration);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnershipdevelopmentregistrationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update LearnershipDevelopmentRegistration in database
 	 * @author TechFinium 
 	 * @see    LearnershipDevelopmentRegistration
 	 */
    public void learnershipdevelopmentregistrationUpdate() {
		try {
				 service.update(this.learnershipdevelopmentregistration);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 learnershipdevelopmentregistrationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete LearnershipDevelopmentRegistration from database
 	 * @author TechFinium 
 	 * @see    LearnershipDevelopmentRegistration
 	 */
	public void learnershipdevelopmentregistrationDelete() {
		try {
			 service.delete(this.learnershipdevelopmentregistration);
			  prepareNew();
			 learnershipdevelopmentregistrationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of LearnershipDevelopmentRegistration 
 	 * @author TechFinium 
 	 * @see    LearnershipDevelopmentRegistration
 	 */
	public void prepareNew() {
		learnershipdevelopmentregistration = new LearnershipDevelopmentRegistration();
	}

/*
    public List<SelectItem> getLearnershipDevelopmentRegistrationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	learnershipdevelopmentregistrationInfo();
    	for (LearnershipDevelopmentRegistration ug : learnershipdevelopmentregistrationList) {
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
    public List<LearnershipDevelopmentRegistration> complete(String desc) {
		List<LearnershipDevelopmentRegistration> l = null;
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
    
    public List<LearnershipDevelopmentRegistration> getLearnershipDevelopmentRegistrationList() {
		return learnershipdevelopmentregistrationList;
	}
	public void setLearnershipDevelopmentRegistrationList(List<LearnershipDevelopmentRegistration> learnershipdevelopmentregistrationList) {
		this.learnershipdevelopmentregistrationList = learnershipdevelopmentregistrationList;
	}
	public LearnershipDevelopmentRegistration getLearnershipdevelopmentregistration() {
		return learnershipdevelopmentregistration;
	}
	public void setLearnershipdevelopmentregistration(LearnershipDevelopmentRegistration learnershipdevelopmentregistration) {
		this.learnershipdevelopmentregistration = learnershipdevelopmentregistration;
	}

    public List<LearnershipDevelopmentRegistration> getLearnershipDevelopmentRegistrationfilteredList() {
		return learnershipdevelopmentregistrationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param learnershipdevelopmentregistrationfilteredList the new learnershipdevelopmentregistrationfilteredList list
 	 * @see    LearnershipDevelopmentRegistration
 	 */	
	public void setLearnershipDevelopmentRegistrationfilteredList(List<LearnershipDevelopmentRegistration> learnershipdevelopmentregistrationfilteredList) {
		this.learnershipdevelopmentregistrationfilteredList = learnershipdevelopmentregistrationfilteredList;
	}

	
	public LazyDataModel<LearnershipDevelopmentRegistration> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LearnershipDevelopmentRegistration> dataModel) {
		this.dataModel = dataModel;
	}
	
}
