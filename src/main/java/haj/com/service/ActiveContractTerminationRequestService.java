package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import haj.com.dao.ActiveContractTerminationRequestDAO;
import haj.com.entity.ActiveContractExtensionRequest;
import haj.com.entity.ActiveContractTerminationRequest;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.ContractStatusEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class ActiveContractTerminationRequestService extends AbstractService {
	
	/** The dao. */
	private ActiveContractTerminationRequestDAO dao = new ActiveContractTerminationRequestDAO();
	
	/** Links to service levels */
	private ConfigDocService configDocService = new ConfigDocService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	private RejectReasonsService rejectReasonsService = null;

	/**
	 * Get all ActiveContractTerminationRequest
 	 * @author TechFinium 
 	 * @see   ActiveContractTerminationRequest
 	 * @return a list of {@link haj.com.entity.ActiveContractTerminationRequest}
	 * @throws Exception the exception
 	 */
	public List<ActiveContractTerminationRequest> allActiveContractTerminationRequest() throws Exception {
	  	return dao.allActiveContractTerminationRequest();
	}


	/**
	 * Create or update ActiveContractTerminationRequest.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ActiveContractTerminationRequest
	 */
	public void create(ActiveContractTerminationRequest entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ActiveContractTerminationRequest.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ActiveContractTerminationRequest
	 */
	public void update(ActiveContractTerminationRequest entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ActiveContractTerminationRequest.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ActiveContractTerminationRequest
	 */
	public void delete(ActiveContractTerminationRequest entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ActiveContractTerminationRequest}
	 * @throws Exception the exception
	 * @see    ActiveContractTerminationRequest
	 */
	public ActiveContractTerminationRequest findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ActiveContractTerminationRequest by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ActiveContractTerminationRequest}
	 * @throws Exception the exception
	 * @see    ActiveContractTerminationRequest
	 */
	public List<ActiveContractTerminationRequest> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ActiveContractTerminationRequest
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ActiveContractTerminationRequest}
	 * @throws Exception the exception
	 */
	public List<ActiveContractTerminationRequest> allActiveContractTerminationRequest(int first, int pageSize) throws Exception {
		return dao.allActiveContractTerminationRequest(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ActiveContractTerminationRequest for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ActiveContractTerminationRequest
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ActiveContractTerminationRequest.class);
	}
	
    /**
     * Lazy load ActiveContractTerminationRequest with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ActiveContractTerminationRequest class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ActiveContractTerminationRequest} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ActiveContractTerminationRequest> allActiveContractTerminationRequest(Class<ActiveContractTerminationRequest> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList(( List<ActiveContractTerminationRequest>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	}
	
    /**
     * Get count of ActiveContractTerminationRequest for lazy load with filters
     * @author TechFinium 
     * @param entity ActiveContractTerminationRequest class
     * @param filters the filters
     * @return Number of rows in the ActiveContractTerminationRequest entity
     * @throws Exception the exception     
     */	
	public int count(Class<ActiveContractTerminationRequest> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public void requestTerminationRequestWorkflowByActiveContract(ActiveContracts activeContracts, Users user, Doc doc, boolean systemGenerated) throws Exception {
		// set system generated extension request and create request
		ActiveContractTerminationRequest activeContractTerminationRequest = new ActiveContractTerminationRequest(activeContracts, user, getSynchronizedDate(), ApprovalEnum.PendingApproval, systemGenerated);
		create(activeContractTerminationRequest);
		
		// sets doc against action
		doc.setTargetClass(activeContractTerminationRequest.getClass().getName());
		doc.setTargetKey(activeContractTerminationRequest.getId());
		DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
		
		// creates the task
		TasksService.instance().findFirstInProcessAndCreateTask(user, activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_TERMINATION_REQUEST, false);
		
		// update the active contract that under extension workflow
		activeContracts.setExtensionTerminationWorkflowActive(true);
		dao.update(activeContracts);
	}
	
	public void systemGeneratedTerminationRequestWorkflowByActiveContract(ActiveContracts activeContracts, boolean systemGenerated) throws Exception {
		// set system generated extension request and create request
		ActiveContractTerminationRequest activeContractTerminationRequest = new ActiveContractTerminationRequest(activeContracts, getSynchronizedDate(), ApprovalEnum.PendingApproval, systemGenerated);
		create(activeContractTerminationRequest);
		
		// creates the task
		TasksService.instance().findFirstInProcessAndCreateTask(null, activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_TERMINATION_REQUEST, false);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContractTerminationRequest> allActiveContractTerminationRequestByActiveContractId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long activeContractId) throws Exception {
		String hql = "select o from ActiveContractTerminationRequest o where o.activeContracts.id = :activeContractId";
		filters.put("activeContractId", activeContractId);
		return populateAdditionalInformationList((List<ActiveContractTerminationRequest>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllActiveContractTerminationRequestByActiveContractId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContractTerminationRequest o where o.activeContracts.id = :activeContractId";
		return dao.countWhere(filters, hql);
	}
	
	public List<ActiveContractTerminationRequest> populateAdditionalInformationList(List<ActiveContractTerminationRequest> activeContractTerminationRequestList) throws Exception {
		for (ActiveContractTerminationRequest activeContractTerminationRequest : activeContractTerminationRequestList) {
			populateAdditionalInformation(activeContractTerminationRequest);
		}
		return activeContractTerminationRequestList;
	}

	public ActiveContractTerminationRequest populateAdditionalInformation(ActiveContractTerminationRequest activeContractTerminationRequest) throws Exception {
		if (activeContractTerminationRequest.getActiveContracts() != null && activeContractTerminationRequest.getActiveContracts().getId() != null) {
			activeContractTerminationRequest.setActiveContracts(activeContractsService.findByKey(activeContractTerminationRequest.getActiveContracts().getId()));
		}
		if (activeContractTerminationRequest.getId() != null) {
			populateDocsByTargetClassAndKey(activeContractTerminationRequest);
		}
		return activeContractTerminationRequest;
	}

	public ActiveContractTerminationRequest populateDocsByTargetClassAndKey(ActiveContractTerminationRequest entity) throws Exception {
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		return entity;
	}

	public void populateDocsByConfigDocProcessAndCompanyUserTypeEnum(ActiveContractTerminationRequest entity, ConfigDocProcessEnum configDocProcessEnum, CompanyUserTypeEnum CompanyUserTypeEnum) throws Exception {
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcessEnum, CompanyUserTypeEnum);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				entity.getDocs().add(new Doc(a));
			});
		}
	}
	
	public void validationsOfEditAndUpload(ActiveContractTerminationRequest activeContractTerminationRequest, boolean canEdit, boolean canUpload) throws Exception{
		boolean update = false;
		List<String> expectionList = new ArrayList<>();
		
		// edit permissions:
		if (canEdit) {
			
		}
		
		// upload permission
		if (canUpload) {
			if (activeContractTerminationRequest.getDocs() != null && activeContractTerminationRequest.getDocs().size() != 0) {
				String errors = "";
				for (Doc doc : activeContractTerminationRequest.getDocs()) {
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
			update(activeContractTerminationRequest);
		}
	}

	public void completeTask(ActiveContractTerminationRequest activeContractTerminationRequest, Users user, Tasks task) throws Exception {
		if (task.getFirstInProcess() == null) {
			TasksService.instance().completeTask(task);
			TasksService.instance().findFirstInProcessAndCreateTask(user, activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), task.getWorkflowProcess(), false);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask(user, task, null, false);
		}
		RejectReasonMultipleSelectionService.instance().clearEntries(activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), task.getWorkflowProcess());
		update(activeContractTerminationRequest);
	}

	public void rejectTask(ActiveContractTerminationRequest activeContractTerminationRequest, Users user, Tasks task, List<RejectReasons> selectedRejectionReasons) throws Exception {
		if (activeContractTerminationRequest.getStatus() == ApprovalEnum.PendingFinalApproval) {
			activeContractTerminationRequest.setStatus(ApprovalEnum.PendingApproval);
			update(activeContractTerminationRequest);
		}
		TasksService.instance().findPreviousInProcessAndCreateTask(user, task, null);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), selectedRejectionReasons, user, task.getWorkflowProcess());
	}

	public void approveTask(ActiveContractTerminationRequest activeContractTerminationRequest, Users user, Tasks task) throws Exception {
		activeContractTerminationRequest.setStatus(ApprovalEnum.PendingFinalApproval);
		update(activeContractTerminationRequest);
		TasksService.instance().findNextInProcessAndCreateTask(user, task, null, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), task.getWorkflowProcess());
	}

	public void finalApproveTask(ActiveContractTerminationRequest activeContractTerminationRequest, Users user, Tasks task) throws Exception {
		activeContractTerminationRequest.setStatus(ApprovalEnum.Approved);
		activeContractTerminationRequest.setApproveUser(user);
		activeContractTerminationRequest.setApprovalDate(getSynchronizedDate());
		update(activeContractTerminationRequest);
		TasksService.instance().completeTask(task);
		RejectReasonMultipleSelectionService.instance().clearEntries(activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), task.getWorkflowProcess());
		if (activeContractTerminationRequest.getActiveContracts() != null && activeContractTerminationRequest.getActiveContracts().getId() != null) {
			finalActionsRegardingActiveContract(activeContractTerminationRequest, task.getWorkflowProcess());
		}
	}

	public void finalRejectTask(ActiveContractTerminationRequest activeContractTerminationRequest, Users user, Tasks task, List<RejectReasons> selectedRejectionReasons) throws Exception {
		activeContractTerminationRequest.setStatus(ApprovalEnum.Rejected);
		activeContractTerminationRequest.setApproveUser(user);
		activeContractTerminationRequest.setApprovalDate(getSynchronizedDate());
		update(activeContractTerminationRequest);
		TasksService.instance().completeTask(task);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), selectedRejectionReasons, user, task.getWorkflowProcess());
		if (activeContractTerminationRequest.getActiveContracts() != null && activeContractTerminationRequest.getActiveContracts().getId() != null) {
			finalActionsRegardingActiveContract(activeContractTerminationRequest, task.getWorkflowProcess());
		}
	}
	
	public void finalActionsRegardingActiveContract(ActiveContractTerminationRequest activeContractTerminationRequest, ConfigDocProcessEnum configDocEnum) throws Exception{
		
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		ActiveContracts activeContract = activeContractsService.findByKey(activeContractTerminationRequest.getActiveContracts().getId());
		
		boolean sendEmailNotification = false;
		String subject = "";
		String message = "";
		
		if (activeContractTerminationRequest.getStatus() == ApprovalEnum.Approved) {
			// update status to terminated and end all work flows regarding active contract
			activeContract.setExtensionTerminationWorkflowActive(false);
			activeContract.setStatus(ApprovalEnum.ProjectTerminated);
			activeContract.setContractStatusEnum(ContractStatusEnum.Terminated);
			activeContractsService.update(activeContract);
			
			TasksService.instance().completeTaskByTargetKey(activeContract.getClass().getName(), activeContract.getId());
			
			sendEmailNotification = true;
			subject = "";
			message = "";
		} else if (activeContractTerminationRequest.getStatus() == ApprovalEnum.Rejected) {
			
			activeContract.setExtensionTerminationWorkflowActive(false);
			activeContractsService.update(activeContract);
			
			if (rejectReasonsService == null) {
				rejectReasonsService = new RejectReasonsService();
			}
			List<RejectReasons> rejectionReasons = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(activeContractTerminationRequest.getId(), activeContractTerminationRequest.getClass().getName(), configDocEnum);
			
			sendEmailNotification = true;
			subject = "";
			message = "";
		}

		// sends notification based on result
		if (sendEmailNotification) {
			List<Users> recievers = new ArrayList<>();
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
