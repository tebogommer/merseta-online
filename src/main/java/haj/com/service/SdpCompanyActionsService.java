package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.SdpCompanyActionsDAO;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.SDPCompany;
import haj.com.entity.SdpCompanyActions;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class SdpCompanyActionsService extends AbstractService {

	/** The dao. */
	private SdpCompanyActionsDAO dao = new SdpCompanyActionsDAO();

	/* Service Levels */
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();
	private ConfigDocService configDocService = new ConfigDocService();
	private RegisterService registerService = new RegisterService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	
	/**
	 * Get all SdpCompanyActions
	 * 
	 * @author TechFinium
	 * @see SdpCompanyActions
	 * @return a list of {@link haj.com.entity.SdpCompanyActions}
	 * @throws Exception
	 *             the exception
	 */
	public List<SdpCompanyActions> allSdpCompanyActions() throws Exception {
		return dao.allSdpCompanyActions();
	}

	/**
	 * Create or update SdpCompanyActions.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SdpCompanyActions
	 */
	public void create(SdpCompanyActions entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update SdpCompanyActions.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SdpCompanyActions
	 */
	public void update(SdpCompanyActions entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SdpCompanyActions.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SdpCompanyActions
	 */
	public void delete(SdpCompanyActions entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SdpCompanyActions}
	 * @throws Exception
	 *             the exception
	 * @see SdpCompanyActions
	 */
	public SdpCompanyActions findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SdpCompanyActions by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SdpCompanyActions}
	 * @throws Exception
	 *             the exception
	 * @see SdpCompanyActions
	 */
	public List<SdpCompanyActions> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SdpCompanyActions
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SdpCompanyActions}
	 * @throws Exception
	 *             the exception
	 */
	public List<SdpCompanyActions> allSdpCompanyActions(int first, int pageSize) throws Exception {
		return dao.allSdpCompanyActions(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SdpCompanyActions for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SdpCompanyActions
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SdpCompanyActions.class);
	}

	/**
	 * Lazy load SdpCompanyActions with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SdpCompanyActions class
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
	 * @return a list of {@link haj.com.entity.SdpCompanyActions} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SdpCompanyActions> allSdpCompanyActions(Class<SdpCompanyActions> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SdpCompanyActions>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of SdpCompanyActions for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SdpCompanyActions class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SdpCompanyActions entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SdpCompanyActions> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public Integer countByNewDesignationIdCompanyTrainingSiteAndOpenStatus(Long newDesId, Long companyId, Long siteId, List<ApprovalEnum> approvalList) throws Exception {
		return dao.countByNewDesignationIdCompanyTrainingSiteAndOpenStatus(newDesId, companyId, siteId, approvalList);
	}
	
	public Integer countByUserIdCompanyTrainingSiteAndOpenStatus(Long userId, Long companyId, Long siteId, List<ApprovalEnum> approvalList) throws Exception {
		return dao.countByUserIdCompanyTrainingSiteAndOpenStatus(userId, companyId, siteId, approvalList);
	}

	@SuppressWarnings("unchecked")
	public List<SdpCompanyActions> allSdpCompanyActionsByCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from SdpCompanyActions o where o.company.id = :companyId";
		filters.put("companyId", companyId);
		return (List<SdpCompanyActions>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllSdpCompanyActionsByCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SdpCompanyActions o where o.company.id = :companyId";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<SdpCompanyActions> allSdpCompanyActionsByCompanyNoSiteAssigned(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from SdpCompanyActions o where o.company.id = :companyId and o.trainingSite is null";
		filters.put("companyId", companyId);
		return (List<SdpCompanyActions>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllSdpCompanyActionsByCompanyNoSiteAssigned(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SdpCompanyActions o where o.company.id = :companyId and o.trainingSite is null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<SdpCompanyActions> allSdpCompanyActionsBySiteAssigned(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long trainingSiteId) throws Exception {
		String hql = "select o from SdpCompanyActions o where o.trainingSite.id = :trainingSiteId";
		filters.put("trainingSiteId", trainingSiteId);
		return (List<SdpCompanyActions>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllSdpCompanyActionsBySiteAssigned(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SdpCompanyActions o where o.trainingSite.id = :trainingSiteId";
		return dao.countWhere(filters, hql);
	}
	
	public List<SdpCompanyActions> populateAdditionalInformationList(List<SdpCompanyActions> list) throws Exception {
		for (SdpCompanyActions entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	public SdpCompanyActions populateAdditionalInformation(SdpCompanyActions entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDoc(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
		return entity;
	}
	
	public void populateDocsWithWorkflowDocuments(CompanyLearnersTradeTest entity, ConfigDocProcessEnum config, CompanyUserTypeEnum userType) throws Exception{
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), config, userType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				entity.getDocs().add(new Doc(a));
			});
		}
	}
	
	public void initiateWorkflow(SdpCompanyActions entity, Users sdpUser, SDPCompany sdpCompany, TrainingProviderApplication trainingProviderApplication, Users sessionUser, List<Doc> docList) throws Exception {
		if (sdpUser.getId() == null && sessionUser.getId() == null) {
			registerService.register(sdpUser, true);
			sessionUser = sdpUser;
		}
		entity.setApprovalStatus(ApprovalEnum.PendingApproval);
		if (sessionUser.getId() == null) {
			registerService.register(sessionUser, true);
		}
		entity.setCreateUser(sessionUser);
		if (sdpCompany != null && sdpCompany.getId() != null) {
			entity.setSdpCompanyFlatId(sdpCompany.getId());
			entity.setSdpUser(sdpCompany.getSdp());
		} else {
			// check if user is registered
			if (sdpUser.getId() == null) {
				registerService.register(sdpUser, true);
			}
			entity.setSdpUser(sdpUser);
		}
		if (trainingProviderApplication != null && trainingProviderApplication.getId() != null) {
			entity.setTrainingProviderApplication(trainingProviderApplication);
		}
		create(entity);
		if (docList != null && !docList.isEmpty()) {
			for (Doc doc : docList) {
				if (doc.getId() == null) {
					doc.setTargetClass(entity.getClass().getName());
					doc.setTargetKey(entity.getId());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				}
			}
		}
		if (sdpCompany != null && sdpCompany.getId() != null) {
			sdpCompany.setApprovalStatus(ApprovalEnum.PendingApproval);
			sdpCompanyService.update(sdpCompany);
		}
		switch (entity.getSdpCompanyAction()) {
		case NewSDP:
			TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.NEW_SDP_LINK, null, null);
			break;
		case RemoveSDP:
			TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.REMOVE_SDP_LINK, null, null);			
			break;
		case UpdateDesignation:
			TasksService.instance().findFirstInProcessAndCreateTask("", sessionUser, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.UPDATE_SDP_DESIGNATION, null, null);			
			break;
		default:
			break;
		}
	}
	
	public void finalApproveAction(SdpCompanyActions entity, Tasks task, Users sessionUser) throws Exception {
		
		List<IDataEntity> updateList = new ArrayList<>();
		List<IDataEntity> createList = new ArrayList<>();
		List<IDataEntity> deleteList = new ArrayList<>();
		
		Integer emailNotification = 0;
		entity.setApprovalUser(sessionUser);
		entity.setApprovalDate(getSynchronizedDate());
		entity.setApprovalStatus(ApprovalEnum.Approved);
		updateList.add(entity);
		switch (entity.getSdpCompanyAction()) {
		case NewSDP:
			emailNotification = 1;
			SDPCompany sdpCompany = new SDPCompany(entity.getCompany(), entity.getSdpUser());
			if (entity.getTrainingSite() != null && entity.getTrainingSite().getId() != null) {
				sdpCompany.setTrainingSite(entity.getTrainingSite());
			}
			sdpCompany.setApprovalStatus(ApprovalEnum.Approved);
			sdpCompany.setSdpType(entity.getNewDesignation());
			createList.add(sdpCompany);
			// fail safe to create old link
			List<CompanyUsers>  cuList = null;
			cuList = companyUsersService.findByUserAndCompanyAndTypeAndDesignationIsNotNull(entity.getCompany().getId(), entity.getSdpUser().getId(), ConfigDocProcessEnum.TP);
			if (cuList.isEmpty()) {
				CompanyUsers newCompUser = new CompanyUsers(entity.getSdpUser(), entity.getCompany());
				newCompUser.setCompanyUserType(ConfigDocProcessEnum.TP);
				if (entity.getNewDesignation().getDesignation() != null) {
					newCompUser.setDesignation(entity.getNewDesignation().getDesignation());
				}
				newCompUser.setExistingUser(true);
				createList.add(newCompUser);
			}
			sdpCompany = null;
			break;
		case RemoveSDP:
			emailNotification = 3;
			if (entity.getSdpCompanyFlatId() != null) {
				SDPCompany sdpCompanyRemoval = sdpCompanyService.findByKey(entity.getSdpCompanyFlatId());
				if (sdpCompanyRemoval != null && sdpCompanyRemoval.getId() != null) {				
					deleteList.add(sdpCompanyRemoval);
					Integer countAssignedByCompanyLink = sdpCompanyService.countBySdpIdCompanyIdAndNotId(sdpCompanyRemoval.getSdp().getId(), sdpCompanyRemoval.getCompany().getId(), sdpCompanyRemoval.getId());
					if (countAssignedByCompanyLink == 0) {
						List<CompanyUsers> compUserLinkList = companyUsersService.findByUserAndCompanyAndTypeAndDesignationIsNotNull(entity.getCompany().getId(), sdpCompanyRemoval.getSdp().getId(), ConfigDocProcessEnum.TP);
						if (!compUserLinkList.isEmpty()) {
							deleteList.addAll(compUserLinkList);
						}
						compUserLinkList = null;
					}
				}
				sdpCompanyRemoval = null;
			}
			break;
		case UpdateDesignation:
			emailNotification = 5;
			if (entity.getSdpCompanyFlatId() != null) {
				SDPCompany sdpCompanyUpdate = sdpCompanyService.findByKey(entity.getSdpCompanyFlatId());
				if (sdpCompanyUpdate != null && sdpCompanyUpdate.getId() != null && entity.getNewDesignation() != null) {
					sdpCompanyUpdate.setSdpType(entity.getNewDesignation());
					sdpCompanyUpdate.setApprovalStatus(ApprovalEnum.Approved);
					updateList.add(sdpCompanyUpdate);
				}
				sdpCompanyUpdate = null;
			}
			break;
		default:
			break;
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
		if (!deleteList.isEmpty()) {
			dao.deleteBatch(deleteList);
		}
		updateList.clear();
		createList.clear();
		deleteList.clear();
		TasksService.instance().completeTask(task);
		RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), entity.getClass().getName(), task.getWorkflowProcess());
		sendEmailNotificationNewEntry(entity, emailNotification, new ArrayList<>());
	}
	
	public void finalRejectAction(SdpCompanyActions entity, Tasks task, Users sessionUser, List<RejectReasons> rejectReasonsList) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		Integer emailNotification = 0;
		entity.setApprovalUser(sessionUser);
		entity.setApprovalDate(getSynchronizedDate());
		entity.setApprovalStatus(ApprovalEnum.Rejected);
		updateList.add(entity);
		if (entity.getSdpCompanyFlatId() != null) {
			SDPCompany sdpCompany = sdpCompanyService.findByKey(entity.getSdpCompanyFlatId());
			if (sdpCompany != null && sdpCompany.getId() != null) {
				// if linked to an SDP company link revert status back to approved. 
				sdpCompany.setApprovalStatus(ApprovalEnum.Approved);
				updateList.add(sdpCompany);
			}
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
		updateList.clear();
		TasksService.instance().completeTask(task);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbWithTaskId(entity.getId(), entity.getClass().getName(), rejectReasonsList, sessionUser, task.getWorkflowProcess(), task.getId());
		switch (entity.getSdpCompanyAction()) {
		case NewSDP:
			emailNotification = 2;
			sendEmailNotificationNewEntry(entity, emailNotification, rejectReasonsList);
			break;
		case RemoveSDP:
			emailNotification = 4;
			sendEmailNotificationNewEntry(entity, emailNotification, rejectReasonsList);
			break;
		case UpdateDesignation:
			emailNotification = 6;
			sendEmailNotificationNewEntry(entity, emailNotification, rejectReasonsList);
			break;
		default:
			break;
		}
	}

	/**
	 * 1 - Add new SDP Approval
	 * 2 - Add new SDP Rejection
	 * 3 - Remove SDP Approval
	 * 4 - Remove SDP Rejection
	 * 5 - Re-assign SDP Approval
	 * 6 - Re-assign SDP Rejection
	 */
	private void sendEmailNotificationNewEntry(SdpCompanyActions entity, Integer emailIndicator, List<RejectReasons> rejectionReasonsList) throws Exception {
		boolean sendEmail = false;
		StringBuilder subject = new StringBuilder();
		StringBuilder message = new StringBuilder();
		switch (emailIndicator) {
		case 1:
			sendEmail = true;
			subject.append("Approval of Skills Development Provider Contact Person");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#, </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that #SDP_USER_FULL_NAME# (#SDP_USER_EMAIL#) has been approved as a #NEW_DESIGNATION# on #APPROVAL_DATE# for #COMPANY_NAME# (#COMPANY_LEVY_NUMBER#) for training site: #SITE_NAME#. </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For clarity/support, please contact your Regional Office. </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,<br/>#APPROVAL_USER_FULL_NAME#</p>");
			break;
		case 2:
			sendEmail = true;
			subject.append("Non-approval of Skills Development Provider Contact Person");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#, </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that #SDP_USER_FULL_NAME# (#SDP_USER_EMAIL#) has been not been approved as a #NEW_DESIGNATION# on #APPROVAL_DATE# for #COMPANY_NAME# (#COMPANY_LEVY_NUMBER#) for training site: #SITE_NAME# for the following reason(s): </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#REJCTION_REASONS_HTML#</p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For clarity/support, please contact your Regional Office. </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,<br/>#APPROVAL_USER_FULL_NAME#</p>");
			break;
		case 3:
			sendEmail = true;
			subject.append("Approval of Skills Development Provider Contact Person Removal");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#, </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that the request to remove #SDP_USER_FULL_NAME# (#SDP_USER_EMAIL#) as a #OLD_DESIGNATION# for #COMPANY_NAME# (#COMPANY_LEVY_NUMBER#) for training site: #SITE_NAME# has been approved on #APPROVAL_DATE#. </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For clarity/support, please contact your Regional Office. </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,<br/>#APPROVAL_USER_FULL_NAME#</p>");
			break;
		case 4:
			sendEmail = true;
			subject.append("Non-approval of Skills Development Provider Contact Person Removal");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#, </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that the request to remove #SDP_USER_FULL_NAME# (#SDP_USER_EMAIL#) as a #OLD_DESIGNATION# for #COMPANY_NAME# (#COMPANY_LEVY_NUMBER#) for training site: #SITE_NAME# has not been approved for the following reason(s): </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#REJCTION_REASONS_HTML#</p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For clarity/support, please contact your Regional Office. </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,<br/>#APPROVAL_USER_FULL_NAME#</p>");
			break;
		case 5:
			sendEmail = true;
			subject.append("Approval of Skills Development Provider Contact Person Designation Amendment");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#, </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that the skills development provider designation type for #SDP_USER_FULL_NAME# (#SDP_USER_EMAIL#) has been approved to be amended from #OLD_DESIGNATION# to #NEW_DESIGNATION# on #APPROVAL_DATE# for #COMPANY_NAME# (#COMPANY_LEVY_NUMBER#) for training site: #SITE_NAME#. </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For clarity/support, please contact your Regional Office. </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,<br/>#APPROVAL_USER_FULL_NAME#</p>");
			break;
		case 6:
			sendEmail = true;
			subject.append("Non-approval of Skills Development Provider Contact Person Designation Amendment");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#, </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised that the request to amend the skills development provider designation type for #SDP_USER_FULL_NAME# (#SDP_USER_EMAIL#) to be amended from #OLD_DESIGNATION# to #NEW_DESIGNATION# has not been approved for #COMPANY_NAME# (#COMPANY_LEVY_NUMBER#) for training site: #SITE_NAME# for the following reason(s): </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#REJCTION_REASONS_HTML#</p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For clarity/support, please contact your Regional Office. </p>");
			message.append("<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,<br/>#APPROVAL_USER_FULL_NAME#</p>");
			break;
		default:
			sendEmail = false;
			break;
		}
		if (sendEmail) {
			List<Users> notificationRecivers = new ArrayList<>();
			notificationRecivers.addAll(locateUsersForNotification(entity));
			String finalMessage = replaceStringsProviderApplicationSuspensionDeActivated(message.toString(), entity, rejectionReasonsList);
			notifyUsers(subject.toString(), finalMessage, notificationRecivers, false, null);
		}
	}

	private List<Users> locateUsersForNotification(SdpCompanyActions entity) throws Exception {
		List<Users> notificationRecivers = new ArrayList<>();
		List<Users> allUsersList = new ArrayList<>();
		// initiator
		if (entity.getCreateUser() != null && entity.getCreateUser().getId() != null) {
			allUsersList.add(entity.getCreateUser());
		}
		// SDP involved
		if (entity.getSdpUser() != null && entity.getSdpUser().getId() != null) {
			allUsersList.add(entity.getSdpUser());
		}
		// approval user
		if (entity.getApprovalUser() != null && entity.getApprovalUser().getId() != null) {
			allUsersList.add(entity.getApprovalUser());
		}
		if (entity.getSdpCompanyAction() != null) {
			switch (entity.getSdpCompanyAction()) {
			case NewSDP:
				if (entity.getNewDesignation().getId().equals(HAJConstants.PRIMARY_SDP_ID)) {
					// notify all SDPs involved  
					if (entity.getTrainingSite() != null && entity.getTrainingSite().getId() != null) {
						// SDPS linked to site
						List<Users> usersNewSDP = sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(entity.getCompany().getId(), entity.getTrainingSite().getId());
						if (!usersNewSDP.isEmpty()) {
							allUsersList.addAll(usersNewSDP);
						}
						usersNewSDP = null;
					} else {
						// SDPS linked to holding company
						List<Users> usersNewSDP = sdpCompanyService.findAllSdpByCompanyIdReturnUsers(entity.getCompany().getId());
						if (!usersNewSDP.isEmpty()) {
							allUsersList.addAll(usersNewSDP);
						}
						usersNewSDP = null;
					}
				}
				break;
			case UpdateDesignation:
				if (entity.getNewDesignation().getId().equals(HAJConstants.PRIMARY_SDP_ID)) {
					// notify all SDPs involved
					if (entity.getTrainingSite() != null && entity.getTrainingSite().getId() != null) {
						List<Users> usersNewSDP = sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(entity.getCompany().getId(), entity.getTrainingSite().getId());
						if (!usersNewSDP.isEmpty()) {
							allUsersList.addAll(usersNewSDP);
						}
						usersNewSDP = null;
					} else {
						List<Users> usersNewSDP = sdpCompanyService.findAllSdpByCompanyIdReturnUsers(entity.getCompany().getId());
						if (!usersNewSDP.isEmpty()) {
							allUsersList.addAll(usersNewSDP);
						}
						usersNewSDP = null;
					}
				}
				break;
			case RemoveSDP:
				if (entity.getCurrentDesignation().getId().equals(HAJConstants.PRIMARY_SDP_ID)) {
					// notify all SDPs involved
					if (entity.getTrainingSite() != null && entity.getTrainingSite().getId() != null) {
						List<Users> usersNewSDP = sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(entity.getCompany().getId(), entity.getTrainingSite().getId());
						if (!usersNewSDP.isEmpty()) {
							allUsersList.addAll(usersNewSDP);
						}
						usersNewSDP = null;
					} else {
						List<Users> usersNewSDP = sdpCompanyService.findAllSdpByCompanyIdReturnUsers(entity.getCompany().getId());
						if (!usersNewSDP.isEmpty()) {
							allUsersList.addAll(usersNewSDP);
						}
						usersNewSDP = null;
					}
				}
				break;
			default:
				break;
			}
		}
		for (Users user : allUsersList) {
			if (determainIfUserCanBeAdded(notificationRecivers, user)) {
				notificationRecivers.add(user);
			}
		}
		return notificationRecivers;
	}

	private boolean determainIfUserCanBeAdded(List<Users> usersList, Users newUser) {
		// prevents duplicate users being added to notification
		boolean addUser = true;
		for (Users users : usersList) {
			if (users.getId().equals(newUser.getId())) {
				addUser = false;
				break;
			}
		}
		return addUser;
	}

	public String replaceStringsProviderApplicationSuspensionDeActivated(String msg, SdpCompanyActions entity, List<RejectReasons> rejectReasonsList) throws Exception {
		/*
		 * Tags: #ACTION_USER_FULL_NAME#, #ACTION_USER_EMAIL#, #ACTION_DATE#,
		 * #SDP_USER_FULL_NAME#, #SDP_USER_EMAIL#, #COMPANY_NAME#,
		 * #COMPANY_LEVY_NUMBER#, #SITE_NAME# #APPROVAL_USER_FULL_NAME#,
		 * #APPROVAL_USER_EMAIL#, #APPROVAL_DATE#, #FINAL_STATUS#, #ACTION#
		 * #OLD_DESIGNATION#, #NEW_DESIGNATION#, #REJCTION_REASONS_HTML#
		 */
		String actionUserFullName = "";
		if (entity.getCreateUser() != null && entity.getCreateUser().getId() != null) {
			actionUserFullName = entity.getCreateUser().getFirstName().trim() + " "
					+ entity.getCreateUser().getLastName().trim();
		} else {
			actionUserFullName = "#UNABLE TO LOCATE ACTION USER#";
		}
		msg = msg.replace("#ACTION_USER_FULL_NAME#", actionUserFullName);
		msg = msg.replace("#ACTION_USER_EMAIL#",
				((entity != null && entity.getCreateUser() != null && entity.getCreateUser().getEmail() != null)
						? entity.getCreateUser().getEmail().trim() : "N/A"));
		msg = msg.replace("#ACTION_DATE#", ((entity != null && entity.getCreateDate() != null)
				? HAJConstants.sdfDDMMYYYYHHmm.format(entity.getCreateDate()) : "N/A"));
		actionUserFullName = null;

		String sdpUser = "";
		if (entity.getSdpUser() != null && entity.getSdpUser().getId() != null) {
			sdpUser = entity.getSdpUser().getFirstName().trim() + " " + entity.getSdpUser().getLastName().trim();
		} else {
			sdpUser = "#UNABLE TO LOCATE SDP USER#";
		}
		msg = msg.replace("#SDP_USER_FULL_NAME#", sdpUser);
		msg = msg.replace("#SDP_USER_EMAIL#",
				((entity != null && entity.getSdpUser() != null && entity.getSdpUser().getEmail() != null)
						? entity.getSdpUser().getEmail().trim() : "N/A"));
		sdpUser = null;

		msg = msg.replace("#COMPANY_NAME#",
				((entity != null && entity.getCompany() != null && entity.getCompany().getId() != null)
						? entity.getCompany().getCompanyName().trim() : "#UNABLE TO LOCATE COMPANY NAME#"));
		msg = msg
				.replace("#COMPANY_LEVY_NUMBER#",
						((entity != null && entity.getCompany() != null && entity.getCompany().getId() != null)
								? entity.getCompany().getLevyNumber().trim()
								: "#UNABLE TO LOCATE COMPANY LEVY NUMBER#"));
		msg = msg
				.replace("#SITE_NAME#",
						((entity != null && entity.getTrainingSite() != null
								&& entity.getTrainingSite().getId() != null)
										? entity.getTrainingSite().getSiteName().trim() : "N/A"));
		String approvalUser = "";
		if (entity.getApprovalUser() != null && entity.getApprovalUser().getId() != null) {
			approvalUser = entity.getApprovalUser().getFirstName().trim() + " "
					+ entity.getApprovalUser().getLastName().trim();
		} else {
			approvalUser = "#UNABLE TO LOCATE APPROVAL USER#";
		}
		msg = msg.replace("#APPROVAL_USER_FULL_NAME#", approvalUser);
		msg = msg
				.replace("#APPROVAL_USER_EMAIL#",
						((entity != null && entity.getApprovalUser() != null
								&& entity.getApprovalUser().getEmail() != null)
										? entity.getApprovalUser().getEmail().trim() : "N/A"));
		msg = msg.replace("#APPROVAL_DATE#", ((entity != null && entity.getApprovalDate() != null)
				? HAJConstants.sdfDDMMYYYYHHmm.format(entity.getApprovalDate()) : "N/A"));
		msg = msg.replace("#FINAL_STATUS#", ((entity != null && entity.getApprovalStatus() != null)
				? entity.getApprovalStatus().getFriendlyName().trim() : "N/A"));
		msg = msg.replace("#ACTION#", ((entity != null && entity.getSdpCompanyAction() != null)
				? entity.getSdpCompanyAction().getFriendlyName().trim() : "N/A"));
		approvalUser = null;

		msg = msg.replace("#OLD_DESIGNATION#",
				((entity != null && entity.getCurrentDesignation() != null
						&& entity.getCurrentDesignation().getId() != null)
								? entity.getCurrentDesignation().getDescription().trim() : "N/A"));
		msg = msg
				.replace("#NEW_DESIGNATION#",
						((entity != null && entity.getNewDesignation() != null
								&& entity.getNewDesignation().getId() != null)
										? entity.getNewDesignation().getDescription().trim() : "N/A"));
		
		if (rejectReasonsList != null && !rejectReasonsList.isEmpty()) {
			msg = msg.replace("#REJCTION_REASONS_HTML#", populateRejectionReasonsForMail(rejectReasonsList));
		}
		return msg;
	}
	
	private String populateRejectionReasonsForMail(List<RejectReasons> rejectReasons) throws Exception {
		String list = "<ul>";
		try {
			for (RejectReasons rejectReason : rejectReasons) {
				list += "<li> " + rejectReason.getDescription();
				list += "</li>";
			}
		} catch (Exception e) {
		}
		list += "</ul>";
		return list;
	}

	public void notifyUsers(String subject, String message, List<Users> notificationRecivers, boolean withAttachment,
			List<AttachmentBean> attachmentBeans) {
		for (Users users : notificationRecivers) {
			String fullName = "";
			if (users.getTitle() != null && users.getTitle().getDescription() != null) {
				fullName = users.getTitle().getDescription() + " ";
			}
			fullName += users.getFirstName().trim() + " " + users.getLastName().trim();
			if (withAttachment) {
				GenericUtility.sendMailWithAttachementTempWithLog(users.getEmail(), subject,
						message.replace("#RECEIVER_FULL_NAME#", fullName), attachmentBeans, null);
			} else {
				GenericUtility.sendMail(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName),
						null);
			}
		}
	}

}