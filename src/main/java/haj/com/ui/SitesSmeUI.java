package haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.LazyDataModel;

import haj.com.constants.HAJConstants;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.Employees;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.SmeQualifications;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.EmployeesService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.SitesService;
import haj.com.service.SitesSmeService;
import haj.com.service.SmeQualificationsService;
import haj.com.service.UsersService;
import haj.com.validators.CheckID;

@ManagedBean(name = "sitessmeUI")
@ViewScoped
public class SitesSmeUI extends AbstractUI {

	/** Service Level */
	private SitesSmeService service = new SitesSmeService();
	private SitesService sitesService = new SitesService();
	private SmeQualificationsService smeQualificationsService = new SmeQualificationsService();
	public static ChangeReason changeReason = new ChangeReason();

	/** Array Lists */
	private List<SitesSme> sitessmeList = null;
	private List<SitesSme> sitessmefilteredList = null;
	private List<SmeQualifications> smeQualificationsList = null;
	
	/** Entity Level */
	private SitesSme sitessme = null;
	private SitesSme existingsitessme = null;
	private SitesSme sitessmeupdate = null;
	private Company company = null;
	private SmeQualifications smeQualifications = null;
	private Doc doc;
	
	/** Data Model Lists */
	private LazyDataModel<SitesSme> dataModel;
	
	/** Action Booleans */
	private boolean companyHasSites;
	private boolean editFirstLastName;
	private boolean disableAll;
	private boolean upload;
	
