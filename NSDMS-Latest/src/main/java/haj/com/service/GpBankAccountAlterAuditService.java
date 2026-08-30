package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.GpBankAccountAlterAuditDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyUsers;
import haj.com.entity.GpBankAccountAlterAudit;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RegionTown;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.RegionTownService;
import haj.com.utils.GenericUtility;

public class GpBankAccountAlterAuditService extends AbstractService {
	
	/** The dao. */
	private GpBankAccountAlterAuditDAO dao = new GpBankAccountAlterAuditDAO();	
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	
	/**
	 * Get all GpBankAccountAlterAudit
 	 * @author TechFinium 
 	 * @see   GpBankAccountAlterAudit
 	 * @return a list of {@link haj.com.entity.GpBankAccountAlterAudit}
	 * @throws Exception the exception
 	 */
	public List<GpBankAccountAlterAudit> allGpBankAccountAlterAudit() throws Exception {
	  	return dao.allGpBankAccountAlterAudit();
	}


	/**
	 * Create or update GpBankAccountAlterAudit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     GpBankAccountAlterAudit
	 */
	public void create(GpBankAccountAlterAudit entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  GpBankAccountAlterAudit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    GpBankAccountAlterAudit
	 */
	public void update(GpBankAccountAlterAudit entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  GpBankAccountAlterAudit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    GpBankAccountAlterAudit
	 */
	public void delete(GpBankAccountAlterAudit entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.GpBankAccountAlterAudit}
	 * @throws Exception the exception
	 * @see    GpBankAccountAlterAudit
	 */
	public GpBankAccountAlterAudit findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find GpBankAccountAlterAudit by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.GpBankAccountAlterAudit}
	 * @throws Exception the exception
	 * @see    GpBankAccountAlterAudit
	 */
	public List<GpBankAccountAlterAudit> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load GpBankAccountAlterAudit
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.GpBankAccountAlterAudit}
	 * @throws Exception the exception
	 */
	public List<GpBankAccountAlterAudit> allGpBankAccountAlterAudit(int first, int pageSize) throws Exception {
		return dao.allGpBankAccountAlterAudit(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of GpBankAccountAlterAudit for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the GpBankAccountAlterAudit
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(GpBankAccountAlterAudit.class);
	}
	
    /**
     * Lazy load GpBankAccountAlterAudit with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 GpBankAccountAlterAudit class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.GpBankAccountAlterAudit} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<GpBankAccountAlterAudit> allGpBankAccountAlterAudit(Class<GpBankAccountAlterAudit> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<GpBankAccountAlterAudit>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of GpBankAccountAlterAudit for lazy load with filters
     * @author TechFinium 
     * @param entity GpBankAccountAlterAudit class
     * @param filters the filters
     * @return Number of rows in the GpBankAccountAlterAudit entity
     * @throws Exception the exception     
     */	
	public int count(Class<GpBankAccountAlterAudit> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	// send notification on change
	public void sendNotificationForOnHold(GpBankAccountAlterAudit audit, Company company, Users sessionUser) throws Exception{
		Boolean withAttachment = false;
		String subject = "NSDMS Notification: ACCOUNT PLACED ON HOLD";
		String message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #RECEIVER_FULL_NAME#, </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> ACCOUNT PLACED ON HOLD </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please note that your account has been placed on hold for: #COMPANY_NAME# (#LEVY_NUMBER#). This is due to the fact that your bank details are no longer valid. This could be due to various reasons, the most common being that the bank account has been closed. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please will you update you bank details on the merSETA website at your earliest convenience, as well as forwarding the original documents to our offices. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> You can email grantsinfo@merseta.org.za if you have any queries. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kind Regards <br/> merSETA Finance Department </p>";
		
		List<Users> notificationRecivers = new ArrayList<>();
		List<AttachmentBean> attachmentBeans = new ArrayList<>();
		
		// Primary and Labor SDF
		List<Users> sdfUsers = sdfCompanyService.findPrimaryAndLabourSDFUsers(company);
		if (!sdfUsers.isEmpty()) {
			notificationRecivers.addAll(sdfUsers);
		}
		sdfUsers = null;
		// HR manager
		List<CompanyUsers> cu = companyUsersService.findByCompanyResponsibilityAndUserPosition(company, ConfigDocProcessEnum.DG_VERIFICATION, (long) 3);
		for (CompanyUsers companyUsers : cu) {
			notificationRecivers.add(companyUsers.getUser());
			break;
		}
		
		// fail safe
		if (notificationRecivers.isEmpty()) {
			addUsersToNotificationList(notificationRecivers, hostingCompanyEmployeesService.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_COORDINATOR_QUALITY_ADMINISTRATION_ID));
		}
		
		addUsersToNotificationList(notificationRecivers, hostingCompanyEmployeesService.findByAllActiveUsersByJobTitleId(HAJConstants.JOB_TITLE_MANAGER_QUALITY_ADMINISTRATION_ID));
		
		message = replaceMessageContents(message, audit, company, sessionUser);
		notifyUsers(subject, message, notificationRecivers, withAttachment, attachmentBeans);
		
	}
	
	private String replaceMessageContents(String message, GpBankAccountAlterAudit audit, Company company, Users sessionUser) {
		message = message.replace("#COMPANY_NAME#", ( (company != null && company.getId() != null && company.getCompanyName() != null) ? company.getCompanyName().trim() : "#UNABLE_TO_LOCATE_COMPANY_NAME#"));
		message = message.replace("#LEVY_NUMBER#", ( (company != null && company.getId() != null && company.getLevyNumber() != null) ? company.getLevyNumber().trim() : "#UNABLE_TO_LOCATE_COMPANY_LEVY_NUMBER#"));
		message = message.replace("#CHANGE_REASON#", ( (audit != null && audit.getId() != null) ? audit.getReason().trim() : "#UNABLE_TO_LOCATE_REASON_FOR_CHANGE#"));
		message = message.replace("#CHANGE_USER_FIRST_NAME#", ( (sessionUser != null && sessionUser.getId() != null) ? sessionUser.getFirstName().trim() : "#UNABLE_TO_LOCATE_CHANGE_USER_FIRST_NAME#"));
		message = message.replace("#CHANGE_USER_LAST_NAME#", ( (sessionUser != null && sessionUser.getId() != null) ? sessionUser.getLastName().trim() : "#UNABLE_TO_LOCATE_CHANGE_USER_LAST_NAME#"));
		message = message.replace("#CHANGE_USER_EMAIL_NAME#", ( (sessionUser != null && sessionUser.getId() != null) ? sessionUser.getEmail().trim() : "#UNABLE_TO_LOCATE_CHANGE_USER_EMAIL_NAME#" ));
		return message;
	}
	
	private void addUsersToNotificationList(List<Users> emailReceivers, List<Users> usersToAdd) throws Exception{
		if (!usersToAdd.isEmpty()) {
			for (Users newUser : usersToAdd) {
				boolean addToList = true;
				for (Users usersInList : emailReceivers) {
					if (usersInList.getId().equals(newUser.getId())) {
						addToList = false;
					}
				}
				if (Boolean.TRUE.equals(addToList)) {
					emailReceivers.add(newUser);
				}
			}
		}
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