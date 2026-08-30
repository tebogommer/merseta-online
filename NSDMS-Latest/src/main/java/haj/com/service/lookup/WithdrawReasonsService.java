package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.WithdrawReasonsDAO;
import haj.com.entity.CompanyLearners;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Users;
import haj.com.entity.WithdrawReasonMultipleSelection;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.WithdrawReasons;
import haj.com.framework.AbstractService;
import haj.com.service.RejectReasonMultipleSelectionService;
import haj.com.service.WithdrawReasonMultipleSelectionService;

// TODO: Auto-generated Javadoc
/**
 * The Class WithdrawReasonsService.
 */
public class WithdrawReasonsService extends AbstractService {

	/** The dao. */
	private WithdrawReasonsDAO dao = new WithdrawReasonsDAO();

	/**
	 * Get all WithdrawReasons.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WithdrawReasons}
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasons
	 */
	public List<WithdrawReasons> allWithdrawReasons() throws Exception {
		return dao.allWithdrawReasons();
	}

	/**
	 * Create or update WithdrawReasons.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasons
	 */
	public void create(WithdrawReasons entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update WithdrawReasons.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasons
	 */
	public void update(WithdrawReasons entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WithdrawReasons.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasons
	 */
	public void delete(WithdrawReasons entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WithdrawReasons}
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasons
	 */
	public WithdrawReasons findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WithdrawReasons by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WithdrawReasons}
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasons
	 */
	public List<WithdrawReasons> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WithdrawReasons.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WithdrawReasons}
	 * @throws Exception
	 *             the exception
	 */
	public List<WithdrawReasons> allWithdrawReasons(int first, int pageSize) throws Exception {
		return dao.allWithdrawReasons(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WithdrawReasons for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the WithdrawReasons
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WithdrawReasons.class);
	}

	/**
	 * Lazy load WithdrawReasons with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            WithdrawReasons class
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
	 * @return a list of {@link haj.com.entity.WithdrawReasons} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasons> allWithdrawReasons(Class<WithdrawReasons> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WithdrawReasons>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of WithdrawReasons for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            WithdrawReasons class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WithdrawReasons entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WithdrawReasons> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by process.
	 *
	 * @param process
	 *            the process
	 * @return the list
	 */
	public List<WithdrawReasons> findByProcess(ConfigDocProcessEnum process) {
		return dao.findByProcess(process);
	}

	public List<WithdrawReasons> findByProcessAndSoftDelete(ConfigDocProcessEnum process, Boolean softDelete) {
		return dao.findByProcessAndSoftDelete(process, softDelete);
	}

	public List<WithdrawReasons> findByProcessSeniorManager(ConfigDocProcessEnum process, Boolean booleanValue) {
		return dao.findByProcessSeniorManager(process, booleanValue);
	}

	public List<WithdrawReasons> findByBooleans(ConfigDocProcessEnum process, Boolean forSeniorManager,
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
	public List<WithdrawReasons> findByProcessAndBooleanSelection(ConfigDocProcessEnum process, Boolean booleanValue)
			throws Exception {
		return dao.findByProcessAndBooleanSelection(process, booleanValue);
	}

	public List<WithdrawReasons> findByProcessAndBooleanSelectionAndSoftDelete(ConfigDocProcessEnum process,
			Boolean booleanValue, Boolean softDelete) throws Exception {
		return dao.findByProcessAndBooleanSelectionAndSoftDelete(process, booleanValue, softDelete);
	}

	public List<WithdrawReasons> locateReasonsSelectedByTargetKeyAndClass(Long targetKey, String targetClass)
			throws Exception {
		return dao.findLinkedByMultipleSelection(targetKey, targetClass);
	}

	public List<WithdrawReasons> locateReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		return dao.locateReasonsSelectedByTargetKeyClassAndProcess(targetKey, targetClass, process);
	}

	public List<WithdrawReasons> findByTargetKeyClassAndProcessAndResolveWithdrawDate(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		return dao.findByTargetKeyClassAndProcessAndResolveWithdrawDate(targetKey, targetClass, process);
	}

