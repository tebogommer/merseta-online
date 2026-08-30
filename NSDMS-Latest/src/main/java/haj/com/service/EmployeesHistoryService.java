package haj.com.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.EmployeesHistoryDAO;
import haj.com.entity.Company;
import haj.com.entity.Employees;
import haj.com.entity.EmployeesHistory;
import haj.com.entity.Wsp;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesHistoryService.
 */
public class EmployeesHistoryService extends AbstractService {
	/** The dao. */
	private EmployeesHistoryDAO dao = new EmployeesHistoryDAO();
	
	
	private static EmployeesHistoryService employeesHistoryService=null;
	/**
	 * The Instance
	 * @return EmployeesHistoryService
	 * */
	public static synchronized EmployeesHistoryService instance()
	{
		if(employeesHistoryService==null)
		{
			employeesHistoryService=new EmployeesHistoryService();
		}
		return employeesHistoryService;
	}

	/**
	 * Get all EmployeesHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             the exception
	 * @see EmployeesHistory
	 */
	public List<EmployeesHistory> allEmployeesHistory() throws Exception {
		return dao.allEmployeesHistory();
	}

	/**
	 * Create or update EmployeesHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see EmployeesHistory
	 */
	public void create(EmployeesHistory entity) throws Exception {
		// validate(entity);
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}
	


	/**
	 * Creates the.
	 *
	 * @param EmployeesHistory
	 *            the EmployeesHistory
	 * @throws Exception
	 *             the exception
	 */
	public void create(List<IDataEntity> EmployeesHistory) throws Exception {
		for (IDataEntity entity : EmployeesHistory) {
			this.dao.create(entity);
		}
	}

	

	/**
	 * Validate.
	 *
	 * @param emp
	 *            the emp
	 * @throws Exception
	 *             the exception
	 */
	private void validate(EmployeesHistory emp) throws Exception {
		if (StringUtils.isBlank(emp.getSdlNumber()) && StringUtils.isBlank(emp.getSiteNumber())) {
			throw new Exception("Enter either SDL or Site number");
		}

	}

	/**
	 * Update EmployeesHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see EmployeesHistory
	 */
	public void update(EmployeesHistory entity) throws Exception {
		this.dao.update(entity);
	}



	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             the exception
	 * @see EmployeesHistory
	 */
	public EmployeesHistory findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find EmployeesHistory by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             the exception
	 * @see EmployeesHistory
	 */
	public List<EmployeesHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load EmployeesHistory.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             the exception
	 */
	public List<EmployeesHistory> allEmployeesHistory(int first, int pageSize) throws Exception {
		return dao.allEmployeesHistory(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of EmployeesHistory for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the EmployeesHistory
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(EmployeesHistory.class);
	}

	/**
	 * Lazy load EmployeesHistory with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            EmployeesHistory class
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
	 * @param wsp
	 *            the wsp
	 * @return a list of {@link haj.com.entity.EmployeesHistory} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesHistory> allEmployeesHistory(Class<EmployeesHistory> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		if (company == null)
			return null;
		return (List<EmployeesHistory>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters, company.getId(), false);

	}



	
	


	/**
	 * Get count of EmployeesHistory for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            EmployeesHistory class
	 * @param filters
	 *            the filters
	 * @param wsp
	 *            the wsp
	 * @return Number of rows in the EmployeesHistory entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<EmployeesHistory> entity, Map<String, Object> filters, Company company) throws Exception {
		if (company == null)
			return 0;
		return dao.count(entity, filters, company.getId(), false);
	}




	/**
	 * All EmployeesHistory employed.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<EmployeesHistory> allEmployeesHistoryEmployed(Wsp wsp) throws Exception {
		return dao.allEmployeesHistory();
	}

	/**
	 * All EmployeesHistory employed count.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allEmployeesHistoryEmployedCount(Wsp wsp) throws Exception {
		return dao.allEmployeesHistoryEmployedCount(wsp);
	}
	
	/**
	 * Find EmployeesHistory by forEmployee.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.EmployeesHistory}
	 * @throws Exception
	 *             global exception
	 * @see EmployeesHistory
	 */
	@SuppressWarnings("unchecked")
	public List<EmployeesHistory> findByForEmployee(Long id) throws Exception {
		return dao.findByForEmployee(id);
	}
	
	/**Create EmployeesHistory
	 * @throws Exception */
	public void createHistory(Long id) throws Exception
	{
		EmployeesService employeesService=new EmployeesService();
		
		Employees employees=employeesService.findByKey(id);
		EmployeesHistory history=new EmployeesHistory(employees);
		BeanUtils.copyProperties(history, employees);
		history.setId(null);
		create(history);
		
	}
	
	public void deleteByForID(Long id) throws Exception
	{
		List<EmployeesHistory>  histories=findByForEmployee(id);
		for(EmployeesHistory empHistory:histories)
		{
			empHistory.setForEmployees(null);
			dao.update(empHistory);
			dao.delete(empHistory);
		}
	}


}

