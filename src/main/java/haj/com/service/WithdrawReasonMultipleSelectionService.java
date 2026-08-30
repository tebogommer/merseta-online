package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WithdrawReasonMultipleSelectionDAO;
import haj.com.entity.WithdrawReasonMultipleSelection;
import haj.com.entity.WithdrawReasonMultipleSelectionHistory;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.WithdrawReasons;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WithdrawReasonMultipleSelectionService extends AbstractService {
	/** The dao. */
	private WithdrawReasonMultipleSelectionDAO dao = new WithdrawReasonMultipleSelectionDAO();

	private static WithdrawReasonMultipleSelectionService WithdrawReasonMultipleSelectionService;

	public static synchronized WithdrawReasonMultipleSelectionService instance() {
		if (WithdrawReasonMultipleSelectionService == null) {
			WithdrawReasonMultipleSelectionService = new WithdrawReasonMultipleSelectionService();
		}
		return WithdrawReasonMultipleSelectionService;
	}
	
	/**
	 * Get all WithdrawReasonMultipleSelection
	 * 
	 * @author TechFinium
	 * @see WithdrawReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             the exception
	 */
	public List<WithdrawReasonMultipleSelection> allWithdrawReasonMultipleSelection() throws Exception {
		return dao.allWithdrawReasonMultipleSelection();
	}

	/**
	 * Create or update WithdrawReasonMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasonMultipleSelection
	 */
	public void create(WithdrawReasonMultipleSelection entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update WithdrawReasonMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasonMultipleSelection
	 */
	public void update(WithdrawReasonMultipleSelection entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WithdrawReasonMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasonMultipleSelection
	 */
	public void delete(WithdrawReasonMultipleSelection entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasonMultipleSelection
	 */
	public WithdrawReasonMultipleSelection findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WithdrawReasonMultipleSelection by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             the exception
	 * @see WithdrawReasonMultipleSelection
	 */
	public List<WithdrawReasonMultipleSelection> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WithdrawReasonMultipleSelection
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 * @throws Exception
	 *             the exception
	 */
	public List<WithdrawReasonMultipleSelection> allWithdrawReasonMultipleSelection(int first, int pageSize)
			throws Exception {
		return dao.allWithdrawReasonMultipleSelection(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WithdrawReasonMultipleSelection for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the WithdrawReasonMultipleSelection
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WithdrawReasonMultipleSelection.class);
	}

	/**
	 * Lazy load WithdrawReasonMultipleSelection with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            WithdrawReasonMultipleSelection class
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
	 * @return a list of {@link haj.com.entity.WithdrawReasonMultipleSelection}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WithdrawReasonMultipleSelection> allWithdrawReasonMultipleSelection(
			Class<WithdrawReasonMultipleSelection> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<WithdrawReasonMultipleSelection>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of WithdrawReasonMultipleSelection for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            WithdrawReasonMultipleSelection class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WithdrawReasonMultipleSelection entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WithdrawReasonMultipleSelection> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public List<WithdrawReasonMultipleSelection> findByTargetKeyAndClassName(Long targetKey, String className) throws Exception {
		return dao.findByTargetKeyAndClassName(targetKey, className);
	}
	
	public List<WithdrawReasonMultipleSelection> findByTargetKeyClassNameAndProcess(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		return dao.findByTargetKeyClassNameAndProcess(targetKey, className, process);
	}
	
	public List<WithdrawReasonMultipleSelectionHistory> findHistoryByTargetKeyClassNameAndProcess(Long target_key, String target_class, ConfigDocProcessEnum for_process) throws Exception {
		return dao.findHistoryByTargetKeyClassNameAndProcess(target_key, target_class, for_process);
	}

	/**
	 * Removes old links and creates new links for rejected reasons
	 * @param id
	 * @param name
	 * @param WithdrawReasonList
	 * @return entries
	 */
	public List<WithdrawReasonMultipleSelection> removeCreateLinks(Long targetKey, String className, List<WithdrawReasons> WithdrawReasonList, Users sessionUser, ConfigDocProcessEnum process) throws Exception{
		// delete all old entries by target key and class name
		System.out.println("3333333333333:: WithdrawReasonList.size():: "+WithdrawReasonList.size());
		clearEntries(targetKey, className, process);
		
		List<WithdrawReasonMultipleSelection> entries = new ArrayList<WithdrawReasonMultipleSelection>();
		for (WithdrawReasons WithdrawReason : WithdrawReasonList) {
			WithdrawReasonMultipleSelection rms = new WithdrawReasonMultipleSelection(targetKey, className, WithdrawReason, sessionUser, process);
			entries.add(rms);
		}
		System.out.println("444444444444:: entries.size():: "+entries.size());
		return entries;
	}

	public void clearEntries(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		System.out.println("aaaaaaaaaaaaaaaa:: targetKey:: "+targetKey);
		System.out.println("bbbbbbbbbbbbbb:: className:: "+className);
		System.out.println("ccccccccccccccc:: process:: "+process);
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		entityList.addAll(findByTargetKeyClassNameAndProcess(targetKey, className, process));
		if (entityList.size() != 0) {
			dao.deleteBatch(entityList);
		}
		entityList = null;
	}
	
	public void removeCreateLinksOnDb(Long targetKey, String className, List<WithdrawReasons> WithdrawReasonList, Users sessionUser, ConfigDocProcessEnum process) throws Exception{
		// delete all old entries by target key and class name
		clearEntries(targetKey, className, process);
		
		List<WithdrawReasonMultipleSelection> entries = new ArrayList<WithdrawReasonMultipleSelection>();
		for (WithdrawReasons WithdrawReason : WithdrawReasonList) {
			WithdrawReasonMultipleSelection rms = new WithdrawReasonMultipleSelection(targetKey, className, WithdrawReason, sessionUser, process);
			entries.add(rms);
		}
		List<IDataEntity> createList = new ArrayList<>();
		if (entries.size() != 0) {
			createList.addAll(entries);
		}
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
	}
	
	public void removeCreateLinksOnDbWithTaskId(Long targetKey, String className, List<WithdrawReasons> WithdrawReasonList, Users sessionUser, ConfigDocProcessEnum process, Long taskId) throws Exception{
		// delete all old entries by target key and class name
		clearEntries(targetKey, className, process);
		
		List<WithdrawReasonMultipleSelection> entries = new ArrayList<>();
		if (WithdrawReasonList != null && !WithdrawReasonList.isEmpty()) {
			for (WithdrawReasons WithdrawReason : WithdrawReasonList) {
				WithdrawReasonMultipleSelection rms = new WithdrawReasonMultipleSelection(targetKey, className, WithdrawReason, sessionUser, process);
				if (taskId != null) {
					rms.setTaskId(taskId);
				}
				entries.add(rms);
			}
		}
		List<IDataEntity> createList = new ArrayList<>();
		if (!entries.isEmpty()) {
			createList.addAll(entries);
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
	}
	
	public void clearEntriesNoProcess(Long targetKey, String className) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		entityList.addAll(findByTargetKeyAndClassName(targetKey, className));
		if (entityList.size() != 0) {
			dao.deleteBatch(entityList);
		}
		entityList = null;
	}
	
	public List<WithdrawReasonMultipleSelection> removeCreateLinksNoProcess(Long targetKey, String className, List<WithdrawReasons> WithdrawReasonList, Users sessionUser) throws Exception{
		// delete all old entries by target key and class name
		clearEntriesNoProcess(targetKey, className);
		
		List<WithdrawReasonMultipleSelection> entries = new ArrayList<WithdrawReasonMultipleSelection>();
		for (WithdrawReasons withdrawReason : WithdrawReasonList) {
			WithdrawReasonMultipleSelection rms = new WithdrawReasonMultipleSelection(targetKey, className, withdrawReason, sessionUser, null);
			//WithdrawReasonMultipleSelection rms = new WithdrawReasonMultipleSelection(targetKey, className, WithdrawReason, sessionUser, null);
			entries.add(rms);
		}
		return entries;
	}
	
	public void removeCreateLinksOnDbNoProcess(Long targetKey, String className, List<WithdrawReasons> WithdrawReasonList, Users sessionUser) throws Exception{
		clearEntriesNoProcess(targetKey, className);
		List<WithdrawReasonMultipleSelection> entries = new ArrayList<WithdrawReasonMultipleSelection>();
		for (WithdrawReasons withdrawReason : WithdrawReasonList) {
			WithdrawReasonMultipleSelection rms = new WithdrawReasonMultipleSelection(targetKey, className, withdrawReason, sessionUser, null);
			entries.add(rms);
		}
		List<IDataEntity> createList = new ArrayList<>();
		if (entries.size() != 0) {
			createList.addAll(entries);
		}
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
	}
}
