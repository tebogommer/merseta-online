package  haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.CompanyLearnersQualificationAchievementStatus;
import haj.com.service.CompanyLearnersQualificationAchievementStatusService;
import haj.com.entity.datamodel.CompanyLearnersQualificationAchievementStatusDatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "companylearnersqualificationachievementstatusUI")
@ViewScoped
public class CompanyLearnersQualificationAchievementStatusUI extends AbstractUI {

	private CompanyLearnersQualificationAchievementStatusService service = new CompanyLearnersQualificationAchievementStatusService();
	private List<CompanyLearnersQualificationAchievementStatus> companylearnersqualificationachievementstatusList = null;
	private List<CompanyLearnersQualificationAchievementStatus> companylearnersqualificationachievementstatusfilteredList = null;
	private CompanyLearnersQualificationAchievementStatus companylearnersqualificationachievementstatus = null;
	private LazyDataModel<CompanyLearnersQualificationAchievementStatus> dataModel; 

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
	 * Initialize method to get all CompanyLearnersQualificationAchievementStatus and prepare a for a create of a new CompanyLearnersQualificationAchievementStatus
 	 * @author TechFinium 
 	 * @see    CompanyLearnersQualificationAchievementStatus
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		companylearnersqualificationachievementstatusInfo();
	}

	/**
	 * Get all CompanyLearnersQualificationAchievementStatus for data table
 	 * @author TechFinium 
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 */
	public void companylearnersqualificationachievementstatusInfo() {
//			dataModel = new CompanyLearnersQualificationAchievementStatusDatamodel();
	}

	/**
	 * Insert CompanyLearnersQualificationAchievementStatus into database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 */
	public void companylearnersqualificationachievementstatusInsert() {
		try {
				 service.create(this.companylearnersqualificationachievementstatus);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnersqualificationachievementstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompanyLearnersQualificationAchievementStatus in database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 */
    public void companylearnersqualificationachievementstatusUpdate() {
		try {
				 service.update(this.companylearnersqualificationachievementstatus);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 companylearnersqualificationachievementstatusInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompanyLearnersQualificationAchievementStatus from database
 	 * @author TechFinium 
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 */
	public void companylearnersqualificationachievementstatusDelete() {
		try {
			 service.delete(this.companylearnersqualificationachievementstatus);
			  prepareNew();
			 companylearnersqualificationachievementstatusInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompanyLearnersQualificationAchievementStatus 
 	 * @author TechFinium 
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 */
	public void prepareNew() {
		companylearnersqualificationachievementstatus = new CompanyLearnersQualificationAchievementStatus();
	}

/*
    public List<SelectItem> getCompanyLearnersQualificationAchievementStatusDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	companylearnersqualificationachievementstatusInfo();
    	for (CompanyLearnersQualificationAchievementStatus ug : companylearnersqualificationachievementstatusList) {
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
    public List<CompanyLearnersQualificationAchievementStatus> complete(String desc) {
		List<CompanyLearnersQualificationAchievementStatus> l = null;
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
    
    public List<CompanyLearnersQualificationAchievementStatus> getCompanyLearnersQualificationAchievementStatusList() {
		return companylearnersqualificationachievementstatusList;
	}
	public void setCompanyLearnersQualificationAchievementStatusList(List<CompanyLearnersQualificationAchievementStatus> companylearnersqualificationachievementstatusList) {
		this.companylearnersqualificationachievementstatusList = companylearnersqualificationachievementstatusList;
	}
	public CompanyLearnersQualificationAchievementStatus getCompanylearnersqualificationachievementstatus() {
		return companylearnersqualificationachievementstatus;
	}
	public void setCompanylearnersqualificationachievementstatus(CompanyLearnersQualificationAchievementStatus companylearnersqualificationachievementstatus) {
		this.companylearnersqualificationachievementstatus = companylearnersqualificationachievementstatus;
	}

    public List<CompanyLearnersQualificationAchievementStatus> getCompanyLearnersQualificationAchievementStatusfilteredList() {
		return companylearnersqualificationachievementstatusfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param companylearnersqualificationachievementstatusfilteredList the new companylearnersqualificationachievementstatusfilteredList list
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 */	
	public void setCompanyLearnersQualificationAchievementStatusfilteredList(List<CompanyLearnersQualificationAchievementStatus> companylearnersqualificationachievementstatusfilteredList) {
		this.companylearnersqualificationachievementstatusfilteredList = companylearnersqualificationachievementstatusfilteredList;
	}

	
	public LazyDataModel<CompanyLearnersQualificationAchievementStatus> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersQualificationAchievementStatus> dataModel) {
		this.dataModel = dataModel;
	}
	
}
