package haj.com.ui;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.entity.Doc;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.TasksService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "pipupdateUI")
@ViewScoped
public class PipUpdateUI extends AbstractUI {

	private ProjectImplementationPlanService service = new ProjectImplementationPlanService();
	private ProjectImplementationPlan projectImplementationPlan;
	private Doc doc;

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

	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.PIP_UPDATE) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.projectImplementationPlan = service.findByKey(getSessionUI().getTask().getTargetKey());
			service.prepareNewRegistration(projectImplementationPlan);
		}
		if(doc == null){
			doc = new Doc();
		}
	}

	public void requestWorkflow() {
		try {
			service.requestWorkflow(projectImplementationPlan, getSessionUI().getActiveUser());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeWorkflow() {
		try {
			service.completeWorkflow(projectImplementationPlan, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			service.approveWorkflow(projectImplementationPlan, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectWorkflow() {
		try {
			service.rejectWorkflow(projectImplementationPlan, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(projectImplementationPlan, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflow() {
		try {
			service.finalRejectWorkflow(projectImplementationPlan, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public ProjectImplementationPlan getProjectImplementationPlan() {
		return projectImplementationPlan;
	}

	public void setProjectImplementationPlan(ProjectImplementationPlan projectImplementationPlan) {
		this.projectImplementationPlan = projectImplementationPlan;
	}
	
	public void storeNewFilePIP(FileUploadEvent event) {
		try {
			if(projectImplementationPlan.getDocs() != null && !projectImplementationPlan.getDocs().isEmpty()){
				doc = projectImplementationPlan.getDocs().get(0);
			}else{
				doc = new Doc();
				doc = projectImplementationPlan.getDocs().get(0);
			}
			doc.setNote(this.getDoc().getNote());
			doc.setCreateDate(new Date(System.currentTimeMillis()));
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setTargetClass(projectImplementationPlan.getClass().getName());
			doc.setTargetKey(projectImplementationPlan.getId());
			if (doc.getId() == null) {
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			} else {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			projectImplementationPlan.getDocs().add(doc);
			service.update(projectImplementationPlan);
			super.runClientSideExecute("PF('dlgPIPUpdate').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

}
