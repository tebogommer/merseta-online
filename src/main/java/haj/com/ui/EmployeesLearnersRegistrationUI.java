package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Learners;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.enums.GenerateAddEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.LearnersService;
import haj.com.service.TrainingProviderVerficationService;
import haj.com.service.UsersDisabilityService;
import haj.com.service.UsersLanguageService;

@ManagedBean(name = "employeesLearnersRegistrationUI")
@ViewScoped
public class EmployeesLearnersRegistrationUI extends AbstractUI {

	private List<Learners> learnersfilteredList = null;
	/** The company service. */
	private CompanyService companyService = new CompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private UsersLanguageService usersLanguageService = new UsersLanguageService();
	private UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
	private LearnersService service = new LearnersService();
	private TrainingProviderVerficationService trainingProviderVerficationService = new TrainingProviderVerficationService();

	private LazyDataModel<Company> companyDataModel;
	private Company selectedCompany;
	private LazyDataModel<CompanyLearners> dataModel;
	private CompanyLearners companyLearners = null;
	private TrainingProviderVerfication trainingProviderVerfication;
	private GenerateAddEnum generateAddEnum;
	private boolean viewLearnerDetails = false;
	
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
		companyInfo();
	}
	
	public void companyInfo() {
		companyDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					companyList = companyService.allCompanySetaCompaniesNew(Company.class, first, pageSize, sortField, sortOrder, filters);
					companyDataModel.setRowCount(companyService.countSetaCompaniesNew(Company.class, filters));

				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}

			@Override
			public Object getRowKey(Company object) {
				// TODO Auto-generated method stub
				return object.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void learnersInfo() {
		
		dataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();

			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (selectedCompany != null) {
						filters.put("companyID", selectedCompany.getId());
						retorno = service.allLearnersByCompany(first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.countByCompany(Learners.class, filters));
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
	}
	
	public void redirectToLearnerRegForCompany() {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getId() != null ) {
			getSessionUI().setTask(null);
		}
		//super.setParameter("companyId", this.selectedCompany.getId(), true);
		//super.ajaxRedirect("/pages/employeelearnerregistrationform.jsf");
		super.setParameter("employerId", this.selectedCompany.getId(), true);		
		super.ajaxRedirect("/pages/tp/learnerregistrationsdf.jsf");
	}
	
	public void prepTrainingProviderVerfication() {
		trainingProviderVerfication = new TrainingProviderVerfication();
		trainingProviderVerfication.setTrainingProvider(selectedCompany);
		trainingProviderVerfication.setCompanyLearners(companyLearners);		
	}
	
	public void requestVerificationLearner() {
		try {
			if(trainingProviderVerfication == null) {
				trainingProviderVerfication = new TrainingProviderVerfication();
				trainingProviderVerfication.setTrainingProvider(selectedCompany);
				trainingProviderVerfication.setCompanyLearners(companyLearners);
			}
			if(trainingProviderVerfication == null) {
				throw new Exception("Error with the verification application");
			}
			if(trainingProviderVerfication.getCompanyLearners() == null) {
				throw new Exception("Error with the laerner application");
			}
			if(trainingProviderVerficationService.findByCompanyLearner(trainingProviderVerfication.getCompanyLearners()).size() > 0){
				throw new Exception("Already applied for verification");
			}
			trainingProviderVerfication.setGenerateAddEnum(generateAddEnum);
			if(trainingProviderVerfication.getGenerateAddEnum() == null) {
				throw new Exception("Please select if you wish to generate certificate or add certificate details");
			}
			
			trainingProviderVerfication.setCreatedByMerseta(true);
			CompanyLearnersService.instance().requestVerificationLearner(trainingProviderVerfication, getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
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
	
	public void viewLearnerDetails() {
		try {
			viewLearnerDetails = true;
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

	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
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

	public TrainingProviderVerfication getTrainingProviderVerfication() {
		return trainingProviderVerfication;
	}

	public void setTrainingProviderVerfication(TrainingProviderVerfication trainingProviderVerfication) {
		this.trainingProviderVerfication = trainingProviderVerfication;
	}

	public GenerateAddEnum getGenerateAddEnum() {
		return generateAddEnum;
	}

	public void setGenerateAddEnum(GenerateAddEnum generateAddEnum) {
		this.generateAddEnum = generateAddEnum;
	}

	public boolean isViewLearnerDetails() {
		return viewLearnerDetails;
	}

	public void setViewLearnerDetails(boolean viewLearnerDetails) {
		this.viewLearnerDetails = viewLearnerDetails;
	}
}
