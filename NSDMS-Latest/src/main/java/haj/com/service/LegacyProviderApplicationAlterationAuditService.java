package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.LegacyProviderApplicationAlterationAuditDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.LegacyProviderApplicationAlterationAudit;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.Users;
import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyProviderApplicationAlterationAuditService extends AbstractService {
	
	/** The dao. */
	private LegacyProviderApplicationAlterationAuditDAO dao = new LegacyProviderApplicationAlterationAuditDAO();
	
	/** Extended Service levels */
	private CompanyQualificationsService companyQualificationsService;
	private CompanyUnitStandardService companyUnitStandardService;
	private TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService;
	private TrainingProviderSkillsSetService trainingProviderSkillsSetService;
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	

	/**
	 * Get all LegacyProviderApplicationAlterationAudit
 	 * @author TechFinium 
 	 * @see   LegacyProviderApplicationAlterationAudit
 	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationAlterationAudit}
	 * @throws Exception the exception
 	 */
	public List<LegacyProviderApplicationAlterationAudit> allLegacyProviderApplicationAlterationAudit() throws Exception {
	  	return dao.allLegacyProviderApplicationAlterationAudit();
	}


	/**
	 * Create or update LegacyProviderApplicationAlterationAudit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LegacyProviderApplicationAlterationAudit
	 */
	public void create(LegacyProviderApplicationAlterationAudit entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LegacyProviderApplicationAlterationAudit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyProviderApplicationAlterationAudit
	 */
	public void update(LegacyProviderApplicationAlterationAudit entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LegacyProviderApplicationAlterationAudit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyProviderApplicationAlterationAudit
	 */
	public void delete(LegacyProviderApplicationAlterationAudit entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyProviderApplicationAlterationAudit}
	 * @throws Exception the exception
	 * @see    LegacyProviderApplicationAlterationAudit
	 */
	public LegacyProviderApplicationAlterationAudit findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LegacyProviderApplicationAlterationAudit by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationAlterationAudit}
	 * @throws Exception the exception
	 * @see    LegacyProviderApplicationAlterationAudit
	 */
	public List<LegacyProviderApplicationAlterationAudit> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LegacyProviderApplicationAlterationAudit
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationAlterationAudit}
	 * @throws Exception the exception
	 */
	public List<LegacyProviderApplicationAlterationAudit> allLegacyProviderApplicationAlterationAudit(int first, int pageSize) throws Exception {
		return dao.allLegacyProviderApplicationAlterationAudit(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LegacyProviderApplicationAlterationAudit for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LegacyProviderApplicationAlterationAudit
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LegacyProviderApplicationAlterationAudit.class);
	}
	
    /**
     * Lazy load LegacyProviderApplicationAlterationAudit with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LegacyProviderApplicationAlterationAudit class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LegacyProviderApplicationAlterationAudit} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderApplicationAlterationAudit> allLegacyProviderApplicationAlterationAudit(Class<LegacyProviderApplicationAlterationAudit> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LegacyProviderApplicationAlterationAudit>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LegacyProviderApplicationAlterationAudit for lazy load with filters
     * @author TechFinium 
     * @param entity LegacyProviderApplicationAlterationAudit class
     * @param filters the filters
     * @return Number of rows in the LegacyProviderApplicationAlterationAudit entity
     * @throws Exception the exception     
     */	
	public int count(Class<LegacyProviderApplicationAlterationAudit> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	
	/**
	 * Prep a new audit with base information
	 */	
	public LegacyProviderApplicationAlterationAudit prepNewAudit(TrainingProviderApplication trainingProviderApplication, Company company, LegacyProviderAccreditation legacyProviderAccreditation, Users sessionUser, QualificationTypeSelectionEnum qualificationTypeSelectionEnum, String reason) throws Exception{
		LegacyProviderApplicationAlterationAudit audit = new LegacyProviderApplicationAlterationAudit();
		audit.setUsers(sessionUser);
		audit.setTrainingProviderApplication(trainingProviderApplication);
		audit.setCompany(company);
		audit.setLegacyProviderAccreditation(legacyProviderAccreditation);
		audit.setQualificationTypeSelectionEnum(qualificationTypeSelectionEnum);
		audit.setReasonForAlteration(reason);
		return audit;
	}
	
	
	/*
	private TrainingProviderSkillsSetService trainingProviderSkillsSetService;
	 */
	
	public void createNewLinkAndAudit(LegacyProviderApplicationAlterationAudit audit, Qualification qualification, SkillsSet skillsSet, SkillsProgram skillsProgram, UnitStandards unitStandard, List<UnitStandards> unitStandardList) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		
		StringBuilder mainQualification = new StringBuilder();
		StringBuilder unitStandards = new StringBuilder();
		
		switch (audit.getQualificationTypeSelectionEnum()) {
		case Qualification:
			// ensure value
			if (qualification == null) {
				throw new Exception("Select a qualification before proceeding.");
			}
			
			if (companyQualificationsService == null) {
				companyQualificationsService = new CompanyQualificationsService();
			}
			
			// ensure not already added
			if (companyQualificationsService.countByApplicationAndQualification(TrainingProviderApplication.class.getName(), audit.getTrainingProviderApplication().getId(), qualification.getId(),audit.getCompany().getId()) > 0 ) {
				throw new Exception("Qualification already assigned to application. Please select a different qualification.");
			}
			
			// creates link
			CompanyQualifications cq = new CompanyQualifications(audit.getCompany(), qualification);
			cq.setManuallyAdded(true);
			cq.setTargetClass(TrainingProviderApplication.class.getName());
			cq.setTargetKey(audit.getTrainingProviderApplication().getId());
			cq.setCompany(audit.getCompany());
			cq.setAccept(true);
			createList.add(cq);
			// creates audit
			audit.setQualification(qualification);
			createList.add(audit);
			mainQualification.append(qualification.getSaqaQualification());
			// unit standard stuff
			if (!unitStandardList.isEmpty()) {
				for (UnitStandards us : unitStandardList) {
					boolean addUnitStandard = false;
					
					if (companyUnitStandardService == null) {
						companyUnitStandardService = new CompanyUnitStandardService();
					}
					
					// check if already assigned
					if (companyUnitStandardService.countUnitStandardTargetClassAndKey(TrainingProviderApplication.class.getName(), audit.getTrainingProviderApplication().getId(), us) == 0) {
						addUnitStandard = true;
					}
					
					// if not assigned:
					if (addUnitStandard) {
						// creates link 
						CompanyUnitStandard cu = new CompanyUnitStandard();
						cu.setManuallyAdded(true);
						cu.setTargetClass(TrainingProviderApplication.class.getName());
						cu.setTargetKey(audit.getTrainingProviderApplication().getId());
						cu.setCompany(audit.getCompany());
						cu.setUnitStandard(us);
						cu.setAccept(true);
						createList.add(cu);
						cu = null;
						
						
						// creates audit
						LegacyProviderApplicationAlterationAudit subAudit = new LegacyProviderApplicationAlterationAudit();
						subAudit.setLegacyProviderApplicationAlterationAudit(audit);
						subAudit.setCompany(audit.getCompany());
						subAudit.setTrainingProviderApplication(audit.getTrainingProviderApplication());
						subAudit.setLegacyProviderAccreditation(audit.getLegacyProviderAccreditation());
						subAudit.setUsers(audit.getUsers());
						subAudit.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.UnitStandards);
						subAudit.setUnitStandard(us);
						subAudit.setReasonForAlteration(audit.getReasonForAlteration());
						createList.add(subAudit);
						subAudit = null;
						
						unitStandards.append("<ul>");
						unitStandards.append("<li> (" + us.getCode().toString() + ") " + us.getTitle() + "</li>");
						unitStandards.append("</ul>");
					}
				}
				unitStandardList = null;
			}
			break;
		case SkillsSet:
			// ensure value
			if (skillsSet == null) {
				throw new Exception("Select a skills set before proceeding.");
			}
			
			// ensure not already added
			if (trainingProviderSkillsSetService == null) {
				trainingProviderSkillsSetService = new TrainingProviderSkillsSetService();
			}
			if (trainingProviderSkillsSetService.countByApplicationAndSkillsSet(audit.getTrainingProviderApplication(), skillsSet) > 0 ) {
				throw new Exception("Skills set already assigned to application. Please select a different skills set.");
			}
			// creates link
			TrainingProviderSkillsSet tpSkillsSet = new TrainingProviderSkillsSet(skillsSet, audit.getTrainingProviderApplication());
			tpSkillsSet.setManuallyAdded(true);
			tpSkillsSet.setAccept(true);
			tpSkillsSet.setTrainingProviderApplication(audit.getTrainingProviderApplication());
			createList.add(tpSkillsSet);
			
			// creates audit
			audit.setSkillsSet(skillsSet);
			createList.add(audit);
			if (skillsSet.getProgrammeID() != null && !skillsSet.getProgrammeID().isEmpty()) {
				mainQualification.append("("+ skillsSet.getProgrammeID() + ") ");
			}
			mainQualification.append(skillsSet.getDescription());
			
			
			// unit standard stuff
			if (!unitStandardList.isEmpty()) {
				for (UnitStandards us : unitStandardList) {
					boolean addUnitStandard = false;
					
					if (companyUnitStandardService == null) {
						companyUnitStandardService = new CompanyUnitStandardService();
					}
					
					// check if already assigned
					if (companyUnitStandardService.countUnitStandardTargetClassAndKey(TrainingProviderApplication.class.getName(), audit.getTrainingProviderApplication().getId(), us) == 0) {
						addUnitStandard = true;
					}
					
					// if not assigned:
					if (addUnitStandard) {
						// creates link 
						CompanyUnitStandard cu = new CompanyUnitStandard();
						cu.setManuallyAdded(true);
						cu.setTargetClass(TrainingProviderApplication.class.getName());
						cu.setTargetKey(audit.getTrainingProviderApplication().getId());
						cu.setCompany(audit.getCompany());
						cu.setUnitStandard(us);
						cu.setAccept(true);
						createList.add(cu);
						cu = null;
						
						// creates audit
						LegacyProviderApplicationAlterationAudit subAudit = new LegacyProviderApplicationAlterationAudit();
						subAudit.setLegacyProviderApplicationAlterationAudit(audit);
						subAudit.setCompany(audit.getCompany());
						subAudit.setTrainingProviderApplication(audit.getTrainingProviderApplication());
						subAudit.setLegacyProviderAccreditation(audit.getLegacyProviderAccreditation());
						subAudit.setUsers(audit.getUsers());
						subAudit.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.UnitStandards);
						subAudit.setUnitStandard(us);
						subAudit.setReasonForAlteration(audit.getReasonForAlteration());
						createList.add(subAudit);
						subAudit = null;
						
						unitStandards.append("<ul>");
						unitStandards.append("<li> (" + us.getCode().toString() + ") " + us.getTitle() + "</li>");
						unitStandards.append("</ul>");
						
					}
				}
				unitStandardList = null;
			}
			break;
		case SkillsProgram:
			// ensure value
			if (skillsProgram == null) {
				throw new Exception("Select a skills program before proceeding.");
			}
			
			if (trainingProviderSkillsProgrammeService == null) {
				trainingProviderSkillsProgrammeService = new TrainingProviderSkillsProgrammeService();
			}
			
			// ensure not already added
			if (trainingProviderSkillsProgrammeService.countByApplicationAndSkillsProgramme(audit.getTrainingProviderApplication(), skillsProgram) > 0) {
				throw new Exception("Skills program already assigned to application. Please select a different skills program.");
			}
			
			// creates link
			TrainingProviderSkillsProgramme tpSkillsProg = new TrainingProviderSkillsProgramme(skillsProgram,  audit.getTrainingProviderApplication());
			tpSkillsProg.setManuallyAdded(true);
			tpSkillsProg.setAccept(true);
			tpSkillsProg.setTrainingProviderApplication(audit.getTrainingProviderApplication());
			createList.add(tpSkillsProg);
			
			// creates audit
			audit.setSkillsProgram(skillsProgram);
			createList.add(audit);
			
			if (skillsProgram.getProgrammeID() != null && !skillsProgram.getProgrammeID().isEmpty()) {
				mainQualification.append("("+ skillsProgram.getProgrammeID() + ") ");
			}
			mainQualification.append(skillsProgram.getDescription());
			
			// unit standard stuff
			if (!unitStandardList.isEmpty()) {
				for (UnitStandards us : unitStandardList) {
					boolean addUnitStandard = false;
					
					if (companyUnitStandardService == null) {
						companyUnitStandardService = new CompanyUnitStandardService();
					}
					
					// check if already assigned
					if (companyUnitStandardService.countUnitStandardTargetClassAndKey(TrainingProviderApplication.class.getName(), audit.getTrainingProviderApplication().getId(), us) == 0) {
						addUnitStandard = true;
					}
					
					// if not assigned:
					if (addUnitStandard) {
						// creates link 
						CompanyUnitStandard cu = new CompanyUnitStandard();
						cu.setManuallyAdded(true);
						cu.setTargetClass(TrainingProviderApplication.class.getName());
						cu.setTargetKey(audit.getTrainingProviderApplication().getId());
						cu.setCompany(audit.getCompany());
						cu.setUnitStandard(us);
						cu.setAccept(true);
						createList.add(cu);
						cu = null;
						
						// creates audit
						LegacyProviderApplicationAlterationAudit subAudit = new LegacyProviderApplicationAlterationAudit();
						subAudit.setLegacyProviderApplicationAlterationAudit(audit);
						subAudit.setCompany(audit.getCompany());
						subAudit.setTrainingProviderApplication(audit.getTrainingProviderApplication());
						subAudit.setLegacyProviderAccreditation(audit.getLegacyProviderAccreditation());
						subAudit.setUsers(audit.getUsers());
						subAudit.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.UnitStandards);
						subAudit.setUnitStandard(us);
						subAudit.setReasonForAlteration(audit.getReasonForAlteration());
						createList.add(subAudit);
						subAudit = null;
						
						unitStandards.append("<ul>");
						unitStandards.append("<li> (" + us.getCode().toString() + ") " + us.getTitle() + "</li>");
						unitStandards.append("</ul>");
					}
				}
				unitStandardList = null;
			}
			break;
		case UnitStandards:
			// ensure value
			if (unitStandard == null) {
				throw new Exception("Select a unit standard before proceeding.");
			}
			
			// ensure not already added
			if (companyUnitStandardService == null) {
				companyUnitStandardService = new CompanyUnitStandardService();
			}
			if (companyUnitStandardService.countUnitStandardTargetClassAndKey(TrainingProviderApplication.class.getName(), audit.getTrainingProviderApplication().getId(), unitStandard) > 0) {
				throw new Exception("Unit standard already assigned to application. Please select a different unit standard.");
			}
			
			// creates link
			CompanyUnitStandard cu = new CompanyUnitStandard();
			cu.setManuallyAdded(true);
			cu.setTargetClass(TrainingProviderApplication.class.getName());
			cu.setTargetKey(audit.getTrainingProviderApplication().getId());
			cu.setCompany(audit.getCompany());
			cu.setUnitStandard(unitStandard);
			cu.setAccept(true);
			createList.add(cu);
			
			mainQualification.append("(" + unitStandard.getCode().toString() + ") " + unitStandard.getTitle() + "</li>");
			
			// creates audit
			audit.setUnitStandard(unitStandard);
			createList.add(audit);
			
			break;
		default:
			throw new Exception("Unable to locate what link must be created. Contact Support!");
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList, 500);
		}
		
		// send notification
		List<Users> notificationRecivers = locateUsersForNotification();
		
		// populate subject
		String subject = "PROVIDER SCOPE AMENDMENT";
		
		// populate main message
		StringBuilder message = new StringBuilder();
		message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#</p>");
		message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised #FULL_NAME_SESSION_USER# has implemented the following changes to the provider’s scope:</p>");
		message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">");
		message.append("Provider name: #PROVIDER_NAME# <br/>");
		message.append("Accreditation number: #ACCREDICIATION_NUMBER# <br/>");
		message.append("Scope Type: #SCOPE_TYPE#<br/>");
		message.append("Additional Scope: #MAIN_SCOPE#<br/>");
		if (unitStandards != null && !unitStandards.toString().trim().isEmpty()) {
			message.append("Scope Unit Standards:<br/>");
			message.append("#UNIT_STANDARD_LIST#");
		}
		message.append("</p>");
		message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">System Generated Notification </p>");
		
		// replace string and send notification
		notifyUsers(subject, replaceStringMessage(message.toString(), audit, mainQualification.toString(), unitStandards.toString()), notificationRecivers, false, null);
	}
	
	private List<Users> locateUsersForNotification() throws Exception{
		List<Users> notificationRecivers = new ArrayList<>();
		List<Users> employeeList = new ArrayList<>();
		// Senior Manager: Quality Assurance and Partnerships, Manager: Quality Assurance, Chief Operations Officer, Senior Manager: Client Services, Senior Manager: Administration
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID));
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_MANAGER_QUALITY_ASSURANCE_ID));
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_CHIEF_OPERATIONS_OFFICER_ID));
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_CLIENT_SERVICE_ID));
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_ADMINISTRATION_ID));
		
		for (Users employee : employeeList) {
			if (determainIfUserCanBeAdded(notificationRecivers, employee)) {
				notificationRecivers.add(employee);
			}
		}	
		return notificationRecivers;
	}


	private boolean determainIfUserCanBeAdded(List<Users> usersList, Users newUser){
		// prevents duplicate users being added to notification
		boolean addUser = true;
		for (Users users : usersList) {
			if (users.getId().equals(newUser.getId())){
				addUser = false;
				break;
			}
		}
		return addUser;
	}
	
	
	private String replaceStringMessage(String message, LegacyProviderApplicationAlterationAudit audit, String mainQualification, String unitStandardList) throws Exception {
		// Full Name Session User
		message = message.replace("#FULL_NAME_SESSION_USER#", ( (audit.getUsers() != null && audit.getUsers().getId() != null) ?  audit.getUsers().getFirstName().trim() + " " + audit.getUsers().getLastName().trim() + " (" + audit.getUsers().getEmail().trim() + ")" : "#UNABLE_TO_LOCATE_FULL_NAME_SESSION_USER#" ) );
		// provider name
		message = message.replace("#PROVIDER_NAME#", ( (audit.getCompany() != null && audit.getCompany().getId() != null) ?  audit.getCompany().getCompanyName() : "#UNABLE_TO_LOCATE_PROVIDER_NAME#" ) );
		// ACCREDICIATION_NUMBER
		message = message.replace("#ACCREDICIATION_NUMBER#", ( (audit.getTrainingProviderApplication() != null && audit.getTrainingProviderApplication().getId() != null && audit.getTrainingProviderApplication().getAccreditationNumber() != null) ?  audit.getTrainingProviderApplication().getAccreditationNumber() : "#UNABLE_TO_LOCATE_ACCREDICIATION_NUMBER#" ) );
		// SCOPE_TYPE
		message = message.replace("#SCOPE_TYPE#", ( (audit.getQualificationTypeSelectionEnum() != null) ?  audit.getQualificationTypeSelectionEnum().getFriendlyName() : "#UNABLE_TO_LOCATE_SCOPE_TYPE#" ) );
		// MAIN_SCOPE
		message = message.replace("#MAIN_SCOPE#", ( (mainQualification != null && !mainQualification.trim().isEmpty()) ?  mainQualification.trim() : "#UNABLE_TO_LOCATE_MAIN_SCOPE#" ) );
		if (unitStandardList != null && !unitStandardList.trim().isEmpty()) {
			// UNIT_STANDARD_LIST
			message = message.replace("#UNIT_STANDARD_LIST#", ( (unitStandardList != null && !unitStandardList.trim().isEmpty()) ?  unitStandardList.trim() : "#UNABLE_TO_LOCATE_UNIT_STANDARD_LIST#" ) );
		}
		return message;
	}


	public void notifyUsers(String subject, String message, List<Users> notificationRecivers, boolean withAttachment, List<AttachmentBean> attachmentBeans) {
		for (Users users : notificationRecivers) {
			String fullName = "";
			if (users.getTitle() != null && users.getTitle().getDescription() != null) {
				fullName = users.getTitle().getDescription() + " ";
			}
			fullName += users.getFirstName().trim() + " " + users.getLastName().trim();
			if (withAttachment) {
				GenericUtility.sendMailWithAttachementTempWithLog(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), attachmentBeans, null);
			}else {
				GenericUtility.sendMail(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), null);
			}
		}
	}
	
}
