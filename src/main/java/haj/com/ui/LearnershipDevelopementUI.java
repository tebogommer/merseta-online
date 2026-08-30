package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.BeanUtils;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelectionHistory;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.InterventionTitle;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SkillsIdentification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.LearnershipDevelopmentRegistrationService;
import haj.com.service.MailDef;
import haj.com.service.ProcessRolesService;
import haj.com.service.RejectReasonMultipleSelectionService;
import haj.com.service.TasksService;
import haj.com.service.lookup.InterventionTitleService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.SkillsIdentificationService;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

@ManagedBean(name = "learnershipDevelopementUI")
@ViewScoped
public class LearnershipDevelopementUI extends AbstractUI {

	/** Entity Level */
	private Company selectedCompany = null;
	private LearnershipDevelopmentRegistration learnershipDevelopmentRegistration = null;
	private Doc doc = null;
	private List<Doc> dheRerectdoc = null;
	/** Service Level */
	private LearnershipDevelopmentRegistrationService learnershipDevelopmentRegistrationService = null;
	private CompanyUsersService companyUsersService = null;
	private CompanyService companyService = null;
	private SkillsIdentificationService skillsIdentificationService = null;
	private InterventionTitleService interventionTitleService = null;

	/** Array Lists */
	private List<Company> companies = null;
	private List<String> reqiredFeilds = null;
	private List<SkillsIdentification> skillsIdentificationList = null;
	private List<InterventionTitle> interventionTitleList = null;

	/** LazyDataModel Lists */
	private LazyDataModel<LearnershipDevelopmentRegistration> learnershipDevelopmentRegistrationDM;
	private List<LearnershipDevelopmentRegistration> learnershipDevelopmentRegistrationDMfilteredList = null;	
	private LazyDataModel<LearnershipDevelopmentRegistration> dataModel = null;
	private LazyDataModel<LearnershipDevelopmentRegistration> userLearnershipDataModel = null;
	
	/** Display Booleans */
	private Boolean displayRequiredFeilds;
	private Boolean skillsSet = null;
	private Boolean skillsProgram = null;
	private Boolean shortCreditBearing = null;
	
	/** Strings */
	private String renderingConditions = null;
	
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
	
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	private List<RejectReasons> rejectReason=new ArrayList<>();
	public static ChangeReason changeReason = new ChangeReason();
	private List<RejectReasons> rejectReasonHistory = new ArrayList<>();
	
	/**Company Search type*/
	private String searchType;
	
	DocService docService=new DocService();

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
	
