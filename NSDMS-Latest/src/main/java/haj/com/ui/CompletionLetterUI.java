package  haj.com.ui;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.CompletionLetter;
import haj.com.entity.Doc;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.RejectReasons;
import haj.com.service.CompletionLetterService;
import haj.com.service.DocService;
import haj.com.service.TasksService;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.service.lookup.RejectReasonsService;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "completionletterUI")
@ViewScoped
public class CompletionLetterUI extends AbstractUI {

	private CompletionLetterService service = new CompletionLetterService();
	private List<CompletionLetter> completionletterList = null;
	private List<CompletionLetter> completionletterfilteredList = null;
	private CompletionLetter completionletter = null;
	private LazyDataModel<CompletionLetter> dataModel; 
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	private List<RejectReasons> rejectReason=new ArrayList<>();
	private Doc doc;
	private InterventionTypeService interventionTypeService = new InterventionTypeService();
	private InterventionType interventionType = null;
	
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
	 * Initialize method to get all CompletionLetter and prepare a for a create of a new CompletionLetter
 	 * @author TechFinium 
 	 * @see    CompletionLetter
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.CompletionLetter) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.completionletter = service.findByKeyAndPopulateLearnerDocs(getSessionUI().getTask().getTargetKey());
			service.prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.CompletionLetter, completionletter, completionletter, getSessionUI().getTask().getProcessRole());
			interventionType = interventionTypeService.findByKey(this.completionletter.getCompanyLearners().getInterventionType().getId());
			if(completionletter.getStatus() ==ApprovalEnum.Rejected) {
				populateRejectReasons();
			}
		} else {
			prepareNew();
			completionletterInfo();
		}		
	}
	
	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(completionletter.getId(), CompletionLetter.class.getName(), ConfigDocProcessEnum.CompletionLetter);			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Get all CompletionLetter for data table
 	 * @author TechFinium 
 	 * @see    CompletionLetter
 	 */
	public void completionletterInfo() {
			//dataModel = new CompletionLetterDatamodel();
	}

	/**
	 * Insert CompletionLetter into database
 	 * @author TechFinium 
 	 * @see    CompletionLetter
 	 */
	public void completionletterInsert() {
		try {
				 service.create(this.completionletter);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 completionletterInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CompletionLetter in database
 	 * @author TechFinium 
 	 * @see    CompletionLetter
 	 */
    public void completionletterUpdate() {
		try {
				 service.update(this.completionletter);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 completionletterInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CompletionLetter from database
 	 * @author TechFinium 
 	 * @see    CompletionLetter
 	 */
	public void completionletterDelete() {
		try {
			 service.delete(this.completionletter);
			  prepareNew();
			 completionletterInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CompletionLetter 
 	 * @author TechFinium 
 	 * @see    CompletionLetter
 	 */
	public void prepareNew() {
		completionletter = new CompletionLetter();
	}

	public void completeWorkflow() {
		try {
			service.completeWorkflow(completionletter, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}


	public void approveWorkflow() {
		try {
			service.approveWorkflow(completionletter, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.rejectWorkflow(completionletter, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(completionletter, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalRejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.finalRejectWorkflow(completionletter, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setReviewDateForApproval() {
		try {
			service.update(completionletter);
			addInfoMessage("Date Added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.CompletionLetter);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(completionletter.getId());
				doc.setTargetClass(CompletionLetter.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<CompletionLetter> complete(String desc) {
		List<CompletionLetter> l = null;
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
    
    public List<CompletionLetter> getCompletionLetterList() {
		return completionletterList;
	}
	public void setCompletionLetterList(List<CompletionLetter> completionletterList) {
		this.completionletterList = completionletterList;
	}
	public CompletionLetter getCompletionletter() {
		return completionletter;
	}
	public void setCompletionletter(CompletionLetter completionletter) {
		this.completionletter = completionletter;
	}

    public List<CompletionLetter> getCompletionLetterfilteredList() {
		return completionletterfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param completionletterfilteredList the new completionletterfilteredList list
 	 * @see    CompletionLetter
 	 */	
	public void setCompletionLetterfilteredList(List<CompletionLetter> completionletterfilteredList) {
		this.completionletterfilteredList = completionletterfilteredList;
	}

	
	public LazyDataModel<CompletionLetter> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompletionLetter> dataModel) {
		this.dataModel = dataModel;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public InterventionType getInterventionType() {
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType) {
		this.interventionType = interventionType;
	}
	
}
