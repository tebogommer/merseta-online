package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersChange;
import haj.com.entity.CompanyLearnersLostTime;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompletionLetter;
import haj.com.entity.Doc;
import haj.com.entity.Learners;
import haj.com.entity.NonSetaQualificationsCompletion;
import haj.com.entity.SDPCompany;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.CreatedByEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.GenerateAddEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerChangeTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.LearnerTransferTypeEnum;
import haj.com.entity.enums.LostTimeReason;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.TerminationTypeEnum;
import haj.com.entity.enums.TradeTestProgressEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.TransferRequestTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SdpType;
import haj.com.entity.lookup.WithdrawReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyLearnersChangeService;
import haj.com.service.CompanyLearnersLostTimeService;
import haj.com.service.CompanyLearnersOtpSignoffService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyLearnersTerminationService;
import haj.com.service.CompanyLearnersTradeTestService;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.CompletionLetterService;
import haj.com.service.DocService;
import haj.com.service.JasperService;
import haj.com.service.LearnersService;
import haj.com.service.NonSetaQualificationsCompletionService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SDPCompanyService;
import haj.com.service.TasksService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderSkillsProgrammeService;
import haj.com.service.TrainingProviderSkillsSetService;
import haj.com.service.TrainingProviderVerficationService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DateChangeReasonsService;
import haj.com.service.lookup.DesignatedTradeLevelService;
import haj.com.service.lookup.SaqaUsQualificationService;
import haj.com.service.lookup.WithdrawReasonsService;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

@ManagedBean(name = "learnersUI")
@ViewScoped
public class LearnersUI extends AbstractUI {
	
	private SDFCompanyService sDFCompanyService = new SDFCompanyService();
	private LearnersService service = new LearnersService();
	private CompanyService companyService = new CompanyService();
	private CompletionLetterService completionLetterService = new CompletionLetterService();
	private NonSetaQualificationsCompletionService nonSetaQualificationsCompletionService = new NonSetaQualificationsCompletionService();
	private TrainingProviderVerficationService trainingProviderVerficationService = new TrainingProviderVerficationService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private List<Learners> learnersList = null;
	private List<Learners> learnersfilteredList = null;
	private List<Company> companies = null;
	private Company selectedCompany;
	private Learners learners = null;
	private CompanyLearners companyLearners = null;
	private CompanyLearnersTransfer companyLearnersTransfer;
	private CompanyLearnersLostTime companyLearnersLostTime;
	private CompanyLearnersTermination companyLearnersTermination;
	private CompanyLearnersChange companyLearnersChange;
	private CompanyLearnersTradeTest companyLearnersTradeTest;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private TrainingProviderVerfication trainingProviderMonitoring;
	private NonSetaQualificationsCompletion nonSetaQualificationsCompletion;
	private CompletionLetter completionLetter;
	private LazyDataModel<CompanyLearners> dataModel;
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private boolean viewLearnerData = false;
	private boolean viewLearnerDetails = false;
	private boolean viewTaskData = false;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewdataModel;
	private LazyDataModel<Users> dataModelUsers;
	private Users learner;
	private Users selectedLearner;
	private Date readyDateForTradeTestMinium;
	private Date readyDateForTradeTestMax;
	private boolean cbmtQualification = false;
	private LazyDataModel<CompanyQualifications> qualAccredetedCompanyDataModel; 
	private CompanyQualifications selectedCompanyQualification;
	private String accreditedQuallMssg;
	private String workplaceApprovalMsg;
	private TrainingProviderApplication selectedTrainingProvider;
	private Boolean learnerRequest;
	private Boolean primaryOrSecondarySDF;
	private Boolean companyContactRegisterLearner;
	private SdpType sdpType = null;
	private SDPCompany sdpCompanyLink = null;
	private Boolean registerLearners = false;
	private boolean requireWPA = false;
	private CompanyLearnersLostTimeService companyLearnersLostTimeService;
	UsersService usersService= new UsersService();
	
	/* Trade test updates */
	private List<DateChangeReasons> dateChangeReasonSelectionList = null;
	private List<DateChangeReasons> dateChangeReasonAvalibleSelectionList = null;
	/* Entity */
	private DesignatedTradeLevel selectedDesignatedTradeLevel = null;
	
	/* Service levels */
	private CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
	private CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
	private DesignatedTradeLevelService designatedTradeLevelService = new DesignatedTradeLevelService();
	private UsersLanguageService usersLanguageService = new UsersLanguageService();
	private UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	
	/* Array Lists */
	private List<DesignatedTradeLevel> designatedTradeLevelList = new ArrayList<>();
	
	/* booleans */
	private boolean displayLastInfo = false;
	
	/* Vars */
	private int avalibleTradeTestCenters = 0;
	private String validationErrors = "";
	private Boolean selectedNewDesignatedTradeLevel = false;
	
	private LazyDataModel<Tasks> dataModelTasks;
	private TasksService tasksService = null;
	private GenerateAddEnum generateAddEnum;
	private List<WithdrawReasons> withdrawRejectReason = new ArrayList<>();
	private List<WithdrawReasons> withdrawReason = null;
	
	AddressService addressService =new AddressService();
	
