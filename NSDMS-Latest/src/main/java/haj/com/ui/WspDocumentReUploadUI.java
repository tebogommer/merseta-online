package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.entity.Company;
import haj.com.entity.DgVerification;
import haj.com.entity.Doc;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.service.lookup.RejectReasonsService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "wspDocumentReUploadUI")
@ViewScoped
public class WspDocumentReUploadUI extends AbstractUI {

	/** Entity objects */
	private Wsp wsp = null;
	private Company company = null;
	private Doc doc;
	
	/** Service objects */
	private WspService wspService;
	private CompanyService companyService;
	
	/** Array lists of objects */
	private List<RejectReasons> selectedRejectReason;
	
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
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.WSPDocumentUpload && super.getParameter("id", false) != null) {
			populateServiceObjects();
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			locateWspAndCompany();
		} else {
			super.ajaxRedirectToDashboard();
		}
	}

	private void locateWspAndCompany() throws Exception {
		wsp = wspService.findByKey(getSessionUI().getTask().getTargetKey(), getSessionUI().getActiveUser());
		company = companyService.findByKey(wsp.getCompany().getId());
	}
	
	public List<RejectReasons> getEmployeeRejectionReasons(){
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
			try {
				l= rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(),ConfigDocProcessEnum.WSPDocumentUpload);
			} catch (Exception e) {
				addErrorMessage(e.getMessage(),e);
			}
		return l;
	}

	/**
	 * Populates the service objects/layers for use.
	 * @throws Exception
	 */
	private void populateServiceObjects() throws Exception{
		if (wspService == null) {
			wspService = new WspService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
	}
	
	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setWsp(wsp);
			if (doc.getId() == null) {
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			} else {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			locateWspAndCompany();
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.WSPDocumentUpload);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Submission of the SDF
	 */
	public void sdfSubmission() {
		try {
			wspService.submitDocumentUploadTask(wsp, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	
	/** Getters and Setters */
	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public WspService getWspService() {
		return wspService;
	}

	public void setWspService(WspService wspService) {
		this.wspService = wspService;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
