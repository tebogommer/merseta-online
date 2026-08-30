package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.FileUploadEvent;

import haj.com.entity.Address;
import haj.com.entity.AddressHistory;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.CompanyHistory;
import haj.com.entity.CompanyUsers;
import haj.com.entity.CompanyUsersHistory;
import haj.com.entity.Doc;
import haj.com.entity.Employees;
import haj.com.entity.EmployeesHistory;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.SDFCompany;
import haj.com.entity.SDFCompanyHistory;
import haj.com.entity.Sites;
import haj.com.entity.SitesHistory;
import haj.com.entity.TrainingComittee;
import haj.com.entity.TrainingComitteeHistory;
import haj.com.entity.UsersResponsibilities;
import haj.com.entity.UsersResponsibilitiesHistory;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressHistoryService;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyHistoryService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersHistoryService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.EmployeesHistoryService;
import haj.com.service.EmployeesService;
import haj.com.service.SDFCompanyHistoryService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SitesHistoryService;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.service.TrainingComitteeHistoryService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.UsersResponsibilitiesHistoryService;
import haj.com.service.UsersResponsibilitiesService;

@ManagedBean(name = "companymanagementUI")
@ViewScoped
public class CompanyManagementUI extends AbstractUI {

	private Company company;
	private Company linkedCompany;
	private CompanyService companyService = new CompanyService();

	private CompanyUsers companyUsers;
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	private SDFCompany sdfCompany;
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	private SDFCompanyHistory sdfCompanyHistory;
	private SDFCompanyHistoryService sdfCompanyHistoryService = new SDFCompanyHistoryService();

	private TrainingComittee trainingComittee;
	private TrainingComitteeHistory trainingComitteeHistory;
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();
	private TrainingComitteeHistoryService trainingComitteeHistoryService = new TrainingComitteeHistoryService();

	private Sites sites;
	private SitesService sitesService = new SitesService();

	private SitesHistory sitesHistory;
	private SitesHistoryService sitesHistoryService = new SitesHistoryService();

	private CompanyHistory companyHistory;

	private Doc doc;
	private List<Doc> previoursDoc = new ArrayList<>();
	private List<Doc> currentDoc = new ArrayList<>();
	private List<Doc> docVersions = new ArrayList<>();
	private ConfigDocProcessEnum workflowProcess;

	private AddressHistory residentialAddressHistory;
	private AddressHistory postalAddressHistory;

	private List<UsersResponsibilities> usersResponsibilities;
	private List<UsersResponsibilitiesHistory> usersResponsibilitiesHistories;

	private CompanyUsersHistory companyUsersHistory;
	private CompanyUsersHistoryService companyUsersHistoryService = new CompanyUsersHistoryService();

	private Employees employees;
	private EmployeesHistory employeesHistory;
	private EmployeesHistoryService employeesHistoryService = new EmployeesHistoryService();
	private EmployeesService employeeService = new EmployeesService();
	private ChangeReason changeReason = new ChangeReason();
	private List<ChangeReason> changeReasonsList = new ArrayList<>();
	
