package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Learners;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyInternship;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacyTvet;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.SDFCompanyService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.lookup.LegacyApprenticeshipService;
import haj.com.service.lookup.LegacyApprenticeshipTradeTestService;
import haj.com.service.lookup.LegacyBursaryService;
import haj.com.service.lookup.LegacyInternshipService;
import haj.com.service.lookup.LegacyLearnershipAssessmentService;
import haj.com.service.lookup.LegacyLearnershipService;
import haj.com.service.lookup.LegacySECTTwentyEightService;
import haj.com.service.lookup.LegacySectionTwentyEightTradeTestService;
import haj.com.service.lookup.LegacySkillsProgrammeAssessmentsService;
import haj.com.service.lookup.LegacySkillsProgrammeService;
import haj.com.service.lookup.LegacyTvetService;
import haj.com.service.lookup.LegacyUnitStandardAssessmentService;
import haj.com.service.lookup.LegacyUnitStandardService;

@ManagedBean(name = "legacyLearnersUI")
@ViewScoped
public class LegacyLearnersUI extends AbstractUI {
	private LegacyApprenticeshipTradeTestService legacyApprenticeshipTradeTestService = new LegacyApprenticeshipTradeTestService();
	private LegacyUnitStandardAssessmentService legacyUnitStandardAssessmentService = new LegacyUnitStandardAssessmentService();
	private LegacySkillsProgrammeAssessmentsService legacySkillsProgrammeAssessmentsService = new LegacySkillsProgrammeAssessmentsService();
	private LegacyLearnershipAssessmentService legacyLearnershipAssessmentService = new LegacyLearnershipAssessmentService();	
	private LegacySectionTwentyEightTradeTestService legacySectionTwentyEightTradeTestService = new LegacySectionTwentyEightTradeTestService();
	
	private List<Learners> learnersfilteredList = null;
	
	//Bursary
	private LegacyBursaryService bursaryservice = new LegacyBursaryService();
	private List<LegacyBursary> legacybursaryList = null;
	private List<LegacyBursary> legacybursaryfilteredList = null;
	private LegacyBursary legacybursary = null;
	private LazyDataModel<LegacyBursary> dataModel;
	
	private LegacyApprenticeshipService apprenticeshipservice = new LegacyApprenticeshipService();
	private List<LegacyApprenticeship> legacyapprenticeshipList = null;
	private List<LegacyApprenticeship> legacyapprenticeshipfilteredList = null;
	private LegacyApprenticeship legacyapprenticeship = null;
	private LazyDataModel<LegacyApprenticeship> apprenticeshipdataModel;
	
	private LegacyInternshipService internshiservice = new LegacyInternshipService();
	private List<LegacyInternship> legacyinternshipList = null;
	private List<LegacyInternship> legacyinternshipfilteredList = null;
	private LegacyInternship legacyinternship = null;
	private LazyDataModel<LegacyInternship> internshipdataModel;
	
	private LegacyLearnershipService learnershipservice = new LegacyLearnershipService();
	private List<LegacyLearnership> legacylearnershipList = null;
	private List<LegacyLearnership> legacylearnershipfilteredList = null;
	private LegacyLearnership legacylearnership = null;
	private LazyDataModel<LegacyLearnership> learnershipdataModel;
	
	private LegacySkillsProgrammeService skillsprogrammeservice = new LegacySkillsProgrammeService();
	private List<LegacySkillsProgramme> legacyskillsprogrammeList = null;
	private List<LegacySkillsProgramme> legacyskillsprogrammefilteredList = null;
	private LegacySkillsProgramme legacyskillsprogramme = null;
	private LazyDataModel<LegacySkillsProgramme> skillsprogrammedataModel;
	
	private LegacyUnitStandardService unitstandardservice = new LegacyUnitStandardService();
	private List<LegacyUnitStandard> legacyunitstandardList = null;
	private List<LegacyUnitStandard> legacyunitstandardfilteredList = null;
	private LegacyUnitStandard legacyunitstandard = null;
	private LazyDataModel<LegacyUnitStandard> unitstandarddataModel;
	
	private LegacyTvetService tvetservice = new LegacyTvetService();
	private List<LegacyTvet> legacytvetList = null;
	private List<LegacyTvet> legacytvetfilteredList = null;
	private LegacyTvet legacytvet = null;
	private LazyDataModel<LegacyTvet> tvetdataModel;
	
	private LegacySECTTwentyEightService legacySECTTwentyEightService = new LegacySECTTwentyEightService();
	private List<LegacySECTTwentyEight> legacysecttwentyeightList = null;
	private List<LegacySECTTwentyEight> legacysecttwentyeightfilteredList = null;
	private LegacySECTTwentyEight legacysecttwentyeight = null;
	private LazyDataModel<LegacySECTTwentyEight> twentyEightdataModel;


