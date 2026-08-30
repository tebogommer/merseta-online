package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.MailingListDAO;
import haj.com.entity.Company;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.MailingListTypeEnum;
import haj.com.entity.lookup.MailingList;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

public class MailingListService extends AbstractService {
	/** The dao. */
	private MailingListDAO dao = new MailingListDAO();
	
	private static MailingListService mailingListService;
	public static synchronized MailingListService instance() {
		if (mailingListService == null) {
			mailingListService = new MailingListService();
		}
		return mailingListService;
	}

	/**
	 * Get all MailingList
 	 * @author TechFinium 
 	 * @see   MailingList
 	 * @return a list of {@link haj.com.entity.MailingList}
	 * @throws Exception the exception
 	 */
	public List<MailingList> allMailingList() throws Exception {
	  	return dao.allMailingList();
	}


	/**
	 * Create or update MailingList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     MailingList
	 */
	public void create(MailingList entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  MailingList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MailingList
	 */
	public void update(MailingList entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  MailingList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MailingList
	 */
	public void delete(MailingList entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.MailingList}
	 * @throws Exception the exception
	 * @see    MailingList
	 */
	public MailingList findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find MailingList by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.MailingList}
	 * @throws Exception the exception
	 * @see    MailingList
	 */
	public List<MailingList> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load MailingList
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.MailingList}
	 * @throws Exception the exception
	 */
	public List<MailingList> allMailingList(int first, int pageSize) throws Exception {
		return dao.allMailingList(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of MailingList for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the MailingList
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(MailingList.class);
	}
	
    /**
     * Lazy load MailingList with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 MailingList class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.MailingList} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<MailingList> allMailingList(Class<MailingList> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<MailingList>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of MailingList for lazy load with filters
     * @author TechFinium 
     * @param entity MailingList class
     * @param filters the filters
     * @return Number of rows in the MailingList entity
     * @throws Exception the exception     
     */	
	public int count(Class<MailingList> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<MailingList> findByType(MailingListTypeEnum mailingListTypeEnum) throws Exception {
		return dao.findByType(mailingListTypeEnum);
	}
	
	public List<MailingList> findByMailingListType(MailingListTypeEnum mailingListTypeEnum) throws Exception{
		if (mailingListTypeEnum == null) {
			return findByType(MailingListTypeEnum.Default);
		} else {
			List<MailingList> list =  findByType(mailingListTypeEnum);
			if (list.size() == 0) {
				list = findByType(MailingListTypeEnum.Default);
			}
			return list;
		}
	}
	
	public void testNotification() throws Exception{
		List<MailingList> list =  findByMailingListType(MailingListTypeEnum.Default);
		for (MailingList listEntry: list) {
			GenericUtility.sendMail(listEntry.getEmailAddress(), "Test", "Test", null);
		}
	}
	
	public void sendNotification(Users sessionUser, MailingListTypeEnum mailingListTypeEnum, ExtensionRequest extensionRequest, Wsp wsp, Company company){
		try {
			
			String subject = "";
			String message = "";
			boolean sendNotification = false;
			
			switch (mailingListTypeEnum) {
			
			case ExtensionRequestCreate:
				
				/* Fields Required: sessionUser, extensionRequest, wsp, company  */				
				sendNotification = true;

				subject = "EXTENSION REQUEST CREATION NOTIFICATION: #ENTITY_ID#";
				message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please be advised: an extension request has been created by: #SESSION_USER_FIRST_NAME# #SESSION_USER_LAST_NAME# (#SESSION_USER_EMAIL_ADDRESS#) on #EXTENSION_REQUEST_CREATE_DATE#. </p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Extension request forcompany: #COMPANY_NAME# (#ENTITY_ID#), Grant year: #GRANT_YEAR#. Application Type: #WSP_TYPE#</p>"
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Automated notification - Do not respond </p>";
				
				// string replacements
				subject = subject.replaceAll("#ENTITY_ID#",  (company != null) ? company.getLevyNumber().trim() : "Unable to locate User First Name");
				message = message.replaceAll("#SESSION_USER_FIRST_NAME#", (sessionUser != null) ? sessionUser.getFirstName().trim() : "Unable to locate User First Name");
				message = message.replaceAll("#SESSION_USER_LAST_NAME#", (sessionUser != null) ? sessionUser.getLastName().trim() : "Unable to locate User Last Name");
				message = message.replaceAll("#SESSION_USER_EMAIL_ADDRESS#", (sessionUser != null) ? sessionUser.getEmail().trim() : "Unable to locate User Email Address");
				message = message.replaceAll("#EXTENSION_REQUEST_CREATE_DATE#", (extensionRequest != null && extensionRequest.getCreateDate() != null) ? HAJConstants.sdfDDMMYYYYHHmm.format(extensionRequest.getCreateDate()) : "Unable to locate Extension Create Date");
				message = message.replaceAll("#COMPANY_NAME#",  (company != null) ? company.getCompanyName().trim() : "Unable to locate User First Name");
				message = message.replaceAll("#ENTITY_ID#",  (company != null) ? company.getLevyNumber().trim() : "Unable to locate User First Name");
				message = message.replaceAll("#GRANT_YEAR#",  (wsp != null) ? wsp.getFinYear().toString() : "Extension On Company Level");
				message = message.replaceAll("#WSP_TYPE#",  (wsp != null) ? wsp.getWspType().getFriendlyName().trim() : "N/A");
				
				break;
			default:
				sendNotification = false;
				break;
			}
			
			if (sendNotification) {
				
				List<MailingList> list = findByMailingListType(mailingListTypeEnum);
				for (MailingList mailingList : list) {
					GenericUtility.sendMail(mailingList.getEmailAddress(), subject, message, null);
				}
				
			}
		} catch (Exception e) {
			logger.fatal(e);
			GenericUtility.mailError("ERROR ON Mail List Notification", "Error On Notification: haj.com.service.lookup.MailingListService.sendNotification(Users, MailingListTypeEnum, ExtensionRequest, Wsp, Company). On Type: " + ((mailingListTypeEnum != null) ? mailingListTypeEnum.getFriendlyName() : "Unable to locate Type"));
		}
	}
}
