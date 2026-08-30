package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.RejectReasonsChildDAO;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;

/**
 * The Class RejectReasonsChildService.
 */
public class RejectReasonsChildService extends AbstractService {
	/** The dao. */
	private RejectReasonsChildDAO dao = new RejectReasonsChildDAO();

	/**
	 * Get all RejectReasonsChild.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.RejectReasonsChild}
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonsChild
	 */
	public List<RejectReasonsChild> allRejectReasonsChild() throws Exception {
		return dao.allRejectReasonsChild();
	}

	/**
	 * Create or update RejectReasonsChild.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonsChild
	 */
	public void create(RejectReasonsChild entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void createWspReject(List<RejectReasons> selectedRejectReason, Wsp wsp, Tasks task, String additionalInformation) throws Exception {
		for (RejectReasons rejectReason : selectedRejectReason) {
			if (rejectReason != null) create(new RejectReasonsChild(rejectReason, wsp.getCompany(), wsp, task, additionalInformation));
		}
	}

	public void createSDFReject(List<RejectReasons> selectedRejectReason, SDFCompany companysdf, Tasks task, String additionalInformation) throws Exception {
		for (RejectReasons rejectReason : selectedRejectReason) {
			if (rejectReason != null) create(new RejectReasonsChild(rejectReason, companysdf.getCompany(), companysdf.getSdf(), task, additionalInformation));
		}
	}

	public void createSDFReject(List<RejectReasons> selectedRejectReason, Users companysdf, Tasks task, String additionalInformation) throws Exception {
		for (RejectReasons rejectReason : selectedRejectReason) {
			if (rejectReason != null) create(new RejectReasonsChild(rejectReason, null, companysdf, task, additionalInformation));
		}
	}

	public void createReject(List<RejectReasons> selectedRejectReason, String targetClass, Long targetKey, Tasks task, String additionalInformation) throws Exception {
		for (RejectReasons rejectReason : selectedRejectReason) {
			if (rejectReason != null) create(new RejectReasonsChild(rejectReason, task, targetClass, targetKey));
		}
	}
	
	public List<RejectReasonsChild> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassAndKey(targetClass, targetKey);
	}

	/**
	 * Update RejectReasonsChild.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonsChild
	 */
	public void update(RejectReasonsChild entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete RejectReasonsChild.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonsChild
	 */
	public void delete(RejectReasonsChild entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.RejectReasonsChild}
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonsChild
	 */
	public RejectReasonsChild findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find RejectReasonsChild by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.RejectReasonsChild}
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonsChild
	 */
	public List<RejectReasonsChild> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load RejectReasonsChild.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.RejectReasonsChild}
	 * @throws Exception
	 *             the exception
	 */
	public List<RejectReasonsChild> allRejectReasonsChild(int first, int pageSize) throws Exception {
		return dao.allRejectReasonsChild(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of RejectReasonsChild for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the RejectReasonsChild
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(RejectReasonsChild.class);
	}

	/**
	 * Lazy load RejectReasonsChild with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            RejectReasonsChild class
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
	 * @return a list of {@link haj.com.entity.RejectReasonsChild} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonsChild> allRejectReasonsChild(Class<RejectReasonsChild> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<RejectReasonsChild>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of RejectReasonsChild for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            RejectReasonsChild class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the RejectReasonsChild entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<RejectReasonsChild> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public List<RejectReasonsChild> findBySDF(Users users) throws Exception {
		return dao.findBySDF(users);
	}
	
	public List<RejectReasonsChild> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}
	
	public List<RejectReasonsChild> findByCompanyBankingDetails(Company company) throws Exception {
		return dao.findByCompanyBankingDetails(company);
	}
	
	public List<RejectReasonsChild> findByCompanyBankingDetails(Company company, BankingDetails bankingDetails) throws Exception {
		return dao.findByCompanyBankingDetails(company,bankingDetails);
	}
	
	public List<RejectReasonsChild> findByAM(AssessorModeratorApplication assessorModeratorApplication) throws Exception {
		return dao.findByAM(assessorModeratorApplication);
	}

	public List<RejectReasonsChild> findByWsp(Wsp wsp) throws Exception {
		return dao.findByWsp(wsp);
	}

	public void createAMReject(List<RejectReasons> selectedRejectReason, Users user, Tasks task, String additionalInformation, AssessorModeratorApplication amApplication) throws Exception {
		for (RejectReasons rejectReason : selectedRejectReason) {
			if (rejectReason != null) {
				RejectReasonsChild reasonsChild = new RejectReasonsChild();

				reasonsChild.setRejectReasons(rejectReason);
				reasonsChild.setAssessorModeratorApplication(amApplication);
				reasonsChild.setUser(user);
				reasonsChild.setTasks(task);
				reasonsChild.setAdditionalInformation(additionalInformation);
				create(reasonsChild);
			}
		}
	}
}
