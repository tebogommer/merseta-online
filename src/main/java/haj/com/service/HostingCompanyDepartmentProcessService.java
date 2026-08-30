package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.HostingCompanyDepartmentProcessDAO;
import haj.com.entity.HostingCompanyDepartmentProcess;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.HostingCompanyProcess;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentProcessService.
 */
public class HostingCompanyDepartmentProcessService extends AbstractService {
	/** The dao. */
	private HostingCompanyDepartmentProcessDAO dao = new HostingCompanyDepartmentProcessDAO();

	/**
	 * Get all HostingCompanyDepartmentProcess.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentProcess}
	 * @throws Exception the exception
	 * @see   HostingCompanyDepartmentProcess
	 */
	public List<HostingCompanyDepartmentProcess> allHostingCompanyDepartmentProcess() throws Exception {
	  	return dao.allHostingCompanyDepartmentProcess();
	}


	/**
	 * Create or update HostingCompanyDepartmentProcess.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     HostingCompanyDepartmentProcess
	 */
	public void create(HostingCompanyDepartmentProcess entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  HostingCompanyDepartmentProcess.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	public void update(HostingCompanyDepartmentProcess entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  HostingCompanyDepartmentProcess.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	public void delete(HostingCompanyDepartmentProcess entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HostingCompanyDepartmentProcess}
	 * @throws Exception the exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	public HostingCompanyDepartmentProcess findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find HostingCompanyDepartmentProcess by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentProcess}
	 * @throws Exception the exception
	 * @see    HostingCompanyDepartmentProcess
	 */
	public List<HostingCompanyDepartmentProcess> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load HostingCompanyDepartmentProcess.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.HostingCompanyDepartmentProcess}
	 * @throws Exception the exception
	 */
	public List<HostingCompanyDepartmentProcess> allHostingCompanyDepartmentProcess(int first, int pageSize) throws Exception {
		return dao.allHostingCompanyDepartmentProcess(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of HostingCompanyDepartmentProcess for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the HostingCompanyDepartmentProcess
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(HostingCompanyDepartmentProcess.class);
	}
	
    /**
     * Lazy load HostingCompanyDepartmentProcess with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 HostingCompanyDepartmentProcess class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.HostingCompanyDepartmentProcess} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartmentProcess> allHostingCompanyDepartmentProcess(Class<HostingCompanyDepartmentProcess> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<HostingCompanyDepartmentProcess>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of HostingCompanyDepartmentProcess for lazy load with filters.
     *
     * @author TechFinium
     * @param entity HostingCompanyDepartmentProcess class
     * @param filters the filters
     * @return Number of rows in the HostingCompanyDepartmentProcess entity
     * @throws Exception the exception
     */	
	public int count(Class<HostingCompanyDepartmentProcess> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by department.
	 *
	 * @param hostingCompanyDepartments the hosting company departments
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyDepartmentProcess> findByDepartment(HostingCompanyDepartments hostingCompanyDepartments) throws Exception { 
		return dao.findByDepartment(hostingCompanyDepartments.getId());
	}
	
	/**
	 * Find not used.
	 *
	 * @param hostingCompanyDepartments the hosting company departments
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<HostingCompanyProcess> findNotUsed(HostingCompanyDepartments hostingCompanyDepartments) throws Exception { 
		return dao.findNotUsed(hostingCompanyDepartments.getId(), hostingCompanyDepartments.getHostingCompany().getId());
	}
}
