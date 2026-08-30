package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

import haj.com.bean.CompanyLearnersBean;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.Learners;
import haj.com.entity.Users;
import haj.com.entity.UsersLanguage;
import haj.com.entity.datamodel.CompanyLearnersDataModel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.DocService;
import haj.com.service.UsersLanguageService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "companyLearnersMonitorUI")
@ViewScoped
public class CompanyLearnersMonitorUI extends AbstractUI {
	private List<Learners> learnersfilteredList = null;
	private LazyDataModel<CompanyLearners> dataModel;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private CompanyLearners companyLearners;
	private Doc doc;
	private List<UsersLanguage> usersLanguageList    = new ArrayList<>();
	private UsersLanguage       usersLanguage                 = new UsersLanguage();
	private UsersLanguageService usersLanguageService = new UsersLanguageService();
	private boolean             homeLanguageSelected          = false;
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		learnersInfo();
	}
	
	public void learnersInfo() {
		dataModel = new CompanyLearnersDataModel();
	}
	
	public void prepareCompanyLearners() {
		
	}
	
	public void prepareCompanyLearnersLanguage() {
		try {
			if(companyLearners != null && companyLearners.getUser() != null) {
				usersLanguageList = usersLanguageService.findByUser(companyLearners.getUser());
				if(usersLanguageList != null && usersLanguageList.size() >0) {
					homeLanguagePreCheck();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareCompanyLearnersDocuments() {
		try {
			companyLearners.setDocs(DocService.instance().searchByTargetKeyAndClass(CompanyLearners.class.getName(), companyLearners.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
		
	public void companyLearnersUpdate() {
		try {
			companyLearnersService.update(companyLearners);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void companyLearnersUpdateLanguage() {
		try {
			homeLanguageCheck();
			for(UsersLanguage ul: usersLanguageList) {
				usersLanguageService.update(ul);
			}
			addInfoMessage(super.getEntryLanguage("update.successful"));
			learnersInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}	
	
	private void homeLanguageCheck() throws Exception {
		int count = 0;
		for (UsersLanguage ul : usersLanguageList) {
			if(ul.getHomeLanguage() != null && ul.getHomeLanguage()) {
				count ++;
			}
		}		
		if(count == 0) {
			throw new Exception("Select home language");
		}else if(count > 1) {
			throw new Exception("Select one home language");
		}
	}
	
	public void companyLearnersUpdateDocument() {
		try {
			doc.setTargetClassTemp(doc.getTargetClass());
			doc.setTargetKeyTemp(doc.getTargetKey());
			doc.setTargetClass(null);
			doc.setTargetKey(null);
			DocService.instance().update(doc);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			prepareCompanyLearnersDocuments();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addLanguage() {
		try {

			languagePreCheck();
			if (usersLanguage != null && usersLanguage.getRead() != null) {
				//usersLanguageList.add(usersLanguage);
				if (usersLanguage.getHomeLanguage() != null && usersLanguage.getHomeLanguage()) {
					homeLanguageSelected = true;
				}
				usersLanguage.setUser(companyLearners.getUser());
				usersLanguageService.create(usersLanguage);
				prepareCompanyLearnersLanguage();
				usersLanguage = new UsersLanguage();				
			} else {
				addErrorMessage("Select a language or speak, read and write");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}

	}
	
	public void removeLanguageFromList() {
		try {
			usersLanguageService.delete(usersLanguage);
			prepareCompanyLearnersLanguage();
			usersLanguage = new UsersLanguage();		
			addInfoMessage("Language removed");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void homeLanguagePreCheck() throws Exception {
		for (UsersLanguage ul : usersLanguageList) {
			if(ul.getHomeLanguage() != null && ul.getHomeLanguage()) {
				homeLanguageSelected= true;
			}
		}
	}	
	
	public void languagePreCheck() throws Exception {
		if(usersLanguage != null && usersLanguage.getLanguage() != null) {
			for (UsersLanguage ul : usersLanguageList) {
				if (ul != null && ul.getLanguage() != null && usersLanguage != null && usersLanguage.getId() != null && usersLanguage.getRead() != null  && ul.getLanguage().getId() == usersLanguage.getLanguage().getId()) {
					throw new Exception("Language already exist on your language list");
				}
			}
		}else {
			throw new Exception("Select a language");
		}		
	}
	
	public void usersHomeLanguageChange() throws Exception {
		try {
			usersLanguageService.update(usersLanguage);
			for (UsersLanguage ul : usersLanguageList) {
				if(ul.getId() != usersLanguage.getId()) {
					ul.setHomeLanguage(true);
					usersLanguageService.update(ul);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void startDateFilterData() {
		try {
			calculateEndDate();		
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void calculateEndDate() {
		this.companyLearners.setCompletionDate(GenericUtility.addMonthsToDate(this.companyLearners.getCommencementDate(), this.companyLearners.getInterventionType().getDuration()));
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

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<UsersLanguage> getUsersLanguageList() {
		return usersLanguageList;
	}

	public UsersLanguage getUsersLanguage() {
		return usersLanguage;
	}

	public void setUsersLanguageList(List<UsersLanguage> usersLanguageList) {
		this.usersLanguageList = usersLanguageList;
	}

	public void setUsersLanguage(UsersLanguage usersLanguage) {
		this.usersLanguage = usersLanguage;
	}

	public boolean isHomeLanguageSelected() {
		return homeLanguageSelected;
	}

	public void setHomeLanguageSelected(boolean homeLanguageSelected) {
		this.homeLanguageSelected = homeLanguageSelected;
	}
}
