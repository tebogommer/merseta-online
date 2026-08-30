package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyTradeTestEmployer;
import haj.com.entity.DetailsOfExperienceArpl;
import haj.com.entity.DetailsOfTrainingArpl;
import haj.com.entity.Signoff;
import haj.com.entity.TradeTestTaskResult;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AprlProgressEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyLearnersTradeTestService;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyTradeTestEmployerService;
import haj.com.service.DetailsOfExperienceArplService;
import haj.com.service.DetailsOfTrainingArplService;
import haj.com.service.SignoffService;
import haj.com.service.TradeTestTaskResultService;
import haj.com.service.UsersService;
import haj.com.service.lookup.LegacyApprenticeshipService;
import haj.com.service.lookup.LegacyApprenticeshipTradeTestService;

@ManagedBean(name = "viewCreateLegacyArplUI")
@ViewScoped
public class ViewCreateLegacyArplUI extends AbstractUI {
	
	/* Entity Levels */
	private CompanyLearnersTradeTest companyLearnersTradeTest = null;
	private CompanyLearnersTradeTest viewCompanyLearnersTradeTest = null;
	private CompanyTradeTestEmployer companyTradeTestEmployer = null;
	private CompanyLearners companyLearners = new CompanyLearners();

	/* Service Levels */
	private CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
	private CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
	private DetailsOfExperienceArplService detailsOfExperienceArplService = new DetailsOfExperienceArplService();
	private DetailsOfTrainingArplService detailsOfTrainingArplService = new DetailsOfTrainingArplService();
	private CompanyTradeTestEmployerService companyTradeTestEmployerService = new CompanyTradeTestEmployerService();
	private TradeTestTaskResultService tradeTestTaskResultService = new TradeTestTaskResultService();
	private SignoffService signoffService = new SignoffService();
	private CompanyService companyService = new CompanyService();
	
	/* Lazy Data Models */
	private LazyDataModel<CompanyLearnersTradeTest> companyLearnersTradeTestDataModel;
	private LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl;
	private LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl;
	private LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult;
	
	/* Array Lists */
	private List<CompanyLearnersTradeTest> companyLearnersTradeTestList;
	private List<Signoff> signOffLists = new ArrayList<>();
	
	
	/* Vars */
	private int openCount = 0;
	private int avalibleTradeTestCenters = 0;
	private Boolean checkRegionBool;
	private Boolean check;
	private boolean validationFailed = false;
	private boolean displayTradeTestCenter = false;
	private boolean displayLastInfo = false;
	private String validationErrors = "";
	
	private final String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;
	private final String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;
	private final String FAX_NUMBER_FORMAT = HAJConstants.FAX_NUMBER_FORMAT;
	
