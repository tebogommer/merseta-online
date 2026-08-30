package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.Doc;
import haj.com.entity.SitesSme;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalMentors;
import haj.com.entity.WorkPlaceApprovalSites;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.SitesService;
import haj.com.service.SitesSmeService;
import haj.com.service.TasksService;
import haj.com.service.WorkPlaceApprovalMentorsService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.lookup.RejectReasonsService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "workPlaceApprovalMentorsUI")
@ViewScoped
public class WorkPlaceApprovalMentorsUI extends AbstractUI {
	/** Service Level */
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();
	private WorkPlaceApprovalService service = new WorkPlaceApprovalService();
	private WorkPlaceApprovalMentorsService workPlaceApprovalMentorsService = new WorkPlaceApprovalMentorsService();
	private SitesService sitesService = new SitesService();
	private SitesSmeService sitesSmeService = new SitesSmeService();
	
	/** Entity Level */
	private WorkPlaceApproval workplaceapproval = null;
	private WorkPlaceApprovalMentors workPlaceApprovalMentors;
	private SitesSme sitesSme = null;
	private WorkPlaceApprovalSites site;
	
	private LazyDataModel<SitesSme> avalibleSmeDataModel;
	
	private List<WorkPlaceApprovalSites> sites;
	private List<WorkPlaceApprovalSites> workPlaceApprovalSites;
	private List<RejectReasons> rejectReasonsList = null;
	private List<RejectReasons> selectedRejectReasonsList = null;
	private List<RejectReasons> selectedRejectionReasons = new ArrayList<RejectReasons>();
	private Doc doc;
	private boolean companyHasSites = false;
	private boolean disableAll = true;
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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.WORKPLACE_APPROVAL_MENTORS) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			workPlaceApprovalMentors = workPlaceApprovalMentorsService.findByKey(getSessionUI().getTask().getTargetKey());
			workplaceapproval = service.findByKey(workPlaceApprovalMentors.getWorkPlaceApproval().getId());
			populateAvalibleSmeSiteSelection();
			populateRejectReasonsAssigned();
			checkIfSitesAssigned();
			populateOldSites();
		}
	}


	/**
	 * Populates boolean if a company has sites assigned to them
	 * 
	 * @throws Exception
	 */
	private void checkIfSitesAssigned() throws Exception {
		if (workplaceapproval != null) {
			if (workplaceapproval.getCompany() != null
					&& sitesService.findByCompany(workplaceapproval.getCompany()).size() > 0) {
				companyHasSites = true;
			} else {
				companyHasSites = false;
			}
		}
	}
	/**
	 * Populates available SME for selection against work place approval
	 */
	private void populateAvalibleSmeSiteSelection() throws Exception {		
		workPlaceApprovalSites=service.findSitesByWorkPlaceApprovalMentors(workPlaceApprovalMentors, null, null);
		avalibleSmeDataModel = new LazyDataModel<SitesSme>() {
			private static final long serialVersionUID = 1L;
			private List<SitesSme> retorno = new ArrayList<SitesSme>();

			@Override
			public List<SitesSme> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = sitesSmeService.avalaibleSiteSmeForSelection(first, pageSize, sortField, sortOrder,
							filters, workplaceapproval, null, null);
					avalibleSmeDataModel
							.setRowCount(sitesSmeService.countAvalaibleSiteSmeForSelection(filters, workplaceapproval));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SitesSme obj) {
				return obj.getId();
			}

			@Override
			public SitesSme getRowData(String rowKey) {
				for (SitesSme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	private void populateOldSites() throws Exception{
		sites = service.findSitesByApproval(workplaceapproval,ApprovalEnum.Approved,null, null);
	}
	/**
	 * Populates rejected reasons assigned to workplace approval
	 * 
	 * @throws Exception
	 */
	private void populateRejectReasonsAssigned() throws Exception {
		if (workplaceapproval != null && workplaceapproval.getId() != null) {
			rejectReasonsList = rejectReasonsService.locateReasonsSelectedByTargetKeyAndClass(workplaceapproval.getId(), workplaceapproval.getClass().getName());
		} else {
			rejectReasonsList = null;
		}
	}
	
	private void caluclatePermissions() {
		if(getSessionUI().getTask().getFirstInProcess()) {
			disableAll = false;
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	
	public void addSiteSmeToWorkplaceApproval() {
		try {
			service.createWorkPlaceApprovalSitesWithSme(workplaceapproval, sitesSme,workPlaceApprovalMentors);
			sitesSme = null;
			populateAvalibleSmeSiteSelection();
			addWarningMessage("Mentor linked to application");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void deleteWorkPlaceApprovalSitesLinkedToWorkplaceApproval() {
		try {
			service.delete(site);
			populateAvalibleSmeSiteSelection();
			addWarningMessage("Entry Removed");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveRegistration() {
		try {
			workPlaceApprovalMentorsService.finalApproveRegistration(workPlaceApprovalMentors, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void cloRejectToSdf() {
		try {
			if (selectedRejectionReasons.size() == 0) {
				throw new ValidationException("Provide At Least One Rejection Reason Before Proceeding");
			}
			workPlaceApprovalMentorsService.rejectRegistration(workPlaceApprovalMentors, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeRegistration() {
		try {	
			if(workPlaceApprovalSites == null) {
				throw new Exception("Provide At Least One Mentor");
			}
			if(workPlaceApprovalSites.size() == 0) {
				throw new Exception("Provide At Least One Mentor");
			}
			workPlaceApprovalMentorsService.completeRegistration(workPlaceApprovalMentors, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectRegistration() {
		try {
			workPlaceApprovalMentorsService.rejectRegistration(workPlaceApprovalMentors, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectionReasons);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectRegistration() {
		try {
			if (selectedRejectionReasons.size() == 0) {
				throw new ValidationException("Provide At Least One Rejection Reason Before Proceeding");
			}
			workPlaceApprovalMentorsService.finalRejectRegistration(workPlaceApprovalMentors, getSessionUI().getActiveUser(),
					getSessionUI().getTask(), selectedRejectionReasons);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public WorkPlaceApproval getWorkplaceapproval() {
		return workplaceapproval;
	}

	public WorkPlaceApprovalMentors getWorkPlaceApprovalMentors() {
		return workPlaceApprovalMentors;
	}

	public LazyDataModel<SitesSme> getAvalibleSmeDataModel() {
		return avalibleSmeDataModel;
	}

	public List<WorkPlaceApprovalSites> getSites() {
		return sites;
	}

	public List<RejectReasons> getRejectReasonsList() {
		return rejectReasonsList;
	}

	public void setWorkplaceapproval(WorkPlaceApproval workplaceapproval) {
		this.workplaceapproval = workplaceapproval;
	}

	public void setWorkPlaceApprovalMentors(WorkPlaceApprovalMentors workPlaceApprovalMentors) {
		this.workPlaceApprovalMentors = workPlaceApprovalMentors;
	}

	public void setAvalibleSmeDataModel(LazyDataModel<SitesSme> avalibleSmeDataModel) {
		this.avalibleSmeDataModel = avalibleSmeDataModel;
	}

	public void setSites(List<WorkPlaceApprovalSites> sites) {
		this.sites = sites;
	}

	public void setRejectReasonsList(List<RejectReasons> rejectReasonsList) {
		this.rejectReasonsList = rejectReasonsList;
	}

	public SitesSme getSitesSme() {
		return sitesSme;
	}

	public void setSitesSme(SitesSme sitesSme) {
		this.sitesSme = sitesSme;
	}

	public boolean isDisableAll() {
		return disableAll;
	}

	public void setDisableAll(boolean disableAll) {
		this.disableAll = disableAll;
	}

	public WorkPlaceApprovalSites getSite() {
		return site;
	}

	public void setSite(WorkPlaceApprovalSites site) {
		this.site = site;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<RejectReasons> getSelectedRejectReasonsList() {
		return selectedRejectReasonsList;
	}

	public List<RejectReasons> getSelectedRejectionReasons() {
		return selectedRejectionReasons;
	}

	public void setSelectedRejectReasonsList(List<RejectReasons> selectedRejectReasonsList) {
		this.selectedRejectReasonsList = selectedRejectReasonsList;
	}

	public void setSelectedRejectionReasons(List<RejectReasons> selectedRejectionReasons) {
		this.selectedRejectionReasons = selectedRejectionReasons;
	}

	public List<WorkPlaceApprovalSites> getWorkPlaceApprovalSites() {
		return workPlaceApprovalSites;
	}

	public void setWorkPlaceApprovalSites(List<WorkPlaceApprovalSites> workPlaceApprovalSites) {
		this.workPlaceApprovalSites = workPlaceApprovalSites;
	}

	public boolean isCompanyHasSites() {
		return companyHasSites;
	}

	public void setCompanyHasSites(boolean companyHasSites) {
		this.companyHasSites = companyHasSites;
	}
}
