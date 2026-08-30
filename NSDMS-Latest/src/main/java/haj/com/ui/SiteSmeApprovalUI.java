package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;

import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.SmeQualifications;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DocService;
import haj.com.service.SitesService;
import haj.com.service.SitesSmeService;
import haj.com.service.SmeQualificationsService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "siteSmeApprovalUI")
@ViewScoped
public class SiteSmeApprovalUI extends AbstractUI {

	/** Service Level */
	private SitesSmeService service = new SitesSmeService();
	private SitesService sitesService = new SitesService();
	private SmeQualificationsService smeQualificationsService = new SmeQualificationsService();
	private CompanyService companyService = new CompanyService();
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();

	/** Entity Level */
	private SitesSme sitessme = null;
	private SmeQualifications smeQualifications = null;
	private Company company = null;
	private Doc doc;

	/** Action Booleans */
	private boolean companyHasSites = false;
	private boolean editFirstLastName = false;
	private boolean disableAll = false;
	private boolean upload = false;

	/** Array Lists */
	private List<RejectReasons> rejectReasonsList = null;
	private List<RejectReasons> selectedRejectionReasons = new ArrayList<>();

	/**
	 * Inits the.
	 */
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
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SITE_SME_REGISTRATION) {
			locateSiteSmeAndQualifications();
			checkIfSitesAssigned();
			checkPermissions();
			prepNewSmeQualification();
		}else if(getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SITE_SME_UPDATE) {
			locateSiteSmeAndQualifications();
			checkIfSitesAssigned();
			checkPermissions();
			prepNewSmeQualification();
		}else if(getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SITE_SME_DELETE) {
			locateSiteSmeAndQualifications();
			checkIfSitesAssigned();
			checkPermissions();
			prepNewSmeQualification();
		}else {
			super.ajaxRedirectToDashboard();
		}
	}

	/**
	 * Populates site sme by task id
	 * 
	 * @throws Exception
	 */
	private void locateSiteSmeAndQualifications() throws Exception {
		sitessme = service.findByKeyPopulateInformation(getSessionUI().getTask().getTargetKey(), ConfigDocProcessEnum.SITE_SME_REGISTRATION, CompanyUserTypeEnum.User, null);
		if (company == null) {
			company = companyService.findByKey(sitessme.getCompany().getId());
		}
		if (!sitessme.getExistingUser()) {
			editFirstLastName = true;
		}
		sitessme.setSmeQualificationsList(smeQualificationsService.findBySme(sitessme));
		// locates lastest rejection reasons
		rejectReasonsList = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(sitessme.getId(), sitessme.getClass().getName(), ConfigDocProcessEnum.SITE_SME_REGISTRATION);
	}

	/**
	 * Sets permission based on process role
	 * 
	 * @throws Exception
	 */
	private void checkPermissions() throws Exception {
		if (getSessionUI().getTask().getProcessRole() != null) {
			upload = true;
			disableAll = true;
			switch (getSessionUI().getTask().getProcessRole().getRolePermission()) {
			case Edit:
				disableAll = false;
				break;
			case EditUpload:
				upload = false;
				disableAll = false;
				break;
			case FinalEdit:
				disableAll = false;
				break;
			case Upload:
				upload = false;
				break;
			case FinalUpload:
				upload = false;
				break;
			case FinalUploadApproval:
				upload = false;
				break;
			default:
				upload = true;
				disableAll = true;
				break;
			}
		} else {
			// sdf can upload and edit
			upload = false;
			disableAll = false;
		}
	}

	/**
	 * Populates boolean if a company has sites assigned to them
	 * 
	 * @throws Exception
	 */
	private void checkIfSitesAssigned() throws Exception {
		if (sitessme != null && sitessme.getId() != null) {
			if (sitessme.getCompany() != null && sitesService.findByCompany(sitessme.getCompany()).size() > 0) {
				companyHasSites = true;
			} else {
				companyHasSites = false;
			}
		} else if (company != null) {
			if (sitesService.findByCompany(company).size() > 0) {
				companyHasSites = true;
			} else {
				companyHasSites = false;
			}
		} else {
			companyHasSites = false;
		}
	}

	/**
	 * Populates company sites
	 * 
	 * @throws Exception
	 */
	private void populateCompanySites() throws Exception {
		if (companyHasSites) {
			if (company != null) {
				List<Sites> allSitesAssigned = sitesService.findByCompany(company);
				if (allSitesAssigned.size() == 1) {
					sitessme.setSites(allSitesAssigned.get(0));
				}
				allSitesAssigned = null;
			}
		} else {
			if (company != null) {
				sitessme.setResidentialAddress(company.getResidentialAddress());
			}
		}
	}

	/**
	 * Preps new instance of Sme Qualification
	 * 
	 * @throws Exception
	 */
	public void prepNewSmeQualification() throws Exception {
		smeQualifications = smeQualificationsService.prepNewSmeQualifications(sitessme, null);
	}

	/**
	 * Add new Sme Qualification
	 * 
	 * @throws Exception
	 */
	public void addNewSmeQualification() throws Exception {
		try {
			String error = validationOnSmeQualification();
			if (error.equals("")) {
				smeQualificationsService.createUpdateSmeQualifications(smeQualifications,
						getSessionUI().getActiveUser());
				locateSiteSmeAndQualifications();
				prepNewSmeQualification();
				addInfoMessage("Qualification Succusfully Added");
			} else {
				addWarningMessage("Provide the following before proceeding: " + error);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public String validationOnSmeQualification() {
		String error = "";
		if (smeQualifications.getQualification() == null) {
			error += "Qualification";
		} else {
			if (sitessme.getId() == null) {
				for (SmeQualifications smeQualification : sitessme.getSmeQualificationsList()) {
					if (smeQualification.getQualification().getId()
							.equals(smeQualifications.getQualification().getId())) {
						if (error.equals("")) {
							error += "Qualification Has Already Been Added To SME.";
						} else {
							error += ", Qualification Has Already Been Added To SME";
						}
					}
				}
			}
		}
		if (smeQualifications.getSitesSme() == null) {
			if (error.equals("")) {
				error += "SME Link";
			} else {
				error += ", SME Link";
			}
		}
		for (Doc doc : smeQualifications.getDocs()) {
			if (doc.getId() == null && doc.getData() == null) {
				if (error.equals("")) {
					error += "Document: Required Document";
				} else {
					error += ", Document: SME Qualification Evidence";
				}
			}
		}
		return error;
	}

	/**
	 * Deletes SME entry
	 */
	public void deleteSmeQualificationEntry() {
		try {
			smeQualificationsService.delete(smeQualifications);
			locateSiteSmeAndQualifications();
			prepNewSmeQualification();
			addWarningMessage("Entry Deleted");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Store new file
	 * 
	 * @param event
	 */
	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			runClientSideExecute("PF('dlgUploadUser').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Download.
	 */
	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Populates the rejection reasons for selection
	 * 
	 * @return
	 */
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.SITE_SME_REGISTRATION);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/** Work flow methods */
	public void clientSubmission() {
		try {
			service.clientSubmission(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(), !disableAll, !upload);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeWorkflow() {
		try {
			service.completeWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(), !disableAll, !upload);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			service.approveWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(), !disableAll, !upload);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectWorkflow() {
		try {
			if (selectedRejectionReasons.size() == 0) {
				addWarningMessage("Select Atleast One Reason Of Rejection Before Procceding");
			} else {
				service.rejectWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(),
						selectedRejectionReasons, !disableAll, !upload);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			//service.finalApproveWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(), !disableAll, !upload);
			service.finalApproveWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);			
			ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveUpdateWorkflow() {
		try {
			//service.finalApproveUpdateWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(), !disableAll, !upload);
			service.finalApproveUpdateWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();			
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflow() {
		try {
			if (selectedRejectionReasons.size() == 0) {
				addWarningMessage("Select Atleast One Reason Of Rejection Before Procceding");
			} else {
				service.finalRejectWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons, !disableAll, !upload);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalRejectUpdateWorkflow() {
		try {
			if (selectedRejectionReasons.size() == 0) {
				addWarningMessage("Select Atleast One Reason Of Rejection Before Procceding");
			} else {
				service.finalRejectUpdateWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons, !disableAll, !upload);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveDeleteWorkflow() {
		try {
			service.finalApproveDeleteWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(), !disableAll, !upload);
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();

			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			super.runClientSideExecute("uploadDone()");
		}
	}

	public void finalRejectDeleteWorkflow() {
		try {
			if (selectedRejectionReasons.size() == 0) {
				addWarningMessage("Select Atleast One Reason Of Rejection Before Procceding");
			} else {
				service.finalRejectDeleteWorkflow(sitessme, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons, !disableAll, !upload);
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	public SmeQualificationsService getSmeQualificationsService() {
		return smeQualificationsService;
	}

	public void setSmeQualificationsService(SmeQualificationsService smeQualificationsService) {
		this.smeQualificationsService = smeQualificationsService;
	}

	public SitesSme getSitessme() {
		return sitessme;
	}

	public void setSitessme(SitesSme sitessme) {
		this.sitessme = sitessme;
	}

	public SmeQualifications getSmeQualifications() {
		return smeQualifications;
	}

	public void setSmeQualifications(SmeQualifications smeQualifications) {
		this.smeQualifications = smeQualifications;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public boolean isCompanyHasSites() {
		return companyHasSites;
	}

	public void setCompanyHasSites(boolean companyHasSites) {
		this.companyHasSites = companyHasSites;
	}

	public boolean isEditFirstLastName() {
		return editFirstLastName;
	}

	public void setEditFirstLastName(boolean editFirstLastName) {
		this.editFirstLastName = editFirstLastName;
	}

	public boolean isDisableAll() {
		return disableAll;
	}

	public void setDisableAll(boolean disableAll) {
		this.disableAll = disableAll;
	}

	public boolean isUpload() {
		return upload;
	}

	public void setUpload(boolean upload) {
		this.upload = upload;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<RejectReasons> getRejectReasonsList() {
		return rejectReasonsList;
	}

	public void setRejectReasonsList(List<RejectReasons> rejectReasonsList) {
		this.rejectReasonsList = rejectReasonsList;
	}

	public List<RejectReasons> getSelectedRejectionReasons() {
		return selectedRejectionReasons;
	}

	public void setSelectedRejectionReasons(List<RejectReasons> selectedRejectionReasons) {
		this.selectedRejectionReasons = selectedRejectionReasons;
	}

}
