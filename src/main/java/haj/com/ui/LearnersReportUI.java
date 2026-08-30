package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.bean.CompanyLearnersBean;
import haj.com.entity.Company;
import haj.com.entity.SDPCompany;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.SdpType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyUsersService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SDPCompanyService;
import haj.com.service.TrainingProviderApplicationService;

@ManagedBean(name = "learnersReportUI")
@ViewScoped
public class LearnersReportUI extends AbstractUI {
	
	private SDFCompanyService sDFCompanyService = new SDFCompanyService();


	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private List<Company> companies = null;
	private Company selectedCompany;


	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private boolean viewLearnerData = false;

	private TrainingProviderApplication selectedTrainingProvider;
	private Boolean primaryOrSecondarySDF;
	private Boolean companyContactRegisterLearner;
	private SdpType sdpType = null;
	private Boolean registerLearners = false;
	
	private List<CompanyLearnersBean> companyLearnersList;
	
	private SDPCompany sdpCompanyLink = null;
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();


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
		if (getSessionUI().isExternalParty()) {
			companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
		} else {
			learnerUsersInfo();
		}
	}
	
	private void learnerUsersInfo() throws Exception {
		companyLearnersList=companyLearnersService.generateCompanyLearnerByCompanyReport(selectedCompany);
	}

	public void checkLearnerInfo() {
		prepareNew();
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
	
	private void prepareNew() {
		companyLearnersList = new ArrayList<>();
	}

	private void learnersInfo() throws Exception {
		companyLearnersList=companyLearnersService.generateCompanyLearnerByCompanyReport(selectedCompany);
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
				throw new Exception("Kindly be advised that you require the relevant authorisation to access this information.");
			}
		} else {
			registerLearners = true;
		}
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public boolean isViewLearnerData() {
		return viewLearnerData;
	}

	public TrainingProviderApplication getSelectedTrainingProvider() {
		return selectedTrainingProvider;
	}

	public Boolean getPrimaryOrSecondarySDF() {
		return primaryOrSecondarySDF;
	}

	public Boolean getRegisterLearners() {
		return registerLearners;
	}

	public List<CompanyLearnersBean> getCompanyLearnersList() {
		return companyLearnersList;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public void setViewLearnerData(boolean viewLearnerData) {
		this.viewLearnerData = viewLearnerData;
	}

	public void setSelectedTrainingProvider(TrainingProviderApplication selectedTrainingProvider) {
		this.selectedTrainingProvider = selectedTrainingProvider;
	}

	public void setPrimaryOrSecondarySDF(Boolean primaryOrSecondarySDF) {
		this.primaryOrSecondarySDF = primaryOrSecondarySDF;
	}

	public void setRegisterLearners(Boolean registerLearners) {
		this.registerLearners = registerLearners;
	}

	public void setCompanyLearnersList(List<CompanyLearnersBean> companyLearnersList) {
		this.companyLearnersList = companyLearnersList;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public SDPCompany getSdpCompanyLink() {
		return sdpCompanyLink;
	}

	public void setSdpCompanyLink(SDPCompany sdpCompanyLink) {
		this.sdpCompanyLink = sdpCompanyLink;
	}

}
