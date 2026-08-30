package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.ProviderAccreditAssessorDAO;
import haj.com.entity.lookup.ProviderAccreditAssessor;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderAccreditAssessorService.
 */
public class ProviderAccreditAssessorService extends AbstractService {
	/** The dao. */
	private ProviderAccreditAssessorDAO dao = new ProviderAccreditAssessorDAO();

	/**
	 * Get all ProviderAccreditAssessor.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.ProviderAccreditAssessor}
	 * @throws Exception the exception
	 * @see   ProviderAccreditAssessor
	 */
	public List<ProviderAccreditAssessor> allProviderAccreditAssessor() throws Exception {
	  	return dao.allProviderAccreditAssessor();
	}


	/**
	 * Create or update ProviderAccreditAssessor.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ProviderAccreditAssessor
	 */
	public void create(ProviderAccreditAssessor entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ProviderAccreditAssessor.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderAccreditAssessor
	 */
	public void update(ProviderAccreditAssessor entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ProviderAccreditAssessor.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderAccreditAssessor
	 */
	public void delete(ProviderAccreditAssessor entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderAccreditAssessor}
	 * @throws Exception the exception
	 * @see    ProviderAccreditAssessor
	 */
	public ProviderAccreditAssessor findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ProviderAccreditAssessor by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ProviderAccreditAssessor}
	 * @throws Exception the exception
	 * @see    ProviderAccreditAssessor
	 */
	public List<ProviderAccreditAssessor> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ProviderAccreditAssessor.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ProviderAccreditAssessor}
	 * @throws Exception the exception
	 */
	public List<ProviderAccreditAssessor> allProviderAccreditAssessor(int first, int pageSize) throws Exception {
		return dao.allProviderAccreditAssessor(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ProviderAccreditAssessor for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the ProviderAccreditAssessor
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ProviderAccreditAssessor.class);
	}
	
    /**
     * Lazy load ProviderAccreditAssessor with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 ProviderAccreditAssessor class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ProviderAccreditAssessor} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ProviderAccreditAssessor> allProviderAccreditAssessor(Class<ProviderAccreditAssessor> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ProviderAccreditAssessor>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of ProviderAccreditAssessor for lazy load with filters.
     *
     * @author TechFinium
     * @param entity ProviderAccreditAssessor class
     * @param filters the filters
     * @return Number of rows in the ProviderAccreditAssessor entity
     * @throws Exception the exception
     */	
	public int count(Class<ProviderAccreditAssessor> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