	/** The doc. */
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
		if (getSessionUI().getTask() != null ) {
			getSessionUI().setTask(null);
		}
		prepareNew();
		if (getSessionUI().isExternalParty()) {
			companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
		} else {
			learnerUsersInfo();
		}
	}
	
	public void checkLearnerInfo() {
		if (getSessionUI().isExternalParty()) {
			try {
				registerLearners = false;
				primaryOrSecondarySDF = sDFCompanyService.checkIfPrimarOrSecondaryCanRegisterLearners(getSessionUI().getActiveUser(), selectedCompany);
				
				if(!primaryOrSecondarySDF) {
					companyContactRegisterLearner = companyUsersService.checkIfCompanyContactCanRegisterLearner(getSessionUI().getActiveUser(), selectedCompany, ConfigDocProcessEnum.Learner);
					if(companyContactRegisterLearner) {
						registerLearners = true;
						learnersInfo();
					} else {
						addErrorMessage("Kindly be advised that you require the relevant authorisation to access this information");
					}
				} else {
					registerLearners = true;
					learnersInfo();
				}
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
			}			
		}		
	}
	
	public void populateSdpType() throws Exception{
		if (getSessionUI().isExternalParty()) {
			sdpCompanyLink = null;
			sdpType = null;
			if (selectedTrainingProvider != null && selectedTrainingProvider.getCompany() != null && selectedTrainingProvider.getId() != null) {
				if (selectedTrainingProvider.getTrainingSite() != null && selectedTrainingProvider.getTrainingSite().getId() != null) {
					sdpCompanyLink = sdpCompanyService.findBySdpIdCompanyIdAndTrainingSiteIdReturnOneResult(getSessionUI().getActiveUser().getId(), selectedTrainingProvider.getCompany().getId(), selectedTrainingProvider.getTrainingSite().getId());
				} else {
					sdpCompanyLink = sdpCompanyService.findBySdpIdAndCompanyIdReturnOneResult(getSessionUI().getActiveUser().getId(), selectedTrainingProvider.getCompany().getId());
				}
				if (sdpCompanyLink != null) {
					sdpType = sdpCompanyLink.getSdpType();
					if (sdpType != null && sdpType.getRegisterLearners()) {
						registerLearners = true;
					}
				} else {
					// old version fall back
					sdpType = trainingProviderApplicationService.locateSdpTypeByApplicationAndSessionUser(selectedTrainingProvider, getSessionUI().getActiveUser());
					if (sdpType != null && sdpType.getRegisterLearners()) {
						registerLearners = true;
					}
				}
			}
			// old version
//			if (selectedTrainingProvider != null && selectedTrainingProvider.getCompany() != null && selectedTrainingProvider.getId() != null) {
//				sdpType = trainingProviderApplicationService.locateSdpTypeByApplicationAndSessionUser(selectedTrainingProvider, getSessionUI().getActiveUser());
//				if (sdpType != null && sdpType.getRegisterLearners()) {
//					registerLearners = true;
//				}
//			}
			
		}else {
			registerLearners = true;
		}
	}
	
	public void validiateCanViewInformation() throws Exception{
		if (getSessionUI().isExternalParty()) {
			if (sdpType == null || sdpType.getViewLearners() == null || !sdpType.getViewLearners()) {
				selectedTrainingProvider = null;
				dataModel = null;
				throw new Exception("Kindly be advised that you require the relevant authorisation to access this information.");
			}
		} else {
			registerLearners = true;
		}
	}
	
	public void learnersInfo() {
		try {
			this.viewLearnerData = false;
			this.viewTaskData = false;
			this.viewLearnerDetails = false;
			if (registerLearners == null || !registerLearners) {
				populateSdpType();
				validiateCanViewInformation();
			}
			dataModel = new LazyDataModel<CompanyLearners>() {
				private static final long serialVersionUID = 1L;
				private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();

				@Override
				public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
					try {

						if (selectedCompany != null) {
							filters.put("companyID", selectedCompany.getId());
							//filters.put("terminated", LearnerStatusEnum.Terminated);
							retorno = service.allLearnersByCompany(first, pageSize, sortField, sortOrder, filters);
							dataModel.setRowCount(service.countByCompany(Learners.class, filters));
						} else {
							filters.put("userID", learner.getId());
							retorno = service.allLearnersByUser(first, pageSize, sortField, sortOrder, filters);
							/*for(CompanyLearners companyLearners: retorno) {
								if(companyLearners.getEmployer() != null && companyLearners.getEmployer().getPostalAddress() != null) {
									companyLearners.getEmployer().setPostalAddress(AddressService.instance().findByKey(companyLearners.getEmployer().getPostalAddress().getId()));
									companyLearners.getEmployer().setContactPerson(companyUsersService.findCompanyContactPerson(companyLearners.getEmployer().getId()));
								}
								companyLearners.getEmployer().setContactPerson(companyUsersService.findCompanyContactPerson(companyLearners.getEmployer().getId()));
								companyLearners.setCompany(companyService.findByKey(companyLearners.getCompany().getId()));
								if(companyLearners.getUser() != null) {
									companyLearners.getUser().setUsersLanguageList(usersLanguageService.findByUser(companyLearners.getUser()));
									if(companyLearners.getUser().getDisability() == YesNoEnum.Yes) {
										companyLearners.getUser().setUsersDisabilityList(usersDisabilityService.findByKeyUser(companyLearners.getUser()));
									}
								}
							}*/
							// companyLearnersService.resolveAllData(retorno);
							dataModel.setRowCount(service.countByUser(Learners.class, filters));
						}

					} catch (Exception e) {
						addErrorMessage(e.getMessage(), e);
						logger.fatal(e);
						e.printStackTrace();
					}
					return retorno;
				}

				@Override
				public Object getRowKey(CompanyLearners obj) {
					return obj.getId();
				}

				@Override
				public CompanyLearners getRowData(String rowKey) {
					for (CompanyLearners obj : retorno) {
						if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
					}
					return null;
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		
		
		
	}

	public void learnerUsersInfo() {
		// this.viewLearnerData = false;
		dataModelUsers = new LazyDataModel<Users>() {
			private static final long serialVersionUID = 1L;
			private List<Users> retorno = new ArrayList<Users>();

			@Override
			public List<Users> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allUsersFilter(Users.class, first, pageSize, sortField, sortOrder, filters);
					dataModelUsers.setRowCount(service.countUsersFilter(Users.class, filters));
		
					/*retorno = service.allLearnersAsUsers(first, pageSize, sortField, sortOrder, filters);
					dataModelUsers.setRowCount(service.countUsers(Learners.class, filters));*/
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Users obj) {
				return obj.getId();
			}

			@Override
			public Users getRowData(String rowKey) {
				for (Users obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}	

	public void viewTasksByWsp() {
		try {
			dataModelTasksInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void dataModelTasksInfo() {
		if (tasksService == null) {
			tasksService = new TasksService();
		}
		this.viewTaskData = true;
		dataModelTasks = new LazyDataModel<Tasks>() {
			private static final long serialVersionUID = 1L;
			private List<Tasks> retorno = new ArrayList<Tasks>();
			@Override
			public List<Tasks> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = tasksService.allTasksByTargetClassAndKey(first, pageSize, sortField, sortOrder, filters, companyLearners.getId(), companyLearners.getClass().getName());					
					dataModelTasks.setRowCount(tasksService.countAllTasksByTargetClassAndKey(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(Tasks obj) {
				return obj.getId();
			}

			@Override
			public Tasks getRowData(String rowKey) {
				for (Tasks obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void viewCompanyLearnerData() {
		try {
			companyLearnersService.resolveAllData(companyLearners);
			this.viewLearnerData = true;
			viewLearnerDetails = false;
			//RequestContext context = RequestContext.getCurrentInstance();
		    //context.scrollTo("mainForm:pgLearnerDetails");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void viewCompanyLearnerDetails() {
		try {
			
			if(companyLearners == null) {
				throw new Exception("Learner error");
			}			
			companyLearnersService.additionalData(companyLearners);
			companyLearnersService.prepareNewRegistration(companyLearners);			
			if(companyLearners.getUser() != null) {
				companyLearners.getUser().setUsersLanguageList(usersLanguageService.findByUser(companyLearners.getUser()));
				if(companyLearners.getUser().getDisability() == YesNoEnum.Yes) {
					companyLearners.getUser().setUsersDisabilityList(usersDisabilityService.findByKeyUser(companyLearners.getUser()));
				}
			}		
			if(companyLearners.getInterventionType().getDescription().contains("Bursar")) {
				companyLearners.setContinuation_flag_check(true);
				if(companyLearners.getContinuation_flag()!=null)
				{
				if(companyLearners.getContinuation_flag()==1) {
					companyLearners.setContinuation_flag_check_status(true);
				}else {
					companyLearners.setContinuation_flag_check_status(false);
				}
				}
				else
				{
					companyLearners.setContinuation_flag_check_status(false);
				}
			}else {
				companyLearners.setContinuation_flag_check(false);
			}
//			System.out.println("companyLearners.getInterventionType().getDescription()----"+companyLearners.getInterventionType().getDescription());
//			System.out.println("companyLearners.isContinuation_flag_check()----"+companyLearners.isContinuation_flag_check());
//			System.out.println("companyLearners.getContinuation_flag()----"+companyLearners.getContinuation_flag());
//			System.out.println("companyLearners.isContinuation_flag_check_status()----"+companyLearners.isContinuation_flag_check_status());

//			System.out.println("emp status:"+companyLearners.getEmploymentStatus());
//			System.out.println("employement status:"+companyLearners.getEmploymentStatus().getRegistrationName());
			viewLearnerDetails = true;
			this.viewLearnerData = false;
			addInfoMessage("Learner Selected");
			//RequestContext context = RequestContext.getCurrentInstance();
			//context.scrollTo("mainForm:pageLearnerDetails");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);			
		}
	}

	public void learnersInsert() {
		try {
			service.create(this.learners);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void learnersUpdate() {
		try {
			service.update(this.learners);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void companyLearnersUpdate() {
		// method added for jira 114
		System.out.println("in companyLearnersUpdate");
		try {
			companyLearnersService.update(companyLearners);
			usersService.update(this.companyLearners.getUser());
			addressService.update(this.companyLearners.getUser().getResidentialAddress());
			addressService.update(this.companyLearners.getUser().getPostalAddress());
//			companyService.update(this.companyLearners.getEmployer());
//			companyService.update(this.companyLearners.getCompany());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	

	public void learnersDelete() {
		try {
			service.delete(this.learners);
			prepareNew();
			learnersInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareNew() {
		learners = new Learners();
	}

	public List<Learners> complete(String desc) {
		List<Learners> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void redirectToLearnerRegNonMersetaCompanies () {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("companyId", this.selectedCompany.getId(), true);
		super.ajaxRedirect("/pages/tp/learnerregistrationformnonmerseta.jsf");
	}

	public void redirectToLearnerRegForCompany() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("selectedTrainingProviderId", this.selectedTrainingProvider.getId(), true);
		super.ajaxRedirect("/pages/tp/learnerRegistrationForm.jsf");
	}
	
	public void redirectToLearnerRegForEmployer() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("employerId", this.selectedCompany.getId(), true);		
		super.ajaxRedirect("/pages/tp/learnerregistrationsdf.jsf");
		//super.ajaxRedirect("/pages/tp/learnerRegistrationFormSDF.jsf");
	}
	
	public void redirectToLearnerRegForEmployerOtpSignoff() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("employerId", this.selectedCompany.getId(), true);		
		super.ajaxRedirect("/pages/tp/learnerregistrationotpsignoff.jsf");
	}
	
	public void redirectToLearnerRegForProiderOtpSignoff() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("selectedTrainingProviderId", this.selectedTrainingProvider.getId(), true);		
		super.ajaxRedirect("/pages/tp/learnerregistrationformsignoff.jsf");
	}
	
	public void redirectToLearnerRegTP() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("selectedTrainingProviderId", this.selectedTrainingProvider.getId(), true);
		super.ajaxRedirect("/pages/companylearneruser.jsf");
	}
	
	public void redirectToLearnerReg() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("employerId", this.selectedCompany.getId(), true);		
		super.ajaxRedirect("/pages/companylearneruser.jsf");
		//super.ajaxRedirect("/pages/tp/learnerRegistrationFormSDF.jsf");
	}

	public void redirectToLearnerUpdateForCompany() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		super.setParameter("companyLearnersId", this.companyLearners.getId(), true);
		super.ajaxRedirect("/pages/tp/companylearnersdetailschange.jsf");
	}
	
	public void downlodDocument() {
		try {
			if(companyLearners.getElearner() != null && companyLearners.getElearner() ) {
				CompanyLearnersOtpSignoffService companyLearnersOtpSignoffService = new CompanyLearnersOtpSignoffService();
				companyLearnersOtpSignoffService.downloadOTPAgreementForm(companyLearners);
			}else {
				companyLearnersService.downloadWorkBasedLearningProgrammeAgreement(companyLearners);
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());			
		}
	}
	
	public void prepTransfer() {
		try {			
			companyLearnersTransfer = new CompanyLearnersTransfer();
			companyLearnersTransfer.setCompanyLearners(companyLearners);
			requireWPA = companyLearnersService.checkIfRequiresWPA(companyLearners);
			if (getSessionUI().isLearner()) {
				companyLearnersTransfer.setTransferRequestType(TransferRequestTypeEnum.Learner);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
	}

	
	
	public void prepDocumentChange() {
		prepVisitDateOnWorkplaceApproval();
		prepVisitDateOnWorkplaceApprovalChange();
	}

	public void prepTermination() {
		companyLearnersTermination = new CompanyLearnersTermination();
		companyLearnersTermination.setCompanyLearners(companyLearners);
		if(selectedTrainingProvider !=null){
			companyLearnersTermination.setTrainingProviderApplication(selectedTrainingProvider);
		}
		if(getSessionUI().isEmployee()) {
			companyLearnersTermination.setCreatedByEnum(CreatedByEnum.merSETA);
		}else {
			if(selectedTrainingProvider !=null && getSessionUI().isTrainingProvider()) {
				companyLearnersTermination.setCreatedByEnum(CreatedByEnum.sdp);
			}else {
				companyLearnersTermination.setCreatedByEnum(CreatedByEnum.sdf);
			}
		}
	}
	
	public void prepWithdrawal() {
	    try {
		WithdrawReasonsService withdrawReasonsService = new WithdrawReasonsService();
		withdrawReason = new ArrayList<>();
		withdrawReason = withdrawReasonsService.findByProcess(ConfigDocProcessEnum.MutualLearnerTermination);
	    } catch (Exception e) {
		e.printStackTrace();		
	    }
	}

	public void prepCompanyLearnersChange() {
		companyLearnersChange = new CompanyLearnersChange();
		companyLearnersChange.setCompanyLearners(companyLearners);
		if(selectedTrainingProvider !=null){
			companyLearnersChange.setTrainingProviderApplication(selectedTrainingProvider);
		}
		if(getSessionUI().isEmployee()) {
			companyLearnersChange.setCreatedByEnum(CreatedByEnum.merSETA);
		}else {
			if(selectedTrainingProvider !=null && getSessionUI().isTrainingProvider()) {
				companyLearnersChange.setCreatedByEnum(CreatedByEnum.sdp);
			}else {
				companyLearnersChange.setCreatedByEnum(CreatedByEnum.sdf);
			}
		}
	}

	public void prepCompanyLearnersTradeTest() {
		try {
			if (companyLearners.getQualification() != null) {
				readyDateForTradeTestMinium = null;
				readyDateForTradeTestMax = null;
				if (companyLearners.getQualification().getDesignatedTrade() == null) {
//				if (DesignatedTradeLevelService.instance().countEntiresByQualificationId(companyLearners.getQualification()) == 0) {
					cbmtQualification = false;
					readyDateForTradeTestMinium = companyLearnersService.calculateMiniumDateForTradeTestBasedOnLearnerNonCBMT(companyLearners);
				} else {
					cbmtQualification = true;
				}
				
				companyLearnersTradeTest = new CompanyLearnersTradeTest();
				companyLearnersTradeTest.setCompanyLearners(companyLearners);
				companyLearnersTradeTest.setTradeTestType(TradeTestTypeEnum.TRADE_TEST);
				companyLearnersTradeTest.setCbmtQualification(cbmtQualification);
				if (cbmtQualification) {
					// calculates if its the last level of the CBMT show
//					companyLearnersService.locateCorrectDesignatedTradeLevel(companyLearnersTradeTest, companyLearners);
					readyDateForTradeTestMinium = companyLearnersService.calculateMinDateForCbmt(companyLearnersTradeTest, companyLearners);
					readyDateForTradeTestMax = companyLearnersService.calculateMaxDateForCbmt(companyLearnersTradeTest, companyLearners);
					CompanyLearnersTradeTest latestCompanyLearnersTradeTest=companyLearnersTradeTestService.findLastByTradeTypeLearnerQualification(TradeTestTypeEnum.TRADE_TEST, companyLearners.getUser().getId(), companyLearners.getQualification().getId());
					if(latestCompanyLearnersTradeTest !=null && latestCompanyLearnersTradeTest.getDesignatedTradeLevel().equals(companyLearnersTradeTest.getDesignatedTradeLevel()) && latestCompanyLearnersTradeTest.getCompetenceEnum() ==CompetenceEnum.NotYetCompetent ) {
						companyLearnersTradeTest.setPreviousAttemptDate(latestCompanyLearnersTradeTest.getDateOfTest());
						companyLearnersTradeTest.setPreviousTrainingCenter(latestCompanyLearnersTradeTest.getPreferredTrainingCenter());
						companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.Yes);
						companyLearnersTradeTest.setAttemptNumber(latestCompanyLearnersTradeTest.getAttemptNumber()+1);
					}
				}
				companyLearnersTradeTest.setLearner(companyLearners.getUser());
				companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithInitiator);
				companyLearnersTradeTest.setMinDate(readyDateForTradeTestMinium);
				if (readyDateForTradeTestMax != null) {
					companyLearnersTradeTest.setMaxDate(readyDateForTradeTestMax);
				}
				companyLearnersTradeTest.setQualification(companyLearners.getQualification());
				runClientSideExecute("PF('dlgTradeTest').show()");
			}else{
				addWarningMessage("Unable to locate qualification, contact support!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepCompanyLearnersTradeTestVersionTwo() {
		try {

			
			if (companyLearners.getQualification() != null) {

				readyDateForTradeTestMinium = null;
				readyDateForTradeTestMax = null;
				
				// Determine if qualification is CBMT / Designated Trade
				cbmtQualification = designatedTradeLevelService.determainQualificationDesignatedTrade(companyLearners.getQualification());
				// set default information
				companyLearnersTradeTest = new CompanyLearnersTradeTest();
				
				companyLearnersTradeTest.setTradeTestType(TradeTestTypeEnum.TRADE_TEST);
				companyLearnersTradeTest.setOnHold(false);
				companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithInitiator);
				companyLearnersTradeTest.setQualification(companyLearners.getQualification());
				companyLearnersTradeTest.setAlterEmployerInfo(false);
				companyLearnersTradeTest.setCbmtQualification(cbmtQualification);
				
				companyLearnersTradeTest.setCreateUser(getSessionUI().getActiveUser());
				companyLearnersTradeTest.setCompanyLearners(companyLearners);
				companyLearnersTradeTest.setLearner(companyLearners.getUser());
				
				if (companyLearners.getEmployer() != null && companyLearners.getEmployer().getId() != null) {
					companyLearnersTradeTest.setEmploymentStatus(EmploymentStatusEnum.Employed);
					companyLearnersTradeTest.setEmployerOnNsdms(YesNoEnum.Yes);
					companyLearnersTradeTest.setCompanyEmployer(companyLearners.getEmployer());
				}
				
				if (cbmtQualification) {
					
					// check if can complete in any order
					boolean noOrder = designatedTradeLevelService.determainQualificationDesignatedTradeAndNoCompletionOrder(companyLearners.getQualification(), 0l);
					if (noOrder) {
						
						companyLearnersTradeTest.setFinalCbmtQualification(false);
						selectedNewDesignatedTradeLevel = true;
						
						// populates all designated trade level
						designatedTradeLevelList = designatedTradeLevelService.findByQualificationIdOrderByLevel(companyLearners.getQualification());
						Integer incompleteMoudles = designatedTradeLevelList.size();
						for (DesignatedTradeLevel tradeLevel : designatedTradeLevelList) {
							int completedMoudle = companyLearnersTradeTestService.countTradeTestByCompanyLearnerDesignatedTradeAndCompleted(TradeTestTypeEnum.TRADE_TEST, companyLearnersTradeTest.getCompanyLearners(), tradeLevel, CompetenceEnum.Competent, ApprovalEnum.Completed);
							if (completedMoudle > 0) {
								tradeLevel.setCanSelected(false);
								incompleteMoudles--;
							} else {
								tradeLevel.setCanSelected(true);
							}
						}
						if (incompleteMoudles == 1) {
							companyLearnersTradeTest.setFinalCbmtQualification(true);
						}
					} else {
						selectedNewDesignatedTradeLevel = false;
						companyLearnersService.locateCorrectDesignatedTradeLevel(companyLearnersTradeTest, companyLearners);
						readyDateForTradeTestMinium = companyLearnersService.calculateMinDateForCbmt(companyLearnersTradeTest, companyLearners);
						readyDateForTradeTestMax = companyLearnersService.calculateMaxDateForCbmt(companyLearnersTradeTest, companyLearners);
						
						// populates all designated trade level
						designatedTradeLevelList = designatedTradeLevelService.findByQualificationIdOrderByLevel(companyLearners.getQualification());
						
						// calculate previous attempts 
						CompanyLearnersTradeTest latestCompanyLearnersTradeTest = companyLearnersTradeTestService.findLastByTradeTypeLearnerQualification(TradeTestTypeEnum.TRADE_TEST, companyLearners.getUser().getId(), companyLearners.getQualification().getId());
						if (latestCompanyLearnersTradeTest !=null && latestCompanyLearnersTradeTest.getDesignatedTradeLevel().equals(companyLearnersTradeTest.getDesignatedTradeLevel()) && latestCompanyLearnersTradeTest.getCompetenceEnum() ==CompetenceEnum.NotYetCompetent) {
							companyLearnersTradeTest.setPreviousAttemptDate(latestCompanyLearnersTradeTest.getDateOfTest());
							companyLearnersTradeTest.setPreviousTrainingCenter(latestCompanyLearnersTradeTest.getPreferredTrainingCenter());
							companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.Yes);
							companyLearnersTradeTest.setAttemptNumber(latestCompanyLearnersTradeTest.getAttemptNumber() + 1);
							// of an attempt can re-select CBMT Qualification with no completion order
							selectedNewDesignatedTradeLevel = false;	
						} else {
							companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.No);
							companyLearnersTradeTest.setAttemptNumber(1);
						}
					}
					
				} else {
					companyLearnersTradeTestService.calculateTradeTestAttempts(companyLearnersTradeTest, companyLearnersTradeTest.getQualification(), companyLearners.getUser(), cbmtQualification);
					readyDateForTradeTestMinium = companyLearnersService.calculateMiniumDateForTradeTestBasedOnLearnerNonCBMT(companyLearners);
				}
				
				// sets additional data if avalible
				if (readyDateForTradeTestMinium != null) {
					companyLearnersTradeTest.setLearnerReadinessDate(readyDateForTradeTestMinium);
				}
				if (readyDateForTradeTestMinium != null) {
					companyLearnersTradeTest.setMinDate(readyDateForTradeTestMinium);
				}
				if (readyDateForTradeTestMax != null) {
					companyLearnersTradeTest.setMaxDate(readyDateForTradeTestMax);
				}
				
				displayLastInfo = false;
				avalibleTradeTestCenters = companyQualificationsService.countCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(companyLearnersTradeTest.getQualification(), true, getNow(), AccreditationApplicationTypeEnum.QCTOTradeTestCentre);
				runClientSideExecute("PF('dlgTradeTestVTwo').show()");
			} else {
				addWarningMessage("Unable to locate qualification, contact support!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void setDesigantedTradeLevel(){
		try {
			
			if (selectedDesignatedTradeLevel == null) {
				throw new Exception("Unable to locate level selected. Contact Support!");
			}
			
			companyLearnersTradeTest.setDesignatedTradeLevel(selectedDesignatedTradeLevel);
//			CompanyLearnersTradeTest latestCompanyLearnersTradeTest = companyLearnersTradeTestService.findLastByTradeTypeLearnerQualification(TradeTestTypeEnum.TRADE_TEST, companyLearners.getUser().getId(), companyLearners.getQualification().getId());
			CompanyLearnersTradeTest latestCompanyLearnersTradeTest = companyLearnersTradeTestService.findLastByTradeTypeLearnerQualificationAndDesignatedTradeLevel(TradeTestTypeEnum.TRADE_TEST, companyLearners.getUser().getId(), companyLearners.getQualification().getId(),selectedDesignatedTradeLevel.getId());
			if (latestCompanyLearnersTradeTest !=null && latestCompanyLearnersTradeTest.getCompetenceEnum() == CompetenceEnum.NotYetCompetent  && latestCompanyLearnersTradeTest.getStatus() == ApprovalEnum.Completed) {	
				// sets to designated trade level of the previously failed test and can not re-select 
				companyLearnersTradeTest.setDesignatedTradeLevel(latestCompanyLearnersTradeTest.getDesignatedTradeLevel());	
				companyLearnersTradeTest.setPreviousAttemptDate(latestCompanyLearnersTradeTest.getDateOfTest());
				companyLearnersTradeTest.setPreviousTrainingCenter(latestCompanyLearnersTradeTest.getPreferredTrainingCenter());
				companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.Yes);
				companyLearnersTradeTest.setAttemptNumber(latestCompanyLearnersTradeTest.getAttemptNumber() + 1);
				readyDateForTradeTestMinium = companyLearnersService.calculateMinDateForCbmt(companyLearnersTradeTest, companyLearners);
				readyDateForTradeTestMax = companyLearnersService.calculateMaxDateForCbmt(companyLearnersTradeTest, companyLearners);
			} else {
				companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.No);
				companyLearnersTradeTest.setAttemptNumber(1);
			}
			
			Date readyDateForTradeTestMiniumChange = companyLearnersService.calculateMinDateForCbmt(companyLearnersTradeTest, companyLearnersTradeTest.getCompanyLearners());
			Date readyDateForTradeTestMaxChange = companyLearnersService.calculateMaxDateForCbmt(companyLearnersTradeTest, companyLearnersTradeTest.getCompanyLearners());
			if (readyDateForTradeTestMiniumChange != null) {
				companyLearnersTradeTest.setLearnerReadinessDate(readyDateForTradeTestMiniumChange);
			}
			if (readyDateForTradeTestMiniumChange != null) {
				companyLearnersTradeTest.setMinDate(readyDateForTradeTestMiniumChange);
			}
			if (readyDateForTradeTestMaxChange != null) {
				companyLearnersTradeTest.setMaxDate(readyDateForTradeTestMaxChange);
			}
			
			readyDateForTradeTestMiniumChange = null;
			readyDateForTradeTestMaxChange = null;
			selectedDesignatedTradeLevel = null;
			
			addInfoMessage("Change Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<Company> findCompaniesAssignedToQualification(String desc){
		List<Company> l = null;
		try {
			if (companyLearnersTradeTest != null && companyLearnersTradeTest.getQualification() != null) {
				l = companyQualificationsService.findCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(desc, companyLearnersTradeTest.getQualification(), true, getNow(), AccreditationApplicationTypeEnum.QCTOTradeTestCentre);
			} else {
				throw new Exception("Unable to locate qualification selected for test center selection. Contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return  l;
	}
	
	public List<TrainingProviderApplication> findProviderApplicationsAssignedToQualification(String desc){
		List<TrainingProviderApplication> l = null;
		try {
			if (companyLearnersTradeTest != null && companyLearnersTradeTest.getQualification() != null) {
				l = companyQualificationsService.findTrainingProviderApllicationsByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(desc, companyLearnersTradeTest.getQualification(), true, getNow(), AccreditationApplicationTypeEnum.QCTOTradeTestCentre);
			} else {
				throw new Exception("Unable to locate qualification selected for test center selection. Contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return  l;
	}
	
	
	
	public void checkRegion() {
		List<Users> qualityAssuror = new ArrayList<>();
		displayLastInfo = true;
		try {
			qualityAssuror = companyLearnersTradeTestService.findRegionQualityAssuror(companyLearnersTradeTest.getPreferredTrainingCenter());
			if (qualityAssuror.size() == 0) {
				displayLastInfo = false;
				addWarningMessage("MerSeta has not yet allocated a Quality Assuror to the region of " + companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void setTradeTestCenterAndRegionCheck(){
		List<Users> qualityAssuror = new ArrayList<>();
		displayLastInfo = true;
		
		// set TTC
		companyLearnersTradeTest.setPreferredTrainingCenter(companyLearnersTradeTest.getTrainingProviderApplication().getCompany());
			
		try {	
			List<TrainingProviderApplication> appList = companyQualificationsService.findTrainingProviderApllicationsByCompanyIdAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(companyLearnersTradeTest.getPreferredTrainingCenter().getId(), companyLearnersTradeTest.getQualification(), true, getNow(), AccreditationApplicationTypeEnum.QCTOTradeTestCentre);
			if (appList.isEmpty()) {
				throw new Exception("Unable to locate trade test center accrediciation. Contact support!");
			} else {
				companyLearnersTradeTest.setTrainingProviderApplication(appList.get(0));
			}
			// resolve company address
			companyService.resolveCompanyAddresses(companyLearnersTradeTest.getPreferredTrainingCenter());
			
			qualityAssuror = companyLearnersTradeTestService.findRegionQualityAssuror(companyLearnersTradeTest.getPreferredTrainingCenter());
			if (qualityAssuror.size() == 0) {
				displayLastInfo = false;
				addWarningMessage("MerSeta has not yet allocated a Quality Assuror to the region of " + companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName());
			}
		} catch (Exception e) {
			companyLearnersTradeTest.setPreferredTrainingCenter(null);
			displayLastInfo = false;
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepTrainingProviderVerfication() {
		try {
			trainingProviderMonitoring = new TrainingProviderVerfication();
			trainingProviderMonitoring.setTrainingProvider(selectedCompany);
			trainingProviderMonitoring.setCompanyLearners(companyLearners);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepNonMersetaQual() {
		nonSetaQualificationsCompletion = new NonSetaQualificationsCompletion();
		nonSetaQualificationsCompletion.setCompany(selectedCompany);
		nonSetaQualificationsCompletion.setCompanyLearners(companyLearners);
	}
	
	public void prepCompletionLetter() {
		completionLetter = new CompletionLetter();
		completionLetter.setCompany(selectedCompany);
		completionLetter.setCompanyLearners(companyLearners);
	}

	public void requestTransfer() {
		try {
			
			if(companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.ProviderTransfer) {
				if(companyLearnersTransfer.getTransferTrainingProviderApplication() == null) {
					throw new Exception("Please provide tarining provider");
				}else {
					if(companyLearners.getTrainingProviderApplication() != null && companyLearnersTransfer.getTransferTrainingProviderApplication().getId() == companyLearners.getTrainingProviderApplication().getId()) {
						throw new Exception("Please use a different training provider");
					}
					companyLearnersTransfer.setTransferToCompany(companyLearnersTransfer.getTransferTrainingProviderApplication().getCompany());
				}
			}
			checkRequiredFields();
			companyLearnersService.requestTransfer(companyLearnersTransfer, getSessionUI().getActiveUser());
			runClientSideExecute("PF('dlgTransferLearner').hide();");
			runClientSideExecute("uploadDone()");
			addInfoMessage("Transfer Requested");
		} catch (Exception e) {
			runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void checkRequiredFields() throws Exception
	{
		if(companyLearnersTransfer.getLearnerTransferType() ==null){
			throw new Exception("Please Specify which entity is requesting this transfer");
		}
		else if(companyLearnersTransfer.getTransferRequestType() ==null){
			throw new Exception("Please select Transfer Request Type");
		}
		else if(companyLearnersTransfer.getTransferToCompany() ==null){
			throw new Exception("Please select company");
		}	
		else if(companyLearnersTransfer.getTransferReason() ==null)
		{
			throw new Exception("Please provide transfer reason");
		}
	}
	
	public void prepLostTime() {
		companyLearnersLostTime = new CompanyLearnersLostTime();
		companyLearnersLostTime.setCompanyLearners(companyLearners);
		if(selectedTrainingProvider !=null){
			companyLearnersLostTime.setTrainingProviderApplication(selectedTrainingProvider);
		}
		if (companyLearners.getCompletionDate() != null) {
			companyLearnersLostTime.setOrginalEndDate(companyLearners.getCompletionDate());
		}
		if(getSessionUI().isEmployee()) {
			companyLearnersLostTime.setCreatedByEnum(CreatedByEnum.merSETA);
		}else {
			if(selectedTrainingProvider !=null && getSessionUI().isTrainingProvider()) {
				companyLearnersLostTime.setCreatedByEnum(CreatedByEnum.sdp);
			}else {
				companyLearnersLostTime.setCreatedByEnum(CreatedByEnum.sdf);
			}
		}
	}
	
	public void applyLosTimeData(){
		try {
			if (companyLearnersLostTime.getLostTimeReason() != null) {
				if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.ExtensionOfAgreement) {
					if (companyLearnersLostTimeService == null) {
						companyLearnersLostTimeService = new CompanyLearnersLostTimeService();
					}
					if (companyLearnersLostTimeService.countLostTimeByCompanyLearnerTypeAndStatus(companyLearners.getId(), LostTimeReason.ExtensionOfAgreement, ApprovalEnum.Approved) == 0) {
						// check if a learner is doing a designated trad
						if (designatedTradeLevelService == null) {
							designatedTradeLevelService = new DesignatedTradeLevelService();
						}
						if (companyLearners.getQualification() != null && designatedTradeLevelService.determainQualificationDesignatedTrade(companyLearners.getQualification())) {
							companyLearnersService.validateIfLearnerCanApplyLostTime(companyLearners);
							clearDesignatedTradeLostTimeInfo();
							companyLearnersLostTime.setDesignatedTradeExtension(true);
						} else {
							clearDesignatedTradeLostTimeInfo();
						}
					} else {
						clearDesignatedTradeLostTimeInfo();
						addErrorMessage("Unable to apply again for: " +LostTimeReason.ExtensionOfAgreement.getFriendlyName() + ". Agreement already has been approved for: "+LostTimeReason.ExtensionOfAgreement.getFriendlyName() + ".");
					}
				} else {
					clearDesignatedTradeLostTimeInfo();
				}
			}else {
				clearDesignatedTradeLostTimeInfo();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			companyLearnersLostTime.setLostTimeReason(null);
			clearDesignatedTradeLostTimeInfo();
		}
	}
	
	private void clearDesignatedTradeLostTimeInfo(){
		companyLearnersLostTime.setDesignatedTradeExtension(false);
		companyLearnersLostTime.setDesignatedTrade(null);
		companyLearnersLostTime.setDesignatedTradeLevel(null);
		companyLearnersLostTime.setWeeksAssigned(null);
		companyLearnersLostTime.setCalculatedEndDate(null);
	}

	public void requestLostTime() {
		try {
			
			if(companyLearnersLostTime.getLostTimeReason()==null){throw new Exception("Select reason for lost time");}
			if(companyLearnersLostTime.getLostTimeStartDate()==null){throw new Exception("Select the date lost time started");}
			if(companyLearnersLostTime.getLostTimeEndDate() ==null){throw new Exception("Select the date lost time ended");}
			
			if(companyLearnersLostTime.getCompanyLearners() !=null){
				if(companyLearnersLostTime.getCompanyLearners().getInterventionType() !=null){
					if(companyLearnersLostTime.getCompanyLearners().getInterventionType().getExtensionRequest() !=null){
						if(!companyLearnersLostTime.getCompanyLearners().getInterventionType().getExtensionRequest()){
							throw new Exception("Extensions cannot be granted for "+companyLearnersLostTime.getCompanyLearners().getInterventionType().getDescription());
						}
					}
				}
			}
			companyLearnersService.requestLostTime(companyLearnersLostTime, getSessionUI().getActiveUser());
			runClientSideExecute("uploadDone()");
			runClientSideExecute("PF('dlgLostTime').hide()");
			addInfoMessage("Lost Time Requested");
		} catch (Exception e) {
			runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void requestTermination() {
		try {
			if(companyLearnersTermination.getLastWorkingDayOfLearner()==null){throw new Exception("Select Last Working Day Of Learner");}
			if(companyLearnersTermination.getTerminationTypeEnum()==null){throw new Exception("Select Termination Type");}
			companyLearnersTermination.setCreateUser(getSessionUI().getActiveUser());
			companyLearnersService.requestTermination(companyLearnersTermination, getSessionUI().getActiveUser());
			addInfoMessage("Learner Termination Requested");
			runClientSideExecute("uploadDone()");
			runClientSideExecute("PF('dlgTermination').hide()");
		} catch (Exception e) {
			runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void requestWithdrawal() {
		try {
			if(withdrawRejectReason.size() == 0) {
				throw new Exception("Please select withrawal reasons");
			}
			List<Tasks>tasks = TasksService.instance().findTasksByTypeAndKey(ConfigDocProcessEnum.Learner, companyLearners.getClass().getName(), companyLearners.getId());
			if(tasks!= null && tasks.size()>0) {
				for(Tasks t:tasks) {
					if(t.getTaskStatus() != TaskStatusEnum.Closed) {
						t.setActionDate(getNow());
						t.setCompletionDate(getNow());
						t.setTaskStatus(TaskStatusEnum.Closed);
						TasksService.instance().update(t);
					}
				}
				this.companyLearners.setLearnerStatus(LearnerStatusEnum.Withdrawn);
				this.companyLearners.setStatus(ApprovalEnum.Withdrawn);
				companyLearnersService.update(companyLearners);
				WithdrawReasonsService withdrawReasonsService = new WithdrawReasonsService();
				withdrawReasonsService.createWithrawalReasons(companyLearners, withdrawRejectReason, getSessionUI().getActiveUser());
				runClientSideExecute("PF('dlgLearnerApplicationWithdrawal').hide()");
			}else {
				this.companyLearners.setLearnerStatus(LearnerStatusEnum.Withdrawn);
				this.companyLearners.setStatus(ApprovalEnum.Withdrawn);
				companyLearnersService.update(companyLearners);
				WithdrawReasonsService withdrawReasonsService = new WithdrawReasonsService();
				System.out.println("111111111:: withdrawRejectReason.size():: "+withdrawRejectReason.size());
				withdrawReasonsService.createWithrawalReasons(companyLearners, withdrawRejectReason, getSessionUI().getActiveUser());
				addInfoMessage("Learner withdrawn");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void requestChange() {
		try {			
			companyLearnersService.validateLearnerChange(companyLearnersChange);
			Boolean contue=companyLearnersService.workplaceApprovalAccreditationChecks(companyLearnersChange,  getSessionUI().getActiveUser());	
			if(!contue){
				
				super.runClientSideExecute("uploadDone()");
				super.runClientSideExecute("PF('dlgChange').hide()");
				workplaceApprovalMsg = "The company selected does not have workplace approval for " + companyLearnersService.getCompanyLearnerStringQualification(companyLearnersChange) 
										+ ". The change in qualification can only be initiated if workplace approval has been granted.";
				throw new Exception(workplaceApprovalMsg);
				//super.runClientSideExecute("PF('wpaCompanyForQualDialog').show()");
				//super.runClientSideUpdate("wpaForms1");
			}
			Boolean accredited = companyLearnersService.tpAccreditationChecks(companyLearnersChange);
			if(!accredited){
				throw new Exception("Please note that you are not accredited for this the selected programme. You may apply for accreditation or transfer the learner to an accredited provider.");
			}
			/*String error = companyLearnersService.tPAccreditedChecks(companyLearnersChange);
			if (error.length() > 0 || !error.matches("")) {
				throw new Exception(error);
			}*/
			
			companyLearnersService.requestChange(companyLearnersChange, getSessionUI().getActiveUser());
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('dlgChange').hide()");
			
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public List<Qualification> completeUnitStandartQualification(String desc) {
		SaqaUsQualificationService saqaUsQualificationService = new SaqaUsQualificationService();
		List<Qualification> l = null;
		try {
			l = saqaUsQualificationService.findSaqaUsQualificationByUnitStandardId(companyLearnersChange.getUnitStandard().getCode());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void createWorkplaceApproval()
	{
		try {
			companyLearnersService.initiateWorkplaceApproval(companyLearnersChange, getSessionUI().getActiveUser());
			cancelWorkplaceApproval();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void cancelWorkplaceApproval() {
		try {
			super.runClientSideExecute("PF('wpaCompanyForQualDialog').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<SelectItem> getLearnerChangeTypeEnumDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		for (LearnerChangeTypeEnum val : LearnerChangeTypeEnum.values()) { 
			if(val==LearnerChangeTypeEnum.ChangeOfProgramQalification)
			{
				if(companyLearners.getInterventionType().getInterventionTypeEnum() != InterventionTypeEnum.Apprenticeship &&
				!companyLearners.getInterventionType().getId().equals(HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE))
				{
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			}
			else if(val==LearnerChangeTypeEnum.ChangeOfProgramTrade)
			{
				if(companyLearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship
				&& !companyLearners.getInterventionType().getId().equals(HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE))
				{
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			}
			else if(val==LearnerChangeTypeEnum.ChangeNonCreditBearingTitle)
			{
				if(companyLearners.getInterventionType().getId().equals(HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE))
				{
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			}
			/*else
			{
				l.add(new SelectItem(val, val.getFriendlyName()));
			}*/
		}
		return l;
	}
	
	/*public List<SelectItem> getLearnerChangeTypeEnumDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		for (LearnerChangeTypeEnum val : LearnerChangeTypeEnum.values()) { 
			if(val==LearnerChangeTypeEnum.ChangeOfProgramQalification)
			{
				if(companyLearners.getQualification() !=null && 
				(companyLearners.getQualification().getQualificationtypeid() ==null || !companyLearners.getQualification().getQualificationtypeid().equals(HAJConstants.TRADE_QUALIFICATION_CODE)))
				{
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			}
			else if(val==LearnerChangeTypeEnum.ChangeOfProgramTrade)
			{
				if(companyLearners.getQualification() !=null && 
				(companyLearners.getQualification().getQualificationtypeid() !=null 
				&& companyLearners.getQualification().getQualificationtypeid().equals(HAJConstants.TRADE_QUALIFICATION_CODE) && (companyLearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) ))
				{
					l.add(new SelectItem(val, val.getFriendlyName()));
				}
			}
			else
			{
				l.add(new SelectItem(val, val.getFriendlyName()));
			}
		}
		return l;
	}*/

	public void requestTradeTestApplication() {
		try {
			companyLearnersService.validiationOnTradeTestRequest(companyLearnersTradeTest);
			companyLearnersTradeTest.setCreateUser(getSessionUI().getActiveUser());
			companyLearnersService.requestTradeTestApplication(companyLearnersTradeTest, getSessionUI().getActiveUser());
			addInfoMessage("Trade Test Application Submitted");
			runClientSideExecute("uploadDone()");
			runClientSideExecute("PF('dlgTradeTestVTwo').hide()");
		} catch (Exception e) {
			runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void requestARPLTradeTestApplication() {
		try {
			companyLearnersService.requestARPLTradeTestApplication(companyLearnersTradeTest, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void requestVerification() {
		try {
			companyLearnersService.requestVerification(trainingProviderMonitoring, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void requestVerificationLearner() {
		try {
			if(trainingProviderMonitoring.getCompanyLearners() == null) {
				throw new Exception("There is an error with the learner application!!! Please contact your administrator");
			}
			if(trainingProviderVerficationService.findByCompanyLearner(trainingProviderMonitoring.getCompanyLearners()).size() > 0){
				throw new Exception("Already applied for verification");
			}
			if(trainingProviderMonitoring.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
				trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
			}else if(SKILLS_PROGRAM_LIST.contains(trainingProviderMonitoring.getCompanyLearners().getInterventionType().getId())) {
				trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
			}else if(SKILLS_SET_LIST.contains(trainingProviderMonitoring.getCompanyLearners().getInterventionType().getId())) {
				trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
			}else {
				trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.GenerateCertificate);
			}
			trainingProviderMonitoring.setCreatedByMerseta(false);
			companyLearnersService.requestVerificationLearner(trainingProviderMonitoring, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void requestDocumentsChange() {
		try {
			if(dateChangeReasonSelectionList.size() == 0) {
				throw new Exception("Please select atleast one change reason");
			}
			companyLearnersService.requestDocumentsChange(companyLearners, getSessionUI().getActiveUser(), dateChangeReasonSelectionList,ConfigDocProcessEnum.LearnerRegistrationDocuments);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
		
	public void requestVerificationLearnerByEmployee() {
		try {
			if(trainingProviderMonitoring.getTrainingProvider() == null) {
				throw new Exception("There is an error with the selected learner application!!! Please contact your administrator");
			}
			if(trainingProviderMonitoring.getCompanyLearners() == null) {
				throw new Exception("There is an error with the learner application!!! Please contact your administrator");
			}
			if(trainingProviderVerficationService.findByCompanyLearner(trainingProviderMonitoring.getCompanyLearners()).size() > 0){
				throw new Exception("Already applied for verification");
			}
			
			if(trainingProviderMonitoring.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
				trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
			}else if(SKILLS_PROGRAM_LIST.contains(trainingProviderMonitoring.getCompanyLearners().getInterventionType().getId())) {
				trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
			}else if(SKILLS_SET_LIST.contains(trainingProviderMonitoring.getCompanyLearners().getInterventionType().getId())) {
				trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
			}else {
				trainingProviderMonitoring.setGenerateAddEnum(GenerateAddEnum.GenerateCertificate);
			}
			
			trainingProviderMonitoring.setCreatedByMerseta(true);
			companyLearnersService.requestVerificationLearner(trainingProviderMonitoring, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void requestNonSetaQualificationsCompletion() {
		try {
			if(nonSetaQualificationsCompletionService.findByCompanyLearner(nonSetaQualificationsCompletion.getCompanyLearners()).size() > 0) {
				throw new Exception("Already applied for non seta qualifications completion");
			}
			nonSetaQualificationsCompletionService.requestNonSetaQualificationsCompletion(nonSetaQualificationsCompletion, getSessionUI().getActiveUser());
			CompanyLearners cl = nonSetaQualificationsCompletion.getCompanyLearners();
			cl.setLearnerStatus(LearnerStatusEnum.PendingNonMerSetaQualificationApproval);
			companyLearnersService.update(cl);
			addInfoMessage("Your application has been submitted");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void requestCompletionLetter() {
		try {
			//nonSetaQualificationsCompletionService.
			if(completionLetterService.findByCompanyLearner(completionLetter.getCompanyLearners()).size() > 0) {
				throw new Exception("Already applied for completion letter");
			}
			completionLetterService.requestCompletionLetter(completionLetter, getSessionUI().getActiveUser());
			CompanyLearners cl = completionLetter.getCompanyLearners();
			cl.setLearnerStatus(LearnerStatusEnum.CompletionLetterApproval);
			companyLearnersService.update(cl);
			addInfoMessage("Your application has been submitted");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void downloadApplication() {

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", companyLearners.getId());
			params.put("company_id", companyLearners.getEmployer().getId());
			params.put("call_center_number", "011111111");
			params.put("period", "2019");
			params.put("employer", companyLearners.getEmployer().getCompanyName());
			JasperService.addLogo(params);

			JasperService.instance().createReportFromDBtoServletOutputStream("LPM-FM-011-Request-for Extension-of-Termination-Date-of-Learnership.jasper", params, "Application_Form.pdf");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);e.printStackTrace();
		}
	}
	
	public void calculateEndDate() {
		if(companyLearnersChange.getCommencmentDate() != null && companyLearnersChange.getCompanyLearners().getInterventionType().getDuration()!= null) {
			this.companyLearnersChange.setCompletionDate(GenericUtility.addMonthsToDate(this.companyLearnersChange.getCommencmentDate(), this.companyLearnersChange.getCompanyLearners().getInterventionType().getDuration()));
		}
	}

	public void downloadLearnerAgreementForm() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js = new JasperService();
		try {
			UsersService usersService = new UsersService();
			
			CompanyUsersService companyUsersService=new CompanyUsersService();
			
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");

			

			Users learner = usersService.findByKey(companyLearners.getUser().getId());
			params.put("isMinor", checkRequireGaurdian(learner));
			if(checkRequireGaurdian(learner)){
				if(learner.getLegalGaurdian().getId() !=null){
					Users legalGaurdian = usersService.findByKey(learner.getLegalGaurdian().getId());
					params.put("legalGaurdian", legalGaurdian);
				}
				else{
					params.put("legalGaurdian", new Users());
				}
				
			}
			
			Company employer = companyService.findByKey(companyLearners.getEmployer().getId());
			Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());
			Company skillDevProvider = companyService.findByKey(companyLearners.getCompany().getId());
			Users skillDevProviderContactPerson = companyUsersService.findCompanyContactPerson(skillDevProvider.getId());

			params.put("learner", learner);
			params.put("employer", employer);
			params.put("employer_contact_person", employerContactPerson);
			params.put("skill_dev_provider", skillDevProvider);
			params.put("skill_dev_provider_contact_person", skillDevProviderContactPerson);

			params.put("have_bullet", true);

			js.createReportFromDBtoServletOutputStream("LPM-FM-002-LearnershipAgreement(ExcludingSkillsProgrammes).jasper", params, "AgreementForm.pdf");

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public void checkIfTradeTestCenterIsAccredited()
	{

		try {
			if (CompanyQualificationsService.instance().countfindByCompany(companyLearnersTradeTest.getPreferredTrainingCenter(), companyLearners.getQualification(), true) == 0) {
				
				companyLearnersTradeTest.setPreferredTrainingCenter(null);
				qualAccredetedCompaniesInfo();
				super.runClientSideExecute("PF('accreCompanyForQualDialog').show()");
				accreditedQuallMssg="The company selected is not accredited for " + companyLearners.getQualification().getDescription()+". Select one of the company that is accredited below";
				//throw new Exception("Selected company is not accredited for Qualification: " + companyLearners.getQualification().getDescription());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public void qualAccredetedCompaniesInfo() {
		 
		CompanyQualificationsService companyQualificationsService=new CompanyQualificationsService();
		qualAccredetedCompanyDataModel = new LazyDataModel<CompanyQualifications>() { 
		 
		   private static final long serialVersionUID = 1L; 
		   private List<CompanyQualifications> retorno = new  ArrayList<CompanyQualifications>();
		   
		   @Override 
		   public List<CompanyQualifications> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
		   
			try {
				
				filters.put("qualID", companyLearners.getQualification().getId());
				retorno = companyQualificationsService.allAcceptedCompanyQualifications(first, pageSize, sortField, sortOrder, filters);
				qualAccredetedCompanyDataModel.setRowCount(companyQualificationsService.countAcceptedCompanies(filters));
			} catch (Exception e) {
				logger.fatal(e);
			} 
		    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(CompanyQualifications obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public CompanyQualifications getRowData(String rowKey) {
		        for(CompanyQualifications obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
		
		

}
	
	public void prepPreferredTrainingCenter()
	{
		companyLearnersTradeTest.setPreferredTrainingCenter(selectedCompanyQualification.getCompany());
	}
	
	public List<SelectItem> getTerminationTypeEnumNotSystemDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(TerminationTypeEnum.MutualSidedTermination, TerminationTypeEnum.MutualSidedTermination.getFriendlyName()));
		if(this.companyLearners.getInterventionType().getOnesidedtermination()) {
			l.add(new SelectItem(TerminationTypeEnum.OneSidedTermination, TerminationTypeEnum.OneSidedTermination.getFriendlyName()));
		}
		l.add(new SelectItem(TerminationTypeEnum.DeceasedLearnerTermination, TerminationTypeEnum.DeceasedLearnerTermination.getFriendlyName()));
		return l;
	}

	
	public List<Company> completeCompany(String desc) {
		CompanyService equityService = new CompanyService();
		List<Company> l = null;
		try {
			l = equityService.findByNameOrLevyNum(desc, companyLearnersService.getCompanyLearnerQualification(companyLearners), ApprovalEnum.Approved);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<TrainingProviderApplication> completeTrainingProvidersAccreditationSearch(String desc) {
		
		List<TrainingProviderApplication> l = null;
		try {
			if(SKILLS_PROGRAM_LIST.contains(companyLearners.getInterventionType().getId())) {
				TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService = new TrainingProviderSkillsProgrammeService();
				l = trainingProviderSkillsProgrammeService.allTrainingProviderApplicationBySP(companyLearners.getSkillsProgram().getId(), true, ApprovalEnum.Approved);
			}else if(SKILLS_SET_LIST.contains(companyLearners.getInterventionType().getId())) {
				TrainingProviderSkillsSetService trainingProviderSkillsSetService = new TrainingProviderSkillsSetService();		
				l = trainingProviderSkillsSetService.allTrainingProviderApplicationBySS(companyLearners.getSkillsSet().getId(), true, ApprovalEnum.Approved);
			}else if(companyLearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
				TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
				if(companyLearners.getQualification()!=null) {
					l = trainingProviderApplicationService.allTrainingProviderApplicationQualifications(companyLearners.getQualification().getId(), true, ApprovalEnum.Approved);
				}
				/*if(learningProgramme !=null && learningProgramme.getId() !=null) {
					retorno = trainingProviderApplicationService.allTrainingProviderApplicationLearningProgramme(first, pageSize, sortField, sortOrder, filters, learningProgramme.getId(), selectedQualification.getId(), companylearners.getUnitStandard().getId(),ApprovalEnum.Approved, true);						
					trainingProviderApplicationModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationLearningProgramme(filters));
				}else if(selectedQualification !=null && selectedQualification.getId() !=null) {
					retorno = trainingProviderApplicationService.allTrainingProviderApplicationQualification(first, pageSize, sortField, sortOrder, filters, selectedQualification.getId(),companylearners.getUnitStandard().getId(), ApprovalEnum.Approved, true);						
					trainingProviderApplicationModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationQualification(filters));
				}else {
					retorno = trainingProviderApplicationService.allTrainingProviderApplicationCompanyUnitStandard(first, pageSize, sortField, sortOrder, filters, companylearners.getUnitStandard().getId(), ApprovalEnum.Approved);						
					trainingProviderApplicationModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationCompanyUnitStandard(filters));
				}*/
			}else if(companyLearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
				TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
				l = trainingProviderApplicationService.allTrainingProviderApplicationQualifications(companyLearners.getLearnership().getQualification().getId(), true, ApprovalEnum.Approved);
			}else if(companyLearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
				TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
				l = trainingProviderApplicationService.allTrainingProviderApplicationQualifications(companyLearners.getQualification().getId(), true, ApprovalEnum.Approved);
			}else if(companyLearners.getQualification() != null) {
				TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
				l = trainingProviderApplicationService.allTrainingProviderApplicationQualifications(companyLearners.getQualification().getId(), true, ApprovalEnum.Approved);			
			}/*else {
				l = trainingProviderApplicationService.allTrainingProviderApplicationQualifications(companyLearners.getQualification().getId(), true);
				l = trainingProviderApplicationService.locateTrainingProviderApplicationByStatusDateAndAccreSearch(ApprovalEnum.Approved, getNow(),desc);
			}*/
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<SelectItem> getLearnerTransferTypeEnumDD() {
		List<SelectItem> l = new ArrayList<>();
		l.add(new SelectItem(LearnerTransferTypeEnum.WorkplaceTransfer, LearnerTransferTypeEnum.WorkplaceTransfer.getFriendlyName()));
		if(companyLearners.getInterventionType().getForSdpAccreditaion()) {
			l.add(new SelectItem(LearnerTransferTypeEnum.ProviderTransfer, LearnerTransferTypeEnum.ProviderTransfer.getFriendlyName()));
		}/*else {
			companyLearnersTransfer.setLearnerTransferType(LearnerTransferTypeEnum.WorkplaceTransfer);
		}*/
		/*for (LearnerTransferTypeEnum val : LearnerTransferTypeEnum.values()) {
			l.add(new SelectItem(val, val.getFriendlyName()));
		}*/
		return l;
	}
	
	public void prepVisitDateOnWorkplaceApprovalChange() {
		try {
			dateChangeReasonSelectionList = new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepVisitDateOnWorkplaceApproval() {
		try {
			dateChangeReasonSelectionList = new ArrayList<>();
			dateChangeReasonAvalibleSelectionList = DateChangeReasonsService.instance().findByProcess(ConfigDocProcessEnum.LearnerRegistrationDocuments);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void onRowToggler(CompanyLearners companyLearners) {
		try {
			if(companyLearners.getEmployer() != null && companyLearners.getEmployer().getPostalAddress() != null) {
				companyLearners.getEmployer().setPostalAddress(AddressService.instance().findByKey(companyLearners.getEmployer().getPostalAddress().getId()));
				companyLearners.getEmployer().setContactPerson(companyUsersService.findCompanyContactPerson(companyLearners.getEmployer().getId()));
			}
			companyLearners.getEmployer().setContactPerson(companyUsersService.findCompanyContactPerson(companyLearners.getEmployer().getId()));
			companyLearners.setCompany(companyService.findByKey(companyLearners.getCompany().getId()));
			if(companyLearners.getUser() != null) {
				companyLearners.getUser().setUsersLanguageList(usersLanguageService.findByUser(companyLearners.getUser()));
				if(companyLearners.getUser().getDisability() == YesNoEnum.Yes) {
					companyLearners.getUser().setUsersDisabilityList(usersDisabilityService.findByKeyUser(companyLearners.getUser()));
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public List<WithdrawReasons> getWithdrawReasons() {
		WithdrawReasonsService withdrawReasonsService = new WithdrawReasonsService();
		List<WithdrawReasons>  l = null;
		try {
			l = withdrawReasonsService.findByProcess(ConfigDocProcessEnum.Learner);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void populateCompanyLearner(CompanyLearnersChange companylearnerschange) {	
		try {
			companylearnerschange.setCompanyLearners(companyLearnersService.findByKey(companylearnerschange.getCompanyLearners().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateCompanyLearner(CompanyLearnersTermination companylearnerstermination) {	
		try {
			companylearnerstermination.setCompanyLearners(companyLearnersService.findByKey(companylearnerstermination.getCompanyLearners().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateCompanyLearner(CompanyLearnersLostTime companylearnerslosttime) {	
		try {
			companylearnerslosttime.setCompanyLearners(companyLearnersService.findByKey(companylearnerslosttime.getCompanyLearners().getId()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadForm(CompanyLearnersChange companylearnerschange) {	
		try {
			if(companylearnerschange != null) {
				CompanyLearnersChangeService changeService = new CompanyLearnersChangeService();
				companylearnerschange.setCompanyLearners(companyLearnersService.findByKey(companylearnerschange.getCompanyLearners().getId()));
				if (companylearnerschange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeOfProgramQalification) {
					changeService.downloadLPMAD005(companylearnerschange);
				} else if (companylearnerschange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeOfProgramTrade) {
					changeService.downloadLPMAD010(companylearnerschange);
				} else if (companylearnerschange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeCommencementDate) {
					changeService.downloadLPMAD001(companylearnerschange);
				} else if (companylearnerschange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeNonCreditBearingTitle) {
					changeService.downloadLPMAD005NonCreditBearingEmail(companylearnerschange);
				}
			}else {
				throw new Exception("Error when downloading form");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}	
	}
	
	public void downloadTerminationForm(CompanyLearnersTermination companylearnerstermination) {	
		try {
			if(companylearnerstermination != null) {
				companylearnerstermination.setCompanyLearners(companyLearnersService.findByKey(companylearnerstermination.getCompanyLearners().getId()));
				CompanyLearnersTerminationService companyLearnersTerminationService = new CompanyLearnersTerminationService();
				if (companylearnerstermination.getTerminationTypeEnum() == TerminationTypeEnum.MutualSidedTermination) {
					companyLearnersTerminationService.downloadLPMFM016(companylearnerstermination);
				} else if (companylearnerstermination.getTerminationTypeEnum() == TerminationTypeEnum.OneSidedTermination || companylearnerstermination.getTerminationTypeEnum() == TerminationTypeEnum.DeceasedLearnerTermination) {
					companyLearnersTerminationService.downloadLPMFM017(companylearnerstermination);
				}
			}else {
				throw new Exception("Error when downloading form");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}	
	}
	
	public void downloadLPMAD001(CompanyLearnersLostTime companylearnerslosttime) {	
		try {
			if(companylearnerslosttime != null) {
				companylearnerslosttime.setCompanyLearners(companyLearnersService.findByKey(companylearnerslosttime.getCompanyLearners().getId()));
				CompanyLearnersLostTimeService companyLearnersLostTimeService=new CompanyLearnersLostTimeService();
				companyLearnersLostTimeService.downloadLPMAD001(companylearnerslosttime);
			}else {
				throw new Exception("Error when downloading form");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}	
	}
	
	public void storeFile(FileUploadEvent event) {
		// method added for jira 114
		
		System.out.println("in storeFile");
		System.out.println("doc.getId()----"+doc.getId());
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<Learners> getLearnersList() {
		return learnersList;
	}

	public void setLearnersList(List<Learners> learnersList) {
		this.learnersList = learnersList;
	}

	public Learners getLearners() {
		return learners;
	}

	public void setLearners(Learners learners) {
		this.learners = learners;
	}

	public List<Learners> getLearnersfilteredList() {
		return learnersfilteredList;
	}

	public void setLearnersfilteredList(List<Learners> learnersfilteredList) {
		this.learnersfilteredList = learnersfilteredList;
	}

	public LazyDataModel<CompanyLearners> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearners> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public CompanyLearnersTransfer getCompanyLearnersTransfer() {
		return companyLearnersTransfer;
	}

	public void setCompanyLearnersTransfer(CompanyLearnersTransfer companyLearnersTransfer) {
		this.companyLearnersTransfer = companyLearnersTransfer;
	}

	public CompanyLearnersLostTime getCompanyLearnersLostTime() {
		return companyLearnersLostTime;
	}

	public void setCompanyLearnersLostTime(CompanyLearnersLostTime companyLearnersLostTime) {
		this.companyLearnersLostTime = companyLearnersLostTime;
	}

	public CompanyLearnersTermination getCompanyLearnersTermination() {
		return companyLearnersTermination;
	}

	public void setCompanyLearnersTermination(CompanyLearnersTermination companyLearnersTermination) {
		this.companyLearnersTermination = companyLearnersTermination;
	}

	public CompanyLearnersChange getCompanyLearnersChange() {
		return companyLearnersChange;
	}

	public void setCompanyLearnersChange(CompanyLearnersChange companyLearnersChange) {
		this.companyLearnersChange = companyLearnersChange;
	}

	public LazyDataModel<AuditorMonitorReview> getAuditorMonitorReviewdataModel() {
		return auditorMonitorReviewdataModel;
	}

	public void setAuditorMonitorReviewdataModel(LazyDataModel<AuditorMonitorReview> auditorMonitorReviewdataModel) {
		this.auditorMonitorReviewdataModel = auditorMonitorReviewdataModel;
	}

	public CompanyLearnersTradeTest getCompanyLearnersTradeTest() {
		return companyLearnersTradeTest;
	}

	public void setCompanyLearnersTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest) {
		this.companyLearnersTradeTest = companyLearnersTradeTest;
	}

	public NonSetaQualificationsCompletion getNonSetaQualificationsCompletion() {
		return nonSetaQualificationsCompletion;
	}

	public void setNonSetaQualificationsCompletion(NonSetaQualificationsCompletion nonSetaQualificationsCompletion) {
		this.nonSetaQualificationsCompletion = nonSetaQualificationsCompletion;
	}
	
	public boolean checkRequireGaurdian(Users user) {
		boolean requireGaurdian = user.getDateOfBirth() != null && GenericUtility.getAge(user.getDateOfBirth()) < 18;
		
		return requireGaurdian;
		
	}

	public boolean isViewLearnerData() {
		return viewLearnerData;
	}

	public void setViewLearnerData(boolean viewLearnerData) {
		this.viewLearnerData = viewLearnerData;
	}

	public LazyDataModel<Users> getDataModelUsers() {
		return dataModelUsers;
	}

	public void setDataModelUsers(LazyDataModel<Users> dataModelUsers) {
		this.dataModelUsers = dataModelUsers;
	}

	public Users getLearner() {
		return learner;
	}

	public void setLearner(Users learner) {
		this.learner = learner;
	}

	public Users getSelectedLearner() {
		return selectedLearner;
	}

	public void setSelectedLearner(Users selectedLearner) {
		this.selectedLearner = selectedLearner;
	}

	public Date getReadyDateForTradeTestMinium() {
		return readyDateForTradeTestMinium;
	}

	public void setReadyDateForTradeTestMinium(Date readyDateForTradeTestMinium) {
		this.readyDateForTradeTestMinium = readyDateForTradeTestMinium;
	}

	public Date getReadyDateForTradeTestMax() {
		return readyDateForTradeTestMax;
	}

	public void setReadyDateForTradeTestMax(Date readyDateForTradeTestMax) {
		this.readyDateForTradeTestMax = readyDateForTradeTestMax;
	}

	public boolean isCbmtQualification() {
		return cbmtQualification;
	}

	public void setCbmtQualification(boolean cbmtQualification) {
		this.cbmtQualification = cbmtQualification;
	}

	public LazyDataModel<CompanyQualifications> getQualAccredetedCompanyDataModel() {
		return qualAccredetedCompanyDataModel;
	}

	public void setQualAccredetedCompanyDataModel(LazyDataModel<CompanyQualifications> qualAccredetedCompanyDataModel) {
		this.qualAccredetedCompanyDataModel = qualAccredetedCompanyDataModel;
	}

	public CompanyQualifications getSelectedCompanyQualification() {
		return selectedCompanyQualification;
	}

	public void setSelectedCompanyQualification(CompanyQualifications selectedCompanyQualification) {
		this.selectedCompanyQualification = selectedCompanyQualification;
	}

	public String getAccreditedQuallMssg() {
		return accreditedQuallMssg;
	}

	public void setAccreditedQuallMssg(String accreditedQuallMssg) {
		this.accreditedQuallMssg = accreditedQuallMssg;
	}

	public String getWorkplaceApprovalMsg() {
		return workplaceApprovalMsg;
	}

	public void setWorkplaceApprovalMsg(String workplaceApprovalMsg) {
		this.workplaceApprovalMsg = workplaceApprovalMsg;
	}

	public TrainingProviderApplication getSelectedTrainingProvider() {
		return selectedTrainingProvider;
	}

	public void setSelectedTrainingProvider(TrainingProviderApplication selectedTrainingProvider) {
		this.selectedTrainingProvider = selectedTrainingProvider;
	}

	public CompletionLetter getCompletionLetter() {
		return completionLetter;
	}

	public void setCompletionLetter(CompletionLetter completionLetter) {
		this.completionLetter = completionLetter;
	}

	public Boolean getLearnerRequest() {
		return learnerRequest;
	}

	public void setLearnerRequest(Boolean learnerRequest) {
		this.learnerRequest = learnerRequest;
	}

	public Boolean getPrimaryOrSecondarySDF() {
		return primaryOrSecondarySDF;
	}

	public void setPrimaryOrSecondarySDF(Boolean primaryOrSecondarySDF) {
		this.primaryOrSecondarySDF = primaryOrSecondarySDF;
	}

	public int getAvalibleTradeTestCenters() {
		return avalibleTradeTestCenters;
	}

	public void setAvalibleTradeTestCenters(int avalibleTradeTestCenters) {
		this.avalibleTradeTestCenters = avalibleTradeTestCenters;
	}

	public String getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(String validationErrors) {
		this.validationErrors = validationErrors;
	}

	public boolean isDisplayLastInfo() {
		return displayLastInfo;
	}

	public void setDisplayLastInfo(boolean displayLastInfo) {
		this.displayLastInfo = displayLastInfo;
	}

	public DesignatedTradeLevel getSelectedDesignatedTradeLevel() {
		return selectedDesignatedTradeLevel;
	}

	public void setSelectedDesignatedTradeLevel(DesignatedTradeLevel selectedDesignatedTradeLevel) {
		this.selectedDesignatedTradeLevel = selectedDesignatedTradeLevel;
	}

	public Boolean getSelectedNewDesignatedTradeLevel() {
		return selectedNewDesignatedTradeLevel;
	}

	public void setSelectedNewDesignatedTradeLevel(Boolean selectedNewDesignatedTradeLevel) {
		this.selectedNewDesignatedTradeLevel = selectedNewDesignatedTradeLevel;
	}

	public List<DesignatedTradeLevel> getDesignatedTradeLevelList() {
		return designatedTradeLevelList;
	}

	public void setDesignatedTradeLevelList(List<DesignatedTradeLevel> designatedTradeLevelList) {
		this.designatedTradeLevelList = designatedTradeLevelList;
	}

	public SdpType getSdpType() {
		return sdpType;
	}

	public void setSdpType(SdpType sdpType) {
		this.sdpType = sdpType;
	}

	public Boolean getRegisterLearners() {
		return registerLearners;
	}

	public void setRegisterLearners(Boolean registerLearners) {
		this.registerLearners = registerLearners;
	}

	public LazyDataModel<Tasks> getDataModelTasks() {
		return dataModelTasks;
	}

	public void setDataModelTasks(LazyDataModel<Tasks> dataModelTasks) {
		this.dataModelTasks = dataModelTasks;
	}

	public boolean isViewTaskData() {
		return viewTaskData;
	}

	public void setViewTaskData(boolean viewTaskData) {
		this.viewTaskData = viewTaskData;
	}

	public GenerateAddEnum getGenerateAddEnum() {
		return generateAddEnum;
	}

	public void setGenerateAddEnum(GenerateAddEnum generateAddEnum) {
		this.generateAddEnum = generateAddEnum;
	}

	public boolean isRequireWPA() {
		return requireWPA;
	}

	public void setRequireWPA(boolean requireWPA) {
		this.requireWPA = requireWPA;
	}

	public List<DateChangeReasons> getDateChangeReasonSelectionList() {
		return dateChangeReasonSelectionList;
	}

	public List<DateChangeReasons> getDateChangeReasonAvalibleSelectionList() {
		return dateChangeReasonAvalibleSelectionList;
	}

	public void setDateChangeReasonSelectionList(List<DateChangeReasons> dateChangeReasonSelectionList) {
		this.dateChangeReasonSelectionList = dateChangeReasonSelectionList;
	}

	public void setDateChangeReasonAvalibleSelectionList(List<DateChangeReasons> dateChangeReasonAvalibleSelectionList) {
		this.dateChangeReasonAvalibleSelectionList = dateChangeReasonAvalibleSelectionList;
	}

	public boolean isViewLearnerDetails() {
		return viewLearnerDetails;
	}

	public void setViewLearnerDetails(boolean viewLearnerDetails) {
		this.viewLearnerDetails = viewLearnerDetails;
	}

	public SDPCompany getSdpCompanyLink() {
		return sdpCompanyLink;
	}

	public void setSdpCompanyLink(SDPCompany sdpCompanyLink) {
		this.sdpCompanyLink = sdpCompanyLink;
	}

	public List<WithdrawReasons> getWithdrawRejectReason() {
		return withdrawRejectReason;
	}

	public void setWithdrawRejectReason(List<WithdrawReasons> withdrawRejectReason) {
		this.withdrawRejectReason = withdrawRejectReason;
	}

	public List<WithdrawReasons> getWithdrawReason() {
	    return withdrawReason;
	}

	public void setWithdrawReason(List<WithdrawReasons> withdrawReason) {
	    this.withdrawReason = withdrawReason;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}
	
	
}