	/**
	 * The search user passport or id UI. The sme user
	 */
	private Users smeUser;
	private UsersService usersService = null;
	private IdPassportEnum idpassport;
	@CheckID(message = "Not a valid RSA ID number")
	private String idnumber;
	public Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;
	public Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;
	public String passportNumberFormat = HAJConstants.passportNumberFormat;
	private String passportNumber;
	//private String changeReason = "";

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
	 * Initialize method to get all SitesSme and prepare a for a create of a new
	 * SitesSme
	 * 
	 * @author TechFinium
	 * @see SitesSme
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		// check if in the task 
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SITE_SME_REGISTRATION) {
			checkIfSitesAssigned();
			checkIfUpdateSitesAssigned() ;
		} else {
			disableAll = upload = false;
			checkIfSitesAssigned();
			checkIfUpdateSitesAssigned() ;
			prepareNew();
			prepareNewUpdate();
			//sitessmeInfo();
		}
		
	}
	
	/**
	 * Get all SitesSme for data table
	 * 
	 * @author TechFinium
	 * @see SitesSme
	 */
	public void sitessmeInfo() {
		// dataModel = new SitesSmeDatamodel();
	}

	/**
	 * Insert SitesSme into database
	 * 
	 * @author TechFinium
	 * @see SitesSme
	 */
	public void sitessmeInsert() {
		try {
			service.create(this.sitessme);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sitessmeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SitesSme in database
	 * 
	 * @author TechFinium
	 * @see SitesSme
	 */
	public void sitessmeUpdate() {
		try {
			service.update(this.sitessme);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sitessmeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SitesSme from database
	 * 
	 * @author TechFinium
	 * @see SitesSme
	 */
	public void sitessmeDelete() {
		this.sitessme.setSoftDelete(true);
		try {
			service.update(this.sitessme);
			//service.delete(this.sitessme);
			prepareNew();
			sitessmeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void sitessmeCreateDeleteWorkflow() {
		try {
			service.sitessmeCreateDeleteWorkflow(this.sitessmeupdate, getSessionUI().getActiveUser());
			//service.delete(this.sitessme);
			prepareNew();
			prepareNewUpdate();
			sitessmeInfo();
			addInfoMessage(super.getEntryLanguage("application.submitted.for.review"));
			runClientSideExecute("PF('dlgDeleteReason').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SitesSme
	 * 
	 * @author TechFinium
	 * @see SitesSme
	 */
	
	public void prepareNew() throws Exception{
		sitessme = service.createNewSitesSme(ConfigDocProcessEnum.SITE_SME_REGISTRATION, CompanyUserTypeEnum.User, null);
		sitessme.setActive(true);
		prepNewSmeUser();
		if (company != null) {
			sitessme.setCompany(company);
			// sets default site
			if (companyHasSites) {
				if (company != null) {
					List<Sites> allSitesAssigned = sitesService.findByCompany(company);
					if (allSitesAssigned.size() == 1) {
						sitessme.setSites(allSitesAssigned.get(0));
					}
					allSitesAssigned = null;
				}
			} else {
				// assigns the residential address from the company if no sites assigned
				if (company != null) {
					sitessme.setResidentialAddress(company.getResidentialAddress());
				}
			}
		}
	}
	
	public void prepareNewUpdate() throws Exception{
		sitessmeupdate = service.createNewSitesSme(ConfigDocProcessEnum.SITE_SME_REGISTRATION, CompanyUserTypeEnum.User, null);
		sitessmeupdate.setActive(true);
		prepNewSmeUser();
		if (company != null) {
			sitessmeupdate.setCompany(company);
			// sets default site
			if (companyHasSites) {
				if (company != null) {
					List<Sites> allSitesAssigned = sitesService.findByCompany(company);
					if (allSitesAssigned.size() == 1) {
						sitessmeupdate.setSites(allSitesAssigned.get(0));
					}
					allSitesAssigned = null;
				}
			} else {
				// assigns the residential address from the company if no sites assigned
				if (company != null) {
					sitessmeupdate.setResidentialAddress(company.getResidentialAddress());
				}
			}
		}
	}
	
	public void prepareNewDelete() throws Exception{
		sitessme = service.createNewSitesSme(ConfigDocProcessEnum.SITE_SME_DELETE, CompanyUserTypeEnum.User, null);
		/*
		 * sitessmeupdate.setActive(true); prepNewSmeUser(); if (company != null) {
		 * sitessmeupdate.setCompany(company); // sets default site if (companyHasSites)
		 * { if (company != null) { List<Sites> allSitesAssigned =
		 * sitesService.findByCompany(company); if (allSitesAssigned.size() == 1) {
		 * sitessmeupdate.setSites(allSitesAssigned.get(0)); } allSitesAssigned = null;
		 * } } else { // assigns the residential address from the company if no sites
		 * assigned if (company != null) {
		 * sitessmeupdate.setResidentialAddress(company.getResidentialAddress()); } } }
		 */
	}
	
	/**
	 * Populates boolean if a company has sites assigned to them
	 * 
	 * @throws Exception
	 */
	private void checkIfSitesAssigned() throws Exception {
		if (sitessme != null && sitessme.getId() != null) {
			if (sitessme.getCompany() != null && sitesService.findByCompany(sitessme.getCompany()).size() > 0) {
				companyHasSites = true;
			} else {
				companyHasSites = false;
			}
		} else if (company != null) {
			if (sitesService.findByCompany(company).size() > 0) {
				companyHasSites = true;
			} else {
				companyHasSites = false;
			}
		} else {
			companyHasSites = false;
		}
	}
	
	private void checkIfUpdateSitesAssigned() throws Exception {
		if (sitessmeupdate != null && sitessmeupdate.getId() != null) {
			if (sitessmeupdate.getCompany() != null && sitesService.findByCompany(sitessmeupdate.getCompany()).size() > 0) {
				companyHasSites = true;
			} else {
				companyHasSites = false;
			}
		} else if (company != null) {
			if (sitesService.findByCompany(company).size() > 0) {
				companyHasSites = true;
			} else {
				companyHasSites = false;
			}
		} else {
			companyHasSites = false;
		}
	}
	
	/**
	 * Preps new user to be added
	 * 
	 * @throws Exception
	 */
	public void prepNewSmeUser() throws Exception {
		
		this.idpassport = IdPassportEnum.RsaId;
		smeUser = new Users();
		smeUser.setDoneSearch(false);
		idnumber = "";
		passportNumber = "";
		editFirstLastName = false;
		
		//  = new SitesSme();
		//smeQualifications = new SmeQualifications();
	}
	
	/**
	 * Does a search on the database for the user to see if existing users to
	 * populate information
	 */
	public void searchForUser() {
		try {
			// validation check if user assigned 
			switch (idpassport) {
			case RsaId:
				if (service.countByRsaIdOrPassortNotDeactivated(company, this.idnumber, true, ApprovalEnum.Deactivated) != 0) {
					throw new Exception("User with ID number is already linked to the company");
				}
				// updated below code for resolution of mentor issue REF:: 1660
				// existingsitessme = service.findByRsaIdOrPassort(this.idnumber, true);
				existingsitessme = service.findByRsaIdOrPassortNotDeactivated(company, this.idnumber, true, ApprovalEnum.Deactivated);
				break;
			case Passport:
				if (service.countByRsaIdOrPassortNotDeactivated(company, this.passportNumber, false,ApprovalEnum.Deactivated) != 0) {
					throw new Exception("User with Passport Number is already linked to the company");
				}
				// updated below code for resolution of mentor issue REF:: 1660
				// existingsitessme = service.findByRsaIdOrPassort(this.passportNumber, false);
				existingsitessme = service.findByRsaIdOrPassortNotDeactivated(company, this.passportNumber, false,ApprovalEnum.Deactivated);
				break;
			default:
				throw new Exception("Unable to locate search type, contact support!");
			}
			if (usersService == null) {
				usersService = new UsersService();
			}
			smeUser = new Users();
			switch (idpassport) {
			case RsaId:
				smeUser = usersService.findByRsaIdJoinAddress(this.idnumber);
				break;
			case Passport:
				smeUser = usersService.findByPassportNumberJoinAddress(this.passportNumber);
				break;
			default:
				smeUser = new Users();
				break;
			}
			//check if user is a merSERTA employee
			if(smeUser != null && smeUser.getId() != null) {
				HostingCompanyEmployeesService companyEmployeesService = new HostingCompanyEmployeesService();
				if (companyEmployeesService.findByUserCount(smeUser.getId(), HAJConstants.HOSTING_MERSETA) > 0)
				{
					smeUser = new Users();
					throw new Exception("A merSETA employee cannot be registered as a Mentor");
				}
			}			
						
			usersService = null;
			//For Sandra to test 
			if (smeUser != null && smeUser.getId() != null) {
				/*EmployeesService employeesService = new EmployeesService();
				Employees employee = employeesService.findEmployeeByIdAndCompany(this.smeUser, this.company);
				if (employee != null) {	
					
					if(existingsitessme != null && existingsitessme.getId() != null) {
						sitessme = existingsitessme;
						if (company != null) {
							sitessme.setCompany(company);
						}
						prepSmeQualification();
					}else {						
						sitessme.setFirstName(smeUser.getFirstName());
						sitessme.setLastName(smeUser.getLastName());
						if (idpassport == IdPassportEnum.RsaId) {
							sitessme.setIdentityNumber(smeUser.getRsaIDNumber());
						} else {
							sitessme.setPassportNumber(passportNumber);
						}
					}
					sitessme.setExistingUser(true);
					editFirstLastName = false;
				}else {
					throw new Exception("Only an employee of the company can be registered as a Mentor");
				}*/
			}else {
				if(existingsitessme != null && existingsitessme.getId() != null) {
					sitessme = existingsitessme;
					if (company != null) {
						sitessme.setCompany(company);
					}
					prepSmeQualification();
				}else {	
					/*EmployeesService employeesService = new EmployeesService();
					Employees employee = null;
					if(idpassport == IdPassportEnum.RsaId) {
						employee = employeesService.findEmployeeByIdAndCompany(this.idnumber, this.company, true);
					}else if(idpassport == IdPassportEnum.Passport) {
						employee = employeesService.findEmployeeByIdAndCompany(this.passportNumber, this.company, false);
					}
					
					if (employee != null) {
						smeUser = new Users();
						this.smeUser.setFirstName(employee.getFirstName());
						this.smeUser.setLastName(employee.getLastName());
						this.smeUser.setIdType(employee.getIdType());
						this.smeUser.setEmploymentStatus(employee.getEmploymentStatus());
						this.smeUser.setDateOfBirth(employee.getDateOfBirth());
						this.smeUser.setRsaIDNumber(employee.getRsaIDNumber());
						this.smeUser.setPassportNumber(employee.getPassportNumber());
						this.smeUser.setDisabilityStatus(employee.getDisability());
						this.smeUser.setEquity(employee.getEquity());
						this.smeUser.setGender(employee.getGender());
						this.smeUser.setOfoCodes(employee.getOfoCode());
						
						sitessme.setExistingUser(true);
						sitessme.setFirstName(smeUser.getFirstName());
						sitessme.setLastName(smeUser.getLastName());
						if (idpassport == IdPassportEnum.RsaId) {
							sitessme.setIdentityNumber(smeUser.getRsaIDNumber());
						} else {
							sitessme.setPassportNumber(passportNumber);
						}
						editFirstLastName = false;
					}else {
						throw new Exception("Only employee can be registered as a mentor");
					}*/
				}
			}			
			//End for Sandra to test
			
			if (smeUser != null && smeUser.getId() != null) {
				sitessme.setExistingUser(true);
				sitessme.setFirstName(smeUser.getFirstName());
				sitessme.setLastName(smeUser.getLastName());
				if (idpassport == IdPassportEnum.RsaId) {
					sitessme.setIdentityNumber(smeUser.getRsaIDNumber());
				} else {
					sitessme.setPassportNumber(passportNumber);
				}
				editFirstLastName = false;
			} else {
				sitessme.setExistingUser(false);
				smeUser = new Users();
				if (idpassport == IdPassportEnum.RsaId) {
					sitessme.setIdentityNumber(idnumber);
				} else {
					sitessme.setPassportNumber(passportNumber);
				}
				editFirstLastName = true;
			}
			smeUser.setDoneSearch(true);
			prepNewSmeQualification();
			// bad code, need to re-write 
			if (company != null) {
				sitessme.setCompany(company);
				checkIfSitesAssigned();
				// sets default site
				if (companyHasSites) {
					if (company != null) {
						List<Sites> allSitesAssigned = sitesService.findByCompany(company);
						if (allSitesAssigned.size() == 1) {
							sitessme.setSites(allSitesAssigned.get(0));
						}
						allSitesAssigned = null;
					}
				} else {
					// assigns the residential address from the company if no sites assigned
					if (company != null) {
						sitessme.setResidentialAddress(company.getResidentialAddress());
					}
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * @throws Exception
	 */
	public void prepNewSmeQualification() throws Exception {
		if (sitessme != null) {
			if (sitessme.getSmeQualificationsList() == null) {
				sitessme.setSmeQualificationsList(new ArrayList<>());
			}
			smeQualifications = smeQualificationsService.prepNewSmeQualifications(sitessme, null);
		} else {
			smeQualifications = smeQualificationsService.prepNewSmeQualifications(null, null);
		}		
	}
	
	public void prepSmeQualification() throws Exception {
		if (sitessme != null) {
			service.prepareNewDoc(sitessme, ConfigDocProcessEnum.SITE_SME_REGISTRATION, CompanyUserTypeEnum.User);
			if (sitessme.getSmeQualificationsList() == null) {
				sitessme.setSmeQualificationsList(smeQualificationsService.findBySme(sitessme));
			}
			smeQualifications = smeQualificationsService.prepNewSmeQualifications(sitessme, null);
		} else {
			smeQualifications = smeQualificationsService.prepNewSmeQualifications(null, null);
		}		
	}
	
	public void prepUpdatemeQualification() throws Exception {
				
		if (sitessmeupdate != null) {
			if (sitessmeupdate.getSmeQualificationsList() == null) {
				sitessmeupdate.setSmeQualificationsList(new ArrayList<>());
			}
			//sitessmeupdate.getDocs().clear();
			smeQualifications = smeQualificationsService.prepNewSmeQualifications(sitessmeupdate, null);
		} else {
			smeQualifications = smeQualificationsService.prepNewSmeQualifications(null, null);
		}
	}
	
	/**
	 * Adds new entry to array list
	 */
	public void addSmeQualificationToList(){
		try {
			String error = validationOnSmeQualification();
			if (error.equals("")) {
				sitessme.getSmeQualificationsList().add(smeQualifications);
				addInfoMessage("Entry Added");
				prepNewSmeQualification();
			} else {
				addWarningMessage("Provide the following before proceeding: " + error);
				prepNewSmeQualification();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateSmeQualificationToList(){
		try {
			String error = validationUpdateSmeQualification();
			if (error.equals("")) {
				sitessmeupdate.getSmeQualificationsList().add(smeQualifications);
				addInfoMessage("Entry Added");
				prepUpdatemeQualification();
			} else {
				addWarningMessage("Provide the following before proceeding: " + error);
				prepUpdatemeQualification();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Validation Check on SmeQualification
	 * @param error
	 * @return
	 */
	public String validationOnSmeQualification() {
		String error = "";
		if (smeQualifications.getQualification() == null) {
			error += "Qualification";
		} else {
			if (sitessme.getId() == null) {
				for (SmeQualifications smeQualification : sitessme.getSmeQualificationsList()) {
					if (smeQualification.getQualification().getId().equals(smeQualifications.getQualification().getId())) {	
						if (error.equals("")) {
							error += "Qualification Is Already Linked To Mentor";
						} else {
							error += ", Qualification Is Already Linked To Mentor";
						}
					}
				}
			}
			else if (sitessme.getId() != null) {
				if(sitessme.getSmeQualificationsList().size() >0)
				{
					for (SmeQualifications smeQualification : sitessme.getSmeQualificationsList()) {
						if (smeQualification.getQualification().getId().equals(smeQualifications.getQualification().getId())) {	
							if (error.equals("")) {
								error += "Qualification Is Already Linked To Mentor";
							} else {
								error += ", Qualification Is Already Linked To Mentor";
							}
						}
					}
				}
			}
		}
		if (smeQualifications.getSitesSme() == null) {
			if (error.equals("")) {
				error += "Mentor Link";
			} else {
				error += ", Mentor Link";
			}
		}
		for (Doc doc : smeQualifications.getDocs()) {
			if (doc.getId() == null && doc.getData() == null) {
				if (error.equals("")) {
					error += "Document: Required Document";
				} else {
					error += ", Document: Mentor Qualification Evidence";
				}
			}
		}
		return error;
	}
	
	/**
	 * Validation Check on SmeQualification
	 * @param error
	 * @return
	 */
	public String validationUpdateSmeQualification() {
		String error = "";
		if (smeQualifications.getQualification() == null) {
			error += "Qualification";
		} else {
			if (sitessmeupdate.getId() == null) {
				for (SmeQualifications smeQualification : sitessmeupdate.getSmeQualificationsList()) {
					if (smeQualification.getQualification().getId().equals(smeQualifications.getQualification().getId())) {	
						if (error.equals("")) {
							error += "Qualification Is Already Linked To Mentor";
						} else {
							error += ", Qualification Is Already Linked To Mentor";
						}
					}
				}
			}
			else if (sitessmeupdate.getId() != null) {
				if(sitessmeupdate.getSmeQualificationsList().size() >0)
				{
					for (SmeQualifications smeQualification : sitessmeupdate.getSmeQualificationsList()) {
						if (smeQualification.getQualification().getId().equals(smeQualifications.getQualification().getId())) {	
							if (error.equals("")) {
								error += "Qualification Is Already Linked To Mentor";
							} else {
								error += ", Qualification Is Already Linked To Mentor";
							}
						}
					}
				}
			}
		}
		if (smeQualifications.getSitesSme() == null) {
			if (error.equals("")) {
				error += "Mentor Link";
			} else {
				error += ", Mentor Link";
			}
		}
		for (Doc doc : smeQualifications.getDocs()) {
			if (doc.getId() == null && doc.getData() == null) {
				if (error.equals("")) {
					error += "Document: Required Document";
				} else {
					error += ", Document: Mentor Qualification Evidence";
				}
			}
		}
		return error;
	}
	
	
	/**
	 * Removes SME from the list
	 */
	public void removeFromList(){
		try {
			sitessme.getSmeQualificationsList().remove(smeQualifications);
			if (sitessme.getSmeQualificationsList().size() == 0) {
				sitessme.setSmeQualificationsList(new ArrayList<>());
			}
			addWarningMessage("Item Removed");
			prepNewSmeQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeFromUpdateList(){
		try {
			sitessmeupdate.getSmeQualificationsList().remove(smeQualifications);
			if (sitessmeupdate.getSmeQualificationsList().size() == 0) {
				sitessmeupdate.setSmeQualificationsList(new ArrayList<>());
			}
			addWarningMessage("Item Removed");
			prepUpdatemeQualification();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Store new file
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
			runClientSideExecute("PF('dlgUploadUser').hide()");
			runClientSideExecute("PF('dlgQualificationDocument').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeChangeNewFile(FileUploadEvent event) {
		try {
			doc = new Doc();
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}			
			//runClientSideExecute("PF('dlgDeleteReason').hide()");
			//changeReason.setDoc(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Download.
	 */
	public void download(Doc doc) {
		try {
			if(doc !=null) {
				if(doc.getData() !=null) {
					Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
				}else {
					addErrorMessage("Error on document data download");
				}
			}else {
				addErrorMessage("Error on document download");
			}			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Creates / Update SME entry and kicks off work flow
	 */
	public void createUpdateSmeEntry(){
		try {
			System.out.println("sitessme.getSmeQualificationsList().size():"+sitessme.getSmeQualificationsList().size());
			if (sitessme.getSmeQualificationsList().size() == 0) {
				throw new Exception("Provide At Least 1 Qualification Before Proceeding");
			}
			if (sitessme.getUseCompanyAddress() == null) {
				sitessme.setUseCompanyAddress(true);
			}
			if (sitessme.getUseCompanyAddress() && sitessme.getSites() != null) {
				sitessme.setSites(null);
			}
			if (sitessme.getFirstName() == null || sitessme.getFirstName().isEmpty()) {
				throw new Exception("Provide First Name Of Mentor");
			}
			if (sitessme.getLastName() == null || sitessme.getLastName().isEmpty()) {
				throw new Exception("Provide Last Name Of Mentor");
			}
			service.firstInProcessSend(sitessme, getSessionUI().getActiveUser());
			runClientSideExecute("PF('dlgAddSiteSme').hide()");
			runClientSideExecute("PF('dlgUpdateSiteSme').hide()");
			addInfoMessage(super.getEntryLanguage("application.submitted.for.review"));
			prepareNew();
		}catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		}catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void updateSmeEntry(){
		try {
			if (sitessmeupdate.getSmeQualificationsList().size() == 0) {
				throw new Exception("Provide At Least 1 Qualification Before Proceeding");
			}
			if (sitessmeupdate.getUseCompanyAddress() == null) {
				sitessmeupdate.setUseCompanyAddress(true);
			}
			if (sitessmeupdate.getUseCompanyAddress() && sitessmeupdate.getSites() != null) {
				sitessmeupdate.setSites(null);
			}
			if (sitessmeupdate.getFirstName() == null || sitessmeupdate.getFirstName().isEmpty()) {
				throw new Exception("Provide First Name Of Mentor");
			}
			if (sitessmeupdate.getLastName() == null || sitessmeupdate.getLastName().isEmpty()) {
				throw new Exception("Provide Last Name Of Mentor");
			}
			service.firstInProcessSendUpdate(sitessmeupdate, getSessionUI().getActiveUser());
			runClientSideExecute("PF('dlgAddSiteSme').hide()");
			runClientSideExecute("PF('dlgUpdateSiteSme').hide()");
			addInfoMessage(super.getEntryLanguage("application.submitted.for.review"));
			prepareNewUpdate();
		} catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			// System.out.println(getValidationErrors());
			// addErrorMessage(getValidationErrors());
		}catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public List<Sites> completeSites(String desc) {
		List<Sites> l = null;
		try {
			l = sitesService.findByNameAndCompany(desc, company);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/** Getters and setters */
	public List<SitesSme> getSitesSmeList() {
		return sitessmeList;
	}

	public void setSitesSmeList(List<SitesSme> sitessmeList) {
		this.sitessmeList = sitessmeList;
	}

	public SitesSme getSitessme() {
		return sitessme;
	}

	public void setSitessme(SitesSme sitessme) {
		this.sitessme = sitessme;
	}

	public List<SitesSme> getSitesSmefilteredList() {
		return sitessmefilteredList;
	}

	public LazyDataModel<SitesSme> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SitesSme> dataModel) {
		this.dataModel = dataModel;
	}

	public boolean isDisableAll() {
		return disableAll;
	}

	public void setDisableAll(boolean disableAll) {
		this.disableAll = disableAll;
	}

	public boolean isUpload() {
		return upload;
	}

	public void setUpload(boolean upload) {
		this.upload = upload;
	}

	public Users getSmeUser() {
		return smeUser;
	}

	public void setSmeUser(Users smeUser) {
		this.smeUser = smeUser;
	}

	public IdPassportEnum getIdpassport() {
		return idpassport;
	}

	public void setIdpassport(IdPassportEnum idpassport) {
		this.idpassport = idpassport;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Long getMAX_PASSPORT_NUMBER() {
		return MAX_PASSPORT_NUMBER;
	}

	public void setMAX_PASSPORT_NUMBER(Long mAX_PASSPORT_NUMBER) {
		MAX_PASSPORT_NUMBER = mAX_PASSPORT_NUMBER;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
	}

	public String getPassportNumberFormat() {
		return passportNumberFormat;
	}

	public void setPassportNumberFormat(String passportNumberFormat) {
		this.passportNumberFormat = passportNumberFormat;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public boolean isCompanyHasSites() {
		return companyHasSites;
	}

	public void setCompanyHasSites(boolean companyHasSites) {
		this.companyHasSites = companyHasSites;
	}

	public boolean isEditFirstLastName() {
		return editFirstLastName;
	}

	public void setEditFirstLastName(boolean editFirstLastName) {
		this.editFirstLastName = editFirstLastName;
	}

	public List<SmeQualifications> getSmeQualificationsList() {
		return smeQualificationsList;
	}

	public void setSmeQualificationsList(List<SmeQualifications> smeQualificationsList) {
		this.smeQualificationsList = smeQualificationsList;
	}

	public SmeQualifications getSmeQualifications() {
		return smeQualifications;
	}

	public void setSmeQualifications(SmeQualifications smeQualifications) {
		this.smeQualifications = smeQualifications;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public static ChangeReason getChangeReason() {
		return changeReason;
	}

	public static void setChangeReason(ChangeReason changeReason) {
		SitesSmeUI.changeReason = changeReason;
	}

	public SitesSme getSitessmeupdate() {
		return sitessmeupdate;
	}

	public void setSitessmeupdate(SitesSme sitessmeupdate) {
		this.sitessmeupdate = sitessmeupdate;
	}
	
	private boolean skip;
	public String onFlowProcess(FlowEvent event) {
		if(skip) {
			skip = false;	//reset in case user goes back
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}
	public boolean isSkip() {
		return skip;
	}
	public void setSkip(boolean skip) {
		this.skip = skip;
	}
}