	/* Tab displays */
	private boolean learnerInfoTab;
	private boolean iniTab;
	private boolean applicationTab;
	private boolean employerAppTab;
	private boolean tradeTestTab;
	private boolean signOffTab;
	private boolean docTab;

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
		populateServiceLevels();
		companyLearnersTradeTestDataModelInfo();
		openCount = companyLearnersTradeTestService.countOpenByArplByUser(getSessionUI().getActiveUser(), TradeTestTypeEnum.ARPL);
		check = false;
	}

	private void populateServiceLevels() {
		
	}

	public void companyLearnersTradeTestDataModelInfo() {
		companyLearnersTradeTestDataModel = new LazyDataModel<CompanyLearnersTradeTest>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersTradeTest> retorno = new ArrayList<CompanyLearnersTradeTest>();
			@Override
			public List<CompanyLearnersTradeTest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = companyLearnersTradeTestService.allLegacyCompanyLearnersTradeTest(first, pageSize, sortField, sortOrder, filters,TradeTestTypeEnum.ARPL);
					companyLearnersTradeTestDataModel.setRowCount(companyLearnersTradeTestService.countAllLegacyCompanyLearnersTradeTest(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CompanyLearnersTradeTest obj) {
				return obj.getId();
			}
			@Override
			public CompanyLearnersTradeTest getRowData(String rowKey) {
				for (CompanyLearnersTradeTest obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void prepCompanyLearnersTradeTestArpl() {
		/*
		 * companyLearnersTradeTest = new CompanyLearnersTradeTest();
		 * companyLearnersTradeTest.setCreateUser(getSessionUI().getActiveUser());
		 * companyLearnersTradeTest.setLearner(getSessionUI().getActiveUser());
		 * companyLearnersTradeTest.setTradeTestType(TradeTestTypeEnum.ARPL);
		 * companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithInitiatorOne);
		 * companyLearnersTradeTest.setAlterEmployerInfo(true);
		 */
		validationFailed = false;
		displayTradeTestCenter = false;
		displayLastInfo = false;
		viewCompanyLearnersTradeTest = null;
	}
	
	public void prepCLTradeTestArpl() {
		
		try {
			
			companyLearnersTradeTest = new CompanyLearnersTradeTest();
			companyLearnersTradeTest.setQualification(companyLearners.getQualification());
			companyLearnersTradeTest.setCreateUser(getSessionUI().getActiveUser());
			companyLearnersTradeTest.setLearner(companyLearners.getUser());
			companyLearnersTradeTest.setCompanyLearners(companyLearners);

			companyLearnersTradeTest.setAlterEmployerInfo(false);
			companyLearnersTradeTest.setEmploymentStatus(EmploymentStatusEnum.Employed);
			companyLearnersTradeTest.setEmployerOnNsdms(YesNoEnum.Yes);
			companyLearnersTradeTest.setCompanyEmployer(companyService.findByKey(companyLearners.getEmployer().getId()));
			
			companyLearnersTradeTest.setTradeTestType(TradeTestTypeEnum.ARPL);
			companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithInitiatorOne);
			companyLearnersTradeTest.setAlterEmployerInfo(true);
			validationFailed = false;
			displayTradeTestCenter = false;
			displayLastInfo = false;
			viewCompanyLearnersTradeTest = null;
			cLTradeTestApplicationCheck();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}

		
	}
	
	/* Current on testing */
	public void prepCompanyLearnersTradeTestArplWithEmployer() throws Exception {
		CompanyService comp = new CompanyService();
		companyLearnersTradeTest = new CompanyLearnersTradeTest();
		companyLearnersTradeTest.setCreateUser(getSessionUI().getActiveUser());
		companyLearnersTradeTest.setLearner(getSessionUI().getActiveUser());
		companyLearnersTradeTest.setTradeTestType(TradeTestTypeEnum.ARPL);
		companyLearnersTradeTest.setAprlProgress(AprlProgressEnum.WithInitiatorOne);
		
		companyLearnersTradeTest.setAlterEmployerInfo(false);
		companyLearnersTradeTest.setEmploymentStatus(EmploymentStatusEnum.Employed);
		companyLearnersTradeTest.setEmployerOnNsdms(YesNoEnum.Yes);
		companyLearnersTradeTest.setCompanyEmployer(comp.findByKey(46632l));
		
		validationFailed = false;
		displayTradeTestCenter = false;
		displayLastInfo = false;
		viewCompanyLearnersTradeTest = null;
	}
	
	public void companyLearnersTradeTestApplicationCheck() {
		try {
			clearInfoOnQualificationSelection();
			checkIfQualificationObtainedByLearner();
			displayTradeTestCenter = true;
			check = false;
			companyLearnersTradeTestService.validateUserAllowedArpl(companyLearnersTradeTest.getQualification(), companyLearnersTradeTest.getLearner(), true);
			companyLearnersTradeTestService.calculateArplAttempts(companyLearnersTradeTest, companyLearnersTradeTest.getQualification(), companyLearnersTradeTest.getLearner());
			
			// old version
//			companyLearnersTradeTestList = companyLearnersTradeTestService.findByTradeTypeLearnerQualification(TradeTestTypeEnum.ARPL, companyLearnersTradeTest.getLearner().getId(), companyLearnersTradeTest.getQualification().getId());
//			if (!companyLearnersTradeTestList.isEmpty()) {
//				for (CompanyLearnersTradeTest entryList : companyLearnersTradeTestList) {
//					if (this.companyLearnersTradeTest.getQualification().getId() == entryList.getQualification().getId()) {
//						check = true;
//						if (entryList.getStatus() ==  ApprovalEnum.QualificationObtained) {
//							addWarningMessage("Qualification has already been obtained by the user. Please select a differenet qualification.");
//							displayTradeTestCenter = false;
//							runClientSideUpdate("tradeTestFormRPL");
//						} else {
//							this.companyLearnersTradeTest.setAttemptedTradeTest(YesNoEnum.Yes);
//							this.companyLearnersTradeTest.setPreviousAttemptDate(companyLearnersTradeTestList.get(0).getCreateDate());
//							this.companyLearnersTradeTest.setPreviousTrainingCenter(companyLearnersTradeTestList.get(0).getPreferredTrainingCenter());
//							this.companyLearnersTradeTest.setAttemptNumber(companyLearnersTradeTestList.size());
//							runClientSideUpdate("tradeTestFormRPL");
//						}
//						break;
//					}
//				}
//			} 
			if (companyLearnersTradeTest.getQualification() != null) {
//				avalibleTradeTestCenters = companyQualificationsService.countCompaniesAssignedToQualificationAndAccept(companyLearnersTradeTest.getQualification(), true);
//				avalibleTradeTestCenters = companyQualificationsService.countCompaniesAssignedToQualificationAndTpApplicationIsApproved(companyLearnersTradeTest.getQualification(), true);
//				avalibleTradeTestCenters = companyQualificationsService.countCompaniesAssignedToQualificationAndTpApplicationIsApprovedAndBeforeExpiryDate(companyLearnersTradeTest.getQualification(), true, getNow());
				avalibleTradeTestCenters = companyQualificationsService.countCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(companyLearnersTradeTest.getQualification(), true, getNow(), AccreditationApplicationTypeEnum.QCTOTradeTestCentre);
			} else {
				avalibleTradeTestCenters = 0;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void cLTradeTestApplicationCheck() {
		try {
			clearInfoOnQualificationSelection();
			checkIfQualificationObtainedByLearner();
			displayTradeTestCenter = true;
			check = false;
			companyLearnersTradeTestService.validateUserAllowedArpl(companyLearnersTradeTest.getQualification(), companyLearnersTradeTest.getLearner(), false);
			companyLearnersTradeTestService.calculateArplAttempts(companyLearnersTradeTest, companyLearnersTradeTest.getQualification(), companyLearnersTradeTest.getLearner());
			
			if (companyLearnersTradeTest.getQualification() != null) {
				avalibleTradeTestCenters = companyQualificationsService.countCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(companyLearnersTradeTest.getQualification(), true, getNow(), AccreditationApplicationTypeEnum.QCTOTradeTestCentre);
			} else {
				avalibleTradeTestCenters = 0;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void clearInfoOnQualificationSelection() {
		displayTradeTestCenter = false;
		displayLastInfo = false;
		companyLearnersTradeTest.setFlc(null);
		companyLearnersTradeTest.setPreferredTrainingCenter(null);
		companyLearnersTradeTest.setAttemptedTradeTest(null);
		companyLearnersTradeTest.setPreviousAttemptDate(null);
		companyLearnersTradeTest.setPreviousTrainingCenter(null);
		companyLearnersTradeTest.setAttemptNumber(null);
		companyLearnersTradeTest.setLearnerReadinessDate(null);
	}
	
	private void checkIfQualificationObtainedByLearner() throws Exception{
		validationFailed = false;
		validationErrors = "";
		 
		// ARPL check to see if learner has not already obtained qualification
		int arplQualificationCompleted = companyLearnersTradeTestService.countByTypeLearnerIdQualificationAndStatus(TradeTestTypeEnum.ARPL, companyLearnersTradeTest.getLearner().getId(), companyLearnersTradeTest.getQualification().getId(), ApprovalEnum.QualificationObtained);
		if (arplQualificationCompleted != 0) {
			validationFailed = true;
			validationErrors = "Qualification Already Obtained By Learner Under ARPL";
		}
		
		if (validationFailed) {
			throw new Exception("Valdiation Exception. Refer to message displayed.");
		}
	}

	public List<Company> findCompaniesAssignedToQualification(String desc){
		List<Company> l = null;
		try {
			if (companyLearnersTradeTest != null && companyLearnersTradeTest.getQualification() != null) {
//				l = companyQualificationsService.findCompaniesAssignedToQualification(desc, companyLearnersTradeTest.getQualification(), true);
//				l = companyQualificationsService.findCompaniesAssignedToQualificationAndTpApplicationIsApproved(desc, companyLearnersTradeTest.getQualification(), true);
//				l = companyQualificationsService.findCompaniesAssignedToQualificationAndTpApplicationIsApprovedAndBeforeExpiryDate(desc, companyLearnersTradeTest.getQualification(), true, getNow());
				l = companyQualificationsService.findCompaniesByAcceptQualificationApprovedApplicationBeforeExpiryAndTypeOfApplication(desc, companyLearnersTradeTest.getQualification(), true, getNow(), AccreditationApplicationTypeEnum.QCTOTradeTestCentre);
				
			} else {
				throw new Exception("Unable to locate qualification selected for test center selection. Contact support!");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return  l;
	}

	
	public void requestARPLTradeTestApplication() {
		try {
			validateInformation(companyLearnersTradeTest);
//			companyLearnersTradeTest.setCreateUser(usersService.findByKey(1460));			
			companyLearnersTradeTest.setCreateUser(getSessionUI().getActiveUser());
			//companyLearnersTradeTestService.requestARPLTradeTestApplication(companyLearnersTradeTest, getSessionUI().getActiveUser());
			companyLearnersTradeTestService.requestARPLTradeTestApplicationForLegacy(companyLearnersTradeTest, getSessionUI().getActiveUser());
			addInfoMessage("Application Submitted");
			companyLearnersTradeTestDataModelInfo();
			check = false;
			
			runClientSideExecute("PF('dlgTradeTestRPLVTwo').hide()");
//			runClientSideExecute("PF('dlgTradeTestRPL').hide()");
			//runClientSideExecute("PF('dlgTradeTestRPLcl').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	private void validateInformation(CompanyLearnersTradeTest companyLearnersTradeTest2) {
		
	}

	public void checkRegion() {
		List<Users> qualityAssuror = new ArrayList<>();
		checkRegionBool = false;
		displayLastInfo = true;
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
	
	public void prepViewArpl(){
		try {
			companyLearnersTradeTest = null;
			viewCompanyLearnersTradeTest =  companyLearnersTradeTestService.populateDocsByTargetClassAndKey(companyLearnersTradeTestService.findByKey(viewCompanyLearnersTradeTest.getId()));
			dataModelDetailsOfExperienceArplInfo();
			dataModelDetailsOfTrainingArplInfo();
			if (viewCompanyLearnersTradeTest.getEmployer() != null && viewCompanyLearnersTradeTest.getEmployer().getId() != null) {
				companyTradeTestEmployer = companyTradeTestEmployerService.findByKey(viewCompanyLearnersTradeTest.getEmployer().getId());
			}
			populateSignOffs();
			dataModelTradeTestTaskResultInfo();
			dertmainSectionDisplay();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateSignOffs() throws Exception{
		if (viewCompanyLearnersTradeTest != null && viewCompanyLearnersTradeTest.getId() != null) {
			signOffLists = signoffService.findByTargetKeyAndClass(viewCompanyLearnersTradeTest.getId(), viewCompanyLearnersTradeTest.getClass().getName());
		}
	}
	
	public void dataModelDetailsOfExperienceArplInfo() {
		dataModelDetailsOfExperienceArpl = new LazyDataModel<DetailsOfExperienceArpl>() {
			private static final long serialVersionUID = 1L;
			private List<DetailsOfExperienceArpl> retorno = new ArrayList<DetailsOfExperienceArpl>();
			@Override
			public List<DetailsOfExperienceArpl> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = detailsOfExperienceArplService.allDetailsOfExperienceArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest);
					dataModelDetailsOfExperienceArpl.setRowCount(detailsOfExperienceArplService.countAllDetailsOfExperienceArplByTradeTestId(filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(DetailsOfExperienceArpl obj) {
				return obj.getId();
			}
			@Override
			public DetailsOfExperienceArpl getRowData(String rowKey) {
				for (DetailsOfExperienceArpl obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void dataModelDetailsOfTrainingArplInfo() {
		dataModelDetailsOfTrainingArpl = new LazyDataModel<DetailsOfTrainingArpl>() {
			private static final long serialVersionUID = 1L;
			private List<DetailsOfTrainingArpl> retorno = new ArrayList<DetailsOfTrainingArpl>();

			@Override
			public List<DetailsOfTrainingArpl> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = detailsOfTrainingArplService.allDetailsOfTrainingArplByTradeTestId(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest);
					dataModelDetailsOfTrainingArpl.setRowCount(detailsOfTrainingArplService.countAllDetailsOfTrainingArplByTradeTestId(filters));
				} catch (Exception e) {
					logger.fatal(e.getMessage(), e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DetailsOfTrainingArpl obj) {
				return obj.getId();
			}

			@Override
			public DetailsOfTrainingArpl getRowData(String rowKey) {
				for (DetailsOfTrainingArpl obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void dataModelTradeTestTaskResultInfo() {
		dataModelTradeTestTaskResult = new LazyDataModel<TradeTestTaskResult>() {
			private static final long serialVersionUID = 1L;
			private List<TradeTestTaskResult> retorno = new ArrayList<TradeTestTaskResult>();

			@Override
			public List<TradeTestTaskResult> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = tradeTestTaskResultService.allTradeTestTaskResultByTradeTestId(first, pageSize, sortField, sortOrder, filters, viewCompanyLearnersTradeTest);
					dataModelTradeTestTaskResult.setRowCount(tradeTestTaskResultService.countAllTradeTestTaskResultByTradeTestId(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(TradeTestTaskResult obj) {
				return obj.getId();
			}

			@Override
			public TradeTestTaskResult getRowData(String rowKey) {
				for (TradeTestTaskResult obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void closeViewArpl(){
		try {
			viewCompanyLearnersTradeTest = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void defaultFileds(){
		learnerInfoTab = false;
		iniTab = false;
		applicationTab = false;
		employerAppTab = false;
		tradeTestTab = false;
		signOffTab = false;
		docTab = false;
	}
	
	private void dertmainSectionDisplay() throws Exception {
		defaultFileds();
		learnerInfoTab = true;
		iniTab = true;
		applicationTab = true;
		employerAppTab = true;
		docTab = true;
		tradeTestTab = false;
		signOffTab = false;
		if (viewCompanyLearnersTradeTest.getAprlProgress() != null) {
			switch (viewCompanyLearnersTradeTest.getAprlProgress()) {
			case WithTestCenterOne:
				tradeTestTab = true;
				break;
			case WithMersetaFive:
				tradeTestTab = true;
				signOffTab = true;
				break;
			case WithMersetaSix:
				tradeTestTab = true;
				signOffTab = true;
				break;
			default:
				break;
			}
		}
	}

	/* Getters and setetrs */
	public CompanyLearnersTradeTest getCompanyLearnersTradeTest() {
		return companyLearnersTradeTest;
	}

	public void setCompanyLearnersTradeTest(CompanyLearnersTradeTest companyLearnersTradeTest) {
		this.companyLearnersTradeTest = companyLearnersTradeTest;
	}
	
	public LazyDataModel<CompanyLearnersTradeTest> getCompanyLearnersTradeTestDataModel() {
		return companyLearnersTradeTestDataModel;
	}

	public void setCompanyLearnersTradeTestDataModel(
			LazyDataModel<CompanyLearnersTradeTest> companyLearnersTradeTestDataModel) {
		this.companyLearnersTradeTestDataModel = companyLearnersTradeTestDataModel;
	}

	public int getOpenCount() {
		return openCount;
	}

	public void setOpenCount(int openCount) {
		this.openCount = openCount;
	}

	public CompanyLearnersTradeTest getViewCompanyLearnersTradeTest() {
		return viewCompanyLearnersTradeTest;
	}

	public void setViewCompanyLearnersTradeTest(CompanyLearnersTradeTest viewCompanyLearnersTradeTest) {
		this.viewCompanyLearnersTradeTest = viewCompanyLearnersTradeTest;
	}

	public Boolean getCheckRegionBool() {
		return checkRegionBool;
	}

	public void setCheckRegionBool(Boolean checkRegionBool) {
		this.checkRegionBool = checkRegionBool;
	}

	public List<CompanyLearnersTradeTest> getCompanyLearnersTradeTestList() {
		return companyLearnersTradeTestList;
	}

	public void setCompanyLearnersTradeTestList(List<CompanyLearnersTradeTest> companyLearnersTradeTestList) {
		this.companyLearnersTradeTestList = companyLearnersTradeTestList;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public boolean isDisplayTradeTestCenter() {
		return displayTradeTestCenter;
	}

	public void setDisplayTradeTestCenter(boolean displayTradeTestCenter) {
		this.displayTradeTestCenter = displayTradeTestCenter;
	}

	public boolean isDisplayLastInfo() {
		return displayLastInfo;
	}

	public void setDisplayLastInfo(boolean displayLastInfo) {
		this.displayLastInfo = displayLastInfo;
	}

	public int getAvalibleTradeTestCenters() {
		return avalibleTradeTestCenters;
	}

	public void setAvalibleTradeTestCenters(int avalibleTradeTestCenters) {
		this.avalibleTradeTestCenters = avalibleTradeTestCenters;
	}

	public boolean isValidationFailed() {
		return validationFailed;
	}

	public void setValidationFailed(boolean validationFailed) {
		this.validationFailed = validationFailed;
	}

	public String getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(String validationErrors) {
		this.validationErrors = validationErrors;
	}

	public LazyDataModel<DetailsOfExperienceArpl> getDataModelDetailsOfExperienceArpl() {
		return dataModelDetailsOfExperienceArpl;
	}

	public void setDataModelDetailsOfExperienceArpl(
			LazyDataModel<DetailsOfExperienceArpl> dataModelDetailsOfExperienceArpl) {
		this.dataModelDetailsOfExperienceArpl = dataModelDetailsOfExperienceArpl;
	}

	public LazyDataModel<DetailsOfTrainingArpl> getDataModelDetailsOfTrainingArpl() {
		return dataModelDetailsOfTrainingArpl;
	}

	public void setDataModelDetailsOfTrainingArpl(LazyDataModel<DetailsOfTrainingArpl> dataModelDetailsOfTrainingArpl) {
		this.dataModelDetailsOfTrainingArpl = dataModelDetailsOfTrainingArpl;
	}

	public LazyDataModel<TradeTestTaskResult> getDataModelTradeTestTaskResult() {
		return dataModelTradeTestTaskResult;
	}

	public void setDataModelTradeTestTaskResult(LazyDataModel<TradeTestTaskResult> dataModelTradeTestTaskResult) {
		this.dataModelTradeTestTaskResult = dataModelTradeTestTaskResult;
	}

	public List<Signoff> getSignOffLists() {
		return signOffLists;
	}

	public void setSignOffLists(List<Signoff> signOffLists) {
		this.signOffLists = signOffLists;
	}

	public boolean isLearnerInfoTab() {
		return learnerInfoTab;
	}

	public void setLearnerInfoTab(boolean learnerInfoTab) {
		this.learnerInfoTab = learnerInfoTab;
	}

	public boolean isIniTab() {
		return iniTab;
	}

	public void setIniTab(boolean iniTab) {
		this.iniTab = iniTab;
	}

	public boolean isApplicationTab() {
		return applicationTab;
	}

	public void setApplicationTab(boolean applicationTab) {
		this.applicationTab = applicationTab;
	}

	public boolean isEmployerAppTab() {
		return employerAppTab;
	}

	public void setEmployerAppTab(boolean employerAppTab) {
		this.employerAppTab = employerAppTab;
	}

	public boolean isTradeTestTab() {
		return tradeTestTab;
	}

	public void setTradeTestTab(boolean tradeTestTab) {
		this.tradeTestTab = tradeTestTab;
	}

	public boolean isSignOffTab() {
		return signOffTab;
	}

	public void setSignOffTab(boolean signOffTab) {
		this.signOffTab = signOffTab;
	}

	public boolean isDocTab() {
		return docTab;
	}

	public void setDocTab(boolean docTab) {
		this.docTab = docTab;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public String getFAX_NUMBER_FORMAT() {
		return FAX_NUMBER_FORMAT;
	}

	public CompanyTradeTestEmployer getCompanyTradeTestEmployer() {
		return companyTradeTestEmployer;
	}

	public void setCompanyTradeTestEmployer(CompanyTradeTestEmployer companyTradeTestEmployer) {
		this.companyTradeTestEmployer = companyTradeTestEmployer;
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}
}
