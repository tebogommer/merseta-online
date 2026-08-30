package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.PurposeOfSiteVisitChildDAO;
import haj.com.entity.lookup.PurposeOfSiteVisitChild;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class PurposeOfSiteVisitChildService extends AbstractService {
	/** The dao. */
	private PurposeOfSiteVisitChildDAO dao = new PurposeOfSiteVisitChildDAO();

	/**
	 * Get all PurposeOfSiteVisitChild
 	 * @author TechFinium 
 	 * @see   PurposeOfSiteVisitChild
 	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitChild}
	 * @throws Exception the exception
 	 */
	public List<PurposeOfSiteVisitChild> allPurposeOfSiteVisitChild() throws Exception {
	  	return dao.allPurposeOfSiteVisitChild();
	}


	/**
	 * Create or update PurposeOfSiteVisitChild.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     PurposeOfSiteVisitChild
	 */
	public void create(PurposeOfSiteVisitChild entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  PurposeOfSiteVisitChild.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisitChild
	 */
	public void update(PurposeOfSiteVisitChild entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  PurposeOfSiteVisitChild.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisitChild
	 */
	public void delete(PurposeOfSiteVisitChild entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PurposeOfSiteVisitChild}
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisitChild
	 */
	public PurposeOfSiteVisitChild findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find PurposeOfSiteVisitChild by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitChild}
	 * @throws Exception the exception
	 * @see    PurposeOfSiteVisitChild
	 */
	public List<PurposeOfSiteVisitChild> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load PurposeOfSiteVisitChild
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.PurposeOfSiteVisitChild}
	 * @throws Exception the exception
	 */
	public List<PurposeOfSiteVisitChild> allPurposeOfSiteVisitChild(int first, int pageSize) throws Exception {
		return dao.allPurposeOfSiteVisitChild(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of PurposeOfSiteVisitChild for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the PurposeOfSiteVisitChild
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(PurposeOfSiteVisitChild.class);
	}
	
    /**
     * Lazy load PurposeOfSiteVisitChild with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 PurposeOfSiteVisitChild class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.PurposeOfSiteVisitChild} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<PurposeOfSiteVisitChild> allPurposeOfSiteVisitChild(Class<PurposeOfSiteVisitChild> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<PurposeOfSiteVisitChild>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of PurposeOfSiteVisitChild for lazy load with filters
     * @author TechFinium 
     * @param entity PurposeOfSiteVisitChild class
     * @param filters the filters
     * @return Number of rows in the PurposeOfSiteVisitChild entity
     * @throws Exception the exception     
     */	
	public int count(Class<PurposeOfSiteVisitChild> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
