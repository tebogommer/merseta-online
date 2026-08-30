package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.RelationshipToCompanyDAO;
import haj.com.entity.lookup.RelationshipToCompany;
import haj.com.framework.AbstractService;
import haj.com.service.RejectReasonMultipleSelectionService;

public class RelationshipToCompanyService extends AbstractService {
	/** The dao. */
	private RelationshipToCompanyDAO dao = new RelationshipToCompanyDAO();
	
	private static RelationshipToCompanyService relationshipToCompanyService;
	public static synchronized RelationshipToCompanyService instance() {
		if (relationshipToCompanyService == null) {
			relationshipToCompanyService = new RelationshipToCompanyService();
		}
		return relationshipToCompanyService;
	}

	/**
	 * Get all RelationshipToCompany
 	 * @author TechFinium 
 	 * @see   RelationshipToCompany
 	 * @return a list of {@link haj.com.entity.RelationshipToCompany}
	 * @throws Exception the exception
 	 */
	public List<RelationshipToCompany> allRelationshipToCompany() throws Exception {
	  	return dao.allRelationshipToCompany();
	}


	/**
	 * Create or update RelationshipToCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     RelationshipToCompany
	 */
	public void create(RelationshipToCompany entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  RelationshipToCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    RelationshipToCompany
	 */
	public void update(RelationshipToCompany entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  RelationshipToCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    RelationshipToCompany
	 */
	public void delete(RelationshipToCompany entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.RelationshipToCompany}
	 * @throws Exception the exception
	 * @see    RelationshipToCompany
	 */
	public RelationshipToCompany findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find RelationshipToCompany by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.RelationshipToCompany}
	 * @throws Exception the exception
	 * @see    RelationshipToCompany
	 */
	public List<RelationshipToCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load RelationshipToCompany
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.RelationshipToCompany}
	 * @throws Exception the exception
	 */
	public List<RelationshipToCompany> allRelationshipToCompany(int first, int pageSize) throws Exception {
		return dao.allRelationshipToCompany(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of RelationshipToCompany for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the RelationshipToCompany
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(RelationshipToCompany.class);
	}
	
    /**
     * Lazy load RelationshipToCompany with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 RelationshipToCompany class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.RelationshipToCompany} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<RelationshipToCompany> allRelationshipToCompany(Class<RelationshipToCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<RelationshipToCompany>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of RelationshipToCompany for lazy load with filters
     * @author TechFinium 
     * @param entity RelationshipToCompany class
     * @param filters the filters
     * @return Number of rows in the RelationshipToCompany entity
     * @throws Exception the exception     
     */	
	public int count(Class<RelationshipToCompany> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
