package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.QualificationEntryRequirementDAO;
import haj.com.entity.lookup.QualificationEntryRequirement;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationEntryRequirementService.
 */
public class QualificationEntryRequirementService extends AbstractService {
	/** The dao. */
	private QualificationEntryRequirementDAO dao = new QualificationEntryRequirementDAO();

	/**
	 * Get all QualificationEntryRequirement.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.QualificationEntryRequirement}
	 * @throws Exception the exception
	 * @see   QualificationEntryRequirement
	 */
	public List<QualificationEntryRequirement> allQualificationEntryRequirement() throws Exception {
	  	return dao.allQualificationEntryRequirement();
	}


	/**
	 * Create or update QualificationEntryRequirement.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     QualificationEntryRequirement
	 */
	public void create(QualificationEntryRequirement entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  QualificationEntryRequirement.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationEntryRequirement
	 */
	public void update(QualificationEntryRequirement entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  QualificationEntryRequirement.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationEntryRequirement
	 */
	public void delete(QualificationEntryRequirement entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.QualificationEntryRequirement}
	 * @throws Exception the exception
	 * @see    QualificationEntryRequirement
	 */
	public QualificationEntryRequirement findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find QualificationEntryRequirement by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.QualificationEntryRequirement}
	 * @throws Exception the exception
	 * @see    QualificationEntryRequirement
	 */
	public List<QualificationEntryRequirement> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load QualificationEntryRequirement.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.QualificationEntryRequirement}
	 * @throws Exception the exception
	 */
	public List<QualificationEntryRequirement> allQualificationEntryRequirement(int first, int pageSize) throws Exception {
		return dao.allQualificationEntryRequirement(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of QualificationEntryRequirement for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the QualificationEntryRequirement
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(QualificationEntryRequirement.class);
	}
	
    /**
     * Lazy load QualificationEntryRequirement with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 QualificationEntryRequirement class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.QualificationEntryRequirement} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<QualificationEntryRequirement> allQualificationEntryRequirement(Class<QualificationEntryRequirement> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<QualificationEntryRequirement>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of QualificationEntryRequirement for lazy load with filters.
     *
     * @author TechFinium
     * @param entity QualificationEntryRequirement class
     * @param filters the filters
     * @return Number of rows in the QualificationEntryRequirement entity
     * @throws Exception the exception
     */	
	public int count(Class<QualificationEntryRequirement> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
