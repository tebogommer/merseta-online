package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.QdfCompany;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.Signoff;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.SmeQualifications;
import haj.com.entity.TradeAppraisalsChecklist;
import haj.com.entity.Users;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalSites;
import haj.com.entity.WorkPlaceApprovalSkillsProgramme;
import haj.com.entity.WorkPlaceApprovalToolList;
import haj.com.entity.WorkPlaceApprovalTrades;
import haj.com.entity.WorkPlaceApprovalVisitDateLog;
import haj.com.entity.WorkplaceApprovalSkillsSet;
import haj.com.entity.WorkplaceApprovalUnitStandart;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CloRecommendationEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.WpaDocRequirements;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.ToolListTools;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.ScheduledEventService;
import haj.com.service.SitesService;
import haj.com.service.SitesSmeService;
import haj.com.service.TasksService;
import haj.com.service.TradeAppraisalsChecklistService;
import haj.com.service.UsersService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.WorkPlaceApprovalSkillsProgrammeService;
import haj.com.service.WorkPlaceApprovalToolListService;
import haj.com.service.WorkplaceApprovalSkillsSetService;
import haj.com.service.WorkplaceApprovalUnitStandartService;
import haj.com.service.lookup.DateChangeReasonsService;
import haj.com.service.lookup.LegacyEmployerWA2WorkplaceService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.ToolListService;
import haj.com.utils.GenericUtility;
import haj.com.validators.CheckID;

@ManagedBean(name = "workplaceapprovallegacyUI")
@ViewScoped
public class WorkPlaceApprovalLegacyUI extends AbstractUI {
	private LegacyEmployerWA2WorkplaceService serviceLegacyEmployerWA2Workplace = new LegacyEmployerWA2WorkplaceService();
	/** Entity Level */
	private WorkPlaceApproval workplaceapproval = null;
	private WorkPlaceApprovalSites site;
	private WorkPlaceApprovalSites siteListEntry = null;
	private WorkPlaceApprovalTrades trade;
	private Doc doc;
	private SitesSme sitesSme = null;
	ScheduledEvent scheduledEvent= null;

	/** Service Level */
	private WorkPlaceApprovalService service = new WorkPlaceApprovalService();
	private SitesService sitesService = new SitesService();
	private SitesSmeService sitesSmeService = new SitesSmeService();
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();
	private TradeAppraisalsChecklistService tradeAppraisalsChecklistService = new TradeAppraisalsChecklistService();
	private ScheduledEventService  scheduledEventService = new ScheduledEventService();

	private WorkPlaceApprovalSkillsProgrammeService workPlaceApprovalSkillsProgrammeService = new WorkPlaceApprovalSkillsProgrammeService();
	private WorkplaceApprovalSkillsSetService workplaceApprovalSkillsSetService = new WorkplaceApprovalSkillsSetService();
	private WorkplaceApprovalUnitStandartService workplaceApprovalUnitStandartService = new WorkplaceApprovalUnitStandartService();
	private WorkPlaceApprovalToolListService workPlaceApprovalToolListService = new WorkPlaceApprovalToolListService();
	
	/** Array Lists */
	private List<WorkPlaceApproval> workplaceapprovalList = null;
	private List<WorkPlaceApproval> workplaceapprovalfilteredList = null;
	private List<WorkPlaceApprovalSites> sites;
	private List<WorkPlaceApprovalTrades> trades;
	private List<RejectReasons> rejectReasonsList = null;
	private List<RejectReasons> selectedRejectReasonsList = null;
	private List<DateChangeReasons> dateChangeReasonSelectionList = null;
	private List<DateChangeReasons> dateChangeReasonAvalibleSelectionList = null;
	private List<WorkPlaceApprovalVisitDateLog> visitDateLogList = null;
	private List<RejectReasons> selectedRejectionReasons = new ArrayList<RejectReasons>();
	private List<TradeAppraisalsChecklist> tradeAppraisalsChecklist = null;
	private List<WorkPlaceApprovalToolList>workPlaceApprovalToolList = null;
	
	private List<WorkplaceApprovalSkillsSet>workplaceApprovalSkillsSetList = null;
	private List<WorkPlaceApprovalSkillsProgramme>workplaceApprovalSkillsPrograList = null;
	private List<WorkplaceApprovalUnitStandart>workPlaceApprovalUnitStandartList = null;
	
	/** Lazy Load Lists */
	private LazyDataModel<WorkPlaceApproval> dataModel;
	private LazyDataModel<SitesSme> avalibleSmeDataModel;

	/** Boolean Values */
	private boolean companyHasSites = false;
	private boolean editFirstLastName = false;
	private boolean disableAll = true;
	private boolean disableForOfficeUse = false;
	private boolean tradeApproval = false;
	private boolean uploadAppeal = false;
	private boolean upload = false;
	private boolean uploadSmeDocs = false; // can delete
	private boolean signOff = false;
	private boolean cloAction = false;
	private boolean allowReview = false;
	private boolean canRecommendApproval = false;
	private boolean uploadProofAgreement = false;
	private boolean uploadScopeEvidencet = false;

	/** Date Values */
	private Date today = GenericUtility.getStartOfDay(new Date());

