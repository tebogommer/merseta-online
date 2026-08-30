package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.UserSkillsProgramme;
import haj.com.service.UserSkillsProgrammeService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "userskillsprogrammeUI")
@ViewScoped
public class UserSkillsProgrammeUI extends AbstractUI {

	private UserSkillsProgrammeService service = new UserSkillsProgrammeService();
	private List<UserSkillsProgramme> userskillsprogrammeList = null;
	private List<UserSkillsProgramme> userskillsprogrammefilteredList = null;
	private UserSkillsProgramme userskillsprogramme = null;
	private LazyDataModel<UserSkillsProgramme> dataModel; 

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
	 * Initialize method to get all UserSkillsProgramme and prepare a for a create of a new UserSkillsProgramme
 	 * @author TechFinium 
 	 * @see    UserSkillsProgramme
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		//userskillsprogrammeInfo();
	}

	/**
	 * Get all UserSkillsProgramme for data table
 	 * @author TechFinium 
 	 * @see    UserSkillsProgramme
 	 */
	/*
	 * public void userskillsprogrammeInfo() { dataModel = new
	 * UserSkillsProgrammeDatamodel(); }
	 */

	/**
	 * Insert UserSkillsProgramme into database
 	 * @author TechFinium 
 	 * @see    UserSkillsProgramme
 	 */
	public void userskillsprogrammeInsert() {
		try {
				 service.create(this.userskillsprogramme);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 //userskillsprogrammeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update UserSkillsProgramme in database
 	 * @author TechFinium 
 	 * @see    UserSkillsProgramme
 	 */
    public void userskillsprogrammeUpdate() {
		try {
				 service.update(this.userskillsprogramme);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 //userskillsprogrammeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete UserSkillsProgramme from database
 	 * @author TechFinium 
 	 * @see    UserSkillsProgramme
 	 */
	public void userskillsprogrammeDelete() {
		try {
			 service.delete(this.userskillsprogramme);
			  prepareNew();
			 //userskillsprogrammeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of UserSkillsProgramme 
 	 * @author TechFinium 
 	 * @see    UserSkillsProgramme
 	 */
	public void prepareNew() {
		userskillsprogramme = new UserSkillsProgramme();
	}

/*
    public List<SelectItem> getUserSkillsProgrammeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	userskillsprogrammeInfo();
    	for (UserSkillsProgramme ug : userskillsprogrammeList) {
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
    public List<UserSkillsProgramme> complete(String desc) {
		List<UserSkillsProgramme> l = null;
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
    
    public List<UserSkillsProgramme> getUserSkillsProgrammeList() {
		return userskillsprogrammeList;
	}
	public void setUserSkillsProgrammeList(List<UserSkillsProgramme> userskillsprogrammeList) {
		this.userskillsprogrammeList = userskillsprogrammeList;
	}
	public UserSkillsProgramme getUserskillsprogramme() {
		return userskillsprogramme;
	}
	public void setUserskillsprogramme(UserSkillsProgramme userskillsprogramme) {
		this.userskillsprogramme = userskillsprogramme;
	}

    public List<UserSkillsProgramme> getUserSkillsProgrammefilteredList() {
		return userskillsprogrammefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param userskillsprogrammefilteredList the new userskillsprogrammefilteredList list
 	 * @see    UserSkillsProgramme
 	 */	
	public void setUserSkillsProgrammefilteredList(List<UserSkillsProgramme> userskillsprogrammefilteredList) {
		this.userskillsprogrammefilteredList = userskillsprogrammefilteredList;
	}

	
	public LazyDataModel<UserSkillsProgramme> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<UserSkillsProgramme> dataModel) {
		this.dataModel = dataModel;
	}
	
}
