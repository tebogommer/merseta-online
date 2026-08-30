package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUsersLookupTableUI.
 */
@ManagedBean(name = "companyUsersLookupTableUI")
@ViewScoped
public class CompanyUsersLookupTableUI extends AbstractUI {

	/** The company service. */
	private CompanyService companyService = new CompanyService();
	
	/** The company. */
	private Company company;

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The doc service. */
	private DocService docService = new DocService();
	
	/** The doc list. */
	private List<Doc> docList;
	
	/** The doc. */
	private Doc doc;

	/** The users filtered list. */
	private List<Users> usersFilteredList;
	
	/** The users list. */
	private List<Users> usersList;
	
	/** The user. */
	private Users user;
	
	/** The users service. */
	private UsersService usersService = new UsersService();

	/** The users problem. */
	private String usersProblem;

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
		this.company = new Company();
		this.user = new Users();
		this.doc = new Doc();
		if (super.getParameter("companyId", false) != null) {
			this.company = companyService.findByGUID((String) super.getParameter("companyId", false));
			prepareUsersCompany();
		}
	}

	/**
	 * Store file.
	 *
	 * @param event the event
	 */
	public void storeFile(FileUploadEvent event) {
		if (this.doc.getId() != null) {
			this.doc.setId(null);
			doc.setUsers(getSessionUI().getActiveUser());
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (this.doc.getVersionNo() != null) {
				Integer temp = this.doc.getVersionNo();
				this.doc.setVersionNo(temp + 1);
			}
			
			try {
				// docService.create(this.doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		findDocsForUser();
	}

	/**
	 * Check me shit.
	 */
	public void checkMeShit() {
		usersProblem = usersService.doCheck(this.user);
	}

	/**
	 * Redirect to learner reg for company.
	 */
	public void redirectToLearnerRegForCompany() {
		super.redirect("/admin/learnerRegistrationForm.jsf?userId=" + this.user.getEmailGuid().trim() + "&companyId=" + this.company.getCompanyGuid());
	}

	/**
	 * Redirect to company lookup table for learners reg.
	 */
	public void redirectToCompanyLookupTableForLearnersReg() {
		super.redirect("/admin/companyLookupTableForLearnersReg.jsf");
	}

	/**
	 * Find docs for user.
	 */
	public void findDocsForUser() {
		this.docList = new ArrayList<>();
		try {
			this.docList = docService.findByUsersAndCompanyNull(this.user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prepare users company.
	 */
	public void prepareUsersCompany() {
		if (this.company.getId() != null) {
			this.usersFilteredList = new ArrayList<>();
			this.usersList = new ArrayList<>();
			this.usersList = companyUsersService.findUsersTypeOfCompany(ConfigDocProcessEnum.Learner, this.company.getId());
			if (this.usersList.size() > 0)
				addInfoMessage("Learners found");
			else
				addWarningMessage("No Learners assigned to company");
		}
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
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * Gets the users list.
	 *
	 * @return the users list
	 */
	public List<Users> getUsersList() {
		return usersList;
	}

	/**
	 * Sets the users list.
	 *
	 * @param usersList the new users list
	 */
	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}

	/**
	 * Gets the users filtered list.
	 *
	 * @return the users filtered list
	 */
	public List<Users> getUsersFilteredList() {
		return usersFilteredList;
	}

	/**
	 * Sets the users filtered list.
	 *
	 * @param usersFilteredList the new users filtered list
	 */
	public void setUsersFilteredList(List<Users> usersFilteredList) {
		this.usersFilteredList = usersFilteredList;
	}

	/**
	 * Gets the doc list.
	 *
	 * @return the doc list
	 */
	public List<Doc> getDocList() {
		return docList;
	}

	/**
	 * Sets the doc list.
	 *
	 * @param docList the new doc list
	 */
	public void setDocList(List<Doc> docList) {
		this.docList = docList;
	}

	/**
	 * Gets the users problem.
	 *
	 * @return the users problem
	 */
	public String getUsersProblem() {
		return usersProblem;
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

}
