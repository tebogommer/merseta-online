package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.ActiveContractExtensionRequestDAO;
import haj.com.entity.ActiveContractExtensionRequest;
import haj.com.entity.ActiveContractTerminationRequest;
import haj.com.entity.ActiveContracts;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

public class ActiveContractExtensionRequestService extends AbstractService {

	/** The dao. */
	private ActiveContractExtensionRequestDAO dao = new ActiveContractExtensionRequestDAO();

	/** Links to service levels */
	private ConfigDocService configDocService = new ConfigDocService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	private RejectReasonsService rejectReasonsService = null;

	/**
	 * Get all ActiveContractExtensionRequest
	 * 
	 * @author TechFinium
	 * @see ActiveContractExtensionRequest
	 * @return a list of {@link haj.com.entity.ActiveContractExtensionRequest}
	 * @throws Exception
	 *             the exception
	 */
	public List<ActiveContractExtensionRequest> allActiveContractExtensionRequest() throws Exception {
		return populateAdditionalInformationList(dao.allActiveContractExtensionRequest());
	}

	/**
	 * Create or update ActiveContractExtensionRequest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractExtensionRequest
	 */
	public void create(ActiveContractExtensionRequest entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update ActiveContractExtensionRequest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractExtensionRequest
	 */
	public void update(ActiveContractExtensionRequest entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ActiveContractExtensionRequest.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractExtensionRequest
	 */
	public void delete(ActiveContractExtensionRequest entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.ActiveContractExtensionRequest}
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractExtensionRequest
	 */
	public ActiveContractExtensionRequest findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	/**
	 * Find ActiveContractExtensionRequest by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.ActiveContractExtensionRequest}
	 * @throws Exception
	 *             the exception
	 * @see ActiveContractExtensionRequest
	 */
	public List<ActiveContractExtensionRequest> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc));
	}

	/**
	 * Lazy load ActiveContractExtensionRequest
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.ActiveContractExtensionRequest}
	 * @throws Exception
	 *             the exception
	 */
	public List<ActiveContractExtensionRequest> allActiveContractExtensionRequest(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allActiveContractExtensionRequest(Long.valueOf(first), pageSize));
	}

	/**
	 * Get count of ActiveContractExtensionRequest for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the ActiveContractExtensionRequest
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ActiveContractExtensionRequest.class);
	}

	/**
	 * Lazy load ActiveContractExtensionRequest with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            ActiveContractExtensionRequest class
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
	 * @return a list of {@link haj.com.entity.ActiveContractExtensionRequest}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContractExtensionRequest> allActiveContractExtensionRequest(Class<ActiveContractExtensionRequest> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<ActiveContractExtensionRequest>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	/**
	 * Get count of ActiveContractExtensionRequest for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            ActiveContractExtensionRequest class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ActiveContractExtensionRequest entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<ActiveContractExtensionRequest> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContractExtensionRequest> allActiveContractExtensionRequestByActiveContractId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long activeContractId) throws Exception {
		String hql = "select o from ActiveContractExtensionRequest o where o.activeContracts.id = :activeContractId";
		filters.put("activeContractId", activeContractId);
		return populateAdditionalInformationList((List<ActiveContractExtensionRequest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllActiveContractExtensionRequestByActiveContractId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContractExtensionRequest o where o.activeContracts.id = :activeContractId";
		return dao.countWhere(filters, hql);
	}

	public List<ActiveContractExtensionRequest> populateAdditionalInformationList(List<ActiveContractExtensionRequest> activeContractExtensionRequestList) throws Exception {
		for (ActiveContractExtensionRequest activeContractExtensionRequest : activeContractExtensionRequestList) {
			populateAdditionalInformation(activeContractExtensionRequest);
		}
		return activeContractExtensionRequestList;
	}

	public ActiveContractExtensionRequest populateAdditionalInformation(ActiveContractExtensionRequest activeContractExtensionRequest) throws Exception {
		if (activeContractExtensionRequest.getActiveContracts() != null && activeContractExtensionRequest.getActiveContracts().getId() != null) {
			activeContractExtensionRequest.setActiveContracts(activeContractsService.findByKey(activeContractExtensionRequest.getActiveContracts().getId()));
		}
		if (activeContractExtensionRequest.getId() != null) {
			populateDocsByTargetClassAndKey(activeContractExtensionRequest);
		}
		return activeContractExtensionRequest;
	}

	public ActiveContractExtensionRequest populateDocsByTargetClassAndKey(ActiveContractExtensionRequest entity) throws Exception {
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		return entity;
	}

	public void populateDocsByConfigDocProcessAndCompanyUserTypeEnum(ActiveContractExtensionRequest entity, ConfigDocProcessEnum configDocProcessEnum, CompanyUserTypeEnum CompanyUserTypeEnum) throws Exception {
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcessEnum, CompanyUserTypeEnum);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				entity.getDocs().add(new Doc(a));
			});
		}
	}
	
	public void updateExtensionRequestDate(ActiveContractExtensionRequest activeContractExtensionRequest, Users sessionUser, Boolean useSystemGeneratedDate, Date enteredDate) throws Exception {
		activeContractExtensionRequest.setUseSystemGeneratedDate(useSystemGeneratedDate);
		if (useSystemGeneratedDate) {
			activeContractExtensionRequest.setEnteredDate(null);
		} else {
			activeContractExtensionRequest.setEnteredDate(GenericUtility.getStartOfDay(enteredDate));
		}
		activeContractExtensionRequest.setUserUpdatedDate(sessionUser);
		activeContractExtensionRequest.setDateUpdatedExtensionDate(getSynchronizedDate());
		update(activeContractExtensionRequest);
	}

	public void validationsOfEditAndUpload(ActiveContractExtensionRequest activeContractExtensionRequest, boolean canEdit, boolean canUpload) throws Exception{
		boolean update = false;
		List<String> expectionList = new ArrayList<>();
		
		// edit permissions:
		if (canEdit) {
			if (activeContractExtensionRequest.getUseSystemGeneratedDate() != null && !activeContractExtensionRequest.getUseSystemGeneratedDate()) {
				if (activeContractExtensionRequest.getEnteredDate() == null) {
					expectionList.add("Provide A Date If Not Using System Generated Date");
				}
			}
		}
		
		// upload permission
		if (canUpload) {
			if (activeContractExtensionRequest.getDocs() != null && activeContractExtensionRequest.getDocs().size() != 0) {
				String errors = "";
				for (Doc doc : activeContractExtensionRequest.getDocs()) {
					if (doc.getId() == null) {
						if ((doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) && (doc.getData() == null || doc.getData().length == 0)) {
							if (errors != "") {
								errors += ", " + doc.getConfigDoc().getName();
							} else {
								errors = doc.getConfigDoc().getName();
							}	
						} else if (doc.isRequired() && (doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
							if (errors != "") {
								errors += ", " + doc.getConfigDoc().getName();
							} else {
								errors = doc.getConfigDoc().getName();
							}	
						}
						if (errors != "") {
							expectionList.add("Document Required: " + errors);
						}
					}
				}
			}
		}
			
		if (expectionList.size() > 0) {
			String exceptionMessage = "";
			int count = 1;
			for (String expection : expectionList) {
				if (count == expectionList.size()) {
					exceptionMessage += expection;
				} else {
					exceptionMessage += "," +expection;
				}
			}
			throw new Exception("Validiation Exception, Please Attend To The Exception(s) Before Proceeding: " + exceptionMessage.trim());
		} else {
			update = true;
		}
		
		if (update) {
			update(activeContractExtensionRequest);
		}
	}
	
	public void requestExtensionRequestWorkflowByActiveContractTesting(ActiveContracts activeContracts, Users user) throws Exception {
		// set system generated extension request and create request
		Date systemGeneratedExtendedDate = GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(activeContracts.getDeadlineDate(), 30));
		ActiveContractExtensionRequest activeContractExtensionRequest = new ActiveContractExtensionRequest(activeContracts, user, activeContracts.getDeadlineDate(), systemGeneratedExtendedDate, true, ApprovalEnum.PendingApproval);
		create(activeContractExtensionRequest);
		TasksService.instance().findFirstInProcessAndCreateTask(user, activeContractExtensionRequest.getId(), activeContractExtensionRequest.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_EXTENSION_REQUEST, false);
		
		// update the active contract that under extension workflow
		activeContracts.setExtensionTerminationWorkflowActive(false);
		dao.update(activeContracts);
	}
	
	public void requestExtensionRequestWorkflowByActiveContract(ActiveContracts activeContracts, Users user, Doc doc) throws Exception {
		// set system generated extension request and create request
		Date systemGeneratedExtendedDate = GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(activeContracts.getDeadlineDate(), 30));
		ActiveContractExtensionRequest activeContractExtensionRequest = new ActiveContractExtensionRequest(activeContracts, user, activeContracts.getDeadlineDate(), systemGeneratedExtendedDate, true, ApprovalEnum.PendingApproval);
		create(activeContractExtensionRequest);
		
		// sets doc against action
		doc.setTargetClass(activeContractExtensionRequest.getClass().getName());
		doc.setTargetKey(activeContractExtensionRequest.getId());
		DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
		
		// creates the task
		TasksService.instance().findFirstInProcessAndCreateTask(user, activeContractExtensionRequest.getId(), activeContractExtensionRequest.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_EXTENSION_REQUEST, false);
		
		// update the active contract that under extension workflow
		activeContracts.setExtensionTerminationWorkflowActive(true);
		dao.update(activeContracts);
	}

	public void completeTask(ActiveContractExtensionRequest activeContractExtensionRequest, Users user, Tasks task) throws Exception {
		if (task.getFirstInProcess() == null) {
			TasksService.instance().completeTask(task);
			TasksService.instance().findFirstInProcessAndCreateTask(user, activeContractExtensionRequest.getId(), activeContractExtensionRequest.getClass().getName(), task.getWorkflowProcess(), false);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask(user, task, null, false);
		}
		RejectReasonMultipleSelectionService.instance().clearEntries(activeContractExtensionRequest.getId(), activeContractExtensionRequest.getClass().getName(), task.getWorkflowProcess());
		update(activeContractExtensionRequest);
	}

	public void rejectTask(ActiveContractExtensionRequest activeContractExtensionRequest, Users user, Tasks task, List<RejectReasons> selectedRejectionReasons) throws Exception {
		if (activeContractExtensionRequest.getStatus() == ApprovalEnum.PendingFinalApproval) {
			activeContractExtensionRequest.setStatus(ApprovalEnum.PendingApproval);
			update(activeContractExtensionRequest);
		}
		TasksService.instance().findPreviousInProcessAndCreateTask(user, task, null);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(activeContractExtensionRequest.getId(), activeContractExtensionRequest.getClass().getName(), selectedRejectionReasons, user, task.getWorkflowProcess());
	}

	public void approveTask(ActiveContractExtensionRequest activeContractExtensionRequest, Users user, Tasks task) throws Exception {
		activeContractExtensionRequest.setStatus(ApprovalEnum.PendingFinalApproval);
		update(activeContractExtensionRequest);
		TasksService.instance().findNextInProcessAndCreateTask(user, task, null, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(activeContractExtensionRequest.getId(), activeContractExtensionRequest.getClass().getName(), task.getWorkflowProcess());
	}

	public void finalApproveTask(ActiveContractExtensionRequest activeContractExtensionRequest, Users user, Tasks task) throws Exception {
		activeContractExtensionRequest.setStatus(ApprovalEnum.Approved);
		activeContractExtensionRequest.setApproveUser(user);
		activeContractExtensionRequest.setApprovalDate(getSynchronizedDate());
		update(activeContractExtensionRequest);
		TasksService.instance().completeTask(task);
		RejectReasonMultipleSelectionService.instance().clearEntries(activeContractExtensionRequest.getId(), activeContractExtensionRequest.getClass().getName(), task.getWorkflowProcess());
		if (activeContractExtensionRequest.getActiveContracts() != null && activeContractExtensionRequest.getActiveContracts().getId() != null) {
			finalActionsRegardingActiveContract(activeContractExtensionRequest, task.getWorkflowProcess());
		}
	}

	public void finalRejectTask(ActiveContractExtensionRequest activeContractExtensionRequest, Users user, Tasks task, List<RejectReasons> selectedRejectionReasons) throws Exception {
		activeContractExtensionRequest.setStatus(ApprovalEnum.Rejected);
		activeContractExtensionRequest.setApproveUser(user);
		activeContractExtensionRequest.setApprovalDate(getSynchronizedDate());
		update(activeContractExtensionRequest);
		TasksService.instance().completeTask(task);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(activeContractExtensionRequest.getId(), activeContractExtensionRequest.getClass().getName(), selectedRejectionReasons, user, task.getWorkflowProcess());
		if (activeContractExtensionRequest.getActiveContracts() != null && activeContractExtensionRequest.getActiveContracts().getId() != null) {
			finalActionsRegardingActiveContract(activeContractExtensionRequest, task.getWorkflowProcess());
		}
	}
	
	public void finalActionsRegardingActiveContract(ActiveContractExtensionRequest activeContractExtensionRequest, ConfigDocProcessEnum configDocEnum) throws Exception{
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		ActiveContracts activeContract = activeContractsService.findByKey(activeContractExtensionRequest.getActiveContracts().getId());
		
		boolean sendEmailNotification = false;
		String subject = "";
		String message = "";
		
		if (activeContractExtensionRequest.getStatus() == ApprovalEnum.Approved) {
			if (activeContractExtensionRequest.getUseSystemGeneratedDate()) {
				activeContract.setExtensionTerminationWorkflowActive(false);
				activeContract.setDeadlineDate(activeContractExtensionRequest.getExtenionDate());
			} else {
				activeContract.setExtensionTerminationWorkflowActive(false);
				activeContract.setDeadlineDate(activeContractExtensionRequest.getEnteredDate());
			}
			activeContractsService.update(activeContract);
			sendEmailNotification = true;
			subject = "";
			message = "";
		} else if (activeContractExtensionRequest.getStatus() == ApprovalEnum.Rejected) {
			
			activeContract.setExtensionTerminationWorkflowActive(false);
			activeContractsService.update(activeContract);
			
			if (rejectReasonsService == null) {
				rejectReasonsService = new RejectReasonsService();
			}
			List<RejectReasons> rejectionReasons = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(activeContractExtensionRequest.getId(), activeContractExtensionRequest.getClass().getName(), configDocEnum);
			
			sendEmailNotification = true;
			subject = "";
			message = "";
			
		}

		// sends notification based on result
		if (sendEmailNotification) {
			activeContract = activeContractsService.findByKey(activeContractExtensionRequest.getActiveContracts().getId());
			List<Users> recievers = activeContractsService.populateEmailRecivers(activeContract);
			for (Users user : recievers) {
				String fullName = "";
				if (user.getTitle() != null && user.getTitle().getId() != null) {
					fullName = user.getTitle().getDescription() + " ";
				}
				fullName += user.getFirstName() + " " + user.getLastName();
				GenericUtility.sendMail(user.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
			}
		}
	}
}