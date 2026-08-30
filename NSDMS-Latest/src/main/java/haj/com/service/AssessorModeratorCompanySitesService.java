package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.AssessorModeratorCompanySitesDAO;
import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.entity.SdpCompanyActions;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

public class AssessorModeratorCompanySitesService extends AbstractService {
	
	/** The dao. */
	private AssessorModeratorCompanySitesDAO dao = new AssessorModeratorCompanySitesDAO();
	
	/* Service levels */
	private SDPCompanyService sdpCompanyService = null;

	/**
	 * Get all AssessorModeratorCompanySites
	 * 
	 * @author TechFinium
	 * @see AssessorModeratorCompanySites
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompanySites}
	 * @throws Exception
	 *             the exception
	 */
	public List<AssessorModeratorCompanySites> allAssessorModeratorCompanySites() throws Exception {
		return dao.allAssessorModeratorCompanySites();
	}

	/**
	 * Create or update AssessorModeratorCompanySites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorCompanySites
	 */
	public void create(AssessorModeratorCompanySites entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update AssessorModeratorCompanySites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorCompanySites
	 */
	public void update(AssessorModeratorCompanySites entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete AssessorModeratorCompanySites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorCompanySites
	 */
	public void delete(AssessorModeratorCompanySites entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.AssessorModeratorCompanySites}
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorCompanySites
	 */
	public AssessorModeratorCompanySites findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find AssessorModeratorCompanySites by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompanySites}
	 * @throws Exception
	 *             the exception
	 * @see AssessorModeratorCompanySites
	 */
	public List<AssessorModeratorCompanySites> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load AssessorModeratorCompanySites
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompanySites}
	 * @throws Exception
	 *             the exception
	 */
	public List<AssessorModeratorCompanySites> allAssessorModeratorCompanySites(int first, int pageSize)
			throws Exception {
		return dao.allAssessorModeratorCompanySites(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of AssessorModeratorCompanySites for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the AssessorModeratorCompanySites
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(AssessorModeratorCompanySites.class);
	}

	/**
	 * Lazy load AssessorModeratorCompanySites with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            AssessorModeratorCompanySites class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.AssessorModeratorCompanySites}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> allAssessorModeratorCompanySites(Class<AssessorModeratorCompanySites> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<AssessorModeratorCompanySites>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of AssessorModeratorCompanySites for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            AssessorModeratorCompanySites class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the AssessorModeratorCompanySites entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<AssessorModeratorCompanySites> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> allAssessorModeratorLinkedToCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from AssessorModeratorCompanySites o where o.company.id = :companyId";
		filters.put("companyId", companyId);
		return (List<AssessorModeratorCompanySites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllAssessorModeratorLinkedToCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AssessorModeratorCompanySites o where o.company.id = :companyId";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> allAssessorModeratorLinkedToHoldingCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from AssessorModeratorCompanySites o where o.company.id = :companyId and o.trainingSite is null ";
		filters.put("companyId", companyId);
		return (List<AssessorModeratorCompanySites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllAssessorModeratorLinkedToHoldingCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AssessorModeratorCompanySites o where o.company.id = :companyId and o.trainingSite is null ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<AssessorModeratorCompanySites> allAssessorModeratorLinkedToTrainingSite(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long trainingSiteId) throws Exception {
		String hql = "select o from AssessorModeratorCompanySites o where o.trainingSite.id = :trainingSiteId ";
		filters.put("trainingSiteId", trainingSiteId);
		return (List<AssessorModeratorCompanySites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllAssessorModeratorLinkedToTrainingSite(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from AssessorModeratorCompanySites o where o.trainingSite.id = :trainingSiteId ";
		return dao.countWhere(filters, hql);
	}

	public List<AssessorModeratorCompanySites> findByCompany(Long companyId) throws Exception {
		return dao.findByCompany(companyId);
	}

	public List<AssessorModeratorCompanySites> findByCompanyUserType(Long companyId, Long userId,
			AssessorModType assessorModType) throws Exception {
		return dao.findByCompanyUserType(companyId, userId, assessorModType);
	}

	public List<AssessorModeratorCompanySites> findByHoldingCompany(Long companyId) throws Exception {
		return dao.findByHoldingCompany(companyId);
	}

	public List<AssessorModeratorCompanySites> findByUserHoldingCompany(Long userId, Long companyId) throws Exception {
		return dao.findByUserHoldingCompany(userId, companyId);
	}

	public List<AssessorModeratorCompanySites> findByUserHoldingCompanyAssModType(Long userId, Long companyId, AssessorModType assessorModType) throws Exception {
		return dao.findByUserHoldingCompanyAssModType(userId, companyId, assessorModType);
	}

	public List<AssessorModeratorCompanySites> findByUserHoldingCompanyAssModTypeList(Long userId, Long companyId, List<AssessorModType> assessorModTypeList) throws Exception {
		return dao.findByUserHoldingCompanyAssModTypeList(userId, companyId, assessorModTypeList);
	}

	public Integer countByUserHoldingCompanyAssModType(Long userId, Long companyId, AssessorModType assessorModType) throws Exception {
		return dao.countByUserHoldingCompanyAssModType(userId, companyId, assessorModType);
	}

	public Integer countByUserHoldingCompanyAssModTypeList(Long userId, Long companyId, List<AssessorModType> assessorModTypeList) throws Exception {
		return dao.countByUserHoldingCompanyAssModTypeList(userId, companyId, assessorModTypeList);
	}

	public List<AssessorModeratorCompanySites> findByTrainingSite(Long trainingSiteId) throws Exception {
		return dao.findByTrainingSite(trainingSiteId);
	}

	public List<AssessorModeratorCompanySites> findByUserTrainingSite(Long userId, Long trainingSiteId) throws Exception {
		return dao.findByUserTrainingSite(userId, trainingSiteId);
	}

	public List<AssessorModeratorCompanySites> findByUserTrainingSiteAssModType(Long userId, Long trainingSiteId, AssessorModType assessorModType) throws Exception {
		return dao.findByUserTrainingSiteAssModType(userId, trainingSiteId, assessorModType);
	}

	public Integer countByUserTrainingSiteAssModType(Long userId, Long trainingSiteId, AssessorModType assessorModType)
			throws Exception {
		return dao.countByUserTrainingSiteAssModType(userId, trainingSiteId, assessorModType);
	}

	public List<AssessorModeratorCompanySites> findByUserTrainingSiteAssModTypeList(Long userId, Long trainingSiteId, List<AssessorModType> assessorModTypeList) throws Exception {
		return dao.findByUserTrainingSiteAssModTypeList(userId, trainingSiteId, assessorModTypeList);
	}

	public Integer countByUserTrainingSiteAssModTypeList(Long userId, Long trainingSiteId, List<AssessorModType> assessorModTypeList) throws Exception {
		return dao.countByUserTrainingSiteAssModTypeList(userId, trainingSiteId, assessorModTypeList);
	}
	
	public void createNewAssessorModeratorLink(TrainingProviderApplication selectedTrainingProviderApplication, Users sessionUser, Users assModUser, AssessorModType assessorModType) throws Exception {
		AssessorModeratorCompanySites newEntry = new AssessorModeratorCompanySites(selectedTrainingProviderApplication.getCompany(), assModUser);
		if (selectedTrainingProviderApplication.getTrainingSite() != null && selectedTrainingProviderApplication.getTrainingSite().getId() != null) {
			newEntry.setTrainingSite(selectedTrainingProviderApplication.getTrainingSite());
		}
		if (assessorModType != null) {
			newEntry.setAssessorModType(assessorModType);
		}
		newEntry.setCreateUser(sessionUser);
		newEntry.setLastActionUser(sessionUser);
		newEntry.setApprovalStatus(ApprovalEnum.Approved);
		create(newEntry);
		newEntry = findByKey(newEntry.getId());
		List<Users> notificationRecivers = new ArrayList<>();
		if (sdpCompanyService == null) {
			sdpCompanyService = new SDPCompanyService();
		}
		notificationRecivers.add(assModUser);
		notificationRecivers.add(sessionUser);
		if (newEntry.getTrainingSite() != null && newEntry.getTrainingSite().getId() != null) {
			notificationRecivers.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(newEntry.getCompany().getId(), newEntry.getTrainingSite().getId()));
		} else {
			notificationRecivers.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(newEntry.getCompany().getId()));
		}
		String subject = "Notification of new Assessor/Moderator";
		StringBuilder content = new StringBuilder();
		content.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#, </p>");
		content.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that #ASS_MOD_USER_FULL_NAME# (#ASS_MOD_USER_EMAIL#) has been linked as #ASS_MOD_TYPE# for #COMPANY_NAME# (#COMPANY_LEVY_NUMBER#), training site: #SITE_NAME#.</p>");
		content.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For clarity/support, please contact your Regional Office. </p>");
		String message = replaceStringsAssessorModeratorCompanyLink(content.toString(), newEntry, sessionUser);
		List<Users> finalUserList = new ArrayList<>();
		for (Users user : notificationRecivers) {
			if (determainIfUserCanBeAdded(finalUserList, user)) {
				finalUserList.add(user);
			}
		}	
		notifyUsers(subject, message, finalUserList, false, null);
	}


	public void removeLinkSendNotification(AssessorModeratorCompanySites entry, Users sessionUser) throws Exception{
		// locate All SDPs assigned to company
		List<Users> notificationRecivers = new ArrayList<>();
		if (sdpCompanyService == null) {
			sdpCompanyService = new SDPCompanyService();
		}
		notificationRecivers.add(entry.getAssessorModerator());
		notificationRecivers.add(sessionUser);
		if (entry.getTrainingSite() != null && entry.getTrainingSite().getId() != null) {
			notificationRecivers.addAll(sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(entry.getCompany().getId(), entry.getTrainingSite().getId()));
		} else {
			notificationRecivers.addAll(sdpCompanyService.findAllSdpByCompanyIdReturnUsers(entry.getCompany().getId()));
		}
		if (notificationRecivers.isEmpty()) {
			throw new Exception("Unable to locate notification recivers. Please ensure SDP contact persons are assigned or contact merSETA support.");
		} else {
			String subject = "Notification of Assessor/Moderator removal";
			StringBuilder content = new StringBuilder();
			content.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#, </p>");
			content.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that #ASS_MOD_USER_FULL_NAME# (#ASS_MOD_USER_EMAIL#) has been removed as #ASS_MOD_TYPE# for #COMPANY_NAME# (#COMPANY_LEVY_NUMBER#), training site: #SITE_NAME#.</p>");
			content.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For clarity/support, please contact your Regional Office. </p>");
			String message = replaceStringsAssessorModeratorCompanyLink(content.toString(), entry, sessionUser);
			delete(entry);
			List<Users> finalUserList = new ArrayList<>();
			for (Users employee : notificationRecivers) {
				if (determainIfUserCanBeAdded(finalUserList, employee)) {
					finalUserList.add(employee);
				}
			}	
			notifyUsers(subject, message, finalUserList, false, null);
		}
	}
	
	public String replaceStringsAssessorModeratorCompanyLink(String msg, AssessorModeratorCompanySites entity, Users user) throws Exception {
		String actionUser = "";
		if (user != null && user.getId() != null) {
			actionUser = user.getFirstName().trim() + " " + user.getLastName().trim();
		} else {
			actionUser = "#UNABLE TO LOCATE ACTION USER#";
		}
		msg = msg.replace("#ACTION_USER_FULL_NAME#", actionUser);
		msg = msg.replace("#ACTION_USER_EMAIL#", ((user != null && user.getId() != null &&  user.getEmail() != null) ?  user.getEmail().trim() : "N/A"));
		msg = msg.replace("#ACTION_DATE#", HAJConstants.sdfDDMMYYYYHHmm.format(getSynchronizedDate()));

		String assessorModUserFullName = "";
		if (entity.getAssessorModerator() != null && entity.getAssessorModerator().getId() != null) {
			assessorModUserFullName = entity.getAssessorModerator().getFirstName().trim() + " " + entity.getAssessorModerator().getLastName().trim();
		} else {
			assessorModUserFullName = "#UNABLE TO LOCATE ASSESSOR / MODERATOR INFORMATION#";
		}
		msg = msg.replace("#ASS_MOD_USER_FULL_NAME#", assessorModUserFullName);
		msg = msg.replace("#ASS_MOD_USER_EMAIL#", ((entity.getAssessorModerator() != null && entity.getAssessorModerator().getId() != null &&  entity.getAssessorModerator().getEmail() != null) ?  entity.getAssessorModerator().getEmail().trim() : "N/A"));
		msg = msg.replace("#ASS_MOD_TYPE#", ((entity.getAssessorModType() != null) ? entity.getAssessorModType().getFriendlyName().trim() : "N/A"));
		
		msg = msg.replace("#COMPANY_NAME#", ((entity.getCompany() != null && entity.getCompany().getCompanyName() != null) ? entity.getCompany().getCompanyName().trim() : "N/A"));
		msg = msg.replace("#COMPANY_LEVY_NUMBER#", ((entity.getCompany() != null && entity.getCompany().getLevyNumber() != null) ? entity.getCompany().getLevyNumber().trim() : "N/A"));
		msg = msg.replace("#SITE_NAME#", ((entity.getTrainingSite() != null && entity.getTrainingSite().getId() != null && entity.getTrainingSite().getSiteName() != null) ? entity.getTrainingSite().getSiteName().trim() : "N/A"));
		
		return msg;
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
	
	public void notifyUsers(String subject, String message, List<Users> notificationRecivers, boolean withAttachment, List<AttachmentBean> attachmentBeans) {
		for (Users users : notificationRecivers) {
			String fullName = "";
			if (users.getTitle() != null && users.getTitle().getDescription() != null) {
				fullName = users.getTitle().getDescription() + " ";
			}
			fullName += users.getFirstName().trim() + " " + users.getLastName().trim();
			if (withAttachment) {
				GenericUtility.sendMailWithAttachementTempWithLog(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), attachmentBeans, null);
			} else {
				GenericUtility.sendMail(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), null);
			}
		}
	}

	
}