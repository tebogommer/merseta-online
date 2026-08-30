package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.QualificationTypeDAO;
import haj.com.entity.lookup.QualificationType;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class QualificationTypeService.
 */
public class QualificationTypeService extends AbstractService {
	/** The dao. */
	private QualificationTypeDAO dao = new QualificationTypeDAO();

	/**
	 * Get all QualificationType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.QualificationType}
	 * @throws Exception the exception
	 * @see   QualificationType
	 */
	public List<QualificationType> allQualificationType() throws Exception {
	  	return dao.allQualificationType();
	}
	
	/**
	 * Find qualification type autocomplete.
	 *
	 * @param desc the desc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<QualificationType> findQualificationTypeAutocomplete(String desc) throws Exception {
	  	return dao.findQualificationTypeAutocomplete(desc);
	}


	/**
	 * Create or update QualificationType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     QualificationType
	 */
	public void create(QualificationType entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  QualificationType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationType
	 */
	public void update(QualificationType entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  QualificationType.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    QualificationType
	 */
	public void delete(QualificationType entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.QualificationType}
	 * @throws Exception the exception
	 * @see    QualificationType
	 */
	public QualificationType findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find QualificationType by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.QualificationType}
	 * @throws Exception the exception
	 * @see    QualificationType
	 */
	public List<QualificationType> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load QualificationType.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.QualificationType}
	 * @throws Exception the exception
	 */
	public List<QualificationType> allQualificationType(int first, int pageSize) throws Exception {
		return dao.allQualificationType(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of QualificationType for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the QualificationType
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(QualificationType.class);
	}
	
    /**
     * Lazy load QualificationType with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 QualificationType class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.QualificationType} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<QualificationType> allQualificationType(Class<QualificationType> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<QualificationType>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of QualificationType for lazy load with filters.
     *
     * @author TechFinium
     * @param entity QualificationType class
     * @param filters the filters
     * @return Number of rows in the QualificationType entity
     * @throws Exception the exception
     */	
	public int count(Class<QualificationType> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the qualification type
	 * @throws Exception the exception
	 */
	public QualificationType findByCode(String code) throws Exception { 
		if (StringUtils.isBlank(code)) return null;
		return dao.findByCode(code.trim());
	}
}
