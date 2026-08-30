package  haj.com.ui;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.AssessorModExtensionOfScope;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.service.AssessorModExtensionOfScopeService;
import haj.com.service.UserQualificationsService;
import haj.com.service.UserUnitStandardService;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.exceptions.ValidationException;

@ManagedBean(name = "assessormodextensionofscopeUI")
@ViewScoped
public class AssessorModExtensionOfScopeUI extends AbstractUI {

	private AssessorModExtensionOfScopeService service = new AssessorModExtensionOfScopeService();
	private List<AssessorModExtensionOfScope> assessormodextensionofscopeList = null;
	private List<AssessorModExtensionOfScope> assessormodextensionofscopefilteredList = null;
	private AssessorModExtensionOfScope assessormodextensionofscope = null;
	private LazyDataModel<AssessorModExtensionOfScope> dataModel; 
	/** The user qualifications service. */
	private UserQualificationsService userQualificationsService = new UserQualificationsService();
	
	/** The user unit standard service. */
	private UserUnitStandardService userUnitStandardService = new UserUnitStandardService();

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
	 * Initialize method to get all AssessorModExtensionOfScope and prepare a for a create of a new AssessorModExtensionOfScope
 	 * @author TechFinium 
 	 * @see    AssessorModExtensionOfScope
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		
	}
	

	public ArrayList<UserUnitStandard> userUnitStandard(AssessorModExtensionOfScope assessorModExtensionOfScope)
	{
		ArrayList<UserUnitStandard> l=new ArrayList<>();
		try {
			l= (ArrayList<UserUnitStandard>) userUnitStandardService.findByTargetKeyAndTargetClas(assessorModExtensionOfScope.getId(),AssessorModExtensionOfScope.class.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public  ArrayList<UserQualifications> userQualifications(AssessorModExtensionOfScope assessorModExtensionOfScope)
	{
		 ArrayList<UserQualifications> l=new ArrayList<>();
		try {
			l= (ArrayList<UserQualifications>) userQualificationsService.findByTargetKeyAndTargetClas(assessorModExtensionOfScope.getId(),AssessorModExtensionOfScope.class.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		
		return l;
	}
	
	

	
	/**
	 * Insert AssessorModExtensionOfScope into database
 	 * @author TechFinium 
 	 * @see    AssessorModExtensionOfScope
 	 */
	public void assessormodextensionofscopeInsert() {
		try {
				 service.create(this.assessormodextensionofscope);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AssessorModExtensionOfScope in database
 	 * @author TechFinium 
 	 * @see    AssessorModExtensionOfScope
 	 */
    public void assessormodextensionofscopeUpdate() {
		try {
				 service.update(this.assessormodextensionofscope);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AssessorModExtensionOfScope from database
 	 * @author TechFinium 
 	 * @see    AssessorModExtensionOfScope
 	 */
	public void assessormodextensionofscopeDelete() {
		try {
			 service.delete(this.assessormodextensionofscope);
			  prepareNew();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AssessorModExtensionOfScope 
 	 * @author TechFinium 
 	 * @see    AssessorModExtensionOfScope
 	 */
	public void prepareNew() {
		assessormodextensionofscope = new AssessorModExtensionOfScope();
	}

/*
    public List<SelectItem> getAssessorModExtensionOfScopeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	assessormodextensionofscopeInfo();
    	for (AssessorModExtensionOfScope ug : assessormodextensionofscopeList) {
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
    public List<AssessorModExtensionOfScope> complete(String desc) {
		List<AssessorModExtensionOfScope> l = null;
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
    
    public List<AssessorModExtensionOfScope> getAssessorModExtensionOfScopeList() {
		return assessormodextensionofscopeList;
	}
	public void setAssessorModExtensionOfScopeList(List<AssessorModExtensionOfScope> assessormodextensionofscopeList) {
		this.assessormodextensionofscopeList = assessormodextensionofscopeList;
	}
	public AssessorModExtensionOfScope getAssessormodextensionofscope() {
		return assessormodextensionofscope;
	}
	public void setAssessormodextensionofscope(AssessorModExtensionOfScope assessormodextensionofscope) {
		this.assessormodextensionofscope = assessormodextensionofscope;
	}

    public List<AssessorModExtensionOfScope> getAssessorModExtensionOfScopefilteredList() {
		return assessormodextensionofscopefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param assessormodextensionofscopefilteredList the new assessormodextensionofscopefilteredList list
 	 * @see    AssessorModExtensionOfScope
 	 */	
	public void setAssessorModExtensionOfScopefilteredList(List<AssessorModExtensionOfScope> assessormodextensionofscopefilteredList) {
		this.assessormodextensionofscopefilteredList = assessormodextensionofscopefilteredList;
	}

	
	public LazyDataModel<AssessorModExtensionOfScope> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AssessorModExtensionOfScope> dataModel) {
		this.dataModel = dataModel;
	}
	
}
