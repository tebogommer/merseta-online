package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import haj.com.dao.ExceptionsTableDAO;
import haj.com.entity.ExceptionsTable;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class ExceptionsTableService extends AbstractService {
	/** The dao. */
	private ExceptionsTableDAO dao = new ExceptionsTableDAO();
	private TestService testService = new TestService();

	/**
	 * Get all ExceptionsTable
 	 * @author TechFinium 
 	 * @see   ExceptionsTable
 	 * @return a list of {@link haj.com.entity.ExceptionsTable}
	 * @throws Exception the exception
 	 */
	public List<ExceptionsTable> allExceptionsTable() throws Exception {
	  	return dao.allExceptionsTable();
	}


	/**
	 * Create or update ExceptionsTable.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ExceptionsTable
	 */
	public void create(ExceptionsTable entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ExceptionsTable.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ExceptionsTable
	 */
	public void update(ExceptionsTable entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ExceptionsTable.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ExceptionsTable
	 */
	public void delete(ExceptionsTable entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ExceptionsTable}
	 * @throws Exception the exception
	 * @see    ExceptionsTable
	 */
	public ExceptionsTable findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ExceptionsTable by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ExceptionsTable}
	 * @throws Exception the exception
	 * @see    ExceptionsTable
	 */
	public List<ExceptionsTable> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ExceptionsTable
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ExceptionsTable}
	 * @throws Exception the exception
	 */
	public List<ExceptionsTable> allExceptionsTable(int first, int pageSize) throws Exception {
		return dao.allExceptionsTable(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ExceptionsTable for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ExceptionsTable
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ExceptionsTable.class);
	}
	
    /**
     * Lazy load ExceptionsTable with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ExceptionsTable class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ExceptionsTable} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ExceptionsTable> allExceptionsTable(Class<ExceptionsTable> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ExceptionsTable>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ExceptionsTable for lazy load with filters
     * @author TechFinium 
     * @param entity ExceptionsTable class
     * @param filters the filters
     * @return Number of rows in the ExceptionsTable entity
     * @throws Exception the exception     
     */	
	public int count(Class<ExceptionsTable> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public void runAllExecptions() throws Exception{
		List<ExceptionsTable> allExceptions = allExceptionsTable();
		if (!allExceptions.isEmpty()) {
			List<IDataEntity> deleteList = new ArrayList<IDataEntity>();
			deleteList.addAll(allExceptions);
			if (!deleteList.isEmpty()) {
				dao.deleteBatch(deleteList);
			}
		}
		new Thread(() -> {
			testService.runAll();
		}).start();
	}
}
