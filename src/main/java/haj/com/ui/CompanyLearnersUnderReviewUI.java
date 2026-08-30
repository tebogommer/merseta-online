package haj.com.ui;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Learners;
import haj.com.entity.Users;
import haj.com.entity.datamodel.LearnersDataModel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "companyLearnersUnderReviewUI")
@ViewScoped
public class CompanyLearnersUnderReviewUI extends AbstractUI {
	private List<Learners> learnersfilteredList = null;
	private LazyDataModel<CompanyLearners> dataModel;
	private CompanyLearners companyLearners;
	private CompanyLearnersService companyLearnersService = new haj.com.service.CompanyLearnersService();
	private String validationError ;
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
		learnersInfo();
	}

	public void learnersInfo() {
		dataModel = new LearnersDataModel();
	}
	
	public void prepTasks() {
		
	}
	
	public void submitTasks() {
		try {
			companyLearnersService.completeCompanyLearnersUnderReviewToRegion(companyLearners, getSessionUI().getActiveUser());
			learnersInfo();
			addInfoMessage("Learner submitted for approval");
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('dlgLostTime').hide()");
		} catch (javax.validation.ConstraintViolationException e) {
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('dlgLostTime').hide()");
			extractValidationErrors(e);
			validationError = extractValidationErrorsReturnString(e).replaceAll("\\<.*?\\>", "");;
			addErrorMessage("Validiation Exception, please refer to the error messages.");
		}catch (ValidationException e) {
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('dlgLostTime').hide()");
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('dlgLostTime').hide()");
			e.printStackTrace();
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
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


	public boolean checkRequireGaurdian(Users user) {
		boolean requireGaurdian = user.getDateOfBirth() != null && GenericUtility.getAge(user.getDateOfBirth()) < 18;
		
		return requireGaurdian;
		
	}

	public CompanyLearners getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(CompanyLearners companyLearners) {
		this.companyLearners = companyLearners;
	}

	public String getValidationError() {
		return validationError;
	}

	public void setValidationError(String validationError) {
		this.validationError = validationError;
	}
}
