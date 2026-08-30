package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.Doc;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersTransferService;
import haj.com.service.DocService;
import haj.com.service.TasksService;
import haj.com.service.lookup.RejectReasonsService;


@ManagedBean(name = "learnerTransferWorkflowUI")
@ViewScoped
public class LearnerTransferWorkflowUI extends AbstractUI {
//	
//	/** Entity */
//	private CompanyLearnersTransfer companyLearnersTransfer = new CompanyLearnersTransfer();
//	private Doc doc = null;
//
//    /** the Service Levels */
//	private CompanyLearnersTransferService service = new CompanyLearnersTransferService();
//	private RejectReasonsService rejectReasonsService = new RejectReasonsService();
//	
//	/** Array Lists */
//	private List<RejectReasons> taskRejectReasonsList = new ArrayList<>();
//	private List<RejectReasons> selectedRejectReasonsList = new ArrayList<>(); 
//	
//    @PostConstruct
//	public void init() {
//		try {
//			runInit();
//		} catch (ValidationException e) {
//			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
//		} catch (Exception e) {
//			addErrorMessage(e.getMessage(), e);
//		}
//	}
//
//    // run in it
//	private void runInit() throws Exception {
//		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LearnerTransfer) {
//			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
//			if (getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Completed) {
//				getSessionUI().setTask(null);
//				ajaxRedirectToDashboard();
//			}
//			// locate company learners trade test
//			this.companyLearnersTransfer = service.findByKey(getSessionUI().getTask().getTargetKey());
//			// populate documents against learner transfer
//			service.prepareNewRegistrationTradeTest(getSessionUI().getTask().getWorkflowProcess(), companyLearnersTransfer, getSessionUI().getTask().getProcessRole());
//			// populate rejection reasons
//			populateRejectionReasons(getSessionUI().getTask().getWorkflowProcess());
//		} else {
//			getSessionUI().setTask(null);
//			ajaxRedirectToDashboard();
//		}
//	}
//	
//	// populates rejection reasons on task
//	private void populateRejectionReasons(ConfigDocProcessEnum configDocEnum) throws Exception{
//		taskRejectReasonsList = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(companyLearnersTransfer.getId(), companyLearnersTransfer.getClass().getName(), configDocEnum);
//	}
//
//	// populates array list of rejection reasons for selection
//	public List<RejectReasons> getRejectReasons() {
//		List<RejectReasons> l = null;
//		try {
//			l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());
//		} catch (Exception e) {
//			addErrorMessage(e.getMessage(), e);
//		}
//		return l;
//	}
//	
//	// store document
//	public void storeFile(FileUploadEvent event) {
//		try {
//			doc.setData(event.getFile().getContents());
//			doc.setOriginalFname(event.getFile().getFileName());
//			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
//			doc.setUsers(getSessionUI().getActiveUser());
//			doc.setTargetClass(companyLearnersTransfer.getClass().getName());
//			doc.setTargetKey(companyLearnersTransfer.getId());
//			if (doc.getId() != null) {
//				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
//			} else {
//				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
//			}
//			super.runClientSideExecute("PF('dlgUpload').hide()");
//		} catch (Exception e) {
//			addErrorMessage(e.getMessage(), e);
//		}
//	}
//	
//	/* Getters and setters */
//	public CompanyLearnersTransfer getCompanyLearnersTransfer() {
//		return companyLearnersTransfer;
//	}
//
//	public void setCompanyLearnersTransfer(CompanyLearnersTransfer companyLearnersTransfer) {
//		this.companyLearnersTransfer = companyLearnersTransfer;
//	}
//
//	public Doc getDoc() {
//		return doc;
//	}
//
//	public void setDoc(Doc doc) {
//		this.doc = doc;
//	}
//
//	public List<RejectReasons> getTaskRejectReasonsList() {
//		return taskRejectReasonsList;
//	}
//
//	public void setTaskRejectReasonsList(List<RejectReasons> taskRejectReasonsList) {
//		this.taskRejectReasonsList = taskRejectReasonsList;
//	}
//
//	public List<RejectReasons> getSelectedRejectReasonsList() {
//		return selectedRejectReasonsList;
//	}
//
//	public void setSelectedRejectReasonsList(List<RejectReasons> selectedRejectReasonsList) {
//		this.selectedRejectReasonsList = selectedRejectReasonsList;
//	}
	
}