	public List<WithdrawReasons> locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(Long targetKey,
			String targetClass, ConfigDocProcessEnum process) throws Exception {
		return dao.locateReasonsSelectedByTargetKeyClassAndProcessResolveAditionalInfo(targetKey, targetClass, process);
	}

	public long countReasonsSelectedByTargetKeyClassAndProcess(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		return dao.countReasonsSelectedByTargetKeyClassAndProcess(targetKey, targetClass, process);
	}

	public String locateReasonsSelectedByTargetKeyClassAndProcessAndReturnInString(Long targetKey, String targetClass,
			ConfigDocProcessEnum process) throws Exception {
		String WithdrawionReasons = "";
		// List<WithdrawReasons> rrList =
		// locateReasonsSelectedByTargetKeyClassAndProcess(targetKey,
		// targetClass, process);
		List<WithdrawReasons> rrList = new ArrayList<>();
		if (process == null) {
			rrList = locateReasonsSelectedByTargetKeyAndClass(targetKey, targetClass);
		} else {
			rrList = locateReasonsSelectedByTargetKeyClassAndProcess(targetKey, targetClass, process);
		}
		int count = 1;
		for (WithdrawReasons WithdrawReasons : rrList) {
			if (count == rrList.size()) {
				WithdrawionReasons += WithdrawReasons.getDescription();
			} else {
				WithdrawionReasons += WithdrawReasons.getDescription() + ", ";
			}
			count++;
		}
		return WithdrawionReasons;
	}

	public List<WithdrawReasons> locateReasonsForDgVerificationByVerificationIdAndLastestEntry(Long dgVerificationID, Boolean lastestEntry) throws Exception {
		return dao.locateReasonsForDgVerificationByVerificationIdAndLastestEntry(dgVerificationID, lastestEntry);
	}

	public List<WithdrawReasons> locateReasonsForDgVerificationByVerificationIdRoleAndLastestEntry(Long dgVerificationID, Long roleId, Boolean lastestEntry) throws Exception {
		return dao.locateReasonsForDgVerificationByVerificationIdRoleAndLastestEntry(dgVerificationID, roleId, lastestEntry);
	}

	public String locateReasonsForDgVerificationAndReturnInString(Long dgVerificationID, Boolean lastestEntry, Long roleId) throws Exception {
		String WithdrawionReasons = "";
		List<WithdrawReasons> rrList = new ArrayList<>();
		if (roleId == null) {
			rrList = locateReasonsForDgVerificationByVerificationIdAndLastestEntry(dgVerificationID, lastestEntry);
		} else {
			rrList = locateReasonsForDgVerificationByVerificationIdRoleAndLastestEntry(dgVerificationID, roleId, lastestEntry);
		}
		int count = 1;
		for (WithdrawReasons WithdrawReasons : rrList) {
			if (count == rrList.size()) {
				WithdrawionReasons += WithdrawReasons.getDescription();
			} else {
				WithdrawionReasons += WithdrawReasons.getDescription() + ", ";
			}
			count++;
		}
		return WithdrawionReasons;
	}

	public String convertToString(List<WithdrawReasons> WithdrawReasons) {
		String reason = "";
		int size = 0;
		for (WithdrawReasons r : WithdrawReasons) {
			size += 1;
			reason += r.getDescription();
			if (size == WithdrawReasons.size()) {
				reason += ".";
			} else {
				reason += ", ";
			}
		}
		return reason;
	}

	public void createWithrawalReasons(CompanyLearners companyLearners, List<WithdrawReasons> withdrawRejectReason, Users user) throws Exception {
		List<WithdrawReasonMultipleSelection> rrmList = WithdrawReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), withdrawRejectReason, user, ConfigDocProcessEnum.Learner);
		System.out.println("222222:: rrmList.size():: "+rrmList.size());
		for (WithdrawReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				WithdrawReasonMultipleSelectionService.instance().create(rrm);
			}
		}
	}
}
