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
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersChange;
import haj.com.entity.CompanyLearnersLostTime;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.Learners;
import haj.com.entity.NonSetaCompany;
import haj.com.entity.NonSetaQualificationsCompletion;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.TransferRequestTypeEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.JasperService;
import haj.com.service.LearnersService;
import haj.com.service.NonSetaCompanyService;
import haj.com.service.NonSetaCompanyUsersService;
import haj.com.service.NonSetaQualificationsCompletionService;
import haj.com.service.UsersService;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "nonSetaCompaniesLearnersUI")
@ViewScoped
public class NonSetaCompaniesLearnersUI extends AbstractUI {

	
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	private NonSetaQualificationsCompletionService nonSetaQualificationsCompletionService = new NonSetaQualificationsCompletionService();
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
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private boolean viewLearnerData = false;
	private LazyDataModel<AuditorMonitorReview> auditorMonitorReviewdataModel;
	private LazyDataModel<Users> dataModelUsers;
	private Users learner;
	
	/* Entity Level */
	private NonSetaCompany nonSetaCompany;
	
	/* Service Levels */
	private LearnersService service;
	private NonSetaCompanyService nonSetaCompanyService;
	private NonSetaCompanyUsersService nonSetaCompanyUsersService;
	
	/* Lazy Data Models */
	private LazyDataModel<NonSetaCompany> dataModelNonSetaCompany;
	private LazyDataModel<CompanyLearners> dataModel;

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
		prepareNew();
		if (getSessionUI().isExternalParty()) {
			nonSetaCompanyInfo();
		} else {
			learnerUsersInfo();
		}
	}
	
	private void populateServiceLevels() throws Exception {
		if (nonSetaCompanyService == null) {
			nonSetaCompanyService = new NonSetaCompanyService();
		}
		if (nonSetaCompanyUsersService == null) {
			nonSetaCompanyUsersService = new NonSetaCompanyUsersService();
		}
		if (service == null){
			service = new LearnersService();
		}
	}

	public void nonSetaCompanyInfo() {
		dataModelNonSetaCompany = new LazyDataModel<NonSetaCompany>() {
			private static final long serialVersionUID = 1L;
			private List<NonSetaCompany> companyList = new ArrayList<>();
			@Override
			public List<NonSetaCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					companyList = nonSetaCompanyService.allNonSetaCompanyByUserAssigned(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
					dataModelNonSetaCompany.setRowCount(nonSetaCompanyService.countAllNonSetaCompanyByUserAssigned(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}
			@Override
			public Object getRowKey(NonSetaCompany object) {
				return object.getId();
			}
			@Override
			public NonSetaCompany getRowData(String rowKey) {
				for (NonSetaCompany obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void learnersInfo() {
		this.viewLearnerData = false;
		dataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();

			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (nonSetaCompany != null) {
						filters.put("nonSetaCompanyId", nonSetaCompany.getId());
						filters.put("terminated", LearnerStatusEnum.Terminated);
						retorno = service.allLearnersNonSectaCompany(first, pageSize, sortField, sortOrder, filters);
						// companyLearnersService.resolveAllData(retorno);
						dataModel.setRowCount(service.countAllLearnersNonSectaCompany(Learners.class, filters));
					} else {
						filters.put("userID", learner.getId());
						retorno = service.allLearnersByUser(first, pageSize, sortField, sortOrder, filters);
						// companyLearnersService.resolveAllData(retorno);
						dataModel.setRowCount(service.countByUser(Learners.class, filters));
					}

				} catch (Exception e) {
					logger.fatal(e);
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
	}

	public void learnerUsersInfo() {
		// this.viewLearnerData = false;
		dataModelUsers = new LazyDataModel<Users>() {
			private static final long serialVersionUID = 1L;
			private List<Users> retorno = new ArrayList<Users>();

			@Override
			public List<Users> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allLearnersAsUsers(first, pageSize, sortField, sortOrder, filters);
					dataModelUsers.setRowCount(service.countUsers(Learners.class, filters));
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

	public void viewCompanyLearnerData() {
		try {
			companyLearnersService.resolveAllData(companyLearners);
			this.viewLearnerData = true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
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

	public void redirectToLearnerRegForCompany() {
		super.setParameter("companyId", this.selectedCompany.getId(), true);
		super.ajaxRedirect("/pages/tp/learnerRegistrationForm.jsf");
	}

	public void redirectToLearnerUpdateForCompany() {
		super.setParameter("companyLearnersId", this.companyLearners.getId(), true);
		super.ajaxRedirect("/pages/tp/learnerRegistrationForm.jsf");
	}

	public void prepTransfer() {
		companyLearnersTransfer = new CompanyLearnersTransfer();
		companyLearnersTransfer.setCompanyLearners(companyLearners);
		if (getSessionUI().isLearner()) {
			companyLearnersTransfer.setTransferRequestType(TransferRequestTypeEnum.Learner);
		}
	}

	public void prepLostTime() {
		companyLearnersLostTime = new CompanyLearnersLostTime();
		companyLearnersLostTime.setCompanyLearners(companyLearners);
	}

	public void prepTermination() {
		companyLearnersTermination = new CompanyLearnersTermination();
		companyLearnersTermination.setCompanyLearners(companyLearners);
	}

	public void prepCompanyLearnersChange() {
		companyLearnersChange = new CompanyLearnersChange();
		companyLearnersChange.setCompanyLearners(companyLearners);
	}

	public void prepCompanyLearnersTradeTest() {
		companyLearnersTradeTest = new CompanyLearnersTradeTest();
		companyLearnersTradeTest.setCompanyLearners(companyLearners);
	}

	public void prepTrainingProviderVerfication() {
		trainingProviderMonitoring = new TrainingProviderVerfication();
		trainingProviderMonitoring.setTrainingProvider(selectedCompany);
	}

	public void prepNonMersetaQual() {
		nonSetaQualificationsCompletion = new NonSetaQualificationsCompletion();
		nonSetaQualificationsCompletion.setCompany(selectedCompany);
	}

	public void requestTransfer() {
		try {
			companyLearnersService.requestTransfer(companyLearnersTransfer, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void requestLostTime() {
		try {
			companyLearnersService.requestLostTime(companyLearnersLostTime, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void requestTermination() {
		try {
			companyLearnersService.requestTermination(companyLearnersTermination, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void requestChange() {
		try {
			companyLearnersService.requestChange(companyLearnersChange, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void requestTradeTestApplication() {
		try {
			companyLearnersService.requestTradeTestApplication(companyLearnersTradeTest, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
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

	public void requestNonSetaQualificationsCompletion() {
		try {
			//nonSetaQualificationsCompletionService.
			nonSetaQualificationsCompletionService.requestNonSetaQualificationsCompletion(nonSetaQualificationsCompletion, getSessionUI().getActiveUser());
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

	public void downloadLearnerAgreementForm() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js = new JasperService();
		try {
			UsersService usersService = new UsersService();
			CompanyService companyService = new CompanyService();
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

	public LazyDataModel<NonSetaCompany> getDataModelNonSetaCompany() {
		return dataModelNonSetaCompany;
	}

	public void setDataModelNonSetaCompany(LazyDataModel<NonSetaCompany> dataModelNonSetaCompany) {
		this.dataModelNonSetaCompany = dataModelNonSetaCompany;
	}

	public NonSetaCompany getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(NonSetaCompany nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

}