	private String setmisValidiationException = "";

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
	 * Initialize method to get all ExtensionRequest and prepare a for a create of a
	 * new ExtensionRequest
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		ConfigDocProcessEnum configDocProcessEnum = getSessionUI().getTask().getWorkflowProcess();
		try{
			if (getSessionUI().getTask() != null) {
				workflowProcess = getSessionUI().getTask().getWorkflowProcess();

				switch (configDocProcessEnum) {

					case COMPANY_CHANGE_APPROVAL:
						this.company = companyService.findByKey(getSessionUI().getTask().getTargetKey());
						companyService.resolveCompanyAddresses(this.company);
						List<CompanyHistory> ch = CompanyHistoryService.instance().findByCompanyLatest(company);
						if (ch.size() > 0) {
							companyHistory = ch.get(0);
						}
						residentialAddressHistory = AddressHistoryService.instance().findByForAddress(company.getResidentialAddress().getId()).get(0);
						postalAddressHistory = AddressHistoryService.instance().findByForAddress(company.getPostalAddress().getId()).get(0);

						Address residentialAddress = new Address();
						Address postalAddress = new Address();
						Long tempResAddressId = companyHistory.getResidentialAddress().getId();
						Long tempPostalAddressId = companyHistory.getPostalAddress().getId();
						BeanUtils.copyProperties(residentialAddress, residentialAddressHistory);
						BeanUtils.copyProperties(postalAddress, postalAddressHistory);
						residentialAddress.setId(tempResAddressId);
						postalAddress.setId(tempPostalAddressId);

						companyHistory.setResidentialAddress(residentialAddress);
						companyHistory.setPostalAddress(postalAddress);

						break;

					case CONTACT_PERSONS_CHANGE:
						this.companyUsers = companyUsersService.findByCompanyUserID(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(companyUsers.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						usersResponsibilities = UsersResponsibilitiesService.instance().findByCompanyUser(companyUsers);
						companyUsersHistory = companyUsersHistoryService.findAllByForID(companyUsers.getId()).get(0);
						usersResponsibilitiesHistories = UsersResponsibilitiesHistoryService.instance().findByCompanyUser(companyUsers);

						break;

					case LINKED_COMPANY_CHANGE:
						this.linkedCompany = companyService.findByKey(getSessionUI().getTask().getTargetKey());
						// Parent company
						this.company = companyService.findByKey(linkedCompany.getLinkedCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						// Linked company History
						List<CompanyHistory> chList = CompanyHistoryService.instance().findByCompanyLatest(linkedCompany);
						if (chList.size() > 0) {
							companyHistory = chList.get(0);
						}

						break;

					case NEW_LINKED_COMPANY:
						this.linkedCompany = companyService.findByKey(getSessionUI().getTask().getTargetKey());
						// Parent company
						this.company = companyService.findByKey(linkedCompany.getLinkedCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						break;

					case REMOVE_LINKED_COMPANY:
						this.linkedCompany = companyService.findByKey(getSessionUI().getTask().getTargetKey());
						// Parent company
						this.company = companyService.findByKey(linkedCompany.getLinkedCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						break;

					case TRAINING_COMMITTEE_CHANGE:
						trainingComittee = trainingComitteeService.findByKey(getSessionUI().getTask().getTargetKey());
						trainingComitteeHistory = trainingComitteeHistoryService.findByForTrainingComittee(trainingComittee).get(0);
						this.company = companyService.findByKey(trainingComittee.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						break;

					case SITES_CHANGE:
						this.sites = sitesService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(sites.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						this.sitesHistory = sitesHistoryService.findByCompany(company).get(0);

						Address address = new Address();
						AddressHistory latestAddressHistory = AddressHistoryService.instance().findByForAddress(sites.getRegisteredAddress().getId()).get(0);
						BeanUtils.copyProperties(address, latestAddressHistory);
						this.sitesHistory.setRegisteredAddress(address);

						break;

					case NEW_SITES:
						this.sites = sitesService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(sites.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						break;

					case NEW_TRAINING_COMMITTEE:

						this.trainingComittee = trainingComitteeService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(trainingComittee.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						break;
					case NEW_CONTACT_PERSON:
						this.companyUsers = companyUsersService.findByCompanyUserID(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(companyUsers.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);

						usersResponsibilities = UsersResponsibilitiesService.instance().findByCompanyUser(companyUsers);

						break;

					case DELETE_CONTACT_PERSON:
						this.companyUsers = companyUsersService.findByCompanyUserID(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(companyUsers.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);

						usersResponsibilities = UsersResponsibilitiesService.instance().findByCompanyUser(companyUsers);

						break;

					case NEW_SDF:
						this.sdfCompany = sdfCompanyService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(sdfCompany.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);

						break;

					case DELETE_SDF:
						this.sdfCompany = sdfCompanyService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(sdfCompany.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);

						break;

					case SDF_CHANGE:
						this.sdfCompany = sdfCompanyService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(sdfCompany.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);

						this.sdfCompanyHistory = sdfCompanyHistoryService.bySDF(sdfCompany.getSdf().getId()).get(0);

						break;

					case NEW_COMPANY_USER:
						this.companyUsers = companyUsersService.findByCompanyUserID(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(companyUsers.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);

						break;
					case NEW_EMPLOYEE:

						this.employees = employeeService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(employees.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);

						break;

					case DELETE_EMPLOYEE:
						this.employees = employeeService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(employees.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);

						break;

					case EMPLOYEE_CHANGE:

						this.employees = employeeService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(employees.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						// Emp History
						this.employeesHistory = employeesHistoryService.findByForEmployee(employees.getId()).get(0);

						break;

					case DOC_CHANGE:

						doc = DocService.instance().findByKeyWithJoins(getSessionUI().getTask().getTargetKey());
						docVersions = DocService.instance().findByVersionNo(doc);
						this.company = companyService.findByKey(doc.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);

						if (docVersions.size() > 0) {
							if (docVersions.get(0).getVersionNo() == 2) {
								// Finding the original doc
								previoursDoc.add(DocService.instance().findByKeyWithJoins(docVersions.get(0).getDoc().getId()));
							} else {
								// Adding only the latest version
								if (docVersions.size() > 1) {
									previoursDoc.add(docVersions.get(1));
								} else {
									previoursDoc.add(docVersions.get(0));
								}
							}
						}
						currentDoc.add(doc);
						break;
					case DELETE_TRAINING_COMMITTEE:
						this.trainingComittee = trainingComitteeService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(trainingComittee.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						break;
					case DELETE_SISTE:
						this.sites = sitesService.findByKey(getSessionUI().getTask().getTargetKey());
						this.company = companyService.findByKey(sites.getCompany().getId());
						companyService.resolveCompanyAddresses(this.company);
						address = new Address();
						if (AddressHistoryService.instance().countByForAddress(sites.getRegisteredAddress().getId()) > 0) {
							latestAddressHistory = AddressHistoryService.instance().findByForAddress(sites.getRegisteredAddress().getId()).get(0);
							BeanUtils.copyProperties(address, latestAddressHistory);
						}
						if (sitesHistoryService.countByCompany(company) > 0) {
							this.sitesHistory = sitesHistoryService.findByCompany(company).get(0);
							this.sitesHistory.setRegisteredAddress(address);
						}
						break;
					default:
						break;
				}
				findChangeReason();
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			}
		}catch (Exception e){
			System.out.println("Error while getting data of Target Key:" + getSessionUI().getTask().getTargetKey() + " for " + configDocProcessEnum.getFriendlyName());
			throw new Exception("Error while getting data of Target Key:" + getSessionUI().getTask().getTargetKey() + " for " + configDocProcessEnum.getFriendlyName());
		}
	}

	public void findChangeReason() throws Exception {
		changeReasonsList = (List<ChangeReason>) ChangeReasonService.instance().findByTargetClassAndTargetKey(getSessionUI().getTask().getTargetClass(), getSessionUI().getTask().getTargetKey());
		if (changeReasonsList.size() > 0) {
			changeReason = changeReasonsList.get(0);
		}
	}

	public void storeNewFile(FileUploadEvent event) {

	}

	public void completeCompanyTask() {
		try {
			setmisValidiationException = "";
			companyService.completeChangeRequest(company, getSessionUI().getTask(), getSessionUI().getActiveUser());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectCompanyTask() {
		try {
			companyService.rejectChangeRequest(company, getSessionUI().getTask(), getSessionUI().getActiveUser());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeLinkedCompanyTask() {
		try {
			setmisValidiationException = "";
			companyService.completeRemoveLinkedRequest(company, getSessionUI().getTask(), getSessionUI().getActiveUser());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeDeregisterLinkedCompanyTask() {
		try {
			setmisValidiationException = "";
			companyService.completeDeregisterLinkedCompanyTask(company, linkedCompany, getSessionUI().getTask(), getSessionUI().getActiveUser());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveNewSiteTask() {
		try {
			setmisValidiationException = "";
			sitesService.approveNewSiteTask(sites, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectNewSiteTask() {
		try {
			setmisValidiationException = "";
			sitesService.rejectNewSiteTask(sites, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve new TrainingComittee
	 */
	public void approveNewTrainingComitteeTask() {
		try {
			setmisValidiationException = "";
			trainingComitteeService.approveNewTrainingTask(trainingComittee, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve new Employees
	 */
	public void approveNewEmployeesTask() {
		try {
			setmisValidiationException = "";
			employeeService.approveNewEmployeeTask(employees, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Reject Employees Changes
	 */
	public void rejectEmployeesChangesTask() {
		try {
			setmisValidiationException = "";
			employeeService.rejectEmployeeChangesTask(employees, employeesHistory, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve Employees Changes
	 */
	public void approveEmployeesChangesTask() {
		try {
			setmisValidiationException = "";
			employeeService.approveEmployeeChangesTask(employees, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Reject new Employees
	 */
	public void rejectNewEmployeesTask() {
		try {
			setmisValidiationException = "";
			employeeService.rejectNewEmployeeTask(employees, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Reject new SDF
	 */
	public void rejectNewSDFTask() {
		try {
			setmisValidiationException = "";
			sdfCompanyService.rejectNewSDFTask(sdfCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve new SDF
	 */
	public void approveNewSDFTask() {
		try {
			setmisValidiationException = "";
			sdfCompanyService.approveNewSDFTask(sdfCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Reject SDF Changes
	 */
	public void rejectSDFChangesTask() {
		try {
			setmisValidiationException = "";
			sdfCompanyService.rejectSDFChangesTask(sdfCompany, sdfCompanyHistory, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve new SDF
	 */
	public void approveSDFChangesTask() {
		try {
			setmisValidiationException = "";
			sdfCompanyService.approveSDFChangesTask(sdfCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve Contact Person changes Task
	 */
	public void approveContactPersonChangeTask() {
		try {
			setmisValidiationException = "";
			companyUsersService.approveContactPersonChangeTask(companyUsers, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve new Contact Person Task
	 */
	public void approveNewContactPersonTask() {
		try {
			setmisValidiationException = "";
			companyUsersService.approveNewContactPersonTask(companyUsers, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve new Company Task
	 */
	public void approveNewCompanyTask() {
		try {
			setmisValidiationException = "";
			companyUsersService.approveNewCompanyUserTask(companyUsers, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve new Company Task
	 */
	public void rejectNewCompanyTask() {
		try {
			setmisValidiationException = "";
			companyUsersService.rejectNewCompanyUserTask(companyUsers, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Reject Contact Person Changes Task
	 */
	public void rejectContactPersonChangesTask() {
		try {
			setmisValidiationException = "";
			companyUsersService.rejectContactPersonChangeTask(companyUsers, companyUsersHistory, usersResponsibilitiesHistories, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Reject new Contact Person Task
	 */
	public void rejectNewContactPersonTask() {
		try {
			setmisValidiationException = "";
			companyUsersService.rejectNewContactPersonTask(companyUsers, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Reject new TrainingComittee*
	 */
	public void rejectNewTrainingComitteeTask() {
		try {
			setmisValidiationException = "";
			trainingComitteeService.rejectNewTrainingTask(trainingComittee, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveSiteUpdateTask() {
		try {
			setmisValidiationException = "";
			sitesService.approveSiteUpdateTask(sites, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectSiteUpdateTask() {
		try {
			setmisValidiationException = "";
			sitesService.rejectSiteUpdateTask(sites, getSessionUI().getTask(), sitesHistory);
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectSiteDeleteTask() {
		try {
			setmisValidiationException = "";
			sitesService.rejectSiteDeleteTask(sites, getSessionUI().getTask(), sitesHistory);
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveSiteDeleteTask() {
		try {
			setmisValidiationException = "";
			sitesService.approveSiteDeleteTask(sites, getSessionUI().getTask(), sitesHistory);
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			logger.fatal(e);
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			logger.fatal(e);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveTrainingCommitteUpdateTask() {
		try {
			setmisValidiationException = "";
			trainingComitteeService.approveTrainingComUpdateTask(trainingComittee, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			logger.fatal(e);
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			logger.fatal(e);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectTrainingCommitteeUpdateTask() {
		try {
			setmisValidiationException = "";
			trainingComitteeService.rejectTrainingComUpdateTask(trainingComittee, getSessionUI().getTask(), trainingComitteeHistory);
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectLinkedCompany() {
		try {
			setmisValidiationException = "";
			companyService.rejectLinkedCompanyTask(linkedCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void approveLinkedCompanyChangesTask() {
		try {
			setmisValidiationException = "";
			companyService.approveLinkedCompanyChangesTask(linkedCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectLinkedCompanyChangesTask() {
		try {
			setmisValidiationException = "";
			companyService.rejectLinkedCompanyChangesTask(linkedCompany, companyHistory, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve Task to delete Employees.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void approveEmployeeDeleteTask() {
		try {
			setmisValidiationException = "";
			employeeService.approveEmployeeDeleteTask(this.employees, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Approve Task to delete Contact Person.
	 *
	 * @author TechFinium
	 * @see CompanyUsers
	 */
	public void approveContactPersonDeleteTask() {
		try {
			setmisValidiationException = "";
			companyUsersService.approveCompanyUsersDeleteTask(companyUsers, getSessionUI().getTask(), (ArrayList<UsersResponsibilities>) usersResponsibilities);
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Reject Task to delete Contact Person.
	 *
	 * @author TechFinium
	 * @see CompanyUsers
	 */
	public void rejectContactPersonDeleteTask() {
		try {
			setmisValidiationException = "";
			companyUsersService.rejectCompanyUsersDeleteTask(companyUsers, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Reject Task to delete Employees.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void rejectEmployeeDeleteTask() {
		try {
			setmisValidiationException = "";
			employeeService.rejectEmployeeDeleteTask(this.employees, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveLinkedCompanyTask() {
		try {
			setmisValidiationException = "";
			companyService.approveLinkedCompanyTask(linkedCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectLinkedCompanyTask() {
		try {
			setmisValidiationException = "";
			companyService.rejectRemoveLinkedRequest(linkedCompany, getSessionUI().getTask(), getSessionUI().getActiveUser());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveDeleteSDFTask() {
		try {
			setmisValidiationException = "";
			SDFCompanyService.instance().approveDeleteSDFTask(sdfCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectDeleteSDFTask() {
		try {
			setmisValidiationException = "";
			SDFCompanyService.instance().rejectDeleteSDFTask(sdfCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveRemoveLinkedCompany() {
		try {
			setmisValidiationException = "";
			companyService.approveRemoveLinkedCompany(linkedCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveRemoveTrainingCommitteeTask() {
		try {
			setmisValidiationException = "";
			trainingComitteeService.approveRemoveTCTask(trainingComittee, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectRemoveTrainingCommitteeTask() {
		try {
			setmisValidiationException = "";
			trainingComitteeHistory = trainingComitteeHistoryService.findByForTrainingComittee(trainingComittee).get(0);
			trainingComitteeService.rejectDeleteTCTask(trainingComittee, getSessionUI().getTask(), trainingComitteeHistory);
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectRemoveLinkedCompany() {
		try {
			setmisValidiationException = "";
			companyService.rejectRemoveLinkedCompany(linkedCompany, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveDocumentTask() {
		try {
			setmisValidiationException = "";
			DocService.instance().approveDocument(doc, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectDocumentTask() {
		try {
			setmisValidiationException = "";
			DocService.instance().rejectDocument(doc, getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (javax.validation.ConstraintViolationException e) {
			setmisValidiationException = extractValidationErrorsReturnString(e);
			addErrorMessage("Validation Error, please refer to the message below.");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public ConfigDocProcessEnum getWorkflowProcess() {
		return workflowProcess;
	}

	public void setWorkflowProcess(ConfigDocProcessEnum workflowProcess) {
		this.workflowProcess = workflowProcess;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyUsers getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(CompanyUsers companyUsers) {
		this.companyUsers = companyUsers;
	}

	public SDFCompany getSdfCompany() {
		return sdfCompany;
	}

	public void setSdfCompany(SDFCompany sdfCompany) {
		this.sdfCompany = sdfCompany;
	}

	public TrainingComittee getTrainingComittee() {
		return trainingComittee;
	}

	public void setTrainingComittee(TrainingComittee trainingComittee) {
		this.trainingComittee = trainingComittee;
	}

	public Sites getSites() {
		return sites;
	}

	public void setSites(Sites sites) {
		this.sites = sites;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public CompanyHistory getCompanyHistory() {
		return companyHistory;
	}

	public void setCompanyHistory(CompanyHistory companyHistory) {
		this.companyHistory = companyHistory;
	}

	public SitesHistory getSitesHistory() {
		return sitesHistory;
	}

	public void setSitesHistory(SitesHistory sitesHistory) {
		this.sitesHistory = sitesHistory;
	}

	public TrainingComitteeHistory getTrainingComitteeHistory() {
		return trainingComitteeHistory;
	}

	public void setTrainingComitteeHistory(TrainingComitteeHistory trainingComitteeHistory) {
		this.trainingComitteeHistory = trainingComitteeHistory;
	}

	public AddressHistory getResidentialAddressHistory() {
		return residentialAddressHistory;
	}

	public void setResidentialAddressHistory(AddressHistory residentialAddressHistory) {
		this.residentialAddressHistory = residentialAddressHistory;
	}

	public AddressHistory getPostalAddressHistory() {
		return postalAddressHistory;
	}

	public void setPostalAddressHistory(AddressHistory postalAddressHistory) {
		this.postalAddressHistory = postalAddressHistory;
	}

	public List<UsersResponsibilities> getUsersResponsibilities() {
		return usersResponsibilities;
	}

	public void setUsersResponsibilities(List<UsersResponsibilities> usersResponsibilities) {
		this.usersResponsibilities = usersResponsibilities;
	}

	public List<UsersResponsibilitiesHistory> getUsersResponsibilitiesHistories() {
		return usersResponsibilitiesHistories;
	}

	public void setUsersResponsibilitiesHistories(List<UsersResponsibilitiesHistory> usersResponsibilitiesHistories) {
		this.usersResponsibilitiesHistories = usersResponsibilitiesHistories;
	}

	public CompanyUsersHistory getCompanyUsersHistory() {
		return companyUsersHistory;
	}

	public void setCompanyUsersHistory(CompanyUsersHistory companyUsersHistory) {
		this.companyUsersHistory = companyUsersHistory;
	}

	public SDFCompanyHistory getSdfCompanyHistory() {
		return sdfCompanyHistory;
	}

	public void setSdfCompanyHistory(SDFCompanyHistory sdfCompanyHistory) {
		this.sdfCompanyHistory = sdfCompanyHistory;
	}

	public Company getLinkedCompany() {
		return linkedCompany;
	}

	public void setLinkedCompany(Company linkedCompany) {
		this.linkedCompany = linkedCompany;
	}

	public Employees getEmployees() {
		return employees;
	}

	public void setEmployees(Employees employees) {
		this.employees = employees;
	}

	public EmployeesHistory getEmployeesHistory() {
		return employeesHistory;
	}

	public void setEmployeesHistory(EmployeesHistory employeesHistory) {
		this.employeesHistory = employeesHistory;
	}

	public ChangeReason getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(ChangeReason changeReason) {
		this.changeReason = changeReason;
	}

	public List<Doc> getDocVersions() {
		return docVersions;
	}

	public void setDocVersions(List<Doc> docVersions) {
		this.docVersions = docVersions;
	}

	public List<Doc> getPrevioursDoc() {
		return previoursDoc;
	}

	public void setPrevioursDoc(List<Doc> previoursDoc) {
		this.previoursDoc = previoursDoc;
	}

	public List<Doc> getCurrentDoc() {
		return currentDoc;
	}

	public void setCurrentDoc(List<Doc> currentDoc) {
		this.currentDoc = currentDoc;
	}

	public String getSetmisValidiationException() {
		return setmisValidiationException;
	}

	public void setSetmisValidiationException(String setmisValidiationException) {
		this.setmisValidiationException = setmisValidiationException;
	}

}