	private List<Company> companies = null;
	private Company selectedCompany;
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	
	private Boolean primaryOrSecondarySDF;
	private Boolean companyContactRegisterLearner;
	private CompanyService companyService = new CompanyService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private boolean viewLegacyData = false;
	private Company provider;

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
		prepareNew();
		if (getSessionUI().isExternalParty()) {
			companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
		}else if(getSessionUI().isEmployee()) {
			companies = companyUsersService.find();
		}
	}
	
	private void prepareNew() {

	}
	
	public void clearAll() {
		dataModel = null;
		apprenticeshipdataModel = null;
		internshipdataModel = null;
		learnershipdataModel =null;
		skillsprogrammedataModel =null;
		unitstandarddataModel = null;
		tvetdataModel = null;
		twentyEightdataModel = null;
	}
	
	public void populateInfor() {
		clearAll();
		if (getSessionUI().isExternalParty()) {
			try {
				primaryOrSecondarySDF = sdfCompanyService.checkIfPrimarOrSecondaryCanRegisterLearners(getSessionUI().getActiveUser(), selectedCompany);
				if(!primaryOrSecondarySDF) {
					companyContactRegisterLearner = companyUsersService.checkIfCompanyContactCanRegisterLearner(getSessionUI().getActiveUser(), selectedCompany, ConfigDocProcessEnum.Learner);
					if (companyContactRegisterLearner) {
						//legacybursaryInfo();
						//legacyapprenticeshipInfo();
						//legacyinternshipInfo();
						//legacylearnershipInfo();
						//legacyskillsprogrammeInfo();
						//legacyunitstandardInfo();
						//legacytvetInfo();
						//legacysecttwentyeightInfo();
						viewLegacyData = true;
					} else if(getSessionUI().isTrainingProvider()){
						//legacybursaryInfo();
						//legacyapprenticeshipInfo();
						//legacyinternshipInfo();
						//legacylearnershipInfo();
						//legacyskillsprogrammeInfo();
						//legacyunitstandardInfo();
						//legacytvetInfo();
						//legacysecttwentyeightInfo();
						viewLegacyData = true;
					}else {
						addErrorMessage("Kindly be advised that you require the relevant authorisation to access this information");
					}
				} else {
					//legacybursaryInfo();
					//legacyapprenticeshipInfo();
					//legacyinternshipInfo();
					//legacylearnershipInfo();
					//legacyskillsprogrammeInfo();
					//legacyunitstandardInfo();
					//legacytvetInfo();
					//legacysecttwentyeightInfo();
					viewLegacyData = true;
				}
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
				e.printStackTrace();
				clearAll();
			}			
		}		
	}
	
	/**
	 * Get all LegacyBursary for data table
	 * 
	 * @author TechFinium
	 * @see LegacyBursary
	 */
	public void legacybursaryInfo() {
		clearAll();
		dataModel = new LazyDataModel<LegacyBursary>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyBursary> retorno = new ArrayList<LegacyBursary>();

			@Override
			public List<LegacyBursary> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					filters.put("employerSdl", selectedCompany.getLevyNumber());
					retorno = bursaryservice.allLegacyBursary(LegacyBursary.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(bursaryservice.count(LegacyBursary.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyBursary obj) {
				return obj.getId();
			}

			@Override
			public LegacyBursary getRowData(String rowKey) {
				for (LegacyBursary obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	public void legacyapprenticeshipInfo() {
		clearAll();
		apprenticeshipdataModel = new LazyDataModel<LegacyApprenticeship>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyApprenticeship> retorno = new ArrayList<LegacyApprenticeship>();

			@Override
			public List<LegacyApprenticeship> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					filters.put("sdlNo", selectedCompany.getLevyNumber());
					retorno = apprenticeshipservice.allLegacyApprenticeship(LegacyApprenticeship.class, first, pageSize, sortField, sortOrder, filters);
					for(LegacyApprenticeship legacyApprenticeship: retorno) {
						legacyApprenticeship.setLegacyApprenticeshipTradeTestList(legacyApprenticeshipTradeTestService.findByLegacyApprenticeship(legacyApprenticeship));
					}
					apprenticeshipdataModel.setRowCount(apprenticeshipservice.count(LegacyApprenticeship.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyApprenticeship obj) {
				return obj.getId();
			}

			@Override
			public LegacyApprenticeship getRowData(String rowKey) {
				for (LegacyApprenticeship obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	
	public void legacyinternshipInfo() {
		clearAll();
		internshipdataModel = new LazyDataModel<LegacyInternship>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyInternship> retorno = new ArrayList<LegacyInternship>();

			@Override
			public List<LegacyInternship> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				
				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					filters.put("employerSdl", selectedCompany.getLevyNumber());
					retorno = internshiservice.allLegacyInternship(LegacyInternship.class, first, pageSize, sortField, sortOrder, filters);
					internshipdataModel.setRowCount(internshiservice.count(LegacyInternship.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyInternship obj) {
				return obj.getId();
			}

			@Override
			public LegacyInternship getRowData(String rowKey) {
				for (LegacyInternship obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void legacylearnershipInfo() {
		clearAll();
		learnershipdataModel = new LazyDataModel<LegacyLearnership>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyLearnership> retorno = new ArrayList<LegacyLearnership>();

			@Override
			public List<LegacyLearnership> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					filters.put("employerSdl", selectedCompany.getLevyNumber());
					retorno = learnershipservice.allLegacyLearnership(LegacyLearnership.class, first, pageSize, sortField, sortOrder, filters);
					for(LegacyLearnership legacyLearnership: retorno) {
						legacyLearnership.setLegacyLearnershipAssessmentList(legacyLearnershipAssessmentService.findByLegacyLegacyLearnership(legacyLearnership));
					}
					learnershipdataModel.setRowCount(learnershipservice.count(LegacyLearnership.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyLearnership obj) {
				return obj.getId();
			}

			@Override
			public LegacyLearnership getRowData(String rowKey) {
				for (LegacyLearnership obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	public void legacyskillsprogrammeInfo() {
		clearAll();
		skillsprogrammedataModel = new LazyDataModel<LegacySkillsProgramme>() {

			private static final long serialVersionUID = 1L;
			private List<LegacySkillsProgramme> retorno = new ArrayList<LegacySkillsProgramme>();

			@Override
			public List<LegacySkillsProgramme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					filters.put("employerSDL", selectedCompany.getLevyNumber());
					retorno = skillsprogrammeservice.allLegacySkillsProgramme(LegacySkillsProgramme.class, first, pageSize, sortField, sortOrder, filters);
					for(LegacySkillsProgramme legacySkillsProgramme: retorno) {
						legacySkillsProgramme.setLegacySkillsProgrammeAssessmentsList(legacySkillsProgrammeAssessmentsService.findByLegacySkillsProgramme(legacySkillsProgramme));
					}
					skillsprogrammedataModel.setRowCount(skillsprogrammeservice.count(LegacySkillsProgramme.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacySkillsProgramme obj) {
				return obj.getId();
			}

			@Override
			public LegacySkillsProgramme getRowData(String rowKey) {
				for (LegacySkillsProgramme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void legacytvetInfo() {
		clearAll();
		tvetdataModel = new LazyDataModel<LegacyTvet>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyTvet> retorno = new ArrayList<LegacyTvet>();

			@Override
			public List<LegacyTvet> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					filters.put("employerSDL", selectedCompany.getLevyNumber());
					retorno = tvetservice.allLegacyTvet(LegacyTvet.class, first, pageSize, sortField, sortOrder, filters);
					tvetdataModel.setRowCount(tvetservice.count(LegacyTvet.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyTvet obj) {
				return obj.getId();
			}

			@Override
			public LegacyTvet getRowData(String rowKey) {
				for (LegacyTvet obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void legacyunitstandardInfo() {
		clearAll();
		unitstandarddataModel = new LazyDataModel<LegacyUnitStandard>() {

			private static final long serialVersionUID = 1L;
			private List<LegacyUnitStandard> retorno = new ArrayList<LegacyUnitStandard>();

			@Override
			public List<LegacyUnitStandard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					filters.put("employerSDL", selectedCompany.getLevyNumber());
					retorno = unitstandardservice.allLegacyUnitStandard(LegacyUnitStandard.class, first, pageSize, sortField, sortOrder, filters);
					for(LegacyUnitStandard legacyUnitStandard: retorno) {
						legacyUnitStandard.setLegacyUnitStandardAssessmentList(legacyUnitStandardAssessmentService.findByLegacyUnitStandard(legacyUnitStandard));
					}
					unitstandarddataModel.setRowCount(unitstandardservice.count(LegacyUnitStandard.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacyUnitStandard obj) {
				return obj.getId();
			}

			@Override
			public LegacyUnitStandard getRowData(String rowKey) {
				for (LegacyUnitStandard obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	public void legacysecttwentyeightInfo() {
		clearAll();
		twentyEightdataModel = new LazyDataModel<LegacySECTTwentyEight>() {

			private static final long serialVersionUID = 1L;
			private List<LegacySECTTwentyEight> retorno = new ArrayList<LegacySECTTwentyEight>();

			@Override
			public List<LegacySECTTwentyEight> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					filters.put("sdlNo", selectedCompany.getLevyNumber());
					retorno = legacySECTTwentyEightService.allLegacySECTTwentyEight(LegacySECTTwentyEight.class, first, pageSize, sortField, sortOrder, filters);
					for(LegacySECTTwentyEight legacySECTTwentyEight: retorno) {
						legacySECTTwentyEight.setLegacysectiontwentyeighttradetestList(legacySectionTwentyEightTradeTestService.findByLegacySECTTwentyEight(legacySECTTwentyEight));
					}
					twentyEightdataModel.setRowCount(legacySECTTwentyEightService.count(LegacySECTTwentyEight.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(LegacySECTTwentyEight obj) {
				return obj.getId();
			}

			@Override
			public LegacySECTTwentyEight getRowData(String rowKey) {
				for (LegacySECTTwentyEight obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}
	
	public void redirectLegacyApprenticeship() {
		try {
			
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
				getSessionUI().setTask(null);
			}
			super.setParameter("bursaryId", null, true);
			super.setParameter("internshipId", null, true);
			super.setParameter("learnershipId", null, true);
			super.setParameter("skillsprogrammeId", null, true);
			super.setParameter("tvetId", null, true);
			super.setParameter("unitstandardId", null, true);
			super.setParameter("secttwentyeightId", null, true);
			super.setParameter("legacyexperientialId", null, true);
			
			if(legacyapprenticeship.getIdNo() == null) {
				if(legacyapprenticeship.getAlternateId() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacyapprenticeship.getAlternateId().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}else if(legacyapprenticeship.getIdNo().equalsIgnoreCase("")) {
				if(legacyapprenticeship.getAlternateId() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacyapprenticeship.getAlternateId().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacyapprenticeship.getQualification() == null) {
				throw new Exception("Qualification not found");
			}
			
			if(legacyapprenticeship.getSdlNo() == null ) {
				throw new Exception("Learner is not linked to an employer, please contact the merSETA Office");
			}
			selectedCompany = companyService.findByLevyNumber(legacyapprenticeship.getSdlNo());
			
			if(selectedCompany == null) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			if(selectedCompany.getCompanyStatus() != CompanyStatusEnum.Active) {
				throw new Exception("Employer is not yet active !!!, please activate employer before proceeding");
			}
			
			legacyapprenticeship.setEmployer(selectedCompany);
			legacyapprenticeship.setCompany(selectedCompany);
			//legacyapprenticeship.setContractNumber("0");
			legacyapprenticeship.setContractStatus("Application");
						LegacyApprenticeshipService.instance().update(legacyapprenticeship);
									
			super.setParameter("apprenticeshipId", this.legacyapprenticeship.getId(), true);
			super.ajaxRedirect("/pages/tp/legacyLearnerRegistrationForm.jsf");
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			addErrorMessage(e.getMessage());
		}
	}
	
	public void redirectLegacyBusary() {
		try {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
				getSessionUI().setTask(null);
			}
			
			super.setParameter("apprenticeshipId", null, true);
			super.setParameter("internshipId", null, true);
			super.setParameter("learnershipId", null, true);
			super.setParameter("skillsprogrammeId", null, true);
			super.setParameter("tvetId", null, true);
			super.setParameter("unitstandardId", null, true);
			super.setParameter("secttwentyeightId", null, true);
			super.setParameter("legacyexperientialId", null, true);
			
			if(legacybursary.getIdNo() == null && legacybursary.getIdNo().equalsIgnoreCase("")) {
				if(legacybursary.getIdTwo() == null && legacybursary.getIdTwo().equalsIgnoreCase("")) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			if(legacybursary.getIdNo() == null) {
				if(legacybursary.getIdTwo() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacybursary.getIdTwo().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}else if(legacybursary.getIdNo().equalsIgnoreCase("")) {
				if(legacybursary.getIdTwo() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacybursary.getIdTwo().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacybursary.getEmployerSdl()== null ) {
				throw new Exception("Learner is not linked to an employer, please contact the merSETA Office");
			}
			
			if(legacybursary.getQualification() == null) {
				throw new Exception("Qualification not found");
			}
			
			selectedCompany = companyService.findByLevyNumber(legacybursary.getEmployerSdl());
			
			if(selectedCompany == null) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			if(selectedCompany.getCompanyStatus() != CompanyStatusEnum.Active) {
				throw new Exception("Employer is not yet active !!!, please activate employer before proceeding");
			}
			
			legacybursary.setEmployer(selectedCompany);
			legacybursary.setCompany(selectedCompany);
			
			LegacyBursaryService.instance().update(legacybursary);
			
			super.setParameter("bursaryId", this.legacybursary.getId(), true);
			super.ajaxRedirect("/pages/tp/legacyLearnerRegistrationForm.jsf");
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			addErrorMessage(e.getMessage());
		}
	}
	
	public void redirectLegacyInternship() {
		try {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
				getSessionUI().setTask(null);
			}
			
			super.setParameter("apprenticeshipId", null, true);
			super.setParameter("bursaryId", null, true);
			super.setParameter("learnershipId", null, true);
			super.setParameter("skillsprogrammeId", null, true);
			super.setParameter("tvetId", null, true);
			super.setParameter("unitstandardId", null, true);
			super.setParameter("secttwentyeightId", null, true);
			super.setParameter("legacyexperientialId", null, true);
			
			if(legacyinternship.getIdNo() == null && legacyinternship.getIdNo().equalsIgnoreCase("")) {
				 if(legacyinternship.getIdTwo() == null && legacyinternship.getIdTwo().equalsIgnoreCase("")) {
					 throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacyinternship.getIdNo()  == null) {
				if(legacyinternship.getIdTwo() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacyinternship.getIdTwo().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}else if(legacyinternship.getIdNo().equalsIgnoreCase("")) {
				if(legacyinternship.getIdTwo() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacyinternship.getIdTwo().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacyinternship.getEmployerSdl()== null ) {
				throw new Exception("Learner is not linked to an employer, please contact the merSETA Office");
			}
			
			if(legacyinternship.getQualification() == null) {
				throw new Exception("Qualification not found");
			}
			
			selectedCompany = companyService.findByLevyNumber(legacyinternship.getEmployerSdl());
			
			if(selectedCompany == null) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			if(selectedCompany.getCompanyStatus() != CompanyStatusEnum.Active) {
				throw new Exception("Employer is not yet active !!!, please activate employer before proceeding");
			}
			
			legacyinternship.setEmployer(selectedCompany);
			legacyinternship.setCompany(selectedCompany);
			
			LegacyInternshipService.instance().update(legacyinternship);
			
			super.setParameter("internshipId", this.legacyinternship.getId(), true);
			super.ajaxRedirect("/pages/tp/legacyLearnerRegistrationForm.jsf");
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			addErrorMessage(e.getMessage());
		}
	}
	
	public void redirectLegacyLearnership() {		
		
		try {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
			
			super.setParameter("apprenticeshipId", null, true);
			super.setParameter("bursaryId", null, true);
			super.setParameter("internshipId", null, true);
			super.setParameter("skillsprogrammeId", null, true);
			super.setParameter("tvetId", null, true);
			super.setParameter("unitstandardId", null, true);
			super.setParameter("secttwentyeightId", null, true);
			super.setParameter("legacyexperientialId", null, true);
			
			if(legacylearnership.getIdNo() == null || legacylearnership.getIdNo().equalsIgnoreCase("")) {
				throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
			}
			
			if(legacylearnership.getIdNo()  == null) {
				throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
			}else if(legacylearnership.getIdNo().equalsIgnoreCase("")) {
				throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
			}
			
			if(legacylearnership.getEmployerSdl()== null ) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			
			if(legacylearnership.getAccreditationNumber()== null ) {
				throw new Exception("Learner is not kinked to an training provider, please contact the merSETA Office");
			}
			
			if(legacylearnership.getLearnership() == null) {
				throw new Exception("Learnership not found");	
			}

			selectedCompany = companyService.findByLevyNumber(legacylearnership.getEmployerSdl());
			
			if(selectedCompany == null) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			
			if(selectedCompany.getCompanyStatus() != CompanyStatusEnum.Active) {
				throw new Exception("Employer is not yet active !!!, please activate employer before proceeding");
			}
			
			TrainingProviderApplication trainingProviderApplication= TrainingProviderApplicationService.instance().findByCertificateNumberOrAccreditationNumber(legacylearnership.getAccreditationNumber(), ApprovalEnum.Approved);
			
			if(trainingProviderApplication == null) {
				throw new Exception("Learner is not kinked to an training provider, please contact the merSETA Office");
			}
			
			legacylearnership.setEmployer(selectedCompany);
			legacylearnership.setTrainingProviderApplication(trainingProviderApplication);			
			LegacyLearnershipService.instance().update(legacylearnership);
			
			super.setParameter("learnershipId", this.legacylearnership.getId(), true);
			super.ajaxRedirect("/pages/tp/legacyLearnerRegistrationForm.jsf");
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			addErrorMessage(e.getMessage());
		}
	}
	
	public void redirectLegacySkillsprogramme() {
		try {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
				getSessionUI().setTask(null);
			}
			
			super.setParameter("apprenticeshipId", null, true);
			super.setParameter("bursaryId", null, true);
			super.setParameter("internshipId", null, true);
			super.setParameter("legacylearnership", null, true);
			super.setParameter("tvetId", null, true);
			super.setParameter("unitstandardId", null, true);
			super.setParameter("secttwentyeightId", null, true);
			super.setParameter("legacyexperientialId", null, true);
			
			if(legacyskillsprogramme.getIdNo() == null || legacyskillsprogramme.getIdNo().equalsIgnoreCase("")) {
				if(legacyskillsprogramme.getAlternateId() == null && legacyskillsprogramme.getAlternateId().equalsIgnoreCase("")) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}				
			}
			
			if(legacyskillsprogramme.getIdNo()  == null) {
				if(legacyskillsprogramme.getAlternateId() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacyskillsprogramme.getAlternateId().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}else if(legacyskillsprogramme.getIdNo().equalsIgnoreCase("")) {
				if(legacyskillsprogramme.getAlternateId() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacyskillsprogramme.getAlternateId().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacyskillsprogramme.getEmployerSDL()== null ) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			
			if(legacyskillsprogramme.getSkillsProgram() == null) {
				throw new Exception("SkillsProgram not found");
			}
			
			if(legacyskillsprogramme.getAccreditationNumber()== null ) {
				throw new Exception("Learner is not kinked to an training provider, please contact the merSETA Office");
			}
			
			selectedCompany = companyService.findByLevyNumber(legacyskillsprogramme.getEmployerSDL());
			
			if(selectedCompany == null) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			
			if(selectedCompany.getCompanyStatus() != CompanyStatusEnum.Active) {
				throw new Exception("Employer is not yet active !!!, please activate employer before proceeding");
			}
			
			TrainingProviderApplication trainingProviderApplication= TrainingProviderApplicationService.instance().findByCertificateNumberOrAccreditationNumber(legacyskillsprogramme.getAccreditationNumber(), ApprovalEnum.Approved);
			
			if(trainingProviderApplication == null) {
				throw new Exception("Learner is not kinked to an training provider, please contact the merSETA Office");
			}
			
			legacyskillsprogramme.setEmployer(selectedCompany);
			legacyskillsprogramme.setTrainingProviderApplication(trainingProviderApplication);			
			LegacySkillsProgrammeService.instance().update(legacyskillsprogramme);
			
			super.setParameter("skillsprogrammeId", this.legacyskillsprogramme.getId(), true);
			super.ajaxRedirect("/pages/tp/legacyLearnerRegistrationForm.jsf");
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			addErrorMessage(e.getMessage());
		}
	}
	
	public void redirectLegacytvet() {
		try {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
				getSessionUI().setTask(null);
			}
			
			super.setParameter("apprenticeshipId", null, true);
			super.setParameter("bursaryId", null, true);
			super.setParameter("internshipId", null, true);
			super.setParameter("legacylearnership", null, true);
			super.setParameter("skillsprogrammeId", null, true);
			super.setParameter("unitstandardId", null, true);
			super.setParameter("secttwentyeightId", null, true);
			super.setParameter("legacyexperientialId", null, true);
			
			if(legacytvet.getIdNo() == null && legacytvet.getIdNo().equalsIgnoreCase("")) {
				if(legacytvet.getIdTwo() == null && legacytvet.getIdTwo().equalsIgnoreCase("")) {
					 throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacytvet.getIdNo()  == null) {
				if(legacytvet.getIdTwo() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacytvet.getIdTwo().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}else if(legacytvet.getIdNo().equalsIgnoreCase("")) {
				if(legacytvet.getIdTwo() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacytvet.getIdTwo().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacytvet.getEmployerSDL()== null ) {
				throw new Exception("Learner is not linked to an employer, please contact the merSETA Office");
			}
			
			if(legacytvet.getQualification() == null) {
				throw new Exception("Qualification not found");
			}
			
			selectedCompany = companyService.findByLevyNumber(legacytvet.getEmployerSDL());
			
			if(selectedCompany == null) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			if(selectedCompany.getCompanyStatus() != CompanyStatusEnum.Active) {
				throw new Exception("Employer is not yet active !!!, please activate employer before proceeding");
			}
			
			legacytvet.setEmployer(selectedCompany);
			legacytvet.setCompany(selectedCompany);
			
			LegacyTvetService.instance().update(legacytvet);
			
			super.setParameter("tvetId", this.legacytvet.getId(), true);
			super.ajaxRedirect("/pages/tp/legacyLearnerRegistrationForm.jsf");
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			addErrorMessage(e.getMessage());
		}
	}
	
	public void redirectLegacyUnitStandard() {
		try {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
				getSessionUI().setTask(null);
			}
			
			super.setParameter("apprenticeshipId", null, true);
			super.setParameter("bursaryId", null, true);
			super.setParameter("internshipId", null, true);
			super.setParameter("legacylearnership", null, true);
			super.setParameter("skillsprogrammeId", null, true);
			super.setParameter("tvetId", null, true);
			super.setParameter("secttwentyeightId", null, true);
			super.setParameter("legacyexperientialId", null, true);
			
			if(legacyunitstandard.getIdNo() == null && legacyunitstandard.getIdNo().equalsIgnoreCase("")) {
				 if(legacyunitstandard.getAlternateId() == null && legacyunitstandard.getAlternateId().equalsIgnoreCase("")) {
					 throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				 }					
			}
			
			if(legacyunitstandard.getIdNo()  == null) {
				if(legacyunitstandard.getAlternateId() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacyunitstandard.getAlternateId().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}else if(legacyunitstandard.getIdNo().equalsIgnoreCase("")) {
				if(legacyunitstandard.getAlternateId() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacyunitstandard.getAlternateId().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacyunitstandard.getEmployerSDL() == null ) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			
			if(legacyunitstandard.getUnitStandard() == null) {
				throw new Exception("Unit Standard not found");
			}
			
			if(legacyunitstandard.getAccreditationNumber()== null ) {
				throw new Exception("Learner is not kinked to an training provider, please contact the merSETA Office");
			}
			
			selectedCompany = companyService.findByLevyNumber(legacyunitstandard.getEmployerSDL());
			
			if(selectedCompany == null) {
				throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
			}
			
			if(selectedCompany.getCompanyStatus() != CompanyStatusEnum.Active) {
				throw new Exception("Employer is not yet active !!!, please activate employer before proceeding");
			}
			
			TrainingProviderApplication trainingProviderApplication= TrainingProviderApplicationService.instance().findByCertificateNumberOrAccreditationNumber(legacyunitstandard.getAccreditationNumber(), ApprovalEnum.Approved);
			
			if(trainingProviderApplication == null) {
				throw new Exception("Learner is not kinked to an training provider, please contact the merSETA Office");
			}
			
			legacyunitstandard.setEmployer(selectedCompany);
			legacyunitstandard.setTrainingProviderApplication(trainingProviderApplication);			
			LegacyUnitStandardService.instance().update(legacyunitstandard);
						
			super.setParameter("unitstandardId", this.legacyunitstandard.getId(), true);
			super.ajaxRedirect("/pages/tp/legacyLearnerRegistrationForm.jsf");
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			addErrorMessage(e.getMessage());
		}
	}
	
	public void redirectLegacySectTwentyEight() {
		try {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
				getSessionUI().setTask(null);
			}			
			super.setParameter("apprenticeshipId", null, true);
			super.setParameter("bursaryId", null, true);
			super.setParameter("internshipId", null, true);
			super.setParameter("legacylearnership", null, true);
			super.setParameter("skillsprogrammeId", null, true);
			super.setParameter("tvetId", null, true);
			super.setParameter("unitstandardId", null, true);
			super.setParameter("legacyexperientialId", null, true);
			
			if(legacysecttwentyeight.getIdNo() == null && legacysecttwentyeight.getIdNo().equalsIgnoreCase("")) {
				if(legacysecttwentyeight.getAlternateIDNo() == null && legacysecttwentyeight.getAlternateIDNo().equalsIgnoreCase("")) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacysecttwentyeight.getIdNo()  == null) {
				if(legacysecttwentyeight.getAlternateIDNo() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacysecttwentyeight.getAlternateIDNo().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}else if(legacysecttwentyeight.getIdNo().equalsIgnoreCase("")) {
				if(legacysecttwentyeight.getAlternateIDNo() == null) {
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}else if(legacysecttwentyeight.getAlternateIDNo().equalsIgnoreCase("")){
					throw new Exception("RSA/Passport error when registering learner, please contact the merSETA Office");
				}
			}
			
			if(legacysecttwentyeight.getSdlNo() != null ) {
				selectedCompany =companyService.findByLevyNumber(legacysecttwentyeight.getSdlNo());
				if(selectedCompany == null) {
					throw new Exception("Learner is not kinked to an employer , please contact the merSETA Office");
				}
				if(selectedCompany.getCompanyStatus() != CompanyStatusEnum.Active) {
					throw new Exception("Employer is not yet active !!!, please activate employer before proceeding");
				}
				legacysecttwentyeight.setEmployer(selectedCompany);
			}
			
			if(legacysecttwentyeight.getWasdl() != null ) {
				provider =companyService.findByLevyNumber(legacysecttwentyeight.getWasdl());
				if(provider == null) {
					provider = selectedCompany;
				}
				if(provider.getCompanyStatus() != CompanyStatusEnum.Active) {
					throw new Exception("Employer is not yet, please activate company before proceeding");
				}
				legacysecttwentyeight.setCompany(provider);
			}else {
				if(provider == null && selectedCompany!=null) {
					provider = selectedCompany;
				}
			}
			legacysecttwentyeight.setEmployer(selectedCompany);
			legacysecttwentyeight.setCompany(provider);
			if(legacysecttwentyeight.getCompany() != null) {
				legacySECTTwentyEightService.update(legacysecttwentyeight);
			}
			if(legacysecttwentyeight.getQualification()== null) {
				throw new Exception("Qualification not found");
			}			
			super.setParameter("secttwentyeightId", this.legacysecttwentyeight.getId(), true);
			
			super.ajaxRedirect("/pages/tp/legacyLearnerRegistrationForm.jsf");
			//super.ajaxRedirect("/pages/arpl/arpltradetestlegacyregistration.jsf");
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
			addErrorMessage(e.getMessage());
		}
	}
	
	public void onRowToggleApprenticeship(LegacyApprenticeship legacyApprenticeship) {
		try {
			this.legacyapprenticeship = legacyApprenticeship;
			this.legacyapprenticeship.setLegacyApprenticeshipTradeTestList(legacyApprenticeshipTradeTestService.findByLegacyApprenticeship(legacyApprenticeship));
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}
	
	public void onRowToggleBursaryModel(LegacyBursary legacyBursary) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}
	
	public void onRowToggleInternshipModel(LegacyInternship legacyInternship) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}
	
	public void onRowToggleLearnershipModel(LegacyLearnership legacyLearnership) {
		try {
			this.legacylearnership = legacyLearnership;
			this.legacylearnership.setLegacyLearnershipAssessmentList(legacyLearnershipAssessmentService.findByLegacyLegacyLearnership(legacylearnership));
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}
	
	public void onRowToggleSkillsProgrammeModel(LegacySkillsProgramme legacySkillsprogramme) {
		try {
			this.legacyskillsprogramme = legacySkillsprogramme;
			this.legacyskillsprogramme.setLegacySkillsProgrammeAssessmentsList(legacySkillsProgrammeAssessmentsService.findByLegacySkillsProgramme(legacySkillsprogramme));
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}
	
	public void onRowToggleTvetModel(LegacyTvet legacyTvet) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}
	
	public void onRowToggleUnitStandardModel(LegacyUnitStandard legacyUnitstandard ) {
		try {
			this.legacyunitstandard = legacyUnitstandard;
			legacyunitstandard.setLegacyUnitStandardAssessmentList(legacyUnitStandardAssessmentService.findByLegacyUnitStandard(legacyUnitstandard));
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}
	
	public void onRowToggleTwentyEightModel(LegacySECTTwentyEight legacySecttwentyeight) {
		try {
			this.legacysecttwentyeight = legacySecttwentyeight;
			this.legacysecttwentyeight.setLegacysectiontwentyeighttradetestList(legacySectionTwentyEightTradeTestService.findByLegacySECTTwentyEight(legacySecttwentyeight));
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}
	}
	
	public void downloadReportApprenticeship(){
		try {

			apprenticeshipservice.downloadReport(selectedCompany.getLevyNumber());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReportBursary(){
		try {
			bursaryservice.downloadReport(selectedCompany.getLevyNumber());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReportInternship(){
		try {
			internshiservice.downloadReport(selectedCompany.getLevyNumber());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReportLearnership(){
		try {
			learnershipservice.downloadReport(selectedCompany.getLevyNumber());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReportSkillsProgramme(){
		try {
			skillsprogrammeservice.downloadReport(selectedCompany.getLevyNumber());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReportTVET(){
		try {
			tvetservice.downloadReport(selectedCompany.getLevyNumber());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReportUnitStandard(){
		try {
			unitstandardservice.downloadReport(selectedCompany.getLevyNumber());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void downloadReportARPLSection28(){
		try {
			legacySECTTwentyEightService.downloadReport(selectedCompany.getLevyNumber());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
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
	public List<Learners> getLearnersfilteredList() {
		return learnersfilteredList;
	}

	public void setLearnersfilteredList(List<Learners> learnersfilteredList) {
		this.learnersfilteredList = learnersfilteredList;
	}

	public List<LegacyBursary> getLegacybursaryList() {
		return legacybursaryList;
	}

	public void setLegacybursaryList(List<LegacyBursary> legacybursaryList) {
		this.legacybursaryList = legacybursaryList;
	}

	public List<LegacyBursary> getLegacybursaryfilteredList() {
		return legacybursaryfilteredList;
	}

	public void setLegacybursaryfilteredList(List<LegacyBursary> legacybursaryfilteredList) {
		this.legacybursaryfilteredList = legacybursaryfilteredList;
	}

	public LegacyBursary getLegacybursary() {
		return legacybursary;
	}

	public void setLegacybursary(LegacyBursary legacybursary) {
		this.legacybursary = legacybursary;
	}

	public LazyDataModel<LegacyBursary> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LegacyBursary> dataModel) {
		this.dataModel = dataModel;
	}

	public List<LegacyApprenticeship> getLegacyapprenticeshipList() {
		return legacyapprenticeshipList;
	}

	public void setLegacyapprenticeshipList(List<LegacyApprenticeship> legacyapprenticeshipList) {
		this.legacyapprenticeshipList = legacyapprenticeshipList;
	}

	public List<LegacyApprenticeship> getLegacyapprenticeshipfilteredList() {
		return legacyapprenticeshipfilteredList;
	}

	public void setLegacyapprenticeshipfilteredList(List<LegacyApprenticeship> legacyapprenticeshipfilteredList) {
		this.legacyapprenticeshipfilteredList = legacyapprenticeshipfilteredList;
	}

	public LegacyApprenticeship getLegacyapprenticeship() {
		return legacyapprenticeship;
	}

	public void setLegacyapprenticeship(LegacyApprenticeship legacyapprenticeship) {
		this.legacyapprenticeship = legacyapprenticeship;
	}

	public LazyDataModel<LegacyApprenticeship> getApprenticeshipdataModel() {
		return apprenticeshipdataModel;
	}

	public void setApprenticeshipdataModel(LazyDataModel<LegacyApprenticeship> apprenticeshipdataModel) {
		this.apprenticeshipdataModel = apprenticeshipdataModel;
	}

	public List<LegacyInternship> getLegacyinternshipList() {
		return legacyinternshipList;
	}

	public void setLegacyinternshipList(List<LegacyInternship> legacyinternshipList) {
		this.legacyinternshipList = legacyinternshipList;
	}

	public List<LegacyInternship> getLegacyinternshipfilteredList() {
		return legacyinternshipfilteredList;
	}

	public void setLegacyinternshipfilteredList(List<LegacyInternship> legacyinternshipfilteredList) {
		this.legacyinternshipfilteredList = legacyinternshipfilteredList;
	}

	public LegacyInternship getLegacyinternship() {
		return legacyinternship;
	}

	public void setLegacyinternship(LegacyInternship legacyinternship) {
		this.legacyinternship = legacyinternship;
	}

	public LazyDataModel<LegacyInternship> getInternshipdataModel() {
		return internshipdataModel;
	}

	public void setInternshipdataModel(LazyDataModel<LegacyInternship> internshipdataModel) {
		this.internshipdataModel = internshipdataModel;
	}

	public List<LegacyLearnership> getLegacylearnershipList() {
		return legacylearnershipList;
	}

	public void setLegacylearnershipList(List<LegacyLearnership> legacylearnershipList) {
		this.legacylearnershipList = legacylearnershipList;
	}

	public List<LegacyLearnership> getLegacylearnershipfilteredList() {
		return legacylearnershipfilteredList;
	}

	public void setLegacylearnershipfilteredList(List<LegacyLearnership> legacylearnershipfilteredList) {
		this.legacylearnershipfilteredList = legacylearnershipfilteredList;
	}

	public LegacyLearnership getLegacylearnership() {
		return legacylearnership;
	}

	public void setLegacylearnership(LegacyLearnership legacylearnership) {
		this.legacylearnership = legacylearnership;
	}

	public LazyDataModel<LegacyLearnership> getLearnershipdataModel() {
		return learnershipdataModel;
	}

	public void setLearnershipdataModel(LazyDataModel<LegacyLearnership> learnershipdataModel) {
		this.learnershipdataModel = learnershipdataModel;
	}

	public List<LegacySkillsProgramme> getLegacyskillsprogrammeList() {
		return legacyskillsprogrammeList;
	}

	public void setLegacyskillsprogrammeList(List<LegacySkillsProgramme> legacyskillsprogrammeList) {
		this.legacyskillsprogrammeList = legacyskillsprogrammeList;
	}

	public List<LegacySkillsProgramme> getLegacyskillsprogrammefilteredList() {
		return legacyskillsprogrammefilteredList;
	}

	public void setLegacyskillsprogrammefilteredList(List<LegacySkillsProgramme> legacyskillsprogrammefilteredList) {
		this.legacyskillsprogrammefilteredList = legacyskillsprogrammefilteredList;
	}

	public LegacySkillsProgramme getLegacyskillsprogramme() {
		return legacyskillsprogramme;
	}

	public void setLegacyskillsprogramme(LegacySkillsProgramme legacyskillsprogramme) {
		this.legacyskillsprogramme = legacyskillsprogramme;
	}

	public LazyDataModel<LegacySkillsProgramme> getSkillsprogrammedataModel() {
		return skillsprogrammedataModel;
	}

	public void setSkillsprogrammedataModel(LazyDataModel<LegacySkillsProgramme> skillsprogrammedataModel) {
		this.skillsprogrammedataModel = skillsprogrammedataModel;
	}

	public List<LegacyUnitStandard> getLegacyunitstandardList() {
		return legacyunitstandardList;
	}

	public void setLegacyunitstandardList(List<LegacyUnitStandard> legacyunitstandardList) {
		this.legacyunitstandardList = legacyunitstandardList;
	}

	public List<LegacyUnitStandard> getLegacyunitstandardfilteredList() {
		return legacyunitstandardfilteredList;
	}

	public void setLegacyunitstandardfilteredList(List<LegacyUnitStandard> legacyunitstandardfilteredList) {
		this.legacyunitstandardfilteredList = legacyunitstandardfilteredList;
	}

	public LegacyUnitStandard getLegacyunitstandard() {
		return legacyunitstandard;
	}

	public void setLegacyunitstandard(LegacyUnitStandard legacyunitstandard) {
		this.legacyunitstandard = legacyunitstandard;
	}

	public LazyDataModel<LegacyUnitStandard> getUnitstandarddataModel() {
		return unitstandarddataModel;
	}

	public void setUnitstandarddataModel(LazyDataModel<LegacyUnitStandard> unitstandarddataModel) {
		this.unitstandarddataModel = unitstandarddataModel;
	}

	public List<LegacyTvet> getLegacytvetList() {
		return legacytvetList;
	}

	public void setLegacytvetList(List<LegacyTvet> legacytvetList) {
		this.legacytvetList = legacytvetList;
	}

	public List<LegacyTvet> getLegacytvetfilteredList() {
		return legacytvetfilteredList;
	}

	public void setLegacytvetfilteredList(List<LegacyTvet> legacytvetfilteredList) {
		this.legacytvetfilteredList = legacytvetfilteredList;
	}

	public LegacyTvet getLegacytvet() {
		return legacytvet;
	}

	public void setLegacytvet(LegacyTvet legacytvet) {
		this.legacytvet = legacytvet;
	}

	public LazyDataModel<LegacyTvet> getTvetdataModel() {
		return tvetdataModel;
	}

	public void setTvetdataModel(LazyDataModel<LegacyTvet> tvetdataModel) {
		this.tvetdataModel = tvetdataModel;
	}

	public List<LegacySECTTwentyEight> getLegacysecttwentyeightList() { 
		return legacysecttwentyeightList;
	}

	public void setLegacysecttwentyeightList(List<LegacySECTTwentyEight> legacysecttwentyeightList) {
		this.legacysecttwentyeightList = legacysecttwentyeightList;
	}

	public List<LegacySECTTwentyEight> getLegacysecttwentyeightfilteredList() {
		return legacysecttwentyeightfilteredList;
	}

	public void setLegacysecttwentyeightfilteredList(List<LegacySECTTwentyEight> legacysecttwentyeightfilteredList) {
		this.legacysecttwentyeightfilteredList = legacysecttwentyeightfilteredList;
	}

	public LegacySECTTwentyEight getLegacysecttwentyeight() {
		return legacysecttwentyeight;
	}

	public void setLegacysecttwentyeight(LegacySECTTwentyEight legacysecttwentyeight) {
		this.legacysecttwentyeight = legacysecttwentyeight;
	}

	public LazyDataModel<LegacySECTTwentyEight> getTwentyEightdataModel() {
		return twentyEightdataModel;
	}

	public void setTwentyEightdataModel(LazyDataModel<LegacySECTTwentyEight> twentyEightdataModel) {
		this.twentyEightdataModel = twentyEightdataModel;
	}

	public boolean isViewLegacyData() {
		return viewLegacyData;
	}

	public void setViewLegacyData(boolean viewLegacyData) {
		this.viewLegacyData = viewLegacyData;
	}
}
