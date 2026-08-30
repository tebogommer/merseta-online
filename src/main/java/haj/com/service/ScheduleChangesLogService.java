package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.ScheduleChangesLogDAO;
import haj.com.entity.Company;
import haj.com.entity.ScheduleChangesLog;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;

public class ScheduleChangesLogService extends AbstractService {

	/** The dao. */

	private ScheduleChangesLogDAO dao = new ScheduleChangesLogDAO();

	/** Instance Of Service Level */

	private static ScheduleChangesLogService scheduleChangesLogService;

	public static synchronized ScheduleChangesLogService instance() {
		if (scheduleChangesLogService == null) {
			scheduleChangesLogService = new ScheduleChangesLogService();
		}
		return scheduleChangesLogService;
	}

	/**
	 * Get all ScheduleChangesLog
	 * @author TechFinium
	 * @see ScheduleChangesLog
	 * @return a list of {@link haj.com.entity.ScheduleChangesLog}
	 * @throws Exception
	 * the exception
	 */
	public List<ScheduleChangesLog> allScheduleChangesLog() throws Exception {
		return dao.allScheduleChangesLog();
	}

	/**
	 * Create or update ScheduleChangesLog.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see ScheduleChangesLog
	 */
	public void create(ScheduleChangesLog entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update ScheduleChangesLog.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see ScheduleChangesLog
	 */
	public void update(ScheduleChangesLog entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ScheduleChangesLog.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see ScheduleChangesLog
	 */
	public void delete(ScheduleChangesLog entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.ScheduleChangesLog}
	 * @throws Exception
	 * the exception
	 * @see ScheduleChangesLog
	 */
	public ScheduleChangesLog findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public Integer countBytargetClassandTargetKey(String targetClass, Long targetKey) throws Exception {
		return dao.countBytargetClassandTargetKey(targetClass, targetKey);
	}

	public ScheduleChangesLog findBytargetClassandTargetKey(String targetClass, Long targetKey) throws Exception {
		return dao.findBytargetClassandTargetKey(targetClass, targetKey);
	}

	public List<ScheduleChangesLog> findBytargetClassandTargetKeyList(String targetClass, Long targetKey) throws Exception {
		return dao.findBytargetClassandTargetKeyList(targetClass, targetKey);
	}

	public ScheduleChangesLog findBytargetClassandTargetKeyListReturnFirst(String targetClass, Long targetKey) throws Exception {
		List<ScheduleChangesLog> list = findBytargetClassandTargetKeyList(targetClass, targetKey);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * Find ScheduleChangesLog by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.ScheduleChangesLog}
	 * @throws Exception
	 * the exception
	 * @see ScheduleChangesLog
	 */
	public List<ScheduleChangesLog> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ScheduleChangesLog
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.ScheduleChangesLog}
	 * @throws Exception
	 * the exception
	 */
	public List<ScheduleChangesLog> allScheduleChangesLog(int first, int pageSize) throws Exception {
		return dao.allScheduleChangesLog(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ScheduleChangesLog for lazy load
	 * @author TechFinium
	 * @return Number of rows in the ScheduleChangesLog
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(ScheduleChangesLog.class);
	}

	/**
	 * Lazy load ScheduleChangesLog with pagination, filter, sorting
	 * @author TechFinium
	 * @param class1
	 * ScheduleChangesLog class
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
	 * @return a list of {@link haj.com.entity.ScheduleChangesLog} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleChangesLog> allScheduleChangesLog(Class<ScheduleChangesLog> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ScheduleChangesLog>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of ScheduleChangesLog for lazy load with filters
	 * @author TechFinium
	 * @param entity
	 * ScheduleChangesLog class
	 * @param filters
	 * the filters
	 * @return Number of rows in the ScheduleChangesLog entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<ScheduleChangesLog> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void addNewEntry(Company company, String targetClass, Long targetKey, String information, boolean manualIntervention) throws Exception {
		ScheduleChangesLog scheduleChangesLog = new ScheduleChangesLog(targetClass, targetKey, information, manualIntervention);
		if (company != null) {
			scheduleChangesLog.setCompany(company);
		}
		create(scheduleChangesLog);
	}

	public void addNewEntry(Company company, Users user, String targetClass, Long targetKey, String information, boolean manualIntervention) throws Exception {
		ScheduleChangesLog scheduleChangesLog = new ScheduleChangesLog(targetClass, targetKey, information, manualIntervention);
		if (company != null) {
			scheduleChangesLog.setCompany(company);
		}
		if (user != null) {
			scheduleChangesLog.setForUser(user);
		}
		create(scheduleChangesLog);
	}

	public ScheduleChangesLog addNewEntryWithoutCreate(Company company, Users user, String targetClass, Long targetKey, String information, boolean manualIntervention) throws Exception {
		ScheduleChangesLog scheduleChangesLog = new ScheduleChangesLog(targetClass, targetKey, information, manualIntervention);
		if (company != null) {
			scheduleChangesLog.setCompany(company);
		}
		if (user != null) {
			scheduleChangesLog.setForUser(user);
		}
		return scheduleChangesLog;
	}
}
