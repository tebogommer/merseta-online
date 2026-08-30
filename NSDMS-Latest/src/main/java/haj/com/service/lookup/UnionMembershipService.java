package haj.com.service.lookup;

import java.util.List;

import haj.com.dao.lookup.UnionMembershipDAO;
import haj.com.entity.lookup.UnionMembership;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class UnionMembershipService extends AbstractService {
	/** The dao. */
	private UnionMembershipDAO dao = new UnionMembershipDAO();

	/**
	 * Get all UnionMembership
 	 * @author TechFinium 
 	 * @see   UnionMembership
 	 * @return a list of {@link haj.com.entity.UnionMembership}
	 * @throws Exception the exception
 	 */
	public List<UnionMembership> allUnionMembership() throws Exception {
	  	return dao.allUnionMembership();
	}


	/**
	 * Create or update UnionMembership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UnionMembership
	 */
	public void create(UnionMembership entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UnionMembership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UnionMembership
	 */
	public void update(UnionMembership entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UnionMembership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UnionMembership
	 */
	public void delete(UnionMembership entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UnionMembership}
	 * @throws Exception the exception
	 * @see    UnionMembership
	 */
	public UnionMembership findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UnionMembership by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UnionMembership}
	 * @throws Exception the exception
	 * @see    UnionMembership
	 */
	public List<UnionMembership> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UnionMembership
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UnionMembership}
	 * @throws Exception the exception
	 */
	public List<UnionMembership> allUnionMembership(int first, int pageSize) throws Exception {
		return dao.allUnionMembership(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UnionMembership for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UnionMembership
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UnionMembership.class);
	}
	
    /**
     * Lazy load UnionMembership with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UnionMembership class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UnionMembership} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UnionMembership> allUnionMembership(Class<UnionMembership> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UnionMembership>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UnionMembership for lazy load with filters
     * @author TechFinium 
     * @param entity UnionMembership class
     * @param filters the filters
     * @return Number of rows in the UnionMembership entity
     * @throws Exception the exception     
     */	
	public int count(Class<UnionMembership> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
