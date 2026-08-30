package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.QualificationTasksDAO;
import haj.com.entity.lookup.QualificationTasks;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class QualificationTasksService extends AbstractService {
	/** The dao. */
	private QualificationTasksDAO dao = new QualificationTasksDAO();

	/**
	 * Get all QualificationTasks
 	 * @author TechFinium 
 	 * @see   QualificationTasks
 	 * @return a list of {@link haj.com.entity.QualificationTasks}
	 * @throws Exception the exception
 	 */
	public List<QualificationTasks> allQualificationTasks() throws Exception {
	  	return dao.allQualificationTasks();
	}


	/**
	 * Create or update QualificationTasks.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     QualificationTasks
	 */
	public void create(QualificationTasks entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  QualificationTasks.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationTasks
	 */
	public void update(QualificationTasks entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  QualificationTasks.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationTasks
	 */
	public void delete(QualificationTasks entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.QualificationTasks}
	 * @throws Exception the exception
	 * @see    QualificationTasks
	 */
	public QualificationTasks findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find QualificationTasks by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.QualificationTasks}
	 * @throws Exception the exception
	 * @see    QualificationTasks
	 */
	public List<QualificationTasks> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load QualificationTasks
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.QualificationTasks}
	 * @throws Exception the exception
	 */
	public List<QualificationTasks> allQualificationTasks(int first, int pageSize) throws Exception {
		return dao.allQualificationTasks(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of QualificationTasks for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the QualificationTasks
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(QualificationTasks.class);
	}
	
    /**
     * Lazy load QualificationTasks with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 QualificationTasks class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.QualificationTasks} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<QualificationTasks> allQualificationTasks(Class<QualificationTasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<QualificationTasks>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of QualificationTasks for lazy load with filters
     * @author TechFinium 
     * @param entity QualificationTasks class
     * @param filters the filters
     * @return Number of rows in the QualificationTasks entity
     * @throws Exception the exception     
     */	
	public int count(Class<QualificationTasks> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
