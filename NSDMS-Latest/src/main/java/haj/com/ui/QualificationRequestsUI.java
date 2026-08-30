package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.springframework.beans.BeanUtils;

import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.QualificationsCurriculumDevelopment;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.LearnershipDevelopmentRegistrationService;
import haj.com.service.QualificationsCurriculumDevelopmentService;
import haj.com.service.TasksService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SdfUI.
 */
@ManagedBean(name = "qualificationRequestsUI")
@ViewScoped
public class QualificationRequestsUI extends AbstractUI {

	private Qualification selectedRealignmentQualification = null;
	private Qualification selectedReviewQualification = null;
	
	/** Entity Level */
	private QualificationsCurriculumDevelopment qualificationscurriculumdevelopment = null;
	private Company selectedCompany = null;
	private Doc doc = null;

	/** Service Level */
	private QualificationsCurriculumDevelopmentService service = new QualificationsCurriculumDevelopmentService();
	private LearnershipDevelopmentRegistrationService learnershipDevelopmentRegistrationService = null;
	private CompanyUsersService companyUsersService = null;

	/** Array Lists */
	private List<Company> companies = null;
	private List<String> reqiredFeilds = null;
	
	/** Strings */
	//private String renderingConditions = null;
	
	/**The company*/
	private Company company=new Company();
	/**Company Postal Address*/
	private Address compPostalAddress=new Address();
	/**Company Residential Address*/
	private Address compResidentialAddress=new Address();
	
	private boolean copyAddress;
	
	private boolean doneWithCompanyInfo;
	
	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;
	
	/**Company Search type*/
	private String searchType;

	@PostConstruct
	public void init() {
		try {
			runInit();
			getSearchCompanyUI().setObject(this);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		doneWithCompanyInfo=false;
		populateServiceLayers();
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			selectedCompany.setLearnershipDevelopmentRegistrations(learnershipDevelopmentRegistrationService.findByCompany(selectedCompany));
			qualificationscurriculumdevelopment = new QualificationsCurriculumDevelopment();
			
		} else {
			//qualificationscurriculumdevelopmentInfo()
			qualificationscurriculumdevelopment = new QualificationsCurriculumDevelopment();
		}
		populateLookUpTableLists();
	}
	
	/**
	 * Get all QualificationsCurriculumDevelopment for data table
	 * 
	 * @author TechFinium
	 * @see QualificationsCurriculumDevelopment
	 */
	public void qualificationscurriculumdevelopmentInfo() {
		try {
			companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
			for (Company company : companies) {
				company.setQualificationsCurriculumDevelopmentList(service.findByCompany(company));
			}
		} catch (Exception e) {
			this.addErrorMessage(e.getMessage());
		}
	}
	
