package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.AssessorModReRegistration;
import haj.com.service.AssessorModReRegistrationService;
import org.primefaces.model.LazyDataModel;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "assessormodreregistrationUI")
@ViewScoped
public class AssessorModReRegistrationUI extends AbstractUI {

	private AssessorModReRegistrationService service = new AssessorModReRegistrationService();
	private List<AssessorModReRegistration> assessormodreregistrationList = null;
	private List<AssessorModReRegistration> assessormodreregistrationfilteredList = null;
	private AssessorModReRegistration assessormodreregistration = null;
	private LazyDataModel<AssessorModReRegistration> dataModel; 

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
	 * Initialize method to get all AssessorModReRegistration and prepare a for a create of a new AssessorModReRegistration
 	 * @author TechFinium 
 	 * @see    AssessorModReRegistration
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		assessormodreregistrationInfo();
	}

	/**
	 * Get all AssessorModReRegistration for data table
 	 * @author TechFinium 
 	 * @see    AssessorModReRegistration
 	 */
	public void assessormodreregistrationInfo() {
			//dataModel = new AssessorModReRegistrationDatamodel();
	}

	/**
	 * Insert AssessorModReRegistration into database
 	 * @author TechFinium 
 	 * @see    AssessorModReRegistration
 	 */
	public void assessormodreregistrationInsert() {
		try {
				 service.create(this.assessormodreregistration);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 assessormodreregistrationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AssessorModReRegistration in database
 	 * @author TechFinium 
 	 * @see    AssessorModReRegistration
 	 */
    public void assessormodreregistrationUpdate() {
		try {
				 service.update(this.assessormodreregistration);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 assessormodreregistrationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AssessorModReRegistration from database
 	 * @author TechFinium 
 	 * @see    AssessorModReRegistration
 	 */
	public void assessormodreregistrationDelete() {
		try {
			 service.delete(this.assessormodreregistration);
			  prepareNew();
			 assessormodreregistrationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AssessorModReRegistration 
 	 * @author TechFinium 
 	 * @see    AssessorModReRegistration
 	 */
	public void prepareNew() {
		assessormodreregistration = new AssessorModReRegistration();
	}

/*
    public List<SelectItem> getAssessorModReRegistrationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	assessormodreregistrationInfo();
    	for (AssessorModReRegistration ug : assessormodreregistrationList) {
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
    public List<AssessorModReRegistration> complete(String desc) {
		List<AssessorModReRegistration> l = null;
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
    
    public List<AssessorModReRegistration> getAssessorModReRegistrationList() {
		return assessormodreregistrationList;
	}
	public void setAssessorModReRegistrationList(List<AssessorModReRegistration> assessormodreregistrationList) {
		this.assessormodreregistrationList = assessormodreregistrationList;
	}
	public AssessorModReRegistration getAssessormodreregistration() {
		return assessormodreregistration;
	}
	public void setAssessormodreregistration(AssessorModReRegistration assessormodreregistration) {
		this.assessormodreregistration = assessormodreregistration;
	}

    public List<AssessorModReRegistration> getAssessorModReRegistrationfilteredList() {
		return assessormodreregistrationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param assessormodreregistrationfilteredList the new assessormodreregistrationfilteredList list
 	 * @see    AssessorModReRegistration
 	 */	
	public void setAssessorModReRegistrationfilteredList(List<AssessorModReRegistration> assessormodreregistrationfilteredList) {
		this.assessormodreregistrationfilteredList = assessormodreregistrationfilteredList;
	}

	
	public LazyDataModel<AssessorModReRegistration> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AssessorModReRegistration> dataModel) {
		this.dataModel = dataModel;
	}
	
}