	public ArrayList<Doc> populateDocs(LearnershipDevelopmentRegistration learnershipDevelopmentRegistration)
	{
		ArrayList<Doc> docs=new ArrayList<>();
		try {
			 docs=(ArrayList<Doc>) docService.searchByTargetKeyAndClass(LearnershipDevelopmentRegistration.class.getName(), learnershipDevelopmentRegistration.getId(),ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		
		return docs;
	}

	private void runInit() throws Exception {
		doneWithCompanyInfo=false;
		populateServiceLayers();
		populateDataModel();
		if (super.getParameter("id", false) != null) {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION) {
				
				//getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				
				if(getSessionUI().getTask().getPreviousTask() != null) {
					//ProcessRolesService processRolesService = new ProcessRolesService();
					//ProcessRoles processRoles = processRolesService.findByKey(getSessionUI().getTask().getPreviousTask().getProcessRole().getId());
					//learnershipDevelopmentRegistration = learnershipDevelopmentRegistrationService.findByKeyAndSetDocs(getSessionUI().getTask().getTargetKey(),processRoles);
					learnershipDevelopmentRegistration = learnershipDevelopmentRegistrationService.findByKey(getSessionUI().getTask().getTargetKey());
					learnershipDevelopmentRegistrationService.prepareTransferDocumentByProcess(ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, learnershipDevelopmentRegistration, getSessionUI().getTask().getProcessRole());
				} else {
					learnershipDevelopmentRegistration = learnershipDevelopmentRegistrationService.findByKeyAndSetDocs(getSessionUI().getTask().getTargetKey(),getSessionUI().getTask().getProcessRole());					
				}
				
				if(learnershipDevelopmentRegistration.getCompany() !=null)
				{
					selectedCompany = companyService.findByKey(learnershipDevelopmentRegistration.getCompany().getId());
					selectedCompany.setLearnershipDevelopmentRegistrations(learnershipDevelopmentRegistrationService.findByCompany(selectedCompany));
				}
				
				if(learnershipDevelopmentRegistration.getApprovalEnum() == ApprovalEnum.Rejected) {
					populateRejectReasons();
					populateRejectDocument();
					//learnershipDevelopmentRegistrationService.prepareTransferDocumentByProcess(ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, learnershipDevelopmentRegistration, getSessionUI().getTask().getProcessRole());
					//learnershipDevelopmentRegistrationService.prepareNewRegistration(getSessionUI().getTask().getWorkflowProcess(), learnershipDevelopmentRegistration, getSessionUI().getTask().getProcessRole());
				}
				
				populatePreviousRejectReasons();
	//			renderingConditions = "#{sessionUI.task.processRole.rolePermission ne UserPermissionEnum.Edit and sessionUI.task.processRole.rolePermission ne UserPermissionEnum.EditUpload}";
			} else {
				populateDefaultLists();
	//			renderingConditions = "false";
			}
		}else
		{
			populateDefaultLists();
		}
		populateLookUpTableLists();
		populateUserLearnershipDataModel();
	}

	private void populateRejectDocument() {
		try {
			dheRerectdoc = docService.searchByClassAndKey(learnershipDevelopmentRegistration.getId(), LearnershipDevelopmentRegistration.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void populatePreviousRejectReasons() {
		try {
			List<RejectReasonMultipleSelectionHistory> list= RejectReasonMultipleSelectionService.instance().findHistoryByTargetKeyClassNameAndProcess(learnershipDevelopmentRegistration.getId(), LearnershipDevelopmentRegistration.class.getName(), ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION);
			
			for(RejectReasonMultipleSelectionHistory rjr: list) {
				RejectReasons rj =rjr.getRejectReason();
				if(rjr.getCreateDate()!=null) {
					rj.setCreateDate(rjr.getCreateDate());
				}
				rejectReasonHistory.add(rj);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateRejectReasons() {
		RejectReasonsService rs= new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(learnershipDevelopmentRegistration.getId(), LearnershipDevelopmentRegistration.class.getName(), ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION);			
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}		
	}
	
	private void populateUserLearnershipDataModel() {
		userLearnershipDataModel = new LazyDataModel<LearnershipDevelopmentRegistration>() { 			 
			   private static final long serialVersionUID = 1L; 
			   private List<LearnershipDevelopmentRegistration> retorno = new  ArrayList<LearnershipDevelopmentRegistration>();
			   
			   @Override 
			   public List<LearnershipDevelopmentRegistration> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  {			   
				try {
					    filters.put("users", getSessionUI().getActiveUser());
						retorno = learnershipDevelopmentRegistrationService.allUserLearnershipDevelopmentRegistration(first,pageSize,sortField,sortOrder,filters);
						userLearnershipDataModel.setRowCount(learnershipDevelopmentRegistrationService.countUserlearnership(LearnershipDevelopmentRegistration.class,filters));
					} catch (Exception e) {
						logger.fatal(e);
					} 
				    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(LearnershipDevelopmentRegistration obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public LearnershipDevelopmentRegistration getRowData(String rowKey) {
			        for(LearnershipDevelopmentRegistration obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 		
	}

	private void populateDataModel() {
		dataModel = new LazyDataModel<LearnershipDevelopmentRegistration>() { 			 
			   private static final long serialVersionUID = 1L; 
			   private List<LearnershipDevelopmentRegistration> retorno = new  ArrayList<LearnershipDevelopmentRegistration>();
			   
			   @Override 
			   public List<LearnershipDevelopmentRegistration> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  {			   
				try {
						retorno = learnershipDevelopmentRegistrationService.allLearnershipDevelopmentRegistration(LearnershipDevelopmentRegistration.class,first,pageSize,sortField,sortOrder,filters);
						
						dataModel.setRowCount(learnershipDevelopmentRegistrationService.count(LearnershipDevelopmentRegistration.class,filters));
					} catch (Exception e) {
						logger.fatal(e);
					} 
				    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(LearnershipDevelopmentRegistration obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public LearnershipDevelopmentRegistration getRowData(String rowKey) {
			        for(LearnershipDevelopmentRegistration obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 		
	}

	/**
	 * Populates service levels
	 * 
	 * @throws Exception
	 */
	private void populateServiceLayers() throws Exception {
		companyUsersService = new CompanyUsersService();
		learnershipDevelopmentRegistrationService = new LearnershipDevelopmentRegistrationService();
		companyService = new CompanyService();
		skillsIdentificationService = new SkillsIdentificationService();
		interventionTitleService = new InterventionTitleService();
	}
	
	/**
	 * Populates look up tables
	 * @throws Exception
	 */
	private void populateLookUpTableLists() throws Exception{
		skillsIdentificationList = skillsIdentificationService.allSkillsIdentificationWithoutOther();
		interventionTitleList = interventionTitleService.allInterventionTitle(); 
	}

	/**
	 * Populates lists for runInit()
	 * 
	 * @throws Exception
	 */
	private void populateDefaultLists() throws Exception {
		populateAssignedCompaniesTP();
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
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	/**
	 * Preps a new instance of the learnership for user to add information
	 * @throws Exception
	 */
	public void prepNewLearnership() throws Exception{
		try {
			if(selectedCompany !=null)
			{
				selectedCompany.setLearnershipDevelopmentRegistrations(learnershipDevelopmentRegistrationService.findByCompany(selectedCompany));
				locateLearnershipsAssignedToCompany();
			}
			else
			{
				populateUserLearnershipDataModel();
			}
			newInstanceOfLearnership();
			
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
	 * Download.
	 */
	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	/**
	 * Preps a new instance of the learnership for user to add information
	 * @throws Exception
	 */
	public void prepNewExternalLearnership() throws Exception{
		try {
				
				if(company.getId()==null)
				{
					validateCompany();
				}

			learnershipDevelopmentRegistration=new LearnershipDevelopmentRegistration();
			doneWithCompanyInfo=true;
			newInstanceOfLearnership();
		} catch (Exception e) {
			addErrorMessage(e.getMessage() ,e);
		}
	}
	
	/**
	 * Cancels new insert oflearnershipDevelopmentRegistration
	 */
	public void cancelInsert(){
		try {
			learnershipDevelopmentRegistration = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void redirect(){
		try {
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	/**
	 * Cancels new insert oflearnershipDevelopmentRegistration
	 */
	public void cancelExternalInsert(){
		try {
			learnershipDevelopmentRegistration = null;
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
				addInfoMessage(super.getEntryLanguage("application.submitted.for.review"));
				if(selectedCompany==null)
				{
					populateUserLearnershipDataModel();
				}
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
	public void createAndSubmitLearnership() {
		try {
			Boolean displayRequiredFeilds = checkAllInfoRequired();
			if (displayRequiredFeilds) {
				learnershipDevelopmentRegistrationService.submitLearnership(getSessionUI().getActiveUser(), learnershipDevelopmentRegistration, getSessionUI().getTask());				
				addInfoMessage(super.getEntryLanguage("application.submitted.for.review"));
				super.ajaxRedirectToDashboard();
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
			learnershipDevelopmentRegistrationService.submitNewExternalLearnership(getSessionUI().getActiveUser(), learnershipDevelopmentRegistration,company,compPostalAddress,compResidentialAddress, true);
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
		if(company.getLevyNumber() != null)
		{
			if(companyService.searchBySDL(company.getLevyNumber()) !=null)
			{
				throw new Exception("Company with entity ID("+company.getLevyNumber()+") already exist");
			}			
		}
		else if(company.getCompanyRegistrationNumber() !=null)
		{
			if(companyService.searchByReg(company.getCompanyRegistrationNumber()) !=null)
			{
				throw new Exception("Company with registration number("+company.getCompanyRegistrationNumber()+") already exist");
			}
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
	 * Preps new instance of
	 * 
	 * @throws Exception
	 */
	private void newInstanceOfLearnership() throws Exception {
		learnershipDevelopmentRegistration = null;
		if(selectedCompany !=null)
		{
			learnershipDevelopmentRegistration = new LearnershipDevelopmentRegistration(selectedCompany, getSessionUI().getActiveUser());
		}
		else
		{
			learnershipDevelopmentRegistration = new LearnershipDevelopmentRegistration();
			learnershipDevelopmentRegistration.setUsers( getSessionUI().getActiveUser());
		}
		learnershipDevelopmentRegistrationService.prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, learnershipDevelopmentRegistration, learnershipDevelopmentRegistration, null);//getSessionUI().getTask().getProcessRole()
		//learnershipDevelopmentRegistration.setDocs(learnershipDevelopmentRegistrationService.prepareNewRegistration(LearnershipDevelopmentRegistration.class.getName(), learnershipDevelopmentRegistration.getId(), ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, CompanyUserTypeEnum.User));
		
	}

	/**
	 * Creates instance of
	 * 
	 * @throws Exception
	 */
	private void createLearnership() throws Exception {
		learnershipDevelopmentRegistrationService.submitNewLearnership(getSessionUI().getActiveUser(), learnershipDevelopmentRegistration, true);
		learnershipDevelopmentRegistration = null;
	}
	
	/**
	 * Based on apply intervention the relevent entry componets will appear and
	 * set feilds to null
	 */
	public void applyInterventionData() {
		try {
			if (learnershipDevelopmentRegistration.getInterventionType() != null) {
				if (SKILLS_PROGRAM_LIST.contains(learnershipDevelopmentRegistration.getInterventionType().getId())) {
					this.skillsProgram = true;
					this.skillsSet = false;
					this.shortCreditBearing = false;
					learnershipDevelopmentRegistration.setSkillsSet(null);
					learnershipDevelopmentRegistration.setUnitStandard(null);
				} else if (SKILLS_SET_LIST.contains(learnershipDevelopmentRegistration.getInterventionType().getId())) {
					this.skillsProgram = false;
					this.skillsSet = true;
					this.shortCreditBearing = false;
					learnershipDevelopmentRegistration.setSkillsProgram(null);
					learnershipDevelopmentRegistration.setUnitStandard(null);
				} else if (learnershipDevelopmentRegistration.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					learnershipDevelopmentRegistration.setSkillsProgram(null);
					learnershipDevelopmentRegistration.setSkillsSet(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = true;
				} else {
					learnershipDevelopmentRegistration.setSkillsProgram(null);
					learnershipDevelopmentRegistration.setSkillsSet(null);
					learnershipDevelopmentRegistration.setUnitStandard(null);
					this.skillsProgram = false;
					this.skillsSet = false;
					this.shortCreditBearing = false;
				}
				learnershipDevelopmentRegistrationService.applyInterventionData(learnershipDevelopmentRegistration);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Actions Start */
	// view, edit, upload, editUpload permissions
	public void completeRegistration() {
		try {
			if (getSessionUI().getTask().getProcessRole() == null) {
				learnershipDevelopmentRegistrationService.completeToFirst(learnershipDevelopmentRegistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
			} else {
				learnershipDevelopmentRegistrationService.completeLearnership(learnershipDevelopmentRegistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void completeLearnershipTask() {
		try {
			if (getSessionUI().getTask().getProcessRole() != null) {
				learnershipDevelopmentRegistrationService.completeLearnershipTask(learnershipDevelopmentRegistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	// approve permissions
	public void approveLearnership() {
		try {
			learnershipDevelopmentRegistrationService.approveLearnership(learnershipDevelopmentRegistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		/*} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));*/
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	// approve permissions
	public void approveLearnershipForDhet() {
		try {
			learnershipDevelopmentRegistrationService.approveLearnershipForDhet(learnershipDevelopmentRegistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	// final approve permissions
	public void finalApproveLearnership() {
		try {
			learnershipDevelopmentRegistrationService.finalApproveLearnership(learnershipDevelopmentRegistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	// reject allowed
	public void rejectLearnership() {
		try {
			if (selectedRejectReason.size() == 0) 
			{
				throw new Exception("Please select a reject reason");				
			}
			else
			{
				learnershipDevelopmentRegistrationService.rejectLearnership(learnershipDevelopmentRegistration, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
				getSessionUI().setTask(null);
				addInfoMessage(super.getEntryLanguage("update.successful"));
				super.ajaxRedirectToDashboard();
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	// reject allowed
		public void rejectLearnershipTask() {
			try {
				if (selectedRejectReason.size() == 0) 
				{
					throw new Exception("Please select a reject reason");				
				}
				else
				{
					learnershipDevelopmentRegistrationService.rejectLearnershipTask(learnershipDevelopmentRegistration, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
					getSessionUI().setTask(null);
					addInfoMessage(super.getEntryLanguage("update.successful"));
					super.ajaxRedirectToDashboard();
				}
			} catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
			}
		}

	// final reject with permission
	public void finalRejectLearnership() {
		try {
			if (selectedRejectReason.size() == 0) 			{
				throw new Exception("Please select a reject reason");				
			}
			learnershipDevelopmentRegistrationService.finalRejectLearnership(learnershipDevelopmentRegistration, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			getSessionUI().setTask(null);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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
			locateLearnershipsAssignedToCompany();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Locates learnerships assigned to company
	 * 
	 * @throws Exception
	 */
	private void locateLearnershipsAssignedToCompany() throws Exception {
		
		learnershipDevelopmentRegistrationDM = new LazyDataModel<LearnershipDevelopmentRegistration>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<LearnershipDevelopmentRegistration> retorno = new  ArrayList<LearnershipDevelopmentRegistration>();
			   
			   @Override 
			   public List<LearnershipDevelopmentRegistration> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = learnershipDevelopmentRegistrationService.allLearnershipDevelopmentRegistrationByCompany(
							LearnershipDevelopmentRegistration.class, first, pageSize, sortField, sortOrder, filters,
							selectedCompany);
					learnershipDevelopmentRegistrationDM.setRowCount((int) learnershipDevelopmentRegistrationService
							.allLearnershipDevelopmentRegistrationByCompanyCount(filters, selectedCompany));
					
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(LearnershipDevelopmentRegistration obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public LearnershipDevelopmentRegistration getRowData(String rowKey) {
			        for(LearnershipDevelopmentRegistration obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  };
	}
	
	/**
	 * This gets a return from AbstractUIInterface.
	 *
	 * @param object
	 *            the object
	 */
	public void callBackMethod(Object object) {
		try {
			
			/*if (object instanceof Users) {
				this.formUser = (Users) object;
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.AM, null, this.formUser, CompanyUserTypeEnum.User);
				amApplication = new AssessorModeratorApplication();
				loadUserLanguage();
			} else if (object instanceof Company) {
				this.company = (Company) object;
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.AM, this.company, null, CompanyUserTypeEnum.Company);
				// companyService.prepareNewRegistration(ConfigDocProcessEnum.AM,this.company);
			}*/
			if(!company.isExistingCompany()&& searchType.matches("levyNumber"))
			{
				
				company.setDoneSearch(false);
				addErrorMessage("Company does not exists");
			}
			else
			{			
			
				if (object instanceof Company) {
					this.company = (Company) object;
					if(company.getId() != null)
					{
						if(company.getPostalAddress() !=null)
						{
							if(company.getPostalAddress().getId() !=null)
							{
								compPostalAddress=company.getPostalAddress();
							}
						}
						if(company.getResidentialAddress() != null)
						{
							if(company.getResidentialAddress().getId() !=null)
							{
								compResidentialAddress=company.getResidentialAddress();
							}
						}
					}
					
					
				}
				
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeChangeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			changeReason.setDoc(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	public void prepChangeDoc() {
		clearChangeReason();
		doc = new Doc();
		doc.setNote("DHET rejected application");
		//Bushman
		doc.setOriginalFname("DHET Rejection Document");
		doc.setChangeReason(null);
	}
	public void clearChangeReason() {
		changeReason = new ChangeReason();
		changeReason.setDescription(null);
	}
	public void createDhetRejectTask() {
		try {
			learnershipDevelopmentRegistrationService.createDhetRejectTask(this.learnershipDevelopmentRegistration, this.getSessionUI().getActiveUser(), this.getSessionUI().getTask(), ConfigDocProcessEnum.LEARNERSHIP_DEVELOPMENT_REGISTRATION, true, doc);
			addInfoMessage("Application has been rejected");
			super.runClientSideExecute("PF('dlgDeleContactPersonReason').hide()");
			redirect();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void companyUsersInfo() {
		try {
			//this.learnershipDevelopmentRegistration = learnershipDevelopmentRegistration;
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

	public LazyDataModel<LearnershipDevelopmentRegistration> getLearnershipDevelopmentRegistrationDM() {
		return learnershipDevelopmentRegistrationDM;
	}

	public void setLearnershipDevelopmentRegistrationDM(
			LazyDataModel<LearnershipDevelopmentRegistration> learnershipDevelopmentRegistrationDM) {
		this.learnershipDevelopmentRegistrationDM = learnershipDevelopmentRegistrationDM;
	}

	public LearnershipDevelopmentRegistration getLearnershipDevelopmentRegistration() {
		return learnershipDevelopmentRegistration;
	}

	public void setLearnershipDevelopmentRegistration(
			LearnershipDevelopmentRegistration learnershipDevelopmentRegistration) {
		this.learnershipDevelopmentRegistration = learnershipDevelopmentRegistration;
	}

	public List<String> getReqiredFeilds() {
		return reqiredFeilds;
	}

	public void setReqiredFeilds(List<String> reqiredFeilds) {
		this.reqiredFeilds = reqiredFeilds;
	}

	public Boolean getDisplayRequiredFeilds() {
		return displayRequiredFeilds;
	}

	public void setDisplayRequiredFeilds(Boolean displayRequiredFeilds) {
		this.displayRequiredFeilds = displayRequiredFeilds;
	}

	public String getRenderingConditions() {
		return renderingConditions;
	}

	public void setRenderingConditions(String renderingConditions) {
		this.renderingConditions = renderingConditions;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<SkillsIdentification> getSkillsIdentificationList() {
		return skillsIdentificationList;
	}

	public void setSkillsIdentificationList(List<SkillsIdentification> skillsIdentificationList) {
		this.skillsIdentificationList = skillsIdentificationList;
	}

	public SkillsIdentificationService getSkillsIdentificationService() {
		return skillsIdentificationService;
	}

	public void setSkillsIdentificationService(SkillsIdentificationService skillsIdentificationService) {
		this.skillsIdentificationService = skillsIdentificationService;
	}

	public Boolean getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(Boolean skillsSet) {
		this.skillsSet = skillsSet;
	}

	public Boolean getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(Boolean skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public Boolean getShortCreditBearing() {
		return shortCreditBearing;
	}

	public void setShortCreditBearing(Boolean shortCreditBearing) {
		this.shortCreditBearing = shortCreditBearing;
	}

	public List<InterventionTitle> getInterventionTitleList() {
		return interventionTitleList;
	}

	public void setInterventionTitleList(List<InterventionTitle> interventionTitleList) {
		this.interventionTitleList = interventionTitleList;
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

	public List<LearnershipDevelopmentRegistration> getLearnershipDevelopmentRegistrationDMfilteredList() {
		return learnershipDevelopmentRegistrationDMfilteredList;
	}

	public void setLearnershipDevelopmentRegistrationDMfilteredList(
			List<LearnershipDevelopmentRegistration> learnershipDevelopmentRegistrationDMfilteredList) {
		this.learnershipDevelopmentRegistrationDMfilteredList = learnershipDevelopmentRegistrationDMfilteredList;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public LazyDataModel<LearnershipDevelopmentRegistration> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<LearnershipDevelopmentRegistration> dataModel) {
		this.dataModel = dataModel;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public LazyDataModel<LearnershipDevelopmentRegistration> getUserLearnershipDataModel() {
		return userLearnershipDataModel;
	}

	public void setUserLearnershipDataModel(LazyDataModel<LearnershipDevelopmentRegistration> userLearnershipDataModel) {
		this.userLearnershipDataModel = userLearnershipDataModel;
	}

	public List<Doc> getDheRerectdoc() {
		return dheRerectdoc;
	}

	public void setDheRerectdoc(List<Doc> dheRerectdoc) {
		this.dheRerectdoc = dheRerectdoc;
	}

	public List<RejectReasons> getRejectReasonHistory() {
		return rejectReasonHistory;
	}

	public void setRejectReasonHistory(List<RejectReasons> rejectReasonHistory) {
		this.rejectReasonHistory = rejectReasonHistory;
	}

}
