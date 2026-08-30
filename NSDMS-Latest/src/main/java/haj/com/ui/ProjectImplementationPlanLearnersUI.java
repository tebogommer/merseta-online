package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.ProjectImplementationPlanLearners;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ProjectImplementationPlanLearnersService;

@ManagedBean(name = "projectimplementationplanlearnersUI")
@ViewScoped
public class ProjectImplementationPlanLearnersUI extends AbstractUI {

	private ProjectImplementationPlanLearnersService service = new ProjectImplementationPlanLearnersService();
	private List<ProjectImplementationPlanLearners> projectimplementationplanlearnersList = null;
	private List<ProjectImplementationPlanLearners> projectimplementationplanlearnersfilteredList = null;
	private ProjectImplementationPlanLearners projectimplementationplanlearners = null;
	private LazyDataModel<ProjectImplementationPlanLearners> dataModel; 

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
	 * Initialize method to get all ProjectImplementationPlanLearners and prepare a for a create of a new ProjectImplementationPlanLearners
 	 * @author TechFinium 
 	 * @see    ProjectImplementationPlanLearners
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		projectimplementationplanlearnersInfo();
	}

	/**
	 * Get all ProjectImplementationPlanLearners for data table
 	 * @author TechFinium 
 	 * @see    ProjectImplementationPlanLearners
 	 */
	public void projectimplementationplanlearnersInfo() {
//			dataModel = new ProjectImplementationPlanLearnersDatamodel();
	}

	/**
	 * Insert ProjectImplementationPlanLearners into database
 	 * @author TechFinium 
 	 * @see    ProjectImplementationPlanLearners
 	 */
	public void projectimplementationplanlearnersInsert() {
		try {
				 service.create(this.projectimplementationplanlearners);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 projectimplementationplanlearnersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update ProjectImplementationPlanLearners in database
 	 * @author TechFinium 
 	 * @see    ProjectImplementationPlanLearners
 	 */
    public void projectimplementationplanlearnersUpdate() {
		try {
				 service.update(this.projectimplementationplanlearners);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 projectimplementationplanlearnersInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ProjectImplementationPlanLearners from database
 	 * @author TechFinium 
 	 * @see    ProjectImplementationPlanLearners
 	 */
	public void projectimplementationplanlearnersDelete() {
		try {
			 service.delete(this.projectimplementationplanlearners);
			  prepareNew();
			 projectimplementationplanlearnersInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of ProjectImplementationPlanLearners 
 	 * @author TechFinium 
 	 * @see    ProjectImplementationPlanLearners
 	 */
	public void prepareNew() {
		projectimplementationplanlearners = new ProjectImplementationPlanLearners();
	}

/*
    public List<SelectItem> getProjectImplementationPlanLearnersDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	projectimplementationplanlearnersInfo();
    	for (ProjectImplementationPlanLearners ug : projectimplementationplanlearnersList) {
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
    public List<ProjectImplementationPlanLearners> complete(String desc) {
		List<ProjectImplementationPlanLearners> l = null;
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
    
    public List<ProjectImplementationPlanLearners> getProjectImplementationPlanLearnersList() {
		return projectimplementationplanlearnersList;
	}
	public void setProjectImplementationPlanLearnersList(List<ProjectImplementationPlanLearners> projectimplementationplanlearnersList) {
		this.projectimplementationplanlearnersList = projectimplementationplanlearnersList;
	}
	public ProjectImplementationPlanLearners getProjectimplementationplanlearners() {
		return projectimplementationplanlearners;
	}
	public void setProjectimplementationplanlearners(ProjectImplementationPlanLearners projectimplementationplanlearners) {
		this.projectimplementationplanlearners = projectimplementationplanlearners;
	}

    public List<ProjectImplementationPlanLearners> getProjectImplementationPlanLearnersfilteredList() {
		return projectimplementationplanlearnersfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param projectimplementationplanlearnersfilteredList the new projectimplementationplanlearnersfilteredList list
 	 * @see    ProjectImplementationPlanLearners
 	 */	
	public void setProjectImplementationPlanLearnersfilteredList(List<ProjectImplementationPlanLearners> projectimplementationplanlearnersfilteredList) {
		this.projectimplementationplanlearnersfilteredList = projectimplementationplanlearnersfilteredList;
	}

	
	public LazyDataModel<ProjectImplementationPlanLearners> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ProjectImplementationPlanLearners> dataModel) {
		this.dataModel = dataModel;
	}
	
}
