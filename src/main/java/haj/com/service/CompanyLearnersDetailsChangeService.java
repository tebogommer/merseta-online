package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import haj.com.constants.HAJConstants;
import haj.com.dao.CompanyLearnersDetailsChangeDAO;
import haj.com.entity.Address;
import haj.com.entity.AddressChange;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersDetailsChange;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Tasks;
import haj.com.entity.UserChangeRequest;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.AddressEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class CompanyLearnersDetailsChangeService extends AbstractService {
	/** The dao. */
	private CompanyLearnersDetailsChangeDAO dao = new CompanyLearnersDetailsChangeDAO();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private RolesService rolesService = new RolesService();
	private RegionService regionService;
	private ConfigDocService configDocService = new ConfigDocService();
	/** Instance of service level */
	private static CompanyLearnersDetailsChangeService companyLearnersDetailsChangeService;
	public static synchronized CompanyLearnersDetailsChangeService instance() {
		if (companyLearnersDetailsChangeService == null) {
			companyLearnersDetailsChangeService = new CompanyLearnersDetailsChangeService();
		}
		return companyLearnersDetailsChangeService;
	}
	/**
	 * Get all CompanyLearnersDetailsChange
 	 * @author TechFinium 
 	 * @see   CompanyLearnersDetailsChange
 	 * @return a list of {@link haj.com.entity.CompanyLearnersDetailsChange}
	 * @throws Exception the exception
 	 */
	public List<CompanyLearnersDetailsChange> allCompanyLearnersDetailsChange() throws Exception {
	  	return dao.allCompanyLearnersDetailsChange();
	}


	/**
	 * Create or update CompanyLearnersDetailsChange.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CompanyLearnersDetailsChange
	 */
	public void create(CompanyLearnersDetailsChange entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  CompanyLearnersDetailsChange.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyLearnersDetailsChange
	 */
	public void update(CompanyLearnersDetailsChange entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CompanyLearnersDetailsChange.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyLearnersDetailsChange
	 */
	public void delete(CompanyLearnersDetailsChange entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @param companylearnerdetaileschange 
	 * @return a {@link haj.com.entity.CompanyLearnersDetailsChange}
	 * @throws Exception the exception
	 * @see    CompanyLearnersDetailsChange
	 */
	public CompanyLearnersDetailsChange findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find CompanyLearnersDetailsChange by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnersDetailsChange}
	 * @throws Exception the exception
	 * @see    CompanyLearnersDetailsChange
	 */
	public List<CompanyLearnersDetailsChange> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load CompanyLearnersDetailsChange
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnersDetailsChange}
	 * @throws Exception the exception
	 */
	public List<CompanyLearnersDetailsChange> allCompanyLearnersDetailsChange(int first, int pageSize) throws Exception {
		return dao.allCompanyLearnersDetailsChange(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of CompanyLearnersDetailsChange for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the CompanyLearnersDetailsChange
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CompanyLearnersDetailsChange.class);
	}
	
    /**
     * Lazy load CompanyLearnersDetailsChange with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 CompanyLearnersDetailsChange class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CompanyLearnersDetailsChange} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersDetailsChange> allCompanyLearnersDetailsChange(Class<CompanyLearnersDetailsChange> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<CompanyLearnersDetailsChange>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of CompanyLearnersDetailsChange for lazy load with filters
     * @author TechFinium 
     * @param entity CompanyLearnersDetailsChange class
     * @param filters the filters
     * @return Number of rows in the CompanyLearnersDetailsChange entity
     * @throws Exception the exception     
     */	
	public int count(Class<CompanyLearnersDetailsChange> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, CompanyLearnersDetailsChange entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}

		if (configDocProcess == ConfigDocProcessEnum.Learner && entity != null && entity.getInterventionType() != null) {
			if (!entity.getInterventionType().getBusary()) {
				// System.out.println(entity.getInterventionType().getDescription());
				if (entity.getDocs() != null && entity.getDocs().size() > 3) {
					entity.getDocs().remove(4);
					entity.getDocs().remove(entity.getDocs().size() - 1);

				}
			}
		}
	}
	
	/***/
	public void createLearnerWorkflow(CompanyLearnersDetailsChange companyLearnersDetailsChange, Users user,UserChangeRequest userChangeRequest, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList, AddressChange residentialAddressChange, AddressChange postalAddressChange, AddressChange birthAddressChange, Tasks task) throws Exception {
	
		List<Users> users = findRegionClinetServiceAdministrator(companyLearnersDetailsChange.getCompanyLearnersParent().getCompany());
		if (users.size() == 0) {
			throw new Exception("No Client Service Administrator assigned to region");
		}

		String desc = "Please review  ";
		companyLearnersDetailsChange.setStatus(ApprovalEnum.PendingApproval);
		dao.update(companyLearnersDetailsChange);
		List<IDataEntity> createBatch = new ArrayList<>();
		
		createBatch.add(residentialAddressChange);
		createBatch.add(postalAddressChange);
		createBatch.add(birthAddressChange);
		
		createBatch.add(userChangeRequest);
		createBatch.addAll(usersDisabilityList);
		createBatch.addAll(usersLanguages);
		dao.updateBatch(createBatch);
		TasksService.instance().completeTask(task);
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, companyLearnersDetailsChange.getId(), companyLearnersDetailsChange.getClass().getName(), true, ConfigDocProcessEnum.CompanyLearnerDetailesChange, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), users);
	}
	
	public void finalApproveWorkflow(CompanyLearnersDetailsChange companyLearnersDetailsChange, Users user,UserChangeRequest userChangeRequest, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList, AddressChange residentialAddressChange, AddressChange postalAddressChange, AddressChange birthAddressChange, Tasks task) throws Exception {
		updateCompanyLearnerInfomation(companyLearnersDetailsChange,  user, userChangeRequest, usersLanguages, usersDisabilityList,  residentialAddressChange,  postalAddressChange,  birthAddressChange);
		companyLearnersDetailsChange.setStatus(ApprovalEnum.Approved);
		companyLearnersDetailsChange.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearnersDetailsChange);

		List<Users> list = new ArrayList<>();
		list.add(companyLearnersDetailsChange.getCreateUser());
		list.add(companyLearnersDetailsChange.getCompanyLearnersParent().getUser());

		TasksService.instance().completeTask(task);

		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendApprovalEmail(companyLearnersDetailsChange, u);
		}
	}
	
	private void updateCompanyLearnerInfomation(CompanyLearnersDetailsChange companyLearnersDetailsChange, Users user,UserChangeRequest userChangeRequest, List<UsersLanguage> usersLanguages,
												List<UsersDisability> usersDisabilityList, AddressChange residentialAddressChange,AddressChange postalAddressChange, AddressChange birthAddressChange) throws Exception {
		List<IDataEntity> createBatch = new ArrayList<>();
		List<IDataEntity> updateBatch = new ArrayList<>();
		
		UsersLanguageService usersLanguageService = new UsersLanguageService();
		UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
		
		CompanyLearners companyLearners = companyLearnersDetailsChange.getCompanyLearnersParent();

		companyLearners.setHighestEducationEnum(companyLearnersDetailsChange.getHighestEducationEnum());
		companyLearners.setLastSchoolYear(companyLearnersDetailsChange.getLastSchoolYear());
		companyLearners.setPreviousSchools(companyLearnersDetailsChange.getPreviousSchools());
		companyLearners.setHighestQualification(companyLearnersDetailsChange.getHighestQualification());		
		
		Users learner = companyLearners.getUser();
		
		learner.setFirstName(userChangeRequest.getFirstName());
		learner.setMiddleName(userChangeRequest.getMiddleName());
		learner.setLastName(userChangeRequest.getLastName());
		learner.setNationality(userChangeRequest.getNationality());
		learner.setRsaIDNumber(userChangeRequest.getRsaIDNumber());
		learner.setPassportNumber(userChangeRequest.getPassportNumber());
		learner.setTelNumber(userChangeRequest.getTelNumber());
		learner.setCellNumber(userChangeRequest.getCellNumber());
		learner.setFaxNumber(userChangeRequest.getFaxNumber());
		learner.setEmail(userChangeRequest.getEmail());
		learner.setGender(userChangeRequest.getGender());
		learner.setEquity(userChangeRequest.getEquity());
		learner.setDisabilityStatus(userChangeRequest.getDisabilityStatus());
		learner.setTitle(userChangeRequest.getTitle());
		learner.setUrbanRuralEnum(userChangeRequest.getUrbanRuralEnum());
		learner.setLegalGaurdian(userChangeRequest.getLegalGaurdian());
		learner.setDisability(userChangeRequest.getDisability());
		learner.setDateOfBirth(userChangeRequest.getDateOfBirth());
		learner.setMaried(userChangeRequest.getMaried());		
		
		Address residentialAddress = AddressService.instance().findByKey(companyLearnersDetailsChange.getCompanyLearnersParent().getUser().getResidentialAddress().getId());
		Address postalAddress = AddressService.instance().findByKey(companyLearnersDetailsChange.getCompanyLearnersParent().getUser().getPostalAddress().getId());
		Address birthAddress = AddressService.instance().findByKey(companyLearnersDetailsChange.getCompanyLearnersParent().getUser().getBirthAddress().getId());
		
		AddressChangeService.instance().copyAddressChangeToAddress(residentialAddressChange, residentialAddress);
		AddressChangeService.instance().copyAddressChangeToAddress(postalAddressChange, postalAddress);
		if(birthAddressChange != null && birthAddressChange.getId() != null && birthAddress!=null && birthAddress.getId() != null) {
			AddressChangeService.instance().copyAddressChangeToAddress(birthAddressChange, birthAddress);
			updateBatch.add(birthAddress);
		}
		
		List<UsersLanguage> languageList= usersLanguageService.findByUser(learner);
		List<UsersDisability>disabilityList = usersDisabilityService.findByKeyUser(learner);
		
		CompanyLearnersService.instance().update(companyLearners);
		
		/*User Language Delete*/
		if(usersLanguages != null && usersLanguages.size() >0) {
			List<IDataEntity> usersLanguagesList = new ArrayList<>();
			usersLanguagesList.addAll(languageList);
			dao.deleteBatch(usersLanguagesList);
			for(UsersLanguage usersLanguage:usersLanguages) {
				usersLanguage.setUser(learner);
				createBatch.add(usersLanguage);
			}
		}
				
		/*User disability Delete*/
		if(usersDisabilityList != null && usersDisabilityList.size() >0) {
			for(UsersDisability usersDisability:usersDisabilityList) {
				usersDisability.setUser(learner);
				createBatch.add(usersDisability);
			}
			List<IDataEntity> userDisabilityList = new ArrayList<>();
			userDisabilityList.addAll(disabilityList);
			dao.deleteBatch(userDisabilityList);
			
		}
		
		updateBatch.add(companyLearners);
		updateBatch.add(learner);
		AddressService.instance().saveAddresses(residentialAddress, postalAddress);
		//updateBatch.add(residentialAddress);
		//updateBatch.add(postalAddress);
		dao.updateBatch(updateBatch);
		dao.createBatch(createBatch);
	}


	public void rejectCompanyLearner(CompanyLearnersDetailsChange companyLearnersDetailsChange, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		companyLearnersDetailsChange.setStatus(ApprovalEnum.Rejected);
		companyLearnersDetailsChange.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearnersDetailsChange);
		
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearnersDetailsChange.getId(), CompanyLearnersDetailsChange.class.getName(), rejectReasons, user, ConfigDocProcessEnum.CompanyLearnerDetailesChange);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		List<Users>list = new ArrayList<>();
		list.add(companyLearnersDetailsChange.getCreateUser());

		if (tasks != null) {
			TasksService.instance().completeTask(tasks);
		}
		
		String emailMessage = "Please Review Learner Registration By Non-Merseta Companies doing merSETA qualifications and are not funded by merSETA";
		String subject = "Learner Registration By Non-Merseta Companies doing merSETA qualifications and are not funded by merSETA";
		String description = "The learner registration application for: "+companyLearnersDetailsChange.getCompanyLearnersParent().getUser().getFirstName()+" "+companyLearnersDetailsChange.getCompanyLearnersParent().getUser().getLastName()+" ("+anIDNumber(companyLearnersDetailsChange.getCompanyLearnersParent().getUser())+", "+companyLearnersDetailsChange.getCompanyLearnersParent().getUser().getEmail()+") for "+companyLearnersDetailsChange.getCompanyLearnersParent().getEmployer().getCompanyName()+" ("+companyLearnersDetailsChange.getCompanyLearnersParent().getEmployer().getLevyNumber()+") has not been approved. Please review the application.";
		//TasksService.instance().findFirstInProcessAndCreateTask("", user, companyLearners.getId(), companyLearners.getClass().getName(), true, ConfigDocProcessEnum.LearnerRegistrationByNonMersetaCompanies, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), list);
		TasksService.instance().createTask(CompanyLearnersDetailsChange.class.getName(), null, emailMessage, subject, description, user, companyLearnersDetailsChange.getId(), true, true, tasks, list, ConfigDocProcessEnum.CompanyLearnerDetailesChange, null);

		list.add(companyLearnersDetailsChange.getCompanyLearnersParent().getUser());
		
		super.removeDuplicatesFromList(list);
		for(Users u : list) {
			sendRejectEmail(companyLearnersDetailsChange, rejectReasons, u);
		}	
		//sendRejectEmailToTheLearner(companyLearners, rejectReasons);
	}
	
	public void finalRejectCompanyLearners(CompanyLearnersDetailsChange companyLearnersDetailsChange, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		companyLearnersDetailsChange.setStatus(ApprovalEnum.Rejected);
		companyLearnersDetailsChange.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearnersDetailsChange);
		// TasksService.instance().completeTask(tasks);
		List<Users> users = new ArrayList<>();
		users.add(tasks.getCreateUser());
		new MailDef();
		TasksService.instance().completeTask(tasks);
		//TasksService.instance().createTask(CompanyLearners.class.getName(), companyLearners.getCompany(), emailMessage, subject, description, user, companyLearners.getId(), sendMail, createTask, tasks, users, tasks.getWorkflowProcess(), mailDef);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearnersDetailsChange.getId(), CompanyLearnersDetailsChange.class.getName(), rejectReasons, user, tasks.getWorkflowProcess());
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		List<Users>list = new ArrayList<>();
		list.add(companyLearnersDetailsChange.getCreateUser());
		list.add(companyLearnersDetailsChange.getCompanyLearnersParent().getUser());
		
		super.removeDuplicatesFromList(list);
		for(Users u : list) {
			sendRejectEmail(companyLearnersDetailsChange, rejectReasons, u);
		}	
	}
	
	public void sendApprovalEmail(CompanyLearnersDetailsChange companyLearnersDetailsChange, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// Qualification qualification =
		// getCompanyLearnerQualification(companyLearners);
		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		// String regionDescription = getRegionString(companyLearners.getCompany());

		String rsaIdPassport = anIDNumber(companyLearnersDetailsChange.getUser());

		String subject = "LEARNER DETAILS CHANGE APPLICATION OUTCOME";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + companyLearnersDetailsChange.getCreateUser().getFirstName() + " " + companyLearnersDetailsChange.getCreateUser().getLastName() + "</p>" 
		+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner:  " + companyLearnersDetailsChange.getUser().getFirstName() + " " + companyLearnersDetailsChange.getUser().getLastName() + " (" + rsaIdPassport + ")" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA hereby confirms that the detailes change application for the above learner have been registered accordingly." + "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the learner registration documents are available under the learner's profile." + "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "You are requested to note that if the operations of the business changes, or if it is desired to transfer the agreement to another employer, the merSETA must be notified beforehand." + "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Client Services Administrator </p>";

		GenericUtility.sendMail(toEmail, subject, mssg, null);

	}
	
	public void sendRejectEmail(CompanyLearnersDetailsChange companyLearnersDetailsChange, List<RejectReasons> rejectReasons, Users u) throws Exception {
		CompanyService companyService = new CompanyService();
		Company company = companyService.findByKey(companyLearnersDetailsChange.getCompanyLearnersParent().getCompany().getId());
		String accreditationNumber = "N/A";
		if(company !=null && company.getAccreditationNumber()!=null) {
			accreditationNumber = company.getAccreditationNumber();
		}
		String rsaIdPassport = anIDNumber(companyLearnersDetailsChange.getCompanyLearnersParent().getUser());

		String reasons = convertToHTML(rejectReasons);
		
		String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FirstName# #Surname#,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "#LearnerFirstName# #LearnerSurname# (#rsaIdPassport#) "
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + 
				"The merSETA hereby confirms that the detailes change application for the above learner have been not been registered accordingly for the following reason(s):</p>" 
				+ reasons
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" 
				+ "Please attend to the issues and re-submit the application. Should you require any assistance or further information, kindly contact the Client Liaison Officer at the "+getRegionString(company)+" Office</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours faithfully,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Client Services Administrator</p>" + "<br/>";
		welcome = welcome.replaceAll("#FirstName#", companyLearnersDetailsChange.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#Surname#", companyLearnersDetailsChange.getCreateUser().getLastName());
		welcome = welcome.replaceAll("#LearnerFirstName#", companyLearnersDetailsChange.getCompanyLearnersParent().getUser().getFirstName());
		welcome = welcome.replaceAll("#LearnerSurname#", companyLearnersDetailsChange.getCompanyLearnersParent().getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		welcome = welcome.replaceAll("#CompanyName#", company.getCompanyName());
		welcome = welcome.replaceAll("#AccreditationNumber#", accreditationNumber);

		GenericUtility.sendMail(u.getEmail(), "merSETA LEARNER DETAILES CHANGE APPLICATION", welcome, null);
	}
	
	private String anIDNumber(Users user) {
		if (user.getRsaIDNumber() != null && user.getRsaIDNumber() != "" && !user.getRsaIDNumber().isEmpty()) {
			return user.getRsaIDNumber();
		} else if (user.getPassportNumber() != null && user.getPassportNumber() != "" && !user.getPassportNumber().isEmpty()) {
			return user.getPassportNumber();
		} else {
			return "N/A";
		}
	}

	public String convertToHTML(List<RejectReasons> rejectReasons) {
		String sb = "<ul style=\"font-size:11.0pt;\";font-family:\"Arial\">";
		for (RejectReasons r : rejectReasons) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}
	
	public String getRegionString(Company company) throws Exception {
		String regionDescription = "";
		RegionTown rt = new RegionTown();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		}
		if (regionService == null) {
			regionService = new RegionService();
		}
		
		Region r = new Region();
		if (rt.getRegion() != null) {
			r = regionService.findByKey(rt.getRegion().getId());
			if(r!=null) {
				regionDescription = r.getDescription();
			}
		}
		return regionDescription;
	}


	@SuppressWarnings("unused")
	public void submitLearnerUpdate(Users activeUser, Users user, CompanyLearners companylearners, boolean requireGaurdian, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList) throws Exception {
		
		List<Users> users = findRegionClinetServiceAdministrator(companylearners.getCompany());
		if (users.size() == 0) {
			throw new Exception("No Client Service Administrator assigned to region");
		}
		
		List<IDataEntity> createBatch = new ArrayList<>();
		if(user!= null) {
			UserChangeRequest userChangeRequest = new haj.com.entity.UserChangeRequest(user);
			userChangeRequest.setApprovalStatus(ApprovalEnum.PendingApproval);
			createBatch.add(userChangeRequest);
			
		}else {
			throw new Exception("User information errorr");
		}
		
		AddressChange residentialAddressChange = AddressChangeService.instance().copyAddressToAddressChange(user.getResidentialAddress());
		AddressChange postalAddressChange = AddressChangeService.instance().copyAddressToAddressChange(user.getPostalAddress());
		residentialAddressChange.setUser(companylearners.getUser());
		residentialAddressChange.setAddressEnum(AddressEnum.Residential);
		postalAddressChange.setUser(companylearners.getUser());
		postalAddressChange.setAddressEnum(AddressEnum.Postal);
		createBatch.add(residentialAddressChange);
		createBatch.add(postalAddressChange);
		//AddressChangeService.instance().saveAddresses(residentialAddressChange, postalAddressChange);
		
		if(user.getBirthAddress()  != null) {
			AddressChange birthAddressChange = AddressChangeService.instance().copyAddressToAddressChange(user.getBirthAddress());
			birthAddressChange.setUser(companylearners.getUser());
			birthAddressChange.setAddressEnum(AddressEnum.Birth);
			createBatch.add(birthAddressChange);
		}
		
		CompanyLearnersDetailsChange companyLearnersDetailsChange = new CompanyLearnersDetailsChange();
		companyLearnersDetailsChange.setCreateUser(activeUser);
		companyLearnersDetailsChange.setHighestEducationEnum(companylearners.getHighestEducationEnum());
		companyLearnersDetailsChange.setLastSchoolYear(companylearners.getLastSchoolYear());
		companyLearnersDetailsChange.setPreviousSchools(companylearners.getPreviousSchools());
		companyLearnersDetailsChange.setHighestQualification(companylearners.getHighestQualification());
		companyLearnersDetailsChange.setCompanyLearnersParent(companylearners);
		createBatch.add(companyLearnersDetailsChange);
		
		
		
		this.dao.createBatch(createBatch);
		List<IDataEntity> batch = new ArrayList<>();
		if (usersLanguages != null && usersLanguages.size() > 0) {
			for (UsersLanguage ul : usersLanguages) {
				ul.setTargetClass(CompanyLearnersDetailsChange.class.getName());
				ul.setTargetKey(companyLearnersDetailsChange.getId());
				batch.add(ul);
			}
		}
		if (usersDisabilityList != null && usersDisabilityList.size() > 0) {
			for (UsersDisability ud : usersDisabilityList) {
				ud.setTargetClass(CompanyLearnersDetailsChange.class.getName());
				ud.setTargetKey(companyLearnersDetailsChange.getId());
				batch.add(ud);
			}
		}
		this.dao.createBatch(batch);
		TasksService.instance().findFirstInProcessAndCreateTask("", activeUser, companyLearnersDetailsChange.getId(), companyLearnersDetailsChange.getClass().getName(), true, ConfigDocProcessEnum.CompanyLearnerDetailesChange, null, users);
	}
	public void submitLearnerUpdate(Users activeUser, UserChangeRequest userChangeRequest, AddressChange residentialAddressChange, AddressChange postalAddressChange, 
									AddressChange birthAddressChange, List<UsersLanguage> usersLanguageList, List<UsersDisability> usersDisabilityList, CompanyLearners companylearners) throws Exception {
		
		
		List<IDataEntity> createBatch = new ArrayList<>();
		List<Users> users = findRegionClinetServiceAdministrator(companylearners.getCompany());
		if (users.size() == 0) {
			throw new Exception("No Client Service Administrator assigned to region");
		}
		
		CompanyLearnersDetailsChange companyLearnersDetailsChange = new CompanyLearnersDetailsChange();
		companyLearnersDetailsChange.setCreateUser(activeUser);
		companyLearnersDetailsChange.setHighestEducationEnum(companylearners.getHighestEducationEnum());
		companyLearnersDetailsChange.setLastSchoolYear(companylearners.getLastSchoolYear());
		companyLearnersDetailsChange.setPreviousSchools(companylearners.getPreviousSchools());
		companyLearnersDetailsChange.setHighestQualification(companylearners.getHighestQualification());
		companyLearnersDetailsChange.setCompanyLearnersParent(companylearners);
		companyLearnersDetailsChange.setUser(companylearners.getUser());
		createBatch.add(companyLearnersDetailsChange);
		
		residentialAddressChange.setUser(companylearners.getUser());
		residentialAddressChange.setAddressEnum(AddressEnum.Residential);
		postalAddressChange.setUser(companylearners.getUser());
		postalAddressChange.setAddressEnum(AddressEnum.Postal);
		if(birthAddressChange != null && birthAddressChange.getTown() != null) {			
			birthAddressChange.setUser(companylearners.getUser());
			birthAddressChange.setAddressEnum(AddressEnum.Birth);
			createBatch.add(birthAddressChange);
		}
		createBatch.add(residentialAddressChange);
		createBatch.add(postalAddressChange);
		
		userChangeRequest.setApprovalStatus(ApprovalEnum.PendingApproval);
		userChangeRequest.setUser(companylearners.getUser());
		createBatch.add(userChangeRequest);
		
		this.dao.createBatch(createBatch);
		List<IDataEntity> batch = new ArrayList<>();
		if (usersLanguageList != null && usersLanguageList.size() > 0) {
			for (UsersLanguage ul : usersLanguageList) {
				ul.setTargetClass(CompanyLearnersDetailsChange.class.getName());
				ul.setTargetKey(companyLearnersDetailsChange.getId());
				batch.add(ul);
			}
		}
		if (usersDisabilityList != null && usersDisabilityList.size() > 0) {
			for (UsersDisability ud : usersDisabilityList) {
				ud.setTargetClass(CompanyLearnersDetailsChange.class.getName());
				ud.setTargetKey(companyLearnersDetailsChange.getId());
				batch.add(ud);
			}
		}
		this.dao.createBatch(batch);
		TasksService.instance().findFirstInProcessAndCreateTask("", activeUser, companyLearnersDetailsChange.getId(), companyLearnersDetailsChange.getClass().getName(), true, ConfigDocProcessEnum.CompanyLearnerDetailesChange, null, users);
	}
	
	public List<Users> findRegionClinetServiceAdministrator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_ADMIN_ROLE_ID);
		list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public void copyLearnerInfo(Users learner, UserChangeRequest userChangeRequest) {
		learner.setFirstName(userChangeRequest.getFirstName());
		learner.setMiddleName(userChangeRequest.getMiddleName());
		learner.setLastName(userChangeRequest.getLastName());
		learner.setNationality(userChangeRequest.getNationality());
		learner.setRsaIDNumber(userChangeRequest.getRsaIDNumber());
		learner.setPassportNumber(userChangeRequest.getPassportNumber());
		learner.setTelNumber(userChangeRequest.getTelNumber());
		learner.setCellNumber(userChangeRequest.getCellNumber());
		learner.setFaxNumber(userChangeRequest.getFaxNumber());
		learner.setEmail(userChangeRequest.getEmail());
		learner.setGender(userChangeRequest.getGender());
		learner.setEquity(userChangeRequest.getEquity());
		learner.setDisabilityStatus(userChangeRequest.getDisabilityStatus());
		learner.setTitle(userChangeRequest.getTitle());
		learner.setUrbanRuralEnum(userChangeRequest.getUrbanRuralEnum());
		learner.setLegalGaurdian(userChangeRequest.getLegalGaurdian());
		learner.setDisability(userChangeRequest.getDisability());
		learner.setDateOfBirth(userChangeRequest.getDateOfBirth());
		learner.setMaried(userChangeRequest.getMaried());	
	}
	
	public void copyUserChangeRequestInfo(UserChangeRequest userChangeRequest, Users learner) {
		userChangeRequest.setFirstName(learner.getFirstName());
		userChangeRequest.setMiddleName(learner.getMiddleName());
		userChangeRequest.setLastName(learner.getLastName());
		userChangeRequest.setNationality(learner.getNationality());
		userChangeRequest.setRsaIDNumber(learner.getRsaIDNumber());
		userChangeRequest.setPassportNumber(learner.getPassportNumber());
		userChangeRequest.setTelNumber(learner.getTelNumber());
		userChangeRequest.setCellNumber(learner.getCellNumber());
		userChangeRequest.setFaxNumber(learner.getFaxNumber());
		userChangeRequest.setEmail(learner.getEmail());
		userChangeRequest.setGender(learner.getGender());
		userChangeRequest.setEquity(learner.getEquity());
		userChangeRequest.setDisabilityStatus(learner.getDisabilityStatus());
		userChangeRequest.setTitle(learner.getTitle());
		userChangeRequest.setUrbanRuralEnum(learner.getUrbanRuralEnum());
		userChangeRequest.setLegalGaurdian(learner.getLegalGaurdian());
		userChangeRequest.setDisability(learner.getDisability());
		userChangeRequest.setDateOfBirth(learner.getDateOfBirth());
		userChangeRequest.setMaried(learner.getMaried());	
	}
	
	public boolean checkexitingUpdate(Users user) throws Exception {
		List<CompanyLearnersDetailsChange> list = dao.findByUsersAndStatus(user.getId(), ApprovalEnum.PendingApproval);
		if(list !=null && list.size()>0) {
			return false;
		}else {
			return true;
		}
	}	
}
