package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AssessorModeratorCompanySitesService;

@ManagedBean(name = "assessormoderatorcompanysitesUI")
@ViewScoped
public class AssessorModeratorCompanySitesUI extends AbstractUI {

	private AssessorModeratorCompanySitesService service = new AssessorModeratorCompanySitesService();
	private List<AssessorModeratorCompanySites> assessormoderatorcompanysitesList = null;
	private List<AssessorModeratorCompanySites> assessormoderatorcompanysitesfilteredList = null;
	private AssessorModeratorCompanySites assessormoderatorcompanysites = null;
	private LazyDataModel<AssessorModeratorCompanySites> dataModel; 

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
	 * Initialize method to get all AssessorModeratorCompanySites and prepare a for a create of a new AssessorModeratorCompanySites
 	 * @author TechFinium 
 	 * @see    AssessorModeratorCompanySites
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		assessormoderatorcompanysitesInfo();
	}

	/**
	 * Get all AssessorModeratorCompanySites for data table
 	 * @author TechFinium 
 	 * @see    AssessorModeratorCompanySites
 	 */
	public void assessormoderatorcompanysitesInfo() {
//		dataModel = new AssessorModeratorCompanySitesDatamodel();
	}

	/**
	 * Insert AssessorModeratorCompanySites into database
 	 * @author TechFinium 
 	 * @see    AssessorModeratorCompanySites
 	 */
	public void assessormoderatorcompanysitesInsert() {
		try {
				 service.create(this.assessormoderatorcompanysites);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 assessormoderatorcompanysitesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AssessorModeratorCompanySites in database
 	 * @author TechFinium 
 	 * @see    AssessorModeratorCompanySites
 	 */
    public void assessormoderatorcompanysitesUpdate() {
		try {
				 service.update(this.assessormoderatorcompanysites);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 assessormoderatorcompanysitesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AssessorModeratorCompanySites from database
 	 * @author TechFinium 
 	 * @see    AssessorModeratorCompanySites
 	 */
	public void assessormoderatorcompanysitesDelete() {
		try {
			 service.delete(this.assessormoderatorcompanysites);
			  prepareNew();
			 assessormoderatorcompanysitesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AssessorModeratorCompanySites 
 	 * @author TechFinium 
 	 * @see    AssessorModeratorCompanySites
 	 */
	public void prepareNew() {
		assessormoderatorcompanysites = new AssessorModeratorCompanySites();
	}

/*
    public List<SelectItem> getAssessorModeratorCompanySitesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	assessormoderatorcompanysitesInfo();
    	for (AssessorModeratorCompanySites ug : assessormoderatorcompanysitesList) {
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
    public List<AssessorModeratorCompanySites> complete(String desc) {
		List<AssessorModeratorCompanySites> l = null;
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
    
    public List<AssessorModeratorCompanySites> getAssessorModeratorCompanySitesList() {
		return assessormoderatorcompanysitesList;
	}
	public void setAssessorModeratorCompanySitesList(List<AssessorModeratorCompanySites> assessormoderatorcompanysitesList) {
		this.assessormoderatorcompanysitesList = assessormoderatorcompanysitesList;
	}
	public AssessorModeratorCompanySites getAssessormoderatorcompanysites() {
		return assessormoderatorcompanysites;
	}
	public void setAssessormoderatorcompanysites(AssessorModeratorCompanySites assessormoderatorcompanysites) {
		this.assessormoderatorcompanysites = assessormoderatorcompanysites;
	}

    public List<AssessorModeratorCompanySites> getAssessorModeratorCompanySitesfilteredList() {
		return assessormoderatorcompanysitesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param assessormoderatorcompanysitesfilteredList the new assessormoderatorcompanysitesfilteredList list
 	 * @see    AssessorModeratorCompanySites
 	 */	
	public void setAssessorModeratorCompanySitesfilteredList(List<AssessorModeratorCompanySites> assessormoderatorcompanysitesfilteredList) {
		this.assessormoderatorcompanysitesfilteredList = assessormoderatorcompanysitesfilteredList;
	}

	
	public LazyDataModel<AssessorModeratorCompanySites> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AssessorModeratorCompanySites> dataModel) {
		this.dataModel = dataModel;
	}
	
}
