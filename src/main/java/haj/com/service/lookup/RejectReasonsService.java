package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.RejectReasonsDAO;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.service.RejectReasonMultipleSelectionService;

// TODO: Auto-generated Javadoc
/**
 * The Class RejectReasonsService.
 */
public class RejectReasonsService extends AbstractService {

	/** The dao. */
	private RejectReasonsDAO dao = new RejectReasonsDAO();

	/**
	 * Get all RejectReasons.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.RejectReasons}
	 * @throws Exception
	 *             the exception
	 * @see RejectReasons
	 */
	public List<RejectReasons> allRejectReasons() throws Exception {
		return dao.allRejectReasons();
	}

	/**
	 * Create or update RejectReasons.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RejectReasons
	 */
	public void create(RejectReasons entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update RejectReasons.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RejectReasons
	 */
	public void update(RejectReasons entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete RejectReasons.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RejectReasons
	 */
	public void delete(RejectReasons entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.RejectReasons}
	 * @throws Exception
	 *             the exception
	 * @see RejectReasons
	 */
	public RejectReasons findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find RejectReasons by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.RejectReasons}
	 * @throws Exception
	 *             the exception
	 * @see RejectReasons
	 */
	public List<RejectReasons> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load RejectReasons.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.RejectReasons}
	 * @throws Exception
	 *             the exception
	 */
	public List<RejectReasons> allRejectReasons(int first, int pageSize) throws Exception {
		return dao.allRejectReasons(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of RejectReasons for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the RejectReasons
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(RejectReasons.class);
	}

	/**
	 * Lazy load RejectReasons with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            RejectReasons class
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
	 * @return a list of {@link haj.com.entity.RejectReasons} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasons> allRejectReasons(Class<RejectReasons> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<RejectReasons>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of RejectReasons for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            RejectReasons class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the RejectReasons entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<RejectReasons> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by process.
	 *
	 * @param process
	 *            the process
	 * @return the list
	 */
	public List<RejectReasons> findByProcess(ConfigDocProcessEnum process) {
		return dao.findByProcess(process);
	}

	public List<RejectReasons> findByProcessAndSoftDelete(ConfigDocProcessEnum process, Boolean softDelete) {
		return dao.findByProcessAndSoftDelete(process, softDelete);
	}

	public List<RejectReasons> findByProcessSeniorManager(ConfigDocProcessEnum process, Boolean booleanValue) {
		return dao.findByProcessSeniorManager(process, booleanValue);
	}

	public List<RejectReasons> findByBooleans(ConfigDocProcessEnum process, Boolean forSeniorManager,
			Boolean forManager, Boolean forExecutive, Boolean forCrm) {
		return dao.findByBooleans(process, forSeniorManager, forManager, forExecutive, forCrm);
	}

	/**
	 * 
	 * 
	 * @param process
	 * @param booleanValue
	 * @return throws Exception
	 */
	public List<RejectReasons> findByProcessAndBooleanSelection(ConfigDocProcessEnum process, Boolean booleanValue)
			throws Exception {
		return dao.findByProcessAndBooleanSelection(process, booleanValue);
	}

	public List<RejectReasons> findByProcessAndBooleanSelectionAndSoftDelete(ConfigDocProcessEnum process,
			Boolean booleanValue, Boolean softDelete) throws Exception {
		return dao.findByProcessAndBooleanSelectionAndSoftDelete(process, booleanValue, softDelete);
	}

	public List<RejectReasons> locateReasonsSelectedByTargetKeyAndClass(Long targetKey, String targetClass)
			throws Exception {
		return dao.findLinkedByMultipleSelection(targetKey, targetClass);
	}

	public List<RejectReasons> locateReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		return dao.locateReasonsSelectedByTargetKeyClassAndProcess(targetKey, targetClass, process);
	}

	public List<RejectReasons> findByTargetKeyClassAndProcessAndResolveRejectDate(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		return dao.findByTargetKeyClassAndProcessAndResolveRejectDate(targetKey, targetClass, process);
	}

	public List<RejectReasons> locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(Long targetKey,
			String targetClass, ConfigDocProcessEnum process) throws Exception {
		return dao.locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(targetKey, targetClass, process);
	}

	public long countReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		return dao.countReasonsSelectedByTargetKeyClassAndProcess(targetKey, targetClass, process);
	}

	public String locateReasonsSelectedByTargetKeyClassAndProcessAndReturnInString(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		String rejectionReasons = "";
		// List<RejectReasons> rrList =
		// locateReasonsSelectedByTargetKeyClassAndProcess(targetKey,
		// targetClass, process);
		List<RejectReasons> rrList = new ArrayList<>();
		if (process == null) {
			rrList = locateReasonsSelectedByTargetKeyAndClass(targetKey, targetClass);
		} else {
			rrList = locateReasonsSelectedByTargetKeyClassAndProcess(targetKey, targetClass, process);
		}
		int count = 1;
		for (RejectReasons rejectReasons : rrList) {
			if (count == rrList.size()) {
				rejectionReasons += rejectReasons.getDescription();
			} else {
				rejectionReasons += rejectReasons.getDescription() + ", ";
			}
			count++;
		}
		return rejectionReasons;
	}

	public List<RejectReasons> locateReasonsForDgVerificationByVerificationIdAndLastestEntry(Long dgVerificationID, Boolean lastestEntry) throws Exception {
		return dao.locateReasonsForDgVerificationByVerificationIdAndLastestEntry(dgVerificationID, lastestEntry);
	}

	public List<RejectReasons> locateReasonsForDgVerificationByVerificationIdRoleAndLastestEntry(Long dgVerificationID, Long roleId, Boolean lastestEntry) throws Exception {
		return dao.locateReasonsForDgVerificationByVerificationIdRoleAndLastestEntry(dgVerificationID, roleId, lastestEntry);
	}

	public String locateReasonsForDgVerificationAndReturnInString(Long dgVerificationID, Boolean lastestEntry, Long roleId) throws Exception {
		String rejectionReasons = "";
		List<RejectReasons> rrList = new ArrayList<>();
		if (roleId == null) {
			rrList = locateReasonsForDgVerificationByVerificationIdAndLastestEntry(dgVerificationID, lastestEntry);
		} else {
			rrList = locateReasonsForDgVerificationByVerificationIdRoleAndLastestEntry(dgVerificationID, roleId, lastestEntry);
		}
		int count = 1;
		for (RejectReasons rejectReasons : rrList) {
			if (count == rrList.size()) {
				rejectionReasons += rejectReasons.getDescription();
			} else {
				rejectionReasons += rejectReasons.getDescription() + ", ";
			}
			count++;
		}
		return rejectionReasons;
	}

	public String convertToString(List<RejectReasons> rejectReasons) {
		String reason = "";
		int size = 0;
		for (RejectReasons r : rejectReasons) {
			size += 1;
			reason += r.getDescription();
			if (size == rejectReasons.size()) {
				reason += ".";
			} else {
				reason += ", ";
			}
		}
		return reason;
	}
}
