package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.ProcessRolesDAO;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.ProcessRoles;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessRolesService.
 */
public class ProcessRolesService extends AbstractService {
	/** The dao. */
	private ProcessRolesDAO dao = new ProcessRolesDAO();
	/* Instance */
	private static ProcessRolesService processRolesService;
	public static synchronized ProcessRolesService instance() {
		if (processRolesService == null) {
			processRolesService = new ProcessRolesService();
		}
		return processRolesService;
	}

	/**
	 * Get all ProcessRoles.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProcessRoles}
	 * @throws Exception
	 *             the exception
	 * @see ProcessRoles
	 */
	public List<ProcessRoles> allProcessRoles() throws Exception {
		return dao.allProcessRoles();
	}

	/**
	 * Create or update ProcessRoles.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProcessRoles
	 */
	public void create(ProcessRoles entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update ProcessRoles.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProcessRoles
	 */
	public void update(ProcessRoles entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Update ProcessRoles.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProcessRoles
	 */
	@SuppressWarnings("unchecked")
	public void update(List<ProcessRoles> entity) throws Exception {
		this.dao.updateBatch((List<IDataEntity>) (List<?>) entity);
	}

	/**
	 * Delete ProcessRoles.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see ProcessRoles
	 */
	public void delete(ProcessRoles entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.ProcessRoles}
	 * @throws Exception
	 *             the exception
	 * @see ProcessRoles
	 */
	public ProcessRoles findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find ProcessRoles by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.ProcessRoles}
	 * @throws Exception
	 *             the exception
	 * @see ProcessRoles
	 */
	public List<ProcessRoles> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ProcessRoles.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.ProcessRoles}
	 * @throws Exception
	 *             the exception
	 */
	public List<ProcessRoles> allProcessRoles(int first, int pageSize) throws Exception {
		return dao.allProcessRoles(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ProcessRoles for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ProcessRoles
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(ProcessRoles.class);
	}

	/**
	 * Lazy load ProcessRoles with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            ProcessRoles class
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
	 * @return a list of {@link haj.com.entity.ProcessRoles} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessRoles> allProcessRoles(Class<ProcessRoles> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<ProcessRoles>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Lazy load ProcessRoles with pagination, filter, sorting.
	 *
	 * @author TechFinium
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
	 * @return a list of {@link haj.com.entity.ProcessRoles} containing data
	 * @throws Exception
	 *             the exception
	 */
	public List<ProcessRoles> allProcessRoles(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return dao.allProcessRoles(first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of ProcessRoles for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            ProcessRoles class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the ProcessRoles entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<ProcessRoles> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * All process roles.
	 *
	 * @param hostingCompanyProcess
	 *            the hosting company process
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ProcessRoles> allProcessRoles(HostingCompanyProcess hostingCompanyProcess) throws Exception {
		return dao.allProcessRoles(hostingCompanyProcess);
	}
	
	public List<ProcessRoles> findByConfigDoc(ConfigDocProcessEnum workflowProcess) throws Exception {
		return dao.findByConfigDoc(workflowProcess);
	}

	/**
	 * First process roles.
	 *
	 * @param hostingCompanyProcess
	 *            the hosting company process
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ProcessRoles> firstProcessRoles(HostingCompanyProcess hostingCompanyProcess) throws Exception {
		return dao.firstProcessRoles(hostingCompanyProcess);
	}

	/**
	 * Find next process roles.
	 *
	 * @param processRoles
	 *            the process roles
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ProcessRoles> findNextProcessRoles(ProcessRoles processRoles) throws Exception {
		return dao.findNextProcessRoles(processRoles);
	}

	/**
	 * Find previous process roles.
	 *
	 * @param processRoles
	 *            the process roles
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ProcessRoles> findPreviousProcessRoles(ProcessRoles processRoles) throws Exception {
		return dao.findPreviousProcessRoles(processRoles);
	}
	
	public List<ProcessRoles> findPreviousProcessRoles(ProcessRoles processRoles, Integer roleOrder) throws Exception {
		return dao.findPreviousProcessRoles(processRoles,roleOrder);
	}

	/**
	 * Find previous process roles count.
	 *
	 * @param processRoles the process roles
	 * @return the long
	 * @throws Exception the exception
	 */
	public long findPreviousProcessRolesCount(ProcessRoles processRoles) throws Exception {
		return dao.findPreviousProcessRolesCount(processRoles);
	}

	/**
	 * Last process roles.
	 *
	 * @param hostingCompanyProcess
	 *            the hosting company process
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ProcessRoles> lastProcessRoles(HostingCompanyProcess hostingCompanyProcess) throws Exception {
		return dao.lastProcessRoles(hostingCompanyProcess);
	}

	public List<ProcessRoles> findByPosition(HostingCompanyProcess hostingCompanyProcess, int position) throws Exception {
		return dao.findByPosition(hostingCompanyProcess, position);
	}
}