	public void prepNewQualificationsCurriculumDevelopment() {
		
		if(company.getId()== null)
		{
			try {
				companyUsersService.createCompanyUsers(company, this.getSessionUI().getActiveUser());
			} catch (Exception e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			}
		}
		
		this.qualificationscurriculumdevelopment = new QualificationsCurriculumDevelopment();
		this.qualificationscurriculumdevelopment.setCreateDate(new Date(System.currentTimeMillis()));
		this.qualificationscurriculumdevelopment.setCreateUser(this.getSessionUI().getActiveUser());
		this.qualificationscurriculumdevelopment.setCompany(company);
		if (qualificationscurriculumdevelopment.getReAlignmentQualificationList() == null) {
			qualificationscurriculumdevelopment.setReAlignmentQualificationList(new ArrayList() {});
		}
		if (qualificationscurriculumdevelopment.getReviewQualificationList() == null) {qualificationscurriculumdevelopment.setReviewQualificationList(new ArrayList() {	});
		}

		try {
			service.prepareNewDocs(qualificationscurriculumdevelopment);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void submitQualificationsCurriculumDevelopment() {
		try {
			convertTransListsIntoStrings();
			service.submitQualificationsCurriculumDevelopment(qualificationscurriculumdevelopment,getSessionUI().getActiveUser());
			
			addInfoMessage(super.getEntryLanguage("update.successful"));
			this.qualificationscurriculumdevelopment = new QualificationsCurriculumDevelopment();
			this.company.setDoneSearch(false);
			doneWithCompanyInfo = false;
			qualificationscurriculumdevelopmentInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts the transient object populated into a string list for storing to
	 * the database
	 */
	private void convertTransListsIntoStrings() {
		try {
			String realignmentQualification = GenericUtility.convertListIntoSeperatedString(qualificationscurriculumdevelopment.getReAlignmentQualificationList(), ",");
			String reviewQualification = GenericUtility.convertListIntoSeperatedString(qualificationscurriculumdevelopment.getReviewQualificationList(), ",");
			qualificationscurriculumdevelopment.setReAlignmentQualificationIdList(realignmentQualification);
			qualificationscurriculumdevelopment.setReviewQualificationIdList(reviewQualification);
		} catch (Exception exception) {
			this.addErrorMessage(exception.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void addQualificationToRealignmentTable(SelectEvent event) {
		if (qualificationscurriculumdevelopment.getReAlignmentQualificationList() == null) {
			qualificationscurriculumdevelopment.setReAlignmentQualificationList(new ArrayList() {});
		}
		qualificationscurriculumdevelopment.getReAlignmentQualificationList().add(this.getSelectedRealignmentQualification());
	}

	/**
	 * 
	 */
	public void removeFromQualificationToRealignmentTable() {
		qualificationscurriculumdevelopment.getReAlignmentQualificationList().remove(this.getSelectedRealignmentQualification());
	}

	/**
	 * 
	 */
	public void addQualificationToReviewTable(SelectEvent event) {
		if (qualificationscurriculumdevelopment.getReviewQualificationList() == null) {
			qualificationscurriculumdevelopment.setReviewQualificationList(new ArrayList() {});
		}
		qualificationscurriculumdevelopment.getReviewQualificationList().add(this.getSelectedReviewQualification());
	}

	/**
	 * 
	 */
	public void removeFromQualificationToReviewTable() {
		qualificationscurriculumdevelopment.getReviewQualificationList().remove(this.getSelectedReviewQualification());
	}
	/**
	 * Populates service levels
	 * 
	 * @throws Exception
	 */
	private void populateServiceLayers() throws Exception {
		companyUsersService = new CompanyUsersService();
		learnershipDevelopmentRegistrationService = new LearnershipDevelopmentRegistrationService();		
	}
	
	/**
	 * Populates look up tables
	 * @throws Exception
	 */
	private void populateLookUpTableLists() throws Exception{
	}

	/**
	 * Populates a list of companies the TP is assigned to
	 * 
	 * @throws Exception
	 */
	private void populateAssignedCompaniesTP() throws Exception {
		// change to lazy load
		companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
		setCompaniesLearnerships();
	}
	
	/**
	 * Sets the learnerships against a company
	 * @throws Exception
	 */
	private void setCompaniesLearnerships() throws Exception{
		for (Company company : companies) {
			company.setLearnershipDevelopmentRegistrations(learnershipDevelopmentRegistrationService.findByCompany(company));
		}
	}

	/**
	 * Method for row toggel
	 * @param event
	 */
	public void onRowToggle(ToggleEvent event) {

	}
	
	/**
	 * Preps a new instance of the learnership for user to add information
	 * @throws Exception
	 */
	public void prepNewLearnership() throws Exception{
		try {
			selectedCompany.setLearnershipDevelopmentRegistrations(learnershipDevelopmentRegistrationService.findByCompany(selectedCompany));

		} catch (Exception e) {
			addErrorMessage(e.getMessage() ,e);
		}
	}
	
	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(compPostalAddress);
		}
	}
	
	
	/**
	 * Preps a new instance of the learnership for user to add information
	 * @throws Exception
	 */
	//Do not remove
	public void prepNewExternalQualification() throws Exception{
		try {	
			prepNewQualificationsCurriculumDevelopment();
			if(company.getId()==null)
			{
				validateCompany();
			}
			doneWithCompanyInfo=true;
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage() ,e);
		}
	}
	
	/**
	 * Cancels new insert oflearnershipDevelopmentRegistration
	 */
	public void cancelInsert(){
		try {
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	/**
	 * Cancels new insert oflearnershipDevelopmentRegistration
	 */
	public void cancelExternalInsert(){
		try {
			doneWithCompanyInfo=false;
			company=new Company();
			compPostalAddress=new Address();
			compResidentialAddress=new Address();
			copyAddress=false;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}

	/**
	 * Creates new instance of learnershipDevelopmentRegistration
	 */
	public void createAndSubmitNewLearnership() {
		try {
			Boolean displayRequiredFeilds = checkAllInfoRequired();
			if (displayRequiredFeilds) {
				createLearnership();
				populateAssignedCompaniesTP();
				selectedCompany = null;
				addInfoMessage("Add Complete");
			} else {
				addWarningMessage("Please provide remaining items displayed on the screen");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Creates new instance of learnershipDevelopmentRegistration
	 */
	public void createNewExternalLearnership() {
		try {
			
			if(copyAddress)
			{
				compResidentialAddress.setSameAddress(copyAddress);
				BeanUtils.copyProperties(compResidentialAddress, compPostalAddress);
			}
			cancelExternalInsert();
			clearPostal();
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Check if company already registered
	 * */
	public void validateCompany() throws Exception
	{
		CompanyService companyService=new CompanyService();
		if(companyService.searchBySDL(company.getLevyNumber()) !=null)
		{
			throw new Exception("Company with entity ID("+company.getLevyNumber()+") already exist");
		}
		else if(companyService.searchByReg(company.getCompanyRegistrationNumber()) !=null)
		{
			throw new Exception("Company with registration number("+company.getCompanyRegistrationNumber()+") already exist");
		}
	}

	/**
	 * Checks to ensure all required information provided
	 * adds what is not provided to string list: reqiredFeilds
	 * @return
	 */
	private Boolean checkAllInfoRequired()  throws Exception{
		reqiredFeilds = new ArrayList<>();
		Boolean checkPassed = true;
		if (checkPassed) {
			reqiredFeilds = null;
		}
		return checkPassed;
	}

	/**
	 * Creates instance of
	 * 
	 * @throws Exception
	 */
	private void createLearnership() throws Exception {
	}	

	/** Actions End */
	
	/**
	 * Handles document upload against LearnershipDevelopmentRegistration
	 * @param event
	 */
	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Old or not required code
	 */
	/**
	 * Displays selected companies learnerships
	 * old code
	 */
	public void selectCompanyAndDisplayLearnerships() {
		try {
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object
	 *            the object
	 */
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Company) {
				this.company = (Company) object;
				
				if(!company.isExistingCompany()&& searchType.matches("levyNumber"))
				{
					
					company.setDoneSearch(false);
					addErrorMessage("Company does not exists");
				}
				{
					if(company.getId() != null )
					{
						if(company.getPostalAddress()!=null)
						{
							compPostalAddress=company.getPostalAddress();
						}
						else 
						{
							compPostalAddress = new Address();
						}
						if(company.getResidentialAddress()!=null)
						{
							compResidentialAddress=company.getResidentialAddress();
						}
						else
						{
							compResidentialAddress = new Address();
						}
					}
				}
				
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/** Getters and Setters */
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

	public List<String> getReqiredFeilds() {
		return reqiredFeilds;
	}

	public void setReqiredFeilds(List<String> reqiredFeilds) {
		this.reqiredFeilds = reqiredFeilds;
	}

	/*public Boolean getDisplayRequiredFeilds() {
		return displayRequiredFeilds;
	}

	public void setDisplayRequiredFeilds(Boolean displayRequiredFeilds) {
		this.displayRequiredFeilds = displayRequiredFeilds;
	}*/

	/*public String getRenderingConditions() {
		return renderingConditions;
	}

	public void setRenderingConditions(String renderingConditions) {
		this.renderingConditions = renderingConditions;
	}*/

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public boolean isDoneWithCompanyInfo() {
		return doneWithCompanyInfo;
	}

	public void setDoneWithCompanyInfo(boolean doneWithCompanyInfo) {
		this.doneWithCompanyInfo = doneWithCompanyInfo;
	}

	public Address getCompPostalAddress() {
		return compPostalAddress;
	}

	public void setCompPostalAddress(Address compPostalAddress) {
		this.compPostalAddress = compPostalAddress;
	}

	public Address getCompResidentialAddress() {
		return compResidentialAddress;
	}

	public void setCompResidentialAddress(Address compResidentialAddress) {
		this.compResidentialAddress = compResidentialAddress;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
	}

	public QualificationsCurriculumDevelopment getQualificationscurriculumdevelopment() {
		return qualificationscurriculumdevelopment;
	}

	public void setQualificationscurriculumdevelopment(
			QualificationsCurriculumDevelopment qualificationscurriculumdevelopment) {
		this.qualificationscurriculumdevelopment = qualificationscurriculumdevelopment;
	}

	public Qualification getSelectedRealignmentQualification() {
		return selectedRealignmentQualification;
	}

	public void setSelectedRealignmentQualification(Qualification selectedRealignmentQualification) {
		this.selectedRealignmentQualification = selectedRealignmentQualification;
	}

	public Qualification getSelectedReviewQualification() {
		return selectedReviewQualification;
	}

	public void setSelectedReviewQualification(Qualification selectedReviewQualification) {
		this.selectedReviewQualification = selectedReviewQualification;
	}
	
	

}

