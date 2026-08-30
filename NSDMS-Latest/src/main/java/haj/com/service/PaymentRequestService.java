package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.PaymentRequestDAO;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.PaymentRequest;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class PaymentRequestService extends AbstractService {
	/** The dao. */
	private PaymentRequestDAO           dao                         = new PaymentRequestDAO();
	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();
	private ActiveContractsService      activeContractService       = new ActiveContractsService();
	private WspDGService                wspDGService       = new WspDGService();

	/**
	 * Get all PaymentRequest
	 * @author TechFinium
	 * @see PaymentRequest
	 * @return a list of {@link haj.com.entity.PaymentRequest}
	 * @throws Exception
	 * the exception
	 */
	public List<PaymentRequest> allPaymentRequest() throws Exception {
		return dao.allPaymentRequest();
	}

	/**
	 * Create or update PaymentRequest.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see PaymentRequest
	 */
	public void create(PaymentRequest entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update PaymentRequest.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see PaymentRequest
	 */
	public void update(PaymentRequest entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete PaymentRequest.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see PaymentRequest
	 */
	public void delete(PaymentRequest entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.PaymentRequest}
	 * @throws Exception
	 * the exception
	 * @see PaymentRequest
	 */
	public PaymentRequest findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find PaymentRequest by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.PaymentRequest}
	 * @throws Exception
	 * the exception
	 * @see PaymentRequest
	 */
	public List<PaymentRequest> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load PaymentRequest
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.PaymentRequest}
	 * @throws Exception
	 * the exception
	 */
	public List<PaymentRequest> allPaymentRequest(int first, int pageSize) throws Exception {
		return dao.allPaymentRequest(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of PaymentRequest for lazy load
	 * @author TechFinium
	 * @return Number of rows in the PaymentRequest
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(PaymentRequest.class);
	}

	/**
	 * Lazy load PaymentRequest with pagination, filter, sorting
	 * @author TechFinium
	 * @param class1
	 * PaymentRequest class
	 * @param first
	 * the first
	 * @param pageSize
	 * the page size
	 * @param sortField
	 * the sort field
	 * @param sortOrder
	 * the sort order
	 * @param filters
	 * the filters
	 * @return a list of {@link haj.com.entity.PaymentRequest} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<PaymentRequest> allPaymentRequest(Class<PaymentRequest> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<PaymentRequest>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of PaymentRequest for lazy load with filters
	 * @author TechFinium
	 * @param entity
	 * PaymentRequest class
	 * @param filters
	 * the filters
	 * @return Number of rows in the PaymentRequest entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<PaymentRequest> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void requesteWorkflow(ProjectImplementationPlan entity, Users u) throws Exception {
		PaymentRequest pr = new PaymentRequest();
		pr.setProjectImplementationPlan(entity);
		pr.setStatus(ApprovalEnum.PendingApproval);
		pr.setCreateUser(u);
		pr.setCompany(entity.getWsp().getCompany());
		dao.create(pr);
		Wsp wsp = wspDGService.findByKeyPlain(entity.getWsp().getId());
		List<Users> users = new ArrayList<>();
		users.add(wsp.getProjectManager());
		TasksService.instance().findFirstInProcessAndCreateTask("", u, pr.getId(), pr.getClass().getName(), false, ConfigDocProcessEnum.REQUEST_FUNDING_PAYMENT, null, users);
	}

	public void completeWorkflow(PaymentRequest entity, Users user, Tasks tasks) throws Exception {
		String error = "";
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument() != null) {

				}
			}
		}

		if (error.length() > 0) throw new ValidationException(error);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void approveWorkflow(PaymentRequest entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void rejectWorkflow(PaymentRequest entity, Users user, Tasks tasks) throws Exception {
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
	}

	public void finalApproveWorkflow(PaymentRequest entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		entity.setApprovalUser(user);
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
		ActiveContracts ac = activeContractService.findByWSP(entity.getProjectImplementationPlan().getWsp());
		activeContractDetailService.addProjectPayment(ac, user, entity.getProjectImplementationPlan());
	}

	public void finalRejectWorkflow(PaymentRequest entity, Users user, Tasks tasks, List<RejectReasons> rejectionReasonsList) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
		// create rejection reasons
		if (rejectionReasonsList != null && !rejectionReasonsList.isEmpty()) {
			List<IDataEntity> createList = new ArrayList<>();
			createList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), entity.getClass().getName(), rejectionReasonsList, user, tasks.getWorkflowProcess()));
			dao.createBatch(createList);
		}
	}
}
