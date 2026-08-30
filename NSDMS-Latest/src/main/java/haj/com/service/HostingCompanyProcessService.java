package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.HostingCompanyProcessDAO;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyProcessService.
 */
public class HostingCompanyProcessService extends AbstractService {
	/** The dao. */
	private HostingCompanyProcessDAO dao = new HostingCompanyProcessDAO();

	/**
	 * Get all HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.HostingCompanyProcess}
	 * @throws Exception the exception
	 * @see   HostingCompanyProcess
	 */
	public List<HostingCompanyProcess> allHostingCompanyProcess() throws Exception {
	  	return dao.allHostingCompanyProcess();
	}


	/**
	 * Create or update HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     HostingCompanyProcess
	 */
	public void create(HostingCompanyProcess entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HostingCompanyProcess
	 */
	public void update(HostingCompanyProcess entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HostingCompanyProcess
	 */
	public void delete(HostingCompanyProcess entity) throws Exception  {
		this.dao.delete(entity);
	}
	
	/**
	 * Delete  HostingCompanyProcess.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    HostingCompanyProcess
	 */
	public void deleteWithPrevious(HostingCompanyProcess entity) throws Exception  {
		HostingCompanyProcess previous = findPreviousProcess(entity);
		if (previous != null) {
			previous.setNextProcess(null);
			update(previous);
		}
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.HostingCompanyProcess}
	 * @throws Exception the exception
	 * @see    HostingCompanyProcess
	 */
	public HostingCompanyProcess findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find HostingCompanyProcess by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.HostingCompanyProcess}
	 * @throws Exception the exception
	 * @see    HostingCompanyProcess
	 */
	public List<HostingCompanyProcess> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load HostingCompanyProcess.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.HostingCompanyProcess}
	 * @throws Exception the exception
	 */
	public List<HostingCompanyProcess> allHostingCompanyProcess(int first, int pageSize) throws Exception {
		return dao.allHostingCompanyProcess(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of HostingCompanyProcess for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the HostingCompanyProcess
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(HostingCompanyProcess.class);
	}
	
    /**
     * Lazy load HostingCompanyProcess with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 HostingCompanyProcess class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.HostingCompanyProcess} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<HostingCompanyProcess> allHostingCompanyProcess(Class<HostingCompanyProcess> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<HostingCompanyProcess>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of HostingCompanyProcess for lazy load with filters.
     *
     * @author TechFinium
     * @param entity HostingCompanyProcess class
     * @param filters the filters
     * @return Number of rows in the HostingCompanyProcess entity
     * @throws Exception the exception
     */	
	public int count(Class<HostingCompanyProcess> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * All hosting company process.
	 *
	 * @param hostingCompany
	 *            the hosting company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<HostingCompanyProcess> allHostingCompanyProcess(HostingCompany hostingCompany) throws Exception {
	  	return dao.allHostingCompanyProcess(hostingCompany);
	}
	
	/**
	 * All hosting company process.
	 *
	 * @param hostingCompany
	 *            the hosting company
	 * @param processEnum
	 *            the process enum
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public HostingCompanyProcess allHostingCompanyProcess(HostingCompany hostingCompany, ConfigDocProcessEnum processEnum) throws Exception {
		return dao.allHostingCompanyProcess(hostingCompany, processEnum);
	}
	
	/**
	 * Find previous process.
	 *
	 * @param nextProcess the next process
	 * @return the hosting company process
	 * @throws Exception the exception
	 */
	public HostingCompanyProcess findPreviousProcess(HostingCompanyProcess nextProcess) throws Exception {
		return dao.findPreviousProcess(nextProcess);
	}
}
