package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUsersService;
import haj.com.service.UsersService;
import haj.com.service.UsersTrainingProviderService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnerUI.
 */
@ManagedBean(name = "learnerUI")
@ViewScoped
public class LearnerUI extends AbstractUI {

	/** The form user. */
	private Users formUser;
	
	/** The service. */
	private UsersService service = new UsersService();

	/** The company. */
	private Company company;
	
	/** The training provider. */
	private Company trainingProvider;

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	
	/** The users training provider service. */
	private UsersTrainingProviderService usersTrainingProviderService = new UsersTrainingProviderService();

	/** The doc. */
	private Doc doc;
	
	/** The docs. */
	private List<Doc> docs;

	/** The qualification list. */
	private List<Qualification> qualificationList;
	
	/** The qualification. */
	private Qualification qualification;

	/** The search user passport or id UI. */
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;
	
	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;

	/** The final bool. */
	private Boolean trainerBool, finalBool;

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
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	private void runInit() throws Exception {
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
		prepareNew();
	}

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractUIInterface#callBackMethod(java.lang.Object)
	 */
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				this.formUser = (Users) object;
				service.prepareNewRegistration(ConfigDocProcessEnum.Learner, this.formUser);
			} else if (object instanceof Company && !this.trainerBool) {
				this.company = (Company) object;
			} else if (object instanceof Company && this.trainerBool) {
				this.trainingProvider = (Company) object;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Creates the company learner.
	 */
	public void createCompanyLearner() {
		try {
			if (this.docs.size() > 0) {
				if (this.formUser.getId() == null) {
					service.createLearnerWithCompanyTrainingProviderAndDocs(this.formUser, this.company, this.trainingProvider);
					addInfoMessage(super.getEntryLanguage("email.has.been.sent.to.learner"));
					prepareNew();
				} else if (company.getId() != null && formUser.getId() != null) {
					usersTrainingProviderService.createUsersTrainingProvider(this.formUser, this.trainingProvider);
					companyUsersService.createCompanyUsersCheckExistence(this.trainingProvider, this.formUser);
					service.createLearnerAndDocs(this.formUser, trainingProvider);
				} else
				super.addWarningMessage(super.getEntryLanguage("issue.with.registration"));
				prepareNew();
				addInfoMessage(super.getEntryLanguage("registered.to.company.and.training.provider.learner.is.already.on.system"));
			} else {
				super.addWarningMessage(super.getEntryLanguage("no.documents.uploaded"));
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			this.trainerBool = false;
			this.finalBool = false;
			this.company = null;
			this.trainingProvider = null;
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare new.
	 */
	public void prepareNew() {
		this.formUser = null;
		this.company = null;
		this.trainingProvider = null;
		this.formUser = new Users();
		formUser.setAdmin(false);
		formUser.setDoneSearch(false);
		formUser.setRegFieldsDone(false);
		this.docs = new ArrayList<>();
		this.trainerBool = false;
		this.finalBool = false;
	}

	/**
	 * Store file.
	 *
	 * @param event the event
	 */
	public void storeFile(FileUploadEvent event) {
		doc.setUsers(this.formUser);
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUpload').hide()");
		this.docs.add(this.doc);
	}

	/**
	 * Prep doc.
	 *
	 * @param index the index
	 */
	public void prepDoc(int index) {
		this.doc = this.formUser.getDocs().get(index);
	}

	/**
	 * Done user bit.
	 */
	public void doneUserBit() {
		this.formUser.setRegFieldsDone(true);
	}

	/**
	 * Done company bit.
	 */
	public void doneCompanyBit() {
		this.company.setRegDone(true);
		this.trainerBool = true;
		getSearchCompanyUI().setObject(this);
	}

	/**
	 * Done trainer bit.
	 */
	public void doneTrainerBit() {
		this.trainingProvider.setRegDone(true);
		this.finalBool = true;
	}

	/**
	 * Gets the form user.
	 *
	 * @return the form user
	 */
	public Users getFormUser() {
		return formUser;
	}

	/**
	 * Sets the form user.
	 *
	 * @param formUser the new form user
	 */
	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the search user passport or id UI.
	 *
	 * @return the search user passport or id UI
	 */
	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	/**
	 * Sets the search user passport or id UI.
	 *
	 * @param searchUserPassportOrIdUI the new search user passport or id UI
	 */
	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	/**
	 * Gets the search company UI.
	 *
	 * @return the search company UI
	 */
	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	/**
	 * Sets the search company UI.
	 *
	 * @param searchCompanyUI the new search company UI
	 */
	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 *
	 * @param doc the new doc
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	/**
	 * Gets the qualification list.
	 *
	 * @return the qualification list
	 */
	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	/**
	 * Sets the qualification list.
	 *
	 * @param qualificationList the new qualification list
	 */
	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	/**
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification the new qualification
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * Gets the docs.
	 *
	 * @return the docs
	 */
	public List<Doc> getDocs() {
		return docs;
	}

	/**
	 * Sets the docs.
	 *
	 * @param docs the new docs
	 */
	public void setDocs(List<Doc> docs) {
		this.docs = docs;
	}

	/**
	 * Gets the training provider.
	 *
	 * @return the training provider
	 */
	public Company getTrainingProvider() {
		return trainingProvider;
	}

	/**
	 * Sets the training provider.
	 *
	 * @param trainingProvider the new training provider
	 */
	public void setTrainingProvider(Company trainingProvider) {
		this.trainingProvider = trainingProvider;
	}

	/**
	 * Gets the trainer bool.
	 *
	 * @return the trainer bool
	 */
	public Boolean getTrainerBool() {
		return trainerBool;
	}

	/**
	 * Sets the trainer bool.
	 *
	 * @param trainerBool the new trainer bool
	 */
	public void setTrainerBool(Boolean trainerBool) {
		this.trainerBool = trainerBool;
	}

	/**
	 * Gets the final bool.
	 *
	 * @return the final bool
	 */
	public Boolean getFinalBool() {
		return finalBool;
	}

	/**
	 * Sets the final bool.
	 *
	 * @param finalBool the new final bool
	 */
	public void setFinalBool(Boolean finalBool) {
		this.finalBool = finalBool;
	}

}