	/**
	 * The search user passport or id UI. The sme user
	 */
	private Users smeUser;
	private UsersService usersService = null;
	private IdPassportEnum idpassport;
	@CheckID(message = "Not a valid RSA ID number")
	private String idnumber;
	public Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;
	public Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;
	public String passportNumberFormat = HAJConstants.passportNumberFormat;
	private String passportNumber;

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
	 * Initialize method to get all WorkPlaceApproval and prepare a for a create
	 * of a new WorkPlaceApproval
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApproval
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		signoff = new Signoff();
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LEGACY_WORKPLACE_APPROVAL) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			workplaceapproval = service.findByKey(getSessionUI().getTask().getTargetKey());
			//service.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(), workplaceapproval, getSessionUI().getTask().getProcessRole(), false);
			caluclatePermissions();
			//tradeApproval = workplaceapproval.getOfoCodes() != null;
			//tradeApproval = workplaceapproval.getQualification().getQualificationtypeid().equalsIgnoreCase(HAJConstants.TRADE_QUALIFICATION_CODE);
			if(workplaceapproval.getQualification()!=null && workplaceapproval.getQualification().getQualificationtypeid() !=null && workplaceapproval.getQualification().getQualificationtypeid().equalsIgnoreCase(HAJConstants.TRADE_QUALIFICATION_CODE)) {
				workPlaceApprovalToolList = workPlaceApprovalToolListService.findByworkplaceapproval(workplaceapproval);
				tradeApproval = true;
			}				
			
			populateAvalibleSmeSiteSelection();
			populateRejectReasonsAssigned();
			populateTradeAppraisalsChecklist();
			populateScheduledEvent();
			populateLists();
			// prepNewSiteEntry();
		}else if (super.getParameter("legacyId", true) != null) {
			LegacyEmployerWA2Workplace legacyEmployerWA2Workplace = serviceLegacyEmployerWA2Workplace.findByKey((Long) super.getParameter("apprenticeshipId", true));	
			
		}else {
			prepareNew();
		}
		checkIfSitesAssigned();
	}

	private void populateLists() throws Exception {
		workplaceApprovalSkillsSetList = workplaceApprovalSkillsSetService.findByWorkplaceapproval(this.workplaceapproval);
		workplaceApprovalSkillsPrograList= workPlaceApprovalSkillsProgrammeService.findByWorkplaceapproval(this.workplaceapproval);
		workPlaceApprovalUnitStandartList = workplaceApprovalUnitStandartService.findByWorkplaceapproval(this.workplaceapproval);
	}

	private void populateScheduledEvent() {
		try {
			scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(workplaceapproval.getId(), WorkPlaceApproval.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateTradeAppraisalsChecklist() throws Exception {	
		tradeAppraisalsChecklist = tradeAppraisalsChecklistService.findByWorkPlaceApproval(workplaceapproval);		
	}
	
	public void removeDocument() throws Exception{	
		
	}

	/**
	 * Calculates what the permissions for the user
	 */
	private void caluclatePermissions() {
		if (getSessionUI().getTask().getProcessRole() == null) {
			if (workplaceapproval.getApprovalEnum() == ApprovalEnum.PendingApproval) {
				signOff = false;
				if (workplaceapproval.getWithClientCompany() != null && workplaceapproval.getWithClientCompany()) {
					// with the clients view
					disableAll = false;
					upload = true;
					disableForOfficeUse = true;
					allowReview = false;
					canRecommendApproval = false;
				} else if (workplaceapproval.getWithManager() != null && workplaceapproval.getWithManager()) {
					// with the clients view
					disableAll = true;
					upload = false;
					disableForOfficeUse = true;
					allowReview = false;
					canRecommendApproval = false;
				}else {
					// with the clo for review
					disableAll = true;
					upload = false;
					disableForOfficeUse = false;
					if (workplaceapproval.getReviewDate() != null && (GenericUtility.getStartOfDay(workplaceapproval.getReviewDate()).equals(GenericUtility.getStartOfDay(today)) || GenericUtility.getStartOfDay(workplaceapproval.getReviewDate()).before(GenericUtility.getStartOfDay(today)))) {
						allowReview = true;
					} else {
						allowReview = false;
					}
				}
				checklistValueCheck();
			} else if (workplaceapproval.getApprovalEnum() == ApprovalEnum.PendingSignOff) {
				signOff = true;
				cloAction = false;
				allowReview = false;
				disableForOfficeUse = true;
				if (getSessionUI().isExternalParty() && workplaceapproval.getCloRecommendation() == CloRecommendationEnum.Rejection) {
					if (workplaceapproval.getWpaAppealed() == null || !workplaceapproval.getWpaAppealed()) {
						uploadAppeal = true;
					}
				}
			} else {
				signOff = false;
				cloAction = false;
				allowReview = false;
				disableForOfficeUse = true;
			}
		} else {
			allowReview = false;
			cloAction = false;
			signOff = true;
			// disable all
			this.disableAll = getSessionUI().getTask().getProcessRole().getRolePermission() != UserPermissionEnum.Edit
					&& getSessionUI().getTask().getProcessRole().getRolePermission() != UserPermissionEnum.EditUpload;
			// if user can upload
			this.upload = getSessionUI().getTask().getProcessRole() == null
					|| getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload
					|| getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Upload
					|| getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload
					|| getSessionUI().getTask().getProcessRole()
							.getRolePermission() == UserPermissionEnum.FinalUploadApproval;
			this.uploadSmeDocs = this.upload;

			// disable for office use
			this.disableForOfficeUse = getSessionUI().getTask().getProcessRole()
					.getRolePermission() != UserPermissionEnum.View;
		}
	}
	private boolean canShowDocument= false;
	public void checkRequiredDocuments() throws Exception {
		if(workplaceapproval.getWorkplaceAbleToCoverTradeOrQualificationSdf()!=null && workplaceapproval.getWorkplaceAbleToCoverTradeOrQualificationSdf().getId() == HAJConstants.NO_ID) {
			if(workplaceapproval.getFormalAgreementWithOtherSdf() !=null && workplaceapproval.getFormalAgreementWithOtherSdf().getId() == HAJConstants.YES_ID) {
				canShowDocument = true;
			}else {
				canShowDocument= false;
			}
		}
		else {
			canShowDocument= false;
		}
		
		service.prepareNewDocs(workplaceapproval, ConfigDocProcessEnum.WORKPLACE_APPROVAL, CompanyUserTypeEnum.Company, canShowDocument);
		//service.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(), workplaceapproval, getSessionUI().getTask().getProcessRole(), canShowDocument);
		/*
		 * List<Doc> docs = workplaceapproval.getDocs(); if(canShowDocument) {
		 * System.out.println("Can Show doc"+ canShowDocument); for(Doc doc: docs) {
		 * if(doc.getConfigDoc().getName().matches("Proof Of Agreement Between Entities"
		 * )) {
		 * service.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(),
		 * workplaceapproval,
		 * getSessionUI().getTask().getProcessRole(),canShowDocument); } } }else {
		 * System.out.println("Can Show doc"+ canShowDocument); for(Doc doc: docs) {
		 * if(doc.getConfigDoc().getName().matches("Proof Of Agreement Between Entities"
		 * )) {
		 * service.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(),
		 * workplaceapproval, getSessionUI().getTask().getProcessRole(),
		 * canShowDocument); } } }
		 */
	}

	public void checklistValueCheck() {
		try {
			canRecommendApproval = true;
			
			if (workplaceapproval.getAllToolsAvailable() == null || workplaceapproval.getAllToolsAvailable().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			if (workplaceapproval.getPretectiveWear()== null || workplaceapproval.getPretectiveWear().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			if (workplaceapproval.getAccessToMaterial()== null || workplaceapproval.getAccessToMaterial().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			if (workplaceapproval.getWorkplaceAbleToCoverTradeOrQualification()== null || workplaceapproval.getWorkplaceAbleToCoverTradeOrQualification().getId() == HAJConstants.NO_ID) {
				//canRecommendApproval = false; Patrick
			}
			
			if (workplaceapproval.getStructuredImplementationPlan()== null || workplaceapproval.getStructuredImplementationPlan().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			//1-----workplaceAbleToCoverTradeOrQualification Patrick
			if (workplaceapproval.getFormalAgreementWithOther()== null || workplaceapproval.getFormalAgreementWithOther().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			//2-----formalAgreementWithOther Patrick
			if (workplaceapproval.getWorkplaceAbleToCoverTradeOrQualification()!= null && workplaceapproval.getWorkplaceAbleToCoverTradeOrQualification().getId() == HAJConstants.NO_ID && workplaceapproval.getFormalAgreementWithOther()!= null && workplaceapproval.getFormalAgreementWithOther().getId() == HAJConstants.YES_ID) {
				canRecommendApproval = true;
			}
			
			//3-----formalAgreementWithOther Patrick
			if (workplaceapproval.getWorkplaceAbleToCoverTradeOrQualification()!= null && workplaceapproval.getWorkplaceAbleToCoverTradeOrQualification().getId() == HAJConstants.YES_ID && workplaceapproval.getFormalAgreementWithOther()!= null && workplaceapproval.getFormalAgreementWithOther().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = true;
			}
			
			if (workplaceapproval.getCommittedOhsaOrMhsaCompliant()== null || workplaceapproval.getCommittedOhsaOrMhsaCompliant().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			if (workplaceapproval.getCommittedtoBecompliantRelevantLegislationApplicable()== null || workplaceapproval.getCommittedtoBecompliantRelevantLegislationApplicable().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			if (workplaceapproval.getLayoutEnvironmentSafe()== null || workplaceapproval.getLayoutEnvironmentSafe().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			if (workplaceapproval.getIdentifiedCommittedStaff()== null || workplaceapproval.getIdentifiedCommittedStaff().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			if (workplaceapproval.getRecordKeepingSystem()== null || workplaceapproval.getRecordKeepingSystem().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			if (workplaceapproval.getQualifiedMentorsForTrade()== null || workplaceapproval.getQualifiedMentorsForTrade().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			if (workplaceapproval.getMentorArtisanRationAcceptable()== null || workplaceapproval.getMentorArtisanRationAcceptable().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			
			/*if (workplaceapproval.getIdentifiedCommittedStaff() == null || workplaceapproval.getIdentifiedCommittedStaff().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			if (workplaceapproval.getDeclarationFromEmployer() == null || workplaceapproval.getDeclarationFromEmployer().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			if (workplaceapproval.getStructuredImplementationPlan() == null || workplaceapproval.getStructuredImplementationPlan().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			if (workplaceapproval.getSuitableQualifiedMentors() == null || workplaceapproval.getSuitableQualifiedMentors().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}

			if (workplaceapproval.getDeclarationIndicationCommitment() == null || workplaceapproval.getDeclarationIndicationCommitment().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}
			if (workplaceapproval.getCopyOfSelfEvaluation() == null || workplaceapproval.getCopyOfSelfEvaluation().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}*/
			/*if (workplaceapproval.getWorkplaceAbleToCoverScope() == null || workplaceapproval.getWorkplaceAbleToCoverScope().getId() == HAJConstants.NO_ID) {
				// user needs to upload supporting document
				canRecommendApproval = false;
				if (workplaceapproval.getWorkplaceAbleToCoverScope() != null && workplaceapproval.getWorkplaceAbleToCoverScope().getId() == HAJConstants.NO_ID) {
					uploadProofAgreement = true;
				}
			}*/
			/*if (workplaceapproval.getFormalAgreementAprrovedWorkpalces() == null || workplaceapproval.getFormalAgreementAprrovedWorkpalces().getId() == HAJConstants.NO_ID) {
				// user needs to upload supporting document
				canRecommendApproval = false;
				if (workplaceapproval.getFormalAgreementAprrovedWorkpalces() != null && workplaceapproval.getFormalAgreementAprrovedWorkpalces().getId() == HAJConstants.NO_ID) {
					uploadScopeEvidencet = true;
				}
			}*/
			/*if (workplaceapproval.getMentorArtisanRationAcceptable() == null || workplaceapproval.getMentorArtisanRationAcceptable().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}*/
			/*if (workplaceapproval.getEvidenceAttachedOfPrevios() == null || workplaceapproval.getEvidenceAttachedOfPrevios().getId() == HAJConstants.NO_ID) {
				canRecommendApproval = false;
			}*/
			// commented out answers by requirement
//			if (workplaceapproval.getTaxClearanceCertificate() == null || workplaceapproval.getTaxClearanceCertificate().getId() == HAJConstants.NO_ID) {
//			canRecommendApproval = false;
//			}
//			if (workplaceapproval.getLetterOfCommitment() == null || workplaceapproval.getLetterOfCommitment().getId() == HAJConstants.NO_ID) {
//				canRecommendApproval = false;
//			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
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
	
	public void setSiteSmeToWorkplaceApproval(SitesSme sitesSme) {
		try {
			this.sitesSme = sitesSme;
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addSiteSmeToWorkplaceApproval() {
		try {
			//List<SmeQualifications>list = sitesSme.getSmeQualificationsList();
			
			service.createWorkPlaceApprovalSitesWithSme(workplaceapproval, sitesSme);
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

	/**
	 * Populates available SME for selection against work place approval
	 */
	private void populateAvalibleSmeSiteSelection() throws Exception {
		sites = service.findSitesByApproval(workplaceapproval, null, null);
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



	/**
	 * Insert WorkPlaceApproval into database
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApproval
	 */
	public void workplaceapprovalInsert() {
		try {
			service.create(this.workplaceapproval);
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
	 * Update WorkPlaceApproval in database
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApproval
	 */
	public void workplaceapprovalUpdate() {
		try {
			service.update(this.workplaceapproval);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void workplaceapprovalDelete() {
		try {
			service.delete(this.workplaceapproval);
			prepareNew();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareNew() {
		workplaceapproval = new WorkPlaceApproval();
	}

	public List<WorkPlaceApproval> complete(String desc) {
		List<WorkPlaceApproval> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Client submits to the Clo for review
	 */
	public void clientSubmitToClo() {
		try {
			String errors = service.validateChecks(workplaceapproval, sites);
			if (errors == "" || errors.isEmpty()) {
				service.submitToCloForReview(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), trades);
				if(tradeAppraisalsChecklist.size() > 0) {					
					tradeAppraisalsChecklistService.updateAll(tradeAppraisalsChecklist);
					tradeAppraisalsChecklist = null;
				}
				getSessionUI().setTask(null);
				super.ajaxRedirectToDashboard();
			} else {
				addWarningMessage("Validation Error. Provide the following before proceeding: " + errors);
				//runInit();
				//getSessionUI().setTask(getSessionUI().getTask());
			}
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void clientSubmitToManager() {
		try {
			String errors = service.validateChecks(workplaceapproval, sites);
			if (errors == "" || errors.isEmpty()) {
				service.clientSubmitToManager(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), trades);
				if(tradeAppraisalsChecklist.size() > 0) {					
					tradeAppraisalsChecklistService.updateAll(tradeAppraisalsChecklist);
					tradeAppraisalsChecklist = null;
				}
				getSessionUI().setTask(null);
				super.ajaxRedirectToDashboard();
			} else {
				addWarningMessage("Validation Error. Provide the following before proceeding: " + errors);
				//runInit();
				//getSessionUI().setTask(getSessionUI().getTask());
			}
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveTaskfindNextInProcess()
	{
		try {
			service.approveRegistration(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void cloRejectToSdf() {
		try {
			if (selectedRejectionReasons.size() == 0) {
				throw new ValidationException("Provide At Least One Rejection Reason Before Proceeding");
			}
			service.rejectionToSDF(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void managerReject() {
		try {
			if (selectedRejectionReasons.size() == 0) {
				throw new ValidationException("Provide At Least One Rejection Reason Before Proceeding");
			}
			service.managerReject(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void cloRecommendationApprovalSelection() {
		try {
			if(workplaceapproval.getSignOffs().size() ==0) {
				throw new Exception("Provide At Least One Sign-off");
			}
			workplaceapproval.setCloRecommendation(CloRecommendationEnum.Approval);
			service.submitForSignOff(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), null);
			service.updateWorkPlaceApprovalSkillsList(workplaceApprovalSkillsSetList, workplaceApprovalSkillsPrograList, workPlaceApprovalUnitStandartList,workPlaceApprovalToolList);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void cloRecommendationRejctionSelection() {
		try {
			
			if(workplaceapproval.getSignOffs().size() ==0) {
				throw new Exception("Provide At Least One Sign-off");
			}
			
			if (selectedRejectionReasons.size() == 0) {
				throw new ValidationException("Provide At Least One Rejection Reason Before Proceeding");
			}
			workplaceapproval.setCloRecommendation(CloRecommendationEnum.Rejection);
			service.submitForSignOff(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons);
			service.updateWorkPlaceApprovalSkillsList(workplaceApprovalSkillsSetList, workplaceApprovalSkillsPrograList, workPlaceApprovalUnitStandartList,workPlaceApprovalToolList);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage(), e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void signOffTask(){
		try {
			for (Signoff signoff : workplaceapproval.getSignOffs()) {
				if (signoff.getUser().getId().equals(getSessionUI().getActiveUser().getId()) && (signoff.getDispute() == true) && (signoff.getAccept()== true)) {
					throw new Exception("Please Select Only One Sign Off Before Proceeding");
				}
				if (signoff.getUser().getId().equals(getSessionUI().getActiveUser().getId()) && (signoff.getDispute() == false) && (signoff.getAccept()== false)) {
					throw new Exception("Please Select Only One Sign Off Before Proceeding");
				}
				
				/*if (signoff.getUser().getId().equals(getSessionUI().getActiveUser().getId()) && (signoff.getAccept() != true )) {
					if(signoff.getAccept()  && signoff.getAccept() ) {
						throw new Exception("Please Select Only One Sign Off Before Proceeding");
					}
					if(signoff.getDispute() != true ) {
						throw new Exception("Please Sign Off Before Proceeding");
					}
				}else if (signoff.getUser().getId().equals(getSessionUI().getActiveUser().getId()) && (signoff.getDispute() != true )) {
					if(signoff.getAccept()  && signoff.getAccept() ) {
						throw new Exception("Please Select Only One Sign Off Before Proceeding");
					}
					if(signoff.getAccept() != true ) {
						throw new Exception("Please Sign Off Before Proceeding");
					}
				}else if (signoff.getUser().getId().equals(getSessionUI().getActiveUser().getId()) && (signoff.getDispute()) && (signoff.getAccept())) {
					throw new Exception("Please Select Only One Sign Off Before Proceeding");
				}*/
			}
			service.signOff(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void signOffTaskWithAppeal(){
		try {
			for (Signoff signoff : workplaceapproval.getSignOffs()) {
				if (signoff.getUser().getId().equals(getSessionUI().getActiveUser().getId()) && signoff.getAccept() != true) {
					throw new Exception("Please Sign Off Before Proceeding");
				}
			}
			for (Doc doc : workplaceapproval.getDocs()) {
				if (doc.getId() == null && doc.getData() == null && doc.getConfigDoc().getRequiredForAppeal()) {
					throw new Exception("Provide All Required Documents Before Appealing WPA");
				}
			}
			workplaceapproval.setWpaAppealed(true);
			service.signOff(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeRegistration() {
		try {			
			if (sites != null && sites.size() > 0) {
				service.completeLegacyRegistration(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(),sites, trades);
				
				getSessionUI().setTask(null);
				ajaxRedirectToDashboard();
			} else {
				addWarningMessage("Validation Error. Provide the following before proceeding: At Least One Mentor");				
			}
			
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveRegistration() {
		try {
			service.approveRegistration(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveRegistration() {
		try {
			service.finalApproveLegacyRegistration(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectRegistration() {
		try {
			service.rejectRegistration(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(),selectedRejectionReasons);
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
			service.finalRejectLegacyRegistration(workplaceapproval, getSessionUI().getActiveUser(),
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

	/**
	 * Preps new instance of trades
	 */
	public void prepNewTradeEntry() {
		try {
			trades = service.findTradesByApproval(workplaceapproval);
			trade = new WorkPlaceApprovalTrades(workplaceapproval);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Preps new instance of site entry
	 * 
	 * @throws Exception
	 */
	public void prepNewSiteEntry() throws Exception {
		sites = service.findSitesByApproval(workplaceapproval, null, null);
		this.site = service.prepareNewDoc(new WorkPlaceApprovalSites(workplaceapproval),
				ConfigDocProcessEnum.WORKPLACE_APPROVAL, CompanyUserTypeEnum.User);
		this.site.setUseCompanyAddress(false);
		prepNewSmeUser();
		if (companyHasSites) {
			if (workplaceapproval != null && workplaceapproval.getCompany() != null) {
				List<Sites> allSitesAssigned = sitesService.findByCompany(workplaceapproval.getCompany());
				if (allSitesAssigned.size() == 1) {
					this.site.setSites(allSitesAssigned.get(0));
				}
				allSitesAssigned = null;
			}
		} else {
			// assigns the residential address from the company if no sites
			// assigned
			if (workplaceapproval.getCompany().getResidentialAddress() != null) {
				this.site.setResidentialAddress(workplaceapproval.getCompany().getResidentialAddress());
			}
		}
	}

	/**
	 * Preps new user to be added
	 * 
	 * @throws Exception
	 */
	public void prepNewSmeUser() throws Exception {
		this.idpassport = IdPassportEnum.RsaId;
		smeUser = new Users();
		smeUser.setDoneSearch(false);
		idnumber = "";
		passportNumber = "";
		editFirstLastName = false;
	}

	/**
	 * Does a search on the database for the user to see if existing users to
	 * populate information
	 */
	public void searchForUser() {
		try {
			if (usersService == null) {
				usersService = new UsersService();
			}
			smeUser = new Users();
			switch (idpassport) {
			case RsaId:
				smeUser = usersService.findByRsaIdJoinAddress(this.idnumber);
				break;
			case Passport:
				smeUser = usersService.findByPassportNumberJoinAddress(this.passportNumber);
				break;
			default:
				smeUser = new Users();
				break;
			}
			usersService = null;
			if (smeUser != null && smeUser.getId() != null) {
				site.setFirstName(smeUser.getFirstName());
				site.setLastName(smeUser.getLastName());
				site.setIdentityNumber(smeUser.getRsaIDNumber());
				editFirstLastName = false;
			} else {
				smeUser = new Users();
				site.setIdentityNumber(this.idnumber);
				editFirstLastName = true;
			}
			smeUser.setDoneSearch(true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Preps WorkPlaceApprovalSites for update
	 */
	public void updateWorkPlaceApprovalSites() {
		try {
			site = service.prepareNewDoc(site, ConfigDocProcessEnum.WORKPLACE_APPROVAL, CompanyUserTypeEnum.User);
			idnumber = site.getIdentityNumber();
			searchForUser();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}

	/**
	 * add to list entry for sites
	 */
	public void addWorkPlaceApprovalSites() {
		try {
			String errors = validationCheck(true);
			if (errors == null || errors.isEmpty()) {
				service.createWorkPlaceApprovalSites(site, getSessionUI().getActiveUser());
				prepNewSiteEntry();
				addInfoMessage("Submit Successful");
			} else {
				addErrorMessage("Unable to add entry. Provide the following information before adding: " + errors);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Validation Check on entry of new site
	 * 
	 * @param siteTradeCheck
	 *            true for site check and false for trade check
	 * @throws Exception
	 */
	private String validationCheck(boolean siteTradeCheck) throws Exception {
		String error = null;
		if (siteTradeCheck) {
			if (site.getFirstName() == null || site.getFirstName().isEmpty() || site.getFirstName() == "") {
				error = " First Name";
			}
			if (site.getLastName() == null || site.getLastName().isEmpty() || site.getLastName() == "") {
				if (error == null) {
					error = " Last Name";
				} else {
					error += ", Last Name";
				}
			}
			if (site.getQualification() == null) {
				if (error == null) {
					error = " Qualification";
				} else {
					error += ", Qualification";
				}
			} else {
				if (service.countSiteByApprovalQualificationRsaIdAndId(workplaceapproval, site.getQualification(),
						site.getIdentityNumber(), site) != 0) {
					error = " Duplicate Entry For Qualification: " + site.getQualification().getDescription()
							+ " and Id Number: " + site.getIdentityNumber()
							+ ". Please Select A Differenet Qualification In Order To Proceed.";
				}
			}
			if (companyHasSites) {
				if (site.getSites() == null && !site.getUseCompanyAddress()) {
					if (error == null) {
						error = " Site";
					} else {
						error += ", Site";
					}
				} else if (site.getUseCompanyAddress() && site.getResidentialAddress() == null
						&& workplaceapproval.getCompany() != null) {
					site.setResidentialAddress(workplaceapproval.getCompany().getResidentialAddress());
				}
				// clearing data
				if (site.getSites() != null && site.getUseCompanyAddress()) {
					site.setSites(null);
				}
				if (site.getResidentialAddress() != null && !site.getUseCompanyAddress()) {
					site.setResidentialAddress(null);
				}
			} else {
				// if no site assigned, check to ensure Residential Address is
				// assigned
				if (site.getSites() == null && site.getResidentialAddress() == null
						&& workplaceapproval.getCompany() != null) {
					site.setResidentialAddress(workplaceapproval.getCompany().getResidentialAddress());
				}
			}
			if (uploadSmeDocs) {
				for (Doc doc : site.getDocs()) {
					// check to ensure docs have been uploaded
					if ((doc.getId() == null && doc.getData() == null)
							|| (doc.getId() != null && DocService.instance().findDocByteByDocID(doc.getId()) == null)) {
						if (error == null) {
							error = " Document Upload: " + doc.getConfigDoc().getName();
						} else {
							error += ", Document Required: " + doc.getConfigDoc().getName();
						}
					}
				}
			}
		} else {
			if (trade.getOfoCodes() == null) {
				error = " Ofo Code";
			} else {
				if (service.countTradesByApprovalOfoAndId(workplaceapproval, trade.getOfoCodes(), trade) != 0) {
					error = " Duplicate Entry For Ofo Code: " + trade.getOfoCodes().getDescription()
							+ ". Please Select A Differenet Ofo Code To Proceed.";
				}
			}
		}
		return error;
	}

	/**
	 * Removes or deletes entry form list
	 */
	public void removeDeleteFromList() {
		try {
			if (site.getId() != null) {
				service.deleteWorkPlaceApprovalSites(site, getSessionUI().getActiveUser());
			} else {
				this.sites.remove(siteListEntry);
			}
			prepNewSiteEntry();
			addWarningMessage("Entry Removed");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void removeFromList() {
		this.sites.remove(site);
		this.site = null;
	}

	public void addTradeToList() {
		try {
			String errors = validationCheck(false);
			if (errors == null || errors.isEmpty()) {
				service.createWorkPlaceApprovalTrade(trade, getSessionUI().getActiveUser());
				prepNewTradeEntry();
				addInfoMessage("Submit Successful");
			} else {
				addErrorMessage("Unable to add entry. Provide the following information before adding: " + errors);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void deleteTradeFromList() {
		try {
			service.deleteWorkPlaceApprovalTrade(trade, getSessionUI().getActiveUser());
			addWarningMessage("Entry Removed");
			prepNewTradeEntry();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<Sites> completeSites(String desc) {
		List<Sites> l = null;
		try {
			l = sitesService.findByNameAndCompany(desc, workplaceapproval.getCompany());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * The first set of the visit date
	 */
	public void setVisitDateOnWorkplaceApproval() {
		try {
			service.setFirstVisitDate(workplaceapproval, getSessionUI().getActiveUser());
			
			if(scheduledEvent == null) {
				scheduledEvent= new ScheduledEvent();
			}
			
			scheduledEvent.setTargetKey(workplaceapproval.getId());
			scheduledEvent.setTargetClass(WorkPlaceApproval.class.getName());
			scheduledEvent.setUser(getSessionUI().getActiveUser());
			scheduledEvent.setFromDateTime(workplaceapproval.getReviewDate());
			scheduledEventService.create(scheduledEvent);
			
			addInfoMessage("Date Added");
			caluclatePermissions();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Populates a list of dates of visit assigned to the work place approval
	 */
	public void viewVisitDateOnWorkplaceApprovalAdded() {
		try {
			visitDateLogList = service.findVistDateLogByWorkplaceApproval(workplaceapproval,
					ConfigDocProcessEnum.WORKPLACE_APPROVAL);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Preps the update for manual date of visit change
	 */
	public void prepVisitDateOnWorkplaceApprovalChange() {
		try {
			dateChangeReasonSelectionList = new ArrayList<>();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Preps reuqired lists for update on Visit Date On WorkplaceApproval
	 */
	public void prepVisitDateOnWorkplaceApproval() {
		try {
			dateChangeReasonSelectionList = new ArrayList<>();
			dateChangeReasonAvalibleSelectionList = DateChangeReasonsService.instance().findByProcess(ConfigDocProcessEnum.WORKPLACE_APPROVAL);
			runClientSideExecute("PF('dlgReviewDate').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Clo updates the date of visit on the workplace approval
	 */
	public void updateVisitDateOnWorkplaceApproval() {
		try {
			if (dateChangeReasonSelectionList.size() != 0) {
				service.create(workplaceapproval);
				service.addNewWorkPlaceApprovalVisitDateLog(workplaceapproval, getSessionUI().getActiveUser(), null,
						false, dateChangeReasonSelectionList, workplaceapproval.getReviewDate(),
						ConfigDocProcessEnum.WORKPLACE_APPROVAL);
				
				scheduledEvent.setFromDateTime(workplaceapproval.getReviewDate());
				scheduledEventService.create(scheduledEvent);
				
				dateChangeReasonSelectionList = null;
				dateChangeReasonAvalibleSelectionList = null;
				addInfoMessage("Date Updated");
				runClientSideExecute("PF('dlgReviewDate').hide()");
				caluclatePermissions();
			} else {
				addWarningMessage("Provide A Minium Of One Reason For Date Change Before Proceeding");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * This is the system driven change of the Visit Date On Work Place Approval
	 * sets the date 5 days from the day activated. Only activated if certain
	 * information selected. Indicator is now set to true if amendments required
	 */
	public void amenmdntsRequiredAfterVisit() {
		try {
			workplaceapproval
					.setReviewDate(GenericUtility.addDaysToDateExcludeWeekends(workplaceapproval.getReviewDate(), 20));
			workplaceapproval.setAmendmentsRequired(true);
			service.create(workplaceapproval);
			service.addNewWorkPlaceApprovalVisitDateLog(workplaceapproval, null,
					"System generated, added 5 days based on business rules provided", true, null,
					workplaceapproval.getReviewDate(), null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	private Signoff signoff;
	private Signoff signoffSelected;
	public void addToList() {
		try {
			if (signoff.getUser() == null || signoff.getUser().getId() == null) {
				this.addErrorMessage("Please select a sign off user");
			} else {
				for (Signoff signoffloop : workplaceapproval.getSignOffs()) {
					if (signoffloop.getUser().getId().equals(signoff.getUser().getId())) {
						prepareNewSignOff();
						throw new Exception("User is already allocated for sign off");
					}
				}
				signoff.setTargetClass(WorkPlaceApproval.class.getName());
				signoff.setTargetKey(workplaceapproval.getId());
				workplaceapproval.getSignOffs().add(signoff);
				prepareNewSignOff();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeFromSignOffList() {
		if (signoffSelected != null) {
			workplaceapproval.setSignOffs(workplaceapproval.getSignOffs().stream()
					.filter(sf -> !validateWspSignOffRemoval(sf)).collect(Collectors.toList()));
			prepareNewSignOff();
			addInfoMessage("Sign Off Removed");
		} else {
			addErrorMessage("Unable to locate selected sign off, contact support!");
		}

	}
	
	public List<Users> findDistinctCompanyUsersPosistionPopulated() {
		CompanyUsersService lookupsService = new CompanyUsersService();
		List<Users> l = null;
		try {
			l = lookupsService.findCompanyUsersByCompanyPopulateRoles(workplaceapproval.getCompany());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	private boolean validateWspSignOffRemoval(Signoff signoff) {
		if (signoff.getUser().getId().equals(signoffSelected.getUser().getId())) {
			return true;
		} else {
			return false;
		}
	}
	
	public void prepareNewSignOff() {
		try {
			signoff = new Signoff();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	//Withdraw WorkplaceApproval methods
	public void witdrawWorkPlaceApproval() {
		try {
			if(doc == null) {
				throw new Exception("Please Upload Required Document");
			}
			if(doc != null && doc.getTargetKey() == null) {
				throw new Exception("Please Upload Required Document");
			}
			service.witdrawWorkPlaceApproval(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), doc);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void completeWorkPlaceApproval() {
		try {
			service.createwithdarawalWorkPlaceApproval(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), doc);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void rejectWorkPlaceApprovalWithdrawal() {
		try {
			service.rejectWorkPlaceApprovalWithdrawal(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalRejectWorkPlaceApprovalWithdrawal() {
		try {
			service.finalRejectWorkPlaceApprovalWithdrawal(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void approveWorkPlaceApprovalWithdrawal(){
		try {
			service.approveWorkPlaceApprovalWithdrawal(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalApproveWorkPlaceApprovalWithdrawal(){
		try {
			service.finalApproveWorkPlaceApprovalWithdrawal(workplaceapproval, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
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
			if(getSessionUI().getTask() != null) {
				l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.WORKPLACE_APPROVAL);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
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

	/** Getters and setters */
	public List<WorkPlaceApproval> getWorkPlaceApprovalList() {
		return workplaceapprovalList;
	}

	public void setWorkPlaceApprovalList(List<WorkPlaceApproval> workplaceapprovalList) {
		this.workplaceapprovalList = workplaceapprovalList;
	}

	public WorkPlaceApproval getWorkplaceapproval() {
		return workplaceapproval;
	}

	public void setWorkplaceapproval(WorkPlaceApproval workplaceapproval) {
		this.workplaceapproval = workplaceapproval;
	}

	public List<WorkPlaceApproval> getWorkPlaceApprovalfilteredList() {
		return workplaceapprovalfilteredList;
	}

	public void setWorkPlaceApprovalfilteredList(List<WorkPlaceApproval> workplaceapprovalfilteredList) {
		this.workplaceapprovalfilteredList = workplaceapprovalfilteredList;
	}

	public LazyDataModel<WorkPlaceApproval> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WorkPlaceApproval> dataModel) {
		this.dataModel = dataModel;
	}

	public boolean isDisableAll() {
		return disableAll;
	}

	public void setDisableAll(boolean disableAll) {
		this.disableAll = disableAll;
	}

	public List<WorkPlaceApprovalSites> getSites() {
		return sites;
	}

	public void setSites(List<WorkPlaceApprovalSites> sites) {
		this.sites = sites;
	}

	public WorkPlaceApprovalSites getSite() {
		return site;
	}

	public void setSite(WorkPlaceApprovalSites site) {
		this.site = site;
	}

	public List<WorkPlaceApprovalTrades> getTrades() {
		return trades;
	}

	public void setTrades(List<WorkPlaceApprovalTrades> trades) {
		this.trades = trades;
	}

	public WorkPlaceApprovalTrades getTrade() {
		return trade;
	}

	public void setTrade(WorkPlaceApprovalTrades trade) {
		this.trade = trade;
	}

	public boolean isTradeApproval() {
		return tradeApproval;
	}

	public void setTradeApproval(boolean tradeApproval) {
		this.tradeApproval = tradeApproval;
	}

	public boolean isDisableForOfficeUse() {
		return disableForOfficeUse;
	}

	public void setDisableForOfficeUse(boolean disableForOfficeUse) {
		this.disableForOfficeUse = disableForOfficeUse;
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

	public Users getSmeUser() {
		return smeUser;
	}

	public void setSmeUser(Users smeUser) {
		this.smeUser = smeUser;
	}

	public IdPassportEnum getIdpassport() {
		return idpassport;
	}

	public void setIdpassport(IdPassportEnum idpassport) {
		this.idpassport = idpassport;
	}

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
	}

	public Long getMAX_PASSPORT_NUMBER() {
		return MAX_PASSPORT_NUMBER;
	}

	public void setMAX_PASSPORT_NUMBER(Long mAX_PASSPORT_NUMBER) {
		MAX_PASSPORT_NUMBER = mAX_PASSPORT_NUMBER;
	}

	public String getPassportNumberFormat() {
		return passportNumberFormat;
	}

	public void setPassportNumberFormat(String passportNumberFormat) {
		this.passportNumberFormat = passportNumberFormat;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public WorkPlaceApprovalSites getSiteListEntry() {
		return siteListEntry;
	}

	public void setSiteListEntry(WorkPlaceApprovalSites siteListEntry) {
		this.siteListEntry = siteListEntry;
	}

	public boolean isUpload() {
		return upload;
	}

	public void setUpload(boolean upload) {
		this.upload = upload;
	}

	public boolean isUploadSmeDocs() {
		return uploadSmeDocs;
	}

	public void setUploadSmeDocs(boolean uploadSmeDocs) {
		this.uploadSmeDocs = uploadSmeDocs;
	}

	public boolean isSignOff() {
		return signOff;
	}

	public void setSignOff(boolean signOff) {
		this.signOff = signOff;
	}

	public boolean isCloAction() {
		return cloAction;
	}

	public void setCloAction(boolean cloAction) {
		this.cloAction = cloAction;
	}

	public List<RejectReasons> getRejectReasonsList() {
		return rejectReasonsList;
	}

	public void setRejectReasonsList(List<RejectReasons> rejectReasonsList) {
		this.rejectReasonsList = rejectReasonsList;
	}

	public List<RejectReasons> getSelectedRejectReasonsList() {
		return selectedRejectReasonsList;
	}

	public void setSelectedRejectReasonsList(List<RejectReasons> selectedRejectReasonsList) {
		this.selectedRejectReasonsList = selectedRejectReasonsList;
	}

	public List<DateChangeReasons> getDateChangeReasonSelectionList() {
		return dateChangeReasonSelectionList;
	}

	public void setDateChangeReasonSelectionList(List<DateChangeReasons> dateChangeReasonSelectionList) {
		this.dateChangeReasonSelectionList = dateChangeReasonSelectionList;
	}

	public List<WorkPlaceApprovalVisitDateLog> getVisitDateLogList() {
		return visitDateLogList;
	}

	public void setVisitDateLogList(List<WorkPlaceApprovalVisitDateLog> visitDateLogList) {
		this.visitDateLogList = visitDateLogList;
	}

	public boolean isAllowReview() {
		return allowReview;
	}

	public void setAllowReview(boolean allowReview) {
		this.allowReview = allowReview;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public List<DateChangeReasons> getDateChangeReasonAvalibleSelectionList() {
		return dateChangeReasonAvalibleSelectionList;
	}

	public void setDateChangeReasonAvalibleSelectionList(
			List<DateChangeReasons> dateChangeReasonAvalibleSelectionList) {
		this.dateChangeReasonAvalibleSelectionList = dateChangeReasonAvalibleSelectionList;
	}

	public LazyDataModel<SitesSme> getAvalibleSmeDataModel() {
		return avalibleSmeDataModel;
	}

	public void setAvalibleSmeDataModel(LazyDataModel<SitesSme> avalibleSmeDataModel) {
		this.avalibleSmeDataModel = avalibleSmeDataModel;
	}

	public SitesSme getSitesSme() {
		return sitesSme;
	}

	public void setSitesSme(SitesSme sitesSme) {
		this.sitesSme = sitesSme;
	}

	public boolean isCanRecommendApproval() {
		return canRecommendApproval;
	}

	public void setCanRecommendApproval(boolean canRecommendApproval) {
		this.canRecommendApproval = canRecommendApproval;
	}

	public List<RejectReasons> getSelectedRejectionReasons() {
		return selectedRejectionReasons;
	}

	public void setSelectedRejectionReasons(List<RejectReasons> selectedRejectionReasons) {
		this.selectedRejectionReasons = selectedRejectionReasons;
	}

	public boolean isUploadAppeal() {
		return uploadAppeal;
	}

	public void setUploadAppeal(boolean uploadAppeal) {
		this.uploadAppeal = uploadAppeal;
	}

	public boolean isUploadProofAgreement() {
		return uploadProofAgreement;
	}

	public void setUploadProofAgreement(boolean uploadProofAgreement) {
		this.uploadProofAgreement = uploadProofAgreement;
	}

	public boolean isUploadScopeEvidencet() {
		return uploadScopeEvidencet;
	}

	public void setUploadScopeEvidencet(boolean uploadScopeEvidencet) {
		this.uploadScopeEvidencet = uploadScopeEvidencet;
	}

	public List<TradeAppraisalsChecklist> getTradeAppraisalsChecklist() {
		return tradeAppraisalsChecklist;
	}

	public void setTradeAppraisalsChecklist(List<TradeAppraisalsChecklist> tradeAppraisalsChecklist) {
		this.tradeAppraisalsChecklist = tradeAppraisalsChecklist;
	}

	public boolean isCanShowDocument() {
		return canShowDocument;
	}

	public void setCanShowDocument(boolean canShowDocument) {
		this.canShowDocument = canShowDocument;
	}

	public Signoff getSignoff() {
		return signoff;
	}

	public void setSignoff(Signoff signoff) {
		this.signoff = signoff;
	}

	public Signoff getSignoffSelected() {
		return signoffSelected;
	}

	public void setSignoffSelected(Signoff signoffSelected) {
		this.signoffSelected = signoffSelected;
	}

	public List<WorkplaceApprovalSkillsSet> getWorkplaceApprovalSkillsSetList() {
		return workplaceApprovalSkillsSetList;
	}

	public void setWorkplaceApprovalSkillsSetList(List<WorkplaceApprovalSkillsSet> workplaceApprovalSkillsSetList) {
		this.workplaceApprovalSkillsSetList = workplaceApprovalSkillsSetList;
	}

	public List<WorkPlaceApprovalSkillsProgramme> getWorkplaceApprovalSkillsPrograList() {
		return workplaceApprovalSkillsPrograList;
	}

	public void setWorkplaceApprovalSkillsPrograList(
			List<WorkPlaceApprovalSkillsProgramme> workplaceApprovalSkillsPrograList) {
		this.workplaceApprovalSkillsPrograList = workplaceApprovalSkillsPrograList;
	}

	public List<WorkplaceApprovalUnitStandart> getWorkPlaceApprovalUnitStandartList() {
		return workPlaceApprovalUnitStandartList;
	}

	public void setWorkPlaceApprovalUnitStandartList(
			List<WorkplaceApprovalUnitStandart> workPlaceApprovalUnitStandartList) {
		this.workPlaceApprovalUnitStandartList = workPlaceApprovalUnitStandartList;
	}

	public List<WorkPlaceApprovalToolList> getWorkPlaceApprovalToolList() {
		return workPlaceApprovalToolList;
	}

	public void setWorkPlaceApprovalToolList(List<WorkPlaceApprovalToolList> workPlaceApprovalToolList) {
		this.workPlaceApprovalToolList = workPlaceApprovalToolList;
	}

}
