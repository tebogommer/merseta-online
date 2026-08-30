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

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.Learners;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AprlProgressEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerChangeTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.TerminationTypeEnum;
import haj.com.entity.enums.TradeTestProgressEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyLearnersTradeTestService;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.JasperService;
import haj.com.service.LearnersService;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.lookup.DesignatedTradeLevelService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "arplTradeTestLearnersReportUI")
@ViewScoped
public class ArplTradeTestLearnersReportUI extends AbstractUI {
	
	private LearnersService service = new LearnersService();
	private CompanyService companyService = new CompanyService();
	private List<Learners> learnersList = null;
	private List<Learners> learnersfilteredList = null;
	private CompanyLearners companyLearners = null;
	private CompanyLearnersTradeTest companyLearnersTradeTest;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private LazyDataModel<CompanyLearners> dataModel;
	private boolean viewLearnerData = false;
	private boolean viewTaskData = false;
	private boolean cbmtQualification = false;
	
	/* Service levels */
	private CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
	private CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
	private DesignatedTradeLevelService designatedTradeLevelService = new DesignatedTradeLevelService();
	private LazyDataModel<CompanyQualifications> qualAccredetedCompanyDataModel; 
	private CompanyQualifications selectedCompanyQualification;
	
	/* Entity */
	private DesignatedTradeLevel selectedDesignatedTradeLevel = null;
	/* Array Lists */
	private List<DesignatedTradeLevel> designatedTradeLevelList = new ArrayList<>();
	
	/* booleans */
	private boolean displayLastInfo = false;
	private Date readyDateForTradeTestMinium;
	private Date readyDateForTradeTestMax;
	
	/* Vars */
	private int avalibleTradeTestCenters = 0;
	private String validationErrors = "";
	private Boolean selectedNewDesignatedTradeLevel = false;
	
	private LazyDataModel<Tasks> dataModelTasks;
	private TasksService tasksService = null;
	private String accreditedQuallMssg;
	
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
		learnersInfo();
	}
	
	
	public void learnersInfo() {
		try {
			dataModel = new LazyDataModel<CompanyLearners>() {
				private static final long serialVersionUID = 1L;
				private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();

				@Override
				public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
					try {
							filters.put("terminated", LearnerStatusEnum.Terminated);
							filters.put("forTradeTest", true);
							retorno = service.allArplTradeTestLearners(first, pageSize, sortField, sortOrder, filters);
							for(CompanyLearners companyLearners: retorno) {
								if(companyLearners.getCompany() !=null && companyLearners.getCompany().getId()!=null) {
									companyLearners.setCompany(companyService.findByKey(companyLearners.getCompany().getId()));
								}
							}
							dataModel.setRowCount(service.countAllArplTradeTestLearners(filters));
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
			RequestContext context = RequestContext.getCurrentInstance();
		    context.scrollTo("mainForm:pgLearnerDetails");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
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
	
	
	public void downlodDocument() {
		try {
			companyLearnersService.downloadWorkBasedLearningProgrammeAgreement(companyLearners);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());			
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
				
				// FOR TESTING NEEDS TO BE REMOVED
				companyLearnersTradeTest.setLearnerReadinessDate(GenericUtility.deductDaysFromDate(getNow(), 1));
				
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
			// resolve company address
			companyService.resolveCompanyAddresses(companyLearnersTradeTest.getPreferredTrainingCenter());
			
			qualityAssuror = companyLearnersTradeTestService.findRegionQualityAssuror(companyLearnersTradeTest.getPreferredTrainingCenter());
			if (qualityAssuror.size() == 0) {
				displayLastInfo = false;
				addWarningMessage("MerSeta has not yet allocated a Quality Assuror to the region of " + companyLearnersTradeTest.getPreferredTrainingCenter().getCompanyName());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	public void requestWithdrawal() {
		try {
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
				runClientSideExecute("PF('dlgLearnerApplicationWithdrawal').hide()");
			}else {
				addErrorMessage("Error with the learner information");
			}
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
			else
			{
				l.add(new SelectItem(val, val.getFriendlyName()));
			}
		}
		return l;
	}
	

	public void requestTradeTestApplication() {
		try {
			if(companyLearners.getLegacyTargetClass() != null) {
				companyLearnersService.validiationOnTradeTestRequest(companyLearnersTradeTest);
				companyLearnersTradeTest.setCreateUser(getSessionUI().getActiveUser());
				companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithInitiatorOne);			
				if(companyLearners.getLegacyTargetClass().matches(LegacySECTTwentyEight.class.getName())) {
					companyLearnersTradeTest.setTradeTestType(TradeTestTypeEnum.ARPL);	
				}else if(companyLearners.getLegacyTargetClass().matches(LegacyApprenticeship.class.getName())) {
					companyLearnersTradeTest.setTradeTestType(TradeTestTypeEnum.TRADE_TEST);
				}else {
					throw new Exception("Trade Test Type Error!!! Please contact administrator");
				}				
				companyLearnersTradeTestService.requestARPLTradeTestApplicationForLegacy(companyLearnersTradeTest, getSessionUI().getActiveUser());
				addInfoMessage("Trade Test Application Submitted");
				runClientSideExecute("uploadDone()");
				runClientSideExecute("PF('dlgTradeTestVTwo').hide()");
			}else {
				throw new Exception("Trade Test not a legacy!!! Please contact administrator");
			}
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

	public List<Learners> getLearnersList() {
		return learnersList;
	}

	public void setLearnersList(List<Learners> learnersList) {
		this.learnersList = learnersList;
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


	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public CompanyLearnersTradeTest getCompanyLearnersTradeTest() {
		return companyLearnersTradeTest;
	}

	public void setCompanyLearnersTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest) {
		this.companyLearnersTradeTest = companyLearnersTradeTest;
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

}
