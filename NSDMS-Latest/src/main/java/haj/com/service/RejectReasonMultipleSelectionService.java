package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.RejectReasonMultipleSelectionDAO;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.RejectReasonMultipleSelectionHistory;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class RejectReasonMultipleSelectionService extends AbstractService {
	/** The dao. */
	private RejectReasonMultipleSelectionDAO dao = new RejectReasonMultipleSelectionDAO();

	private static RejectReasonMultipleSelectionService rejectReasonMultipleSelectionService;

	public static synchronized RejectReasonMultipleSelectionService instance() {
		if (rejectReasonMultipleSelectionService == null) {
			rejectReasonMultipleSelectionService = new RejectReasonMultipleSelectionService();
		}
		return rejectReasonMultipleSelectionService;
	}
	
	/**
	 * Get all RejectReasonMultipleSelection
	 * 
	 * @author TechFinium
	 * @see RejectReasonMultipleSelection
	 * @return a list of {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             the exception
	 */
	public List<RejectReasonMultipleSelection> allRejectReasonMultipleSelection() throws Exception {
		return dao.allRejectReasonMultipleSelection();
	}

	/**
	 * Create or update RejectReasonMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonMultipleSelection
	 */
	public void create(RejectReasonMultipleSelection entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update RejectReasonMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonMultipleSelection
	 */
	public void update(RejectReasonMultipleSelection entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete RejectReasonMultipleSelection.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonMultipleSelection
	 */
	public void delete(RejectReasonMultipleSelection entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonMultipleSelection
	 */
	public RejectReasonMultipleSelection findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find RejectReasonMultipleSelection by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             the exception
	 * @see RejectReasonMultipleSelection
	 */
	public List<RejectReasonMultipleSelection> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load RejectReasonMultipleSelection
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.RejectReasonMultipleSelection}
	 * @throws Exception
	 *             the exception
	 */
	public List<RejectReasonMultipleSelection> allRejectReasonMultipleSelection(int first, int pageSize)
			throws Exception {
		return dao.allRejectReasonMultipleSelection(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of RejectReasonMultipleSelection for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the RejectReasonMultipleSelection
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(RejectReasonMultipleSelection.class);
	}

	/**
	 * Lazy load RejectReasonMultipleSelection with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            RejectReasonMultipleSelection class
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
	 * @return a list of {@link haj.com.entity.RejectReasonMultipleSelection}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<RejectReasonMultipleSelection> allRejectReasonMultipleSelection(
			Class<RejectReasonMultipleSelection> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<RejectReasonMultipleSelection>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of RejectReasonMultipleSelection for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            RejectReasonMultipleSelection class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the RejectReasonMultipleSelection entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<RejectReasonMultipleSelection> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public List<RejectReasonMultipleSelection> findByTargetKeyAndClassName(Long targetKey, String className) throws Exception {
		return dao.findByTargetKeyAndClassName(targetKey, className);
	}
	
	public List<RejectReasonMultipleSelection> findByTargetKeyClassNameAndProcess(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		return dao.findByTargetKeyClassNameAndProcess(targetKey, className, process);
	}
	
	public List<RejectReasonMultipleSelectionHistory> findHistoryByTargetKeyClassNameAndProcess(Long target_key, String target_class, ConfigDocProcessEnum for_process) throws Exception {
		return dao.findHistoryByTargetKeyClassNameAndProcess(target_key, target_class, for_process);
	}

	/**
	 * Removes old links and creates new links for rejected reasons
	 * @param id
	 * @param name
	 * @param rejectReasonsList
	 * @return entries
	 */
	public List<RejectReasonMultipleSelection> removeCreateLinks(Long targetKey, String className, List<RejectReasons> rejectReasonsList, Users sessionUser, ConfigDocProcessEnum process) throws Exception{
		// delete all old entries by target key and class name
		clearEntries(targetKey, className, process);
		
		List<RejectReasonMultipleSelection> entries = new ArrayList<RejectReasonMultipleSelection>();
		for (RejectReasons rejectReasons : rejectReasonsList) {
			RejectReasonMultipleSelection rms = new RejectReasonMultipleSelection(targetKey, className, rejectReasons, sessionUser, process);
			entries.add(rms);
		}
		return entries;
	}

	public List<RejectReasonMultipleSelection> removeCreateLinks(Long targetKey, String className, List<RejectReasons> rejectReasonsList, Users sessionUser, ConfigDocProcessEnum process, String additionalInformation) throws Exception{
		// delete all old entries by target key and class name
		clearEntries(targetKey, className, process);
		
		List<RejectReasonMultipleSelection> entries = new ArrayList<RejectReasonMultipleSelection>();
		for (RejectReasons rejectReasons : rejectReasonsList) {
			RejectReasonMultipleSelection rms = new RejectReasonMultipleSelection(targetKey, className, rejectReasons, sessionUser, process, additionalInformation);
			entries.add(rms);
		}
		return entries;
	}
	
	public void clearEntries(Long targetKey, String className, ConfigDocProcessEnum process) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		entityList.addAll(findByTargetKeyClassNameAndProcess(targetKey, className, process));
		if (entityList.size() != 0) {
			dao.deleteBatch(entityList);
		}
		entityList = null;
	}
	
	public void removeCreateLinksOnDb(Long targetKey, String className, List<RejectReasons> rejectReasonsList, Users sessionUser, ConfigDocProcessEnum process) throws Exception{
		// delete all old entries by target key and class name
		clearEntries(targetKey, className, process);
		
		List<RejectReasonMultipleSelection> entries = new ArrayList<RejectReasonMultipleSelection>();
		for (RejectReasons rejectReasons : rejectReasonsList) {
			RejectReasonMultipleSelection rms = new RejectReasonMultipleSelection(targetKey, className, rejectReasons, sessionUser, process);
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
	
	public void removeCreateLinksOnDbWithTaskId(Long targetKey, String className, List<RejectReasons> rejectReasonsList, Users sessionUser, ConfigDocProcessEnum process, Long taskId) throws Exception{
		// delete all old entries by target key and class name
		clearEntries(targetKey, className, process);
		
		List<RejectReasonMultipleSelection> entries = new ArrayList<>();
		if (rejectReasonsList != null && !rejectReasonsList.isEmpty()) {
			for (RejectReasons rejectReasons : rejectReasonsList) {
				RejectReasonMultipleSelection rms = new RejectReasonMultipleSelection(targetKey, className, rejectReasons, sessionUser, process);
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
	
	public List<RejectReasonMultipleSelection> removeCreateLinksNoProcess(Long targetKey, String className, List<RejectReasons> rejectReasonsList, Users sessionUser) throws Exception{
		// delete all old entries by target key and class name
		clearEntriesNoProcess(targetKey, className);
		
		List<RejectReasonMultipleSelection> entries = new ArrayList<RejectReasonMultipleSelection>();
		for (RejectReasons rejectReasons : rejectReasonsList) {
			RejectReasonMultipleSelection rms = new RejectReasonMultipleSelection(targetKey, className, rejectReasons, sessionUser, null);
			entries.add(rms);
		}
		return entries;
	}
	
	public void removeCreateLinksOnDbNoProcess(Long targetKey, String className, List<RejectReasons> rejectReasonsList, Users sessionUser) throws Exception{
		clearEntriesNoProcess(targetKey, className);
		List<RejectReasonMultipleSelection> entries = new ArrayList<RejectReasonMultipleSelection>();
		for (RejectReasons rejectReasons : rejectReasonsList) {
			RejectReasonMultipleSelection rms = new RejectReasonMultipleSelection(targetKey, className, rejectReasons, sessionUser, null);
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
