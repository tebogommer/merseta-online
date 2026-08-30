package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.LegacyProviderApplicationSiteAllocationDAO;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompany;
import haj.com.entity.LegacyProviderApplicationSiteAllocation;
import haj.com.entity.ProviderApplicationSuspensionDeActivated;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingSite;
import haj.com.entity.Users;
import haj.com.entity.enums.ProviderSusActionsEnum;
import haj.com.entity.lookup.Training;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class LegacyProviderApplicationSiteAllocationService extends AbstractService {
	
	/** The dao. */
	private LegacyProviderApplicationSiteAllocationDAO dao = new LegacyProviderApplicationSiteAllocationDAO();
	
	/* Service levels */
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = null;
	private TrainingProviderApplicationService trainingProviderApplicationService = null;

	/**
	 * Get all LegacyProviderApplicationSiteAllocation
 	 * @author TechFinium 
 	 * @see   LegacyProviderApplicationSiteAllocation
 	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationSiteAllocation}
	 * @throws Exception the exception
 	 */
	public List<LegacyProviderApplicationSiteAllocation> allLegacyProviderApplicationSiteAllocation() throws Exception {
	  	return dao.allLegacyProviderApplicationSiteAllocation();
	}


	/**
	 * Create or update LegacyProviderApplicationSiteAllocation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LegacyProviderApplicationSiteAllocation
	 */
	public void create(LegacyProviderApplicationSiteAllocation entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LegacyProviderApplicationSiteAllocation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyProviderApplicationSiteAllocation
	 */
	public void update(LegacyProviderApplicationSiteAllocation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LegacyProviderApplicationSiteAllocation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyProviderApplicationSiteAllocation
	 */
	public void delete(LegacyProviderApplicationSiteAllocation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyProviderApplicationSiteAllocation}
	 * @throws Exception the exception
	 * @see    LegacyProviderApplicationSiteAllocation
	 */
	public LegacyProviderApplicationSiteAllocation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LegacyProviderApplicationSiteAllocation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationSiteAllocation}
	 * @throws Exception the exception
	 * @see    LegacyProviderApplicationSiteAllocation
	 */
	public List<LegacyProviderApplicationSiteAllocation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LegacyProviderApplicationSiteAllocation
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyProviderApplicationSiteAllocation}
	 * @throws Exception the exception
	 */
	public List<LegacyProviderApplicationSiteAllocation> allLegacyProviderApplicationSiteAllocation(int first, int pageSize) throws Exception {
		return dao.allLegacyProviderApplicationSiteAllocation(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LegacyProviderApplicationSiteAllocation for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LegacyProviderApplicationSiteAllocation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LegacyProviderApplicationSiteAllocation.class);
	}
	
    /**
     * Lazy load LegacyProviderApplicationSiteAllocation with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LegacyProviderApplicationSiteAllocation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LegacyProviderApplicationSiteAllocation} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderApplicationSiteAllocation> allLegacyProviderApplicationSiteAllocation(Class<LegacyProviderApplicationSiteAllocation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList(( List<LegacyProviderApplicationSiteAllocation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	}
	
    /**
     * Get count of LegacyProviderApplicationSiteAllocation for lazy load with filters
     * @author TechFinium 
     * @param entity LegacyProviderApplicationSiteAllocation class
     * @param filters the filters
     * @return Number of rows in the LegacyProviderApplicationSiteAllocation entity
     * @throws Exception the exception     
     */	
	public int count(Class<LegacyProviderApplicationSiteAllocation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	private List<LegacyProviderApplicationSiteAllocation> populateAdditionalInformationList(List<LegacyProviderApplicationSiteAllocation> list) throws Exception {
		for (LegacyProviderApplicationSiteAllocation entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private LegacyProviderApplicationSiteAllocation populateAdditionalInformation(LegacyProviderApplicationSiteAllocation entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDoc(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
		return entity;
	}
	
	public void submitNewEntry(List<Doc> supportingDocs, Users sessionUser, TrainingProviderApplication tpa, TrainingSite trainingSite, String reason) throws Exception{
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
		// update provider application
		tpa.setTrainingSite(TrainingSiteService.instance().createUpdateSite(trainingSite));
		trainingProviderApplicationService.create(tpa);
		// create new link
		LegacyProviderApplicationSiteAllocation newEntry = new LegacyProviderApplicationSiteAllocation();
		newEntry.setCompany(tpa.getCompany());
		newEntry.setCreateUser(sessionUser);
		newEntry.setReason(reason);
		newEntry.setTrainingProviderApplication(tpa);
		newEntry.setTrainingSite(tpa.getTrainingSite());
		if (tpa.getAccreditationNumber() != null && !tpa.getAccreditationNumber().trim().isEmpty()) {
			newEntry.setAccreditationNumberAtTime(tpa.getAccreditationNumber());
		}else if (tpa.getCertificateNumber() != null && !tpa.getCertificateNumber().trim().isEmpty()) {
			newEntry.setAccreditationNumberAtTime(tpa.getCertificateNumber());
		}
		create(newEntry);
		// creates / add supporting documents 
		if (supportingDocs != null) {
			for (Doc doc : supportingDocs) {
				if (doc.getId() == null) {
					doc.setTargetClass(newEntry.getClass().getName());
					doc.setTargetKey(newEntry.getId());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				}
			}
		}
		sendEmailNotificationNewEntry(newEntry);
	}

	private void sendEmailNotificationNewEntry(LegacyProviderApplicationSiteAllocation entity) throws Exception {
		String subject = "CHANGES TO PROVIDER APPLICATION: SITE ALLOCATION";
		String message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#,</p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised #ACTION_USER_FULL_NAME# (#ACTION_USER_EMAIL#) has implemented changes to the following provider on #ACTION_DATE#:</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + 
				"Provider name: #COMPANY_NAME#  <br/>" +
				"Accreditation number: #APP_ACCRED_NUMBER# <br/>" +
				"Site assigned: #SITE_NAME# <br/>" +
				"</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For further clarity, please contact #ACTION_USER_FULL_NAME#.</p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">System Generated Notification.</p>";
		List<Users> notificationRecivers = new ArrayList<>();
		notificationRecivers.addAll(locateUsersForNotification(entity));
		message = replaceStringsProviderApplicationSuspensionDeActivated(message, entity);
		notifyUsers(subject, message, notificationRecivers, false, null);
	}
	
	private List<Users> locateUsersForNotification(LegacyProviderApplicationSiteAllocation entity) throws Exception{
		List<Users> notificationRecivers = new ArrayList<>();
		List<Users> employeeList = new ArrayList<>();
		if (hostingCompanyEmployeesService == null) {
			hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		}
		/*
			1. Action user
			2. Senior Manager: Quality Assurance & Partnerships
			3. Senior Manager: Client Services
			4. Senior Manager: Administration
			5. Chief Operations Officer
		 */
		// 
		if (entity.getCreateUser() != null && entity.getCreateUser().getId() != null) {
			notificationRecivers.add(entity.getCreateUser());
		}
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID));
//		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_MANAGER_QUALITY_ASSURANCE_ID));
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
	
	public String replaceStringsProviderApplicationSuspensionDeActivated(String msg, LegacyProviderApplicationSiteAllocation entity) throws Exception {
		/*
		 * Tags:
		 * #ACTION_USER_FULL_NAME#, #ACTION_USER_EMAIL#, #ACTION_DATE#, #COMPANY_NAME#, #APP_ACCRED_NUMBER#, #REASON#, #SITE_NAME#
		 */
		String actionUserFullName = "";
		if (entity.getCreateUser() != null && entity.getCreateUser().getId() != null) {
			actionUserFullName = entity.getCreateUser().getFirstName().trim() + " " + entity.getCreateUser().getLastName().trim();
		}else {
			actionUserFullName = "#UNABLE TO LOCATE ACTION USER#";
		}
		msg = msg.replace("#ACTION_USER_FULL_NAME#", actionUserFullName);
		
		
		String actionUserEmail = "";
		if (entity.getCreateUser() != null && entity.getCreateUser().getId() != null) {
			actionUserEmail = entity.getCreateUser().getEmail();
		}else {
			actionUserEmail = "#UNABLE TO LOCATE ACTION USER EMAIL#";
		}
		msg = msg.replace("#ACTION_USER_EMAIL#", actionUserEmail);
		msg = msg.replace("#ACTION_DATE#", ( (entity != null && entity.getId() != null && entity.getCreateDate() != null) ?  HAJConstants.sdfDDMMYYYYHHmm.format(entity.getCreateDate()) : "#UNABLE TO LOCATE ACTION DATE#" ) );
		msg = msg.replace("#COMPANY_NAME#", ( (entity != null && entity.getId() != null && entity.getCompany() != null) ?  entity.getCompany().getCompanyName().trim() : "#UNABLE TO LOCATE COMPANY NAME#" ) );
		msg = msg.replace("#APP_ACCRED_NUMBER#", ( (entity != null && entity.getId() != null && entity.getAccreditationNumberAtTime() != null) ?  entity.getAccreditationNumberAtTime().trim() : "#UNABLE TO LOCATE APPLICATION ACC NUMBER#" ) );
		msg = msg.replace("#REASON#", ( (entity != null && entity.getId() != null && entity.getReason() != null) ?  entity.getReason().trim() : "#UNABLE TO LOCATE REASON#" ) );
		msg = msg.replace("#SITE_NAME#", ( (entity != null && entity.getTrainingSite() != null && entity.getTrainingSite().getSiteName() != null) ?  entity.getTrainingSite().getSiteName().trim() : "#UNABLE TO LOCATE SITE NAME#" ) );
		return msg;
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
